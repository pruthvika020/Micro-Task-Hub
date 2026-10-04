package com.example.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Users;
import com.example.service.UsersService;

@RestController
@RequestMapping("/authservice")
public class UsersController {
	
	@Autowired
	UsersService service;
	
	@GetMapping("/")
	public String test() {
		return "Spring Boot - Backend Running!";
	}
	
	@PostMapping("/signin")
	public Object signin(@RequestBody Map<String, String> u1) {
		return service.signinService(u1);
	}
	
	@PostMapping("/signup")
	public Object signup(@RequestBody Users u1) {
		return service.signupService(u1);
	}
	
	@GetMapping("/uinfo")
	public Object uinfo(@RequestHeader("Token") String token) {
		return service.uinfo(token);
	}
	
	@GetMapping("/profile")
	public Object getProfile(@RequestHeader("Token") String token) {
		return service.getProfile(token);
	}
	
	//http://localhost:8001/authservice/getallusers/2/3
	@GetMapping("/getallusers/{page}/{limit}")
	public Object uinfo(@PathVariable("page") int page, @PathVariable("limit") int limit, @RequestHeader("Token") String token) {
		return service.getAllUsers(page, limit, token);
	}
	
	@GetMapping("/getuser/{id}")
	public Object getUser(@PathVariable("id") int id, @RequestHeader("Token") String token) {
		return service.getUserById(id, token);
	}
	
	@PostMapping("saveuser")
	public Object saveUser(@RequestBody Users u1, @RequestHeader("Token") String token) {
		return service.saveUser(u1, token);
	}
	
	@PutMapping("updateuser/{id}")
	public Object updateUser(@PathVariable("id") int id, @RequestBody Users u1, @RequestHeader("Token") String token) {
		return service.updateUser(id, u1, token);
	}
	
	@DeleteMapping("deleteuser/{id}")
	public Object deleteUser(@PathVariable("id") int id, @RequestHeader("Token") String Token) {
		return service.deleteUser(id, Token);
	}
	
	@GetMapping("searchuser/{key}")
	public Object searchUser(@PathVariable("key") String key) {
		return service.searchUserByKey(key);
	}
	
	//the parameter is from url
	//http://localhost:8001/authservice/signup/123/abc
	//@GetMapping("/signup/{x}/{y}")
	//public void fun (@PathVariable("x") int a, @PathVariable("y") String b) {}
	
	//http://localhost:8001/authservice/signup?id=123&name=abc
	//@GetMapping("/signup")
	//public void fun (@PathParam("id") int id, @PathParam("name") String name)
	
}
