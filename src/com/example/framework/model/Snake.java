package com.example.framework.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.example.framework.model.DynamicGameObject;
import com.kingsnake.math.Vector2;


public class Snake extends DynamicGameObject {
	
		static  float WORLD_WIDTH;
		static  float WORLD_HEIGHT;
		
		public final static int RIGHT = 0;
		public final static int LEFT = 1;
		public final static int UP = 2;
		public final static int DOWN = 3;
		
	
		public static  int STAY = 0;
		public static  int MOVE = 1;
		
		static float PARTS_DISTANCE = 0.45f;		
		
		public static float ACT_SPEED = 0.05f;	//animation speed
		public static float SPEED = 2;
	    
	    Vector2 deltaSin;	//offset of sine moving 
	    
	    float tModePeriod;	    
	    Random rand; 	
	    public List<SnakePart> parts = new ArrayList<SnakePart>();
	    	  	    
	    public Snake(float x, float y, float wWidth, float wHeight, int dir, int level, WorldKingSnake world) {
	    	
	    	super(x, y, 0, 0, 0, DynamicGameObject.SNAKE, world);
	    	
	 		WORLD_WIDTH = wWidth;
	 		WORLD_HEIGHT = wHeight;
	    	
	 		isCharacter = true;
	 		tModePeriod = 0;	 		
	 		rand = new Random();	 			        
            deltaSin = new Vector2();
            this.world = world;
	    	
	        parts.add(new SnakePart(x, y, dir, level, Statics.DynamicGameObject.SNAKE_HEAD_INDEX, this, world));	        	        
	        parts.add(new SnakePart(x - PARTS_DISTANCE, y, dir, level,  Statics.DynamicGameObject.SNAKE_FIRST_PART_INDEX, this, world));
	        parts.add(new SnakePart(x - 2 * PARTS_DISTANCE, y, dir, level, Statics.DynamicGameObject.SNAKE_SECOND_PART_INDEX, this, world));
	        
	        for(int i=0; i < level+1; i++)
		        parts.add(new SnakePart(x - 3 * PARTS_DISTANCE - i * PARTS_DISTANCE, y, dir, level, Statics.DynamicGameObject.SNAKE_OTHER_PARTS_INDEX + i, this, world));
	        
	        velocity.set(parts.get(0).velocity);
	        setLevel(level);
	        world.world2d.createSnake(this);
	        initArmor();

	        /*fSkills.addSkillDefault(Statics.FightingSkills.FORWARD_ATTACK);
	        fSkills.setSkillLevel(Statics.FightingSkills.FORWARD_ATTACK, 3);
	        fSkills.setSkillChargeLevel(Statics.FightingSkills.FORWARD_ATTACK, 3);
	        
	        fSkills.addSkillDefault(Statics.FightingSkills.FORWARD_LEFT_HOOK);
	        fSkills.setSkillLevel(Statics.FightingSkills.FORWARD_LEFT_HOOK, 3);
	        fSkills.setSkillChargeLevel(Statics.FightingSkills.FORWARD_LEFT_HOOK, 3);
	        
	        fSkills.addSkillDefault(Statics.FightingSkills.FORWARD_RIGHT_HOOK);
	        fSkills.setSkillLevel(Statics.FightingSkills.FORWARD_RIGHT_HOOK, 3);
	        fSkills.setSkillChargeLevel(Statics.FightingSkills.FORWARD_RIGHT_HOOK, 3);
	        
	        fSkills.addSkillDefault(Statics.FightingSkills.FORWARD_LEFT_TURN);
	        fSkills.setSkillLevel(Statics.FightingSkills.FORWARD_LEFT_TURN, 3);
	        fSkills.setSkillChargeLevel(Statics.FightingSkills.FORWARD_LEFT_TURN, 3);
	        
	        fSkills.addSkillDefault(Statics.FightingSkills.FORWARD_RIGHT_TURN);
	        fSkills.setSkillLevel(Statics.FightingSkills.FORWARD_RIGHT_TURN, 3);
	        fSkills.setSkillChargeLevel(Statics.FightingSkills.FORWARD_RIGHT_TURN, 3);
	        
	        fSkills.addSkillDefault(Statics.FightingSkills.BACK_LEFT_HOOK);
	        fSkills.setSkillLevel(Statics.FightingSkills.BACK_LEFT_HOOK, 3);
	        fSkills.setSkillChargeLevel(Statics.FightingSkills.BACK_LEFT_HOOK, 3);
	        
	        fSkills.addSkillDefault(Statics.FightingSkills.BACK_RIGHT_HOOK);
	        fSkills.setSkillLevel(Statics.FightingSkills.BACK_RIGHT_HOOK, 3);
	        fSkills.setSkillChargeLevel(Statics.FightingSkills.BACK_RIGHT_HOOK, 3);
	        
	        fSkills.addSkillDefault(Statics.FightingSkills.BACK_ATTACK);
	        fSkills.setSkillLevel(Statics.FightingSkills.BACK_ATTACK, 3);
	        fSkills.setSkillChargeLevel(Statics.FightingSkills.BACK_ATTACK, 3);
	        
	        fSkills.addSkillDefault(Statics.FightingSkills.IMPULSE_DEFFENSE_FORWARD);
	        fSkills.setSkillLevel(Statics.FightingSkills.IMPULSE_DEFFENSE_FORWARD, 3);
	        fSkills.setSkillChargeLevel(Statics.FightingSkills.IMPULSE_DEFFENSE_FORWARD, 3);
	        
	        //fSkills.addSkillDefault(Statics.FightingSkills.IMPULSE_ATTACK_FORWARD);
	        //fSkills.setSkillLevel(Statics.FightingSkills.IMPULSE_ATTACK_FORWARD, 3);
	        //fSkills.setSkillChargeLevel(Statics.FightingSkills.IMPULSE_ATTACK_FORWARD, 3);
	        
	        fSkills.addSkillDefault(Statics.FightingSkills.IMPULSE_DEFFENSE_LEFTSIDE);
	        fSkills.setSkillLevel(Statics.FightingSkills.IMPULSE_DEFFENSE_LEFTSIDE, 3);
	        fSkills.setSkillChargeLevel(Statics.FightingSkills.IMPULSE_DEFFENSE_LEFTSIDE, 3);
	        
	        fSkills.addSkillDefault(Statics.FightingSkills.IMPULSE_DEFFENSE_RIGHTSIDE);
	        fSkills.setSkillLevel(Statics.FightingSkills.IMPULSE_DEFFENSE_RIGHTSIDE, 3);
	        fSkills.setSkillChargeLevel(Statics.FightingSkills.IMPULSE_DEFFENSE_RIGHTSIDE, 3);   */    
	    }
	    
