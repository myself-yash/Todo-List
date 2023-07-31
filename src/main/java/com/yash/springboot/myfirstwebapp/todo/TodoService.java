package com.yash.springboot.myfirstwebapp.todo;

import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

//@Service
public class TodoService {

    private static List<Todo> todos = new ArrayList<>();
    private static int todoCount;

    static {
        todos.add(new Todo(++todoCount, "Yash", "Learn Spring", LocalDate.now(), true));
        todos.add(new Todo(++todoCount, "Yash", "Learn SpringBoot", LocalDate.now(), false));
        todos.add(new Todo(++todoCount, "Yash", "Learn JPA", LocalDate.now().plusYears(1), true));
        todos.add(new Todo(++todoCount, "Yash", "Learn Hibernate", LocalDate.now().plusYears(1), false));
        todos.add(new Todo(++todoCount, "Yash", "Learn JSP", LocalDate.now().plusYears(1), false));

    }

    public List<Todo> findByUsername(String username){

        Predicate<? super Todo > predicate =
                todo -> todo.getUsername().equalsIgnoreCase(username);

        return todos.stream().filter(predicate).toList();
    }

    public void addTodo(String username,String description,LocalDate targetDate,boolean done){
        Todo todo = new Todo(++todoCount,username,description,targetDate,done);
        todos.add(todo);
    }

    public void  deleteById(int id){
        Predicate<? super Todo > predicate = todo -> todo.getId()==id;
        todos.removeIf(predicate);
    }
    public Todo findById(int id){
        Predicate<? super Todo > predicate = todo -> todo.getId()==id;
        Todo todo = todos.stream().filter(predicate).findFirst().get();
        return todo;
    }


    public void updateTodo(@Valid Todo todo) {
        deleteById(todo.getId()); // this will delete the existing todo
        todos.add(todo);  // this will add the new todo

    }
}
