package net.bumblebee.service;
import net.bumblebee.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();

    Product saveProduct(Product Product);

    Product getProductById(Long id);

    Product updateProduct(Product Product);

    void deleteProductById(Long id);
}
