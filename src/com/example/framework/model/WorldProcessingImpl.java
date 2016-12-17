package com.example.framework.model;



import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.kingsnake.gl.ParticlesEffectsManager;
import com.kingsnake.math.OverlapTester;
import com.kingsnake.math.Rectangle;
import com.kingsnake.math.Vector2;
import com.kingsnake.physicsBox2d.PhysicsBox2d;

public class WorldProcessingImpl implements WorldProcessing{
	
 	public static final int WORLD_WIDTH = Statics.WKS.WORLD_WIDTH;
    public static final int WORLD_HEIGHT = Statics.WKS.WORLD_HEIGHT;
    public static final int CELL_DIM = Statics.WKS.CELL_DIM;
    public static final float CELL_SIZE = Statics.WKS.CELL_SIZE;
 	public static final int WORLD_LWIDTH = WORLD_WIDTH * CELL_DIM;
    public static final int WORLD_LHEIGHT = WORLD_HEIGHT * CELL_DIM;

	public static final int MOVE_LEFT = 1;
	public static final int MOVE_RIGHT = 2;
	public static final int MOVE_UP = 3;
	public static final int MOVE_DOWN = 4;

	WorldKingSnake world;
	
	int nCoords;
	int[] coords;
	
	int nLogCoords;
	
	Vector2 tVector1, tVector2, tVector3;
	Rectangle rect;
	
	
	WorldProcessingImpl(WorldKingSnake world)
	{
		this.world = world;
		coords = new int[10];
		nLogCoords = WorldKingSnake.CELL_DIM * WorldKingSnake.CELL_DIM * 2;
		tVector1 = new Vector2();
		tVector2 = new Vector2();
		tVector3 = new Vector2();
		rect = new Rectangle(0, 0, 0, 0);
		
	}
	
	public boolean placeObjectToCell(int x, int y, int z, int type)
	{
    	if(x >= WorldKingSnake.WORLD_WIDTH || x < 0 || y >= WorldKingSnake.WORLD_HEIGHT || y < 0 || z < 0 || z >= WorldKingSnake.WORLD_DEPTH)
    		return false;
    	
    	for(int i=0; i < WorldKingSnake.CELL_DIM; i++)
    		for(int j = 0; j < WorldKingSnake.CELL_DIM; j++)
    			world.fields[z][x * WorldKingSnake.CELL_DIM + i][y * WorldKingSnake.CELL_DIM + j] = type;
    	
    	return true;
	};
	
	public boolean deleteCellContent(int x, int y, int z)
	{
    	if(x >= WorldKingSnake.WORLD_WIDTH || x < 0 || y >= WorldKingSnake.WORLD_HEIGHT || y < 0 || z < 0 || z >= WorldKingSnake.WORLD_DEPTH)
    		return false;
    	
    	for(int i=0; i < WorldKingSnake.CELL_DIM; i++)
    		for(int j = 0; j < WorldKingSnake.CELL_DIM; j++)
    			world.fields[z][x * WorldKingSnake.CELL_DIM + i][y * WorldKingSnake.CELL_DIM + j] = 0;
    	
    	return true;
	};
	
	public boolean deleteSubObjectFromGreedByCoords(float _x, float _y, int z)
	{
		int x = (int)(_x * WorldKingSnake.CELL_DIM);
		int y = (int)(_y * WorldKingSnake.CELL_DIM);
		
    	if(x >= WorldKingSnake.WORLD_WIDTH * WorldKingSnake.CELL_DIM|| x < 0 || y >= WorldKingSnake.WORLD_HEIGHT * WorldKingSnake.CELL_DIM|| y < 0 || z < 0 || z >= WorldKingSnake.WORLD_DEPTH)
    		return false;	       	
    	
    	world.fields[z][x][y] = 0;	
    	return true;
	};
	
	public boolean deleteObjectFromGreedByCoords(float _x, float _y, int z, int type)
	{
		if(type == DynamicGameObject.TREE_BOMB)
		{
			return deleteSubObjectFromGreedByCoords(_x, _y, WorldKingSnake.WORLD_DYNAMIC_OBJECTS_LEVEL);
		}
		else
		{
			int x = (int)(_x * WorldKingSnake.CELL_DIM);
			int y = (int)(_y * WorldKingSnake.CELL_DIM);
			
				
		    if(x >= WorldKingSnake.WORLD_WIDTH * WorldKingSnake.CELL_DIM|| x < 0 || y >= WorldKingSnake.WORLD_HEIGHT * WorldKingSnake.CELL_DIM || y < 0)
		    	return false; 			
		    
			int wWidth =  WorldKingSnake.WORLD_WIDTH * WorldKingSnake.CELL_DIM;
			int wHeight = WorldKingSnake.WORLD_HEIGHT * WorldKingSnake.CELL_DIM;
		    
		    for(int i = -WorldKingSnake.CELL_DIM/2; i <= WorldKingSnake.CELL_DIM/2; i++)
		    {
		    	for(int j = -WorldKingSnake.CELL_DIM/2; j <= WorldKingSnake.CELL_DIM/2; j++)
		    	{
		    		if(x + i >= 0 && x + i < wWidth && y + j >= 0 && y + j < wHeight)
		    			if(world.fields[z][x+i][y+j] == type) 
		    				world.fields[z][x+i][y+j] = 0;	    			
		    	}
		    }
		}
		
		return true;  
	};
	
	//use generally for dynamic objects
	void deleteObjectFromGreedByMatrix(int[] logCoords, int z)
	{
		int size =  WorldKingSnake.CELL_DIM * WorldKingSnake.CELL_DIM * 2;
		
		for(int i=0; i<size; i+=2)
			if(logCoords[i] != -1)
				world.fields[z][logCoords[i]][logCoords[i+1]] = 0;
					
	};
	
	
	public boolean isEmptyCell(int x, int y, int z)
	{
    	if(x >= WorldKingSnake.WORLD_WIDTH || x < 0 || y >= WorldKingSnake.WORLD_HEIGHT || y < 0 || z < 0 || z >= WorldKingSnake.WORLD_DEPTH)
    		return false;	    
    	
    	for(int i=0; i < WorldKingSnake.CELL_DIM; i++)
    		for(int j = 0; j < WorldKingSnake.CELL_DIM; j++)
    			if(world.fields[z][x * WorldKingSnake.CELL_DIM + i][y * WorldKingSnake.CELL_DIM + j] != 0)
    				return false;
    	
    	return true;
	};
	
	public boolean isEmptyCellByCoords(float _x, float _y, int z)
	{
		int x = (int)(_x);
		int y = (int)(_y);
		       	
    	
    	if(x >= WorldKingSnake.WORLD_WIDTH || x < 0 || y >= WorldKingSnake.WORLD_HEIGHT || y < 0 || z < 0 || z >= WorldKingSnake.WORLD_DEPTH)
    		return false;	    
    	
    	for(int i=0; i < WorldKingSnake.CELL_DIM; i++)
    		for(int j = 0; j < WorldKingSnake.CELL_DIM; j++)
    			if(world.fields[z][x * WorldKingSnake.CELL_DIM + i][y * WorldKingSnake.CELL_DIM + j] != 0)
    				return false;
    	
    	return true;
	};
	
	public boolean isEmptyNeighbourCellByCoords(float _x, float _y, int z, int dir)
	{
		int x = (int)(_x);
		int y = (int)(_y);
		
		switch(dir)
		{
		case MOVE_LEFT:
			--x;
			break;
		case MOVE_RIGHT:
			++x;
			break;
		case MOVE_UP:
			++y;
			break;
		case MOVE_DOWN:
			--y;
			break;
		}
		       	
    	if(x >= WorldKingSnake.WORLD_WIDTH) x = 0;
    	else if( x < 0) x = WorldKingSnake.WORLD_WIDTH - 1;
    	
    	if(y >= WorldKingSnake.WORLD_HEIGHT) y = 0;
    	else if( y < 0) y = WorldKingSnake.WORLD_HEIGHT - 1;
    	
    	if(z < 0 || z >= WorldKingSnake.WORLD_DEPTH)
    		return false;
    	
    	return isEmptyCell(x, y, z);
	}
	
