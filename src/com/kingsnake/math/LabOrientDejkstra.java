package com.kingsnake.math;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.util.Log;

import com.example.framework.model.DynamicGameObject;
import com.example.framework.model.GameObject;
import com.example.framework.model.Gifts;
import com.example.framework.model.HealthScore;
import com.example.framework.model.Snake;
import com.example.framework.model.SnakePart;
import com.example.framework.model.Statics;
import com.example.framework.model.WorldKingSnake;

public class LabOrientDejkstra {
	
	public static final int LAB_ORIENT_SIMPLE_MOVING = 1;
	public static final int LAB_ORIENT_SMART_MOVING = 2;
	
	static final int WORLD_WIDTH = WorldKingSnake.WORLD_WIDTH * WorldKingSnake.CELL_DIM;
	static final int WORLD_HEIGHT = WorldKingSnake.WORLD_HEIGHT * WorldKingSnake.CELL_DIM;
	
	public static final int LAB_ORIENT_EMPTY_SPACE_WEIGHT = Statics.AI.EMPTY_SPACE_WEIGHT;
	public static final int LAB_ORIENT_MAX_WEIGHT = 2000;
	public static final int LAB_ORIENT_MAX_STATIC_KOEF = 10;
	public static final int LAB_ORIENT_MAX_DYN_KOEF = 10;
	public static final int LAB_ORIENT_START_MAX_WEIGHT = 100000000;
	public static final int LAB_ORIENT_WEIGHT_GRASS = LAB_ORIENT_EMPTY_SPACE_WEIGHT;
	public static final int LAB_ORIENT_WEIGHT_SAND = 2 * LAB_ORIENT_EMPTY_SPACE_WEIGHT;
	public static final int LAB_ORIENT_WEIGHT_SWAMP = 2 * LAB_ORIENT_EMPTY_SPACE_WEIGHT;
	public static final int LAB_ORIENT_WEIGHT_WATER = 10 * LAB_ORIENT_EMPTY_SPACE_WEIGHT;
	public static final int LAB_ORIENT_WEIGHT_BOMB_FROM_TREE = 7;
	public static final int LAB_ORIENT_WEIGHT_BLUE_MUSHROOM = Statics.AI.WEIGHT_BLUE_MUSHROOM;
	public static final float LAB_ORIENT_WEIGHT_BROWN_MUSHROOM = Statics.AI.WEIGHT_BROWN_MUSHROOM;
	public static final int LAB_ORIENT_WEIGHT_BRED_MUSHROOM = Statics.AI.WEIGHT_BRED_MUSHROOM;
	public static final int LAB_ORIENT_HEDGEHOG_DEF_RADIUS = 3;
	
	public static final int LAB_ORIENT_DIR_STAY = -1;
	public static final int LAB_ORIENT_DIR_RIGHT = 6;
	public static final int LAB_ORIENT_DIR_UP = 4;
	public static final int LAB_ORIENT_DIR_LEFT = 1;
	public static final int LAB_ORIENT_DIR_DOWN = 3;
	public static final int LAB_ORIENT_DIR_UPRIGHT = 7;
	public static final int LAB_ORIENT_DIR_UPLEFT = 2;
	public static final int LAB_ORIENT_DIR_DOWNLEFT = 0;
	public static final int LAB_ORIENT_DIR_DOWNRIGHT = 5;
	public static final int LAB_ORIENT_START_RIB_DATA_INDEX = 2;
	public static final int LAB_ORIENT_RIB_DATA_SIZE = 2;
	public static final int LAB_ORIENT_RIBS_NUMBER = 8;
	public static final int LAB_ORIENT_VERT_DIST_INDEX = 0;
	public static final int LAB_ORIENT_VERT_PARENT_INDEX = 1;
	public static final int LAB_ORIENT_VERT_KEYCOORD_BIT_INDEX = 2;
	public static final int LAB_ORIENT_VERT_KEYCOORD_X_x_HEIGHT_Y_INDEX = 3;
	

    List<AdjListPointers> adjList;
	
	WorldKingSnake world;
	Vector2 newPos, tempVector;
	Random rand;
	
	int smartType;
	int wWidth;
	int wHeight;	
	int ribsNumber;		
	boolean isBordered;
	
	

    public List<VertsPointers> pathList;
	public int indexMinWeight;
    
    public LabOrientDejkstra (WorldKingSnake world)
    {
		this.world = world;
        newPos = new Vector2();
        tempVector = new Vector2(); 
        rand = new Random();
        
		wWidth = 0;
		wHeight = 0;	
		ribsNumber = 0;	
		smartType = 0;
		pathList = null;
       
		indexMinWeight = -1;
		isBordered = true;
    }
    
    public int LabOrient_GetBotMove(DynamicGameObject dynObj, int type)
    {
    	int dir = LAB_ORIENT_DIR_STAY;
    	smartType = type;
    	
		if(smartType == LAB_ORIENT_SIMPLE_MOVING)
		{
			wWidth = WorldKingSnake.WORLD_WIDTH;
			wHeight = WorldKingSnake.WORLD_HEIGHT;	
			ribsNumber = LAB_ORIENT_RIBS_NUMBER / 2;			
		}
		else if(smartType == LAB_ORIENT_SMART_MOVING)
		{
			wWidth = WORLD_WIDTH;
			wHeight = WORLD_HEIGHT;	
			ribsNumber = LAB_ORIENT_RIBS_NUMBER;
		}
    	
    	dir = LabOrient_Dejkstra(dynObj);
    	
    	return dir;
    }

	int LabOrient_Dejkstra(DynamicGameObject dynObj)
	{
	    List<VertsPointers> resList;
		resList = new ArrayList<VertsPointers>();	
		adjList = new ArrayList<AdjListPointers>();
		
		if(pathList != null)
			pathList.clear();
		pathList = null;
		
		HeapBinary.counterOperates = 0;

    	LabOrient_UpdateAdjLists(dynObj);
    	
		int xPos = 0;
		int yPos = 0;

		if(smartType == LAB_ORIENT_SIMPLE_MOVING)
		{
			xPos = (int) dynObj.position.x;
			yPos = (int) dynObj.position.y;
		}
		else if(smartType == LAB_ORIENT_SMART_MOVING)
		{
			xPos = (int) (dynObj.position.x * WorldKingSnake.CELL_DIM);
			yPos = (int) (dynObj.position.y * WorldKingSnake.CELL_DIM);			
		}
			
		
		if(xPos >= wWidth) xPos = 0;
		if(xPos < 0) xPos = wWidth - 1;		
		if(yPos >= wHeight) yPos = 0;
		if(yPos < 0) yPos = wHeight - 1;
			
		
		adjList.get(xPos * wHeight + yPos).myVert.d = 0; //start vertex distance = 0;				
		HeapBinary.buildHeap(adjList);
		
		int size = adjList.size();
		AdjListPointers vert;
		boolean relaxFinished = false;
		int newDist;
		VertsPointers vertNeigb;
		float temp, minWeight = LAB_ORIENT_START_MAX_WEIGHT;
		
		indexMinWeight = -1;
		
		Exception ee = new Exception("vertInex > adjList.size");
		
		try
		{		
			for(int i = 0; i < size && !relaxFinished; ++i)
			{
				vert = HeapBinary.extractMin(adjList);			
				
				for(int j = 0; j < ribsNumber; ++j)
				{
					vertNeigb = vert.myNeighbs[j];	
					newDist =  vert.myVert.d + (int) vert.myNeighbsLocalD[j];
					
					if(vert.myNeighbsLocalD[j] < LAB_ORIENT_MAX_WEIGHT && vertNeigb.d > newDist)
					{//if old d(v) > d(u) + w(u, v) then relax v
						
						vertNeigb.d =   newDist;	
						vertNeigb.parent = vert.myVert;
						
						
						if(vertNeigb.index >= adjList.size())
							throw ee;
						
						HeapBinary.decreaseKey(adjList, vertNeigb.index,  newDist);
					}
				}
				
				if(vert.myVert.d == LAB_ORIENT_START_MAX_WEIGHT)
					relaxFinished = true;
				
				vert.myVert.index = (short) i;	//get new index for new resList
				resList.add(vert.myVert);
				temp = (float) (vert.myVert.weight);// * Math.log(vert.myVert.d));
				
				if(temp < minWeight)
				{
					minWeight = temp;
					indexMinWeight = i;
				}
			}		
		}
		catch(Exception e)
		{
			HeapBinary.fps.logFrame("MyException:" + ee.getMessage());			
		}
		
		adjList.clear();
		adjList = null;
		
		int dir = LAB_ORIENT_DIR_STAY;

		if(resList.size() < 2)
			return dir;	
		
		//if we don't have any goal let's go anywhere!
		/*if(minWeight >= LAB_ORIENT_EMPTY_SPACE_WEIGHT)
		{
			indexMinWeight = rand.nextInt(resList.size());
			
			//we don't need our current place, let's move on at least 1 cell...
			if(indexMinWeight < 1)
				++indexMinWeight;	
		}*/
		
		//we have a goal
		pathList = new ArrayList<VertsPointers>();
		
		if(indexMinWeight > -1)
			LabOrient_GetPath(resList, pathList, indexMinWeight);
		
		if(indexMinWeight > -1)
		{
			dir = LabOrient_GetDirection(resList, indexMinWeight, dynObj);
		}
		
		resList.clear();	
		
		HeapBinary.fps.logFrame("Heap are built for" + HeapBinary.counterOperates + " operates");
		
		return dir;
	}
	
