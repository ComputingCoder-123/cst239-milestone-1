package edu.gcu.cst239.kabwe.elijah.lab1;

/**
 * Driver class demonstrating CoffeeMaker states.
 */
public class CoffeeMakerDemo {
    public static void run() {
        WaterTank tank = new WaterTank();
        FilterBasket basket = new FilterBasket();
        CoffeeMaker maker = new CoffeeMaker(tank, basket);

        System.out.println("Attempt 1 - nothing loaded: " + maker.startBrewing());

        tank.addWater();
        basket.loadFilter();
        System.out.println("Attempt 2 - needs grounds: " + maker.startBrewing());

        basket.addGrounds();
        System.out.println("Attempt 3 - ready: " + maker.startBrewing());
        System.out.println("Brewing: " + maker.isBrewing());

        maker.stopBrewing();
        System.out.println("Brewing after stop: " + maker.isBrewing());
    }
}