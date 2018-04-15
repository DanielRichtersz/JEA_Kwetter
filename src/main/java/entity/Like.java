package entity;

import java.io.Serializable;

public class Like implements Serializable {

    private User owner;

    public Like() {

    }

    public User getOwner() {
        return owner;
    }
}
