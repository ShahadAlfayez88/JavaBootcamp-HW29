package com.example.springsecurity.ServiceTest;

import com.example.springsecurity.Model.MyUser;
import com.example.springsecurity.Model.Todo;
import com.example.springsecurity.Repoistory.AuthRepoitory;
import com.example.springsecurity.Repoistory.MyUserRepository;
import com.example.springsecurity.Repoistory.TodoRepoistory;
import com.example.springsecurity.Service.AuthService;
import com.example.springsecurity.Service.TodoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    @InjectMocks
    AuthService authService;
    @Mock
    AuthRepoitory authRepoitory;

    @Mock
    MyUserRepository myUserRepository;

    MyUser myUser, myUser1,myUser2, myUser3;


    List<MyUser> myusers;

    @BeforeEach
    void setUp() {
        myUser = new MyUser(null, "Shahad", "1234", "CUSTOMER", null);
        myUser1 = new MyUser(null, "Amal", "12345", "ADMIN", null);
        myUser2 = new MyUser(null, "ali", "12345", "ADMIN", null);
        myUser3 = new MyUser(null, "asma", "12345", "ADMIN", null);

        myusers = new ArrayList<>();
        myusers.add(myUser1);
        myusers.add(myUser2);
        myusers.add(myUser3);
    }

    @Test
    public void getAllUsers(){
        when(myUserRepository.findAll()).thenReturn(myusers);
        List<MyUser> userList = authService.getAllUsers();
        Assertions.assertEquals(3,userList.size());
        verify(myUserRepository,times(1)).findAll();
    }

    @Test
    public void UserById(){
        when(myUserRepository.findMyUserById(myUser.getId())).thenReturn(myUser);
        MyUser user = authService.getUser(myUser.getId());
        Assertions.assertEquals(user,myUser);
        verify(myUserRepository,times(1)).findMyUserById(myUser.getId());
    }

    @Test
    public void updateUser(){
        when(myUserRepository.findMyUserById(myUser1.getId())).thenReturn(myUser1);
        authService.updateUser(myUser2,myUser1.getId());

        verify(myUserRepository,times(1)).findMyUserById(myUser1.getId());
        verify(myUserRepository,times(1)).save(myUser2);
    }

    @Test
    public void delete(){
        // user
        when(myUserRepository.findMyUserById(myUser.getId())).thenReturn(myUser);
        //TO do
        authService.deleteUser(myUser.getId());

        verify(myUserRepository,times(1)).findMyUserById(myUser.getId());
        verify(myUserRepository,times(1)).delete(myUser);

    }

    @Test
    public void addUser(){
        authService.addUser(myUser1);
        verify(myUserRepository,times(1)).save(myUser1);
    }

}



