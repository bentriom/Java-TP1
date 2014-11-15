/**
 * Permet de déplacer un robot dans une direction
 * @author bentriom
 *
 */
public class EvtDeplacement extends Evenement {

	private Robot robot;
	private Case position;
	
	public EvtDeplacement(long d, Robot r, Case c) {
		super(d);
		this.robot = r;
		this.position = c;
	}
	
	/* Deplacement du robot dans la direction dir */
	@Override
	public void execute() {
		this.robot.moveTo(this.position);
	}
}