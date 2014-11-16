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
    	tomTom.aStar(position, c, canBeNextTo());
		LinkedList<Evenement> evtList = tomTom.pathFinder();
		this.busy();
		return evtList;
    }
    
    public LinkedList<Evenement> fetchWater(long dateAbs) {
    	Driver tomTom = new Driver(Case.map, this);
    	long date = (long) tomTom.findWater(position, canBeNextTo());
		LinkedList<Evenement> evtList = tomTom.pathFinder();
		evtList.add(this.remplirEau(date + dateAbs));
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

    public boolean canBeNextTo(){
    	return true;
    }

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
    public Evenement remplirEau(long date_absolue) {
    	return new RemplirRobot(date_absolue+(int)this.getFullingTime(),this);
    }

    
    /* Méthode math pour savoir en combien de temps on va deverse
     * Prend : le volume que l'on veut deverser
     * Renvoie : date relative de l'evenement deverser */
    public double timeDeverserEau(int vol) {
    	double nbOpD = Math.ceil(Math.min(vol, waterVol)/getWaterOutFlow());
        int nbOp = (int) nbOpD;
        return nbOp*getOutTime();
    }
    
    /* Méthode math pour savoir ce qu'on deverse */
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
    
    /* Méthode qui agit sur l'incendie */
    public void deverserEau(Incendie incendie) {
    	int waterNeed = incendie.getWaterNeed();
    	waterNeed -= this.deverserEau(waterNeed);
    	incendie.setWaterNeed(waterNeed);
    	System.out.println("Eau restant : " + String.valueOf(waterNeed) + "robot eau : " + String.valueOf(this.getWaterVol()));
    	if (waterNeed <= 0) {
    		incendie = null;
    	}
    }
    
    public Evenement deverserEau(long date_absolue,Incendie incendie) {
    	return new EvtDeverserEau(date_absolue+(int)this.timeDeverserEau(incendie.getWaterNeed()), incendie, this);
    }

    /* Cree l'evenement pour deverser une fois */
    /*
    public Evenement deverserSurIncendie(long date, Incendie incendie) {
    	return new EvtDeverserEau(date,incendie,this);
    }*/
    
    /* Cette méthode envoie un robot eteindre un incendie
     * Il va a l'incendie et deverse UNE FOIS
     */
    public LinkedList<Evenement> eteindreIncendie(long dateAbs, Incendie incendie) { 
    	LinkedList<Evenement> evtsList = new LinkedList<Evenement>();
    	Driver tomTom = new Driver(Case.map, this);
    	long tempVoyage = 
    			(long) tomTom.aStar(position, incendie.getPosition(), canBeNextTo());
		evtsList = tomTom.pathFinder();
	    evtsList.add(this.deverserEau(tempVoyage + dateAbs,incendie));
    	/* Evenement pour dire qu'il est libre */
    	evtsList.add(this.imUnbusy(dateAbs));	
    	return evtsList;
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
    
    public Evenement imUnbusy(long date_absolue) {
    	return new EvtImUnbusy(date_absolue,this);
    }
    
    @Override
    public String toString(){
    	return " robot a la case : " + position.toString() + " de vitesse : " + this.getVitesse(NatureTerrain.TERRAIN_LIBRE);
    }
}
