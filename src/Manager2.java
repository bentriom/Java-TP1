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
		
		DonneesSimulation data = simu.getData();
		long dateCour = simu.getDate();
		int nbIncendies = data.getSizeIncendie();
		int nbRobots = data.getSizeRobot();
		for (int i=0; i<nbIncendies; i++){
			Incendie fire= simu.getData().getIncendie(i);
			if (fire.getWaterNeed() <= 0) {
				continue;
			}
			double coutMin = Double.MAX_VALUE;
			Robot nextest = null;
			for (int r=0; r<nbRobots; r++){
				Robot robot = simu.getData().getRobot(r);
				if (robot.isBusy()) {
					continue;
				}
				if (robot.getWaterVol() <= 0) {
					moveEvents.addAll(robot.fetchWater(dateCour));
				}
				double coutLoc = robot.timeToMoveTo(fire.getPosition());
				if ( coutLoc <= coutMin && coutLoc >= 0){
					nextest = robot;
					coutMin = coutLoc;
				}
			}
			
			if (nextest != null)
				moveEvents.addAll(nextest.eteindreIncendie(dateCour, fire));
		}
		this.simu.ajouteEvenement(0,moveEvents);
		
		/* On va afficher les evenements par robot */
		/*
		for(int i=0; i < this.simu.getData().getSizeRobot(); i++) {
			Robot r = this.simu.getData().getRobot(i);
			System.out.println("Robot de type : " + r.specifString());
			if (r.isBusy())
				System.out.println("Je suis busy");
			else
				System.out.println("je suis pas busy");
			for(Evenement E : this.simu.getEvts()) {
				System.out.println(E);
			}
			System.out.print("\n ------ date = " + String.valueOf(clock) + "\n");
		}*/
		//System.out.println("evenements = " + this.simu.getEvts());
	}

}
