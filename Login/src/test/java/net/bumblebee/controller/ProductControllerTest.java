package net.bumblebee.controller;

import net.bumblebee.entity.Product;
import net.bumblebee.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ProductControllerTest {

    private ProductController productController;

    @Mock
    private ProductService productService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        productController = new ProductController(productService);
    }

    @Test
    public void testListProducts() {
        // Arrange
        List<Product> products = new ArrayList<>();
        products.add(new Product(1L, "Product 1", "Description 1", "Category 1", 10.0));
        products.add(new Product(2L, "Product 2", "Description 2", "Category 2", 20.0));
        when(productService.getAllProducts()).thenReturn(products);

        // Act
        ResponseEntity<List<Product>> responseEntity = productController.listProducts();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(products, responseEntity.getBody());
        verify(productService, times(1)).getAllProducts();
    }

    @Test
    public void testCreateProduct() {
        // Arrange
        Product product = new Product(1L, "Product 1", "Description 1", "Category 1", 10.0);

        // Act
        ResponseEntity<Product> responseEntity = productController.createProduct(product);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(product, responseEntity.getBody());
        verify(productService, times(1)).saveProduct(product);
    }

    @Test
    public void testGetProductById() {
        // Arrange
        Long id = 1L;
        Product product = new Product(id, "Product 1", "Description 1", "Category 1", 10.0);
        when(productService.getProductById(id)).thenReturn(product);

        // Act
        ResponseEntity<Product> responseEntity = productController.getProductById(id);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(product, responseEntity.getBody());
        verify(productService, times(1)).getProductById(id);
    }

    @Test
    public void testGetProductByIdNotFound() {
        // Arrange
        Long id = 1L;
        when(productService.getProductById(id)).thenReturn(null);

        // Act
        ResponseEntity<Product> responseEntity = productController.getProductById(id);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        verify(productService, times(1)).getProductById(id);
    }

    @Test
    public void testUpdateProduct() {
        // Arrange
        Long id = 1L;
        Product existingProduct = new Product(id, "Product 1", "Description 1", "Category 1", 10.0);
        Product updatedProduct = new Product(id, "Product 2", "Description 2", "Category 2", 20.0);
        when(productService.getProductById(id)).thenReturn(existingProduct);

        // Act
        ResponseEntity<Product> responseEntity = productController.updateProduct(id, updatedProduct);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updatedProduct, responseEntity.getBody());
        verify(productService, times(1)).getProductById(id);
        verify(productService, times(1)).updateProduct(existingProduct);
        assertEquals(existingProduct.getName(), updatedProduct.getName());
        assertEquals(existingProduct.getDescription(), updatedProduct.getDescription());
        assertEquals(existingProduct.getCategory(), updatedProduct.getCategory());
        assertEquals(existingProduct.getPrice(), updatedProduct.getPrice());
    }

    @Test
    public void testUpdateProductNotFound() {
        // Arrange
        Long id = 1L;
        Product updatedProduct = new Product(id, "Product 2", "Description 2", "Category 2", 20.0);
        when(productService.getProductById(id)).thenReturn(null);

        // Act
        ResponseEntity<Product> responseEntity = productController.updateProduct(id, updatedProduct);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        verify(productService, times(1)).getProductById(id);
        verify(productService, times(0)).updateProduct(any(Product.class));
    }

    @Test
    public void testDeleteProduct() {
        // Arrange
        Long id = 1L;
        Product existingProduct = new Product(id, "Product 1", "Description 1", "Category 1", 10.0);
        when(productService.getProductById(id)).thenReturn(existingProduct);

        // Act
        ResponseEntity<Void> responseEntity = productController.deleteProduct(id);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(productService, times(1)).getProductById(id);
        verify(productService, times(1)).deleteProductById(id);
    }

    @Test
    public void testDeleteProductNotFound() {
        // Arrange
        Long id = 1L;
        when(productService.getProductById(id)).thenReturn(null);

        // Act
        ResponseEntity<Void> responseEntity = productController.deleteProduct(id);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        verify(productService, times(1)).getProductById(id);
        verify(productService, times(0)).deleteProductById(anyLong());
    }
}