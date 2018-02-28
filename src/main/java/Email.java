import com.neovisionaries.i18n.CountryCode;

public class Email {


    private String email;
    private boolean isConfirmed;

    public Email(String Email, boolean IsConfirmed) {
        this.email = Email;
        this.isConfirmed = IsConfirmed;
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
}
