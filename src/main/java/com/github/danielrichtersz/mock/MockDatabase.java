package mock;

import entity.Email;
import entity.Tweet;
import entity.TweetType;
import entity.User;

import javax.annotation.PostConstruct;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Singleton
public class MockDatabase {

    private static MockDatabase db;

    List<Tweet> tweetList = new ArrayList<>();
    List<User> userList = new ArrayList<>();
    List<Email> emailList = new ArrayList<>();

    @PostConstruct
    private void initialize() {
        User jan;
        User henk;
        User daniel;
        Tweet tweet;
        Tweet tweet2;
        Tweet tweet3;
        Email email;

        email = new Email();
        email.setEmail("test@mail.com");
        email.setConfirmed(false);
        email.setId(this.emailList.size() + 1);
        this.emailList.add(email);

        Email email1 = new Email();
        email1.setEmail("test1@mail.com");
        email1.setConfirmed(true);
        email1.setId(this.emailList.size() + 1);
        this.emailList.add(email1);

        Email email2 = new Email();
        email2.setEmail("test2@mail.com");
        email2.setConfirmed(false);
        email2.setId(this.emailList.size() + 1);
        this.emailList.add(email2);

        List<String> interests = new ArrayList<>();
        interests.add("Food");
        interests.add("Something interesting");

        jan = new User();
        jan.setEmail(email1);
        jan.setPhonenumber("0612345678");
        jan.setFirstName("Jan");
        jan.setLastName("Boezel");
        jan.setPassword("Password1");
        jan.setProfilePictureURL("./url/another/folder/picture2.jpeg");
        jan.setInterests(interests);

        henk = new User();
        henk.setEmail(email2);
        henk.setPhonenumber("0687654321");
        henk.setFirstName("Henk");
        henk.setLastName("De Wit");
        henk.setPassword("Password2");
        henk.setProfilePictureURL("./url/another/folder/picture3.jpeg");
        henk.setInterests(interests);

        List<User> followers = new ArrayList<>();
        jan.setId((long) userList.size() + 1);
        followers.add(jan);
        userList.add(jan);

        List<User> followings = new ArrayList<>();
        henk.setId((long) userList.size() + 1);
        followings.add(henk);
        userList.add(henk);

        daniel = new User();
        daniel.setEmail(email);
        daniel.setPhonenumber("0621374675");
        daniel.setFirstName("Daniel");
        daniel.setLastName("Richtersz");
        daniel.setPassword("Password");
        daniel.setProfilePictureURL("./url/another/folder/picture.jpeg");
        daniel.setInterests(interests);
        daniel.setFollowers(followers);
        daniel.setFollowing(followings);
        daniel.setId((long)userList.size() + 1);
        userList.add(daniel);

        // Tweets
        tweet = new Tweet();
        tweet.setMessage("First tweet");
        tweet.setOwner(daniel);
        tweet.setDateCreated(new Date());
        tweet.setType(TweetType.InititalTweet);

        tweet2 = new Tweet();
        tweet2.setMessage('"' + tweet.getMessage() + '"' + " - Such nice tweet.");
        tweet2.setOwner(henk);
        tweet2.setDateCreated(new Date());
        tweet2.setType(TweetType.ReTweet);


        tweet3 = new Tweet();
        tweet3.setMessage("I am following " + daniel.getFirstName() + " " + daniel.getLastName() + "! ");
        tweet3.setOwner(jan);
        tweet3.setDateCreated(new Date());
        tweet3.setType(TweetType.InititalTweet);

        tweet.setId((long)tweetList.size() + 1);
        tweet.addLike(henk.getId());
        tweetList.add(tweet);

        tweet2.setId((long)tweetList.size() + 1);
        tweet2.addLike(jan.getId());
        tweet2.addLike(daniel.getId());
        tweetList.add(tweet2);

        tweet3.setId((long)tweetList.size() + 1);
        tweet2.addLike(henk.getId());
        tweetList.add(tweet3);
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
