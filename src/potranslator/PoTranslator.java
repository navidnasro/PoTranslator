package potranslator;

import org.openqa.selenium.WebDriver;  
import org.openqa.selenium.chrome.ChromeDriver;  

/**
 *
 * @author LENOVO
 */
public class PoTranslator {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        System.setProperty("webdriver.chrome.driver", "resources/chromedriver.exe");
        
        WebDriver driver = new ChromeDriver();  
        
        driver.navigate().to("https://translate.google.com/?tl=fa");
        
        FileHandler handler = new FileHandler(driver);
    }
    
}