	//show path for debug only
	void LabOrient_GetPath(List<VertsPointers> resList, List<VertsPointers> pathList, int index)
	{
		if(index < 1)
			return;

		VertsPointers vert;
		int counter = 0;
		
		try{			
			while(index > 0)
			{
				vert = resList.get(index).parent;				
				pathList.add(vert);
				index = vert.index;
				++counter;
				
				if(counter > 1000)
				{
					Log.d("IN GET_PATH: ENDLESS CYCLE!!!!", "");
					break;
				}
			}				
		}
		catch(Exception e)
		{
			Log.d("NULLPOINTER Exception:", " " + index);			
		}
	}	
	
	int LabOrient_GetDirection(List<VertsPointers> resList, int index, DynamicGameObject dynObj)
	{
		int dir = LAB_ORIENT_DIR_STAY;
		int startIndex = index;
		VertsPointers vert = null, oldVert = null;
		float straightPartAngle = 1000;
		
		try{			
			if (index < 1)
				return dir;
			else if(resList.get(index).parent.index > 0)
			{
				int counter = 0;
				float xNextTurn = -1;
				float yNextTurn = -1;
				
				while(index > 0)
				{
					oldVert = vert;
					vert = resList.get(index).parent;
					index = vert.index;
					
					if(oldVert != null)
					{
						tempVector.x = ((vert.indexByBits & 0xff00) >> 8) - ((oldVert.indexByBits & 0xff00) >> 8);
						tempVector.y = (vert.indexByBits & 0x00ff) - (oldVert.indexByBits & 0x00ff);
						
						//remember our old and next turn points coords
						if(tempVector.angle() != straightPartAngle && Math.abs(tempVector.angle() - straightPartAngle) > 10)
						{
							if(smartType == LAB_ORIENT_SIMPLE_MOVING)
							{
								xNextTurn = ((oldVert.indexByBits & 0xff00) >> 8) + WorldKingSnake.CELL_SIZE / 2;
								yNextTurn = (oldVert.indexByBits & 0x00ff) + WorldKingSnake.CELL_SIZE / 2;
							}
							else if(smartType == LAB_ORIENT_SMART_MOVING)
							{
								xNextTurn = ((oldVert.indexByBits & 0xff00) >> 8) + WorldKingSnake.CELL_SIZE / (2 * WorldKingSnake.CELL_DIM);
								yNextTurn = (oldVert.indexByBits & 0x00ff) + WorldKingSnake.CELL_SIZE / (2 * WorldKingSnake.CELL_DIM);								
							}
						}
						
						straightPartAngle = tempVector.angle();
					}
										
					++counter;
					
					if(counter > 1000)
					{
						Log.d("IN GET_PATH: ENDLESS CYCLE!!!!", "");
						break;
					}
				}
				
				//remember our old and next turn points coords	
				if(xNextTurn >= 0 && yNextTurn >= 0)
				{
					if(dynObj.xNextTurn != xNextTurn || dynObj.yNextTurn != yNextTurn)
					{
						dynObj.xOldTurn = dynObj.xNextTurn;
						dynObj.yOldTurn = dynObj.yNextTurn;
					}
					dynObj.xNextTurn = xNextTurn;
					dynObj.yNextTurn = yNextTurn;
				}
				
			}
			else				
				oldVert = resList.get(index);
		
			if(oldVert != null)
			{
				short bitCoords = resList.get(0).indexByBits; 
	        	int xCur = (bitCoords & 0xff00) >> 8;
	        	int yCur = bitCoords & 0x00ff;
	        	int xNext = (oldVert.indexByBits & 0xff00) >> 8;
	        	int yNext = oldVert.indexByBits & 0x00ff;
	        	
	        	//check and process border crossing;
	        	if(Math.abs(xNext - xCur) >= wWidth / 2)
	        		if(xNext < xCur)
	        			xNext = xCur + 1;
	        		else
	        			xNext = xCur - 1;
	        	
	        	if(Math.abs(yNext - yCur) >= wHeight / 2)
	        		if(yNext < yCur)
	        			yNext = yCur + 1;
	        		else
	        			yNext = yCur - 1;
	        	
	        	
	        	if(xNext > xCur && yNext > yCur)
	        		dir = LAB_ORIENT_DIR_UPRIGHT;
	        	else if(xNext > xCur && yNext < yCur)
	        		dir = LAB_ORIENT_DIR_DOWNRIGHT;
	        	else if(xNext < xCur && yNext < yCur)
	        		dir = LAB_ORIENT_DIR_DOWNLEFT;
	        	else if(xNext < xCur && yNext > yCur)
	        		dir = LAB_ORIENT_DIR_UPLEFT;
	        	else if(xNext > xCur)
	        		dir = LAB_ORIENT_DIR_RIGHT;
	        	else if(xNext < xCur)
	        		dir = LAB_ORIENT_DIR_LEFT;
	        	else if(yNext > yCur)
	        		dir = LAB_ORIENT_DIR_UP;
	        	else if(yNext < yCur)
	        		dir = LAB_ORIENT_DIR_DOWN;
	        	
	    		if(dir == LAB_ORIENT_DIR_STAY)
	    			Log.d("WE STOPPED IN GET_DIRECTION!!!!", "xCur=" + xCur + ", yCur=" + yCur +
	    					", xNext=" + xNext + ", yNext=" + yNext);
	        	
			}			
		}
		catch(Exception e)
		{
			Log.d("NULLPOINTER Exception:", "index=" + index + ", startIndex=" + startIndex);			
		}
		
		if(dir == LAB_ORIENT_DIR_STAY)
			Log.d("WE STOPPED IN GET_DIRECTION!!!!", "index=" + index);
		
		//dir = LAB_ORIENT_DIR_STAY;
		
		return dir;
	}		
	
	
	void LabOrient_FillDynObjectWeights(DynamicGameObject dynObj, DynamicGameObject dObj)
	{
		int xCur, yCur;
		int objRadius = WorldKingSnake.CELL_DIM / 2;	
		int wWidth = WORLD_WIDTH;
		int wHeight = WORLD_HEIGHT;
		int wLevel = WorldKingSnake.WORLD_LAB_ORIENT_WEIGHTS_LEVEL;
		int mWeight = LAB_ORIENT_MAX_WEIGHT;
		
		int xPos = (int) (dObj.position.x * WorldKingSnake.CELL_DIM);
		int yPos = (int) (dObj.position.y * WorldKingSnake.CELL_DIM);	
		
		if(dObj.objType >= Statics.DynamicGameObject.BEGIN_OF_PASSIVE_DYN_OBJS_RANGE && dObj.objType <= Statics.DynamicGameObject.END_OF_PASSIVE_DYN_OBJS_RANGE)
		{//fill dynObj body with obstacle weight
			
			for(int x = - objRadius; x <= objRadius; ++x)
			{
				xCur = xPos + x;
				if(xCur >= wWidth)
					xCur = 0;
				if(xCur < 0)
					xCur = wWidth - 1;
				
				for(int y = - objRadius; y <= objRadius; ++y)
				{
					yCur = yPos + y;					
					
					if(yCur >= wHeight)
						yCur = 0;
					if(yCur < 0)
						yCur = wHeight - 1;
					
					if(world.fields[wLevel][xCur][yCur] < mWeight)
						world.fields[wLevel][xCur][yCur] *= (float)(1 + 8 * dObj.stateHS.density / dynObj.stateHS.density);					
				}	
			}			
		}
		else if(dObj.objType >= Statics.DynamicGameObject.BEGIN_OF_ACTIVE_DYN_OBJS_RANGE && dObj.objType <= Statics.DynamicGameObject.END_OF_ACTIVE_DYN_OBJS_RANGE)
		{//fill dynObj body with active obstacle (bombs etc..) weight
			
			if(dObj.objType >= Statics.DynamicGameObject.BEGIN_OF_BOMBS_RANGE && dObj.objType <= Statics.DynamicGameObject.END_OF_BOMBS_RANGE)
			{
				if(world.fields[wLevel][xPos][yPos] < mWeight)
				{					
					switch(dObj.objType)
					{
					case DynamicGameObject.BOMB_FROM_TREE:
						float damage = Gifts.BOMB_FROM_TREE_DAMAGE * dynObj.stateHS.defencePower;
						if(damage >= dynObj.stateHS.health)
							world.fields[wLevel][xPos][yPos] = mWeight;
						else
							world.fields[wLevel][xPos][yPos] *= LAB_ORIENT_WEIGHT_BOMB_FROM_TREE * (dynObj.stateHS.health / (dynObj.stateHS.health - damage));
						break;
					}
				}
			}			
		}
		else if(dObj.objType >= Statics.DynamicGameObject.BEGIN_OF_CHARACTERS_RANGE && dObj.objType <= Statics.DynamicGameObject.END_OF_CHARACTERS_RANGE)
		{//fill CharObj body fields by coins weight;	
			
			float weightAttack = LabOrient_GetDynObjAttackWeight(dynObj, dObj);	
			if(weightAttack > 10)
				weightAttack = 10;
						
			float dObjCostWeight = 1 / LabOrient_GetDynObjCoinsWeight(dynObj, dObj, weightAttack);
			
			for(int x = - objRadius; x <= objRadius; ++x)
			{
				for(int y = - objRadius; y <= objRadius; ++y)
				{
					xCur = xPos + x;
					yCur = yPos + y;
					
					if(xCur >= wWidth)
						xCur = 0;
					if(xCur < 0)
						xCur = wWidth - 1;
					
					if(yCur >= wHeight)
						yCur = 0;
					if(yCur < 0)
						yCur = wHeight - 1;
					
					if(world.fields[wLevel][xCur][yCur] < mWeight)
						world.fields[wLevel][xCur][yCur] *= dObjCostWeight;					
				}	
			}
			
			//fill dObj around by coins weights
			float weightK = dObjCostWeight;			
			int radius = (int) Math.sqrt(Math.abs(LAB_ORIENT_EMPTY_SPACE_WEIGHT * weightK - LAB_ORIENT_EMPTY_SPACE_WEIGHT));
			int totalRadius = radius + objRadius;
			
			for(int x = -totalRadius; x <= totalRadius; ++x)
			{
				for(int y = -totalRadius; y <= totalRadius; ++y)
				{
					if(Math.abs(x) > objRadius || Math.abs(y) > objRadius)
					{
						xCur = xPos + x;
						yCur = yPos + y;
						
						if(xCur >= wWidth)
							xCur = 0;
						if(xCur < 0)
							xCur = wWidth - 1;
						
						if(yCur >= wHeight)
							yCur = 0;
						if(yCur < 0)
							yCur = wHeight - 1;
						
						if(world.fields[wLevel][xCur][yCur] < LAB_ORIENT_MAX_WEIGHT)	
							world.fields[wLevel][xCur][yCur] *= (float) (Math.pow((Math.max(Math.abs(x) - objRadius, Math.abs(y) - objRadius)), 2)/LAB_ORIENT_EMPTY_SPACE_WEIGHT + weightK);
					}			
				}
			}
			
			//fill object around with attack/defense weights			
			radius = (int) Math.sqrt(Math.abs(LAB_ORIENT_EMPTY_SPACE_WEIGHT * weightAttack - LAB_ORIENT_EMPTY_SPACE_WEIGHT));
			totalRadius = radius + objRadius;
			
			if(dObj.objType == DynamicGameObject.HEDGEHOG)
			{//if HG everything is complicated. He defended from 3 sides, except his stomach
				radius = LAB_ORIENT_HEDGEHOG_DEF_RADIUS;
				totalRadius = radius + objRadius;
				
				for(int x = -totalRadius; x <= totalRadius; ++x)
				{
					for(int y = -totalRadius; y <= totalRadius; ++y)
					{
						xCur = xPos + x;
						yCur = yPos + y;
						
						if(xCur >= wWidth)
							xCur = 0;
						if(xCur < 0)
							xCur = wWidth - 1;
						
						if(yCur >= wHeight)
							yCur = 0;
						if(yCur < 0)
							yCur = wHeight - 1;
						
						if(((int) dObj.velocity.angle() == 0 || (int)dObj.velocity.angle() == 180) && y > -objRadius && Math.abs(x) > objRadius)
						{// RIGHT and LEFT						
							if(world.fields[wLevel][xCur][yCur] < mWeight)
								world.fields[wLevel][xCur][yCur] *= (weightAttack - Math.pow(Math.max(Math.abs(x) - objRadius, Math.abs(y) - objRadius), 2)/LAB_ORIENT_EMPTY_SPACE_WEIGHT);
						}
						else if(((int) dObj.velocity.angle() == 90 || (int)dObj.velocity.angle() == 270) && x < objRadius && Math.abs(y) > objRadius)
						{//UP and DOWN						
							if(world.fields[wLevel][xCur][yCur] < mWeight)
								world.fields[wLevel][xCur][yCur] *= (weightAttack - Math.pow(Math.max(Math.abs(x) - objRadius, Math.abs(y) - objRadius), 2)/LAB_ORIENT_EMPTY_SPACE_WEIGHT);
						}
						else if(((int) dObj.velocity.angle() == 45 || (int)dObj.velocity.angle() == 225) && (y > objRadius || x < -objRadius))
						{//RIGHT-UP	and LEFT-DOWN				
							if(world.fields[wLevel][xCur][yCur] < mWeight)
								world.fields[wLevel][xCur][yCur] *= (weightAttack - Math.pow(Math.max(Math.abs(x) - objRadius, Math.abs(y) - objRadius), 2)/LAB_ORIENT_EMPTY_SPACE_WEIGHT);
						}
						else if(((int) dObj.velocity.angle() == 135 || (int)dObj.velocity.angle() == 315) && (x > objRadius || y > objRadius))
						{//UP-LEFT and RIGHT-DOWN					
							if(world.fields[wLevel][xCur][yCur] < mWeight)
								world.fields[wLevel][xCur][yCur] *= (weightAttack - Math.pow(Math.max(Math.abs(x) - objRadius, Math.abs(y) - objRadius), 2)/LAB_ORIENT_EMPTY_SPACE_WEIGHT);
						}
					}
				}						
			}
			else if(dObj.velocity.len() > dynObj.velocity.len())
			{//attack 360`, no direction as dObj is faster	
				for(int x = -totalRadius; x <= totalRadius; ++x)
				{
					for(int y = -totalRadius; y <= totalRadius; ++y)
					{
						if(Math.abs(x) > objRadius || Math.abs(y) > objRadius)
						{
							xCur = xPos + x;
							yCur = yPos + y;
							
							if(xCur >= wWidth)
								xCur = 0;
							if(xCur < 0)
								xCur = wWidth - 1;
							
							if(yCur >= wHeight)
								yCur = 0;
							if(yCur < 0)
								yCur = wHeight - 1;
							
							if(world.fields[wLevel][xCur][yCur] < mWeight)
								world.fields[wLevel][xCur][yCur] *= (weightAttack - Math.pow(Math.max(Math.abs(x) - objRadius, Math.abs(y) - objRadius), 2)/LAB_ORIENT_EMPTY_SPACE_WEIGHT);
						}			
					}
				}			
			}
			else
			{//attack weights has vector
						
				for(int x = -totalRadius; x <= totalRadius; ++x)
				{
					for(int y = -totalRadius; y <= totalRadius; ++y)
					{
						xCur = xPos + x;
						yCur = yPos + y;
						
						if(xCur >= wWidth)
							xCur = 0;
						if(xCur < 0)
							xCur = wWidth - 1;
						
						if(yCur >= wHeight)
							yCur = 0;
						if(yCur < 0)
							yCur = wHeight - 1;
						
						if(world.fields[wLevel][xCur][yCur] < mWeight)
						{						
							if((int) dObj.velocity.angle() == 0 && x > objRadius && x >= Math.abs(y))
							// RIGHT						
								world.fields[wLevel][xCur][yCur] *= (weightAttack - Math.pow(Math.max(Math.abs(x) - objRadius, Math.abs(y) - objRadius), 2)/LAB_ORIENT_EMPTY_SPACE_WEIGHT);							
							else if((int) dObj.velocity.angle() == 90 && y > objRadius && y >= Math.abs(x))
							//UP						
								world.fields[wLevel][xCur][yCur] *= (weightAttack - Math.pow(Math.max(Math.abs(x) - objRadius, Math.abs(y) - objRadius), 2)/LAB_ORIENT_EMPTY_SPACE_WEIGHT);							
							else if((int) dObj.velocity.angle() == 180 && x < objRadius && Math.abs(x) >= Math.abs(y))
							//LEFT						
								world.fields[wLevel][xCur][yCur] *= (weightAttack - Math.pow(Math.max(Math.abs(x) - objRadius, Math.abs(y) - objRadius), 2)/LAB_ORIENT_EMPTY_SPACE_WEIGHT);						
							else if((int) dObj.velocity.angle() == 270 && y < objRadius && Math.abs(y) >= Math.abs(x))
							//DOWN					
								world.fields[wLevel][xCur][yCur] *= (weightAttack - Math.pow(Math.max(Math.abs(x) - objRadius, Math.abs(y) - objRadius), 2)/LAB_ORIENT_EMPTY_SPACE_WEIGHT);							
							else if((int) dObj.velocity.angle() == 45 && (x > objRadius || y > objRadius))
							//RIGHT-UP					
								world.fields[wLevel][xCur][yCur] *= (weightAttack - Math.pow(Math.max(Math.abs(x) - objRadius, Math.abs(y) - objRadius), 2)/LAB_ORIENT_EMPTY_SPACE_WEIGHT);							
							else if((int) dObj.velocity.angle() == 135 && (x < -objRadius || y > objRadius))
							//UP-LEFT					
								world.fields[wLevel][xCur][yCur] *= (weightAttack - Math.pow(Math.max(Math.abs(x) - objRadius, Math.abs(y) - objRadius), 2)/LAB_ORIENT_EMPTY_SPACE_WEIGHT);							
							else if((int) dObj.velocity.angle() == 225 && (x < -objRadius || y < -objRadius))
							//LEFT-DOWN					
								world.fields[wLevel][xCur][yCur] *= (weightAttack - Math.pow(Math.max(Math.abs(x) - objRadius, Math.abs(y) - objRadius), 2)/LAB_ORIENT_EMPTY_SPACE_WEIGHT);							
							else if((int) dObj.velocity.angle() == 315 && (x > objRadius || y < -objRadius))
							//RIGHT-DOWN					
								world.fields[wLevel][xCur][yCur] *= (weightAttack - Math.pow(Math.max(Math.abs(x) - objRadius, Math.abs(y) - objRadius), 2)/LAB_ORIENT_EMPTY_SPACE_WEIGHT);							
						}
					}
				}			
			}		

		}	
	}
	
