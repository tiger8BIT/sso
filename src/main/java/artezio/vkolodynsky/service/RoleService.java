package artezio.vkolodynsky.service;
import artezio.vkolodynsky.model.Role;
import java.util.List;

public interface RoleService {
    List<Role> findAll();
    Role save(Role value);
    void deleteByID(long id);
    Role findByID(long id);
}
