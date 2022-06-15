package com.recipes.selenium.menu;
import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.SelenideElement;

// page_url = "http://localhost:3000/ingredients"


public class AdminMenu {
    
    public SelenideElement mainMenuDiv = $("#main-menu");

    public SelenideElement ingredientsLink = $("#Ingredients");

    public SelenideElement recipesLink = $("#Recipes");

    public SelenideElement logOutButton = $("#log-out-button");
    
}
