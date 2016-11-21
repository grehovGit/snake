package com.kingsnake.physicsBox2d;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.util.ArrayMap;
import android.util.Log;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.PrismaticJoint;
import com.badlogic.gdx.physics.box2d.joints.PrismaticJointDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap.Entry;
import com.example.framework.model.DynamicEffect;
import com.example.framework.model.DynamicEffectHypnose;
import com.example.framework.model.DynamicGameObject;
import com.example.framework.model.GameObject;
import com.example.framework.model.Gifts;
import com.example.framework.model.HealthScore;
import com.example.framework.model.SnakePart;
import com.example.framework.model.StaticEffect;
import com.example.framework.model.Statics;
import com.example.framework.model.WorldKingSnake;
import com.example.framework.model.WorldProcessingImpl;
import com.kingsnake.gl.ParticlesEffectsManager;

public class MyContactListener implements ContactListener{
	
	World world2d;
	WorldKingSnake world;
	PhysicsBox2d worldPhys;
	Vector2 tVector, tVector2;
	
	MyContactListener(World world2d_, WorldKingSnake world_, PhysicsBox2d worldPh)
	{
		world2d = world2d_;
		world = world_;
		worldPhys = worldPh;

		tVector = new Vector2();
		tVector2 = new Vector2();
	}

	@Override
	public void beginContact(Contact contact) {
		// TODO Auto-generated method 
		
		Fixture fixA = contact.getFixtureA();
		Fixture fixB = contact.getFixtureB();		
		GameObject gObjA = (GameObject)fixA.getBody().getUserData();
		GameObject gObjB = (GameObject)fixB.getBody().getUserData();	
		
		if(gObjA == null || gObjB == null)
			return;
		
		beginContactProcess(gObjA, fixA, gObjB, fixB, contact);
		beginContactProcess(gObjB, fixB, gObjA, fixA, contact);			
	}
	
	public void beginContactProcess(GameObject gObjA, Fixture fixA, GameObject gObjB, Fixture fixB, Contact contact) {
		
		if(gObjA.isDynamicObject && ((DynamicGameObject)gObjA).isCharacter)
		{				
			if(fixA.isSensor())
				contactCharObjSensorTo(fixA, (DynamicGameObject)gObjA, fixB, contact, true);				
			else if(!fixB.isSensor())
				contactCharObjTo(fixA, (DynamicGameObject)gObjA, gObjB, contact, true);
			else if(fixB.isSensor())
				contactCharObjToSensor(fixA, (DynamicGameObject)gObjA, gObjB, fixB, contact);
		}
		//else if(gObjA.isDynamicObject)
		if(gObjA.isDynamicObject)
		{
			DynamicGameObject dynObjA = (DynamicGameObject)gObjA;			
			if(fixA.isSensor() && dynObjA.isDynamicEffectWithSensor() && !fixB.isSensor())
				contactDynEffectSensorTo(fixA, dynObjA, fixB, contact);	
			else
				contDynObjTo(dynObjA, gObjB, contact);
			
		}		
	}
	
