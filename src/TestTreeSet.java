import java.util.*;

/**
 * Test l'utilisation de listes chainées triées (TreeSet)
 * @author bentriom
 */

public class TestTreeSet {

	public static void main(String[] args) {
		ComparateurEvenements C = new ComparateurEvenements();
		
		TreeSet<Evenement> Evts = new TreeSet<Evenement>(C);
		Evts.add(new EvenementMsg(20,"slt\n"));
		Evts.add(new EvenementMsg(40,"kikou"));
		Evts.add(new EvenementMsg(10,"slt"));
		Evts.add(new EvenementMsg(15,"slt"));
		
		/* Doit afficher evenement avec date : 10 */
		System.out.println(Evts.first().toString());
	}
}