	    public void update(float deltaTime)
	    {	    	
		    int size = parts.size();	    
		    
		    SnakePart part;
		    
		    for (int i = 0; i < size; ++i)
		    {
		    	part = parts.get(i);
		    	part.update(deltaTime);
		    }
    		
    		if(stateHS.isDead != HealthScore.ALIVE || size == 0)
    			return;
    		
		    //get total position from head position
		    SnakePart head = parts.get(0);
    		position.set(head.position);
    		stateHS.health = head.stateHS.health;
    		angle = head.angle;
		    
	    	if (stateHS.isBot)
	    	{// bot
	    		
		    	if(stateHS.isHeated && stateHS.hitPeriod == stateHS.hitPeriodFull)
		    	{
		    		isMovingOld = isMoving;
					isMoving = CharacterMind.STAY;		
		    	}   	   	
		    	else if(!stateHS.isHeated && !stateHS.isStriking && !stateHS.isHypnosed && world.mind.getBotMove(this, deltaTime))    
				{
		    		head.velocity = velocity;
		    		world.world2d.implementSnakeMoveForce(head);
				}
				else
					isMoving = CharacterMind.STAY;
		    	
	    		world.world2d.implementSnakeMoveCorrectingForce(head);
	    	}
	    	else
	    	{//user character
	    				         	   	
		    	if(!stateHS.isHeated && !stateHS.isStriking && !stateHS.isHypnosed && isMoving == MOVE)    
				{
		    		head.velocity = velocity;
		    		world.world2d.implementSnakeMoveForce(head);
				}
				else
					isMoving = CharacterMind.STAY;
		    	
	    		//world.world2d.implementSnakeMoveCorrectingForce(head);	    			    
	    	}
	    	
	    	if(stateHS.isSwallow)
	    		swallow();
	    	
	    	processFSkills(deltaTime);    		    			    		
    		advance(deltaTime);
	    	    
	        act_time += deltaTime;
	    }
	    
	    public void initArmor()
	    {	    	
		    int size = parts.size();	    	    
		    SnakePart part;
		    
		    for (int i = 0; i < size; ++i)
		    {
		    	part = parts.get(i);
		    	part.armors.init();
		    }
	    }
	    
	    
	    public void advance(float deltaTime) {
	    	
	        SnakePart head = parts.get(0);	             	        
	    	
	        //calculations for sinusoidal motion     
            float phaseY = (float) (this.act_time * head.stateHS.velocity * Math.PI - Math.PI/2);
            float phaseAngle = (float) (this.act_time * head.stateHS.velocity * Math.PI);
            float velAngle = 0;           
    	
	        int len = parts.size();	        
	        
	        for(int i = 0; i < len; ++i)
	        {
	        	
	            SnakePart part = parts.get(i);	        
	            
            	velAngle = (float) Math.toDegrees(part.myBody.getAngle());           	
            	deltaSin.y  = (float) Math.sin(phaseY - i) / 6;	 
            	deltaSin.x = 0;
            	deltaSin.rotate(velAngle);
            	
            	part.sinPos.x = part.position.x + deltaSin.x;
            	part.sinPos.y = part.position.y + deltaSin.y;            	            	
            	part.sinAngle = (float)  Math.sin(phaseAngle - i) * 25 + velAngle;   
	        }           
	    }
	        