	public void contactCharObjSensorTo(Fixture fixA, DynamicGameObject dynObjA, Fixture fixB, Contact contact, boolean isA) 
	{	
		GameObject gObjB = (GameObject) fixB.getBody().getUserData();
		float [] fixAdata = ((float[])fixA.getUserData());
		float [] fixBdata = ((float[])fixB.getUserData());
		
		if(fixAdata[PhysicsBox2d.FIXTURE_PROPERTY1_INDEX_FIXTURE_TYPE] == PhysicsBox2d.FIXTURE_PROPERTY1_SENSOR_FOR_CORRECT_MOVING_FORCE)
		{//char sensor for correcting forces to escape impacts with relief etc..
			
			boolean needCorrectingForce = false;
			
			if(gObjB.isDynamicObject)
			{
				DynamicGameObject dynObjB = (DynamicGameObject) gObjB;
				
				if(dynObjB.isCharacter)
				{//char moving correcting sensor to char
			
				}
				else
				{//char moving correcting sensor to dyn
					
					//if dynB is a bomb
					if(dynObjB.objType == DynamicGameObject.BOMB_FROM_TREE && dynObjB.stateHS.isDead != HealthScore.DEAD_DELETED)
						needCorrectingForce = true;
				}
			}
			else
			{//char moving correcting sensor to relief		
			
				if(gObjB.type >= WorldKingSnake.WALL && gObjB.type <= WorldKingSnake.REILEF_END_OF_INSURMOUNTABLE_RANGE)
					needCorrectingForce = true;					
			}
			
			if(needCorrectingForce)
			{			
				if(fixAdata[PhysicsBox2d.FIXTURE_PROPERTY4_INDEX_FIXTURE_INDEX] == PhysicsBox2d.FIXTURE_PROPERTY4_SENSOR_CORRECT_MOVING_FORCE_UP)
				{
					com.kingsnake.math.Vector2 correctForce = new com.kingsnake.math.Vector2(world.actTime + Statics.PhysicsBox2D.CORRECT_POWER_LIFETIME, -1);
					dynObjA.correctForces.add(correctForce);
					worldPhys.charMoveCorrectingForces.add(fixB);
				}
				else if(fixAdata[PhysicsBox2d.FIXTURE_PROPERTY4_INDEX_FIXTURE_INDEX] == PhysicsBox2d.FIXTURE_PROPERTY4_SENSOR_CORRECT_MOVING_FORCE_DOWN)
				{
					com.kingsnake.math.Vector2 correctForce = new com.kingsnake.math.Vector2(world.actTime + Statics.PhysicsBox2D.CORRECT_POWER_LIFETIME, 1);
					dynObjA.correctForces.add(correctForce);
					worldPhys.charMoveCorrectingForces.add(fixB);
				}
			}
		}
		else if(fixAdata[PhysicsBox2d.FIXTURE_PROPERTY1_INDEX_FIXTURE_TYPE] == PhysicsBox2d.FIXTURE_PROPERTY1_SENSOR_FOR_FIGHT_SKILL
				&& fixBdata != null && (fixBdata[PhysicsBox2d.FIXTURE_PROPERTY1_INDEX_FIXTURE_TYPE] == PhysicsBox2d.FIXTURE_PROPERTY1_IS_ATTACK_ACTIVE ||
				fixBdata[PhysicsBox2d.FIXTURE_PROPERTY1_INDEX_FIXTURE_TYPE] == PhysicsBox2d.FIXTURE_PROPERTY1_IS_ATTACK_PASSIVE))
		{//char fight skill sensor
			
			boolean enemyDetected = false;
			
			if(gObjB.isDynamicObject)
			{
				DynamicGameObject dynObjB = (DynamicGameObject) gObjB;
				
				if(dynObjB.isCharacter)
				{//char sensor to char
					
					if(!world.wProc.isCharFriend(dynObjA, dynObjB))
						enemyDetected = true;
				}
				else
				{//char sensor to dyn
					
				}
			}
			else
			{//char sensor to relief or static		
							
			}
			
			if(enemyDetected)
			{	
				String sensor;
			
				if(fixAdata[PhysicsBox2d.FIXTURE_PROPERTY4_INDEX_FIXTURE_INDEX] == PhysicsBox2d.FIXTURE_PROPERTY4_SENSOR_FIGHT_SKILL_FORWARD)
					sensor = Statics.FightingSkills.SENSOR_FORWARD;
				else if(fixAdata[PhysicsBox2d.FIXTURE_PROPERTY4_INDEX_FIXTURE_INDEX] == PhysicsBox2d.FIXTURE_PROPERTY4_SENSOR_FIGHT_SKILL_LEFT)
					sensor = Statics.FightingSkills.SENSOR_LEFT_SIDE;
				else if(fixAdata[PhysicsBox2d.FIXTURE_PROPERTY4_INDEX_FIXTURE_INDEX] == PhysicsBox2d.FIXTURE_PROPERTY4_SENSOR_FIGHT_SKILL_RIGHT)
					sensor = Statics.FightingSkills.SENSOR_RIGHT_SIDE;
				else if(fixAdata[PhysicsBox2d.FIXTURE_PROPERTY4_INDEX_FIXTURE_INDEX] == PhysicsBox2d.FIXTURE_PROPERTY4_SENSOR_FIGHT_SKILL_BACK)
					sensor = Statics.FightingSkills.SENSOR_BACK;
				else return;
				
				//if snake, add target to general fSkills, but not to part's fSkills
				if(dynObjA.objType == Statics.DynamicGameObject.SNAKE)
					dynObjA = ((SnakePart)dynObjA).snake;
				
				dynObjA.fSkills.addTarget(sensor, gObjB);
			}
		}
		else if(((float[])fixA.getUserData())[PhysicsBox2d.FIXTURE_PROPERTY1_INDEX_FIXTURE_TYPE] == PhysicsBox2d.FIXTURE_PROPERTY1_SENSOR_TONGUE
				&& world.actTime - ((float[])fixA.getUserData())[PhysicsBox2d.FIXTURE_PROPERTY3_INDEX_HEALTH] >= Statics.PhysicsBox2D.SENSOR_TONGUE_DELAY_AFTER_RELEASE
				&& !fixB.isSensor())
		{//if char tongue - close our jaws
			
			if(dynObjA.objType == Statics.DynamicGameObject.SNAKE)
				dynObjA = ((SnakePart)dynObjA).snake;			
			
			if(dynObjA.stateHS.isOpenJaws)
				dynObjA.closeJaws(fixB.getBody());
		}

	}
	
	public void contactDynEffectSensorTo(Fixture fixA, DynamicGameObject dynObjA, Fixture fixB, Contact contact) 
	{
		GameObject gObjB = (GameObject) fixB.getBody().getUserData();
		
		if(gObjB.isDynamicObject && ((DynamicGameObject)gObjB).objType == Statics.DynamicGameObject.SNAKE)
			gObjB = ((SnakePart)gObjB).snake;
		float [] fixData = ((float[])fixA.getUserData());
		
		if(fixData[PhysicsBox2d.FIXTURE_PROPERTY1_INDEX_FIXTURE_TYPE] == PhysicsBox2d.FIXTURE_PROPERTY1_SENSOR_DYN_EFFECT)
		{
			if(fixData[PhysicsBox2d.FIXTURE_PROPERTY4_INDEX_FIXTURE_INDEX] == Statics.DynamicGameObject.FSKILL_FORWARD_ATTACK_IMPULSE_EFFECT
					|| fixData[PhysicsBox2d.FIXTURE_PROPERTY4_INDEX_FIXTURE_INDEX] == Statics.DynamicGameObject.FSKILL_FORWARD_ATTACK_ICE_EFFECT)
			{						
				if (!((DynamicEffect) dynObjA).master.equals(gObjB))		
					((DynamicEffect) dynObjA).lifetimePeriod = 0;		
			}
			else if(fixData[PhysicsBox2d.FIXTURE_PROPERTY4_INDEX_FIXTURE_INDEX] == Statics.DynamicGameObject.FSKILL_FORWARD_ATTACK_HYPNOSE_EFFECT)
			{
				if(gObjB.isDynamicObject && ((DynamicGameObject)gObjB).isCharacter 
						&& !((DynamicEffect) dynObjA).master.equals(gObjB))
					worldPhys.world.wProc.processHypnoseCharacter((DynamicGameObject)gObjB, ((DynamicEffectHypnose)dynObjA));
			}
		}
	}
	
