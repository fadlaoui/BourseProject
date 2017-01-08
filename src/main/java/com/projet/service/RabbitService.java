package com.projet.service;

import javax.annotation.PostConstruct;

import com.projet.entities.Achat;
import com.projet.entities.Ordre;
import com.projet.entities.Societe;
import com.projet.entities.Vente;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by Mohamed on 30/12/2016.
 */

@Service
@Component
public class RabbitService {
    private final static String QUEUE_NAME = "app_queue";

    private ConnectionFactory connectionFactory;
    private RabbitAdmin rabbitAdmin;
    private Queue queue;
    private RabbitTemplate rabbitTemplate;

    private ObjectMapper objectMapper;

    @PostConstruct
    public void intialize() {
        connectionFactory = new CachingConnectionFactory();
        rabbitAdmin = new RabbitAdmin(connectionFactory);
        queue = new Queue(QUEUE_NAME, false);
        rabbitAdmin.declareQueue(queue);
        rabbitTemplate = new RabbitTemplate(connectionFactory);
        objectMapper = new ObjectMapper();
    }

    public void notifyNouveauSociete(Societe societe) {
        try {
            String jsonString = objectMapper.writeValueAsString(societe);
            rabbitTemplate.convertAndSend(QUEUE_NAME,
                    "ajoutdeSociete");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void notifyNouveauVente(Ordre vente) {
        try {
            String jsonString = objectMapper.writeValueAsString(vente.getSociete().getIdSociete());
            rabbitTemplate.convertAndSend(QUEUE_NAME,jsonString
                    );
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void notifyNouveauAchat(Ordre achat) {
        try {
            String jsonString = objectMapper.writeValueAsString(achat.getSociete().getIdSociete());
            rabbitTemplate.convertAndSend(QUEUE_NAME,
                    jsonString);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