	public int getSubCellByCoords(float _x, float _y, int z)
	{
		int x = (int)(_x * WorldKingSnake.CELL_DIM);
		int y = (int)(_y * WorldKingSnake.CELL_DIM);
		
    	if(x >= WorldKingSnake.WORLD_WIDTH * WorldKingSnake.CELL_DIM|| x < 0 || y >= WorldKingSnake.WORLD_HEIGHT * WorldKingSnake.CELL_DIM|| y < 0 || z < 0 || z >= WorldKingSnake.WORLD_DEPTH)
    		return -1;	       	
    	
    	return world.fields[z][x][y];
	};
	
	public boolean placeSubObjectToGreedByCoords(float _x, float _y, int z, int type)
	{
		int x = (int)(_x * WorldKingSnake.CELL_DIM);
		int y = (int)(_y * WorldKingSnake.CELL_DIM);
		
    	if(x >= WorldKingSnake.WORLD_WIDTH * WorldKingSnake.CELL_DIM|| x < 0 || y >= WorldKingSnake.WORLD_HEIGHT * WorldKingSnake.CELL_DIM|| y < 0 || z < 0 || z >= WorldKingSnake.WORLD_DEPTH)
    		return false;	
    	
    	world.fields[z][x][y] = type;
    	
		return true;
	}
	
	public boolean placeObjectToGreedByCoords(float _x, float _y, int z, int type ,int[] logCoords)
	{
		int x = (int)(_x * WorldKingSnake.CELL_DIM);
		int y = (int)(_y * WorldKingSnake.CELL_DIM);
		
	    if(x >= WorldKingSnake.WORLD_WIDTH * WorldKingSnake.CELL_DIM || x < 0 || y >= WorldKingSnake.WORLD_HEIGHT * WorldKingSnake.CELL_DIM || y < 0
	    		|| z < 0 || z >= WorldKingSnake.WORLD_DEPTH)
	    	return false; 			
	    
		int wWidth =  WorldKingSnake.WORLD_WIDTH * WorldKingSnake.CELL_DIM;
		int wHeight = WorldKingSnake.WORLD_HEIGHT * WorldKingSnake.CELL_DIM;
		int index = 0;
	    
	    for(int i = -WorldKingSnake.CELL_DIM/2; i <= WorldKingSnake.CELL_DIM/2; i++)
	    {
	    	for(int j = -WorldKingSnake.CELL_DIM/2; j <= WorldKingSnake.CELL_DIM/2; j++)
	    	{	    		
	    		if(x + i >= 0 && x + i < wWidth && y + j >= 0 && y + j < wHeight)
	    		{
	    			world.fields[z][x+i][y+j] = type;
	    			
	    			if(index % 2 == 0)
	    				logCoords[index] = x + i;
	    			else
	    				logCoords[index] = y + j;
	    			
	    		}
	    		else
	    			logCoords[index] = -1;
	    		
	    		++index;
	    	}
	    }
		return true;
	}
	
	public boolean moveObjectToGreedByCoords(float _x, float _y, int z, int type ,int[] logCoords)
	{
		int wWidth =  WorldKingSnake.WORLD_WIDTH * WorldKingSnake.CELL_DIM;
		int wHeight = WorldKingSnake.WORLD_HEIGHT * WorldKingSnake.CELL_DIM;
		
		int x = (int)(_x * WorldKingSnake.CELL_DIM);
		int y = (int)(_y * WorldKingSnake.CELL_DIM);
		
		if(x >= wWidth) x = 0;
		else if(x < 0 ) x = wWidth - 1;
		if(y >= wHeight) y = 0;
		else if(y < 0 ) y = wHeight - 1;
		
	    if(z < 0 || z >= WorldKingSnake.WORLD_DEPTH || logCoords == null)
	    	return false; 			
	    
		int index = 0;
		
		//delete old position
		for(int i=0; i< nLogCoords; i+=2)
			if(logCoords[i] != -1 && logCoords[i + 1] != -1)
				world.fields[z][logCoords[i]][logCoords[i+1]] = 0;
	    
		//put new logical position
	    for(int i = -WorldKingSnake.CELL_DIM/2; i <= WorldKingSnake.CELL_DIM/2; i++)
	    {
	    	for(int j = -WorldKingSnake.CELL_DIM/2; j <= WorldKingSnake.CELL_DIM/2; j++)
	    	{	    		
	    		if(x + i >= 0 && x + i < wWidth && y + j >= 0 && y + j < wHeight)
	    		{
	    			world.fields[z][x+i][y+j] = type;	    			
	    			
	    				logCoords[index*2] = x + i;
	    				logCoords[index*2 + 1] = y + j;
	    			
	    		}
	    		else
	    		{
	    			logCoords[index*2] = -1;
	    			logCoords[index*2+1] = -1;	    			
	    		}
	    		
	    		++index;
	    	}
	    }
		return true;
	}
	
	
	public void placeRelief(){
		
		FileHandle fHandle = Gdx.files.internal("map1.txt");
		String strMap = fHandle.readString();
		
		int str =  WorldKingSnake.WORLD_HEIGHT - 1, col = 0;
		int len = strMap.length();
		StringBuffer word = new StringBuffer();
		int relief = 0;
		
		
		for(int i = 0; i < len; ++i)
		{
			if(strMap.charAt(i) == 9)	//TAB
			{
				String strData = word.toString();
				relief = Integer.parseInt(strData);
				
				if(relief > WorldKingSnake.GRASS)
				{
					world.relief.add(new StaticEffect(col, str, 1, 1, relief, 0, world));
					placeObjectToCell(col, str, WorldKingSnake.WORLD_RELIEF_LEVEL, relief);
				}
				word.setLength(0);
				++col;
			}
			else if(strMap.charAt(i) == 10)	//Enter
			{
				String strData = word.toString();
				relief = Integer.parseInt(strData);
				
				if(relief > WorldKingSnake.GRASS)
				{
					world.relief.add(new StaticEffect(col, str, 1, 1, relief, 0, world));
					placeObjectToCell(col, str, WorldKingSnake.WORLD_RELIEF_LEVEL, relief);
				}
				word.setLength(0);	
				col = 0;
				--str;	
			}
			else if(strMap.charAt(i) > 47 && strMap.charAt(i) < 58)	
				word.append(strMap.charAt(i));

		}
	};
	
	public void CharToCharAttack(DynamicGameObject dynObj, DynamicGameObject dObj)
	{
		if(dynObj.objType == Statics.DynamicGameObject.SNAKE)
			dynObj = ((SnakePart)dynObj).snake;
		
		float attackPower = (dynObj.stateHS.isStriking && dynObj.stateHS.strikeType == Statics.Render.STRIKE_JUMP) ? 
				dynObj.stateHS.attackPower * dynObj.stateHS.strikePower : dynObj.stateHS.attackPower;
		
		float defPower =  (dynObj.stateHS.isStriking && dynObj.stateHS.strikeType == Statics.Render.STRIKE_JUMP) ? 
				dObj.stateHS.defencePower * dObj.stateHS.strikePower : dObj.stateHS.defencePower;
		
		dObj.stateHS.healthDecrease += attackPower * attackPower / 	(2 * defPower);
		
		//if we've killed char then get coins and provide swallow effect
		if(dObj.stateHS.healthDecrease >= dObj.stateHS.health)
		{
			dynObj.stateHS.coinsIncrease += dObj.stateHS.cost;
			dynObj.stateHS.isSwallow = true;
			dynObj.stateHS.swallowStartTime = world.actTime;
		}
	}
	
