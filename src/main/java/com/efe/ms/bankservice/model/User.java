package com.efe.ms.bankservice.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseModel{

	private String name;
	private String nickName;
	private String accessToken;
	private String password;
	private String phoneNo;
	private String address;
	private Integer age;
	private String introduction;
	private String avatar;
	private List<String> roles;
	
	public User(String id,String name){
		this.id = id;
		this.name = name;
	}
}
