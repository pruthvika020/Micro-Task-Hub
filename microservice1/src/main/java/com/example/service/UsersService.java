package com.example.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.model.Users;
import com.example.repository.UserRepository;

@Service
public class UsersService {
	
	@Autowired
	UserRepository repo;
	
	@Autowired
	JWTService jwtService;
			

	public Object signupService(Users u1) {
		
		Map<String, Object> response = new HashMap<>();
		
		try {
		
			Object id = repo.checkEmail(u1.getEmail());
			if(id != null) {
				response.put("code", 501);
				response.put("message", "user already exist");
			}
			else {
				u1.setRole(1);
				u1.setStatus(1);
				repo.save(u1);
				
				response.put("code", 200);
				response.put("message", "User Registerd Successfully");
			}
					
			return response;
		}
		catch (Exception e) {
			response.put("code", 500);
			response.put("message", e.getMessage());
			return response;
		}
	}
	
	public Object signinService(Map<String, String> u1) {
		Map<String, Object> response = new HashMap<>();
		
		try {
		
			Object role = repo.checkCredentials(u1.get("username"), u1.get("password"));
			Users user = repo.findByFullname(u1.get("username"));
			
			if(role == null) {
				response.put("code", "501");
				response.put("message", "Authentication Failed");
			}else {
				response.put("code", 200);
				response.put("jwt", jwtService.generateJWT(u1, role.toString(), user.getId()));
			}
			
			return response;
		}
		catch (Exception e) {
			response.put("code", 500);
			response.put("message", e.getMessage());
			return response;
		}
	}
	
	public Object uinfo(String token) {
		
		Map<String, Object> response = new HashMap<>();
		
		try {
			Map<String, String> parsedJWT = jwtService.validateJWT(token);
			Users u1 = repo.findByFullname(parsedJWT.get("username"));
			
			List<Object> menulist = repo.getMenus(Integer.valueOf(parsedJWT.get("role")));
			
			response.put("code", 200);
			response.put("fullname", u1.getFullname());
			response.put("menulist", menulist);
			return response;
		}catch (Exception e) {
			response.put("code", 500);
			response.put("message", e.getMessage());
			return response;
		}
	}
	
	public Object getProfile(String token) {
		Map<String, Object> response = new HashMap<>();
		
		try {
			Map<String, String> parsedJWT = jwtService.validateJWT(token);
			Object user = repo.getprofile(parsedJWT.get("username"));
			
			response.put("code", 200);
			response.put("user", user);
			return response;
		}catch (Exception e) {
			response.put("code", 500);
			response.put("message", e.getMessage());
			return response;
		}
	}
	
	public Object getAllUsers(int page, int limit, String token){
		
		Map<String, Object> response = new HashMap<>();
		
		try {
			Map<String, String> parsedJWT = jwtService.validateJWT(token);
			Pageable pageable = PageRequest.of(page-1, limit);
			Page<Users> users = repo.findAll(pageable);
			
			response.put("code", 200);
			response.put("page", page);
			response.put("size", limit);
			response.put("totalpages", users.getTotalPages());
			response.put("users", users.getContent());
			return response;
		}catch (Exception e) {
			response.put("code", 500);
			response.put("message", e.getMessage());
			return response;
		}
		
	}
	
	public Object getUserById(int id, String token) {
		Map<String, Object> response = new HashMap<>();
		
		try {
			Map<String, String> parsedJWT = jwtService.validateJWT(token);
			
			Users user = repo.findById(id).get();
			
			if(user == null) {
				throw new Exception("User Not Found");
			}
			
			response.put("code", 200);
			response.put("user", user);
			return response;
		}catch (Exception e) {
			response.put("code", 500);
			response.put("message", e.getMessage());
			return response;
		}
	}
	
	public Object saveUser(Users u1, String token) {
		Map<String, Object> response = new HashMap<>();
		
		try {
			Map<String, String> parsedJWT = jwtService.validateJWT(token);
			
			repo.save(u1);
			
			response.put("code", 200);
			response.put("message", "saved successfully");
			return response;
		}catch (Exception e) {
			response.put("code", 500);
			response.put("message", e.getMessage());
			return response;
		}
	}
	
	public Object updateUser(int id, Users u1, String token) {
		Map<String, Object> response = new HashMap<>();
		
		try {
			Map<String, String> parsedJWT = jwtService.validateJWT(token);
			
			Users user = repo.findById(id).get();
			if(user == null) {
				throw new Exception("user not exist");
			}
			
			user.setFullname(u1.getFullname());
			user.setPassword(u1.getPassword());
			user.setPhone(u1.getPhone());
			user.setEmail(u1.getEmail());
			repo.save(user);
			
			response.put("code", 200);
			response.put("message", "updated successfully");
			return response;
		}catch (Exception e) {
			response.put("code", 500);
			response.put("message", e.getMessage());
			return response;
		}
	}
	
	public Object deleteUser(int id, String token) {
		Map<String, Object> response = new HashMap<>();
		
		try {
			Map<String, String> parsedJWT = jwtService.validateJWT(token);
			
			Users user = repo.findById(id).get();
			if(user == null) {
				throw new Exception("user not exist");
			}
			
			repo.deleteById(id);
			
			response.put("code", 200);
			response.put("message", "deleted successfully");
			return response;
		}catch (Exception e) {
			response.put("code", 500);
			response.put("message", e.getMessage());
			return response;
		}
	}
	
	public Object searchUserByKey(String key) {
		Map<String, Object> response = new HashMap<>();
		
		try {
			
			List<Users> users = repo.searchKey(key);
			
			response.put("code", 200);
			response.put("users", users);
			return response;
		}catch (Exception e) {
			response.put("code", 500);
			response.put("message", e.getMessage());
			return response;
		}
	}
	
}









