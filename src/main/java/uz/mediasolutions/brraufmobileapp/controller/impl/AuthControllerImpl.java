package uz.mediasolutions.brraufmobileapp.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import uz.mediasolutions.brraufmobileapp.controller.abs.AuthController;
import uz.mediasolutions.brraufmobileapp.manual.ApiResult;
import uz.mediasolutions.brraufmobileapp.payload.SignInDTO;
import uz.mediasolutions.brraufmobileapp.payload.TokenDTO;
import uz.mediasolutions.brraufmobileapp.service.abs.AuthService;

@RestController
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {

    private final AuthService authService;

    @Override
    public ApiResult<TokenDTO> signIn(SignInDTO dto) {
        return authService.signIn(dto);
    }
}
