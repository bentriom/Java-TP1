package environnement;


/**
 * Classe qui definit l'entite Case, pour définir la classe Carte
 * @author Mahmoud Bentriou, Mathias Biehler, Cyril Dutrieux
 */


public class Case {
	private int ligne;
	private int colonne;
	private NatureTerrain nature;
	
	/**
	 * Constructeur de case
	 * @param l indice de la ligne de la Case
	 * @param c indice de la colonne de la Case
	 * @param n nature du terrain de la Case
	 */
	public Case(int l, int c, NatureTerrain n) {
		this.ligne = l;
		this.colonne = c;
		this.nature = n;
	}
 
	
	/**
	 * Constructeur de copie de Case
	 * @param c case à copier
	 */
	public Case(Case c) {
		this(c.ligne,c.colonne,c.nature);
	}
	
	/**
	 * Accesseur de la ligne de la case
	 * @return la ligne de la case
	 */
	public int getLigne() {
		return this.ligne;
	}
	
	/**
	 * Accesseur de la colonne de la case
	 * @return la colonne de la case
	 */
	public int getColonne() {
		return this.colonne;
	}
	
	/**
	 * Accesseur de la nature du terrain de la case
	 * @return la nature du terrain de la case
	 */
	public NatureTerrain getNature() {
		return this.nature;
	}
	
	/**
	 * Modifier de la nature du terrain de la case
	 * @param n nouvelle nature du terrain
	 */
	public void setNature(NatureTerrain n) {
		this.nature = n;
	}
 
	/**
	 * Renvoie l'image a afficher selon le type de terrain
	 * @return le chemin vers l'image representant une case de ce type
	 */
	public String image() {
	    String s;
	    switch(this.nature) {
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
	        default : s = "images/water.png";
	                  break;
	    }
	    return s;
	}
	
	@Override
	public String toString() {
		return "(" + String.valueOf(ligne) + "," + String.valueOf(colonne) + ")" +this.nature.toString();
	}
	

}
