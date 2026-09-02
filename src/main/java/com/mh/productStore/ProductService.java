package com.mh.productStore;

import com.mh.productStore.exception.ProductNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductService {
    private ProductRepository repository;
    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }
    public List<Product> getProducts(){
    return repository.findAll();
    }
    public void addProduct(Product product){
        repository.save(product);
    }
    public Product getProduct(int id){
        return repository.findById(id).orElseThrow(()->new ProductNotFoundException("product not found with id:"+id));
    }
    public void updateProduct(Product product){
        repository.save(product);
    }
    public void deleteProduct(int id){
        repository.deleteById(id);
    }
    public List<Product> getProductsByCategory(String category){
        return repository.findByCategory(category);
    }
    public List<Product> getProductsByName(String name){
        return repository.findByName(name);
    }
    public List<Product> getExpensiveProducts(double price){
        return repository.getExpensiveProducts(price);
    }
    public List<Product> getCheapbyProducts(double price){
        return repository.getCheapProducts(price);
    }
    public Page<Product> getProducts(int page, int size){
        return repository.findAll(PageRequest.of(page, size));
    }
    public List<Product> getProductsSorted(String field){
        return repository.findAll(Sort.by(field));
    }
    public List<Product> getProductsSortedDesc(String field){
        return repository.findAll(Sort.by(field).descending());
    }
    public List<Product> searchProducts(String keyword){
        return repository.findByNameContainsIgnoreCase(keyword);
    }
    public List<Product> findByCategoryAndPriceLessThan(String category, double price) {
        return repository.findByCategoryAndPriceLessThan(category, price);
    }
    public List<Product> findProductsBetweenPrice(double min,double max){
        return repository.findProductBetweenPrice(min,max);
    }









}
