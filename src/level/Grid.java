package level;

import java.util.ArrayList;

import entity.BaseEntity;
import entity.BasePerson;
import game.Faction;
import game.Pathfinder;
import models.LevelManager;

public class Grid {

	protected Tile[][] tiles;
	public Faction[] facs;
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
		pathfinder = new Pathfinder(this);

		facs = new Faction[numCivs];
		for (int i = 0; i < numCivs; i++)
		{
			Faction fac = null;
			if (i == 0)
			{
				fac = new Faction("Colony");
				for (int j = 0; j < 5; j++)
				{
					//Spawn new units
				}
			}
			else
			{

			}
			facs[i] = fac;
		}
	}

	public void sync(LevelManager lm)
	{
		levelManager = lm;
	}

	public void move(BaseEntity en, int r, int c)
	{
		if (en.location != null)
		{
			en.location.units.remove(en);
			if (en.location.item.equals(en))
				en.location.item = null;
		}
		Tile t = getTile(r,c);
		en.location = t; //Could possibly be null
		if (t != null)
		{
			t.units.add(en);
		}
		en.offsetX = 0; en.offsetY = 0;
		levelManager.moveEntity(en, r, c);
	}

	public void remove(BaseEntity en)
	{
		if (en.location != null)
		{
			en.location.units.remove(en);
			if (en.location.item.equals(en))
				en.location.item = null;
		}
		en.location = null;
		levelManager.removeEntity(en);
	}

	public ArrayList<Tile> findPath(BasePerson unit, int a, int b, int c, int d)
	{
		return pathfinder.findPath(a, b, c, d, true);
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
