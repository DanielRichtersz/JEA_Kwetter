package mock;

import entity.Email;
import entity.Tweet;
import entity.TweetType;
import entity.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MockDatabase {

    List<Tweet> tweetList = new ArrayList<>();
    List<User> userList = new ArrayList<>();
    List<Email> emailList = new ArrayList<>();

    public MockDatabase(){
        this.initialize();
    }

    private void initialize() {
        User follower;
        User following;
        User user;
        Tweet tweet;
        Tweet tweet2;
        Email email;

        email = new Email();
        email.setEmail("test@mail.com");
        email.setConfirmed(false);
        Email email1 = new Email();
        email1.setEmail("test1@email.com");
        email1.setConfirmed(true);
        Email email2 = new Email();
        email2.setEmail("test2@email.com");
        email2.setConfirmed(false);

        List<String> interests = new ArrayList<>();
        interests.add("Food");
        interests.add("Something interesting");

        follower = new User();
        follower.setEmail(email1);
        follower.setPhonenumber("0612345678");
        follower.setFirstName("Follow");
        follower.setLastName("Er");
        follower.setPassword("Password1");
        follower.setProfilePictureURL("./url/another/folder/picture2.jpeg");
        follower.setInterests(interests);

        following = new User();
        following.setEmail(email2);
        following.setPhonenumber("0687654321");
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
        user.setPhonenumber("0621374675");
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
        tweet.setPostDate(new Date());
        tweet.setType(TweetType.InititalTweet);

        tweet2 = new Tweet();
        tweet2.setMessage('"' + tweet.getMessage() + '"' + " - Such nice tweet");
        tweet2.setOwner(following);
        tweet2.setPostDate(new Date());
        tweet2.setType(TweetType.ReTweet);

        userList.add(user);
        userList.add(follower);
        userList.add(following);
        tweetList.add(tweet);
        tweetList.add(tweet2);
        emailList.add(email);
    }

    public List<Tweet> getTweetList() {
        return tweetList;
    }

    public List<User> getUserList() {
        return userList;
    }

    public List<Email> getEmailList() {
        return emailList;
    }


}
