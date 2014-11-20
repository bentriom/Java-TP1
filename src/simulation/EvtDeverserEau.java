package simulation;
import elements.Incendie;
import elements.Robot;


/**
 * Classe d'Evenement permettant de verser de l'eau sur un incendie
 * @author dutrieux
 *
 */
public class EvtDeverserEau extends Evenement {
	
	private Incendie incendie;
	
	/**
	 * 
	 * @param d temps dans lequel l'evenement allait être exécuté après sa création
	 * @param i incendie sur lequel deverser de l'eau
	 * @param r robot concerné par l'Evenement
	 */
	public EvtDeverserEau(long d, Incendie i, Robot r) {
		super(d,r);
		this.incendie = i;
	}

	/**
	 *  dervers de l'eau sur un incendie
	 *  apres l'execution, le robot n'est plus occupé.
	 */
	@Override
	public void execute() {
		this.robot.deverserEau(this.incendie);
		this.robot.unBusy();
	}
}