	void LabOrient_FillDynObjectWeightsSimple(DynamicGameObject dynObj, DynamicGameObject dObj)
	{
		int xCur, yCur;
		int wWidth = WORLD_WIDTH;
		int wHeight = WORLD_HEIGHT;
		int dim = WorldKingSnake.CELL_DIM;
		int objRadius = WorldKingSnake.CELL_DIM / 2;	
		int wLevel = WorldKingSnake.WORLD_LAB_ORIENT_WEIGHTS_LEVEL;
		int mWeight = LAB_ORIENT_MAX_WEIGHT;
		
		int xPos = (int) (dObj.position.x * WorldKingSnake.CELL_DIM);
		int yPos = (int) (dObj.position.y * WorldKingSnake.CELL_DIM);		
		
		if(dObj.objType >= Statics.DynamicGameObject.BEGIN_OF_PASSIVE_DYN_OBJS_RANGE && dObj.objType <= Statics.DynamicGameObject.END_OF_PASSIVE_DYN_OBJS_RANGE)
		{//fill dynObj body with obstacle weight

			for(int x = - objRadius; x <= objRadius; ++x)
			{
				for(int y = - objRadius; y <= objRadius; ++y)
				{								
					xCur = ((xPos + x) / dim) * dim;
					yCur = ((yPos + y) / dim) * dim;
					
					if(xCur >= wWidth)
						xCur = 0;
					if(xCur < 0)
						xCur = wWidth - 1;
					
					if(yCur >= wHeight)
						yCur = 0;
					if(yCur < 0)
						yCur = wHeight - 1;
					
					if(world.fields[wLevel][xCur][yCur] < mWeight)
					{
						if(world.fields[wLevel][xCur][yCur] < 65000)
							world.fields[wLevel][xCur][yCur] <<= 16;	//hold our value in higher bites if it hasn't been done yet
						
						int oldVal = world.fields[wLevel][xCur][yCur] & 0xffff0000;	
						oldVal >>= 16;
						world.fields[wLevel][xCur][yCur] += oldVal * (1 + 8 * dObj.stateHS.density / dynObj.stateHS.density);	
					}
				}
			}
			
			//clear our high bytes;
			for(int x = - objRadius; x <= objRadius; ++x)
			{
				for(int y = - objRadius; y <= objRadius; ++y)
				{			
					xCur = ((xPos + x) / dim) * dim;
					yCur = ((yPos + y) / dim) * dim;
					
					if(xCur >= wWidth)
						xCur = 0;
					if(xCur < 0)
						xCur = wWidth - 1;
					
					if(yCur >= wHeight)
						yCur = 0;
					if(yCur < 0)
						yCur = wHeight - 1;
					
					world.fields[wLevel][xCur][yCur] &= 0x0000ffff;					
				}
			}			
		}
		else if(dObj.objType >= Statics.DynamicGameObject.BEGIN_OF_CHARACTERS_RANGE && dObj.objType <= Statics.DynamicGameObject.END_OF_CHARACTERS_RANGE)
		{//fill dynObj body fields by coins weight;	
			
			float weightAttack = LabOrient_GetDynObjAttackWeight(dynObj, dObj);
			
			if(weightAttack > 10)
				weightAttack = 10;
						
			float dObjCostWeight = 1 / LabOrient_GetDynObjCoinsWeight(dynObj, dObj, weightAttack);
			
			xCur = (xPos / dim) * dim;
			yCur = (yPos / dim) * dim;
					
			if(world.fields[wLevel][xCur][yCur] < mWeight)
				world.fields[wLevel][xCur][yCur] *= dObjCostWeight;					
			
			//fill object around with attack/defense weights			
			int radius = 1;
			
			if(dObj.objType == DynamicGameObject.HEDGEHOG)
			{//if HG everything is complicated. He defended from 3 sides, except his stomach
				
				for(int x = -radius; x <= radius; ++x)
				{
					for(int y = -radius; y <= radius; ++y)
					{
						xCur = (xPos / dim) * dim + x * dim;
						yCur = (yPos / dim) * dim + y * dim;
						
						if(xCur >= wWidth)
							xCur = 0;
						if(xCur < 0)
							xCur = wWidth - 1;
						
						if(yCur >= wHeight)
							yCur = 0;
						if(yCur < 0)
							yCur = wHeight - 1;
						
						if(world.fields[wLevel][xCur][yCur] < mWeight)
						{						
							if(((int) dObj.velocity.angle() == 0 || (int)dObj.velocity.angle() == 180) && y > -objRadius && Math.abs(x) > objRadius)
							// RIGHT and LEFT						
									world.fields[wLevel][xCur][yCur] *= weightAttack;						
							else if(((int) dObj.velocity.angle() == 90 || (int)dObj.velocity.angle() == 270) && x < objRadius && Math.abs(y) > objRadius)
							//UP and DOWN						
								world.fields[wLevel][xCur][yCur] *= weightAttack;						
							else if(((int) dObj.velocity.angle() == 45 || (int)dObj.velocity.angle() == 225) && (y > objRadius || x < -objRadius))
							//RIGHT-UP	and LEFT-DOWN				
								world.fields[wLevel][xCur][yCur] *= weightAttack;						
							else if(((int) dObj.velocity.angle() == 135 || (int)dObj.velocity.angle() == 315) && (x > objRadius || y > objRadius))
							//UP-LEFT and RIGHT-DOWN					
								world.fields[wLevel][xCur][yCur] *= weightAttack;						
						}
					}
				}						
			}
			else
			{//attack weights has vector
						
				for(int x = -radius; x <= radius; ++x)
				{
					for(int y = -radius; y <= radius; ++y)
					{
						xCur = (xPos / dim) * dim + x * dim;
						yCur = (yPos / dim) * dim + y * dim;
						
						if(xCur >= wWidth)
							xCur = 0;
						if(xCur < 0)
							xCur = wWidth - 1;
						
						if(yCur >= wHeight)
							yCur = 0;
						if(yCur < 0)
							yCur = wHeight - 1;
						
						int angle = (int) dObj.velocity.angle();
						
						if(world.fields[wLevel][xCur][yCur] < mWeight)
						{												
							if((int) angle == 0 && x > 0)
							{
							// RIGHT	
								world.fields[wLevel][xCur][yCur] *= weightAttack;
							}
							else if((int) angle == 90 && y > 0)
							//UP						
								world.fields[wLevel][xCur][yCur] *= weightAttack;
							else if((int) angle == 180 && x < 0)
							//LEFT						
								world.fields[wLevel][xCur][yCur] *= weightAttack;
							else if((int) angle == 270 && y < 0)
							//DOWN					
								world.fields[wLevel][xCur][yCur] *= weightAttack;
							else if((int) angle == 45 && (x > 0 || y > 0))
							//RIGHT-UP					
								world.fields[wLevel][xCur][yCur] *= weightAttack;
							else if((int) angle == 135 && (x < 0 || y > 0))
							//UP-LEFT					
								world.fields[wLevel][xCur][yCur] *= weightAttack;
							else if((int) angle == 225 && (x < 0 || y < 0))
							//LEFT-DOWN					
								world.fields[wLevel][xCur][yCur] *= weightAttack;
							else if((int) angle == 315 && (x > 0 || y < 0))
							//RIGHT-DOWN					
								world.fields[wLevel][xCur][yCur] *= weightAttack;
						}
					}
				}			
			}		
		}	
	}

	
	void LabOrient_FillAdjRibs(int xPos, int yPos)
	{
		int wLevel = WorldKingSnake.WORLD_LAB_ORIENT_WEIGHTS_LEVEL;
		
		AdjListPointers adjVerts = adjList.get(xPos * wHeight + yPos);
		//adjVerts[0] = LAB_ORIENT_MAX_WEIGHT;	//initialization distance
		//adjVerts[1] = -1;	//initialization parent
		
		int objRadius = WorldKingSnake.CELL_DIM / 2;
		int xCur, yCur, index = 0;	
		
		adjVerts.myVert.weight = (short) world.fields[wLevel][xPos][yPos];
		
		for(int x = -objRadius; x <= objRadius; ++x)
		{
			for(int y = -objRadius; y <= objRadius; ++y)
			{
				if(x != 0 || y != 0)
				{
					xCur = xPos + x;
					yCur = yPos + y;
					
					if(xCur >= wWidth)
						xCur = 0;
					if(xCur < 0)
						xCur = wWidth - 1;
					
					if(yCur >= wHeight)
						yCur = 0;
					if(yCur < 0)
						yCur = wHeight - 1;					
									
					adjVerts.myNeighbs[index] = adjList.get(xCur * wHeight + yCur).myVert;	//pointer of vertex connected to rib
					adjVerts.myNeighbsLocalD[index] = (short) LabOrient_GetAdjRib(xCur, yCur, index);	//weight of rib considering direction					
					++index;
				}
			}
		}			 				
	}
	
