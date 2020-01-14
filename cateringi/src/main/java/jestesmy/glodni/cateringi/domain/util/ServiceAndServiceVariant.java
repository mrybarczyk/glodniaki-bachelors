package jestesmy.glodni.cateringi.domain.util;

import jestesmy.glodni.cateringi.domain.model.Service;
import jestesmy.glodni.cateringi.domain.model.ServiceVariant;

import java.util.ArrayList;
import java.util.List;

public class ServiceAndServiceVariant {

    private Service service;

    private List<ServiceVariant> serviceVariants = new ArrayList<>();

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public List<ServiceVariant> getServiceVariants() {
        return serviceVariants;
    }

    public void setServiceVariants(List<ServiceVariant> serviceVariants) {
        this.serviceVariants = serviceVariants;
    }
}
