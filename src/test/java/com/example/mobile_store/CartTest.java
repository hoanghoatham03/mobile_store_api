package com.example.mobile_store;

import com.example.mobile_store.dto.CartRequestDTO;
import com.example.mobile_store.entity.Cart;
import com.example.mobile_store.entity.CartDetail;
import com.example.mobile_store.entity.Product;
import com.example.mobile_store.entity.User;
import com.example.mobile_store.repository.CartDetailRepository;
import com.example.mobile_store.repository.CartRepository;
import com.example.mobile_store.repository.ProductRepository;
import com.example.mobile_store.repository.UserRepository;
import com.example.mobile_store.service.CartService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class CartTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CartService cartService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private CartRepository cartRepository;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private CartDetailRepository cartDetailRepository;

    private final String endpoint = "/api/carts/add";

    @Test
    @WithMockUser(username = "user1", authorities = {"ROLE_USER"})
    public void testAddToCart() throws Exception {
        // Arrange
        CartRequestDTO request = new CartRequestDTO();
        request.setProductId(1);
        request.setQuantity(2);
        request.setUserId(1);

        User user = new User();
        user.setId(1);

        Product product = new Product();
        product.setId(1);
        product.setProductName("Test Product");
        product.setPrice(100.0);

        Cart cart = new Cart();
        cart.setId(1);
        cart.setUser(user);
        cart.setTotal(0.0);

        given(userRepository.findById(1)).willReturn(Optional.of(user));
        given(productRepository.findById(1)).willReturn(Optional.of(product));
        given(cartRepository.findByUser(user)).willReturn(cart);

        // Act & Assert
        mockMvc.perform(post(endpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated());

        // Verify service method was called with correct parameters
        verify(cartService).addToCart(request.getProductId(), request.getQuantity(), request.getUserId());
    }



    
}