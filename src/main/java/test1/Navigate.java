package test1;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver; // Import the File class
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory; // Import the IOException class to handle errors
import org.openqa.selenium.support.ui.WebDriverWait;

public class Navigate {
    private WebDriver driver;

    @FindBy(xpath="//*[@id=\"cell0\"]")
    private WebElement fieldOne;
    @FindBy(xpath="//*[@id=\"r0\"]")
    private WebElement introOne;
    @FindBy(id="okBtn")
    private WebElement buttonOne;

    private WebElement selectableElement;
    public Boolean isCorrect = false;

    public Navigate(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this); // initializes all the WebElement parts.
    }

    public void naVi(WebDriver driver) {
        Logger logger = Logger.getLogger(this.getClass());
        BasicConfigurator.configure();

        this.driver = driver;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        String filePathString = "/tmp/SUCCESS.txt";
        String filePathString2 = "/tmp/FAILURE.txt";
        Path path = Paths.get(filePathString);
        Path path2 = Paths.get(filePathString2);
        
        try {
            driver.get(Locators.webSite);
            // wait.until(ExpectedConditions.visibilityOf(userNameField));
            Thread.sleep(2000);
            //introOne.click();
            buttonOne.click();
            Thread.sleep(1000);

            fieldOne.click();
            
            System.out.println("First Phase completed.");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in First Phase.");
            driver.quit();
            System.exit(0);
        }
                
        try {
            Thread.sleep(1000);
            System.out.println("Second Phase, Part 2 starts here. Current info is: " + isCorrect);
            String comparisonOne = fieldOne.getText();
            if ( comparisonOne.contains("playerText")) {
                isCorrect = false;
            
            }
            else {
                isCorrect = true;
            }

           
        }
        catch (Exception e) {
            System.out.println("Error in Second Phase, Part 2, error in element section.");
        }
        try {

            if (Files.exists(path)) { // temp file existance check
                System.out.println("File Creation Phase, File " + path.toString() + " is: " + Files.exists(path));
                Files.delete(path);
            }
            if (Files.exists(path2)) { // temp file existance check
                System.out.println("File Creation Phase, File " + path.toString() + " is: " + Files.exists(path2));
                Files.delete(path2);
            }

            if (isCorrect) {
                // System.out.println("Function works");
                // logger.info("Program works");
                try {

                    FileWriter writer = new FileWriter(path.toString());
                    // Write "SUCCESS" to the file
                    writer.write("SUCCESS");

                    // Close the PrintWriter (this will also close the underlying BufferedWriter and
                    // FileWriter)
                    writer.close();

                    System.out.println("Data written to file successfully.");
                    System.out.println("Third  Phase completed: isCorrect is: " + isCorrect);

                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Error writing file.");

                }
            } else {
                // System.out.println("Fatal error, program is not working");
                // logger.info("Failure in Program");
                try {

                    FileWriter writer = new FileWriter(path2.toString());
                    // Write "SUCCESS" to the file
                    writer.write("FAILURE");

                    // Close the PrintWriter (this will also close the underlying BufferedWriter and
                    // FileWriter)
                    writer.close();

                    System.out.println("Data written to file successfully.");
                    System.out.println("Third  Phase completed: isCorrect is: " + isCorrect);

                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Error writing file.");

                }
                // driver.quit();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        finally {
            if (isCorrect) {
                logger.info("SUCCESS");
            } else {
                logger.info("FAILURE");
            }

            driver.quit();
        }

    }

}
