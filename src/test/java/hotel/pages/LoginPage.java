package hotel.pages;

import static com.codeborne.selenide.Selenide.*;

import com.codeborne.selenide.SelenideElement;

public class LoginPage implements NavigationBar {

  public MyPage doLogin(String email, String password) {
    $("#email").setValue(email);
    $("#password").setValue(password);
    $("#login-button").click();
    return page(MyPage.class);
  }

  public SelenideElement getEmailMessage() {
    return $("#email-message");
  }

  public SelenideElement getPasswordMessage() {
    return $("#password-message");
  }

}
