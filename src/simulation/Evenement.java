package simulation;
import elements.Robot;

/**
 * Définition de la classe abstraite Evenement
 * Ces objets concernent un robot, sont datées et contiennent une fonction à executer
 * @author dutrieux, biehler, bentriou
 *
 */

public abstract class Evenement implements Comparable<Evenement> {
	protected long date;
	protected Robot robot;
	
	/**
	 * Constructeur d'Evenement
	 * @param d temps dans lequel l'evenement allait être exécuté après sa création
	 * @param r robot concerné par l'Evenement
	 */
	public Evenement(long d, Robot r) {
		this.date = Simulateur.getDate() + d;
		this.robot = r;
	}
	
	/**
	 * Accesseur de la date a laquelle executer l'évenement
	 * @return la date absolue de l'évenement
	 */
	public long getDate() {
		return this.date;
	}
	
	/**
	 * Modifie la date de l'évenement
	 * @param d date absolue a laquelle sera executé l'évenement
	 */
	public void setDate(long d) {
		this.date = d;
	}

	/**
	 * Comparateur entre évenement
	 */
	public int compareTo(Evenement E) {
		long res = (E.getDate() - this.date);
		if (this.equals(E)) 
			return 0;
		if (res>0) 
			return -1;
		else if (res==0) 
			return -1;
		else
			return 1;
	}
	
	@Override
	public String toString() {
		return "Evenement avec date : " + String.valueOf(date);
	}
	
	/**
	 * Procédure a executer a la date de l'evenement
	 */
	public abstract void execute();
}
