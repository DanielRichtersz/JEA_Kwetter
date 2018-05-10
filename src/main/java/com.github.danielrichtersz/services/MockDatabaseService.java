package com.github.danielrichtersz.services;

import com.github.danielrichtersz.mock.MockDatabase;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton
@Startup
public class MockDatabaseService {

    private MockDatabase db;

    public MockDatabase getDb() {
        return db;
    }

    @PostConstruct
    public void init() {
        db = MockDatabase.getInstance();
    }
}
