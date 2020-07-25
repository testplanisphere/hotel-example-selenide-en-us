package hotel;

import static com.codeborne.selenide.CollectionCondition.*;
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
@DisplayName("Plans")
class PlansTest {

  @AfterEach
  void tearDown() {
    clearBrowserCookies();
  }

  @Test
  @Order(1)
  @DisplayName("It should be display plans when not logged in")
  void testPlanListNotLogin() {
    var topPage = open("/en-US/", TopPage.class);
    var plansPage = topPage.goToPlansPage();
    var plans = plansPage.getPlans();

    plans.shouldHave(size(7));
    plans.get(0).shouldHave(exactText("Plan with special offers"));
    plans.get(1).shouldHave(exactText("Staying without meals"));
    plans.get(2).shouldHave(exactText("Business trip"));
    plans.get(3).shouldHave(exactText("With beauty salon"));
    plans.get(4).shouldHave(exactText("With private onsen"));
    plans.get(5).shouldHave(exactText("For honeymoon"));
    plans.get(6).shouldHave(exactText("With complimentary ticket"));
  }

  @Test
  @Order(2)
  @DisplayName("It should be display plans when logged in member")
  void testPlanListLoginNormal() {
    var topPage = open("/en-US/", TopPage.class);
    var loginPage = topPage.goToLoginPage();
    var myPage = loginPage.doLogin("diana@example.com", "pass1234");
    var plansPage = myPage.goToPlansPage();
    var plans = plansPage.getPlans();

    plans.shouldHave(size(9));
    plans.get(0).shouldHave(exactText("Plan with special offers"));
    plans.get(1).shouldHave(exactText("With dinner"));
    plans.get(2).shouldHave(exactText("Economical"));
    plans.get(3).shouldHave(exactText("Staying without meals"));
    plans.get(4).shouldHave(exactText("Business trip"));
    plans.get(5).shouldHave(exactText("With beauty salon"));
    plans.get(6).shouldHave(exactText("With private onsen"));
    plans.get(7).shouldHave(exactText("For honeymoon"));
    plans.get(8).shouldHave(exactText("With complimentary ticket"));
  }

  @Test
  @Order(3)
  @DisplayName("It should be display plans when logged in premium member")
  void testPlanListLoginPremium() {
    var topPage = open("/en-US/", TopPage.class);
    var loginPage = topPage.goToLoginPage();
    var myPage = loginPage.doLogin("clark@example.com", "password");
    var plansPage = myPage.goToPlansPage();
    var plans = plansPage.getPlans();

    plans.shouldHave(size(10));
    plans.get(0).shouldHave(exactText("Plan with special offers"));
    plans.get(1).shouldHave(exactText("Premium plan"));
    plans.get(2).shouldHave(exactText("With dinner"));
    plans.get(3).shouldHave(exactText("Economical"));
    plans.get(4).shouldHave(exactText("Staying without meals"));
    plans.get(5).shouldHave(exactText("Business trip"));
    plans.get(6).shouldHave(exactText("With beauty salon"));
    plans.get(7).shouldHave(exactText("With private onsen"));
    plans.get(8).shouldHave(exactText("For honeymoon"));
    plans.get(9).shouldHave(exactText("With complimentary ticket"));
  }

}
