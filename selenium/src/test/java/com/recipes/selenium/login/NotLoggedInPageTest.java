package com.recipes.selenium.login;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.*;
import static com.recipes.selenium.config.SeleniumConfig.MAIN_PAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NotLoggedInPageTest {
    NotLoggedInPage notLoggedInPage = new NotLoggedInPage();

    @BeforeAll
    public static void setUpAll() {
        Configuration.browserSize = "1280x800";
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    public void setUp() {
        open(MAIN_PAGE);
    }

    @AfterEach
    public void cleanUp() {
        closeWebDriver();
    }

    @Test
    void checkIfElementsPresent() {
        final String notLoggedInText = "You are not logged in";

        assertTrue(notLoggedInPage.notLoggedInLabel.exists());
        assertTrue(notLoggedInPage.logInButton.exists());
        assertTrue(notLoggedInPage.passwordFieldInput.exists());
        assertTrue(notLoggedInPage.usernameFieldInput.exists());
        assertEquals(notLoggedInPage.notLoggedInLabel.getText(), notLoggedInText);
    }

    @Test
    void checkIncorrectLogIn() {
        notLoggedInPage.usernameFieldInput.sendKeys("totally-incorrect-user");
        notLoggedInPage.passwordFieldInput.sendKeys("absolutely-wrong-password");
        notLoggedInPage.logInButton.click();

        $("div[role='alert']").shouldBe(Condition.visible);
        $x("//div[text()='Somethintg went wrong while logging in!']").shouldBe(Condition.visible);
    }

    @Test
    void checkCorrectLogIn() {
        notLoggedInPage.usernameFieldInput.sendKeys("admin1");
        notLoggedInPage.passwordFieldInput.sendKeys("piwo");
        notLoggedInPage.logInButton.click();

        $("div[role='alert']").shouldBe(Condition.visible);
        $x("//div[text()='Successfully logged in!']").shouldBe(Condition.visible);
        $("#main-menu").shouldBe(Condition.visible);
    }

    @Test
    void checkRegisterAccountAndCancel() {
        notLoggedInPage.createAccountButton.click();

        $("#registration-page-label").shouldBe(Condition.visible);
        notLoggedInPage.cancelRegistrationButton.shouldBe(Condition.visible);

        notLoggedInPage.cancelRegistrationButton.click();
        notLoggedInPage.notLoggedInLabel.shouldBe(Condition.visible);
    }

    @Test
    void checkRegisterAccountAndProceedCorrectInput() {

        final String username = "testingUsername5";
        final String password = "testingPassword5";

        notLoggedInPage.createAccountButton.click();

        $("#registration-page-label").shouldBe(Condition.visible);
        notLoggedInPage.registerButton.shouldBe(Condition.visible);
        $("#register-username-field").shouldBe(Condition.visible);
        $("#register-password-field").shouldBe(Condition.visible);

        $("#register-username-field").sendKeys(username);
        $("#register-password-field").sendKeys(password);

        notLoggedInPage.registerButton.click();

        notLoggedInPage.notLoggedInLabel.shouldBe(Condition.visible);

        notLoggedInPage.usernameFieldInput.sendKeys(username);
        notLoggedInPage.passwordFieldInput.sendKeys(password);
        notLoggedInPage.logInButton.click();

        $("div[role='alert']").shouldBe(Condition.visible);
        $x("//div[text()='Successfully logged in!']").shouldBe(Condition.visible);
        $("#main-menu").shouldBe(Condition.visible);
    }

    @Test
    void checkRegisterAccountAndProceedDuplicate() {

        final String username = "admin1";
        final String password = "piwo";

        notLoggedInPage.createAccountButton.click();

        $("#registration-page-label").shouldBe(Condition.visible);
        notLoggedInPage.registerButton.shouldBe(Condition.visible);
        $("#register-username-field").shouldBe(Condition.visible);
        $("#register-password-field").shouldBe(Condition.visible);

        $("#register-username-field").sendKeys(username);
        $("#register-password-field").sendKeys(password);

        notLoggedInPage.registerButton.click();

        $x("//div[text()='Request failed with status code 403']").shouldBe(Condition.visible);
    }
}
