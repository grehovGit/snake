package com.kingsnake.gl;

import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.example.framework.model.HealthScore;
import com.example.framework.model.Statics;
import com.example.framework.model.Statics.PhysicsBox2D;
import com.kingsnake.physicsBox2d.PhysicsBox2d;

public class ParticlesEffectsManager {
	
	public static void provideMaterialImpactDamageEffect(int material, float angle, float impDamage, float x, float y)
	{
		provideMaterialDamageEffect(material, angle, impDamage, x, y);
	}
	
	
	public static void provideMaterialBiteDamageEffect(int material, float angle, float biteDamage, float x, float y)
	{
		switch(material)
		{
			case PhysicsBox2D.FIXTURE_MATERIAL_BRONZE:
				provideMetalBiteDamageEffect(HealthScore.POWER_PINK * (PhysicsBox2d.FIXTURE_MATERIAL_BRONZE_BITE_RESISTANCE / PhysicsBox2d.FIXTURE_MATERIAL_MEAT_BITE_RESISTANCE),
						angle, biteDamage, x, y);
				break;
			case PhysicsBox2D.FIXTURE_MATERIAL_STEEL:
				provideMetalBiteDamageEffect(HealthScore.POWER_PINK * (PhysicsBox2d.FIXTURE_MATERIAL_STEEL_BITE_RESISTANCE / PhysicsBox2d.FIXTURE_MATERIAL_MEAT_BITE_RESISTANCE),
						angle, biteDamage, x, y);
				break;
			case PhysicsBox2D.FIXTURE_MATERIAL_ADAMANT:
				provideMetalBiteDamageEffect(HealthScore.POWER_PINK * (PhysicsBox2d.FIXTURE_MATERIAL_ADAMANT_BITE_RESISTANCE / PhysicsBox2d.FIXTURE_MATERIAL_MEAT_BITE_RESISTANCE),
						angle, biteDamage, x, y);
				break;
			default: 
				provideMaterialDamageEffect(material, angle, biteDamage, x, y);			
		}
	}
	
	public static void provideMaterialDamageEffect(int material, float angle, float damage, float x, float y)
	{
		switch(material)
		{
			case PhysicsBox2D.FIXTURE_MATERIAL_WOOD:
				provideWoodDamageEffect(angle, damage,  x, y);
				break;
			case PhysicsBox2D.FIXTURE_MATERIAL_BRONZE:
				provideMetalDamageEffect(HealthScore.POWER_PINK * (PhysicsBox2d.FIXTURE_MATERIAL_BRONZE_BITE_RESISTANCE / PhysicsBox2d.FIXTURE_MATERIAL_MEAT_BITE_RESISTANCE),
						angle, damage, x, y);
				break;
			case PhysicsBox2D.FIXTURE_MATERIAL_STEEL:
				provideMetalDamageEffect(HealthScore.POWER_PINK * (PhysicsBox2d.FIXTURE_MATERIAL_STEEL_BITE_RESISTANCE / PhysicsBox2d.FIXTURE_MATERIAL_MEAT_BITE_RESISTANCE),
						angle, damage, x, y);
				break;
			case PhysicsBox2D.FIXTURE_MATERIAL_ROCK:
				provideRockDamageEffect(angle, damage, x, y);
				break;
			case PhysicsBox2D.FIXTURE_MATERIAL_ICE:
				provideIceDamageEffect(angle, damage, x, y);
				break;
			case PhysicsBox2D.FIXTURE_MATERIAL_ADAMANT:
				provideMetalDamageEffect(HealthScore.POWER_PINK * (PhysicsBox2d.FIXTURE_MATERIAL_ADAMANT_BITE_RESISTANCE / PhysicsBox2d.FIXTURE_MATERIAL_MEAT_BITE_RESISTANCE),
						angle, damage, x, y);
				break;			
		}
	}
	
	static void provideMetalBiteDamageEffect(float metalHealth, float angle, float biteDamage, float x, float y)
	{
		provideMetalDamageEffect(metalHealth, angle, biteDamage, x, y);
		
		angle += 180;
		
		if(angle > 360)
			angle -= 360;
		
		provideMetalDamageEffect(metalHealth, angle, biteDamage, x, y);
	}

