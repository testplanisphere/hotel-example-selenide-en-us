package hotel.pages;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

import com.codeborne.selenide.SelenideElement;

public class ConfirmPage {

  public SelenideElement getTotalBill() {
    return $("#total-bill");
  }

  public SelenideElement getPlanName() {
    return $("#plan-name");
  }

  public SelenideElement getTerm() {
    return $("#term");
  }

  public SelenideElement getHeadCount() {
    return $("#head-count");
  }

  public SelenideElement getPlans() {
    return $("#plans");
  }

  public SelenideElement getUsername() {
    return $("#username");
  }

  public SelenideElement getContact() {
    return $("#contact");
  }

  public SelenideElement getComment() {
    return $("#comment");
  }

  public void confirm() {
    $("button[data-target=\"#success-modal\"]").click();
    sleep(2000);
    $("#success-modal").waitUntil(appears, 10000);
  }

  public SelenideElement getModalMessage() {
    return $("#success-modal > div > div > .modal-body");
  }

  public void close() {
    $("#success-modal > div > div > div > button.btn-success").click();
  }

}
