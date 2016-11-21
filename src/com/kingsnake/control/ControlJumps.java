package com.kingsnake.control;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.example.framework.model.Statics;

public class ControlJumps {
	
	Controls controls;
	Vector2 curPoint;
	Rectangle bounds;
	public float startStateChangeTime;
	
	Rectangle boundsForward;
	Rectangle boundsForwardLeftHook;
	Rectangle boundsForwardRightHook;
	Rectangle boundsForwardLeftTurn;
	Rectangle boundsForwardRightTurn;
	Rectangle boundsBackLeftHook;
	Rectangle boundsBackRightHook;
	Rectangle boundsBack;
	
	 public enum JumpsState {
	        Ready,
	        Starting,
	        Running,
	        Ending
	    }
	
	 public JumpsState state;
	 
	 ControlJumps(Controls controls)
	 {
		 curPoint = new Vector2();
		 this.controls = controls;
		 state = JumpsState.Ready;
		 float xCenter = Statics.Render.CONTROL_JUMPS_POS_X;
		 float yCenter = Statics.Render.CONTROL_JUMPS_POS_Y;
		 float offset = Statics.Render.CONTROL_JUMPS_SKILLS_OFFSET;
		 float offsetDiag = (float) Math.sqrt(offset);
		 
		 bounds = new Rectangle(xCenter - Statics.Render.CONTROL_JUMPS_SIZE / 2, yCenter - Statics.Render.CONTROL_JUMPS_SIZE / 2, 
				 Statics.Render.CONTROL_JUMPS_SIZE, Statics.Render.CONTROL_JUMPS_SIZE);	
		 
		 boundsForward = new Rectangle(xCenter - Statics.Render.CONTROL_JUMPS_SKILLS_SIZE / 2, yCenter + offset - Statics.Render.CONTROL_JUMPS_SKILLS_SIZE / 2, 
				 Statics.Render.CONTROL_JUMPS_SKILLS_SIZE, Statics.Render.CONTROL_JUMPS_SKILLS_SIZE);
		 boundsForwardLeftHook = new Rectangle(xCenter - offsetDiag - Statics.Render.CONTROL_JUMPS_SKILLS_SIZE / 2, yCenter + offsetDiag - Statics.Render.CONTROL_JUMPS_SKILLS_SIZE / 2, 
				 Statics.Render.CONTROL_JUMPS_SKILLS_SIZE, Statics.Render.CONTROL_JUMPS_SKILLS_SIZE);
		 boundsForwardRightHook = new Rectangle(xCenter + offsetDiag - Statics.Render.CONTROL_JUMPS_SKILLS_SIZE / 2, yCenter + offsetDiag - Statics.Render.CONTROL_JUMPS_SKILLS_SIZE / 2, 
				 Statics.Render.CONTROL_JUMPS_SKILLS_SIZE, Statics.Render.CONTROL_JUMPS_SKILLS_SIZE);		 
		 boundsForwardLeftTurn = new Rectangle(xCenter - offset - Statics.Render.CONTROL_JUMPS_SKILLS_SIZE / 2, yCenter - Statics.Render.CONTROL_JUMPS_SKILLS_SIZE / 2, 
				 Statics.Render.CONTROL_JUMPS_SKILLS_SIZE, Statics.Render.CONTROL_JUMPS_SKILLS_SIZE);
		 boundsForwardRightTurn = new Rectangle(xCenter + offset - Statics.Render.CONTROL_JUMPS_SKILLS_SIZE / 2, yCenter - Statics.Render.CONTROL_JUMPS_SKILLS_SIZE / 2, 
				 Statics.Render.CONTROL_JUMPS_SKILLS_SIZE, Statics.Render.CONTROL_JUMPS_SKILLS_SIZE);
		 boundsBackLeftHook = new Rectangle(xCenter - offsetDiag - Statics.Render.CONTROL_JUMPS_SKILLS_SIZE / 2, yCenter - offsetDiag - Statics.Render.CONTROL_JUMPS_SKILLS_SIZE / 2, 
				 Statics.Render.CONTROL_JUMPS_SKILLS_SIZE, Statics.Render.CONTROL_JUMPS_SKILLS_SIZE);
		 boundsBackRightHook = new Rectangle(xCenter + offsetDiag - Statics.Render.CONTROL_JUMPS_SKILLS_SIZE / 2, yCenter - offsetDiag - Statics.Render.CONTROL_JUMPS_SKILLS_SIZE / 2, 
				 Statics.Render.CONTROL_JUMPS_SKILLS_SIZE, Statics.Render.CONTROL_JUMPS_SKILLS_SIZE);
		 boundsBack = new Rectangle(xCenter - Statics.Render.CONTROL_JUMPS_SKILLS_SIZE / 2, yCenter - offset - Statics.Render.CONTROL_JUMPS_SKILLS_SIZE / 2, 
				 Statics.Render.CONTROL_JUMPS_SKILLS_SIZE, Statics.Render.CONTROL_JUMPS_SKILLS_SIZE);		 		 

	 }
	 
