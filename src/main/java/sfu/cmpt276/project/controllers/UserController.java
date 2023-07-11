package sfu.cmpt276.project.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import sfu.cmpt276.project.model.AddUser;
import sfu.cmpt276.project.model.User;
import sfu.cmpt276.project.model.UserRepository;

@Controller
public class UserController {
    @Autowired
    private UserRepository userRepo;
    @GetMapping("/")
    public RedirectView rootView(){
        return new RedirectView("login");
    }
    @PostMapping("/")
    public RedirectView returnLanding(){
        return new RedirectView("login");
    }
    @GetMapping("user/addUser") 
    public String displaySignup(AddUser addUser, Model model){
        AddUser tempUser = new AddUser();
        model.addAttribute("addUser", tempUser);
        return "user/addUser";
    }
    @PostMapping("/admin/adminLanding")
    public String displayUsers(Model model){
        List<User> us = userRepo.findAll();
        model.addAttribute("userList" , us);
        return "admin/adminLanding";
    }
    private String fName;
    private String lName;
    private String email;
    private String username; 
    private String password;
    @PostMapping("/user/addUser")
    public String addUser(@RequestParam Map<String, String> newUser, AddUser addUser, HttpServletResponse response, Model model){
        AddUser tempUser = new AddUser(newUser.get("fname"), newUser.get("lname"), newUser.get("email"), newUser.get("username"));
        fName = newUser.get("fname");
        lName = newUser.get("lname");
        email = newUser.get("email");
        username = newUser.get("username");
        password = newUser.get("password2");
        if(!userRepo.findByEmail(email).isEmpty()){
            model.addAttribute("emailUsed", "Email has already been used before. Please try again.");
            model.addAttribute("addUser", tempUser);
            return "user/addUser";
        }
        if(!userRepo.findByUsername(username).isEmpty()){
            model.addAttribute("usernameUsed", "Username has already been used before. Please try again.");
            model.addAttribute("addUser", tempUser);
            return "user/addUser";
        }
        else{
            response.setStatus(201);
            return "user/addPrefs";
        }
    }
    @PostMapping("user/addPrefs")    
    public String addPreferences(@RequestParam Map<String, String> newUser, HttpServletRequest request,HttpSession session){
        String access = newUser.get("access");
        String diet = newUser.get("diet");
        String lang1 = newUser.get("language1");
        String lang2 = newUser.get("language2");
        String lang3 = newUser.get("language3");
        User createdUser = new User(username, password,fName, lName, email);
        createdUser.setPreferences(access, diet, lang1, lang2, lang3);
        userRepo.save(createdUser);
        return "user/login";
    }
    @GetMapping("/user/editPrefs") 
    public String editPreferences(@RequestParam Map<String, String> editUser, HttpServletRequest request,HttpSession session, Model model){
        User user2 = (User) request.getSession().getAttribute("session_user");
        model.addAttribute("edit", user2);
        return "user/editPrefs";
    }
    @PostMapping("/editPrefsSaved") 
    public String savePreferences(@RequestParam Map<String, String> newUser, HttpServletRequest request,HttpSession session, Model model){
        User editedUser = (User) request.getSession().getAttribute("session_user");
        String access = newUser.get("access");
        String diet = newUser.get("diet");
        String lang1 = newUser.get("language1");
        String lang2 = newUser.get("language2");
        String lang3 = newUser.get("language3");
        editedUser.setPreferences(access, diet, lang1, lang2, lang3);
        userRepo.save(editedUser);
        model.addAttribute("user", editedUser);
        return "user/userLanding";
    }
    @GetMapping("/login")
    public String getLogin(Model model, HttpServletResponse request, HttpSession session){
        User user = (User) session.getAttribute("session_user");
        if(user == null){
            return "user/login";
        }
        else{
            model.addAttribute("user", user);
           if(user.getAccountType().equals("admin")){
                return displayUsers(model);
            }
            else{
            return "user/userLanding";
            }
        }
    }
    @PostMapping("/login")
    public String login(@RequestParam Map<String,String> formData, Model model, HttpServletRequest request, HttpSession session){
        String uName = formData.get("username");
        String pwd = formData.get("password-input");
        List <User> userList = userRepo.findByUsernameAndPassword(uName, pwd);
        if (userList.isEmpty()){
            model.addAttribute("loginError", "Username or password is incorrect. Invalid login!");
            return "user/login";
        }
        else {
            User user = userList.get(0);
            request.getSession().setAttribute("session_user", user);
            model.addAttribute("user", user);
            if(user.getAccountType().equals("admin")){
                List<User> us = userRepo.findAll();
                model.addAttribute("userList" , us);
                return "admin/adminLanding";
            }
            else{
            return "user/userLanding";
            }
        }
    }
    @GetMapping("/logout")
    public String removeSession(HttpServletRequest request){
        request.getSession().invalidate();
        return "user/login";
    }
}
