package spring101.cloudstorage.pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ResultPage {

    @FindBy(name = "successBackLink")
    private WebElement successBackLink;

    @FindBy(name = "errorBackLink")
    private WebElement errorBackLink;

    public ResultPage(WebDriver webDriver) {
        PageFactory.initElements(webDriver,this);
    }

    public void successGoBack(){
        successBackLink.click();
    }

    public void errorGoBack(){
        errorBackLink.click();
    }
}
