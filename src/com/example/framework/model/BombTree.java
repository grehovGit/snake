package com.example.framework.model;

import com.example.framework.model.DynamicGameObject;
import com.kingsnake.math.Vector2;

import java.util.Random;

public class BombTree extends DynamicGameObject {
	
	static  float WORLD_WIDTH;
	static  float WORLD_HEIGHT;
	
	public static  int STAY = 0;
	public static  int MOVE_LEFT = 1;
	public static  int MOVE_RIGHT = 2;
	public static  int MOVE_UP = 3;
	public static  int MOVE_DOWN = 4;
	public static  int NMOVE_MODES = 4;
	public static  int MAX_MODEPERIOD = 11;
	public static  int BOMBING_PERIOD_LONG = 10;
	public static  int BOMBING_PERIOD_SHORT = 1;
	
	static  float WIDTH = 1;
	static  float HEIGHT = 4;
	static  float TARGET_OFFSET = HEIGHT / 2 - HEIGHT / 9;
	static  float BOMB_START_OFFSET = HEIGHT / 5;
	public static final float BOMB_SIZE = 0.2f;
	public static final float BOMB_EXIST_TIME = 1.0f;
	public static final float BOMB_SPEED = (BombTree.TARGET_OFFSET  -  BombTree.BOMB_START_OFFSET) / BOMB_EXIST_TIME;

	
    public int isMoving;
    public boolean isMagnified;
    public boolean readyBombing;
    DynamicGameObject target;
		
	static  float SPEED = 0.5f;	  
    float tModePeriod, bombPeriod;  
    Random rand; 
    Vector2 tempVector;
        
    
    public BombTree(float x, float y, 
    		float w_width, float w_height, WorldKingSnake world) 
    {
        super(x, y, WIDTH, HEIGHT, 0, DynamicGameObject.TREE_BOMB, world);
        
 		WORLD_WIDTH = w_width;
 		WORLD_HEIGHT = w_height;
 		isMoving = STAY;
 		isMagnified = false;
 		tModePeriod = 0;
 		bombPeriod = BOMBING_PERIOD_LONG;
 		readyBombing = false;
 		target = null;
 		
 		rand = new Random(); 	
 		tempVector = new Vector2();
        
        this.position.set(x, y);
        this.world = world;
        
    }
    
    public void update(float deltaTime) {
    	
    	//moving
    	if(!isMagnified)
    		haoticFlying(deltaTime);
    	else
    		magnifyFlying(deltaTime);
    	
    	//if dynamic object is under us
    	if(world.wProc.getSubCellByCoords(position.x, position.y - TARGET_OFFSET, WorldKingSnake.WORLD_DYNAMIC_OBJECTS_LEVEL) > 0)
    	{
    		bombPeriod = BOMBING_PERIOD_SHORT;
    		target = world.wProc.getDynObjByCoords(position.x, position.y - TARGET_OFFSET);
    		
    		if(target != null && target.isCharacter)
        		isMagnified = true; 
    		else
    			target = null;
    	}
    	
    	//bombing
    	if(bombPeriod < 0)
    	{
    		readyBombing = true;
    		bombPeriod = BOMBING_PERIOD_LONG;
    	}
    	
    	if(readyBombing)
    		if(AdjustBombing())
    			readyBombing = false;  		
    	
        actTime += deltaTime;
      	tModePeriod -= deltaTime;
      	bombPeriod -= deltaTime;
    }
    
    public boolean AdjustBombing()
    {
    	if(world.wProc.getSubCellByCoords(position.x, position.y - TARGET_OFFSET, WorldKingSnake.WORLD_RELIEF_LEVEL) > 0 ||
    			world.wProc.getSubCellByCoords(position.x, position.y - TARGET_OFFSET, WorldKingSnake.WORLD_STATIC_OBJECTS_LEVEL) > 0)
    		return false;
    	
    	world.flyingObjects.add(new DynamicGameObject(position.x, position.y - BOMB_START_OFFSET, BOMB_SIZE, 
    			BOMB_SIZE, 0, DynamicGameObject.BOMB_FALL_ACTION, world));    	
    	world.flyingObjects.get(world.flyingObjects.size() - 1).SetVelocity(0, - BOMB_SPEED);
    	    	    	
    	return true;
    }
    

    public void haoticFlying(float deltaTime)
    {
	  	//it'stime to change direction
        if(tModePeriod < 0)
        {
        	isMoving = rand.nextInt(NMOVE_MODES); 
        	++isMoving;
         	tModePeriod = rand.nextInt(MAX_MODEPERIOD);
        }
    	
		if (isMoving == MOVE_LEFT){
			velocity.x = -SPEED;
			velocity.y = 0;
		}
		else if (isMoving == MOVE_RIGHT){
			velocity.x = SPEED;
			velocity.y = 0;
		}
		else if (isMoving == MOVE_UP){
			velocity.x = 0;
			velocity.y = SPEED;
		}
		else if (isMoving == MOVE_DOWN){
			velocity.x = 0;
			velocity.y = -SPEED;
		}
			
		//moving	
		if(isMoving > 0 && isMoving < 5)
		{   			
            position.add(velocity.x * deltaTime, velocity.y * deltaTime);
            if(position.x < 0) position.x = WORLD_WIDTH;
            else if(position.x > WORLD_WIDTH) position.x = 0;
            
            if(position.y < 0) position.y = WORLD_HEIGHT;
            else if(position.y > WORLD_HEIGHT) position.y = 0;            
		}
	
    }
    
	public void magnifyFlying(float deltaTime)
	{
		if(target == null)
		{
    		isMagnified = false;
    		return;
		}
		
		tempVector.x = target.position.x - position.x;
		tempVector.y = target.position.y - position.y;		
		velocity.rotate(tempVector.angle() - velocity.angle());	
		
        position.add(velocity.x * deltaTime, velocity.y * deltaTime);
        if(position.x < 0) position.x = WORLD_WIDTH;
        else if(position.x > WORLD_WIDTH) position.x = 0;
        
        if(position.y < 0) position.y = WORLD_HEIGHT;
        else if(position.y > WORLD_HEIGHT) position.y = 0;    
	}  
    
}
