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
	 * Il va creer des messages a afficher selon certaines dates
	 */
	@Override
	public void manage() {
		this.simu.getEvts().clear();
		this.simu.ajouteEvenement(new EvenementMsg(2,"decale gwada a gauche\n"));
		this.simu.ajouteEvenement(new EvenementMsg(4,"decale gwada a droite\n"));
		this.simu.ajouteEvenement(new EvenementMsg(5,"maladie du bouger bouger\n"));
	}

}
