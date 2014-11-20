package elements;
public class ExceptionRemplirSansEau extends Exception {
    public ExceptionRemplirSansEau() {
        System.out.println("Le robot essaye de se remplir sans etre a cote d'eau. ");
    }
}
