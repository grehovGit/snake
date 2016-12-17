package com.example.framework.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.util.Log;

import com.badlogic.gdx.physics.box2d.World;
import com.kingsnake.gl.FPSCounter;
import com.kingsnake.gl.ParticlesEffectsManager;
import com.kingsnake.math.Vector2;
import com.kingsnake.physicsBox2d.PhysicsBox2d;

public class WorldKingSnake {
	
		static float TIME_STEP = 1/30f;
	 	public static final int WORLD_WIDTH = Statics.WKS.WORLD_WIDTH;
	    public static final int WORLD_HEIGHT = Statics.WKS.WORLD_HEIGHT;
	    static final int WORLD_DEPTH = Statics.WKS.WORLD_DEPTH;
	    public static final int CELL_DIM = Statics.WKS.CELL_DIM;
	    public static final float CELL_SIZE = Statics.WKS.CELL_SIZE;
	    static float G = 10;
	    
	    public static final float SEPECEFFECT_ACT_SPEED = 0.07f;	
	    
	    public static final int WORLD_RELIEF_LEVEL = 0;
	    static final int WORLD_RELIEF_STATE_LEVEL = 1;
	    static final int WORLD_STATIC_OBJECTS_LEVEL = 2;
	    static final int WORLD_DYNAMIC_OBJECTS_LEVEL = 3;
	    public static final int WORLD_LAB_ORIENT_WEIGHTS_LEVEL = 4;
	    
	    //relief level = 0
	    public static final int WALL = 1;
	    public static final int WALL_DELAY = 1;
	    
	    public static final int GREEN_TREE = 2;
	    static final int GREEN_TREE_DELAY = 2;
	    static final int GREEN_TREE_DAMAGE = 1;
	    
	    public static final int YELLOW_TREE = 3;
	    static final int YELLOW_TREE_DELAY = 3;
	    static final int YELLOW_TREE_DAMAGE = 3;
	    
	    public static final int BROWN_TREE = 4;
	    static final int BROWN_TREE_DELAY = 5;
	    static final int BROWN_TREE_DAMAGE = 5;
	    
	    public static final int ARROW_STONE = 5;
	    static final int ARROW_STONE_DELAY = 10;
	    
	    public static final int DEAD_STONE1 = 6;
	    static final int DEAD_STONE1_DELAY = 1;
	    static final int DEAD_STONE1_DAMAGE = 10;
	    
	    public static final int DEAD_STONE2 = 7;
	    static final int DEAD_STONE2_DELAY = 1;
	    static final int DEAD_STONE2_DAMAGE = 20;
	    
	    public static final int DEAD_STONE3 = 8;
	    static final int DEAD_STONE3_DELAY = 1;
	    static final int DEAD_STONE3_DAMAGE = 30;	    
	    
	    public static final int SAND = 20;
	    static final int SAND_VEL_DECREESE = 2;
	    static final float SAND_FF = 08f;		//friction factor
	    
	    public static final int SWAMP = 21;
	    static final int SWAMP_VEL_DECREESE = 3;
	    static final float SWAMP_FF = 0.9f;		//friction factor
	    
	    public static final int WATER = 22;	
	    static final int WATER_VEL_DECREESE = 2;	
	    public static final int WATER_DAMAGE = 10;	
	    static final float WATER_FF = 0.2f;		//friction factor
	  
	    public static final int GRASS = 0;
	    static final float GRASS_FF = 0.5f;	//friction factor

	    public static final int REILEF_END_OF_INSURMOUNTABLE_RANGE = 19;
	    public static final int REILEF_END_OF_RANGE = 100;
	    
		public static int CHARACTER_APPERIANCE_PERIOD_30 = 30;
		public static int CHARACTER_APPERIANCE_PERIOD_60 = 60;
		public static int CHARACTER_APPERIANCE_PERIOD_90 = 60;
		public static int CHARACTER_APPERIANCE_PERIOD_180 = 180;
	    
	    //0 - relief; 1 - relief state/animation; 2 - static objects; 3 - dynamic objects
	    //4 - frogs;
	    public int fields[][][] = new int[WORLD_DEPTH][WORLD_WIDTH * CELL_DIM][WORLD_HEIGHT * CELL_DIM];
	    Random random = new Random();
	    private float accumulator;
	    
	    public WorldProcessing wProc;
	    public CharacterMind mind;
	    public World world;
	    public PhysicsBox2d world2d;
	    public DynamicGameObject character;

	    public List<DynamicGameObject> dynEffectsAbove;
	    public List<DynamicGameObject> flyingObjects;
	    public List<DynamicGameObject> dynEffectsAboveObjects;
	    public List<GameObject> statEffectsAbove;
	    public List<DynamicGameObject> dynObjects;
	    public List<GameObject> statObjects;
	    public List<GameObject> relief;
	    public List<GameObject> bloodStains;

	    boolean mushroomFlag;
	    boolean characterFlag;
	    
	    public boolean gameOver = false;;
	    public int score = 0;
	    
	    int updateTime;	//remember the time of logic updating issue	  
	    public float actTime;
	    public float logicUpdateTime;
	    
		FPSCounter fps;
        int[] logCoords;
	    
