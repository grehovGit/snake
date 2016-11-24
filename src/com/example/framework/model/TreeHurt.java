package com.example.framework.model;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.JointDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.kingsnake.physicsBox2d.PhysicsBox2d;


public class TreeHurt extends DynamicGameObject {

	private final float branchAngle;
	private float branchCurAngle;
	private final Builder builder;
	private final List<Branch> branches = new ArrayList<Branch>();
	private enum State {normal, excited};
	State state;
	
	public TreeHurt(final float x, final float y, final float angle, final int level, final WorldKingSnake world) {
    	super(x, y, 2 * Statics.Render.STANDART_SIZE_1,	2 * Statics.Render.STANDART_SIZE_1,	angle, 
    			Statics.DynamicGameObject.TREE_HURT, level, world);	
    	PhysicsBox2d.setBodyVertical(myBody);
    	PhysicsBox2d.setBodyMass(myBody, Statics.PhysicsBox2D.TREEHURT_BODY_MASS);
    	branchAngle = 360 / (level + 1);
    	branchCurAngle = 0;  
    	fixtureGroupFilterId = (short) world.world2d.addCharacterFilterGroup(myBody);
    	builder = new Builder(this);
    	state = State.normal;
    	builder.buildClawTree();
	}
	
	void turnBranchAngle() {
		branchCurAngle += branchAngle;
	}
	
	private float getBranchAngle() {
		return branchCurAngle;
	}
	
	private void setRotation(State state) {
		if(state == State.excited) {
			for (Branch branch : branches) {
				RevoluteJoint joint = branch.getRootJoint();
				joint.setMotorSpeed(-150);
				joint.setMaxMotorTorque(150);
			}
		} else {
			for (Branch branch : branches) {
				RevoluteJoint joint = branch.getRootJoint();
				joint.setMotorSpeed(0);
				joint.setMaxMotorTorque(Statics.PhysicsBox2D.TREEHURT_BRANCHITEM_JOINT_NORMAL_TORQUE);
			}			
		}
	}
	
	private void provideWaving() {
		for (Branch branch : branches)
			branch.provideWaving();	
	}
	
	private int getTreeGroupFilter() {
		return fixtureGroupFilterId;
	}
	
    public void update(float deltaTime) {    	
    	super.update(deltaTime);
    	if (world.world2d.isEnemyInRegion(this, 2 * Statics.PhysicsBox2D.SENSOR_FIGHT_SKILL_TREEHURT_RADIUS, 
    			2 * Statics.PhysicsBox2D.SENSOR_FIGHT_SKILL_TREEHURT_RADIUS))
    			setState(State.excited);
    	else {
    		setState(State.normal);
    		provideWaving();
    	}
    	
    }
    
    private void setState(final State state) {  
    	if (this.state != state)
    		setRotation(this.state = state);
    }

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	interface BranchItem {
		void setJoint(Joint joint);
		RevoluteJoint getJoint();
	}
	
	class BranchClawItem extends DynamicGameObject implements BranchItem {
		private Joint myJoint;
		BranchClawItem (final float x, final float y, final float angle, final int level, final WorldKingSnake world) {
	    	super(x, y, Statics.Render.STANDART_SIZE_1,	Statics.Render.STANDART_SIZE_1,	angle, 
	    			Statics.DynamicGameObject.TREE_HURT_ORDYNARY_SEGMENT, level, world);
	    	myJoint = null;
	    	int filter = getTreeGroupFilter();
	    	PhysicsBox2d.setBodyFilter(filter, myBody);
		}
		@Override
		public void setJoint(Joint joint) {
			myJoint = joint;
		}
		
		@Override
		public RevoluteJoint getJoint() {
			return (RevoluteJoint)myJoint;
		}
	}
	
	class Branch {
		private List<BranchItem> items = new ArrayList<BranchItem>(); 
		
		public void addItem(BranchItem item) {
			items.add(item);
		}
		public BranchItem getItem(int index) {
			return items.get(index);
		}
		RevoluteJoint getRootJoint() {
			return  (RevoluteJoint)items.get(0).getJoint();
		}
		void provideWaving() {
			int index = 0;
			for (BranchItem item : items) {
				item.getJoint().setMaxMotorTorque(Statics.PhysicsBox2D.TREEHURT_BRANCHITEM_JOINT_NORMAL_TORQUE);
//				item.getJoint().setMotorSpeed( (float) (index++ % 2 == 0 ? Math.sin(world.actTime * Statics.PhysicsBox2D.TREEHURT_BRANCHITEM_JOINT_NORMAL_TORQUE / 5)
//						: -Math.sin(world.actTime * Statics.PhysicsBox2D.TREEHURT_BRANCHITEM_JOINT_NORMAL_TORQUE / 5)));
//				
				float speed = (float) (Math.sin( (world.actTime * Math.PI - 0.5 * index * Math.PI) /4));
				index++;
								
				item.getJoint().setMotorSpeed(speed);
			}
		}
	}
	
	class Builder {
		TreeHurt master;
		public Builder (TreeHurt treeHurt) {
			this.master = treeHurt;
		}
		
		public void addClawBranches() {
			for (int i = 0; i < master.stateHS.level + 1; i++)
				addClawBranch();
		}
		
		public void addClawBranch() {
			Branch branch = new Branch();
			for (int i=0; i < Statics.PhysicsBox2D.TREEHURT_ORDYNARY_SEGMENT_COUNT; i++) {
				branch.addItem(getClawItem(i, (DynamicGameObject) (i == 0 ? master : branch.getItem(i - 1))));
			}
			turnBranchAngle();
			branches.add(branch);
		}
		
		private BranchItem getClawItem(final int index, DynamicGameObject previousItem) {
			Vector2 offset = new Vector2(index * Statics.PhysicsBox2D.TREEHURT_FIRST_SEGMENT_OFFSET 
					+ Statics.PhysicsBox2D.TREEHURT_STEP_SEGMENT_OFFSET, 0);
			Vector2 anchorPos = new Vector2(offset);
			offset.setAngle(getBranchAngle());
			offset.add(master.position.x, master.position.y);			
			BranchClawItem newItem =  new BranchClawItem(offset.x, offset.y, getBranchAngle(), master.stateHS.level, master.world);

			anchorPos.add(Statics.PhysicsBox2D.TREEHURT_SEGMENT_ANCHOR_OFFSET, 0);
			anchorPos.setAngle(getBranchAngle());
			anchorPos.add(master.position.x, master.position.y);	
			RevoluteJoint joint = (RevoluteJoint) world.world2d.createJoint(Statics.PhysicsBox2D.TREEHURT_BRANCHITEM_JOINT, (JointDef)Statics.PhysicsBox2D.getHurtTreeBranchItemJointDef(),
					(int)((master.stateHS.level + 1) * Statics.PhysicsBox2D.JOINT_FORCE_BASIC), index, previousItem.myBody, newItem.myBody, anchorPos);
			if (index == 0) joint.enableLimit(false);
			newItem.setJoint(joint);
			world.dynObjects.add(newItem);
			return newItem;
		}
		public void buildClawTree() {
			addClawBranches();
		}
	}
	
	

}
