package com.alvarez_asencio.service.implementation;

import com.alvarez_asencio.model.Order;
import com.alvarez_asencio.model.Order.OrderStatus;
import com.alvarez_asencio.repository.IGenericRepository;
import com.alvarez_asencio.repository.IOrderRepository;
import com.alvarez_asencio.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService extends GenericService<Order, Integer> implements IOrderService {

    private final IOrderRepository repo;

    @Override
    protected IGenericRepository<Order, Integer> getRepo() {
        return repo;
    }

    @Override
    public Order save(Order order) throws Exception {
        // orderDate la asigna @CreationTimestamp
        order.setOrderDate(null);
        if (order.getStatus() == null) {
            order.setStatus(OrderStatus.PENDIENTE);
        }
        return super.save(order);
    }

    @Override
    public Order update(Integer id, Order order) throws Exception {
        // Se conserva la fecha original del pedido (y lanza 404 si no existe)
        Order existing = findById(id);
        order.setOrderDate(existing.getOrderDate());
        if (order.getStatus() == null) {
            order.setStatus(existing.getStatus());
        }
        return super.update(id, order);
    }
}
