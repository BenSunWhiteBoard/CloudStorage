package spring101.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import spring101.cloudstorage.pageObject.*;

import javax.xml.transform.Result;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

    @LocalServerPort
    private int port;

    private WebDriver driver;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        this.driver = new ChromeDriver();
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }

    @Test
    public void getLoginPage() {
        driver.get("http://localhost:" + this.port + "/login");
        Assertions.assertEquals("Login", driver.getTitle());
    }

    @Test
    public void unauthorizedUser() {
        driver.get("http://localhost:" + this.port + "/signup");
        Assertions.assertEquals("Sign Up", driver.getTitle());

        driver.get("http://localhost:" + this.port + "/home");
        Assertions.assertNotEquals("Home", driver.getTitle());
        Assertions.assertEquals("Login", driver.getTitle());
    }

    @Test
    @Order(1)
    public void authorizedUser() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        //sign up
        driver.get("http://localhost:" + this.port + "/signup");
        SignupPage signupPage = new SignupPage(driver);
        String firstname = "ben";
        String lastname = "sun";
        String username = "SunBen";
        String password = "#password#";
        boolean signupSuccess = signupPage.signUpForm(firstname, lastname, username, password);
        Assertions.assertEquals(true, signupSuccess);

        //login
        //driver.get("http://localhost:" + this.port + "/login");
        wait.until(webDriver -> driver.findElement(By.name("loginButton")));
        Thread.sleep(2000);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);
        wait.until(webDriver -> driver.findElement(By.name("logoutButton")));
        Thread.sleep(2000);
        Assertions.assertEquals("Home", driver.getTitle());
        //logout
        //driver.get("http://localhost:" + this.port + "/home");
        HomePage homePage = new HomePage(driver);
        homePage.toLogout();
        Assertions.assertEquals(true, loginPage.isLogout());
        unauthorizedUser();
    }

    @Test
    @Order(2)
    public void noteOperations() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        //login existing user
        driver.get("http://localhost:" + this.port + "/login");
        String username = "SunBen";
        String password = "#password#";
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);

        //add Note
        wait.until(webDriver -> driver.findElement(By.name("logoutButton")));
        HomePage homePage = new HomePage(driver);
        homePage.toNoteTab();
        NoteTabPage noteTabPage = new NoteTabPage(driver);
        String noteTitle = "Interview";
        String noteDescription = "GE interview on March 12th 2020";
        noteTabPage.addNote(noteTitle, noteDescription);
        ResultPage resultPage = new ResultPage(driver);
        resultPage.successGoBack();
        Thread.sleep(2000);
        homePage.toNoteTab();
        Thread.sleep(2000);
        Assertions.assertEquals("Home", driver.getTitle());
        Assertions.assertEquals(noteTitle, driver.findElement(By.name("displayedNoteTitle1")).getText());
        Assertions.assertEquals(noteDescription, driver.findElement(By.name("displayedNoteDescription1")).getText());
        //edit Note
        noteTitle = "interview round 2";
        noteDescription = "GE interviewed by HM";
        noteTabPage.editNote(noteTitle, noteDescription);
        resultPage.successGoBack();
        homePage.toNoteTab();
        Thread.sleep(2000);
        Assertions.assertEquals("Home", driver.getTitle());
        Assertions.assertEquals(noteTitle, driver.findElement(By.name("displayedNoteTitle1")).getText());
        Assertions.assertEquals(noteDescription, driver.findElement(By.name("displayedNoteDescription1")).getText());
        //delete Note
        noteTabPage.deleteNote();
        Thread.sleep(2000);
        resultPage.successGoBack();
        homePage.toNoteTab();
        Thread.sleep(2000);
        Assertions.assertEquals("Home", driver.getTitle());
        try {
            driver.findElement(By.name("displayedNoteTitle1"));
        } catch (Exception e) {
            Assertions.assertEquals(org.openqa.selenium.NoSuchElementException.class, e.getClass());
        }
        try {
            driver.findElement(By.name("displayedNoteDescription1"));
        } catch (Exception e) {
            Assertions.assertEquals(org.openqa.selenium.NoSuchElementException.class, e.getClass());
        }
    }

    @Test
    @Order(3)
    public void credentialOperations() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        //login existing user
        driver.get("http://localhost:" + this.port + "/login");
        String username = "SunBen";
        String password = "#password#";
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);

        //add Credentials
        wait.until(webDriver -> driver.findElement(By.name("logoutButton")));
        HomePage homePage = new HomePage(driver);
        homePage.toCredentialTab();
        CredentialTabPage credentialTabPage = new CredentialTabPage(driver);
        String url = "localhost:8080";
        credentialTabPage.addCredential(url,username,password);
        ResultPage resultPage = new ResultPage(driver);
        resultPage.successGoBack();
        homePage.toCredentialTab();
        Thread.sleep(2000);
        Assertions.assertEquals("Home", driver.getTitle());
        Assertions.assertEquals(url, driver.findElement(By.name("displayedCredentialUrl1")).getText());
        Assertions.assertEquals(username, driver.findElement(By.name("displayedCredentialUsername1")).getText());
        //password are hashed value
        Assertions.assertNotEquals(password, driver.findElement(By.name("displayedCredentialPassword1")).getText());
        //edit Note
        url = "www.google.com";
        username = "Dark Souls";
        password = "hollow by the fate";
        credentialTabPage.editCredential(url,username,password);
        resultPage.successGoBack();
        homePage.toCredentialTab();
        Thread.sleep(2000);
        Assertions.assertEquals("Home", driver.getTitle());
        Assertions.assertEquals(url, driver.findElement(By.name("displayedCredentialUrl1")).getText());
        Assertions.assertEquals(username, driver.findElement(By.name("displayedCredentialUsername1")).getText());
        //password are hashed value
        Assertions.assertNotEquals(password, driver.findElement(By.name("displayedCredentialPassword1")).getText());
        //delete Note
        credentialTabPage.deleteCredential();
        Thread.sleep(2000);
        resultPage.successGoBack();
        homePage.toCredentialTab();
        Thread.sleep(2000);
        Assertions.assertEquals("Home", driver.getTitle());
        try {
            driver.findElement(By.name("displayedCredentialUrl1"));
        } catch (Exception e) {
            Assertions.assertEquals(org.openqa.selenium.NoSuchElementException.class, e.getClass());
        }
    }
}
