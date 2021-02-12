package com.example.demo.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;

import com.example.demo.entity.User;
import com.example.demo.model.UserModel;

public interface UserService {
	List<UserModel> listAllUsers();
	List<UserModel> listAllpacientes();
	List<UserModel> listAllmedicos();
	UserModel findModel(int id);
	User addUser(UserModel userModel);
	int removeUser(int id);
	User transform(UserModel userModel);
	UserModel transform(User user);
	User updateUser(UserModel userModel);
	void sendVerificationEmail(User user, String siteURL) throws MessagingException, UnsupportedEncodingException;
	     
    
}
