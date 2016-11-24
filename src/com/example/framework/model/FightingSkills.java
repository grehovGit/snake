package com.example.framework.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.ArrayMap.Values;
import com.badlogic.gdx.utils.ObjectMap.Entry;

public class FightingSkills implements FightSkills {
	
 	public static final int WORLD_WIDTH = Statics.WKS.WORLD_WIDTH;
    public static final int WORLD_HEIGHT = Statics.WKS.WORLD_HEIGHT;
    public static final int CELL_DIM = Statics.WKS.CELL_DIM;
    public static final float CELL_SIZE = Statics.WKS.CELL_SIZE;
	
	ArrayMap <String, FightingSkill>  fSkills;
	List <String>  fSensors;
	List<Entry<String, GameObject>> fTargets;
	DynamicGameObject master;
	List <DynamicGameObject>  weaponQueue;
	
	boolean isRelaxing;
	float startRelaxTime; 
	
	//data for fast rendering
	public boolean forwardAttack = false;
	public boolean forwardLeftHook = false;
	public boolean forwardRightHook = false;
	public boolean forwardLeftTurn = false;
	public boolean forwardRightTurn = false;
	public boolean backLeftHook = false;
	public boolean backRightHook = false;
	public boolean backAttack = false;
	public boolean block = false;
	public boolean impulseForwardDef = false;
	public boolean impulseForwardAttack = false;
	public boolean impulseLeftSideDef = false;
	public boolean impulseRightSideDef = false;	
	public boolean iceForwardAttack = false;
	public boolean hypnoseForwardAttack = false;
	public boolean fireForwardAttack = false;
	
	FightingSkills(DynamicGameObject dynObj)
	{
		fSkills = new ArrayMap <String, FightingSkill>();
		fSensors = new ArrayList <String>();
		fTargets = new ArrayList<Entry<String, GameObject>>();
		weaponQueue = new ArrayList <DynamicGameObject>();
		master = dynObj;
		isRelaxing = false;
		startRelaxTime = 0;
	}
	
	public FightingSkill addSkill(String type, int fCount, float[] fields)
	{
		FightingSkill fSkill = new FightingSkill(type, fCount, fields, master);
		fSkills.put(type, fSkill);
		addNeededSensor(type);
		udpateRenderVariables(type, true);
		return  fSkill;
	}
	
	
	public FightingSkill addSkillDefault(String type)
	{
		FightingSkill fSkill = new FightingSkill(type, master);
		fSkills.put(type, fSkill);
		addNeededSensor(type);
		udpateRenderVariables(type, true);
		return fSkill;
	}
	
