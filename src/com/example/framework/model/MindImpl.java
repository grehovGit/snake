package com.example.framework.model;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.utils.ObjectMap.Entry;
import com.example.framework.utils.SortUtils;


abstract public class MindImpl implements Mind{	
	
	public MindImpl() {
		// TODO Auto-generated constructor stub
	}

	
	//general functions
	public boolean flyingHaoticMoving(DynamicGameObject dynObj)
	{		
		return true;
	}
	
	float getAppleWeight(DynamicGameObject dynObj)
	{
		//return  (dynObj.stateHS.defencePower < dynObj.stateHS.health ? 1 : dynObj.stateHS.defencePower / dynObj.stateHS.health) * ((dynObj.stateHS.health + dynObj.stateHS.health * Gifts.APPLE_HEALTH_INCREASE) / dynObj.stateHS.health);
		return  (float) (dynObj.stateHS.defencePower <= dynObj.stateHS.health ? 1.5f : 0.3f + Math.pow(dynObj.stateHS.defencePower / dynObj.stateHS.health, 2));
	}
	
	float getYellowMWeight(DynamicGameObject dynObj)
	{
		return  Gifts.MUSHROOM_YELLOW_COINS / (dynObj.stateHS.defencePower * Gifts.APPLE_HEALTH_INCREASE);
	}
	
	float getCharToEnemyAggressionWeight(DynamicGameObject dynObj, DynamicGameObject dObj)
	{
		int aggressionType = (int) Statics.AI.getWeightFromCharGoalsMap(Statics.DynamicGameObject.ABSTRACT_TYPE_ENEMY,
				dynObj.objType, dynObj.stateHS.level);
		
		//the character doesn't have politics about enemies
		if(aggressionType == 0)
			return 1;
		
		//rating based on cost of health: health 100 costs 100coins. from 0.1 to 10
		float coinsWeightKoef = 1 / (dynObj.stateHS.defencePower * Gifts.APPLE_HEALTH_INCREASE);
		float strongRate = getMyStrongToEnemyRate(dynObj, dObj);
		float enemyWeightKoef = 1;
		
		//then provide politics for current aggressive type
		switch(aggressionType)
		{
		case Statics.AI.AGGRESSION_TO_ENEMY_COLDMIND:
			enemyWeightKoef = agressionByClearProfit(dynObj, dObj, strongRate, coinsWeightKoef);
			break;
		case Statics.AI.AGGRESSION_TO_ENEMY_BY_PROFIT_RATIO_ALIVE:
			enemyWeightKoef = agressionByCoinsToDangerRateAlive(dynObj, dObj, strongRate, coinsWeightKoef);
			break;
		case Statics.AI.AGGRESSION_TO_ENEMY_AGGRESSIVE_ALIVE:
			enemyWeightKoef = agressionAlive(dynObj, dObj, strongRate, coinsWeightKoef);
			break;
		case Statics.AI.AGGRESSION_TO_ENEMY_BY_PROFIT_RATIO:
			enemyWeightKoef = agressionByCoinsToDangerRate(dynObj, dObj,strongRate, coinsWeightKoef);
			break;
		case Statics.AI.AGGRESSION_TO_ENEMY_MAD:
			enemyWeightKoef = agressionByCoins(dynObj, dObj, coinsWeightKoef);
			break;
		}
		
		return enemyWeightKoef;
	}
	
	float agressionByClearProfit(DynamicGameObject dynObj, DynamicGameObject dObj, float strongRate, float coinsWeightKoef)
	{
		float enemyWeightKoef;
		float lostHealth = dynObj.stateHS.health / strongRate;
		
		if(strongRate >= 1)
			enemyWeightKoef = Statics.AI.MAX_GOAL_WEIGHT_KOEF;
		else if(lostHealth >= dObj.stateHS.cost) 
			enemyWeightKoef = Statics.AI.MAX_GOAL_WEIGHT_KOEF * (strongRate < 1 ? 1 : Statics.AI.MAX_GOAL_WEIGHT_KOEF * strongRate);			
		else
			enemyWeightKoef = 1 / (coinsWeightKoef * (dObj.stateHS.cost - lostHealth));
		
		return enemyWeightKoef;
	}
	
	float agressionByCoinsToDangerRateAlive(DynamicGameObject dynObj, DynamicGameObject dObj, float strongRate, float coinsWeightKoef)
	{
		float enemyWeightKoef;
		float lostHealth = dynObj.stateHS.health / strongRate;
		
		if(strongRate >= 1)
			enemyWeightKoef = Statics.AI.MAX_GOAL_WEIGHT_KOEF;	
		else
			enemyWeightKoef = 1 / (coinsWeightKoef * dObj.stateHS.cost * ((dynObj.stateHS.health - lostHealth) / dynObj.stateHS.health));
		
		return enemyWeightKoef;
	}
	
