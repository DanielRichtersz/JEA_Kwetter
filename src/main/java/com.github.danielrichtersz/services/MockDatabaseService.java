package com.github.danielrichtersz.services;

import com.github.danielrichtersz.mock.MockDatabase;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class MockDatabaseService {

    @Inject
    private MockDatabase db;

    public MockDatabase getDb() {
        return db;
    }
}
