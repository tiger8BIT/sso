package artezio.vkolodynsky.security;

import artezio.vkolodynsky.model.User;
import artezio.vkolodynsky.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
@Slf4j
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserService userService;
    public UserDetails loadUserByUsername(String username) {
        Optional<User> user = userService.findByLogin(username);
        if (user.isEmpty()) throw new UsernameNotFoundException(username);
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ADMIN"));
        log.info("-------------------------------------------------------=> {}", user);
        return new org.springframework.security.core.userdetails.User(user.get().getLogin(), user.get().getPassword(), grantedAuthorities);
    }
}
