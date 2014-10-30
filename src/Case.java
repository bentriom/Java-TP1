/**
 * Classe qui définit l'entité Case, pour définir l'entité Carte
 * @author Mahmoud Bentriou, Mathias Biehler, Cyril Dutrieux
 */


public class Case {
	private int ligne;
	private int colonne;
	private NatureTerrain nature;
	
	/* Constructeur */
	public Case(int l, int c, NatureTerrain n) {
		this.ligne = l;
		this.colonne = c;
		this.nature = n;
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
	
	@Override
	public String toString() {
		return this.nature.toString();
	}
}
