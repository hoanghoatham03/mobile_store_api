package com.example.mobile_store;

import com.example.mobile_store.dto.ProductCreateDTO;
import com.example.mobile_store.dto.ProductCriteriaDTO;
import com.example.mobile_store.dto.ProductDTO;
import com.example.mobile_store.dto.ProductUpdateDTO;
import com.example.mobile_store.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.multipart.MultipartFile;


import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(roles = {"ADMIN"})
public class ProductTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    private final String endpoint = "/api/products";

//    @Test
//    public void testCreate() throws Exception {
//        // Mock Product
//        ProductCreateDTO newProduct = new ProductCreateDTO();
//        newProduct.setProductName("Product 1");
//        newProduct.setItemcode("PD001");
//        newProduct.setDescription("Product 1 description");
//        newProduct.setManufacture("Manufacture 1");
//        newProduct.setCategory("Category 1");
//        newProduct.setPrice(1000000.0);
//        newProduct.setQuantity(10);
//        newProduct.setProductCondition("New");
//
//        // Mock MultipartFile
//        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "Test content".getBytes());
//
//        // Mock ProductService
//        given(productService.createProduct(any(ProductCreateDTO.class), any(MultipartFile.class))).willReturn(newProduct);
//
//        // Perform POST request
//        ResultActions response = mockMvc.perform(post(endpoint)
//                .contentType("multipart/form-data")
//                .content(objectMapper.writeValueAsString(newProduct))
//                .param("file", file.getBytes().toString()));
//
//        // Verify the response
//        response.andDo(print())
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.productName").value(newProduct.getProductName()))
//                .andExpect(jsonPath("$.itemcode").value(newProduct.getItemcode()))
//                .andExpect(jsonPath("$.description").value(newProduct.getDescription()))
//                .andExpect(jsonPath("$.manufacture").value(newProduct.getManufacture()))
//                .andExpect(jsonPath("$.category").value(newProduct.getCategory()))
//                .andExpect(jsonPath("$.price").value(newProduct.getPrice()))
//                .andExpect(jsonPath("$.quantity").value(newProduct.getQuantity()))
//                .andExpect(jsonPath("$.productCondition").value(newProduct.getProductCondition()));
//    }


    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void testCreate() throws Exception {
        // Create ProductCreateDTO
        ProductCreateDTO productCreateDTO = new ProductCreateDTO();
        productCreateDTO.setProductName("Product 1");
        productCreateDTO.setItemcode("PD001");
        productCreateDTO.setDescription("Product 1 description");
        productCreateDTO.setManufacture("Manufacture 1");
        productCreateDTO.setCategory("Category 1");
        productCreateDTO.setPrice(1000000.0);
        productCreateDTO.setQuantity(10);
        productCreateDTO.setProductCondition("New");

        // Create ProductDTO (expected result)
        ProductDTO expectedProductDTO = new ProductDTO();
        expectedProductDTO.setId(1);
        expectedProductDTO.setProductName(productCreateDTO.getProductName());
        expectedProductDTO.setItemcode(productCreateDTO.getItemcode());
        expectedProductDTO.setDescription(productCreateDTO.getDescription());
        expectedProductDTO.setManufacture(productCreateDTO.getManufacture());
        expectedProductDTO.setCategory(productCreateDTO.getCategory());
        expectedProductDTO.setPrice(productCreateDTO.getPrice());
        expectedProductDTO.setQuantity(productCreateDTO.getQuantity());
        expectedProductDTO.setProductCondition(productCreateDTO.getProductCondition());
        expectedProductDTO.setImage("http://localhost:8080/resources/images/product/test-image.jpg");

        // Mock MultipartFile
        MockMultipartFile file = new MockMultipartFile("image", "test-image.jpg", "image/jpeg", "test image content".getBytes());

        // Mock ProductService
        given(productService.createProduct(any(ProductCreateDTO.class), any(MultipartFile.class))).willReturn(expectedProductDTO);

        // Perform POST request
        ResultActions response = mockMvc.perform(multipart(endpoint + "/create")
                .file(file)
                .param("productName", productCreateDTO.getProductName())
                .param("itemcode", productCreateDTO.getItemcode())
                .param("description", productCreateDTO.getDescription())
                .param("manufacture", productCreateDTO.getManufacture())
                .param("category", productCreateDTO.getCategory())
                .param("price", productCreateDTO.getPrice().toString())
                .param("quantity", productCreateDTO.getQuantity().toString())
                .param("productCondition", productCreateDTO.getProductCondition())
                .contentType(MediaType.MULTIPART_FORM_DATA));

        // Verify the response
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(expectedProductDTO.getId())))
                .andExpect(jsonPath("$.productName", is(expectedProductDTO.getProductName())))
                .andExpect(jsonPath("$.itemcode", is(expectedProductDTO.getItemcode())))
                .andExpect(jsonPath("$.description", is(expectedProductDTO.getDescription())))
                .andExpect(jsonPath("$.manufacture", is(expectedProductDTO.getManufacture())))
                .andExpect(jsonPath("$.category", is(expectedProductDTO.getCategory())))
                .andExpect(jsonPath("$.price", is(expectedProductDTO.getPrice())))
                .andExpect(jsonPath("$.quantity", is(expectedProductDTO.getQuantity())))
                .andExpect(jsonPath("$.productCondition", is(expectedProductDTO.getProductCondition())))
                .andExpect(jsonPath("$.image", is(expectedProductDTO.getImage())));
    }

    @Test
    public void testGetAllProducts() throws Exception {
        List<ProductDTO> productList = Arrays.asList(
                new ProductDTO("Product 1", "P1", "Desc 1", "Manu 1", "Cat 1", 1000.0, 10, "New", "image1.jpg", 1),
                new ProductDTO("Product 2", "P2", "Desc 2", "Manu 2", "Cat 2", 2000.0, 20, "Used", "image2.jpg", 2)
        );

        given(productService.getAllProducts(any(Pageable.class))).willReturn(productList);

        ResultActions response = mockMvc.perform(get(endpoint)
                .param("pageNo", "1")
                .param("pageSize", "10"));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(productList.size())))
                .andExpect(jsonPath("$[0].productName", is("Product 1")))
                .andExpect(jsonPath("$[1].productName", is("Product 2")));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void testUpdateProduct() throws Exception {
        int productId = 1;
        ProductUpdateDTO productUpdateDTO = new ProductUpdateDTO();
        productUpdateDTO.setProductName("Updated Product");
        productUpdateDTO.setPrice(1500000.0);

        ProductDTO updatedProductDTO = new ProductDTO();
        updatedProductDTO.setId(productId);
        updatedProductDTO.setProductName(productUpdateDTO.getProductName());
        updatedProductDTO.setPrice(productUpdateDTO.getPrice());
        updatedProductDTO.setImage("http://localhost:8080/resources/images/product/updated-image.jpg");

        MockMultipartFile file = new MockMultipartFile("image", "updated-image.jpg", "image/jpeg", "updated image content".getBytes());

        given(productService.updateProduct(any(ProductUpdateDTO.class), any(MultipartFile.class), eq(productId)))
                .willReturn(updatedProductDTO);

        ResultActions response = mockMvc.perform(multipart(endpoint + "/{id}", productId)
                .file(file)
                .param("productName", productUpdateDTO.getProductName())
                .param("price", productUpdateDTO.getPrice().toString())
                .with(request -> {
                    request.setMethod("PUT");
                    return request;
                })
                .contentType(MediaType.MULTIPART_FORM_DATA));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(productId)))
                .andExpect(jsonPath("$.productName", is(productUpdateDTO.getProductName())))
                .andExpect(jsonPath("$.price", is(productUpdateDTO.getPrice())))
                .andExpect(jsonPath("$.image", is(updatedProductDTO.getImage())));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void testDeleteProduct() throws Exception {
        int productId = 1;

        willDoNothing().given(productService).deleteProduct(productId);

        ResultActions response = mockMvc.perform(delete(endpoint + "/{id}", productId));

        response.andExpect(status().isOk());

        verify(productService, times(1)).deleteProduct(productId);
    }

    @Test
    public void testFilterProducts() throws Exception {
        // Prepare test data
        List<ProductDTO> filteredProducts = Arrays.asList(
                new ProductDTO("iPhone 12", "IP12", "Apple smartphone", "Apple", "Smartphone", 999.99, 50, "New", "iphone12.jpg", 1),
                new ProductDTO("Galaxy S21", "GS21", "Samsung flagship", "Samsung", "Smartphone", 899.99, 30, "New", "galaxys21.jpg", 2)
        );

        // Mock service method
        given(productService.filterProducts(any(Pageable.class), any(ProductCriteriaDTO.class)))
                .willReturn(filteredProducts);

        // Perform GET request with filter parameters
        ResultActions response = mockMvc.perform(get(endpoint + "/filter")
                .param("pageNo", "1")
                .param("pageSize", "10")
                .param("productName", "phone")
                .param("price", "500", "1000")
                .param("category", "Smartphone")
                .param("manufacture", "Apple", "Samsung")
                .contentType(MediaType.APPLICATION_JSON));

        // Verify the response
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(filteredProducts.size())))
                .andExpect(jsonPath("$[0].productName", is("iPhone 12")))
                .andExpect(jsonPath("$[0].category", is("Smartphone")))
                .andExpect(jsonPath("$[0].manufacture", is("Apple")))
                .andExpect(jsonPath("$[1].productName", is("Galaxy S21")))
                .andExpect(jsonPath("$[1].category", is("Smartphone")))
                .andExpect(jsonPath("$[1].manufacture", is("Samsung")));

        // Verify that the service method was called with correct parameters
        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
        ArgumentCaptor<ProductCriteriaDTO> criteriaCaptor = ArgumentCaptor.forClass(ProductCriteriaDTO.class);
        verify(productService).filterProducts(pageableCaptor.capture(), criteriaCaptor.capture());

        Pageable capturedPageable = pageableCaptor.getValue();
        assertEquals(0, capturedPageable.getPageNumber());
        assertEquals(10, capturedPageable.getPageSize());

        ProductCriteriaDTO capturedCriteria = criteriaCaptor.getValue();
        assertTrue(capturedCriteria.getProductName().isPresent());
        assertEquals("phone", capturedCriteria.getProductName().get());
        assertTrue(capturedCriteria.getPrice().isPresent());
        assertEquals(Arrays.asList("500", "1000"), capturedCriteria.getPrice().get());
        assertTrue(capturedCriteria.getCategory().isPresent());
        assertEquals(Collections.singletonList("Smartphone"), capturedCriteria.getCategory().get());
        assertTrue(capturedCriteria.getManufacture().isPresent());
        assertEquals(Arrays.asList("Apple", "Samsung"), capturedCriteria.getManufacture().get());
    }
}