package com.kingsnake.control;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.example.framework.model.Snake;
import com.example.framework.model.Statics;

public class MyInputProcessorUI implements InputProcessor {
	
	Controls controls;
	int joystickFinger;
	int controlFinger;
	
	
	MyInputProcessorUI(Controls controls)
	{
		this.controls = controls;
		joystickFinger = controlFinger = -1;
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		
		Vector3  coords3d = new Vector3(screenX, screenY, 0);		
		coords3d = controls.screen.camera.unproject(coords3d);
		
		if(controls.joystick.bounds.contains(coords3d.x, coords3d.y))
		{
			controls.joystick.state = Joystick.JoystickState.Running;
			joystickFinger = pointer;
			
			controls.joystick.curPoint.set(coords3d.x, coords3d.y);
			controls.joystick.curPoint.sub(Statics.Render.JOYSTICK_POS_X, Statics.Render.JOYSTICK_POS_Y);	
			controls.joystick.angle = 0;
			controls.joystick.setAvatarMoveMode(Snake.MOVE);
			controls.joystick.tupStartTime = controls.screen.actTime;
			return true;
		}
		else if(controls.jumps.bounds.contains(coords3d.x, coords3d.y))
		{
			controlFinger = pointer;
			controls.controlsActive = Controls.ControlActive.Jumps;
			controls.jumps.state = ControlJumps.JumpsState.Starting;
			controls.jumps.startStateChangeTime = 0;
			return true;
		}
		else if(controls.impulses.bounds.contains(coords3d.x, coords3d.y))
		{
			controlFinger = pointer;
			controls.controlsActive = Controls.ControlActive.Impulses;
			controls.impulses.state = ControlImpulse.ImpulseState.Starting;
			controls.impulses.startStateChangeTime = 0;
			return true;
		}
		
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		if(pointer == joystickFinger)
		{
			controls.joystick.state = Joystick.JoystickState.Ending;
			joystickFinger = -1;
			controls.joystick.setAvatarMoveMode(Snake.STAY);
			
			if(controls.screen.actTime - controls.joystick.tupStartTime <= Statics.Controls.JOYSTICK_TAP_PERIOD)
				controls.joystick.setAvatarJaws();
			
			return true;
		}
		else if(pointer == controlFinger)
		{
			if(controls.controlsActive == Controls.ControlActive.Jumps)
				controls.jumps.state = ControlJumps.JumpsState.Ending;
			else if(controls.controlsActive == Controls.ControlActive.Impulses)
				controls.impulses.state = ControlImpulse.ImpulseState.Ending;
			
			controls.controlsActive = Controls.ControlActive.None;	
			controls.jumps.startStateChangeTime = 0;
			controls.impulses.startStateChangeTime = 0;
			return true;
		}
		
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		Vector3  coords3d = new Vector3(screenX, screenY, 0);		
		coords3d = controls.screen.camera.unproject(coords3d);
		
		if(pointer == joystickFinger && controls.joystick.state == Joystick.JoystickState.Running)
		{
			Vector2 coords = new Vector2(coords3d.x, coords3d.y);
			coords.sub(Statics.Render.JOYSTICK_POS_X, Statics.Render.JOYSTICK_POS_Y);
			controls.joystick.setAngle(coords.angle());			
			return true;
		}
		else if(pointer == controlFinger)
		{
			if(controls.controlsActive == Controls.ControlActive.Jumps && controls.jumps.state == ControlJumps.JumpsState.Running)
				controls.jumps.processControlJumps(coords3d.x, coords3d.y);
			else if(controls.controlsActive == Controls.ControlActive.Impulses && controls.impulses.state == ControlImpulse.ImpulseState.Running)
				controls.impulses.processControlImpulses(coords3d.x, coords3d.y);
			return true;			
		}
		
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
