package com.alvarez_asencio.controller;

import com.alvarez_asencio.dto.OrderDTO;
import com.alvarez_asencio.model.Order;
import com.alvarez_asencio.service.IOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final IOrderService service;
    private final ModelMapper mapper;

    // GET /v1/orders -> 200
    @GetMapping
    public ResponseEntity<List<OrderDTO>> findAll() throws Exception {
        List<OrderDTO> list = service.findAll().stream().map(this::convertToDto).toList();
        return ResponseEntity.ok(list);
    }

    // GET /v1/orders/{id} -> 200 / 404
    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> findById(@PathVariable Integer id) throws Exception {
        return ResponseEntity.ok(convertToDto(service.findById(id)));
    }

    // POST /v1/orders -> 201 / 400
    @PostMapping
    public ResponseEntity<OrderDTO> save(@Valid @RequestBody OrderDTO dto) throws Exception {
        dto.setIdOrder(null);
        Order saved = service.save(convertToEntity(dto));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getIdOrder())
                .toUri();
        return ResponseEntity.created(location).body(convertToDto(saved));
    }

    // PUT /v1/orders/{id} -> 200 / 400 / 404
    @PutMapping("/{id}")
    public ResponseEntity<OrderDTO> update(@PathVariable Integer id, @Valid @RequestBody OrderDTO dto) throws Exception {
        dto.setIdOrder(id);
        Order updated = service.update(id, convertToEntity(dto));
        return ResponseEntity.ok(convertToDto(updated));
    }

    // DELETE /v1/orders/{id} -> 204 / 404
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) throws Exception {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    private OrderDTO convertToDto(Order order) {
        return mapper.map(order, OrderDTO.class);
    }

    private Order convertToEntity(OrderDTO dto) {
        return mapper.map(dto, Order.class);
    }
}
