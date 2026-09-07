package com.mh.productStore.controller;

import com.mh.productStore.Product;
import com.mh.productStore.ProductService;
import com.mh.productStore.dto.LoginRequest;
import com.mh.productStore.security.JwtService;
import com.mh.productStore.user.AppUser;
import com.mh.productStore.user.RegisterRequest;
import com.mh.productStore.user.UserService;
import jakarta.validation.Valid;
import org.apache.catalina.User;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HelloController {
    private final JwtService  jwtService;
    private final ProductService service;
    private final AuthenticationManager authenticationManager;//مدیریت احراز هویت
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public HelloController (ProductService service, JwtService jwtService, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, UserService userService) {
        this.service = service;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;//مدیر احراز هویت
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @PostMapping("/token")
    public String token (@RequestBody LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        return jwtService.generateToken(request.getUsername());
    }
    @GetMapping("/hello")
    public String hello(){
        return "hello spring boot";
    }
    @GetMapping("/")
    public String home(){
        return "welcome to store";
    }
    @GetMapping("about")
    public String about(){
        return "Spring Boot Learning Project";
    }
    @GetMapping("/products")
    public List<Product> getProducts() {
        return service.getProducts();
    }
    @PostMapping("/products")
    public String addProduct( @Valid @RequestBody Product product){
        service.addProduct(product);
        return "Added product successfully";
    }
    @GetMapping("/products/{id}")
    public Product getProduct(@PathVariable int id){
        return service.getProduct(id);
    }
    @PutMapping("/products")
    public String updateProduct(@RequestBody Product product){
        service.updateProduct(product);
        return "product updated";
    }
    @GetMapping("/product/category/{category}")
    public List<Product> getProductsByCategory(@PathVariable String category){
        return service.getProductsByCategory(category);
    }
    @GetMapping("/product/name/{name}")
    public List<Product> getProductsByname(@PathVariable String name){
        return service.getProductsByName(name);
    }
    @GetMapping("/products/price/{price}")
    public List<Product> getProductsByPrice(@PathVariable double price){
        return service.getExpensiveProducts(price);
    }
    @GetMapping("/products/cheap/{price}")
    public List<Product> getProductsByCheap(@PathVariable double price){
        return service.getCheapbyProducts(price);
    }
    @GetMapping("/products/page")
    public Page<Product> getProductsByPage(int page, int size){
        return service.getProducts(page, size);
    }
    @GetMapping("/products/sort")
    public List<Product> getProductsBySort(String field){
        return service.getProductsSorted(field);
    }
    @GetMapping("/products/sort-desc")
    public List<Product> getProductsBySortDesc(String field){
        return service.getProductsSortedDesc(field);
    }
    @GetMapping("/products/search")
    public List<Product> getProductsBySearch(String field){
        return service.searchProducts(field);
    }
    @GetMapping("/products/filter/{category}/{price}")
    public List<Product> findByCategoryAndPriceLessThan(@PathVariable String category,@PathVariable double price){
        return service.findByCategoryAndPriceLessThan(category, price);
    }
    @GetMapping("/products/range/{min}/{max}")
    public List<Product> getProductsByRange(@PathVariable double min, @PathVariable double max){
        return service.findProductsBetweenPrice(min,max);
    }
    @GetMapping("/username")
    public String getUsername(){
        String token = jwtService.generateToken("user");
        return jwtService.extractUsername(token);
    }
    @GetMapping("/validate")
    public Boolean validate(){
        String token = jwtService.generateToken("user");
        return jwtService.validateToken(token,"user");
    }
    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(), request.getPassword())
        );
        return jwtService.generateToken(request.getUsername());
    }
    @PostMapping("/encode/{password}")
    public String encode(@PathVariable String password){
        return passwordEncoder.encode(password);
    }
    @PostMapping("/register")
    public String register(@Valid @RequestBody RegisterRequest registerRequest){
        userService.register(registerRequest);
        return "User registered successfully";
    }
    @GetMapping("/users")
    public List<AppUser> getUsers(){
        return userService.getAllUser();
    }
    @PostMapping("/users/{username}")
    public String deleteUser(@PathVariable String username){
        userService.deleteUser(username);
        return "User deleted successfully";
    }



}