	public void provideMushroomAction(DynamicGameObject dynObj, int mushroom, int action)
	{
		if(mushroom == Gifts.MUSHROOM_BLUE)
		{
			if(action == Gifts.MUSHROOM_BLUE_ATTACK)
				dynObj.stateHS.attackPowerIncrease += Gifts.MUSHROOM_BLUE_ATTACK_AMOUNT * dynObj.stateHS.attackPower;
			else if(action == Gifts.MUSHROOM_BLUE_DEFENSE)
				dynObj.stateHS.defencePowerIncrease += Gifts.MUSHROOM_BLUE_DEFENSE_AMOUNT * dynObj.stateHS.defencePower;
			else if(action == Gifts.MUSHROOM_BLUE_SPEED)
				dynObj.stateHS.velocityIncrease += Gifts.MUSHROOM_BLUE_SPEED_AMOUNT * dynObj.stateHS.velocity;
			else if(action == Gifts.MUSHROOM_BLUE_HEALTH)
				dynObj.stateHS.healthIncrease += Gifts.MUSHROOM_BLUE_HEALTH_AMOUNT * dynObj.stateHS.defencePower;
			else if(action == Gifts.MUSHROOM_BLUE_COINS)
				dynObj.stateHS.coinsIncrease += Gifts.MUSHROOM_BLUE_COINS_AMOUNT;
		}
		else if(mushroom == Gifts.MUSHROOM_BROWN)
		{
			if(action == Gifts.MUSHROOM_BROWN_ATTACK)
				dynObj.stateHS.attackPowerIncrease -= Gifts.MUSHROOM_BROWN_ATTACK_AMOUNT * dynObj.stateHS.attackPower;
			else if(action == Gifts.MUSHROOM_BROWN_DEFENSE)
				dynObj.stateHS.defencePowerIncrease -= Gifts.MUSHROOM_BROWN_DEFENSE_AMOUNT * dynObj.stateHS.defencePower;
			else if(action == Gifts.MUSHROOM_BROWN_SPEED)
				dynObj.stateHS.velocityIncrease -= Gifts.MUSHROOM_BROWN_SPEED_AMOUNT * dynObj.stateHS.velocity;
			else if(action == Gifts.MUSHROOM_BROWN_HEALTH)
				dynObj.stateHS.healthIncrease -= Gifts.MUSHROOM_BROWN_HEALTH_AMOUNT * dynObj.stateHS.defencePower;
			else if(action == Gifts.MUSHROOM_BROWN_COINS)
				dynObj.stateHS.coinsIncrease -= Gifts.MUSHROOM_BROWN_COINS_AMOUNT;			
		}
	}
	
	public boolean dynObjGrounding(DynamicGameObject dynObj, Vector2 newPoint)
	{
        
        dynObj.stateHS.healthDecrease = 0;
        
        //check stop relief
        int stop = 0;
        int x = ((int) newPoint.x) * WorldKingSnake.CELL_DIM;
        int y = ((int) newPoint.y) * WorldKingSnake.CELL_DIM;
        
        //more damaging relief overwrites before       	
        if(world.fields[WorldKingSnake.WORLD_RELIEF_LEVEL][x][y] >= WorldKingSnake.WALL &&
        world.fields[WorldKingSnake.WORLD_RELIEF_LEVEL][x][y] < WorldKingSnake.SAND &&
        world.fields[WorldKingSnake.WORLD_RELIEF_LEVEL][x][y] > stop)
        	stop = world.fields[WorldKingSnake.WORLD_RELIEF_LEVEL][x][y];
        
        switch(stop)
        {
        case WorldKingSnake.WALL:
			dynObj.stateHS.isHeated = true;
			dynObj.stateHS.hitPeriodFull = dynObj.stateHS.hitPeriod = WorldKingSnake.WALL_DELAY;
			break;
        case WorldKingSnake.GREEN_TREE:
			dynObj.stateHS.isHeated = true;
			dynObj.stateHS.hitPeriodFull = dynObj.stateHS.hitPeriod = WorldKingSnake.GREEN_TREE_DELAY;							
			break;			
        case WorldKingSnake.YELLOW_TREE:
			dynObj.stateHS.isHeated = true;
			dynObj.stateHS.hitPeriodFull = dynObj.stateHS.hitPeriod = WorldKingSnake.YELLOW_TREE_DELAY;
			break;
        case WorldKingSnake.BROWN_TREE:
			dynObj.stateHS.isHeated = true;
			dynObj.stateHS.hitPeriodFull = dynObj.stateHS.hitPeriod = WorldKingSnake.BROWN_TREE_DELAY;
			break;
        case WorldKingSnake.ARROW_STONE:
			dynObj.stateHS.isHeated = true;
			dynObj.stateHS.hitPeriodFull = dynObj.stateHS.hitPeriod = WorldKingSnake.ARROW_STONE_DELAY;
			break;
        case WorldKingSnake.DEAD_STONE1:
			dynObj.stateHS.isHeated = true;
			dynObj.stateHS.healthDecrease += dynObj.stateHS.defencePower * WorldKingSnake.DEAD_STONE1_DAMAGE / 100;
			dynObj.stateHS.hitPeriodFull = dynObj.stateHS.hitPeriod = WorldKingSnake.DEAD_STONE1_DELAY;
			break;
        case WorldKingSnake.DEAD_STONE2:
			dynObj.stateHS.isHeated = true;
			dynObj.stateHS.healthDecrease += dynObj.stateHS.defencePower * WorldKingSnake.DEAD_STONE2_DAMAGE / 100;
			dynObj.stateHS.hitPeriodFull = dynObj.stateHS.hitPeriod = WorldKingSnake.DEAD_STONE2_DELAY;
			break;
        case WorldKingSnake.DEAD_STONE3:
			dynObj.stateHS.isHeated = true;
			dynObj.stateHS.healthDecrease += dynObj.stateHS.defencePower * WorldKingSnake.DEAD_STONE3_DAMAGE / 100;
			dynObj.stateHS.hitPeriodFull = dynObj.stateHS.hitPeriod = WorldKingSnake.DEAD_STONE3_DELAY;
			break;
    		
        }
        
        if(dynObj.stateHS.healthDecrease >= dynObj.stateHS.health || stop > 0)
        	return false;

        //check stop dynamic objects       
        for(int i = 0; i < WorldKingSnake.CELL_DIM; i++)
        {
        	for(int j=0; j< WorldKingSnake.CELL_DIM; j++)
        	{      	
	        	if(world.fields[WorldKingSnake.WORLD_DYNAMIC_OBJECTS_LEVEL][x+i][y+j] > 0)
	        	{
	        		DynamicGameObject dObj = getDynObj(x+i, y+j, world.fields[WorldKingSnake.WORLD_DYNAMIC_OBJECTS_LEVEL][x+i][y+j]);
	        			
	        		if(dObj != null)
	        		{   				
	        			if(dObj.objType != DynamicGameObject.HEDGEHOG)
	        			{
		        			dObj.stateHS.healthDecrease += dynObj.stateHS.attackPower;	        				        			
		        			if(dObj.stateHS.health > dObj.stateHS.healthDecrease)
		        				stop = 1;		//didn't kill the object        				
	        			}
	        			else
	        			{        				
	        				stop = 1;	
	        				dynObj.stateHS.healthDecrease += dObj.stateHS.attackPower;	//damaged from hedgehog's needles
	        			}        				        				
	        		}
	        	}
        	}
        }
        
        //couldn't overcome;
        if(stop > 0)
        {
			dynObj.stateHS.isHeated = true;
			dynObj.stateHS.hitPeriodFull = dynObj.stateHS.hitPeriod = WorldKingSnake.WALL_DELAY;
			return false;
        }        
        
        //check static objects:             	
        if(world.fields[WorldKingSnake.WORLD_STATIC_OBJECTS_LEVEL][x][y] > 0 &&
        world.fields[WorldKingSnake.WORLD_STATIC_OBJECTS_LEVEL][x][y] <= DynamicGameObject.BOMB_FROM_TREE)
        {
        		
        	GameObject sObj = getStaticObj(x, y);
        			
        	if(sObj != null)
        	{
        		//implement action for static object
        		switch(sObj.type){
        			
        		case GameObject.APPLE:
        			dynObj.stateHS.health += Gifts.APPLE_HEALTH_INCREASE * dynObj.stateHS.defencePower;
        			((Gifts)sObj).isEaten = true;
        			break;
        		case GameObject.MUSHROOM_BLUE:
        			((Gifts)sObj).isEaten = true;
    				provideMushroomAction(dynObj, GameObject.MUSHROOM_BLUE, ((Gifts)sObj).getBlueMushroomAction());
        			break;
        		case GameObject.MUSHROOM_YELLOW:
        			((Gifts)sObj).isEaten = true;
        			break;
        		case GameObject.MUSHROOM_BROWN:
        			((Gifts)sObj).isEaten = true;
    				provideMushroomAction(dynObj, GameObject.MUSHROOM_BLUE, ((Gifts)sObj).getBrownMushroomAction());
        			break;
        		case GameObject.MUSHROOM_BLUE_RED:
        			((Gifts)sObj).isEaten = true;
        			break;
        		case DynamicGameObject.BOMB_FROM_TREE:
        			dynObj.stateHS.healthDecrease += Gifts.BOMB_FROM_TREE_DAMAGE * dynObj.stateHS.defencePower;
        			((Gifts)sObj).isEaten = true;       				
            		break;
            		}       		       				
        		}
        	}
        
        //can't move - we dead
        if(dynObj.stateHS.health <= dynObj.stateHS.healthDecrease)
        	return false;	
        	
        //special processing for each character TO DO:       
		if(dynObj.objType == DynamicGameObject.FROG)
		{
			
		}	

		return true;		
	}
	
	
	
	
	
	
	//get object not from 1 cell
	public DynamicGameObject getDynObj(int x, int y, int type)
	{
    	if(x > WorldKingSnake.WORLD_WIDTH * WorldKingSnake.CELL_DIM - 1|| x < 0 || y > WorldKingSnake.WORLD_HEIGHT * WorldKingSnake.CELL_DIM - 1|| y < 0)
    		return null;   
    	
    	DynamicGameObject dObj;
    	
		for (int i = 0; i < world.dynObjects.size(); i++)
		{
			dObj = world.dynObjects.get(i);
			
			int xPos = (int)(dObj.position.x * WorldKingSnake.CELL_DIM);
			int yPos = (int)(dObj.position.y * WorldKingSnake.CELL_DIM);			
			
			if(x >= xPos - 1 && x <= xPos + 1 && y >= yPos - 1 && y <= yPos + 1 && dObj.objType == type)
				return dObj;
		}
		
		return null;
	}
	
