package es.ucm.fdi.util;

import org.junit.Test;

import es.ucm.fdi.launcher.ExampleMain;

import static org.junit.Assert.fail;

public class MainTest {

   private void runTests(String folderName, boolean expectException) throws Exception {
        try {
			ExampleMain.test("src/test/resources/examples/" + folderName);
            if (expectException) {
                fail("Did not expect to reach this line");
            }
        } catch (Exception e) {
            if ( ! expectException) {
                throw new Exception("When running tests on " + folderName, e);
            }
        }
    }

    @Test
    public void testBasic() throws Exception {
        runTests("basic", false);
    }
    @Test
    public void testAdvanced() throws Exception {
        runTests("advanced", false);
        runTests("advanced/ya funciona", false);

    }
    @Test
    public void testError() throws Exception {
		runTests("err", true);
	}
}
