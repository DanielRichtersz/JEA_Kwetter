package entity;

import java.io.Serializable;

public class Email implements Serializable {

    private Long id;

    private String email;

    private boolean isConfirmed;

    public Email() {

    }

    public Email(String emailAdress, boolean isConfirmed) {
        this.email = emailAdress;
        this.isConfirmed = isConfirmed;
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