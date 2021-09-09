import org.junit.Assert;
import org.junit.Test;

public class MainClassTest {

    @Test
    public void testGetClassString() {

        Assert.assertTrue(
                "Return value by getClassString form MainClass does not contain hello or Hello substring",
                MainClass.getClassString().contains("hello") || MainClass.getClassString().contains("Hello")
        );
    }

    @Test
    public void testGetClassNumber() {

        Assert.assertTrue(
                "Return value by getClassNumber form MainClass <= 45",
                45 < MainClass.getClassNumber()
        );
    }

    @Test
    public void testGetLocalNumber() {

        Assert.assertEquals(
                "Return value by getLocalNumber form MainClass is not 14",
                14,
                MainClass.getLocalNumber()
        );
    }
}
