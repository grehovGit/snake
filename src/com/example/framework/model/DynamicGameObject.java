package com.example.framework.model;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.ObjectMap.Entry;
import com.kingsnake.math.Vector2;


public class DynamicGameObject extends GameObject
{
	public static final int DEAD_PART_VISIBLE_STILL = Statics.DynamicGameObject.DEAD_PART_VISIBLE_STILL;
	public static final int SNAKE = Statics.DynamicGameObject.SNAKE;
	public static final int LEMMING = Statics.DynamicGameObject.LEMMING;
	public static final int HEDGEHOG = Statics.DynamicGameObject.HEDGEHOG;
	public static final int FROG = Statics.DynamicGameObject.FROG;
	public static final int FISH = Statics.DynamicGameObject.FISH;
	
	public static final int BLOCK_WOOD =  Statics.DynamicGameObject.BLOCK_WOOD;
	public static final int BLOCK_MARBLE =  Statics.DynamicGameObject.BLOCK_MARBLE;

	public static final int BOMB_FROM_TREE = Statics.DynamicGameObject.BOMB_FROM_TREE;
	public static final int TREE_HYPNOTIC= Statics.DynamicGameObject.TREE_HYPNOTIC;
	
	public static float BLOCK_SIZE = 1;
	
	//not in dynamic objects level
	public static final int BOMB_FALL_ACTION = Statics.DynamicGameObject.BOMB_FALL_ACTION;
	public static final int TREE_BOMB = Statics.DynamicGameObject.TREE_BOMB;
		
    public static int LOG_POS_CENTER = WorldKingSnake.CELL_DIM * WorldKingSnake.CELL_DIM - 1;
	public static final float TREE_BOMB_POWER = 1;
		
    public HealthScore stateHS;
    public boolean isCharacter;
    public boolean isPartOfBody = false;
    public int goalMode;
    public int globalGoal;
    public float globalGoalweight;
	
	public Vector2 velocity;
	public Vector2 accel;
	public Vector2 oldPos;
	public Vector2 newPos;	
	public Vector2 forcePoint;	//to render force point for debug	
	public int[] renderParts;	//if renderParts[i] == 0 => according fixture is destroyed
    public int isMoving;  
    public int isMovingOld; 
    
	public boolean biteAndHoldMode;
   
    //variables for character correct physics moving
    public float xNextTurn, yNextTurn, xOldTurn, yOldTurn;
    public boolean isTurn;
    public boolean isPlaceToCellCenterMovingMode;
	public float toMiddleOfCellVelKoef = 0;	
	public float beforeTurnVelKoef = 0;	
	public float applyForceCharMovingKoef = 0;	
    public List <Vector2> correctForces;
    
    public FightingSkills fSkills;
	
	//variable for contact effects
	float angleContact;
	public float weaponPenetrationWidth;
	
	//variables for box2d phyiscs
	public short fixtureGroupFilterId;
	 
	 public int objType;
	 private int team;
	 public GameObject objTarget, objLocalTarget;
	 
	 public DynamicGameObject() {};
	
	 public DynamicGameObject(float x, float y, float width, float height, float angle, int type, WorldKingSnake world) {
	        super(x, y, width, height, angle, true, 0, world);	        
	        init(x, y, type, HealthScore.LEVEL_PINK);
	    }
	 
	 public DynamicGameObject(float x, float y, float width, float height, float angle, int type, int level, WorldKingSnake world) {
	        super(x, y, width, height, angle, true, 0, world);	        
	        init(x, y, type, level);
	    }
	 
	 private void init(float x, float y, int type, int level)
	 {
	        velocity = new Vector2();
	        accel = new Vector2();
	        objType = type;
	        team = type;
	        stateHS = new HealthScore(level, type);
	        oldPos = new Vector2();
	        newPos = new Vector2();
	        forcePoint = new Vector2();
	        objTarget = null;
	        objLocalTarget = null;	        
	    	renderParts = new int[Statics.PhysicsBox2D.MAX_BODY_PARTS_COUNT];
	    	
	    	for(int i = 0; i < Statics.PhysicsBox2D.MAX_BODY_PARTS_COUNT; ++i)
	    		renderParts[i] = 1;
	        
	 		isMovingOld = isMoving = CharacterMind.STAY;
	 		isTurn = false;
	 		xNextTurn = x;
	 		yNextTurn = y;
	 		xOldTurn = x;
	 		yOldTurn = y;
	 	    isPlaceToCellCenterMovingMode = false;
	 	    isCharacter = false;
	 		biteAndHoldMode = false;
	 		angleContact = 0;
	        correctForces = new ArrayList<Vector2>();
	        goalMode = Statics.AI.MODE_FREE_DEFAULT;
	        globalGoal = Statics.GameObject.APPLE;
	        globalGoalweight = Statics.AI.GOAL_WEIGHT_DEFAULT;
	        fixtureGroupFilterId = 0;
	        weaponPenetrationWidth = 100;
	 		
	 		if(objType == TREE_BOMB)
	 			isImmortal = true;
	 	    
	 		createDynObjPhysicBox2d();
	        fSkills = new FightingSkills(this);		 
	 }
	 
