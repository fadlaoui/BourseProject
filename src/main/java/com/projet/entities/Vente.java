package com.projet.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Mohamed on 26/12/2016.
 */
@Entity
@DiscriminatorValue("VE")
public class Vente  extends Ordre implements Serializable{
    private static final long serialVersionUID = 1L;
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Vente(Date date, int nbrAction, double prix) {
        super(date, nbrAction, prix);
    }

    public Vente() {
    }
    @Transient
    public String getDiscriminatorValue(){
        DiscriminatorValue val = this.getClass().getAnnotation( DiscriminatorValue.class );

        return val == null ? null : val.value();
    }
}
