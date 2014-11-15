/** 
 * Manager test, qui va cr�er
 * @author Moud
 *
 */
public final class ManagerTest extends Manager {
	
	public ManagerTest(Simulateur s) {
		super(s);
	}

	/* Ceci est un manager test
	 * Il va faire bouger un robot puis un autre
	 */
	@Override
	public void manage() {
		this.simu.getEvts().clear();
		
		Robot robot = this.simu.getData().getRobot(1);
		int temps_absolu = 0;
		
		this.simu.ajouteEvenement(temps_absolu,robot.moveTo(Direction.OUEST));
		temps_absolu++;
		this.simu.ajouteEvenement(temps_absolu,robot.remplirEau());
		temps_absolu++;
		this.simu.ajouteEvenement(temps_absolu,robot.moveTo(Direction.NORD));
		temps_absolu++;
		
		Incendie incendie1 = this.simu.getData().getIncendie(4);
		System.out.println("Ok on va creer les evts pour eteindre le bail");
		this.simu.ajouteEvenement(temps_absolu,robot.eteindreIncendie(temps_absolu,incendie1));
		
		/* On regarde le nb d'evts */
		System.out.println("evenements = " + String.valueOf(this.simu.getEvts().size()));
	}
}
