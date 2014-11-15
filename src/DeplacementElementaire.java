/**
 * Permet de déplacer un robot dans une direction
 * @author bentriom
 *
 */
public class DeplacementElementaire extends Evenement {

	private Robot robot;
	private Direction direction;
	
	public DeplacementElementaire(long d, Robot r, Direction dir) {
		super(d);
		this.robot = r;
		this.direction = dir;
	}
	
	/* Deplacement du robot dans la direction dir */
	@Override
	public void execute() {
		this.robot.moveVoisin(direction);
	}
}