	void LabOrient_FillAdjRibsSimple(int xPos, int yPos)
	{
		int wLevel = WorldKingSnake.WORLD_LAB_ORIENT_WEIGHTS_LEVEL;
		int dim = WorldKingSnake.CELL_DIM;
		
		AdjListPointers adjVerts = adjList.get(xPos * wHeight + yPos);
		//adjVerts[0] = LAB_ORIENT_MAX_WEIGHT;	//initialization distance
		//adjVerts[1] = -1;	//initialization parent
		
		int objRadius = WorldKingSnake.CELL_DIM / 2;
		int xCur, yCur, index = 0;	
		
		adjVerts.myVert.weight = (short) world.fields[wLevel][xPos * dim][yPos * dim];
		
		for(int x = -objRadius; x <= objRadius; ++x)
		{
			for(int y = -objRadius; y <= objRadius; ++y)
			{
				if(x != 0 || y != 0)
				{//only left-right up-down directions 
					if(Math.abs(x) + Math.abs(y) <= objRadius)
					{
						xCur = xPos + x;
						yCur = yPos + y;
						
						if(xCur >= wWidth)
							xCur = 0;
						if(xCur < 0)
							xCur = wWidth - 1;
						
						if(yCur >= wHeight)
							yCur = 0;
						if(yCur < 0)
							yCur = wHeight - 1;					
										
						adjVerts.myNeighbs[index] = adjList.get(xCur * wHeight + yCur).myVert;	//pointer of vertex connected to rib
						adjVerts.myNeighbsLocalD[index] = (short) world.fields[wLevel][xPos * dim][yPos * dim];	
						++index;
					}
				}
			}
		}			 				
	}
	
	
	int LabOrient_GetAdjRib(int xPos, int yPos, int dir)
	{
		int wLevel = WorldKingSnake.WORLD_LAB_ORIENT_WEIGHTS_LEVEL;
		int xCur, yCur, weight = 0;
		
		if(dir == LAB_ORIENT_DIR_RIGHT )
		{
			xCur = xPos + 1;
			yCur = yPos;
			
			if(xCur >= wWidth)
				xCur = 0;
			
			weight += world.fields[wLevel][xCur][yCur];
			
			yCur = yPos + 1;
			
			if(yCur >= wHeight)
				yCur = 0;
			
			weight += world.fields[wLevel][xCur][yCur];
			
			yCur = yPos - 1;
			
			if(yCur < 0)
				yCur = wHeight - 1;
			
			weight += world.fields[wLevel][xCur][yCur];
		}
		else if(dir == LAB_ORIENT_DIR_UP)	
		{
			xCur = xPos;
			yCur = yPos + 1;
			
			if(yCur >= wHeight)
				yCur = 0;
			
			weight += world.fields[wLevel][xCur][yCur];
			
			xCur = xPos + 1;
			
			if(xCur >= wWidth)
				xCur = 0;
			
			weight += world.fields[wLevel][xCur][yCur];
			
			xCur = xPos - 1;
			
			if(xCur < 0)
				xCur = wWidth - 1;
			
			weight += world.fields[wLevel][xCur][yCur];			
		}
		else if(dir == LAB_ORIENT_DIR_LEFT)	
		{
			xCur = xPos - 1;
			yCur = yPos;
			
			if(xCur < 0)
				xCur = wWidth - 1;
			
			weight += world.fields[wLevel][xCur][yCur];
			
			yCur = yPos + 1;
			
			if(yCur >= wHeight)
				yCur = 0;
			
			weight += world.fields[wLevel][xCur][yCur];
			
			yCur = yPos - 1;
			
			if(yCur < 0)
				yCur = wHeight - 1;
			
			weight += world.fields[wLevel][xCur][yCur];
		}
		else if(dir == LAB_ORIENT_DIR_DOWN)	
		{
			xCur = xPos;
			yCur = yPos - 1;
			
			if(yCur < 0)
				yCur = wHeight - 1;
			
			weight += world.fields[wLevel][xCur][yCur];
			
			xCur = xPos + 1;
			
			if(xCur >= wWidth)
				xCur = 0;
			
			weight += world.fields[wLevel][xCur][yCur];
			
			xCur = xPos - 1;
			
			if(xCur < 0)
				xCur = wWidth - 1;
			
			weight += world.fields[wLevel][xCur][yCur];			
		}
		else if(dir == LAB_ORIENT_DIR_UPRIGHT)		
		{
			xCur = xPos;
			yCur = yPos + 1;
			
			if(yCur >= wHeight)
				yCur = 0;
			
			weight += world.fields[wLevel][xCur][yCur];
			
			xCur = xPos - 1;
			
			if(xCur < 0)
				xCur = wWidth - 1;
			
			weight += world.fields[wLevel][xCur][yCur];	
			
			xCur = xPos + 1;
			
			if(xCur >= wWidth)
				xCur = 0;
			
			weight += world.fields[wLevel][xCur][yCur];
			
			
			yCur = yPos;					
			weight += world.fields[wLevel][xCur][yCur];
			
			yCur = yPos - 1;
			
			if(yCur < 0)
				yCur = wHeight - 1;
			
			weight += world.fields[wLevel][xCur][yCur];								
		}
		else if(dir == LAB_ORIENT_DIR_UPLEFT)
		{
			xCur = xPos;
			yCur = yPos + 1;
			
			if(yCur >= wHeight)
				yCur = 0;
			
			weight += world.fields[wLevel][xCur][yCur];
			
			xCur = xPos + 1;
			
			if(xCur >= wWidth)
				xCur = 0;
			
			weight += world.fields[wLevel][xCur][yCur];
			
			xCur = xPos - 1;
			
			if(xCur < 0)
				xCur = wWidth - 1;
			
			weight += world.fields[wLevel][xCur][yCur];	
			
			yCur = yPos;
			weight += world.fields[wLevel][xCur][yCur];
			
			yCur = yPos - 1;
			
			if(yCur < 0)
				yCur = wHeight - 1;
			
			weight += world.fields[wLevel][xCur][yCur];			
		}
		else if(dir == LAB_ORIENT_DIR_DOWNLEFT)	
		{
			xCur = xPos;
			yCur = yPos - 1;
			
			if(yCur < 0)
				yCur = wHeight - 1;
			
			weight += world.fields[wLevel][xCur][yCur];
			
			xCur = xPos + 1;
			
			if(xCur >= wWidth)
				xCur = 0;
			
			weight += world.fields[wLevel][xCur][yCur];
			
			xCur = xPos - 1;
			
			if(xCur < 0)
				xCur = wWidth - 1;
			
			weight += world.fields[wLevel][xCur][yCur];		
					
			yCur = yPos;		
			weight += world.fields[wLevel][xCur][yCur];
			
			yCur = yPos + 1;
			
			if(yCur >= wHeight)
				yCur = 0;
			
			weight += world.fields[wLevel][xCur][yCur];
		}
		else if(dir == LAB_ORIENT_DIR_DOWNRIGHT)	
		{
			xCur = xPos;
			yCur = yPos - 1;
			
			if(yCur < 0)
				yCur = wHeight - 1;
			
			weight += world.fields[wLevel][xCur][yCur];
			
			xCur = xPos - 1;
			
			if(xCur < 0)
				xCur = wWidth - 1;
			
			weight += world.fields[wLevel][xCur][yCur];	
			
			xCur = xPos + 1;
			
			if(xCur >= wWidth)
				xCur = 0;
			
			weight += world.fields[wLevel][xCur][yCur];
					
			yCur = yPos;		
			weight += world.fields[wLevel][xCur][yCur];
			
			yCur = yPos + 1;
			
			if(yCur >= wHeight)
				yCur = 0;
			
			weight += world.fields[wLevel][xCur][yCur];
		}	
		return weight;
	}

