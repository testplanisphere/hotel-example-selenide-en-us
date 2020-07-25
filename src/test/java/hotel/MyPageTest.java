package hotel;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.*;
import static org.junit.jupiter.api.Assertions.*;

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
@DisplayName("MyPage")
class MyPageTest {

  @AfterEach
  void tearDown() {
    clearBrowserCookies();
  }

  @Test
  @Order(1)
  @DisplayName("It should be display preset user [clark]")
  void testMyPageExistUserOne() {
    var topPage = open("/en-US/", TopPage.class);
    var loginPage = topPage.goToLoginPage();
    var myPage = loginPage.doLogin("clark@example.com", "password");

    myPage.getEmail().shouldHave(exactText("clark@example.com"));
    myPage.getUsername().shouldHave(exactText("Clark Evans"));
    myPage.getRank().shouldHave(exactText("Premium"));
    myPage.getAddress().shouldHave(exactText("Mountain View, California"));
    myPage.getTel().shouldHave(exactText("01234567891"));
    myPage.getGender().shouldHave(exactText("male"));
    myPage.getBirthday().shouldHave(exactText("not answered"));
    myPage.getNotification().shouldHave(exactText("received"));
  }

  @Test
  @Order(2)
  @DisplayName("It should be display preset user [diana]")
  void testMyPageExistUserTwo() {
    var topPage = open("/en-US/", TopPage.class);
    var loginPage = topPage.goToLoginPage();
    var myPage = loginPage.doLogin("diana@example.com", "pass1234");

    myPage.getEmail().shouldHave(exactText("diana@example.com"));
    myPage.getUsername().shouldHave(exactText("Diana Johansson"));
    myPage.getRank().shouldHave(exactText("Normal"));
    myPage.getAddress().shouldHave(exactText("Redmond, Washington"));
    myPage.getTel().shouldHave(exactText("not answered"));
    myPage.getGender().shouldHave(exactText("female"));
    myPage.getBirthday().shouldHave(exactText("April 1, 2000"));
    myPage.getNotification().shouldHave(exactText("not received"));
  }

  @Test
  @Order(3)
  @DisplayName("It should be display preset user [ororo]")
  void testMyPageExistUserThree() {
    var topPage = open("/en-US/", TopPage.class);
    var loginPage = topPage.goToLoginPage();
    var myPage = loginPage.doLogin("ororo@example.com", "pa55w0rd!");

    myPage.getEmail().shouldHave(exactText("ororo@example.com"));
    myPage.getUsername().shouldHave(exactText("Ororo Saldana"));
    myPage.getRank().shouldHave(exactText("Premium"));
    myPage.getAddress().shouldHave(exactText("Cupertino, California"));
    myPage.getTel().shouldHave(exactText("01212341234"));
    myPage.getGender().shouldHave(exactText("other"));
    myPage.getBirthday().shouldHave(exactText("December 17, 1988"));
    myPage.getNotification().shouldHave(exactText("not received"));
  }

  @Test
  @Order(4)
  @DisplayName("It should be display preset user [miles]")
  void testMyPageExistUserFour() {
    var topPage = open("/en-US/", TopPage.class);
    var loginPage = topPage.goToLoginPage();
    var myPage = loginPage.doLogin("miles@example.com", "pass-pass");

    myPage.getEmail().shouldHave(exactText("miles@example.com"));
    myPage.getUsername().shouldHave(exactText("Miles Boseman"));
    myPage.getRank().shouldHave(exactText("Normal"));
    myPage.getAddress().shouldHave(exactText("not answered"));
    myPage.getTel().shouldHave(exactText("01298765432"));
    myPage.getGender().shouldHave(exactText("not answered"));
    myPage.getBirthday().shouldHave(exactText("August 31, 1992"));
    myPage.getNotification().shouldHave(exactText("received"));
  }

  @Test
  @Order(5)
  @DisplayName("It should be display new user")
  void testMyPageNewUser() {
    var topPage = open("/en-US/", TopPage.class);

    var signupPage = topPage.goToSignupPage();
    signupPage.setEmail("new-user@example.com");
    signupPage.setPassword("11111111");
    signupPage.setPasswordConfirmation("11111111");
    signupPage.setUsername("Jane Doe");
    signupPage.setRank(Rank.NORMAL);
    signupPage.setAddress("Detroit, Michigan");
    signupPage.setTel("09876543211");
    signupPage.setGender("female");
    signupPage.setBirthday(LocalDate.parse("2000-01-01"));
    signupPage.setNotification(false);
    var myPage = signupPage.submit();

    myPage.getEmail().shouldHave(exactText("new-user@example.com"));
    myPage.getUsername().shouldHave(exactText("Jane Doe"));
    myPage.getRank().shouldHave(exactText("Normal"));
    myPage.getAddress().shouldHave(exactText("Detroit, Michigan"));
    myPage.getTel().shouldHave(exactText("09876543211"));
    myPage.getGender().shouldHave(exactText("female"));
    myPage.getBirthday().shouldHave(exactText("January 1, 2000"));
    myPage.getNotification().shouldHave(exactText("not received"));
  }

  @Test
  @Order(6)
  @DisplayName("It should be an error selecting not image on icon setting")
  void testIconNotImage() {
    var topPage = open("/en-US/", TopPage.class);
    var loginPage = topPage.goToLoginPage();
    var myPage = loginPage.doLogin("new-user@example.com", "11111111");

    var iconPage = myPage.goToIconPage();
    iconPage.setIcon("dummy.txt");

    iconPage.getIconMessage().shouldHave(exactText("Please select an image file."));
  }

  @Test
  @Order(7)
  @DisplayName("It should be an error selecting over 10KB file on icon setting")
  void testIconOverSize() {
    var topPage = open("/en-US/", TopPage.class);
    var loginPage = topPage.goToLoginPage();
    var myPage = loginPage.doLogin("new-user@example.com", "11111111");

    var iconPage = myPage.goToIconPage();
    iconPage.setIcon("240x240_12.png");

    iconPage.getIconMessage().shouldHave(exactText("Please select a file with a size of 10 KB or less."));
  }

  @Test
  @Order(8)
  @DisplayName("It should be display icon image")
  void testIconSuccess() {
    var topPage = open("/en-US/", TopPage.class);
    var loginPage = topPage.goToLoginPage();
    var myPage = loginPage.doLogin("new-user@example.com", "11111111");

    var iconPage = myPage.goToIconPage();
    iconPage.setIcon("240x240_01.png");
    iconPage.setZoom(80);
    iconPage.setColor("#000000");
    iconPage.submit();

    myPage.getIconImage().should(exist);
    myPage.getIconImage().shouldHave(attribute("width", "70"));
    myPage.getIconImage().shouldHave(cssValue("backgroundColor", "rgba(0, 0, 0, 1)"));
  }

  @Test
  @Order(9)
  @DisplayName("it should delete new user")
  void testDeleteUser() {
    var topPage = open("/en-US/", TopPage.class);
    var loginPage = topPage.goToLoginPage();
    var myPage = loginPage.doLogin("new-user@example.com", "11111111");

    myPage.deleteUser();
    confirm("If you cancel your membership, all information will be deleted.\nDo you wish to proceed?");
    confirm("The process has been completed. Thank you for your service.");
    assertTrue(url().contains("index.html"));
  }

}
