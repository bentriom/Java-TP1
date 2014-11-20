package elements;

/**
 * Exception quand on remplit un robot sans être à coté de l'eau
 * @author bentriom
 *
 */

public class ExceptionRemplirSansEau extends Exception {
    public ExceptionRemplirSansEau() {
        System.out.println("Le robot essaye de se remplir sans etre a cote d'eau. ");
    }
}
