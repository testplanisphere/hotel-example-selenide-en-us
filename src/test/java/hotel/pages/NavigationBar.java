package hotel.pages;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

public interface NavigationBar {

  default LoginPage goToLoginPage() {
    $(byLinkText("Login")).click();
    return page(LoginPage.class);
  }

  default SignupPage goToSignupPage() {
    $(byLinkText("Sign up")).click();
    return page(SignupPage.class);
  }

  default PlansPage goToPlansPage() {
    $(byLinkText("Reserve")).click();
    return page(PlansPage.class);
  }

}
