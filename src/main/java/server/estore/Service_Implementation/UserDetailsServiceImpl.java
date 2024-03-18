package server.estore.Service_Implementation;

import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import server.estore.Model.User.User;
import server.estore.Repository.UserRepo;

@Service("CustomUserDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {
    
    @Autowired
    private UserRepo userRepo;
    
    @Override
    @Transactional
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("user not found"));
        Hibernate.initialize(user.getAddresses());
        return user;
    }
}
