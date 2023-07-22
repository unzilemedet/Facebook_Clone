package com.muhammet.repository;

import com.muhammet.repository.entity.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IAuthRepository extends JpaRepository<Auth,Long> {
    Optional<Auth> findOptionalByUsernameAndPassword(String username,String password);

    Boolean existsByUsername(String username);

    @Query("select count(a)>0 from Auth a where a.username = ?1")
    Boolean existsByUsernameQuery(String username);

}
