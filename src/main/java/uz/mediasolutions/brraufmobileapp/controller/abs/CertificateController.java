package uz.mediasolutions.brraufmobileapp.controller.abs;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.mediasolutions.brraufmobileapp.utills.constants.Rest;

@RequestMapping(CertificateController.CERTIFICATE)
public interface CertificateController {

    String CERTIFICATE = Rest.BASE_PATH + "certificate/";
    String GET = "get/{studentId}";

    @GetMapping(GET)
    ResponseEntity<?> get(@PathVariable Long studentId);


}
