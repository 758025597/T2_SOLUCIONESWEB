package com.alvarez_asencio.service.implementation;

import com.alvarez_asencio.model.Product;
import com.alvarez_asencio.repository.IGenericRepository;
import com.alvarez_asencio.repository.IProductRepository;
import com.alvarez_asencio.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService extends GenericService<Product, Integer> implements IProductService {

    private final IProductRepository repo;

    @Override
    protected IGenericRepository<Product, Integer> getRepo() {
        return repo;
    }

    @Override
    public Product save(Product product) throws Exception {
        // Regla: active por defecto true
        if (product.getActive() == null) {
            product.setActive(true);
        }
        return super.save(product);
    }

    @Override
    public Product update(Integer id, Product product) throws Exception {
        if (product.getActive() == null) {
            product.setActive(true);
        }
        return super.update(id, product);
    }
}
