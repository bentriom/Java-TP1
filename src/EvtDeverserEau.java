
public class EvtDeverserEau extends Evenement {
	
	private Incendie incendie;
	
	public EvtDeverserEau(long d, Incendie i, Robot r) {
		super(d,r);
		this.incendie = i;
	}

	@Override
	public void execute() {
		System.out.println("Ok on demande a deverser eau par robot " + this.robot.specifString());
		this.robot.deverserEau(this.incendie);
		if (this.incendie.getWaterNeed() <= 0) {
			System.out.println("Le robot " + this.robot.specifString() + " a suppr un incendie");
		}
		this.robot.unBusy();
	}
}
