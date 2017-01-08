package com.projet.metier;

import com.projet.dao.SocieteRepository;
import com.projet.entities.Societe;
import com.projet.service.RabbitService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * Created by Mohamed on 26/12/2016.
 */
@Service()
@Component()
public class MetierSociete implements  IMetierSociete {
    @Autowired
    private RabbitService rabbitService;
    @Autowired
    private SocieteRepository societeRepository;
    @Autowired
    private IMetierOrdre metierOrdre;
    @Override
    public Societe saveSociete(Societe societe) {
        if(societe != null){
            rabbitService.notifyNouveauSociete(societe);
            societeRepository.save(societe);
        }
        return societe;
    }

    @Override
    public Societe updateSociete(Societe societe) {
        if(societe != null){
             return societeRepository.saveAndFlush(societe);
        }
        return null;
    }

    @Override
    public boolean removeSociete(String id) {
        if(id != null) {
            societeRepository.delete(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean societeExiste(String id) {
        if(id!=null){
            if(societeRepository.findOne(id) !=null){
                return true;
            }
        }
        return false;
    }

    @Override
    public void SaveSocietesJsoup() {
        Document doc;
        try {
            doc = Jsoup.connect("https://www.wafabourse.com/marches/actions/r").get();
            Elements societes = doc.select("td.longNameQS a");
            String id;
            for (Element societe : societes) {
                id=societe.attr("href").split("/")[3];
                if( !societeExiste(id.trim())){
                    saveSociete(new Societe(societe.attr("href").split("/")[3], societe.text()));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   //@Scheduled(fixedRate = 500000)
    public void SocieteWithOrderWafaBANK(){
        SaveSocietesJsoup();
        List<Societe> s=societeRepository.findAll();
        for (Societe societe : s) {
            metierOrdre.ajouterOrdersBySociete(societe.getIdSociete().trim());
        }

    }

}
