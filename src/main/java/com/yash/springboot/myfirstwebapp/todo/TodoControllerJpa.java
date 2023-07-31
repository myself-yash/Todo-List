package com.yash.springboot.myfirstwebapp.todo;

import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@SessionAttributes("name")
public class TodoControllerJpa {

    private TodoRepo todoRepo;

    public TodoControllerJpa( TodoRepo todoRepo) {

        this.todoRepo = todoRepo;
    }

    @RequestMapping(value = "list-todos")
    public String listAllTodos(ModelMap modelMap) {
        String username = getLoggedInUserName(modelMap);
        List<Todo> todos = todoRepo.findByUsername(username);
        modelMap.put("todos", todos);

        return "listTodos";
    }



    //GET
    @RequestMapping(value = "add-todo",method = RequestMethod.GET)
    public String showNewTodoPage(ModelMap model){
        String username = getLoggedInUserName(model);
        Todo todo = new Todo(0,username,"",LocalDate.now().plusYears(1),false);
        model.put("todo",todo);
        return "todo";
    }
//POST
    @RequestMapping(value = "add-todo",method = RequestMethod.POST)
    public String addNewTodoPage(ModelMap model, @Valid Todo todo, BindingResult result){
        if (result.hasErrors()){
            return "todo";
        }
        String username = getLoggedInUserName(model);
        todo.setUsername(username);
        todoRepo.save(todo);

//        todoService.addTodo(username,todo.getDescription(), todo.getTargetDate(),todo.isDone());
        return "redirect:list-todos";
    }

    @RequestMapping(value = "delete-todo")
    public String deleteTodo(@RequestParam int id) {
//    Delte Todo
        todoRepo.deleteById(id);
        return "redirect:list-todos";

    }
 @RequestMapping(value = "update-todo" ,method = RequestMethod.GET)
    public String showUpdateTodoPage(@RequestParam int id,ModelMap model) {
//    update Todo
     Todo todo =  todoRepo.findById(id).get();
     model.addAttribute("todo", todo);
        return "todo";

    }

    @RequestMapping(value = "update-todo",method = RequestMethod.POST)
    public String updatedTodo(ModelMap model, @Valid Todo todo, BindingResult result){

        if (result.hasErrors()){
            return "todo";
        }
        String username = getLoggedInUserName(model);
        todo.setUsername(username);
        todoRepo.save(todo);
        return "redirect:list-todos";
    }

    private static String getLoggedInUserName(ModelMap modelMap) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }


}