	void LabOrient_FillReliefWeights(DynamicGameObject dynObj)
	{	
		int wLevel = WorldKingSnake.WORLD_LAB_ORIENT_WEIGHTS_LEVEL;
		int dim = WorldKingSnake.CELL_DIM;
		
		if(smartType == LAB_ORIENT_SIMPLE_MOVING)
		{
	        for(int i=0; i < wWidth; ++i)
	        	for(int j=0; j < wHeight; ++j)
	        			world.fields[wLevel][i * dim][j * dim] = LabOrient_GetReliefWeight(dynObj, i, j);
		}
		else if(smartType == LAB_ORIENT_SMART_MOVING)
		{
	        for(int i=0; i < wWidth; ++i)
	        	for(int j=0; j < wHeight; ++j)
	        			world.fields[wLevel][i][j] = LabOrient_GetReliefWeight(dynObj, i, j);
	        
	        //not correct, only for DEBUGGING!!! DELETE AFTER!!!
	        if(isBordered)
	        {
	        	for(int i=0; i < wWidth; ++i)
	        	{
        			world.fields[wLevel][i][0] = LAB_ORIENT_MAX_WEIGHT;	
        			world.fields[wLevel][i][wHeight - 1] = LAB_ORIENT_MAX_WEIGHT;	
	        	}
	        	for(int i=0; i < wHeight; ++i)
	        	{
        			world.fields[wLevel][0][i] = LAB_ORIENT_MAX_WEIGHT;	
        			world.fields[wLevel][wWidth - 1][i] = LAB_ORIENT_MAX_WEIGHT;	
	        	}
	        }
		}
	}
	
