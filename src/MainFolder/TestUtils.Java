import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class TestUtils {
    CustomMath m;

    @BeforeEach
    public void setUp(){
        m = new checkNonBlocking();
    }
    @AfterEach
    public void tearDown(){
        m = null;
    }
    @Test
    public void test(){
        int result = m.addition(1,2);
        assertEquals(result,3);
    }
}