	float agressionAlive(DynamicGameObject dynObj, DynamicGameObject dObj, float strongRate, float coinsWeightKoef)
	{
		float enemyWeightKoef;
		
		if(strongRate >= 1)
			enemyWeightKoef = Statics.AI.MAX_GOAL_WEIGHT_KOEF;	
		else
			enemyWeightKoef = 1 / (coinsWeightKoef * dObj.stateHS.cost);
		
		return enemyWeightKoef;
	}
	
	float agressionByCoinsToDangerRate(DynamicGameObject dynObj, DynamicGameObject dObj, float strongRate, float coinsWeightKoef)
	{
		float enemyWeightKoef;
		float lostHealth = dynObj.stateHS.health / strongRate;
		
		if(strongRate >= 1)
			enemyWeightKoef = Statics.AI.MAX_GOAL_WEIGHT_KOEF;	
		else
			enemyWeightKoef = 1 / (coinsWeightKoef * dObj.stateHS.cost * ((dynObj.stateHS.health >= lostHealth ? 1 : dynObj.stateHS.health / lostHealth)));
		
		return enemyWeightKoef;
	}
	
	float agressionByCoins(DynamicGameObject dynObj, DynamicGameObject dObj, float coinsWeightKoef)
	{
		return 1 / (coinsWeightKoef * dObj.stateHS.cost);
	}
	
	float getMyStrongToEnemyRate(DynamicGameObject dynObj, DynamicGameObject dObj)
	{
		float dynDefPower = dynObj.stateHS.defencePower;
		float dynAttackPower = dynObj.stateHS.attackPower;
		float dDefPower = dObj.stateHS.defencePower;
		float dAttackPower = dObj.stateHS.attackPower;
		
		if(dynDefPower == 0)
			dynDefPower = 0.1f;
		if(dynAttackPower == 0)
			dynAttackPower = 0.1f;
		if(dDefPower == 0)
			dDefPower = 0.1f;
		if(dAttackPower == 0)
			dAttackPower = 0.1f;
		
		float attacksNumberAlife = dynObj.stateHS.health / (dAttackPower * dAttackPower /(2 * dynDefPower)) < 1 ? 
				1 : dynObj.stateHS.health / (dAttackPower * dAttackPower / (2 * dynDefPower));		

		float attacksNumberAlifeEnemy = dObj.stateHS.health / (dynAttackPower * dynAttackPower / (2 * dDefPower)) < 1 ?
						1 : dObj.stateHS.health / (dynAttackPower * dynAttackPower / (2 * dDefPower));
		
		return attacksNumberAlife / attacksNumberAlifeEnemy;

	}
	
	float getGlobalGoalDefaultWeight(int goal, DynamicGameObject dynObj, DynamicGameObject dObj)
	{
		float weight = Statics.AI.GOAL_WEIGHT_DEFAULT;
		
		switch(goal)
		{
		case Statics.GameObject.APPLE:
			weight = getAppleWeight(dynObj);
			break;
		case Statics.GameObject.MUSHROOM_BLUE:
			weight = Statics.AI.WEIGHT_BLUE_MUSHROOM;
			break;
		case Statics.GameObject.MUSHROOM_YELLOW:
			weight = getYellowMWeight(dynObj);
			break;
		case Statics.GameObject.MUSHROOM_BLUE_RED:
			weight = Statics.AI.WEIGHT_BRED_MUSHROOM;
			break;
		case Statics.GameObject.MUSHROOM_BROWN:
			weight = Statics.AI.WEIGHT_BROWN_MUSHROOM;
			break;
		case Statics.DynamicGameObject.ABSTRACT_TYPE_ENEMY:
			weight = Statics.AI.GOAL_WEIGHT_DEFAULT;
			break;	
		case Statics.DynamicGameObject.ABSTRACT_TYPE_ENEMY_TARGET:
			weight = Statics.AI.GOAL_WEIGHT_DEFAULT;
		case Statics.DynamicGameObject.ABSTRACT_TYPE_FRIEND:
			weight = Statics.AI.GOAL_WEIGHT_DEFAULT;
			break;		
		case Statics.DynamicGameObject.ABSTRACT_TYPE_FRIEND_TO_PROTECT:
			weight = Statics.AI.GOAL_WEIGHT_DEFAULT;
			break;
		case Statics.DynamicGameObject.ABSTRACT_TYPE_PROTECTED_PLACE:
			weight = Statics.AI.GOAL_WEIGHT_DEFAULT;
			break;
		case Statics.DynamicGameObject.ABSTRACT_TYPE_WEAPON_HELM:
			weight = Statics.AI.GOAL_WEIGHT_DEFAULT;
			break;		
		case Statics.DynamicGameObject.ABSTRACT_TYPE_WEAPON_SHIELD:
			weight = Statics.AI.GOAL_WEIGHT_DEFAULT;
			break;		
		case Statics.DynamicGameObject.ABSTRACT_TYPE_WEAPON_SWORD:
			weight = Statics.AI.GOAL_WEIGHT_DEFAULT;
			break;
		case Statics.DynamicGameObject.ABSTRACT_TYPE_WEAPON_AXE:
			weight = Statics.AI.GOAL_WEIGHT_DEFAULT;
			break;
		case Statics.DynamicGameObject.ABSTRACT_TYPE_WEAPON_SPEAR:
			weight = Statics.AI.GOAL_WEIGHT_DEFAULT;
			break;
		}		
		return weight;
	}	
	
