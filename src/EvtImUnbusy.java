
public class EvtImUnbusy extends Evenement {

	private Robot robot;
	
	public EvtImUnbusy(long d, Robot r) {
		super(d);
		this.robot = r;
	}
	@Override
	public void execute() {
		this.robot.unBusy();
	}
}
