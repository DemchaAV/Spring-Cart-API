package com.demcha.spring_cart_api.orders;

import com.demcha.spring_cart_api.auth.AuthService;
import com.demcha.spring_cart_api.users.User;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;


@AllArgsConstructor
@Service
public class OrderService {
    private OrderRepository orderRepository;
    private OrderMapper orderMapper;
    private AuthService authService;

    public List<OrderDto> getAllOrders() {
        User currentUser = authService.getCurrentUser();

        List<Order> orders = orderRepository.findOrdersByCustomer(currentUser);

        return orders.stream().map(orderMapper::toDto).toList();
    }

    public OrderDto getOrder(Long orderId) {
        Order order = orderRepository
                .getOrderWithItems(orderId)
                .orElseThrow(OrderNotFoundException::new);

        User user = authService.getCurrentUser();
        if (!order.isPlacedBy(user)) {
            throw new AccessDeniedException("You do not have permission to access this order");
        }
        return orderMapper.toDto(order);
    }
}
