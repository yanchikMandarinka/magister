package com.magister;

import java.util.concurrent.Callable;

import org.h2.server.web.WebServlet;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import com.magister.db.domain.Mote;
import com.magister.db.domain.Network;
import com.magister.network.service.MoteRunable;
import com.magister.network.service.MoteRunnableFactory;
import com.magister.network.service.NetworkCallable;
import com.magister.network.service.NetworkCallableFactory;

@SpringBootApplication(exclude = ErrorMvcAutoConfiguration.class)
public class Application {

    @Bean
    public ServletRegistrationBean h2servletRegistration() {
        ServletRegistrationBean registration = new ServletRegistrationBean(new WebServlet());
        registration.addUrlMappings("/console/*");
        return registration;
    }

    @Bean
    public NetworkCallableFactory networkCallableFactory() {
        return new NetworkCallableFactory() {

            @Override
            public Callable<Boolean> createNetworkCallable(Network network) {
                return networkCallable(network);
            }
        };
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Callable<Boolean> networkCallable(Network network) {
        return new NetworkCallable(network);
    }

    @Bean
    public MoteRunnableFactory moteRunnableFactory() {
        return new MoteRunnableFactory() {

            @Override
            public Runnable createMoteRunnable(Mote mote, Network network) {
                return moteRunnable(mote, network);
            }
        };
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Runnable moteRunnable(Mote mote, Network network) {
        return new MoteRunable(mote, network);
    }


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