	static void provideMetalDamageEffect(float metalHealth, float angle, float damage, float x, float y)
	{
		float scaleKoef = Statics.Render.PARTICLE_EFFECTS_SPARKS_MAX_SCALE * damage / metalHealth;
		scaleKoef = (scaleKoef >= Statics.Render.PARTICLE_EFFECTS_SPARKS_MIN_SCALE ? scaleKoef : Statics.Render.PARTICLE_EFFECTS_SPARKS_MIN_SCALE);
		scaleKoef = (scaleKoef <= Statics.Render.PARTICLE_EFFECTS_SPARKS_MAX_SCALE ? scaleKoef : Statics.Render.PARTICLE_EFFECTS_SPARKS_MAX_SCALE);
		
		PooledEffect effectSparks = AssetsGL.particlesEffectAdditiveSparksPool.obtain();			
		ParticleEmitter emitter = effectSparks.getEmitters().first();
	
		if(angle >= 0 && angle <= 70)
		{
			emitter.getAngle().setHigh(angle);
			emitter.getAngle().setLow(-45, 0);
			emitter.getRotation().setHigh(angle);
			emitter.getRotation().setLow(angle - 45);
			emitter.getRotation().setScaling(new float[]{1, 0});
		}
		else if(angle > 70 && angle <= 90)
		{
			emitter.getAngle().setHigh(angle);
			emitter.getAngle().setLow(-45, 0);
			emitter.getRotation().setHigh(angle - 20);
			emitter.getRotation().setLow(angle - 65);
			emitter.getRotation().setScaling(new float[]{1, 0});
		}
		else if(angle > 90 && angle <= 180)
		{
			emitter.getAngle().setHigh(angle);
			emitter.getAngle().setLow(45, 0);
			emitter.getRotation().setHigh(60);
			emitter.getRotation().setLow(angle + 20);
			emitter.getRotation().setScaling(new float[]{0, 1});
		}
		else if(angle > 180 && angle <= 270)
		{
			emitter.getAngle().setHigh(angle);
			emitter.getAngle().setLow(45, 0);
			emitter.getRotation().setHigh(45);
			emitter.getRotation().setLow(angle + 20);
			emitter.getRotation().setScaling(new float[]{0, 1});
		}
		else if(angle > 270 && angle < 360)
		{
			emitter.getAngle().setHigh(angle);
			emitter.getAngle().setLow(45, 0);
			emitter.getRotation().setHigh(45);
			emitter.getRotation().setLow(angle + 20);
			emitter.getRotation().setScaling(new float[]{1, 0});
		}

		effectSparks.setPosition(x, y);
	    float curScale = emitter.getScale().getHighMax() / Statics.Render.PARTICLE_EFFECTS_SPARKS_ORIGINALSIZE;		    
	    effectSparks.scaleEffect(scaleKoef / curScale);
		AssetsGL.particlesEffectsAdditiveArray.add(effectSparks);
		
	}
	
	static void provideWoodDamageEffect(float angle, float damage, float x, float y)
	{
		float woodHealth = HealthScore.POWER_PINK * (PhysicsBox2d.FIXTURE_MATERIAL_WOOD_BITE_RESISTANCE / PhysicsBox2d.FIXTURE_MATERIAL_MEAT_BITE_RESISTANCE);
		float scaleKoef = Statics.Render.PARTICLE_EFFECTS_WOODEN_FLINDERS_MAX_SCALE * damage / woodHealth;
		scaleKoef = (scaleKoef >= Statics.Render.PARTICLE_EFFECTS_WOODEN_FLINDERS_MIN_SCALE ? scaleKoef : Statics.Render.PARTICLE_EFFECTS_WOODEN_FLINDERS_MIN_SCALE);
		scaleKoef = (scaleKoef <= Statics.Render.PARTICLE_EFFECTS_WOODEN_FLINDERS_MAX_SCALE ? scaleKoef : Statics.Render.PARTICLE_EFFECTS_WOODEN_FLINDERS_MAX_SCALE);

		PooledEffect effect = AssetsGL.particlesEffectNormalWoodenFlindersPool.obtain();			
		ParticleEmitter emitter = effect.getEmitters().first();
		effect.setPosition(x, y);
	    float curScale = emitter.getScale().getHighMax() / Statics.Render.PARTICLE_EFFECTS_WOODEN_FLINDERS_ORIGINALSIZE;		    
	    effect.scaleEffect(scaleKoef / curScale);
		AssetsGL.particlesEffectsNormalArray.add(effect);		
	}
	
	static void provideRockDamageEffect(float angle, float damage, float x, float y)
	{
		float rockHealth = HealthScore.POWER_PINK * (PhysicsBox2d.FIXTURE_MATERIAL_ROCK_BITE_RESISTANCE / PhysicsBox2d.FIXTURE_MATERIAL_MEAT_BITE_RESISTANCE);
		float scaleKoef = Statics.Render.PARTICLE_EFFECTS_ROCK_SHARDS_MAX_SCALE * damage / rockHealth;
		scaleKoef = (scaleKoef >= Statics.Render.PARTICLE_EFFECTS_ROCK_SHARDS_MIN_SCALE ? scaleKoef : Statics.Render.PARTICLE_EFFECTS_ROCK_SHARDS_MIN_SCALE);
		scaleKoef = (scaleKoef <= Statics.Render.PARTICLE_EFFECTS_ROCK_SHARDS_MAX_SCALE ? scaleKoef : Statics.Render.PARTICLE_EFFECTS_ROCK_SHARDS_MAX_SCALE);
		
		PooledEffect effect = AssetsGL.particlesEffectNormalRockShardsPool.obtain();			
		ParticleEmitter emitter = effect.getEmitters().first();
		effect.setPosition(x, y);
	    float curScale = emitter.getScale().getHighMax() / Statics.Render.PARTICLE_EFFECTS_ROCK_SHARDS_ORIGINALSIZE;		    
	    effect.scaleEffect(scaleKoef / curScale);
		AssetsGL.particlesEffectsNormalArray.add(effect);	
	}
	