	 public DynamicGameObject(float x, float y, float width, float height, int type) {
	        super(type, x, y, width, height);
	        velocity = new Vector2();
	        accel = new Vector2();
	        objType = type;
	        objTarget = null;
	        oldPos = new Vector2();
	        newPos = new Vector2();
	    }
	 
	 public DynamicGameObject(float x, float y, float width, float height, int type, int level) {
		 	this(x, y, width, height, type);
	        stateHS = new HealthScore(level, type);
	    }
	 
	 public void SetObjectType(int type) {
			 	this.objType = type;
	    }
	 
	 public void SetMoveAngle(float angle) {
		 
		velocity.set(stateHS.velocity , 0);
		velocity.rotate(angle);	
	 }
	 
	 public void SetMoveMode(int isMoving) {
		 
		this.isMoving = isMoving;
	 }
	 	 
	 public void SetVelocity(float x, float y) {
			 	this.velocity.x = x;
			 	this.velocity.y = y;
	    }
	 
	 public void SetObjTarget(GameObject gObj) {
		 objTarget = gObj;
	 }
	 
	 public void setFriend(DynamicGameObject friend, int typeFriendship) {
		 switch (typeFriendship) {
		 case HealthScore.FREND_BYRACE:
			 stateHS.frendByRace = true;
		 case HealthScore.FREND_TOALIENRACE:
			 stateHS.frendtoAlienRace = friend.objType;
		 case HealthScore.FREND_TOTEAM:
			 stateHS.frendToTeam = friend.team;
		 default:
				 assert false : "illegal frienship type";
		 }
	 }
	 
	 public void unsetFriendship(int typeFriendship) {
		 switch (typeFriendship) {
		 case HealthScore.FREND_BYRACE:
			 stateHS.frendByRace = false;
		 case HealthScore.FREND_TOALIENRACE:
			 stateHS.frendtoAlienRace = 0;
		 case HealthScore.FREND_TOTEAM:
			 stateHS.frendToTeam = 0;
		 default:
			 assert false : "illegal frienship type";
		 }
	 }
	 
	 public void update(float deltaTime) {	
		 
		 super.update(deltaTime);
		 
		 if(isPartOfBody)
			 return;
		  	
		if((objType >= Statics.DynamicGameObject.BEGIN_DYN_OBJS_RANGE && objType <= Statics.DynamicGameObject.END_DYN_OBJS_RANGE) 
			|| objType >= Statics.DynamicGameObject.BEGIN_DYNAMIC_EEFFECTS_WITH_SENSOR_RANGE && objType <= Statics.DynamicGameObject.END_DYNAMIC_EEFFECTS_WITH_SENSOR_RANGE
			|| objType == DEAD_PART_VISIBLE_STILL)
		{
		 	position.x = myBody.getPosition().x;
		 	position.y = myBody.getPosition().y;
		 	angle = (float) Math.toDegrees(myBody.getAngle());
		 	
		 	if(objType == DEAD_PART_VISIBLE_STILL && lifetimePeriod < 0)
		 		stateHS.isDead = HealthScore.DEAD_DELETED;
		 	
		 	if(isCharacter)
		 	{		 	
			 	if(stateHS.isSwallow && objType > Statics.DynamicGameObject.SNAKE)
			 	{
			 		float dTime = world.actTime - stateHS.swallowStartTime;
			 		float sclByTime = (float) Math.sin(Math.PI * dTime / Statics.DynamicGameObject.SWALLOW_PERIOD);
			 		float sizeIncrease = 1 + sclByTime * Statics.DynamicGameObject.SWALLOW_BODY_INCREASE;
			 		bounds.setSize(finalBounds.width * sizeIncrease, finalBounds.height);
			 		
			 		if(stateHS.swallowStartTime +  Statics.DynamicGameObject.SWALLOW_PERIOD <= world.actTime)
			 		{
			 			stateHS.isSwallow = false;
			 			bounds.set(finalBounds);
			 		}
			 	}			 
			 	processFSkills(deltaTime);
		 	}
		}
		else
		{
		    this.position.x += velocity.x * deltaTime;
		    this.position.y += velocity.y * deltaTime;	    		
		}	
		
		if(isFlaming)
			processFlamingDamage(deltaTime);
			
		lifetimePeriod -= deltaTime;
	 }
	 
	 void processFSkills(float deltaTime)
	 {
	    //update and implement fSkills. Deykstra must be provided first 
		if(fSkills != null)
		{
			fSkills.update(world.actTime, deltaTime);
			
			if(stateHS.isBot )
				world.mind.processTargets(this);
		}		 
	 }
	 
