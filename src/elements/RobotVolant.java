package elements;
import environnement.Case;
import environnement.NatureTerrain;

/**
 * Classe finale héritée de robot qui décrit un robot volant 
 * @author Mahmoud Bentriou, Mathias Biehler, Cyril Dutrieux
 */

public class RobotVolant extends Robot {

    private double speed = 100.0*(1000.0/3600.0);
    static final int waterVolMax = 10000;
    static final double inTime = 30*60;
    static final int outFlow = 10000;
    static final double outTime = 30;

    public RobotVolant(Case c, int waterVol){
    	super(c, waterVol);
    }
    
    public RobotVolant(Case c, int waterVol, double speed){
    	super(c, waterVol);
    	this.speed = Math.min(speed, 150.0)*(1000.0/3600.0);
    }
    
    @Override
    public double getVitesse(NatureTerrain n){
    	return speed;
    }

    @Override
    public int getWaterOutFlow() {
        return outFlow; 
    }

    @Override 
    public double getFullingTime() {
        return inTime;
    }

    @Override 
    public double getOutTime() {
        return outTime;
    }

    @Override
    public int getWaterVolMax() {
         return waterVolMax;
    }
    
    @Override
    public String image(){
        if (this.isBusy())
    	    return "images/drone.png";
        else
            return "images/drone_libre.png";
    }
    
    @Override
    public String specifString(){
    	return " volant ";
    }
    
    public boolean canBeNextTo(){
    	return false;
    }

    
    @Override
    public double remplir() {
       	boolean nearWater = false; 
       	nearWater = (this.getPosition().getNature() == NatureTerrain.EAU);
       	if (nearWater) {
       		waterVol = getWaterVolMax(); 
       	} else {
       		System.out.println("Vous essayez de remplir le robot " + this.specifString() + " sans eau !");
       	}
       	return getFullingTime();

    }
}
