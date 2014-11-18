/**
 * Sous-classe de Manager qui implémente la stratégie "naive"
 * @author Mahmoud Bentriou, Mathias Biehler, Cyril Dutrieux
 */

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
		
		/* Liste d'evenements qui vont etre envoyes au simulateur */
		LinkedList<Evenement> moveEvents = new LinkedList<Evenement>();
		boolean continuer = false;
		for (Incendie fire : simu.getData().getIncendies()){
			if (fire.getWaterNeed() <= 0) {
				continue;
			}
			double coutMin = Double.MAX_VALUE;
			Robot nextest = null;
			for ( Robot robot : simu.getData().getRobots()) {
				if (robot.isBusy()) {
					continue;
				}
				if (robot.getWaterVol() <= 0) {
					moveEvents.addAll(robot.fetchWater());
					continuer = true;
					continue;
				}
				double coutLoc = robot.timeToMoveTo(fire.getPosition());
				if ( coutLoc <= coutMin && coutLoc >= 0){
					nextest = robot;
					coutMin = coutLoc;
					continuer = true;
				}
			}
			
			if (nextest != null)
				moveEvents.addAll(nextest.eteindreIncendie(fire));
		}
		this.simu.ajouteEvenement(moveEvents);
		simulationTerminee = !continuer; 
		
	}

}
