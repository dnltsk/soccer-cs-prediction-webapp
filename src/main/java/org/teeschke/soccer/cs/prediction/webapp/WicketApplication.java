package org.teeschke.soccer.cs.prediction.webapp;

import com.giffing.wicket.spring.boot.starter.app.WicketBootWebApplication;
import com.giffing.wicket.spring.boot.starter.context.WicketSpringBootApplication;
import org.apache.wicket.Page;
import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.markup.html.WebPage;
import org.springframework.boot.builder.SpringApplicationBuilder;

@WicketSpringBootApplication
public class WicketApplication extends WicketBootWebApplication {

  public static void main(String[] args) throws Exception {
    new SpringApplicationBuilder()
        .sources(WicketApplication.class)
        .run(args);
  }

  @Override
  public RuntimeConfigurationType getConfigurationType() {
    return RuntimeConfigurationType.DEVELOPMENT;
  }

  @Override
  public Class<? extends Page> getHomePage() {
    return HomePage.class;
  }

  @Override
  protected Class<? extends WebPage> getSignInPageClass() {
    return null;
  }
}
