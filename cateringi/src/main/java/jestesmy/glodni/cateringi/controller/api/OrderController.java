package jestesmy.glodni.cateringi.controller.api;

import jestesmy.glodni.cateringi.exception.IdMismatchException;
import jestesmy.glodni.cateringi.exception.NotFoundException;
import jestesmy.glodni.cateringi.model.Client;
import jestesmy.glodni.cateringi.model.Order;
import jestesmy.glodni.cateringi.repository.ClientRepository;
import jestesmy.glodni.cateringi.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping
    public Iterable findAll(){
        return orderRepository.findAll();
    }

    @GetMapping("/clients/{clientID}")
    public List findByClient(@PathVariable int clientID){
        Client client = clientRepository.findById(clientID).orElseThrow(NotFoundException::new);
        return orderRepository.findByClient(client);
    }

    @GetMapping("/{orderID}")
    public Order findOne(@PathVariable int orderID){
        return orderRepository.findById(orderID)
                .orElseThrow(NotFoundException::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Order create(@RequestBody Order order){
        return orderRepository.save(order);
    }

    @DeleteMapping("/{orderID}")
    public void delete(@PathVariable int orderID){
        orderRepository.findById(orderID)
                .orElseThrow(NotFoundException::new);
        orderRepository.deleteById(orderID);
    }

    @PutMapping("/{orderID}")
    public Order updateOrder(@RequestBody Order order, @PathVariable int orderID){
        if (order.getOrderID() != orderID){
            throw new IdMismatchException();
        }

        orderRepository.findById(orderID)
                .orElseThrow(NotFoundException::new);
        return orderRepository.save(order);
    }
}
