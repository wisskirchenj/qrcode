package de.wisskirchenj.qr.service;

import org.springframework.stereotype.Service;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Arrays;

@Service
public class ImageService {


    public BufferedImage getWhiteSquare(int size) {
        var image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
        fillWhite(image, size);
        return image;
    }

    private static void fillWhite(BufferedImage image, int size) {
        var pixels = new int[size * size];
        Arrays.fill(pixels, Color.WHITE.getRGB());
        image.setRGB(0, 0, size, size, pixels, 0, size);
    }
}