	void udpateRenderVariables(String type, boolean add_remove)
	{
		if(add_remove)
		{
			if(type == Statics.FightingSkills.FORWARD_ATTACK)
				forwardAttack = true;
			else if (type == Statics.FightingSkills.FORWARD_LEFT_HOOK)
				forwardLeftHook = true;			
			else if (type == Statics.FightingSkills.FORWARD_RIGHT_HOOK)
				forwardRightHook = true;
			else if (type == Statics.FightingSkills.FORWARD_LEFT_TURN)
				forwardLeftTurn = true;
			else if (type == Statics.FightingSkills.FORWARD_RIGHT_TURN)
				forwardRightTurn = true;
			else if (type == Statics.FightingSkills.BACK_LEFT_HOOK)
				backLeftHook = true;
			else if (type == Statics.FightingSkills.BACK_RIGHT_HOOK)
				backRightHook = true;
			else if (type == Statics.FightingSkills.BACK_ATTACK)
				backAttack = true;
			else if (type == Statics.FightingSkills.BLOCK)
				block = true;	
			else if (type == Statics.FightingSkills.IMPULSE_ATTACK_FORWARD)
				impulseForwardAttack = true;	
			else if (type == Statics.FightingSkills.IMPULSE_DEFFENSE_FORWARD)
				impulseForwardDef = true;	
			else if (type == Statics.FightingSkills.IMPULSE_DEFFENSE_LEFTSIDE)
				impulseLeftSideDef = true;	
			else if (type == Statics.FightingSkills.IMPULSE_DEFFENSE_RIGHTSIDE)
				impulseRightSideDef = true;					
			else if (type == Statics.FightingSkills.ICE_ATTACK_FORWARD)
				iceForwardAttack = true;	
			else if (type == Statics.FightingSkills.HYPNOSE_ATTACK_FORWARD)
				hypnoseForwardAttack = true;	
			else if (type == Statics.FightingSkills.FIRE_ATTACK_FORWARD)
				fireForwardAttack = true;	
		}
		else
		{
			if(type == Statics.FightingSkills.FORWARD_ATTACK)
				forwardAttack = false;
			else if (type == Statics.FightingSkills.FORWARD_LEFT_HOOK)
				forwardLeftHook = false;			
			else if (type == Statics.FightingSkills.FORWARD_RIGHT_HOOK)
				forwardRightHook = false;
			else if (type == Statics.FightingSkills.FORWARD_LEFT_TURN)
				forwardLeftTurn = false;
			else if (type == Statics.FightingSkills.FORWARD_RIGHT_TURN)
				forwardRightTurn = false;
			else if (type == Statics.FightingSkills.BACK_LEFT_HOOK)
				backLeftHook = false;
			else if (type == Statics.FightingSkills.BACK_RIGHT_HOOK)
				backRightHook = false;
			else if (type == Statics.FightingSkills.BACK_ATTACK)
				backAttack = false;
			else if (type == Statics.FightingSkills.BLOCK)
				block = false;
			else if (type == Statics.FightingSkills.IMPULSE_ATTACK_FORWARD)
				impulseForwardAttack = false;	
			else if (type == Statics.FightingSkills.IMPULSE_DEFFENSE_FORWARD)
				impulseForwardDef = false;	
			else if (type == Statics.FightingSkills.IMPULSE_DEFFENSE_LEFTSIDE)
				impulseLeftSideDef = false;	
			else if (type == Statics.FightingSkills.IMPULSE_DEFFENSE_RIGHTSIDE)
				impulseRightSideDef = false;
			else if (type == Statics.FightingSkills.ICE_ATTACK_FORWARD)
				iceForwardAttack = false;	
			else if (type == Statics.FightingSkills.HYPNOSE_ATTACK_FORWARD)
				hypnoseForwardAttack = false;	
			else if (type == Statics.FightingSkills.FIRE_ATTACK_FORWARD)
				fireForwardAttack = false;			
		}			
	}
	