	float getGlobalGoalWeight(int goal, DynamicGameObject dynObj, DynamicGameObject dObj)
	{
		float weight = getGlobalGoalDefaultWeight(goal, dynObj, dObj);
		float charMapWeight = Statics.AI.GOAL_WEIGHT_DEFAULT;
		
		//implement charater's goals weights map
		if(goal == Statics.DynamicGameObject.ABSTRACT_TYPE_ENEMY)
			charMapWeight = getCharToEnemyAggressionWeight(dynObj, dObj);			
		else
			charMapWeight = Statics.AI.getWeightFromCharGoalsMap(goal, dynObj.objType, dynObj.stateHS.level);

		//add character mode goals weights map
		charMapWeight *= Statics.AI.getWeightFromModeGoalsMap(goal, dynObj.goalMode);
		
		if(charMapWeight == 0)
			weight = Statics.AI.GOAL_WEIGHT_DEFAULT;
		else
			weight *= charMapWeight;
	
		return weight;
	}
	
	
	void processTargets(DynamicGameObject dynObj)
	{				
		//targets processing by shield
		if (dynObj.fSkills.haveSkill(Statics.FightingSkills.WEAPON_HOLD_SHIELD))
		{
			FightingSkill shieldHold = dynObj.fSkills.getSkill(Statics.FightingSkills.WEAPON_HOLD_SHIELD);
			
			if(shieldHold.isReady)
				shieldHold.updateSensorsDataForShield();
		}
		
		int size = dynObj.fSkills.fTargets.size();
		List <Entry <Float, Entry<String, GameObject>>> ratedTargets = getRatedTargets(dynObj.fSkills.fTargets, dynObj);
		
		if(size > 0)
		{
			SortUtils.quickSortFloatStringObjectList(ratedTargets);
			//jumps, impulses and other skills live there:
			fSkillTargetsProcessing(ratedTargets, dynObj);							
			leaveMostTwoDangerTargets(ratedTargets);
		}			 
	
		if (dynObj.fSkills.haveSkill(Statics.FightingSkills.WEAPON_HOLD_LEFT_ARM))
		{
			FightingSkill armHold = dynObj.fSkills.getSkill(Statics.FightingSkills.WEAPON_HOLD_LEFT_ARM);
			armTargetsProcessing(dynObj, ratedTargets, armHold);
		}
		
		if (dynObj.fSkills.haveSkill(Statics.FightingSkills.WEAPON_HOLD_RIGHT_ARM))
		{
			FightingSkill armHold = dynObj.fSkills.getSkill(Statics.FightingSkills.WEAPON_HOLD_RIGHT_ARM);
			armTargetsProcessing(dynObj, ratedTargets, armHold);
		}
		
		if (size > 0 && dynObj.fSkills.haveSkill(Statics.FightingSkills.WEAPON_THROW_SHIELD))
		{
			GameObject topTarget = ratedTargets.get(0).value.value;
			dynObj.fSkills.getSkill(Statics.FightingSkills.WEAPON_THROW_SHIELD).targetPos.set(topTarget.position.x, topTarget.position.y);
		}	
	}
	
	
	void leaveMostTwoDangerTargets(List <Entry <Float, Entry<String, GameObject>>> ratedTargets)
	{
		int size = ratedTargets.size();
		
		if (size == 1)
			ratedTargets.add(ratedTargets.get(0));
		else if(size > 2)			
			ratedTargets = ratedTargets.subList(0, 2);
	}
	
	void armTargetsProcessing(DynamicGameObject dynObj, List <Entry <Float, Entry<String, GameObject>>> mostDangerTargets, FightingSkill armHold)
	{		 		
		if (armHold.isReady)
		{
			if(mostDangerTargets != null)
				armHold.setTargetPos(mostDangerTargets.get(0).value.value.position);
			else
				armHold.setTargetPos(null);
		}		
	}
	
