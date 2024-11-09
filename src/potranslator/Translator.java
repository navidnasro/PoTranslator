package potranslator;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author LENOVO
 */
public class Translator {
    
    //translation input box
    private WebElement inputBox;
    ////translation output box
    //private WebElement outputBox;
    
    private WebDriverWait wait;
    private WebDriver drv ;
    /**
     * Constructor
     * 
     * @param driver 
     */
    public Translator(WebDriver driver)
    {
        //instansiating input
        inputBox = driver.findElement(By.xpath("//textarea[@aria-label=\"Source text\"]"));
        ////instansiating output
        //outputBox = driver.findElement(By.xpath("//div[@aria-live=\"polite\"]/div/div[1]"));
        wait = new WebDriverWait(driver,Duration.ofSeconds(30));
        drv = driver;
    }
    
    /**
     * Translation operation
     * 
     * @param text
     * @return String
     */
    public String translate(String text)
    {
        //translated text
        String translation = "";
        
        try
        {
            //sending text to be translated
            inputBox.sendKeys(text);
            
//            inputBox.sendKeys(Keys.ENTER);
            //waiting
            TimeUnit.SECONDS.sleep(5);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@aria-live=\"polite\"]/div/div[1]")));
            translation = drv.findElement(By.xpath("//div[@aria-live=\"polite\"]/div/div[1]")).getText();
            
            inputBox.clear();
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return translation;
    }
    
}