	public void addNeededSensor(String type)
	{
		String sensor = "";
		
		if((type == Statics.FightingSkills.FORWARD_ATTACK || type == Statics.FightingSkills.FORWARD_LEFT_HOOK
				|| type == Statics.FightingSkills.FORWARD_RIGHT_HOOK || type == Statics.FightingSkills.IMPULSE_DEFFENSE_FORWARD) 
				&& !fSensors.contains(Statics.FightingSkills.SENSOR_FORWARD))
			sensor = Statics.FightingSkills.SENSOR_FORWARD;
			
		else if((type == Statics.FightingSkills.BACK_LEFT_HOOK || type == Statics.FightingSkills.FORWARD_LEFT_TURN
				|| type == Statics.FightingSkills.IMPULSE_DEFFENSE_LEFTSIDE)
			&& !fSensors.contains(Statics.FightingSkills.SENSOR_LEFT_SIDE)) 
				sensor = Statics.FightingSkills.SENSOR_LEFT_SIDE;
					
		else if((type == Statics.FightingSkills.BACK_RIGHT_HOOK || type == Statics.FightingSkills.FORWARD_RIGHT_TURN
				|| type == Statics.FightingSkills.IMPULSE_DEFFENSE_RIGHTSIDE)
		&& !fSensors.contains(Statics.FightingSkills.SENSOR_RIGHT_SIDE)) 
			sensor = Statics.FightingSkills.SENSOR_RIGHT_SIDE;	
		
		else if((type == Statics.FightingSkills.BACK_ATTACK)
				&& !fSensors.contains(Statics.FightingSkills.SENSOR_BACK)) 
			sensor = Statics.FightingSkills.SENSOR_BACK;
		
		else if(type == Statics.FightingSkills.WEAPON_HOLD_LEFT_ARM || 
				type == Statics.FightingSkills.WEAPON_HOLD_RIGHT_ARM ||
				type == Statics.FightingSkills.WEAPON_HOLD_SHIELD)
		{
			if(!fSensors.contains(Statics.FightingSkills.SENSOR_FORWARD))
				sensor = Statics.FightingSkills.SENSOR_FORWARD;
			else if(!fSensors.contains(Statics.FightingSkills.SENSOR_LEFT_SIDE))
				sensor = Statics.FightingSkills.SENSOR_LEFT_SIDE;
			else if(!fSensors.contains(Statics.FightingSkills.SENSOR_RIGHT_SIDE))
				sensor = Statics.FightingSkills.SENSOR_RIGHT_SIDE;
		}		
		
		if(sensor != "")
		{
			fSensors.add(sensor);	
			
			if(master.objType == Statics.DynamicGameObject.SNAKE) 
			{
				Snake snake = (Snake) master;
				int size = snake.parts.size();

				if(sensor ==  Statics.FightingSkills.SENSOR_FORWARD)
				{
					master.world.world2d.addFSkillSensor(snake.parts.get(0), sensor);
				}
				else if(sensor ==  Statics.FightingSkills.SENSOR_LEFT_SIDE || sensor ==  Statics.FightingSkills.SENSOR_RIGHT_SIDE)
				{					
					for(int i = 0; i < size; ++i)
					{
						master.world.world2d.addFSkillSensor(snake.parts.get(i), sensor);
					}
				}
				else if(sensor ==  Statics.FightingSkills.SENSOR_BACK)
				{
					master.world.world2d.addFSkillSensor(snake.parts.get(size - 1), sensor);					
				}
			}
			else
				master.world.world2d.addFSkillSensor(master, sensor);
			
			//call recursive while not all needed sensors created
			if(type == Statics.FightingSkills.WEAPON_HOLD_LEFT_ARM ||  type == Statics.FightingSkills.WEAPON_HOLD_RIGHT_ARM
					|| type == Statics.FightingSkills.WEAPON_HOLD_SHIELD)
				addNeededSensor(type);
		}
	}
	
	public void removeSkill(String type)
	{
		fSkills.removeKey(type);
		udpateRenderVariables(type, false);
	}
	
	public boolean haveSkill(String type)
	{
		return fSkills.containsKey(type);
	}
	
	public void setSkillLevel(String type, int newLevel)
	{
		FightingSkill fSkill = fSkills.get(type);
		
		if(fSkill != null)
		{
			fSkill.fValues[Statics.FightingSkills.ATTACK_POWER_INDEX] = newLevel;
			fSkill.workDistance = Statics.FightingSkills.getFSkillWorkDistance(type, newLevel);
		}
	}
	
	public void setSkillChargeLevel(String type, int newLevel)
	{
		FightingSkill fSkill = fSkills.get(type);
		
		if(fSkill != null)
			fSkill.fValues[Statics.FightingSkills.CHARGE_POWER_INDEX] = newLevel;
	}
	
	public boolean haveSensor(String type)
	{
		return fSensors.contains(type);
	}
	
	public FightingSkill getSkill(String type)
	{
		return fSkills.get(type);
	}
	
	public boolean isSkillReady(String type)
	{
		FightingSkill fSkill = fSkills.get(type);
		
		if(fSkill != null)
			return fSkill.isReady;
		else
			return false;
	}
	
	public void addTarget(String sensor, GameObject target)
	{
		if(haveTarget(sensor, target))
			return;
		
		Entry<String, GameObject> entry = new Entry<String, GameObject>();
		entry.key = sensor;
		entry.value = target;
		fTargets.add(entry);
	}
	
