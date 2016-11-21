package com.example.framework.model;

import java.util.Random;

import com.example.framework.model.GameObject;

public class Gifts extends GameObject{
	
	
	 static final Random rand = new Random();
	 public static float ACT_SPEED = 0.07f;
	 public static float APPERAIANCE_PERIOD = ACT_SPEED * 14;
	 public static float GIFT_SIZE = 1;
	 public static float SHAKE_PERIOD = 6;
	 public static float SHAKE_DURATION = 0.333f;
	 
	 
	 public static final float APPLE_HEALTH_INCREASE = 0.1f;
	 
	 public static final int MUSHROOM_PERIOD = 60;	
	 public static final float MUSHROOM_BLUE_CHANCE = 2;
	 public static final float MUSHROOM_YELLOW_CHANCE = 4;
	 public static final float MUSHROOM_BROWN_CHANCE = 3;
	 public static final float MUSHROOM_BRED_CHANCE = 1;
	 
	 public static final int MUSHROOM_BLUE_ATTACK = 1;
	 public static final float MUSHROOM_BLUE_ATTACK_CHANCE = 2;
	 public static final float MUSHROOM_BLUE_ATTACK_AMOUNT = 0.2f;
	 public static final int MUSHROOM_BLUE_DEFENSE = 2;
	 public static final float MUSHROOM_BLUE_DEFENSE_CHANCE = 2;
	 public static final float MUSHROOM_BLUE_DEFENSE_AMOUNT = 0.2f;
	 public static final int MUSHROOM_BLUE_SPEED = 3;
	 public static final float MUSHROOM_BLUE_SPEED_CHANCE = 2;
	 public static final float MUSHROOM_BLUE_SPEED_AMOUNT = 0.2f;
	 public static final int MUSHROOM_BLUE_HEALTH = 4;
	 public static final float MUSHROOM_BLUE_HEALTH_CHANCE = 2;
	 public static final float MUSHROOM_BLUE_HEALTH_AMOUNT = 0.5f;
	 public static final int MUSHROOM_BLUE_COINS = 5;
	 public static final float MUSHROOM_BLUE_COINS_CHANCE = 2;
	 public static final int MUSHROOM_BLUE_COINS_AMOUNT = 100;
	 
	 public static final float MUSHROOM_BROWN_ACTIONS_AMOUNT = 5;
	 public static final int MUSHROOM_BROWN_ATTACK = 1;
	 public static final float MUSHROOM_BROWN_ATTACK_CHANCE = 2;
	 public static final float MUSHROOM_BROWN_ATTACK_AMOUNT = 0.5f;	 
	 public static final int MUSHROOM_BROWN_DEFENSE = 2;
	 public static final float MUSHROOM_BROWN_DEFENSE_CHANCE = 2;
	 public static final float MUSHROOM_BROWN_DEFENSE_AMOUNT = 0.5f;	 
	 public static final int MUSHROOM_BROWN_SPEED = 3;
	 public static final float MUSHROOM_BROWN_SPEED_CHANCE = 2;
	 public static final float MUSHROOM_BROWN_SPEED_AMOUNT = 0.3f;	 
	 public static final int MUSHROOM_BROWN_HEALTH = 4;
	 public static final float MUSHROOM_BROWN_HEALTH_CHANCE = 2;
	 public static final float MUSHROOM_BROWN_HEALTH_AMOUNT = 0.7f;	 
	 public static final int MUSHROOM_BROWN_COINS = 5;
	 public static final float MUSHROOM_BROWN_COINS_CHANCE = 2;
	 public static final int MUSHROOM_BROWN_COINS_AMOUNT = 500;
	 
	 public static final float MUSHROOM_YELLOW_COINS = 20;
	 
	 public static final float BOMB_FROM_TREE_DAMAGE = 0.25f;
	 public static boolean BOMB_FROM_TREE_READY = false;
	 public boolean isEaten;	
	 public float shakeTime;
	 Random random = new Random();
	        
	 public Gifts(float x, float y, float width, float height, int type, WorldKingSnake world) 
	 {
		 		super(x, y, width, height, 0, false, type, world);
		 
		 		act_time = 0;
		 		shakeTime = SHAKE_PERIOD;
		 		isEaten = false;	
		 		isImmortal = true;
	 }
	 
	 public void Set(float x, float y, int type) 
	 {
		 
		 		this.type = type;
		 		act_time = 0;
		 		shakeTime = SHAKE_PERIOD;
		 		isEaten = false;
		 			         
	            this.position.set(x, y);
	            this.setPosStatObjPhysicBox2d();
	            
	 }
	 
