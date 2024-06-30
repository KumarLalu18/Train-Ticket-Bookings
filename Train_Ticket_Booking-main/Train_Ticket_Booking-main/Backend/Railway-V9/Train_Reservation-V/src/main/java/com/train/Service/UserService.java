package com.train.Service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.train.Exception.ThrowValidException;
import com.train.Repository.UserRepository;
import com.train.model.User;

@Service
public class UserService{

	@Autowired
	UserRepository repo;

	public List<User> getAllLogins() {

		return repo.findAll();

	}

	public List<String> getAllUsers() {

		return repo.getAllUsers();

	}

	public Set<String> getByRoles() {

		return repo.getAllRoles();

	}

	public List<User> getByRole(String role) {

		return repo.getByRole(role);

	}

	public List<User> searchUser(String keyword) {

		return repo.searchUser(keyword);

	}

	public User getUser(String username, String password) {
		System.out.println(username + " " + password);

		User login = repo.getByUsername(username);
		System.out.println(login);

		if (login != null) {

			if (login.getPassword().equals(password)) {

				return login;

			}
			else
			{
				throw new ThrowValidException("Password is incorrect");
			}

		}

		return new User();

	}

	public User getUser(long id) {

		return repo.findById(id).get();

	}

	public User createLogin(User login) {

		System.out.println(login);

		return repo.save(login);

	}

	public User updateUser(User login) {

		System.out.println(login);

		return repo.save(login);

	}

	public void removeUser(User login) {

		repo.delete(login);

	}
}
