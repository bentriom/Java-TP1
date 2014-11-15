
public class DeverserEau extends Evenement {
	private Incendie incendie;
	private Robot robot;
	
	public DeverserEau(long d, Incendie i, Robot r) {
		super(d);
		this.incendie = i;
		this.robot = r;
	}

	@Override
	public void execute() {
		System.out.println("ok on passe ici");
		this.robot.deverserEau(this.incendie);
	}
}
