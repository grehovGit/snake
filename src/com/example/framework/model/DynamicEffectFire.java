package com.example.framework.model;

import com.kingsnake.gl.ParticlesEffectsManager;

public class DynamicEffectFire extends DynamicEffect{
	

	public DynamicEffectFire(float x, float y, float width, float height,
			float angle_, float lifeTime, float power, DynamicGameObject master_) {
		super(x, y, width, height, angle_, lifeTime, Statics.DynamicGameObject.FSKILL_FORWARD_ATTACK_FIRE_EFFECT, master_);
		 
		 particleEffect = ParticlesEffectsManager.createFireEffect(x, y, lifeTime, power);
		 particleEffect.start();
		 this.power = power;
		 isParticleEffect = true;
	}
} 