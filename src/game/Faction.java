package game;

import java.util.ArrayList;

import auxil.Color;
import entity.BaseEntity;
import level.Tile;

public class Faction {

	public ArrayList<BaseEntity> units = new ArrayList<BaseEntity>();
	//public ArrayList<Tile> land = new ArrayList<Tile>();
	
	public String name;
	public Color color;
	
	public Faction(String name)
	{
		this.name = name;
		/*r = (float)(Math.random()*255f);
		g = (float)(Math.random()*255f);
		b = (float)(Math.random()*255f);*/
		color = new Color((float)(Math.random()*255f),(float)(Math.random()*255f),(float)(Math.random()*255f));
	}
	
	public boolean equals(Faction other)
	{
		return color.r == other.color.r && color.g == other.color.g && color.b == other.color.b;
	}
	
}
