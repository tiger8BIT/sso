package artezio.vkolodynsky.service;
import artezio.vkolodynsky.model.App;
import java.util.List;

public interface AppService {
    List<App> findAll();
    App save(App value);
    void deleteByID(int id);
    App findByID(int id);
    App getAppByUrl(String url);
    App getAppByName(String name);
}
