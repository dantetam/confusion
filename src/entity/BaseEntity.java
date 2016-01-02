package entity;

import java.util.ArrayList;

import level.Intelligence;
import level.Tile;

public abstract class BaseEntity {

	public Tile location = null;
	public float offsetX, offsetY; //measured in units of self (1.0 -> full length of self)
	
	public String name;

	public BaseEntity(String name)
	{
		this.name = name;
		offsetX = 0; offsetY = 0;
	}

	public void tick()
	{
		
	}

}
