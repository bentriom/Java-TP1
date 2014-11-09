/**
 * Classe qui definit l'entite Case, pour définir la classe Carte
 * @author Mahmoud Bentriou, Mathias Biehler, Cyril Dutrieux
 */


public class Case {
	private int ligne;
	private int colonne;
	private NatureTerrain nature;
	static Carte map;
	
	/* Constructeur */
	public Case(int l, int c, NatureTerrain n) {
		this.ligne = l;
		this.colonne = c;
		this.nature = n;
	}

	/* Constructeur */
	public Case(int l, int c, NatureTerrain n, Carte map) {
		this.ligne = l;
		this.colonne = c;
		this.nature = n;
		Case.map = map;
	}
	
	/* Constructeur de copie */
	public Case(Case c) {
		this(c.ligne,c.colonne,c.nature);
	}
	
	public int getLigne() {
		return this.ligne;
	}
	
	public int getColonne() {
		return this.colonne;
	}
	
	public NatureTerrain getNature() {
		return this.nature;
	}
	
	public void setNature(NatureTerrain n) {
		this.nature = n;
	}

	public Case getVoisin(Direction d) {
		return map.getVoisin(this, d);
	}

	public String image() {
	    String s;
	    switch(nature) {
	        case TERRAIN_LIBRE : s = "images/lowlands.png";
	                             break;
	        case HABITAT : s = "images/town.png";
	                             break;
	        case FORET : s = "images/forest.png";
	        					break;
	        case ROCHE : s = "images/rock.png";
	        				break;
	        case EAU : s = "images/water.png";
							break;
	        default : s = "images/lowlands.png";
	                  break;
	    }
	    return s;
	}
	
	@Override
	public String toString() {
		return this.nature.toString();
	}
	

}