	 boolean isActive()
	 {
		 if(!stateHS.isStriking && !stateHS.isHeated && !stateHS.isHypnosed)
			 return true;
		 else
			 return false;
	 }
	 
	 public int setLevel(int newLevel) {
			
			int oldLevel = stateHS.level;
			stateHS.SetLevel(newLevel);
			return oldLevel;		
		}
	 
	 public void createDynObjPhysicBox2d()
	 {
	    	if(objType > Statics.DynamicGameObject.SNAKE && objType <= Statics.DynamicGameObject.END_OF_CHARACTERS_RANGE)
	    		world.world2d.createCharObject(this);
	    	else if(objType >= Statics.DynamicGameObject.BEGIN_OF_PASSIVE_DYN_OBJS_RANGE && objType <= Statics.DynamicGameObject.END_DYN_OBJS_RANGE)
	    		world.world2d.createDynObject(this);
	    	else if(Statics.DynamicGameObject.isDynamicEffectWithSensor(objType))
	    		world.world2d.createDynEffectSensor((DynamicEffect)this, ((DynamicEffect)this).radius, objType);
	 }
	 
	 public boolean isSeparetable() {
			
			if(objType >= Statics.DynamicGameObject.BEGIN_OF_PASSIVE_DYN_OBJS_SEPARATABLE_RANGE && objType <= Statics.DynamicGameObject.END_OF_PASSIVE_DYN_OBJS_SEPARATABLE_RANGE)	
				return true;
			else
				return false;
		}
	 
	 public boolean isBreakable() {
			
			if(objType >=  Statics.DynamicGameObject.BEGIN_OF_PASSIVE_DYN_OBJS_BREAKABLE_ATONCE_RANGE && objType <= Statics.DynamicGameObject.END_OF_PASSIVE_DYN_OBJS_BREAKABLE_ATONCE_RANGE)	
				return true;
			else
				return false;
		}
	 
	 public boolean isBreakableCircle() {
			
			if(objType >=  Statics.DynamicGameObject.BEGIN_OF_BREAKABLE_CIRCLE_RANGE&& objType <= Statics.DynamicGameObject.END_OF_BREAKABLE_CIRCLE_RANGE)	
				return true;
			else
				return false;
		}
	 
	 public boolean isDynamicEffectWithSensor() {
			
			if(objType >=  Statics.DynamicGameObject.BEGIN_DYNAMIC_EEFFECTS_WITH_SENSOR_RANGE && objType <= Statics.DynamicGameObject.END_DYNAMIC_EEFFECTS_WITH_SENSOR_RANGE)	
				return true;
			else
				return false;
		}
	 
	 public boolean isWeapon() {
			
			if(objType >=  Statics.DynamicGameObject.BEGIN_OF_WEAPON_RANGE && objType <= Statics.DynamicGameObject.END_OF_WEAPON_RANGE)	
				return true;
			else
				return false;
		}
	 
	 public void removeCorrectForce(int dir) {
			
			boolean removedForce = false;
			int size = correctForces.size();
			int i = 0;
			
			while(!removedForce && i < size)
			{
				if(correctForces.get(i).y == dir)
				{
					correctForces.remove(i);
					removedForce = true;
				}
				++i;
			}
		}
	 
	 public int getCorrectForceDir(float actTime) 
	 {		
		int dir = 0;
			
		for(int i = 0; i < correctForces.size(); ++i)
		{
			if(correctForces.get(i).x <= actTime)
			{
				correctForces.remove(i);
				--i;
			}
			else
			{
				dir += correctForces.get(i).y;
			}
		}			
		return dir;
	}
	 
	 public boolean setJawsState()
	 {
		 world.world2d.setJawsSate(objType == Statics.DynamicGameObject.SNAKE ? 
				 ((Snake)this).parts.get(0).myBody : myBody, stateHS.isOpenJaws = stateHS.isOpenJaws ? false : true);
		 return stateHS.isOpenJaws;
	 }
	 
	 public void closeJaws(Body body)
	 {
		 world.world2d.addBiteHoldToQeue(objType == Statics.DynamicGameObject.SNAKE ? 
				 ((Snake)this).parts.get(0).myBody : myBody, body);
		 stateHS.isOpenJaws = false;
	 }
	 
	 void processFlamingDamage(float deltaTime)
	 {
		if (stateHS == null)
			return;
		
		float actTime = world.actTime;
		float oldTime = actTime - deltaTime;
				
		int intActTime = (int) (actTime / Statics.PhysicsBox2D.EFFECTS_ACTIONS_UPDATE_FREQUENCY);
		int intOldTime = (int) (oldTime / Statics.PhysicsBox2D.EFFECTS_ACTIONS_UPDATE_FREQUENCY);
		
		if(intOldTime != intActTime)
			stateHS.healthDecrease += WorldProcessingImpl.getFireDamage(flamePower);
	 }
	 
}
