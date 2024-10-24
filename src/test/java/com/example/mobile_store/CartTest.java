package com.example.mobile_store;

import com.example.mobile_store.dto.CartDTO;
import com.example.mobile_store.dto.CartRequestDTO;
import com.example.mobile_store.entity.*;
import com.example.mobile_store.repository.*;
import com.example.mobile_store.service.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CartTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private CartDetailRepository cartDetailRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CartService cartService;

    private User testUser;
    private Cart testCart;
    private Product testProduct;
    private CartDetail testCartDetail;
    private List<CartDetail> testCartDetails;

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

        // Set up test cart detail with a valid price
        testCartDetail = new CartDetail();
        testCartDetail.setId(1);
        testCartDetail.setCart(testCart);
        testCartDetail.setProduct(testProduct);
        testCartDetail.setQuantity(1);
        testCartDetail.setProductName("Test Phone");
        testCartDetail.setPrice(999.99);

        testCartDetails = new ArrayList<>();
        testCartDetails.add(testCartDetail);
    }


    @Test
    void testAddToCart() {
        // Arrange
        CartRequestDTO request = new CartRequestDTO();
        request.setProductId(1);
        request.setQuantity(2);
        request.setUserId(1);

        when(userRepository.findById(1)).thenReturn(Optional.of(testUser));
        when(productRepository.findById(1)).thenReturn(Optional.of(testProduct));
        when(cartRepository.findByUser(testUser)).thenReturn(testCart);
        when(cartDetailRepository.findByCartAndProduct(testCart, testProduct)).thenReturn(null);
        when(cartDetailRepository.save(any(CartDetail.class))).thenReturn(testCartDetail);

        // Act
        cartService.addToCart(1, 2, 1);

        // Assert
        verify(cartDetailRepository, times(1)).save(any(CartDetail.class));
        verify(cartRepository, times(1)).save(any(Cart.class));
    }


    @Test
    void testGetCart() {
        // Arrange
        when(userRepository.findById(1)).thenReturn(Optional.of(testUser));
        when(cartRepository.findByUser(testUser)).thenReturn(testCart);
        when(cartDetailRepository.sumQuantityByCart(testCart.getId())).thenReturn(1);

        // Act
        CartDTO result = cartService.getCart(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals(999.99, result.getTotal());
        assertEquals(1, result.getUser_id());
        assertEquals(1, result.getSumProduct());
    }


    @Test
    void testRemoveFromCart() {
        // Arrange
        when(userRepository.findById(1)).thenReturn(Optional.of(testUser));
        when(cartRepository.findByUser(testUser)).thenReturn(testCart);
        when(productRepository.findById(1)).thenReturn(Optional.of(testProduct));
        when(cartDetailRepository.findByCartAndProduct(testCart, testProduct)).thenReturn(testCartDetail);

        // Act
        cartService.removeFromCart(1, 1);

        // Assert
        verify(cartDetailRepository, times(1)).delete(testCartDetail);
        verify(cartRepository, times(1)).save(testCart);
        assertEquals(0.0, testCart.getTotal());
    }


}
