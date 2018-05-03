package entity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class EmployeeTest {

    @PersistenceContext
    EntityManager em;

    Employee empl;

    @Before
    public void setUp() throws Exception {
        empl = new Employee();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void setName() {
        empl.setName("Edwin");
        em.persist(empl);
    }
}