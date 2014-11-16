import ihm.*;

import java.awt.Color;
import java.io.FileNotFoundException;

public class Affichage {

    public static void dessineCases(Carte map, IGSimulateur ihm){
    	try {
			int col = map.getNbColonnes();
			int lig = map.getNbLignes();
			for (int i = 0; i < lig; i++){
				for (int j = 0; j < col; j++){
					Case c = map.getCase(i, j);
					ihm.paintImage(j, i, c.image(), 1, 1);
				}
			}
    	} catch (MapIndexOutOfBoundsException e) {
    		e.printStackTrace();
    	}
    }
    
   public static void dessineRobots(DonneesSimulation data, IGSimulateur ihm){
	   	try {
				int nbRobots = data.getSizeRobot();
				Robot rob;
				for (int numRobot = 0; numRobot < nbRobots; numRobot++){
					rob = data.getRobot(numRobot);
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
					int nbIncendies= data.getSizeIncendie();
					Incendie fire;
					for (int numIncendie= 0; numIncendie < nbIncendies; numIncendie++){
						fire = data.getIncendie(numIncendie);
						if (fire.getWaterNeed() <= 0){
							continue;
						}
						ihm.paintImage(fire.getPosition().getColonne(), 
								fire.getPosition().getLigne(), 
								"images/feu.png", 1, 1);
					}
		   	} catch (MapIndexOutOfBoundsException e) {
		   		e.printStackTrace();
		   	}
   	}	
}
