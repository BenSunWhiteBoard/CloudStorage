package spring101.cloudstorage.pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.security.SecureRandom;

public class LoginPage {

    @FindBy(id = "error-msg")
    private WebElement errorMsg;

    @FindBy(id="logout-msg")
    private WebElement logoutMsg;

    @FindBy(id = "inputUsername")
    private WebElement inputUsername;

    @FindBy(id = "inputPassword")
    private WebElement inputPassword;

    @FindBy(name = "loginButton")
    private WebElement loginButton;

    @FindBy(id = "signupLink")
    private WebElement signupLink;

    public LoginPage(WebDriver webDriver){
        PageFactory.initElements(webDriver,this);
    }

    public void login(String username, String password){
        inputUsername.sendKeys(username);
        inputPassword.sendKeys(password);
        loginButton.click();
    }

    public void toSignup(){
        signupLink.click();
    }

    public boolean isLogout(){
        return logoutMsg!=null;
    }

    public boolean isError(){
        return errorMsg!=null;
    }
}
