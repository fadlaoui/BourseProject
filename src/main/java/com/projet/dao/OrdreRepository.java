package com.projet.dao;

import com.projet.entities.Achat;
import com.projet.entities.Ordre;
import com.projet.entities.Vente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.rmi.registry.LocateRegistry;
import java.util.List;

/**
 * Created by Mohamed on 26/12/2016.
 */
@RepositoryRestResource
public interface OrdreRepository extends JpaRepository<Ordre,Long> {
    @Query(value = "select o from Ordre o where o.societe.idSociete =:code")
    Page<Ordre> findOrdreBySoc(@Param("code") String code , Pageable pageable);
    @Query(value = "select sum(o.prix) from Ordre o where o.societe.idSociete =:code and o.class = Vente")
    Double totalVente(@Param("code") String code);
    @Query(value = "select sum(o.prix) from Ordre o where o.societe.idSociete =:code and o.class = Achat")
    Double totalAchat(@Param("code") String code);


    @Query(value = "select avg(o.prix) from Ordre o where o.societe.idSociete =:code and o.class = Achat")
    Double moyenneAchat(@Param("code") String code);
    @Query(value = "select avg(o.prix) from Ordre o where o.societe.idSociete =:code and o.class = Vente")
    Double moyenneVente(@Param("code") String code);
    @Query(value = "select o from Ordre o where o.societe.idSociete =:code")
    List<Ordre> findOrdreBySoclist(@Param("code") String code);

    @Query(value = "select o from Ordre o where o.societe.idSociete =:code and o.class = Vente")
    List<Vente> findVentes(@Param("code") String code);

    @Query(value = "select o from Ordre o where o.societe.idSociete =:code and o.class = Achat")
    List<Achat> findAchats(@Param("code") String code);

}
