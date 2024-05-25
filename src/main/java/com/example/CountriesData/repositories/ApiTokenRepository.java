package com.example.CountriesData.repositories;

import com.example.CountriesData.models.user.ApiToken;
import com.example.CountriesData.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApiTokenRepository extends JpaRepository<ApiToken, Long> {

    List<ApiToken> findAllByUser(User user);

    ApiToken findApiTokenByToken(String token);

}
