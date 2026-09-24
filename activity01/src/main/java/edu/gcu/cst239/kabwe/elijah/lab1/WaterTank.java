package edu.gcu.cst239.kabwe.elijah.lab1;

/**
 * WaterTank represents the water reservoir for the coffee maker.
 */
public class WaterTank {
    private boolean waterAvailable = false;

    /**
     * Fills the water tank.
     */
    public void addWater() {
        this.waterAvailable = true;
    }

    /**
     * Checks if water is available in the tank.
     * @return true if water is present, false otherwise
     */
    public boolean hasWater() {
        return this.waterAvailable;
    }
}