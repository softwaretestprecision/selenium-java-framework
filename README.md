# Video 5 — Selenium Java Framework from Scratch: Page Object Model + TestNG

A clean, small Selenium 4 framework in Java 17 with Maven and TestNG, built against https://www.saucedemo.com.

```
code/
├── pom.xml
├── testng.xml                         <- suite, parallel="methods", listener
└── src
    ├── main/java/com/softwaretestprecision/framework
    │   ├── config/Config.java         <- config.properties + -D overrides
    │   ├── driver/DriverFactory.java  <- ThreadLocal WebDriver, chrome/firefox/edge
    │   └── pages/                     <- BasePage + LoginPage, InventoryPage, CartPage, CheckoutPage
    └── test
        ├── java/com/softwaretestprecision/tests
        │   ├── base/BaseTest.java     <- start/quit driver per test
        │   ├── listeners/ScreenshotOnFailureListener.java
        │   ├── LoginTest.java
        │   ├── InventoryTest.java
        │   └── CheckoutTest.java
        └── resources/config.properties
```

## Requirements
- Java 17+ (`java -version`)
- Maven 3.9+ (`mvn -v`)
- Google Chrome (Firefox/Edge optional). Selenium Manager downloads the matching driver automatically.

## Run
```bash
mvn test                                  # all tests, headless Chrome, 3 threads
mvn test -Dheadless=false                 # watch it
mvn test -Dbrowser=firefox                # another browser
mvn test -Dgroups=smoke                   # only @Test(groups = "smoke")
```
Results: `target/surefire-reports/index.html`. Failure screenshots: `target/screenshots/`.

## Troubleshooting
**Clicks after login silently do nothing / timeouts on the next page.** Chrome's password manager can open a "password found in a data breach" dialog after a login with a demo password. Selenium can't see it, but it blocks clicks. `DriverFactory` disables it with the prefs `credentials_enable_service`, `profile.password_manager_enabled`, and `profile.password_manager_leak_detection` set to `false`. Keep those if you copy the factory into your own project.

## Design choices (explained in the video)
- **Page methods return the next page** (`loginAs()` returns `InventoryPage`), so tests read like a user journey.
- **Assertions live in tests**, not in page objects.
- **Explicit waits only**, implicit wait set to zero.
- **ThreadLocal driver** so `parallel="methods"` is safe.

Adding Allure reporting and GitHub Actions is covered in video 7. Ready-made Selenium starter framework: https://swtestprecision.gumroad.com

---

## Software Test Precision
- 📺 YouTube: https://www.youtube.com/@softwaretestprecision
- 🧰 Templates & starter frameworks: https://swtestprecision.gumroad.com

License: MIT (see `LICENSE`).
