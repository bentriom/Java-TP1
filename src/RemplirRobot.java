
public class RemplirRobot extends Evenement {
	
	private Robot robot;
	
	public RemplirRobot(long d, Robot r) {
		super(d);
		this.robot = r;
	}

	@Override
	public void execute() {
		this.robot.remplir();
	}

}