	void LabOrient_FillDynObjsWeights(DynamicGameObject dynObj)
	{		
		int size = world.dynObjects.size();
		DynamicGameObject dObj;	
		
		for(int i=0; i<size; ++i)
		{
			dObj = world.dynObjects.get(i);
			
			//simple, not important objects, like dead parts
			if(dObj.stateHS == null || dObj.objType == DynamicGameObject.DEAD_PART_VISIBLE_STILL)
				continue;			
			
			if((dynObj.stateHS.frendByRace == true && (dObj.objType == dynObj.objType)) || 
					(dynObj.stateHS.frendToGamer > 0 && (!dObj.stateHS.isBot || dynObj.stateHS.frendToGamer == dObj.stateHS.frendToGamer)) ||
					(dynObj.stateHS.frendtoAlienRace == dObj.objType)
					)
					continue;	//friend		
				
			if(dynObj.equals(dObj))
				continue;	//me				
							
			if(smartType == LAB_ORIENT_SIMPLE_MOVING)
				LabOrient_FillDynObjectWeightsSimple(dynObj, dObj);
			else //smart
				LabOrient_FillDynObjectWeights(dynObj,dObj);
		}
		
		//add snake's body to weights map to avoid stacks
		if(dynObj.objType == Statics.DynamicGameObject.SNAKE)
			LabOrient_FillDynObjectBodyWeights(dynObj);			
	}
	
