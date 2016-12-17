package com.example.framework.model;

import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.ObjectMap.Entry;
import com.kingsnake.physicsBox2d.PhysicsBox2d;

public class FightingSkill {
	
	int fNumber;
	String type;
	int skillClass;
	float[] fValues;
	boolean isReady;
	boolean isStriking;
	boolean isStartStriking;
	float startTime;
	float rateByDistanceClass;
	float fSkillRate;
	DynamicGameObject master;
	DynamicGameObject weapon;
	Vector2 targetPos;
	Vector2 startPos;
	float workDistance;
	float curPath;
	float maxPath;
	
	FightingSkill(String type, int fNumber, float[] fValues, DynamicGameObject master)
	{
		this.type = type;
		this.fNumber = fNumber;
		this.fValues = fValues;
		this.master = master;
		weapon = null;
		targetPos = new Vector2();
		startPos = new Vector2();
		isReady = false;
		isStriking = false;
		isStartStriking = true;
		startTime = 0;
		curPath = 0;
		maxPath = 0;
		rateByDistanceClass = Statics.FightingSkills.getFSkillRateByDistanceClass(type);
		fSkillRate = Statics.FightingSkills.getFSkillRate(type);
		skillClass = Statics.FightingSkills.getFSkillClass(type);
		workDistance = Statics.FightingSkills.getFSkillWorkDistance(type, (int) fValues[Statics.FightingSkills.ATTACK_POWER_INDEX]);
	}
	
	FightingSkill(String type, DynamicGameObject master)
	{
		this.type = type;
		this.master = master;
		targetPos = new Vector2();
		startPos = new Vector2();
		isReady = false;
		isStriking = false;
		isStartStriking = true;
		rateByDistanceClass = Statics.FightingSkills.getFSkillRateByDistanceClass(type);
		fSkillRate = Statics.FightingSkills.getFSkillRate(type);
		skillClass = Statics.FightingSkills.getFSkillClass(type);
		workDistance = Statics.FightingSkills.getFSkillWorkDistance(type, Statics.FightingSkills.FIGHTSKILL_LEVEL_PINK);
		
		if(type == Statics.FightingSkills.FORWARD_ATTACK
			|| type == Statics.FightingSkills.FORWARD_LEFT_HOOK
			|| type == Statics.FightingSkills.FORWARD_RIGHT_HOOK
			|| type == Statics.FightingSkills.FORWARD_LEFT_TURN
			|| type == Statics.FightingSkills.FORWARD_RIGHT_TURN
			|| type == Statics.FightingSkills.BACK_LEFT_HOOK
			|| type == Statics.FightingSkills.BACK_RIGHT_HOOK
			|| type == Statics.FightingSkills.BACK_ATTACK
			|| type == Statics.FightingSkills.BLOCK
			|| type == Statics.FightingSkills.IMPULSE_DEFFENSE_FORWARD
			|| type == Statics.FightingSkills.IMPULSE_DEFFENSE_LEFTSIDE
			|| type == Statics.FightingSkills.IMPULSE_DEFFENSE_RIGHTSIDE
			|| type == Statics.FightingSkills.IMPULSE_ATTACK_FORWARD			
			|| type == Statics.FightingSkills.ICE_ATTACK_FORWARD
			|| type == Statics.FightingSkills.HYPNOSE_ATTACK_FORWARD
			|| type == Statics.FightingSkills.FIRE_ATTACK_FORWARD					
			|| type == Statics.FightingSkills.WEAPON_HOLD_HELM
			|| type == Statics.FightingSkills.WEAPON_HOLD_LEFT_ARM
			|| type == Statics.FightingSkills.WEAPON_HOLD_RIGHT_ARM
			|| type == Statics.FightingSkills.WEAPON_THROW
			|| type == Statics.FightingSkills.WEAPON_THROW_SPEAR
			|| type == Statics.FightingSkills.WEAPON_THROW_SHIELD
			)
		{
			fNumber = Statics.FightingSkills.FORWARD_ATTACK_FIELDS_COUNT;
			fValues = new float[Statics.FightingSkills.FORWARD_ATTACK_FIELDS_COUNT];
			fValues[Statics.FightingSkills.ATTACK_POWER_INDEX] = Statics.FightingSkills.FIGHTSKILL_LEVEL_PINK;
			fValues[Statics.FightingSkills.CHARGE_POWER_INDEX] = Statics.FightingSkills.FIGHTSKILL_LEVEL_PINK;
		}	
		else if(type == Statics.FightingSkills.WEAPON_HOLD_SHIELD)
		{
			fNumber = Statics.FightingSkills.WEAPON_HOLD_FIELDS_COUNT;
			fValues = new float[Statics.FightingSkills.WEAPON_HOLD_FIELDS_COUNT];
			fValues[Statics.FightingSkills.ATTACK_POWER_INDEX] = Statics.FightingSkills.FIGHTSKILL_LEVEL_PINK;
			fValues[Statics.FightingSkills.CHARGE_POWER_INDEX] = Statics.FightingSkills.FIGHTSKILL_LEVEL_PINK;	
			
			//targets in sensors
			fValues[Statics.INDEX_2] = 0;	//forward
			fValues[Statics.INDEX_3] = 0;	//left	
			fValues[Statics.INDEX_4] = 0;	//right	
			fValues[Statics.INDEX_5] = 0;	//back
			fValues[Statics.INDEX_6] = 0;	//modified flag
		}
	}
	

	
	void update(float actTime, float deltaTime)
	{		
		if(skillClass == Statics.FightingSkills.FIGHTSKILL_CLASS_SKILL && !isReady && Statics.FightingSkills.CHARGING_DEFAULT_PERIOD * fValues[Statics.FightingSkills.ATTACK_POWER_INDEX] / fValues[Statics.FightingSkills.CHARGE_POWER_INDEX]
				< actTime - startTime)
			isReady = true;

		
		if(isStriking)
			processStriking(actTime);		
		
		if(type == Statics.FightingSkills.IMPULSE_ATTACK_FORWARD && isReady)
			processSensorForwardRayQueueByImpulse();
		if(type == Statics.FightingSkills.ICE_ATTACK_FORWARD && isReady)
			processSensorForwardRayQueueByIce();	
		if(type == Statics.FightingSkills.HYPNOSE_ATTACK_FORWARD && isReady)
			processSensorForwardRayQueueByHypnose();	
		if(type == Statics.FightingSkills.FIRE_ATTACK_FORWARD && isReady)
			processSensorForwardRayQueueByFire();	
		else if(type == Statics.FightingSkills.WEAPON_THROW_SPEAR && isReadyToThrowSpear())
			processSensorForwardRayQueueByWeaponThrow(getThrowingSpear());
		else if(type == Statics.FightingSkills.WEAPON_THROW && isReadyToThrowWeapon())
			processSensorForwardRayQueueByWeaponThrow(getThrowingWeapon());
		else if(type == Statics.FightingSkills.WEAPON_HOLD_SHIELD && isReady)
			updateShieldPos();
		else if(type == Statics.FightingSkills.WEAPON_THROW_SHIELD)
			throwShieldIfBroken();
		else if(type == Statics.FightingSkills.WEAPON_HOLD_LEFT_ARM && isReady)
			updateArm(actTime, deltaTime);
		else if(type == Statics.FightingSkills.WEAPON_HOLD_RIGHT_ARM && isReady)
			updateArm(actTime, deltaTime);
	}
	
	boolean isReadyToThrowWeapon()
	{
		int weaponCounter = 0;
		
		if(master.fSkills.haveSkill(Statics.FightingSkills.WEAPON_HOLD_LEFT_ARM))
		{
			FightingSkill leftArm =  master.fSkills.getSkill(Statics.FightingSkills.WEAPON_HOLD_LEFT_ARM);
			if (leftArm.isReady)
			{
				if (leftArm.weapon != null)
					++weaponCounter;
				else 
					leftArm.isReady = false;
			}			
		}
		
		if(master.fSkills.haveSkill(Statics.FightingSkills.WEAPON_HOLD_RIGHT_ARM))
		{
			FightingSkill rightArm =  master.fSkills.getSkill(Statics.FightingSkills.WEAPON_HOLD_RIGHT_ARM);
			if (rightArm.isReady)
			{
				if (rightArm.weapon != null)
					++weaponCounter;
				else 
					rightArm.isReady = false;
			}			
		}
		
		if (weaponCounter > 1)
			return true;
		else
			return false;

	}
	
