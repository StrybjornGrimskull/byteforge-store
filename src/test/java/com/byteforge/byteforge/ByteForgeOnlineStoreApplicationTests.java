package com.byteforge.byteforge;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ByteForgeOnlineStoreApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
	void contextLoads() {
	}

    @Test
    public void showProductDetails() throws Exception {
        mockMvc.perform(get("/products/mvc/details/{0}", "1"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void listProductsPage() throws Exception {
        mockMvc.perform(get("/products/mvc/list")
                        .param("categoryId", "1")
                        .param("brandId", "1")
                        .param("minPrice", "200")
                        .param("maxPrice", "300"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
