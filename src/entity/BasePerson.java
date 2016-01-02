package entity;

import java.util.ArrayList;

import game.Faction;
import level.Tile;

public abstract class BasePerson extends BaseEntity {
	
	public Faction owner;
	public ArrayList<String> queueAction = new ArrayList<String>();
	public double action = 2, maxAction = 2;
	public ArrayList<Tile> queueTiles = new ArrayList<Tile>();
	
	public BasePerson(String name, Faction civ) {
		super(name);
		// TODO Auto-generated constructor stub
		owner = civ;
		civ.units.add(this);
	}
	
}