	boolean isReadyToThrowSpear()
	{
		boolean ready = false;
		
		if(master.fSkills.haveSkill(Statics.FightingSkills.WEAPON_HOLD_LEFT_ARM))
		{
			FightingSkill leftArm = master.fSkills.getSkill(Statics.FightingSkills.WEAPON_HOLD_LEFT_ARM);
			if (leftArm.isReady)		
			{
				if (leftArm.weapon != null)
				{
					if(Statics.DynamicGameObject.getWeaponClass(leftArm.weapon.objType) == Statics.DynamicGameObject.ABSTRACT_TYPE_WEAPON_SPEAR)
						ready = true;							
				}
				else 
					leftArm.isReady = false;
			}
		}
		
		if(master.fSkills.haveSkill(Statics.FightingSkills.WEAPON_HOLD_RIGHT_ARM))
		{
			FightingSkill rightArm = master.fSkills.getSkill(Statics.FightingSkills.WEAPON_HOLD_RIGHT_ARM);
			if (rightArm.isReady)		
			{
				if (rightArm.weapon != null)
				{
					if(Statics.DynamicGameObject.getWeaponClass(rightArm.weapon.objType) == Statics.DynamicGameObject.ABSTRACT_TYPE_WEAPON_SPEAR)
						ready = true;							
				}
				else 
					rightArm.isReady = false;
			}
		};
		
		return ready;
	}
	
	DynamicGameObject getThrowingSpear()
	{
		DynamicGameObject spear = null;
		
		if(master.fSkills.haveSkill(Statics.FightingSkills.WEAPON_HOLD_LEFT_ARM) && master.fSkills.getSkill(Statics.FightingSkills.WEAPON_HOLD_LEFT_ARM).isReady
				&& Statics.DynamicGameObject.getWeaponClass(master.fSkills.getSkill(Statics.FightingSkills.WEAPON_HOLD_LEFT_ARM).weapon.objType) ==
				Statics.DynamicGameObject.ABSTRACT_TYPE_WEAPON_SPEAR)
			spear  = master.fSkills.getSkill(Statics.FightingSkills.WEAPON_HOLD_LEFT_ARM).weapon;
		else if(master.fSkills.haveSkill(Statics.FightingSkills.WEAPON_HOLD_RIGHT_ARM) && master.fSkills.getSkill(Statics.FightingSkills.WEAPON_HOLD_RIGHT_ARM).isReady
					&& Statics.DynamicGameObject.getWeaponClass(master.fSkills.getSkill(Statics.FightingSkills.WEAPON_HOLD_RIGHT_ARM).weapon.objType) ==
					Statics.DynamicGameObject.ABSTRACT_TYPE_WEAPON_SPEAR)
			spear  = master.fSkills.getSkill(Statics.FightingSkills.WEAPON_HOLD_RIGHT_ARM).weapon;
		
		return spear;
	}
	
	FightingSkill getArmThrowingSpear()
	{
		FightingSkill arm = null;
		
		if(master.fSkills.haveSkill(Statics.FightingSkills.WEAPON_HOLD_LEFT_ARM) && master.fSkills.getSkill(Statics.FightingSkills.WEAPON_HOLD_LEFT_ARM).isReady
				&& Statics.DynamicGameObject.getWeaponClass(master.fSkills.getSkill(Statics.FightingSkills.WEAPON_HOLD_LEFT_ARM).weapon.objType) ==
				Statics.DynamicGameObject.ABSTRACT_TYPE_WEAPON_SPEAR)
			arm  = master.fSkills.getSkill(Statics.FightingSkills.WEAPON_HOLD_LEFT_ARM);
		else if(master.fSkills.haveSkill(Statics.FightingSkills.WEAPON_HOLD_RIGHT_ARM) && master.fSkills.getSkill(Statics.FightingSkills.WEAPON_HOLD_RIGHT_ARM).isReady
					&& Statics.DynamicGameObject.getWeaponClass(master.fSkills.getSkill(Statics.FightingSkills.WEAPON_HOLD_RIGHT_ARM).weapon.objType) ==
					Statics.DynamicGameObject.ABSTRACT_TYPE_WEAPON_SPEAR)
			arm = master.fSkills.getSkill(Statics.FightingSkills.WEAPON_HOLD_RIGHT_ARM);
		
		return arm;
	}
	
	DynamicGameObject getThrowingWeapon()
	{
		DynamicGameObject weaponLeft = null;
		DynamicGameObject weaponRight = null;
		
		if(master.fSkills.haveSkill(Statics.FightingSkills.WEAPON_HOLD_LEFT_ARM) && master.fSkills.getSkill(Statics.FightingSkills.WEAPON_HOLD_LEFT_ARM).isReady)
			weaponLeft  = master.fSkills.getSkill(Statics.FightingSkills.WEAPON_HOLD_LEFT_ARM).weapon;
		
		if(master.fSkills.haveSkill(Statics.FightingSkills.WEAPON_HOLD_RIGHT_ARM) && master.fSkills.getSkill(Statics.FightingSkills.WEAPON_HOLD_RIGHT_ARM).isReady)
			weaponRight  = master.fSkills.getSkill(Statics.FightingSkills.WEAPON_HOLD_RIGHT_ARM).weapon;
			
		if (weaponLeft != null && Statics.DynamicGameObject.getWeaponClass(weaponLeft.objType) == Statics.DynamicGameObject.ABSTRACT_TYPE_WEAPON_SPEAR)
			return weaponLeft;
		else if (weaponRight != null && Statics.DynamicGameObject.getWeaponClass(weaponRight.objType) == Statics.DynamicGameObject.ABSTRACT_TYPE_WEAPON_SPEAR)
			return weaponRight;
		else if (weaponLeft != null)
			return weaponLeft;
		else
			return weaponRight;			
	}
	
	FightingSkill getArmThrowingWeapon()
	{
		FightingSkill leftArm = null;
		FightingSkill rightArm = null;
		
		if(master.fSkills.haveSkill(Statics.FightingSkills.WEAPON_HOLD_LEFT_ARM) && master.fSkills.getSkill(Statics.FightingSkills.WEAPON_HOLD_LEFT_ARM).isReady)
			leftArm  = master.fSkills.getSkill(Statics.FightingSkills.WEAPON_HOLD_LEFT_ARM);
		
		if(master.fSkills.haveSkill(Statics.FightingSkills.WEAPON_HOLD_RIGHT_ARM) && master.fSkills.getSkill(Statics.FightingSkills.WEAPON_HOLD_RIGHT_ARM).isReady)
			rightArm = master.fSkills.getSkill(Statics.FightingSkills.WEAPON_HOLD_RIGHT_ARM);
			
		if (leftArm != null && Statics.DynamicGameObject.getWeaponClass(leftArm.weapon.objType) == Statics.DynamicGameObject.ABSTRACT_TYPE_WEAPON_SPEAR)
			return leftArm;
		else if (rightArm != null && Statics.DynamicGameObject.getWeaponClass(rightArm.weapon.objType) == Statics.DynamicGameObject.ABSTRACT_TYPE_WEAPON_SPEAR)
			return rightArm;
		else if (leftArm != null)
			return leftArm;
		else
			return rightArm;			
	}
	
