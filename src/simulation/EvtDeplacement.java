package simulation;
import elements.ExceptionTeleportation;
import elements.Robot;
import environnement.Case;


/**
 * Permet de déplacer un robot dans une case voisine
 * @author bentriom
 *
 */
public class EvtDeplacement extends Evenement {

	private Case position;
	
	/**
	 * Constructeur d'evenement de déplacement
	 * @param d temps dans lequel l'evenement allait être exécuté après sa création
	 * @param r robot concerné par l'Evenement
	 * @param c Case vers laquelle se déplacer
	 */
	public EvtDeplacement(long d, Robot r, Case c) {
		super(d,r);
		this.position = c;
	}
	
	/**
	 * Deplacement du robot dans une case voisine prédéfinie
	 * 
	 */
	@Override
	public void execute() {
        try {
		    this.robot.moveTo(this.position);
        }
        catch (ExceptionTeleportation e) { }
	}
}