		 public int setLevel(int newLevel) {
			 
		    for(int i=0; i<parts.size(); ++i)
		    	parts.get(i).setLevel(newLevel);
		    
		 	applyForceCharMovingKoef = Statics.DynamicGameObject.APPLY_FORCE_CHAR_MOVING_KOEFF_SNAKE_PINK 
		 			+ newLevel * Statics.PhysicsBox2D.APPLY_MAIN_FORCE_CHAR_MOVING_KOEFF_STEP;	
			 
			 switch(newLevel)
			 {
			 case HealthScore.LEVEL_PINK:
				 	toMiddleOfCellVelKoef = Statics.DynamicGameObject.TO_MIDDLE_OF_CELL_VELOCITY_KOEFF_SNAKE_PINK;	
				 	beforeTurnVelKoef = Statics.DynamicGameObject.BEFORE_TURN_VELOCITY_KOEFF_SNAKE_PINK;	
				 	break;
			 case HealthScore.LEVEL_GREEN:
				 	toMiddleOfCellVelKoef = Statics.DynamicGameObject.TO_MIDDLE_OF_CELL_VELOCITY_KOEFF_SNAKE_GREEN;	
				 	beforeTurnVelKoef = Statics.DynamicGameObject.BEFORE_TURN_VELOCITY_KOEFF_SNAKE_GREEN;	
					break;
			 }
			 applyForceCharMovingKoef *= Statics.PhysicsBox2D.ALL_FORCES_CHAR_MOVING_GENERAL_TUNING_KOEFF;
			 toMiddleOfCellVelKoef *= Statics.PhysicsBox2D.ALL_FORCES_CHAR_MOVING_GENERAL_TUNING_KOEFF;	
			 beforeTurnVelKoef *= Statics.PhysicsBox2D.ALL_FORCES_CHAR_MOVING_GENERAL_TUNING_KOEFF;
			 return super.setLevel(newLevel);						
		}
		 
		public void eatApple()
		{
			int size = parts.size();
			int indexPartNeedHealth = -1;
			float minHealthKoef = 1000000;
				
			for(int i = 0; i < size; ++i)
			{
				SnakePart sPart = parts.get(i);
				if(sPart.stateHS.health / sPart.stateHS.defencePower < minHealthKoef)
				{
					minHealthKoef = sPart.stateHS.health / sPart.stateHS.defencePower;
					indexPartNeedHealth = i;
				}
			}
				
			if(indexPartNeedHealth >= 0)
			{
				//add health to the most damaged part. If no, add health to the head
				if(minHealthKoef < 1)
					parts.get(indexPartNeedHealth).stateHS.health += Gifts.APPLE_HEALTH_INCREASE * parts.get(indexPartNeedHealth).stateHS.defencePower;
				else
					parts.get(0).stateHS.health += Gifts.APPLE_HEALTH_INCREASE * parts.get(0).stateHS.defencePower;

				++stateHS.healthIncrease;	//mark that some part got health
			}
		}
			
		void destroyPart(int index)
		{				
			parts.remove(index);			
		}
		
		void swallow()
		{	
		 	float dTime = world.actTime - stateHS.swallowStartTime;
		 	
		 	int size = parts.size();
		 	
		 	for(int i = 0; i < size; ++i)
		 	{
		 		SnakePart sPart = parts.get(i);
		 		float sPartTime = dTime - i * Statics.DynamicGameObject.SWALLOW_PERIOD / 2;
		 			 		
			 	if(sPartTime > 0 && sPartTime <= Statics.DynamicGameObject.SWALLOW_PERIOD)
			 	{
				 	float sclByTime = (float) Math.sin(Math.PI * sPartTime / Statics.DynamicGameObject.SWALLOW_PERIOD);
				 	float sizeIncrease = 1 + sclByTime * Statics.DynamicGameObject.SWALLOW_BODY_INCREASE;	
				 	
				 	sPart.bounds.setSize(sPart.finalBounds.width /* sizeIncrease*/, sPart.finalBounds.height * sizeIncrease);				 	
			 	}
			 	else if (sPartTime > Statics.DynamicGameObject.SWALLOW_PERIOD)	
			 	{
			 		sPart.bounds.setSize(sPart.finalBounds.width, sPart.finalBounds.height);
			 		
			 		if(i == size - 1)
			 			stateHS.isSwallow = false;
			 	}
		 	}		 	
		}
}