	public void removeTarget(String sensor, GameObject targetObj)
	{
		int index = 0;
		Iterator <Entry<String, GameObject>> targetsIterator = fTargets.iterator();
		Entry <String, GameObject> target = null;
		boolean targetToRemoveFlag = false;
		
		while(targetsIterator.hasNext())
		{
			target = targetsIterator.next();
			
			if(target.key == sensor && target.value == targetObj)
			{
				targetToRemoveFlag = true;
				break;
			}
			++index;
		}
		
		if(targetToRemoveFlag)
			fTargets.remove(index);
	}
	
	public void clearTargets()
	{
		int size = fTargets.size();
		
		for (int i = 0; i < size; ++i)
		{
			Entry <String, GameObject> target = fTargets.get(i);
			
			if(target.key == null || target.value == null || target.value.myBody == null 
				|| target.key == Statics.FightingSkills.SENSOR_FORWARD_RAY
				||target.key == Statics.FightingSkills.SENSOR_VIRTUAL_FORWARD_RAY_IMPULSE
				||target.key == Statics.FightingSkills.SENSOR_VIRTUAL_FORWARD_RAY_ICE
				||target.key == Statics.FightingSkills.SENSOR_VIRTUAL_FORWARD_RAY_HYPNOSE
				||target.key == Statics.FightingSkills.SENSOR_VIRTUAL_FORWARD_RAY_FIRE
				|| master.position.dist(target.value.position) > 2.5f * Statics.FightingSkills.getSensorRadius(target.key))
			{
				fTargets.remove(i);
				--i;
				--size;
			}
		}		
	}
	
	public boolean haveTarget(String sensor, GameObject targetObj)
	{
		Iterator <Entry<String, GameObject>> targetsIterator = fTargets.iterator();
		
		while(targetsIterator.hasNext())
		{
			Entry <String, GameObject> target = targetsIterator.next();
			
			if(target.key == sensor && target.value == targetObj)
				return true;
		}		
		return false;
	}
	
	public boolean haveTargets()
	{
		if(fSensors.size() > 0)
			return true;
		else
			return false;
	}
	
	@Override
	public void update(float actTime, float deltaTime)
	{
		clearTargets();		
		Values<FightingSkill> skills = fSkills.values();
		
		if(isRelaxing)
			processRelaxing(actTime);
		
		if(hasRaySensorFSkills())
		{
			Vector2 targetPoint = new Vector2(Statics.PhysicsBox2D.SENSOR_FIGHT_SKILL_FORWARD_RAY_RADIUS, 0);
			targetPoint.setAngle(master.angle).add(master.position.x, master.position.y);			
			master.world.world2d.fSkillRayCast(master, targetPoint);
		}
		
		while(skills.hasNext())			
			skills.next().update(actTime, deltaTime);	
		
		processWeaponQueue();
	}
	
	void processRelaxing(float actTime)
	{
		if(actTime - startRelaxTime >= Statics.FightingSkills.FIGHTSKILL_RELAX_PERIOD)
		{
			isRelaxing  = false;
			startRelaxTime = 0;
		}		
	}
	
	public void startRelaxing()
	{
		isRelaxing = true;
		startRelaxTime = master.world.actTime;
	}
	
	public void strike(float actTime, DynamicGameObject target, FightingSkill fSkill, boolean isBot)
	{
		if(!isRelaxing && fSkill.isReady && !master.stateHS.isStriking && !master.stateHS.isHeated && !master.stateHS.isHypnosed)
			fSkill.strikeStart(actTime, target, isBot);
	}
	
	public void strikeAvatar(float actTime, String _fSkill)
	{
		if(!master.stateHS.isStriking && !master.stateHS.isHeated && !master.stateHS.isHypnosed)
		{
			FightingSkill fSkill = getSkill(_fSkill);
			
			if(fSkill != null && fSkill.isReady)
				fSkill.strikeStart(actTime, null, false);
		}
	}
	
	public String[] getFSkills()
	{
		return fSkills.keys;
	}
	
	List <FightingSkill> getFSkillsBySensor(String fSensor)
	{
		List <FightingSkill>  fSkillsList;
		fSkillsList = new ArrayList <FightingSkill>();	
		List <String> fSkillsBySensorList = Statics.FightingSkills.getFSkillsBySensor(fSensor);
		
		for (String fSkill : fSkillsBySensorList)
			addFSkillBySensor(fSkillsList, fSkill);
		
		return fSkillsList;
	}
	
