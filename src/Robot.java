 /*
 * Classe qui permet de gere les robots
 * @author Mahmoud Bentriou, Mathias Biehler, Cyril Dutrieux
 */

public abstract class Robot {

	
    private Case position;
    private int waterVol;
    private boolean busy;

    public Robot(Case c, int waterVol){
    	this.position = c;
    	this.waterVol = waterVol;
    	this.busy = false;
    }

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
    
    
    abstract public int getWaterOutFlow();
    abstract public double getFullingTime();
    abstract public double getOutTime();
    abstract public int getWaterVolMax();
    abstract public String image();

    public int getWaterVol() {
       return waterVol;
    }

    public double remplir() {
    	boolean nearWater = false; 
    	for (Direction d : Direction.values()){
    		nearWater = (position.getVoisin(d).getNature() == NatureTerrain.EAU) || nearWater;      		
    	}
    	nearWater = (position.getNature() == NatureTerrain.EAU) || nearWater;
    	if (nearWater) {
    		waterVol = getWaterVolMax(); 
    	} else {
		    System.out.println("Vous essayez de bouger le robot sans eau !");
    	}
    	return getFullingTime();
    }

    public double timeDeverserEau(int vol) {
    	double nbOpD = Math.ceil(Math.min(vol, waterVol)/getWaterOutFlow());
        int nbOp = (int) nbOpD;
        return nbOp*getOutTime();
    }
    
    public int deverserEau(int vol) {
    	double nbOpD = Math.ceil(Math.min(vol, waterVol)/getWaterOutFlow());
        int nbOp = (int) nbOpD;
        waterVol -= nbOp*getWaterOutFlow();
        return nbOp*getWaterOutFlow();
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
