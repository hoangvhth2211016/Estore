package server.estore.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import server.estore.Model.User.Role;
import server.estore.Model.User.User;
import server.estore.Repository.UserRepo;

import java.util.Optional;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public void run(String... args) throws Exception {
        loadIntoDB();
    }

    private void loadIntoDB() {
        Optional<User> admin = userRepo.findByUsername("admin");
        if(admin.isEmpty()){
            User user = new User();
            user.setFirstname("admin");
            user.setLastname("admin");
            user.setUsername("admin");
            user.setEmail("admin@gmail.com");
            user.setPassword(passwordEncoder.encode("admin"));
            user.setRole(Role.ADMIN);
            user.setEnabled(true);
            userRepo.save(user);
        }
    }


}
