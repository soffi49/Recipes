package com.recipes.selenium.menu;

import static com.codeborne.selenide.Selenide.*;
import static com.recipes.selenium.config.SeleniumConfig.MAIN_PAGE;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.recipes.selenium.login.NotLoggedInPage;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AdminMenuTest {

    AdminMenu adminMenu = new AdminMenu();
    NotLoggedInPage notLoggedInPage = new NotLoggedInPage();

    @BeforeAll
    public static void setUpAll() {
        Configuration.browserSize = "1280x800";
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    public void setUp() {
        open(MAIN_PAGE);
        logIn();
    }

    @AfterEach
    public void cleanUp() {
        closeWebDriver();
    }

    @Test
    void checkIfElementsPresent() {
        $("div[role='alert']").shouldBe(Condition.visible);
        $x("//div[text()='Successfully logged in!']").shouldBe(Condition.visible);

        assertTrue(adminMenu.mainMenuDiv.exists());
        assertTrue(adminMenu.ingredientsLink.exists());
        assertTrue(adminMenu.recipesLink.exists());
        assertTrue(adminMenu.logOutButton.exists());
    }

    @Test
    void checkIfLogoutWorks() {
        adminMenu.logOutButton.click();

        $("div[role='alert']").shouldBe(Condition.visible);
        $x("//div[text()='Successfully logged out!']").shouldBe(Condition.visible);
        assertTrue(notLoggedInPage.usernameFieldInput.exists());
        assertTrue(notLoggedInPage.passwordFieldInput.exists());
    }

    @Test
    void checkIfIngredientsHyperlinkWorks() {
        adminMenu.ingredientsLink.click();

        $("#add-ingredient-button").shouldBe(Condition.exist);
        $("#ingredient-table-container").shouldBe(Condition.visible);
        $("#ingredient-table-header").shouldBe(Condition.visible);
        $("#ingredient-table-body").shouldBe(Condition.visible);
        $("#ingredient-table-footer").shouldBe(Condition.visible);
    }

    @Test
    void checkIfRecipesHyperlinkWorks() {
        adminMenu.recipesLink.click();

        $("#add-recipe-button").shouldBe(Condition.exist);
        $("#recipe-table-container").shouldBe(Condition.visible);
        $("#recipe-table-header").shouldBe(Condition.visible);
        $("#recipe-table-body").shouldBe(Condition.visible);
        $("#recipe-table-footer").shouldBe(Condition.visible);
    }

    @Test
    void checkIfSwitchingFromIngredientsToRecipesWorks() {
        adminMenu.ingredientsLink.click();

        $("#add-ingredient-button").shouldBe(Condition.exist);
        $("#ingredient-table-container").shouldBe(Condition.visible);
        $("#ingredient-table-header").shouldBe(Condition.visible);
        $("#ingredient-table-body").shouldBe(Condition.visible);
        $("#ingredient-table-footer").shouldBe(Condition.visible);

        adminMenu.recipesLink.click();

        $("#add-recipe-button").shouldBe(Condition.exist);
        $("#recipe-table-container").shouldBe(Condition.visible);
        $("#recipe-table-header").shouldBe(Condition.visible);
        $("#recipe-table-body").shouldBe(Condition.visible);
        $("#recipe-table-footer").shouldBe(Condition.visible);
    }

    @Test
    void checkIfSwitchingFromRecipesToIngredientsWorks() {
        adminMenu.recipesLink.click();

        $("#add-recipe-button").shouldBe(Condition.exist);
        $("#recipe-table-container").shouldBe(Condition.visible);
        $("#recipe-table-header").shouldBe(Condition.visible);
        $("#recipe-table-body").shouldBe(Condition.visible);
        $("#recipe-table-footer").shouldBe(Condition.visible);

        adminMenu.ingredientsLink.click();

        $("#add-ingredient-button").shouldBe(Condition.exist);
        $("#ingredient-table-container").shouldBe(Condition.visible);
        $("#ingredient-table-header").shouldBe(Condition.visible);
        $("#ingredient-table-body").shouldBe(Condition.visible);
        $("#ingredient-table-footer").shouldBe(Condition.visible);
    }

    private void logIn() {
        notLoggedInPage.usernameFieldInput.sendKeys("admin1");
        notLoggedInPage.passwordFieldInput.sendKeys("piwo");
        notLoggedInPage.logInButton.click();
    }

}
