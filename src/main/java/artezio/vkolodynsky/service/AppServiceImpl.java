package artezio.vkolodynsky.service;
import artezio.vkolodynsky.model.App;
import artezio.vkolodynsky.model.Session;
import artezio.vkolodynsky.repository.AppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public void deleteByID(int id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<App> findByID(int id) {
        return repository.findById(id);
    }

    @Override
    public Optional<App> getAppByUrl(String url) {
        return repository.getAppByUrl(url);
    }

    @Override
    public Optional<App> getAppByName(String name) {
        return repository.getAppByName(name);
    }
}
