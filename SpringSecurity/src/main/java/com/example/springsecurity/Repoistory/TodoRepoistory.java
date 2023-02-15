package com.example.springsecurity.Repoistory;

import com.example.springsecurity.Model.MyUser;
import com.example.springsecurity.Model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepoistory extends JpaRepository<Todo, Integer> {

    Todo findTodoById(Integer id);

    List<Todo> findAllByMyUser(MyUser myUser);

}