	static void provideIceDamageEffect(float angle, float damage, float x, float y)
	{
		float iceHealth = HealthScore.POWER_PINK * (PhysicsBox2d.FIXTURE_MATERIAL_ICE_BITE_RESISTANCE / PhysicsBox2d.FIXTURE_MATERIAL_MEAT_BITE_RESISTANCE);
		float scaleKoef = Statics.Render.PARTICLE_EFFECTS_ICE_SHARDS_MAX_SCALE * damage / iceHealth;
		scaleKoef = (scaleKoef >= Statics.Render.PARTICLE_EFFECTS_ICE_SHARDS_MIN_SCALE ? scaleKoef : Statics.Render.PARTICLE_EFFECTS_ICE_SHARDS_MIN_SCALE);
		scaleKoef = (scaleKoef <= Statics.Render.PARTICLE_EFFECTS_ICE_SHARDS_MAX_SCALE ? scaleKoef : Statics.Render.PARTICLE_EFFECTS_ICE_SHARDS_MAX_SCALE);
		
		PooledEffect effect = AssetsGL.particlesEffectNormalIceShardsPool.obtain();			
		ParticleEmitter emitter = effect.getEmitters().first();
		effect.setPosition(x, y);
	    float curScale = emitter.getScale().getHighMax() / Statics.Render.PARTICLE_EFFECTS_ICE_SHARDS_ORIGINALSIZE;		    
	    effect.scaleEffect(scaleKoef / curScale);
		AssetsGL.particlesEffectsNormalArray.add(effect);		
	}
	
	public static void provideWaterBulbEffect(float x, float y)
	{
		PooledEffect effect = AssetsGL.particlesEffectNormalWaterBulbsPool.obtain();			
		effect.setPosition(x + 0.5f, y + 0.5f);
		effect.setDuration(2000);
		AssetsGL.particlesEffectsNormalArray.add(effect);		
	}
	
	public static PooledEffect createHypnoseEffect(float x, float y, float period)
	{
		PooledEffect effect = AssetsGL.particlesEffectAdditiveHypnosePool.obtain();			
		effect.setPosition(x, y);
		effect.setDuration((int)(1000 * period - 2000)); 		//subtract effect duration
		AssetsGL.particlesEffectsAdditiveArray.add(effect);
		return effect;
	}
	
	public static PooledEffect createHypnoseEffect(float x, float y)
	{
		PooledEffect effect = AssetsGL.particlesEffectAdditiveHypnosePool.obtain();			
		effect.setPosition(x, y);
		AssetsGL.particlesEffectsAdditiveArray.add(effect);
		return effect;
	}
	
	public static PooledEffect createFireEffect(float x, float y, float period, float level)
	{
		float scaleKoef = Statics.Render.PARTICLE_EFFECTS_FIRE_START_SCALE
				+ level * Statics.Render.PARTICLE_EFFECTS_FIRE_STEP_SCALE;
		
		PooledEffect effect = AssetsGL.particlesEffectAdditiveFirePool.obtain();			
		effect.setPosition(x, y);
		effect.setDuration((int)(1000 * period - 3000));	 		//subtract effect duration
		ParticleEmitter emitter = effect.getEmitters().first();
	    float curScale = emitter.getScale().getHighMax() / Statics.Render.PARTICLE_EFFECTS_FIRE_ORIGINALSIZE;		    
	    effect.scaleEffect(scaleKoef / curScale);		
		AssetsGL.particlesEffectsAdditiveArray.add(effect);
		return effect;
	}
	
	public static void provideSteamEffect(float level, float x, float y, float period)
	{
		float scaleKoef = Statics.Render.PARTICLE_EFFECTS_STEAM_START_SCALE
				+ level * Statics.Render.PARTICLE_EFFECTS_STEAM_STEP_SCALE;
		
		PooledEffect effect = AssetsGL.particlesEffectAdditiveSteamPool.obtain();			
		effect.setPosition(x, y);
		ParticleEmitter emitter = effect.getEmitters().first();
	    float curScale = emitter.getScale().getHighMax() / Statics.Render.PARTICLE_EFFECTS_STEAM_ORIGINALSIZE;		    
	    effect.scaleEffect(scaleKoef / curScale);	
		AssetsGL.particlesEffectsAdditiveArray.add(effect);	
	}

	
	public static void releaseEffectByValue(PooledEffect effect)
	{	
        effect.free();
        if(effect.getEmitters().get(0).isAdditive())
        	AssetsGL.particlesEffectsAdditiveArray.removeValue(effect, false);
        else
        	AssetsGL.particlesEffectsNormalArray.removeValue(effect, false);
	}
	
}