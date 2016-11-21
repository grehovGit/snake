package com.kingsnake.physicsBox2d;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap.Entry;
import com.example.framework.model.DynamicGameObject;
import com.example.framework.model.GameObject;
import com.example.framework.model.WorldKingSnake;


public class MyRayCastCallback implements RayCastCallback {
	
	String type;
	float data1, data2, data3, data4, data5;
	Array<Entry<Float, Object>> fixturesArray;
	
	World world2d;
	WorldKingSnake world;
	PhysicsBox2d worldPhys;
	DynamicGameObject dynObj;
	
	MyRayCastCallback(World world2d_,  WorldKingSnake world_ , PhysicsBox2d worldPhys_, DynamicGameObject dynObj_,
			Array<Entry<Float, Object>> fixArray)
	{
		world2d = world2d_;
		world = world_;
		worldPhys = worldPhys_;
		dynObj = dynObj_;
		fixturesArray = fixArray;

		data1 = 0;
		data2 = 0;
		data3 = 0;
		data4 = 0;
		data5 = 0;		
	}

	@Override
	public float reportRayFixture(Fixture fixture, Vector2 point,
			Vector2 normal, float fraction) {
		// TODO Auto-generated method stub
	
		GameObject gObj = (GameObject) fixture.getBody().getUserData();
		
		//don't notice sensors, fixtures without body, own body and own weapon 
		if(!fixture.isSensor() && gObj != null && fixture.getFilterData().groupIndex != dynObj.fixtureGroupFilterId && !gObj.equals(dynObj))
		{
			Entry<Float, Object> entry = new Entry<Float, Object>();
			entry.key = fraction;
			entry.value = fixture;
			fixturesArray.add(entry);			
		}
				
		return 1;
	}
}
