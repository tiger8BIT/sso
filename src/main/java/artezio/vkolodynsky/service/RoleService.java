package artezio.vkolodynsky.service;
import artezio.vkolodynsky.model.App;
import artezio.vkolodynsky.model.Role;
import artezio.vkolodynsky.model.User;
import artezio.vkolodynsky.model.data.RoleData;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    List<Role> findAll();
    Role save(RoleData roleData);
    void deleteByID(int id);
    Optional<Role> findByID(int id);
    List<Role> findByApp(App app);
    List<Role> findByUserAndApp(User user, App app);
    List<Role> findByUser(User user);
    public Role save(Role value);
}
