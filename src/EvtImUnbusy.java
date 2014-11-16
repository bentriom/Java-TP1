
public class EvtImUnbusy extends Evenement {

	public EvtImUnbusy(long d, Robot r) {	
		super(d,r);
		System.out.println("Je construis un evt unbusy de date = " + String.valueOf(d));
	}
	@Override
	public void execute() {
		System.out.println("Je passe ici!!!!!");
		//this.robot.unBusy();
	}
}
