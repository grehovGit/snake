package com.example.framework.model;

import com.example.framework.model.DynamicGameObject;


public class Lemming extends DynamicGameObject {
	
	static  float WORLD_WIDTH;
	static  float WORLD_HEIGHT;
	
	static  float WIDTH = 1;
	static  float HEIGHT = 1;
	
	static  float APPERIANCE_PERIOD = 1;
	
	public static final float TO_MIDDLE_OF_CELL_VELOCITY_KOEFF_LEMMY = 2f;	
	public static final float BEFORE_TURN_VELOCITY_KOEFF_LEMMY = 2f;	
	public static final float APPLY_FORCE_CHAR_MOVING_KOEFF_LEMMY = 3.0f;
	
	public static final float TO_MIDDLE_OF_CELL_VELOCITY_KOEFF_VIKING = 2f;	
	public static final float BEFORE_TURN_VELOCITY_KOEFF_VIKING = 2f;	
	public static final float APPLY_FORCE_CHAR_MOVING_KOEFF_VIKING = 2.0f;
	
	public static final float TO_MIDDLE_OF_CELL_VELOCITY_KOEFF_BERSERKER = 2f;	
	public static final float BEFORE_TURN_VELOCITY_KOEFF_BERSERKER = 3f;	
	public static final float APPLY_FORCE_CHAR_MOVING_KOEFF_BERSERKER = 4.0f;	
	
	public static final float TO_MIDDLE_OF_CELL_VELOCITY_KOEFF_KONUNG = 2f;	
	public static final float BEFORE_TURN_VELOCITY_KOEFF_KONUNG = 3f;	
	public static final float APPLY_FORCE_CHAR_MOVING_KOEFF_KONUNG = 4.0f;	
	
	public static final float TO_MIDDLE_OF_CELL_VELOCITY_KOEFF_ULFHEDNAR = 2f;	
	public static final float BEFORE_TURN_VELOCITY_KOEFF_ULFHEDNAR = 3f;	
	public static final float APPLY_FORCE_CHAR_MOVING_KOEFF_ULFHEDNAR = 5.0f;	
	
	//Pink - Lemmy Haotic
	//Green - Lemmy Simple Deykstra
	//Blue - Lemmy Viking
	//Yellow - Lemmy Berserker
	//Orange - Lemmy Ulfhednar
	//Red - Lemmy Konung Harrold(Harry) Fairhair
	
	static  float SPEED = 3.5f;
	
	public static float ACT_SPEED = 0.1f;	//animation speed
    float tModePeriod;
         
    public Lemming(float x, float y, float width, float height, float angle, int level, WorldKingSnake world) 
    {
        super(x, y, width, height, angle, DynamicGameObject.LEMMING, world);
        
 		WORLD_WIDTH = WorldKingSnake.WORLD_WIDTH;
 		WORLD_HEIGHT = WorldKingSnake.WORLD_HEIGHT;
 		isMoving = CharacterMind.STAY;
 		tModePeriod = APPERIANCE_PERIOD; 		    
        position.set(x, y);
        velocity.set(0, 0);  
        this.world = world;
        isCharacter = true;
        
    	setLevel(level);
    	
    	fSkills.addSkillDefault(Statics.FightingSkills.WEAPON_HOLD_HELM);
    	fSkills.addSkillDefault(Statics.FightingSkills.WEAPON_HOLD_SHIELD);
    	fSkills.addSkillDefault(Statics.FightingSkills.WEAPON_HOLD_LEFT_ARM);
    	fSkills.setSkillLevel(Statics.FightingSkills.WEAPON_HOLD_LEFT_ARM, 4);
    	fSkills.addSkillDefault(Statics.FightingSkills.WEAPON_HOLD_RIGHT_ARM);
    	fSkills.setSkillLevel(Statics.FightingSkills.WEAPON_HOLD_RIGHT_ARM, 4);
    	fSkills.addSkillDefault(Statics.FightingSkills.WEAPON_THROW);
    	fSkills.setSkillLevel(Statics.FightingSkills.WEAPON_THROW, 4);
    	fSkills.addSkillDefault(Statics.FightingSkills.WEAPON_THROW_SHIELD);
    	fSkills.setSkillLevel(Statics.FightingSkills.WEAPON_THROW_SHIELD, 4);
    	
    	fSkills.addSkillDefault(Statics.FightingSkills.ICE_ATTACK_FORWARD);
    	fSkills.setSkillLevel(Statics.FightingSkills.ICE_ATTACK_FORWARD, 4);
    	fSkills.setSkillChargeLevel(Statics.FightingSkills.ICE_ATTACK_FORWARD, 4);
    	
//    	fSkills.addSkillDefault(Statics.FightingSkills.HYPNOSE_ATTACK_FORWARD);
//    	fSkills.setSkillLevel(Statics.FightingSkills.HYPNOSE_ATTACK_FORWARD, 4);
//    	fSkills.setSkillChargeLevel(Statics.FightingSkills.HYPNOSE_ATTACK_FORWARD, 4);
    	
    	fSkills.addSkillDefault(Statics.FightingSkills.FIRE_ATTACK_FORWARD);
    	fSkills.setSkillLevel(Statics.FightingSkills.FIRE_ATTACK_FORWARD, 4);
    	fSkills.setSkillChargeLevel(Statics.FightingSkills.FIRE_ATTACK_FORWARD, 4);
    	
    	/*fSkills.addSkillDefault(Statics.FightingSkills.FORWARD_ATTACK);
    	fSkills.setSkillLevel(Statics.FightingSkills.FORWARD_ATTACK, 4);
    	fSkills.setSkillChargeLevel(Statics.FightingSkills.FORWARD_ATTACK, 4);
    	
    	fSkills.addSkillDefault(Statics.FightingSkills.FORWARD_LEFT_HOOK);
    	fSkills.setSkillLevel(Statics.FightingSkills.FORWARD_LEFT_HOOK, 4);
    	fSkills.setSkillChargeLevel(Statics.FightingSkills.FORWARD_LEFT_HOOK, 4);
    	
    	fSkills.addSkillDefault(Statics.FightingSkills.FORWARD_RIGHT_HOOK);
    	fSkills.setSkillLevel(Statics.FightingSkills.FORWARD_RIGHT_HOOK, 4);
    	fSkills.setSkillChargeLevel(Statics.FightingSkills.FORWARD_RIGHT_HOOK, 4);
    	
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
        fSkills.setSkillChargeLevel(Statics.FightingSkills.BACK_ATTACK, 3);*/
    	
    }
    
