package dao;

import entity.User;
import mock.MockDatabase;

import java.util.List;

public class LocalUserDaoImpl implements UserDAO {

    private MockDatabase db = new MockDatabase();

    @Override
    public User findByID(Long id) {
        List<User> users = db.getUserList();
        for (User user : users) {
            if (id == user.getId()) {
                return user;
            }
        }
    }

    @Override
    public List<User> getAll() {
        return db.getUserList();
    }

    @Override
    public void insertUser(User user) {
        db.getUserList().add(user);
    }

    @Override
    public void updateUser(User user) {
        for (User u : db.getUserList()) {
            if (u.getId() == user.getId()){
                db.getUserList().add(db.getUserList().indexOf(u), user);
            }
        }
    }

    @Override
    public void deleteUser(User user) {
        for (User u : db.getUserList()) {
            if (u.getId() == user.getId()){
                db.getUserList().remove(user);
            }
        }
    }
}
