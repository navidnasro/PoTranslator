package potranslator;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;
import org.openqa.selenium.WebDriver;

/**
 *
 * @author LENOVO
 */
public class FileHandler {
    
    //source file path
    private static final String SOURCE = "resources/tt.txt";
    //target file path
    private static final String TARGET = "resources/tt2.txt";
    //translator engine
    private Translator translator;
    
    /**
     * Constructor
     * 
     * @param driver 
     */
    public FileHandler(WebDriver driver)
    {
        //instantiating translator
        translator = new Translator(driver);
        
        //opening file input/output streams
        try(Scanner scan = new Scanner(Paths.get(SOURCE));
            Formatter writer = new Formatter(new File(TARGET),"UTF_16"))
        {
            //starting the operation
            operation(scan,writer);
        }catch(Exception e){
                e.printStackTrace();
        }
    }
    
    /**
     * Reading , Writing and Translating operations
     * 
     * @param scan
     * @param writer 
     */
    private void operation(Scanner scan , Formatter writer)
    {
        //break the read line into two halves
        String[] tokens = new String[2];
        //while there is line too read
        while(scan.hasNextLine())
        {
            //read a line
            String sign = scan.nextLine();
            //if empty line
            if(sign.isEmpty())
                continue;
            
            //split by white space into two halves
            tokens = sign.split(" ",2);
            //if the line begins with "msgid"
            if(tokens[0].equalsIgnoreCase("msgid"))
            {
                //if the msgid quotation is not empty
                if(tokens[1].length() != 2)
                {
                    //write the read line into the file
                    writer.format("%s\n", sign);
                    //grap the text between quotations
                    String text = tokens[1].replaceAll("\"", "");
                    //translate the text
                    String translation = translator.translate(text);
                    //read the next line
                    sign = scan.nextLine();
                    //split it by white space
                    tokens = sign.split(" ",2);
                    //if the line begins with "msgstr" and the quotation infront of it is not empty
                    if(tokens[0].equalsIgnoreCase("msgstr") && tokens[1].length() == 2)
                    {
                        //write the translation
                        writer.format("%s %s\n", "msgstr ","\""+translation+"\"");
                    }
                }
                else
                {
                    ArrayList<String> texts = new ArrayList();
                    //write to the file
                    writer.format("%s\n", sign);
                    //while there are text to be read
                    while(true)
                    {
                        //read the next line
                        sign = scan.nextLine();         
                        //if it begins with " write the line to the file and continue reading
                        if(sign.charAt(0) == '"')
                        {
                            //store the read text for translation
                            texts.add(sign.replaceAll("\"", ""));
                            //write the read text to the file
                            writer.format("%s\n", sign);
                        }
                        else
                        {
                            writer.format("%s\n", sign);
                            break;
                        }
                    }
                    
                    if(!texts.isEmpty())
                    {
                        for(String text : texts)
                        {
                            //translate the text
                            String translation = translator.translate(text);
                            //wrtite the translation to the file
                            writer.format("%s\n","\""+translation+"\"");
                        }
                    }
                }
            }
            else
            {
                //if the text doesn't begin with "msgid" write to the file
                writer.format("%s\n", sign);
            }
        }
    }
}
