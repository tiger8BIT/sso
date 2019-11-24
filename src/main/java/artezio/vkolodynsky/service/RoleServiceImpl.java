package artezio.vkolodynsky.service;
import artezio.vkolodynsky.model.Role;
import artezio.vkolodynsky.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository repository;

    @Override
    public List<Role> findAll() {
        return (List<Role>) repository.findAll();
    }

    @Override
    public Role save(Role value) {
        return repository.save(value);
    }

    @Override
    public void deleteByID(long id) {
        repository.deleteById(id);
    }

    @Override
    public Role findByID(long id) {
        return repository.findById(id).get();
    }
}
