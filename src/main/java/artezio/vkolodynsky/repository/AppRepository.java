package artezio.vkolodynsky.repository;
import artezio.vkolodynsky.model.App;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppRepository extends CrudRepository<App, Long> {

}
