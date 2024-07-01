package de.wisskirchenj.qr.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import de.wisskirchenj.qr.exception.QrWriterException;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.util.Map;

@Service
public class QrService {


    public BufferedImage decodeToQr(String contents, int size, String correction) {
        var qrCodeWriter = new QRCodeWriter();
        var hints = Map.of(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.valueOf(correction));
        try {
            return MatrixToImageWriter.toBufferedImage(
                    qrCodeWriter.encode(contents, BarcodeFormat.QR_CODE, size, size, hints));
        } catch (WriterException e) {
            throw new QrWriterException(e);
        }
    }
}