	    public WorldKingSnake(World world_) {
	    	
	    	world = world_;
	    	world2d = new PhysicsBox2d(world_, this);
	    	world2d.initPhysicWorld_FirstHunting();
	    	
	    	//our logic world
	        for(int i=0; i < WORLD_WIDTH * CELL_DIM; i++)
	        	for(int j=0; j < WORLD_HEIGHT * CELL_DIM; j++)
	        		for(int z=0; z < WORLD_DEPTH; z++)
	        			fields[z][i][j] = 0;
	        
	        dynEffectsAbove = new ArrayList<DynamicGameObject>();
	        dynEffectsAboveObjects = new ArrayList<DynamicGameObject>();
	        
	        flyingObjects = new ArrayList<DynamicGameObject>();
	        flyingObjects.add(new BombTree(5, 5, WORLD_WIDTH, WORLD_HEIGHT, this));	
	        
	        statEffectsAbove = new ArrayList<GameObject>();	        
	        relief = new ArrayList<GameObject>();	  
	        dynObjects = new ArrayList<DynamicGameObject>();
	        
	        character = new Snake(10.5f, 15.5f, WORLD_WIDTH, WORLD_HEIGHT, Snake.RIGHT, HealthScore.LEVEL_PINK, this);
	        dynObjects.add(character);
	        character.stateHS.isBot = false;
	        
	        //SNAKE BOT
	        //dynObjects.add(new Snake(10.5f, 17.5f, WORLD_WIDTH, WORLD_HEIGHT, Snake.RIGHT, HealthScore.LEVEL_PINK, this));	        	        

	        
	        statObjects = new ArrayList<GameObject>();
	        statObjects.add(new Gifts(0, 0, 1, 1, GameObject.APPLE, this));	        	 	        
	        ((Gifts)statObjects.get(0)).isEaten = true;	        
	        mushroomFlag = true;
	        characterFlag = true;
	        
	        bloodStains = new ArrayList<GameObject>();
	        
	        actTime = 0;
	        updateTime = 0;	
	        logicUpdateTime = 0;
	        
	        wProc = (WorldProcessing) new WorldProcessingImpl(this);
	        mind = new CharacterMind(this);	        
	        
	        fps = new FPSCounter();
	        accumulator = 0;
	        logCoords = new int[CELL_DIM * CELL_DIM * 2];
	        
	        placeMap();
	    }
	    
	    void placeMap()
	    {
	        wProc.placeRelief();
	        
	        int nLogPos = WorldKingSnake.CELL_DIM * WorldKingSnake.CELL_DIM * 2;
	        for(int i = 0; i < 2; i++)
	        {
		        if(!wProc.placeObjectToGreedByCoords(3.5f, 4.5f + i, WorldKingSnake.WORLD_DYNAMIC_OBJECTS_LEVEL, DynamicGameObject.BLOCK_WOOD, logCoords))			       
		        	continue;
		        dynObjects.add(new DynamicGameObject(3.5f, 4.5f + i, DynamicGameObject.BLOCK_SIZE, DynamicGameObject.BLOCK_SIZE, 0, DynamicGameObject.BLOCK_WOOD, this));	
	        }
	        dynObjects.add(new DynamicGameObject(4.5f, 13.5f, DynamicGameObject.BLOCK_SIZE, DynamicGameObject.BLOCK_SIZE, 0, DynamicGameObject.BLOCK_WOOD, this));	
	        dynObjects.add(new DynamicGameObject(6.5f, 12.5f, DynamicGameObject.BLOCK_SIZE, DynamicGameObject.BLOCK_SIZE, 0, DynamicGameObject.BLOCK_WOOD, this));	
	        dynObjects.add(new DynamicGameObject(5.5f, 12.5f, DynamicGameObject.BLOCK_SIZE, DynamicGameObject.BLOCK_SIZE, 0, DynamicGameObject.BLOCK_WOOD, this));	
	        dynObjects.add(new DynamicGameObject(4.5f, 12.5f, DynamicGameObject.BLOCK_SIZE, DynamicGameObject.BLOCK_SIZE, 0, DynamicGameObject.BLOCK_WOOD, this));	
	    	        
	        //dynObjects.add(new DynamicGameObject(5.5f, 6.5f, DynamicGameObject.BLOCK_SIZE, DynamicGameObject.BLOCK_SIZE, 0, DynamicGameObject.BLOCK_WOOD, this));	
	        //dynObjects.add(new DynamicGameObject(6.5f, 6.5f, DynamicGameObject.BLOCK_SIZE, DynamicGameObject.BLOCK_SIZE, 0, DynamicGameObject.BLOCK_WOOD, this));	
	        //dynObjects.add(new DynamicGameObject(7.5f, 6.5f, DynamicGameObject.BLOCK_SIZE, DynamicGameObject.BLOCK_SIZE, 0, DynamicGameObject.BLOCK_WOOD, this));	
	        //dynObjects.add(new DynamicGameObject(5.5f, 7.5f, DynamicGameObject.BLOCK_SIZE, DynamicGameObject.BLOCK_SIZE, 0, DynamicGameObject.BLOCK_WOOD, this));	
	        //dynObjects.add(new DynamicGameObject(6.5f, 7.5f, DynamicGameObject.BLOCK_SIZE, DynamicGameObject.BLOCK_SIZE, 0, DynamicGameObject.BLOCK_WOOD, this));	
	        //dynObjects.add(new DynamicGameObject(7.5f, 7.5f, DynamicGameObject.BLOCK_SIZE, DynamicGameObject.BLOCK_SIZE, 0, DynamicGameObject.BLOCK_WOOD, this));	

	        
	        if(!wProc.placeObjectToGreedByCoords(4.5f, 5.5f, WorldKingSnake.WORLD_DYNAMIC_OBJECTS_LEVEL, DynamicGameObject.BLOCK_MARBLE, logCoords))			       
	        	return;
	        dynObjects.add(new DynamicGameObject(4.5f, 5.5f, DynamicGameObject.BLOCK_SIZE, DynamicGameObject.BLOCK_SIZE, 0, DynamicGameObject.BLOCK_MARBLE, this));	
	    }
	    
	    private void dynamicEffectsAboveUpdate(float deltaTime) {
	    	
	    	for(int i = 0; i < dynEffectsAbove.size(); ++i)
	    	{
	    		DynamicEffect dynObj = (DynamicEffect) dynEffectsAbove.get(i);	    			    			    	    

	    		//process dynamic effects
	    		if(dynObj.objType >= Statics.DynamicGameObject.BEGIN_DYNAMIC_EEFFECTS_RANGE && dynObj.objType <= Statics.DynamicGameObject.END_DYNAMIC_EEFFECTS_RANGE)
	    		{
	    			 dynObj.update(deltaTime);	    			

	    			if(dynObj.lifetimePeriod <= 0)
	    			{    				
	    				dynEffectsAbove.remove(i);
	    				--i;
	    			}	    			
	    		}
	    	}
	    }
	    
