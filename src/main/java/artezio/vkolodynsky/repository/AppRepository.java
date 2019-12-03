package artezio.vkolodynsky.repository;
import artezio.vkolodynsky.model.App;
import artezio.vkolodynsky.model.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppRepository extends CrudRepository<App, Integer> {
    App getAppByUrl(String url);
    App getAppByName(String name);
}
