package com.prs.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prs.business.JsonResponse;
import com.prs.business.User;
import com.prs.db.UserRepository;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserRepository userRepo;

	@GetMapping("/")
	public JsonResponse getAll() {
		JsonResponse jr = null;
		try {
			jr = JsonResponse.getInstance(userRepo.findAll());

		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;

	}

	@GetMapping("/{id}")
	public JsonResponse getAll(@PathVariable int id) {
		JsonResponse jr = null;
		try {
			Optional<User> p = userRepo.findById(id);
			if (p.isPresent())
				jr = JsonResponse.getInstance(p);
			else
				jr = JsonResponse.getInstance("No User found for id: " + id);

		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;

	}
	
	@DeleteMapping("/")
	public JsonResponse delete(@RequestBody User p) {
		JsonResponse jr = null;
		// NOTE: May want to enhance exception handling
		try {
			if (userRepo.existsById(p.getId())) {
				userRepo.delete(p);
			jr = JsonResponse.getInstance("User deleted.");
			}
			else {
				jr = JsonResponse.getInstance("User ID: " + p.getId() + " does not exist and you are attempting to delete it");
			}

		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
		
	}
	
	
	@DeleteMapping("/{id}")
	public JsonResponse delete(@PathVariable int id) {
		JsonResponse jr = null;
		try {
			Optional<User> user = userRepo.findById(id);
			if (user.isPresent()) {
				userRepo.deleteById(id);
				jr = JsonResponse.getInstance(user);
			} else
				jr = JsonResponse.getInstance("Delete failed. No user for id: " + id);
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}
	
	
	
	@PutMapping("/")
	public JsonResponse update(@RequestBody User p) {
		JsonResponse jr = null;
		// NOTE: May want to enhance exception handling
		try {
			if (userRepo.existsById(p.getId())) {
			jr = JsonResponse.getInstance(userRepo.save(p));
			}
			else {
				jr = JsonResponse.getInstance("User ID: " + p.getId() + " does not exist and you are attempting to save it");
			}

		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
		
	}

	
	@PostMapping("/")
	public JsonResponse add(@RequestBody User p) {
		JsonResponse jr = null;
		// NOTE: May want to enhance exception handling
		try {
			jr = JsonResponse.getInstance(userRepo.save(p));

		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;

	}
	
	@PostMapping("/authenticate")
	public JsonResponse authenticate(@RequestBody User user) {
		JsonResponse jr = null;
		try {
			Optional<User> p = userRepo.findByuserNameAndPassword(user.getUserName(), user.getPassword());
			if (p.isPresent())
				jr = JsonResponse.getInstance(p);
			else
				jr = JsonResponse.getInstance("No user found for User Name: " + user.getUserName());

		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;

	}

}
