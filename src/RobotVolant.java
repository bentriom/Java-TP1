
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
    	return "images/drone.png";
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
       	System.out.println("On va remplir de leau pour le robot " + this.specifString());
       	System.out.println("Stock eau avant = ");
       	nearWater = (this.getPosition().getNature() == NatureTerrain.EAU);
       	if (nearWater) {
       		waterVol = getWaterVolMax(); 
       	} else {
       		System.out.println("Vous essayez de remplir le robot " + this.specifString() + " sans eau !");
       	}
       	System.out.println("Stock eau apres = " + String.valueOf(this.waterVol));
       	return getFullingTime();

    }
}
