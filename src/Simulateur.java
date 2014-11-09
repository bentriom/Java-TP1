import java.awt.Color;
import java.io.FileNotFoundException;

import ihm.*;



public class Simulateur implements Simulable {

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
			ihm = new IGSimulateur(col, lig, taille, this);
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
    
    private void dessineCases(Carte map){
    	try {
			int col = map.getNbColonnes();
			int lig = map.getNbLignes();
			for (int i = 0; i < lig; i++){
				for (int j = 0; j < col; j++){
					ihm.paintImage(j, i, map.getCase(i, j).image()  ,1 ,1 );
				}
			}
    	} catch (MapIndexOutOfBoundsException e) {
    		e.printStackTrace();
    	}
    }
    
   private void dessineRobots(DonneesSimulation data){
	   	try {
				int nbRobots = data.getSizeRobot();
				Robot rob;
				for (int numRobot = 0; numRobot < nbRobots; numRobot++){
					rob = data.getRobot(numRobot);
					ihm.paintImage(rob.getPosition().getColonne(), 
							rob.getPosition().getColonne(), 
							rob.image(), 1, 1);
				}
	   	} catch (MapIndexOutOfBoundsException e) {
	   		e.printStackTrace();
	   	}
    }
   
   private void dessineIncendies(DonneesSimulation data){
		   	try {
					int nbIncendies= data.getSizeIncendie();
					Incendie fire;
					for (int numIncendie= 0; numIncendie < nbIncendies; numIncendie++){
						fire = data.getIncendie(numIncendie);
						ihm.paintImage(fire.getPosition().getColonne(), 
								fire.getPosition().getColonne(), 
								"image/feu.png", 1, 1);
					}
		   	} catch (MapIndexOutOfBoundsException e) {
		   		e.printStackTrace();
		   	}
   	}	
   
    private void dessine(){
	try {
		dessineCases(data.getCarte());
		dessineIncendies(data);
		dessineRobots(data);
		
		} catch (MapIndexOutOfBoundsException e) {
			e.printStackTrace();
		}
    }
}