	    private void dynamicEffectsAboveObjectsUpdate(float deltaTime) {
	    	
	    	for(int i = 0; i < dynEffectsAboveObjects.size(); ++i)
	    	{
	    		DynamicEffect dynEffect = (DynamicEffect) dynEffectsAboveObjects.get(i);	    			    			    	    

	    		//process dynamic effects
	    		if(dynEffect.objType >= Statics.DynamicGameObject.BEGIN_DYNAMIC_EEFFECTS_RANGE && dynEffect.objType <= Statics.DynamicGameObject.END_DYNAMIC_EEFFECTS_RANGE)
	    		{
	    			dynEffect.update(deltaTime);	    			

	    			if(dynEffect.lifetimePeriod <= 0)
	    			{
	    				if(Statics.DynamicGameObject.isDynamicEffectWithSensor(dynEffect.objType))
	    					dynEffect.destroyPhysicBody();
	    				if(dynEffect.isParticleEffect && dynEffect.particleEffect != null)
	    					ParticlesEffectsManager.releaseEffectByValue(dynEffect.particleEffect);
	    				
	    				dynEffectsAboveObjects.remove(i);
	    				--i;
	    			}	    			
	    		}
	    	}
	    }
	    
	    private void statEffectsAboveUpdate(float deltaTime) {
	    	
	    	for(int i = 0; i < statEffectsAbove.size(); i++)
	    	{
	    		StaticEffect effect = (StaticEffect) statEffectsAbove.get(i);	    			    			    	    

	    		//process static effects
	    		if (effect.type >= Statics.StaticEffect.IMPACT && effect.type <= Statics.StaticEffect.TIME_LIMITED_EFFECTS_RANGE_END)
	    		{	    			
	    			effect.update(deltaTime);

	    			if (effect.actTime >=  effect.lifetimePeriod
	    					|| (effect.getMaster() != null && effect.getMaster().isDynamicObject 
	    												   && ((DynamicGameObject)effect.getMaster()).stateHS.isDead == HealthScore.DEAD_DELETED))
	    			{    				
	    				effect.release();
	    				statEffectsAbove.remove(i);
	    				--i;
	    			}	  
	    		}
	    	}
	    	
	    	//update relief effects
	    	for(int i = 0; i < relief.size(); i++)
	    	{
	    		StaticEffect sObj = (StaticEffect) relief.get(i);	    			    			    	    

	    		if(sObj.isPermanentEffectInActiveMode())
	    		{
	    			sObj.update(deltaTime);	    			

	    			if(sObj.actTime >=  sObj.lifetimePeriod)
	    				sObj.setPermanentEffectToInactiveMode();   			
	    		}
	    	}
	    }
	   
	    	   
	    
