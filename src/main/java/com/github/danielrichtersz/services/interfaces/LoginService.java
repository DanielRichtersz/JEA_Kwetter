package services.interfaces;

import entity.User;

import javax.xml.bind.ValidationException;

public interface LoginService {

    User login(String email, String password) throws ValidationException;

    User validateEmail(String validationCode);
}
