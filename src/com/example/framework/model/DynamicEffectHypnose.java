package com.example.framework.model;

import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.kingsnake.gl.ParticlesEffectsManager;

public class DynamicEffectHypnose extends DynamicEffect{
	

	public DynamicEffectHypnose(float x, float y, float width, float height,
			float angle_, float lifeTime, float power, DynamicGameObject master_) {
		super(x, y, width, height, angle_, lifeTime, Statics.DynamicGameObject.FSKILL_FORWARD_ATTACK_HYPNOSE_EFFECT, master_);
		 
		 particleEffect = ParticlesEffectsManager.createHypnoseEffect(x, y, lifeTime);
		 particleEffect.start();
		 this.power = power;
		 isParticleEffect = true;
	}
} 
