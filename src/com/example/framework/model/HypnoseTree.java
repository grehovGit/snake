package com.example.framework.model;

import java.util.Random;

import com.example.framework.model.GameObject;

public class HypnoseTree extends GameObject{
	
	 public static int TO_LEFT = 1;
	 public static int TO_RIGHT = 2;	 
	
	 static final Random rand = new Random();	 	 	 
		
	static  float WORLD_WIDTH;
	static  float WORLD_HEIGHT;
	 
	 public float tModePeriod;
	 int orientation;
	 
	 public StaticEffect hypnose;
	 
	        
	 public HypnoseTree(float x, float y, float width, float height, 
			 float w_width, float w_height, int orient) 
	 {
		 		super(Statics.DynamicGameObject.TREE_HYPNOTIC, x, y, width, height);
		 
		 		WORLD_WIDTH = w_width;
		 		WORLD_HEIGHT = w_height;
		 		act_time = 0;
		 		tModePeriod = 0;
		 		orientation = orient;
		 			         
	            //this.position.set((float)Math.random() * WORLD_WIDTH,
	            //                 (float)Math.random() * WORLD_HEIGHT);
		 		
		 		this.position.set(x, y);
		 		
		 		float dirAngle = 0;
		 		
		 		if(orient == TO_RIGHT)
		 			dirAngle = 180;

	 }
	 
     public void update(float deltaTime) {   	   	          	
		
        act_time += deltaTime;
  	    tModePeriod += deltaTime;
     }

}
