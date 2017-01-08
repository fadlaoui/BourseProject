package com.projet.service;

import com.projet.dao.OrdreRepository;
import com.projet.dao.SocieteRepository;
import com.projet.entities.Ordre;
import com.projet.entities.Societe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.List;

/**
 * Created by Mohamed on 19/12/2016.
 */
@Component

@WebService

public class SoapService {

    @Autowired
    private SocieteRepository societeRepository;
    @Autowired
    private OrdreRepository ordreRepository;

    @WebMethod(operationName="moyenneVente")
    public Double moyenneVente(@WebParam(name="code") String code) {
        if (code != null) {
            try {
                System.out.println(code);
                double res = ordreRepository.moyenneVente(code);
                System.out.println(res);
                return res;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }

    @WebMethod(operationName="findSociete")
    public Societe findSociete(@WebParam(name="code") String code) {
        if (code != null) {
            try {
                System.out.println(code);
                return societeRepository.findOne(code);

            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }


    @WebMethod(operationName="moyenneAchat")
    public Double moyenneAchat(@WebParam(name="code") String code) {
        if (code != null) {
            return ordreRepository.moyenneAchat(code);
        }
        return null;
    }

    @WebMethod(operationName="totalVente")

    public Double totalVente(@WebParam(name="code") String code) {
        if (code != null) {
            double res =  ordreRepository.totalVente(code);
            System.out.println(res);
            return res;
        }
        return null;
    }

    @WebMethod(operationName="totalAchat")
    public Double totalAchat(@WebParam(name="code") String code) {
        if (code != null) {
            return ordreRepository.totalAchat(code);
        }
        return null;
    }

    @WebMethod(operationName="findByPage")
    public List<Ordre> findByPage(@WebParam(name="code") String code, @WebParam(name="page") int page, @WebParam(name="size") int size) {
        return ordreRepository.findOrdreBySoclist(code);
    }


}