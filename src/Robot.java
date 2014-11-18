/**
 * Classe qui permet de gere les robots
 * @author Mahmoud Bentriou, Mathias Biehler, Cyril Dutrieux
 **/

import java.util.LinkedList;

public abstract class Robot {

    private Case position;
    protected int waterVol;
    private boolean busy;
    private static Carte map;

    /** Constructeur du robot : défini par une position initiale et eau initiale
     * @param c
     * @param waterVol
     * **/
    public Robot(Case c, int waterVol){
    	this.position = c;
    	this.waterVol = waterVol;
    	this.busy = false;
    }

    /** Modifieur de la carte du robot
     * @param map **/
    public static void setMap(Carte map){
    	Robot.map = map;
    }
    
    /* ---------------- Gestion de la position ----------------- */
   
    /** Renvoie la position du robot
     * @return position du robot **/
    public Case getPosition() {
        return position;
    }
    
    /** 
     * Calcul le temps pour que le robot aille à une case
     * @param c
     * @return Temps pour aller à la case c
     */
    public double timeToMoveTo(Case c) {
    	if (this.isBusy()){
    		// return -1;
    	}
    	Driver tomTom = new Driver(map, this);
		return tomTom.aStar(position, c, true);    	
    }

    /**
     * Méthode pour créer les evenements pour déplacer le robot
     * @param c
     * @param nextTo
     * @return Liste d'evenements pour aller jusque c
     */
    public LinkedList<Evenement> moveToFar(Case c, boolean nextTo) {
    	if (this.isBusy()){
    		return null;
    	}
    	Driver tomTom = new Driver(map, this);
    	tomTom.aStar(position, c, nextTo);
		LinkedList<Evenement> evtList = tomTom.pathFinder();
		this.busy();
		return evtList;
    }
    
    /**
     * Envoie le robot chercher de l'eau
     * @return Liste de déplacements jusqu'à une case d'eau + événement remplir
     */
    public LinkedList<Evenement> fetchWater() {
    	if (this.isBusy()) {
    		return null;
    	}
    	Driver tomTom = new Driver(map, this);
    	double tempsVoyage = tomTom.findWater(position, canBeNextTo());
		LinkedList<Evenement> evtList = tomTom.pathFinder();
		evtList.add(this.remplirEau((long) tempsVoyage));	

	    if(!evtList.isEmpty()){
	    	this.busy();
	    }
		return evtList;
    }
    
    /**
     * Modifieur de la position du robot
     * @param c
     */
    public void moveTo(Case c) {
        if (Math.abs(c.getLigne() - position.getLigne()) +
        		Math.abs(c.getColonne() - position.getColonne()) < 2){
            this.position = c;
        } else { 
		    System.out.println("Vous essayez de bouger le robot " + this.specifString() + "sur une case invalide !");
        }
        this.position = c;
    }

    /**
     * Création d'un événement élémentaire
     * @param pos
     * @return Evenement de déplacement élémentaire vers pos
     */
    public Evenement EvtmoveTo(Case pos) {
    	return new EvtDeplacement(0,this,pos);
    }    
    
    /**
     * Accesseur de la vitesse du robot
     * @param n
     * @return Vitesse du robot selon le terrain
     */
    abstract public double getVitesse(NatureTerrain n);
    /**
     * Accesseur du temps pour déverser de l'eau
     * @return Temps pour déverser de l'eau
     */
    abstract public int getWaterOutFlow();
    /**
     * Accesseur du temps pour se remplir
     * @return Temps pour se remplir
     */
    abstract public double getFullingTime();
    /** 
     * Accesseur du temps d'une intervention unitaire pour déverser de l'eau
     * @return Temps d'une intervention unitaire pour déverser de l'eau
     */
    abstract public double getOutTime();
    /**
     * Accesseur du volume maximal d'eau que l'on peut remplir
     * @return Volume maximal d'eau que l'on peut remplir
     */
    abstract public int getWaterVolMax();

