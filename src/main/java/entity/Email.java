package entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "EMAIL")
public class Email implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;


    @Column(name="EMAIL")
    private String email;

    @Column(name="IS_CONFIRMED")
    private boolean isConfirmed;

    public Email() {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }

    public void setConfirmed(boolean confirmed) {
        isConfirmed = confirmed;
    }

    public Long getId() {
        return id;
    }
}