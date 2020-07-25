package hotel;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

import com.codeborne.selenide.junit5.BrowserStrategyExtension;
import com.codeborne.selenide.junit5.ScreenShooterExtension;
import hotel.pages.SignupPage.Rank;
import hotel.pages.TopPage;
import java.time.LocalDate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith({BrowserStrategyExtension.class, ScreenShooterExtension.class})
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("Sign up")
class SignupTest {

  @AfterEach
  void tearDown() {
    clearBrowserCookies();
  }

  @Test
  @Order(1)
  @DisplayName("It should be successful sign up")
  void testSignupSuccess() {
    var topPage = open("/en-US/", TopPage.class);

    var signupPage = topPage.goToSignupPage();
    signupPage.setEmail("new-user@example.com");
    signupPage.setPassword("password");
    signupPage.setPasswordConfirmation("password");
    signupPage.setUsername("new user 1");
    signupPage.setRank(Rank.NORMAL);
    signupPage.setAddress("New York City");
    signupPage.setTel("01234567891");
    signupPage.setGender("female");
    signupPage.setBirthday(LocalDate.parse("2000-01-01"));
    signupPage.setNotification(true);
    var myPage = signupPage.submit();

    myPage.getHeader().shouldHave(exactText("MyPage"));
  }

  @Test
  @Order(2)
  @DisplayName("It should be an error when blank")
  void testSignupErrorBlank() {
    var topPage = open("/en-US/", TopPage.class);

    var signupPage = topPage.goToSignupPage();
    signupPage.setEmail("");
    signupPage.setPassword("");
    signupPage.setPasswordConfirmation("");
    signupPage.setUsername("");
    signupPage.setRank(Rank.PREMIUM);
    signupPage.setAddress("");
    signupPage.setTel("");
    signupPage.setGender("I do not answer.");
    signupPage.setBirthday(null);
    signupPage.setNotification(false);
    signupPage.submit();

    signupPage.getEmailMessage().shouldHave(exactText("Please fill out this field."));
    signupPage.getPasswordMessage().shouldHave(exactText("Please fill out this field."));
    signupPage.getPasswordConfirmationMessage().shouldHave(exactText("Please fill out this field."));
    signupPage.getUsernameMessage().shouldHave(exactText("Please fill out this field."));
    signupPage.getAddressMessage().shouldBe(empty);
    signupPage.getTelMessage().shouldBe(empty);
    signupPage.getGenderMessage().shouldBe(empty);
    signupPage.getBirthdayMessage().shouldBe(empty);
  }

  @Test
  @Order(3)
  @DisplayName("It should be an error when invalid value")
  void testSignupErrorInvalid() {
    var topPage = open("/en-US/", TopPage.class);

    var signupPage = topPage.goToSignupPage();
    signupPage.setEmail("a");
    signupPage.setPassword("1234567");
    signupPage.setPasswordConfirmation("1");
    signupPage.setUsername("tester tester");
    signupPage.setRank(Rank.NORMAL);
    signupPage.setAddress("Chicago, Illinois");
    signupPage.setTel("1234567890");
    signupPage.setGender("other");
    signupPage.setBirthday(LocalDate.parse("2000-01-01"));
    signupPage.setNotification(true);
    signupPage.submit();

    signupPage.getEmailMessage().shouldHave(exactText("Please enter a non-empty email address."));
    signupPage.getPasswordMessage().shouldHave(exactText("Please lengthen this text to 8 characters or more."));
    signupPage.getPasswordConfirmationMessage().shouldHave(exactText("Please lengthen this text to 8 characters or more."));
    signupPage.getUsernameMessage().shouldBe(empty);
    signupPage.getAddressMessage().shouldBe(empty);
    signupPage.getTelMessage().shouldHave(exactText("Please match the requested format."));
    signupPage.getGenderMessage().shouldBe(empty);
    signupPage.getBirthdayMessage().shouldBe(empty);
  }

  @Test
  @Order(4)
  @DisplayName("It should be an error when email has already been taken")
  void testSignupErrorDouble() {
    var topPage = open("/en-US/", TopPage.class);

    var signupPage = topPage.goToSignupPage();
    signupPage.setEmail("new-user@example.com");
    signupPage.setPassword("password");
    signupPage.setPasswordConfirmation("password");
    signupPage.setUsername("new user 1");
    signupPage.setRank(Rank.NORMAL);
    signupPage.setAddress("Las Vegas, Nevada");
    signupPage.setTel("01234567891");
    signupPage.setGender("female");
    signupPage.setBirthday(LocalDate.parse("2000-01-01"));
    signupPage.setNotification(true);
    signupPage.submit();

    signupPage.getEmailMessage().shouldHave(exactText("Email has already been taken."));
  }

  @Test
  @Order(5)
  @DisplayName("It should be an error when password doesn't match")
  void testSignupErrorUnMatchPassword() {
    var topPage = open("/en-US/", TopPage.class);

    var signupPage = topPage.goToSignupPage();
    signupPage.setEmail("new-user@example.com");
    signupPage.setPassword("password");
    signupPage.setPasswordConfirmation("123456789");
    signupPage.setUsername("new user 1");
    signupPage.setRank(Rank.NORMAL);
    signupPage.setAddress("Kansas City, Missouri");
    signupPage.setTel("01234567891");
    signupPage.setGender("female");
    signupPage.setBirthday(LocalDate.parse("2000-01-01"));
    signupPage.setNotification(true);
    signupPage.submit();

    signupPage.getPasswordConfirmationMessage().shouldHave(exactText("Password doesn't match."));
  }

}
