package de.wisskirchenj.qr.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class QrcodeApplicationTests {

    @Autowired
    MockMvc mockMvc;

    @Test
    void healthCall_returnsOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/health"))
                .andExpect(status().isOk());
    }

    @Test
    void qrcodeCall_returnsImagePng() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/qrcode"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.IMAGE_PNG_VALUE));
    }

}
