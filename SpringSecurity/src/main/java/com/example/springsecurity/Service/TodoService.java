package com.example.springsecurity.Service;

import com.example.springsecurity.Exception.ApiException;
import com.example.springsecurity.Model.MyUser;
import com.example.springsecurity.Model.Todo;
import com.example.springsecurity.Repoistory.MyUserRepository;
import com.example.springsecurity.Repoistory.TodoRepoistory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepoistory todoRepository;
    private final MyUserRepository myUserRepository;

    public List getAllTodo(){
        return todoRepository.findAll();
    }

    public Todo getTodoById(Integer id , Integer auth){
        Todo todo=todoRepository.findTodoById(id);
        if (todo==null){
            throw new ApiException("Todo not Found");
        }
        if (todo.getMyUser().getId()!=auth){
            throw new ApiException("Sorry , You do not have the authority to get this Todo!");
        }
        return todo;
    }

    public List getMyTodos(Integer auth){
        MyUser myUser=myUserRepository.findMyUserById(auth);
        List todos=todoRepository.findAllByMyUser(myUser);
        if (todos.isEmpty()){
            throw new ApiException("Empty!");
        }
        return todos;
    }

    public void addTodo(Todo todo , Integer auth){
        MyUser myUser=myUserRepository.findMyUserById(auth);
        todo.setMyUser(myUser);

        todoRepository.save(todo);
    }

    public void updateTodo(Integer id , Todo newTodo , Integer auth){
        Todo oldTodo=todoRepository.findTodoById(id);
        MyUser myUser=myUserRepository.findMyUserById(auth);
        if (oldTodo==null){
            throw new ApiException("Todo not found");
        }else if(oldTodo.getMyUser().getId()!=auth){
            throw new ApiException("you cant update this todo");
        }

        newTodo.setId(id);
        newTodo.setMyUser(myUser);

        todoRepository.save(newTodo);
    }

    public void deleteTodo(Integer id, Integer auth){
        Todo todo=todoRepository.findTodoById(id);
        MyUser myUser=myUserRepository.findMyUserById(auth);
        if (todo==null || myUser==null){
            throw new ApiException("not found");
        }else if(todo.getMyUser().getId()!=auth){
            throw new ApiException("You cant delete this todo");
        }

        todoRepository.delete(todo);
    }
}