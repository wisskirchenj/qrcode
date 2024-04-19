package de.wisskirchenj.qr;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(QrController.class)
class QrcodeApplicationTests {

    @Autowired
    MockMvc mockMvc;

    @Test
    void healthCall_returnsOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/health"))
                .andExpect(status().isOk());
    }

    @Test
    void qrcodeCall_returnsNotImplemented() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/qrcode"))
                .andExpect(status().isNotImplemented());
    }

}
