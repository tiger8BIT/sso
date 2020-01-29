package artezio.vkolodynsky.repository;
import artezio.vkolodynsky.model.App;
import artezio.vkolodynsky.model.Role;
import artezio.vkolodynsky.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {
    List<Role> findByApp(App app);
    Optional<Role> findByRoleNameAndApp(String name, App app);
    List<Role> findByUsersAndApp(List<User> users, App app);
    List<Role> findByUsers(List<User> users);
}
