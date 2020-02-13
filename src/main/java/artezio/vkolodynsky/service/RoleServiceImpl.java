package artezio.vkolodynsky.service;
import artezio.vkolodynsky.model.App;
import artezio.vkolodynsky.model.Role;
import artezio.vkolodynsky.model.User;
import artezio.vkolodynsky.model.data.RoleData;
import artezio.vkolodynsky.repository.AppRepository;
import artezio.vkolodynsky.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository repository;
    @Autowired
    private AppRepository appRepository;

    @Override
    public List<Role> findAll() {
        return (List<Role>) repository.findAll();
    }

    @Override
    public Role save(Role value) {
        return repository.save(value);
    }

    @Override
    @Transactional
    public Role save(RoleData roleData) {
        App app = appRepository.findById(roleData.getAppId()).orElseThrow();
        Role role = Role.from(roleData);
        role.setApp(app);
        return repository.save(role);
    }

    @Override
    public void deleteByID(int id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<Role> findByID(int id) {
        return repository.findById(id);
    }

    @Override
    public List<Role> findByApp(App app) {
        return repository.findByApp(app);
    }

    @Override
    public List<Role> findByUserAndApp(User user, App app) {
        return repository.findByUsersAndApp(List.of(user), app);
    }

    @Override
    public List<Role> findByUser(User user) {
        return repository.findByUsers(List.of(user));
    }
}