	public void contactCharObjTo(Fixture fixA, DynamicGameObject dynObjA, GameObject gObjB, Contact contact, boolean isA) 
	{
		//1. check and process bites char->char and char->dyn
		//2. check impacts and damages char->relief, char->gift
		
		float angleContact = contact.getWorldManifold().getNormal().angle() + (isA ? 0 : 180);
		
		if(gObjB.isDynamicObject)
		{
			DynamicGameObject dynObjB = (DynamicGameObject) gObjB;
			
			if(dynObjB.isCharacter)
			{//char to char
				boolean isFriend = false;
				if((dynObjA.stateHS.frendByRace == true && (dynObjB.objType == dynObjA.objType)) || 
						(dynObjA.stateHS.frendToGamer > 0 && (!dynObjB.stateHS.isBot || dynObjA.stateHS.frendToGamer == dynObjB.stateHS.frendToGamer)) || //gamer team
						(dynObjA.stateHS.frendtoAlienRace == dynObjB.objType)	//char A is friend to char B race
				)
					isFriend = true;
				
				//if charA bites charB
				if(!isFriend && ((float[])fixA.getUserData())[PhysicsBox2d.FIXTURE_PROPERTY1_INDEX_FIXTURE_TYPE] == PhysicsBox2d.FIXTURE_PROPERTY1_IS_ATTACK_ACTIVE)
				{
					if(((float[])contact.getFixtureB().getUserData())[PhysicsBox2d.FIXTURE_PROPERTY1_INDEX_FIXTURE_TYPE] == PhysicsBox2d.FIXTURE_PROPERTY1_PART_ARMOR)
						charToDynAttack(dynObjA, dynObjB, contact);
					else
						world.wProc.CharToCharAttack(dynObjA, dynObjB);		
				}
			}
			else
			{//char to dyn
				
				//if dynB is needed weapon add it to weapon queue and return
				if(dynObjB.isWeapon() && !worldPhys.hasBodyJoints(dynObjB.myBody) && dynObjA.fSkills.processNewWeaponQueue(dynObjB))
					return;	
				
				//if charA bites dynB
				if(((float[])fixA.getUserData())[PhysicsBox2d.FIXTURE_PROPERTY1_INDEX_FIXTURE_TYPE] == PhysicsBox2d.FIXTURE_PROPERTY1_IS_ATTACK_ACTIVE)
					charToDynAttack(dynObjA, dynObjB, contact);	
				
				//if dynB is a bomb
				if(dynObjB.objType == DynamicGameObject.BOMB_FROM_TREE && dynObjB.stateHS.isDead != HealthScore.DEAD_DELETED)
				{
					if(((float[])fixA.getUserData())[PhysicsBox2d.FIXTURE_PROPERTY1_INDEX_FIXTURE_TYPE] == PhysicsBox2d.FIXTURE_PROPERTY1_PART_ARMOR)
					{
						((float[])fixA.getUserData())[PhysicsBox2d.FIXTURE_PROPERTY3_INDEX_HEALTH] -=  Gifts.BOMB_FROM_TREE_DAMAGE * 
								((SnakePart)dynObjA).armors.getArmorByFixtureIndex((int) ((float[])fixA.getUserData())[PhysicsBox2d.FIXTURE_PROPERTY4_INDEX_FIXTURE_INDEX]).stateHS.defencePower;
					}
					else					
						world.wProc.DynObjImpactBasicProcess(dynObjA, WorldKingSnake.WALL_DELAY, Gifts.BOMB_FROM_TREE_DAMAGE * dynObjA.stateHS.defencePower, angleContact);
					
					dynObjB.stateHS.isDead = HealthScore.DEAD_DELETED;
					worldPhys.physicalExplosion(DynamicGameObject.TREE_BOMB_POWER, dynObjB.position.x, dynObjB.position.y);				
				}
			}
		}
		else
		{//char to static or relief		
		
			if(gObjB.type >= WorldKingSnake.WALL && gObjB.type < WorldKingSnake.REILEF_END_OF_RANGE)
			{
				if (((float[])fixA.getUserData())[PhysicsBox2d.FIXTURE_PROPERTY1_INDEX_FIXTURE_TYPE] == PhysicsBox2d.FIXTURE_PROPERTY1_PART_ARMOR)
				{
					float damage = world.wProc.getReliefImpactDamage(gObjB.type);
					
					((float[])fixA.getUserData())[PhysicsBox2d.FIXTURE_PROPERTY3_INDEX_HEALTH] -=  damage *
							((SnakePart)dynObjA).armors.getArmorByFixtureIndex((int) ((float[])fixA.getUserData())[PhysicsBox2d.FIXTURE_PROPERTY4_INDEX_FIXTURE_INDEX]).stateHS.defencePower;					
				}
				else
					world.wProc.DynObjToReliefImpactProcess(dynObjA, gObjB, angleContact);
			}
			else if(gObjB.isGift())
				world.wProc.DynObjToStatImpactProcess(dynObjA, gObjB, angleContact);			
		}
	}
	
