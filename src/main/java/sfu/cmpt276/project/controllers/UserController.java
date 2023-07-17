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
import sfu.cmpt276.project.email.emailUtility;
import sfu.cmpt276.project.model.AddUser;
import sfu.cmpt276.project.model.User;
import sfu.cmpt276.project.model.UserRepository;
import sfu.cmpt276.project.password.generatePin;

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
        
        String pin = generatePin.genPin();
        createdUser.setPin(pin);

        userRepo.save(createdUser);
        return "user/login";
    }
    @GetMapping("/user/editPrefs") 
    public String editPreferences(@RequestParam Map<String, String> user, HttpServletRequest request, HttpSession session, Model model){
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
    @GetMapping("/user/userLanding") 
    public String tripPreferences(@RequestParam Map<String, String> tripUser, HttpServletRequest request, HttpSession session, Model model){
        User tripUser2 = (User) request.getSession().getAttribute("session_user");
        model.addAttribute("tripEdit", tripUser2);
        return "user/userLanding";
    }
    @PostMapping("/tripPrefsSaved") 
    public String saveTripPreferences(@RequestParam Map<String, String> newTripUser, HttpServletRequest request, HttpSession session, Model model){
        User editedTripUser = (User) request.getSession().getAttribute("session_user");
        String location = newTripUser.get("location");
        String budget = newTripUser.get("budget");
        String startDate = newTripUser.get("startDate");
        String endDate = newTripUser.get("endDate");

        editedTripUser.setTripPreferences(location, budget, startDate, endDate);
        userRepo.save(editedTripUser);
        model.addAttribute("user", editedTripUser);
        return "user/tripDisplay";
    }
    @GetMapping("/user/tripDisplay") 
    public String itineraryDisplay(@RequestParam Map<String, String> tripUser, HttpServletRequest request, HttpSession session, Model model){
        model.addAttribute("user", tripUser);
        return "user/tripDisplay";
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
    @GetMapping("user/inputEmailForPin")
    public String displayPinConfirmation(Model model, HttpServletRequest request, HttpSession session) {
        User user = (User) session.getAttribute("session_user1");
        if (user != null) {
            model.addAttribute("user", user);
            return "user/protected";        
        } else {
            return "user/inputEmailForPin";
        }
    }
    @PostMapping("/inputEmailForPin") 
    public String getEmailForPin(@RequestParam Map<String,String> formData, Model model, HttpServletRequest request, HttpSession session) {
        String email = formData.get("email");
        List<User> userList = userRepo.findByEmail(email);

        if(userList.isEmpty()) {
            // if email doesn't have associated email
            model.addAttribute("emailError", "Email does not have an associated account");
            return "user/inputEmailForPin";
        } else {
            User user = userList.get(0);
            request.getSession().setAttribute("session_user1", user);
            model.addAttribute("user", user);

            // send email
            String userEmail = user.getEmail();
            String userPin = user.getPin();
            String body = "Your Wayfinder account pin reset code is " + userPin + ". \nDo not share this with anyone!";
            emailUtility.sendEmail(userEmail, "Wayfinder Password Pin", body);

            return "user/pinConfirmation";
        }
    }
    @GetMapping("user/pinConfirmation")
    public String pinConfirmation(Model model, HttpServletRequest request, HttpSession session) {
        User user = (User) session.getAttribute("session_user1");
        if (user != null) {
            model.addAttribute("user", user);
            return "user/pinConfirmation"; 
        } else {
            return "user/inputEmailForPin";
        }
    }
    @PostMapping("user/pinConfirmation")
    public String confirmPin(@RequestParam Map<String, String> formData, Model model, HttpServletRequest request, HttpSession session) {
        User user = (User) session.getAttribute("session_user1");
        String userPin = user.getPin();
        String userPinInput = formData.get("pin");

        if (userPinInput.equals(userPin)) {
            return "/user/changePassword"; 
        } else {
            model.addAttribute("pinError", "Invalid pin entered. Please try again.");
            return "user/pinConfirmation";
        }
    }
    @GetMapping("user/changePassword")
    public String promptToChangePassword() {
        return "user/changePassword";
    }
    @PostMapping("user/changePassword")
    public String changePassword(@RequestParam("password") String newPassword, HttpServletRequest request, HttpSession session) {
        User user = (User) session.getAttribute("session_user1");
    
        // create new random pin
        String newPin = generatePin.genPin();
        user.setPin(newPin);
        
        user.setPassword(newPassword);
        userRepo.save(user);
        session.invalidate();
        
        return "redirect:/login";
    }
}
