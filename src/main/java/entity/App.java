package entity;

import mock.MockDatabase;

public class App {

    public static MockDatabase db;

    public void init() {
        db = new MockDatabase();
    }

}