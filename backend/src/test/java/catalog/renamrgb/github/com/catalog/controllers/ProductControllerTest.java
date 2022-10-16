package catalog.renamrgb.github.com.catalog.controllers;

import catalog.renamrgb.github.com.catalog.dto.ProductDTO;
import catalog.renamrgb.github.com.catalog.services.ProductService;
import catalog.renamrgb.github.com.catalog.services.exceptions.DatabaseException;
import catalog.renamrgb.github.com.catalog.services.exceptions.ResourceNotFoundException;
import catalog.renamrgb.github.com.catalog.test.Factory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockmvc;

    @MockBean
    private ProductService service;

    @Autowired
    private ObjectMapper objectMapper;

    private long existsId;
    private long nonExistsId;
    private long dependentId;

    private ProductDTO productDto = Factory.createProductDTO();
    private PageImpl<ProductDTO> page;

    @BeforeEach
    void setUp() throws Exception {
        existsId = 1L;
        nonExistsId = 2L;
        dependentId = 3L;
        page = new PageImpl<>(List.of(productDto));

        when(service.findAll(any())).thenReturn(page);

        when(service.findById(existsId)).thenReturn(productDto);
        when(service.findById(nonExistsId)).thenThrow(ResourceNotFoundException.class);

        when(service.update(eq(existsId), any())).thenReturn(productDto);
        when(service.update(eq(nonExistsId), any())).thenThrow(ResourceNotFoundException.class);

        when(service.insert(any())).thenReturn(productDto);

        doNothing().when(service).delete(existsId);
        doThrow(ResourceNotFoundException.class).when(service).delete(nonExistsId);
        doThrow(DatabaseException.class).when(service).delete(dependentId);

    }

    @Test
    public void deleteShouldReturnNoContentWhenIdExists() throws Exception {
        ResultActions result = mockmvc.perform(delete("/products/{id}", existsId).accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isNoContent());
    }

    @Test
    public void deleteShouldReturnResourceNotFoundExceptionWhenIdDoesExist() throws Exception {
        ResultActions result = mockmvc.perform(delete("/products/{id}", nonExistsId).accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isNotFound());
    }

    @Test
    public void insertShouldReturnProductDTO() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(productDto);

        ResultActions result = mockmvc.perform(post("/products").content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isCreated());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.name").exists());
        result.andExpect(jsonPath("$.description").exists());

    }

    @Test
    public void updateShouldReturnProductDTOWhenIdExists() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(productDto);

        ResultActions result = mockmvc.perform(put("/products/{id}", existsId).content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.name").exists());
        result.andExpect(jsonPath("$.description").exists());

    }

    @Test
    public void updateShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(productDto);

        ResultActions result = mockmvc.perform(put("/products/{id}", nonExistsId).content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
    }

    @Test
    public void findAllShouldReturnPage() throws Exception {
        ResultActions result = mockmvc.perform(get("/products").accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isOk());

    }

    @Test
    public void findByIdShouldReturnProductDTOWhenIdExists() throws Exception {
        ResultActions result = mockmvc.perform(get("/products/{id}", existsId).accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.name").exists());
        result.andExpect(jsonPath("$.description").exists());
    }

    @Test
    public void findByIdShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
        ResultActions result = mockmvc.perform(get("/products/{id}", nonExistsId).accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isNotFound());
    }

}