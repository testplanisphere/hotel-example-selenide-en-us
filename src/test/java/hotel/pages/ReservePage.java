package hotel.pages;

import static com.codeborne.selenide.Selenide.*;

import com.codeborne.selenide.SelenideElement;

public class ReservePage {

  public void setReserveDate(String date) {
    $("#date").setValue(date);
    $(".ui-datepicker-close").click();
  }

  public void setReserveTerm(String term) {
    $("#term").setValue(term);
  }

  public void setHeadCount(String headCount) {
    $("#head-count").setValue(headCount);
  }

  public void setBreakfastPlan(boolean checked) {
    $("#breakfast").setSelected(checked);
  }

  public void setEarlyCheckInPlan(boolean checked) {
    $("#early-check-in").setSelected(checked);
  }

  public void setSightseeingPlan(boolean checked) {
    $("#sightseeing").setSelected(checked);
  }

  public void setUsername(String username) {
    $("#username").setValue(username);
  }

  public void setContact(String contact) {
    $("#contact").selectOption(contact);
  }

  public void setEmail(String email) {
    $("#email").setValue(email);
  }

  public void setTel(String tel) {
    $("#tel").setValue(tel);
  }

  public void setComment(String comment) {
    $("#comment").setValue(comment);
  }

  public ConfirmPage submit() {
    $("button[data-test=\"submit-button\"]").click();
    return page(ConfirmPage.class);
  }

  public SelenideElement getPlanName() {
    return $("#plan-name");
  }

  public SelenideElement getReserveDate() {
    return $("#date");
  }

  public SelenideElement getReserveTerm() {
    return $("#term");
  }

  public SelenideElement getHeadCount() {
    return $("#head-count");
  }

  public SelenideElement getUsername() {
    return $("#username");
  }

  public SelenideElement getEmail() {
    return $("#email");
  }

  public SelenideElement getTel() {
    return $("#tel");
  }

  public SelenideElement getReserveDateMessage() {
    return $("#date ~ .invalid-feedback");
  }

  public SelenideElement getReserveTermMessage() {
    return $("#term ~ .invalid-feedback");
  }

  public SelenideElement getHeadCountMessage() {
    return $("#head-count ~ .invalid-feedback");
  }

  public SelenideElement getUsernameMessage() {
    return $("#username ~ .invalid-feedback");
  }

  public SelenideElement getEmailMessage() {
    return $("#email ~ .invalid-feedback");
  }

  public SelenideElement getTelMessage() {
    return $("#tel ~ .invalid-feedback");
  }

}
