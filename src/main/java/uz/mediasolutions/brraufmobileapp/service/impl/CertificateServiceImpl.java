package uz.mediasolutions.brraufmobileapp.service.impl;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BarcodeQRCode;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
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

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

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
//        try {
//            // Load the image file as a resource
//            Path imagePath = Paths.get(uploadDir).resolve(certificate);
//            Resource resource = new UrlResource(imagePath.toUri());
//
//            // Check if the resource exists
//            if (resource.exists()) {
//                return ResponseEntity.ok()
//                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + resource.getFilename())
//                        .contentType(MediaType.IMAGE_JPEG) // Adjust the MediaType according to your image type
//                        .body(resource);
//            } else {
//                return ResponseEntity.notFound().build();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//            return ResponseEntity.status(500).build();
//        }
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
        String dest = "certificates/" + student.getFullName() + student.getId() + ".pdf";
        String path = constructImageUrl(dest);
        String backgroundPath = "certificate_background.jpg";
        try {
//            generateQRCode(qrCodeText, qrCodePath, 100, 100);

            Rectangle pageSize = new Rectangle(792, 612);
            // Create a Document instance
            Document document = new Document(pageSize);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));

            // Open the document
            document.open();

            // Add background image
            Image background = Image.getInstance(backgroundPath);
            background.setAbsolutePosition(0, 0);
            background.scaleToFit(pageSize.getWidth(), pageSize.getHeight());
            document.add(background);

            // Add a title
            Paragraph title = new Paragraph("Certificate of Achievement",
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 36, BaseColor.BLACK));
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingBefore(150);
            document.add(title);

            // Add recipient name
            Paragraph recipient = new Paragraph("This is to certify that\n\nKomilov Azizbek",
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24, BaseColor.BLACK));
            recipient.setAlignment(Element.ALIGN_CENTER);
            recipient.setSpacingBefore(50);
            document.add(recipient);

            // Add description
            Paragraph description = new Paragraph("has successfully completed the Java Programming Course",
                    FontFactory.getFont(FontFactory.HELVETICA, 18, BaseColor.BLACK));
            description.setAlignment(Element.ALIGN_CENTER);
            description.setSpacingBefore(30);
            document.add(description);

            // Create QR code directly inside the PDF
            BarcodeQRCode qrCode = new BarcodeQRCode(path, 100, 100, null);
            Image qrCodeImage = qrCode.getImage();
            qrCodeImage.setAbsolutePosition(600, 50); // Position the QR code on the page
            document.add(qrCodeImage);

            // Close the document
            document.close();
            writer.close();
            System.out.println("Certificate created successfully.");
            return path;
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            throw RestException.restThrow("Certificate creation failed", HttpStatus.INTERNAL_SERVER_ERROR);
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

//    private static void generateQRCode(String text, String filePath, int width, int height) throws IOException {
//        Map<EncodeHintType, Object> hints = new HashMap<>();
//        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
//
//        QRCodeWriter qrCodeWriter = new QRCodeWriter();
//        BitMatrix bitMatrix;
//        try {
//            bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height, hints);
//        } catch (Exception e) {
//            throw new IOException("Could not generate QR Code", e);
//        }
//
//        Path path = FileSystems.getDefault().getPath(filePath);
//        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
//    }
}