	void setAvatarTargetPoint()
	{
		targetPos.set(workDistance, 0);
		targetPos.setAngle(master.angle);
		
		if(type == Statics.FightingSkills.FORWARD_ATTACK || type == Statics.FightingSkills.FORWARD_LEFT_HOOK || type == Statics.FightingSkills.FORWARD_RIGHT_HOOK)
			targetPos.add(startPos);
		else if (type == Statics.FightingSkills.FORWARD_LEFT_TURN)
		{
			targetPos.rotate(135);	
			targetPos.add(startPos);
		}
		else if(type == Statics.FightingSkills.FORWARD_RIGHT_TURN)
		{
			targetPos.rotate(-135);
			targetPos.add(startPos);
		}
		else if (type == Statics.FightingSkills.BACK_LEFT_HOOK)
		{
			targetPos.rotate(90);
			targetPos.add(startPos);
		}
		else if(type == Statics.FightingSkills.BACK_RIGHT_HOOK)
		{
			targetPos.rotate(-90);
			targetPos.add(startPos);
		}
		else if (type == Statics.FightingSkills.BACK_ATTACK)
		{
			SnakePart sTale = ((Snake)master).parts.get(((Snake)master).parts.size() - 1);
			startPos.set(sTale.position.x, sTale.position.y);
			targetPos.set(workDistance, 0);
			targetPos.setAngle(sTale.angle);
			targetPos.rotate(180);
			targetPos.add(startPos);
		}
		else if (type == Statics.FightingSkills.IMPULSE_DEFFENSE_FORWARD)
		{
		}
		else if (type == Statics.FightingSkills.IMPULSE_DEFFENSE_LEFTSIDE)
		{
		}
		else if (type == Statics.FightingSkills.IMPULSE_DEFFENSE_RIGHTSIDE)
		{
		}
		else if (type == Statics.FightingSkills.IMPULSE_ATTACK_FORWARD)
		{
			targetPos.setAngle(master.angle);
			targetPos.add(startPos);
		}
	}
	
	void strikeWeaponStart(float actTime, DynamicGameObject target,  boolean isBot)
	{
		
	}
	
	void strikeWeaponFinish(float actTime, DynamicGameObject target,  boolean isBot)
	{
		
	}
	
	
	void strikeStart(float actTime, DynamicGameObject target,  boolean isBot)
	{
		isReady = false;
		isStriking = true;
		startTime = actTime;
		master.stateHS.isStriking = true;
		isStartStriking = true;
		master.stateHS.striked = false;
		master.stateHS.strikeStartTime = actTime;
		master.stateHS.strikePower = fValues[Statics.FightingSkills.ATTACK_POWER_INDEX];
		startPos.set(master.position.x, master.position.y);

		if(isBot)		
			targetPos.set(target.position.x, target.position.y);
		else
			setAvatarTargetPoint();		
		
		float forcePeriod = 0;
		int a = 2;
		
		if(type == Statics.FightingSkills.FORWARD_ATTACK)
		{
		 	curPath = Vector2.dst(startPos.x, startPos.y, targetPos.x, targetPos.y);
		 	maxPath =  (Statics.FightingSkills.SENSOR_FORWARD_SENS_START_DISTANCE + 
		 			Statics.FightingSkills.SENSOR_FORWARD_SENS_STEP_DISTANCE * fValues[Statics.FightingSkills.ATTACK_POWER_INDEX]);		 	

		 	forcePeriod = Statics.FightingSkills.FORWARD_ATTACK_PERIOD * curPath / maxPath;	
		 	strikeInitJump(forcePeriod);
		}
		else if (type == Statics.FightingSkills.FORWARD_LEFT_HOOK || type == Statics.FightingSkills.FORWARD_RIGHT_HOOK)
		{
		 	curPath = (float) (Math.PI * Vector2.dst(startPos.x, startPos.y, targetPos.x, targetPos.y) / 2);
		 	maxPath = (float) (Math.PI * (Statics.FightingSkills.SENSOR_FORWARD_SENS_START_DISTANCE + 
		 			Statics.FightingSkills.SENSOR_FORWARD_SENS_STEP_DISTANCE * fValues[Statics.FightingSkills.ATTACK_POWER_INDEX]) / 2);		 	

		 	forcePeriod = Statics.FightingSkills.FORWARD_HOOK_PERIOD * curPath / maxPath;
		 	strikeInitJump(forcePeriod);
		}		
		else if (type == Statics.FightingSkills.FORWARD_LEFT_TURN || type == Statics.FightingSkills.FORWARD_RIGHT_TURN)
		{
		 	curPath = (float) (Math.PI * Vector2.dst(startPos.x, startPos.y, targetPos.x, targetPos.y) / 2);
		 	maxPath = (float) (Math.PI * (Statics.FightingSkills.SENSOR_SIDE_SENS_START_DISTANCE + 
		 			Statics.FightingSkills.SENSOR_SIDE_SENS_STEP_DISTANCE * fValues[Statics.FightingSkills.ATTACK_POWER_INDEX]) / 2);
		 	
		 	forcePeriod = Statics.FightingSkills.FORWARD_TURN_PERIOD * curPath / maxPath;
		 	strikeInitJump(forcePeriod);
		}
		else if (type == Statics.FightingSkills.BACK_LEFT_HOOK || type == Statics.FightingSkills.BACK_RIGHT_HOOK)
		{
			if(master.objType == Statics.DynamicGameObject.SNAKE)
			{
				SnakePart sTale = ((Snake)master).parts.get(((Snake)master).parts.size() - 1);
				startPos.set(sTale.position.x, sTale.position.y);
			}
			
		 	curPath = (float) (Math.PI * Vector2.dst(startPos.x, startPos.y, targetPos.x, targetPos.y) / 2);
		 	maxPath = 2 * (float) (Math.PI * (Statics.FightingSkills.SENSOR_SIDE_SENS_START_DISTANCE + 
		 			Statics.FightingSkills.SENSOR_SIDE_SENS_STEP_DISTANCE * fValues[Statics.FightingSkills.ATTACK_POWER_INDEX]) / 2);
		 	
		 	forcePeriod = Statics.FightingSkills.FORWARD_TURN_PERIOD * curPath / maxPath;
		 	strikeInitJump(forcePeriod);
		}	
		else if (type == Statics.FightingSkills.BACK_ATTACK)
		{
			if(master.objType == Statics.DynamicGameObject.SNAKE)
			{
				SnakePart sTale = ((Snake)master).parts.get(((Snake)master).parts.size() - 1);
				startPos.set(sTale.position.x, sTale.position.y);
			}
			
		 	curPath = Vector2.dst(startPos.x, startPos.y, targetPos.x, targetPos.y);
		 	maxPath =  (Statics.FightingSkills.SENSOR_BACK_SENS_START_DISTANCE + 
		 			Statics.FightingSkills.SENSOR_BACK_SENS_STEP_DISTANCE * fValues[Statics.FightingSkills.ATTACK_POWER_INDEX]);		 	

		 	forcePeriod = Statics.FightingSkills.FORWARD_ATTACK_PERIOD * curPath / maxPath;		
		 	strikeInitJump(forcePeriod);
		}
		else if (type == Statics.FightingSkills.BLOCK)
			a = 1;
		else if (type == Statics.FightingSkills.IMPULSE_DEFFENSE_FORWARD)
			strikeInitImpulse();
		else if (type == Statics.FightingSkills.IMPULSE_DEFFENSE_LEFTSIDE || type == Statics.FightingSkills.IMPULSE_DEFFENSE_RIGHTSIDE)
			strikeInitImpulse();	
		else if (type == Statics.FightingSkills.IMPULSE_ATTACK_FORWARD)
		 	strikeInitImpulse();
		else if (type == Statics.FightingSkills.ICE_ATTACK_FORWARD)
		 	strikeInitIce();
		else if (type == Statics.FightingSkills.HYPNOSE_ATTACK_FORWARD)
		 	strikeInitHypnose();
		else if (type == Statics.FightingSkills.FIRE_ATTACK_FORWARD)
		 	strikeInitFire();
	}
	
	void strikeInitJump(float forcePeriod)
	{
		master.stateHS.strikeType = Statics.Render.STRIKE_JUMP;
		
		if(master.objType == Statics.DynamicGameObject.SNAKE)
		{
			Snake snake = (Snake) master;
			SnakePart sPart = null;
		 	int size = snake.parts.size();
		 	master.stateHS.strikePeriod =  2 * size *  Statics.Render.SNAKE_GOLD_STRIKE_SEGMENT_CHANGING_PERIOD + forcePeriod;
		 	
		 	for(int i = size - 1; i >= 0; --i)
		 	{
		 		sPart = snake.parts.get(i);
		 		sPart.stateHS.strikePower = fValues[Statics.FightingSkills.ATTACK_POWER_INDEX];
				sPart.stateHS.strikeType = Statics.Render.STRIKE_JUMP;
				sPart.myBody.setAngularDamping(Statics.PhysicsBox2D.CHAR_ANGUALR_DAMPING_JUMP);
				sPart.myBody.setLinearDamping(Statics.PhysicsBox2D.CHAR_LINEAR_DAMPING_JUMP);
				sPart.stateHS.strikePeriod = forcePeriod + (i + 1) * 2 * Statics.Render.SNAKE_GOLD_STRIKE_SEGMENT_CHANGING_PERIOD;
		 	}
		}
		else
		{
		 	master.stateHS.strikePeriod =  2 * Statics.Render.SNAKE_GOLD_STRIKE_SEGMENT_CHANGING_PERIOD + forcePeriod;
			master.myBody.setAngularDamping(Statics.PhysicsBox2D.CHAR_ANGUALR_DAMPING_JUMP);
			master.myBody.setLinearDamping(Statics.PhysicsBox2D.CHAR_LINEAR_DAMPING_JUMP);
		}		
	}
	
