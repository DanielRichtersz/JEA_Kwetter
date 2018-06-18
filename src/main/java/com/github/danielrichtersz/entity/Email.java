package entity;

import java.io.Serializable;

public class Email implements Serializable {

    private long id;

    private String email;

    private boolean isConfirmed;

    private String validationCode;

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getValidationCode() {
        return validationCode;
    }

    public void setValidationCode(String validationCode) {
        this.validationCode = validationCode;
    }
}