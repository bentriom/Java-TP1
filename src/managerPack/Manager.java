package managerPack;
import simulation.Simulateur;

/**
 * Classe qui définit un manager de robots selon les incendies
 * Décide des événements à executer 
 * @author Mahmoud Bentriou, Mathias Biehler, Cyril Dutrieux
 *
 */
public abstract class Manager {
	protected Simulateur simu;
	protected boolean nothingHappens = false;
	protected boolean simulationTerminee = false;
	
	/** Constructeur de classe
	 * @param s
	 */
	public Manager(Simulateur s) {
		this.simu = s;
	}
	
	/**
	 * Accesseur du simulateur
	 * @return Simulateur du manager
	 */
	public Simulateur getSimu() {
		return this.simu;
	}
	
	/**
	 * Méthode principale qui gère les données du simulateur
	 * @return void
	 */
	public abstract void manage();
	
	/**
	 * Méthode qui détermine si la simulation est terminée
	 * @return Vrai si simulation terminée
	 */
	public boolean end(){
		return (simulationTerminee && simu.getEvts().isEmpty());
	}
}
