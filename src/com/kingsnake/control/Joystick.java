package com.kingsnake.control;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.example.framework.model.Snake;
import com.example.framework.model.Statics;

public class Joystick {
	
	Controls controls;
	Vector2 curPoint;
	Rectangle bounds;
	public float angle;
	float tupStartTime;
	
	 public enum JoystickState {
	        Ready,
	        Running,
	        Ending,
	    }
	
	 public JoystickState state;
	 
	 Joystick(Controls controls)
	 {
		 curPoint = new Vector2();
		 this.controls = controls;
		 angle = 0;
		 state = JoystickState.Ready;
		 bounds = new Rectangle(Statics.Render.JOYSTICK_POS_X - Statics.Render.JOYSTICK_SIZE / 2, 
				 Statics.Render.JOYSTICK_POS_Y - Statics.Render.JOYSTICK_SIZE / 2, Statics.Render.JOYSTICK_SIZE, Statics.Render.JOYSTICK_SIZE);
	 }
	 
	 void setAngle (float angle)
	 {
		 this.angle = angle;
	 }
	 
	 void update (float deltaTime)
	 {
		 if(state == Joystick.JoystickState.Ending)
		 {
			 if(angle * curPoint.angle() > 0)
			 {
				float angleDelta = curPoint.angle() > 0 ? - 1 * 360 * deltaTime / Statics.Render.JOYSTICK_PERIOD :
					 				360 * deltaTime / Statics.Render.JOYSTICK_PERIOD;
				angle += angleDelta;
			 }
			 else
			 {
				 state = Joystick.JoystickState.Ready;
				 angle = 0;
			 }			
		 }
		 
		 //set move force direction to our character
		 if(state == Joystick.JoystickState.Running)
		 {
			 controls.avatar.SetMoveAngle(angle);
			 setAvatarMoveMode(Snake.MOVE);
		 }
	 }
	 
	 void setAvatarMoveMode(int mode)
	 {
		 controls.avatar.SetMoveMode(mode);
	 }
	 
	 void setAvatarJaws()
	 {
		 controls.avatar.setJawsState();
	 }

}
