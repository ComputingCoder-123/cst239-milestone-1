package edu.gcu.cst239.kabwe.elijah.lab1;

/**
 * FilterBasket represents the coffee basket holding the filter and grounds.
 */
public class FilterBasket {
    private boolean filterLoaded = false;
    private boolean groundsLoaded = false;

    /**
     * Loads a filter into the basket.
     */
    public void loadFilter() {
        this.filterLoaded = true;
    }

    /**
     * Adds coffee grounds to the basket.
     */
    public void addGrounds() {
        this.groundsLoaded = true;
    }

    /**
     * Checks if both the filter and grounds are loaded.
     * @return true if both filter and grounds are present
     */
    public boolean isReady() {
        return this.filterLoaded && this.groundsLoaded;
    }
}