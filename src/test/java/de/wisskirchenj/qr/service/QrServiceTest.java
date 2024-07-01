package de.wisskirchenj.qr.service;

import org.junit.jupiter.api.Test;

import java.awt.Color;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class QrServiceTest {

    static final int SIZE = 250;

    @Test
    void test_returns_correct_size() {
        var imageService = new QrService();
        var image = imageService.decodeToQr("test", SIZE, "L");
        assertEquals(SIZE, image.getWidth());
        assertEquals(SIZE, image.getHeight());
    }

    @Test
    void test_qr_is_black_and_white() {
        var imageService = new QrService();
        var qr = imageService.decodeToQr("test", SIZE, "L");
        var blackOrWhite = Set.of(Color.WHITE.getRGB(), Color.BLACK.getRGB());
        for (var x = 0; x < qr.getWidth(); x++) {
            for (var y = 0; y < qr.getHeight(); y++) {
                assertTrue(blackOrWhite.contains(qr.getRGB(x, y)));
            }
        }
    }
}