	 void update (float deltaTime)
	 {
		 if(state == ControlJumps.JumpsState.Starting)	
		 {
			 if(startStateChangeTime >= Statics.Controls.CONTROL_JUMP_STATE_CHANGE_PERIOD)
				 state = ControlJumps.JumpsState.Running;
		 }
		 else if(state == ControlJumps.JumpsState.Ending)	
		 {
			 if(startStateChangeTime >= Statics.Controls.CONTROL_JUMP_STATE_CHANGE_PERIOD)
				 state = ControlJumps.JumpsState.Ready;
		 }
		 
		 startStateChangeTime += deltaTime;	
	 }
	 
	 void processControlJumps(float xPoint, float yPoint)
	 {
		 if(controls.avatar.fSkills.forwardAttack && boundsForward.contains(xPoint, yPoint))
			 controls.avatar.fSkills.strikeAvatar(controls.screen.world.actTime, Statics.FightingSkills.FORWARD_ATTACK);
		 
		 else if(controls.avatar.fSkills.forwardLeftHook && boundsForwardLeftHook.contains(xPoint, yPoint))
			 controls.avatar.fSkills.strikeAvatar(controls.screen.world.actTime, Statics.FightingSkills.FORWARD_LEFT_HOOK);

		 else if(controls.avatar.fSkills.forwardRightHook && boundsForwardRightHook.contains(xPoint, yPoint))
			 controls.avatar.fSkills.strikeAvatar(controls.screen.world.actTime, Statics.FightingSkills.FORWARD_RIGHT_HOOK);

		 else if(controls.avatar.fSkills.forwardLeftTurn && boundsForwardLeftTurn.contains(xPoint, yPoint))
			 controls.avatar.fSkills.strikeAvatar(controls.screen.world.actTime, Statics.FightingSkills.FORWARD_LEFT_TURN);

		 else if(controls.avatar.fSkills.forwardRightTurn && boundsForwardRightTurn.contains(xPoint, yPoint))
			 controls.avatar.fSkills.strikeAvatar(controls.screen.world.actTime, Statics.FightingSkills.FORWARD_RIGHT_TURN);

		 else if(controls.avatar.fSkills.backLeftHook && boundsBackLeftHook.contains(xPoint, yPoint))
			 controls.avatar.fSkills.strikeAvatar(controls.screen.world.actTime, Statics.FightingSkills.BACK_LEFT_HOOK);

		 else if(controls.avatar.fSkills.backRightHook && boundsBackRightHook.contains(xPoint, yPoint))
			 controls.avatar.fSkills.strikeAvatar(controls.screen.world.actTime, Statics.FightingSkills.BACK_RIGHT_HOOK);

		 else if(controls.avatar.fSkills.backAttack && boundsBack.contains(xPoint, yPoint))
			 controls.avatar.fSkills.strikeAvatar(controls.screen.world.actTime, Statics.FightingSkills.BACK_ATTACK);		 
	 }

}
