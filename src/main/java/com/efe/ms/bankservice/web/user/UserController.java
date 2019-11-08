package com.efe.ms.bankservice.web.user;

import java.util.Arrays;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.efe.ms.bankservice.model.User;
import com.efe.ms.bankservice.vo.BusinessResult;

/**
 * 用户控制器
 *
 * @author TianLong Liu
 * @date 2019年11月7日 上午9:49:30
 */
@RestController
@RequestMapping("/user")
public class UserController {

	@PostMapping("/login")
	public BusinessResult login(@RequestBody User user){
		System.out.println("user login"); 
		if("admin".equals(user.getName()) && "111111".equals(user.getPassword())){
			return BusinessResult.success().data("this-is-admin-token"); 
		}
		return BusinessResult.fail("用户名或密码不正确");
	}
	
	@GetMapping("/info")
	public BusinessResult info(User user){
		user.setRoles(Arrays.asList(new String[]{"admin"}));
		user.setId("1");
		user.setName("admin");
		user.setIntroduction("I am admin");
		user.setPassword(null); 
		return BusinessResult.success().data(user); 
	}
	
	@PostMapping("/logout")
	public BusinessResult logout(@RequestBody(required=false) User user){
		System.out.println("user logout"); 
		return BusinessResult.success().data("success");
	}
}
