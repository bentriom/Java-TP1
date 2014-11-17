
public class RemplirRobot extends Evenement {
	
	public RemplirRobot(long d, Robot r) {
		super(d,r);
	}

	@Override
	public void execute() {
		this.robot.remplir();
		this.robot.unBusy();
	}

}
