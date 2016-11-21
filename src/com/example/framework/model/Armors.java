package com.example.framework.model;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.utils.ArrayMap;

public class Armors {	
	
	Armor  armorLeft;
	Armor  armorRight;
	DynamicGameObject master;
	
	Armors(DynamicGameObject dynObj)
	{
		armorLeft = null;
		armorRight = null;
		master = dynObj;
	}
	
	public Armor addArmor(int type, int level, boolean isLeft)
	{
		Armor armor = new Armor(type, level, master, isLeft);
		
		if(isLeft && armorLeft == null)
			armorLeft = armor;
		else if(!isLeft && armorRight == null)
			armorRight = armor;
		
		return armor;
	}
	
	public void removeArmor(boolean isLeft)
	{		
		if(isLeft)
			armorLeft = null;
		else
			armorRight = null;

	}
	
	public void init()
	{				
		if(armorLeft != null)
			armorLeft.createArmorFixture();
		
		if(armorRight != null)
			armorRight.createArmorFixture();
	}
	
	
	public void update()
	{
		if(armorLeft != null)
		{
			armorLeft.stateHS.health -= armorLeft.stateHS.healthDecrease;
			armorLeft.stateHS.healthDecrease = 0;
			
			if(armorLeft.stateHS.health < 0)
				armorLeft = null;
		}
		
		if(armorRight != null)
		{
			armorRight.stateHS.health -= armorRight.stateHS.healthDecrease;
			armorRight.stateHS.healthDecrease = 0;
			
			if(armorRight.stateHS.health < 0)
				armorRight = null;	
		}
		
	}
	
	public Armor getArmorByFixtureIndex(int index)
	{		
		if(index >= Statics.PhysicsBox2D.SNAKE_WOOD_ARMOR_RIGHT_FIXTURE_START_INDEX)
			 return armorRight;
		else 
			return armorLeft;
	}
	
	public boolean haveArmor(boolean isLeft)
	{		
		if(isLeft && armorLeft != null)
			return true;
		else if(!isLeft && armorRight != null)
			return true;
		else 
			return false;
	}
	
	public int getArmorType(boolean isLeft)
	{		
		if(isLeft && armorLeft != null)
			return armorLeft.type;
		else if(!isLeft && armorRight != null)
			return armorRight.type;
		else 
			return 0;
	}
	
	public Armor getArmor(boolean isLeft)
	{		
		if(isLeft && armorLeft != null)
			return armorLeft;
		else if(!isLeft && armorRight != null)
			return armorRight;
		else 
			return null;
	}
	
}
