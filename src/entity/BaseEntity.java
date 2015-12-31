package entity;

import java.util.ArrayList;

import game.Civilization;
import level.Intelligence;
import level.Tile;

public class BaseEntity {

	public Tile location = null;
	public float offsetX, offsetY;
	
	public String name;

	public BaseEntity(Civilization civ, String name)
	{
		owner = civ;
		civ.units.add(this);
		this.name = name;
	}

	public void tick()
	{
		
	}

}
