package com.example.framework.model;

import com.kingsnake.math.MyMath;

public class Seed extends DynamicGameObject {
	public final static int DEFAULT_LIFETIME_PERIOD = 20;
	public final static int EXCITED_PERIOD = 10;
	public final static int EXCITED_BEATEN_PERIOD = 1;
	public final static int NORMAL_BEATEN_PERIOD = 3;
	public final static float BEAT_PERIOD = 0.15f;
	public final static float BEAT_DELTA_SCALE = 0.1f;
	
	private int lifeTime;
	
	Seed(final float x, final float y, final float angle, final int type, final int level, final WorldKingSnake world) {
    	super(x, y, Statics.Render.SEED_STANDART_SIZE + level * Statics.Render.SEED_LEVEL_STEP_SIZE,
    			Statics.Render.SEED_STANDART_SIZE + level * Statics.Render.SEED_LEVEL_STEP_SIZE,
    			angle, type, level, world);	
    	isCharacter = true;
    	lifeTime = DEFAULT_LIFETIME_PERIOD;
	}
	
	public void setLifeTime (final int lTime) {
		lifeTime = lTime;
	}	
	
	public float getCurScale (final boolean isX) {
		return 1 + MyMath.getTwoStagedHurtBittenScale(actTime, lifeTime, EXCITED_PERIOD, BEAT_PERIOD,
				NORMAL_BEATEN_PERIOD, EXCITED_BEATEN_PERIOD, BEAT_DELTA_SCALE, isX);
	}
	
	public void createTree() {	
		if (stateHS.isDead == HealthScore.ALIVE) {
			Factories.FactoryProducer.getFactory(Factories.FACTORY_DYNAMIC_OBJECTS).
			getDynamicObject(getTreeIdBySeed(objType), position.x, position.y, 1, 1, 0, stateHS.level, world);
			stateHS.isDead = HealthScore.DEAD_DELETED;
		}
	}
	
	public int getTreeIdBySeed(final int idSeed) {
		switch (idSeed) {
		case Statics.DynamicGameObject.SEED_BEAT:
			return Statics.DynamicGameObject.TREE_BEAT;
		case Statics.DynamicGameObject.SEED_HURT:
			return Statics.DynamicGameObject.TREE_HURT;
		case Statics.DynamicGameObject.SEED_MINER:
			return Statics.DynamicGameObject.TREE_BOMB;
		case Statics.DynamicGameObject.SEED_SMILE:
			return Statics.DynamicGameObject.TREE_SMILE;
		case Statics.DynamicGameObject.SEED_MAD:
			return Statics.DynamicGameObject.TREE_HYPNOTIC;
		default:
			throw new AssertionError("invalid seed id");
		}		
	}
	
	public static boolean isSeed(final int idSeed) {
		switch (idSeed) {
		case Statics.DynamicGameObject.SEED_BEAT:
		case Statics.DynamicGameObject.SEED_HURT:
		case Statics.DynamicGameObject.SEED_MINER:
		case Statics.DynamicGameObject.SEED_SMILE:
		case Statics.DynamicGameObject.SEED_MAD:
			return true;
		default:
			return false;
		}		
	}
	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		if (actTime > lifeTime) createTree();			
	}
}
