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
    	Driver tomTom = new Driver(Case.map, this);
		return tomTom.aStar(position, c, true);    	
    }

    public LinkedList<Evenement> moveToFar(Case c) {
    	Driver tomTom = new Driver(Case.map, this);
		LinkedList<Evenement> evtList = tomTom.pathFinder(position, c, false);
		this.busy();
		return evtList;
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

    public Evenement EvtmoveTo(Case pos) {
    	return new EvtDeplacement(0,this,pos);
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
        int deverse;
    	if (vol < waterVol) { 
    		deverse = nbOp*getWaterOutFlow();
    		this.waterVol -= deverse;
    	} else {
    		deverse = waterVol;
    		this.waterVol = 0;
    	}
        this.waterVol -= nbOp*getWaterOutFlow();
        return deverse;
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
    	/* Cree la liste de deplacements via l'algo */
    	/* Mettre a jour les dates selon la date absolue */
    	
    	/* Cree evenement deverser */
    	
    	return null;
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
    
    @Override
    public String toString(){
    	return " robot a la case : " + position.toString() + " de vitesse : " + this.getVitesse(NatureTerrain.TERRAIN_LIBRE);
    }
}
