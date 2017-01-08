package com.projet.dao;

import com.projet.entities.Ordre;
import com.projet.entities.Societe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by Mohamed on 26/12/2016.
 */
@RepositoryRestResource
public interface SocieteRepository extends JpaRepository<Societe,String> {
    @Query(value = "select s from Societe s")
    Page<Societe> findSocietes(Pageable pageable);
}