	void strikeInitImpulse()
	{
		 master.stateHS.strikeType = Statics.Render.STRIKE_IMPULSE;
		 master.stateHS.strikePeriod = Statics.Render.SNAKE_BLUE_STRIKE_PERIOD;		
	}
	
	void strikeInitIce()
	{
		 master.stateHS.strikeType = Statics.Render.STRIKE_ICE;
		 master.stateHS.strikePeriod = Statics.Render.SNAKE_BLUE_STRIKE_PERIOD;		
	}

	void strikeInitHypnose()
	{
		 master.stateHS.strikeType = Statics.Render.STRIKE_HYPNOSE;
		 master.stateHS.strikePeriod = Statics.Render.SNAKE_BLUE_STRIKE_PERIOD;		
	}
	
	void strikeInitFire()
	{
		 master.stateHS.strikeType = Statics.Render.STRIKE_FIRE;	
	}
	
	void strikeFinish()
	{
		isStriking = false;
		master.stateHS.isStriking = false;
		master.stateHS.strikeStartTime = 0;
		targetPos.set(0, 0);
		master.fSkills.startRelaxing();
		
		if(master.objType == Statics.DynamicGameObject.SNAKE)
		{
			Snake snake = (Snake) master;
			SnakePart sPart = null;
		 	int size = snake.parts.size();
		 	
		 	for(int i = size - 1; i >= 0; --i)
		 	{
		 		sPart = snake.parts.get(i);
		 		sPart.stateHS.striked = false;
				sPart.stateHS.strikeType = Statics.Render.STRIKE_NO;
		 		
		 		if(i == 0)
		 		{
					sPart.myBody.setLinearDamping(Statics.PhysicsBox2D.CHAR_LINEAR_DAMPING);	
					sPart.myBody.setAngularDamping(Statics.PhysicsBox2D.CHAR_ANGUALR_DAMPING);	
		 		}
		 		else
		 		{
					sPart.myBody.setLinearDamping(Statics.PhysicsBox2D.DYNOBJ_LINEAR_DAMPING);	
					sPart.myBody.setAngularDamping(Statics.PhysicsBox2D.DYNOBJ_ANGUALR_DAMPING);			 			
		 		}
		 	}
		}
		else
		{
			master.myBody.setLinearDamping(Statics.PhysicsBox2D.CHAR_LINEAR_DAMPING);	
			master.myBody.setAngularDamping(Statics.PhysicsBox2D.CHAR_ANGUALR_DAMPING);		
		}
	}
	
	void processStriking(float actTime)
	{
		if(type == Statics.FightingSkills.FORWARD_ATTACK)
			strikeForward(actTime, true);
		else if (type == Statics.FightingSkills.FORWARD_LEFT_HOOK)
			strikeForwardHook(actTime, true, true);
		else if (type == Statics.FightingSkills.FORWARD_RIGHT_HOOK)
			strikeForwardHook(actTime, false, true);			
		else if (type == Statics.FightingSkills.FORWARD_LEFT_TURN)
			strikeForwardHook(actTime, false, true);
		else if(type == Statics.FightingSkills.FORWARD_RIGHT_TURN)
			strikeForwardHook(actTime, true, true);
		else if (type == Statics.FightingSkills.BACK_LEFT_HOOK)
			strikeForwardHook(actTime, true, false);			
		else if(type == Statics.FightingSkills.BACK_RIGHT_HOOK)
			strikeForwardHook(actTime, false, false);
		else if (type == Statics.FightingSkills.BACK_ATTACK)
			strikeForward(actTime, false);
		else if (type == Statics.FightingSkills.BLOCK)
			return;
		else if (type == Statics.FightingSkills.IMPULSE_DEFFENSE_FORWARD)
			impulseDefForward(actTime);
		else if (type == Statics.FightingSkills.IMPULSE_DEFFENSE_LEFTSIDE)
			impulseDefSide(actTime, true);
		else if (type == Statics.FightingSkills.IMPULSE_DEFFENSE_RIGHTSIDE)
			impulseDefSide(actTime, false);
		else if (type == Statics.FightingSkills.IMPULSE_ATTACK_FORWARD)
			impulseAttackForward(actTime);	
		else if (type == Statics.FightingSkills.ICE_ATTACK_FORWARD)
			iceAttackForward(actTime);
		else if (type == Statics.FightingSkills.HYPNOSE_ATTACK_FORWARD)
			hypnoseAttackForward(actTime);
		else if (type == Statics.FightingSkills.FIRE_ATTACK_FORWARD)
			fireAttackForward(actTime);
		else
			return; 
	}
	
	void strikeForward(float actTime, boolean isForward)
	{
	 	float dTime = actTime - master.stateHS.strikeStartTime;
	 	
		if(master.objType == Statics.DynamicGameObject.SNAKE)
		{
			Snake snake = (Snake) master;
		 	int size = snake.parts.size();
		 	
		 	for(int i = size - 1; i >= 0; --i)
		 	{
		 		SnakePart sPart = snake.parts.get(i);
		 		float sPartTime = dTime - (size - 1 - i) * Statics.Render.SNAKE_GOLD_STRIKE_SEGMENT_CHANGING_PERIOD;
		 			 		
			 	if(sPartTime > 0 && sPartTime <= master.stateHS.strikePeriod)
			 	{
				 	sPart.stateHS.isStriking = true;
				 	sPart.sPartGoldWaveTime = sPartTime;
			 	}
			 	else if (sPartTime > master.stateHS.strikePeriod)	
			 	{
				 	sPart.stateHS.isStriking = false;
			 		
			 		if(i == 0)
			 			 strikeFinish();			 		
			 	}
		 	}
		 	
		 	if(dTime > size *  Statics.Render.SNAKE_GOLD_STRIKE_SEGMENT_CHANGING_PERIOD / 3)
		 	{
		 		master.world.world2d.snakeForwardJump(isForward ? snake.parts.get(0) : snake.parts.get(size - 1), targetPos, isForward);
		 		
		 		if(!master.stateHS.striked)
		 		{
			 		master.world.world2d.snakeForwardJumpImpulse(isForward ? snake.parts.get(0) : snake.parts.get(size - 1), targetPos);
			 		master.stateHS.striked = true;
		 		}
		 	}
		}
		else
		{		
		 	if(dTime > 0 && dTime <= master.stateHS.strikePeriod)
			 	master.stateHS.isStriking = true;
		 	else if (dTime > master.stateHS.strikePeriod)	
		 		 strikeFinish();
		 	
		 	if(dTime > 0)
		 	{
		 		master.world.world2d.forwardJump(master, targetPos);
		 		
		 		if(!master.stateHS.striked)
		 		{
			 		master.world.world2d.forwardJumpImpulse(master, targetPos);
			 		master.stateHS.striked = true;
		 		}
		 	}		 	
		}	 			 	
	}
	
