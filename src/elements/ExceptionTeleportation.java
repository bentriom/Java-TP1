package elements;
public class ExceptionTeleportation extends Exception {
    public ExceptionTeleportation() {
        System.out.println("Le robot essaye de se deplacer sur une case qui n'est pas a coté. ");
    }
}
