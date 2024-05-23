package uz.mediasolutions.brraufmobileapp.controller.abs;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.mediasolutions.brraufmobileapp.manual.ApiResult;
import uz.mediasolutions.brraufmobileapp.payload.SignInDTO;
import uz.mediasolutions.brraufmobileapp.payload.TokenDTO;
import uz.mediasolutions.brraufmobileapp.payload.UserDTO;
import uz.mediasolutions.brraufmobileapp.utills.constants.Rest;

import javax.validation.Valid;

@RequestMapping(AuthController.AUTH)
public interface AuthController {

    String AUTH = Rest.BASE_PATH + "auth/";

    String SIGN_IN = "sign-in";
    String SIGN_IN_MOBILE_APP = "sign-in-app";
    String GET_ME = "get-me";

    @GetMapping(GET_ME)
    ApiResult<UserDTO> getMe();

    @PostMapping(SIGN_IN_MOBILE_APP)
    ApiResult<TokenDTO> signInMobileApp(@Valid @RequestBody SignInDTO dto);

    @PostMapping(SIGN_IN)
    ApiResult<TokenDTO> signIn(@RequestBody @Valid SignInDTO dto);


}
