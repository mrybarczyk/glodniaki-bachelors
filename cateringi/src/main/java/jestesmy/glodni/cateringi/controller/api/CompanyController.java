package jestesmy.glodni.cateringi.controller.api;

import jestesmy.glodni.cateringi.exception.IdMismatchException;
import jestesmy.glodni.cateringi.exception.NotFoundException;
import jestesmy.glodni.cateringi.model.Company;
import jestesmy.glodni.cateringi.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    @Autowired
    CompanyRepository companyRepository;

    @GetMapping
    public Iterable findAll() {
        return companyRepository.findAll();
    }

    @GetMapping("/name/{companyName}")
    public List findByName(@PathVariable String companyName) {
         return companyRepository.findByName(companyName);
    }

    @GetMapping("/{id}")
    public Company findOne(@PathVariable int id) {
        return companyRepository.findById(id)
                .orElseThrow(NotFoundException::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Company create(@RequestBody Company company) {
        return companyRepository.save(company);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        companyRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        companyRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public Company updateCompany(@RequestBody Company company, @PathVariable int id){
        if(company.getCompanyID()!=id) {
            throw new IdMismatchException();
        }
        companyRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        return companyRepository.save(company);
    }

}
