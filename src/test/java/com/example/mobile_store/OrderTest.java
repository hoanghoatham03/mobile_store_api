package com.example.mobile_store;

import com.example.mobile_store.dto.OrderDTO;
import com.example.mobile_store.entity.*;
import com.example.mobile_store.repository.*;
import com.example.mobile_store.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class OrderTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderDetailRepository orderDetailRepository;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private CartDetailRepository cartDetailRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private OrderService orderService;

    private User testUser;
    private Cart testCart;
    private Product testProduct;
    private CartDetail testCartDetail;
    private List<CartDetail> testCartDetails;
    private Order testOrder;
    private List<Order> testOrders;

    @BeforeEach
    void setUp() {
        // Set up test user
        testUser = new User();
        testUser.setId(1);
        testUser.setUsername("testUser");

        // Set up test product
        testProduct = new Product();
        testProduct.setId(1);
        testProduct.setProductName("Test Phone");
        testProduct.setPrice(999.99);

        // Set up test cart
        testCart = new Cart();
        testCart.setId(1);
        testCart.setUser(testUser);
        testCart.setTotal(999.99);

        // Set up test cart detail
        testCartDetail = new CartDetail();
        testCartDetail.setId(1);
        testCartDetail.setCart(testCart);
        testCartDetail.setProduct(testProduct);
        testCartDetail.setQuantity(1);
        testCartDetail.setProductName("Test Phone");

        testCartDetails = new ArrayList<>();
        testCartDetails.add(testCartDetail);
    }

    @Test
    void testCheckout() {
        // Arrange
        // Add another product to cart
        Product product2 = new Product();
        product2.setId(2);
        product2.setProductName("Test Phone 2");
        product2.setPrice(799.99);

        CartDetail cartDetail2 = new CartDetail();
        cartDetail2.setId(2);
        cartDetail2.setCart(testCart);
        cartDetail2.setProduct(product2);
        cartDetail2.setQuantity(1);
        cartDetail2.setProductName("Test Phone 2");

        testCartDetails.add(cartDetail2);
        testCart.setTotal(1799.98); // Sum of both products

        when(userRepository.findById(1)).thenReturn(Optional.of(testUser));
        when(cartRepository.findByUser(testUser)).thenReturn(testCart);
        when(cartDetailRepository.findAllByCart(testCart)).thenReturn(testCartDetails);
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> {
            Order order = invocation.getArgument(0);
            order.setId(1);
            return order;
        });

        // Act
        orderService.addOrder(1);

        // Assert
        verify(orderRepository, times(1)).save(any(Order.class));
        verify(orderDetailRepository, times(2)).save(any(OrderDetail.class));
        verify(cartDetailRepository, times(1)).deleteAll(testCartDetails);
        verify(cartRepository, times(1)).delete(testCart);
    }

    @Test
    void testGetAllOrders() {
        // Arrange
        Order order1 = new Order();
        order1.setId(1);
        order1.setUser(testUser);
        order1.setTotal(999.99);
        order1.setStatus("Completed");

        Order order2 = new Order();
        order2.setId(2);
        order2.setUser(testUser);
        order2.setTotal(799.99);
        order2.setStatus("Pending");

        List<Order> orders = new ArrayList<>();
        orders.add(order1);
        orders.add(order2);

        when(orderRepository.findAll()).thenReturn(orders);

        // Act
        List<OrderDTO> result = orderService.getAllOrders();

        // Assert
        assertEquals(2, result.size());
        assertEquals(order1.getId(), result.get(0).getId());
        assertEquals(order2.getId(), result.get(1).getId());
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void testGetOrderById() {
        // Arrange
        int orderId = 1;
        int userId = 1; // Add userId
        Order order = new Order();
        order.setId(orderId);
        order.setUser(testUser);
        order.setTotal(999.99);
        order.setStatus("Completed");

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        // Act
        OrderDTO result = orderService.getOrder(orderId, userId); // Pass both orderId and userId

        // Assert
        assertNotNull(result);
        assertEquals(orderId, result.getId());
        assertEquals(order.getTotal(), result.getTotal());
        assertEquals(order.getStatus(), result.getStatus());
        verify(orderRepository, times(1)).findById(orderId);
    }

    @Test
    void testUpdateOrderStatus() {
        // Arrange
        int orderId = 1;
        String newStatus = "Shipped";
        Order order = new Order();
        order.setId(orderId);
        order.setUser(testUser);
        order.setTotal(999.99);
        order.setStatus("Pending");

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        // Act
        orderService.updateOrderStatus(orderId, newStatus);

        // Assert
        assertEquals(newStatus, order.getStatus());
        verify(orderRepository, times(1)).findById(orderId);
        verify(orderRepository, times(1)).save(order);
    }
}