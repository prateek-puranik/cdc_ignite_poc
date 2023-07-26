package com.example.IgniteTrial.config;

import com.example.IgniteTrial.model.company_3;
import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.spi.communication.tcp.TcpCommunicationSpi;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import java.util.concurrent.Executor;

@Configuration
@ComponentScan(basePackages = {"com.example.easynotes"})
public class ListnerConfig extends AsyncConfigurerSupport {


    /**
     * Bean to Create an Ignite Client Node and set up the Cache Configuration
     */

    @Bean(name="igniteInstance")
    public Ignite igniteInstance(){
        CacheConfiguration<Integer, company_3>  ccfg=new CacheConfiguration<>("Cache");//Name of the Cache
        ccfg.setCacheMode(CacheMode.REPLICATED);//Can be Partitoned
        ccfg.setIndexedTypes(Integer.class, company_3.class);//The key and value classes need to be specified
        ccfg.setSqlSchema("Schema");//The Name of the Schema generted, if unused Schema will be set as Cache Name

        IgniteConfiguration cfg=new IgniteConfiguration();
        cfg.setClientMode(true);                              //To open Ignite Thick Client
        cfg.setIgniteInstanceName("Client Embedded Node");
        Ignite ignite=Ignition.start(cfg);                   //Starts the ignite node
        ignite.getOrCreateCache(ccfg);                       //Create cache based on the Cache Configuration Set above
        return ignite;
    }


    /**
     *Function to set the Thread Pool Parameters(Used in Async Functions
     */

    @Override
    @Bean("Async")
    public Executor getAsyncExecutor()
    {
        ThreadPoolTaskExecutor executor=new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("CDC Async-");
        executor.initialize();
        return executor;

    }


}
