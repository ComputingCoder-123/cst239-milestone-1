package edu.gcu.cst239.kabwe.elijah.lab1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a person with various attributes and abilities.
 */
public class Person {
	/** Unique identifier for the person. */
	private int id;
	/** Name of the person. */
	private String name;
	/** Age of the person. */
	private int age;
	/** Number of imaginary friends the person has. */
	private int numberOfImaginaryFriends;
	/** Whether the person is a time traveler. */
	private boolean isTimeTraveler;
	/** Coffee consumption rate of the person. */
	private double coffeeConsumptionRate;

	/** List of super powers the person possesses. */
	private List<String> superPowers = new ArrayList<>();
	/** Map of favorite movies with their ranking. */
	private Map<Integer, String> favoriteMovies = new HashMap<>();
	
	/**
	 * Default constructor initializing default values.
	 */
	public Person() {
		super();
		this.id = 0;
		this.name = "John Doe";
		this.age = 0;
		this.numberOfImaginaryFriends = 0;
		this.isTimeTraveler = false;
		this.coffeeConsumptionRate = 0.0;
		
		this.superPowers.add("Invisibility");
	}

	/**
	 * Constructs a person with all attributes specified.
	 * @param id Unique identifier
	 * @param name Name of the person
	 * @param age Age of the person
	 * @param numberOfImaginaryFriends Number of imaginary friends
	 * @param isTimeTraveler Whether the person is a time traveler
	 * @param coffeeConsumptionRate Coffee consumption rate
	 * @param superPowers List of super powers
	 * @param favoriteMovies Map of favorite movies
	 */
	public Person(int id, String name, int age, int numberOfImaginaryFriends, boolean isTimeTraveler,
			double coffeeConsumptionRate, List<String> superPowers, Map<Integer, String> favoriteMovies) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.numberOfImaginaryFriends = numberOfImaginaryFriends;
		this.isTimeTraveler = isTimeTraveler;
		this.coffeeConsumptionRate = coffeeConsumptionRate;
		this.superPowers = superPowers;
		this.favoriteMovies = favoriteMovies;
	}

	/**
	 * Constructs a person with a name and age.
	 * @param n Name of the person
	 * @param a Age of the person
	 */
	public Person(String n, int a) {
		this();
		this.name = n;
		this.age = a;
	}

	/**
	 * Gets the unique identifier of the person.
	 * @return id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the unique identifier of the person.
	 * @param id Unique identifier
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Gets the name of the person.
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the person.
	 * @param name Name of the person
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the age of the person.
	 * @return age
	 */
	public int getAge() {
		return age;
	}

	/**
	 * Sets the age of the person.
	 * @param age Age of the person
	 */
	public void setAge(int age) {
		this.age = age;
	}

	/**
	 * Gets the number of imaginary friends.
	 * @return number of imaginary friends
	 */
	public int getNumberOfImaginaryFriends() {
		return numberOfImaginaryFriends;
	}

	/**
	 * Sets the number of imaginary friends.
	 * @param numberOfImaginaryFriends Number of imaginary friends
	 */
	public void setNumberOfImaginaryFriends(int numberOfImaginaryFriends) {
		this.numberOfImaginaryFriends = numberOfImaginaryFriends;
	}

	/**
	 * Checks if the person is a time traveler.
	 * @return true if time traveler, false otherwise
	 */
	public boolean isTimeTraveler() {
		return isTimeTraveler;
	}

	/**
	 * Sets whether the person is a time traveler.
	 * @param isTimeTraveler true if time traveler
	 */
	public void setTimeTraveler(boolean isTimeTraveler) {
		this.isTimeTraveler = isTimeTraveler;
	}

	/**
	 * Gets the coffee consumption rate.
	 * @return coffee consumption rate
	 */
	public double getCoffeeConsumptionRate() {
		return coffeeConsumptionRate;
	}

	/**
	 * Sets the coffee consumption rate.
	 * @param coffeeConsumptionRate Coffee consumption rate
	 */
	public void setCoffeeConsumptionRate(double coffeeConsumptionRate) {
		this.coffeeConsumptionRate = coffeeConsumptionRate;
	}

	/**
	 * Gets the list of super powers.
	 * @return list of super powers
	 */
	public List<String> getSuperPowers() {
		return superPowers;
	}

	/**
	 * Sets the list of super powers.
	 * @param superPowers List of super powers
	 */
	public void setSuperPowers(List<String> superPowers) {
		this.superPowers = superPowers;
	}

	/**
	 * Gets the map of favorite movies.
	 * @return map of favorite movies
	 */
	public Map<Integer, String> getFavoriteMovies() {
		return favoriteMovies;
	}

	/**
	 * Sets the map of favorite movies.
	 * @param favoriteMovies Map of favorite movies
	 */
	public void setFavoriteMovies(Map<Integer, String> favoriteMovies) {
		this.favoriteMovies = favoriteMovies;
	}

	/**
	 * Compares coffee consumption rate with another person.
	 * @param other The other person to compare with
	 * @return Comparison result as a string
	 */
	public String compareCoffeeConsumption(Person other) {
		if (this.coffeeConsumptionRate > other.coffeeConsumptionRate) {
			return this.name + " drinks more coffee than " + other.name + ".";
		}
		else if (this.coffeeConsumptionRate < other.coffeeConsumptionRate) {
			return other.name + " drinks more coffee than " + this.name + ".";
		} else {
			return this.name + " drinks the same amount of coffee as " + other.name;
		}
	}
	
	/**
	 * Returns a string representation of the person.
	 * @return String representation
	 */
//	@Override
//	public String toString() {
//		return "Person [id=" + id + ", name=" + name + ", age=" + age + ", numberOfImaginaryFriends="
//				+ numberOfImaginaryFriends + ", isTimeTraveler=" + isTimeTraveler + ", coffeeConsumptionRate="
//				+ coffeeConsumptionRate + ", superPowers=" + superPowers + ", favoriteMovies=" + favoriteMovies + "]";
//	}
//	
	

}
