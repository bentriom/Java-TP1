 /*
 * Classe qui permet de gere les robots
 * @author Mahmoud Bentriou, Mathias Biehler, Cyril Dutrieux
 */

import java.util.LinkedList;
public abstract class Robot {

	
    private Case position;
    protected int waterVol;
    private boolean busy;

    public Robot(Case c, int waterVol){
    	this.position = c;
    	this.waterVol = waterVol;
    	this.busy = false;
    }

    /* Gestion de la position */
    public Case getPosition() {
        return position;
    }

    public void setPosition(Case c) {
        position = c;
    }

    abstract public double getVitesse(NatureTerrain n);
    
    public double timeToMoveTo(Case c) {
    	return 0;
    }

    public void moveTo(Case c) {
        if (Math.abs(c.getLigne() - position.getLigne()) +
        		Math.abs(c.getColonne() - position.getColonne()) < 2){
            position = c;
        } else { 
		    System.out.println("Vous essayez de bouger le robot sur une case invalide !");
        }
        position = c;
    }
    
    public void moveVoisin(Direction dir) {
    	this.setPosition(this.position.getVoisin(dir));	
    }
    
    public Evenement moveTo(Direction dir) {
    	return new DeplacementElementaire(0,this,dir);
    }
    
    
    abstract public int getWaterOutFlow();
    abstract public double getFullingTime();
    abstract public double getOutTime();
    abstract public int getWaterVolMax();
    abstract public String image();

    /* Gestion de l'eau */
    public int getWaterVol() {
       return waterVol;
    }

    public double remplir() {
    	boolean nearWater = false; 
    	System.out.println("On va remplir de leau !" + String.valueOf(this.waterVol));
    	System.out.println("Stock eau avant = ");
    	for (Direction d : Direction.values()){
    		nearWater = (position.getVoisin(d).getNature() == NatureTerrain.EAU) || nearWater;      		
    	}
    	nearWater = (position.getNature() == NatureTerrain.EAU) || nearWater;
    	if (nearWater) {
    		waterVol = getWaterVolMax(); 
    	} else {
		    System.out.println("Vous essayez de remplir le robot sans eau !");
    	}
    	System.out.println("Stock eau apres = " + String.valueOf(this.waterVol));
    	return getFullingTime();
    }

    /* Cree l'evenement pour que le robot se remplisse (en une fois) */
    public Evenement remplirEau() {
    	return new RemplirRobot(0,this);
    }

    public double timeDeverserEau(int vol) {
    	double nbOpD = Math.ceil(Math.min(vol, waterVol)/getWaterOutFlow());
        int nbOp = (int) nbOpD;
        return nbOp*getOutTime();
    }
    
    public int deverserEau(int vol) {
    	double nbOpD = Math.ceil(Math.min(vol, waterVol)/getWaterOutFlow());
        int nbOp = (int) nbOpD;
        this.waterVol -= nbOp*getWaterOutFlow();
        return nbOp*getWaterOutFlow();
    }
    
    public void deverserEau(Incendie incendie) {
    	int waterNeed = incendie.getWaterNeed();
    	waterNeed -= this.deverserEau(waterNeed);
    	incendie.setWaterNeed(waterNeed);
    	System.out.println("Eau restant : " + String.valueOf(waterNeed) + "robot eau : " + String.valueOf(this.getWaterVol()));
    	if (waterNeed <= 0) {
    		System.out.println("Suppression d'incendie");
    	}
    }

    /* Cree l'evenement pour deverser une fois */
    public Evenement deverserSurIncendie(long date, Incendie incendie) {
    	return new DeverserEau(date,incendie,this);
    }
    
    public LinkedList<Evenement> eteindreIncendie(long date_absolue, Incendie incendie) {
    	LinkedList<Evenement> ListeEvts = new LinkedList<Evenement>();
    	double nombreDeverse =  this.timeDeverserEau(incendie.getWaterNeed());
    	System.out.println("On est dans eteindreincendie, water = " + String.valueOf(waterVol));
    	for(int i=0; i < nombreDeverse; i++) {
    		ListeEvts.add(deverserSurIncendie(date_absolue+i,incendie));
    	}
    	return ListeEvts;
    }
    
    public boolean isBusy(){
    	return busy;
    }
    
    public void busy(){
    	busy = true;
    }
    
    public void unBusy(){
    	busy = false;
    }
    
}
