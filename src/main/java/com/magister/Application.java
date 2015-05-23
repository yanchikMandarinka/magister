package com.magister;

import java.util.concurrent.Callable;

import org.h2.server.web.WebServlet;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import com.magister.db.domain.Mote;
import com.magister.db.domain.Network;
import com.magister.network.service.NetworkCallableFactory;
import com.magister.network.service.auto.AutomaticNetwork;
import com.magister.network.service.auto.MoteRunable;
import com.magister.network.service.auto.MoteRunnableFactory;

@SpringBootApplication
public class Application {

    @Bean
    public ServletRegistrationBean h2servletRegistration() {
        ServletRegistrationBean registration = new ServletRegistrationBean(new WebServlet());
        registration.addUrlMappings("/console/*");
        return registration;
    }

    @Bean
    public NetworkCallableFactory automaticNetworkFactory() {
        return new NetworkCallableFactory() {

            @Override
            public Callable<Boolean> createNetworkCallable(Network network) {
                switch(network.getMode()) {
                    case AUTOMATIC: {
                        return automaticNetwork(network);
                    }
                    case MANUAL: {
                        return automaticNetwork(network);
                    }
                    default: {
                        throw new UnsupportedOperationException("Unknown sensor network mode");
                    }
                }
            }
        };
    }

    @Bean
    public MoteRunnableFactory nodeRunnableFactory() {
        return new MoteRunnableFactory() {

            @Override
            public Runnable createMoteRunnable(Mote mote) {
                return moteRunnable(mote);
            }
        };
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Runnable moteRunnable(Mote mote) {
        return new MoteRunable(mote);
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Callable<Boolean> automaticNetwork(Network network) {
        return new AutomaticNetwork(network);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
