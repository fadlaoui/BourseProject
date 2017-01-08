package com.projet.service;

import com.projet.dao.OrdreRepository;
import com.projet.dao.SocieteRepository;
import com.projet.entities.Achat;
import com.projet.entities.Ordre;
import com.projet.entities.Societe;
import com.projet.entities.Vente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

/**
 * Created by Mohamed on 26/12/2016.
 */
@RestController
public class RestService {
    @Autowired
    private RabbitService rabbitService;
    @Autowired
    private SocieteRepository societeRepository;
    @Autowired
    private OrdreRepository ordreRepository;

    @RequestMapping(value = "findSociete", method = RequestMethod.GET)
    @ResponseBody
    public Societe findSociete(@RequestParam("code") String code) {
        if (code != null) {
            try {
                Societe societe = societeRepository.findOne(code);

                return societe;

            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }


    @RequestMapping(value = "moyenneVente", method = RequestMethod.GET)
    @ResponseBody

    public Double moyenneVente(@RequestParam("code") String code) {
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

    @RequestMapping(value = "moyenneAchat", method = RequestMethod.GET)
    @ResponseBody
    public Double moyenneAchat(@RequestParam("code") String code) {
        if (code != null) {
            return ordreRepository.moyenneAchat(code);
        }
        return null;
    }

    @RequestMapping(value = "totalVente", method = RequestMethod.GET)
    @ResponseBody

    public Double totalVente(@RequestParam("code") String code) {
        if (code != null) {
           double res =  ordreRepository.totalVente(code);
            System.out.println(res);
            return res;
        }
        return null;
    }

    @RequestMapping(value = "totalAchat", method = RequestMethod.GET )
    @ResponseBody
    public Double totalAchat(@RequestParam("code") String code) {
        if (code != null) {
            return ordreRepository.totalAchat(code);
        }
        return null;
    }

    @RequestMapping(value = "findOrdersByPage", method = RequestMethod.GET)
    @ResponseBody
    public Page<Ordre> findByPage(@RequestParam("code") String code,@RequestParam("page") int page, @RequestParam("size") int size) {
        return ordreRepository.findOrdreBySoc(code,new PageRequest(page, size));
    }

    @RequestMapping(value = "findSocietes", method = RequestMethod.GET)
    @ResponseBody
    public Page<Societe> findSocietes(@RequestParam("page") int page, @RequestParam("size") int size) {
        return societeRepository.findSocietes(new PageRequest(page, size));
    }

    @RequestMapping(value = "saveOrdre", method = RequestMethod.POST)
    @ResponseBody
    public Ordre saveOrdre(@RequestBody Ordre ordre ,@RequestParam("code") String code ,@RequestParam("type") String type ) {
        Societe socitete=societeRepository.findOne(code);
        Ordre ordre1 = null;
        if(type.equals("AC")){
              ordre1= new Achat();
            ordre1.setDate(ordre.getDate());
            ordre1.setNbrAction(ordre.getNbrAction());
            ordre1.setPrix(ordre.getPrix());
            ordre1.setSociete(socitete);
            rabbitService.notifyNouveauAchat(ordre1);
        }else {

            ordre1= new Vente();
            ordre1.setDate(ordre.getDate());
            ordre1.setNbrAction(ordre.getNbrAction());
            ordre1.setPrix(ordre.getPrix());
            ordre1.setSociete(socitete);
            rabbitService.notifyNouveauVente(ordre1);
        }
        return ordreRepository.save(ordre1);
    }


    @RequestMapping(value = "saveSociete", method = RequestMethod.POST)
    public Societe saveSociete(@RequestBody Societe societe  ) {

        if(societe!=null) {
            rabbitService.notifyNouveauSociete(societe);

            return societeRepository.save(societe);
        }
        return null;
    }
}
