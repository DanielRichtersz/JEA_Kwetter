import entity.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserTest {

    User user;
    User follower;
    User following;
    Email email;
    String phonenumber;
    Tweet tweet;
    Tweet tweet2;

    @Before
    public void InitializeTestData() {
        email = new Email();
        email.setEmail("test@mail.com");
        email.setConfirmed(false);

        phonenumber = "0621374675";

        List<String> interests = new ArrayList<>();
        interests.add("Food");
        interests.add("Something interesting");

        follower = new User();
        follower.setEmail(email);
        follower.setPhonenumber(phonenumber);
        follower.setFirstName("Follow");
        follower.setLastName("Er");
        follower.setPassword("Password1");
        follower.setProfilePictureURL("./url/another/folder/picture2.jpeg");
        follower.setInterests(interests);

        following = new User();
        following.setEmail(email);
        following.setPhonenumber(phonenumber);
        following.setFirstName("Follow");
        following.setLastName("Ing");
        following.setPassword("Password2");
        following.setProfilePictureURL("./url/another/folder/picture3.jpeg");
        following.setInterests(interests);

        List<User> followers = new ArrayList<>();
        followers.add(follower);

        List<User> followings = new ArrayList<>();
        followings.add(following);

        user = new User();
        user.setEmail(email);
        user.setPhonenumber(phonenumber);
        user.setFirstName("Dan");
        user.setLastName("Iel");
        user.setPassword("Password");
        user.setProfilePictureURL("./url/another/folder/picture.jpeg");
        user.setInterests(interests);
        user.setFollowers(followers);
        user.setFollowing(followings);

        tweet = new Tweet();
        tweet.setMessage("First tweet");
        tweet.setOwner(user);
        tweet.setDateCreated(new Date());
        tweet.setType(TweetType.InititalTweet);

        tweet2 = new Tweet();
        tweet.setMessage('"' + tweet.getMessage() + '"' + " - Such nice tweet");
        tweet.setOwner(following);
        tweet.setDateCreated(new Date());
        tweet.setType(TweetType.ReTweet);
    }

    //region GetterTests
    @Test
    public void GetEmailTest() {

    }

    @Test
    public void GetPasswordTest() {

    }

    @Test
    public void GetFirstNameTest() {

    }

    @Test
    public void GetLastNameTest() {

    }

    @Test
    public void GetUsernameTest() {

    }

    @Test
    public void GetInterestsTest() {

    }

    @Test
    public void GetProfilePictureURLTest() {

    }

    @Test
    public void GetFollowersTest() {

    }

    @Test
    public void GetFollowingTest() {

    }
    //endregion
    //region SetterTests
    @Test
    public void SetEmailTest() {

    }

    @Test
    public void SetPasswordTest() {

    }

    @Test
    public void SetFirstNameTest() {

    }

    @Test
    public void SetLastNameTest() {

    }

    @Test
    public void SetUsernameTest() {

    }

    @Test
    public void SetInterestsTest() {

    }

    @Test
    public void SetProfilePictureURLTest() {

    }

    @Test
    public void SetFollowersTest() {

    }

    @Test
    public void SetFollowingTest() {

    }
    //endregion
}
