package com.inscription.devoir;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
public class DevoirApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(DevoirApplication.class, args);
    }


    @Bean
    public RestTemplate restTemplate (RestTemplateBuilder builder){
        return builder.build();
    }

    @Configuration
    @EnableJpaRepositories(basePackages = "com.inscription.devoir.repositories")
    public class AppConfig {
        // Autres configurations de votre application
    }
/*
@Service
public class PaymentService {

    @Autowired
    private PaymentGateway paymentGateway;

    public boolean processPayment(double amount) {
        // Effectue le traitement de paiement en utilisant le PaymentGateway
        // Retourne true si le paiement est réussi, sinon retourne false

        // Exemple simplifié : vérifie simplement si le montant est supérieur à zéro
        if (amount > 0) {
            // Effectuer le traitement du paiement (intégration avec une passerelle de paiement, une banque, etc.)
            // Retourner true pour indiquer que le paiement a réussi
            return paymentGateway.processPayment(amount);
        } else {
            // Retourner false pour indiquer que le paiement a échoué
            return false;
        }
    }

    public boolean processMonthlyRegistrationPayment(double registrationFee, int startYear, int endYear) {
        // Calculer le nombre total de mois entre novembre 2020 et septembre 2021
        int totalMonths = calculateTotalMonths(startYear, endYear);

        // Effectuer le paiement mensuel pour chaque mois
        for (int month = 11; month <= 9 + (12 * (endYear - startYear)); month++) {
            boolean paymentStatus = processPayment(registrationFee);

            // Si le paiement échoue pour l'un des mois, retourner false
            if (!paymentStatus) {
                return false;
            }
        }
        
        // Tous les paiements mensuels ont réussi, retourner true
        return true;
    }

    private int calculateTotalMonths(int startYear, int endYear) {
        int totalMonths = (endYear - startYear) * 12 + 9 - 11 + 1;
        return totalMonths;
    }

    // Autres méthodes et fonctionnalités de gestion des paiements...
}
Dans cet exemple, la méthode processMonthlyRegistrationPayment() calcule
le nombre total de mois entre novembre 2020 et septembre 2021 en utilisant
 la méthode calculateTotalMonths(). Ensuite, la fonction effectue le paiement
  mensuel pour chaque mois en appelant la méthode processPayment() avec le montant
  de la registrationFee pour chaque mois. Si le paiement échoue pour l'un des mois,
   la fonction retourne false. Sinon, si tous les paiements mensuels réussissent,
   la fonction retourne true. Assurez-vous d'avoir une configuration appropriée de
   la passerelle de paiement dans votre application Spring pour qu'elle soit injectée
    correctement dans le PaymentService.
 */
/*

 */
}
