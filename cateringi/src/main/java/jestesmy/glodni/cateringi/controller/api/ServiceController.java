package jestesmy.glodni.cateringi.controller.api;

import jestesmy.glodni.cateringi.exception.IdMismatchException;
import jestesmy.glodni.cateringi.exception.NotFoundException;
import jestesmy.glodni.cateringi.model.Service;
import jestesmy.glodni.cateringi.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/services")
public class ServiceController {
    @Autowired
    private ServiceRepository serviceRepository;

    @GetMapping
    public Iterable findAll(){
        return serviceRepository.findAll();
    }

    @GetMapping("/name/{serviceName}")
    public List findByName(@PathVariable String serviceName){
        return serviceRepository.findByServiceName(serviceName);
    }

    @GetMapping("/{serviceID}")
    public Service findOne(@PathVariable int serviceID){
        return serviceRepository.findById(serviceID)
                .orElseThrow(NotFoundException::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Service create(@RequestBody Service service){
        return serviceRepository.save(service);
    }

    @DeleteMapping("/{serviceID}")
    public void delete(@PathVariable int serviceID){
        serviceRepository.findById(serviceID)
                .orElseThrow(NotFoundException::new);
        serviceRepository.deleteById(serviceID);
    }

    @PutMapping("/{serviceID}")
    public Service updateService(@RequestBody Service service, @PathVariable int serviceID){
        if(service.getServiceID() != serviceID){
            throw new IdMismatchException();
        }
        serviceRepository.findById(serviceID)
                .orElseThrow(NotFoundException::new);
        return serviceRepository.save(service);
    }
}
