package com.kingsnake.gl;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MySpriteBatcher extends SpriteBatch{
	
	public MySpriteBatcher()
	{
		super();
	}
	
	public void draw(float x, float y, float width, float height, float angle, Texture texture)
	{
		super.draw(texture, x - width/2, y - height/2, width/2, height/2, width, height, 1, 1, angle, 1, 1, 1, 1, false, false);
	}
	
	public void draw(float x, float y, float width, float height, float angle, TextureRegion region)
	{
		super.draw(region, x - width/2, y - height/2, width/2, height/2, width, height, 1, 1, angle);
	}
	
	public void draw(float x, float y, float width, float height, TextureRegion region)
	{
		super.draw(region, x - width / 2, y - height / 2, width, height);
	}

}
