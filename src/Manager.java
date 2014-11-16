
public abstract class Manager {
	protected Simulateur simu;
	protected boolean simulationTerminee = false;
	
	public Manager(Simulateur s) {
		this.simu = s;
	}
	
	public Simulateur getSimu() {
		return this.simu;
	}
	
	public abstract void manage();
	
	public boolean end(){
		return (simulationTerminee || simu.getEvts().isEmpty());
	}
}
