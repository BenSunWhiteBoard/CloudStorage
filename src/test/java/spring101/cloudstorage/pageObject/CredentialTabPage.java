package spring101.cloudstorage.pageObject;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CredentialTabPage {

    private final JavascriptExecutor js;

    @FindBy(name = "addCredentialButton")
    private WebElement addCredentialButton;

    @FindBy(id = "credential-url")
    private WebElement inputCredentialUrl;

    @FindBy(id = "credential-username")
    private WebElement inputCredentialUsername;

    @FindBy(id = "credential-password")
    private WebElement inputCredentialPassword;

    @FindBy(name = "saveCredentialChanges")
    private WebElement credentialSaveChangeButton;

    @FindBy(name = "credentialEditLink1")
    private WebElement editButton;

    @FindBy(name = "credentialDeleteLink1")
    private WebElement deleteButton;

    public CredentialTabPage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
        js = (JavascriptExecutor) webDriver;
    }

    public void addCredential(String url, String username, String password) {
        js.executeScript("arguments[0].click();", addCredentialButton);
        js.executeScript("arguments[0].value='" + url + "';", inputCredentialUrl);
        js.executeScript("arguments[0].value='" + username + "';", inputCredentialUsername);
        js.executeScript("arguments[0].value='" + password + "';", inputCredentialPassword);
        js.executeScript("arguments[0].click();", credentialSaveChangeButton);
    }

    public void editCredential(String url, String username, String password) {
        js.executeScript("arguments[0].click();", editButton);
        js.executeScript("arguments[0].value='" + url + "';", inputCredentialUrl);
        js.executeScript("arguments[0].value='" + username + "';", inputCredentialUsername);
        js.executeScript("arguments[0].value='" + password + "';", inputCredentialPassword);
        js.executeScript("arguments[0].click();", credentialSaveChangeButton);
    }

    public void deleteCredential(){
        js.executeScript("arguments[0].click();", deleteButton);
    }
}
