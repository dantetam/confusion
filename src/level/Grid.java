package level;

import java.util.ArrayList;

import entity.BaseEntity;
import entity.BasePerson;
import game.Civilization;
import game.Pathfinder;
import models.LevelManager;

public class Grid {

	protected Tile[][] tiles;
	public Civilization[] civs;
	protected Pathfinder pathfinder;
	public Intelligence intelligence;

	public LevelManager levelManager;
	
	public Grid(int rows, int cols, int numCivs)
	{
		tiles = new Tile[rows][cols];
		this.rows = rows;
		this.cols = cols;
		for (int r = 0; r < rows; r++)
		{
			for (int c = 0; c < cols; c++)
			{
				Tile tile = new Tile(this,r,c);
				tile.start((int)(Math.random()*6), (int)(Math.random()*6));
				tile.improve(0, 0);
				tiles[r][c] = tile;
			}
		}
		colorTilesAverage();

		pathfinder = new Pathfinder(this);
		
		civs = new Civilization[numCivs];
		for (int i = 0; i < numCivs; i++)
		{
			Civilization civ = new Civilization();
			civs[i] = civ;
			for (int j = 0; j < 1; j++)
			{
				BaseEntity en = new BaseEntity(civ, "Settler");
				Tile t = null;
				do
				{
					t = getTile((int)(Math.random()*tiles.length), (int)(Math.random()*tiles[0].length));
				} while (t.units.size() != 0);
				move(en, t.row, t.col);
			}
		}
	}
	
	public void sync(LevelManager lm)
	{
		levelManager = lm;
	}

	public void move(BasePerson en, int r, int c)
	{
		if (en.location != null)
			en.location.units.remove(en);
		Tile t = getTile(r,c);
		en.location = t; //Could possibly be null
		if (t != null)
		{
			t.units.add(en);
		}
		en.offsetX = 0; en.offsetY = 0;
		levelManager.moveEntity(en, r, c);
	}
	
	public void remove(BasePerson en)
	{
		en.location.units.remove(en);
		en.location = null;
	}
	
	public ArrayList<Tile> findPath(BasePerson unit, int a, int b, int c, int d)
	{
		return pathfinder.findPath(unit.owner, a, b, c, d, true);
	}

	public Tile getTile(int r, int c)
	{
		if (r >= 0 && r < tiles.length && c >= 0 && c < tiles[0].length)
			return tiles[r][c];
		return null;
	}
	public int rows;
	public int cols;

	/*private void colorTilesAverage()
	{
		float[][] newShades = new float[rows][cols];
		for (int r = 0; r < rows; r++)
		{
			for (int c = 0; c < cols; c++)
			{
				float n = 0; int sight = 1;
				for (int rr = r - sight; rr <= r + sight; rr++)
				{
					for (int cc = c - sight; cc <= c + sight; cc++)
					{
						if (getTile(rr,cc) == null) continue;
						newShades[r][c] += tiles[rr][cc].color.r;
						n++;
					}
				}
				newShades[r][c] /= n;
			}
		}
		for (int r = 0; r < rows; r++)
		{
			for (int c = 0; c < cols; c++)
			{
				tiles[r][c].color.set(newShades[r][c]);
			}
		}
	}*/

}
