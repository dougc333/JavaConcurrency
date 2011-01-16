import org.junit.Assert;
import org.junit.Test;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

public class simpleFormTest {
    // what is this server for?
    static private String appServerURL = "http://vcb3:8080/";

    @Test
    public void testSimple() throws Exception {

	Selenium selBrowser = new DefaultSelenium("localhost", 4444,
		"*firefox", appServerURL);
	selBrowser.start();
	// optional to make it visible/slower:
	// selenium.setSpeed("500");
	selBrowser.open("/TEST_WebAppTestSelenium/");
	selBrowser.type("fullname", "bla1");
	selBrowser.type("age", "99");
	selBrowser.click("//input[@value='Press me']");
	// optional for slower pages (?):
	// selBrowser.waitForPageToLoad("3000");
	Assert.assertTrue(selBrowser.isTextPresent("bla1, 99"));
	selBrowser.stop();

    }
}
