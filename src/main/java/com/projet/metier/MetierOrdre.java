package com.projet.metier;

import com.projet.dao.OrdreRepository;
import com.projet.dao.SocieteRepository;
import com.projet.entities.Achat;
import com.projet.entities.Ordre;
import com.projet.entities.Societe;
import com.projet.entities.Vente;
import com.projet.service.RabbitService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Mohamed on 26/12/2016.
 */
@Service()
@Component()
public class MetierOrdre implements IMetierOrdre {
    @Autowired
    private RabbitService rabbitService;
    @Autowired
    private SocieteRepository societeRepository;
    @Autowired
    private OrdreRepository ordreRepository;


    @Override
    public Ordre saveOrdre(Ordre ordre) {
            if(ordre !=null){
                return ordreRepository.save(ordre);
            }
        return null;
    }

    @Override
    public Ordre updateOrdre(Ordre ordre) {
        if(ordre !=null){
            return ordreRepository.saveAndFlush(ordre);
        }
        return null;    }

    @Override
    public boolean removeOrdre(Long id) {
        if(id!=null){
            if(ordreRepository.findOne(id) !=null){
                return true;
            }
        }
        return false;
    }

    @Override
    public void ajouterOrdersBySociete(String code) {
        Document doc;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/mm/yyyy HH:mm");
        try {
            doc = Jsoup.connect("https://www.wafabourse.com/trader/market/"+code+"/XCAS/ISIN").get();
            Elements stes = doc.select("table.tab_ordres");
            Date date = simpleDateFormat.parse(doc.select("#instrLAST_PRICE_DATE").text());
            String str= stes.text();
            NumberFormat nf = NumberFormat.getInstance(Locale.FRENCH);

            for (Element ste : stes) {
                str=ste.select("th.th_achat").text();
                if(str.equals("Achat")){
                    Elements li=ste.select("tr");
                    for(int i=3;i<li.size();i++){

                        if(li.get(i).select("td.bidVolume").text().trim().isEmpty()==false ){
                            if(li.get(i).select("td.bidVolume").text().trim().charAt(0)!='-')
                            {
                                System.out.printf("[ QTE :"+nf.parse(li.get(i).select("td.bidVolume").text()).shortValue() +" | ");
                               System.out.println(nf.parse(li.get(i).select("td.bidPrice").text()).doubleValue()+" DH]");
                                Societe societe=societeRepository.findOne(code.trim());
                                Achat achat = new Achat(date,nf.parse(li.get(i).select("td.bidVolume").text()).shortValue(),nf.parse(li.get(i).select("td.bidPrice").text()).doubleValue());
                                achat.setSociete(societe);
                                saveOrdre(achat);
                                rabbitService.notifyNouveauAchat(achat);
                            }}
                    }
                }
                else if(ste.select("th.th_vente").text().equals("Vente")){
                    Elements li=ste.select("tr");
                    for(int i=3;i<li.size();i++){
                        if(li.get(i).select("td.askPrice").text().isEmpty()==false ){
                            if( li.get(i).select("td.askPrice").text().charAt(0)!='-')
                            {
                                System.out.printf("[QTE :"+nf.parse(li.get(i).select("td.askVolume").text()).shortValue() +" | ");
                              System.out.println(nf.parse(li.get(i).select("td.askPrice").text()).doubleValue()+" DH]");
                                Societe societe=societeRepository.findOne(code.trim());
                                Vente vente = new Vente(date,nf.parse(li.get(i).select("td.askVolume").text()).shortValue(),nf.parse(li.get(i).select("td.askPrice").text()).doubleValue());
                                vente.setSociete(societe);
                                saveOrdre(vente);
                                rabbitService.notifyNouveauVente(vente);
                            }}
                    }
                }

            }
        } catch (IOException | ParseException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }
}