	public void contactCharObjToSensor(Fixture fixA, DynamicGameObject dynObjA, GameObject gObjB, Fixture fixB, Contact contact) 
	{
		//1. check and process char->relief sensor (water, fire)
		
		if(gObjB.isDynamicObject)
		{
			DynamicGameObject dynObjB = (DynamicGameObject) gObjB;
			
			if(dynObjB.isCharacter)
			{//char to char

			}
			else
			{//char to dyn
				
			}
		}
		else
		{//char to static or relief		
		
			if(gObjB.type == WorldKingSnake.WATER)
			{
				if (((float[])fixA.getUserData())[PhysicsBox2d.FIXTURE_PROPERTY1_INDEX_FIXTURE_TYPE] == PhysicsBox2d.FIXTURE_PROPERTY1_IS_ATTACK_ACTIVE
						||((float[])fixA.getUserData())[PhysicsBox2d.FIXTURE_PROPERTY1_INDEX_FIXTURE_TYPE] == PhysicsBox2d.FIXTURE_PROPERTY1_IS_ATTACK_PASSIVE)
				{
				//contact only with char body
					
					StaticEffect water = (StaticEffect) gObjB;
					
					if(!worldPhys.isCharInWater(fixA, fixB));						
						worldPhys.addCharToWater(fixA, fixB);
					
					if(!water.isPermanentEffectInActiveMode())
					{
						ParticlesEffectsManager.provideWaterBulbEffect(water.position.x, water.position.y);
						water.setPermanentEffectToActiveMode(2);
					}
					
					if(dynObjA.isFlaming)
						WorldProcessingImpl.processEventFlamingObjectToWaterReaction(dynObjA, dynObjA.flamePower);
				}
			}			
		}
	}

		
	public void charToDynAttack(DynamicGameObject dynObjA, DynamicGameObject dynObjB, Contact contact) 
	{
		if(!dynObjB.isImmortal)
		{
			Fixture fixB = contact.getFixtureB();
			
			if(fixB.getUserData() != null && !dynObjB.isImmortal)
			{
				float[] fixProps = (float[]) fixB.getUserData();
				worldPhys.initMaterialProperties(fixB);
				float attackPower = dynObjA.stateHS.isStriking ? dynObjA.stateHS.attackPower * dynObjA.stateHS.strikePower :
									dynObjA.stateHS.attackPower;
				float biteDamage = attackPower * attackPower / (2 * PhysicsBox2d.FIXTURE_MATERIAL_BITE_BASE_DEFFENSE * worldPhys.materialBiteResist /
								PhysicsBox2d.FIXTURE_MATERIAL_MEAT_BITE_RESISTANCE);
				fixProps[PhysicsBox2d.FIXTURE_PROPERTY3_INDEX_HEALTH] -= biteDamage;
				
				float angle = contact.getWorldManifold().getNormal().rotate90(1).angle();
				Vector2 pos = contact.getWorldManifold().getPoints()[0];
				ParticlesEffectsManager.provideMaterialBiteDamageEffect((int) fixProps[PhysicsBox2d.FIXTURE_PROPERTY2_INDEX_MATERIAL], angle, biteDamage, pos.x, pos.y);
			}
		}
	}
	
	public void contDynObjTo(DynamicGameObject dynObjA, GameObject gObjB, Contact contact) {
		
	}

	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub				
		Fixture fixA = contact.getFixtureA();
		Fixture fixB = contact.getFixtureB();		
		GameObject gObjA = (GameObject)fixA.getBody().getUserData();
		GameObject gObjB = (GameObject)fixB.getBody().getUserData();	
						
		if(gObjA != null)
		{
			if(gObjA.isDynamicObject && ((DynamicGameObject)gObjA).isCharacter)
			{				
				if(fixA.isSensor())
					endContactCharObjSensorTo(fixA, (DynamicGameObject)gObjA, fixB, contact );				
			}
			else if(gObjA.isDynamicObject)
			{
				
			}
		}

