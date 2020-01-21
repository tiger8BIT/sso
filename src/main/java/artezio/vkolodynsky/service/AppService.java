package artezio.vkolodynsky.service;
import artezio.vkolodynsky.model.App;
import artezio.vkolodynsky.model.Session;

import java.util.List;
import java.util.Optional;

public interface AppService {
    List<App> findAll();
    App save(App value);
    void deleteByID(int id);
    Optional<App> findByID(int id);
    Optional<App> getAppByUrl(String url);
    Optional<App> getAppByName(String name);
}
