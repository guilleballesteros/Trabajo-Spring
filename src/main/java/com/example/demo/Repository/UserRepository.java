package com.example.demo.Repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.User;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Serializable>{
	public abstract User findByUsername(String username);
	public abstract List<User> findByRole(String role);
	public abstract User findByVerificationCode(String code);
	

}
