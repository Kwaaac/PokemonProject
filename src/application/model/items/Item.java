package application.model.items;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import application.model.pokemon.Pokemon;

/**
 * Represent an Item in the Pokemon Games, can be holdable or just usable
 * @author Armadindon
 */
public interface Item {
	
	/**
	 * Apply the effect on a pokemon
	 * @param p
	 */
	public void applyEffect(Pokemon p);
	
	/**
	 * Get the id of the Item
	 * @return the id
	 */
	public int getId();
	
	/**
	 * Get the name of the Item
	 * @return the name
	 */
	public String getName();
	
	/**
	 * Get the the sprite path of the item
	 * @return the path of the sprite
	 */
	public String getSprite();
	
	/**
	 * Take the input of a CSV File and convert it to a list of Items
	 * @param data - The Map generated by the CSV Reader
	 * @return
	 */
	public static ArrayList<Item> fromMap(Map<String, List<String>> data){
		ArrayList<Item> possibleItems = new ArrayList<>();
		return possibleItems;
	}
}
