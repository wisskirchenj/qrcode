package de.wisskirchenj.qr.web;

import de.wisskirchenj.qr.service.QrService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@MockitoSettings
class QrcodeApplicationTests {

    @Autowired
    MockMvc mockMvc;

    @SpyBean
    QrService qrService;

    @Test
    void healthCall_returnsOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/health"))
                .andExpect(status().isOk());
    }

    @Test
    void qrCodeCall_onlyContents_hasRightDefaults() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/qrcode?contents=test"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.IMAGE_PNG));
        verify(qrService).decodeToQr("test", 250, "L");
    }

    @ParameterizedTest
    @ValueSource(strings = {"png", "jpeg", "gif"})
    void qrcodeCall_returnsMimeType_asSpecified(String type) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/qrcode?contents=test&size=200&type=" + type))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("image/" + type)));
    }

    @ParameterizedTest
    @ValueSource(ints = {150, 250, 350})
    void qrcodeCall_returnsImageSize_asSpecified(int size) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/qrcode?contents=test&size=" + size + "&type=gif"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.IMAGE_GIF))
                .andExpect(result -> {
                    var image = result.getResponse().getContentAsByteArray();
                    var imageWidth = image[6] & 0xFF | (image[7] & 0xFF) << 8; // width is at index 6 and 7 in GIF
                    assertEquals(size, imageWidth);
                });
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   ", " &size=200&type=gif", " &size=0&type=gif", " &size=200&type=gyf"})
    void qrcodeCall_errorsWhenContentBlank(String contents) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/qrcode?contents=" + contents))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"error\":\"Contents cannot be null or blank\"}"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"A", " ", "A&type=gif", "A&type=no"})
    void qrcodeCall_errorsWhenCorrectionInvalid(String correction) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/qrcode?contents=test&size=200&correction=" + correction))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"error\":\"Permitted error correction levels are L, M, Q, H\"}"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"149", "351", "0", "-10", "1000&type=gif", "100&type=güfff&correction=A"})
    void qrcodeCall_errorsWhenSizeOutOfRange(String size) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/qrcode?contents=test&size=" + size))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"error\":\"Image size must be between 150 and 350 pixels\"}"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"jpg", "bmp"})
    void qrcodeCall_errorsWhenTypeInvalid(String type) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/qrcode?contents=test&size=200&type=" + type))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"error\":\"Only png, jpeg and gif image types are supported\"}"));
    }

}
