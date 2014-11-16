import java.util.LinkedList;


public class Manager2 extends Manager {
	private int clock = 0;
	
	public Manager2(Simulateur s) {
		super(s);
	}

	@Override
	public void manage() {
		clock++;
		if (clock % 10 != 0){
			return;
		}
		LinkedList<Evenement> moveEvents = new LinkedList<Evenement>();
		DonneesSimulation data = simu.getData();
		long dateCour = simu.getDate();
		int nbIncendies = data.getSizeIncendie();
		int nbRobots = data.getSizeRobot();
		for (int i=0; i<nbIncendies; i++){
			Incendie fire= simu.getData().getIncendie(i);
			double coutMin = Double.MAX_VALUE;
			Robot nextest = null;
			for (int r=0; r<nbRobots; r++){
				Robot robot = simu.getData().getRobot(r);
				if (robot.isBusy()) {
					continue;
				}
				if (robot.getWaterVol() <= 0) {
					moveEvents = robot.fetchWater(dateCour);
				}
				double coutLoc = robot.timeToMoveTo(fire.getPosition());
				if ( coutLoc <= coutMin && coutLoc >= 0){
					nextest = robot;
					coutMin = coutLoc;
				}
			}
			// ajout_evt : beginning derverser eau, date = coutMin
			moveEvents.addAll(nextest.moveToFar(fire.getPosition()));
			// ajout evt : fin de derversage, date = coutMin + watertime
			nbIncendies = data.getSizeIncendie();
		}
	}

}
