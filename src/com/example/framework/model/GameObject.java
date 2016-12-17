package com.example.framework.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.kingsnake.math.Vector2;


public class GameObject {
	
	 public static final int APPLE = Statics.GameObject.APPLE;	 
	 public static final int MUSHROOM_BLUE = Statics.GameObject.MUSHROOM_BLUE;	//gifts	 
	 public static final int MUSHROOM_YELLOW = Statics.GameObject.MUSHROOM_YELLOW;	 
	 public static final int MUSHROOM_BROWN = Statics.GameObject.MUSHROOM_BROWN;	 
	 public static final int MUSHROOM_BLUE_RED = Statics.GameObject.MUSHROOM_BLUE_RED;
	 
	 public static final int BLOOD_STAIN1 = 1010;
	 public static final int BLOOD_STAIN2 = 1011;
	 public static final int BLOOD_STAIN3 = 1012;	 
	 
	 WorldKingSnake world;
	 public int type = 0;
	 public float actTime = 0;
	 public float lifetimePeriod = 0;
	 public boolean isDynamicObject = false;
	 public boolean isImmortal;
	 public Body myBody;
	
	 public Vector2 position;
	 public Rectangle bounds;
	 public Rectangle finalBounds;
	 public float angle;
	 
	 public boolean isFlaming = false;
	 public float flamePeriod = 0;
	 public int flamePower = 0;
	 public float flameStartTime = 0;
	 
	 public GameObject() {};
	 
	    public GameObject(int type) {
	        position = null;
	        bounds = null;
	        finalBounds = null;
	        angle = 0;
	        world = null;
	        myBody = null;
	        isImmortal = false;
	        this.type = type;
	    }
	 
	    public GameObject(int type, float x, float y, float width, float height) {
	        position = new Vector2(x,y);
	        bounds = new Rectangle(x-width/2, y-height/2, width, height);
	        finalBounds = new Rectangle(bounds);
	        angle = 0;
	        world = null;
	        myBody = null;
	        isImmortal = false;
	        this.type = type;
	    }
	    
	    public GameObject(float x, float y, float width, float height, float angle, boolean isDynObj, int type, WorldKingSnake world) {
	        this.position = new Vector2(x,y);
	        this.bounds = new Rectangle(x-width/2, y-height/2, width, height);
	        finalBounds = new Rectangle(bounds);
	        this.isDynamicObject = isDynObj;
	        this.angle = angle;
	        this.world = world;
	        myBody = null;
	        this.type = type;
	        isImmortal = false;
	        
	        if(!isDynObj)
	        	 createStatObjPhysicBox2d();	        	
	    }
	    
	    public void createStatObjPhysicBox2d()
	    {
	    	if(type >= Statics.Static.BEGIN_GIFTS_DIAPASON && type < Statics.Static.END_GIFTS_DIAPASON)
	    		world.world2d.createGiftObject(this);
	    	else if(type > WorldKingSnake.GRASS && type < WorldKingSnake.REILEF_END_OF_RANGE)
	    		world.world2d.createReliefObject(this);
	    }
	    
	    public void destroyPhysicBody()
	    {
	    	//if(type > APPLE && type < END_GIFTS_DIAPASON)
	    		world.world2d.destroyPhysicBody(this);
	    }
	    
	    public void setPosStatObjPhysicBox2d()
	    {
	    	if(type >= Statics.Static.BEGIN_GIFTS_DIAPASON && type < Statics.Static.END_GIFTS_DIAPASON)
	    		world.world2d.setStaticObjectPos(this);
	    }
	    
		 public boolean isGift()
		 {
			 if(type >= GameObject.APPLE && type < Statics.Static.END_GIFTS_DIAPASON)
				 return true;
			 else
				 return false;						 
		 }
		 
		 public void update (float deltaTime)
		 {
			 actTime += deltaTime;
			 
			 if (isFlaming)
				 processFlaming();
		 }
		 
		 public void processFlaming()
		 {
			 if (world.actTime - flameStartTime > flamePeriod)
				 stopFlaming();			 
		 }
		 
		 public void startFlaming(final int flameLevel)
		 {
			 isFlaming = true;
			 flameStartTime = world.actTime;
			 flamePeriod = Statics.FightingSkills.FIGHTSKILL_FIRE_FORWARD_START_PERIOD +
						flameLevel * Statics.FightingSkills.FIGHTSKILL_FIRE_FORWARD_STEP_PERIOD;
			 flamePower = flameLevel;
		 }
		 
		 /**
		  * turn off flaming flag
		  */		 
		 public void stopFlaming()
		 {
			isFlaming = false; 
		 }

}
