package artezio.vkolodynsky.repository;
import artezio.vkolodynsky.model.App;
import artezio.vkolodynsky.model.Role;
import artezio.vkolodynsky.model.Session;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppRepository extends CrudRepository<App, Integer> {
    Optional<App> getAppByUrl(String url);
    Optional<App> getAppByName(String name);
}
