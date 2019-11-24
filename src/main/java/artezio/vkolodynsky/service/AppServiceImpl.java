package artezio.vkolodynsky.service;
import artezio.vkolodynsky.model.App;
import artezio.vkolodynsky.repository.AppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppServiceImpl implements AppService {
    @Autowired
    private AppRepository repository;

    @Override
    public List<App> findAll() {
        return (List<App>) repository.findAll();
    }

    @Override
    public App save(App value) {
        return repository.save(value);
    }

    @Override
    public void deleteByID(long id) {
        repository.deleteById(id);
    }

    @Override
    public App findByID(long id) {
        return repository.findById(id).get();
    }
}