    public void update(float deltaTime) {
    	
    	super.update(deltaTime);
    	
    	if(stateHS.isHeated && stateHS.hitPeriod == stateHS.hitPeriodFull)
    	{
    		isMovingOld = isMoving;
			isMoving = CharacterMind.STAY;		
    	}   	   	
    	else if(!stateHS.isHeated && !stateHS.isHypnosed && world.mind.getBotMove(this, deltaTime))    
		{
    		world.world2d.implementCharMoveForce(this);	        	
		}
		else
			isMoving = CharacterMind.STAY;
			  		
      	tModePeriod -= deltaTime;
    }
    
	 public int setLevel(int newLevel) {
		 
		 switch(newLevel)
		 {
		 case HealthScore.LEVEL_PINK:
			 	toMiddleOfCellVelKoef = TO_MIDDLE_OF_CELL_VELOCITY_KOEFF_LEMMY;	
			 	beforeTurnVelKoef = BEFORE_TURN_VELOCITY_KOEFF_LEMMY;	
			 	applyForceCharMovingKoef = APPLY_FORCE_CHAR_MOVING_KOEFF_LEMMY;	
			 	break;
		 case HealthScore.LEVEL_GREEN:
			    toMiddleOfCellVelKoef = TO_MIDDLE_OF_CELL_VELOCITY_KOEFF_LEMMY;	
			    beforeTurnVelKoef = BEFORE_TURN_VELOCITY_KOEFF_LEMMY;	
			    applyForceCharMovingKoef = APPLY_FORCE_CHAR_MOVING_KOEFF_LEMMY;	
				break;
		 case HealthScore.LEVEL_BLUE:
			    toMiddleOfCellVelKoef = TO_MIDDLE_OF_CELL_VELOCITY_KOEFF_VIKING;	
			    beforeTurnVelKoef = BEFORE_TURN_VELOCITY_KOEFF_VIKING;	
			    applyForceCharMovingKoef = APPLY_FORCE_CHAR_MOVING_KOEFF_VIKING;				   
				break;
		 case HealthScore.LEVEL_YELLOW:
			    toMiddleOfCellVelKoef = TO_MIDDLE_OF_CELL_VELOCITY_KOEFF_BERSERKER;	
			    beforeTurnVelKoef = BEFORE_TURN_VELOCITY_KOEFF_BERSERKER;	
			    applyForceCharMovingKoef = APPLY_FORCE_CHAR_MOVING_KOEFF_BERSERKER;	
				break;
		 case HealthScore.LEVEL_ORANGE:
			    toMiddleOfCellVelKoef = TO_MIDDLE_OF_CELL_VELOCITY_KOEFF_ULFHEDNAR;	
			    beforeTurnVelKoef = BEFORE_TURN_VELOCITY_KOEFF_ULFHEDNAR;	
			    applyForceCharMovingKoef = APPLY_FORCE_CHAR_MOVING_KOEFF_ULFHEDNAR;	
				break;
		 case HealthScore.LEVEL_RED:
			    toMiddleOfCellVelKoef = TO_MIDDLE_OF_CELL_VELOCITY_KOEFF_KONUNG;	
			    beforeTurnVelKoef = BEFORE_TURN_VELOCITY_KOEFF_KONUNG;	
			    applyForceCharMovingKoef = APPLY_FORCE_CHAR_MOVING_KOEFF_KONUNG;		
				break;
		 case HealthScore.LEVEL_FIRE:
				break;
		 }
		 
		 toMiddleOfCellVelKoef *= Statics.PhysicsBox2D.ALL_FORCES_CHAR_MOVING_GENERAL_TUNING_KOEFF;	
		 beforeTurnVelKoef *= Statics.PhysicsBox2D.ALL_FORCES_CHAR_MOVING_GENERAL_TUNING_KOEFF;	
		 applyForceCharMovingKoef *= Statics.PhysicsBox2D.ALL_FORCES_CHAR_MOVING_GENERAL_TUNING_KOEFF;
		 return super.setLevel(newLevel);
					
		}
}
