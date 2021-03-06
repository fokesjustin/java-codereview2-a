import org.fluentlenium.adapter.FluentTest;
import org.junit.ClassRule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import static org.fluentlenium.core.filter.FilterConstructor.*;
import static org.assertj.core.api.Assertions.assertThat;

public class AppTest extends FluentTest {
  public WebDriver webDriver = new HtmlUnitDriver();

  @Override
  public WebDriver getDefaultDriver() {
    return webDriver;
  }

  @ClassRule
  public static ServerRule server = new ServerRule();


  @Test
  public void rootTest() {
    goTo("http://localhost:4567/");
    assertThat(pageSource()).contains("Dictionary");
  }

  @Test
  public void WordIsCreatedTest() {
    goTo("http://localhost:4567/");
    click("a", withText("Add a New Word"));
    fill("#word").with("Code");
    submit(".btn");
    assertThat(pageSource()).contains("Your word has been saved.");
  }

  @Test
  public void WordIsDisplayedTest() {
    goTo("http://localhost:4567/words/new");
    fill("#word").with("Code");
    submit(".btn");
    click("a", withText("View Words"));
    assertThat(pageSource()).contains("Code");
  }

  @Test
  public void wordDefinitionFormIsDisplayed() {
    goTo("http://localhost:4567/words/new");
    fill("#word").with("Code");
    submit(".btn");
    click("a", withText("View Words"));
    click("a", withText("Code"));
    click("a", withText("Add a new definition"));
    assertThat(pageSource()).contains("Add a definition to the word Code");
  }

  @Test
  public void DefinitionIsAddedAndDisplayed() {
    goTo("http://localhost:4567/words/new");
    fill("#word").with("Happiness");
    submit(".btn");
    click("a", withText("View Words"));
    click("a", withText("Happiness"));
    click("a", withText("Add a new definition"));
    fill("#definition").with("Goodness");
    submit(".btn");
    click("a", withText("View Words"));
    click("a", withText("Happiness"));
    assertThat(pageSource()).contains("Goodness");
  }
}
