package com.projet.metier;

import com.projet.entities.Achat;
import com.projet.entities.Ordre;
import com.projet.entities.Societe;
import com.projet.entities.Vente;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Created by Mohamed on 30/12/2016.
 */
public interface IMetierRMI extends Remote {
    public Societe searchSociete(String code) throws RemoteException;
    public Double moyenneVente(String code)throws RemoteException;
    public Double moyenneAchat(String code)throws RemoteException;
    public Double totalVente(String code)throws RemoteException;
    public Double totalAchat(String code)throws RemoteException;
    List<Ordre>  OrdersSoceite(String code)throws RemoteException;
    public List<Societe> findAllSocietes() throws RemoteException;
    public List<Achat> findAchats(String idSociete) throws RemoteException ;
    public List<Vente> findVentes(String idSociete) throws RemoteException;
    public Societe findSociete(String id) throws RemoteException;
}

