package com.sirzhangs.provider.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sirzhangs.common.entity.RequestResult;
import com.sirzhangs.provider.dto.UserDto;
import com.sirzhangs.provider.entity.User;
import com.sirzhangs.provider.service.UserService;

@RestController
@RequestMapping(value = "/user/manage")
public class UserController {

	@Autowired
	private UserService userService;
	
	@PostMapping("add")
	public RequestResult add(
			@RequestBody User user
			) {
		return userService.add(user);
	}
	
	@GetMapping("delete")
	public RequestResult delete(
			@RequestBody List<String> ids
			) {
		return userService.delete(ids);
	}
	
	@PostMapping("update")
	public RequestResult update(
			@RequestBody User user
			) {
		return userService.update(user);
	}
	
	@GetMapping("findById/{id}")
	public RequestResult findById(
			@PathVariable(value = "id",required = true) String id
			) {
		return userService.findById(id);
	}
	
	@PostMapping("findList")
	public RequestResult findList(
			@RequestBody UserDto userDto
			) {
		return userService.findList(userDto);
	}
	
}
