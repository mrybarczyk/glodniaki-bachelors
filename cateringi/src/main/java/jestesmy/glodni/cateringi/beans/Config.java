package jestesmy.glodni.cateringi.beans;

import jestesmy.glodni.cateringi.model.Client;
import jestesmy.glodni.cateringi.model.Company;
import jestesmy.glodni.cateringi.model.Service;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Bean
    public Company getCompany() {
        Company company = new Company();
        company.setCompanyID(1);
        company.setName("TestowaFirma");
        company.setNIP("111");
        company.setREGON("222");
        return company;
    }

    @Bean
    public Client getClient(){
        Client client = new Client();
        client.setClientID(999);
        client.setName("Rambo");
        client.setLastName("Jambo");
        return client;
    }

    @Bean
    public Service getService(){
        Service service = new Service();
        service.setServiceID(1);
        service.setCompany(getCompany());
        service.setServiceName("Szczypior świeży z rzodkiewą");
        service.setDescription("Jak w tytule");
        return service;
    }
}