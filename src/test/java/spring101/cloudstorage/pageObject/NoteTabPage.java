package spring101.cloudstorage.pageObject;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.WeakHashMap;

public class NoteTabPage {

    private final JavascriptExecutor js;

    @FindBy(id = "addNoteButton")
    private WebElement addNoteButton;

    @FindBy(id = "note-title")
    private WebElement noteTitleForm;

    @FindBy(id = "note-description")
    private WebElement noteDescriptionForm;

    @FindBy(name = "saveNoteChanges")
    private WebElement noteSaveChangeButton;

    @FindBy(name = "editLink1")
    private WebElement editButton;

    @FindBy(name = "deleteLink1")
    private WebElement deleteButton;

    public  NoteTabPage(WebDriver webDriver){
        PageFactory.initElements(webDriver,this);
        js = (JavascriptExecutor) webDriver;
    }

    public void addNote(String noteTitle, String noteDescription){
        js.executeScript("arguments[0].click();", addNoteButton);
        js.executeScript("arguments[0].value='" + noteTitle + "';", noteTitleForm);
        js.executeScript("arguments[0].value='" + noteDescription + "';", noteDescriptionForm);
        js.executeScript("arguments[0].click();", noteSaveChangeButton);
    }

    public void editNote(String noteTitle, String noteDescription){
        js.executeScript("arguments[0].click();", editButton);
        js.executeScript("arguments[0].value='" + noteTitle + "';", noteTitleForm);
        js.executeScript("arguments[0].value='" + noteDescription + "';", noteDescriptionForm);
        js.executeScript("arguments[0].click();", noteSaveChangeButton);
    }

    public void deleteNote(){
        js.executeScript("arguments[0].click();", deleteButton);
    }

}
