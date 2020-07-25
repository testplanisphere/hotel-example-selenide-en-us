package hotel.pages;

import static com.codeborne.selenide.Selenide.*;

import com.codeborne.selenide.SelenideElement;

public class MyPage implements NavigationBar {

  public SelenideElement getHeader() {
    return $("h2");
  }

  public IconPage goToIconPage() {
    $("#icon-link").click();
    return page(IconPage.class);
  }

  public SelenideElement getEmail() {
    return $("#email");
  }

  public SelenideElement getUsername() {
    return $("#username");
  }

  public SelenideElement getRank() {
    return $("#rank");
  }

  public SelenideElement getAddress() {
    return $("#address");
  }

  public SelenideElement getTel() {
    return $("#tel");
  }

  public SelenideElement getGender() {
    return $("#gender");
  }

  public SelenideElement getBirthday() {
    return $("#birthday");
  }

  public SelenideElement getNotification() {
    return $("#notification");
  }

  public SelenideElement getIconImage() {
    return $("#icon-holder > img");
  }

  public void deleteUser() {
    $("#delete-form > button").click();
  }

}
