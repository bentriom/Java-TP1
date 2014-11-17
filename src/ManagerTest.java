/** 
 * Manager test, qui va cr�er
 * @author Moud
 *
 */

import java.util.LinkedList;

public final class ManagerTest extends Manager {
	
	private boolean dejapasse = false;
	
	public ManagerTest(Simulateur s) {
		super(s);
	}

	/* Ceci est un manager test
	 * Il va faire bouger un robot puis un autre
	 */
	@Override
	public void manage() {
		long dateCour = this.simu.getDate();
		LinkedList<Evenement> evtlist = new LinkedList<Evenement>();
		if (!dejapasse) {
			Robot robot = this.simu.getData().getRobot(0);
			Incendie incendie = this.simu.getData().getIncendie(0);
			evtlist.addAll(robot.fetchWater(dateCour));
			evtlist.addAll(robot.eteindreIncendie(1600,incendie));
			this.simu.ajouteEvenement(0, evtlist);
			this.dejapasse = true;
		}
		
		/*
		this.simu.ajouteEvenement(temps_absolu,robot.moveTo(Direction.OUEST));
		temps_absolu++;
		this.simu.ajouteEvenement(temps_absolu,robot.remplirEau());
		temps_absolu++;
		this.simu.ajouteEvenement(temps_absolu,robot.moveTo(Direction.NORD));
		temps_absolu++;
		
		Incendie incendie1 = this.simu.getData().getIncendie(4);
		System.out.println("Ok on va creer les evts pour eteindre le bail");
		this.simu.ajouteEvenement(temps_absolu,robot.eteindreIncendie(temps_absolu,incendie1));
		*/
	}
}
