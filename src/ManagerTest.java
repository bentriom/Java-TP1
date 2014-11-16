/** 
 * Manager test, qui va cr�er
 * @author Moud
 *
 */

import java.util.LinkedList;

public final class ManagerTest extends Manager {
	
	public ManagerTest(Simulateur s) {
		super(s);
	}

	/* Ceci est un manager test
	 * Il va faire bouger un robot puis un autre
	 */
	@Override
	public void manage() {
		LinkedList<Evenement> evtlist = new LinkedList<Evenement>();
		this.simu.getEvts().clear();
		
		Robot robot = this.simu.getData().getRobot(1);
		Incendie incendie = this.simu.getData().getIncendie(4);
		Carte map = this.simu.getData().getCarte();
		int temps_absolu = 0;

		
		this.simu.ajouteEvenement(0, evtlist);
		
		if (!robot.isBusy()) {
			evtlist = robot.moveToFar(map.getCase(0, 0));
			evtlist = robot.eteindreIncendie(temps_absolu, incendie);
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
		
		/* On regarde le nb d'evts */
		System.out.println("evenements = " + String.valueOf(this.simu.getEvts().size()));
	}
}
