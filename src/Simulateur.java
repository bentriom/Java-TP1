
import java.util.LinkedList;

public class Simulateur {
	private long dateCourrante = 0;
	private long dateEvtMax = 0;
	private LinkedList<Evenement> evenements;
	
	/* On utilise le constructeur de Object */
	public Simulateur() {
		this.dateCourrante = 0;
	}
	
	/* Méthodes */
	public void incrementeDate() {
		this.dateCourrante++;
	}
	
	/* On ajoute les evenements dans le desordre
	 * Le manager se chargera de mieux organiser la liste d'evts
	 */
	public void ajouteEvenement(Evenement e) {
		this.evenements.add(e);
		if (dateEvtMax < e.getDate())
			dateEvtMax = e.getDate();
	}
	
	public LinkedList<Evenement> getEvts() {
		return this.evenements;
	}
	
	public boolean simulationTerminee() {
		return (dateCourrante > dateEvtMax);
	}
}
