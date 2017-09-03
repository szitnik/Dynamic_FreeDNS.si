package si.zitnik.dynamicdns.util;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import org.xbill.DNS.Lookup;
import org.xbill.DNS.SimpleResolver;
import org.xbill.DNS.TextParseException;
import org.xbill.DNS.Type;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.logging.Level;

/**
 * Created by slavkoz on 03/09/2017.
 */
public class FreeDNSManager {
    private static final String FREEDNS_NS = "ns1.freedns.si";
    private static final String FREEDNS_WEBPAGE = "http://freedns.si";
    private static final int SLEEP_TIME = 5000;

    public static String getARecordValue(String domain) throws Exception {
        Lookup lookup = new Lookup(domain, Type.A);
        lookup.setResolver(new SimpleResolver(FREEDNS_NS));
        lookup.run();
        if (lookup.getResult() == Lookup.SUCCESSFUL)
            return lookup.getAnswers()[0].rdataToString().trim();

        throw new Exception("Problem retrieving A record.");
    }

    public static void updateARecord(String domainEditPage, String username, String password, String newIP) throws Exception {
        WebClient wc = new WebClient(BrowserVersion.CHROME);
        java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF);
        wc.getOptions().setThrowExceptionOnScriptError(false);
        wc.getOptions().setThrowExceptionOnFailingStatusCode(false);

        //Login
        HtmlPage page = wc.getPage(FREEDNS_WEBPAGE);
        Thread.sleep(SLEEP_TIME + new Random().nextInt(5000));

        ((HtmlTextInput)page.getElementById("username")).setText(username);
        ((HtmlPasswordInput)page.getElementById("password")).setText(password);
        page = ((HtmlButton)page.getElementByName("login")).click();
        Thread.sleep(SLEEP_TIME + new Random().nextInt(5000));

        //Edit domain
        page = wc.getPage(domainEditPage);
        Thread.sleep(SLEEP_TIME + new Random().nextInt(5000));

        ((HtmlTextInput)page.getElementById("content")).setText(newIP);
        page = ((HtmlSubmitInput)page.getElementByName("save")).click();
        Thread.sleep(SLEEP_TIME + new Random().nextInt(5000));
    }
}
