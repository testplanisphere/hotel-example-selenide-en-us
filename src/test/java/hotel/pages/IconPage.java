package hotel.pages;

import static com.codeborne.selenide.Selenide.*;

import com.codeborne.selenide.SelenideElement;

public class IconPage {

  public void setIcon(String fileName) {
    $("#icon").uploadFromClasspath(fileName);
  }

  public void setZoom(int value) {
    executeJavaScript("arguments[0].value = arguments[1]", $("#zoom"), Integer.toString(value));
  }

  public void setColor(String color) {
    executeJavaScript("arguments[0].value = arguments[1]", $("#color"), color);
  }

  public MyPage submit() {
    $("#icon-form > button").click();
    return page(MyPage.class);
  }

  public SelenideElement getIconMessage() {
    return $("#icon ~ .invalid-feedback");
  }

}
