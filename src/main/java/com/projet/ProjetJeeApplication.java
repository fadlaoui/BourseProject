package com.projet;

import com.projet.dao.UserRepository;
import com.projet.entities.User;
import com.projet.metier.IMetierSociete;
import com.projet.metier.MetierSociete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

@EnableDiscoveryClient
@SpringBootApplication
@EnableScheduling
@ImportResource(value = "spring-beans.xml")
@PropertySource("classpath:bootstrap.properties")

public class ProjetJeeApplication implements CommandLineRunner {
	@Autowired()
	private IMetierSociete metierSociete;
	@Autowired()
	private UserRepository userRepository;
	public static void main(String[] args) {
		try {
			LocateRegistry.createRegistry(1099);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		SpringApplication.run(ProjetJeeApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {
	/*	User user = new User();
		user.setEmail("med@gmail.com");
		user.setPassword("$2a$10$aS/lF2c/9JWPUjDHfJ/zTed1ihGBgfX/7xnGTOM5/lW59X4FHalSi");
		user = userRepository.save(user);
*/
		metierSociete.SaveSocietesJsoup();
		metierSociete.SocieteWithOrderWafaBANK();
	}
}
