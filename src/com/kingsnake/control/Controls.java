package com.kingsnake.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.example.framework.model.DynamicGameObject;
import com.kingsnake.screens.LevelScreen;

public class Controls {
	
	DynamicGameObject avatar;
	LevelScreen screen;
	public Joystick joystick;
	public ControlJumps jumps;
	public ControlImpulse impulses;
	InputMultiplexer multiplexer;
	MyInputProcessorUI inputProcessorUI;
	MyInputProcessorGame inputProcessorGame;
	
	public enum ControlActive {
	        Jumps,
	        Impulses,
	        Weapon,
	        None
	}
	 
	public ControlActive controlsActive;
 
	
	public Controls(LevelScreen screen)
	{
		this.screen = screen;
		avatar = screen.world.character;
		inputProcessorUI = new MyInputProcessorUI(this);
		inputProcessorGame = new MyInputProcessorGame(this);
		
		multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(inputProcessorUI);
		multiplexer.addProcessor(inputProcessorGame);
		Gdx.input.setInputProcessor(multiplexer);
		
		joystick = new Joystick(this);
		jumps = new ControlJumps(this);
		impulses = new ControlImpulse(this);
		
		controlsActive = ControlActive.None;
	}
	
	public void update (float deltaTime)
	{
		joystick.update(deltaTime);
		jumps.update(deltaTime);
		impulses.update(deltaTime);
	}

}
