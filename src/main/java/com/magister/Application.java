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
import com.magister.network.service.MoteRunnableFactory;
import com.magister.network.service.NetworkCallableFactory;
import com.magister.network.service.auto.AutomaticNetwork;
import com.magister.network.service.auto.MoteRunable;
import com.magister.network.service.manual.ManualMoteRunable;
import com.magister.network.service.manual.ManualNetwork;

@SpringBootApplication(exclude = ErrorMvcAutoConfiguration.class)
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
                switch (network.getMode()) {
                    case AUTOMATIC: {
                        return automaticNetwork(network);
                    }
                    case MANUAL: {
                        return manualNetwork(network);
                    }
                    default: {
                        throw new UnsupportedOperationException("Unknown sensor network mode");
                    }
                }
            }
        };
    }

    @Bean(name="moteRunnableFactory")
    public MoteRunnableFactory moteRunnableFactory() {
        return new MoteRunnableFactory() {

            @Override
            public Runnable createMoteRunnable(Mote mote, Network network) {
                return moteRunnable(mote);
            }
        };
    }

    @Bean(name="manualMoteRunnableFactory")
    public MoteRunnableFactory manualMoteRunnableFactory() {
        return new MoteRunnableFactory() {

            @Override
            public Runnable createMoteRunnable(Mote mote, Network network) {
                return manualMoteRunnable(mote, network);
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
    public Runnable manualMoteRunnable(Mote mote, Network network) {
        return new ManualMoteRunable(mote, network);
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Callable<Boolean> automaticNetwork(Network network) {
        return new AutomaticNetwork(network);
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Callable<Boolean> manualNetwork(Network network) {
        return new ManualNetwork(network);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
