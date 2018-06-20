package services.interfaces;

import dto.LoginDTO;
import entity.User;

import javax.xml.bind.ValidationException;

public interface LoginService {

    LoginDTO login(String email, String password) throws ValidationException;

    User validateEmail(String validationCode);

    boolean validateToken(String token, String userId);

}
