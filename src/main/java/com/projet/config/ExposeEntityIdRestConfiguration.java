package com.projet.config;

/**
 * Created by Mohamed on 15/12/2016.
 */
import com.projet.entities.Achat;
import com.projet.entities.Ordre;
import com.projet.entities.Societe;
import com.projet.entities.Vente;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import org.springframework.stereotype.Component;


@Configuration
@Component
public class ExposeEntityIdRestConfiguration extends RepositoryRestConfigurerAdapter {
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(Societe.class);
        config.exposeIdsFor(Ordre.class);
        config.exposeIdsFor(Vente.class);
        config.exposeIdsFor(Achat.class);

    }
}
