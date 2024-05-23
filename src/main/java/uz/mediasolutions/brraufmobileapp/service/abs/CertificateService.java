package uz.mediasolutions.brraufmobileapp.service.abs;

import org.springframework.http.ResponseEntity;

public interface CertificateService {

    ResponseEntity<?> get(Long studentId);
}
