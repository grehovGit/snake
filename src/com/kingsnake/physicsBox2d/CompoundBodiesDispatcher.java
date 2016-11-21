package com.kingsnake.physicsBox2d;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.example.framework.model.Armor;
import com.example.framework.model.DynamicGameObject;
import com.example.framework.model.Statics;
import com.example.framework.model.Statics.BodyData;
import com.kingsnake.math.MyMath;

public class CompoundBodiesDispatcher {
	
	static final float BORDER_WIDTH = Statics.PhysicsBox2D.BORDER_WIDTH;
	
	PhysicsBox2d worldPhys;
	Vector2 vector, vector2, vector3, vector4;
	
	CompoundBodiesDispatcher(PhysicsBox2d worldPhys)
	{
		this.worldPhys = worldPhys;
		vector = new Vector2();
		vector2 = new Vector2();
		vector3 = new Vector2();
		vector4 = new Vector2();
	}
	
	void createCompoundBody(DynamicGameObject dynObj, Body body, DynamicGameObject master)
	{
		//try
		//{				
			if(dynObj.objType >= Statics.DynamicGameObject.BEGIN_OF_BLOCKS_RANGE && dynObj.objType <= Statics.DynamicGameObject.END_OF_BLOCKS_RANGE)
				createBlock(dynObj, body);
			else if(dynObj.isWeapon())
				createWeapon(dynObj, body, master);
			else if(dynObj.isBreakable())
				createBreakableObject(dynObj, body, master);
		//}
		//catch(Exception e)
		//{
		//	Log.d("Exception in 'CreatingCompoundBody'", e.getMessage());
		//}
			
	}
	
	private void createBlock(DynamicGameObject dynObj, Body bodyDyn)
	{
        PolygonShape rect = new PolygonShape();
        float bodyWidth = dynObj.bounds.width / 2 -  dynObj.bounds.width * BORDER_WIDTH / 2;
        float bodyHeight = dynObj.bounds.height /2 - dynObj.bounds.height * BORDER_WIDTH / 2;       
        short material = DynamicGameObject.BLOCK_WOOD;
        FixtureDef fixtureDynDef = new FixtureDef();

       
        if(dynObj.objType == DynamicGameObject.BLOCK_WOOD)
        	material = PhysicsBox2d.FIXTURE_PROPERTY2_MATERIAL_WOOD;
        else if(dynObj.objType == DynamicGameObject.BLOCK_MARBLE)
        	material = PhysicsBox2d.FIXTURE_PROPERTY2_MATERIAL_ROCK;
        
		worldPhys.initMaterialProperties(material, dynObj.stateHS.level);
		dynObj.stateHS.health = dynObj.stateHS.defencePower = worldPhys.materialHealth;
        
        //begin from left-up and counter clock-wise
    	vector.set(-bodyWidth / 2, bodyHeight / 2);
        rect.setAsBox(bodyWidth/2, bodyHeight/2, vector, 0);
        fixtureDynDef.shape = rect;
        fixtureDynDef.density = worldPhys.materialDensity;
    	bodyDyn.createFixture(fixtureDynDef).setUserData(worldPhys.setFixUserData(PhysicsBox2d.FIXTURE_PROPERTY1_IS_ATTACK_PASSIVE, 
    			material, 0, 0));
    	
    	vector.set(-bodyWidth / 2, -bodyHeight / 2);
        rect.setAsBox(bodyWidth/2, bodyHeight/2, vector, 0);
        fixtureDynDef.shape = rect;
    	bodyDyn.createFixture(fixtureDynDef).setUserData(worldPhys.setFixUserData(PhysicsBox2d.FIXTURE_PROPERTY1_IS_ATTACK_PASSIVE, 
    			material, 0, 1));
    	
    	vector.set(bodyWidth / 2, -bodyHeight / 2);
        rect.setAsBox(bodyWidth/2, bodyHeight/2, vector, 0);
        fixtureDynDef.shape = rect;
    	bodyDyn.createFixture(fixtureDynDef).setUserData(worldPhys.setFixUserData(PhysicsBox2d.FIXTURE_PROPERTY1_IS_ATTACK_PASSIVE, 
    			material, 0, 2));
    	
    	vector.set(bodyWidth / 2, bodyHeight / 2);
        rect.setAsBox(bodyWidth/2, bodyHeight/2, vector, 0);
        fixtureDynDef.shape = rect;
    	bodyDyn.createFixture(fixtureDynDef).setUserData(worldPhys.setFixUserData(PhysicsBox2d.FIXTURE_PROPERTY1_IS_ATTACK_PASSIVE, 
    			material, 0, 3));
		
	}
	
