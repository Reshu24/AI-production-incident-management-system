package com.reshmah.incident.initializer;

import com.reshmah.incident.entity.UserEntity;
import com.reshmah.incident.repository.Users;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminData implements CommandLineRunner {

    private final Users users;
    private final PasswordEncoder passwordEncoder;

    public AdminData(Users users, PasswordEncoder passwordEncoder){
        this.users = users;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args){
        if(users.findByUserName("admin1").isEmpty()){
            UserEntity admin = new UserEntity();
            admin.setUserName("admin1");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole(UserEntity.Role.ADMIN);

            users.save(admin);
        }
        if(users.findByUserName("support1").isEmpty()){
            UserEntity admin = new UserEntity();
            admin.setUserName("support1");
            admin.setPassword(passwordEncoder.encode("support123"));
            admin.setRole(UserEntity.Role.SUPPORT);

            users.save(admin);
        }
        if(users.findByUserName("viewer1").isEmpty()){
            UserEntity admin = new UserEntity();
            admin.setUserName("viewer1");
            admin.setPassword(passwordEncoder.encode("viewer123"));
            admin.setRole(UserEntity.Role.VIEWER);

            users.save(admin);
        }
    }

}
