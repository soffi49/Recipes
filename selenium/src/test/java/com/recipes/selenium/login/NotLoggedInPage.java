package com.recipes.selenium.login;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

// page_url = "http://localhost:3000/"
public class NotLoggedInPage {
    public SelenideElement notLoggedInLabel = $("#not-logged-in-label");

    public SelenideElement logInButton = $("#log-in-button");

    public SelenideElement createAccountButton = $("#create-account-button");

    public SelenideElement passwordFieldInput = $("#password-field");

    public SelenideElement usernameFieldInput = $("#username-field");

    public SelenideElement cancelRegistrationButton = $("#cancel-registration-button");

    public SelenideElement registerButton = $("#register-button");
}
