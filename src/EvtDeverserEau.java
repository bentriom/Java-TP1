
public class EvtDeverserEau extends Evenement {
	
	private Incendie incendie;
	
	public EvtDeverserEau(long d, Incendie i, Robot r) {
		super(d,r);
		this.incendie = i;
	}

	@Override
	public void execute() {
		System.out.println("Ok on va deverser eau");
		this.robot.deverserEau(this.incendie);
		if (this.incendie.getWaterNeed() <= 0) {
			System.out.println("On vient de supprimer un incendie");
		}
		this.robot.unBusy();
	}
}