	//get object not from 1 cell
	public DynamicGameObject getDynObjByCoords(float _x, float _y, int type)
	{
		int x = (int)(_x * WorldKingSnake.CELL_DIM);
		int y = (int)(_y * WorldKingSnake.CELL_DIM);
		
    	if(x >= WorldKingSnake.WORLD_WIDTH * WorldKingSnake.CELL_DIM|| x < 0 || y >= WorldKingSnake.WORLD_HEIGHT * WorldKingSnake.CELL_DIM|| y < 0)
    		return null;
    	
    	DynamicGameObject dObj;
    	
		for (int i = 0; i < world.dynObjects.size(); i++)
		{
			dObj = world.dynObjects.get(i);
			
			int xPos = (int)(dObj.position.x * WorldKingSnake.CELL_DIM);
			int yPos = (int)(dObj.position.y * WorldKingSnake.CELL_DIM);			
			
			if(x >= xPos - 1 && x <= xPos + 1 && y >= yPos - 1 && y <= yPos + 1 && dObj.objType == type)
				return dObj;
		}
		
		return null;    	
	}
	
	//get object not from 1 cell
	public DynamicGameObject getDynObjByCoords(float _x, float _y)
	{
		int x = (int)(_x * WorldKingSnake.CELL_DIM);
		int y = (int)(_y * WorldKingSnake.CELL_DIM);
		
    	if(x >= WorldKingSnake.WORLD_WIDTH * WorldKingSnake.CELL_DIM|| x < 0 || y >= WorldKingSnake.WORLD_HEIGHT * WorldKingSnake.CELL_DIM|| y < 0)
    		return null;
    	
    	DynamicGameObject dObj;
    	
		for (int i = 0; i < world.dynObjects.size(); i++)
		{
			dObj = world.dynObjects.get(i);
			
			int xPos = (int)(dObj.position.x * WorldKingSnake.CELL_DIM);
			int yPos = (int)(dObj.position.y * WorldKingSnake.CELL_DIM);			 
			
			if(x >= xPos - 1 && x <= xPos + 1 && y >= yPos - 1 && y <= yPos + 1)
				return dObj;
		}
		
		return null;    	
	}
	
	
	//get object from 1 cell
	public GameObject getStaticObj(int x, int y)
	{
    	if(x > WorldKingSnake.WORLD_WIDTH * WorldKingSnake.CELL_DIM - 1|| x < 0 || y > WorldKingSnake.WORLD_HEIGHT * WorldKingSnake.CELL_DIM - 1 || y < 0)
    		return null;   
    	
    	GameObject sObj;
    	
		for (int i = 0; i < world.statObjects.size(); i++)
		{
			sObj = world.statObjects.get(i);
			if(x >= WorldKingSnake.CELL_DIM * ((int) sObj.position.x) && x < WorldKingSnake.CELL_DIM * ((int) sObj.position.x) + WorldKingSnake.CELL_DIM
			&& y >= WorldKingSnake.CELL_DIM * ((int) sObj.position.y) && y < WorldKingSnake.CELL_DIM * ((int)sObj.position.y) + WorldKingSnake.CELL_DIM)
			{
				return sObj;
			}
		}
		
		return null;
	}
	
	public float getReliefFF(int x, int y)
	{
    	if(x > WorldKingSnake.WORLD_WIDTH - 1|| x < 0 || y > WorldKingSnake.WORLD_HEIGHT - 1 || y < 0)
    		return 0.5f;
    	
    	float ff = 0;
    	
    	for(int i=0; i < WorldKingSnake.CELL_DIM; i++)
    	{
    		for (int j=0; j<WorldKingSnake.CELL_DIM; j++)
    		{
    			switch(world.fields[WorldKingSnake.WORLD_RELIEF_LEVEL][x+i][y+j])
    			{
    			case WorldKingSnake.GRASS:
    				ff += WorldKingSnake.GRASS_FF;
    				break;
    			case WorldKingSnake.SAND :
    				ff += WorldKingSnake.SAND_FF;
    				break;
    			case WorldKingSnake.WATER :
    				ff += WorldKingSnake.WATER_FF;
    				break;
    			case WorldKingSnake.SWAMP :
    				ff += WorldKingSnake.SWAMP_FF;
    				break;    				
    			}
    		}    		
    	} 
    	
    	if (ff == 0)
    		return 0.5f;
    	
    	return ff /= WorldKingSnake.CELL_DIM * WorldKingSnake.CELL_DIM;	//get middle ff;
	}
	
	public int getRelief(int x, int y)
	{
    	if(x > WorldKingSnake.WORLD_WIDTH - 1|| x < 0 || y > WorldKingSnake.WORLD_HEIGHT - 1 || y < 0)
    		return 0;
    	
		return world.fields[WorldKingSnake.WORLD_RELIEF_LEVEL][x * WorldKingSnake.CELL_DIM][y * WorldKingSnake.CELL_DIM];
	}
	
	
	public void placeTreeBomb(DynamicGameObject dynObj)
	{
		if(getSubCellByCoords(dynObj.position.x, dynObj.position.y, WorldKingSnake.WORLD_DYNAMIC_OBJECTS_LEVEL) > 0)
		{
			DynamicGameObject dObj = getDynObjByCoords(dynObj.position.x, dynObj.position.y);
			
			if(dObj == null)
				return;
			
			dObj.stateHS.healthDecrease += Gifts.BOMB_FROM_TREE_DAMAGE;
			
		}
		else			
			if(placeSubObjectToGreedByCoords(dynObj.position.x, dynObj.position.y, WorldKingSnake.WORLD_DYNAMIC_OBJECTS_LEVEL, DynamicGameObject.BOMB_FROM_TREE))
				world.dynObjects.add(new DynamicGameObject(dynObj.position.x, dynObj.position.y, BombTree.BOMB_SIZE,
						BombTree.BOMB_SIZE, 0, DynamicGameObject.BOMB_FROM_TREE, world));						
		
		dynObj.stateHS.isDead = HealthScore.DEAD_DELETED;
	}
	