	    private void statObjectsUpdate(float deltaTime) {
	    	
	    	for(int i = 0; i < statObjects.size(); i++)
	    	{
	    		GameObject sObj = statObjects.get(i);
	    		
	    		//shaking
	    		if(sObj.type >= Statics.Static.BEGIN_GIFTS_DIAPASON && sObj.type <= Statics.Static.END_GIFTS_DIAPASON)
	    		{
	    			if(((Gifts)sObj).shakeTime < Gifts.SHAKE_DURATION && ((Gifts)sObj).shakeTime > 0)
	    			{
			            float shakeX = 0.08f * (float)Math.sin(12 * Math.PI * ((Gifts)sObj).shakeTime);
			            float shakeY = 0.1f * (float)Math.cos(12 * Math.PI * ((Gifts)sObj).shakeTime);
	    				sObj.bounds.width = Gifts.GIFT_SIZE + shakeX;
			            sObj.bounds.height = Gifts.GIFT_SIZE + shakeY;

	    			}
	    		}
	    		
		    	//process apple
	    		if(sObj.type == GameObject.APPLE)
	    		{
			    	Gifts apple = (Gifts)sObj;
			    	apple.update(deltaTime);
			    	
			    	if(apple.isEaten)
			    	{
			    		wProc.deleteCellContent((int)apple.position.x, (int)apple.position.y, WorldKingSnake.WORLD_STATIC_OBJECTS_LEVEL);
			    		
				        int appleX = random.nextInt(WORLD_WIDTH);
				        int appleY = random.nextInt(WORLD_HEIGHT);
				        int attempts = WORLD_WIDTH * WORLD_HEIGHT;
				        
				        while (true) {
				            if (wProc.isEmptyCell(appleX, appleY, WORLD_RELIEF_LEVEL) && wProc.isEmptyCell(appleX, appleY, WORLD_STATIC_OBJECTS_LEVEL) &&
				            		wProc.isEmptyCell(appleX, appleY,  WORLD_DYNAMIC_OBJECTS_LEVEL))
				                break;
				            
				            appleX += 1;
				            --attempts;
				            if (appleX >= WORLD_WIDTH) {
				                appleX = 0;
				                appleY += 1;
				                --attempts;
				                if (appleY >= WORLD_HEIGHT) {
				                    appleY = 0;
				                }
				            }
				            
				            if(attempts < 0)
				            	break;
				        }
				        
				        if(attempts >= 0)
				        {
				        	apple.Set((float) appleX, (float) appleY, GameObject.APPLE);	
				        	wProc.placeObjectToCell(appleX, appleY, WorldKingSnake.WORLD_STATIC_OBJECTS_LEVEL, GameObject.APPLE);
				        	apple.isEaten = false;
				        }
				        	
				        
			    	}
	    		}	    			    	    	
	    		//process mashrooms
	    		else if(sObj.type >= Statics.Static.BEGIN_MUSHROOM_DIAPASON && sObj.type <= Statics.Static.END_MUSHROOM_DIAPASON)
	    		{
	    			((Gifts)sObj).update(deltaTime);
	    		
		    		if(((Gifts)sObj).isEaten)
		    			if(wProc.deleteCellContent((int)sObj.position.x,(int)sObj.position.y, WorldKingSnake.WORLD_STATIC_OBJECTS_LEVEL))
		    			{	
		    				((Gifts)sObj).destroyPhysicBody();
			    			statObjects.remove(i);
			    			--i;
		    			}
		    		
	    		}
	    		/*else if(sObj.type == GameObject.BOMB_FROM_TREE)
	    		{
	    			((Gifts)sObj).update(deltaTime);
	    		
		    		if(((Gifts)sObj).isEaten)
		    			if(wProc.deleteSubObjectFromGreedByCoords(sObj.position.x, sObj.position.y, WorldKingSnake.WORLD_STATIC_OBJECTS_LEVEL))
		    			{
		    				((Gifts)sObj).destroyPhysicBody();
		    				statObjects.remove(i);
		    				--i;
		    				statEffectsAbove.add(new StaticEffect(sObj.position.x, sObj.position.y, StaticEffect.BOMB_FROM_TREE_EXPLOSION_SIZE,
		    						StaticEffect.BOMB_FROM_TREE_EXPLOSION_SIZE, GameObject.BOMB_FROM_TREE_EXPLOSION, 0, StaticEffect.BOMB_FROM_TREE_EXPLOSION_PERIOD, null));
		    			}
	    		}*/
	    		//process static effects
	    		else if(sObj.type >= Statics.StaticEffect.BLOOD_SPLATTER_SMALL && sObj.type < Statics.StaticEffect.IMPACT_STARS)
	    		{
	    			((StaticEffect) sObj).update(deltaTime);	    			

	    			if(((StaticEffect) sObj).actTime >= ((StaticEffect) sObj).lifetimePeriod)
	    			{
	    				statObjects.remove(i);
	    				--i;
	    			}
	    			
	    		}
	    	}
	    		
	    	
		    if(updateTime % Gifts.MUSHROOM_PERIOD == 0 && mushroomFlag)
		    {
			    int mushroomX = random.nextInt(WORLD_WIDTH);
			    int mushroomY = random.nextInt(WORLD_HEIGHT);
			    int attempts = WORLD_WIDTH * WORLD_HEIGHT;
			    mushroomFlag = false;
			        
			    while (true) 
			    {
			       if (wProc.isEmptyCell(mushroomX, mushroomY, WORLD_RELIEF_LEVEL) && wProc.isEmptyCell(mushroomX, mushroomY, WORLD_STATIC_OBJECTS_LEVEL) &&
			            	wProc.isEmptyCell(mushroomX, mushroomY, WORLD_DYNAMIC_OBJECTS_LEVEL))
			           break;
			            
			       mushroomX += 1;
			       --attempts;
			       if (mushroomX >= WORLD_WIDTH) {
			           mushroomX = 0;
			           mushroomY += 1;
			           --attempts;
			           if (mushroomY >= WORLD_HEIGHT) {
			               mushroomY = 0;
			           }
			       }
			            
			       if(attempts < 0)
			            	break;
			   }
			        
			   if(attempts >= 0)
			   {
			        int type = (int)(Gifts.MUSHROOM_BLUE_CHANCE + Gifts.MUSHROOM_YELLOW_CHANCE + Gifts.MUSHROOM_BRED_CHANCE
			        		+ Gifts.MUSHROOM_BROWN_CHANCE);
			        			
			        int mushroomType = random.nextInt(type);
			        	
			        if(mushroomType >= 0 && mushroomType <= Gifts.MUSHROOM_BLUE_CHANCE)
			        	mushroomType = Gifts.MUSHROOM_BLUE;
			        else if(mushroomType > Gifts.MUSHROOM_BLUE_CHANCE && mushroomType <= Gifts.MUSHROOM_BLUE_CHANCE + Gifts.MUSHROOM_YELLOW_CHANCE)
			        	mushroomType = Gifts.MUSHROOM_YELLOW;
			        else if(mushroomType > Gifts.MUSHROOM_BLUE_CHANCE + Gifts.MUSHROOM_YELLOW_CHANCE && 
			        		mushroomType <= Gifts.MUSHROOM_BLUE_CHANCE + Gifts.MUSHROOM_YELLOW_CHANCE + Gifts.MUSHROOM_BRED_CHANCE)
			        	mushroomType = Gifts.MUSHROOM_BLUE_RED;
			        else if(mushroomType > Gifts.MUSHROOM_BLUE_CHANCE + Gifts.MUSHROOM_YELLOW_CHANCE + Gifts.MUSHROOM_BRED_CHANCE)
			        	mushroomType = Gifts.MUSHROOM_BROWN;
			        
			        if(wProc.placeObjectToCell(mushroomX, mushroomY, WorldKingSnake.WORLD_STATIC_OBJECTS_LEVEL, mushroomType))			       
			        	statObjects.add(new Gifts((float) mushroomX, (float) mushroomY, Gifts.GIFT_SIZE, Gifts.GIFT_SIZE, mushroomType, this));
			    }		        		      		        
			    
		    }
		    	
		    if(updateTime % Gifts.MUSHROOM_PERIOD > 1 && !mushroomFlag)
		    	mushroomFlag = true;
	    }
	    
