package de.wisskirchenj.qr;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.NOT_IMPLEMENTED;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api")
class QrController {

    @GetMapping("/health")
    ResponseEntity<Void> health() {
        return ok().build();
    }

    @GetMapping("/qrcode")
    ResponseEntity<Void> qrcode() {
        return status(NOT_IMPLEMENTED).build();
    }
}
