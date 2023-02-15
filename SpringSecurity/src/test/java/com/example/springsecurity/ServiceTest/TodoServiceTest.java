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
public class TodoServiceTest {

    // اوصل للservice

    @InjectMocks
    TodoService todoService;
    @Mock
    TodoRepoistory todoRepoistory;

    @Mock
    MyUserRepository myUserRepository;

    MyUser myUser;

    Todo todo1, todo2, todo3;

    List<Todo> todos;

    @BeforeEach
    void setUp() {
        myUser = new MyUser(null, "Maha", "12345", "ADMIN", null);
        todo1 = new Todo(null, "todo1", "body1", myUser);
        todo2 = new Todo(null, "todo2", "body2", myUser);
        todo3 = new Todo(null, "todo3", "body3", myUser);

        todos = new ArrayList<>();
        todos.add(todo1);
        todos.add(todo2);
        todos.add(todo3);
    }
    @Test
    public void getAllTodo(){
        when(todoRepoistory.findAll()).thenReturn(todos);
        List<Todo> todoList = todoService.getAllTodo();
        Assertions.assertEquals(3,todoList.size());
        verify(todoRepoistory,times(1)).findAll();
    }
    @Test
    public void getAllTodoById(){
        when(myUserRepository.findMyUserById(myUser.getId())).thenReturn(myUser);
        when(todoRepoistory.findAllByMyUser(myUser)).thenReturn(todos);
        List<Todo> todoList = todoService.getMyTodos(myUser.getId());
        Assertions.assertEquals(todoList,todos);
        verify(myUserRepository,times(1)).findMyUserById(myUser.getId());
        verify(todoRepoistory,times(1)).findAllByMyUser(myUser);
    }

    @Test
    public void addTodo(){
        when(myUserRepository.findMyUserById(myUser.getId())).thenReturn(myUser);

        todoService.addTodo(todo1,myUser.getId());
        verify(todoRepoistory,times(1)).save(todo1);
    }
    @Test
    public void updateTodo(){
        when(myUserRepository.findMyUserById(myUser.getId())).thenReturn(myUser);
        when(todoRepoistory.findTodoById(todo1.getId())).thenReturn(todo1);

        todoService.updateTodo(todo1.getId(),todo2,myUser.getId());

        verify(myUserRepository,times(1)).findMyUserById(myUser.getId());
        verify(todoRepoistory,times(1)).findTodoById(todo1.getId());
        verify(todoRepoistory,times(1)).save(todo2);
    }

    @Test
    public void delete(){
        // user
        when(myUserRepository.findMyUserById(myUser.getId())).thenReturn(myUser);
        //TO do
        when(todoRepoistory.findTodoById(todo1.getId())).thenReturn(todo1);

        todoService.deleteTodo(todo1.getId(),myUser.getId());

        verify(myUserRepository,times(1)).findMyUserById(myUser.getId());
        verify(todoRepoistory,times(1)).findTodoById(todo1.getId());
        verify(todoRepoistory,times(1)).delete(todo1);

    }}