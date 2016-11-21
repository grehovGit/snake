package com.example.framework.model;

import com.badlogic.gdx.math.Vector2;

public class Armor extends DynamicGameObject{
	
	DynamicGameObject master;	
	public boolean isLeft;
	
	Armor(int type, int level, DynamicGameObject master, boolean isLeft)
	{	
		super(0, 0, Statics.Render.getObjectRenderSizes(type).x, Statics.Render.getObjectRenderSizes(type).y, type, level);
		this.master = master;
		this.isLeft = isLeft;
		this.isPartOfBody = true;
	}
	
	public void createArmorFixture()
	{
		switch(type)
		{
			case Statics.DynamicGameObject.SNAKE_WOOD_ARMOR:
				master.world.world2d.createArmorFixture(type, isLeft, master, this);	
		}
	}
}
