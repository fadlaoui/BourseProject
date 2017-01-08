package com.projet.metier;

import com.projet.dao.OrdreRepository;
import com.projet.dao.SocieteRepository;
import com.projet.entities.Achat;
import com.projet.entities.Ordre;
import com.projet.entities.Societe;
import com.projet.entities.Vente;
import org.springframework.beans.factory.annotation.Autowired;

import java.rmi.RemoteException;
import java.util.List;

/**
 * Created by Mohamed on 30/12/2016.
 */
public class ServiceRMImp implements IMetierRMI {
    @Autowired
    private SocieteRepository societeRepository;
    @Autowired
    private OrdreRepository ordreRepository;

    @Override
    public Societe searchSociete(String code)throws RemoteException {
        if(code != null)
        {
            return societeRepository.findOne(code);
        }
        return null;
    }

    @Override
    public Double moyenneVente(String code) throws RemoteException{
        if(code != null)
        {
            return ordreRepository.moyenneVente(code);
        }
        return null;
    }

    @Override
    public Double moyenneAchat(String code) throws RemoteException{
        if(code != null)
        {
            return ordreRepository.moyenneAchat(code);
        }
        return null;
    }

    @Override
    public Double totalVente(String code) throws RemoteException {
        if(code != null)
        {
            return ordreRepository.totalVente(code);
        }
        return null;
    }

    @Override
    public Double totalAchat(String code) throws RemoteException {
        if(code != null)
        {
            return ordreRepository.totalAchat(code);
        }
        return null;
    }
    @Override
    public List<Ordre> OrdersSoceite(String code) throws RemoteException {
        if(code != null)
        {
            return ordreRepository.findOrdreBySoclist(code);
        }
        return null;
    }

    @Override
    public List<Societe> findAllSocietes() throws RemoteException {

        return societeRepository.findAll();
    }

    @Override
    public List<Achat> findAchats(String idSociete) throws RemoteException {
        if(idSociete != null)
        {
            return ordreRepository.findAchats(idSociete);
        }
        return null;
    }

    @Override
    public List<Vente> findVentes(String idSociete) throws RemoteException {
        if(idSociete != null)
        {
            return ordreRepository.findVentes(idSociete);
        }
        return null;
    }

    @Override
    public Societe findSociete(String id) throws RemoteException {
        if (id != null) {
            return societeRepository.findOne(id);
        }
        return null;
    }
}
