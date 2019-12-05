package jestesmy.glodni.cateringi.controller.api;

import jestesmy.glodni.cateringi.dto.ServiceDto;
import jestesmy.glodni.cateringi.exception.IdMismatchException;
import jestesmy.glodni.cateringi.exception.NotFoundException;
import jestesmy.glodni.cateringi.model.Company;
import jestesmy.glodni.cateringi.model.Service;
import jestesmy.glodni.cateringi.repository.CompanyRepository;
import jestesmy.glodni.cateringi.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/services")
public class ServiceApiController {
    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @GetMapping
    public Iterable<ServiceDto> findAll() {
        return StreamSupport.stream(serviceRepository.findAll().spliterator(), false)
                .map(this::dtoFromModel)
                .collect(Collectors.toList());
    }

    @GetMapping("/name/{serviceName}")
    public List<ServiceDto> findByName(@PathVariable String serviceName) {
        final List<Service> services = serviceRepository.findByServiceName(serviceName);
        final List<ServiceDto> serviceDtos = new ArrayList<>();
        for (Service service : services) {
            ServiceDto serviceDto = dtoFromModel(service);
            serviceDtos.add(serviceDto);
        }
        return serviceDtos;
    }

    @GetMapping("/{serviceID}")
    public ServiceDto findOne(@PathVariable int serviceID) {
        return serviceRepository.findById(serviceID)
                .map(this::dtoFromModel)
                .orElseThrow(NotFoundException::new);
    }

    @GetMapping("/company/{companyID}")
    public List<ServiceDto> findByCompany(@PathVariable int companyID) {
        final Company company = companyRepository.findById(companyID).orElseThrow(NotFoundException::new);
        final List<Service> services = serviceRepository.findByCompany(company);
        return services.stream()
                .map(this::dtoFromModel)
                .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ServiceDto create(@RequestBody ServiceDto serviceDto) {
        final Service service = modelFromDto(serviceDto);
        final Service savedService = serviceRepository.save(service);
        return dtoFromModel(savedService);
    }

    @DeleteMapping("/{serviceID}")
    public void delete(@PathVariable int serviceID) {
        serviceRepository.findById(serviceID)
                .orElseThrow(NotFoundException::new);
        serviceRepository.deleteById(serviceID);
    }

    @PutMapping("/{serviceID}")
    public ServiceDto updateService(@RequestBody ServiceDto serviceDto, @PathVariable int serviceID) {
        if (serviceDto.getId() != serviceID) {
            throw new IdMismatchException();
        }
        serviceRepository.findById(serviceID)
                .orElseThrow(NotFoundException::new);

        throw new UnsupportedOperationException("not implemented yet");
    }

    private ServiceDto dtoFromModel(Service service) {
        return new ServiceDto(
                service.getServiceID(),
                service.getServiceName(),
                service.getDescription(),
                service.getCompany().getCompanyID()
        );
    }

    private Service modelFromDto(ServiceDto serviceDto) {
        final Company company = companyRepository.findById(serviceDto.getCompanyId()).orElseThrow(NotFoundException::new);

        final Service service = new Service();
        service.setServiceID(serviceDto.getId());
        service.setServiceName(serviceDto.getName());
        service.setDescription(serviceDto.getDescription());
        service.setCompany(company);
        return service;

    }
}
