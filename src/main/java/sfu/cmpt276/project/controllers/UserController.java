package sfu.cmpt276.project.controllers;

import java.util.ArrayList;
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
    @GetMapping("user/addUser") 
    public String displaySignup(){
        return"user/addUser";
    }
    @GetMapping("/admin/adminLanding")
    public String displayUsers(Model model){
        System.out.println("Displaying users");
        List<User> users = userRepo.findAll();
        model.addAttribute("userList" , users);
        return "admin/adminLanding";
    }
    private int tempId = -1;
    @PostMapping("/user/addUser")
    public String addUser(@RequestParam Map<String, String> newUser, HttpServletResponse response){
        String fName = newUser.get("fname");
        String lName = newUser.get("lname");
        String email = newUser.get("email");
        String username = newUser.get("username");
        String password = newUser.get("password2");
        if(!userRepo.findByEmail(email).isEmpty() || !userRepo.findByUsername(username).isEmpty()){
            System.out.println("Error!");
            return "user/addUser";
        }
        else{
            User createdUser = new User(username, password,fName, lName, email);
            userRepo.save(createdUser);
            tempId = createdUser.getUid();
            System.out.println(tempId + "This is the users user Id");
            response.setStatus(201);
            return "user/addPrefs";
        }
    }
    @PostMapping("/addPrefs") 
    public String addPreferences(@RequestParam Map<String, String> newUser, HttpServletRequest request,HttpSession session){
        System.out.println(request.getSession().getId());
        List <User> tempList = userRepo.findByUid(tempId);
        User addedUser = tempList.get(0);
        String access = newUser.get("access");
        String diet = newUser.get("diet");
        String lang1 = newUser.get("language1");
        String lang2 = newUser.get("language2");
        String lang3 = newUser.get("language3");
        addedUser.setPreferences(access, diet, lang1, lang2, lang3);
        return "user/userLanding";
    }
    private User editedUser;
    @PostMapping("/user/editPrefs") 
    public String editPreferences(@RequestParam Map<String, String> editUser, HttpServletRequest request,HttpSession session, Model model){
        User user = userRepo.getReferenceById(Integer.parseInt(editUser.get("userId")));
        User user2 = (User) request.getSession().getAttribute("session_user");
        model.addAttribute("edit", user2);
        return "/user/editPrefsSaved";
    }
    @PostMapping("/user/editPrefsSaved") 
    public String savePreferences(@RequestParam Map<String, String> newUser, HttpServletRequest request,HttpSession session, Model model){
        User editedUser = (User) request.getSession().getAttribute("session_user");
        String access = newUser.get("access");
        String diet = newUser.get("diet");
        String lang1 = newUser.get("language1");
        String lang2 = newUser.get("language2");
        String lang3 = newUser.get("language3");
        editedUser.setPreferences(access, diet, lang1, lang2, lang3);
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
            return "user/login";
        }
        else {
            User user = userList.get(0);
            request.getSession().setAttribute("session_user", user);
            model.addAttribute("user", user);
            if(user.getAccountType().equals("admin")){
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