	void strikeForwardHook(float actTime, boolean isLeft, boolean isForward)
	{
	 	float dTime = actTime - master.stateHS.strikeStartTime;
	 	
		if(master.objType == Statics.DynamicGameObject.SNAKE)
		{
			Snake snake = (Snake) master;
		 	int size = snake.parts.size();
		 	
		 	for(int i = size - 1; i >= 0; --i)
		 	{
		 		SnakePart sPart = snake.parts.get(i);
		 		float sPartTime = dTime - (size - 1 - i) * Statics.Render.SNAKE_GOLD_STRIKE_SEGMENT_CHANGING_PERIOD;
		 			 		
			 	if(sPartTime > 0 && sPartTime <= master.stateHS.strikePeriod)
			 	{
				 	sPart.stateHS.isStriking = true;
				 	sPart.sPartGoldWaveTime = sPartTime;
			 	}
			 	else if (sPartTime > master.stateHS.strikePeriod)	
			 	{
				 	sPart.stateHS.isStriking = false;
			 		
			 		if(i == 0)
			 			 strikeFinish();			 		
			 	}
		 	}
		 	
		 	if(dTime > size *  Statics.Render.SNAKE_GOLD_STRIKE_SEGMENT_CHANGING_PERIOD / 3)
		 	{
		 		master.world.world2d.snakeForwardHook(isForward ? snake.parts.get(0) : snake.parts.get(size - 1), startPos, targetPos, isLeft, maxPath, isForward);
		 		
		 		if(!master.stateHS.striked)
		 		{
			 		master.world.world2d.snakeForwardHookImpulse(isForward ? snake.parts.get(0) : snake.parts.get(size - 1), targetPos, isLeft, maxPath);
			 		master.stateHS.striked = true;
		 		}
		 	}
		}
		else
		{		
		 	if(dTime > 0 && dTime <= master.stateHS.strikePeriod)
			 	master.stateHS.isStriking = true;
		 	else if (dTime > master.stateHS.strikePeriod)	
		 		 strikeFinish();
		 	
		 	if(dTime > 0)
		 	{
		 		master.world.world2d.forwardHook(master, startPos, targetPos, isLeft, maxPath);
		 		
		 		if(!master.stateHS.striked)
		 		{
			 		master.world.world2d.forwardHookImpulse(master, targetPos, isLeft, maxPath);
			 		master.stateHS.striked = true;
		 		}
		 	}		 	
		}	 			 	
	}
	
	void impulseDefForward(float actTime)
	{
	 	float dTime = actTime - master.stateHS.strikeStartTime;
	 	master.stateHS.strikeTime = dTime;
	 	
	 	if(master.objType == Statics.DynamicGameObject.SNAKE)
	 		((Snake)master).parts.get(0).stateHS.isStriking = true;
		 			 		
		if(dTime >= 0 && dTime <= master.stateHS.strikePeriod)
		{
			if (isStartStriking)
			{
				Vector2 offset = new Vector2(workDistance / 2, 0);
				offset.setAngle(master.objType == Statics.DynamicGameObject.SNAKE ?	((Snake)master).parts.get(0).angle : master.angle);
				
				master.world.wProc.createStaticEffect (master.position.x + offset.x, master.position.y + offset.y, 2 * workDistance, 2 * workDistance, 
						master.objType == Statics.DynamicGameObject.SNAKE ?	((Snake)master).parts.get(0).angle : master.angle,
						Statics.StaticEffect.IMPACT, Statics.Render.SPECEFFECT_IMPACT_PERIOD, 0, null);
				isStartStriking = false;
			}
		}
		else if (dTime > master.stateHS.strikePeriod)	
		{
			if(master.objType == Statics.DynamicGameObject.SNAKE)
		 		((Snake)master).parts.get(0).stateHS.isStriking = false;
			
			strikeFinish();		
		}
		 	
		if(dTime > Statics.Render.SPECEFFECT_IMPACT_PERIOD / 4)
		{		 		
			if(!master.stateHS.striked)
			{
				master.world.world2d.fSkillImpulse(Statics.FightingSkills.FIGHTSKILL_IMPULSE_POWER_STEP * fValues[Statics.FightingSkills.ATTACK_POWER_INDEX],
				 	master.stateHS.attackPower /*TEMPORARY*/, workDistance, master.objType == Statics.DynamicGameObject.SNAKE ?
				 			((Snake)master).parts.get(0) : master);
				master.stateHS.striked = true;
			}
		}			 	
	}
	
	void impulseDefSide(float actTime, boolean isLeft)
	{
	 	float dTime = actTime - master.stateHS.strikeStartTime;
	 	master.stateHS.strikeTime = dTime;
	 	
	 	if(master.objType == Statics.DynamicGameObject.SNAKE)
	 	{		 	
			Snake snake = (Snake) master;
		 	int size = snake.parts.size();
		 	
		 	for(int i = 1; i < size - 1; ++i)
		 	{
		 		SnakePart sPart = snake.parts.get(i);
			 			 		
				if(dTime >= 0 && dTime <= master.stateHS.strikePeriod)
				{
					if (!sPart.stateHS.striked)
					{
						Vector2 offset = new Vector2(workDistance / 3, 0);
						offset.setAngle(sPart.angle + (isLeft ? 90 : - 90));
						
						master.world.wProc.createStaticEffect(sPart.position.x + offset.x, sPart.position.y + offset.y,
								2 * workDistance, 2 * workDistance, sPart.angle + (isLeft ? 90 : - 90), Statics.StaticEffect.IMPACT,
										Statics.Render.SPECEFFECT_IMPACT_PERIOD, 0, null);
						
						master.world.world2d.fSkillSideImpulse(Statics.FightingSkills.FIGHTSKILL_IMPULSE_POWER_STEP * fValues[Statics.FightingSkills.ATTACK_POWER_INDEX],
							 	master.stateHS.attackPower /*TEMPORARY*/, workDistance, sPart, isLeft);
						
					 	sPart.stateHS.isStriking = true;
					 	sPart.stateHS.strikeTime = dTime;
					 	sPart.stateHS.striked = true;
					}
				}
				else if (dTime > master.stateHS.strikePeriod)	
				{
					sPart.stateHS.isStriking = false;					
					strikeFinish();		
				}		
		 	}
	 	}
	 	else
	 	{
	 		
	 	}
	}
	
	void impulseAttackForward(float actTime)
	{
	 	float dTime = actTime - master.stateHS.strikeStartTime;
	 	master.stateHS.strikeTime = dTime;
	 	
	 	if(master.objType == Statics.DynamicGameObject.SNAKE)
	 		((Snake)master).parts.get(0).stateHS.isStriking = true;
		 			 		
		if(dTime >= 0 && dTime <= master.stateHS.strikePeriod)
		{
			if(!master.stateHS.striked)
			{
				float lifeTimePeriod = workDistance / Statics.FightingSkills.IMPULSE_ATTACK_FORWARD_VELOCITY;
				DynamicGameObject localMaster = master.objType == Statics.DynamicGameObject.SNAKE ?	((Snake)master).parts.get(0) : master;
				
				DynamicEffect dynEffect = new DynamicEffect(localMaster.position.x, localMaster.position.y, 1f, 1f, targetPos.sub(master.position.x, master.position.y).angle(), 
								lifeTimePeriod, Statics.DynamicGameObject.FSKILL_FORWARD_ATTACK_IMPULSE_EFFECT, master);
				
				dynEffect.setDataForwardAttackImpulse(workDistance / Statics.FightingSkills.SENSOR_IMPULSE_FORWARD_ATTACK_KOEF_TO_FORWARD_DEFENSE_SENSE,
						Statics.FightingSkills.FIGHTSKILL_IMPULSE_POWER_STEP * fValues[Statics.FightingSkills.ATTACK_POWER_INDEX],
						master.stateHS.attackPower /*TEMPORARY*/);
				
				master.world.dynEffectsAboveObjects.add(dynEffect);	
				master.stateHS.striked = true;
			}
		}
		else if (dTime > master.stateHS.strikePeriod)	
		{
			if(master.objType == Statics.DynamicGameObject.SNAKE)
		 		((Snake)master).parts.get(0).stateHS.isStriking = false;
			
			strikeFinish();		
		}		 	
	}
	
