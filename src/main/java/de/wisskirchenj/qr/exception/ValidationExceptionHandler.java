package de.wisskirchenj.qr.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.internalServerError;

@ControllerAdvice
@RequiredArgsConstructor
class ValidationExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(QrWriterException.class)
    ResponseEntity<Object> handleQrWriterException(QrWriterException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("error", ex.getMessage());
        return internalServerError().body(body);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    ResponseEntity<Object> handleValidationException(HandlerMethodValidationException ex) {
        Map<String, Object> body = new HashMap<>();
        var resolvableError = ex.getAllValidationResults().getFirst()
                .getResolvableErrors().getFirst();
        body.put("error", messageSource.getMessage(resolvableError, Locale.getDefault()));
        return badRequest().body(body);
    }
}
