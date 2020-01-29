package artezio.vkolodynsky.config;

import artezio.vkolodynsky.model.App;
import artezio.vkolodynsky.model.Role;
import artezio.vkolodynsky.model.User;
import artezio.vkolodynsky.repository.RoleRepository;
import artezio.vkolodynsky.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RoleRepository appRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

    }

    @Transactional
    Role createRoleIfNotFound(final String name, final App app) {
        Optional<Role> role = roleRepository.findByRoleNameAndApp(name, app);
        if (role.isEmpty()){
            Role newRole = new Role();
            newRole.setRoleName(name);
            newRole.setApp(app);
            return newRole;
        } else {
            return role.get();
        }
    }
}
