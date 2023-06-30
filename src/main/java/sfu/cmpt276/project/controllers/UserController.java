package sfu.cmpt276.project.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {
    @Autowired
    private UserRepository userRepo;
    @GetMapping("/users/home")
    public String displayUserHome(){
        return "users/home";
    }
    @GetMapping("/admin/home")
    public String displayAdminHome(){
        return "admin/home";
    }
    @PostMapping("/users/addUser")
    public String addUser(@RequestParam Map<String, String> newUser, HttpServletResponse response){
        String fName = newUser.get("fname");
        String lName = newUser.get("lname");
        String email = newUser.get("email");
        String username = newUser.get("username");
        String password = newUser.get("password");
        userRepo.save(new User(username, password,fName, lName, email));
        response.setStatus(201);
        return "users/addedUser";
    }
    @GetMapping("/login")
    public String getLogin(Model model, HttpServletResponse request, HttpSession session){
        User user = (User) session.getAttribute("session_user");
        if(user == null){
            return "users/login";
        }
        else{
            model.addAttribute("user", user);
            return "users/protected";
        }
    }
    @PostMapping("/login")
    public String login(@RequestParam Map<String,String> formData, Model model, HttpServletResponse request, HttpSession session){
        String uName = formData.get("username");
        String password = formData.get("password");
        List <User> userList = userRepo.findByUserAndPass(uName, password);
        if (userList.isEmpty()){
            return "users/login";
        }
        else {
            User user = userList.get(0);
            request.getSession().setAttribute("sesssion_user", user);
            model.addAttribute("user", user);
            return "users/protected";
        }
    }
}
