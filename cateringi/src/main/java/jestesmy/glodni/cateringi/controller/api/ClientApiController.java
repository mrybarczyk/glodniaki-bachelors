package jestesmy.glodni.cateringi.controller.api;

import jestesmy.glodni.cateringi.exception.IdMismatchException;
import jestesmy.glodni.cateringi.exception.NotFoundException;
import jestesmy.glodni.cateringi.model.Client;
import jestesmy.glodni.cateringi.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientApiController {

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping
    public Iterable findAll() {
        return clientRepository.findAll();
    }

    @GetMapping("/lastName/{clientLastName}")
    public List findByLastName(@PathVariable String clientLastName) {
        return clientRepository.findByLastName(clientLastName);
    }

    @GetMapping("/{clientID}")
    public Client findOne(@PathVariable int clientID) {
        return clientRepository.findById(clientID)
                .orElseThrow(NotFoundException::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Client create(@RequestBody Client client) {
        return clientRepository.save(client);
    }

    @DeleteMapping("/{clientID}")
    public void delete(@PathVariable int clientID) {
        clientRepository.findById(clientID)
                .orElseThrow(NotFoundException::new);
        clientRepository.deleteById(clientID);
    }

    @PutMapping("/{clientID}")
    public Client updateClient(@RequestBody Client client, @PathVariable int clientID) {
        if (client.getClientID() != clientID) {
            throw new IdMismatchException();
        }
        clientRepository.findById(clientID)
                .orElseThrow(NotFoundException::new);
        return clientRepository.save(client);
    }
}
