package org.example.repositories;

import org.example.models.ApiToken;
import org.example.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApiTokenRepository extends JpaRepository<ApiToken, Long> {

    List<ApiToken> findAllByUser(User user);

    ApiToken findApiTokenByToken(String token);

}
