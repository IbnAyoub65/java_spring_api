package com.inscription.devoir.helper;

import com.inscription.devoir.exception.InscriptionException;
import com.inscription.devoir.models.*;
import com.inscription.devoir.repositories.*;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Slf4j
@Component
public class InscriptionHelper {

    private final InscriptionRepositorie inscriptionRepositorie ;
    private final AnneeScolaireRepositorie anneeScolaireRepositorie;
    private final ClasseRepositorie classeRepositorie;
    private final EtudiantRepositorie etudiantRepositorie;
    private final NiveauRepositorie niveauRepositorie;
    private final FilièreRepositorie filièreRepositorie;
    private final  PaiementRepositorie paiementRepositorie;



    public  InscriptionHelper(AnneeScolaireRepositorie anneeScolaireRepositorie, ClasseRepositorie classeRepositorie, EtudiantRepositorie etudiantRepositorie, InscriptionRepositorie inscriptionRepositorie, NiveauRepositorie niveauRepositorie, FilièreRepositorie filièreRepositorie, PaiementRepositorie paiementRepositorie){
        this.anneeScolaireRepositorie = anneeScolaireRepositorie;
        this.classeRepositorie = classeRepositorie;
        this.etudiantRepositorie = etudiantRepositorie;
        this.inscriptionRepositorie = inscriptionRepositorie;
        this.niveauRepositorie = niveauRepositorie;
        this.filièreRepositorie = filièreRepositorie;
        this.paiementRepositorie = paiementRepositorie;
    }

    @Transactional
    public Inscription saveInscription(Inscription inscription) {

        addClasseInscription(inscription);
        addAnneeScolaire(inscription);
        verifyEtudiantandInscription(inscription);
        procedureInscriptionPaiement(inscription);
        return inscriptionRepositorie.save(inscription);

    }


    private void processsInscription(Inscription inscription){
        addClasseInscription(inscription);
        addAnneeScolaire(inscription);
        validEtudiantInscription(inscription);

    }

    private void addClasseInscription(Inscription inscription){
        Classe classe = null;
        if(inscription.getClasse().getId() == null){
            classe = new Classe();
        }else {
            classe = classeRepositorie.findById(inscription.getClasse().getId()).orElse(null);
            if(classe != null){
                inscription.setClasse(classe);
            }
        }

    }

    private void addAnneeScolaire(Inscription inscription){
        AnneeScolaire anneeScolaire = null;
        if(inscription.getAnneeScolaire().getId() ==  null){
            anneeScolaire = new AnneeScolaire();
            validEtudiantInscription(inscription);
        }else {
            anneeScolaire = anneeScolaireRepositorie.findById(inscription.getAnneeScolaire().getId()).orElse(null);
            if (anneeScolaire != null) {
                inscription.setAnneeScolaire(anneeScolaire);
            }
        }
    }

    public void addFiliereCLasse(Classe classe){

        classe.setFilière(classe.getFilière());
    }

    public void addNiveauClasse(Classe classe){
        classe.setNiveau(classe.getNiveau());
    }



    public void verifyEtudiantandInscription(Inscription inscription) {

        Etudiant etudiant = inscription.getEtudiant();
        if(etudiant.getId() == null){
           // etudiant = etudiantRepositorie.save(etudiant);
            validEtudiantInscription(inscription);
            inscription.setEtudiant(etudiant);
        }else{
            etudiant = etudiantRepositorie.findById(etudiant.getId()).orElse(null);
            if(etudiant != null){
              //  etudiant = etudiantRepositorie.save(etudiant);
                verifyEtudiantByAnneeByClasse(inscription.getEtudiant(),inscription.getClasse(),inscription.getAnneeScolaire());
                inscription.setEtudiant(etudiant);
            }else{
                throw new InscriptionException("Cet étudiant n'existe pas");
            }
        }

    }





   private void validEtudiantInscription(Inscription inscription){
            LocalDate localDate = inscription.getEtudiant().getDatenaiss().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate dateJour = LocalDate.now();
            int age = (int) ChronoUnit.YEARS.between(localDate,dateJour);
            if (age < 18) {
                log.info("L'étudiant est trop jeune pour s'inscrire.");
                throw  new InscriptionException("L'étudiant est trop jeune pour s'inscrire.");
            }
    }




    public void verifyEtudiantByAnneeByClasse(Etudiant etudiant,Classe classe,AnneeScolaire anneeScolaire) throws InscriptionException {
        Optional<Inscription> existingInscription = inscriptionRepositorie.findByEtudiantAndClasseAndAnneeScolaire(etudiant,classe,anneeScolaire);
        if (existingInscription.isPresent()) {
            throw new InscriptionException("L'étudiant est déjà inscrit dans cette classe pour l'année scolaire ");
        }

    }
@Transactional
public void inscriptionPaiement(Inscription inscription,Double depotInitial,Double montantObligatoire, Double mensualte){
        List<Paiement> paiements = new ArrayList<>();
        Paiement paiement1 = new Paiement();
        paiement1.setInscription(inscription);
        paiement1.setMois("Novembre");
        paiement1.setAmount(mensualte);
        paiements.add(paiement1);

        Double rest = depotInitial - montantObligatoire;
        if(rest >= mensualte){
            int nbrmMois = (int)Math.floor(rest/mensualte);
            Double sommeRestant = rest % mensualte;
            inscription.getEtudiant().setSolde( sommeRestant);
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.MONTH,11);
            for (int i = 0; i<nbrmMois; i++){
                Paiement paiement = new Paiement();
                paiement.setInscription(inscription);
                calendar.add(Calendar.MONTH,i);
                paiement.setMois(new SimpleDateFormat("MMMM").format(calendar.getTime()));
                paiement.setAmount(mensualte);
                paiements.add(paiement);
            }
        }

