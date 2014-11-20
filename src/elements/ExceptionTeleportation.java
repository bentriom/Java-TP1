package elements;

/**
 * Exception qui se lance quand on essaye d'atteindre une case qui n'est pas a coté du robot
 * @author Mahmoud Bentriou, Mathias Biehler, Cyril Dutrieux
 *
 */

public class ExceptionTeleportation extends Exception {
    public ExceptionTeleportation() {
        System.out.println("Le robot essaye de se deplacer sur une case qui n'est pas a coté. ");
    }
}
