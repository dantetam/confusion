package level;

import java.util.ArrayList;

import auxil.Color;
import entity.BaseEntity;
import game.Civilization;

public class Tile {

	public Grid grid;
	public Civilization owner;
	public int row, col;

	public int biome, resource;
	public int food, metal;
	public int foodImpr, metalImpr;

	public Color color;

	public ArrayList<BaseEntity> units = new ArrayList<BaseEntity>();

	public Tile(Grid g, int r, int c)
	{
		grid = g;
		biome = (int)(Math.random()*7);
		row = r;
		col = c;
		float shade = (float)(Math.random()*255f);
		color = new Color(shade,shade,shade);
	}

	public void start(int f, int m)
	{
		food = f;
		metal = m;
	}

	public void improve(int f, int m)
	{
		foodImpr = f;
		metalImpr = m;
	}

	public void improveBiome() //Possibly convert biome? Change/upgrade/degrade biome numbers?
	{
		int[] impr = getImprovement(biome, resource);
		foodImpr = impr[0]; metalImpr = impr[1];
	}
	public static int[] getImprovement(int biome, int resource)
	{
		int f = 0, m = 0;
		if (resource > 0)
		{
			if (resource == 1) {f = 3;}
			else if (resource == 2) {f = 2; m = 1;}
			else if (resource == 3) {f = 1; m = 2;}
			else if (resource == 4) {m = 3;}
		}
		else
		{
			if (biome >= 0 || biome <= 2) {m = 2;}
			else if (biome >= 4 || biome <= 6) {f = 2;}
			else
			{
				f = 1;
				m = 1;
			}
		}
		return new int[]{f,m};
	}

	public double dist(Tile t)
	{
		return Math.sqrt(Math.pow(t.row - row, 2) + Math.pow(t.col - col, 2));
	}

}