	public boolean hasRaySensorFSkills()
	{
		List <String> fSkillsByRaySensorList = Statics.FightingSkills.getFSkillsBySensor(Statics.FightingSkills.SENSOR_FORWARD_RAY);
		for (String fSkill : fSkillsByRaySensorList)
			if(haveSkill(fSkill))
				return true;
		
		return false;
	}
	
	void addFSkillBySensor(List <FightingSkill> fSkillsList, String fSkill){
		
		FightingSkill fightSkill = fSkills.get(fSkill);
		
		if(fightSkill != null)
			fSkillsList.add(fightSkill);		
	}
	
	Entry <Float, FightingSkill> defineBestFSkillForTarget(String fSensor, DynamicGameObject target, float forceRate)
	{
		Entry <Float, FightingSkill> result = new Entry <Float, FightingSkill>();
		result.key = new Float(0);
		result.value = null;
		
		List <FightingSkill>  fSkillsList =  getFSkillsBySensor(fSensor);
		
		if(fSkillsList == null)
			return result;
		
		//get the optimal fSkill for current target with highest rate
		int size = fSkillsList.size();
		float maxFSkillRate = 0;
		float fSkillRate = 0;
		FightingSkill optimalFSkill = null, curFSkill= null;
		
		float dst = Vector2.dst(master.position.x, master.position.y, target.position.x, target.position.y);
		
		for (int i = 0; i < size; ++i)
		{
			curFSkill = fSkillsList.get(i);
			
			if(!curFSkill.isReady)
				continue;
						
			if(dst > curFSkill.workDistance)
				continue;				
			
			fSkillRate = curFSkill.fSkillRate;										   // 1 - 1.8
			fSkillRate *= curFSkill.rateByDistanceClass; 							   // 1 - 8
			fSkillRate *= getObstacleKoefToFSkill(curFSkill.type, target);			   // 0 - 1
			fSkillRate *= getFSkillOptimalRateForTarget(curFSkill, target, forceRate); // 0.01 - 1
			
			if(fSkillRate > maxFSkillRate)
			{
				maxFSkillRate = fSkillRate;
				optimalFSkill = fSkillsList.get(i);
			}
		}
		
		result.key = maxFSkillRate;
		result.value = optimalFSkill;		
		return result;
	}
	
	float getObstacleKoefToFSkill(String type, DynamicGameObject target)
	{
		float obWeight = 0;
		if(type == Statics.FightingSkills.FORWARD_ATTACK)
			obWeight = master.world.wProc.getObstacleKoefForForwardAttack(master, target);
		else if (type == Statics.FightingSkills.FORWARD_LEFT_HOOK)
			obWeight = master.world.wProc.getObstacleKoefForHook(master, target, true);	
		else if (type == Statics.FightingSkills.FORWARD_RIGHT_HOOK)
			obWeight = master.world.wProc.getObstacleKoefForHook(master, target, false);				
		else if (type == Statics.FightingSkills.FORWARD_LEFT_TURN)
			obWeight = master.world.wProc.getObstacleKoefForHook(master, target, false);	
		else if(type == Statics.FightingSkills.FORWARD_RIGHT_TURN)
			obWeight = master.world.wProc.getObstacleKoefForHook(master, target, true);	
		else if (type == Statics.FightingSkills.BACK_LEFT_HOOK)
			obWeight = master.world.wProc.getObstacleKoefForBackHook(master, target, true);				
		else if(type == Statics.FightingSkills.BACK_RIGHT_HOOK)
			obWeight = master.world.wProc.getObstacleKoefForBackHook(master, target, false);	
		else if (type == Statics.FightingSkills.BACK_ATTACK)
			obWeight = master.world.wProc.getObstacleKoefForBackAttack(master, target);
		else if (type == Statics.FightingSkills.BLOCK)
			return 1;
		else if (type == Statics.FightingSkills.IMPULSE_DEFFENSE_FORWARD)
			return 1;
		else if (type == Statics.FightingSkills.IMPULSE_DEFFENSE_LEFTSIDE || type == Statics.FightingSkills.IMPULSE_DEFFENSE_RIGHTSIDE)
			return 1;
		else if (type == Statics.FightingSkills.IMPULSE_ATTACK_FORWARD
					|| type == Statics.FightingSkills.ICE_ATTACK_FORWARD
					|| type == Statics.FightingSkills.HYPNOSE_ATTACK_FORWARD
					|| type == Statics.FightingSkills.FIRE_ATTACK_FORWARD
					|| type == Statics.FightingSkills.WEAPON_THROW
					|| type == Statics.FightingSkills.WEAPON_THROW_SPEAR)
			return 1;
		else
			return 1; 
		
		obWeight = 1 - obWeight / Statics.AI.MAX_WEIGHT;
		
		return obWeight <= 0 ? 0 : obWeight;
	}
	
	
	
