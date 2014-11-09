public class RobotVolant extends Robot {

    static double speed = 100/60/60;
    static final int waterVolMax = 10000;
    static final double inTime = 30*60;
    static final int outFlow = 10000;
    static final double outTime = 30;

    public RobotVolant(Case c, int waterVol){
    	super(c, waterVol);
    }
    
    public RobotVolant(Case c, int waterVol, double speed){
    	super(c, waterVol);
    	RobotVolant.speed = Math.min(speed, 150/60/60);
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
    public double remplir() {
    	boolean nearWater = false; 
    	nearWater = (this.getPosition().getNature() == NatureTerrain.EAU) || nearWater;
    	if (nearWater) {
    		waterVol = getWaterVolMax(); 
    	} else {
		    System.out.println("Vous essayez de remplir le robot sans eau !");
    	}
    	return getFullingTime();
    }
}
