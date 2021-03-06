package com.example.demo.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.Repository.UserRepository;
import com.example.demo.model.UserModel;
import com.example.demo.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserDetailsService, UserService{
	
	@Autowired
	@Qualifier("userRepository")
	private UserRepository userRepository;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private DozerBeanMapper dozer;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	private static final Log Logger=LogFactory.getLog(UserServiceImpl.class);
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		com.example.demo.entity.User usuario=userRepository.findByUsername(username);
		
		UserBuilder builder=null;
		
		if(usuario!=null) {
			builder=User.withUsername(username);
			builder.disabled(false);
			builder.password(usuario.getPassword());
			builder.authorities(new SimpleGrantedAuthority(usuario.getRole()));
		}
		else {
			throw new UsernameNotFoundException("Usuario no encontrado");
		}
		return builder.build();
	}
	
	public com.example.demo.entity.User register(com.example.demo.entity.User user){
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setEnabled(true);
		user.setRole("ROLE_PACIENTE");
		return userRepository.save(user);
	}

	@Override
	public List<UserModel> listAllUsers() {
		// TODO Auto-generated method stub
		return userRepository.findAll().stream().map(c->transform(c)).collect(Collectors.toList());
	}

	@Override
	public UserModel findModel(int id) {
		com.example.demo.entity.User usuario=userRepository.findById(id).orElse(null);
		Logger.info(usuario.getFechaalta());
		Logger.info(transform(usuario).getFechaalta());
		return transform(usuario);
	}


	@Override
	public com.example.demo.entity.User transform(UserModel userModel) {
		// TODO Auto-generated method stub
		return dozer.map(userModel, com.example.demo.entity.User.class);
	}

	@Override
	public UserModel transform(com.example.demo.entity.User user) {
		UserModel usuario= dozer.map(user, UserModel.class);
		usuario.setFechaalta(user.getFechaalta());
		return usuario;
	}

	@Override
	public com.example.demo.entity.User addUser(UserModel userModel) {
		return userRepository.save(transform(userModel));
	}

	@Override
	public int removeUser(int id) {
		userRepository.deleteById(id);
		return 0;
	}

	@Override
	public com.example.demo.entity.User updateUser(UserModel userModel) {
		Logger.info("Code: "+userModel.getVerificationCode());
		com.example.demo.entity.User usuario=userRepository.save(transform(userModel));
		Logger.info("Code: "+usuario.getVerificationCode());
		return usuario;
	}

	@Override
	public List<UserModel> listAllpacientes() {
		return userRepository.findByRole("ROLE_PACIENTE").
				stream().map(c-> transform(c)).collect(Collectors.toList());
	}

	@Override
	public List<UserModel> listAllmedicos() {
		return userRepository.findByRole("ROLE_MEDICO").
				stream().map(c-> transform(c)).collect(Collectors.toList());
	}
	
	public UserModel findByUsername(String username) {
		return transform(userRepository.findByUsername(username));
		
	}
	public UserModel findByCode(String code) {
		return transform(userRepository.findByVerificationCode(code));
	}
	
	@Override
	public void sendVerificationEmail(com.example.demo.entity.User user, String siteURL) throws MessagingException, UnsupportedEncodingException {
	    String toAddress = user.getUsername();
	    String fromAddress = "trabajospring@gmail.com";
	    String senderName = "SegPrivada";
	    String subject = "Please verify your registration";
	    String content = "Dear [[name]],<br>"
	            + "Please click the link below to verify your registration:<br>"
	            + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
	            + "Thank you,<br>"
	            + "Your company name.";
	     
	    MimeMessage message = mailSender.createMimeMessage();
	    MimeMessageHelper helper = new MimeMessageHelper(message);
	     
	    helper.setFrom(fromAddress, senderName);
	    helper.setTo(toAddress);
	    helper.setSubject(subject);
	     
	    content = content.replace("[[name]]", user.getNombre());
	    String verifyURL = siteURL + "/verify?code=" + user.getVerificationCode();
	     
	    content = content.replace("[[URL]]", verifyURL);
	     
	    helper.setText(content, true);
	     
	    mailSender.send(message);
	     
	}

	
	
	
	

}
