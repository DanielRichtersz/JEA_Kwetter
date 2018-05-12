package com.github.danielrichtersz.services;

import com.github.danielrichtersz.mock.MockDatabase;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Startup
@Singleton(mappedName = "MockDatabaseServiceMN")
public class MockDatabaseService {

    private MockDatabase db;

    public MockDatabase getDb() {
        return db;
    }

    @PostConstruct
    public void init() {
        //By calling the static instance of the MockDatabase, the returned instance will always be the same
        db = MockDatabase.getInstance();
    }
}
