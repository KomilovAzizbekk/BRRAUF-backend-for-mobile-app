package uz.mediasolutions.brraufmobileapp.service.abs;

import uz.mediasolutions.brraufmobileapp.entity.User;
import uz.mediasolutions.brraufmobileapp.manual.ApiResult;
import uz.mediasolutions.brraufmobileapp.payload.SignInDTO;
import uz.mediasolutions.brraufmobileapp.payload.TokenDTO;
import uz.mediasolutions.brraufmobileapp.payload.UserDTO;

public interface AuthService {

    ApiResult<TokenDTO> signIn(SignInDTO signInDTO);

    TokenDTO generateToken(User user);

    User checkUsernameAndPasswordAndEtcAndSetAuthenticationOrThrow(String username, String password);

    ApiResult<UserDTO> getMe();

    ApiResult<TokenDTO> signInMobileApp(SignInDTO dto);
}
