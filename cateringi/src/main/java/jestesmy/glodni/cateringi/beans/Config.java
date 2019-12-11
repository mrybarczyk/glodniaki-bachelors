package jestesmy.glodni.cateringi.beans;

import jestesmy.glodni.cateringi.model.Client;
import jestesmy.glodni.cateringi.model.Company;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Bean
    public Company getCompany() {
        Company company = new Company();
        company.setCompanyID(999);
        company.setName("TestowaFirma");
        company.setNip("111");
        company.setRegon("222");
        company.setAverageRating(0);
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
}