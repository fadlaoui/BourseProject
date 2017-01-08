package com.projet.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Mohamed on 26/12/2016.
 */
@Entity
public class Societe  implements Serializable{
    private static final long serialVersionUID = 1L;
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    @Id
    private  String idSociete;
    private String nameSociete;
    @OneToMany(mappedBy = "societe",fetch = FetchType.LAZY)
    private List <Ordre> ordres;

    public Societe() {
    }

    public Societe(String idSociete, String nameSociete) {
        this.idSociete = idSociete;
        this.nameSociete = nameSociete;
    }

    public String getIdSociete() {
        return idSociete;
    }

    public void setIdSociete(String idSociete) {
        this.idSociete = idSociete;
    }

    public String getNameSociete() {
        return nameSociete;
    }

    public void setNameSociete(String nameSociete) {
        this.nameSociete = nameSociete;
    }

    public List<Ordre> getOrdres() {
        return ordres;
    }

    public void setOrdres(List<Ordre> ordres) {
        this.ordres = ordres;
    }
}
