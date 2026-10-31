package com.softwaretestprecision.framework.driver;

import com.softwaretestprecision.framework.config.Config;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;
import java.util.Map;

/**
 * One WebDriver per thread, so TestNG can run tests in parallel safely.
 * Selenium Manager (Selenium 4.6+) resolves the browser driver binary automatically.
 */
public final class DriverFactory {

    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    /**
     * Chrome's password manager can show a "password found in a data breach" / "save password"
     * dialog after a login form is submitted. Demo passwords like "secret_sauce" trigger it.
     * The dialog is browser UI, so Selenium can't see it, but it swallows every click on the page.
     * These prefs turn it off for test runs.
     */
    private static final Map<String, Object> NO_PASSWORD_POPUPS = Map.of(
            "credentials_enable_service", false,
            "profile.password_manager_enabled", false,
            "profile.password_manager_leak_detection", false);

    private DriverFactory() {}

    public static WebDriver getDriver() {
        WebDriver driver = DRIVER.get();
        if (driver == null) {
            throw new IllegalStateException("Driver not started. Did BaseTest.setUp() run?");
        }
        return driver;
    }

    public static void startDriver() {
        String browser = Config.get("browser").toLowerCase();
        boolean headless = Config.getBool("headless");
        WebDriver driver = switch (browser) {
            case "firefox" -> {
                FirefoxOptions o = new FirefoxOptions();
                if (headless) o.addArguments("-headless");
                yield new FirefoxDriver(o);
            }
            case "edge" -> {
                EdgeOptions o = new EdgeOptions();
                if (headless) o.addArguments("--headless=new");
                o.setExperimentalOption("prefs", NO_PASSWORD_POPUPS);
                yield new EdgeDriver(o);
            }
            default -> {
                ChromeOptions o = new ChromeOptions();
                if (headless) o.addArguments("--headless=new");
                o.addArguments("--window-size=1366,900", "--no-sandbox", "--disable-dev-shm-usage");
                o.setExperimentalOption("prefs", NO_PASSWORD_POPUPS);
                yield new ChromeDriver(o);
            }
        };
        driver.manage().timeouts().implicitlyWait(Duration.ZERO); // explicit waits only
        DRIVER.set(driver);
    }

    public static void quitDriver() {
        WebDriver driver = DRIVER.get();
        if (driver != null) {
            driver.quit();
            DRIVER.remove();
        }
    }
}
