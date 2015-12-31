package level;

import java.util.ArrayList;

import entity.BaseEntity;
import game.Civilization;
import game.Pathfinder;

public class Grid {

	protected Tile[][] tiles;
	public Civilization[] civs;
	protected Pathfinder pathfinder;
	public Intelligence intelligence;

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

	public void move(BaseEntity en, int r, int c)
	{
		if (en.location != null)
			en.location.units.remove(en);
		Tile t = getTile(r,c);
		en.location = t; //Could possibly be null
		if (t != null)
		{
			t.units.add(en);
		}
	}
	
	public ArrayList<Tile> findPath(BaseEntity unit, int a, int b, int c, int d)
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

	public Tile[] settlerSpots(Civilization civ, Tile t, double dist, int resultLength)
	{
		ArrayList<Object[]> best = new ArrayList<Object[]>(); //Make a 'tuple' of tile + its score
		int[][] scores = returnCityScores(civ);
		for (int r = 0; r < rows; r++)
		{
			if (Math.abs(t.row - r) > dist) //Process some tiles out of the way. What was the word...4 letters...curl? whip?...
				continue;
			for (int c = 0; c < cols; c++)
			{
				if (Math.abs(t.col - c) > dist) //This is so that if dist is a large number, we won't process a huge amount of nonexistent tiles
					continue; //At most and least, process the entire grid
				Tile candidate = getTile(r,c); double candidateDist = candidate.dist(t);
				if (candidateDist <= dist)
				{
					int score = scores[r][c];
					for (int i = 0; i < resultLength; i++)
					{
						if (best.size() <= i || (int)best.get(i)[1] < score) //Insert into the list if not of size or better score
						{
							best.add(i,new Object[]{candidate,score});
							if (best.size() > resultLength) //Remove last element when list is sufficient length
								best.remove(resultLength);
							break;
						}
					}
				}
			}
		}
		Tile[] tiles = new Tile[resultLength];
		for (int i = 0; i < resultLength; i++)
		{
			if (i >= best.size())
			{
				for (int j = 0; j < resultLength - i; j++)
					best.add(new Object[]{null,-1});
				break;
			}
			tiles[i] = (Tile)best.get(i)[0];
		}
		return tiles;
	}
	//Returns data for the scores of all possible 5x5 city areas, ignoring foreign tiles owned by others
	private int[][] returnCityScores(Civilization civ) 
	{
		int[][] temp = new int[rows][cols];
		int[][] tileScores = new int[rows][cols];
		for (int r = 0; r < rows; r++)
			for (int c = 0; c < cols; c++)
				tileScores[r][c] = evalTile(civ,r,c);
		//I feel like I've written this code before
		for (int r = 0; r < rows; r++)
			for (int c = 0; c < cols; c++)
				for (int rr = r - 2; rr <= r + 2; rr++)
					for (int cc = c - 2; cc <= c + 2; cc++)
						if (getTile(rr,cc) != null)
							temp[r][c] += tileScores[rr][cc];
		return temp;
	}
	public int evalTile(Civilization civ, int r, int c)
	{
		Tile t = getTile(r,c);
		if (t.owner == null || t.owner.equals(civ))
			return t.food + t.foodImpr + t.metal + t.metalImpr;
		return 0;
	}
	
	private void colorTilesAverage()
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
	}

}
