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
		
		for(int i=1;i<20;i+=2) {
			this.simu.ajouteEvenement(new DeplacementElementaire(i,
				this.simu.getData().getRobot(0),Direction.NORD));
		}
		for(int i=2;i<20;i+=2) {
			this.simu.ajouteEvenement(new DeplacementElementaire(i,
				this.simu.getData().getRobot(0),Direction.SUD));
		}
		for(int i=20;i<40;i+=2) {
			this.simu.ajouteEvenement(new DeplacementElementaire(i,
				this.simu.getData().getRobot(2),Direction.OUEST));
		}
		for(int i=21;i<40;i+=2) {
			this.simu.ajouteEvenement(new DeplacementElementaire(i,
				this.simu.getData().getRobot(2),Direction.EST));
		}
	}

}