    /**
     * Accesseur du chemin de l'image associée au robot qui sera déplacé
     * @return Chaine de l'image associé au robot qui sera déplacé
     */
    abstract public String image();
    /**
     * Renvoie le type du robot
     * @return Nom du type du robot
     */
    abstract public String specifString();

    /**
     * Indique la facon d'éteindre un incendie
     * @return Vrai si éteint l'incendie en étant à coté de la case, faux sinon
     */
    public boolean canBeNextTo(){
    	return true;
    }

    /* ---------------- Gestion de l'eau ---------------- */
    
    /**
     * Accesseur du volume d'eau du robot en cours
     *  @return Le volume d'eau du robot en cours */
    public int getWaterVol() {
       return waterVol;
    }

    /**
     * Rempli le robot à sa contenance maximale
     * @return le temps que met un robot a se remplir
     */
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

    /** 
     * Création d'un événement élémentaire
     * @return L'evenement pour que le robot se remplisse (en une fois) */
    public Evenement remplirEau(long date_apres_deplacement) {
    	return new EvtRemplirRobot(date_apres_deplacement+(long)this.getFullingTime(),this);
    }

    
    /** Méthode mathématique pour savoir en combien de temps on va deverse
     * @param vol
     * @return date relative de l'evenement deverser */
    public double timeDeverserEau(int vol) {
    	double nbOpD = Math.ceil(((double)Math.min(vol, waterVol))/((double)getWaterOutFlow()));
        int nbOp = (int) nbOpD;
        return Math.min(nbOp*getOutTime(),
        		getWaterVolMax()*((double)getOutTime())/((double)getWaterOutFlow()));
    }
    
    /** Méthode mathématique pour savoir ce que l'on peut déverser selon la demande d'un volume précis 
     * @param vol
     * @return Volume d'eau effectivement déversé sur l'incendie
     */
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
    
    /** Méthode qui agit sur l'incendie 
     * @param incendie (non null)
     * Garantit : Deverse la quantité d'eau maximale possible du robot sur l'incendie */
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
    
    /**
     * Création d'un événement élémentaire
     * @param date_apres_deplacement
     * @param incendie
     * @return Evenement elementaire déverser eau selon date relative
     */
    public Evenement deverserEau(long date_apres_deplacement,Incendie incendie) {
    	return new EvtDeverserEau(date_apres_deplacement+(long)this.timeDeverserEau(incendie.getWaterNeed()), incendie, this);
    }
    
    /** Cette méthode envoie un robot éteindre un incendie
     * @apram incendie
     * @return Les evenements nécessaires à partir de la date courrante pour :
     * @return - aller à l'incendie
     * @return - déverser l'eau qu'il a sur l'incendie
     */
    public LinkedList<Evenement> eteindreIncendie(Incendie incendie) {
    	if (this.isBusy()) 
    		return null;
    	
    	LinkedList<Evenement> evtsList = new LinkedList<Evenement>();
    	Driver tomTom = new Driver(map, this);
    	long tempsVoyage = 
    			(long) tomTom.aStar(position, incendie.getPosition(), canBeNextTo());
		evtsList = tomTom.pathFinder();
		Evenement evt = this.deverserEau(tempsVoyage,incendie);
	    evtsList.add(evt);

	    if(!evtsList.isEmpty())
	    	this.busy();
	    	
    	return evtsList;
    }
    
    /** Indique si le robot est occupé 
     * @return Vrai si occupé **/
    public boolean isBusy(){
    	return busy;
    }
    
    /**
     * Rend le robot occupé
     */
    public void busy(){
    	busy = true;
    }
    
    /**
     * Libère le robot
     */
    public void unBusy(){
    	busy = false;
    }
    
    /**
     * Transforme un robot en une chaine qui le décrit (utile pour le deboggage)
     * @return Chaine qui décrit le robot
     */
    @Override
    public String toString(){
    	return "Robot " + this.specifString() + " a la case : " + position.toString() + " de vitesse : " + this.getVitesse(NatureTerrain.TERRAIN_LIBRE);
    }
}
