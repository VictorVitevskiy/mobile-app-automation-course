import org.junit.Assert;
import org.junit.Test;

public class MainClassTest {

    @Test
    public void testGetClassNumber() {

        Assert.assertTrue(
                "Return value by getClassNumber form MainClass <= 45",
                45 < MainClass.getClassNumber()
        );
    }
}
