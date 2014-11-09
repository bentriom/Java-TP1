
public abstract class Manager {
	protected Simulateur simu;
	
	public Manager(Simulateur s) {
		this.simu = s;
	}
	
	public Simulateur getSimu() {
		return this.simu;
	}
	
	public abstract void manage();
}
