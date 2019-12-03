package artezio.vkolodynsky.service;
import artezio.vkolodynsky.model.App;
import artezio.vkolodynsky.model.Role;
import artezio.vkolodynsky.model.User;

import java.util.List;

public interface RoleService {
    List<Role> findAll();
    Role save(Role value);
    void deleteByID(int id);
    Role findByID(int id);
    List<Role> findByApp(App app);
    List<Role> findByUserAndApp(User user, App app);
}
