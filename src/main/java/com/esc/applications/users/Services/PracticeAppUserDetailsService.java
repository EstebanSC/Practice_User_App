package com.esc.applications.users.Services;

import com.esc.applications.users.Repositories.UserInRoleRepository;
import com.esc.applications.users.Repositories.UserRepository;
import com.esc.applications.users.entities.User;
import com.esc.applications.users.entities.UserInRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PracticeAppUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserInRoleRepository userInRoleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUserName(username);
        if(user.isPresent()){
            List<UserInRole> userInRoles = userInRoleRepository.findByUser(user.get());
            String [] rolesUser = userInRoles.stream().map(r->r.getRole().getName()).toArray(String[]::new);
            return org.springframework.security.core.userdetails.User.withUsername(user.get().getUserName()).password(passwordEncoder.encode(user.get().getPassword())).roles(rolesUser).build();
        }
        else{
            throw  new UsernameNotFoundException(String.format("User with name : %s not found",username));
        }
    }
}
