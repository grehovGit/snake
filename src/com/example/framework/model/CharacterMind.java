package com.example.framework.model;

import android.util.Log;

import com.example.framework.model.DynamicGameObject;
import com.kingsnake.math.LabOrientDejkstra;
import com.kingsnake.math.Vector2;

import java.util.Random;

public class CharacterMind extends MindImpl{

	public static final float UPDATE_FREQUENCY = 0.1f;
	public static final int STAY = 0;
	public static final int MOVE_LEFT = 1;
	public static final int MOVE_RIGHT = 2;
	public static final int MOVE_UP = 3;
	public static final int MOVE_DOWN = 4;
	
	public static final int MOVE_LEFT_DOWN = 5;
	public static final int MOVE_LEFT_UP = 6;
	public static final int MOVE_RIGHT_UP = 7;
	public static final int MOVE_RIGHT_DOWN = 8;
	
	public static final int LEMMING_NMOVE_MODES = 5;
	public static final int LEMMING_MAX_MODEPERIOD = 5;
	
	public static final int HEDGEHOG_NMOVE_MODES = 5;
	public static final int HEDGEHOG_MAX_MODEPERIOD = 5;
	
	public static final int SNAKE_NMOVE_MODES = 9;
	public static final int SNAKE_MAX_MODEPERIOD = 5;

	WorldKingSnake world;
	public LabOrientDejkstra labOrientD;
	Vector2 newPos;
    Random rand; 
    int isMoving;
    float tModePeriod;
	
	CharacterMind(WorldKingSnake world)
	{
		this.world = world;
        newPos = new Vector2();
        labOrientD = new LabOrientDejkstra(world);
 		rand = new Random();  
 		isMoving = STAY;
 		tModePeriod = 0; 
	}
	
	
	//function for character implementation
	public boolean getBotMove(DynamicGameObject dynObj, float deltaTime)
	{
		
		switch(dynObj.objType)
		{
		case DynamicGameObject.SNAKE:
			return getSnakeMove(dynObj, deltaTime);
		case DynamicGameObject.LEMMING:
			return getLemmingMove(dynObj, deltaTime);
		case DynamicGameObject.HEDGEHOG:
			return getHedgeHogMove(dynObj, deltaTime);
		case DynamicGameObject.FROG:
			return getFrogMove(dynObj, deltaTime);
		}
		
		return true;		
	}
	
	public void getBombTreeMove(DynamicGameObject dynObj)
	{
		
	}
	
	public boolean getFrogMove(DynamicGameObject dynObj, float deltaTime)
	{
		Frog frog = (Frog) dynObj;
		boolean result= false;
		
		if(frog.stateHS.heatFinish)
		{
			frog.stateHS.heatFinish = false;
		}
		
		switch(dynObj.stateHS.level)
		{
		case HealthScore.LEVEL_PINK:
			result = newJumpPosWithCheckRelief(dynObj, 0, 0, WorldKingSnake.WORLD_WIDTH, WorldKingSnake.WORLD_HEIGHT);
			break;
		case HealthScore.LEVEL_GREEN:
			//check relief; hit target 5x5 and bounce to health or mushroom 5x5;
			break;
		case HealthScore.LEVEL_BLUE:
			//check relief; hit target 3x3 and bounce to health or mushroom 3x3;
			break;
		}	
		
		return result;
	}
	
	public boolean newJumpPosWithCheckRelief(DynamicGameObject dynObj, int xStart, int yStart, int width, int height)
	{
		int xNext = xStart + rand.nextInt(width);
		int yNext = yStart + rand.nextInt(height);		
	    int attempts = width * height;
	        
	    while (true) 
	    {
	       if (world.wProc.isEmptyCell(xNext, yNext, WorldKingSnake.WORLD_RELIEF_LEVEL))
	           break;
	            
	       xNext += 1;
	       --attempts;
	       if (xNext >= xStart + width) {
	           xNext = xStart;
	           yNext += 1;
	           --attempts;
	           if (yNext >= yStart + height) {
	               yNext = yStart;
	           }
	       }	            
	       if(attempts < 0)
	            	break;
	   }
	    
	    if(attempts < 0)
	    	return false;
	    
	    dynObj.oldPos.x = xNext + WorldKingSnake.CELL_SIZE / 2;	    
	    dynObj.oldPos.y = yNext + WorldKingSnake.CELL_SIZE / 2;
	      		
		return true;
	}
	