	    //place characters to the WorldKingSnake
	    public void dynObjectsDispatch(float deltaTime) {

		    if(updateTime % CHARACTER_APPERIANCE_PERIOD_180 == 0 && characterFlag)
		    {
			    int charX = random.nextInt(WORLD_WIDTH);
			    int charY = random.nextInt(WORLD_HEIGHT);
			    int attempts = WORLD_WIDTH * WORLD_HEIGHT;
			    characterFlag = false;
			        
			    while (attempts < 0) 
			    {
			       if (wProc.isEmptyCell(charX, charY, WORLD_RELIEF_LEVEL) && wProc.isEmptyCell(charX, charY, WORLD_STATIC_OBJECTS_LEVEL) &&
			            	wProc.isEmptyCell(charX, charY, WORLD_DYNAMIC_OBJECTS_LEVEL))
			           break;
			            
			       charX += 1;
			       --attempts;
			       if (charX >= WORLD_WIDTH) {
			           charX = 0;
			           charY += 1;
			           --attempts;
			           if (charY >= WORLD_HEIGHT) {
			               charY = 0;
			           }
			       }
			   }
			      
			   if(attempts >= 0)
			   {		        			
			        int charType = random.nextInt(3) + 2;
			        
			        if(!wProc.placeObjectToGreedByCoords(charX + 0.5f, charY + 0.5f, WorldKingSnake.WORLD_DYNAMIC_OBJECTS_LEVEL, charType, logCoords))			       
			        	return;
			        			        
			        /*switch(charType)
			        {
			        case DynamicGameObject.FROG:
				        dynObjects.add(new Frog((float) charX + 0.5f, (float) charY + 0.5f, Frog.WIDTH, Frog.HEIGHT, this));
				        break;
			        case DynamicGameObject.LEMMING:
				        dynObjects.add(new Lemming((float) charX + 0.5f, (float) charY + 0.5f, Lemming.WIDTH, Lemming.HEIGHT, this));
				        break;
			        case DynamicGameObject.HEDGEHOG:
				        dynObjects.add(new Hedgehog((float) charX+ 0.5f, (float) charY+ 0.5f, Hedgehog.WIDTH, Hedgehog.HEIGHT, this));
				        break;
			        }*/
			        
			       //dynObjects.add(new Lemming((float) 1.5f, (float) 8.5f, Lemming.WIDTH, Lemming.HEIGHT, 90, HealthScore.LEVEL_GREEN, this));
			        dynObjects.add(new Lemming((float) 4.5f, (float) 6.5f, Lemming.WIDTH, Lemming.HEIGHT, 90, HealthScore.LEVEL_GREEN, this));

			        
			        //move coords from temp massive to object's massive
			        int nLogPos = WorldKingSnake.CELL_DIM * WorldKingSnake.CELL_DIM * 2;
			        DynamicGameObject dynObj = dynObjects.get(dynObjects.size() - 1);
			        
			        //helm
			        dynObjects.add(new DynamicGameObject(4.5f, 6.5f, 1, 1, 0, Statics.DynamicGameObject.HELM_VIKING_WOODEN, this));


			        //spear
			        DynamicGameObject spear = new DynamicGameObject(4.5f, 6.5f, 1, 2, 0, Statics.DynamicGameObject.SPEAR_VIKING_BRONZE_WOODEN, this);
			        dynObjects.add(spear);

			        //axe 
			        //DynamicGameObject axe = new DynamicGameObject(4.5f, 6.5f, 1, 1, 0, Statics.DynamicGameObject.AXE_VIKING_MONOSIDE_BRONZE_WOODEN, this);
			        //dynObjects.add(axe);
			        
			        //sword
			        DynamicGameObject sword = new DynamicGameObject(4.5f, 6.5f, 1, 1, 0, Statics.DynamicGameObject.SWORD_VIKING_BRONZE_WOODEN, this);
			        dynObjects.add(sword);			        
			        
			        //DynamicGameObject axe2 = new DynamicGameObject(5.5f, 5.5f, 1, 1, 0, Statics.DynamicGameObject.AXE_VIKING_DOUBLESIDE_BRONZE_WOODEN, this);
			        //dynObjects.add(axe2);			        
			        
			        //shield
			        DynamicGameObject shield = new DynamicGameObject(4.5f, 6.5f, 1, 1, 0, Statics.DynamicGameObject.SHIELD_VIKING_WOODEN, this);
			        dynObjects.add(shield);
			        
			        //seed
			        Factories.FactoryProducer.getFactory(Factories.FACTORY_DYNAMIC_OBJECTS).
			        getDynamicObject(Statics.DynamicGameObject.SEED_HURT, 10, 3, 0, 0, 0, 0, this);			       
			        
			        Factories.FactoryProducer.getFactory(Factories.FACTORY_DYNAMIC_OBJECTS).
			        getDynamicObject(Statics.DynamicGameObject.TREE_HURT, 8, 10, 0, 0, 0, 1, this);			        

			    }		        		      		        
			    
		    }
		    	
		    if(updateTime % CHARACTER_APPERIANCE_PERIOD_30 > 1 && !characterFlag)
		    	characterFlag = true;
	    	
	    }
	    
	    
	    public void dynObjectsUpdate(float deltaTime) {	    	
	    
            for(int i=0; i < dynObjects.size(); ++i)
            {
            	DynamicGameObject dynObj = dynObjects.get(i);  
            	
            	if (dynObj == null)
            		dynObj = null;
            	
            	//some simple dyn objects, like dead parts
            	if(dynObj.stateHS == null)
            	{
               	 	dynObj.update(deltaTime);
               	 	continue;
            	}
        	
            	if(dynObj.stateHS.healthDecrease > 0)
            	{      		
            		dynObj.stateHS.health -= dynObj.stateHS.healthDecrease;
            		
            		
            		if(dynObj.stateHS.health <= 0)
                    	dynObj.stateHS.isDead = HealthScore.DEAD_DELETED;
            		
        			//decide how much blood we need
            		if(dynObj.isCharacter)
            		{
            			if(dynObj.objType == Statics.DynamicGameObject.SNAKE)
            			{
            				dynObj.stateHS.healthDecrease = 0;
            				int size = ((Snake)dynObj).parts.size();
            				
            				for(int j = 0; j < size; ++j)
            				{
            					SnakePart sPart = ((Snake)dynObj).parts.get(j);
            					
                        		sPart.stateHS.health -= sPart.stateHS.healthDecrease;                      
    		            		
                        		if(sPart.stateHS.health <= 0)
                        		{
                                	sPart.stateHS.isDead = HealthScore.DEAD_DELETED; 
                                	
                                	if(sPart.index <= Statics.DynamicGameObject.SNAKE_SECOND_PART_INDEX ||
                                			dynObj.stateHS.isDead == HealthScore.DEAD_SHOWN)
                                	//if died right now or was dead before
                                		dynObj.stateHS.isDead = HealthScore.DEAD_DELETED;
                                	else
                                		dynObj.stateHS.isDead = HealthScore.DEAD_SOME_MY_PART_DEAD;
                        		}                       		
            					wProc.bloodProcess(sPart);
            					sPart.stateHS.healthDecrease = 0;           					
            				}
            			}
            			else
            				wProc.bloodProcess(dynObj);
            		}

        			
        	        //change flags for textures
        			/*if((float) (dObj.stateHS.health / dObj.stateHS.defencePower) < HealthScore.WOUND_STRONG_LEVEL )
        				dObj.stateHS.isWounded = HealthScore.WOUND_STRONG;
        			else if((float) (dObj.stateHS.health / dObj.stateHS.defencePower) < HealthScore.WOUND_EASY_LEVEL )
        				dObj.stateHS.isWounded = HealthScore.WOUND_EASY;*/
        			
            		dynObj.stateHS.healthDecrease = 0;
            	}
            	
            	if(dynObj.stateHS.isHeated)
            	{
            		//create impact effect once
            		if(dynObj.stateHS.hitPeriod  == dynObj.stateHS.hitPeriodFull)
            		{           			
	            		
	            		if(dynObj.stateHS.isDead == HealthScore.ALIVE && dynObj.isCharacter)
	            		{
		            		float impSize = (float) Math.log10(dynObj.stateHS.attackPower);
		            		wProc.createStaticEffect(dynObj.position.x, dynObj.position.y, StaticEffect.IMPACT_SIZE + impSize, StaticEffect.IMPACT_SIZE + impSize, dynObj.angleContact, Statics.StaticEffect.IMPACT, StaticEffect.IMPACT_PERIOD, 0, null);	

		            		if(!dynObj.stateHS.heatStarsOn)
		            		{
		            			wProc.createStaticEffect(dynObj.position.x, dynObj.position.y, 1, 1, dynObj.velocity.angle(), Statics.StaticEffect.IMPACT_STARS, dynObj.stateHS.hitPeriod, 0, dynObj);	
		            			dynObj.stateHS.heatStarsOn = true;
		            		}
	            		}
            		}

            		
            		dynObj.stateHS.hitPeriod -= deltaTime;            	            
            		
            		if(dynObj.stateHS.hitPeriod < 0)
            		{
            			dynObj.stateHS.hitPeriod = 0;            			               			
            			dynObj.stateHS.isHeated = false; 
            			dynObj.stateHS.heatFinish = true;
            			dynObj.stateHS.hitPeriodFull = -1;
            			dynObj.stateHS.heatStarsOn = false;
          			
            		}            	
            	}
            	if(dynObj.stateHS.isHypnosed)
            	{
            		dynObj.stateHS.hypPeriod -= deltaTime;
            		
            		if(dynObj.stateHS.hypPeriod < 0)
            		{
            			dynObj.stateHS.hypPeriod = 0;
            			dynObj.stateHS.isHypnosed = false;
            		}
            	}
            	
            	if(dynObj.stateHS.attackPowerIncrease != 0)
            	{
            		dynObj.stateHS.attackPower += dynObj.stateHS.attackPowerIncrease;
            		
            		if(dynObj.stateHS.attackPowerIncrease > 0)
            			dynEffectsAbove.add(new DynamicEffect(dynObj.position.x, dynObj.position.y, 
            					DynamicEffect.LEVEL_UP_SIZE, DynamicEffect.LEVEL_UP_SIZE, DynamicEffect.ATTACK_UP_EFFECT, this));
            		else
            			dynEffectsAbove.add(new DynamicEffect(dynObj.position.x, dynObj.position.y, 
            					DynamicEffect.LEVEL_UP_SIZE, DynamicEffect.LEVEL_UP_SIZE, DynamicEffect.ATTACK_DOWN_EFFECT, this));

            		dynObj.stateHS.attackPowerIncrease = 0;
            		
            	}
            	
            	if(dynObj.stateHS.defencePowerIncrease != 0)
            	{
            		dynObj.stateHS.defencePower += dynObj.stateHS.defencePowerIncrease;
            		
            		if(dynObj.stateHS.defencePowerIncrease > 0)
            			dynEffectsAbove.add(new DynamicEffect(dynObj.position.x, dynObj.position.y, 
            					DynamicEffect.LEVEL_UP_SIZE, DynamicEffect.LEVEL_UP_SIZE, DynamicEffect.DEFENSE_UP_EFFECT, this));
            		else
            			dynEffectsAbove.add(new DynamicEffect(dynObj.position.x, dynObj.position.y, 
            					DynamicEffect.LEVEL_UP_SIZE, DynamicEffect.LEVEL_UP_SIZE, DynamicEffect.DEFENSE_DOWN_EFFECT, this));

            		dynObj.stateHS.defencePowerIncrease = 0;
            		
            	}
            	
            	if(dynObj.stateHS.velocityIncrease!= 0)
            	{
            		dynObj.stateHS.velocity += dynObj.stateHS.velocityIncrease;
            		
            		if(dynObj.stateHS.velocityIncrease > 0)
            			dynEffectsAbove.add(new DynamicEffect(dynObj.position.x, dynObj.position.y, 
            					DynamicEffect.LEVEL_UP_SIZE, DynamicEffect.LEVEL_UP_SIZE, DynamicEffect.SPEED_UP_EFFECT, this));
            		else
            			dynEffectsAbove.add(new DynamicEffect(dynObj.position.x, dynObj.position.y, 
            					DynamicEffect.LEVEL_UP_SIZE, DynamicEffect.LEVEL_UP_SIZE, DynamicEffect.SPEED_DOWN_EFFECT, this));

            		dynObj.stateHS.velocityIncrease = 0;
            		
            	}
            	
            	if(dynObj.stateHS.healthIncrease != 0)
            	{
            		if(dynObj.objType != Statics.DynamicGameObject.SNAKE)
            			dynObj.stateHS.health += dynObj.stateHS.healthIncrease;
            		
            		if(dynObj.stateHS.healthIncrease > 0)
            			dynEffectsAbove.add(new DynamicEffect(dynObj.position.x, dynObj.position.y, 
            					DynamicEffect.LEVEL_UP_SIZE, DynamicEffect.LEVEL_UP_SIZE, DynamicEffect.HEALTH_UP_EFFECT, this));
            		else
            			dynEffectsAbove.add(new DynamicEffect(dynObj.position.x, dynObj.position.y, 
            					DynamicEffect.LEVEL_UP_SIZE, DynamicEffect.LEVEL_UP_SIZE, DynamicEffect.HEALTH_DOWN_EFFECT, this));

            		dynObj.stateHS.healthIncrease = 0;            		
            	}
            	
            	if(dynObj.stateHS.coinsIncrease != 0)
            	{
            		dynObj.stateHS.coins += dynObj.stateHS.coinsIncrease;

            		if(dynObj.stateHS.coinsIncrease > 0)
	            		dynEffectsAbove.add(new DynamicEffect(dynObj.position.x, dynObj.position.y, 
	            				DynamicEffect.LEVEL_UP_SIZE, DynamicEffect.COIN_SIZE, DynamicEffect.GET_COINS_EFFECT, this));
            		else
	            		dynEffectsAbove.add(new DynamicEffect(dynObj.position.x, dynObj.position.y, 
	            				DynamicEffect.LEVEL_UP_SIZE, DynamicEffect.COIN_SIZE, DynamicEffect.LOOSE_COINS_EFFECT, this));

            		
            		dynObj.stateHS.coinsIncrease = 0;            		
            	}
         	
              	//this block for snake, if some part is dead
            	if(dynObj.objType == Statics.DynamicGameObject.SNAKE)
            	{
            		if(dynObj.stateHS.isDead == HealthScore.DEAD_DELETED)
            		{       				
        				for(int j = 0; j < ((Snake)dynObj).parts.size(); ++j)
        				{
        					SnakePart sPart = ((Snake)dynObj).parts.get(j);
        						            		
                    		if(sPart.stateHS.isDead == HealthScore.DEAD_DELETED)
                    		{
                        	//snake part is dead
                        		wProc.deleteObjectFromGreedByCoords(sPart.position.x, sPart.position.y,
                        				WorldKingSnake.WORLD_DYNAMIC_OBJECTS_LEVEL, dynObj.objType);
                        		sPart.destroyPhysicBody(); 
                        		((Snake)dynObj).destroyPart(j);
                        		--j;
                    		}  
                    		else
                    			sPart.stateHS.isDead = HealthScore.DEAD_SHOWN;
        				} 
        				
        				dynObj.stateHS.isDead = HealthScore.DEAD_SHOWN;  
        				
        				if(((Snake)dynObj).parts.size() == 0)
        				{
        				//let's kill snake
        	            	dynObjects.remove(i);
        	            	--i;            	
        	            	continue;        					
        				}
            		}
            		else if (dynObj.stateHS.isDead == HealthScore.DEAD_SOME_MY_PART_DEAD)
            		{
        				boolean isDeadShown = false;
        				
        				for(int j = 0; j < ((Snake)dynObj).parts.size(); ++j)
        				{
        					SnakePart sPart = ((Snake)dynObj).parts.get(j);
        						            		
                    		if(sPart.stateHS.isDead == HealthScore.DEAD_DELETED)
                    		{
                        	//snake part is dead snake is alive
                        		wProc.deleteObjectFromGreedByCoords(sPart.position.x, sPart.position.y,
                        				WorldKingSnake.WORLD_DYNAMIC_OBJECTS_LEVEL, dynObj.objType);
                        		sPart.destroyPhysicBody(); 
                        		((Snake)dynObj).destroyPart(j);
                        		--j;
                        		//other parts are cut off and aren't alive
                        		isDeadShown = true;
                    		}  
                    		else if(isDeadShown)
                    			sPart.stateHS.isDead = HealthScore.DEAD_SHOWN;
        				} 
        				
        				dynObj.stateHS.isDead = HealthScore.ALIVE;
            		}           		
            	}
            	else if(dynObj.stateHS.isDead == HealthScore.DEAD_DELETED)
            	{
		    		if(dynObj.objType == DynamicGameObject.BOMB_FROM_TREE)
		    			wProc.createStaticEffect(dynObj.position.x, dynObj.position.y, StaticEffect.BOMB_FROM_TREE_EXPLOSION_SIZE,
		    					StaticEffect.BOMB_FROM_TREE_EXPLOSION_SIZE, 0, Statics.StaticEffect.BOMB_FROM_TREE_EXPLOSION, StaticEffect.BOMB_FROM_TREE_EXPLOSION_PERIOD, 0, null);

            		wProc.deleteObjectFromGreedByCoords(dynObj.position.x, dynObj.position.y,
            				WorldKingSnake.WORLD_DYNAMIC_OBJECTS_LEVEL, dynObj.objType);
            		dynObj.destroyPhysicBody();
	            	dynObjects.remove(i);
	            	--i;            	
	            	continue;
            	} 
            	
	            switch(dynObj.objType)
	            {
		            case DynamicGameObject.SNAKE:
				         Snake snake = (Snake) dynObj;	            		
				         snake.update(deltaTime);
				         break;
		            case DynamicGameObject.FROG:
		            	 Frog frog = (Frog) dynObj;
		            	 frog.update(deltaTime);
		            	 break;
		            case DynamicGameObject.LEMMING:
		            	 Lemming lemming = (Lemming) dynObj;
		            	 lemming.update(deltaTime);
		            	 break; 
		            case DynamicGameObject.HEDGEHOG:
		            	 Hedgehog hedgehog = (Hedgehog) dynObj;
		            	 hedgehog.update(deltaTime);
		            	 break; 
		            default: 
		            	 dynObj.update(deltaTime);
	            }
            }
	    }
	    
