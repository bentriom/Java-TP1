import java.util.*;
import java.io.FileNotFoundException;
import java.util.LinkedList;

import ihm.*;

public class Simulateur implements Simulable {

	private int pasDeManage = 100;
    private IGSimulateur ihm;
    private DonneesSimulation data;
	private Horloge clock;
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
			clock = new Horloge();
	        dessine();
		} catch (FileNotFoundException e) {
			System.out.println("fichier " + args[0] + " inconnu ou illisible");
		} catch (ExceptionFormatDonnees e) {
			System.out.println("\n\t**format du fichier " + args[0] + " invalide: " + e.getMessage());
		}
	}

	public void setManager(Manager M) {
		this.manager = M;
	}
    
	/* Implémentation de l'interface Simulable */
    @Override 
	public void next(){
        /* On manage a la date tous les 100 */
    	if (manager.end()){
    		Affichage.fin(data, ihm);
    		System.out.println("La simulation est finie");
    		Thread.sleep(10000);
    	}
        this.manager.manage();
        this.incrementeDate();
    	if (this.evenementExistant()) {
            boolean evenementExecute = false;
	    	Evenement E = this.evenements.first();
		    while (E.getDate() < Horloge.getDate()) {
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
    	this.manager.manage();
    }
   
    private void dessine(){
		Affichage.dessineCases(data.getCarte(), ihm);
		Affichage.dessineIncendies(data, ihm);
		Affichage.dessineRobots(data, ihm);
    }
    
	/* Incrémente la date */
	public void incrementeDate() {
		clock.increDate();
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
			System.out.println("evt num "+ String.valueOf(index) + "date = " + String.valueOf(E.getDate()));
			ajouteEvenement(E.getDate()+date_debut,E);
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
	
	/* Accès a la date courante */
	public long getDate() {
		return Horloge.getDate();
	}
}
