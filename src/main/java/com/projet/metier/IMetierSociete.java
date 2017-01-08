package com.projet.metier;

import com.projet.entities.Societe;

/**
 * Created by Mohamed on 26/12/2016.
 */
public interface IMetierSociete {
    public Societe saveSociete(Societe societe);
    public Societe updateSociete(Societe societe);
    public boolean removeSociete(String id);
    public boolean societeExiste(String id);
    public void SaveSocietesJsoup();
    public void SocieteWithOrderWafaBANK();
}