	float getFSkillOptimalRateForTarget(FightingSkill fSkill, DynamicGameObject target, float forceRate)
	{
		//optimal fSkillPower to forceRate formula: 
		//power >= FIGHTSKILL_POWER_START && power <= FIGHTSKILL_POWER_FINISH: F(power) / (F(power) +  DeltaF(power))

		//F(power): forceRate = DeadlyForceRate / (1 + force) ^ 2
		//DeltaF(power): [OurForceRate] - [forceRate = F(power) = DeadlyForceRate / (1 + force) ^ 2]
		
		//let's go:
		float power = fSkill.fValues[Statics.FightingSkills.ATTACK_POWER_INDEX];	
		float optimalForceRate = (float) (Statics.FightingSkills.FIGHTSKILL_DEADLY_FORCE_RATE / Math.pow((1 + power), 2));
		float optimalFSkillRate = optimalForceRate / (Math.abs(forceRate - optimalForceRate) + optimalForceRate);

		return optimalFSkillRate;
	}
	
	 public boolean processNewWeaponQueue(DynamicGameObject weapon) {
		 
		 	boolean need = false;
		 	int type = weapon.objType;
		 	int weaponClass = Statics.DynamicGameObject.getWeaponClass(type);
		 	FightingSkill fSkill = null;
		 		
			if(weaponClass == Statics.DynamicGameObject.ABSTRACT_TYPE_WEAPON_HELM && haveSkill(Statics.FightingSkills.WEAPON_HOLD_HELM))
			{
				fSkill = getSkill(Statics.FightingSkills.WEAPON_HOLD_HELM);
				
				if(!fSkill.isReady && fSkill.isStartStriking == true)
					need = true;
			}
			else if(weaponClass == Statics.DynamicGameObject.ABSTRACT_TYPE_WEAPON_SHIELD && haveSkill(Statics.FightingSkills.WEAPON_HOLD_SHIELD))
			{
				fSkill = getSkill(Statics.FightingSkills.WEAPON_HOLD_SHIELD);
				
				if(!fSkill.isReady && fSkill.isStartStriking == true)
					need = true;		
			}
			else if((weaponClass == Statics.DynamicGameObject.ABSTRACT_TYPE_WEAPON_SWORD
					|| weaponClass == Statics.DynamicGameObject.ABSTRACT_TYPE_WEAPON_AXE
					|| weaponClass == Statics.DynamicGameObject.ABSTRACT_TYPE_WEAPON_SPEAR))
			{			
				if(haveSkill(Statics.FightingSkills.WEAPON_HOLD_LEFT_ARM))
				{
					fSkill = getSkill(Statics.FightingSkills.WEAPON_HOLD_LEFT_ARM);
					
					if(!fSkill.isReady && fSkill.isStartStriking == true)
						need = true;
				}
				if(haveSkill(Statics.FightingSkills.WEAPON_HOLD_RIGHT_ARM))
				{
					fSkill = getSkill(Statics.FightingSkills.WEAPON_HOLD_RIGHT_ARM);
					
					if(!fSkill.isReady && fSkill.isStartStriking == true)
						need = true;
				}
			}
			
			if(need)
			{	
				if(!weaponQueue.contains(weapon))
					weaponQueue.add(weapon);
				
				return true;
			}			
			return false;			
		}
	 
