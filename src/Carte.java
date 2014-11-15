/**
 * Classe qui permet de modeliser la carte
 * @author Mahmoud Bentriou, Mathias Biehler, Cyril Dutrieux
 */

public class Carte {
	
	private int nbLignes;
	private int nbColonnes;	
	private int tailleCases;
	private Case [][] tabCases;

	/* Constructeur */
	public Carte(int lignes, int colonnes, int taillecase, Case [][] tabCases) {
		this.nbLignes = lignes;
		this.nbColonnes = colonnes;
		this.tailleCases = taillecase;
		this.tabCases = tabCases;
		tabCases[0][0] = new Case(0, 0, tabCases[0][0].getNature(),this);
	}
	
	/* Constructeur Carte terrain libre*/
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
	
	/* Constructeur de copie */
	public Carte(Carte carte) {
		this.nbLignes = carte.nbLignes;
		this.nbColonnes = carte.nbColonnes;
		this.tailleCases = carte.tailleCases;
		this.tabCases = carte.tabCases;
	}
	
	public int getNbLignes() {
		return this.nbLignes;
	}
	
	public int getNbColonnes() {
		return this.nbColonnes;
	}
	
	public int getTailleCases() {
		return this.tailleCases;
	}
	
	public Case getCase(int l, int c) {
		return this.tabCases[l][c];
	}
	
	public boolean voisinExiste(Case source, Direction d) {	
		switch(d) {
			case NORD:
				return (source.getColonne() >= 1);
			case EST:
				return (source.getLigne() <= this.nbLignes-2);		
			case OUEST:
				return (source.getLigne() >= 1);			
			case SUD:
				return (source.getColonne() <= this.nbColonnes-2);			
			default:
				return false;
		}
	}
	
	public Case getVoisin(Case source, Direction d) {
		if(this.voisinExiste(source,d)) {
			switch(d) {
			case  NORD:
				return this.tabCases[source.getLigne()][source.getColonne()-1];
			case  EST:
				return this.tabCases[source.getLigne()+1][source.getColonne()];
			case  OUEST:
				return this.tabCases[source.getLigne()-1][source.getColonne()];
			case  SUD:
				return this.tabCases[source.getLigne()][source.getColonne()+1];
			default:
				return source; // Pour satisfaire le compilateur
			}
		}
		return source; // De meme
	}
	
	public void affiche() {
		for(int i=0; i<this.nbLignes; i++) {
			for(int j=0;j<this.nbColonnes; j++) {
				System.out.print("(" + this.tabCases[i][j].toString() + ") ");
			}
			System.out.print("\n");
		}
	}
}
