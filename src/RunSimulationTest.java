
import java.awt.Color;
import java.io.FileNotFoundException;

import simulation.Simulateur;
import ihm.*;


public class RunSimulationTest {
	public static void main(String[] args) {
		Simulateur simu = new Simulateur(args);
		ManagerTest M = new ManagerTest(simu);
		simu.setManager(M);
		M.manage();
	}
}




/*
class Simulateur implements Simulable {

    private IGSimulateur ihm;
    private DonneesSimulation data;
    
	public Simulateur(String[] args) {
		if (args.length < 1) {
			System.out.println("Syntaxe: java TestLecteurDonnees <nomDeFichier>");
			System.exit(1);
		}
		
		try {
			data = LecteurDonnees.creeDonnees(args[0]);
			int col = data.getCarte().getNbColonnes();
			int lig = data.getCarte().getNbLignes();
			int taille = data.getCarte().getTailleCases();
			ihm = new IGSimulateur(col, lig, 30, this);
	        dessine();
		} catch (FileNotFoundException e) {
			System.out.println("fichier " + args[0] + " inconnu ou illisible");
		} catch (ExceptionFormatDonnees e) {
			System.out.println("\n\t**format du fichier " + args[0] + " invalide: " + e.getMessage());
		}
	}
    
    @Override 
	public void next(){
    }

    @Override 
	public void restart(){
    }
    

   
    private void dessine(){
		Affichage.dessineCases(data.getCarte(), ihm);
		Affichage.dessineIncendies(data, ihm);
		Affichage.dessineRobots(data, ihm);
    }
}*/
