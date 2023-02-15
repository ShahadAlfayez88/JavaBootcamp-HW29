package com.example.springsecurity.Service;

import com.example.springsecurity.Exception.ApiException;
import com.example.springsecurity.Model.MyUser;
import com.example.springsecurity.Repoistory.AuthRepoitory;
import com.example.springsecurity.Repoistory.MyUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class AuthService {


    private final MyUserRepository myUserRepository;


    // get all users
    public List<MyUser> getAllUsers(){
        return myUserRepository.findAll();
    }


    // get user by id
    public MyUser getUser(Integer id){
        MyUser myUser=myUserRepository.findMyUserById(id);
        if (myUser==null){
            throw new ApiException("User Not Found!");
        }
        return myUser;
    }


    public void addUser(MyUser newUser){
        newUser.setRole("USER");
        String hashedPassword=new BCryptPasswordEncoder().encode(newUser.getPassword());
        newUser.setPassword(hashedPassword);
        myUserRepository.save(newUser);
    }

    public void deleteUser(Integer id){
        MyUser myUser=myUserRepository.findMyUserById(id);
        if(myUser==null){
            throw new ApiException("User Not Found");
        }
        myUserRepository.delete(myUser);
    }


    public void updateUser(MyUser newUser, Integer id){
        MyUser oldUser=myUserRepository.findMyUserById(id);

        newUser.setId(id);
        newUser.setRole(oldUser.getRole());
        newUser.setPassword(new BCryptPasswordEncoder().encode(newUser.getPassword()));

        myUserRepository.save(newUser);
    }


}