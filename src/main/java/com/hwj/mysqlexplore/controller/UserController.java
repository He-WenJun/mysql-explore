package com.hwj.mysqlexplore.controller;

import com.hwj.mysqlexplore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	@GetMapping("/create")
	public ResponseEntity<String> createDate (Integer createCount) {
		return userService.createDate(createCount);
	}
}
