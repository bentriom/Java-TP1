
public abstract class Manager {
	protected Simulateur simu;
	protected boolean nothingHappens = false;
	protected boolean simulationTerminee = false;
	
	public Manager(Simulateur s) {
		this.simu = s;
	}
	
	public Simulateur getSimu() {
		return this.simu;
	}
	
	public abstract void manage();
	
	public boolean end(){
		return (simulationTerminee && nothingHappens && simu.getEvts().isEmpty());
	}
}
