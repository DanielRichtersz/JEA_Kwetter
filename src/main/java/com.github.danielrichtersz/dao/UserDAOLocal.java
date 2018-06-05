package com.github.danielrichtersz.dao;

import com.github.danielrichtersz.entity.Email;
import com.github.danielrichtersz.entity.User;
import com.github.danielrichtersz.mock.MockDatabase;
import com.github.danielrichtersz.services.MockDatabaseService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;

@ApplicationScoped
public class UserDAOLocal implements UserDAO {

    @Inject
    MockDatabaseService mockDatabaseService;

    public MockDatabase getDatabaseTest() {
        return mockDatabaseService.getDb();
    }

    @Override
    public User getByID(Long id) {
        for (User user : mockDatabaseService.getDb().getUserList()) {
            if (user.getId() == id) {
                return user;
            }
        }
        throw new NotFoundException("The specified user could not be found");
    }

    @Override
    public void create(User entity) {
        entity.setId(this.getNewUserID());

        // Setup validation
        entity.getEmail().setValidationCode(generateValidationCode());

        mockDatabaseService.getDb().getUserList().add(entity);
    }

    @Override
    public void edit(User entity) {
        for (User user : mockDatabaseService.getDb().getUserList()) {
            if (user.getId() == entity.getId()) {
                this.remove(user);
                this.create(entity);
                return;
            }
        }
        throw new NotFoundException("The specified user could not be found");
    }

    @Override
    public void remove(User entity) {
        for (User user : mockDatabaseService.getDb().getUserList()) {
            if (user.getId() == entity.getId()) {
                mockDatabaseService.getDb().getUserList().remove(user);
            }
        }
    }

    @Override
    public User getByCredentials(String email, String password) {
        for (User user : mockDatabaseService.getDb().getUserList()) {
            //Comparison doesn't work, something going wrong in string format? In debug all values are the same ('test@mail.com' and 'Password')
            if (email.equals(user.getEmail().getEmail()) && password.equals(user.getPassword())) {
                return user;
            }
        }
        throw new NotFoundException("The specified credentials do not match any registered user");
    }

    @Override
    public User validateEmail(String validationCode) {
        // Find the user with the corresponding validationcode to be validated
        for (User user : mockDatabaseService.getDb().getUserList()) {
            if (user.getEmail().isConfirmed() == false && user.getEmail().getValidationCode() == validationCode) {
                // Update confirmation status
                user.getEmail().setConfirmed(true);

                // Persist the change
                edit(user);
                return user;
            }
        }
        throw new NotFoundException("The specified validation code was not found");
    }

    public String generateValidationCode() {
        // TODO: Generate actual random validationcode
        return "randomemailverificationcode";
    }

    private long getNewUserID() {
        return mockDatabaseService.getDb().getUserList().size() + 1;
    }


}
