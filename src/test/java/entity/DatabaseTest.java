package entity;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class DatabaseTest {

    //@PersistenceContext()
    //private EntityManager em;

    @Test
    public void DatabaseTest() {
        List<String> interests = new ArrayList<>();
        interests.add("Food");
        interests.add("Something interesting");

        User user = new User();

        Email email = new Email();
        email.setEmail("email@email.com");
        email.setConfirmed(true);
        user.setEmail(email);
        user.setPhonenumber("0621374675");
        user.setFirstName("Follow");
        user.setLastName("Er");
        user.setPassword("Password1");
        user.setProfilePictureURL("./url/another/folder/picture2.jpeg");
        user.setInterests(interests);

        //em.persist(user);
    }
}
