package hotel.pages;

import static com.codeborne.selenide.Selenide.*;

import com.codeborne.selenide.SelenideElement;

public class RoomPage {

  public SelenideElement getHeader() {
    return $("h5");
  }
}
