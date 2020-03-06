package com.example.demo;

import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.Map;

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
        return "index";
    }

    @RequestMapping("/")
    public String listMessages(Principal principal, Model model) {
        if(userService.getUser() != null) {
            model.addAttribute("myuser", userService.getUser());
            String username = principal.getName();
            model.addAttribute("user", userRepository.findByUsername(username));
        }
        model.addAttribute("messages", messageRepository.findAll());
        return "index";
    }



//    @GetMapping("/add")
//    public String messageForm(Principal principal, Model model) {
//        String username = principal.getName();
//        model.addAttribute("user", userRepository.findByUsername(username));
//        model.addAttribute("message", new Message());
//        return "messageform";
//    }

//    @GetMapping("/add")
//    public String messageForm(Model model, Principal principal){
//        model.addAttribute("message", new Message());
////        model.addAttribute("users", userRepository.findAll());
////        model.addAttribute("user_id",userRepository.findByUsername(principal.getName()).getId());
//        return "messageform";
//    }

//    @GetMapping("/add")
//    public String messageForm(Model model, Principal principal){
//        model.addAttribute("message", new Message());
//        model.addAttribute("users", userRepository.findAll());
//        model.addAttribute("user_id",userRepository.findByUsername(principal.getName()).getId());
//        return "messageform";
//    }
    @GetMapping("/add")
    public String messageForm(Model model) {
//        model.addAttribute("myuser", userService.getUser());
//        model.addAttribute("user", userService.getUser());
        model.addAttribute("message", new Message());
        return "messageform";
    }

    @PostMapping("/process")
    public String processForm(@ModelAttribute Message message, Model model) {
//        model.addAttribute("myuser", userService.getUser());
//        model.addAttribute("user", userService.getUser());
//        if (file.isEmpty()) {
//            return "redirect:/add";
//        }
//        try {
//            Map uploadResult = cloudc.upload(file.getBytes(), ObjectUtils.asMap("resourcetype", "auto"));
//            message.setPic(uploadResult.get("url").toString());
//            messageRepository.save(message);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return "redirect:/add";
//        }
//        User user = userRepository.findById(message_user_id).get();
//        message.setUser(user);
        message.setUser(userService.getUser());
        messageRepository.save(message);
        return "redirect:/";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/secure")
    public String secure(Principal principal, Model model) {
        String username = principal.getName();
        model.addAttribute("user", userRepository.findByUsername(username));
        return "secure";
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
    public String delMessage(@PathVariable("id") long id) {
        messageRepository.deleteById(id);
        return "redirect:/";
    }
}
