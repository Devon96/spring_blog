package com.blog.blog.config;

import com.blog.blog.model.Authorities;
import com.blog.blog.model.User;
import com.blog.blog.repository.AuthoritiesRepository;
import com.blog.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.Arrays;
import java.util.Date;

@Component
public class UserDataSetup implements ApplicationListener<ContextRefreshedEvent> {

    private UserRepository userRepository;
    private AuthoritiesRepository authoritiesRepository;
    private PasswordEncoder passwordEncoder;

    boolean alredySetup = false;

    @Autowired
    public UserDataSetup(UserRepository userRepository, AuthoritiesRepository authoritiesRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authoritiesRepository = authoritiesRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if(alredySetup){
            return;
        }

        createRoleIfNotExist("USER");
        createRoleIfNotExist("ADMIN");

        Authorities role = authoritiesRepository.findByName("ADMIN");
        Authorities role2 = authoritiesRepository.findByName("USER");

        User user = new User();
        user.setEnabled(true);
        user.setUsername("user");
        user.setEmail("alma@gmail.com");
        user.setRoles(Arrays.asList(role, role2));
        user.setPassword(passwordEncoder.encode("a"));
        user.setBirthday(new Date());
        user.setImg("asasa");
        userRepository.save(user);

        alredySetup = true;
    }

    @Transactional
    public Authorities createRoleIfNotExist(String name) {
        Authorities role = authoritiesRepository.findByName(name);

        if(role == null){
            role = new Authorities(name);
            authoritiesRepository.save(role);
        }
        return role;
    }
}
