package com.rathin.pollify.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.common.BitMatrix;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class QRCodeGenerator {

    public static String generateQRCodeImage(String text, String outputDir, String fileName, int width, int height)
            throws WriterException, IOException {

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        File dir = new File(outputDir);
        if (!dir.exists()) dir.mkdirs();

        Path path = new File(dir, fileName).toPath();
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
        return path.toString();
    }
}