	    public void flyingObjectsUpdate(float deltaTime) {
	    	
		    
            for(int i=0; i < flyingObjects.size(); i++)
            {
            	DynamicGameObject dynObj = flyingObjects.get(i);
            	
            	if(dynObj.stateHS.healthDecrease > 0)
            	{
        	    	Log.d("WKS", String.valueOf(actTime));
        	    	
            		dynObj.stateHS.health -= dynObj.stateHS.healthDecrease;
            		
            		if(dynObj.stateHS.health <= 0)
                    	dynObj.stateHS.isDead = HealthScore.DEAD_DELETED;
            		
        			//decide how much blood we need
        			if(dynObj.stateHS.healthDecrease >= HealthScore.BLOOD_LARGE_LEVEL)	//>= 3000
        			{
                   		statObjects.add(new StaticEffect(dynObj.position.x, dynObj.position.y,
                    			StaticEffect.BLOOD_SPLATTER_LARGE_SIZE + dynObj.stateHS.healthDecrease / 3000,
                    			StaticEffect.BLOOD_SPLATTER_LARGE_SIZE + dynObj.stateHS.healthDecrease / 3000, Statics.StaticEffect.BLOOD_SPLATTER_BIG, dynObj.velocity.angle(), StaticEffect.BLOOD_SPLATTER_LARGE_PERIOD));
                   		
                   		bloodStains.add(new StaticEffect(dynObj.position.x, dynObj.position.y,
                    			StaticEffect.BLOOD_STAIN_LARGE_SIZE,
                    			StaticEffect.BLOOD_STAIN_LARGE_SIZE, GameObject.BLOOD_STAIN1 + random.nextInt(2), random.nextInt(360)));
        			}
        			else if(dynObj.stateHS.healthDecrease >= HealthScore.BLOOD_MEDIUM_LEVEL)	//100-2999
        			{
                		statObjects.add(new StaticEffect(dynObj.position.x, dynObj.position.y,
                    			StaticEffect.BLOOD_SPLATTER_MEDIUM_SIZE + dynObj.stateHS.healthDecrease / 3000,
                    			StaticEffect.BLOOD_SPLATTER_MEDIUM_SIZE + dynObj.stateHS.healthDecrease / 3000, Statics.StaticEffect.BLOOD_SPLATTER_MEDIUM, dynObj.velocity.angle(), StaticEffect.BLOOD_SPLATTER_MEDIUM_PERIOD));
                		
                   		bloodStains.add(new StaticEffect(dynObj.position.x, dynObj.position.y,
                    			StaticEffect.BLOOD_STAIN_MEDIUM_SIZE,
                    			StaticEffect.BLOOD_STAIN_MEDIUM_SIZE, GameObject.BLOOD_STAIN1 + random.nextInt(2), random.nextInt(360)));
        			}
        			else if(dynObj.stateHS.healthDecrease >= HealthScore.BLOOD_SMALL_LEVEL) //1-99
        			{
                		statObjects.add(new StaticEffect(dynObj.position.x, dynObj.position.y,
                			StaticEffect.BLOOD_SPLATTER_SMALL_SIZE + dynObj.stateHS.healthDecrease / 50,
                			StaticEffect.BLOOD_SPLATTER_SMALL_SIZE + dynObj.stateHS.healthDecrease / 50, Statics.StaticEffect.BLOOD_SPLATTER_SMALL, dynObj.velocity.angle(), StaticEffect.BLOOD_SPLATTER_SMALL_PERIOD));   
                		
                		if(dynObj.stateHS.healthDecrease >= HealthScore.BLOOD_MEDIUM_LEVEL / 4)
	                   		bloodStains.add(new StaticEffect(dynObj.position.x, dynObj.position.y,
	                    			StaticEffect.BLOOD_STAIN_SMALL_SIZE,
	                    			StaticEffect.BLOOD_STAIN_SMALL_SIZE, GameObject.BLOOD_STAIN1 + random.nextInt(3), random.nextInt(360)));
        			}       		
        			
            		dynObj.stateHS.healthDecrease = 0;
            	}
            	

            	if(dynObj.stateHS.isHypnosed)
            	{
            		dynObj.stateHS.hypPeriod -= deltaTime;
            		
            		if(dynObj.stateHS.hypPeriod < 0)
            		{
            			dynObj.stateHS.hypPeriod = 0;
            			dynObj.stateHS.isHypnosed = false;
            		}
            	}

            	switch(dynObj.objType)
            	{        		
	            	case DynamicGameObject.TREE_BOMB:
			            BombTree bTree = (BombTree) dynObj;
		        		bTree.update(deltaTime);
			            break;	
	            	case DynamicGameObject.BOMB_FALL_ACTION:
	            		dynObj.update(deltaTime);
	            		if(dynObj.actTime > BombTree.BOMB_EXIST_TIME)
	            			wProc.placeTreeBomb(dynObj);
	            		break;
            	}
            	
            	if(dynObj.stateHS.isDead == HealthScore.DEAD_DELETED)
            	{
            		flyingObjects.remove(i);
            		--i;          		
            	}
            	
            }         
	    }
	 
	    
	    public void doPhysicsStep(float deltaTime) {
	    	
	        // fixed time step
	        // max frame time to avoid spiral of death (on slow devices)
	        float frameTime = Math.min(deltaTime, 0.25f);
	        accumulator += frameTime;
	        while (accumulator >= TIME_STEP) {
	        	world.step(TIME_STEP, 6, 2);
	        	update(TIME_STEP);
	        	world2d.updatePhysics(TIME_STEP);
	            accumulator -= TIME_STEP;
	        }
	    }

	    
	    public void update(float deltaTime) {
	    	
            actTime += deltaTime;
            updateTime = (int) actTime; 
            logicUpdateTime = actTime;          
            
            dynamicEffectsAboveUpdate(deltaTime);
            flyingObjectsUpdate(deltaTime); 
            dynamicEffectsAboveObjectsUpdate(deltaTime);
            statEffectsAboveUpdate(deltaTime);
            dynObjectsUpdate(deltaTime);            
            statObjectsUpdate(deltaTime); 
	    	dynObjectsDispatch(deltaTime);	//create new characters
	    }
	    	    
}
