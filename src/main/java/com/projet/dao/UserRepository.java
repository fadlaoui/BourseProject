package com.projet.dao;

import com.projet.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Mohamed on 01/01/2017.
 */
public interface UserRepository extends JpaRepository<User,Long> {
    public User findByEmail(String email);
}
