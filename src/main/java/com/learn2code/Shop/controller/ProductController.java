package com.learn2code.Shop.controller;

import com.learn2code.Shop.db.repository.ProductRepository;
import com.learn2code.Shop.domain.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("product")
public class ProductController implements ControllerForAll<Product> {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @PostMapping
    public ResponseEntity add(@RequestBody Product product) { //vytiahne Body do objektu
        productRepository.save(product);
        Integer id = product.getId();
        if (id != null) {
            return new ResponseEntity<>(id, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); //ak sa nepodarí vytvoriť
    }

    @GetMapping("{id}")
    public ResponseEntity get(@PathVariable("id") int id) {
        Optional<Product> productWithId = productRepository.findById(id);
        if (productWithId.isEmpty()) { // ak nenajde product v db, vracia null a status not found
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(productWithId, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getAll() {
        List<Product> products = productRepository.findAll();
        return new ResponseEntity<>(products, HttpStatus.OK); //vracia productlist a status
    }
}
