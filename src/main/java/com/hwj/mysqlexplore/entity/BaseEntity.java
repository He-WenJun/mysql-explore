package com.hwj.mysqlexplore.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * 这个注解表示在父类上面的，用来标识父类。
 * 基于代码复用和模型分离的思想，在项目开发中使用JPA的@MappedSuperclass注解将实体类的多个属性分别封装到不同的非实体类中。例如，数据库表中都需要id来表示编号，id是这些映射实体类的通用的属性，交给jpa统一生成主键id编号，那么使用一个父类来封装这些通用属性，并用@MappedSuperclas标识。
 * 	注意:
 * 	1.标注为@MappedSuperclass的类将不是一个完整的实体类，他将不会映射到数据库表，但是他的属性都将映射到其子类的数据库字段中。
 * 	2.标注为@MappedSuperclass的类不能再标注@Entity或@Table注解，也无需实现序列化接口。
 */
@MappedSuperclass
@Getter
@Setter
public class BaseEntity {
	
	/**
	 * JAP 的 四种主键生成策略：
	 * IDENTITY：采用数据库 ID自增长的方式来自增主键字段，Oracle 不支持这种方式；
	 * AUTO： JPA自动选择合适的策略，是默认选项；
	 * SEQUENCE：通过序列产生主键，通过 @SequenceGenerator 注解指定序列名，MySql 不支持这种方式
	 * TABLE：通过表产生主键，框架借由表模拟序列产生主键，使用该策略可以使应用更易于数据库移植。
	 */
	@Id
	@GeneratedValue(strategy  = GenerationType.SEQUENCE)
	private Long id;
	
	@Column(nullable = false)
	private Boolean deleteState;
	
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	
	/**
	 * jap实体生命周期回调
	 * PrePersist 新增时的回调
	 * PreRemove 删除时的回调
	 * PreUpdate 更新时的回调
	 */
	@PrePersist
	protected void prePersist () {
		deleteState = false;
		createTime = new Date();
	}
}
