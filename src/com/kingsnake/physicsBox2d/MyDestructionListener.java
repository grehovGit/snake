package com.kingsnake.physicsBox2d;

import com.badlogic.gdx.physics.box2d.DestructionListener;
import com.badlogic.gdx.physics.box2d.Joint;

public class MyDestructionListener implements DestructionListener {
	
	PhysicsBox2d worldPhys;
	
	MyDestructionListener(PhysicsBox2d world)
	{
		worldPhys = world;
	}
	
	void SayGoodbye(Joint joint) 
    { 		
        // remove all references to joint. 
	 	switch((int)((float[])joint.getUserData())[PhysicsBox2d.JOINT_PROPERTY1_INDEX_JOINT_TYPE])
	 	{
	 	case PhysicsBox2d.JOINT_PROPERTY1_BITEHOLD:
	 		while(worldPhys.biteJoints.remove(joint));
	 		break;
	 	}
	 	
    } 

}
