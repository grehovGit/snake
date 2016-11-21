package com.example.framework.model;

import com.example.framework.model.DynamicGameObject;
import com.kingsnake.math.Vector2;

import java.util.Random;

public class Frog extends DynamicGameObject {
	
	static  float WORLD_WIDTH;
	static  float WORLD_HEIGHT;
	
	static  float WIDTH = 1.2f;
	static  float HEIGHT = 1.2f;
	
	public static  int SIT = 0;
	public static  int JUMPING = 1;
	public static  int GROUNDING = 2; //show dust clouds
	
	static float G = 10.0f;
	static float GROUNDING_PERIOD = 0.6f;
	static float NEXT_JUMP_PERIOD = 5;
	
	public static float ACT_SPEED = 0.1f;	//animation speed
    public float angle;
    public Vector2 gravity;
    public Vector2 move;
    float tJump;
    float tSitPeriod;
     
    Random rand; 
    
    //sitting
    public static int[] frameCodeFrogSit = {
			0, 8, 2,	//eyes from right to forward and breathing
			8, 1, 15,	// look forward
			19, 7, 2,	//breath 2 times look forward
			19, 7, 2,
			19, 1, 10,	//look forward
			8, 4, 2,	//eyes to right
			12, 1, 15,	//look right
			12, 7, 3,	//breath slowly look right			
			12, 1, 15,	//look right
			12, 7, 2,	//breath fast look right
			12, 7, 2,	
			12, 1, 10	//look right			
		   };
    
    //jumping
    public static int manSizeFrogSit = 12;
        
    
    public Frog(float x, float y, float width, float height, WorldKingSnake world) 
    {
        super(x, y, width, height, 0, DynamicGameObject.FROG, world);
        
        WORLD_WIDTH = WorldKingSnake.WORLD_WIDTH;
        WORLD_HEIGHT = WorldKingSnake.WORLD_HEIGHT;
        isCharacter = true;
        
 		angle = 0;
 		tJump = 0;
 		tSitPeriod = 5.0f;		
 		rand = new Random();
 		this.world = world;
 		       
        position.set(x, y);
        velocity.set(0, 0);
        
 		gravity = new Vector2(0, -G); 		
 		move = new Vector2(0, 0);
    }
    
    public void update(float deltaTime) {   	
    	   	  	   	
    	if(isMoving == JUMPING)
    	{    		
    		velocity.add(gravity.x * deltaTime, gravity.y * deltaTime);   		    		
	        position.add(velocity.x * deltaTime, velocity.y * deltaTime);
	        
	        if(position.x < 0) position.x = WORLD_WIDTH;
	        else if(position.x > WORLD_WIDTH) position.x = 0;
	        
	        if(position.y < 0) position.y = WORLD_HEIGHT;
	        else if(position.y > WORLD_HEIGHT) position.y = 0;	        	        
	        
	        if(tJump <= 0)
	        {	        	
	        	isMoving = GROUNDING;	        	
	            this.velocity.set(0, 0);
	            this.gravity.set(0, -G);	            
	            this.position.set(newPos);	          	           
	        }
    	}
    	else if(isMoving == GROUNDING && tJump < -GROUNDING_PERIOD)
    	{
			isMoving = SIT;
			tJump = 0;
			
			if(position.x > 11)
				position.x = position.x;
			
    		if(!world.wProc.dynObjGrounding(this, newPos))
    		{
    			//if grounding isn't able - jump again:
    			tSitPeriod = NEXT_JUMP_PERIOD;  
    		}
    	}
    	
   	   //it'stime to jump
        if(isMoving == SIT && tSitPeriod >= NEXT_JUMP_PERIOD)
        { 	   	
        	if(!stateHS.isHeated && !stateHS.isHypnosed && world.mind.getBotMove(this, deltaTime))
        	{
	             jump(oldPos.x, oldPos.y);
	             tSitPeriod = 0;  
        	}
        	else if(stateHS.isHeated && stateHS.hitPeriod == stateHS.hitPeriodFull)
        	{
        		//TO DO actions after hitting:
        		
        	} 
    		else
    			isMoving = SIT;  
        	

        }
    	
        act_time += deltaTime;
        tJump -= deltaTime;
  	    tSitPeriod += deltaTime;          	
    }
    
    public void jump(float xNext, float yNext) {
    	  		
    		newPos.set(xNext, yNext);
    		    		
    		move.set(newPos);    		
    		move.sub(position);   		
    		
    		float dist = move.len();
    		
    		
    		//if (dist < 1)	//no need to jump
    		//	return;
    		
  		
	    		float moveAngle =  move.angle();    		
	    		float ballisticAngle = 45;
	    		
	    		if(moveAngle > 45 && moveAngle < 90)
	    			ballisticAngle = 90 - moveAngle;
	    		
	    		else if(moveAngle > 90 && moveAngle < 135)
	    			ballisticAngle = 135 - moveAngle;
	    			
	    		else if(moveAngle > 225 && moveAngle < 270)
	    			ballisticAngle = 270 - moveAngle;
	    			
	    		else if(moveAngle > 315 && moveAngle < 360)
	    			ballisticAngle = 360 - moveAngle;	    	   
	    		
	    		
	    		//count our ballistic in zero coords
	    		float ballisticRadians = (float) Math.toRadians(ballisticAngle);
	    		
	    		float vel = 0;
	    		gravity.set(0, -G);
	    		
	    		if(moveAngle != 90 && moveAngle != 270)	   		//non vertical moving 		
	    		{
	    			vel = (float) Math.sqrt(dist * G / Math.sin(2 * ballisticRadians));
	    			velocity.set((float)(Math.cos(ballisticRadians) * vel), (float)(Math.sin(ballisticRadians) * vel));
	    			
		    		//set jump period
		    		tJump = (float) (2 * Math.sin(ballisticRadians) * velocity.len()/ G);
		    		
		    		//if 2,3 quadrant provide horizon flip
		    		if(moveAngle > 90 && moveAngle < 270)
		    		{
		    			velocity.mirrorVert();	
		    			gravity.mirrorVert();
		    		}	
		    		
		    		//translate ballistic in general coords		
		    		velocity.rotate(moveAngle);  
		    		gravity.rotate(moveAngle);	//rotate our G vector according to our direction
		    		
	    		}
	    		else if(moveAngle == 90)
	    		{
	    			vel = (float) Math.sqrt(2 * dist * G);
	    			velocity.set(0, vel);			//vertical moving
	    			
		    		//set jump period
		    		tJump = (float) velocity.len()/ G;
	    		}	
	    		else if(moveAngle == 270)
	    		{
	    			vel = (float) Math.sqrt(2 * dist * G);
	    			velocity.set(0, -vel);			//vertical moving
	    			
		    		//set jump period
		    		tJump = (float) velocity.len()/ G;
	    		}	    	
    		
    		isMoving = JUMPING;
    		
    	}    
}
