package com.example.jpos_server.config;

import com.example.jpos_server.domain.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

@Configuration
public class DataRestConfig {
    @Bean
    public RepositoryRestConfigurer repositoryRestConfigurer(){
        return RepositoryRestConfigurer.withConfig((config, cors) ->
                config
                        .exposeIdsFor(Store.class)
                        .exposeIdsFor(Menu.class)
                        .exposeIdsFor(PosOrder.class)
                        .exposeIdsFor(Seat.class)
                        .exposeIdsFor(Category.class)
        );
    }
}
