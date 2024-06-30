package com.train.Controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.train.Service.UserService;
import com.train.model.User;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {

	@Autowired
	private UserService service;

	@GetMapping("/")
	public ResponseEntity<String> hello() {

		return new ResponseEntity<String>("<h1>Welcome to Login Module...</h1>", HttpStatus.OK);

	}

	@PostMapping("/create")
	public ResponseEntity<User> createLogin(RequestEntity<User> request) {

		User login = request.getBody();

		if (login != null) {

			User login1 = service.createLogin(login);

			return new ResponseEntity<User>(login1, HttpStatus.OK);

		}

		return new ResponseEntity<User>(login, HttpStatus.NO_CONTENT);

	}

	@PatchMapping(value = "/update")
	public ResponseEntity<User> updateLogin(RequestEntity<User> request) {

		User login = request.getBody();

		if (login != null) {

			User login1 = service.updateUser(login);

			return new ResponseEntity<User>(login1, HttpStatus.OK);

		}

		return new ResponseEntity<User>(login, HttpStatus.NO_CONTENT);

	}

	@GetMapping("/list")
	public ResponseEntity<List<User>> getAllLogins() {

		List<User> list = service.getAllLogins();

		if (list != null) {

			return new ResponseEntity<List<User>>(list, HttpStatus.OK);

		}

		return new ResponseEntity<List<User>>(list, HttpStatus.NO_CONTENT);

	}

	@GetMapping("/all")
	public ResponseEntity<List<String>> getAllUsers() {

		List<String> set = service.getAllUsers();

		if (set != null) {

			return new ResponseEntity<List<String>>(set, HttpStatus.OK);

		}

		return new ResponseEntity<List<String>>(set, HttpStatus.NO_CONTENT);

	}

	@GetMapping("/roles")
	public ResponseEntity<Set<String>> getAllRole() {

		Set<String> set = service.getByRoles();

		if (set != null) {

			return new ResponseEntity<Set<String>>(set, HttpStatus.OK);

		}

		return new ResponseEntity<Set<String>>(set, HttpStatus.NO_CONTENT);

	}

//	http://localhost:8080/user/search/Amar
	@GetMapping("/search/{keyword}")
	public ResponseEntity<List<User>> searchUser(@PathVariable String keyword) {

		List<User> list = service.searchUser(keyword);

		if (list != null) {

			return new ResponseEntity<List<User>>(list, HttpStatus.OK);

		}

		return new ResponseEntity<List<User>>(list, HttpStatus.NO_CONTENT);

	}

	@GetMapping("/show/{id}")
	public ResponseEntity<User> getLogin(@PathVariable long id) {

		User login = service.getUser(id);

		if (login != null) {

			return new ResponseEntity<User>(login, HttpStatus.OK);

		}

		return new ResponseEntity<User>(login, HttpStatus.NO_CONTENT);

	}

	@PostMapping(value = "/getlogin")
	public ResponseEntity<User> getLogin(RequestEntity<User> request) {

		User login = request.getBody();

		System.out.println("Controller : " + login);

		if (login != null) {

			User login2 = service.getUser(login.getUsername(), login.getPassword());

			return new ResponseEntity<User>(login2, HttpStatus.OK);

		}

		return new ResponseEntity<User>(login, HttpStatus.NO_CONTENT);

	}

//	http://localhost:8080/user/role/User
	@GetMapping("/role/{role}")
	public ResponseEntity<List<User>> getByRole(@PathVariable String role) {

		List<User> list = service.getByRole(role);

		if (list != null) {

			return new ResponseEntity<List<User>>(list, HttpStatus.OK);

		}

		return new ResponseEntity<List<User>>(list, HttpStatus.NO_CONTENT);

	}

	@DeleteMapping(value = "/remove")
	public ResponseEntity<User> removeLogin(RequestEntity<User> request) {

		User login = request.getBody();

		if (login != null) {

			service.removeUser(login);

			return new ResponseEntity<User>(login, HttpStatus.OK);

		}

		return new ResponseEntity<User>(login, HttpStatus.NO_CONTENT);

	}

}
