package services.impl;

import dao.UserDAOLocal;
import dto.LoginDTO;
import entity.User;
import services.interfaces.LoginService;
import util.JWTAuth;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.xml.bind.ValidationException;
import java.nio.file.AccessDeniedException;

@ApplicationScoped
public class LoginServiceImpl implements LoginService {

    // Not the interface >>> causes unfixable error
    @Inject
    UserDAOLocal userDAOLocal;

    @Override
    public LoginDTO login(String email, String password) throws ValidationException {
        User user = userDAOLocal.getByCredentials(email, password);
        if (user != null) {
            // Username & password match
            String token = JWTAuth.getInstance().GenerateToken(String.valueOf(user.getId()));
            LoginDTO loginDTO = new LoginDTO(token, user);
            return loginDTO;
        }
        throw new ValidationException("The login credentials could not be validated");
    }

    @Override
    public User validateEmail(String validationCode) {
        return userDAOLocal.validateEmail(validationCode);
    }

    /**
     * Called to validate the passed token in combination with the userID.
     * This method is used to validate the token when performing actions which requires a user to be logged in.
     * @param token Token received from front-end
     * @param userId UserID received from front-end
     * @return
     */
    @Override
    public boolean validateToken(String token, String userId) {
        try {
            // Check headers input.
            if (token == null || token.isEmpty() || userId == null || userId.isEmpty()) {
                throw new AccessDeniedException("No userId or token received");
            }

            // Verify token.
            if (JWTAuth.getInstance().VerifyToken(token, userId)) {
                return true;
            }
        } catch (AccessDeniedException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
}
