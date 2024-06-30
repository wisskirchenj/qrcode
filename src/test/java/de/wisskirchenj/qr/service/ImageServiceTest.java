package de.wisskirchenj.qr.service;

import org.junit.jupiter.api.Test;

import java.awt.Color;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

class ImageServiceTest {

    static final int SIZE = 250;

    @Test
    void test_returns_correct_size() {
        ImageService imageService = new ImageService();
        BufferedImage image = imageService.getWhiteSquare(SIZE);
        assertEquals(250, image.getWidth());
        assertEquals(250, image.getHeight());
    }

    @Test
    void test_white_square_generation() {
        ImageService imageService = new ImageService();
        BufferedImage whiteSquare = imageService.getWhiteSquare(SIZE);
        int whiteRGB = Color.WHITE.getRGB();
        for (int x = 0; x < whiteSquare.getWidth(); x++) {
            for (int y = 0; y < whiteSquare.getHeight(); y++) {
                assertEquals(whiteRGB, whiteSquare.getRGB(x, y));
            }
        }
    }
}