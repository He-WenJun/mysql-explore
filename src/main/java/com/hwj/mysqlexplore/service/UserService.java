package com.hwj.mysqlexplore.service;

import com.hwj.mysqlexplore.dao.UserRepository;
import com.hwj.mysqlexplore.entity.User;
import com.hwj.mysqlexplore.util.CreateDateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@Slf4j
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	private static final ExecutorService pool = Executors.newFixedThreadPool(4);
	
	private int completeThreadCount = 0;
	
	private synchronized void threadCountIncrement () {
		completeThreadCount ++;
		log.info(String.format("完成工作的线程数：%s，线程：%s-%s", completeThreadCount, Thread.currentThread().getId(), Thread.currentThread().getName()));
	}
	
	
	public ResponseEntity<String> createDate(Integer createCount) {
		int batchCount = 10000;
		int totalThreadCount = createCount / batchCount;
		log.info(String.format("准备创建线程任务数：%s", totalThreadCount));
		for (int threadCount = 0; threadCount < totalThreadCount; threadCount++) {
			
			Runnable item = () ->{
				List<User> usersCache = new LinkedList<>();
				Random random = new Random();
				for (int count = 0; count < batchCount; count ++) {
					User user = new User();
					user.setName(CreateDateUtil.getRandomName(3));
					user.setAge(random.nextInt(100) + 1);
					usersCache.add(user);
				}
				userRepository.saveAll(usersCache);
				threadCountIncrement();
			};
			
			pool.submit(item);
		}
		return ResponseEntity.ok("数据创建完成");
	}
}
