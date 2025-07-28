import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.example.Data;

import java.time.Duration;
import java.util.List;

public class TestCases {
    WebDriver driver;
    
    @Test (priority = 1)
    public void Login_test_TC_0001() throws InterruptedException{
        System.out.println("TC_0001");
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement email = wait.until( ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Email']"))  );
        email.sendKeys("wrong@mail.com");
        System.out.println("Set Email= " + "wrong@mail.com");
        
        driver.findElement(By.xpath("//input[@placeholder='Password']")).sendKeys(Data.Password);
        System.out.println("Set Password= " + Data.Password);

        driver.findElement(By.xpath("//button[@type='submit']")).click();
        System.out.println("Click on login button ");

        WebElement status = wait.until( ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[@class='text-status-login']")));
        System.out.println(status.getText() );
        assert status.getText().equals("Login failed. Check your credentials.");
    }
    @Test (priority = 2)
    public void Login_test_TC_0002(){
        System.out.println("TC_0002");
        
        driver.findElement(By.xpath("//input[@placeholder='Email']")).clear();;
        driver.findElement(By.xpath("//input[@placeholder='Email']")).sendKeys(Data.Email);
        System.out.println("Set Email= " + Data.Email);
        
        driver.findElement(By.xpath("//input[@placeholder='Password']")).clear();
        driver.findElement(By.xpath("//input[@placeholder='Password']")).sendKeys("wrongPass");
        System.out.println("Set Password= " + "wrongPass");

        driver.findElement(By.xpath("//button[@type='submit']")).click();
        System.out.println("Click on login button ");

        WebElement status = driver.findElement(By.xpath("//p[@class='text-status-login']"));
        System.out.println(status.getText() );
        assert status.getText().equals("Login failed. Check your credentials.");
        
    }
    
    @Test (priority = 3)
    public void Login_test_TC_0003(){
        System.out.println("TC_0003");
        
        driver.findElement(By.xpath("//input[@placeholder='Email']")).clear();;
        driver.findElement(By.xpath("//input[@placeholder='Email']")).sendKeys(Data.Email);
        System.out.println("Set Email= " + Data.Email);
        
        driver.findElement(By.xpath("//input[@placeholder='Password']")).clear();
        driver.findElement(By.xpath("//input[@placeholder='Password']")).sendKeys(Data.Password);
        System.out.println("Set Password= " + Data.Password);

        driver.findElement(By.xpath("//button[@type='submit']")).click();
        System.out.println("Click on login button ");

        WebElement status = driver.findElement(By.xpath("//p[@class='text-status-message']"));
        System.out.println(status.getText() );
        assert status.getText().equals("Login successful!");
        
    }
    
    @Test (priority = 4,dependsOnMethods = "Login_test_TC_0003")
    public void Add_Record_TC_0004() {

        System.out.println("TC_0004");
        
        driver.findElement(By.id("Enter-ToDo")).sendKeys("task 1");
        driver.findElement(By.id("btn-todo")).click();;
        System.out.println("added task 1 ");

        WebElement status = driver.findElement(By.xpath("//p[@class='text-status-message']"));
        System.out.println(status.getText() );
        assert status.getText().equals("New todo added.");
        

        driver.findElement(By.id("Enter-ToDo")).sendKeys("task 2");
        driver.findElement(By.id("btn-todo")).click();;
        System.out.println("added task 2 ");

        status = driver.findElement(By.xpath("//p[@class='text-status-message']"));
        assert status.getText().equals("New todo added.");
    }
    
    @Test (priority = 5)
    public void Add_Record_TC_0005(){
        System.out.println("TC_0005");

        driver.findElement(By.id("Enter-ToDo")).sendKeys("");
        driver.findElement(By.id("btn-todo")).click();;
        System.out.println("Enter Empty todo and should be faild ");

        WebElement status = driver.findElement(By.xpath("//p[@class='text-status-message']"));
        assert !status.getText().equals("New todo added.");

    }

    @Test (priority = 6,dependsOnMethods = "Login_test_TC_0003")
    public void Edit_Record_TC_0006() {

        System.out.println("TC_0006");

        driver.findElement(By.id("Enter-ToDo")).sendKeys("task 2 edit");
        driver.findElement(By.id("btn-todo")).click();

        List <WebElement> element_list = driver.findElements(By.className("item-line-in-list-mb-2"));

        WebElement text;
        WebElement edit_icon ;
        for(int i =0 ; i < element_list.size(); i++)
        {
            
            text = element_list.get(i).findElement(By.className("text-item-line-in-todo"));


            if (text.getText().equals( "task 2 edit"))
            {
                edit_icon = element_list.get(i).findElement(By.className("text-edit"));
                edit_icon.click();
                driver.findElement(By.id("Enter-ToDo")).clear();
                driver.findElement(By.id("Enter-ToDo")).sendKeys("after edit 2 task ");
                driver.findElement(By.id("btn-todo")).click();
            }
        }
    }

    @Test (priority = 7,dependsOnMethods = "Login_test_TC_0003")
    public void Delete_Record_TC_0007() {

        System.out.println("TC_0007");

        List <WebElement> element_list = driver.findElements(By.className("item-line-in-list-mb-2"));

        WebElement text;
        WebElement delete_icon ;
        WebElement status ;
        int flag_task_1 =0;
        int flag_task_2 =0;
        int flag_edittask_2 =0;
        for(int i =0 ; i < element_list.size(); i++)
        {
            
            text = element_list.get(i).findElement(By.className("text-item-line-in-todo"));

            if(text.getText().equals( "task 1")) {flag_task_1++;}
            if(text.getText().equals( "task 2")) {flag_task_2++;}
            if(text.getText().contains( "after edit")) {flag_edittask_2++;}

            if (text.getText().equals( "") 
            || (text.getText().contains( "after edit") && (flag_edittask_2>1))
            || (text.getText().equals( "task 2") && (flag_task_2>1)) 
            || (text.getText().equals( "task 1") && (flag_task_1>1)) )
            {   
                delete_icon = element_list.get(i).findElement(By.className("text-delete"));
                delete_icon.click();
                status = driver.findElement(By.xpath("//p[@class='text-status-message']"));
                assert status.getText().equals("Todo deleted successfully.");
            }
        }
        element_list = driver.findElements(By.className("item-line-in-list-mb-2"));
        for(int i =0 ; i < element_list.size(); i++)
        {
            text = element_list.get(i).findElement(By.className("text-item-line-in-todo"));
            System.out.println(text.getText());
        }
    }
    

    @BeforeClass
    public void Open_browser(){
        driver = new ChromeDriver();
        driver.get("http://localhost:3000/");
        driver.manage().window().maximize();
        System.out.println("Navigate to Website");
        
    }
    @AfterClass
    public void Close_browser()
    {
        driver.close();
        System.out.println("Close Website");
    }
}