	public DynamicGameObject getFlyingObjByCoords(float x, float y)
	{
		DynamicGameObject dynObj;
		
		for(int i=0; i<world.flyingObjects.size(); i++)
		{
			dynObj = world.flyingObjects.get(i);
			rect.set(dynObj.position.x - dynObj.bounds.width/2, dynObj.position.y - dynObj.bounds.height/2, dynObj.bounds.width, dynObj.bounds.height);
			tVector1.x = x;
			tVector1.y = y;
			
			if(OverlapTester.pointInRectangle(rect, tVector1))
				return dynObj;
								
		}
		
		return null;
	}
	
	public GameObject getAnyObjByCoords(float x, float y)
	{
		GameObject gObj = null;
		
		//first, check flying objects;
		gObj = getFlyingObjByCoords(x, y);
		
		if(gObj != null)
			return gObj;
		
		//then check dynamic objects;
		gObj = getDynObjByCoords(x, y);
		
		if(gObj != null)
			return gObj;
		
		//then check static objects;
		int _x = (int)(x * WorldKingSnake.CELL_DIM);
		int _y = (int)(y * WorldKingSnake.CELL_DIM);
		
		gObj = getStaticObj(_x, _y);
		
		return gObj;				
	}
	
	public float getDistanceToMyCellCenter(float x, float y)
	{
		int xCell = (int) (x * WorldKingSnake.CELL_DIM);
		int yCell = (int) (y * WorldKingSnake.CELL_DIM);
		float xMiddleCell = xCell + WorldKingSnake.CELL_SIZE/ 2;
		float yMiddleCell = yCell + WorldKingSnake.CELL_SIZE/ 2;
		
		return (float) Math.sqrt((x - xMiddleCell) * (x - xMiddleCell) + (y - yMiddleCell) * (y - yMiddleCell));
	}
	
	public void DynObjToReliefImpactProcess(DynamicGameObject dynObj, GameObject gObj, float angleImpact)
	{
        switch(gObj.type)
        {
        case WorldKingSnake.WALL:
			DynObjImpactBasicProcess(dynObj, WorldKingSnake.WALL_DELAY, getReliefImpactDamage(gObj.type), angleImpact);
			break;
        case WorldKingSnake.GREEN_TREE:
			DynObjImpactBasicProcess(dynObj, WorldKingSnake.GREEN_TREE_DELAY, getReliefImpactDamage(gObj.type), angleImpact);							
			break;			
        case WorldKingSnake.YELLOW_TREE:
			DynObjImpactBasicProcess(dynObj, WorldKingSnake.YELLOW_TREE_DELAY, getReliefImpactDamage(gObj.type), angleImpact);
			break;
        case WorldKingSnake.BROWN_TREE:
			DynObjImpactBasicProcess(dynObj, WorldKingSnake.BROWN_TREE_DELAY, getReliefImpactDamage(gObj.type), angleImpact);
			break;
        case WorldKingSnake.ARROW_STONE:
			DynObjImpactBasicProcess(dynObj, WorldKingSnake.ARROW_STONE_DELAY, getReliefImpactDamage(gObj.type), angleImpact);
			break;
        case WorldKingSnake.DEAD_STONE1:
			DynObjImpactBasicProcess(dynObj, WorldKingSnake.DEAD_STONE1_DELAY, dynObj.stateHS.defencePower * getReliefImpactDamage(gObj.type), angleImpact);
			break;
        case WorldKingSnake.DEAD_STONE2:
			DynObjImpactBasicProcess(dynObj, WorldKingSnake.DEAD_STONE2_DELAY, dynObj.stateHS.defencePower * getReliefImpactDamage(gObj.type), angleImpact);
			break;
        case WorldKingSnake.DEAD_STONE3:
			DynObjImpactBasicProcess(dynObj, WorldKingSnake.DEAD_STONE3_DELAY, dynObj.stateHS.defencePower * getReliefImpactDamage(gObj.type), angleImpact);
			break;    		
        }		
	}
	
	public float getReliefImpactDamage(int type)
	{
		float damage = 0;
		
        switch(type)
        {
        case WorldKingSnake.WALL:
			break;
        case WorldKingSnake.GREEN_TREE:						
			break;			
        case WorldKingSnake.YELLOW_TREE:
			break;
        case WorldKingSnake.BROWN_TREE:
			break;
        case WorldKingSnake.ARROW_STONE:
			break;
        case WorldKingSnake.DEAD_STONE1:
			damage = (float) WorldKingSnake.DEAD_STONE1_DAMAGE / 100;
			break;
        case WorldKingSnake.DEAD_STONE2:
			damage = (float) WorldKingSnake.DEAD_STONE2_DAMAGE / 100;
			break;
        case WorldKingSnake.DEAD_STONE3:
			damage = (float) WorldKingSnake.DEAD_STONE3_DAMAGE / 100;
			break;    		
        }	
        
        return damage;
	}
	
	public void DynObjToStatImpactProcess(DynamicGameObject dynObj, GameObject sObj, float angleImpact)
	{
		if(((Gifts)sObj).isEaten || dynObj.stateHS.isDead != HealthScore.ALIVE)
			return;
		
		//if snake part obj get pointer to snake
		if(dynObj.objType == Statics.DynamicGameObject.SNAKE)
			dynObj = ((SnakePart)dynObj).snake;
		
		//implement action for static object
		switch(sObj.type)
		{		
		case GameObject.APPLE:
			if(dynObj.objType == Statics.DynamicGameObject.SNAKE)
				((Snake)dynObj).eatApple();
			else
				dynObj.stateHS.healthIncrease += Gifts.APPLE_HEALTH_INCREASE * dynObj.stateHS.defencePower;
			break;
		case GameObject.MUSHROOM_BLUE:
			provideMushroomAction(dynObj, GameObject.MUSHROOM_BLUE, ((Gifts)sObj).getBlueMushroomAction());
			break;
		case GameObject.MUSHROOM_YELLOW:
			dynObj.stateHS.coinsIncrease += Gifts.MUSHROOM_YELLOW_COINS;
			break;
		case GameObject.MUSHROOM_BROWN:
			provideMushroomAction(dynObj, GameObject.MUSHROOM_BROWN, ((Gifts)sObj).getBrownMushroomAction());
			break;
		case GameObject.MUSHROOM_BLUE_RED:
			provideMushroomAction(dynObj, GameObject.MUSHROOM_BLUE_RED, 0);
			break;
		} 
		
		((Gifts)sObj).isEaten = true;
		dynObj.stateHS.isSwallow = true;
		dynObj.stateHS.swallowStartTime = world.actTime;
		
	}
	
	
	public void DynObjImpactBasicProcess(DynamicGameObject dynObj, int hitFullPeriod, float healthDecrease, float angleImpact)
	{
		dynObj.stateHS.healthDecrease += healthDecrease;	
		
		if(dynObj.objType == Statics.DynamicGameObject.SNAKE)
		{
			if(((SnakePart)dynObj).index == Statics.DynamicGameObject.SNAKE_HEAD_INDEX)
			{
				Snake snake = ((SnakePart)dynObj).snake;
				
				if(snake.stateHS.isDead == HealthScore.ALIVE)
				{
					snake.stateHS.isHeated = true;
					snake.angleContact = angleImpact;
					snake.stateHS.hitPeriodFull = snake.stateHS.hitPeriod = hitFullPeriod;
				}
			}			
		}
		else if(dynObj.stateHS.isDead == HealthScore.ALIVE)
		{
			dynObj.stateHS.hitPeriodFull = dynObj.stateHS.hitPeriod = hitFullPeriod;			
			dynObj.stateHS.isHeated = true;
			dynObj.angleContact = angleImpact;			
		}			
	}
	
	public DynamicGameObject CreateDeadPart(float x, float y, float width, float height)
	{
		DynamicGameObject gObj = new DynamicGameObject(x, y, width, height, 0, DynamicGameObject.DEAD_PART_VISIBLE_STILL, world);
		world.dynObjects.add(gObj);
		return gObj;
			
	}
	
