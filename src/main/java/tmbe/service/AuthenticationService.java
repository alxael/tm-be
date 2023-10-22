package tmbe.service;

import tmbe.dto.UserForSignInDto;
import tmbe.dto.UserForSignUpDto;

public interface AuthenticationService {
    String signInUser(UserForSignInDto userForSignInDto);

    String signUpUser(UserForSignUpDto userForSignUpDto);
}