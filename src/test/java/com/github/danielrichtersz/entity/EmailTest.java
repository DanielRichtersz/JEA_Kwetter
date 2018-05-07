package com.github.danielrichtersz.entity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EmailTest {

    private Email email;

    @Before
    public void InitializeTestData() {
        email = new Email();
        email.setEmail("test@mail.com");
        email.setConfirmed(false);
    }

    //region GetterTests
    @Test public void GetEmailTest() {
        Assert.assertEquals("test@mail.com", email.getEmail());
    }

    @Test public void GetIsConfirmedTest() {
        Assert.assertEquals(false, email.isConfirmed());
    }
    //endregion
    //region SetterTests
    @Test public void SetEmailTest() {
        email.setEmail("changed@mail.com");
        Assert.assertEquals("changed@mail.com", email.getEmail());
    }

    @Test public void SetIsConfirmedTest() {
        email.setConfirmed(true);
        Assert.assertEquals(true, email.isConfirmed());
    }
    //endregion
}
