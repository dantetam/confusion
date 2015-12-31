package entity;

import java.util.ArrayList;

import game.Civilization;
import level.Tile;

public class Person extends BaseEntity {

	public Civilization owner;
	public ArrayList<String> queueAction = new ArrayList<String>();
	public double action = 2, maxAction = 2;
	public ArrayList<Tile> queueTiles = new ArrayList<Tile>();
	
}
