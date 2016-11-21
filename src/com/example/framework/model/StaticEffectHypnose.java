package com.example.framework.model;

import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.kingsnake.gl.ParticlesEffectsManager;

public class StaticEffectHypnose extends StaticEffect {
	
	PooledEffect effect;
	
	public StaticEffectHypnose(float x, float y, float width, float height, float angle, float lifeTime, 
			 DynamicGameObject _master)  
	{
		 super(x, y, width, height, Statics.StaticEffect.HYPNOSE_EFFECT, angle, lifeTime);
		 master =  _master;
		 effect = ParticlesEffectsManager.createHypnoseEffect(x, y, lifeTime);
		 effect.start();
	}
	
	public void update(float deltaTime)
	{
		super.update(deltaTime);
		
		if(master != null && effect != null && !effect.isComplete())
			effect.setPosition(master.position.x, master.position.y);			
	}
	 
	

}
