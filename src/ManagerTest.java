/** 
 * Manager test, qui va cr�er
 * @author Mahmoud Bentriou, Mathias Biehler, Cyril Dutrieux
 *
 */

import java.util.LinkedList;

import managerPack.Manager;
import elements.Incendie;
import elements.Robot;
import simulation.Evenement;
import simulation.Simulateur;

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
		LinkedList<Evenement> evtlist = new LinkedList<Evenement>();
		if (!dejapasse) {
			Robot robot = this.simu.getData().getRobot(0);
			Incendie incendie = this.simu.getData().getIncendie(0);
			evtlist.addAll(robot.fetchWater());

			robot = this.simu.getData().getRobot(1);
			evtlist.addAll(robot.eteindreIncendie(incendie));
			this.simu.ajouteEvenement(evtlist);
			this.dejapasse = true;
		}
		
	}
}