	void iceAttackForward(float actTime)
	{
	 	float dTime = actTime - master.stateHS.strikeStartTime;
	 	master.stateHS.strikeTime = dTime;
	 	
	 	if(master.objType == Statics.DynamicGameObject.SNAKE)
	 		((Snake)master).parts.get(0).stateHS.isStriking = true;
		 			 		
		if(dTime >= 0 && dTime <= master.stateHS.strikePeriod)
		{
			if(!master.stateHS.striked)
			{
				float lifeTimePeriod = workDistance / Statics.FightingSkills.ICE_ATTACK_FORWARD_VELOCITY;
				DynamicGameObject localMaster = master.objType == Statics.DynamicGameObject.SNAKE ?	((Snake)master).parts.get(0) : master;
				
				DynamicEffect dynEffect = new DynamicEffect(localMaster.position.x, localMaster.position.y, 1f, 1f, targetPos.sub(master.position.x, master.position.y).angle(), 
								lifeTimePeriod, Statics.DynamicGameObject.FSKILL_FORWARD_ATTACK_ICE_EFFECT, master);
				dynEffect.setDataForwardAttackPower(fValues[Statics.FightingSkills.ATTACK_POWER_INDEX]);
				
				master.world.dynEffectsAboveObjects.add(dynEffect);	
				master.stateHS.striked = true;
			}
		}
		else if (dTime > master.stateHS.strikePeriod)	
		{
			if(master.objType == Statics.DynamicGameObject.SNAKE)
		 		((Snake)master).parts.get(0).stateHS.isStriking = false;
			
			strikeFinish();		
		}		 	
	}
	
	void hypnoseAttackForward(float actTime)
	{
	 	float dTime = actTime - master.stateHS.strikeStartTime;
	 	master.stateHS.strikeTime = dTime;
	 	
	 	if(master.objType == Statics.DynamicGameObject.SNAKE)
	 		((Snake)master).parts.get(0).stateHS.isStriking = true;
		 			 		
		if(dTime >= 0 && dTime <= master.stateHS.strikePeriod)
		{
			if(!master.stateHS.striked)
			{
				float lifeTimePeriod = workDistance / Statics.FightingSkills.HYPNOSE_ATTACK_FORWARD_VELOCITY;
				DynamicGameObject localMaster = master.objType == Statics.DynamicGameObject.SNAKE ?	((Snake)master).parts.get(0) : master;
				
				DynamicEffectHypnose dynEffect = new DynamicEffectHypnose(localMaster.position.x, localMaster.position.y, 1f, 1f, targetPos.sub(master.position.x, master.position.y).angle(), 
								lifeTimePeriod, fValues[Statics.FightingSkills.ATTACK_POWER_INDEX], master);
				
				master.world.dynEffectsAboveObjects.add(dynEffect);	
				master.stateHS.striked = true;
			}
		}
		else if (dTime > master.stateHS.strikePeriod)	
		{
			if(master.objType == Statics.DynamicGameObject.SNAKE)
		 		((Snake)master).parts.get(0).stateHS.isStriking = false;
			
			strikeFinish();		
		}		 	
	}
	
	void fireAttackForward(float actTime)
	{
	 	float dTime = actTime - master.stateHS.strikeStartTime;
	 	master.stateHS.strikeTime = dTime;
	 	
	 	if(master.objType == Statics.DynamicGameObject.SNAKE)
	 		((Snake)master).parts.get(0).stateHS.isStriking = true;
		 			 		
		if(dTime >= 0 && dTime <= master.stateHS.strikePeriod)
		{
			if(!master.stateHS.striked)
			{
				float lifeTimePeriod = workDistance / Statics.FightingSkills.HYPNOSE_ATTACK_FORWARD_VELOCITY;
				DynamicGameObject localMaster = master.objType == Statics.DynamicGameObject.SNAKE ?	((Snake)master).parts.get(0) : master;
				
				DynamicEffectFire dynEffect = new DynamicEffectFire(localMaster.position.x, localMaster.position.y, 1f, 1f, targetPos.sub(master.position.x, master.position.y).angle(), 
								lifeTimePeriod, fValues[Statics.FightingSkills.ATTACK_POWER_INDEX], master);
				
				master.world.dynEffectsAboveObjects.add(dynEffect);	
				master.stateHS.striked = true;
			}
		}
		else if (dTime > master.stateHS.strikePeriod)	
		{
			if(master.objType == Statics.DynamicGameObject.SNAKE)
		 		((Snake)master).parts.get(0).stateHS.isStriking = false;
			
			strikeFinish();		
		}		 	
	}
	
	int setSensorState(int sensorState, GameObject gObj, Vector2 sensorCenterPos, float sensorRadius)
	{	
		sensorCenterPos.rotate(master.angle);
		sensorCenterPos.add(master.position.x, master.position.y);
		float distance = sensorCenterPos.dst(gObj.position.x, gObj.position.y);
		
		if(distance < sensorRadius || sensorState == Statics.FightingSkills.WEAPON_HOLD_SHIELD_TARGET_IN_WORKRADIUS)
			return (int) Statics.FightingSkills.WEAPON_HOLD_SHIELD_TARGET_IN_WORKRADIUS;
		else
			return (int) Statics.FightingSkills.WEAPON_HOLD_SHIELD_TARGET_IN_SENSOR;
	}
	
	void updateSensorsDataForShield()
	{
		boolean changed = false;
		int forward = 0;				
		int left = 0;					
		int right = 0;					
		String sensor = null;
		GameObject  target = null;
		Iterator <Entry<String, GameObject>> targetsIterator = master.fSkills.fTargets.iterator();
		Vector2 sensorCenterPos = new Vector2();
		
		while (targetsIterator.hasNext())
		{
			Entry<String, GameObject> targetEntry = targetsIterator.next();
			sensor = targetEntry.key;
			target = targetEntry.value; 
			
			if (sensor == Statics.FightingSkills.SENSOR_FORWARD)
			{
				sensorCenterPos.set(Statics.PhysicsBox2D.SENSOR_FIGHT_SKILL_FORWARD_CENTER_POS_X, Statics.PhysicsBox2D.SENSOR_FIGHT_SKILL_FORWARD_CENTER_POS_Y);
				forward = setSensorState(forward, target, sensorCenterPos, Statics.PhysicsBox2D.SENSOR_FIGHT_SKILL_FORWARD_RADIUS);
			}
			else if(sensor == Statics.FightingSkills.SENSOR_LEFT_SIDE)
			{
				sensorCenterPos.set(Statics.PhysicsBox2D.SENSOR_FIGHT_SKILL_LEFT_CENTER_POS_X, Statics.PhysicsBox2D.SENSOR_FIGHT_SKILL_LEFT_CENTER_POS_Y);
				left = setSensorState(left, target, sensorCenterPos, Statics.PhysicsBox2D.SENSOR_FIGHT_SKILL_SIDE_RADIUS);
			}
			else if(sensor == Statics.FightingSkills.SENSOR_RIGHT_SIDE)
			{
				sensorCenterPos.set(Statics.PhysicsBox2D.SENSOR_FIGHT_SKILL_RIGHT_CENTER_POS_X, Statics.PhysicsBox2D.SENSOR_FIGHT_SKILL_RIGHT_CENTER_POS_Y);
				right = setSensorState(right, target, sensorCenterPos, Statics.PhysicsBox2D.SENSOR_FIGHT_SKILL_SIDE_RADIUS);
			}
		}
		
		
		if((forward == Statics.FightingSkills.WEAPON_HOLD_SHIELD_TARGET_IN_WORKRADIUS ? forward - 1 : forward)
				!= (int)fValues[Statics.INDEX_2])
		{
			if(forward == Statics.FightingSkills.WEAPON_HOLD_SHIELD_TARGET_IN_WORKRADIUS)				
			{
				fValues[Statics.INDEX_2] = forward - 1;				
				changed = true;				
			}
			else if(forward == 0)
			{
				fValues[Statics.INDEX_2] = forward;				
				changed = true;
			}
		}

		if((left == Statics.FightingSkills.WEAPON_HOLD_SHIELD_TARGET_IN_WORKRADIUS ? left - 1 : left)
				!= (int)fValues[Statics.INDEX_3])
		{
			if(left == Statics.FightingSkills.WEAPON_HOLD_SHIELD_TARGET_IN_WORKRADIUS)				
			{
				fValues[Statics.INDEX_3] = left - 1;				
				changed = true;				
			}
			else if(left == 0)
			{
				fValues[Statics.INDEX_3] = left;				
				changed = true;
			}
		}
		
		if((right == Statics.FightingSkills.WEAPON_HOLD_SHIELD_TARGET_IN_WORKRADIUS ? right - 1 : right)
				!= (int)fValues[Statics.INDEX_4])
		{
			if(right == Statics.FightingSkills.WEAPON_HOLD_SHIELD_TARGET_IN_WORKRADIUS)				
			{
				fValues[Statics.INDEX_4] = right - 1;				
				changed = true;				
			}
			else if(right == 0)
			{
				fValues[Statics.INDEX_4] = right;				
				changed = true;
			}
		}
		
		if(changed)
			fValues[Statics.INDEX_6] = 1;		
	}
		
