package sfu.cmpt276.project.controllers;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import sfu.cmpt276.project.chatGptApi.ChatController;
import sfu.cmpt276.project.email.emailUtility;
import sfu.cmpt276.project.model.TripModel.Trip;
import sfu.cmpt276.project.model.TripModel.TripRepository;
import sfu.cmpt276.project.model.UserModel.AddUser;
import sfu.cmpt276.project.model.UserModel.User;
import sfu.cmpt276.project.model.UserModel.UserRepository;
import sfu.cmpt276.project.password.generatePin;
import sfu.cmpt276.project.tripGeneration.GenTripQuery;

@Controller
public class UserController {
    @Autowired
    private TripRepository tripRepo;

    @Autowired
    private UserRepository userRepo;

    private ChatController chatController = new ChatController();

    private String queryTest;

    @Value("${openai.key}")             // ChatGPT api key
    private String openaikey;

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
    @GetMapping("/admin/adminLanding")
    public String displayUsers(Model model,HttpSession session,HttpServletRequest request){
        User user2 = (User) request.getSession().getAttribute("session_user");
        model.addAttribute("user", user2);
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
    @GetMapping("/user/editAccSettings") 
    public String editAcc(@RequestParam Map<String, String> editUser, HttpServletRequest request,HttpSession session, Model model){
        User user2 = (User) request.getSession().getAttribute("session_user");
        model.addAttribute("edit", user2);
        return "user/editAccSettings";
    }
    @GetMapping("/user/changeUsername") 
    public String editUsername(@RequestParam Map<String, String> editUser, HttpServletRequest request,HttpSession session, Model model){
        User user2 = (User) request.getSession().getAttribute("session_user");
        model.addAttribute("edit", user2);
        return "user/changeUsername";
    }
    @GetMapping("/user/changeEmail") 
    public String editEmail(@RequestParam Map<String, String> editUser, HttpServletRequest request,HttpSession session, Model model){
        User user2 = (User) request.getSession().getAttribute("session_user");
        model.addAttribute("edit", user2);
        return "user/changeEmail";
    }
    @GetMapping("/user/changeAccPass") 
    public String editPassword(@RequestParam Map<String, String> editUser, HttpServletRequest request,HttpSession session, Model model){
        User user2 = (User) request.getSession().getAttribute("session_user");
        model.addAttribute("edit", user2);
        return "user/changeAccPass";
    }
    @PostMapping("/user/editUsername")
    public String updateUsername(@RequestParam Map<String, String> editUser, HttpServletRequest request,HttpSession session, Model model){
        User user2 = (User) request.getSession().getAttribute("session_user");
        String username = editUser.get("username");
        String pass1 = editUser.get("password1");
        String pass2 = editUser.get("password2");
        List <User> checkUsernameList = userRepo.findByUsername(username);
        model.addAttribute("edit", user2);
        if (pass1.equals(pass2) && pass1.equals(user2.getPassword())){
            if(checkUsernameList.isEmpty()){
                user2.setUsername(username);
                userRepo.save(user2);
            }
            else{
                model.addAttribute("usernameError", "Username is taken. Please try again.");
                return "user/changeUsername";
            }
        }
        else{
            model.addAttribute("passwordError", "Password is incorrect. Please try again.");
            return "user/changeUsername";
        }
        return "user/editAccSettings";
    }
    @PostMapping("/user/editEmail")
    public String updateEmail(@RequestParam Map<String, String> editUser, HttpServletRequest request,HttpSession session, Model model){
        User user2 = (User) request.getSession().getAttribute("session_user");
        String email = editUser.get("email");
        String pass1 = editUser.get("password1");
        String pass2 = editUser.get("password2");
        List <User> checkEmailList = userRepo.findByEmail(email);
        model.addAttribute("edit", user2);
        if (pass1.equals(pass2) && pass1.equals(user2.getPassword())){
            if(checkEmailList.isEmpty()){
                user2.setEmail(email);
                userRepo.save(user2);
            }
            else{
                model.addAttribute("emailError", "Email is taken. Please try again.");
                return "user/changeEmail";
            }
        }
        else{
            model.addAttribute("passwordError", "Password is incorrect. Please try again.");
            return "user/changeEmail";
        }
        return "user/editAccSettings";
    }
    @PostMapping("/user/editPassword")
    public String updatePassword(@RequestParam Map<String, String> editUser, HttpServletRequest request,HttpSession session, Model model){
        User user2 = (User) request.getSession().getAttribute("session_user");
        String newPass = editUser.get("password1");
        String newPass2 = editUser.get("password2");
        String currentPass = editUser.get("password3");
        String currentPass2 = editUser.get("password4");
        model.addAttribute("edit", user2);
        if (currentPass.equals(user2.getPassword())){
            if(currentPass.equals(currentPass2)){
                if(newPass.equals(newPass2)){
                    user2.setPassword(newPass);
                    userRepo.save(user2);
                }
            }
            else{
                model.addAttribute("passwordError", "Your current password entered does not match. Please try again.");
                return "user/changeAccPass";
            }
        }
        else{
            model.addAttribute("passwordError", "Password entered is incorrect. Please try again.");
            return "user/changeAccPass";
        }
        return "user/editAccSettings";
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
    
    @PostMapping("/admin/delete")
    public String deleteUser(@RequestParam Map<String, String> deleteUser, HttpServletRequest request,HttpSession session, Model model){
        User user2 = (User) request.getSession().getAttribute("session_user");
        model.addAttribute("user", user2);
        String userId = deleteUser.get("userId");
        List<User> checkValid = userRepo.findByUid(Integer.parseInt(userId));
        if(checkValid.isEmpty()){
            List<User> us = userRepo.findAll();
            model.addAttribute("userList" , us);
            model.addAttribute("userIdError", "UserId does not exist.");
            return "admin/adminLanding";
        }
        User delete = userRepo.getById(Integer.valueOf(userId));
        userRepo.delete(delete);
        List<User> us = userRepo.findAll();
        model.addAttribute("userList" , us);
        return "admin/adminLanding";
    }
    @PostMapping("/admin/editUser")
    public String editUser(@RequestParam Map<String, String> editUser, HttpServletRequest request,HttpSession session, Model model){
        User user2 = (User) request.getSession().getAttribute("session_user");
        model.addAttribute("user", user2);
        String userId = editUser.get("userId");
        List<User> checkValid = userRepo.findByUid(Integer.parseInt(userId));
        if(checkValid.isEmpty()){
            List<User> us = userRepo.findAll();
            model.addAttribute("userList" , us);
            model.addAttribute("userIdError", "UserId does not exist.");
            System.out.println("error!");
            return "/admin/adminLanding";
        }
        User editedUser = userRepo.getById(Integer.valueOf(userId));
        model.addAttribute("edit", editedUser);
        System.out.println(userId);
        return "/admin/editUser";
    }
    @PostMapping("/admin/saveEditedUser")
    public String saveEditedUser(@RequestParam Map<String, String> editUser, HttpServletRequest request,HttpSession session, Model model){
        User user2 = (User) request.getSession().getAttribute("session_user");
        model.addAttribute("user", user2);
        String username = editUser.get("username");
        String userId = editUser.get("userId");
        String first = editUser.get("firstName");
        String last = editUser.get("lastName");
        String pass = editUser.get("password");
        String email = editUser.get("email");
        String access = editUser.get("access");
        String diet = editUser.get("diet");
        String lang1 = editUser.get("language1");
        String lang2 = editUser.get("language2");
        String lang3 = editUser.get("language3");
        User editedUser = userRepo.getById(Integer.valueOf(userId));
        editedUser.setPreferences(access, diet, lang1, lang2, lang3);
        editedUser.setUsername(username);
        editedUser.setFirstName(first);
        editedUser.setLastName(last);
        editedUser.setFirstName(first);
        editedUser.setPassword(pass);
        editedUser.setEmail(email);
        userRepo.save(editedUser);
        List<User> us = userRepo.findAll();
        model.addAttribute("userList" , us);
        model.addAttribute("edit", editedUser);
        return "admin/adminLanding";
    }
    @GetMapping("/user/userLanding") 
    public String tripPreferences(@RequestParam Map<String, String> tripUser, HttpServletRequest request, HttpSession session, Model model){
        User tripUser2 = (User) request.getSession().getAttribute("session_user");
        model.addAttribute("tripEdit", tripUser2);
        return "user/userLanding";
    }

    //@PostMapping("/tripPrefsSaved")
    @RequestMapping(value = "/tripPrefsSaved", method = RequestMethod.GET)
    public ResponseEntity<?> saveTripPreferences(@RequestParam("location") String location, @RequestParam("budget") String budget, @RequestParam("startDate") 
                                                 String startDate, @RequestParam("endDate") String endDate, HttpServletRequest request, HttpSession session, Model model){
        User editedTripUser = (User) request.getSession().getAttribute("session_user");

        // Generate and parse trip list of locations/activities
        String chatTripQuery = GenTripQuery.genTripQuery(location, startDate, endDate, budget);
        try {
            String chatResponse = chatController.queryChatGPT(chatTripQuery, openaikey);
            if (chatResponse == ChatController.ERROR) return ResponseEntity.badRequest().build();
                queryTest = chatResponse;                                           
            Trip tripObject = new Trip(chatResponse, startDate, endDate, location, budget, editedTripUser.getUid());

            Trip savedTrip = tripRepo.save(tripObject);

            editedTripUser.setMostRecentTrip(savedTrip.getUid());
            userRepo.save(editedTripUser);
        } catch(InterruptedException e) {
            return ResponseEntity.badRequest().build();
        }
                                            
        return ResponseEntity.ok().build();

    }

    @GetMapping("/user/tripDisplay") 
    public String itineraryDisplay(HttpServletRequest request, HttpSession session, Model model){
        User itineraryUser = (User) session.getAttribute("session_user"); 
        Trip currTrip = tripRepo.getById(itineraryUser.getMostRecentTrip());
        //List<String> locations = currTrip.get(0).getLocationsList();
        List<String> locations = currTrip.getLocationsList();
        // remove null values

        model.addAttribute("user", itineraryUser);
        model.addAttribute("currTrip", currTrip);
        model.addAttribute("locations", locations);
        model.addAttribute("query", queryTest);
        
        //model.addAttribute("location", locationTest);

        return "user/tripDisplay";
        //return "test";
    }
     

    @GetMapping("/login")
    public String getLogin(Model model, HttpServletRequest request, HttpSession session){
        User user = (User) session.getAttribute("session_user");
        if(user == null){
            return "user/login";
        }
        else{
            model.addAttribute("user", user);
           if(user.getAccountType().equals("admin")){
                return displayUsers(model,session,request);
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
    @GetMapping("user/logout")
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

    // Test function for testing chatgpt package
    @GetMapping("/test")
    public String chatTest(Model model) {
        String response;
        String prompt = "when was calculus invented?";
        try {
            response = chatController.queryChatGPT(prompt, openaikey);
        } catch(InterruptedException e) {
            response = ChatController.ERROR;
        }

        model.addAttribute("chat", response);
        return "testing/chatTest";
    }
}
