package com.prs.db;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.prs.business.User;

public interface UserRepository extends CrudRepository<User, Integer> {
	
	Optional<User> findByuserNameAndPassword(String userName, String Password);

}
