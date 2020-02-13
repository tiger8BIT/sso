package artezio.vkolodynsky.repository;

import artezio.vkolodynsky.model.RemoteAddress;
import artezio.vkolodynsky.model.Session;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RemoteAddressRepository extends CrudRepository<RemoteAddress, Integer> {
    List<RemoteAddress> findBySession(Session session);
}
