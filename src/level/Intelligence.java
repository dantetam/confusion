package level;

import java.util.ArrayList;

import entity.BaseEntity;

public class Intelligence {
	
	//Measure permanent gain over number of turns. Almost like a E/P ratio instead of a P/E. A ROI?
	public double[] scoreFromSettlerPerTurn(BaseEntity en, Tile t) 
	{
		double score = 0;
		for (int rr = t.row - 2; rr <= t.row + 2; rr++)
			for (int cc = t.col - 2; cc <= t.col + 2; cc++)
				if (en.location.grid.getTile(rr,cc) != null)
					score += en.location.grid.evalTile(en.owner, rr, cc);
		return new double[]{score,turnsEntityToTile(en,t)};
	}

	public double turnsEntityToTile(BaseEntity en, Tile t)
	{
		ArrayList<Tile> path = en.location.grid.findPath(en, en.location.row, en.location.col, t.row, t.col);
		return Math.ceil((double)path.size()/(double)en.maxAction);
	}

	public double scoreFromWorkerPerTurn(BaseEntity en, int turns)
	{
		ArrayList<Tile> queueToImpr = new ArrayList<Tile>();
		int score = 0;
		while (true)
		{
			if (turns <= 0) break;
			int turnsBest = -1; double gainPerTurn = 0;
			for (int rr = en.location.row - 2; rr <= en.location.row + 2; rr++)
				for (int cc = en.location.col - 2; cc <= en.location.col + 2; cc++)
				{
					Tile t = en.location.grid.getTile(rr,cc);
					if (t.foodImpr > 0 || t.metalImpr > 0 || queueToImpr.contains(t)) continue; //Skip if improved or about to be improved
					if (t != null)
					{
						int turnsCandidate = (int)turnsEntityToTile(en, t);
						int[] impr = Tile.getImprovement(t.biome, t.resource);
						double gainPerTurnCandidate = (impr[0]+impr[1])/turnsCandidate;
						if (gainPerTurnCandidate > gainPerTurn || turnsBest == -1)
						{
							gainPerTurn = gainPerTurnCandidate;
							turnsBest = turnsCandidate;
						}
					}
				}
			turns -= turnsBest;
			score += gainPerTurn;
		}
		return score; //Is already divided by turns
	}

}
