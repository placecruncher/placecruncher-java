package com.placecruncher.server.controller;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.placecruncher.server.application.data.SecurityTestData;

public class SourceViewControllerIT extends ViewTestCase {
    private final Logger log = Logger.getLogger(getClass());

    @Test
    public void listSources() throws Exception {
        String username = SecurityTestData.TEST_USERNAME;
        String password = SecurityTestData.TEST_PASSWORD;

//        String username = "admin";
//        String password = "secret";

        HtmlPage page = navigateToSecurePage("site/sources/list.html", username, password);


        log.debug(page.asXml());
    }


}
