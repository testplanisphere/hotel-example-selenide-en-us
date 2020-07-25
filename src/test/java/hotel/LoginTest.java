package hotel;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

import com.codeborne.selenide.junit5.BrowserStrategyExtension;
import com.codeborne.selenide.junit5.ScreenShooterExtension;
import hotel.pages.TopPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith({BrowserStrategyExtension.class, ScreenShooterExtension.class})
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("Login")
class LoginTest {

  @AfterEach
  void tearDown() {
    clearBrowserCookies();
  }

  @Test
  @Order(1)
  @DisplayName("It should be successful logged in preset user")
  void testLoginSuccess() {
    var topPage = open("/en-US/", TopPage.class);
    var loginPage = topPage.goToLoginPage();
    var myPage = loginPage.doLogin("clark@example.com", "password");

    myPage.getHeader().shouldHave(exactText("MyPage"));
  }

  @Test
  @Order(2)
  @DisplayName("It should be an error when empty input")
  void testLoginFailBlank() {
    var topPage = open("/en-US/", TopPage.class);
    var loginPage = topPage.goToLoginPage();
    loginPage.doLogin("", "");

    loginPage.getEmailMessage().shouldHave(exactText("Please fill out this field."));
    loginPage.getPasswordMessage().shouldHave(exactText("Please fill out this field."));
  }

  @Test
  @Order(3)
  @DisplayName("It should be an error when invalid user")
  void testLoginFailUnregister() {
    var topPage = open("/en-US/", TopPage.class);
    var loginPage = topPage.goToLoginPage();
    loginPage.doLogin("error@example.com", "error");

    loginPage.getEmailMessage().shouldHave(exactText("Email or password is invalid."));
    loginPage.getPasswordMessage().shouldHave(exactText("Email or password is invalid."));
  }

}
