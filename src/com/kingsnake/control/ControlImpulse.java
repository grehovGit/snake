package com.kingsnake.control;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.example.framework.model.Statics;

public class ControlImpulse {
	
	Controls controls;
	Vector2 curPoint;
	Rectangle bounds;
	public float startStateChangeTime;
	
	Rectangle boundsForwardImpulseAttack;
	Rectangle boundsForwardImpulseDef;
	Rectangle boundsSideImpulseLeft;
	Rectangle boundsSideImpulseRight;
	
	 public enum ImpulseState {
	        Ready,
	        Starting,
	        Running,
	        Ending
	    }
	
	 public ImpulseState state;
	 
	 ControlImpulse(Controls controls)
	 {
		 curPoint = new Vector2();
		 this.controls = controls;
		 state = ImpulseState.Ready;
		 float xCenter = Statics.Render.CONTROL_IMPULSE_POS_X;
		 float yCenter = Statics.Render.CONTROL_IMPULSE_POS_Y;
		 float offset = Statics.Render.CONTROL_IMPULSE_SKILLS_OFFSET;
		 
		 bounds = new Rectangle(xCenter - Statics.Render.CONTROL_IMPULSE_SIZE / 2, yCenter - Statics.Render.CONTROL_IMPULSE_SIZE / 2, 
				 Statics.Render.CONTROL_IMPULSE_SIZE, Statics.Render.CONTROL_IMPULSE_SIZE);	
		 
		 boundsForwardImpulseAttack = new Rectangle(xCenter - Statics.Render.CONTROL_IMPULSE_SKILLS_SIZE / 2, yCenter + offset - Statics.Render.CONTROL_IMPULSE_SKILLS_SIZE / 2, 
				 Statics.Render.CONTROL_IMPULSE_SKILLS_SIZE, Statics.Render.CONTROL_IMPULSE_SKILLS_SIZE);
		 boundsSideImpulseLeft = new Rectangle(xCenter - offset - Statics.Render.CONTROL_IMPULSE_SKILLS_SIZE / 2, yCenter - Statics.Render.CONTROL_IMPULSE_SKILLS_SIZE / 2, 
				 Statics.Render.CONTROL_IMPULSE_SKILLS_SIZE, Statics.Render.CONTROL_IMPULSE_SKILLS_SIZE);
		 boundsSideImpulseRight = new Rectangle(xCenter + offset - Statics.Render.CONTROL_IMPULSE_SKILLS_SIZE / 2, yCenter - Statics.Render.CONTROL_IMPULSE_SKILLS_SIZE / 2, 
				 Statics.Render.CONTROL_IMPULSE_SKILLS_SIZE, Statics.Render.CONTROL_IMPULSE_SKILLS_SIZE);
		 boundsForwardImpulseDef = new Rectangle(xCenter - Statics.Render.CONTROL_IMPULSE_SKILLS_SIZE / 2, yCenter - offset - Statics.Render.CONTROL_IMPULSE_SKILLS_SIZE / 2, 
				 Statics.Render.CONTROL_IMPULSE_SKILLS_SIZE, Statics.Render.CONTROL_IMPULSE_SKILLS_SIZE);		 		 

	 }
	 
	 void update (float deltaTime)
	 {
		 if(state == ControlImpulse.ImpulseState.Starting)	
		 {
			 if(startStateChangeTime >= Statics.Controls.CONTROL_IMP_STATE_CHANGE_PERIOD)
				 state = ControlImpulse.ImpulseState.Running;
		 }
		 else if(state == ControlImpulse.ImpulseState.Ending)	
		 {
			 if(startStateChangeTime >= Statics.Controls.CONTROL_IMP_STATE_CHANGE_PERIOD)
				 state = ControlImpulse.ImpulseState.Ready;
		 }
		 
		 startStateChangeTime += deltaTime;	
	 }
	 
	 void processControlImpulses(float xPoint, float yPoint)
	 {
		 if(controls.avatar.fSkills.impulseForwardAttack && boundsForwardImpulseAttack.contains(xPoint, yPoint))
			 controls.avatar.fSkills.strikeAvatar(controls.screen.world.actTime, Statics.FightingSkills.IMPULSE_ATTACK_FORWARD);

		 else if(controls.avatar.fSkills.impulseLeftSideDef && boundsSideImpulseLeft.contains(xPoint, yPoint))	
			 controls.avatar.fSkills.strikeAvatar(controls.screen.world.actTime, Statics.FightingSkills.IMPULSE_DEFFENSE_LEFTSIDE);

		 else if(controls.avatar.fSkills.impulseRightSideDef && boundsSideImpulseRight.contains(xPoint, yPoint))	
			 controls.avatar.fSkills.strikeAvatar(controls.screen.world.actTime, Statics.FightingSkills.IMPULSE_DEFFENSE_RIGHTSIDE);

		 else if(controls.avatar.fSkills.impulseForwardDef && boundsForwardImpulseDef.contains(xPoint, yPoint))	
			 controls.avatar.fSkills.strikeAvatar(controls.screen.world.actTime, Statics.FightingSkills.IMPULSE_DEFFENSE_FORWARD);	 
	 }

}
