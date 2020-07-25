package hotel.pages;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

import com.codeborne.selenide.ElementsCollection;

public class PlansPage implements NavigationBar {

  public ElementsCollection getPlans() {
    $("#plan-list > div[role=\"status\"]").waitUntil(disappears, 10000);
    return $$(".card-title");
  }

  public void openPlanByTitle(String title) {
    $("#plan-list > div[role=\"status\"]").waitUntil(disappears, 10000);
    for (var elm : $$(".card")) {
      if (title.equals(elm.find(".card-title").getText())) {
        elm.find("a").click();
        break;
      }
    }
  }

}
