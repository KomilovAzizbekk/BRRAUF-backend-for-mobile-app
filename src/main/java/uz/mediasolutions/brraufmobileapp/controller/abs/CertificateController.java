package uz.mediasolutions.brraufmobileapp.controller.abs;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.mediasolutions.brraufmobileapp.utills.constants.Rest;

@RequestMapping(CertificateController.CERTIFICATE)
public interface CertificateController {

    String CERTIFICATE = Rest.BASE_PATH + "certificate/";
    String GET = "get/{studentId}";
    String GET_CERT = "get-cert/{file}";

    @GetMapping(GET)
    @PreAuthorize("hasAnyRole('ROLE_STUDENT', 'ROLE_SUPER_ADMIN', 'ROLE_ADMIN')")
    ResponseEntity<?> get(@PathVariable Long studentId);

    @GetMapping(GET_CERT)
    ResponseEntity<?> getCert(@PathVariable String file);


}
