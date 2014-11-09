public class RobotARoue extends Robot {

    static double speed = 80/60/60;
    static final int waterVolMax = 5000;
    static final double inTime = 10*60;
    static final int outFlow = 100;
    static final double outTime = 5;

    public RobotARoue(Case c, int waterVol){
    	super(c, waterVol);
    }
    
    public RobotARoue(Case c, int waterVol, double speed){
    	super(c, waterVol);
    	RobotARoue.speed = speed;
    }
    
    @Override
    public double getVitesse(NatureTerrain n){
        double res = 0;
        switch(n) {
            case TERRAIN_LIBRE : res = speed;
                                 break;
            case HABITAT : res = speed;
                                 break;
            default : res = 0;
                      break;
        }
        return res;
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
    	return "images/roue.png";
    }
}