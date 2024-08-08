package me.minphoneaung.springcrud.repository;

import me.minphoneaung.springcrud.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findUserByUsername(String username);
}
