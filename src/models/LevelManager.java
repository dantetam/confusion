package models;

import java.util.ArrayList;

import entity.BaseEntity;

public class LevelManager {

	public ArrayList<EntityModelLink> links = new ArrayList<EntityModelLink>();
	
	public void addEntity()
	{
		
	}
	
	public void moveEntity(BaseEntity en, int r, int c)
	{
		
	}
	
	public EntityModelLink removeEntity(BaseEntity en)
	{
		for (EntityModelLink link: links)
		{
			if (link.entity.equals(en))
			{
				links.remove(link); 
				return link;
			}
		}
		return null; //Could not find element. Handle accordingly.
	}
	
	public class EntityModelLink
	{
		public BaseEntity entity;
		public ArrayList<TexturedModel> models;
		
		public EntityModelLink(BaseEntity en)
		{
			entity = en;
			models = new ArrayList<TexturedModel>();
		}
	}
	
}
