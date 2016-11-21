package com.example.framework.model;

import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;

public class Factories {
	
	public static final int FACTORY_DYNAMIC_OBJECTS = 1;
	
	public abstract static class Factory {
		abstract DynamicGameObject getDynamicObject (final int id, float x, float y, float width, float height, float angle, int level, WorldKingSnake world);
	}
	
	public static class FactoryDynObjects extends Factory {
		@Override
		public DynamicGameObject getDynamicObject (final int id, float x, float y, float width, float height, float angle, int level, WorldKingSnake world) {
			DynamicGameObject dynObj = null;
			switch (id) {
			case Statics.DynamicGameObject.SEED_HURT:
			case Statics.DynamicGameObject.SEED_SMILE:
			case Statics.DynamicGameObject.SEED_MINER:
			case Statics.DynamicGameObject.SEED_MAD:
			case Statics.DynamicGameObject.SEED_BEAT:
				dynObj = new Seed(x, y, angle, id, level, world);
				break;
			case Statics.DynamicGameObject.TREE_HURT:
				dynObj = new TreeHurt(x, y, angle, level, world);
				break;
			default:
				throw new AssertionError("invalid dynamic object id");
			}
			world.dynObjects.add(dynObj);
			return dynObj;
		}
	}
	
	public static class FactoryBox2DJoints extends Factory {
		
		@Override
		public DynamicGameObject getDynamicObject (final int id, float x, float y, float width, float height, float angle, int level, WorldKingSnake world) {
			throw new RuntimeException("No overrided in this class");
		}
	}
	
	public static class FactoryProducer {
		
		public static Factory getFactory (final int factory) {
			switch (factory) {
			case Factories.FACTORY_DYNAMIC_OBJECTS:
				return new FactoryDynObjects();
			default:
				throw new RuntimeException("invalid switch case");
			}
		}
	}


}
