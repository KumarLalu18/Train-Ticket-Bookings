package com.train.Repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.train.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	User getByUsername(String username);

	List<User> getByRole(String role);

//	Native Query

	@Query(value = "select u.username from User u")
	public List<String> getAllUsers();

	@Query(value = "select u.role from User u")
	public Set<String> getAllRoles();

	@Query(value = "SELECT u FROM User u WHERE u.username LIKE '%' || :keyword || '%'"
			+ " OR u.role LIKE '%' || :keyword")
	public List<User> searchUser(@Param("keyword") String keyword);
}
