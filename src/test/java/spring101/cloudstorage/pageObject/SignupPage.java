package spring101.cloudstorage.pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPage {

    @FindBy(name = "loginLink")
    private WebElement loginLink;

    @FindBy(name = "signUpSuccessLink")
    private WebElement signUpSuccessLink;

    @FindBy(id = "inputFirstName")
    private WebElement inputFirstname;

    @FindBy(id = "inputLastName")
    private WebElement inputLastname;

    @FindBy(id = "inputUsername")
    private WebElement inputUsername;

    @FindBy(id = "inputPassword")
    private WebElement inputPassword;

    @FindBy(name = "submitButton")
    private WebElement submitButton;

    public SignupPage(WebDriver webDriver) {
        PageFactory.initElements(webDriver,this);
    }

    public boolean signUpForm(String firstname, String lastname, String username, String password) throws InterruptedException {
        inputFirstname.sendKeys(firstname);
        inputLastname.sendKeys(lastname);
        inputUsername.sendKeys(username);
        inputPassword.sendKeys(password);
        submitButton.click();
        Thread.sleep(1000);
        if(signUpSuccessLink!=null){
            signUpSuccessLink.click();
            return true;
        }else{
            return false;
        }
    }

    public void toLogin(){
        loginLink.click();
    }
}
