package simulation;

import ihm.*;

import java.awt.Color;
import java.io.FileNotFoundException;

import elements.Incendie;
import elements.Robot;
import environnement.Carte;
import environnement.Case;
import environnement.ExceptionOutOfMap;

public class Affichage {

	/**
	 * Dessine les cases sur l'interface graphique
	 * @param map Carte à afficher
	 * @param ihm Classe affichage
	 */
    public static void dessineCases(Carte map, IGSimulateur ihm){
    	try {
			int col = map.getNbColonnes();
			int lig = map.getNbLignes();
			Case c = null;
			for (int i = 0; i < lig; i++){
				for (int j = 0; j < col; j++){
					try {
						c = map.getCase(i, j);
					} catch (ExceptionOutOfMap e) { 
						System.out.println("Erreur d'acces à une case lors du dessin des cases");
					}
					ihm.reset(j, i);
					ihm.paintImage(j, i, c.image(), 1, 1);
				}
			}
    	} catch (MapIndexOutOfBoundsException e) {
    		e.printStackTrace();
    	} catch (java.util.ConcurrentModificationException e) {
    		e.printStackTrace();
    	}
    }
    
    /**
     * Dessine les robots sur l'interface graphique
     * @param data données de simulation
     * @param ihm 
     */
   public static void dessineRobots(DonneesSimulation data, IGSimulateur ihm){
	   	try {
				for ( Robot rob : data.getRobots()) {
					ihm.paintImage(rob.getPosition().getColonne(), 
							rob.getPosition().getLigne(), 
							rob.image(), 1, 1);
				}
	   	} catch (MapIndexOutOfBoundsException e) {
	   		e.printStackTrace();
	   	}
    }
   
   /**
    * Dessine les incendies sur l'interface graphique
    * @param data données de simulation
    * @param ihm
    */
   public static void dessineIncendies(DonneesSimulation data, IGSimulateur ihm){
		   	try {
					for (Incendie fire : data.getIncendies()){
						if (fire.getWaterNeed() <= 0){
							ihm.paintImage(fire.getPosition().getColonne(), 
									fire.getPosition().getLigne(), 
									"images/e_fire.png", 1, 1);
						} else {
							ihm.paintImage(fire.getPosition().getColonne(), 
									fire.getPosition().getLigne(), 
									"images/feu.png", 1, 1);
						}
					}
		   	} catch (MapIndexOutOfBoundsException e) {
		   		e.printStackTrace();
		   	}
   	}	
   
   /**
    * Permet d'assombrir l'affichage en cas de fin de simulation (non utilisée)
    * @param data données sim
    * @param ihm
    */
   public static void fin(DonneesSimulation data, IGSimulateur ihm){
	   	try {
	   			Carte map = data.getCarte();
				int col = map.getNbColonnes();
				int lig = map.getNbLignes();
				for (int i = 0; i < lig; i++){
					for (int j = 0; j < col; j++){
						ihm.paintImage(j, i, "images/finish.png", 1, 1);
					}
				}
	   	} catch (MapIndexOutOfBoundsException e) {
	   		e.printStackTrace();
	   	}
   	}	
}
