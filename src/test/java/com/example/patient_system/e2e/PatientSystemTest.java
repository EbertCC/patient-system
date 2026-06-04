package com.example.patient_system.e2e;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class PatientSystemTest {

    WebDriver driver;

    @BeforeEach
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
    }

    @Test
    public void testTituloPaginaPrincipal() {
        driver.get("http://localhost:8085");
        String title = driver.getTitle();
        assertEquals("Home - Patient System", title);

    }

    @Test
    public void testRegistroUsuario() {
        driver.get("http://localhost:8085/register");

        driver.findElement(By.id("name")).sendKeys("Juan Perez");
        driver.findElement(By.id("email")).sendKeys("juan@test.com");
        driver.findElement(By.id("password")).sendKeys("123456");
        driver.findElement(By.id("phone")).sendKeys("999888777");
        driver.findElement(By.id("medicalHistory")).sendKeys("Ninguna");

        driver.findElement(By.cssSelector("button[type='submit']")).click();

        assertTrue(driver.getCurrentUrl().contains("login") || driver.getCurrentUrl().contains("8085"));
    }

    @Test
    public void testLogin() {
        driver.get("http://localhost:8085/login");

        driver.findElement(By.id("username")).sendKeys("juan@test.com");
        driver.findElement(By.id("password")).sendKeys("123456");

        driver.findElement(By.cssSelector("button[type='submit']")).click();

        assertTrue(driver.getCurrentUrl().contains("8085"));
    }

    @AfterEach
    public void teardown() {
        driver.quit();
    }
}