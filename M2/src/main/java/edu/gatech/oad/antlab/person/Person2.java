package edu.gatech.oad.antlab.person;

import java.util.Random;
import java.util.ArrayList;
import java.lang.StringBuilder;

/**
 *  A simple class for person 2
 *  returns their name and a
 *  modified string
 *
 * @author Jake Waldner
 * @version 1.2
 */
public class Person2 {
    /** Holds the persons real name */
    private String name;
	 	/**
	 * The constructor, takes in the persons
	 * name
	 * @param pname the person's real name
	 */
	 public Person2(String pname) {
	   name = pname;
	 }
	/**
	 * This method should take the string
	 * input and return its characters in
	 * random order.
	 * given "gtg123b" it should return
	 * something like "g3tb1g2".
	 *
	 * @param input the string to be modified
	 * @return the modified string
	 */
	private String calc(String input) {
	  //Person 2 put your implementation here
		if (input == null) {
			return null;
		}
		// String newString = "";
		// Random rand = new Random();
        //
		// ArrayList<Integer> shuffle = new ArrayList<>();
		// for (int i = 0; i < input.length(); i ++) {
		// 	int randomInt = rand.nextInt(input.length());
		// 	while (shuffle.contains(randomInt)) {
		// 		randomInt = randomInt + 1;
		// 		if (randomInt > input.length() - 1) {
		// 			randomInt = 0;
		// 		}
		// 	}
		// 	shuffle.add(randomInt);
		// }
		// StringBuilder sb = new StringBuilder();
		// for (int i = 0; i < input.length(); i++) {
		// 	sb.append(input.indexOf(shuffle.remove(i)));
		// }
        //
		// return sb.toString();
		return input;
	}
	/**
	 * Return a string rep of this object
	 * that varies with an input string
	 *
	 * @param input the varying string
	 * @return the string representing the
	 *         object
	 */
	public String toString(String input) {
	  return name + calc(input);
	}
}
