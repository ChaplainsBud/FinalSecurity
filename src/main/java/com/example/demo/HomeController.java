package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;
    
    @Autowired
    UserRepository userRepository;

    @Autowired
    MessageRepository messageRepository;

//    @Autowired
//    CloudinaryConfig cloudc;
    @GetMapping("/register")
    public String showRegistrationPage(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/register")
    public String processRegistrationPage(@Valid @ModelAttribute("user") User user, BindingResult result,
                                          Model model) {
        model.addAttribute("user", user);
        if (result.hasErrors())
        {
            return "registration";
        }
        else
        {
            userService.saveUser(user);
            model.addAttribute("message", "User Account Created");
        }
        return "redirect:/";
    }

    @RequestMapping("/")
    public String listMessages(Principal principal, Model model) {
        if(userService.getUser() != null) {
//            model.addAttribute("myuser", userService.getUser());
//            String username = principal.getName();
//            model.addAttribute("user", userRepository.findByUsername(username));
            model.addAttribute("user_id", userService.getUser().getId());
        }
        model.addAttribute("messages", messageRepository.findAll());
//        model.addAttribute("users", userRepository.findAll());
        return "index";
    }


    @GetMapping("/add")
    public String messageForm(Model model) {
//        model.addAttribute("myuser", userService.getUser());
//        model.addAttribute("user", userService.getUser());
        model.addAttribute("message", new Message());
        return "messageform";
    }

    @PostMapping("/process")
    public String processForm(@ModelAttribute Message message, Model model) {

        message.setUser(userService.getUser());
        messageRepository.save(message);
        return "redirect:/";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

//    @RequestMapping("/logout")
//    public String logout() {
//        return "redirect:/";
//    }

    @RequestMapping("/secure")
    public String secure(Principal principal, Model model) {
        String username = principal.getName();
        model.addAttribute("user", userRepository.findByUsername(username));
        return "secure";
    }

    @RequestMapping("/mymessages")
    public String myMessage(Principal principal, Model model) {
        User user = userService.getUser();
        ArrayList<Message> messages = (ArrayList<Message>) messageRepository.findByUser(user);
        model.addAttribute("messages", messages);

//        if(userService.getUser() != null) {
//            String username = principal.getName();
//            model.addAttribute("user", userRepository.findByUsername(username));
//            model.addAttribute("user_id", userService.getUser().getId());
//        }
        return "mymessages";
    }

    @RequestMapping("/allmessages")
    public String allMessages(Model model) {
        if (userService.getUser() != null) {
            model.addAttribute("user_id", userService.getUser().getId());
        }
        model.addAttribute("messages", messageRepository.findAll());
//        model.addAttribute("users", userRepository.findAll());
        return "allmessages";
    }

    @RequestMapping("/detail/{id}")
    public String showMessage(@PathVariable("id") long id, Model model) {
        model.addAttribute("message", messageRepository.findById(id).get());
        return "show";
    }

    @RequestMapping("/update/{id}")
    public String updateMessage(@PathVariable("id") long id, Model model) {
        model.addAttribute("message", messageRepository.findById(id).get());
        return "messageform";
    }

    @RequestMapping("/delete/{id}")
    public String delMessage(@PathVariable("id") long id, Authentication auth) {
        messageRepository.deleteById(id);
//        System.out.println(auth.getAuthorities().toString());
        if (auth.getAuthorities().toString().equals("[ADMIN]")) {
//            return "redirect:/allmessages";
            System.out.println("true");
            return "redirect:/allmessages";
        }
        else {
            return "redirect:/mymessages";
        }
//        return "redirect:/";
    }
}
