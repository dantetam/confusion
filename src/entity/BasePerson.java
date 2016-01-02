package entity;

import java.util.ArrayList;

import game.Civilization;
import level.Tile;

public abstract class BasePerson extends BaseEntity {
	
	public Civilization owner;
	public ArrayList<String> queueAction = new ArrayList<String>();
	public double action = 2, maxAction = 2;
	public ArrayList<Tile> queueTiles = new ArrayList<Tile>();
	
	public BasePerson(String name, Civilization civ) {
		super(name);
		// TODO Auto-generated constructor stub
		owner = civ;
		civ.units.add(this);
	}
	
}
