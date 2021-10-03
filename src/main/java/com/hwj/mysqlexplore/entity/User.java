package com.hwj.mysqlexplore.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table
public class User extends BaseEntity {
	@Column
	private String name;
	
	
	@Column
	private Integer age;
}