	void updateShieldPos()
	{
		if((int) fValues[Statics.INDEX_6] == 1)
		{
			int shieldMode = Statics.FightingSkills.WEAPON_HOLD_SHIELD_MODE_NORMAL;
					
			if (fValues[Statics.INDEX_3] == 1 && (fValues[Statics.INDEX_4] == 1))
				shieldMode = Statics.FightingSkills.WEAPON_HOLD_SHIELD_MODE_FORWARD;	
			else if (fValues[Statics.INDEX_2] == 1)
				shieldMode = Statics.FightingSkills.WEAPON_HOLD_SHIELD_MODE_FORWARD;
			else if (fValues[Statics.INDEX_3] == 1)
				shieldMode = Statics.FightingSkills.WEAPON_HOLD_SHIELD_MODE_LEFT;
			else if (fValues[Statics.INDEX_4] == 1)
				shieldMode = Statics.FightingSkills.WEAPON_HOLD_SHIELD_MODE_RIGHT;	
			
			master.world.world2d.changeShieldPos(master.myBody, shieldMode);			
			fValues[Statics.INDEX_6] = 0;
		}
	}
	
	void throwShieldIfBroken()
	{
		if(master.fSkills.haveSkill(Statics.FightingSkills.WEAPON_HOLD_SHIELD))
		{
			FightingSkill fSkillHoldShield = master.fSkills.getSkill(Statics.FightingSkills.WEAPON_HOLD_SHIELD);
			
			if(fSkillHoldShield.weapon == null || fSkillHoldShield.weapon.myBody == null)
			{
				fSkillHoldShield.isReady = false;
				isReady = false;
			}
			else
			{
				if(!master.world.world2d.isBodyComplete(fSkillHoldShield.weapon))
				{
					if(targetPos != null)
					{
						weapon = fSkillHoldShield.weapon;
						fSkillHoldShield.releaseWeapon();
						master.world.world2d.tryThrowShield(master, this);
					}
				}
			}
		}
	}
	
	void updateArm(float actTime, float deltaTime)
	{
		if(weapon == null || weapon.myBody == null)
		{
			isReady = false;
			
			if(!isStartStriking)													//use as weapon just released flag
			{
				if(master.world.actTime - startTime > Statics.FightingSkills.WEAPON_HOLD_ARM_NO_ACT_PERIOD_AFTER_WEAPON_RELEASE)
					isStartStriking = true;
			}
		}
		else if (Statics.DynamicGameObject.getWeaponClass(weapon.objType) == Statics.DynamicGameObject.ABSTRACT_TYPE_WEAPON_SWORD
				|| Statics.DynamicGameObject.getWeaponClass(weapon.objType) == Statics.DynamicGameObject.ABSTRACT_TYPE_WEAPON_AXE)
			master.world.world2d.processFencing(master, weapon, targetPos, deltaTime, this, Statics.FightingSkills.WEAPON_HOLD_ARM_FENCING_TYPE_CHOP);
		else if (Statics.DynamicGameObject.getWeaponClass(weapon.objType) == Statics.DynamicGameObject.ABSTRACT_TYPE_WEAPON_SPEAR)
			master.world.world2d.processFencing(master, weapon, targetPos, deltaTime, this, Statics.FightingSkills.WEAPON_HOLD_ARM_FENCING_TYPE_STAB);
	}
	
	void setTargetPos(com.kingsnake.math.Vector2 position)
	{
		if (position == null)
			this.targetPos.set(-1, -1);
		else
		{
			this.targetPos.x = position.x;
			this.targetPos.y = position.y;
		}
	}
	
	public float getPower()
	{
		return fValues[Statics.FightingSkills.ATTACK_POWER_INDEX];
	}
	
	public void tryThrowWeapon()
	{
		FightingSkill arm = null;
		
		if(type == Statics.FightingSkills.WEAPON_THROW)
			arm = getArmThrowingWeapon();
		else if(type == Statics.FightingSkills.WEAPON_THROW_SPEAR)
			arm = getArmThrowingSpear();
		
		if(arm.weapon != null)
			master.world.world2d.tryThrowWeapon(master, arm);
	}
	
	public DynamicGameObject getWeapon()
	{
		return weapon;
	}
	
	public Vector2 getTargetPos()
	{
		return targetPos;
	}
	
	public void releaseWeapon()		
	{
		weapon = null;
		isReady = false;
		startTime = master.world.actTime;
		isStartStriking = false;						//use as weapon just released flag
	}
	
	void processSensorForwardRayQueueByImpulse()
	{
		int objAbstractType = 0;
		
		Iterator<Entry<Float, Object>> iterator = master.world.world2d.getRaySensorFixturesArray().iterator();
		int i = 0;
		float currentDistance = 0;
		float firstDistance = 0;
		float workRadius = workDistance / Statics.FightingSkills.SENSOR_IMPULSE_FORWARD_ATTACK_KOEF_TO_FORWARD_DEFENSE_SENSE;
		
		while(iterator.hasNext())
		{
			int result = 0;
			Entry<Float, Object> item = iterator.next();
			Fixture fixture = (Fixture)item.value;
			GameObject gObj = (GameObject) fixture.getBody().getUserData();
			currentDistance = item.key * Statics.PhysicsBox2D.SENSOR_FIGHT_SKILL_FORWARD_RAY_RADIUS;
			
			if(currentDistance > workDistance)
				break;
			else if(i == 0)
			{
				objAbstractType = defineObstacleType(master, gObj);
				firstDistance = currentDistance;
			}
			else
			{
				currentDistance = currentDistance - firstDistance;
				
				if(currentDistance > workRadius)
					break;
				else
					objAbstractType = defineObstacleType(master, gObj);
			}
			
			switch(objAbstractType)
			{
				case 0:
					break;
				case Statics.DynamicGameObject.ABSTRACT_TYPE_OBSTACLE:
					result = 1;
					break;
				case Statics.DynamicGameObject.ABSTRACT_TYPE_OBSTACLE_INSUPERABLE:
					break;
				case Statics.DynamicGameObject.ABSTRACT_TYPE_ENEMY:
					master.fSkills.addTarget(Statics.FightingSkills.SENSOR_VIRTUAL_FORWARD_RAY_IMPULSE, gObj);
					break;
				case Statics.DynamicGameObject.ABSTRACT_TYPE_FRIEND:
					break;	
				default:
					result = 1;				
			}
			
			if(result == 0)
				break;
		}	
	}
	
	void processSensorForwardRayQueueByIce()
	{
		int objAbstractType = 0;
		
		Iterator<Entry<Float, Object>> iterator = master.world.world2d.getRaySensorFixturesArray().iterator();
		int i = 0;
		float currentDistance = 0;
		float firstDistance = 0;
		float workIceballRadius = Statics.FightingSkills.FIGHTSKILL_ICE_FORWARD_START_RADIUS 
				+ fValues[Statics.FightingSkills.ATTACK_POWER_INDEX] * Statics.FightingSkills.FIGHTSKILL_ICE_FORWARD_STEP_RADIUS;
		
		while(iterator.hasNext())
		{
			int result = 0;
			Entry<Float, Object> item = iterator.next();
			Fixture fixture = (Fixture)item.value;
			GameObject gObj = (GameObject) fixture.getBody().getUserData();
			currentDistance = item.key * Statics.PhysicsBox2D.SENSOR_FIGHT_SKILL_FORWARD_RAY_RADIUS;
			
			if(currentDistance > workDistance)
				break;
			else if(i == 0)
			{
				objAbstractType = defineObstacleType(master, gObj);
				firstDistance = currentDistance;
			}
			else
			{			
				if(currentDistance - firstDistance > workIceballRadius)
					break;
				else
					objAbstractType = defineObstacleType(master, gObj);
			}
			
			switch(objAbstractType)
			{
				case 0:
					break;
				case Statics.DynamicGameObject.ABSTRACT_TYPE_OBSTACLE:
					result = 1;
					break;
				case Statics.DynamicGameObject.ABSTRACT_TYPE_OBSTACLE_INSUPERABLE:
					break;
				case Statics.DynamicGameObject.ABSTRACT_TYPE_ENEMY:
					if(currentDistance > Statics.FightingSkills.SENSOR_ICE_MININIMAL_WORK_DISTANCE)
						master.fSkills.addTarget(Statics.FightingSkills.SENSOR_VIRTUAL_FORWARD_RAY_ICE, gObj);
					break;
				case Statics.DynamicGameObject.ABSTRACT_TYPE_FRIEND:
					break;	
				default:
					result = 1;				
			}
			
			if(result == 0)
				break;
			++i;
		}	
	}
	