        creerPaiement(paiements);
}

public void creerPaiement(List<Paiement> paiements){
        paiementRepositorie.saveAll(paiements);
}


public void procedureInscriptionPaiement(Inscription inscription){

        Double mensualite =  (double) inscription.getClasse().getMensualite();
        Double fraisInscription = (double) inscription.getClasse().getFraisInscription();
        Double sommeObligatoire = mensualite + fraisInscription;
        Double initialDepot = inscription.getInitialDeposit();
        if(initialDepot < sommeObligatoire){
            throw  new  InscriptionException("il faut déposer au minimum " + sommeObligatoire);
        }else{
            inscriptionPaiement(inscription,initialDepot,sommeObligatoire,mensualite);
        }


}
/*
    @Transactional
   public void  proceedPayments(Inscription registration, Double initialDeposit, Double minimumDeposit , Double  mensualite) {

        List<Paiement> payments = new ArrayList<>();
        // Create payment for November
        Paiement paymentNov = new Paiement();
        paymentNov.setInscription(registration);
        paymentNov.setMois("November");
        paymentNov.setAmount(mensualite);
        payments.add(paymentNov);
        // Check amount after november payment
        Double remainingBalanceAfterNov = initialDeposit - minimumDeposit;
        if (remainingBalanceAfterNov >= mensualite) {
            // Check number of month with the remaining amount
            int remainingMonths = (int) Math.floor(remainingBalanceAfterNov / mensualite);
            // See if there is a remaining so i can add to student solde
            Double remainingAmount = remainingBalanceAfterNov % mensualite;
            // Incrémenter le solde de l'étudiant
            registration.getEtudiant().setSolde(registration.getEtudiant().getSolde() + remainingAmount);
            // Créer des paiements pour les mois restants
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.MONTH, 11); // Set month to november
            for (int i = 0; i < remainingMonths; i++) {
                Paiement payment = new Paiement();
                payment.setInscription(registration);
                calendar.add(Calendar.MONTH, i); // increment month after nov
                payment.setMois(new SimpleDateFormat("MMMM").format(calendar.getTime()));
                payment.setAmount(mensualite);
                payments.add(payment);

            }
        }
        // Save payments
        createPayments(payments);
    }
    @Transactional
    public void createPayments(List<Paiement> payments) {
        paiementepositori.saveAll(payments);
    }
 */

  public Niveau saveNiveau(Niveau niveau){
      return niveauRepositorie.save(niveau);
  }

  public Filière saveFilière(Filière filière){
      return filièreRepositorie.save(filière);
  }

  public AnneeScolaire saveAnneeScolaire(AnneeScolaire anneeScolaire){
        return anneeScolaireRepositorie.save(anneeScolaire);
    }

    public Classe saveClasse(Classe classe){
        addFiliereCLasse(classe);
        addNiveauClasse(classe);
        return classeRepositorie.save(classe);

    }

    public Etudiant saveEtudiant(Etudiant etudiant){
        return etudiantRepositorie.save(etudiant);
    }

    public List<Inscription> listInscrit(){
        return inscriptionRepositorie.findAll();
    }

    public Etudiant findByEtudiant(UUID id){
        return etudiantRepositorie.findById(id).orElse(null);
    }

    /*
    import com.inscription.devoir.models.Inscription;
import com.inscription.devoir.models.Paiement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.Date;

@Service
@Transactional
public class PaiementService {

    @Autowired
    private InscriptionRepository inscriptionRepository;

    @Autowired
    private PaiementRepository paiementRepository;

    public void effectuerPaiementMensuel(UUID inscriptionId, Double montantMensuel) {
        Inscription inscription = inscriptionRepository.findById(inscriptionId).orElseThrow(() -> new IllegalArgumentException("Inscription non trouvée"));
        Double montantTotal = montantMensuel * 9;

        Calendar calendar = Calendar.getInstance();
        Date datePaiement = calendar.getTime();

        for (int i = 1; i <= 9; i++) {
            Paiement paiement = new Paiement();
            paiement.setMois("Mois " + i);
            paiement.setAmount(montantMensuel);
            paiement.setInscription(inscription);
            paiementRepository.save(paiement);

            calendar.add(Calendar.MONTH, 1);
            datePaiement = calendar.getTime();
        }

        inscription.setDateInscription(datePaiement);
        inscriptionRepository.save(inscription);
    }
}

     */
}
