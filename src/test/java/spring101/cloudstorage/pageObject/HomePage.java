package spring101.cloudstorage.pageObject;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

    private final JavascriptExecutor js;

    @FindBy(id = "loginUserDiv")
    private WebElement curUser;

    @FindBy(name = "logoutButton")
    private WebElement logoutButton;

    @FindBy(id = "nav-files-tab")
    private WebElement filesTab;

    @FindBy(id = "nav-notes-tab")
    private WebElement notesTab;

    @FindBy(id = "nav-credentials-tab")
    private WebElement credentialsTab;

    public HomePage(WebDriver webDriver){
        PageFactory.initElements(webDriver,this);
        js = (JavascriptExecutor) webDriver;
    }

    public void toLogout(){
        logoutButton.click();
    }

    public String loginUser(){
        return this.curUser.getText();
    }

    public void toNoteTab(){
        js.executeScript("arguments[0].click();", notesTab);
    }

    public void toCredentialTab(){
        js.executeScript("arguments[0].click();", credentialsTab);
    }
}
