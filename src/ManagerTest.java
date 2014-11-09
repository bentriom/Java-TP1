/** 
 * Manager test, qui va créer
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
		EvenementMsg evt = new EvenementMsg(1,"coucou\n");
		this.simu.ajouteEvenement(evt);
	}

}
