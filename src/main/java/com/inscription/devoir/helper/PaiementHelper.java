package com.inscription.devoir.helper;

import com.inscription.devoir.exception.InscriptionException;
import com.inscription.devoir.models.Inscription;
import com.inscription.devoir.models.Paiement;
import com.inscription.devoir.repositories.InscriptionRepositorie;
import com.inscription.devoir.repositories.PaiementRepositorie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Component
@Service
public class PaiementHelper {

    private final InscriptionRepositorie inscriptionRepositorie;
    private final PaiementRepositorie paiementRepositorie;

    public PaiementHelper(InscriptionRepositorie inscriptionRepositorie, PaiementRepositorie paiementRepositorie) {
        this.inscriptionRepositorie = inscriptionRepositorie;
        this.paiementRepositorie = paiementRepositorie;
    }


    @Transactional
    public Paiement payer(Paiement paiement){

        fairePaiement(paiement);
        return paiementRepositorie.save(paiement);
    }

    /*
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

    private void fairePaiement(Paiement paiement){
        Inscription inscriptions = inscriptionRepositorie.findById(paiement.getInscription().getId()).orElseThrow(() -> new InscriptionException("Inscription non trouvée"));
        Double montantTotal = (double)inscriptions.getClasse().getFraisInscription() + (double)inscriptions.getClasse().getAutreFrais() + (double)inscriptions.getClasse().getMensualite() ;


        Calendar calendar = Calendar.getInstance();
        Date datePaiemet = calendar.getTime();

        for (int i =1; i <= 9 ; i++){
            //Paiement paiement = new Paiement();
            paiement.setMois( paiement.getMois());
            paiement.setAmount(montantTotal);
            paiement.setInscription(inscriptions);

        }

    }




}
