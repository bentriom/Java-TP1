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
		
		Robot robot2 = this.simu.getData().getRobot(2);
		Robot robot0 = this.simu.getData().getRobot(0);
		Robot robot = this.simu.getData().getRobot(1);
		Incendie incendie = this.simu.getData().getIncendie(4);
		Carte map = this.simu.getData().getCarte();
		int temps_absolu = 0;
		if (!robot.isBusy()) {			
			System.out.println(robot.toString() + " : pas busy");
			evtlist = robot.moveToFar(map.getCase(0, 0));
			robot.busy();;

			this.simu.ajouteEvenement(0, evtlist);
			//System.out.println("taille evts enregistres apres bail : " + this.simu.getEvts().size());

		}
		
		if (!robot0.isBusy()) {			
			System.out.println(robot0.toString() + " : pas busy");
			evtlist = robot0.fetchWater(0);
			robot.busy();;
			this.simu.ajouteEvenement(0, evtlist);
			//System.out.println("taille evts enregistres apres bail : " + this.simu.getEvts().size());
		}
		
		if (!robot2.isBusy()) {			
			System.out.println(robot2.toString() + " : pas busy");
			evtlist = robot2.eteindreIncendie(0, this.simu.getData().getIncendie(5));
			robot.busy();;
			this.simu.ajouteEvenement(0, evtlist);
			//System.out.println("taille evts enregistres apres bail : " + this.simu.getEvts().size());
		}
		robot2.busy();
		robot.busy();
		robot0.busy();
		
		
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
