/**
 * Classe qui definit l'entite Incendie
 * @author Mahmoud Bentriou, Mathias Biehler, Cyril Dutrieux
 */


public class Incendie {
    private Case position;
    private int waterNeed;
    
    /**
     * Constructeur de classe
     * @param position
     * @param waterNeed
     */
    public Incendie( Case position, int waterNeed)
    {
        this.position = position;
        this.waterNeed = waterNeed;
    } 

    /**
     * Constructeur de copie
     * @param fire
     */
    public Incendie(Incendie fire)
    {
        this.position = fire.position;
        this.waterNeed = fire.waterNeed;
    }
    
    /**
     * Accesseur de la position de l'incendie
     * @return Case de l'incendie
     */
    public Case getPosition() {
        return position;
    }
    
    /**
     * Accesseur de l'eau nécessaire pour éteindre l'incendie
     * @return Volume entier d'eau pour éteindre l'incendie
     */
    public int getWaterNeed() {
    	return this.waterNeed;
    }
    
    /**
     * Modifieur de l'eau nécessaire pour éteindre l'incendie
     * @param w
     */
    public void setWaterNeed(int w) {
    	this.waterNeed = w;
    }
}