	void processSensorForwardRayQueueByHypnose()
	{
		Iterator<Entry<Float, Object>> iterator = master.world.world2d.getRaySensorFixturesArray().iterator();
		float currentDistance = 0;
		
		while(iterator.hasNext())
		{
			Entry<Float, Object> item = iterator.next();
			Fixture fixture = (Fixture)item.value;
			GameObject gObj = (GameObject) fixture.getBody().getUserData();
			currentDistance = item.key * Statics.PhysicsBox2D.SENSOR_FIGHT_SKILL_FORWARD_RAY_RADIUS;
			
			if(currentDistance > workDistance)
				break;
			else if(defineObstacleType(master, gObj) == Statics.DynamicGameObject.ABSTRACT_TYPE_ENEMY)
					master.fSkills.addTarget(Statics.FightingSkills.SENSOR_VIRTUAL_FORWARD_RAY_HYPNOSE, gObj);
		}	
	}
	
	void processSensorForwardRayQueueByFire()
	{
		int objAbstractType = 0;		
		Iterator<Entry<Float, Object>> iterator = master.world.world2d.getRaySensorFixturesArray().iterator();
		float currentDistance = 0;
		
		while(iterator.hasNext())
		{
			int result = 0;
			Entry<Float, Object> item = iterator.next();
			Fixture fixture = (Fixture)item.value;
			GameObject gObj = (GameObject) fixture.getBody().getUserData();
			currentDistance = item.key * Statics.PhysicsBox2D.SENSOR_FIGHT_SKILL_FORWARD_RAY_RADIUS;
			
			if(currentDistance > workDistance)
				break;
			else
				objAbstractType = defineObstacleTypeForFire(master, gObj, fixture);
			
			switch(objAbstractType)
			{
				case 0:
					break;
				case Statics.DynamicGameObject.ABSTRACT_TYPE_OBSTACLE:
					result = 1;
					break;
				case Statics.DynamicGameObject.ABSTRACT_TYPE_OBSTACLE_INSUPERABLE:
					break;
				case Statics.DynamicGameObject.ABSTRACT_TYPE_ENEMY:
					master.fSkills.addTarget(Statics.FightingSkills.SENSOR_VIRTUAL_FORWARD_RAY_FIRE, gObj);
					break;
				case Statics.DynamicGameObject.ABSTRACT_TYPE_FRIEND:
					break;	
				default:
					result = 1;				
			}
			
			if(result == 0)
				break;
		}	
	}

	
	void processSensorForwardRayQueueByWeaponThrow(DynamicGameObject weapon)
	{
		float currentDistance = 0;
		float firstDistance = 0;
		int objAbstractType = 0;				
		float penetrationDistance = 100;	
		float weaponContactWidth = weapon.weaponPenetrationWidth;
		int weaponContactMaterial = Statics.PhysicsBox2D.getWeaponStrikeMaterial(weapon.objType);
		int weaponLevel = weapon.stateHS.level;
		float weaponImpulse = Statics.PhysicsBox2D.WEAPON_ARM_THROW_IMPULSE_START + 
				fValues[Statics.FightingSkills.ATTACK_POWER_INDEX] * Statics.PhysicsBox2D.WEAPON_ARM_THROW_IMPULSE_STEP;
		
		Iterator<Entry<Float, Object>> iterator =  master.world.world2d.getRaySensorFixturesArray().iterator();
		int i = 0;
		
		while(iterator.hasNext())
		{
			int result = 0;
			Entry<Float, Object> item = iterator.next();
			Fixture fixture = (Fixture)item.value;
			GameObject gObj = (GameObject) fixture.getBody().getUserData();
			currentDistance = item.key * Statics.PhysicsBox2D.SENSOR_FIGHT_SKILL_FORWARD_RAY_RADIUS;
			
			if(currentDistance > workDistance)
				break;
			else if(i == 0)
			{
				objAbstractType = defineObstacleType(master, gObj);
				firstDistance = currentDistance;
			}
			else
			{
				currentDistance = currentDistance - firstDistance;
				
				if(currentDistance > penetrationDistance)
					break;
				else
					objAbstractType = defineObstacleType(master, gObj);
			}
			
			switch(objAbstractType)
			{
				case 0:
					break;
				case Statics.DynamicGameObject.ABSTRACT_TYPE_OBSTACLE:
					penetrationDistance = master.world.world2d.getPenetrationDistance(weaponImpulse, weaponContactWidth,
							weaponContactMaterial, weaponLevel, fixture) - currentDistance;
					if (penetrationDistance > 0)
						result = 1;
					break;
				case Statics.DynamicGameObject.ABSTRACT_TYPE_OBSTACLE_INSUPERABLE:
					break;
				case Statics.DynamicGameObject.ABSTRACT_TYPE_ENEMY:
					if(currentDistance + firstDistance >= Statics.FightingSkills.SENSOR_WEAPON_THROW_START_DISTANCE)
						master.fSkills.getSkill(type).tryThrowWeapon();
					break;
				case Statics.DynamicGameObject.ABSTRACT_TYPE_FRIEND:
					break;	
				default:
					result = 1;				
			}
			
			if(result == 0)
				break;
		}
	}
	
	public int defineObstacleType(DynamicGameObject dynObj, GameObject gObj)
	{
		if(!gObj.isDynamicObject)
			return Statics.DynamicGameObject.ABSTRACT_TYPE_OBSTACLE_INSUPERABLE;
		else
		{
			DynamicGameObject dObj = (DynamicGameObject) gObj;
			
			if(dObj.isCharacter)
			{
				if(isFriend(dynObj , dObj))
					return Statics.DynamicGameObject.ABSTRACT_TYPE_FRIEND;
				else
					return Statics.DynamicGameObject.ABSTRACT_TYPE_ENEMY;				
			}
			else
				return Statics.DynamicGameObject.ABSTRACT_TYPE_OBSTACLE;
		}
	}
	
	public int defineObstacleTypeForFire(DynamicGameObject dynObj, GameObject gObj, Fixture fixture)
	{
		if(!gObj.isDynamicObject)
			return Statics.DynamicGameObject.ABSTRACT_TYPE_OBSTACLE_INSUPERABLE;
		else
		{
			DynamicGameObject dObj = (DynamicGameObject) gObj;
			
			if(dObj.isCharacter)
			{
				if(isFriend(dynObj , dObj))
					return Statics.DynamicGameObject.ABSTRACT_TYPE_FRIEND;
				else
					return Statics.DynamicGameObject.ABSTRACT_TYPE_ENEMY;				
			}
			else
			{
				int material = (int) ((float[])fixture.getUserData())[PhysicsBox2d.FIXTURE_PROPERTY2_INDEX_MATERIAL];
				if(master.world.world2d.isInflammableMaterial(material))
					return Statics.DynamicGameObject.ABSTRACT_TYPE_OBSTACLE;
				else
					return Statics.DynamicGameObject.ABSTRACT_TYPE_OBSTACLE_INSUPERABLE;
			}
		}
	}
	
	
	public static boolean isFriend(DynamicGameObject dynObjA, DynamicGameObject dynObjB)
	{
		if((dynObjA.stateHS.frendByRace == true && (dynObjB.objType == dynObjA.objType)) || //friend by race
				(dynObjA.stateHS.frendToTeam > 0 && (!dynObjB.stateHS.isBot || dynObjA.stateHS.frendToTeam == dynObjB.stateHS.frendToTeam)) || //gamer team
				(dynObjA.stateHS.frendtoAlienRace == dynObjB.objType)	//char A is friend to char B race
		)
			return true;
		else
			return false;
	}
}