	public void bloodProcess(DynamicGameObject dynObj)
	{
		if(dynObj.stateHS.healthDecrease >= HealthScore.BLOOD_LARGE_LEVEL)	//>= 3000
		{
       		world.statObjects.add(new StaticEffect(dynObj.position.x, dynObj.position.y,
        			StaticEffect.BLOOD_SPLATTER_LARGE_SIZE + dynObj.stateHS.healthDecrease / 3000,
        			StaticEffect.BLOOD_SPLATTER_LARGE_SIZE + dynObj.stateHS.healthDecrease / 3000, Statics.StaticEffect.BLOOD_SPLATTER_BIG, dynObj.angleContact, StaticEffect.BLOOD_SPLATTER_LARGE_PERIOD));
       		
       		world.bloodStains.add(new StaticEffect(dynObj.position.x, dynObj.position.y,
        			StaticEffect.BLOOD_STAIN_LARGE_SIZE,
        			StaticEffect.BLOOD_STAIN_LARGE_SIZE, GameObject.BLOOD_STAIN1, world.random.nextInt(360)));
		}
		else if(dynObj.stateHS.healthDecrease >= HealthScore.BLOOD_MEDIUM_LEVEL)	//100-2999
		{
			world.statObjects.add(new StaticEffect(dynObj.position.x, dynObj.position.y,
        			StaticEffect.BLOOD_SPLATTER_MEDIUM_SIZE + dynObj.stateHS.healthDecrease / 3000,
        			StaticEffect.BLOOD_SPLATTER_MEDIUM_SIZE + dynObj.stateHS.healthDecrease / 3000, Statics.StaticEffect.BLOOD_SPLATTER_MEDIUM,  dynObj.angleContact, StaticEffect.BLOOD_SPLATTER_MEDIUM_PERIOD));
    		
			world.bloodStains.add(new StaticEffect(dynObj.position.x, dynObj.position.y,
        			StaticEffect.BLOOD_STAIN_MEDIUM_SIZE,
        			StaticEffect.BLOOD_STAIN_MEDIUM_SIZE, GameObject.BLOOD_STAIN1, world.random.nextInt(360)));
		}
		else if(dynObj.stateHS.healthDecrease >= HealthScore.BLOOD_SMALL_LEVEL) //1-99
		{
			world.statObjects.add(new StaticEffect(dynObj.position.x, dynObj.position.y,
    			StaticEffect.BLOOD_SPLATTER_SMALL_SIZE + dynObj.stateHS.healthDecrease / 50,
    			StaticEffect.BLOOD_SPLATTER_SMALL_SIZE + dynObj.stateHS.healthDecrease / 50, Statics.StaticEffect.BLOOD_SPLATTER_SMALL,  dynObj.angleContact, StaticEffect.BLOOD_SPLATTER_SMALL_PERIOD));   
    		
    		if(dynObj.stateHS.healthDecrease >= HealthScore.BLOOD_MEDIUM_LEVEL / 4)
    			world.bloodStains.add(new StaticEffect(dynObj.position.x, dynObj.position.y,
            			StaticEffect.BLOOD_STAIN_SMALL_SIZE,
            			StaticEffect.BLOOD_STAIN_SMALL_SIZE, GameObject.BLOOD_STAIN1 + world.random.nextInt(2) + 1, world.random.nextInt(360)));
		}
	}

	public boolean isCharFriend(DynamicGameObject dynObj, DynamicGameObject dObj)
	{
		if((dynObj.stateHS.frendByRace == true && (dObj.objType == dynObj.objType)) || 
				(dynObj.stateHS.frendToTeam > 0 && (!dObj.stateHS.isBot || dynObj.stateHS.frendToTeam == dObj.stateHS.frendToTeam)) ||
				(dynObj.stateHS.frendtoAlienRace == dObj.objType)
				)
			return true;	//friend
		else
			return false;
		
	}
	
	public static boolean isCharracterFriend(DynamicGameObject dynObj, DynamicGameObject dObj)
	{
		if((dynObj.stateHS.frendByRace == true && (dObj.objType == dynObj.objType)) || 
				(dynObj.stateHS.frendToTeam > 0 && (!dObj.stateHS.isBot || dynObj.stateHS.frendToTeam == dObj.stateHS.frendToTeam)) ||
				(dynObj.stateHS.frendtoAlienRace == dObj.objType)
				)
			return true;	//friend
		else
			return false;
		
	}
	
	void markSnakeCellsForFSKill(DynamicGameObject dynObj)
	{
		Snake snake = (Snake) dynObj;
		SnakePart sPart = null;
		int size = snake.parts.size();
		
		int xPos = 0;
		int yPos = 0;	
		
		for(int i = 0; i < size; ++i)
		{
			sPart = snake.parts.get(i);
			
			xPos = (int) (sPart.position.x * CELL_DIM);
			yPos = (int) (sPart.position.y * CELL_DIM);
			
			//exclude our body weight
			markCellForFSkill(xPos, yPos);
		}
	}
	
	void unmarkSnakeCellsForFSKill(DynamicGameObject dynObj)
	{
		Snake snake = (Snake) dynObj;
		SnakePart sPart = null;
		int size = snake.parts.size();
		
		int xPos = 0;
		int yPos = 0;	
		
		for(int i = 0; i < size; ++i)
		{
			sPart = snake.parts.get(i);			
			xPos = (int) (sPart.position.x * CELL_DIM);
			yPos = (int) (sPart.position.y * CELL_DIM);
			unMarkCellForFSkill(xPos, yPos);
		}
	}
	
	public float getObstacleKoefForForwardAttack(DynamicGameObject dynObj, DynamicGameObject target)
	{
		int xStart = (int) (dynObj.position.x * CELL_DIM);
		int yStart = (int) (dynObj.position.y * CELL_DIM);
		int xTarget = (int) (target.position.x * CELL_DIM);
		int yTarget = (int) (target.position.y * CELL_DIM);
		
		float obWeightSum = 0;
		
		//find coefficient of line between start and target: y = k * x + b;
		float k = xTarget != xStart ? (float) (yTarget - yStart) / (xTarget - xStart) : 1000;	
		
		int xSize = Math.abs( xTarget - xStart);
		int ySize = Math.abs( yTarget - yStart);

		//exclude our body weight
		if(dynObj.objType == Statics.DynamicGameObject.SNAKE)
			markSnakeCellsForFSKill(dynObj);
		else
			markCellForFSkill(xStart, yStart);
		
		int xCur = 0;
		int yCur = 0;
		
		for(int i = 1; i < xSize; ++i)
		{
			xCur = xStart <= xTarget ? xStart + i : xStart - i;
			
			if(xCur >= WORLD_LWIDTH || xCur < 0)
			{
				obWeightSum += Statics.AI.MAX_WEIGHT;
				continue;
			}
			
			yCur = (int) k * (xCur - xStart) + yStart;
			
			if(yCur >= WORLD_LHEIGHT || yCur < 0)
			{
				obWeightSum += Statics.AI.MAX_WEIGHT;
				continue;
			}			
			obWeightSum += getCellWeightForFSkill(xCur, yCur);
		}
		
		for(int i = 1; i < ySize; ++i)
		{
			yCur = yStart <= yTarget ? yStart + i : yStart - i;
			
			if(yCur >= WORLD_LHEIGHT || yCur < 0)
			{
				obWeightSum += Statics.AI.MAX_WEIGHT;
				continue;
			}
			
			xCur =  (int) ((yCur - yStart) / k + xStart);
			
			if(k >= 1000)
			{
				Log.d("K = 1000!!!", "yCur= " + yCur + ", xCur = " + xCur);
			}
			
			if(xCur >= WORLD_LWIDTH || xCur < 0)
			{
				obWeightSum += Statics.AI.MAX_WEIGHT;
				continue;
			}						
			obWeightSum += getCellWeightForFSkill(xCur, yCur);
		}
		
		//now unMark cells
		for(int i = 0; i < xSize; ++i)
		{
			xCur = xStart <= yTarget ? xStart + i : xStart - i;
			
			if(xCur >= WORLD_LWIDTH || xCur < 0)
				continue;
			
			yCur = (int) k * (xCur - xStart) - yStart;
			
			if(yCur >= WORLD_LHEIGHT || yCur < 0)
				continue;
			
			unMarkCellForFSkill(xCur, yCur);
		}
		
		for(int i = 0; i < ySize; ++i)
		{
			yCur = yStart <= yTarget ? yStart + i : yStart - i;
			
			if(yCur >= WORLD_LHEIGHT || yCur < 0)
				continue;
			
			xCur =  (int) ((yCur + yStart) / k + xStart);
			
			if(xCur >= WORLD_LWIDTH || xCur < 0)
				continue;
						
			unMarkCellForFSkill(xCur, yCur);
		}
		
		if(dynObj.objType == Statics.DynamicGameObject.SNAKE)
			unmarkSnakeCellsForFSKill(dynObj);
		else
			unMarkCellForFSkill(xStart, yStart);
				
		return obWeightSum;
	}
	
