package edu.coder.house.fact.service;

import edu.coder.house.fact.entity.Product;
import edu.coder.house.fact.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }


    public Product save(Product product) {
        return repository.save(product);
    }


    public List<Product> getProducts() {
        return repository.findAll();
    }


    public Optional<Product> getById(Long id) {
        return repository.findById(id);
    }

    public void deleteById(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new RuntimeException("Producto no encontrado con ID: " + id);
        }
    }
}