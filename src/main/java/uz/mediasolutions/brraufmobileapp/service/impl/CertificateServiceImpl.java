package uz.mediasolutions.brraufmobileapp.service.impl;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.mediasolutions.brraufmobileapp.entity.Student;
import uz.mediasolutions.brraufmobileapp.exceptions.RestException;
import uz.mediasolutions.brraufmobileapp.repository.StudentRepository;
import uz.mediasolutions.brraufmobileapp.service.abs.CertificateService;
import uz.mediasolutions.brraufmobileapp.utills.constants.Rest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CertificateServiceImpl implements CertificateService {

    private final StudentRepository studentRepository;

    @Value("${upload-dir}")
    private String uploadDir;

    private final Environment environment;

    @Override
    public ResponseEntity<?> get(Long studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(
                () -> RestException.restThrow("Student not found", HttpStatus.BAD_REQUEST));
        String certificate = createCertificate(student);
        return ResponseEntity.ok(certificate);
    }

    @Override
    public ResponseEntity<?> getCert(String file) {
        try {
            // Load the image file as a resource
            Path imagePath = Paths.get(uploadDir).resolve(file);
            Resource resource = new UrlResource(imagePath.toUri());

            // Check if the resource exists
            if (resource.exists()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + resource.getFilename())
                        .contentType(MediaType.APPLICATION_PDF) // Adjust the MediaType according to your image type
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    public String createCertificate(Student student) {
        String pdfPath = "certificates/cert.pdf";
        String outputPdfPath = "certificates/" + student.getFullName().replace(' ', '_') + student.getId() + ".pdf";
        String qrCodeText = constructImageUrl(outputPdfPath);
        String textToAdd = student.getFullName() + "ga";

        try {
            // Generate QR Code
            ByteArrayOutputStream qrCodeStream = generateQRCode(qrCodeText, 80, 80);

            // Load the existing PDF
            PdfDocument pdfDoc = new PdfDocument(new PdfReader(pdfPath), new PdfWriter(outputPdfPath));
            Document document = new Document(pdfDoc, PageSize.B4);

            // Add QR Code to the left-bottom corner of the first page
            ImageData qrCodeImageData = ImageDataFactory.create(qrCodeStream.toByteArray());
            Image qrCodeImage = new Image(qrCodeImageData);
            qrCodeImage.setFixedPosition(100, 70); // Position (x, y) for the left-bottom corner
            qrCodeImage.scaleToFit(80, 80);

            // Add QR code image to the first page
            document.add(qrCodeImage);


            // Add text to the PDF
            // Add styled text to the PDF
            Paragraph paragraph = new Paragraph(textToAdd)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER)
                    .setWidth(400)
                    .setMarginTop(250)
                    .setFontSize(40)
                    .setFontColor(new DeviceRgb(250, 220, 120));

//            paragraph.setFixedPosition(document.getPdfDocument().getFirstPage().getPageSize().getWidth() / 2, 250, 400);
            document.add(paragraph);

            // Close the document
            document.close();

            System.out.println("PDF edited, QR code inserted, and text added successfully.");
            return constructImageUrl(outputPdfPath);
        } catch (IOException | WriterException e) {
            e.printStackTrace();
            throw RestException.restThrow("Error creating certificate", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String constructImageUrl(String fileName) {
        String baseUrl = environment.getProperty("server.base-url", "https://brrauf-backend.medias.uz" + Rest.BASE_PATH);
        return baseUrl + "certificate/get-cert/" + fileName.substring(fileName.indexOf('/') + 1);
    }

//    private String constructImageUrl(String fileName) {
//        String baseUrl = environment.getProperty("server.base-url", "http://localhost:8080" + Rest.BASE_PATH);
//        return baseUrl + "certificate/get-cert/" + fileName.substring(fileName.indexOf('/') + 1);
//    }

    private static ByteArrayOutputStream generateQRCode(String text, int width, int height) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.MARGIN, 0); // Remove QR code margin

        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height, hints);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        return pngOutputStream;
    }
}
