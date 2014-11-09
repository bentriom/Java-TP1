/**
 * Classe qui definit l'entite Incendie
 * @author Mahmoud Bentriou, Mathias Biehler, Cyril Dutrieux
 */


public class Incendie {
    private Case position;
    private int waterNeed;
    
    public Incendie( Case position, int waterNeed)
    {
        this.position = position;
        this.waterNeed = waterNeed;
    } 

    public Incendie(Incendie fire)
    {
        this.position = fire.position;
        this.waterNeed = fire.waterNeed;
    }
    
    public Case getPosition() {
        return position;
    }
}
