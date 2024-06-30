package de.wisskirchenj.qr.web;

import de.wisskirchenj.qr.service.ImageService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.image.BufferedImage;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
class QrController {

    private final ImageService imageService;

    @GetMapping("/health")
    ResponseEntity<Void> health() {
        return ok().build();
    }

    @GetMapping(value = "/qrcode", produces = "image/png")
    ResponseEntity<BufferedImage> qrcode(@Max(350) @Min(150) int size) {
        return ok(imageService.getWhiteSquare(size));
    }
}
