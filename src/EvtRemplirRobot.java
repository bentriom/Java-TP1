
public class EvtRemplirRobot extends Evenement {
	
	public EvtRemplirRobot(long d, Robot r) {
		super(d,r);
	}

	@Override
	public void execute() {
		this.robot.remplir();
		this.robot.unBusy();
	}

}