	public float getObstacleKoefForHook(DynamicGameObject dynObj, DynamicGameObject target, boolean isLeft)
	{
		float xMy = dynObj.position.x * CELL_DIM;
		float yMy = dynObj.position.y * CELL_DIM;
		float xTarget = target.position.x * CELL_DIM;
		float yTarget = target.position.y * CELL_DIM;
		
		float xStart = 0, yStart = 0, xFinish = 0, yFinish = 0;	
		
		com.badlogic.gdx.math.Vector2 jumpDirection = new com.badlogic.gdx.math.Vector2(target.position.x - dynObj.position.x,
				target.position.y - dynObj.position.y);
		
		com.badlogic.gdx.math.Vector2 centerToPointDir = new com.badlogic.gdx.math.Vector2();
		
		float radius = CELL_DIM * target.position.dist(dynObj.position) / 2;
		float xCenter = CELL_DIM * (target.position.x + dynObj.position.x) / 2;
		float yCenter = CELL_DIM * (target.position.y + dynObj.position.y) / 2;
		
		//define x, y intervals by attack direction
		if(xMy <= xTarget && yMy <= yTarget)
		{
			if(isLeft)
			{
				xStart = xCenter - radius;
				xFinish = xTarget;
				yStart = yMy;
				yFinish = yCenter + radius;
			}
			else
			{
				xStart = xMy;
				xFinish = xCenter + radius;
				yStart = yCenter - radius;
				yFinish = yTarget;
			}
		}
		else if(xMy <= xTarget && yMy > yTarget)
		{
			if(isLeft)
			{
				xStart = xMy;
				xFinish = xCenter + radius;
				yStart = yTarget;
				yFinish = yCenter + radius;
			}
			else
			{
				xStart = xCenter - radius;
				xFinish = xTarget;
				yStart = yCenter - radius;
				yFinish = yMy;
			}
		}
		else if(xMy > xTarget && yMy >= yTarget)
		{			
			if(isLeft)
			{
				xStart = xTarget;
				xFinish = xCenter + radius;
				yStart = yCenter - radius;
				yFinish = yMy;
			}
			else
			{
				xStart = xCenter - radius;
				xFinish = xMy;
				yStart = yTarget;
				yFinish =  yCenter + radius;
			}
		}
		else //if(xMy > xTarget && yMy < yTarget)
		{
			if(isLeft)
			{
				xStart = xCenter - radius;
				xFinish = xMy;
				yStart = yCenter - radius;
				yFinish = yTarget;
			}
			else
			{
				xStart = xTarget;
				xFinish = xCenter + radius;
				yStart = yMy;
				yFinish =  yCenter + radius;
			}
		}
		
		int xSize = (int) Math.abs( xFinish - xStart);
		int ySize = (int) Math.abs( yFinish - yStart);
		
		float obWeightSum = 0;

		//exclude our body weight
		markCellForFSkill((int)xMy, (int)yMy);
		
		float xCur = 0;
		float yCur = 0;
		
		for(int i = 0; i < xSize; ++i)
		{
			xCur =  xStart + i;
			
			if(xCur >= WORLD_LWIDTH || xCur < 0)
			{
				obWeightSum += Statics.AI.MAX_WEIGHT;
				continue;
			}
			
			yCur = (float) Math.sqrt(Math.abs(Math.pow(radius, 2) - Math.pow((xCur - xCenter), 2)));
			
			if(yCur + yCenter >= WORLD_LHEIGHT || -yCur + yCenter < 0)
			{
				obWeightSum += Statics.AI.MAX_WEIGHT;
				continue;
			}
			
			centerToPointDir.set(xCur - xCenter, yCur);
			
			if(isLeft ? centerToPointDir.angle(jumpDirection) <= 0 : centerToPointDir.angle(jumpDirection) > 0)			
				obWeightSum += getCellWeightForFSkill((int)xCur, (int)(yCur + yCenter));
			
			centerToPointDir.set(xCur - xCenter, -yCur);
			
			if(isLeft ? centerToPointDir.angle(jumpDirection) <= 0 : centerToPointDir.angle(jumpDirection) > 0)			
				obWeightSum += getCellWeightForFSkill((int)xCur, (int)(-yCur + yCenter));			
		}
		
		for(int i = 0; i < ySize; ++i)
		{
			yCur = yStart + i;
			
			if(yCur >= WORLD_LHEIGHT || yCur < 0)
			{
				obWeightSum += Statics.AI.MAX_WEIGHT;
				continue;
			}
			
			xCur = (float) Math.sqrt(Math.abs(Math.pow(radius, 2) - Math.pow((yCur - yCenter), 2)));
			
			if(xCur + xCenter >= WORLD_LWIDTH || -xCur + xCenter < 0)
			{
				obWeightSum += Statics.AI.MAX_WEIGHT;
				continue;
			}
			
			centerToPointDir.set(xCur, yCur - yCenter);
			
			if(isLeft ? centerToPointDir.angle(jumpDirection) <= 0 : centerToPointDir.angle(jumpDirection) > 0)	
				obWeightSum += getCellWeightForFSkill((int)(xCur + xCenter), (int)yCur);
			
			centerToPointDir.set(-xCur, yCur - yCenter);
			
			if(isLeft ? centerToPointDir.angle(jumpDirection) <= 0 : centerToPointDir.angle(jumpDirection) > 0)	
				obWeightSum += getCellWeightForFSkill((int)(-xCur + xCenter), (int)yCur);
		}
		
		//now unMark cells
		for(int i = 0; i < xSize; ++i)
		{
			xCur =  xStart + i;
			
			if(xCur >= WORLD_LWIDTH || xCur < 0)
				continue;
			
			yCur = (float) Math.sqrt(Math.abs(Math.pow(radius, 2) - Math.pow((xCur - xCenter), 2)));
			
			if(yCur + yCenter >= WORLD_LHEIGHT || -yCur + yCenter < 0)
				continue;
			
			centerToPointDir.set(xCur - xCenter, yCur);
			
			if(isLeft ? centerToPointDir.angle(jumpDirection) <= 0 : centerToPointDir.angle(jumpDirection) > 0)			
				unMarkCellForFSkill((int)xCur, (int)(yCur + yCenter));
			
			centerToPointDir.set(xCur - xCenter, -yCur);
			
			if(isLeft ? centerToPointDir.angle(jumpDirection) <= 0 : centerToPointDir.angle(jumpDirection) > 0)			
				unMarkCellForFSkill((int)xCur, (int)(-yCur + yCenter));			
		}
		
		for(int i = 0; i < ySize; ++i)
		{
			yCur = yStart + i;
			
			if(yCur >= WORLD_LHEIGHT || yCur < 0)
				continue;
			
			xCur = (float) Math.sqrt(Math.abs(Math.pow(radius, 2) - Math.pow((yCur - yCenter), 2)));
			
			if(xCur + xCenter >= WORLD_LWIDTH || -xCur + xCenter < 0)
				continue;
			
			centerToPointDir.set(xCur, yCur - yCenter);
			
			if(isLeft ? centerToPointDir.angle(jumpDirection) <= 0 : centerToPointDir.angle(jumpDirection) > 0)	
				unMarkCellForFSkill((int)(xCur + xCenter), (int)yCur);
			
			centerToPointDir.set(-xCur, yCur - yCenter);
			
			if(isLeft ? centerToPointDir.angle(jumpDirection) <= 0 : centerToPointDir.angle(jumpDirection) > 0)	
				unMarkCellForFSkill((int)(-xCur + xCenter), (int)yCur);
		}		
		unMarkCellForFSkill((int)xMy, (int)yMy);
		
		return obWeightSum;
	}

	
	public float getObstacleKoefForBackHook(DynamicGameObject dynObj, DynamicGameObject target, boolean isLeft)
	{
		if(dynObj.objType == Statics.DynamicGameObject.SNAKE)
		{
			Snake snake = (Snake) dynObj;
			return getObstacleKoefForHook(snake.parts.get(snake.parts.size() - 1), target, isLeft);
		}
		else
			return Statics.AI.MAX_WEIGHT;
	}
	
