package com.inscription.devoir.controllers;


import com.inscription.devoir.exception.InscriptionException;
import com.inscription.devoir.helper.InscriptionHelper;
import com.inscription.devoir.helper.PaiementHelper;
import com.inscription.devoir.models.*;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/api", produces = "application/vnd.api.v1+json")
@RestController
public class InscriptionController {


    private  final InscriptionHelper inscriptionHelper;

    private final PaiementHelper paiementHelper;



    public InscriptionController(InscriptionHelper inscriptionHelper, PaiementHelper paiementHelper){
        this.inscriptionHelper = inscriptionHelper;
        this.paiementHelper = paiementHelper;
    }

    @PostMapping("/inscrire")
    public Inscription inscrire(@RequestBody Inscription inscription ) {

        try {
            return inscriptionHelper.saveInscription(inscription);

        }catch (InscriptionException e){
            throw  e;
        }
    }



    @PostMapping("/paiement")
    public Paiement payer(@RequestBody Paiement paiement){
        try {
            return paiementHelper.payer(paiement);
        }catch (Exception e){
            throw  new  InscriptionException("erreur"+e);
        }
    }
    @PostMapping("/addNiveau")
    public Niveau addNiveau (@RequestBody Niveau niveau)  {
        try {
            return  inscriptionHelper.saveNiveau(niveau);
        }catch (Exception e){
            throw new InscriptionException("Erreur:" +e);
        }
    }

    @PostMapping("/addFilière")
    public Filière filière (@RequestBody Filière filière) {
        try {
            return  inscriptionHelper.saveFilière(filière);
        }catch (Exception e){
            throw new InscriptionException("Erreur:" +e);
        }
    }


    @PostMapping("/addAneeScolaire")
    public AnneeScolaire addAnneeScolaire(@RequestBody  AnneeScolaire anneeScolaire){
        try {
            return  inscriptionHelper.saveAnneeScolaire(anneeScolaire);
        }catch (Exception e){
            throw new InscriptionException("Erreur:" +e);
        }
    }

    @PostMapping("/addClasse")
    public Classe addClasse (@RequestBody Classe classe) {
        try {
            return  inscriptionHelper.saveClasse(classe);
        }catch (Exception e){
            throw new InscriptionException("Erreur:"+e);
        }
    }


}
