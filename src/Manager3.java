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
		//DonneesSimulation data = simu.getData();
		int i=0;
		for (Incendie fire : simu.getData().getIncendies()){
			vivaciteFeu[i] = fire.getWaterNeed();
			i++;
		}

		for ( Robot robot : simu.getData().getRobots()) {
			if (robot.isBusy()) {
				continuer = true;
				continue;
			}
			if (robot.getWaterVol() <= 0) {
				moveEvents.addAll(robot.fetchWater());
				continuer = true;
				continue;
			}
			double coutMin = Double.MAX_VALUE;
			Incendie nextest = null;
			int intNextest = 0;
			i = 0;
			for (Incendie fire : simu.getData().getIncendies()){
				if (vivaciteFeu[i] <= 0){
					i++;
					continue;
				}

				double coutLoc = robot.timeToMoveTo(fire.getPosition());
				if ( coutLoc <= coutMin && coutLoc >= 0){
					nextest = fire;
					coutMin = coutLoc;
					intNextest = i;
				}
				i++;
			}
			if (nextest != null){
				vivaciteFeu[intNextest] -= Math.min(robot.getWaterVol(),vivaciteFeu[intNextest]) ;
				moveEvents.addAll(robot.eteindreIncendie(nextest));
				continuer = true;
			}
		}
		this.simu.ajouteEvenement(moveEvents);
		simulationTerminee = !continuer; 
		return;
	}
}
