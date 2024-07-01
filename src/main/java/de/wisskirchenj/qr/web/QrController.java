package de.wisskirchenj.qr.web;

import de.wisskirchenj.qr.service.QrService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.awt.image.BufferedImage;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
class QrController {

    private final QrService qrService;

    @GetMapping("/health")
    ResponseEntity<Void> health() {
        return ok().build();
    }

    @GetMapping(value = "/qrcode")
    ResponseEntity<BufferedImage> qrcode(
            @RequestParam("contents") @NotBlank String contents,
            @RequestParam(value = "size", defaultValue = "250") @Max(value = 350) @Min(value = 150) int size,
            @RequestParam(value = "correction", defaultValue = "L") @Pattern(regexp = "[LMQH]") String correction,
            @RequestParam(value = "type", defaultValue = "png") @Pattern(regexp = "png|gif|jpeg") String type
    ) {
        return ok()
                .contentType(MediaType.valueOf("image/" + type))
                .body(qrService.decodeToQr(contents, size, correction));
    }
}
