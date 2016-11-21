package com.example.framework.model;

public class Seed extends DynamicGameObject {
	
	Seed(final float x, final float y, final float angle, final int type, final int level, final WorldKingSnake world) {
    	super(x, y, Statics.Render.SEED_STANDART_SIZE + level * Statics.Render.SEED_LEVEL_STEP_SIZE,
    			Statics.Render.SEED_STANDART_SIZE + level * Statics.Render.SEED_LEVEL_STEP_SIZE,
    			angle, type, level, world);	
    	isCharacter = true;
	}
	
	public void createTree() {
		final int idTree = Seed.getTreeIdBySeed(objType);	
		Factories.FactoryProducer.getFactory(Factories.FACTORY_DYNAMIC_OBJECTS).
		getDynamicObject(idTree, position.x, position.y, 1, 1, 0, stateHS.level, world);		
	}
	
	public static int getTreeIdBySeed(final int idSeed) {
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

}