	void LabOrient_FillDynObjectBodyWeights(DynamicGameObject dynObj)
	{
		if(dynObj.objType == Statics.DynamicGameObject.SNAKE)
		{
			Snake snake = (Snake) dynObj;
			int size = snake.parts.size();
			
			int xCur, yCur;
			int objRadius = WorldKingSnake.CELL_DIM / 2;	
			int wWidth = WORLD_WIDTH;
			int wHeight = WORLD_HEIGHT;
			int wLevel = WorldKingSnake.WORLD_LAB_ORIENT_WEIGHTS_LEVEL;
			int mWeight = LAB_ORIENT_MAX_WEIGHT;
			
			for(int i = 1; i < size; ++i)
			{
				SnakePart sPart = snake.parts.get(i);
				
				int xPos = (int) (sPart.position.x * WorldKingSnake.CELL_DIM);
				int yPos = (int) (sPart.position.y * WorldKingSnake.CELL_DIM);	
			
				for(int x = - objRadius; x <= objRadius; ++x)
				{
					for(int y = - objRadius; y <= objRadius; ++y)
					{
						xCur = xPos + x;
						yCur = yPos + y;
							
						if(xCur >= wWidth)
							xCur = 0;
						if(xCur < 0)
							xCur = wWidth - 1;
							
						if(yCur >= wHeight)
							yCur = 0;
						if(yCur < 0)
							yCur = wHeight - 1;
							
						if(world.fields[wLevel][xCur][yCur] < mWeight)
							world.fields[wLevel][xCur][yCur] *= (float)(1 + 8 * dynObj.stateHS.density);					
					}	
				}			
			}
		}
	}
	
	
	float LabOrient_GetDynObjCoinsWeight(DynamicGameObject dynObj, DynamicGameObject dObj, float weightAttack)
	{
		//rating based on cost of health: health 100 costs 100coins. from 0.1 to 10
		float coins = 1 + (dObj.stateHS.cost / (dynObj.stateHS.defencePower * Gifts.APPLE_HEALTH_INCREASE));
		
		//then provide politics for every character
		if(dynObj.objType == DynamicGameObject.LEMMING)
		{
			switch(dynObj.stateHS.level)
			{
			case HealthScore.LEVEL_PINK:	//lemmy haotic 
				coins = 1;
				break;
			case HealthScore.LEVEL_GREEN:	//lemmy simple deykstra avoids enemies by default
				coins = 1;
				break;
			case HealthScore.LEVEL_BLUE:	//lemmy-viking simple deykstra choose enemies by ratio coins / power
				coins /= weightAttack;
				break;
			case HealthScore.LEVEL_YELLOW:	//lemmy-berserker smart deykstra choose the most powerful enemies. divide on 2 to have opportunity to go for apple if we hurt too much
				coins = weightAttack / 2;
				break;
			case HealthScore.LEVEL_ORANGE:	//lemmy-spy smart deykstra avoids enemies always
				coins = 0.1f;
				break;
			case HealthScore.LEVEL_RED:	//lemmy-konung smart deykstra choose the gamer as the enemy
				coins = 1;
				break;
				
			}					
		}
		else if(dynObj.objType == DynamicGameObject.SNAKE)
		{
			switch(dynObj.stateHS.level)
			{
			case HealthScore.LEVEL_PINK:	//snake smart deykstra choose enemies by ratio coins / power
				coins /= weightAttack;
				break;				
			}			
		}
		
		
		return coins;
	}
	
	float LabOrient_GetDynObjAttackWeight(DynamicGameObject dynObj, DynamicGameObject dObj)
	{
		float dObjWeight = 0;
		float dynDefPower = dynObj.stateHS.defencePower;
		float dynAttackPower = dynObj.stateHS.attackPower;
		float dDefPower = dObj.stateHS.defencePower;
		float dAttackPower = dObj.stateHS.attackPower;
		
		if(dynDefPower == 0)
			dynDefPower = 0.1f;
		if(dynAttackPower == 0)
			dynAttackPower = 0.1f;
		if(dDefPower == 0)
			dDefPower = 0.1f;
		if(dAttackPower == 0)
			dAttackPower = 0.1f;
		
		float attacksNumberAlife = dynObj.stateHS.health / (dAttackPower * dAttackPower /
				(2 * dynDefPower));
		
		if(attacksNumberAlife <= 1) //enemy can kill us with 1 hit 
			dObjWeight = LAB_ORIENT_MAX_WEIGHT;
		else
		{
			float attacksNumberAlifeEnemy = dObj.stateHS.health / (dynAttackPower * dynAttackPower /
					(2 * dDefPower));
			
			if(attacksNumberAlife / attacksNumberAlifeEnemy < 1) //enemy is stronger 
				dObjWeight = LAB_ORIENT_MAX_WEIGHT;
			else
				dObjWeight =  1 / ((attacksNumberAlife - attacksNumberAlifeEnemy) / attacksNumberAlife);
		}
				
		return dObjWeight;
	}

	
	void LabOrient_FillStaticWeights(DynamicGameObject dynObj)
	{		
		int size = world.statObjects.size();
		GameObject gObj;
		int x, y;
		int wLevel = WorldKingSnake.WORLD_LAB_ORIENT_WEIGHTS_LEVEL;
		int mWeight = LAB_ORIENT_MAX_WEIGHT;
		
		for(int i=0; i<size; ++i)
		{
			gObj = world.statObjects.get(i);
			if(gObj.type ==  DynamicGameObject.BOMB_FROM_TREE)
			{
				x = (int)((gObj.position.x) * WorldKingSnake.CELL_DIM);
				y = (int)((gObj.position.y) * WorldKingSnake.CELL_DIM);
			}
			else
			{
				x = ((int)gObj.position.x) * WorldKingSnake.CELL_DIM + WorldKingSnake.CELL_DIM / 2;
				y = ((int)gObj.position.y) * WorldKingSnake.CELL_DIM + WorldKingSnake.CELL_DIM / 2;				
			}
			
			switch(gObj.type)
			{
			case GameObject.APPLE:			
				LabOrient_FillStatObjWeights(x, y, 1 / LabOrient_GetAppleWeight(dynObj));
				break;
			case GameObject.MUSHROOM_YELLOW:
				LabOrient_FillStatObjWeights(x, y, 1 / LabOrient_GetYellowMWeight(dynObj));
				break;
			case GameObject.MUSHROOM_BLUE:
				LabOrient_FillStatObjWeights(x, y, 1 / LAB_ORIENT_WEIGHT_BLUE_MUSHROOM);
				break;
			case GameObject.MUSHROOM_BROWN:
				LabOrient_FillStatObjWeights(x, y, LAB_ORIENT_WEIGHT_BROWN_MUSHROOM);
				break;
			case GameObject.MUSHROOM_BLUE_RED:
				LabOrient_FillStatObjWeights(x, y, 1 / LAB_ORIENT_WEIGHT_BRED_MUSHROOM);
				break;								
			}
		}
	}
	
	
	void LabOrient_FillStaticWeightsSimple(DynamicGameObject dynObj)
	{		
		int size = world.statObjects.size();
		GameObject gObj;
		int x, y;
		int wLevel = WorldKingSnake.WORLD_LAB_ORIENT_WEIGHTS_LEVEL;
		int mWeight = LAB_ORIENT_MAX_WEIGHT;
		
		for(int i=0; i<size; ++i)
		{
			gObj = world.statObjects.get(i);

			x = ((int) gObj.position.x) * WorldKingSnake.CELL_DIM;
			y = ((int) gObj.position.y) * WorldKingSnake.CELL_DIM;
			
			switch(gObj.type)
			{
			case GameObject.APPLE:			
				world.fields[wLevel][x][y] *= (float) 1 / LabOrient_GetAppleWeight(dynObj);
				break;
			case GameObject.MUSHROOM_YELLOW:
				world.fields[wLevel][x][y] *= (float) 1 / LabOrient_GetYellowMWeight(dynObj);
				break;
			case GameObject.MUSHROOM_BLUE:
				world.fields[wLevel][x][y] *= (float) 1 / LAB_ORIENT_WEIGHT_BLUE_MUSHROOM;
				break;
			case GameObject.MUSHROOM_BROWN:
				world.fields[wLevel][x][y] *= (float)  LAB_ORIENT_WEIGHT_BROWN_MUSHROOM;
				break;
			case GameObject.MUSHROOM_BLUE_RED:
				world.fields[wLevel][x][y] *= (float) 1 / LAB_ORIENT_WEIGHT_BRED_MUSHROOM;
				break;				
			case DynamicGameObject.BOMB_FROM_TREE:
				float damage = Gifts.BOMB_FROM_TREE_DAMAGE * dynObj.stateHS.defencePower;
				if(damage >= dynObj.stateHS.health)
					world.fields[wLevel][x][y] = mWeight;
				else
					world.fields[wLevel][x][y] *= (LAB_ORIENT_WEIGHT_BOMB_FROM_TREE * (dynObj.stateHS.health / (dynObj.stateHS.health - damage)));
				break;				
			}
		}
	}

	
	
