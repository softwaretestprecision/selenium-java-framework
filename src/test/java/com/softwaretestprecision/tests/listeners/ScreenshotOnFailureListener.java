package com.softwaretestprecision.tests.listeners;

import com.softwaretestprecision.framework.driver.DriverFactory;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/** Saves target/screenshots/<Class>.<method>.png whenever a test fails. */
public class ScreenshotOnFailureListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        try {
            var driver = DriverFactory.getDriver();
            Path src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE).toPath();
            Path dir = Path.of("target", "screenshots");
            Files.createDirectories(dir);
            String name = result.getTestClass().getRealClass().getSimpleName() + "." + result.getName() + ".png";
            Files.copy(src, dir.resolve(name), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Screenshot saved: " + dir.resolve(name));
        } catch (IllegalStateException | IOException e) {
            System.out.println("Could not save screenshot: " + e.getMessage());
        }
    }
}
