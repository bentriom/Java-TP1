package environnement;


/**
 * Classe qui permet de modeliser la carte
 * @author Mahmoud Bentriou, Mathias Biehler, Cyril Dutrieux
 */

public class Carte {
	
	private int nbLignes;
	private int nbColonnes;	
	private int tailleCases;
	private Case [][] tabCases;

	/**
	 * Constructeur de Carte
	 * @param lignes nombre de lignes sur la carte
	 * @param colonnes nombre de colonnes sur la carte
	 * @param taillecase taille des cases en mètre 
	 * @param tabCases ensemble des cases présentes sur la carte
	 * 
	 */
	public Carte(int lignes, int colonnes, int taillecase, Case [][] tabCases) {
		this.nbLignes = lignes;
		this.nbColonnes = colonnes;
		this.tailleCases = taillecase;
		this.tabCases = tabCases;
	}
	
	/**
	 * Constructeur de Carte constituée uniquement de terrain libre
	 * @param lignes nombre de lignes sur la carte
	 * @param colonnes nombre de colonnes sur la carte
	 * @param taillecase taille des cases en mètre 
	 * 
	 */
	public Carte(int lignes, int colonnes, int taillecase) {
		this.nbLignes = lignes;
		this.nbColonnes = colonnes;
		this.tailleCases = taillecase;
		
		this.tabCases = new Case[lignes][colonnes];
		for(int i=0; i<lignes; i++) {
			for(int j=0; j<colonnes; j++) {
				this.tabCases[i][j] = new Case(i,j,NatureTerrain.TERRAIN_LIBRE);
			}
		}
		
	}
	
	/**
	 *  Constructeur de copie 
	 *  @param carte carte à copier 
	 */
	public Carte(Carte carte) {
		this.nbLignes = carte.nbLignes;
		this.nbColonnes = carte.nbColonnes;
		this.tailleCases = carte.tailleCases;
		this.tabCases = carte.tabCases;
		this.tabCases[0][0] = new Case(0, 0, tabCases[0][0].getNature());
	}
	
	/**
	 * Accesseur du nombre de lignes sur la carte
	 * @return le nombre de ligne de la carte
	 */
	public int getNbLignes() {
		return this.nbLignes;
	}
	
	
	/**
	 * Accesseur du nombre de colonnes sur la carte
	 * @return le nombre de colonnes de la carte
	 */
	public int getNbColonnes() {
		return this.nbColonnes;
	}
	

	/**
	 * Accesseur de la taille des cases sur la carte
	 * @return la taille des cases de la carte
	 */
	public int getTailleCases() {
		return this.tailleCases;
	}
	
	/**
	 * Accesseur d'une case de la carte
	 * @param l indice de la ligne
	 * @param c indice de la colonnes
	 * @return la case voulue
	 */
	public Case getCase(int l, int c) {
		return this.tabCases[l][c];
	}
	
	/**
	 * Test si le voisin d'une case existe
	 * @param source case etudiée
	 * @param d direction dans laquelle on teste si il y a une case voisine
	 * @return true si le voisin existe
	 */
	public boolean voisinExiste(Case source, Direction d) {	
		switch(d) {
			case NORD:
				return (source.getLigne() >= 1);
			case EST:
				return (source.getColonne() <= this.nbColonnes-2);		
			case OUEST:
				return (source.getColonne() >= 1);			
			case SUD:
				return (source.getLigne() <= this.nbLignes-2);			
			default:
				return false;
		}
	}
	
	/**
	 * Accesseur d'une case voisine à la case source
	 * @param source case etudiée
	 * @param d direction dans laquelle on veux avoir le voisin
	 * @return la case voisine si elle existe, la case source sinon
	 */
	public Case getVoisin(Case source, Direction d) {
		if(this.voisinExiste(source,d)) {
			switch(d) {
			case  NORD:
				return this.tabCases[source.getLigne()-1][source.getColonne()];
			case  EST:
				return this.tabCases[source.getLigne()][source.getColonne()+1];
			case  OUEST:
				return this.tabCases[source.getLigne()][source.getColonne()-1];
			case  SUD:
				return this.tabCases[source.getLigne()+1][source.getColonne()];
			default:
				return source; // Pour satisfaire le compilateur
			}
		}
		return source; // De meme
	}
	
	/**
	 * 
	 * @return La carte sous forme de String
	 */
	@Override
	public String toString() {
		String s = new String();
		for(int i=0; i<this.nbLignes; i++) {
			for(int j=0;j<this.nbColonnes; j++) {
				s += "(" + this.tabCases[i][j].toString() + ") ";
			}
			s += "\n";
		}
		return s;
	}
}
