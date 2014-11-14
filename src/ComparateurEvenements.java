import java.util.*;

/**
 * Classe qui implémente l'interface Comparator
 * Permet de comparer deux objets Evenement, pour pouvoir les trier dans une liste chainée
 * @author bentriom
 */
public class ComparateurEvenements implements Comparator<Evenement> {
	
	public int compare(Evenement E1, Evenement E2) {
		return E1.compareTo(E2);
	}
	
	/* On utilise la méthode héritée de Object */
	public boolean equals(Evenement E) {
		return this.equals(E);
	}
}