	 public void processWeaponQueue() {
		 
		 int size = weaponQueue.size();
		 
		 for(int i = 0; i < size; ++i)
			 processWeaponHolding(weaponQueue.get(i));
		 
		 weaponQueue.clear();		 
	 }
	
	 public void processWeaponHolding(DynamicGameObject weapon) {
		 
		 	int type = weapon.objType;
		 		
			if(Statics.DynamicGameObject.getWeaponClass(type) == Statics.DynamicGameObject.ABSTRACT_TYPE_WEAPON_HELM 
					&& haveSkill(Statics.FightingSkills.WEAPON_HOLD_HELM))
			{
				FightingSkill fSkill = getSkill(Statics.FightingSkills.WEAPON_HOLD_HELM);
				if(!fSkill.isReady)
				{
					master.world.world2d.processPhysicalHelmHolding(master, weapon.myBody);
					fSkill.isReady = true;					
				}
			}
			else if(Statics.DynamicGameObject.getWeaponClass(type) == Statics.DynamicGameObject.ABSTRACT_TYPE_WEAPON_SHIELD 
					&& haveSkill(Statics.FightingSkills.WEAPON_HOLD_SHIELD))
			{
				FightingSkill fSkill = getSkill(Statics.FightingSkills.WEAPON_HOLD_SHIELD);
				if(!fSkill.isReady)
				{
					master.world.world2d.processPhysicalShieldHolding(master, weapon.myBody);
					fSkill.isReady = true;
					fSkill.weapon = weapon;
				}		
			}
			else if((Statics.DynamicGameObject.getWeaponClass(type) == Statics.DynamicGameObject.ABSTRACT_TYPE_WEAPON_SWORD
					|| Statics.DynamicGameObject.getWeaponClass(type) == Statics.DynamicGameObject.ABSTRACT_TYPE_WEAPON_AXE
					|| Statics.DynamicGameObject.getWeaponClass(type) == Statics.DynamicGameObject.ABSTRACT_TYPE_WEAPON_SPEAR))
			{
				FightingSkill fSkill = null;
				
				if (haveSkill(Statics.FightingSkills.WEAPON_HOLD_LEFT_ARM) && !getSkill(Statics.FightingSkills.WEAPON_HOLD_LEFT_ARM).isReady)
				{
					fSkill = getSkill(Statics.FightingSkills.WEAPON_HOLD_LEFT_ARM);
					processArmWeaponHolding(weapon, fSkill.getPower(), true);	
					fSkill.isReady = true;
					fSkill.weapon = weapon;
				}
				else if (haveSkill(Statics.FightingSkills.WEAPON_HOLD_RIGHT_ARM) && !getSkill(Statics.FightingSkills.WEAPON_HOLD_RIGHT_ARM).isReady)
				{
					fSkill = getSkill(Statics.FightingSkills.WEAPON_HOLD_RIGHT_ARM);
					processArmWeaponHolding(weapon, fSkill.getPower(), false);	
					fSkill.isReady = true;
					fSkill.weapon = weapon;
				}
			}		
		}
 
	 
	 public void processArmWeaponHolding(DynamicGameObject weapon, float power, boolean isLeft) {
		 
		 if (Statics.DynamicGameObject.getWeaponClass(weapon.objType) == Statics.DynamicGameObject.ABSTRACT_TYPE_WEAPON_SWORD)
			 master.world.world2d.processArmSwordHolding(master, weapon, isLeft, (int) power);
		 else if (Statics.DynamicGameObject.getWeaponClass(weapon.objType) == Statics.DynamicGameObject.ABSTRACT_TYPE_WEAPON_AXE)
			 master.world.world2d.processArmAxeHolding(master, weapon, isLeft, (int) power);	 
		 else if (Statics.DynamicGameObject.getWeaponClass(weapon.objType) == Statics.DynamicGameObject.ABSTRACT_TYPE_WEAPON_SPEAR)
			 master.world.world2d.processArmSpearHolding(master, weapon, isLeft, (int) power);
	 }
		
}