	float LabOrient_GetAppleWeight(DynamicGameObject dynObj)
	{
		//return  (dynObj.stateHS.defencePower < dynObj.stateHS.health ? 1 : dynObj.stateHS.defencePower / dynObj.stateHS.health) * ((dynObj.stateHS.health + dynObj.stateHS.health * Gifts.APPLE_HEALTH_INCREASE) / dynObj.stateHS.health);
		return  (float) (dynObj.stateHS.defencePower <= dynObj.stateHS.health ? 1.5f : 0.3f + Math.pow(dynObj.stateHS.defencePower / dynObj.stateHS.health, 2));
	}
	
	float LabOrient_GetYellowMWeight(DynamicGameObject dynObj)
	{
		return  Gifts.MUSHROOM_YELLOW_COINS / (dynObj.stateHS.defencePower * Gifts.APPLE_HEALTH_INCREASE);
	}
	
	void LabOrient_FillStatObjWeights(int xPos, int yPos, float weightK)
	{
		int wWidth = WORLD_WIDTH;
		int wHeight = WORLD_HEIGHT;
		int xCur, yCur;
		int objRadius = WorldKingSnake.CELL_DIM / 2;
		int wLevel = WorldKingSnake.WORLD_LAB_ORIENT_WEIGHTS_LEVEL;
		int mWeight = LAB_ORIENT_MAX_WEIGHT;
				
		for(int x = - objRadius; x <= objRadius; ++x)
		{
			for(int y = - objRadius; y <= objRadius; ++y)
			{
				xCur = xPos + x;
				yCur = yPos + y;
				
				if(xCur >= wWidth)
					xCur = 0;
				if(xCur < 0)
					xCur = wWidth - 1;
				
				if(yCur >= wHeight)
					yCur = 0;
				if(yCur < 0)
					yCur = wHeight - 1;
				
				if(world.fields[wLevel][xCur][yCur] < mWeight)
					world.fields[wLevel][xCur][yCur] *= weightK;					
			}
		}
		
		int radius = (int) Math.sqrt(Math.abs(LAB_ORIENT_EMPTY_SPACE_WEIGHT * weightK - LAB_ORIENT_EMPTY_SPACE_WEIGHT));
		int totalRadius = radius + objRadius;
		
		for(int x = -totalRadius; x <= totalRadius; ++x)
		{
			for(int y = -totalRadius; y <= totalRadius; ++y)
			{
				if(Math.abs(x) > objRadius || Math.abs(y) > objRadius)
				{
					xCur = xPos + x;
					yCur = yPos + y;
					
					if(xCur >= wWidth)
						xCur = 0;
					if(xCur < 0)
						xCur = wWidth - 1;
					
					if(yCur >= wHeight)
						yCur = 0;
					if(yCur < 0)
						yCur = wHeight - 1;
					
					if(world.fields[wLevel][xCur][yCur] < mWeight)
						world.fields[wLevel][xCur][yCur] *= (float) (weightK + (weightK > 1 ? -1 : 1) * Math.pow((Math.max(Math.abs(x) - objRadius, Math.abs(y) - objRadius)), 2)/LAB_ORIENT_EMPTY_SPACE_WEIGHT);
				}
			}
		}

		
	}	
	
	int LabOrient_GetReliefWeight(DynamicGameObject dynObj, int x, int y)
	{
		//IF RELIEF then weight is unreachable
		int weight = 0;
		int cellDim = WorldKingSnake.CELL_DIM;
		
		if(smartType == LAB_ORIENT_SIMPLE_MOVING)
		{
			int xPos = x * cellDim;
			int yPos = y * cellDim;
			
			for(int i = 0; i < cellDim; ++i)
				for(int j = 0; j < cellDim; ++j)
				{
					if(world.fields[WorldKingSnake.WORLD_RELIEF_LEVEL][xPos + i][yPos + j] >= WorldKingSnake.WALL && world.fields[WorldKingSnake.WORLD_RELIEF_LEVEL][xPos + i][yPos + j] < WorldKingSnake.SAND)
						return LAB_ORIENT_MAX_WEIGHT;
					
					switch(world.fields[WorldKingSnake.WORLD_RELIEF_LEVEL][xPos + i][yPos + j])
					{
					case WorldKingSnake.GRASS:
						weight += LAB_ORIENT_WEIGHT_GRASS;
						break;
					case WorldKingSnake.SAND:
						weight += LAB_ORIENT_WEIGHT_SAND;
						break;
					case WorldKingSnake.SWAMP:
						weight += LAB_ORIENT_WEIGHT_SWAMP;
						break;
					case WorldKingSnake.WATER:
						float damage = (float) (WorldKingSnake.WATER_DAMAGE * dynObj.stateHS.defencePower * 0.01f);
						if(damage >= dynObj.stateHS.health)
							weight = LAB_ORIENT_MAX_WEIGHT;
						else
							weight += LAB_ORIENT_WEIGHT_WATER * (dynObj.stateHS.health / (dynObj.stateHS.health - damage));
						break;
					}
				}
		}		
		else if(smartType == LAB_ORIENT_SMART_MOVING)
		{
			if(world.fields[WorldKingSnake.WORLD_RELIEF_LEVEL][x][y] >= WorldKingSnake.WALL && world.fields[WorldKingSnake.WORLD_RELIEF_LEVEL][x][y] < WorldKingSnake.SAND)
				return LAB_ORIENT_MAX_WEIGHT;
			
			switch(world.fields[WorldKingSnake.WORLD_RELIEF_LEVEL][x][y])
			{
			case WorldKingSnake.GRASS:
				weight += LAB_ORIENT_WEIGHT_GRASS;
				break;
			case WorldKingSnake.SAND:
				weight += LAB_ORIENT_WEIGHT_SAND;
				break;
			case WorldKingSnake.SWAMP:
				weight += LAB_ORIENT_WEIGHT_SWAMP;
				break;
			case WorldKingSnake.WATER:
				float damage = (float) (WorldKingSnake.WATER_DAMAGE * dynObj.stateHS.defencePower * 0.01f);
				if(damage >= dynObj.stateHS.health)
					weight = LAB_ORIENT_MAX_WEIGHT;
				else
					weight += LAB_ORIENT_WEIGHT_WATER * (dynObj.stateHS.health / (dynObj.stateHS.health - damage));
				break;
			}
		}
		
		return weight;		
	}
	
	void LabOrient_UpdateAdjLists(DynamicGameObject dynObj)
	{		
 		for(int x = 0; x < wWidth; ++x)
 			for(int y = 0; y < wHeight; ++y)
 			{				
 				VertsPointers myVert = new VertsPointers((short) (x * wHeight + y), (short) (((short)x)<<8 | (short) y));	//dist, parent, Kcoord: first byte holds y, second holds x, Kcoord2: x * wHeight + y
 				VertsPointers[] myNeighbs = new VertsPointers[ribsNumber];
 				adjList.add(new AdjListPointers(myVert, myNeighbs));
 			}

		LabOrient_FillReliefWeights(dynObj);
		
		if(smartType == LAB_ORIENT_SIMPLE_MOVING)
			LabOrient_FillStaticWeightsSimple(dynObj);
		else if(smartType == LAB_ORIENT_SMART_MOVING)
			LabOrient_FillStaticWeights(dynObj);
		
		LabOrient_FillDynObjsWeights(dynObj);

		if(smartType == LAB_ORIENT_SIMPLE_MOVING)
	 		for(int x = 0; x < wWidth; ++x)
		 			for(int y = 0; y < wHeight; ++y)	
		 					LabOrient_FillAdjRibsSimple(x, y);
		else if(smartType == LAB_ORIENT_SMART_MOVING)
	 		for(int x = 0; x < wWidth; ++x)
	 			for(int y = 0; y < wHeight; ++y)	
						LabOrient_FillAdjRibs(x, y);
		
	}



}
