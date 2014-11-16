import java.util.LinkedList;


public class Manager3 extends Manager {
	private int clock = 0;
	
	public Manager3(Simulateur s) {
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
		int nbIncendies = data.getSizeIncendie();
		int nbRobots = data.getSizeRobot();
		long dateCour = simu.getDate();
		
		int[] vivaciteFeu = new int[nbIncendies];
		for (int i=0; i<nbIncendies; i++){
			Incendie fire= simu.getData().getIncendie(i);
			vivaciteFeu[i] = fire.getWaterNeed();
		}

		for (int r=0; r<nbRobots; r++){
			Robot robot = simu.getData().getRobot(r);
			if (robot.isBusy()) {
				continue;
			}
			if (robot.getWaterVol() <= 0) {
				moveEvents.addAll(robot.fetchWater(dateCour));
			}
			double coutMin = Double.MAX_VALUE;
			Incendie nextest = null;
			int intNextest = 0;
			for (int i=0; i<nbIncendies; i++){
				Incendie fire= simu.getData().getIncendie(i);
				if (vivaciteFeu[i] <= 0){
					continue;
				}
				double coutLoc = robot.timeToMoveTo(fire.getPosition());
				if ( coutLoc <= coutMin && coutLoc >= 0){
					nextest = fire;
					coutMin = coutLoc;
					intNextest = i;
				}
			}
			
			vivaciteFeu[intNextest] -= robot.getWaterVol();
			// ajout_evt : beginning derverser eau, date = coutMin
			moveEvents = robot.moveToFar(nextest.getPosition());
			// ajout evt : fin de derversage, date = coutMin + watertime
		}
	}

}