	public boolean getHedgeHogMove(DynamicGameObject dynObj, float deltaTime)
	{
		Hedgehog hg = (Hedgehog) dynObj;
		isMoving = hg.isMoving;
		tModePeriod = hg.tModePeriod;
		boolean result= false;
		
		if(hg.stateHS.heatFinish)
		{
			isMoving = hg.isMoving = hg.isMovingOld;
			changeDirection180(hg);
			hg.stateHS.heatFinish = false;
		}
		
		switch(dynObj.stateHS.level)
		{
		case HealthScore.LEVEL_PINK:
			result = haoticGridMoving(deltaTime, HEDGEHOG_NMOVE_MODES, HEDGEHOG_MAX_MODEPERIOD, dynObj);
			break;
		case HealthScore.LEVEL_GREEN:
			result = haoticGridMoving(deltaTime, HEDGEHOG_NMOVE_MODES, HEDGEHOG_MAX_MODEPERIOD, dynObj);
			break;
		case HealthScore.LEVEL_BLUE:
			result = haoticGridMoving(deltaTime, HEDGEHOG_NMOVE_MODES, HEDGEHOG_MAX_MODEPERIOD, dynObj);
			break;
		}	
		
		hg.isMovingOld = hg.isMoving = isMoving;
		hg.tModePeriod = tModePeriod;
		return result;
	}
	
	public boolean getLemmingMove(DynamicGameObject dynObj, float deltaTime)
	{
		Lemming lemming = (Lemming) dynObj;
		isMoving = lemming.isMoving;
		tModePeriod = lemming.tModePeriod;
		boolean result= false;
		
		switch(dynObj.stateHS.level)
		{
		case HealthScore.LEVEL_PINK:
			if(lemming.stateHS.heatFinish)
			{
				isMoving = lemming.isMoving = lemming.isMovingOld;
				changeDirection180(lemming);
				lemming.stateHS.heatFinish = false;
			}
			result = haoticGridMoving(deltaTime, LEMMING_NMOVE_MODES, LEMMING_MAX_MODEPERIOD, dynObj);
			break;
		case HealthScore.LEVEL_GREEN:
			if(lemming.stateHS.heatFinish)
			{
				isMoving = lemming.isMoving = lemming.isMovingOld;
				//changeDirection180(lemming);
				lemming.stateHS.heatFinish = false;
			}
			result = true;//labirintOrientationMoving(dynObj, LabOrientDejkstra.LAB_ORIENT_SIMPLE_MOVING, deltaTime);
			break;
		case HealthScore.LEVEL_BLUE:
			result = haoticGridMoving(deltaTime, LEMMING_NMOVE_MODES, LEMMING_MAX_MODEPERIOD, dynObj);
			break;
		}	
		
		lemming.isMovingOld = lemming.isMoving = isMoving;
		lemming.tModePeriod = tModePeriod;
		return result;
	}
	
	public boolean getSnakeMove(DynamicGameObject dynObj, float deltaTime)
	{
		Snake snake = (Snake) dynObj;
		isMoving = snake.isMoving;
		tModePeriod = snake.tModePeriod;
		boolean result= false;

		
		switch(dynObj.stateHS.level)
		{
		case HealthScore.LEVEL_PINK:
			if(snake.stateHS.heatFinish)
			{
				isMoving = snake.isMoving = snake.isMovingOld;
				changeDirection180(snake);
				snake.stateHS.heatFinish = false;
			}
			result = labirintOrientationMoving(dynObj, LabOrientDejkstra.LAB_ORIENT_SMART_MOVING, deltaTime);
			break;
		case HealthScore.LEVEL_GREEN:
			result = haoticGridMoving(deltaTime, SNAKE_NMOVE_MODES, SNAKE_MAX_MODEPERIOD, dynObj);
			break;
		case HealthScore.LEVEL_BLUE:
			result = haoticGridMoving(deltaTime, SNAKE_NMOVE_MODES, SNAKE_MAX_MODEPERIOD, dynObj);
			break;
		}	
		
		snake.isMovingOld = snake.isMoving = isMoving;
		//snake.tModePeriod = tModePeriod;
		return result;
	}
	
