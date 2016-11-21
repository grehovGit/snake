package com.kingsnake.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;
import com.kingsnake.gl.MySpriteBatcher;

public class KingSnake extends Game {
	
	public MySpriteBatcher batch;
	public World world;

	@Override
	public void create() {
		// TODO Auto-generated method stub
		  batch = new MySpriteBatcher();
	      Box2D.init();  
		  world = new World(new Vector2(0, 0), true); 
	        //Use LibGDX's default Arial font.
	        this.setScreen(new LevelScreen(this));
		
	}
	
	   public void render() {
	        super.render(); //important!
	    }

	    public void dispose() {
	        batch.dispose();
	    }


}