	public float getObstacleKoefForBackAttack(DynamicGameObject dynObj, DynamicGameObject target)
	{
		if(dynObj.objType == Statics.DynamicGameObject.SNAKE)
		{
			Snake snake = (Snake) dynObj;
			return getObstacleKoefForForwardAttack(snake.parts.get(snake.parts.size() - 1), target);
		}
		else
			return Statics.AI.MAX_WEIGHT;
	}
	

	
	float getCellWeightForFSkill(int xPos, int yPos)
	{
		float weight = 0;
		int radius = CELL_DIM / 2;
		int xCur = 0, yCur = 0;
		
		for(int x = - radius; x <= radius; ++x)
		{
			xCur = xPos + x;
			
			if(xCur >= WORLD_LWIDTH)
				return Statics.AI.MAX_WEIGHT;
			else if(xCur < 0)
				return Statics.AI.MAX_WEIGHT;
			
			for(int y = - radius; y <= radius; ++y)
			{
				yCur = yPos + y;
			
				if(yCur >= WORLD_LHEIGHT)
					return Statics.AI.MAX_WEIGHT;
				else if(yCur < 0)
					return Statics.AI.MAX_WEIGHT;
				
				//get weight and mark cell
				if(world.fields[WorldKingSnake.WORLD_LAB_ORIENT_WEIGHTS_LEVEL][xCur][yCur] > Statics.AI.EMPTY_SPACE_WEIGHT)
				{
					weight += (world.fields[WorldKingSnake.WORLD_LAB_ORIENT_WEIGHTS_LEVEL][xCur][yCur] - 
							Statics.AI.EMPTY_SPACE_WEIGHT);
					world.fields[WorldKingSnake.WORLD_LAB_ORIENT_WEIGHTS_LEVEL][xCur][yCur] *= -1;
				}
			}
		}
		
		return weight;
	}
	
	void unMarkCellForFSkill(int xPos, int yPos)
	{
		int radius = CELL_DIM / 2;
		int xCur = 0, yCur = 0;
		
		for(int x = - radius; x <= radius; ++x)
		{
			xCur = xPos + x;
			
			if(xCur >= WORLD_LWIDTH)
				continue;
			else if(xCur < 0)
				continue;
			
			for(int y = - radius; y <= radius; ++y)
			{
				yCur = yPos + y;
			
				if(yCur >= WORLD_LHEIGHT)
					continue;
				else if(yCur < 0)
					continue;
				
				//get weight and mark cell
				if(world.fields[WorldKingSnake.WORLD_LAB_ORIENT_WEIGHTS_LEVEL][xCur][yCur] < 0)
					world.fields[WorldKingSnake.WORLD_LAB_ORIENT_WEIGHTS_LEVEL][xCur][yCur] *= -1;
			}
		}
	}
	
	void markCellForFSkill(int xPos, int yPos)
	{
		float weight = 0;
		int radius = CELL_DIM / 2;
		int xCur = 0, yCur = 0;
		
		for(int x = - radius; x <= radius; ++x)
		{
			xCur = xPos + x;
			
			if(xCur >= WORLD_LWIDTH)
				continue;
			else if(xCur < 0)
				continue;
			
			for(int y = - radius; y <= radius; ++y)
			{
				yCur = yPos + y;
			
				if(yCur >= WORLD_LHEIGHT)
					continue;
				else if(yCur < 0)
					continue;
				
				//get weight and mark cell
				if(world.fields[WorldKingSnake.WORLD_LAB_ORIENT_WEIGHTS_LEVEL][xCur][yCur] > 0)
					world.fields[WorldKingSnake.WORLD_LAB_ORIENT_WEIGHTS_LEVEL][xCur][yCur] *= -1;
			}
		}
	}
	
    public DynamicGameObject createDynObject(float x, float y, float width, float height, float angle, int objType, int level)
    {
    	DynamicGameObject dynObj = new DynamicGameObject(x, y, width, height, angle, objType, level, this.world);
    	world.dynObjects.add(dynObj);
    	return dynObj;
    }
    
    public StaticEffect createStaticEffect(float x, float y, float width, float height, float angle, int type, float lifetime, int level, GameObject master)
    {
    	StaticEffect effect;
    	
    	switch(type)
    	{
	    	case Statics.StaticEffect.IMPACT_STARS:
	    		effect = new StaticEffectImpactStars(x, y, width, height, angle, lifetime, master);
	    		break;
	    	case Statics.StaticEffect.HYPNOSE_EFFECT:
	    		effect = new StaticEffectHypnose(x, y, width, height, angle, lifetime, (DynamicGameObject)master);
	    		break;
	    	case Statics.StaticEffect.FIRE_EFFECT:
	    		effect = new StaticEffectFire(x, y, width, height, angle, lifetime, level, master);
	    		break;
	    	default: 
	    		effect = new StaticEffect(x, y, width, height, type, angle, lifetime);  	
    	}
    	world.statEffectsAbove.add(effect);
    	return effect;
    }
    
    public void processHypnoseCharacter(DynamicGameObject character, DynamicEffectHypnose effect)
    {
    	if(!character.isCharacter || character.stateHS.isHypnosed || character.stateHS.isDead == HealthScore.DEAD_SHOWN
    			|| character.stateHS.isDead == HealthScore.DEAD_DELETED)
    		return; 
       	
    	character.stateHS.isHypnosed = true;
    	float hypnosePeriod = Statics.StaticEffect.HYPNOSEACTION_START_PERIOD + effect.power * Statics.StaticEffect.HYPNOSEACTION_STEP_PERIOD;
    	character.stateHS.hypPeriod = hypnosePeriod;
    	
		createStaticEffect(character.position.x, character.position.y, 
				Statics.StaticEffect.HYPNOSEACTION_SIZE, Statics.StaticEffect.HYPNOSEACTION_SIZE, 0,
				Statics.StaticEffect.HYPNOSE_EFFECT, hypnosePeriod, 0, character);
    }
    
    public void processObjectFlameReaction(GameObject gObj, final int levelFire, Fixture fixObj)
    {
		int material = (int) ((float[])fixObj.getUserData())[PhysicsBox2d.FIXTURE_PROPERTY2_INDEX_MATERIAL];
    	gObj.startFlaming(levelFire);
		
		if(material == Statics.PhysicsBox2D.FIXTURE_MATERIAL_ICE)
		{
			 ((float[])fixObj.getUserData())[PhysicsBox2d.FIXTURE_PROPERTY3_INDEX_HEALTH] -= Statics.Healthscore.FATAL_DAMAGE;
			 ParticlesEffectsManager.provideSteamEffect(levelFire, gObj.position.x, gObj.position.y, Statics.StaticEffect.STEAM_EFFECT_DEFAULT_PERIOD);
		}
		else
			createStaticEffect(gObj.position.x, gObj.position.y, 0, 0, 0, Statics.StaticEffect.FIRE_EFFECT,  gObj.flamePeriod, levelFire, gObj); 	
    }
    

	

    
    public static float getFireDamage (int fireLevel)
    {
    	return Statics.FightingSkills.FIGHTSKILL_FIRE_FORWARD_START_DAMAGE +
    			fireLevel * Statics.FightingSkills.FIGHTSKILL_FIRE_FORWARD_STEP_DAMAGE;
    }
    
    /**
     * produce steam effect and stops flaming of object when it touch water
     * @param gObj flaming object 
     */
    public static void processEventFlamingObjectToWaterReaction(GameObject gObj)
    {
		 ParticlesEffectsManager.provideSteamEffect(gObj.flamePower, gObj.position.x, gObj.position.y, Statics.StaticEffect.STEAM_EFFECT_DEFAULT_PERIOD);
		 gObj.stopFlaming();   	
    }


}