		if(gObjB != null)
		{
			if(gObjB.isDynamicObject && ((DynamicGameObject)gObjB).isCharacter)
			{				
				if(fixB.isSensor())
					endContactCharObjSensorTo(fixB, (DynamicGameObject)gObjB, fixA, contact);				
			}
			else if(gObjB.isDynamicObject)
			{
				
			}
		}

	}
	
	public void endContactCharObjSensorTo(Fixture fixA, DynamicGameObject dynObjA, Fixture fixB, Contact contact) 
	{
		GameObject gObjB = (GameObject) fixB.getBody().getUserData();
		float [] fixAdata = ((float[])fixA.getUserData());
		float [] fixBdata = ((float[])fixB.getUserData());
		
		if(((float[])fixA.getUserData())[PhysicsBox2d.FIXTURE_PROPERTY1_INDEX_FIXTURE_TYPE] == PhysicsBox2d.FIXTURE_PROPERTY1_SENSOR_FOR_CORRECT_MOVING_FORCE)
		{//correct character moving by correcting forces to escape impacts with relief etc..
					
			if(worldPhys.charMoveCorrectingForces.contains(fixB))
			{			
				if(fixAdata[PhysicsBox2d.FIXTURE_PROPERTY4_INDEX_FIXTURE_INDEX] == PhysicsBox2d.FIXTURE_PROPERTY4_SENSOR_CORRECT_MOVING_FORCE_UP)
					dynObjA.removeCorrectForce(Statics.PhysicsBox2D.CORRECT_POWER_DOWN);
				else if(fixAdata[PhysicsBox2d.FIXTURE_PROPERTY4_INDEX_FIXTURE_INDEX] == PhysicsBox2d.FIXTURE_PROPERTY4_SENSOR_CORRECT_MOVING_FORCE_DOWN)
					dynObjA.removeCorrectForce(Statics.PhysicsBox2D.CORRECT_POWER_UP);
				
				worldPhys.charMoveCorrectingForces.remove(fixB);			
			}				
		}
		else if(fixAdata[PhysicsBox2d.FIXTURE_PROPERTY1_INDEX_FIXTURE_TYPE] == PhysicsBox2d.FIXTURE_PROPERTY1_SENSOR_FOR_FIGHT_SKILL
				&& fixBdata != null && (fixBdata[PhysicsBox2d.FIXTURE_PROPERTY1_INDEX_FIXTURE_TYPE] == PhysicsBox2d.FIXTURE_PROPERTY1_IS_ATTACK_ACTIVE ||
				fixBdata[PhysicsBox2d.FIXTURE_PROPERTY1_INDEX_FIXTURE_TYPE] == PhysicsBox2d.FIXTURE_PROPERTY1_IS_ATTACK_PASSIVE)
				&& gObjB != null && gObjB.isDynamicObject)
		{//char fight skill sensor: delete target
			
			boolean enemyDetected = false;
			
			if(gObjB.isDynamicObject)
			{
				DynamicGameObject dynObjB = (DynamicGameObject) gObjB;
				
				if(dynObjB.isCharacter)
				{//char sensor to char
					
					if(!world.wProc.isCharFriend(dynObjA, dynObjB))
						enemyDetected = true;
				}
				else
				{//char sensor to dyn
					
				}
			}
			else
			{//char sensor to relief or static		
							
			}
			
			if(enemyDetected)
			{			
				//if snake, add target to general fSkills, but not to part's fSkills
				if(dynObjA.objType == Statics.DynamicGameObject.SNAKE)
					dynObjA = ((SnakePart)dynObjA).snake;
	
				if(fixAdata[PhysicsBox2d.FIXTURE_PROPERTY4_INDEX_FIXTURE_INDEX] == PhysicsBox2d.FIXTURE_PROPERTY4_SENSOR_FIGHT_SKILL_FORWARD)
					dynObjA.fSkills.removeTarget(Statics.FightingSkills.SENSOR_FORWARD, gObjB);
				else if(fixAdata[PhysicsBox2d.FIXTURE_PROPERTY4_INDEX_FIXTURE_INDEX] == PhysicsBox2d.FIXTURE_PROPERTY4_SENSOR_FIGHT_SKILL_LEFT)
					dynObjA.fSkills.removeTarget(Statics.FightingSkills.SENSOR_LEFT_SIDE, gObjB);
				else if(fixAdata[PhysicsBox2d.FIXTURE_PROPERTY4_INDEX_FIXTURE_INDEX] == PhysicsBox2d.FIXTURE_PROPERTY4_SENSOR_FIGHT_SKILL_RIGHT)
					dynObjA.fSkills.removeTarget(Statics.FightingSkills.SENSOR_RIGHT_SIDE, gObjB);
			}
		}
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		
		Fixture fixA = contact.getFixtureA();
		Fixture fixB = contact.getFixtureB();
		
		if(fixA.getBody().getUserData() == null || fixB.getBody().getUserData() == null)
			return;
		
		processPreSolve(fixA, fixB, contact, oldManifold);
		processPreSolve(fixB, fixA, contact, oldManifold);	
		processPenetrations(fixB, fixA, contact, oldManifold);
	}
	
	public void processPreSolve(Fixture fixA, Fixture fixB, Contact contact, Manifold oldManifold) 
	{					
		if(((GameObject)fixA.getBody().getUserData()).isDynamicObject && ((GameObject)fixB.getBody().getUserData()).isDynamicObject)
			processPreSolveDynToDyn(fixA, fixB, contact);
		
		if(((GameObject)fixA.getBody().getUserData()).isDynamicObject)
			processPreSolveDynTo(fixA, fixB, contact);
	}
	
	public void processPreSolveDynToDyn(Fixture fixA, Fixture fixB, Contact contact) 
	{
		DynamicGameObject dynObjA = (DynamicGameObject)fixA.getBody().getUserData();
		DynamicGameObject dynObjB = (DynamicGameObject)fixB.getBody().getUserData();
		
		if(dynObjA.isCharacter &&  dynObjB.isCharacter)
			processPreSolveCharToChar(dynObjA, dynObjB, fixA, contact);
		else if(dynObjA.isCharacter)
			processPreSolveCharTo(dynObjA, dynObjB, fixA, contact);
		else 
		{				
		}			
	}
	
	public void processPreSolveCharToChar(DynamicGameObject dynObjA, DynamicGameObject dynObjB, Fixture fixA, Contact contact) 
	{
		boolean isFriend = false;
		if((dynObjA.stateHS.frendByRace == true && (dynObjB.objType == dynObjA.objType)) || 
				(dynObjA.stateHS.frendToGamer > 0 && (!dynObjB.stateHS.isBot || dynObjA.stateHS.frendToGamer == dynObjB.stateHS.frendToGamer)) || //gamer team
				(dynObjA.stateHS.frendtoAlienRace == dynObjB.objType)	//char A is friend to char B race
		)
			isFriend = true;
		
		//if objA are going to bite and hold we don't need impact, so disable contact
		if(!isFriend && ((float[])fixA.getUserData())[PhysicsBox2d.FIXTURE_PROPERTY1_INDEX_FIXTURE_TYPE] == PhysicsBox2d.FIXTURE_PROPERTY1_IS_ATTACK_ACTIVE
				&& dynObjA.biteAndHoldMode)
			contact.setEnabled(false);			
	}
	
	public void processPreSolveCharTo(DynamicGameObject characterA, DynamicGameObject dynObjB, Fixture fixA, Contact contact) 
	{
		//if char objA are going to bite and hold we don't need impact, so disable contact
		if(characterA.biteAndHoldMode && ((float[])fixA.getUserData())[PhysicsBox2d.FIXTURE_PROPERTY1_INDEX_FIXTURE_TYPE] == PhysicsBox2d.FIXTURE_PROPERTY1_IS_ATTACK_ACTIVE)
			contact.setEnabled(false);			
	}
	
	public void processPreSolveDynTo(Fixture fixA, Fixture fixB, Contact contact) 
	{
		DynamicGameObject dynObjA = (DynamicGameObject)fixA.getBody().getUserData();
		GameObject gObjB = (GameObject)fixB.getBody().getUserData();

		if(dynObjA.objType == Statics.DynamicGameObject.FSKILL_FORWARD_ATTACK_FIRE_EFFECT && PhysicsBox2d.isInflammableMaterial((int) ((float[])fixB.getUserData())[PhysicsBox2d.FIXTURE_PROPERTY2_INDEX_MATERIAL]))
		{
			contact.setEnabled(false);
			if(!gObjB.isFlaming)
				worldPhys.world.wProc.processObjectFlameReaction(gObjB, (int)((DynamicEffect) dynObjA).getPower(), fixB);
		}		
	}
	


	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		
		//process damages from impulse for all type bodies
		Fixture fixA = contact.getFixtureA();
		Fixture fixB = contact.getFixtureB();
		
		int impCount = impulse.getCount();
		float tangImpulsesSum = 0;
		float normImpulsesSum = 0;
		float[] tangImpulses = impulse.getTangentImpulses();
		float[] normImpulses = impulse.getTangentImpulses();
		
		//get absolute impulse
		for(int i = 0; i < impCount; ++i)
		{
			tangImpulsesSum += Math.abs(tangImpulses[i]);
			normImpulsesSum += Math.abs(normImpulses[i]);
		}		
		float sumImpulses = tangImpulsesSum + normImpulsesSum;
		
		if(sumImpulses < Statics.PhysicsBox2D.IMPULSE_MIN_ACCOUNTED)
			return;
		
		processPostSolve(fixA, fixB, sumImpulses,tangImpulsesSum, contact);	
		processPostSolve(fixB, fixA, sumImpulses,tangImpulsesSum, contact);	
	}
	
	public void processPostSolve(Fixture fixA, Fixture fixB, float impulse, float tangentImpulse, Contact contact) 
	{
		GameObject gObjA = (GameObject)fixA.getBody().getUserData();
		GameObject gObjB = (GameObject)fixB.getBody().getUserData();
		
		if(gObjA == null || gObjB == null)
			return;
		
		if(gObjA.isDynamicObject)
			if(((DynamicGameObject)gObjA).isWeapon())
				worldPhys.stopWeaponFlyMode(gObjA.myBody);
		
		int materialA = (int) ((float[])fixA.getUserData())[PhysicsBox2d.FIXTURE_PROPERTY2_INDEX_MATERIAL];
		worldPhys.initMaterialProperties(fixA);
		float impulseResistA = worldPhys.materialImpulseResist;

		int materialB = (int) ((float[])fixB.getUserData())[PhysicsBox2d.FIXTURE_PROPERTY2_INDEX_MATERIAL];	
		worldPhys.initMaterialProperties(fixB);
		float impulseResistB = worldPhys.materialImpulseResist;	
		
		float angle = contact.getWorldManifold().getNormal().rotate90(tangentImpulse > 0 ? 1 : -1).angle();
		Vector2 pos = contact.getWorldManifold().getPoints()[0];

		//if impulse value more material impulse resistance threshold then get damage
		if(!gObjA.isImmortal && impulseResistA < impulse * impulseResistB / impulseResistA)
		{
			float impDamage = Statics.PhysicsBox2D.IMPULSE_DAMGAGE_KOEF * impulse * impulseResistB / impulseResistA;
			((float[])fixA.getUserData())[PhysicsBox2d.FIXTURE_PROPERTY3_INDEX_HEALTH] -= impDamage;
			ParticlesEffectsManager.provideMaterialImpactDamageEffect(materialA, angle, impDamage, pos.x, pos.y);
			
		}
		else if(worldPhys.isMetal(materialA) && worldPhys.isSparkingMaterial(materialB))
		{			
			float impDamage = Statics.PhysicsBox2D.IMPULSE_DAMGAGE_KOEF * impulse * impulseResistB / impulseResistA;
			ParticlesEffectsManager.provideMaterialImpactDamageEffect(materialA, angle, impDamage, pos.x, pos.y);			
		}
	}
	
	public Vector2 getImpulseFromPoint(Fixture fix, Contact contact) {
		
		Vector2 impulse = new Vector2 (fix.getBody().getLinearVelocityFromWorldPoint(contact.getWorldManifold().getPoints()[PhysicsBox2d.FIXTURE_PROPERTY1_INDEX_FIXTURE_TYPE]));
		impulse.scl(fix.getBody().getMass());
		
		//get direction vector from mass center to point
		Vector2 pointToMassCenterDir = new Vector2 (contact.getWorldManifold().getPoints()[PhysicsBox2d.FIXTURE_PROPERTY1_INDEX_FIXTURE_TYPE]);
		pointToMassCenterDir.sub(fix.getBody().getWorldCenter());
		pointToMassCenterDir.nor();
		
		//project impulse vector to direction vector coordinates
		Vector2 impulseAnor = new Vector2(impulse).nor();
		float normal = Math.abs(impulseAnor.dot(pointToMassCenterDir));
		pointToMassCenterDir.rotate90(1);
		//tangent divide on 8, mean koef between [ E = m * v^2 / 2 ] and [ E = I * w^2 / 2] [ I = m * l^2 / 12 ]
		float tangent = Math.abs(impulseAnor.dot(pointToMassCenterDir)) / 8;
		float projectionResultKoef = (float) Math.sqrt(normal * normal + tangent * tangent);
		impulse.scl(projectionResultKoef);	
		return impulse;		
	}
	
	void processPenetrations(Fixture fixB, Fixture fixA, Contact contact, Manifold oldManifold)
	{
		if(((GameObject)fixA.getBody().getUserData()).isDynamicObject)				
			defineAndAddNewPentrationToQueue(fixA, fixB, contact, oldManifold);		
		if(((GameObject)fixB.getBody().getUserData()).isDynamicObject)				
			defineAndAddNewPentrationToQueue(fixB, fixA, contact, oldManifold);		
	}
	
	public void defineAndAddNewPentrationToQueue(Fixture fixA, Fixture fixB, Contact contact, Manifold oldManifold) 
	{
		//check and process penetration by hard sharp object. Algorithm:
		//If objA creates impulse pressure in D point more than hardness of objB than we turn off contact and
		//create prismatic joint C with friction according to hardness of objB. ObjB continue to move inside 
		//objB by inertia overcoming friction of joint C.
		//							-------------
		//							|		C \\|
		//							|		   \|D
		//							|			|\		
		//							|			| \	   
		//							|	B		|  \ A
		//							|			|	\
		//							|			|	 \
		//							-------------
		
		/*Penetration physics goals:
		1.Avoid penetrations in close fight:
			- so straight depends on Impulse, contact width;
			- depends on square root of weapon / target materials;
			- mean strike impulse in close fight < 5, but with attack jumps it could be > 7
			- mean strike impulse of throw weapon 5,7,9,11,13,15
			- first point koef. calculated as: 5(MIN_PENETRATION_IMPULSE) / sqrt(0.5(MEAT_HARD) / 9(BRONZE_HARD)) = 21
			- two point koef. calculated as: 5(MIN_PENETRATION_IMPULSE) / (0.05(SHARP_WIDTH) * sqrt(0.5(MEAT_HARD) / 9(BRONZE_HARD))) = 425	
			- if no jumps and bronze weapon we avoid penetrations, otherwise not;		
		2.Provide double penetration Spear -> Target1 -> Target2 */
		
		int materialA = (int) ((float[])fixA.getUserData())[PhysicsBox2d.FIXTURE_PROPERTY2_INDEX_MATERIAL];
		int materialB = (int) ((float[])fixB.getUserData())[PhysicsBox2d.FIXTURE_PROPERTY2_INDEX_MATERIAL];
		
		if(getPenetrationMaterialsResult(materialA , materialB))
		{			
			Vector2 impulseA = getImpulseFromPoint(fixA, contact);
			Vector2 impulseB = getImpulseFromPoint(fixB, contact);
			impulseB.sub(impulseA);	
			
			if(impulseB.len() < Statics.PhysicsBox2D.PENETRATION_MIN_IMPULSE_FILTER)
				return;
						
			//if(((DynamicGameObject)fixB.getBody().getUserData()).objType == Statics.DynamicGameObject.SNAKE)
			//	a++;
			
			//if(((DynamicGameObject)fixB.getBody().getUserData()).objType == 100)
			//	a++;
			
			worldPhys.initMaterialProperties(fixA);
			float materialHardnessA = worldPhys.materialHardnness;
			float materialImpulseResistA = worldPhys.materialImpulseResist;
			
			worldPhys.initMaterialProperties(fixB);
			float materialHardnessB = worldPhys.materialHardnness;
			float materialImpulseResistB = worldPhys.materialImpulseResist;
			
			int nPoints = contact.getWorldManifold().getNumberOfContactPoints();
							
			if (nPoints == 1)
			{
				float impulseNormal = Math.abs(impulseB.dot(contact.getWorldManifold().getNormal()));
				//PENETRATION_FIRST_POINT_KOEF: meat could be penetrated by bronze if impulse > 5:
				// sqrt(0.5 / 9) * 21 = 5; for wood: sqrt(3 / 9) * 21 = 12
				//steel / wood: sqrt(3 / 27) * 21 = 7
				float resistance = (float) (Statics.PhysicsBox2D.PENETRATION_FIRST_POINT_KOEF * Math.sqrt(materialHardnessB / materialHardnessA));
				
				if(impulseNormal > resistance)
				{
					contact.setEnabled(false);
					
					/*DEBUG BLOCK
					if(((DynamicGameObject)fixB.getBody().getUserData()).objType == Statics.DynamicGameObject.SNAKE)
						a++;
					
					if(((DynamicGameObject)fixB.getBody().getUserData()).objType == 100)
						a++;
					
					if(((DynamicGameObject)fixA.getBody().getUserData()).objType == Statics.DynamicGameObject.SWORD_VIKING_BRONZE_WOODEN)
						a++;
					
					if(((DynamicGameObject)fixA.getBody().getUserData()).objType == Statics.DynamicGameObject.SPEAR_VIKING_BRONZE_WOODEN)
						a++;*/
				}
			}
			else if(nPoints > 1)
			{
				//don't use projection to normal here as sometimes normal vector isn't correct
				float impactImpulse = impulseB.len();				
				Vector2 contactSection = new Vector2(contact.getWorldManifold().getPoints()[Statics.INDEX_0]).sub(contact.getWorldManifold().getPoints()[Statics.INDEX_1]);
				Vector2 tangent = new Vector2(impulseA).rotate90(1).nor();
				float contactLength = Math.abs(contactSection.dot(tangent)); 
				contactLength = contactLength < Statics.PhysicsBox2D.PENETRATION_MIN_CONTACT_WIDTH ? Statics.PhysicsBox2D.PENETRATION_MIN_CONTACT_WIDTH : contactLength;

				//PENETRATION_TWO_POINT_KOEF: meat could be penetrated by bronze if impulse > 5 and contLength < 0.05:
				// sqrt(0.5 / 9) * 425 * 0.05 = 5; for wood: sqrt(3 / 9) * 425 * 0.05 = 12
				// steel/meat: sqrt(0.5 / 27) * 425 * 0.05 = 2.9; 	// steel/wood: sqrt(3 / 27) * 425 * 0.05 = 7;
				float resistance = (float) (Statics.PhysicsBox2D.PENETRATION_TWO_POINTS_KOEF * contactLength * Math.sqrt(materialHardnessB / materialHardnessA));
						
				if(impactImpulse > resistance)
				{
					/*DEBUG BLOCK
					if(((DynamicGameObject)fixB.getBody().getUserData()).objType == Statics.DynamicGameObject.SNAKE)
						a++;
					
					if(((DynamicGameObject)fixB.getBody().getUserData()).objType == 100)
						a++;
					
					if(((DynamicGameObject)fixA.getBody().getUserData()).objType == Statics.DynamicGameObject.SWORD_VIKING_BRONZE_WOODEN)
						a++;
					
					if(((DynamicGameObject)fixA.getBody().getUserData()).objType == Statics.DynamicGameObject.SPEAR_VIKING_BRONZE_WOODEN)
						a++;*/
					
					//we've got PENETRATION!!	
					Vector2 anchor = new Vector2(contact.getWorldManifold().getPoints()[Statics.INDEX_0]);
					anchor.lerp(contact.getWorldManifold().getPoints()[Statics.INDEX_1], 0.5f);
				
					PrismaticJointDef jointDef = new PrismaticJointDef(); 
					jointDef.initialize(fixA.getBody(), fixB.getBody(), anchor, impulseA); 
					jointDef.enableMotor = true;
					jointDef.motorSpeed = 0;
					
					//simulate resistance of material to penetration and then friction of penetration	
					//PENETRATION_FRICTION_KOEF: meat:(0.5 / 9) * 54 = 3; wood: (3 / 9) * 54 = 18; steel / wood: (3 / 27) * 54 = 6;
					jointDef.maxMotorForce = Statics.PhysicsBox2D.PENETRATION_FRICTION_KOEF * materialHardnessB / materialHardnessA;
					
					if(!worldPhys.isPenetrationExist(jointDef.bodyA, jointDef.bodyB))
						worldPhys.penetrationNewJointsQueue.add(jointDef);
					
					//get damage from penetration (based on impulse)
					((float[])fixB.getUserData())[PhysicsBox2d.FIXTURE_PROPERTY3_INDEX_HEALTH] -= Statics.PhysicsBox2D.PENTETRATION_IMPULSE_DAMGAGE_KOEF 
							* impactImpulse * materialImpulseResistA / materialImpulseResistB;						
				}
			}
		}
	}
	
	boolean getPenetrationMaterialsResult(int materialA /*penetrator*/, int materialB /*target*/)
	{
		if((materialA == PhysicsBox2d.FIXTURE_PROPERTY2_MATERIAL_BRONZE && materialB == PhysicsBox2d.FIXTURE_PROPERTY2_MATERIAL_MEAT)
				|| (materialB == PhysicsBox2d.FIXTURE_PROPERTY2_MATERIAL_BRONZE && materialA == PhysicsBox2d.FIXTURE_PROPERTY2_MATERIAL_MEAT))
			materialA = materialA + 0;
		
		if(materialA == PhysicsBox2d.FIXTURE_PROPERTY2_MATERIAL_BRONZE && (materialB == PhysicsBox2d.FIXTURE_PROPERTY2_MATERIAL_MEAT || materialB == PhysicsBox2d.FIXTURE_PROPERTY2_MATERIAL_WOOD)
			|| materialA == PhysicsBox2d.FIXTURE_PROPERTY2_MATERIAL_STEEL && (materialB == PhysicsBox2d.FIXTURE_PROPERTY2_MATERIAL_MEAT 
																				|| materialB == PhysicsBox2d.FIXTURE_PROPERTY2_MATERIAL_WOOD
																				|| materialB == PhysicsBox2d.FIXTURE_PROPERTY2_MATERIAL_BRONZE
																				|| materialB == PhysicsBox2d.FIXTURE_PROPERTY2_MATERIAL_ROCK)
			|| materialA == PhysicsBox2d.FIXTURE_PROPERTY2_MATERIAL_ADAMANT && (materialB == PhysicsBox2d.FIXTURE_PROPERTY2_MATERIAL_MEAT 
																				|| materialB == PhysicsBox2d.FIXTURE_PROPERTY2_MATERIAL_WOOD
																				|| materialB == PhysicsBox2d.FIXTURE_PROPERTY2_MATERIAL_BRONZE
																				|| materialB == PhysicsBox2d.FIXTURE_PROPERTY2_MATERIAL_STEEL
																				|| materialB == PhysicsBox2d.FIXTURE_PROPERTY2_MATERIAL_ROCK)
		)
			return true;
		else
			return false;
	}
}
