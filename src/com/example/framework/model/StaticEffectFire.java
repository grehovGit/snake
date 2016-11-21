package com.example.framework.model;

import static org.junit.Assert.fail;

import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.kingsnake.gl.ParticlesEffectsManager;

	/**
	 * produce static (not movable by itself, can move only with master object) effect fire
	 * @author Grehov
	 *
	 */

public class StaticEffectFire extends StaticEffect {
	
	PooledEffect effect;
	GameObject master = null;
	
	public StaticEffectFire(float x, float y, float width, float height, float angle, float lifeTime, int level,
			 GameObject _master)  
	{
		 super(x, y, width, height, Statics.StaticEffect.FIRE_EFFECT, angle, lifeTime);
		 master = _master;
		 effect = ParticlesEffectsManager.createFireEffect(master.position.x, master.position.y, lifeTime, level); 
		 effect.start();
	}
	
	/**
	 * updates effect.
	 * @param deltaTime delta in time (in seconds) between current and previous update
	 */	
	public void update(float deltaTime)
	{
		if (master == null || master.myBody == null || !master.isFlaming)
		{
			finish();
			return;
		}
		
		super.update(deltaTime);
		
		if (effect != null && !effect.isComplete())
			effect.setPosition(master.position.x, master.position.y);	
	}
	
	/**
	 * set lifetime to zero, so forces to be destroyed; releases nested PooledEffect object.
	 * call inside object
	 */	
	public void finish ()
	{
		lifetimePeriod = 0;
		release();
	}
	
	/**
	 * Releases nested PooledEffect object. Call before effect destroying.
	 */	
	public void release ()
	{
		if (effect != null)
			ParticlesEffectsManager.releaseEffectByValue(effect);
	}
	
    /**
     * @return master
     */
    public GameObject getMaster() 
    {
   	 	return master;
    };
}
