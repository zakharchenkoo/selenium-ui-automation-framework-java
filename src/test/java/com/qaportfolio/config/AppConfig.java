package com.qaportfolio.config;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.ConfigFactory;

@Config.Sources("classpath:config/app.properties")
public interface AppConfig extends Config {

    static AppConfig getInstance() {
        return ConfigFactory.create(AppConfig.class, System.getProperties());
    }

    @Key("base.url")
    @DefaultValue("https://demoqa.com")
    String baseUrl();

    @Key("browser")
    @DefaultValue("chrome")
    String browser();

    @Key("run.mode")
    @DefaultValue("local")
    String runMode();

    @Key("remote.url")
    @DefaultValue("http://localhost:4444/wd/hub")
    String remoteUrl();

    @Key("remote.provider")
    @DefaultValue("selenium-grid")
    String remoteProvider();

    @Key("explicit.wait")
    @DefaultValue("10")
    int explicitWait();

    @Key("implicit.wait")
    @DefaultValue("0")
    int implicitWait();

    @Key("headless")
    @DefaultValue("false")
    boolean headless();
}
