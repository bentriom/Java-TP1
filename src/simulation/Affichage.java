package simulation;

import ihm.*;

import java.awt.Color;
import java.io.FileNotFoundException;

import elements.Incendie;
import elements.Robot;
import environnement.Carte;
import environnement.Case;

public class Affichage {

	
    public static void dessineCases(Carte map, IGSimulateur ihm){
    	try {
			int col = map.getNbColonnes();
			int lig = map.getNbLignes();
			for (int i = 0; i < lig; i++){
				for (int j = 0; j < col; j++){
					Case c = map.getCase(i, j);
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
