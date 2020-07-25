package hotel;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static java.time.DayOfWeek.*;
import static org.junit.jupiter.api.Assertions.*;

import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.junit5.BrowserStrategyExtension;
import com.codeborne.selenide.junit5.ScreenShooterExtension;
import hotel.pages.ReservePage;
import hotel.pages.RoomPage;
import hotel.pages.TopPage;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.support.ui.ExpectedConditions;

@ExtendWith({BrowserStrategyExtension.class, ScreenShooterExtension.class})
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("Reservation")
class ReserveTest {

  private static final DateTimeFormatter SHORT_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy");

  private static final DateTimeFormatter LONG_FORMATTER = DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.US);

  @AfterEach
  void tearDown() {
    if (WebDriverRunner.getWebDriver().getWindowHandles().size() > 1) {
      closeWindow();
    }
    switchTo().window("Plans | HOTEL PLANISPHERE - Website for Practice Test Automation");
    clearBrowserCookies();
  }

  @Test
  @Order(1)
  @DisplayName("It should be display initial values [not logged in]")
  void testPageInitValue() {
    var topPage = open("/en-US/", TopPage.class);
    var plansPage = topPage.goToPlansPage();
    plansPage.openPlanByTitle("Plan with special offers");
    switchTo().window("Reservation | HOTEL PLANISPHERE - Website for Practice Test Automation");
    var reservePage = page(ReservePage.class);

    var tomorrow = SHORT_FORMATTER.format(LocalDate.now().plusDays(1));

    reservePage.getPlanName().shouldHave(exactText("Plan with special offers"));
    reservePage.getReserveDate().shouldHave(exactValue(tomorrow));
    reservePage.getReserveTerm().shouldHave(exactValue("1"));
    reservePage.getHeadCount().shouldHave(exactValue("1"));
    reservePage.getEmail().shouldBe(hidden);
    reservePage.getTel().shouldBe(hidden);

    reservePage.setContact("By email");
    reservePage.getEmail().shouldBe(visible);
    reservePage.getTel().shouldBe(hidden);
    reservePage.getEmail().shouldBe(empty);

    reservePage.setContact("By telephone");
    reservePage.getEmail().shouldBe(hidden);
    reservePage.getTel().shouldBe(visible);
    reservePage.getTel().shouldBe(empty);

    switchTo().frame("room");
    var roomPage = page(RoomPage.class);
    roomPage.getHeader().shouldHave(exactText("Standard Twin"));
    switchTo().defaultContent();
  }

  @Test
  @Order(2)
  @DisplayName("It should be display initial values [logged in]")
  void testPageInitValueLogin() {
    var topPage = open("/en-US/", TopPage.class);
    var loginPage = topPage.goToLoginPage();
    var myPage = loginPage.doLogin("clark@example.com", "password");
    var plansPage = myPage.goToPlansPage();
    plansPage.openPlanByTitle("Premium plan");
    switchTo().window("Reservation | HOTEL PLANISPHERE - Website for Practice Test Automation");
    var reservePage = page(ReservePage.class);

    var tomorrow = SHORT_FORMATTER.format(LocalDate.now().plusDays(1));

    reservePage.getPlanName().shouldHave(exactText("Premium plan"));
    reservePage.getReserveDate().shouldHave(exactValue(tomorrow));
    reservePage.getReserveTerm().shouldHave(exactValue("1"));
    reservePage.getHeadCount().shouldHave(exactValue("2"));
    reservePage.getEmail().shouldBe(hidden);
    reservePage.getTel().shouldBe(hidden);

    reservePage.setContact("By email");
    reservePage.getEmail().shouldBe(visible);
    reservePage.getTel().shouldBe(hidden);
    reservePage.getEmail().shouldHave(exactValue("clark@example.com"));

    reservePage.setContact("By telephone");
    reservePage.getEmail().shouldBe(hidden);
    reservePage.getTel().shouldBe(visible);
    reservePage.getTel().shouldHave(exactValue("01234567891"));

    switchTo().frame("room");
    var roomPage = page(RoomPage.class);
    roomPage.getHeader().shouldHave(exactText("Premium Twin"));
    switchTo().defaultContent();
  }

  @Test
  @Order(3)
  @DisplayName("It should be an error when blank values")
  void testBlankInputOne() {
    var topPage = open("/en-US/", TopPage.class);
    var plansPage = topPage.goToPlansPage();
    plansPage.openPlanByTitle("Plan with special offers");
    switchTo().window("Reservation | HOTEL PLANISPHERE - Website for Practice Test Automation");
    var reservePage = page(ReservePage.class);

    reservePage.setReserveDate("");
    reservePage.setReserveTerm("");
    reservePage.setHeadCount("");
    reservePage.setUsername("");  // move focus

    reservePage.getReserveDateMessage().shouldHave(exactText("Please fill out this field."));
    reservePage.getReserveTermMessage().shouldHave(exactText("Please fill out this field."));
    reservePage.getHeadCountMessage().shouldHave(exactText("Please fill out this field."));
  }

  @Test
  @Order(4)
  @DisplayName("It should be an error when invalid values [under]")
  void testInvalidInputSmall() {
    var topPage = open("/en-US/", TopPage.class);
    var plansPage = topPage.goToPlansPage();
    plansPage.openPlanByTitle("Plan with special offers");
    switchTo().window("Reservation | HOTEL PLANISPHERE - Website for Practice Test Automation");
    var reservePage = page(ReservePage.class);

    var today = SHORT_FORMATTER.format(LocalDate.now());

    reservePage.setReserveDate(today);
    reservePage.setReserveTerm("0");
    reservePage.setHeadCount("0");
    reservePage.setUsername("the tester");  // move focus

    reservePage.getReserveDateMessage().shouldHave(exactText("Please enter a date after tomorrow."));
    reservePage.getReserveTermMessage().shouldHave(exactText("Value must be greater than or equal to 1."));
    reservePage.getHeadCountMessage().shouldHave(exactText("Value must be greater than or equal to 1."));
  }

  @Test
  @Order(5)
  @DisplayName("It should be an error when invalid values [over]")
  void testInvalidInputBig() {
    var topPage = open("/en-US/", TopPage.class);
    var plansPage = topPage.goToPlansPage();
    plansPage.openPlanByTitle("Plan with special offers");
    switchTo().window("Reservation | HOTEL PLANISPHERE - Website for Practice Test Automation");
    var reservePage = page(ReservePage.class);

    var after90 = SHORT_FORMATTER.format(LocalDate.now().plusDays(91));

    reservePage.setReserveDate(after90);
    reservePage.setReserveTerm("10");
    reservePage.setHeadCount("10");
    reservePage.setUsername("the tester");  // move focus

    reservePage.getReserveDateMessage().shouldHave(exactText("Please enter a date within 3 months."));
    reservePage.getReserveTermMessage().shouldHave(exactText("Value must be less than or equal to 9."));
    reservePage.getHeadCountMessage().shouldHave(exactText("Value must be less than or equal to 9."));
  }

  @Test
  @Order(6)
  @DisplayName("It should be an error when invalid values [string]")
  void testInvalidInputOther() {
    var topPage = open("/en-US/", TopPage.class);
    var plansPage = topPage.goToPlansPage();
    plansPage.openPlanByTitle("Plan with special offers");
    switchTo().window("Reservation | HOTEL PLANISPHERE - Website for Practice Test Automation");
    var reservePage = page(ReservePage.class);

    reservePage.setReserveDate("12/3//345");
    reservePage.setReserveTerm("a");
    reservePage.setHeadCount("a");
    reservePage.setUsername("the tester");  // move focus

    reservePage.getReserveDateMessage().shouldHave(exactText("Please enter a valid value."));
    reservePage.getReserveTermMessage().shouldHave(exactText("Please fill out this field."));
    reservePage.getHeadCountMessage().shouldHave(exactText("Please fill out this field."));
  }

  @Test
  @Order(7)
  @DisplayName("It should be an error when submitting [mail]")
  void testInvalidInputOnSubmitEmail() {
    var topPage = open("/en-US/", TopPage.class);
    var plansPage = topPage.goToPlansPage();
    plansPage.openPlanByTitle("Plan with special offers");
    switchTo().window("Reservation | HOTEL PLANISPHERE - Website for Practice Test Automation");
    var reservePage = page(ReservePage.class);

    reservePage.setUsername("");
    reservePage.setContact("By email");
    reservePage.setEmail("");
    reservePage.submit();

    reservePage.getUsernameMessage().shouldHave(exactText("Please fill out this field."));
    reservePage.getEmailMessage().shouldHave(exactText("Please fill out this field."));
  }

  @Test
  @Order(8)
  @DisplayName("It should be an error when submitting [tel]")
  void testInvalidInputOnSubmitTel() {
    var topPage = open("/en-US/", TopPage.class);
    var plansPage = topPage.goToPlansPage();
    plansPage.openPlanByTitle("Plan with special offers");
    switchTo().window("Reservation | HOTEL PLANISPHERE - Website for Practice Test Automation");
    var reservePage = page(ReservePage.class);

    reservePage.setUsername("");
    reservePage.setContact("By telephone");
    reservePage.setTel("");
    reservePage.submit();

    reservePage.getUsernameMessage().shouldHave(exactText("Please fill out this field."));
    reservePage.getTelMessage().shouldHave(exactText("Please fill out this field."));
  }

  @Test
  @Order(9)
  @DisplayName("It should be successful the reservation [not logged in] [initial values]")
  void testReserveSuccess() {
    var topPage = open("/en-US/", TopPage.class);
    var plansPage = topPage.goToPlansPage();
    plansPage.openPlanByTitle("Plan with special offers");
    switchTo().window("Reservation | HOTEL PLANISPHERE - Website for Practice Test Automation");
    var reservePage = page(ReservePage.class);

    var expectedStart = LocalDate.now().plusDays(1);
    var expectedEnd = LocalDate.now().plusDays(2);
    String expectedTotalBill;
    if (expectedStart.getDayOfWeek() == SUNDAY || expectedStart.getDayOfWeek() == SATURDAY) {
      expectedTotalBill = "Total $87.50 (included taxes)";
    } else {
      expectedTotalBill = "Total $70.00 (included taxes)";
    }
    final var expectedTerm = LONG_FORMATTER.format(expectedStart) + " - " + LONG_FORMATTER.format(expectedEnd) + ". 1 night(s)";

    reservePage.setUsername("the tester");
    reservePage.setContact("I don't need.");
    var confirmPage = reservePage.submit();

    confirmPage.getTotalBill().shouldHave(exactText(expectedTotalBill));
    confirmPage.getPlanName().shouldHave(exactText("Plan with special offers"));
    confirmPage.getTerm().shouldHave(exactText(expectedTerm));
    confirmPage.getHeadCount().shouldHave(exactText("1 person(s)"));
    confirmPage.getPlans().shouldHave(exactText("none"));
    confirmPage.getUsername().shouldHave(exactText("the tester"));
    confirmPage.getContact().shouldHave(exactText("not required"));
    confirmPage.getComment().shouldHave(exactText("none"));

    confirmPage.confirm();
    confirmPage.getModalMessage().shouldHave(exactText("We look forward to visiting you."));
    confirmPage.close();
    assertTrue(Wait().until(ExpectedConditions.numberOfWindowsToBe(1)));
  }

  @Test
  @Order(10)
  @DisplayName("It should be successful the reservation [logged in]")
  void testReserveSuccess2() {
    var topPage = open("/en-US/", TopPage.class);
    var loginPage = topPage.goToLoginPage();
    var myPage = loginPage.doLogin("clark@example.com", "password");
    var plansPage = myPage.goToPlansPage();
    plansPage.openPlanByTitle("Premium plan");
    switchTo().window("Reservation | HOTEL PLANISPHERE - Website for Practice Test Automation");
    var reservePage = page(ReservePage.class);

    var expectedStart = LocalDate.now().plusDays(90);
    var expectedEnd = LocalDate.now().plusDays(92);
    String expectedTotalBill;
    if (expectedStart.getDayOfWeek() == SATURDAY) {
      expectedTotalBill = "Total $1,120.00 (included taxes)";
    } else if (expectedStart.getDayOfWeek() == SUNDAY || expectedStart.getDayOfWeek() == FRIDAY) {
      expectedTotalBill = "Total $1,020.00 (included taxes)";
    } else {
      expectedTotalBill = "Total $920.00 (included taxes)";
    }
    final var expectedTerm = LONG_FORMATTER.format(expectedStart) + " - " + LONG_FORMATTER.format(expectedEnd) + ". 2 night(s)";

    reservePage.setReserveDate(SHORT_FORMATTER.format(expectedStart));
    reservePage.setReserveTerm("2");
    reservePage.setHeadCount("4");
    reservePage.setBreakfastPlan(true);
    reservePage.setEarlyCheckInPlan(true);
    reservePage.setSightseeingPlan(false);
    reservePage.setContact("By email");
    reservePage.setComment("aaa\n\nbbbbbbb\ncc");
    var confirmPage = reservePage.submit();

    confirmPage.getTotalBill().shouldHave(exactText(expectedTotalBill));
    confirmPage.getPlanName().shouldHave(exactText("Premium plan"));
    confirmPage.getTerm().shouldHave(exactText(expectedTerm));
    confirmPage.getHeadCount().shouldHave(exactText("4 person(s)"));
    confirmPage.getPlans().shouldHave(text("Breakfast"));
    confirmPage.getPlans().shouldHave(text("Early check-in"));
    confirmPage.getPlans().shouldNotHave(text("Sightseeing"));
    confirmPage.getUsername().shouldHave(exactText("Clark Evans"));
    confirmPage.getContact().shouldHave(exactText("Email: clark@example.com"));
    confirmPage.getComment().shouldHave(exactText("aaa\n\nbbbbbbb\ncc"));

    confirmPage.confirm();
    confirmPage.getModalMessage().shouldHave(exactText("We look forward to visiting you."));
    confirmPage.close();
    assertTrue(Wait().until(ExpectedConditions.numberOfWindowsToBe(1)));
  }

}
