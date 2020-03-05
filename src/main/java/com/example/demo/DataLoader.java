package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... strings) throws Exception {
        roleRepository.save(new Role("USER"));
        roleRepository.save(new Role("ADMIN"));
        Role adminRole = roleRepository.findByRole("ADMIN");
        Role userRole = roleRepository.findByRole("USER");

        User user1 = new User("jim@jim.com", "password", "Jim", "Jimmerson",
                true, "jim");
        user1.setRoles(Arrays.asList(userRole));
        userRepository.save(user1);

        User user2 = new User("admin@admin.com", "password", "Admin", "User",
                true, "admin");
        user2.setRoles(Arrays.asList(adminRole));
        userRepository.save(user2);

        Message message = new Message("asdfsdf", "dfgdfgd");
        message.setUser(user1);
        messageRepository.save(message);

        message = new Message("rtyfghj", "vbnbffg");
        message.setUser(user2);
        messageRepository.save(message);

        message = new Message("bcvfger", "erterggde");
        message.setPostedBy("Tom");
        messageRepository.save(message);
    }
}
