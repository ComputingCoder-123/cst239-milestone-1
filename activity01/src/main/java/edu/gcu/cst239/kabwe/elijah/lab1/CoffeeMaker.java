package edu.gcu.cst239.kabwe.elijah.lab1;

/**
 * CoffeeMaker manages the brewing process using a WaterTank and FilterBasket.
 */
public class CoffeeMaker {
    private WaterTank waterTank;
    private FilterBasket filterBasket;
    private boolean brewing = false;

    /**
     * Constructs a CoffeeMaker with injected components.
     * @param waterTank the water tank component
     * @param filterBasket the filter basket component
     */
    public CoffeeMaker(WaterTank waterTank, FilterBasket filterBasket) {
        this.waterTank = waterTank;
        this.filterBasket = filterBasket;
    }

    /**
     * Starts brewing if water is present and filter basket is ready.
     * @return true if brewing successfully started, false otherwise
     */
    public boolean startBrewing() {
        if (this.waterTank.hasWater() && this.filterBasket.isReady()) {
            this.brewing = true;
            return true;
        }
        this.brewing = false;
        return false;
    }

    /**
     * Stops the brewing process.
     */
    public void stopBrewing() {
        this.brewing = false;
    }

    /**
     * Reports current brewing state.
     * @return true if currently brewing
     */
    public boolean isBrewing() {
        return this.brewing;
    }
}