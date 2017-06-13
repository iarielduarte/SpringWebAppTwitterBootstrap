package ar.com.api.ibera.repositories;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.api.ibera.models.User;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Serializable> {

	public abstract User findByUsername(String username);
}