	 public int getBrownMushroomAction() 
	 {
		 int chanceSum = (int) (MUSHROOM_BROWN_ATTACK_CHANCE + MUSHROOM_BROWN_DEFENSE_CHANCE + MUSHROOM_BROWN_SPEED_CHANCE +
				 MUSHROOM_BROWN_HEALTH_CHANCE + MUSHROOM_BROWN_COINS_CHANCE);
		 
		 int actionType = random.nextInt(chanceSum);
     	
	        if(actionType < Gifts.MUSHROOM_BROWN_ATTACK_CHANCE)
	        	actionType = Gifts.MUSHROOM_BROWN_ATTACK;
	        else{
	        	actionType -= MUSHROOM_BROWN_ATTACK_CHANCE;
	        	
		        if(actionType < Gifts.MUSHROOM_BROWN_DEFENSE_CHANCE)
		        	actionType = Gifts.MUSHROOM_BROWN_DEFENSE;
		        else{
		        	actionType -= MUSHROOM_BROWN_DEFENSE_CHANCE;
		        	
			        if(actionType < Gifts.MUSHROOM_BROWN_SPEED_CHANCE)
			        	actionType = Gifts.MUSHROOM_BROWN_SPEED;
			        else{
			        	actionType -= MUSHROOM_BROWN_SPEED_CHANCE;	
			        	
				        if(actionType < Gifts.MUSHROOM_BROWN_HEALTH_CHANCE)
				        	actionType = Gifts.MUSHROOM_BROWN_HEALTH;
				        else{
				        	actionType -= MUSHROOM_BROWN_HEALTH_CHANCE;	
				        	
					        if(actionType < Gifts.MUSHROOM_BROWN_COINS_CHANCE)
					        	actionType = Gifts.MUSHROOM_BROWN_COINS;
					        else{
					        	actionType = Gifts.MUSHROOM_BROWN_ATTACK;
					        }
				        }
			        }
		        }
	        }	       
	        
	     return actionType;       
	 }
	 
	 public int getBlueMushroomAction() 
	 {
		 int chanceSum = (int) (MUSHROOM_BLUE_ATTACK_CHANCE + MUSHROOM_BLUE_DEFENSE_CHANCE + MUSHROOM_BLUE_SPEED_CHANCE +
				 MUSHROOM_BLUE_HEALTH_CHANCE + MUSHROOM_BLUE_COINS_CHANCE);
		 
		 int actionType = random.nextInt(chanceSum);
     	
	        if(actionType < Gifts.MUSHROOM_BLUE_ATTACK_CHANCE)
	        	actionType = Gifts.MUSHROOM_BLUE_ATTACK;
	        else{
	        	actionType -= MUSHROOM_BLUE_ATTACK_CHANCE;
	        	
		        if(actionType < Gifts.MUSHROOM_BLUE_DEFENSE_CHANCE)
		        	actionType = Gifts.MUSHROOM_BLUE_DEFENSE;
		        else{
		        	actionType -= MUSHROOM_BLUE_DEFENSE_CHANCE;
		        	
			        if(actionType < Gifts.MUSHROOM_BLUE_SPEED_CHANCE)
			        	actionType = Gifts.MUSHROOM_BLUE_SPEED;
			        else{
			        	actionType -= MUSHROOM_BLUE_SPEED_CHANCE;	
			        	
				        if(actionType < Gifts.MUSHROOM_BLUE_HEALTH_CHANCE)
				        	actionType = Gifts.MUSHROOM_BLUE_HEALTH;
				        else{
				        	actionType -= MUSHROOM_BLUE_HEALTH_CHANCE;	
				        	
					        if(actionType < Gifts.MUSHROOM_BLUE_COINS_CHANCE)
					        	actionType = Gifts.MUSHROOM_BLUE_COINS;
					        else{
					        	actionType = Gifts.MUSHROOM_BLUE_ATTACK;
					        }
				        }
			        }
		        }
	        }	       
	        
	     return actionType;       
	 }
	 
     public void update(float deltaTime) {

         act_time += deltaTime;
         shakeTime -= deltaTime;
         
         if(shakeTime < 0)
         {
        	 shakeTime = SHAKE_PERIOD;
        	 bounds.width = GIFT_SIZE;
        	 bounds.height = GIFT_SIZE;
         }
         
     }

}
