package artezio.vkolodynsky.service;

import artezio.vkolodynsky.model.RemoteAddress;
import artezio.vkolodynsky.repository.RemoteAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RemoteAddressImpl implements RemoteAddressService {
    @Autowired
    private RemoteAddressRepository addressRepository;
    @Override
    public RemoteAddress save(RemoteAddress value) {
        return addressRepository.save(value);
    }
}
