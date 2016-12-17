package com.example.framework.model;

import com.example.framework.model.DynamicGameObject;

import java.util.Random;

public class Hedgehog extends DynamicGameObject {
	
	static  float WORLD_WIDTH;
	static  float WORLD_HEIGHT;
	
	static  float WIDTH = 1.2f;
	static  float HEIGHT = 1.2f;
	static  float APPERIANCE_PERIOD = 1;
	
	public static  int STAY = 0;
	public static  int MOVE_LEFT = 1;
	public static  int MOVE_RIGHT = 2;
	public static  int MOVE_UP = 3;
	public static  int MOVE_DOWN = 4;
	public static  int STAY_DUSTING = 5;
	
	static  float SPEED = 2f;
	
	public static float ACT_SPEED = 0.1f;	//animation speed
    float tModePeriod;
    
    public int isMoving;    
    Random rand;
        
    
    public Hedgehog(float x, float y, float width, float height, float angle, WorldKingSnake world) 
    {
        super(x, y, width, height, angle, DynamicGameObject.HEDGEHOG, world);
        
 		WORLD_WIDTH = WorldKingSnake.WORLD_WIDTH;
 		WORLD_HEIGHT = WorldKingSnake.WORLD_HEIGHT;
 		isMoving = CharacterMind.STAY;
 		tModePeriod = APPERIANCE_PERIOD; 		    
        position.set(x, y);
        velocity.set(0, 0);  
        this.world = world;   
        isCharacter = true;
    }
    
    public void update(float deltaTime) {
    	  	
    	if(stateHS.isHeated && stateHS.hitPeriod == stateHS.hitPeriodFull)
    	{
    		isMovingOld = isMoving;
			isMoving = CharacterMind.STAY;		
    	}   	   	
    	else if(!stateHS.isHeated && !stateHS.isHypnosed && world.mind.getBotMove(this, deltaTime))    
		{
			newPos.x = position.x;
			newPos.y = position.y;
			newPos.add(velocity.x * deltaTime, velocity.y * deltaTime);						
			
	        if(true/*world.wProc.dynObjMove(this, newPos)*/)
	        {
	            position.add(velocity.x * deltaTime, velocity.y * deltaTime);
	            		        
		        if(position.x < 0)
		            position.x = WORLD_WIDTH;
		        if(position.x > WORLD_WIDTH)
		            position.x = 0;
		        if(position.y < 0)
		            position.y = WORLD_HEIGHT;
		        if(position.y > WORLD_HEIGHT)
		            position.y = 0;	  
	        }
	        	
		}
		else
			isMoving = CharacterMind.STAY;

			
        actTime += deltaTime;
      	tModePeriod -= deltaTime;
    }    
    
}
