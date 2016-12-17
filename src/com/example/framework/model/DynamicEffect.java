package com.example.framework.model;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.kingsnake.math.Vector2;
import com.kingsnake.physicsBox2d.PhysicsBox2d;

public class DynamicEffect extends DynamicGameObject{
	
	 static final Random rand = new Random();
	 
	 public static final int ATTACK_UP_EFFECT = 2010;
	 public static final int ATTACK_DOWN_EFFECT = 2020;
	 public static final int DEFENSE_UP_EFFECT = 2030;
	 public static final int DEFENSE_DOWN_EFFECT = 2040;
	 public static final int HEALTH_UP_EFFECT = 2050;
	 public static final int HEALTH_DOWN_EFFECT = 2060;
	 public static final int SPEED_UP_EFFECT = 2070;
	 public static final int SPEED_DOWN_EFFECT = 2080;
	 public static final int GET_COINS_EFFECT = 2090;
	 public static final int LOOSE_COINS_EFFECT = 2100;
	 
	 public static float ACT_SPEED_COIN = 0.1f;	
	 public static float ACT_SPEED_BONUS = 0.15f;
	 public static float LIFETIME_PERIOD = 0.7f;
	 
	 static float G = 10.0f;
	 static float COINS_DEST_X = 11;
	 static float COINS_DEST_Y = 20;
	 static float LEVEL_UP_DEST_X = 9;
	 static float LEVEL_UP_DEST_Y = 20;
	 static float LEVEL_UP_SIZE = 1.2f;
	 static float COIN_SIZE = 1.1f;	 
	 
	 public Vector2 gravity;
	 public Vector2 move;
	 public  DynamicGameObject master = null;
	 PooledEffect particleEffect = null;
	 boolean isParticleEffect = false;
	 float power;
	 float radius;
	 
	 float data1, data2, data3, data4;
	 
	        
	 public DynamicEffect(float x, float y, float width, float height, int type, WorldKingSnake world) 
	 {
 		super(x, y, width, height, type);
 	
 		power = 0;
 		radius = 1;
 		data1 = 0;
 		data2 = 0;
 		data3 = 0;
 		data4 = 0;
 
 		actTime = 0;			 			 				 		
 		angle = 0;
 		lifetimePeriod = LIFETIME_PERIOD;			 		      
        velocity.set(0, 0);		        
 		gravity = new Vector2(0, -G); 		
 		move = new Vector2(0, 0);
 		
 		if(type == GET_COINS_EFFECT)
 			jump(COINS_DEST_X, COINS_DEST_Y);
 		else
 			jump(LEVEL_UP_DEST_X, LEVEL_UP_DEST_Y);		 		
	 }
	 
	 public DynamicEffect(float x, float y, float width, float height, float angle_, float lifeTime, int type, DynamicGameObject master_) 
	 {
		 super(x, y, width, height, angle_, type, master_.world);
		 
		 power = 0;
	 	 radius = 1;
	 	 data1 = 0;
	 	 data2 = 0;
	 	 data3 = 0;	
	 	 data4 = 0;
		 actTime = 0;			 			 				 		
		 lifetimePeriod = lifeTime;		
		 angle = angle_;
		 master = master_;
		 
		 switch(type)
		 {
		 case Statics.DynamicGameObject.FSKILL_FORWARD_ATTACK_IMPULSE_EFFECT:
				 velocity.set(Statics.FightingSkills.IMPULSE_ATTACK_FORWARD_VELOCITY, 0);
				 radius = Statics.PhysicsBox2D.SENSOR_FIGHT_SKILL_FORWARD_ATTACK_RADIUS;
				 break;
		 case Statics.DynamicGameObject.FSKILL_FORWARD_ATTACK_ICE_EFFECT:
				 velocity.set(Statics.FightingSkills.ICE_ATTACK_FORWARD_VELOCITY, 0);
				 radius = Statics.PhysicsBox2D.SENSOR_FIGHT_SKILL_FORWARD_ATTACK_RADIUS;
				 break;
		 case Statics.DynamicGameObject.FSKILL_FORWARD_ATTACK_HYPNOSE_EFFECT:
				 velocity.set(Statics.FightingSkills.HYPNOSE_ATTACK_FORWARD_VELOCITY, 0);
				 radius = Statics.PhysicsBox2D.SENSOR_FIGHT_SKILL_FORWARD_HYPNOSE_ATTACK_RADIUS;	
				 break;
		 case Statics.DynamicGameObject.FSKILL_FORWARD_ATTACK_FIRE_EFFECT:
			 velocity.set(Statics.FightingSkills.FIRE_ATTACK_FORWARD_VELOCITY, 0);
			 radius = Statics.PhysicsBox2D.SENSOR_FIGHT_SKILL_FORWARD_HYPNOSE_ATTACK_RADIUS;	
			 break;
		 }

		 
    	 if (Statics.DynamicGameObject.isDynamicEffectFskillForwardAttck(type))
    	 {
			 PhysicsBox2d.setBodyFilter(master.fixtureGroupFilterId, myBody);  
			 velocity.rotate(angle);
			 myBody.setLinearVelocity(velocity.x, velocity.y);
    	 }
	 }
	 
