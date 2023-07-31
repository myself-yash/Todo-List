package com.yash.springboot.myfirstwebapp.todo;

import com.yash.springboot.myfirstwebapp.todo.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface TodoRepo extends JpaRepository<Todo,Integer> {

    public List<Todo>  findByUsername(String Username);



}
