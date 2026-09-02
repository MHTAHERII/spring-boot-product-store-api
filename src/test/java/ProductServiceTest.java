import com.mh.productStore.Product;
import com.mh.productStore.ProductRepository;
import com.mh.productStore.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.parameters.P;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

public class ProductServiceTest {
    @Test
    void testMockRepository(){
        ProductRepository repository = Mockito.mock(ProductRepository.class);//این یه ریپازیتوری تقلبی بساز
    }

    @Test
    void testGetAllProducts(){
        ProductRepository repository = Mockito.mock(ProductRepository.class);
        List<Product> products = List.of(new Product(100,"Laptop",1000,"tecnology"));
        when(repository.findAll()).thenReturn(products);
        ProductService service = new ProductService(repository);
        List<Product> result = service.getProducts();
        assertEquals(1, result.size());
    }

    @Test
    void testGetProduct(){
        ProductRepository repository = Mockito.mock(ProductRepository.class);
        Product p1 = new Product();
        Product p2 = new Product();
        List<Product> products = List.of(p1,p2);
        when(repository.findAll()).thenReturn(products);
        ProductService service = new ProductService(repository);
        List<Product> result = service.getProducts();
        assertEquals(2, result.size());
    }
    @Test
    void testAddProduct(){
        ProductRepository repository = Mockito.mock(ProductRepository.class);
        ProductService service = new ProductService(repository);
        Product product = new Product();
        service.addProduct(product);
        Mockito.verify(repository).save(product);
    }

    @Test
    void testGetProductById(){
        ProductRepository repository = Mockito.mock(ProductRepository.class);
        ProductService service = new ProductService(repository);
        Product product = new Product();
        when(repository.findById(1)).thenReturn(Optional.of(product));
        Product result = service.getProduct(1);
        assertEquals(product, result);
    }

    @Test
    void testGetProductNotFound(){
        ProductRepository repository = Mockito.mock(ProductRepository.class);
        ProductService service = new ProductService(repository);
        when(repository.findById(99)).thenReturn(Optional.empty());
        Product result = service.getProduct(99);
        assertNull(result);
    }
}
