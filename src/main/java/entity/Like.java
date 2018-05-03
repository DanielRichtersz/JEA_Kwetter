package entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "LIKE")
public class Like implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;


    //TODO CONFIRM VALIDITY?

    @ManyToOne
    @JoinColumn(name="USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name="TWEET_ID")
    private Tweet tweet;

    public Like() {

    }
}
