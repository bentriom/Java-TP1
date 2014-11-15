import java.util.*;
import java.io.FileNotFoundException;
import java.util.LinkedList;

import ihm.*;

public class Simulateur implements Simulable {

    private IGSimulateur ihm;
    private DonneesSimulation data;
	private long dateCourrante = 0;
	private Manager manager;
	private String fichier;
	ComparateurEvenements C = new ComparateurEvenements();
	private TreeSet<Evenement> evenements = new TreeSet<Evenement>(C);
    
	// constructeur lisant les donnees
	public Simulateur(String[] args) {
		if (args.length < 1) {
			System.out.println("Syntaxe: java TestLecteurDonnees <nomDeFichier>");
			System.exit(1);
		}
		
		try {
			fichier = args[0];
			data = LecteurDonnees.creeDonnees(args[0]);
			int col = data.getCarte().getNbColonnes();
			int lig = data.getCarte().getNbLignes();
			int casePX = Math.min(800/col, 600/lig);
			int taille = data.getCarte().getTailleCases();
			ihm = new IGSimulateur(col, lig, casePX, this);
	        dessine();
		} catch (FileNotFoundException e) {
			System.out.println("fichier " + args[0] + " inconnu ou illisible");
		} catch (ExceptionFormatDonnees e) {
			System.out.println("\n\t**format du fichier " + args[0] + " invalide: " + e.getMessage());
		}
	}

	public void setManager(Manager M) {
		this.manager = M;
		this.manager.manage();
	}
    
	/* Implémentation de l'interface Simulable */
    @Override 
	public void next(){
        this.incrementeDate();
    	if (this.evenementExistant()) {
            boolean evenementExecute = false;
	    	Evenement E = this.evenements.first();
		    while (E.getDate() < this.dateCourrante) {
		    	E.execute();
		    	evenementExecute = true;
		    	this.evenements.remove(E);
                evenementExecute = true;
                if (this.evenementExistant()) {
                    E = this.evenements.first();
                }
                else
                    break;
		    }
            if (evenementExecute)
                dessine();
    	}
    }

    @Override 
	public void restart(){
    	/* manage() supprime la liste d'evenements du simulateur et la recreer */
    	try {
			data = LecteurDonnees.creeDonnees(fichier);
	        dessine();
		} catch (FileNotFoundException e) {
			System.out.println("fichier " + fichier + " inconnu ou illisible");
		} catch (ExceptionFormatDonnees e) {
			System.out.println("\n\t**format du fichier " + fichier + " invalide: " + e.getMessage());
		}
    	this.dateCourrante = 0;
    	this.manager.manage();
    }
   
    private void dessine(){
		Affichage.dessineCases(data.getCarte(), ihm);
		Affichage.dessineIncendies(data, ihm);
		Affichage.dessineRobots(data, ihm);
    }
    
	/* Incrémente la date */
	public void incrementeDate() {
		this.dateCourrante++;
	}
	
	/* Méthodes sur les événements */
	public void ajouteEvenement(Evenement e) {
		this.evenements.add(e);
	}
	// Pour pouvoir réajuster les dates après création de l'evenement
	public void ajouteEvenement(long date, Evenement e) {
		e.setDate(date);
		this.evenements.add(e);
	}
	
	public int ajouteEvenement(long date_debut, LinkedList<Evenement> ListeE) {
		int index=0;
		for(Evenement E : ListeE) {
			ajouteEvenement(date_debut+index,E);
			index++;
		}
		return index;
	}
	
	public TreeSet<Evenement> getEvts() {
		return this.evenements;
	}
	
	public boolean evenementExistant() {
		return !(this.evenements.size() == 0);
	}
	
	/* Accès aux donnees lues : robots et incendies */
	public DonneesSimulation getData() {
		return this.data;
	}
}
