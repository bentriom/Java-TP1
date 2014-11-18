
/**
 * Enumeration d'objets qui correspondent à un type de terrain
 * @author Mahmoud Bentriou, Mathias Biehler, Cyril Dutrieux
 *
 */

public enum NatureRobot {
	DRONE("DRONE"),
	PATTES("PATTES"),
	ROUES("ROUES"),
	CHENILLES("CHENILLES");
	
	private String chaine = "";
	
	/**
	 * Constructeur de classe
	 * @param ch
	 */
	NatureRobot(String ch) {
		this.chaine = ch;
	}
	
	/**
	 * Méthode qui renvoie la chaine du type de robot
	 * @return Nom du type de robot
	 */
	@Override
	public String toString() {
		return chaine;
	}
}
