package com.placecruncher.server.controller;

import java.net.URL;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

public class ViewTestCase extends IntegrationTestCase {
    private final Logger log = Logger.getLogger(getClass());

    private WebClient client;
    private HtmlPage page;

    public WebClient getClient() {
        return client;
    }

    /**
     * Get the page under test.
     * @return The page under test.
     */
    public HtmlPage getPage() {
        return page;
    }

    /**
     * Override this method to navigate to the page under test at the beginning of
     * a test case.  The page under test can be retrieved using the getPage method.
     * @param client The web client.
     * @return The web page under test
     * @throws Exception on error
     */
    public HtmlPage navigateToPage(WebClient client) throws Exception {
        return null;
    }

    /**
     * Override this method to perform any initialization required before navigating
     * to the page under test.
     */
    public void onSetUpBeforePage() throws Exception {
    }

    /**
     * Override this method to provide any initialization required after navigating to
     * the page under test.
     */
    public void onSetUpInPage() throws Exception {
    }

    /**
     * Override this method to provide any teardown required after the test is complete.
     */
    public void onTearDownInPage() throws Exception {
    }

    @Before
    public final void setUp() throws Exception {
        // Initialize the web client
        client = createWebClient();

        onSetUpBeforePage();

        // Navigate to the page under test
        this.page = navigateToPage(client);

        // Page specific setup
        onSetUpInPage();
    }

    @After
    public final void tearDown() throws Exception {
        onTearDownInPage();
    }

    protected WebClient createWebClient() {
        WebClient webClient = new WebClient(BrowserVersion.FIREFOX_3_6);
        webClient.setJavaScriptEnabled(true);
        webClient.setRedirectEnabled(true);
        return webClient;
    }

    protected HtmlPage navigateToSecurePage(String url, String username, String password) throws Exception {
        URL loginUrl = new URL(getBaseUrl() + "/spring_security_login");
        URL secureUrl = new URL(getBaseUrl() + url);
        HtmlPage page = (HtmlPage)client.getPage(secureUrl);
        log.debug("Requested page " + secureUrl.toString() + ", actual page is " + page.getUrl().toString());
        if(page.getUrl().toString().startsWith(loginUrl.toString())) {
            log.info("Access to " + url + " requires authentication, logging in as " + username);
            HtmlForm loginForm = page.getFormByName("f");
            HtmlTextInput userNameInput = loginForm.getInputByName("j_username");
            HtmlPasswordInput passwordInput = loginForm.getInputByName("j_password");
            HtmlSubmitInput submit = loginForm.getInputByName("submit");

            userNameInput.setValueAttribute(username);
            passwordInput.setValueAttribute(password);
            page = submit.click();
        }
        Assert.assertEquals(secureUrl, page.getUrl());
        return page;
    }


}