	boolean labirintOrientationMoving(DynamicGameObject dynObj, int type, float deltaTime)
	{
		//type == 1 - simple lab moving by cells
		//type == 2 - smart lab moving by sub cells
		float actTime = world.actTime;
		float oldTime = actTime - deltaTime;
		
		int intActTime = (int) (actTime / UPDATE_FREQUENCY);
		int intOldTime = (int) (oldTime / UPDATE_FREQUENCY);
		
		if(intOldTime == intActTime)
			return true;		//too early for calculating
		
		//moving	 
		newPos.x = dynObj.position.x;
   		newPos.y = dynObj.position.y;
   		newPos.add(dynObj.velocity.x * deltaTime, dynObj.velocity.y * deltaTime);
   			
   		if(newPos.x < 0) newPos.x = WorldKingSnake.WORLD_WIDTH;	
   		else if(newPos.x > WorldKingSnake.WORLD_WIDTH) newPos.x = 0;	   		
   		if(newPos.y < 0) newPos.y = WorldKingSnake.WORLD_HEIGHT;
   		else if(newPos.y > WorldKingSnake.WORLD_HEIGHT) newPos.y = 0;
   		
   		//if we didn'cross next log cell, it's too early to calculate and change our path
        if(((int)newPos.x) == ((int)dynObj.position.x) && ((int)newPos.y) == ((int)dynObj.position.y))	
        {
           // return true;
        }	
		
		int dir = LabOrientDejkstra.LAB_ORIENT_DIR_STAY;
		float sin_cos_45 = 0.70710678f;
	

		dir =  labOrientD.LabOrient_GetBotMove(dynObj, type);
		
		//if simple moving place our character in the middle of cell
		if(type == LabOrientDejkstra.LAB_ORIENT_SIMPLE_MOVING)
			dir = placeCellCenterBeforeMoving(dynObj, dir);
		
		int oldIsMoving = isMoving;
		
		if(dir == LabOrientDejkstra.LAB_ORIENT_DIR_STAY)
		{
			isMoving = STAY;
			dynObj.velocity.x = 0;
			dynObj.velocity.y= 0;
		}
		else if(dir == LabOrientDejkstra.LAB_ORIENT_DIR_DOWNLEFT)
		{
			isMoving = MOVE_LEFT_DOWN;
			dynObj.velocity.x = (float) - dynObj.stateHS.velocity * sin_cos_45;
			dynObj.velocity.y = (float) - dynObj.stateHS.velocity * sin_cos_45;
		}
		else if(dir == LabOrientDejkstra.LAB_ORIENT_DIR_LEFT)
		{
			isMoving = MOVE_LEFT;
			dynObj.velocity.x = - dynObj.stateHS.velocity;
			dynObj.velocity.y = 0;
		}
		else if(dir == LabOrientDejkstra.LAB_ORIENT_DIR_UPLEFT)
		{
			isMoving = MOVE_LEFT_UP;
			dynObj.velocity.x = (float) - dynObj.stateHS.velocity * sin_cos_45;
			dynObj.velocity.y = (float)   dynObj.stateHS.velocity * sin_cos_45;
		}
		else if(dir == LabOrientDejkstra.LAB_ORIENT_DIR_UP)
		{
			isMoving = MOVE_UP;
			dynObj.velocity.x = 0;
			dynObj.velocity.y = dynObj.stateHS.velocity;
		}
		else if(dir == LabOrientDejkstra.LAB_ORIENT_DIR_UPRIGHT)
		{
			isMoving = MOVE_RIGHT_UP;
			dynObj.velocity.x = (float) dynObj.stateHS.velocity * sin_cos_45;
			dynObj.velocity.y = (float) dynObj.stateHS.velocity * sin_cos_45;
		}
		else if(dir == LabOrientDejkstra.LAB_ORIENT_DIR_RIGHT)
		{
			isMoving = MOVE_RIGHT;
			dynObj.velocity.x = dynObj.stateHS.velocity;
			dynObj.velocity.y = 0;
		}
		else if(dir == LabOrientDejkstra.LAB_ORIENT_DIR_DOWNRIGHT)
		{
			isMoving = MOVE_RIGHT_DOWN;
			dynObj.velocity.x = (float)   dynObj.stateHS.velocity * sin_cos_45;
			dynObj.velocity.y = (float) - dynObj.stateHS.velocity * sin_cos_45;
		}
		else if(dir == LabOrientDejkstra.LAB_ORIENT_DIR_DOWN)
		{
			isMoving = MOVE_DOWN;
			dynObj.velocity.x = 0;
			dynObj.velocity.y = - dynObj.stateHS.velocity;
		}
	
		
		if(oldIsMoving != isMoving)
			dynObj.isTurn = true;
		else
			dynObj.isTurn = false;
		
		if(dir > LabOrientDejkstra.LAB_ORIENT_DIR_STAY)
			return true;
		else
			return false;
	}
	
	
	
