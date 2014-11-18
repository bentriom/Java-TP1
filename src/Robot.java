/*
 * Classe qui permet de gere les robots
 * @author Mahmoud Bentriou, Mathias Biehler, Cyril Dutrieux
 */

import java.util.LinkedList;

public abstract class Robot {

    private Case position;
    protected int waterVol;
    private boolean busy;
    private static Carte map;

    public Robot(Case c, int waterVol){
    	this.position = c;
    	this.waterVol = waterVol;
    	this.busy = false;
    }

    public static void setMap(Carte map){
    	Robot.map = map;
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
    	if (this.isBusy()){
    		// return -1;
    	}
    	Driver tomTom = new Driver(map, this);
		return tomTom.aStar(position, c, true);    	
    }

    
    public LinkedList<Evenement> moveToFar(Case c, long dateAbs, boolean nextTo) {
    	if (this.isBusy()){
    		return null;
    	}
    	Driver tomTom = new Driver(map, this);
    	tomTom.aStar(position, c, nextTo);
		LinkedList<Evenement> evtList = tomTom.pathFinder(dateAbs);
		this.busy();
		return evtList;
    }
    
    public LinkedList<Evenement> fetchWater(long dateAbs) {
    	if (this.isBusy()) {
    		return null;
    	}
    	Driver tomTom = new Driver(map, this);
    	double tempsVoyage = tomTom.findWater(position, canBeNextTo());
		LinkedList<Evenement> evtList = tomTom.pathFinder(dateAbs);
		evtList.add(this.remplirEau((long) tempsVoyage + dateAbs));	

	    if(!evtList.isEmpty()){
	    	this.busy();
	    }
		return evtList;
    }
    
    public void moveTo(Case c) {
        if (Math.abs(c.getLigne() - position.getLigne()) +
        		Math.abs(c.getColonne() - position.getColonne()) < 2){
            position = c;
        } else { 
		    System.out.println("Vous essayez de bouger le robot " + this.specifString() + "sur une case invalide !");
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
    abstract public String specifString();

    public boolean canBeNextTo(){
    	return true;
    }

    /* Gestion de l'eau */
    public int getWaterVol() {
       return waterVol;
    }

    public double remplir() {
    	boolean nearWater = false; 
    	for (Direction d : Direction.values()){
    		nearWater = (map.getVoisin(position, d).getNature() == NatureTerrain.EAU) || nearWater;      		
    	}
    	nearWater = (position.getNature() == NatureTerrain.EAU) || nearWater;
    	if (nearWater) {
    		waterVol = getWaterVolMax(); 
    	} else {
		    System.out.println("Vous essayez de remplir le robot " + this.specifString() + " sans eau !");
    	}
    	return getFullingTime();
    }

    /* Cree l'evenement pour que le robot se remplisse (en une fois) */
    public Evenement remplirEau(long date_absolue) {
    	return new EvtRemplirRobot(date_absolue+(long)this.getFullingTime(),this);
    }

    
    /* Méthode math pour savoir en combien de temps on va deverse
     * Prend : le volume que l'on veut deverser
     * Renvoie : date relative de l'evenement deverser */
    public double timeDeverserEau(int vol) {
    	double nbOpD = Math.ceil(((double)Math.min(vol, waterVol))/((double)getWaterOutFlow()));
        int nbOp = (int) nbOpD;
        return Math.min(nbOp*getOutTime(),
        		getWaterVolMax()*((double)getOutTime())/((double)getWaterOutFlow()));
    }
    
    /* Méthode math pour savoir ce qu'on deverse */
    public int deverserEau(int vol) {
    	double nbOpD = Math.ceil(((double)Math.min(vol, waterVol))/((double)getWaterOutFlow()));
        int nbOp = (int) nbOpD;
        int deverse;
    	if (vol < waterVol - getWaterOutFlow()) { 
    		deverse = Math.min(nbOp*getWaterOutFlow(), waterVol);
    		this.waterVol -= deverse;
    	} else {
    		deverse = waterVol;
    		this.waterVol = 0;
    	}
        return deverse;
    }
    
    /* Méthode qui agit sur l'incendie */
    public void deverserEau(Incendie incendie) {
    	boolean nearFire = (incendie.getPosition() == position);
    	for (Direction d : Direction.values()){
    		nearFire = (incendie.getPosition() == map.getVoisin(position, d) || nearFire);      		
    	}	
    	if (!nearFire){
    		System.out.println("robot " + this.specifString() + " : NO FIRE " );
    		return;
    	}
    	int waterNeed = incendie.getWaterNeed();
    	waterNeed -= this.deverserEau(waterNeed);
    	incendie.setWaterNeed(waterNeed);
    }
    
    public Evenement deverserEau(long date_absolue,Incendie incendie) {
    	return new EvtDeverserEau(date_absolue+(long)this.timeDeverserEau(incendie.getWaterNeed()), incendie, this);
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
    	if (this.isBusy()) {
    		return null;
    	}
    	LinkedList<Evenement> evtsList = new LinkedList<Evenement>();
    	Driver tomTom = new Driver(map, this);
    	long tempsVoyage = 
    			(long) tomTom.aStar(position, incendie.getPosition(), canBeNextTo());
		evtsList = tomTom.pathFinder(dateAbs);
		Evenement evt = this.deverserEau(tempsVoyage + dateAbs,incendie);
	    evtsList.add(evt);

	    if(!evtsList.isEmpty()){
	    	this.busy();
	    }
	    
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
    
    @Override
    public String toString(){
    	return " robot " + this.specifString() + " a la case : " + position.toString() + " de vitesse : " + this.getVitesse(NatureTerrain.TERRAIN_LIBRE);
    }
}