	private void createWeapon(DynamicGameObject dynObj, Body body, DynamicGameObject master)
	{
		int weaponClass = Statics.DynamicGameObject.getWeaponClass(dynObj.objType);
		
		if(weaponClass == Statics.DynamicGameObject.ABSTRACT_TYPE_WEAPON_ARMOR)
			 createArmor((Armor) dynObj, body, master);	
		else if(weaponClass == Statics.DynamicGameObject.ABSTRACT_TYPE_WEAPON_HELM
				|| weaponClass == Statics.DynamicGameObject.ABSTRACT_TYPE_WEAPON_SHIELD
				|| weaponClass == Statics.DynamicGameObject.ABSTRACT_TYPE_WEAPON_SWORD
				|| weaponClass == Statics.DynamicGameObject.ABSTRACT_TYPE_WEAPON_AXE
				|| weaponClass == Statics.DynamicGameObject.ABSTRACT_TYPE_WEAPON_SPEAR)
			createMechanicWeapon(dynObj, body);
	}
	
	private void createBreakableObject(DynamicGameObject dynObj, Body body, DynamicGameObject master)
	{	
		if(dynObj.isBreakableCircle())
			createBreakableCircle(dynObj, body);
		else
			createCompoundBody(dynObj, body);
	}
	
	private void createArmor(Armor armor, Body body, DynamicGameObject master)
	{
		int partsCount = Statics.PhysicsBox2D.getObjPartsFixturesCount(armor.type);
		PolygonShape armorVerts = new PolygonShape(); 
		int material = Statics.PhysicsBox2D.getObjMaterial(armor.type);
		
		worldPhys.initMaterialProperties(material, armor.stateHS.level);
		armor.stateHS.health = armor.stateHS.defencePower = worldPhys.materialHealth;
		
		for(int i = 0; i < partsCount; ++i)
		{
			float[] temp = Statics.PhysicsBox2D.getObjFixtureVerts(armor.type, i);
			float[] vertices;
		
			//if left - flip fixture vertical
			if(armor.isLeft)
			{
				vertices = new float[temp.length];
				
				for(int j = 0; j < vertices.length; ++j)
					vertices[j] = j % 2 == 0 ? temp[j] : temp[j] * -1;
			}
			else
				vertices = temp;
			
			armorVerts.set(vertices);				
			FixtureDef fixArmorDef = new FixtureDef();
			fixArmorDef.filter.groupIndex = master.fixtureGroupFilterId;
			fixArmorDef.shape = armorVerts;		
			fixArmorDef.density = armor.stateHS.density;
			
			body.createFixture(fixArmorDef).setUserData(worldPhys.setFixUserData(PhysicsBox2d.FIXTURE_PROPERTY1_PART_ARMOR, 
	        		(short) material, 0, 
	        		armor.isLeft ? Statics.PhysicsBox2D.SNAKE_WOOD_ARMOR_LEFT_FIXTURE_START_INDEX + i: Statics.PhysicsBox2D.SNAKE_WOOD_ARMOR_RIGHT_FIXTURE_START_INDEX + i));
		}            	
	}
	
	
	private void createMechanicWeapon(DynamicGameObject weapon, Body body)
	{
		int partsCount = Statics.PhysicsBox2D.getObjPartsFixturesCount(weapon.objType);
		int[] materials = Statics.PhysicsBox2D.getObjMaterials(weapon.objType);
		PolygonShape weaponVerts = new PolygonShape();  
		weapon.weaponPenetrationWidth = Statics.PhysicsBox2D.getWeaponStrikeSharpness(weapon.objType);
		
		if (weapon.isSeparetable())
		{		
			for(int i = 0; i < partsCount; ++i)
			{
				float[] vertices = Statics.PhysicsBox2D.getObjFixtureVerts(weapon.objType, i);
	
				weaponVerts.set(vertices);				
				FixtureDef fixWeaponDef = new FixtureDef();
				fixWeaponDef.shape = weaponVerts;		
				worldPhys.initMaterialProperties(materials[i], weapon.stateHS.level);
				fixWeaponDef.density = worldPhys.materialDensity;
				
				body.createFixture(fixWeaponDef).setUserData(worldPhys.setFixUserData(PhysicsBox2d.FIXTURE_PROPERTY1_IS_ATTACK_PASSIVE, 
		        		(short)materials[i],  worldPhys.materialHealth, i));
			} 
		}
		else if (weapon.isBreakable())
		{
			float averageHealth = 0;
			
			for(int i = 0; i < partsCount; ++i)
			{
				float[] vertices = Statics.PhysicsBox2D.getObjFixtureVerts(weapon.objType, i);
	
				weaponVerts.set(vertices);				
				FixtureDef fixWeaponDef = new FixtureDef();
				fixWeaponDef.shape = weaponVerts;	
				worldPhys.initMaterialProperties(materials[i], weapon.stateHS.level);
				fixWeaponDef.density = worldPhys.materialDensity;
				averageHealth += worldPhys.materialHealth;
				
				body.createFixture(fixWeaponDef).setUserData(worldPhys.setFixUserData(PhysicsBox2d.FIXTURE_PROPERTY1_IS_ATTACK_PASSIVE, 
		        		(short)materials[i],  0, i));
			} 
			
			averageHealth /= partsCount;
			weapon.stateHS.health = weapon.stateHS.defencePower = averageHealth;
		}
	}
	