    void changeDirection180(DynamicGameObject dynObj)
    {      	
        switch(isMoving)
        {
        case STAY:
    		break;		        	
        case MOVE_LEFT:
			dynObj.velocity.x *= -1;
			isMoving = MOVE_RIGHT;
			break;
    	case MOVE_RIGHT:
			dynObj.velocity.x *= -1;
			isMoving = MOVE_LEFT;
			break;
    	case MOVE_UP:
			dynObj.velocity.y *= -1;
			isMoving = MOVE_DOWN;
			break;
    	case MOVE_DOWN:
			dynObj.velocity.y *= -1;
			isMoving = MOVE_UP;
			break;	        	
        }        
    }
    
    int placeCellCenterBeforeMoving(DynamicGameObject dynObj, int dir)
    {
    	int x = (int) (dynObj.position.x * WorldKingSnake.CELL_DIM);
    	int y = (int) (dynObj.position.y * WorldKingSnake.CELL_DIM);
    	int xCenter = (x / WorldKingSnake.CELL_DIM) * WorldKingSnake.CELL_DIM + WorldKingSnake.CELL_DIM / 2;
    	int yCenter = (y / WorldKingSnake.CELL_DIM) * WorldKingSnake.CELL_DIM + WorldKingSnake.CELL_DIM / 2;
    	dynObj.isPlaceToCellCenterMovingMode = false;
    	
    	if(dir == LabOrientDejkstra.LAB_ORIENT_DIR_LEFT || dir == LabOrientDejkstra.LAB_ORIENT_DIR_RIGHT)
    	{
    		if(y > yCenter)
    		{
    			dir = LabOrientDejkstra.LAB_ORIENT_DIR_DOWN;
    			dynObj.isPlaceToCellCenterMovingMode = true;
    		}
    		else if(y < yCenter)
    		{
    			dir = LabOrientDejkstra.LAB_ORIENT_DIR_UP;
    			dynObj.isPlaceToCellCenterMovingMode = true;
    		}
    	}
    	else if(dir == LabOrientDejkstra.LAB_ORIENT_DIR_UP || dir == LabOrientDejkstra.LAB_ORIENT_DIR_DOWN)
    	{
    		if(x > xCenter)
    		{
    			dir = LabOrientDejkstra.LAB_ORIENT_DIR_LEFT;
    			dynObj.isPlaceToCellCenterMovingMode = true;
    		}
    		else if(x < xCenter)
    		{
    			dir = LabOrientDejkstra.LAB_ORIENT_DIR_RIGHT;
    			dynObj.isPlaceToCellCenterMovingMode = true;
    		}
    	}
    	
    	return dir;
    }
	
