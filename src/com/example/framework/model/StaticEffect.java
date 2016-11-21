package com.example.framework.model;

import java.util.Random;

import com.example.framework.model.GameObject;
import com.kingsnake.gl.ParticlesEffectsManager;
import com.kingsnake.math.EllipseTrajectory;
import com.kingsnake.math.Vector2;

public class StaticEffect extends GameObject{
	
	 static final Random rand = new Random();
	 
	 public static float ACT_SPEED = 0.1f;	 	 	 			 

	 public static final float BLOOD_SPLATTER_MEDIUM_LARGE_ACT_SPEED = 0.05f;
	 public static final float BLOOD_SPLATTER_SMALL_PERIOD = WorldKingSnake.SEPECEFFECT_ACT_SPEED * 4;
	 public static final float BLOOD_SPLATTER_MEDIUM_PERIOD = BLOOD_SPLATTER_MEDIUM_LARGE_ACT_SPEED * 12;
	 public static final float BLOOD_SPLATTER_LARGE_PERIOD = BLOOD_SPLATTER_MEDIUM_LARGE_ACT_SPEED * 12;
	 
	 public static final float BLOOD_SPLATTER_SMALL_SIZE = 1.4f;
	 public static final float BLOOD_SPLATTER_MEDIUM_SIZE = 1.8f;
	 public static final float BLOOD_SPLATTER_LARGE_SIZE = 2.2f;
	 
	 public static final float BLOOD_STAIN_SMALL_SIZE = 1.0f;
	 public static final float BLOOD_STAIN_MEDIUM_SIZE = 1.25f;
	 public static final float BLOOD_STAIN_LARGE_SIZE = 2.0f;
	 
	 
	 public static final float IMPACT_PERIOD = WorldKingSnake.SEPECEFFECT_ACT_SPEED * 10.3f;
	 public static final float IMPACT_SIZE = 1;
	 
	 public static final float BOMB_FROM_TREE_EXPLOSION_SIZE = 1.2f;
	 public static final float BOMB_FROM_TREE_EXPLOSION_PERIOD = WorldKingSnake.SEPECEFFECT_ACT_SPEED * 8;
	 
	 public static float STAR_ELLIPSE_WIDTH_PROPORTION = 1.2f;
	 public static float STAR_ELLIPSE_HEIGHT_PROPORTION = STAR_ELLIPSE_WIDTH_PROPORTION / 3;	
	 public static float STAR_ELLIPSE_HEIGHT_DISTANCE = 1.0f;	
	 public static float IMPACT_STAR_SIZE = 0.4f;
	 
	 DynamicGameObject master = null;
	 
	        
	 public StaticEffect(float x, float y, float width, float height, int type, float angle) 
	 {
		 		super(type, x, y, width, height);
		 
		 		act_time = 0;			 			 		
		 		this.position.set(x, y);
		 		this.angle = angle;	
	 }
	 
	 public StaticEffect(float x, float y, float width, float height, int type, float angle, WorldKingSnake world) 
	 {
		 		super(x, y, width, height, angle, false, type, world);
		 
		 		act_time = 0;		 			 		
		 		this.position.set(x, y);
		 		this.angle = angle;	
	 }
	 
	 public StaticEffect(float x, float y, float width, float height, int type, float angle, float lifeTime) 
	 {
	 		super(type, x, y, width, height);
	 
	 		act_time = 0;
	 		this.lifetimePeriod = lifeTime;			 			 		
	 		this.angle = angle;	 		
	 }
	 
	 
     public void update(float deltaTime) {

         act_time += deltaTime;         	 
     }
     
     public void setPermanentEffectToActiveMode(float activeModePeriod) {

         act_time = 0; 
         lifetimePeriod = activeModePeriod;       	 
     }
     
     public void setPermanentEffectToInactiveMode() {

         act_time = 0; 
         lifetimePeriod = 0;       	 
     }
     
     public boolean isPermanentEffectInActiveMode() {

         if(lifetimePeriod > 0)
        	 return true;
         else
        	 return false;
     }
     
 	/**
 	 * set lifetime to zero, so forces to be destroyed;
 	 * call inside object
 	 */	
 	public void finish ()
 	{
 		lifetimePeriod = 0;
 	}
     
     public void release() {}; 
     
     /**
      * @return master
      */
     public GameObject getMaster() 
     {
    	 return master;
     }; 

}
