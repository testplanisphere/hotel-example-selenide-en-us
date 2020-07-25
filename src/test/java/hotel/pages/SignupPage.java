package hotel.pages;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

import com.codeborne.selenide.SelenideElement;
import java.time.LocalDate;

public class SignupPage implements NavigationBar {

  public enum Rank {
    PREMIUM("premium"), NORMAL("normal");

    private final String value;

    Rank(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }
  }

  public void setEmail(String email) {
    $("#email").setValue(email);
  }

  public void setPassword(String password) {
    $("#password").setValue(password);
  }

  public void setPasswordConfirmation(String password) {
    $("#password-confirmation").setValue(password);
  }

  public void setUsername(String username) {
    $("#username").setValue(username);
  }

  public void setRank(Rank rank) {
    $(byName("rank")).selectRadio(rank.getValue());
  }

  public void setAddress(String address) {
    $("#address").setValue(address);
  }

  public void setTel(String tel) {
    $("#tel").setValue(tel);
  }

  public void setGender(String gender) {
    $("#gender").selectOption(gender);
  }

  public void setBirthday(LocalDate birthday) {
    var input = birthday != null ? birthday.toString() : "";
    executeJavaScript("arguments[0].value = arguments[1]", $("#birthday"), input);
  }

  public void setNotification(boolean checked) {
    $("#notification").setSelected(checked);
  }

  public MyPage submit() {
    $("#signup-form > button").click();
    return page(MyPage.class);
  }

  public SelenideElement getEmailMessage() {
    return $("#email ~ .invalid-feedback");
  }

  public SelenideElement getPasswordMessage() {
    return $("#password ~ .invalid-feedback");
  }

  public SelenideElement getPasswordConfirmationMessage() {
    return $("#password-confirmation ~ .invalid-feedback");
  }

  public SelenideElement getUsernameMessage() {
    return $("#username ~ .invalid-feedback");
  }

  public SelenideElement getAddressMessage() {
    return $("#address ~ .invalid-feedback");
  }

  public SelenideElement getTelMessage() {
    return $("#tel ~ .invalid-feedback");
  }

  public SelenideElement getGenderMessage() {
    return $("#gender ~ .invalid-feedback");
  }

  public SelenideElement getBirthdayMessage() {
    return $("#birthday ~ .invalid-feedback");
  }
}
