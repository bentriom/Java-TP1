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
	
	NatureTerrain(String ch) {
		this.chaine = ch;
	}
	
	@Override
	public String toString() {
		return chaine;
	}
}