	void fSkillTargetsProcessing(List <Entry <Float, Entry<String, GameObject>>> ratedTargets, DynamicGameObject master)
	{	
		//basic targets processing
		Entry <DynamicGameObject, FightingSkill> targetFSkill = defineLocalTargetAndFSkill(ratedTargets, master);			
		
		//reaction actions:
		if(targetFSkill.key != null && master.isActive())
		{
			//if can strike local target by fSkill do that, if can't - approach it by deykstra
			if(targetFSkill.value != null)
				master.fSkills.strike(master.world.actTime, targetFSkill.key, targetFSkill.value, master.stateHS.isBot);
			else
				master.objLocalTarget = targetFSkill.key;
		}
	}
	
	
	Entry <DynamicGameObject, FightingSkill> defineLocalTargetAndFSkill(List <Entry<Float, Entry<String, GameObject>>> orderedTargets, DynamicGameObject dynObj)
	{
		float maxLocalRate = 0, localRate = 0;		
		DynamicGameObject dObj, dObjTarget = null;
		Entry <Float, FightingSkill> optimalFSkill = null;
		FightingSkill optimFSkill = null;
		
		int size = orderedTargets.size();
				
		for(int i = 0; i < size; ++ i)
		{
			String sensor = orderedTargets.get(i).value.key;			
			dObj = (DynamicGameObject) orderedTargets.get(i).value.value;
			localRate = orderedTargets.get(i).key;			
			optimalFSkill = dynObj.fSkills.defineBestFSkillForTarget(sensor, dObj, localRate);
			//Proper fskill add up to 100% dangerous weight
			localRate *= optimalFSkill.key == 0 ? 0 : (1 + optimalFSkill.key.floatValue() / Statics.FightingSkills.FIGHTSKILL_MAX_WEIGHT);
			
			//if local target is also global target increase the rate
			if(dObj.equals(dynObj.objTarget))
			{
				float globalTargetRate = getGlobalGoalWeight(Statics.DynamicGameObject.ABSTRACT_TYPE_ENEMY_TARGET, dynObj, dObj);
				//increase of local rate: 0 - 100%
				localRate *= (1 + globalTargetRate > 1 ? 0 : (1 - globalTargetRate));  				
			}
			
			if(localRate > maxLocalRate)
			{
				maxLocalRate = localRate;
				dObjTarget = dObj;
				
				if(optimalFSkill.key > 0)				
					optimFSkill = optimalFSkill.value;	
				else
					optimFSkill= null;
			}			
		}
		
		Entry <DynamicGameObject, FightingSkill> targetFSkill = new Entry <DynamicGameObject, FightingSkill>();
		targetFSkill.key = dObjTarget;
		targetFSkill.value = optimFSkill;		
		return targetFSkill;		
	}	
	
	List <Entry <Float, Entry<String, GameObject>>> getRatedTargets(List <Entry<String, GameObject>> targets, DynamicGameObject dynObj)
	{
		List <Entry <Float, Entry<String, GameObject>>> ratedTargets = null;		
		int size = targets.size();
		
		if(size == 0)
			return ratedTargets;
		
		ratedTargets = new ArrayList <Entry <Float, Entry<String, GameObject>>>();
		
		for (Entry<String, GameObject> target : targets)
		{
			Entry <Float, Entry<String, GameObject>> ratedTarget = rateTargetByDanger(target, dynObj);
			ratedTarget = rateTargetByDistanceZone(ratedTarget, dynObj);
			ratedTargets.add(ratedTarget);
		}			
		return ratedTargets;				
	}
	
	Entry <Float, Entry<String, GameObject>> rateTargetByDanger(Entry<String, GameObject> target, DynamicGameObject dynObj)
	{
		DynamicGameObject dObj = (DynamicGameObject) target.value;
		float enemyDangerRate = 1 / getMyStrongToEnemyRate(dynObj, dObj);
		Float rate = new Float(enemyDangerRate);
		Entry <Float, Entry<String, GameObject>> ratedTarget = new Entry <Float, Entry <String, GameObject>>();
		ratedTarget.key = rate;
		ratedTarget.value = target;	
		return ratedTarget;
	}
	
	Entry <Float, Entry<String, GameObject>> rateTargetByDistanceZone(Entry <Float, Entry<String, GameObject>> target, DynamicGameObject dynObj)
	{
		float distKoef = Statics.FightingSkills.getDistanceZoneWeight(Statics.FightingSkills.getDistanceZoneBySensor(target.value.key));
		target.key *= distKoef;
		return target;
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
