package com.projet.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Mohamed on 26/12/2016.
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING,name = "order_type",length = 2)
public class Ordre implements Serializable {
    private static final long serialVersionUID = 1L;
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    private int nbrAction;
    private double prix;

    public Ordre(Date date, int nbrAction, double prix) {
        this.date = date;
        this.nbrAction = nbrAction;
        this.prix = prix;
    }

    @ManyToOne
    @JoinColumn(name="Id_Societe")
    private Societe societe;

    public Ordre() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getNbrAction() {
        return nbrAction;
    }

    public void setNbrAction(int nbrAction) {
        this.nbrAction = nbrAction;
    }
    @JsonIgnore
    public Societe getSociete() {
        return societe;
    }

    public void setSociete(Societe societe) {
        this.societe = societe;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }
    @Transient
    public String getDiscriminatorValue(){
        DiscriminatorValue val = this.getClass().getAnnotation( DiscriminatorValue.class );

        return val == null ? null : val.value();
    }
}
