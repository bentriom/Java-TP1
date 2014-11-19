
public class EvtRemplirRobot extends Evenement {
	
	/**
	 * Constructeur d'evenement de remplissage de robot
	 * @param d temps dans lequel l'evenement allait être exécuté après sa création
	 * @param r robot concerné par l'Evenement
	 */
	public EvtRemplirRobot(long d, Robot r) {
		super(d,r);
	}

	/**
	 * Remplit le robot en eau.
	 * Apres l'execution, le robot n'est plus occupé.
	 */
	@Override
	public void execute() {
        try {
		    this.robot.remplir();
        }
        catch (ExceptionRemplirSansEau e) { }
		this.robot.unBusy();
	}

}
