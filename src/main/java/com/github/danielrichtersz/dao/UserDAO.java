package dao;

import entity.User;

public interface UserDAO extends BaseDAO<User> {

    User getByCredentials(String email, String password);

    User validateEmail(String validationCode);

    String generateValidationCode();

}
