package uz.mediasolutions.brraufmobileapp.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.mediasolutions.brraufmobileapp.entity.User;
import uz.mediasolutions.brraufmobileapp.enums.RoleName;
import uz.mediasolutions.brraufmobileapp.exceptions.RestException;
import uz.mediasolutions.brraufmobileapp.manual.ApiResult;
import uz.mediasolutions.brraufmobileapp.mapper.UserMapper;
import uz.mediasolutions.brraufmobileapp.payload.SignInDTO;
import uz.mediasolutions.brraufmobileapp.payload.TokenDTO;
import uz.mediasolutions.brraufmobileapp.payload.UserDTO;
import uz.mediasolutions.brraufmobileapp.repository.UserRepository;
import uz.mediasolutions.brraufmobileapp.secret.JwtProvider;
import uz.mediasolutions.brraufmobileapp.service.abs.AuthService;
import uz.mediasolutions.brraufmobileapp.utills.CommonUtils;
import uz.mediasolutions.brraufmobileapp.utills.constants.Message;
import uz.mediasolutions.brraufmobileapp.utills.constants.Rest;

import java.sql.Timestamp;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService, UserDetailsService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return (UserDetails) userRepository.findFirstByUsernameAndEnabledIsTrueAndAccountNonExpiredIsTrueAndAccountNonLockedIsTrueAndCredentialsNonExpiredIsTrue(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @Override
    public ApiResult<TokenDTO> signIn(SignInDTO signInDTO) {
        usernameNotFoundThrow(signInDTO.getUsername());

        User currentUser = checkUsernameAndPasswordAndEtcAndSetAuthenticationOrThrow(signInDTO.getUsername(), signInDTO.getPassword());
        TokenDTO tokenDTO = generateToken(currentUser);
        return ApiResult.success(tokenDTO);
    }

    @Override
    public TokenDTO generateToken(User user) {
        //HOZIRGI VAQT
        Timestamp issuedAt = new Timestamp(System.currentTimeMillis());

        //USER ORQALI TOKEN OLYABMIZ
        String token = jwtProvider.generateAccessToken(user, issuedAt);

        //TOKEN NI DTO QILIB BERYABMIZ
        return TokenDTO.builder()
                .tokenType(Rest.TYPE_TOKEN)
                .accessToken(token)
                .build();
    }

    @Override
    public User checkUsernameAndPasswordAndEtcAndSetAuthenticationOrThrow(String username, String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return (User) authentication.getPrincipal();
        } catch (DisabledException | LockedException | CredentialsExpiredException | BadCredentialsException |
                 UsernameNotFoundException disabledException) {
            throw RestException.restThrow(Message.USER_NOT_FOUND_OR_DISABLED, HttpStatus.BAD_REQUEST);
        } catch (AuthenticationException e) {
            throw RestException.restThrow(Message.BAD_REQUEST, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ApiResult<UserDTO> getMe() {
        User user = CommonUtils.getUserFromSecurityContext();
        return ApiResult.success(userMapper.toDTO(user));
    }

    @Override
    public ApiResult<TokenDTO> signInMobileApp(SignInDTO dto) {
        usernameNotFoundThrow(dto.getUsername());

        User currentUser = checkUsernameAndPasswordAndEtcAndSetAuthenticationOrThrow(dto.getUsername(), dto.getPassword());
        if (currentUser.getRole().getName().equals(RoleName.ROLE_STUDENT)) {
            TokenDTO tokenDTO = generateToken(currentUser);
            return ApiResult.success(tokenDTO);
        } else {
            throw RestException.restThrow(Message.BAD_REQUEST, HttpStatus.BAD_REQUEST);
        }
    }


    private void usernameNotFoundThrow(String username){
        if (!userRepository.existsByUsername(username)) {
            throw RestException.restThrow(Message.USERNAME_NOT_FOUND, HttpStatus.BAD_REQUEST);
        }
    }

    public void checkPasswordEqualityIfErrorThrow(String password, String prePassword) {
        if (Objects.nonNull(password) && !Objects.equals(password,prePassword)){
            throw RestException.restThrow(Message.MISMATCH_PASSWORDS, HttpStatus.BAD_REQUEST);
        }
    }

    public void usernameIfExistsThrow(String username) {
        if (userRepository.existsByUsername(username)) {
            throw RestException.restThrow(Message.USER_ALREADY_REGISTERED, HttpStatus.BAD_REQUEST);
        }
    }
}
