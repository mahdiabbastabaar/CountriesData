package com.example.CountriesData.repositories;

import com.example.CountriesData.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsUserByUsername(String username);

    User getUserByUsername(String username);

    @Query("SELECT u.username FROM User u")
    List<String> findAllUsernames();
}
