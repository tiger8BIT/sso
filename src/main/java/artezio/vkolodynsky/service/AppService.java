package artezio.vkolodynsky.service;
import artezio.vkolodynsky.model.App;
import java.util.List;

public interface AppService {
    List<App> findAll();
    App save(App value);
    void deleteByID(long id);
    App findByID(long id);
}
