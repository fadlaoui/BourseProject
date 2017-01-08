package com.projet.metier;

import com.projet.entities.Ordre;

/**
 * Created by Mohamed on 26/12/2016.
 */

public interface IMetierOrdre {
    public Ordre saveOrdre(Ordre ordre);
    public Ordre updateOrdre(Ordre ordre);
    public boolean removeOrdre(Long id);
    public void ajouterOrdersBySociete(String code);
}
