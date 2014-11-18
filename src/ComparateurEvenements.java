
import java.util.*;

/**
 * Classe qui implémente l'interface Comparator
 * Permet de comparer deux objets Evenement, pour pouvoir les trier dans une liste chainée
 * @author bentriom
 */
public class ComparateurEvenements implements Comparator<Evenement> {
	
	/*
	 * Compare deux évenements
	 * (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(Evenement E1, Evenement E2) {
		return E1.compareTo(E2);
	}
	
	/**
	 * Test l'égalité entre évenements
	 * elle utilise la méthode héritée de Object
	 * @param E Evenement avec lequel tester l'égalité
	 * @return true si (this==E)
	 */
	public boolean equals(Evenement E) {
		return this.equals(E);
	}
}
