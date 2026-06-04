package com.example.patient_system.e2e;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PatientSystemTest {

    WebDriver driver;
    WebDriverWait wait;

    private static final String EMAIL    = "juan@test.com";
    private static final String PASSWORD = "123456";

    @BeforeEach
    public void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-save-password-bubble");
        options.addArguments("--password-store=basic");
        options.addArguments("--disable-notifications");
        options.addArguments("--no-first-run");
        options.addArguments("--disable-features=PasswordCheck");
        options.setExperimentalOption("prefs", Map.of(
            "credentials_enable_service", false,
            "profile.password_manager_enabled", false
        ));
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    private void loginComoJuan() {
        driver.get("http://localhost:8085/login");
        driver.findElement(By.id("username")).sendKeys(EMAIL);
        driver.findElement(By.id("password")).sendKeys(PASSWORD);
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        wait.until(ExpectedConditions.urlContains("8085"));
    }

    private void navegarAMedications() {
        driver.get("http://localhost:8085/medications");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name")));
    }

    @Test
    @DisplayName(" Título de la página principal es correcto")
    public void testTituloPaginaPrincipal() {
        driver.get("http://localhost:8085");
        String title = driver.getTitle();
        assertEquals("Home - Patient System", title);
    }

    @Test
    @DisplayName(" Registro de usuario nuevo exitoso")
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
    @DisplayName(" Login con credenciales correctas")
    public void testLogin() {
        driver.get("http://localhost:8085/login");
        driver.findElement(By.id("username")).sendKeys(EMAIL);
        driver.findElement(By.id("password")).sendKeys(PASSWORD);
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        assertTrue(driver.getCurrentUrl().contains("8085"));
    }

    @Test
    @DisplayName(" Agregar medicamento con datos válidos")
    public void testAgregarMedicamento() {
        loginComoJuan();
        navegarAMedications();

        driver.findElement(By.id("name")).clear();
        driver.findElement(By.id("name")).sendKeys("Paracetamol");
        driver.findElement(By.id("dosage")).clear();
        driver.findElement(By.id("dosage")).sendKeys("500mg");
        driver.findElement(By.id("frequency")).clear();
        driver.findElement(By.id("frequency")).sendKeys("Cada 8 horas");

        driver.findElement(By.cssSelector("button[type='submit']")).click();

        wait.until(ExpectedConditions.urlContains("medications"));
        assertTrue(driver.getPageSource().contains("Paracetamol"),
                "El medicamento Paracetamol debe aparecer en la lista");
    }

    @Test
    @DisplayName(" Medicamento con dosage negativo — la app debería rechazarlo")
    public void testMedicamentoDosageNegativo() {
        loginComoJuan();
        navegarAMedications();

        driver.findElement(By.id("name")).sendKeys("Ibuprofeno");
        driver.findElement(By.id("dosage")).sendKeys("-500mg");
        driver.findElement(By.id("frequency")).sendKeys("Cada 6 horas");
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        
        assertTrue(driver.getCurrentUrl().contains("error") || driver.getPageSource().contains("inválido"),
        		"FALLA: la app aceptó dosis negativa '-500mg' sin validación.");
    }

    @Test
    @DisplayName(" Registro con teléfono negativo — la app debería rechazarlo")
    public void testRegistroTelefonoNegativo() {
        driver.get("http://localhost:8085/register");
        driver.findElement(By.id("name")).sendKeys("Test Negativo");
        String emailUnico = "negativo" + System.currentTimeMillis() + "@test.com";
        driver.findElement(By.id("email")).sendKeys(emailUnico);
        driver.findElement(By.id("password")).sendKeys("123456");
        driver.findElement(By.id("phone")).sendKeys("-999888777");
        driver.findElement(By.id("medicalHistory")).sendKeys("Ninguna");
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        
        assertTrue(driver.getCurrentUrl().contains("register"),"FALLA: la app registró un usuario con teléfono negativo '-999888777'. " +
                "Falta validación en el campo phone.");
    }

    @AfterEach
    public void teardown() {
        driver.quit();
    }
}