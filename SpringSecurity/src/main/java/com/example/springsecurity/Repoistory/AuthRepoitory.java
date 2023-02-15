package com.example.springsecurity.Repoistory;

import com.example.springsecurity.Model.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepoitory extends JpaRepository<MyUser, Integer> {
    MyUser findMyUserByUsername(String username);


}
