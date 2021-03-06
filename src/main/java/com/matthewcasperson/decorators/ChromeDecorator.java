package com.matthewcasperson.decorators;

import com.matthewcasperson.AutomatedBrowser;
import com.matthewcasperson.decoratorbase.AutomatedBrowserBase;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ChromeDecorator extends AutomatedBrowserBase {
    final boolean headless;
    File userData;

    public ChromeDecorator(final AutomatedBrowser automatedBrowser) {
        super(automatedBrowser);
        this.headless = false;
        try {
            this.userData = Files.createTempDirectory("user-data").toFile();
        } catch (IOException e) {
            this.userData = null;
        }
    }

    public ChromeDecorator(final boolean headless, final AutomatedBrowser automatedBrowser) {
        super(automatedBrowser);
        this.headless = headless;
    }

    @Override
    public void init() {
        final ChromeOptions options = new ChromeOptions();
        options.setHeadless(headless);
        // https://bugs.chromium.org/p/chromedriver/issues/detail?id=795
        // A random user-data dir can fix issues with multiple tests opening and closing Chrome
        if (userData != null)
            options.addArguments("--user-data-dir=" + userData.getAbsolutePath());
        options.merge(getDesiredCapabilities());
        final WebDriver webDriver = new ChromeDriver(options);
        getAutomatedBrowser().setWebDriver(webDriver);
        getAutomatedBrowser().init();
    }

    @Override
    public void destroy() {
        getAutomatedBrowser().destroy();
        FileUtils.deleteQuietly(this.userData);
    }
}