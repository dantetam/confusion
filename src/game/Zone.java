package game;

import java.util.ArrayList;

import level.Grid;
import level.Tile;

public class Zone {

	public Grid grid;
	public boolean[][] tilesBool; //Mark true for all tiles in zone
	public ArrayList<Tile> tiles;
	
	public int id;
	
	public Zone(Grid grid)
	{
		this.grid = grid;
		tiles = new ArrayList<Tile>();
	}

	//(a,b) & (x,y) representing different points
	public void add(int a, int b, int x, int y)
	{
		for (int r = a; r <= x; r++)
			for (int c = b; c <= y; c++)
				tilesBool[r][c] = true;
	}
	public void remove(int a, int b, int x, int y)
	{
		for (int r = a; r <= x; r++)
			for (int c = b; c <= y; c++)
				tilesBool[r][c] = false;
	}
	
	public void updateTiles()
	{
		tiles.clear();
		if (tilesBool.length == 0 || tilesBool[0].length == 0) return;
		for (int r = 0; r < tilesBool.length; r++)
		{
			for (int c = 0; c < tilesBool[0].length; c++)
			{
				tiles.add(grid.getTile(r, c));
			}
		}
	}


}