	public boolean haoticGridMoving(float deltaTime, int nMoveModes, int maxPeriod,
			   DynamicGameObject dynObj)
	    {
	        if(dynObj.objType == DynamicGameObject.SNAKE)
	        	dynObj.objType = 1;
	        
			//moving	
			if(isMoving > STAY && isMoving < nMoveModes)
			{  
				newPos.x = dynObj.position.x;
	   			newPos.y = dynObj.position.y;
	   			newPos.add(dynObj.velocity.x * deltaTime, dynObj.velocity.y * deltaTime);
	   			
	   			if(newPos.x < 0) newPos.x = WorldKingSnake.WORLD_WIDTH;	
	   			else if(newPos.x > WorldKingSnake.WORLD_WIDTH) newPos.x = 0;	
	   			if(newPos.y < 0) newPos.y = WorldKingSnake.WORLD_HEIGHT;
	   			else if(newPos.y > WorldKingSnake.WORLD_HEIGHT) newPos.y = 0;
	   			 				
	            if(((int)newPos.x) != ((int)dynObj.position.x) || ((int)newPos.y) != ((int)dynObj.position.y))	
	            {
	            	if(!world.wProc.isEmptyNeighbourCellByCoords(newPos.x, newPos.y, WorldKingSnake.WORLD_RELIEF_LEVEL, isMoving))
	            		tModePeriod = -1;
	            }           
			}			
			
		  	//it'stime to change direction	   
	        if(tModePeriod < 0)
	        {
	        	int x = (int) dynObj.position.x;
	        	int y = (int) dynObj.position.y;
	        	
	        	if(isMoving == STAY || (x % WorldKingSnake.CELL_DIM == WorldKingSnake.CELL_DIM / 2 && y % WorldKingSnake.CELL_DIM == WorldKingSnake.CELL_DIM / 2))
	        	{	        		
		        	isMoving = rand.nextInt(nMoveModes); 
		         	tModePeriod = rand.nextInt(maxPeriod);	 
		         	
		    		if(!applyNewDirection(nMoveModes, dynObj))
		    			return false;			//didn'f find correct direction for moving;	

	        	} 
	        	
	    		return true;
	        } 
	        
	        if(isMoving == STAY)
	        	return false;	//we STAY and tModePeriod >= 0
	        else 
	        	return true;	//we MOVING and no obstacles or no need to check for obstacles
	    }
	    
	    boolean applyNewDirection(int nMoveModes, DynamicGameObject dynObj)
	    {
	        int attempts = nMoveModes - 1;  
	        int isMoving = this.isMoving;
	        
	        if(dynObj.objType == DynamicGameObject.SNAKE)
	        	dynObj.objType = 1;
	        
	    	while(attempts > 0)
	    	{
		        switch(isMoving)
		        {
		        case STAY:
	        		//++isMoving;
					dynObj.velocity.x = 0;
					dynObj.velocity.y = 0;
		        	attempts = -2;
		        	break;
		        case MOVE_LEFT:
		        	if(world.wProc.isEmptyNeighbourCellByCoords(dynObj.position.x, dynObj.position.y, WorldKingSnake.WORLD_RELIEF_LEVEL, isMoving))
		        	{
						dynObj.velocity.x = -dynObj.stateHS.velocity;
						dynObj.velocity.y = 0;
						attempts = -2;
						break;
		        	}
		        	else
		        	{
		        		--attempts;
		        		if(attempts <= 0)
		        			break;
		        		++isMoving;
		        	}
		    	case MOVE_RIGHT:
		        	if(world.wProc.isEmptyNeighbourCellByCoords(dynObj.position.x, dynObj.position.y, WorldKingSnake.WORLD_RELIEF_LEVEL, isMoving))
		        	{
						dynObj.velocity.x = dynObj.stateHS.velocity;
						dynObj.velocity.y = 0;
						attempts = -2;
						break;
					}
		        	else
		        	{
		        		--attempts;
		        		if(attempts <= 0)
		        			break;
		        		++isMoving;
		        	}
		    	case MOVE_UP:
		        	if(world.wProc.isEmptyNeighbourCellByCoords(dynObj.position.x, dynObj.position.y, WorldKingSnake.WORLD_RELIEF_LEVEL, isMoving))
		        	{
		        		dynObj.velocity.x = 0;
		        		dynObj.velocity.y = dynObj.stateHS.velocity;
						attempts = -2;
						break;
					}
		        	else
		        	{
		        		--attempts;
		        		if(attempts <= 0)
		        			break;
		        		++isMoving;
		        	}
		    	case MOVE_DOWN:
		        	if(world.wProc.isEmptyNeighbourCellByCoords(dynObj.position.x, dynObj.position.y, WorldKingSnake.WORLD_RELIEF_LEVEL, isMoving))
		        	{
		        		dynObj.velocity.x = 0;
		        		dynObj.velocity.y = -dynObj.stateHS.velocity;
						attempts = -2;
						break;
					}
		        	else
		        	{
		        		--attempts;
		        		if(attempts <= 0)
		        			break;
		        		isMoving = MOVE_LEFT;
		        	}	        	
		        }
	    	}
	    	
	    	if(attempts == -2)
	    	{
	    		this.isMoving = isMoving;
	    		return true;	//found proper direction
	    	}
	    	else 
	    	{
	    		this.isMoving = STAY;	//if no, just stay
        		dynObj.velocity.x = 0;
        		dynObj.velocity.y = 0;
	    		return false;
	    	}
	    }

}
