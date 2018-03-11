import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PhonenumberTest {

    Phonenumber phonenumber;

    @Before
    public void InitializeTestData() {
        phonenumber = new Phonenumber();
        phonenumber.setCountry(Country.EUROPE);
        phonenumber.setNumber("0621374675");
        phonenumber.setPrefix("+31");
    }

    //region GetterTests
    @Test
    public void GetCountryTest() {
        Assert.assertEquals(Country.EUROPE, phonenumber.getCountry());
    }

    @Test
    public void GetNumberTest() {
        Assert.assertEquals("0621374675", phonenumber.getNumber());
    }

    @Test
    public void GetPrefixTest() {
        Assert.assertEquals("+31", phonenumber.getPrefix());
    }
    //endregion
    //region SetterTests
    @Test
    public void SetCountryTest() {
        phonenumber.setNumber("0612345678");
        Assert.assertEquals("0612345678", phonenumber.getNumber());
    }

    @Test
    public void SetNumberTest() {
        phonenumber.setCountry(Country.AFRIKA);
        Assert.assertEquals(Country.AFRIKA, phonenumber.getCountry());
    }

    @Test
    public void SetPrefixTest() {
        phonenumber.setPrefix("+27");
        Assert.assertEquals("+27", phonenumber.getPrefix());
    }
    //endregion
}
