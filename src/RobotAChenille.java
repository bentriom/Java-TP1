public class RobotAChenille extends Robot {

    static double speed = 60;
    static final int waterVolMax = 10000;
    static final double inTime = 30*60;
    static final int outFlow = 10000;
    static final double outTime = 30;

    public RobotAChenille(Case c, int waterVol){
    	super(c, waterVol);
    }
    
    public RobotAChenille(Case c, int waterVol, double speed){
    	super(c, waterVol);
    	RobotAChenille.speed = Math.min(speed, 80/60/60);
    }
    
    @Override
    public double getVitesse(NatureTerrain n){
        double res = 0;
        switch(n) {
            case TERRAIN_LIBRE : res = speed;
                                 break;
            case HABITAT : res = speed;
                           break;
            case FORET : res = speed/2;
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
    	return "images/chenille.png";
    }
}