	private void createBreakableCircle(DynamicGameObject circleObj, Body body)
	{
		BodyData bData = Statics.DynamicGameObject.getBodyData(circleObj.objType);			
        CircleShape circelShape = new CircleShape();
        circelShape.setRadius(circleObj.bounds.width / 2);
	    FixtureDef fixtureDef = new FixtureDef();
	    fixtureDef.shape = circelShape;	 	    
		worldPhys.initMaterialProperties(bData.getMaterials()[0], circleObj.stateHS.level);
		fixtureDef.density = worldPhys.materialDensity;		
	    Fixture fixture = body.createFixture(fixtureDef);		    
	    fixture.setUserData(worldPhys.setFixUserData(PhysicsBox2d.FIXTURE_PROPERTY1_IS_ATTACK_PASSIVE, (short) bData.getMaterials()[0], 0, 0));	              
		circleObj.stateHS.health = circleObj.stateHS.defencePower = worldPhys.materialHealth;
        circelShape.dispose();
	}
	
	boolean isCompoundBodyComplete(DynamicGameObject dynObj)
	{
		if(dynObj.objType >= Statics.DynamicGameObject.BEGIN_OF_WEAPON_RANGE && dynObj.objType <= Statics.DynamicGameObject.END_OF_WEAPON_RANGE)
			return isWeaponComplete(dynObj);
		else
			return true;
		
	}
	
	boolean isWeaponComplete(DynamicGameObject weapon)
	{
		int completePartsCount = Statics.PhysicsBox2D.getObjPartsFixturesCount(weapon.objType);
		int currentPartsCount = weapon.myBody.getFixtureList().size;
		
		if(completePartsCount > currentPartsCount)
			return false;
		else
			return true;
	}
	
	private void createCompoundBody(DynamicGameObject compoundObject, Body body)
	{
		BodyData bData = Statics.DynamicGameObject.getBodyData(compoundObject.objType);	
		float[][] verts = bData.getVerts();
		int[] materials = bData.getMaterials();
		PolygonShape weaponVerts = new PolygonShape();  
		
		if (compoundObject.isSeparetable())
		{		
			for(int i = 0; i < bData.getNParts(); ++i)
			{
				float[] vertices = verts[i];	
				weaponVerts.set(vertices);				
				FixtureDef fixWeaponDef = new FixtureDef();
				fixWeaponDef.shape = weaponVerts;		
				worldPhys.initMaterialProperties(materials[i], compoundObject.stateHS.level);
				fixWeaponDef.density = worldPhys.materialDensity;
				
				body.createFixture(fixWeaponDef).setUserData(worldPhys.setFixUserData(PhysicsBox2d.FIXTURE_PROPERTY1_IS_ATTACK_PASSIVE, 
		        		(short)materials[i],  worldPhys.materialHealth, i));
			} 
		}
		else if (compoundObject.isBreakable())
		{
			float averageHealth = 0;
			
			for(int i = 0; i < bData.getNParts(); ++i)
			{
				float[] vertices = verts[i];	
				vertices = MyMath.scaleVertices(vertices, Statics.Render.SEED_STANDART_SIZE + 
						compoundObject.stateHS.level * Statics.Render.SEED_LEVEL_STEP_SIZE);
				weaponVerts.set(vertices);				
				FixtureDef fixWeaponDef = new FixtureDef();
				fixWeaponDef.shape = weaponVerts;	
				worldPhys.initMaterialProperties(materials[i], compoundObject.stateHS.level);
				fixWeaponDef.density = worldPhys.materialDensity;
				averageHealth += worldPhys.materialHealth;
				
				body.createFixture(fixWeaponDef).setUserData(worldPhys.setFixUserData(PhysicsBox2d.FIXTURE_PROPERTY1_IS_ATTACK_PASSIVE, 
		        		(short)materials[i],  0, i));
			} 
			
			averageHealth /= bData.getNParts();
			compoundObject.stateHS.health = compoundObject.stateHS.defencePower = averageHealth;
		}
	}

}
