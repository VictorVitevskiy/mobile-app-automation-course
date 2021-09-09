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
}
