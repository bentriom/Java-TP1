
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
		System.out.println("Ok on va deverser eau");
		this.robot.deverserEau(this.incendie);
		if (this.incendie.getWaterNeed() <= 0) {
			System.out.println("On vient de supprimer un incendie");
		}
	}
}
