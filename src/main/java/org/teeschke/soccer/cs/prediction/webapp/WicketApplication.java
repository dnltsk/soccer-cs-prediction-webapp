package org.teeschke.soccer.cs.prediction.webapp;

import com.giffing.wicket.spring.boot.starter.app.WicketBootWebApplication;
import com.giffing.wicket.spring.boot.starter.context.WicketSpringBootApplication;
import org.apache.wicket.Page;
import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.markup.html.WebPage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * Application object for your web application.
 * If you want to run this application without deploying, run the Start class.
 *
 */
@WicketSpringBootApplication
public class WicketApplication extends WicketBootWebApplication {

  @Value("${db.pass}")
  public String DB_PASS;

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
