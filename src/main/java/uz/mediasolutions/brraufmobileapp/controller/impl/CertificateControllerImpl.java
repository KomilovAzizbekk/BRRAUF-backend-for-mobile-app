package uz.mediasolutions.brraufmobileapp.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uz.mediasolutions.brraufmobileapp.controller.abs.CertificateController;
import uz.mediasolutions.brraufmobileapp.service.abs.CertificateService;

@RestController
@RequiredArgsConstructor
public class CertificateControllerImpl implements CertificateController {

    private final CertificateService certificateService;

    @Override
    public ResponseEntity<?> get(Long studentId) {
        return certificateService.get(studentId);
    }

    @Override
    public ResponseEntity<?> getCert(String file) {
        return certificateService.getCert(file);
    }
}
