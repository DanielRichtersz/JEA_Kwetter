package entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity(name = "USER")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @OneToOne
    @JoinColumn(name="EMAIL_ID")
    private Email email;

    @Column(name="PHONENUMBER")
    private String phonenumber;

    //TODO CONFIRM VALIDITY?
    @Column(name="PASSWORD")
    private String password;

    @Column(name="FIRST_NAME")
    private String firstName;

    @Column(name="LAST_NAME")
    private String lastName;

    @Column(name="USERNAME")
    private String username;

    @Column(name="PROFILE_PICTURE")
    private String profilePictureURL;

    //TODO CONFIRM VALIDITY ONETOMANY?
    @ElementCollection
    private List<String> interests;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name="FOLLOWER_USER_ID")
    private List<User> followers;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name="FOLLOWING_USER_ID")
    private List<User> following;

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    @JoinTable(name="LIKES")
    private List<Like> likes;

    //TODO CONFIRM VALIDITY?
    @OneToMany(mappedBy = "owner", cascade = CascadeType.PERSIST)
    @JoinTable(name="TWEETS")
    private List<Tweet> tweets;

    public User() {

    }

    //region Getters & Setters
    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getInterests() {
        return interests;
    }

    public void setInterests(List<String> interests) {
        this.interests = interests;
    }

    public String getProfilePictureURL() {
        return profilePictureURL;
    }

    public void setProfilePictureURL(String profilePictureURL) {
        this.profilePictureURL = profilePictureURL;
    }

    public List<User> getFollowers() {
        return followers;
    }

    public void setFollowers(List<User> followers) {
        this.followers = followers;
    }

    public List<User> getFollowing() {
        return following;
    }

    public void setFollowing(List<User> following) {
        this.following = following;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Like> getLikes() {
        return likes;
    }

    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }
    //endregion
}