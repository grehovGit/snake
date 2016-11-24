package com.kingsnake.physicsBox2d;

import java.util.List;

import com.kingsnake.physicsBox2d.PhysicsBox2d;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.WeldJoint;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.example.framework.model.DynamicGameObject;
import com.example.framework.model.GameObject;
import com.example.framework.model.SnakePart;
import com.example.framework.model.Statics;
import com.example.framework.model.WorldKingSnake;

public class MyQueryCallback implements QueryCallback{
	
	static final int TREE_BOMB_EXPLOSION = 1;
	static final int FSKILL_IMPULSE = 2;
	static final int FSKILL_ICE = 3;
	
	public int task;
	float data1, data2, data3, data4, data5;
	List<Fixture> fixList;
	
	World world2d;
	WorldKingSnake world;
	PhysicsBox2d worldPhys;
	DynamicGameObject dynObj;
	
	MyQueryCallback(World world2d_, WorldKingSnake world_, PhysicsBox2d worldPh)
	{
		world2d = world2d_;
		world = world_;
		worldPhys = worldPh;
		dynObj = null;
		fixList = null;
		
		data1 = 0;
		data2 = 0;
		data3 = 0;
		data4 = 0;
		data5 = 0;
	}

	@Override
	public boolean reportFixture(Fixture fixture) {
		// TODO Auto-generated method stub
		
		switch(task)
		{
		case TREE_BOMB_EXPLOSION:
			explosion(data1, data2, data3, fixture);
			break;
		case FSKILL_IMPULSE:
			impulse(data1 /*force*/, data4/*damage*/, data2/*x*/, data3/*y*/, data5/*angle*/, fixture);
			break;
		case FSKILL_ICE:
			freeze(data2 /*x*/, data3/*y*/,  fixture);
			break;
		}
		return true;
	}
	
	public void setTask(int task)
	{
		this.task = task;
	}
	
	public void setDataForTreeBombExplosion(float power, float x, float y)
	{
		data1 = power;
		data2 = x;
		data3 = y;
	}
	
	public void setDataForFImpulse(float power, float x, float y, float damage, float angle, DynamicGameObject dynObj)
	{
		data1 = power;
		data2 = x;
		data3 = y;
		data4 = damage;
		data5 = angle;
		this.dynObj = dynObj;
	}
	
	public void setDataForFreeze(float x, float y, DynamicGameObject dynObj)
	{
		data1 = dynObj.bounds.width / 2;
		data2 = x;
		data3 = y;
		this.dynObj = dynObj;
	}
	
	
	void explosion(float power, float xEpicenter, float yEpicenter, Fixture fix)
	{
		Vector2 vector = new Vector2(fix.getBody().getPosition());
		vector.sub(xEpicenter, yEpicenter);
		float distanceSquare = vector.len2();
		float impPower = power / (distanceSquare > 1 ? distanceSquare : 1);
		vector.nor();
		vector.scl(impPower);
		fix.getBody().applyLinearImpulse(vector, fix.getBody().getPosition(), true);				
	}
	
	void freeze(float xEpicenter, float yEpicenter, Fixture fix)
	{
		Vector2 vector = new Vector2(fix.getBody().getPosition());
		vector.sub(xEpicenter, yEpicenter);
		float distance = vector.len();

		if(!fix.isSensor() && distance <= 1.5f * data1 /* radius */)
		{
			WeldJointDef freezeJointDef = new WeldJointDef();
			freezeJointDef.initialize(dynObj.myBody, fix.getBody(), dynObj.myBody.getPosition());
			WeldJoint freezeJoint = (WeldJoint) worldPhys.world2d.createJoint(freezeJointDef);
			freezeJoint.setUserData(worldPhys.setJointUserData(PhysicsBox2d.JOINT_PROPERTY1_FREEZE_HOLD, -1, PhysicsBox2d.JOINT_PROPERTY3_STATE_ALIVE, 0));				
		}
			
	}
	
	void impulse(float power, float damage, float xEpicenter, float yEpicenter, float angle, Fixture fix)
	{
		Vector2 impulseVector = new Vector2(fix.getBody().getPosition());
		impulseVector.sub(xEpicenter, yEpicenter);
		float distanceSquare = impulseVector.len2();
		
		Vector2 angleVector = new Vector2(1, 0);
		angleVector.setAngle(angle);
		
		//if target is out of impulse zone - return
		if(Math.abs(angleVector.angle(impulseVector)) > 90)
			return;
				
		float impPower = power / (distanceSquare > 1 ? distanceSquare : 1);
		float impDamage = damage / (distanceSquare > 1 ? distanceSquare : 1);	
		impulseVector.nor();
		impulseVector.scl(impPower);
		
		GameObject gObj = (GameObject)fix.getBody().getUserData();
		
		if(gObj!= null)
		{	
			//this is master
			if(gObj.equals(dynObj))
				return;
			
			//if master is snake
			if(gObj.isDynamicObject && ((DynamicGameObject) gObj).objType == Statics.DynamicGameObject.SNAKE && dynObj.objType == Statics.DynamicGameObject.SNAKE
					&& ((SnakePart)gObj).snake.equals(((SnakePart)dynObj).snake))
				return;
			
			worldPhys.initMaterialProperties(fix);
			((float[])fix.getUserData())[PhysicsBox2d.FIXTURE_PROPERTY3_INDEX_HEALTH] -= impDamage / worldPhys.materialImpulseResist;
		}
		
		fix.getBody().applyLinearImpulse(impulseVector, fix.getBody().getPosition(), true);				
	}
	
	

}
