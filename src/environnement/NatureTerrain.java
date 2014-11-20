package environnement;

/**
 * Enumeration d'objets qui correspondent à un type de terrain
 * @author Mahmoud Bentriou, Mathias Biehler, Cyril Dutrieux
 *
 */

public enum NatureTerrain {
	EAU("Eau"),
	FORET("Foret"),
	ROCHE("Roche"),
	TERRAIN_LIBRE("Terrain libre"),
	HABITAT("Habitat");
	
	private String chaine = "";
	
	/** Constructeur des objets enum
	 * @param ch
	 */
	NatureTerrain(String ch) {
		this.chaine = ch;
	}
	
	/**
	 * Méthode qui renvoie la chaine du type de terrain
	 * @return Nom du type de terrain
	 */
	@Override
	public String toString() {
		return chaine;
	}
}