	 void setDataForwardAttackImpulse(float workRadius, float fSkillLevel, float masterAttackPower)
	 {
		 power = fSkillLevel;
		 data1 = workRadius;
		 data2 = masterAttackPower;
	 }
	 
	 public void setDataForwardAttackPower(float fSkillLevel)
	 {
		 power = fSkillLevel;
	 }
	 
	 
	 public float getPower()
	 {
		 return power;
	 }
     
     public void update(float deltaTime) 
     {
    	 if(objType== Statics.DynamicGameObject.FSKILL_FORWARD_ATTACK_HYPNOSE_EFFECT)
    		 objType *= 1;
    	 
		 if(Statics.DynamicGameObject.isDynamicEffectWithSensor(objType))
		 {
			 super.update(deltaTime); 
			 switch(objType)
			 {
				 case Statics.DynamicGameObject.FSKILL_FORWARD_ATTACK_IMPULSE_EFFECT:		 
		    		 if(lifetimePeriod <= 0)
		    			 processImpulse();
		    		 break;	
		    	 case Statics.DynamicGameObject.FSKILL_FORWARD_ATTACK_ICE_EFFECT:		 
		    		 if(lifetimePeriod <= 0)
		    			 processIce();
		    		 break;
			 }
		 }
		 else
		 {
			velocity.add(gravity.x * deltaTime, gravity.y * deltaTime);   		    		
		    position.add(velocity.x * deltaTime, velocity.y * deltaTime);    	        
		    
		    if(lifetimePeriod <= 0)
		    {	        	        	
		        this.velocity.set(0, 0);
		        this.gravity.set(0, G);	            	          	           
		    }
		     actTime += deltaTime;        
		     lifetimePeriod -= deltaTime;   
		 }
		 
		 if(isParticleEffect && particleEffect != null)
			 particleEffect.setPosition(position.x, position.y);			   	    	
     } 
     
     
     public void jump(float xNext, float yNext) {
	  		
 		newPos.set(xNext, yNext);
 		    		
 		move.set(newPos);    		
 		move.sub(position);   		
 		
 		float dist = move.len();
		
		
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
	   gravity.set(0, G);
	   float tJump = 0;
	    		
	   if(moveAngle != 90 && moveAngle != 270)	   		//non vertical moving 		
	   {
	    	vel = (float) Math.sqrt(dist * G / Math.sin(2 * ballisticRadians));
	    	velocity.set((float)(Math.cos(ballisticRadians) * vel), (float)-(Math.sin(ballisticRadians) * vel));
	    			
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
	    	velocity.set(0, -vel);			//vertical moving
	    			
		    //set jump period
	    	tJump = (float) velocity.len()/ G;
	   }	
	   else if(moveAngle == 270)
	   {
	    	vel = (float) Math.sqrt(2 * dist * G);
	    	velocity.set(0, vel);			//vertical moving
	    			
		    //set jump period
	    	tJump = (float) velocity.len()/ G;
	   }
	   
	   if(type == DynamicEffect.GET_COINS_EFFECT || type == DynamicEffect.LOOSE_COINS_EFFECT)
		   lifetimePeriod = tJump;
	   else if(lifetimePeriod > tJump)
		   lifetimePeriod = tJump;
 	}  
     
    void processImpulse()
    {
		Vector2 offset = new Vector2(-0.5f, 0);
		offset.rotate(angle);
		
		world.wProc.createStaticEffect(position.x + offset.x, position.y + offset.y, 2 * data1, 2 * data1, angle, Statics.StaticEffect.IMPACT,
				Statics.Render.SPECEFFECT_IMPACT_PERIOD, 0, null);
		
		world.world2d.fSkillImpulse(Statics.FightingSkills.FIGHTSKILL_IMPULSE_POWER_STEP * power /*impulsePower*/,
			 	data2 /*attackPower TEMPORARY*/, data1 /*workDistance*/, this);
    }
    
    void processIce()
    {
    	float iceballRadius = Statics.FightingSkills.FIGHTSKILL_ICE_FORWARD_START_RADIUS + 
				 Statics.FightingSkills.FIGHTSKILL_ICE_FORWARD_STEP_RADIUS * power;     	
		world.wProc.createDynObject(position.x, position.y, 2 * iceballRadius, 2 * iceballRadius, angle, Statics.DynamicGameObject.FSKILL_ICEBALL, (int) power);
    }
    
}
