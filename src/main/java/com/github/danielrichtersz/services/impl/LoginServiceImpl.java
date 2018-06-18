package services.impl;

import dao.UserDAOLocal;
import entity.User;
import services.interfaces.LoginService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.xml.bind.ValidationException;

@ApplicationScoped
public class LoginServiceImpl implements LoginService {

    // Not the interface >>> causes unfixable error
    @Inject
    UserDAOLocal userDAOLocal;

    @Override
    public User login(String email, String password) throws ValidationException {
        User user = userDAOLocal.getByCredentials(email, password);
        if (user != null) {
            return user;
        }
        throw new ValidationException("The login credentials could not be validated");
    }

    @Override
    public User validateEmail(String validationCode) {
        return userDAOLocal.validateEmail(validationCode);
    }
}
