import org.junit.Assert;
import org.junit.Test;

public class MainClassTest {

    @Test
    public void testGetLocalNumber() {

        Assert.assertEquals(
                "Return value by getLocalNumber form MainClass is not 14",
                14,
                MainClass.getLocalNumber()
        );
    }
}
