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
public class CompanyApiController {

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

    @GetMapping("/{companyID}")
    public Company findOne(@PathVariable int companyID) {
        return companyRepository.findById(companyID)
                .orElseThrow(NotFoundException::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Company create(@RequestBody Company company) {
        return companyRepository.save(company);
    }

    @DeleteMapping("/{companyID}")
    public void delete(@PathVariable int companyID) {
        companyRepository.findById(companyID)
                .orElseThrow(NotFoundException::new);
        companyRepository.deleteById(companyID);
    }

    @PutMapping("/{companyID}")
    public Company updateCompany(@RequestBody Company company, @PathVariable int companyID){
        if(company.getCompanyID() != companyID) {
            throw new IdMismatchException();
        }
        companyRepository.findById(companyID)
                .orElseThrow(NotFoundException::new);
        return companyRepository.save(company);
    }

}
