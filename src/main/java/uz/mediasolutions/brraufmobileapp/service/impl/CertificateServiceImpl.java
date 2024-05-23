package uz.mediasolutions.brraufmobileapp.service.impl;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BarcodeQRCode;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.mediasolutions.brraufmobileapp.entity.Student;
import uz.mediasolutions.brraufmobileapp.exceptions.RestException;
import uz.mediasolutions.brraufmobileapp.repository.StudentRepository;
import uz.mediasolutions.brraufmobileapp.service.abs.CertificateService;

import java.io.FileOutputStream;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class CertificateServiceImpl implements CertificateService {

    private final StudentRepository studentRepository;

    @Value("${upload-dir}")
    private String uploadDir;

    @Override
    public ResponseEntity<?> get(Long studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(
                () -> RestException.restThrow("Student not found", HttpStatus.BAD_REQUEST));
        createCertificate(student);
        return null;
    }

    public void createCertificate(Student student) {
        String dest = student.getFullName() + student.getId() + ".pdf";
        String backgroundPath = "certificate_background.jpg";
        String qrCodeText = "This is a QR code for the certificate";
        String qrCodePath = "qrcode.png";
        try {
//            generateQRCode(qrCodeText, qrCodePath, 100, 100);

            // Create a Document instance
            Document document = new Document(PageSize.A4);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));

            // Open the document
            document.open();

            // Add background image
            Image background = Image.getInstance(backgroundPath);
            background.setAbsolutePosition(0, 0);
            background.scaleToFit(PageSize.A4.getWidth(), PageSize.A4.getHeight());
            document.add(background);

            // Add certificate content
            document.add(new Paragraph("\n\n\n\n\n\n\n\n\n")); // Adjust this to move text down
            document.add(new Paragraph("Certificate of Achievement", com.itextpdf.text.FontFactory.getFont(com.itextpdf.text.FontFactory.HELVETICA_BOLD, 24)));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("This is to certify that", com.itextpdf.text.FontFactory.getFont(com.itextpdf.text.FontFactory.HELVETICA, 16)));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("John Doe", com.itextpdf.text.FontFactory.getFont(com.itextpdf.text.FontFactory.HELVETICA_BOLD, 20)));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("has successfully completed the Java Programming Course", com.itextpdf.text.FontFactory.getFont(com.itextpdf.text.FontFactory.HELVETICA, 16)));

            // Create QR code directly inside the PDF
            BarcodeQRCode qrCode = new BarcodeQRCode(qrCodeText, 100, 100, null);
            Image qrCodeImage = qrCode.getImage();
            qrCodeImage.setAbsolutePosition(450, 50); // Position the QR code on the page
            document.add(qrCodeImage);

            // Close the document
            document.close();

            System.out.println("Certificate created successfully.");
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

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
