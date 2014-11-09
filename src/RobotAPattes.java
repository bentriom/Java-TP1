public class RobotAPattes extends Robot {

    static double speed = 30/60/60;
    static final int waterVolMax = 2^32-1;
    static final double inTime = 0;
    static final int outFlow = 10;
    static final double outTime = 1;

    public RobotAPattes(Case c, int waterVol){
    	super(c, waterVol);
    }
    
    public RobotAPattes(Case c, int waterVol, double speed){
    	super(c, waterVol);
    	RobotAPattes.speed = speed;
    }
    
    @Override
    public double getVitesse(NatureTerrain n){
        double res = 0;
        switch(n) {
            case TERRAIN_LIBRE : res = speed;
                                 break;
            case HABITAT : res = speed;
                                 break;
            case FORET : res = speed;
            					break;
            case ROCHE : res = speed/30;
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
    	return "images/pattes.png";
    }
    
    @Override
    public int deverserEau(int vol) {
    	double nbOpD = Math.ceil(Math.min(vol, this.getWaterVol())/getWaterOutFlow());
        int nbOp = (int) nbOpD;
        return nbOp*getWaterOutFlow();
    }
}
