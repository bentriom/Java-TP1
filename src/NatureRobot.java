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
	
	NatureRobot(String ch) {
		this.chaine = ch;
	}
	
	@Override
	public String toString() {
		return chaine;
	}
}
