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
	
	public TreeHurt(final float x, final float y, final float angle, final int level, final WorldKingSnake world) {
    	super(x, y, 2 * Statics.Render.STANDART_SIZE_1,	2 * Statics.Render.STANDART_SIZE_1,	angle, 
    			Statics.DynamicGameObject.TREE_HURT, level, world);	
    	PhysicsBox2d.setBodyVertical(myBody);
    	PhysicsBox2d.setBodyMass(myBody, Statics.PhysicsBox2D.TREEHURT_BODY_MASS);
    	branchAngle = 360 / (level + 1);
    	branchCurAngle = 0;  
    	fixtureGroupFilterId = (short) world.world2d.addCharacterFilterGroup(myBody);
    	builder = new Builder(this);
    	builder.buildClawTree();
	}
	
	void turnBranchAngle() {
		branchCurAngle += branchAngle;
	}
	
	private float getBranchAngle() {
		return branchCurAngle;
	}
	
	private void setRolling(final boolean roll) {
		if(roll) {
			for (Branch branch : branches) {
				RevoluteJoint joint = branch.getRootJoint();
				joint.setMotorSpeed(-5);
				joint.setMaxMotorTorque(5);
			}
		}
	}
	
	private int getTreeGroupFilter() {
		return fixtureGroupFilterId;
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	interface BranchItem {
		void setJoint(Joint joint);
		Joint getJoint();
	}
	
	class BranchClawItem extends DynamicGameObject implements BranchItem {
		private Joint myJoint;
		BranchClawItem (final float x, final float y, final float angle, final int level, final WorldKingSnake world) {
	    	super(x, y, Statics.Render.STANDART_SIZE_1,	Statics.Render.STANDART_SIZE_1,	angle, 
	    			Statics.DynamicGameObject.TREE_HURT_ORDYNARY_SEGMENT, level, world);
	    	myJoint = null;
	    	///!!!
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
		protected RevoluteJoint getRootJoint() {
			return  (RevoluteJoint)items.get(0).getJoint();
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
			setRolling(true);
		}
	}
	
	

}
