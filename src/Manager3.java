import java.util.LinkedList;


public class Manager3 extends Manager {
	private int clock = 0;
	private int[] vivaciteFeu = new int[simu.getData().getSizeIncendie()];
	
	public Manager3(Simulateur s) {
		super(s);
	}

	@Override
	public void manage() {
		clock++;
		if (clock % 10 != 0){
			return;
		}
		boolean continuer = false;
		LinkedList<Evenement> moveEvents = new LinkedList<Evenement>();
		DonneesSimulation data = simu.getData();
		int nbIncendies = data.getSizeIncendie();
		int nbRobots = data.getSizeRobot();
		long dateCour = simu.getDate();
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
				continuer = true;
			}
			double coutMin = Double.MAX_VALUE;
			Incendie nextest = null;
			int intNextest = 0;
			for (int i=0; i<nbIncendies; i++){
				Incendie fire= simu.getData().getIncendie(i);
				double coutLoc = robot.timeToMoveTo(fire.getPosition());
				if ( coutLoc < 0 ){
					continuer = true;
				}
				if (vivaciteFeu[i] <= 0){
					continue;
				}
				if ( coutLoc <= coutMin && coutLoc >= 0){
					nextest = fire;
					coutMin = coutLoc;
					intNextest = i;
				}
			}
			if (nextest != null){
				vivaciteFeu[intNextest] -= robot.getWaterVol();
				moveEvents.addAll(robot.eteindreIncendie(dateCour, nextest));
			}
		}
		this.simu.ajouteEvenement(0,moveEvents);
		simulationTerminee = !continuer; 
		return;
	}

}
