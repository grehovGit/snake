package com.example.framework.model;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.JointDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;


public class Statics {
	
 	public static final int INDEX_0 = 0;
 	public static final int INDEX_1 = 1;
 	public static final int INDEX_2 = 2;
 	public static final int INDEX_3 = 3;
 	public static final int INDEX_4 = 4;
 	public static final int INDEX_5 = 5;
 	public static final int INDEX_6 = 6;
 	public static final int INDEX_7 = 7;
 	public static final int INDEX_8 = 8;
 	public static final int INDEX_9 = 9;
 	
 	static public class BodyData {
 		
 		private final int partsCount;
 		private final float [][] partsVerts;
 		private final int[] materials;	
 		
 		public BodyData(int pCount, float[][] pVerts, int[] mat)
 		{
 			partsCount = pCount;
 			partsVerts = new float[pCount][];
 			for (int i=0; i < pCount; i++) {
 				partsVerts[i] = new float[pVerts[i].length];
 	 			System.arraycopy(pVerts[i], 0, partsVerts[i], 0, pVerts[i].length); 
 			}
 			materials = new int[pCount];
 			System.arraycopy(mat, 0, materials, 0, pCount); 
 		}
 		
 		public BodyData(BodyData bData) {
 			this(bData.getNParts(), bData.getVerts(), bData.getMaterials());
 		}
 		
 		public int getNParts() { return partsCount;}
 		public float[][] getVerts() { return partsVerts.clone();}
 		public int[] getMaterials() { return materials.clone();}
 	}
	
	static public class WKS{
		
	 	public static final int WORLD_WIDTH = 12;
	    public static final int WORLD_HEIGHT = 20;
	    static final int WORLD_DEPTH = 5;
	    public static final int CELL_DIM = 3;
	    public static final float CELL_SIZE = 1.0f;
	    static float G = 10;
	}
	
	static public class Controls{
		
		public static final float JOYSTICK_TAP_PERIOD = 0.3f;			
		public static final float CONTROL_JUMP_STATE_CHANGE_PERIOD = 0.5f;	
		public static final float CONTROL_IMP_STATE_CHANGE_PERIOD = 0.5f;	
	}
	
	static public class Render{
		public static final float STANDART_SIZE_1 = 1f;
		
		public static final float BLOCK_STANDART_PART_SIZE = 0.5f;		
		public static final float BLOCK_STANDART_PART_OFFSET = 0.25f;
		
		public static final float SEED_STANDART_SIZE = 0.5f;
		public static final float SEED_LEVEL_STEP_SIZE = 0.1f;
		
		public static final float SPECEFFECT_STANDART_ACT_SPEED = 0.07f;
		public static final float SPECEFFECT_SLOW_ACT_SPEED = 0.1f;
		
		public static final float SPECEFFECT_IMPACT_PERIOD = SPECEFFECT_STANDART_ACT_SPEED * 12;
		
		public static final float SNAKE_STANDART_PART_SIZE = 1f;
		public static final float SNAKE_GOLD_FORWARD_ATTACK_CHARGING_ACT_SPEED = 0.15f;	
		public static final float SNAKE_GOLD_ATTACK_ACT_SPEED = 0.03f;	
		public static final float SNAKE_GOLD_STRIKE_SEGMENT_CHANGING_PERIOD = 2 * SNAKE_GOLD_ATTACK_ACT_SPEED;
		public static final float SNAKE_BLUE_STRIKE_PERIOD = SPECEFFECT_STANDART_ACT_SPEED * 4;		
		public static final float SNAKE_DAMAGED_EASY_LEVEL = 0.67f;
		public static final float SNAKE_DAMAGED_HARD_LEVEL = 0.33f;

		public static final int STRIKE_NO = 0;
		public static final int STRIKE_JUMP = 1;
		public static final int STRIKE_IMPULSE = 2;
		public static final int STRIKE_HYPNOSE = 3;
		public static final int STRIKE_ICE = 4;
		public static final int STRIKE_FIRE = 5;
		
		//object render sizes
		public static final float SNAKE_WOOD_ARMOR_WIDTH = 1;
		public static final float SNAKE_WOOD_ARMOR_HEIGHT = 0.5f;
				
		//GUI
		public static final float JOYSTICK_POS_X = 2;
		public static final float JOYSTICK_POS_Y = 1.5f;
		public static final float JOYSTICK_SIZE = 2;
		public static final float JOYSTICK_LEFT_ARROW_OFFSET_X = -0.94f;
		public static final float JOYSTICK_RIGHT_ARROW_OFFSET_X = 0.94f;
		public static final float JOYSTICK_HORISONT_ARROW_OFFSET_Y = 0;			
		public static final float JOYSTICK_UP_ARROW_OFFSET_Y = 0.96f;
		public static final float JOYSTICK_DOWN_ARROW_OFFSET_Y = -0.94f;
		public static final float JOYSTICK_VERT_ARROW_OFFSET_X = 0.03f;			
		public static final float JOYSTICK_PERIOD = 19 * SPECEFFECT_SLOW_ACT_SPEED;
		
		public static final float CONTROL_JUMPS_POS_X = 6;
		public static final float CONTROL_JUMPS_POS_Y = 1.5f;
		public static final float CONTROL_JUMPS_SIZE = 1.8f;
		public static final float CONTROL_JUMPS_SKILLS_OFFSET = 1.5f;
		public static final float CONTROL_JUMPS_SKILLS_SIZE = 1;
		
		public static final float CONTROL_IMPULSE_POS_X = 8;
		public static final float CONTROL_IMPULSE_POS_Y = 1.5f;
		public static final float CONTROL_IMPULSE_SIZE = 1.8f;
		public static final float CONTROL_IMPULSE_SKILLS_OFFSET = 1.5f;
		public static final float CONTROL_IMPULSE_SKILLS_SIZE = 1;	
		
		public static final float PARTICLE_EFFECTS_SPARKS_MAX_SCALE = 0.03f;
		public static final float PARTICLE_EFFECTS_SPARKS_MIN_SCALE = 0.01f;
		public static final float PARTICLE_EFFECTS_SPARKS_ORIGINALSIZE = 20f;
		
		public static final float PARTICLE_EFFECTS_WOODEN_FLINDERS_MAX_SCALE = 0.05f;
		public static final float PARTICLE_EFFECTS_WOODEN_FLINDERS_MIN_SCALE = 0.025f;
		public static final float PARTICLE_EFFECTS_WOODEN_FLINDERS_ORIGINALSIZE = 10f;
		
		public static final float PARTICLE_EFFECTS_ROCK_SHARDS_MAX_SCALE = 0.05f;
		public static final float PARTICLE_EFFECTS_ROCK_SHARDS_MIN_SCALE = 0.025f;
		public static final float PARTICLE_EFFECTS_ROCK_SHARDS_ORIGINALSIZE = 10f;
		
		public static final float PARTICLE_EFFECTS_ICE_SHARDS_MAX_SCALE = 0.05f;
		public static final float PARTICLE_EFFECTS_ICE_SHARDS_MIN_SCALE = 0.025f;
		public static final float PARTICLE_EFFECTS_ICE_SHARDS_ORIGINALSIZE = 10f;
		
		public static final float PARTICLE_EFFECTS_FIRE_START_SCALE = 0.005f;
		public static final float PARTICLE_EFFECTS_FIRE_STEP_SCALE = 0.0025f;
		public static final float PARTICLE_EFFECTS_FIRE_ORIGINALSIZE = 20f;
		
		public static final float PARTICLE_EFFECTS_STEAM_START_SCALE = 0.005f;
		public static final float PARTICLE_EFFECTS_STEAM_STEP_SCALE = 0.0025f;
		public static final float PARTICLE_EFFECTS_STEAM_ORIGINALSIZE = 20f;
		
		public static final float PARTICLE_EFFECTS_WATER_BULBS_SCALE = 0.025f;
		
		public static Vector2 getObjectRenderSizes(int type)
		{
			Vector2 sizes = new Vector2();
			
			switch(type)
			{
				case Statics.DynamicGameObject.SNAKE_WOOD_ARMOR:
					sizes.x = SNAKE_WOOD_ARMOR_WIDTH;	
					sizes.y = SNAKE_WOOD_ARMOR_HEIGHT;	
					break;
			}
			
			return sizes;
		}
	}
	
	static public class GameObject{
		
		 public static final int APPLE = 1001;	 
		 public static final int MUSHROOM_BLUE = 1002;	//gifts	 
		 public static final int MUSHROOM_YELLOW = 1003;	 
		 public static final int MUSHROOM_BROWN = 1004;	
		 public static final int MUSHROOM_BLUE_RED = 1005;
	}
	
	static public class Healthscore{
		
		public static float FATAL_DAMAGE = 1000000;
	}
	
	static public class DynamicGameObject{
		
		public static final int DEAD_PART_VISIBLE_STILL = -1;
		public static final int SNAKE = 1;
		public static final int LEMMING = 2;
		public static final int HEDGEHOG = 3;
		public static final int FROG = 4;
		public static final int FISH = 5;

		//blocks
		public static final int BLOCK_WOOD = 100;
		public static final int BLOCK_MARBLE = 101;
		
		//breakable objects
		public static final int FSKILL_ICEBALL = 131;
		///armor
		public static final int SNAKE_WOOD_ARMOR = 180;
		
		//weapon
		public static final int SWORD_VIKING_BRONZE_WOODEN = 200;
		
		public static final int AXE_VIKING_MONOSIDE_BRONZE_WOODEN = 231;
		public static final int AXE_VIKING_DOUBLESIDE_BRONZE_WOODEN = 232;
		public static final int SPEAR_VIKING_BRONZE_WOODEN = 251;
		
		public static final int HELM_VIKING_WOODEN = 301;		
		public static final int SHIELD_VIKING_WOODEN = 281;
		
		//trees
		public static final int BOMB_FROM_TREE = 400;
		public static final int TREE_HYPNOTIC = 410;
		public static final int TREE_SMILE = 420;
		public static final int TREE_BEAT = 415;
		
		public static final int SEED_SMILE = 141;		
		public static final int SEED_HURT = 142;	
		public static final int SEED_MINER = 143;
		public static final int SEED_MAD = 144;
		public static final int SEED_BEAT = 145;
		
		public static final int TREE_HURT = 146;
		public static final int TREE_HURT_ORDYNARY_SEGMENT = 147;

		public static final int BEGIN_DYN_OBJS_RANGE = 1;
		public static final int BEGIN_OF_CHARACTERS_RANGE = 1;
		public static final int END_OF_CHARACTERS_RANGE = 99;
		
		public static final int BEGIN_OF_PASSIVE_DYN_OBJS_RANGE = 100;
		public static final int BEGIN_OF_PASSIVE_DYN_OBJS_BREAKABLE_ATONCE_RANGE = 100;	
		public static final int BEGIN_OF_COMPOUND_BODIES_RANGE = 100;	
		public static final int BEGIN_OF_BLOCKS_RANGE = 100;
		public static final int BEGIN_OF_BLOCKS_STANDART_RANGE = 100;
		public static final int END_OF_BLOCKS_STANDART_RANGE = 110;
		public static final int END_OF_BLOCKS_RANGE = 130;	
		public static final int BEGIN_OF_BREAKABLE_CIRCLE_RANGE = 131;
		public static final int END_OF_BREAKABLE_CIRCLE_RANGE = 140;
		public static final int BEGIN_OF_WEAPON_RANGE = 180;
		public static final int BEGIN_OF_ARMOR_RANGE = 180;
		public static final int END_OF_ARMOR_RANGE = 199;
		public static final int BEGIN_OF_SWORDS_RANGE = 200;
		public static final int END_OF_SWORDS_RANGE = 230;
		public static final int BEGIN_OF_AXE_RANGE = 231;
		public static final int END_OF_AXE_RANGE = 250;		
		public static final int BEGIN_OF_SPEARS_RANGE = 251;
		public static final int END_OF_SPEARS_RANGE = 280;			
		public static final int END_OF_PASSIVE_DYN_OBJS_BREAKABLE_ATONCE_RANGE = 280;	
		public static final int BEGIN_OF_PASSIVE_DYN_OBJS_SEPARATABLE_RANGE = 281;		
		public static final int BEGIN_OF_SHIELD_RANGE = 281;
		public static final int END_OF_SHIELD_RANGE = 300;
		public static final int BEGIN_OF_HELM_RANGE = 301;
		public static final int END_OF_HELM_RANGE = 320;	
		
		public static final int END_OF_PASSIVE_DYN_OBJS_SEPARATABLE_RANGE = 350;
		public static final int END_OF_COMPOUND_BODIES_RANGE = 350;	
		public static final int END_OF_PASSIVE_DYN_OBJS_RANGE = 350;
		public static final int BEGIN_OF_ACTIVE_DYN_OBJS_RANGE = 351;
		public static final int BEGIN_OF_BOMBS_RANGE = 380;
		public static final int END_OF_BOMBS_RANGE = 400;
		public static final int END_OF_WEAPON_RANGE = 400;
		public static final int END_OF_ACTIVE_DYN_OBJS_RANGE = 400;
		public static final int END_DYN_OBJS_RANGE = 400;
		
		//not in dynamic objects level !!!
		public static final int BOMB_FALL_ACTION = 510;
		public static final int TREE_BOMB = 520;
		
		//dynamic Effects 
		public static final int ATTACK_UP_EFFECT = 2010;
		public static final int ATTACK_DOWN_EFFECT = 2020;
		public static final int DEFENSE_UP_EFFECT = 2030;
		public static final int DEFENSE_DOWN_EFFECT = 2040;
		public static final int HEALTH_UP_EFFECT = 2050;
		public static final int HEALTH_DOWN_EFFECT = 2060;
		public static final int SPEED_UP_EFFECT = 2070;
		public static final int SPEED_DOWN_EFFECT = 2080;
		public static final int GET_COINS_EFFECT = 2090;
		public static final int LOOSE_COINS_EFFECT = 2100;
		 
		public static final int FSKILL_FORWARD_ATTACK_IMPULSE_EFFECT = 2200;
		public static final int FSKILL_FORWARD_ATTACK_ICE_EFFECT = 2210;
		public static final int FSKILL_FORWARD_ATTACK_HYPNOSE_EFFECT = 2220;
		public static final int FSKILL_FORWARD_ATTACK_FIRE_EFFECT = 2230;
	
		public static final int BEGIN_DYNAMIC_EEFFECTS_RANGE = 2000;		
		public static final int BEGIN_DYNAMIC_EEFFECTS_WITH_SENSOR_RANGE = 2200;
		public static final int BEGIN_DYNAMIC_EEFFECTS_FSKILL_FORWARD_ATTACK = 2200;
		public static final int END_DYNAMIC_EEFFECTS_FSKILL_FORWARD_ATTACK = 2250;
		public static final int END_DYNAMIC_EEFFECTS_WITH_SENSOR_RANGE = 2300;	
		public static final int END_DYNAMIC_EEFFECTS_RANGE = 2500;
		
		//snake
		public static final float TO_MIDDLE_OF_CELL_VELOCITY_KOEFF_SNAKE_PINK = 2f;	
		public static final float BEFORE_TURN_VELOCITY_KOEFF_SNAKE_PINK = 2f;	
		public static final float APPLY_FORCE_CHAR_MOVING_KOEFF_SNAKE_PINK = 2f;
		
		public static final float TO_MIDDLE_OF_CELL_VELOCITY_KOEFF_SNAKE_GREEN = 2f;	
		public static final float BEFORE_TURN_VELOCITY_KOEFF_SNAKE_GREEN = 2f;	
		
		public static final int SNAKE_HEAD_INDEX = 0;
		public static final int SNAKE_FIRST_PART_INDEX = 1;
		public static final int SNAKE_SECOND_PART_INDEX = 2;
		public static final int SNAKE_OTHER_PARTS_INDEX = 3;
		
		public static final int SNAKE_PINK_SIZE = 4;
		
		public static final float SWALLOW_PERIOD = 0.6f;
		public static final float SWALLOW_BODY_INCREASE = 0.4f;
		
		
		//abstract objects
		public static final int BEGIN_ABSTRACT_RANGE = 10000;
		public static final int END_ABSTRACT_RANGE = 11000;
		
		public static final int ABSTRACT_TYPE_FRIEND = 10000;
		public static final int ABSTRACT_TYPE_FRIEND_TO_PROTECT = 10001;
		public static final int ABSTRACT_TYPE_ENEMY = 10010;
		public static final int ABSTRACT_TYPE_ENEMY_TARGET = 10011;
		public static final int ABSTRACT_TYPE_PROTECTED_PLACE = 10020;
		public static final int ABSTRACT_TYPE_WEAPON_HELM = 10100;
		public static final int ABSTRACT_TYPE_WEAPON_SHIELD = 10110;
		public static final int ABSTRACT_TYPE_WEAPON_SWORD = 10120;
		public static final int ABSTRACT_TYPE_WEAPON_AXE = 10130;
		public static final int ABSTRACT_TYPE_WEAPON_SPEAR = 10140;	
		public static final int ABSTRACT_TYPE_WEAPON_ARMOR = 10150;
		public static final int ABSTRACT_TYPE_OBSTACLE = 10200;	
		public static final int ABSTRACT_TYPE_OBSTACLE_INSUPERABLE = 10210;	
		
		public static boolean isWeapon(int type)
		{
			if(type >= BEGIN_OF_WEAPON_RANGE && type <= END_OF_WEAPON_RANGE)
				return true;
			else
				return false;
		}
		
		public static boolean isCompoundBody(int type)
		{
			if(type >= BEGIN_OF_COMPOUND_BODIES_RANGE && type <= END_OF_COMPOUND_BODIES_RANGE)
				return true;
			else
				return false;
		}
		
		public static int getWeaponClass(int type)
		{
			if(type >= BEGIN_OF_ARMOR_RANGE && type <= END_OF_ARMOR_RANGE)
				return ABSTRACT_TYPE_WEAPON_ARMOR;
			else if(type >= BEGIN_OF_HELM_RANGE && type <= END_OF_HELM_RANGE)
				return ABSTRACT_TYPE_WEAPON_HELM;
			else if(type >= BEGIN_OF_SHIELD_RANGE && type <= END_OF_SHIELD_RANGE)
				return ABSTRACT_TYPE_WEAPON_SHIELD;
			else if(type >= BEGIN_OF_SWORDS_RANGE && type <= END_OF_SWORDS_RANGE)
				return ABSTRACT_TYPE_WEAPON_SWORD;
			else if(type >= BEGIN_OF_AXE_RANGE && type <= END_OF_AXE_RANGE)
				return ABSTRACT_TYPE_WEAPON_AXE;
			else if(type >= BEGIN_OF_SPEARS_RANGE && type <= END_OF_SPEARS_RANGE)
				return ABSTRACT_TYPE_WEAPON_SPEAR;
			else
				return -1;
		}
		
		public static boolean isDynamicEffect(int type)
		{
			if(type >= BEGIN_DYNAMIC_EEFFECTS_RANGE && type <= END_DYNAMIC_EEFFECTS_RANGE)
				return true;
			else
				return false;
		}
		
		public static boolean isDynamicEffectWithSensor(int type)
		{
			if(type >= BEGIN_DYNAMIC_EEFFECTS_WITH_SENSOR_RANGE && type <= END_DYNAMIC_EEFFECTS_WITH_SENSOR_RANGE)
				return true;
			else
				return false;
		}
		
		public static boolean isDynamicEffectFskillForwardAttck(int type)
		{
			if(type >= BEGIN_DYNAMIC_EEFFECTS_FSKILL_FORWARD_ATTACK && type <= END_DYNAMIC_EEFFECTS_FSKILL_FORWARD_ATTACK)
				return true;
			else
				return false;
		}
		
		public static BodyData getBodyData(final int id) {
			switch (id) {
				case FSKILL_ICEBALL:
					return  PhysicsBox2D.FSKILL_ICEBALL_BODY;
				case SEED_SMILE:
				case SEED_HURT:	
				case SEED_MINER:
				case SEED_MAD:
				case SEED_BEAT:
					return  new BodyData(PhysicsBox2D.SEEDBODY);
				case TREE_HURT:
					return  new BodyData(PhysicsBox2D.TREEHURT_BODY);
				case TREE_HURT_ORDYNARY_SEGMENT:
					return  new BodyData(PhysicsBox2D.TREEHURT_ORDYNARY_SEGMENT_BODY);
				default:
					throw new AssertionError("invalid body id");
			}
		}
	}
	
	static public class Character{
		
	}
	
	static public class Static{
		
		//gifts
		 public static final int BEGIN_GIFTS_DIAPASON  = 1001;
		 public static final int END_GIFTS_DIAPASON  = 1009;
		 public static final int BEGIN_MUSHROOM_DIAPASON  = 1002;
		 public static final int END_MUSHROOM_DIAPASON  = 1009;	
		 
		 //relief
		public static final float WATER_DAMAGE = 5f;
	}
	
	static public class StaticEffect{
		
		 //time limited effects
		 public static final int BLOOD_SPLATTER_SMALL = 1030;
		 public static final int BLOOD_SPLATTER_MEDIUM = 1040;
		 public static final int BLOOD_SPLATTER_BIG = 1050;
		 
		 public static final int IMPACT = 1060;	 
		 public static final int IMPACT_STARS = 1070;
		 
		 public static final int HYPNOSE_EFFECT = 1075;	
		 public static final float HYPNOSEACTION_SIZE = 1;	
		 public static final float HYPNOSEACTION_START_PERIOD = 3;
		 public static final float HYPNOSEACTION_STEP_PERIOD = 3;
		 
		 public static final int STEAM_EFFECT = 1076;
		 public static final float STEAM_EFFECT_DEFAULT_PERIOD = 1;	
		 
		 public static final int FIRE_EFFECT = 1077;
		 
		 public static final int BOMB_FROM_TREE_EXPLOSION = 1080;
		 
		 public static final int TIME_LIMITED_EFFECTS_RANGE_END = 1200;
		 
		 //time looped effects
		 public static final int WATER_EFFECT = 1210;
		 public static final float WATER_ACT_SPEED = 0.2f;
		 
		 public static final int TIME_LOOPED_EFFECTS_RANGE_END = 1500;
	}
	
	static public class PhysicsBox2D{
		
		public static final short FILTER_GROUP_CHAR_START_RANGE = -1;
		
		public static final int MAX_BODY_PARTS_COUNT = 5;
		
		public static final short FIXTURE_MATERIAL_MEAT = 1;
		public static final short FIXTURE_MATERIAL_WOOD = 2;
		public static final short FIXTURE_MATERIAL_ROCK = 3;
		public static final short FIXTURE_MATERIAL_BRONZE = 4;
		public static final short FIXTURE_MATERIAL_STEEL = 5;
		public static final short FIXTURE_MATERIAL_ADAMANT = 6;
		public static final short FIXTURE_MATERIAL_ICE = 7;
		
		public static final float BORDER_WIDTH = 0.1f;		
		public static final float DEAD_PART_ALIFIE_BEFORE_DESTROYING_PERIOD = 2f;
	
		public static final float IMPULSE_MIN_ACCOUNTED = 0.5f;
		public static final float IMPULSE_DAMGAGE_KOEF = 0.3f;	

		public static final float ALL_FORCES_CHAR_MOVING_GENERAL_TUNING_KOEFF = 2f;
		public static final float APPLY_MAIN_FORCE_CHAR_MOVING_KOEFF_STEP = 1f;
		public static final float CORRECT_POWER_TO_MAIN_POWER_KOEF = 0.5f;
		public static final float JUMP_FORWARD_IMPULSE_TO_MAIN_FORCE_KOEF = 4f;	
		public static final float JUMP_FORWARD_FORCE_TO_MAIN_FORCE_KOEF = 4f;
		public static final float JUMP_FORWARD_HOOK_IMPULSE_TO_MAIN_FORCE_KOEF = 0.5f;
		public static final float JUMP_FORWARD_HOOK_FORCE_TO_MAIN_FORCE_KOEF = 5f;	
		public static final float JUMP_FORWARD_FORCE = 2f;	
		public static final int CORRECT_POWER_NO = 0;
		public static final int CORRECT_POWER_UP = 1;	
		public static final int CORRECT_POWER_DOWN = -1;	
		public static final int CORRECT_POWER_LIFETIME = 2;			
		
		public static final float SENSOR_FIGHT_SKILL_FORWARD_RADIUS = 2f;
		public static final float SENSOR_FIGHT_SKILL_SIDE_RADIUS = 1f;
		public static final float SENSOR_FIGHT_SKILL_BACK_RADIUS = 1f;
		public static final float SENSOR_FIGHT_SKILL_FORWARD_RAY_RADIUS = 10f;
		
		public static final float SENSOR_FIGHT_SKILL_TREEHURT_RADIUS = 2f;
		
		public static final float SENSOR_FIGHT_SKILL_FORWARD_CENTER_POS_X = 2.3f;
		public static final float SENSOR_FIGHT_SKILL_FORWARD_CENTER_POS_Y = 0;
		public static final float SENSOR_FIGHT_SKILL_LEFT_CENTER_POS_X = 0f;
		public static final float SENSOR_FIGHT_SKILL_LEFT_CENTER_POS_Y = 1;
		public static final float SENSOR_FIGHT_SKILL_RIGHT_CENTER_POS_X = 0;
		public static final float SENSOR_FIGHT_SKILL_RIGHT_CENTER_POS_Y = -1f;
		public static final float SENSOR_FIGHT_SKILL_BACK_CENTER_POS_X = -1f;
		public static final float SENSOR_FIGHT_SKILL_BACK_CENTER_POS_Y = 0;
		
		public static final float SENSOR_TONGUE_DELAY_AFTER_RELEASE = 2f;
		
		public static final float CHAR_LINEAR_DAMPING = 6f;
		public static final float CHAR_ANGUALR_DAMPING = 8f;
		public static final float CHAR_LINEAR_DAMPING_JUMP = 8f;
		public static final float CHAR_ANGUALR_DAMPING_JUMP = 8f;
		public static final float DYNOBJ_LINEAR_DAMPING = 4f;
		public static final float DYNOBJ_ANGUALR_DAMPING = 4f;		
		public static final float WEAPON_FLY_LINEAR_DAMPING = 0.5f;
		public static final float WEAPON_FLY_ANGULAR_DAMPING = 0.5f;
		
		//joints
		public static final float JOINT_FORCE_BASIC = 6f; // 4 (pink snake move force) * 1.5
		public static final float JOINT_FORCE_HOLD_BASIC = JOINT_FORCE_BASIC / 2;
		
		//penetration
		public static final float PENETRATION_WIDTH_FANTASTIC_SHARP = 0.5f;
		public static final float PENETRATION_WIDTH_VERY_SHARP = 1;
		public static final float PENETRATION_WIDTH_SHARP = 2;
		public static final float PENETRATION_WIDTH_NOT_VERY_SHARP = 4;
		public static final float PENETRATION_WIDTH_NOT_SHARP = 8;
		public static final float PENETRATION_WIDTH_BLUNT = 16;
		public static final float PENETRATION_WIDTH_VERY_BLUNT = 32;
		public static final float PENETRATION_MIN_IMPULSE_FILTER = 3f;
		public static final float PENETRATION_MIN_CONTACT_WIDTH = 0.04f;
		public static final float PENETRATION_FIRST_POINT_KOEF = 21f;	
		public static final float PENETRATION_TWO_POINTS_KOEF = 425f;	
		public static final float PENETRATION_FRICTION_KOEF = 150f;
		public static final float PENETRATION_FIRST_CONTACT_DATA_ALIVE_PERIOD = 0.5f;	
		public static final float PENETRATION_MINIMAL_CONTACT_SEGMENT_LENGTH = 0.05f;
		public static final float PENTETRATION_IMPULSE_DAMGAGE_KOEF = 0.45f;
		
		//effects actions
		public static final float EFFECTS_ACTIONS_UPDATE_FREQUENCY = 1f;
		
		//Dynamic Effects
		public static final float SENSOR_FIGHT_SKILL_FORWARD_ATTACK_RADIUS = 0.1f;
		public static final float SENSOR_FIGHT_SKILL_FORWARD_HYPNOSE_ATTACK_RADIUS = 0.5f;
		public static final float SENSOR_FIGHT_SKILL_FORWARD_FIRE_ATTACK_RADIUS = 0.5f;
		
		//snake		
		public static final float[] SNAKE_FULLHEAD = {-0.375f, 0.359f, -0.469f, 0.109f, -0.469f, -0.109f, 
			-0.344f, -0.344f, -0.016f, -0.344f, 0.484f, -0.031f, 0.484f, 0.094f, -0.125f, 0.359f};
		
		public static final float[] SNAKE_HEAD = {-0.375f, 0.359f, -0.469f, 0.109f, -0.469f, -0.109f, 
			-0.344f, -0.344f, -0.016f, -0.344f, 0.328f, -0.156f, 0.328f, 0.203f, -0.125f, 0.359f};
		public static final float[] SNAKE_MOUTH = {0.328f, -0.156f, 0.484f, -0.031f, 0.484f, 0.094f, 0.328f, 0.203f};
		public static final float[] SNAKE_PART = {-.5f, -.39f, 0f, -.188f, 0f, -.313f, -.078f, -.328f};


		//compound bodies
		public static final int  BODY_FIRST_PART_INDEX = 0;
		public static final int  BODY_SECOND_PART_INDEX = 1;
		public static final int  BODY_THIRD_PART_INDEX = 2;
		
		final public static BodyData FSKILL_ICEBALL_BODY = new BodyData ( 3, 
		new float[][]{{-.45f, -.17f, -.41f, -.25f, -.13f, -.45f, .2f, -.45f, 0, 0},
					  {-.45f, .17f, 0, 0, .09f, .48f},
					  {0, 0, .2f, -.45f, .42f, -.22f, .45f, .23f, .09f, .48f}}, 
		new int[] {FIXTURE_MATERIAL_ICE,
				   FIXTURE_MATERIAL_ICE,
				   FIXTURE_MATERIAL_ICE,});

		final public static BodyData SEEDBODY = new BodyData (1, 
		new float[][]{{0, .48f, -.47f, .11f, -.47f, -.17f, -.13f, -.45f, .13f, -.45f, .47f, -.17f, .45f, .13f}}, 
		new int[] {FIXTURE_MATERIAL_WOOD});	
		
		public final static int TREEHURT_ORDYNARY_SEGMENT_COUNT = 4;
		public final static float TREEHURT_FIRST_SEGMENT_OFFSET = 0.5f;
		public final static float TREEHURT_STEP_SEGMENT_OFFSET = 0.7f;
		public final static float TREEHURT_SEGMENT_ANCHOR_OFFSET = -0.3f;
				
		final static float TREEHURT_CLAW_X_OFFSET = 0.3f;
		final public static BodyData TREEHURT_ORDYNARY_SEGMENT_BODY = new BodyData (3, 
		new float[][]{{-.3f, 0, .08f, -.08f, .25f, .03f, .08f, .09f},
					  {-.11f + TREEHURT_CLAW_X_OFFSET, .13f, -.11f + TREEHURT_CLAW_X_OFFSET, -.08f, .08f + TREEHURT_CLAW_X_OFFSET, -.05f, .08f + TREEHURT_CLAW_X_OFFSET, .08f},
					  {.08f + TREEHURT_CLAW_X_OFFSET, .08f, .08f + TREEHURT_CLAW_X_OFFSET, -.05f, .17f + TREEHURT_CLAW_X_OFFSET, -.19f, .17f + TREEHURT_CLAW_X_OFFSET, 0}}, 
		new int[] {FIXTURE_MATERIAL_ROCK, FIXTURE_MATERIAL_BRONZE, FIXTURE_MATERIAL_BRONZE});	
		
		final public static BodyData TREEHURT_BODY = new BodyData (1, 
		new float[][]{{0, -.3f, -.65f, -.49f, 0, -.74f, .78f, -.49f}}, 
		new int[] {FIXTURE_MATERIAL_WOOD});	
		final public static int TREEHURT_BODY_MASS = 1000;
		
		//Armor fixtures
		public static final int SNAKE_ARMOR_MAX_PARTS_COUNT = 3;
		
		public static final float[] SNAKE_WOOD_ARMOR_VERTS_FIRST_PART = {-.5f, -.39f, 0f, -.188f, 0f, -.313f, -.078f, -.328f};
		public static final float[] SNAKE_WOOD_ARMOR_VERTS_SECOND_PART = {0f, -.188f, .438f, -.047f, .453f, -.094f, .2f, -.234f, 0f, -.313f};
		
		public static final int SNAKE_WOOD_ARMOR_PARTS_COUNT = 2;	
		public static final int SNAKE_WOOD_ARMOR_LEFT_FIXTURE_START_INDEX = 0;	
		public static final int SNAKE_WOOD_ARMOR_RIGHT_FIXTURE_START_INDEX = 3;	
		
		//weapon
		public static final float WEAPON_HOLD_ARM_ETALON_MASS = 0.4f;
		public static final float WEAPON_HOLD_ARM_AXIS_FORCE_DEFAULT = 1f;
		
		public static final float WEAPON_HOLD_ARM_IMPULSE_START_SWORD = 1f;
		public static final float WEAPON_HOLD_ARM_IMPULSE_STEP_SWORD = 0.5f;
		public static final float WEAPON_HOLD_ARM_FREQUENCY_KOEF_START_SWORD = 2f;
		public static final float WEAPON_HOLD_ARM_FREQUENCY_KOEF_STEP_SWORD = 1f;
		public static final float WEAPON_HOLD_ARM_AXIS_FORCE_SWORD = 1f;
		public static final float WEAPON_HOLD_ARM_AXIS_FORCE_STEP_SWORD = 0.2f;
		
		public static final float WEAPON_HOLD_ARM_IMPULSE_START_AXE = 1f;
		public static final float WEAPON_HOLD_ARM_IMPULSE_STEP_AXE = 0.5f;
		public static final float WEAPON_HOLD_ARM_FREQUENCY_KOEF_START_AXE = 2f;
		public static final float WEAPON_HOLD_ARM_FREQUENCY_KOEF_STEP_AXE = 1f;
		public static final float WEAPON_HOLD_ARM_AXIS_FORCE_AXE = 1f;
		public static final float WEAPON_HOLD_ARM_AXIS_FORCE_STEP_AXE = 0.3f;
		
		public static final float WEAPON_HOLD_ARM_IMPULSE_START_SPEAR = 1f;
		public static final float WEAPON_HOLD_ARM_IMPULSE_STEP_SPEAR = 0.5f;
		public static final float WEAPON_HOLD_ARM_FREQUENCY_KOEF_START_SPEAR = 2f;
		public static final float WEAPON_HOLD_ARM_FREQUENCY_KOEF_STEP_SPEAR = 1f;
		public static final float WEAPON_HOLD_ARM_AXIS_FORCE_SPEAR = 1f;
		public static final float WEAPON_HOLD_ARM_AXIS_FORCE_STEP_SPEAR = 0.5f;
		
		public static final float WEAPON_ARM_THROW_IMPULSE_START = 5f;
		public static final float WEAPON_ARM_THROW_IMPULSE_STEP = 2f;
		public static final float PENETRATION_EMPIRICAL_KOEF = 1f;
		
		public static final float WEAPON_SHIELD_THROW_IMPULSE_START = 10f;
		public static final float WEAPON_SHIELD_THROW_IMPULSE_STEP = 2f; 
		

		//Helms		
		public static final int[] WEAPON_HELM_VIKING_WOOD_MATERIALS = {FIXTURE_MATERIAL_WOOD , FIXTURE_MATERIAL_WOOD};
		public static final float[] WEAPON_HELM_VIKING_WOOD_VERTS_FIRST_PART = {-.297f, -.125f, -.219f, .125f, 0f, .297f, 0, -.156f};
		public static final float[] WEAPON_HELM_VIKING_WOOD_VERTS_SECOND_PART = { 0, -.156f, 0f, .297f, .203f, .125f, .297f, -.125f };
		
		//shields	
		public static final float WEAPON_SHIELD_HOLD_JOINT_FREQ = 10;
		public static final float WEAPON_SHIELD_HOLD_JOINT_DAMP_RATIO = 10; 
		public static final float WEAPON_SHIELD_HOLD_JOINT_OFFSET = 0.1f; 
		public static final float WEAPON_SHIELD_HOLD_JOINT_LENGTH = 0.4f; 
		public static final float WEAPON_SHIELD_HOLD_JOINT_LENGTH_HALF = 0.2f; 
		public static final float WEAPON_SHIELD_HOLD_JOINT_LENGTH_SHORT = 0.05f; 
		public static final float WEAPON_SHIELD_HOLD_JOINT_LENGTH_LONG = 0.7f; 
		
		public static final int WEAPON_SHIELD_HOLD_JOINT_LEFT_INDEX = 0; 
		public static final int WEAPON_SHIELD_HOLD_JOINT_RIGHT_INDEX = 1; 
		public static final int WEAPON_SHIELD_HOLD_JOINT_UP_INDEX = 2; 
		
		public static final int[] WEAPON_SHIELD_VIKING_WOOD_MATERIALS = {FIXTURE_MATERIAL_WOOD , FIXTURE_MATERIAL_WOOD, FIXTURE_MATERIAL_WOOD};
		public static final float[] WEAPON_SHIELD_VIKING_WOOD_VERTS_PART1 = {  -.406f, -.313f, -.453f, -.109f, -.438f, .109f, -.375f, .25f, -.17f, .438f, -.109f, .313f, -.188f, .156f, -.078f, .047f, -.109f, -.016f};
		public static final float[] WEAPON_SHIELD_VIKING_WOOD_VERTS_PART2 = {  -.031f, -.047f, -.188f, .156f, -.094f, .328f, -.156f, .438f, -.016f, .484f, .203f, .484f, .438f, .313f, .453f, .172f, .281f, -.109f};
		public static final float[] WEAPON_SHIELD_VIKING_WOOD_VERTS_PART3 = {  -.391f, -.313f, -.234f, -.031f, .281f, -.125f, .469f, .172f, .469f, -.063f, .328f, -.313f, .203f, -.422f, .031f, -.484f, -.172f,-.484f};		
		
		//swords
		public static final int[] WEAPON_SWORD_VIKING_BRONZE_WOOD_MATERIALS = {FIXTURE_MATERIAL_WOOD , FIXTURE_MATERIAL_BRONZE};
		public static final float[] WEAPON_SWORD_VIKING_WOODBRONZE_VERTS_PART1 = {-.047f, -.5f, -.156f, -.109f, -.047f, 0, .047f, 0, .156f, -.109f, .047f, -.5f};
		public static final float[] WEAPON_SWORD_VIKING_WOODBRONZE_VERTS_PART2 = {-.047f, 0, -.047f, .39f, 0, 0.5f, .047f, .39f, .047f, 0};
		
		
		//axes
		public static final int[] WEAPON_AXE_MONOSIDE_VIKING_BRONZE_WOOD_MATERIALS = {FIXTURE_MATERIAL_WOOD , FIXTURE_MATERIAL_BRONZE};
		public static final float[] WEAPON_AXE_MONOSIDE_VIKING_WOODBRONZE_VERTS_PART1 = {-.156f, -.484f, -.094f, -.484f, .156f, .375f, .094f, .422f};
		public static final float[] WEAPON_AXE_MONOSIDE_VIKING_WOODBRONZE_VERTS_PART2 = {-.125f, .125f, -.047f, .125f, .031f, .25f, .063f, .203f, .047f, .438f, -.031f, .5f, -.125f, .328f};
		
		public static final int[] WEAPON_AXE_DOUBLESIDE_VIKING_BRONZE_WOOD_MATERIALS = {FIXTURE_MATERIAL_WOOD , FIXTURE_MATERIAL_BRONZE};
		public static final float[] WEAPON_AXE_DOUBLESIDE_VIKING_WOODBRONZE_VERTS_PART1 = {-.14f, -.469f, -.078f, -.484f, .078f, .14f, .016f, .156f};
		public static final float[] WEAPON_AXE_DOUBLESIDE_VIKING_WOODBRONZE_VERTS_PART2 = {-.172f, .063f, .188f, -.031f, .359f, .203f, .297f, .406f, -.094f, .5f, -.234f, .281f};

		//spears
		public static final int[] WEAPON_SPEAR_VIKING_BRONZE_WOOD_MATERIALS = {FIXTURE_MATERIAL_WOOD , FIXTURE_MATERIAL_BRONZE};
		public static final float[] WEAPON_SPEAR_VIKING_WOODBRONZE_VERTS_PART1 = {-.031f, -1f, .031f, -1f, .031f, .422f, -.031f, .422f};
		public static final float[] WEAPON_SPEAR_VIKING_WOODBRONZE_VERTS_PART2 = {0, .422f, .094f, .672f, 0, 1f, -.094f, .672f};
		
		
		//joints
		public final static int TREEHURT_BRANCHITEM_JOINT = 1;
		public final static int TREEHURT_BRANCHITEM_JOINT_LIMITLOWER_ANGLE = -60;
		public final static int TREEHURT_BRANCHITEM_JOINT_LIMITUPPER_ANGLE = 30;
		public final static float TREEHURT_BRANCHITEM_JOINT_NORMAL_TORQUE = 10f;
		public final static float TREEHURT_BRANCHITEM_JOINT_NORMAL_SPEED = 1f;
		public final static float TREEHURT_BRANCHITEM_JOINT_EXCITED_SPEED_START = 20f;
		public final static float TREEHURT_BRANCHITEM_JOINT_EXCITED_SPEED_STEP = 10f;
		//public ststatic getTreeHurt_
		public static RevoluteJointDef  getHurtTreeBranchItemJointDef() {
			RevoluteJointDef  itemJoint = new RevoluteJointDef();
			itemJoint.enableLimit = true;
			itemJoint.lowerAngle = (float) Math.toRadians(TREEHURT_BRANCHITEM_JOINT_LIMITLOWER_ANGLE);
			itemJoint.upperAngle = (float) Math.toRadians(TREEHURT_BRANCHITEM_JOINT_LIMITUPPER_ANGLE);
			itemJoint.enableMotor = true;
			itemJoint.maxMotorTorque = TREEHURT_BRANCHITEM_JOINT_NORMAL_TORQUE; 
			itemJoint.motorSpeed = TREEHURT_BRANCHITEM_JOINT_NORMAL_SPEED ; 
			return itemJoint;
		};

		
		
		public static float [] getObjFixtureVerts(int type, int index)
		{
			float[] verts = null;
			
			switch(type)
			{
			case Statics.DynamicGameObject.SNAKE_WOOD_ARMOR:
				if(index == 0)
					verts = SNAKE_WOOD_ARMOR_VERTS_FIRST_PART;
				if(index == 1)
					verts = SNAKE_WOOD_ARMOR_VERTS_SECOND_PART;
				break;
			case Statics.DynamicGameObject.HELM_VIKING_WOODEN:
				if(index == 0)
					verts = WEAPON_HELM_VIKING_WOOD_VERTS_FIRST_PART;
				else if(index == 1)
					verts = WEAPON_HELM_VIKING_WOOD_VERTS_SECOND_PART;
				break;
			case Statics.DynamicGameObject.SHIELD_VIKING_WOODEN:
				if(index == 0)
					verts = WEAPON_SHIELD_VIKING_WOOD_VERTS_PART1;
				else if(index == 1)
					verts = WEAPON_SHIELD_VIKING_WOOD_VERTS_PART2;
				else if(index == 2)
					verts = WEAPON_SHIELD_VIKING_WOOD_VERTS_PART3;
				break;
			case Statics.DynamicGameObject.SWORD_VIKING_BRONZE_WOODEN:
				if (index == 0)
					verts = WEAPON_SWORD_VIKING_WOODBRONZE_VERTS_PART1;
				else 
					verts = WEAPON_SWORD_VIKING_WOODBRONZE_VERTS_PART2;
				break;
			case Statics.DynamicGameObject.AXE_VIKING_MONOSIDE_BRONZE_WOODEN:
				if (index == 0)
					verts = WEAPON_AXE_MONOSIDE_VIKING_WOODBRONZE_VERTS_PART1;
				else 
					verts = WEAPON_AXE_MONOSIDE_VIKING_WOODBRONZE_VERTS_PART2;
				break;
			case Statics.DynamicGameObject.AXE_VIKING_DOUBLESIDE_BRONZE_WOODEN:
				if (index == 0)
					verts = WEAPON_AXE_DOUBLESIDE_VIKING_WOODBRONZE_VERTS_PART1;
				else 
					verts = WEAPON_AXE_DOUBLESIDE_VIKING_WOODBRONZE_VERTS_PART2;
				break;
			case Statics.DynamicGameObject.SPEAR_VIKING_BRONZE_WOODEN:
				if (index == 0)
					verts = WEAPON_SPEAR_VIKING_WOODBRONZE_VERTS_PART1;
				else 
					verts = WEAPON_SPEAR_VIKING_WOODBRONZE_VERTS_PART2;
				break;
			}			
			return verts;
		}
		
		public static int [] getObjMaterials(int type)
		{
			int[] materials = null;
			
			switch(type)
			{
			case Statics.DynamicGameObject.HELM_VIKING_WOODEN:
				materials = WEAPON_HELM_VIKING_WOOD_MATERIALS;
				break;	
			case Statics.DynamicGameObject.SHIELD_VIKING_WOODEN:
				materials = WEAPON_SHIELD_VIKING_WOOD_MATERIALS;
				break;
			case Statics.DynamicGameObject.SWORD_VIKING_BRONZE_WOODEN:
				materials = WEAPON_SWORD_VIKING_BRONZE_WOOD_MATERIALS;
				break;
			case Statics.DynamicGameObject.AXE_VIKING_MONOSIDE_BRONZE_WOODEN:
				materials = WEAPON_AXE_MONOSIDE_VIKING_BRONZE_WOOD_MATERIALS;
				break;
			case Statics.DynamicGameObject.AXE_VIKING_DOUBLESIDE_BRONZE_WOODEN:
				materials = WEAPON_AXE_DOUBLESIDE_VIKING_BRONZE_WOOD_MATERIALS;
				break;
			case Statics.DynamicGameObject.SPEAR_VIKING_BRONZE_WOODEN:
				materials = WEAPON_SPEAR_VIKING_BRONZE_WOOD_MATERIALS;
				break;
			}			
			return materials;
		}
		
		public static int getWeaponStrikeMaterial(int type)
		{
			int materials = FIXTURE_MATERIAL_WOOD;
			
			switch(type)
			{
			case Statics.DynamicGameObject.SWORD_VIKING_BRONZE_WOODEN:
				materials = FIXTURE_MATERIAL_BRONZE;
				break;
			case Statics.DynamicGameObject.AXE_VIKING_MONOSIDE_BRONZE_WOODEN:
				materials = FIXTURE_MATERIAL_BRONZE;
				break;
			case Statics.DynamicGameObject.AXE_VIKING_DOUBLESIDE_BRONZE_WOODEN:
				materials = FIXTURE_MATERIAL_BRONZE;
				break;
			case Statics.DynamicGameObject.SPEAR_VIKING_BRONZE_WOODEN:
				materials = FIXTURE_MATERIAL_BRONZE;
				break;
			}			
			return materials;
		}
		
		public static float getWeaponStrikeSharpness(int type)
		{		
			switch(type)
			{
			case Statics.DynamicGameObject.SWORD_VIKING_BRONZE_WOODEN:
				return PENETRATION_WIDTH_NOT_VERY_SHARP;
			case Statics.DynamicGameObject.AXE_VIKING_MONOSIDE_BRONZE_WOODEN:
				return PENETRATION_WIDTH_NOT_SHARP;
			case Statics.DynamicGameObject.AXE_VIKING_DOUBLESIDE_BRONZE_WOODEN:
				return PENETRATION_WIDTH_NOT_SHARP;
			case Statics.DynamicGameObject.SPEAR_VIKING_BRONZE_WOODEN:
				return PENETRATION_WIDTH_SHARP;
			default:
				return PENETRATION_WIDTH_BLUNT;			
			}			
		}
		
		public static int getObjMaterial(int type)
		{
			switch(type)
			{
			case Statics.DynamicGameObject.SNAKE_WOOD_ARMOR:
				return FIXTURE_MATERIAL_WOOD;
			default :
				return 0;
			}							
		}
		
		public static int getObjPartsFixturesCount(int type)
		{
			int count = 0;
			
			switch(type)
			{
			case Statics.DynamicGameObject.SNAKE_WOOD_ARMOR:
				count = SNAKE_WOOD_ARMOR_PARTS_COUNT;
				break;	
			case Statics.DynamicGameObject.HELM_VIKING_WOODEN:
				count = Statics.INDEX_2;
				break;
			case Statics.DynamicGameObject.SHIELD_VIKING_WOODEN:
				count = Statics.INDEX_3;	
				break;
			case Statics.DynamicGameObject.SWORD_VIKING_BRONZE_WOODEN:
				count = Statics.INDEX_2;	
				break;
			case Statics.DynamicGameObject.AXE_VIKING_MONOSIDE_BRONZE_WOODEN:
				count = Statics.INDEX_2;	
				break;
			case Statics.DynamicGameObject.AXE_VIKING_DOUBLESIDE_BRONZE_WOODEN:
				count = Statics.INDEX_2;	
				break;
			case Statics.DynamicGameObject.SPEAR_VIKING_BRONZE_WOODEN:
				count = Statics.INDEX_2;	
				break;
			}			
			return count;
		}
	
	}
	
	static public class FightingSkills{
		
		//General
		public static final int FIGHTSKILL_LEVEL_PINK = 1;	
		public static final int FIGHTSKILL_LEVEL_GREEN = 2;	
		public static final int FIGHTSKILL_LEVEL_BLUE = 3;	
		public static final int FIGHTSKILL_LEVEL_YELLOW = 4;	
		public static final int FIGHTSKILL_LEVEL_ORANGE = 5;	
		public static final int FIGHTSKILL_LEVEL_RED = 6;	
		public static final int FIGHTSKILL_LEVEL_FIRE = 7;
		public static final int FIGHTSKILL_LEVEL_MIN = 1;
		public static final int FIGHTSKILL_LEVEL_MAX = 7;
		
		public static final int FIGHTSKILL_CLASS_SKILL = 1;
		public static final int FIGHTSKILL_CLASS_WEAPONSKILL = 2;
		
		public static final float FIGHTSKILL_POWER_STEP = 0.25f;
		public static final float FIGHTSKILL_DEADLY_FORCE_RATE = 1.42f;
		public static final float FIGHTSKILL_POWER_START = FIGHTSKILL_POWER_STEP;
		public static final float FIGHTSKILL_POWER_END = FIGHTSKILL_LEVEL_FIRE * FIGHTSKILL_POWER_STEP;

		public static final float FIGHTSKILL_IMPULSE_POWER_STEP = 2f;

		public static final float FIGHTSKILL_CLASS_JUMP_WEIGHT = 1f;
		public static final float FIGHTSKILL_CLASS_DEFENSE_WEIGHT = 2f;
		public static final float FIGHTSKILL_CLASS_ATTACK_WEIGHT = 4f;

		public static final float FIGHTSKILL_RELAX_PERIOD = 1f;
		public static final float CHARGING_DEFAULT_PERIOD = 5f;

		public static final String IMPULSE_ATTACK_FORWARD = "ImpulseAttackForward";
		public static final String IMPULSE_DEFFENSE_FORWARD = "ImpulseDeffenseForward";
		public static final String IMPULSE_DEFFENSE_LEFTSIDE = "ImpulseDeffenseLeftSide";
		public static final String IMPULSE_DEFFENSE_RIGHTSIDE = "ImpulseDeffenseRightSide";
		
		public static final String ICE_ATTACK_FORWARD = "IceAttackForward";
		public static final String HYPNOSE_ATTACK_FORWARD = "HypnoseAttackForward";
		public static final String FIRE_ATTACK_FORWARD = "FireAttackForward";
		
		public static final String FORWARD_ATTACK = "ForwardAttack";
		public static final String FORWARD_LEFT_HOOK = "ForwardLeftHook";
		public static final String FORWARD_RIGHT_HOOK = "ForwardRightHook";
		public static final String FORWARD_LEFT_TURN = "ForwardLeftTurn";
		public static final String FORWARD_RIGHT_TURN = "ForwardRightTurn";
		public static final String BACK_LEFT_HOOK = "BackLeftHook";
		public static final String BACK_RIGHT_HOOK = "BackRightHook";
		public static final String BACK_ATTACK = "BackAttack";
		public static final String BLOCK = "Block";
		
		public static final String WEAPON_HOLD_HELM = "WeaponHoldHelm";
		public static final String WEAPON_HOLD_SHIELD = "WeaponHoldShield";
		public static final String WEAPON_HOLD_LEFT_ARM = "WeaponHoldLeftArm";
		public static final String WEAPON_HOLD_RIGHT_ARM = "WeaponHoldRightArm";
		public static final String WEAPON_THROW = "WeaponThrow";
		public static final String WEAPON_THROW_SPEAR = "WeaponThrowSpear";
		public static final String WEAPON_THROW_SHIELD = "WeaponThrowShield";
		
		public static final float IMPULSE_ATTACK_FORWARD_VELOCITY = 3f;
		public static final float ICE_ATTACK_FORWARD_VELOCITY = 3f;
		public static final float HYPNOSE_ATTACK_FORWARD_VELOCITY = 3f;
		public static final float FIRE_ATTACK_FORWARD_VELOCITY = 5f;
		
		public static final float ICE_ATTACK_FORWARD_WEIGHT = 1.2f;
		public static final float HYPNOSE_ATTACK_FORWARD_WEIGHT = 1.1f;
		public static final float FIRE_ATTACK_FORWARD_WEIGHT = 1.4f;		
		public static final float IMPULSE_ATTACK_FORWARD_WEIGHT = 1.3f;
		
		public static final float IMPULSE_DEFFENSE_FORWARD_WEIGHT = 1.4f;
		public static final float IMPULSE_DEFFENSE_LEFTSIDE_WEIGHT = 1.2f;
		public static final float IMPULSE_DEFFENSE_RIGHTSIDE_WEIGHT = 1.2f;
		
		public static final float FORWARD_ATTACK_WEIGHT = 1.8f;
		public static final float FORWARD_LEFT_HOOK_WEIGHT = 1.6f;
		public static final float FORWARD_RIGHT_HOOK_WEIGHT = 1.6f;
		public static final float FORWARD_LEFT_TURN_WEIGHT = 1.4f;
		public static final float FORWARD_RIGHT_TURN_WEIGHT = 1.4f;
		public static final float BACK_LEFT_HOOK_WEIGHT = 1.4f;
		public static final float BACK_RIGHT_HOOK_WEIGHT = 1.2f;
		public static final float BACK_ATTACK_WEIGHT = 1.2f;
		public static final float BLOCK_WEIGHT = 1;
		public static final float FIGHTSKILL_MAX_WEIGHT = FIGHTSKILL_CLASS_ATTACK_WEIGHT * IMPULSE_ATTACK_FORWARD_WEIGHT;
		
		public static final float FORWARD_ATTACK_PERIOD = 0.4f;
		public static final float FORWARD_HOOK_PERIOD = 0.6f;
		public static final float FORWARD_TURN_PERIOD = 0.6f;
		public static final float BACK_HOOK_PERIOD = 0.6f;
		public static final float BACK_ATTACK_PERIOD = 0.6f;
		public static final float BLOCK_PERIOD = 3;

		public static final String SENSOR_FORWARD = "SensorForward";
		public static final String SENSOR_LEFT_SIDE = "SensorLeftSide";
		public static final String SENSOR_RIGHT_SIDE = "SensorRightSide";
		public static final String SENSOR_BACK = "SensorBack";
		public static final String SENSOR_FORWARD_RAY = "SensorForwardRay";
		public static final String SENSOR_VIRTUAL_FORWARD_RAY_IMPULSE = "SensorVirtualForwardRayImpulse";
		public static final String SENSOR_VIRTUAL_FORWARD_RAY_ICE = "SensorVirtualForwardRayIce";
		public static final String SENSOR_VIRTUAL_FORWARD_RAY_HYPNOSE = "SensorVirtualForwardRayHypnose";
		public static final String SENSOR_VIRTUAL_FORWARD_RAY_FIRE = "SensorVirtualForwardRayFire";
		public static final String SENSOR_VIRTUAL_FORWARD_RAY_WEAPON_THROW = "SensorVirtualForwardRayWeaponThrow";
		
		public static final int SENSOR_CLASS_BY_DISTANCE_ACTIVE_DEFFENSE = 1;
		public static final int SENSOR_CLASS_BY_DISTANCE_OPTIONAL_DEFFENSE = 2;
		public static final int SENSOR_CLASS_BY_DISTANCE_REMOTE_TARGET = 3;
		
		public static final float SENSOR_CLASS_BY_DISTANCE_ACTIVE_DEFFENSE_WEIGHT = 4;
		public static final float SENSOR_CLASS_BY_DISTANCE_REMOTE_TARGET_WEIGHT = 1;
		
		public static final float SENSOR_FORWARD_SENS_START_DISTANCE = 1.2f;
		public static final float SENSOR_FORWARD_SENS_STEP_DISTANCE = 0.4f;
		public static final float SENSOR_FORWARD_SENS_START_PATH = (float) (Math.PI * SENSOR_FORWARD_SENS_START_DISTANCE / 2); //for hook
		
		public static final float SENSOR_SIDE_SENS_START_DISTANCE = 1f;
		public static final float SENSOR_SIDE_SENS_STEP_DISTANCE = 0.2f;
		public static final float SENSOR_SIDE_SENS_START_PATH = (float) (Math.PI * SENSOR_SIDE_SENS_START_DISTANCE / 2);	//for hook
		
		public static final float SENSOR_BACK_SENS_START_DISTANCE = 1f;
		public static final float SENSOR_BACK_SENS_STEP_DISTANCE = 0.2f;
		
		public static final float SENSOR_IMPULSE_FORWARD_DEFENSE_SENS_START_DISTANCE = 1.2f;
		public static final float SENSOR_IMPULSE_FORWARD_DEFENSE_SENS_STEP_DISTANCE = 0.2f;
		public static final float SENSOR_IMPULSE_FORWARD_ATTACK_KOEF_TO_FORWARD_DEFENSE_SENSE = 4f;
		
		public static final float SENSOR_ICE_FORWARD_START_DISTANCE = 4.8f;
		public static final float SENSOR_ICE_FORWARD_STEP_DISTANCE = 0.8f;
		public static final float SENSOR_ICE_MININIMAL_WORK_DISTANCE = 1.5f;
		public static final float FIGHTSKILL_ICE_FORWARD_START_RADIUS = 0.3f;
		public static final float FIGHTSKILL_ICE_FORWARD_STEP_RADIUS = 0.05f;
		
		public static final float SENSOR_HYPNOSE_FORWARD_START_DISTANCE = 4.8f;
		public static final float SENSOR_HYPNOSE_FORWARD_STEP_DISTANCE = 0.8f;
		
		public static final float SENSOR_FIRE_FORWARD_START_DISTANCE = 4.8f;
		public static final float SENSOR_FIRE_FORWARD_STEP_DISTANCE = 0.8f;
		public static final float SENSOR_FIRE_MININIMAL_WORK_DISTANCE = 1.5f;
		public static final float FIGHTSKILL_FIRE_FORWARD_START_SCALE = 1f;
		public static final float FIGHTSKILL_FIRE_FORWARD_STEP_SCALE = 0.2f;
		public static final float FIGHTSKILL_FIRE_FORWARD_START_PERIOD = 5f;
		public static final float FIGHTSKILL_FIRE_FORWARD_STEP_PERIOD = 2f;
		public static final float FIGHTSKILL_FIRE_FORWARD_START_DAMAGE = 10f; 
		public static final float FIGHTSKILL_FIRE_FORWARD_STEP_DAMAGE = 5f;

		public static final float SENSOR_WEAPON_THROW_START_DISTANCE = 4.8f;
		public static final float SENSOR_WEAPON_THROW_STEP_DISTANCE = 0.8f;
	
		
		public static final int ATTACK_POWER_INDEX = 0;
		public static final int CHARGE_POWER_INDEX = 1;
		
		//forward attack statics
		public static final int FORWARD_ATTACK_FIELDS_COUNT = 2;
		
		//weapon statics
		public static final float WEAPON_HOLD_SHIELD_GLOBALRATE = 4f;
		public static final float WEAPON_HOLD_HELM_GLOBALRATE = 2f;
		public static final float WEAPON_HOLD_ARM_GLOBALRATE = 1f;
		
		public static final int WEAPON_HOLD_FIELDS_COUNT = 10;
		public static final float WEAPON_HOLD_SHIELD_WORKRADIUS = 1.7f;
		public static final int WEAPON_HOLD_SHIELD_TARGET_IN_SENSOR = 1;
		public static final int WEAPON_HOLD_SHIELD_TARGET_IN_WORKRADIUS = 2;
		public static final int WEAPON_HOLD_SHIELD_MODE_NORMAL = 0;
		public static final int WEAPON_HOLD_SHIELD_MODE_FORWARD = 1;
		public static final int WEAPON_HOLD_SHIELD_MODE_LEFT = 2;
		public static final int WEAPON_HOLD_SHIELD_MODE_RIGHT = 3;
		
		public static final float WEAPON_HOLD_ARM_WORK_RADIUS_SWORD = 2.5f;
		public static final float WEAPON_HOLD_ARM_WORK_RADIUS_AXE = 2f;
		public static final float WEAPON_HOLD_ARM_WORK_RADIUS_SPEAR = 3f;
		public static final float WEAPON_HOLD_ARM_NO_ACT_PERIOD_AFTER_WEAPON_RELEASE = 0.5f;
		
		public static final int WEAPON_HOLD_ARM_FENCING_TYPE_CHOP = 1;
		public static final int WEAPON_HOLD_ARM_FENCING_TYPE_STAB = 2;
		public static final int WEAPON_HOLD_ARM_FENCING_TYPE_CHOP_PERMITTED_ATTACK_ANGLE = 90;
		public static final int WEAPON_HOLD_ARM_FENCING_TYPE_STAB_PERMITTED_ATTACK_ANGLE = 20;
		
		public static float getFencingPermittedAngleAttack(int type) {
			
			switch(type) {
			case  WEAPON_HOLD_ARM_FENCING_TYPE_CHOP:
				return  WEAPON_HOLD_ARM_FENCING_TYPE_CHOP_PERMITTED_ATTACK_ANGLE;
			case  WEAPON_HOLD_ARM_FENCING_TYPE_STAB:
				return  WEAPON_HOLD_ARM_FENCING_TYPE_STAB_PERMITTED_ATTACK_ANGLE;
			default:
				return 0;
			}
			
		}
				
		public static float getWeaponClassRate(int type)
		{
			if(Statics.DynamicGameObject.getWeaponClass(type) == Statics.DynamicGameObject.ABSTRACT_TYPE_WEAPON_SHIELD)
				return Statics.FightingSkills.WEAPON_HOLD_SHIELD_GLOBALRATE;
			else if(Statics.DynamicGameObject.getWeaponClass(type) == Statics.DynamicGameObject.ABSTRACT_TYPE_WEAPON_HELM)
				return Statics.FightingSkills.WEAPON_HOLD_HELM_GLOBALRATE;
			else if(Statics.DynamicGameObject.getWeaponClass(type) == Statics.DynamicGameObject.ABSTRACT_TYPE_WEAPON_SWORD ||
					Statics.DynamicGameObject.getWeaponClass(type) == Statics.DynamicGameObject.ABSTRACT_TYPE_WEAPON_AXE ||
					Statics.DynamicGameObject.getWeaponClass(type) == Statics.DynamicGameObject.ABSTRACT_TYPE_WEAPON_SPEAR)
				return Statics.FightingSkills.WEAPON_HOLD_ARM_GLOBALRATE;
			else
				return 1;

		}
		
		public static int getDistanceZoneBySensor(String type)
		{
			if(type == Statics.FightingSkills.SENSOR_FORWARD
					|| type == Statics.FightingSkills.SENSOR_LEFT_SIDE
					|| type == Statics.FightingSkills.SENSOR_RIGHT_SIDE
					|| type == Statics.FightingSkills.SENSOR_BACK)
				return Statics.FightingSkills.SENSOR_CLASS_BY_DISTANCE_ACTIVE_DEFFENSE;

			else if (type == Statics.FightingSkills.SENSOR_FORWARD_RAY
					     || type == Statics.FightingSkills.SENSOR_VIRTUAL_FORWARD_RAY_IMPULSE
					     || type == Statics.FightingSkills.SENSOR_VIRTUAL_FORWARD_RAY_ICE
					     || type == Statics.FightingSkills.SENSOR_VIRTUAL_FORWARD_RAY_HYPNOSE
					     || type == Statics.FightingSkills.SENSOR_VIRTUAL_FORWARD_RAY_FIRE
					     || type == Statics.FightingSkills.SENSOR_VIRTUAL_FORWARD_RAY_WEAPON_THROW)
				return Statics.FightingSkills.SENSOR_CLASS_BY_DISTANCE_REMOTE_TARGET;
			else
				return SENSOR_CLASS_BY_DISTANCE_REMOTE_TARGET; 
		}
		
		public static float getDistanceZoneWeight(int type)
		{
			if(type == Statics.FightingSkills.SENSOR_CLASS_BY_DISTANCE_ACTIVE_DEFFENSE)
				return Statics.FightingSkills.SENSOR_CLASS_BY_DISTANCE_ACTIVE_DEFFENSE_WEIGHT;
			else if(type == Statics.FightingSkills.SENSOR_CLASS_BY_DISTANCE_REMOTE_TARGET)
				return Statics.FightingSkills.SENSOR_CLASS_BY_DISTANCE_REMOTE_TARGET_WEIGHT;
			
			return Statics.FightingSkills.SENSOR_CLASS_BY_DISTANCE_REMOTE_TARGET_WEIGHT;
		}
		
		public static float getFSkillRateByDistanceClass(String type)
		{
			if(type == Statics.FightingSkills.FORWARD_ATTACK 
						 || type == Statics.FightingSkills.FORWARD_LEFT_HOOK 
						 || type == Statics.FightingSkills.FORWARD_RIGHT_HOOK 
						 || type == Statics.FightingSkills.FORWARD_LEFT_TURN 
						 || type == Statics.FightingSkills.FORWARD_RIGHT_TURN 
						 || type == Statics.FightingSkills.BACK_LEFT_HOOK 
						 || type == Statics.FightingSkills.BACK_RIGHT_HOOK || type == Statics.FightingSkills.BACK_ATTACK
						 || type == Statics.FightingSkills.BLOCK)
				return Statics.FightingSkills.FIGHTSKILL_CLASS_JUMP_WEIGHT;
			else if (type == Statics.FightingSkills.IMPULSE_DEFFENSE_FORWARD 
					     || type == Statics.FightingSkills.IMPULSE_DEFFENSE_LEFTSIDE
					     || type == Statics.FightingSkills.IMPULSE_DEFFENSE_RIGHTSIDE)
				return Statics.FightingSkills.FIGHTSKILL_CLASS_DEFENSE_WEIGHT;
			else if (type == Statics.FightingSkills.IMPULSE_ATTACK_FORWARD
					     || type == Statics.FightingSkills.ICE_ATTACK_FORWARD
					     || type == Statics.FightingSkills.HYPNOSE_ATTACK_FORWARD
					     || type == Statics.FightingSkills.FIRE_ATTACK_FORWARD)
				return Statics.FightingSkills.FIGHTSKILL_CLASS_ATTACK_WEIGHT;
			else
				return FIGHTSKILL_CLASS_JUMP_WEIGHT; 
		}
		
		public static float getFSkillRate(String type)
		{
			if(type == Statics.FightingSkills.FORWARD_ATTACK)
				return Statics.FightingSkills.FORWARD_ATTACK_WEIGHT;
			else if (type == Statics.FightingSkills.FORWARD_LEFT_HOOK || type == Statics.FightingSkills.FORWARD_RIGHT_HOOK)
				return Statics.FightingSkills.FORWARD_LEFT_HOOK_WEIGHT;				
			else if (type == Statics.FightingSkills.FORWARD_LEFT_TURN || type == Statics.FightingSkills.FORWARD_RIGHT_TURN)
				return Statics.FightingSkills.FORWARD_LEFT_TURN_WEIGHT;
			else if (type == Statics.FightingSkills.BACK_LEFT_HOOK || type == Statics.FightingSkills.BACK_RIGHT_HOOK)
				return Statics.FightingSkills.BACK_LEFT_HOOK_WEIGHT;
			else if (type == Statics.FightingSkills.BACK_ATTACK)
				return Statics.FightingSkills.BACK_ATTACK_WEIGHT;
			else if (type == Statics.FightingSkills.BLOCK)
				return Statics.FightingSkills.BLOCK_WEIGHT;
			else if (type == Statics.FightingSkills.IMPULSE_DEFFENSE_FORWARD)
				return Statics.FightingSkills.IMPULSE_DEFFENSE_FORWARD_WEIGHT;
			else if (type == Statics.FightingSkills.IMPULSE_DEFFENSE_LEFTSIDE || type == Statics.FightingSkills.IMPULSE_DEFFENSE_RIGHTSIDE)
				return Statics.FightingSkills.IMPULSE_DEFFENSE_LEFTSIDE_WEIGHT;
			else if (type == Statics.FightingSkills.IMPULSE_ATTACK_FORWARD)
				return Statics.FightingSkills.IMPULSE_ATTACK_FORWARD_WEIGHT;					
			else if (type == Statics.FightingSkills.ICE_ATTACK_FORWARD)
				return Statics.FightingSkills.ICE_ATTACK_FORWARD_WEIGHT;			
			else if (type == Statics.FightingSkills.HYPNOSE_ATTACK_FORWARD)
				return Statics.FightingSkills.HYPNOSE_ATTACK_FORWARD_WEIGHT;			
			else if (type == Statics.FightingSkills.FIRE_ATTACK_FORWARD)
				return Statics.FightingSkills.FIRE_ATTACK_FORWARD_WEIGHT;
			else
				return Statics.FightingSkills.BLOCK_WEIGHT; 
		}
		
		public static int getSensorTypeByDistance(String sensor)
		{
			//ADD NEW SENSORS
			return Statics.FightingSkills.SENSOR_CLASS_BY_DISTANCE_ACTIVE_DEFFENSE;			
		}
		
		public static float getFSkillWorkDistance(String type, int power)
		{
			if(type == Statics.FightingSkills.FORWARD_ATTACK 
					|| type == Statics.FightingSkills.FORWARD_LEFT_HOOK 
					|| type == Statics.FightingSkills.FORWARD_RIGHT_HOOK)				
				return Statics.FightingSkills.SENSOR_FORWARD_SENS_START_DISTANCE +
						Statics.FightingSkills.SENSOR_FORWARD_SENS_STEP_DISTANCE * (power - 1);	
			
			else if(type == Statics.FightingSkills.IMPULSE_DEFFENSE_FORWARD)				
				return Statics.FightingSkills.SENSOR_IMPULSE_FORWARD_DEFENSE_SENS_START_DISTANCE +
						Statics.FightingSkills.SENSOR_IMPULSE_FORWARD_DEFENSE_SENS_STEP_DISTANCE * (power - 1);	
			
			else if (type == Statics.FightingSkills.FORWARD_LEFT_TURN 
					|| type == Statics.FightingSkills.FORWARD_RIGHT_TURN 
					|| type == Statics.FightingSkills.BACK_LEFT_HOOK 
					|| type == Statics.FightingSkills.BACK_RIGHT_HOOK
					|| type == Statics.FightingSkills.IMPULSE_DEFFENSE_LEFTSIDE 
					|| type == Statics.FightingSkills.IMPULSE_DEFFENSE_RIGHTSIDE)				
				return Statics.FightingSkills.SENSOR_SIDE_SENS_START_DISTANCE +
						Statics.FightingSkills.SENSOR_SIDE_SENS_STEP_DISTANCE * (power - 1);	
			
			else if (type == Statics.FightingSkills.BACK_ATTACK)				
				return Statics.FightingSkills.SENSOR_BACK_SENS_START_DISTANCE +
						Statics.FightingSkills.SENSOR_BACK_SENS_STEP_DISTANCE * (power - 1);	
			
			else if (type == Statics.FightingSkills.IMPULSE_ATTACK_FORWARD)				
				return  Statics.FightingSkills.SENSOR_IMPULSE_FORWARD_ATTACK_KOEF_TO_FORWARD_DEFENSE_SENSE *
						Statics.FightingSkills.SENSOR_IMPULSE_FORWARD_DEFENSE_SENS_START_DISTANCE +
						Statics.FightingSkills.SENSOR_IMPULSE_FORWARD_ATTACK_KOEF_TO_FORWARD_DEFENSE_SENSE *
						Statics.FightingSkills.SENSOR_IMPULSE_FORWARD_DEFENSE_SENS_STEP_DISTANCE * (power - 1);				
			else if (type == Statics.FightingSkills.ICE_ATTACK_FORWARD)				
				return  Statics.FightingSkills.SENSOR_ICE_FORWARD_START_DISTANCE +
						Statics.FightingSkills.SENSOR_ICE_FORWARD_STEP_DISTANCE * (power - 1);			
			else if (type == Statics.FightingSkills.HYPNOSE_ATTACK_FORWARD)				
				return  Statics.FightingSkills.SENSOR_HYPNOSE_FORWARD_START_DISTANCE +
						Statics.FightingSkills.SENSOR_HYPNOSE_FORWARD_STEP_DISTANCE * (power - 1);				
			else if (type == Statics.FightingSkills.FIRE_ATTACK_FORWARD)				
				return  Statics.FightingSkills.SENSOR_FIRE_FORWARD_START_DISTANCE +
						Statics.FightingSkills.SENSOR_FIRE_FORWARD_STEP_DISTANCE * (power - 1);				
			else if (type == Statics.FightingSkills.WEAPON_THROW || type == Statics.FightingSkills.WEAPON_THROW_SPEAR)				
				return  Statics.FightingSkills.SENSOR_WEAPON_THROW_START_DISTANCE +
						Statics.FightingSkills.SENSOR_WEAPON_THROW_STEP_DISTANCE * (power - 1);	
			else
				return 1; 		
		}
		
		public static float getweaponWorkDistance(int type)
		{
			float workRadius = 1.5f;
			
			switch(type)
			{
				case Statics.DynamicGameObject.ABSTRACT_TYPE_WEAPON_SWORD:
					workRadius = Statics.FightingSkills.WEAPON_HOLD_ARM_WORK_RADIUS_SWORD;
					break;
				case Statics.DynamicGameObject.ABSTRACT_TYPE_WEAPON_AXE:
					workRadius = Statics.FightingSkills.WEAPON_HOLD_ARM_WORK_RADIUS_AXE;
					break;
				case Statics.DynamicGameObject.ABSTRACT_TYPE_WEAPON_SPEAR:
					workRadius = Statics.FightingSkills.WEAPON_HOLD_ARM_WORK_RADIUS_SPEAR;
					break;
			}
			return workRadius;
		}
		
		public static int getFSkillClass(String type)
		{
			if(type == Statics.FightingSkills.FORWARD_ATTACK 
					|| type == Statics.FightingSkills.FORWARD_LEFT_HOOK 
					|| type == Statics.FightingSkills.FORWARD_RIGHT_HOOK
					|| type == Statics.FightingSkills.IMPULSE_DEFFENSE_FORWARD
					|| type == Statics.FightingSkills.FORWARD_LEFT_TURN 
					|| type == Statics.FightingSkills.FORWARD_RIGHT_TURN 
					|| type == Statics.FightingSkills.BACK_LEFT_HOOK 
					|| type == Statics.FightingSkills.BACK_RIGHT_HOOK
					|| type == Statics.FightingSkills.IMPULSE_DEFFENSE_LEFTSIDE 
					|| type == Statics.FightingSkills.IMPULSE_DEFFENSE_RIGHTSIDE
					|| type == Statics.FightingSkills.BACK_ATTACK
					|| type == Statics.FightingSkills.IMPULSE_ATTACK_FORWARD
					|| type == Statics.FightingSkills.ICE_ATTACK_FORWARD
					|| type == Statics.FightingSkills.HYPNOSE_ATTACK_FORWARD
					|| type == Statics.FightingSkills.FIRE_ATTACK_FORWARD)
				return FIGHTSKILL_CLASS_SKILL;
			
			else if(type == Statics.FightingSkills.WEAPON_HOLD_HELM
					|| type == Statics.FightingSkills.WEAPON_HOLD_SHIELD
					|| type == Statics.FightingSkills.WEAPON_HOLD_LEFT_ARM
					|| type == Statics.FightingSkills.WEAPON_HOLD_RIGHT_ARM
					|| type == Statics.FightingSkills.WEAPON_THROW
					|| type == Statics.FightingSkills.WEAPON_THROW_SPEAR
					|| type == Statics.FightingSkills.WEAPON_THROW_SHIELD)
				return FIGHTSKILL_CLASS_WEAPONSKILL;
			
			else return 0;
		}
		
		public static float getSensorRadius(String sensor)
		{
			if(sensor == Statics.FightingSkills.SENSOR_FORWARD)
				return Statics.PhysicsBox2D.SENSOR_FIGHT_SKILL_FORWARD_RADIUS;
			else if(sensor == Statics.FightingSkills.SENSOR_LEFT_SIDE || sensor == Statics.FightingSkills.SENSOR_RIGHT_SIDE)
				return Statics.PhysicsBox2D.SENSOR_FIGHT_SKILL_SIDE_RADIUS;
			else if(sensor == Statics.FightingSkills.SENSOR_BACK)
				return Statics.PhysicsBox2D.SENSOR_FIGHT_SKILL_BACK_RADIUS;			
			else 
				return Statics.PhysicsBox2D.SENSOR_FIGHT_SKILL_FORWARD_RADIUS;		
		}
		
		public static List <String> getFSkillsBySensor(String fSensor)
		{
			List <String>  fSkillsList;
			fSkillsList = new ArrayList <String>();	
			
			if(fSensor == Statics.FightingSkills.SENSOR_FORWARD)
			{
				fSkillsList.add(Statics.FightingSkills.FORWARD_ATTACK);
				fSkillsList.add(Statics.FightingSkills.FORWARD_LEFT_HOOK);
				fSkillsList.add(Statics.FightingSkills.FORWARD_RIGHT_HOOK);
				fSkillsList.add(Statics.FightingSkills.BLOCK);
				fSkillsList.add(Statics.FightingSkills.IMPULSE_DEFFENSE_FORWARD);
				fSkillsList.add(Statics.FightingSkills.IMPULSE_ATTACK_FORWARD);
				fSkillsList.add(Statics.FightingSkills.ICE_ATTACK_FORWARD);
				fSkillsList.add(Statics.FightingSkills.HYPNOSE_ATTACK_FORWARD);
				fSkillsList.add(Statics.FightingSkills.FIRE_ATTACK_FORWARD);
			}
			else if(fSensor == Statics.FightingSkills.SENSOR_LEFT_SIDE)
			{
				fSkillsList.add(Statics.FightingSkills.FORWARD_LEFT_TURN);
				fSkillsList.add(Statics.FightingSkills.BACK_LEFT_HOOK);
				fSkillsList.add(Statics.FightingSkills.BLOCK);
				fSkillsList.add(Statics.FightingSkills.IMPULSE_DEFFENSE_LEFTSIDE);
			}
			else if(fSensor == Statics.FightingSkills.SENSOR_RIGHT_SIDE)
			{
				fSkillsList.add(Statics.FightingSkills.FORWARD_RIGHT_TURN);
				fSkillsList.add(Statics.FightingSkills.BACK_RIGHT_HOOK);
				fSkillsList.add(Statics.FightingSkills.BLOCK);
				fSkillsList.add(Statics.FightingSkills.IMPULSE_DEFFENSE_RIGHTSIDE);
			}
			else if(fSensor == Statics.FightingSkills.SENSOR_BACK)
			{
				fSkillsList.add(Statics.FightingSkills.BACK_ATTACK);
				fSkillsList.add(Statics.FightingSkills.BLOCK);
			}
			else if(fSensor == Statics.FightingSkills.SENSOR_FORWARD_RAY)
			{
				fSkillsList.add(Statics.FightingSkills.IMPULSE_ATTACK_FORWARD);	
				fSkillsList.add(Statics.FightingSkills.ICE_ATTACK_FORWARD);	
				fSkillsList.add(Statics.FightingSkills.HYPNOSE_ATTACK_FORWARD);	
				fSkillsList.add(Statics.FightingSkills.FIRE_ATTACK_FORWARD);	
				fSkillsList.add(Statics.FightingSkills.WEAPON_THROW);	
				fSkillsList.add(Statics.FightingSkills.WEAPON_THROW_SPEAR);
			}
			else if(fSensor == Statics.FightingSkills.SENSOR_VIRTUAL_FORWARD_RAY_IMPULSE)
					fSkillsList.add(Statics.FightingSkills.IMPULSE_ATTACK_FORWARD);
			else if(fSensor == Statics.FightingSkills.SENSOR_VIRTUAL_FORWARD_RAY_ICE)
				fSkillsList.add(Statics.FightingSkills.ICE_ATTACK_FORWARD);
			else if(fSensor == Statics.FightingSkills.SENSOR_VIRTUAL_FORWARD_RAY_HYPNOSE)
				fSkillsList.add(Statics.FightingSkills.HYPNOSE_ATTACK_FORWARD);
			else if(fSensor == Statics.FightingSkills.SENSOR_VIRTUAL_FORWARD_RAY_FIRE)
				fSkillsList.add(Statics.FightingSkills.FIRE_ATTACK_FORWARD);
			else if(fSensor == Statics.FightingSkills.SENSOR_VIRTUAL_FORWARD_RAY_WEAPON_THROW)
				fSkillsList.add(Statics.FightingSkills.WEAPON_THROW);
			else
				return null;
			
			return fSkillsList;
		}
	}
	
	static public class AI{
		
		public static final float GOAL_WEIGHT_DEFAULT = 1;
		public static final float GOAL_WEIGHT_HIGH = 0.5f;
		public static final float GOAL_WEIGHT_VERY_HIGH = 0.2f;
		public static final float GOAL_WEIGHT_LOW = 2f;
		public static final float GOAL_WEIGHT_VERY_LOW = 5f;
		public static final float MAX_GOAL_WEIGHT_KOEF = 20;
		public static final float MAX_WEIGHT = 2000;
		public static final int   EMPTY_SPACE_WEIGHT = 10;
		
		//MODES
		public static final int MODE_FREE_DEFAULT = 0;
		public static final int MODE_ENEMY_TARGET_ATTACK = 1;
		public static final int MODE_FRIEND_PROTECT = 2;
		public static final int MODE_PLACE_PROTECT = 3;
		public static final int MODE_SURVIVE= 4;
		
		
		public static final int WEIGHT_BLUE_MUSHROOM = 3;
		public static final float WEIGHT_BROWN_MUSHROOM = 1.5f;
		public static final int WEIGHT_BRED_MUSHROOM = 4;
		
		public static final int AGGRESSION_TO_ENEMY_COLDMIND = 1;	//attack if profit > lost [ulfhednar]
		public static final int AGGRESSION_TO_ENEMY_BY_PROFIT_RATIO_ALIVE = 2;	//attack profit / danger if will stay alive 
		public static final int AGGRESSION_TO_ENEMY_AGGRESSIVE_ALIVE = 3; //attack the most expensive targets if stay alive [snake brother, konung]
		public static final int AGGRESSION_TO_ENEMY_BY_PROFIT_RATIO = 4;	//attack profit / danger even if will die [lemmy, viking]
		public static final int AGGRESSION_TO_ENEMY_MAD = 5;	//attack the most expensive and powerful target even if will die [berserker]
		
		
		public static Array <Integer> goals;
		
		//characters weights		
		//snake
		public static ArrayMap <Integer, Float> charSnakeBrotherGoalsMap;
		
		//lemmy
		public static ArrayMap <Integer, Float> charLemmyGoalsMap;
		public static ArrayMap <Integer, Float> charLemmyVikingGoalsMap;
		public static ArrayMap <Integer, Float> charLemmyBerserkerGoalsMap;
		public static ArrayMap <Integer, Float> charLemmyUlfhednarGoalsMap;
		
		//Modes Maps
		public static ArrayMap <Integer, Float> charModeEnemyTargetAttackGoalsMap;
		public static ArrayMap <Integer, Float> charModeFriendProtectGoalsMap;
		public static ArrayMap <Integer, Float> charModePlaceProtectGoalsMap;
		public static ArrayMap <Integer, Float> charModeSurviveGoalsMap;
		
		AI()
		{
			goals = new Array <Integer>();
			goals.addAll(GameObject.APPLE, 
						 GameObject.MUSHROOM_BLUE,
						 GameObject.MUSHROOM_YELLOW,
						 GameObject.MUSHROOM_BLUE_RED,
						 GameObject.MUSHROOM_BROWN,
						 DynamicGameObject.ABSTRACT_TYPE_ENEMY,
						 DynamicGameObject.ABSTRACT_TYPE_ENEMY_TARGET,
						 DynamicGameObject.ABSTRACT_TYPE_FRIEND,
						 DynamicGameObject.ABSTRACT_TYPE_FRIEND_TO_PROTECT,
						 DynamicGameObject.ABSTRACT_TYPE_PROTECTED_PLACE,
						 DynamicGameObject.ABSTRACT_TYPE_WEAPON_HELM,
						 DynamicGameObject.ABSTRACT_TYPE_WEAPON_SHIELD,
						 DynamicGameObject.ABSTRACT_TYPE_WEAPON_SWORD,
						 DynamicGameObject.ABSTRACT_TYPE_WEAPON_AXE,
						 DynamicGameObject.ABSTRACT_TYPE_WEAPON_SPEAR
						 );
			
			//CHARACTERS GOALS MAPS
			//Snaky
			charSnakeBrotherGoalsMap = new ArrayMap <Integer, Float>();
			charSnakeBrotherGoalsMap.put(GameObject.APPLE, GOAL_WEIGHT_DEFAULT);
			charSnakeBrotherGoalsMap.put(GameObject.MUSHROOM_BLUE, GOAL_WEIGHT_DEFAULT);
			charSnakeBrotherGoalsMap.put(GameObject.MUSHROOM_YELLOW, GOAL_WEIGHT_DEFAULT);
			charSnakeBrotherGoalsMap.put(GameObject.MUSHROOM_BLUE_RED, GOAL_WEIGHT_DEFAULT);
			charSnakeBrotherGoalsMap.put(GameObject.MUSHROOM_BROWN, GOAL_WEIGHT_DEFAULT);
			charSnakeBrotherGoalsMap.put(DynamicGameObject.ABSTRACT_TYPE_ENEMY, (float)AGGRESSION_TO_ENEMY_AGGRESSIVE_ALIVE);
			charSnakeBrotherGoalsMap.put(DynamicGameObject.ABSTRACT_TYPE_ENEMY_TARGET, GOAL_WEIGHT_DEFAULT);
			charSnakeBrotherGoalsMap.put(DynamicGameObject.ABSTRACT_TYPE_FRIEND_TO_PROTECT, GOAL_WEIGHT_DEFAULT);
			charSnakeBrotherGoalsMap.put(DynamicGameObject.ABSTRACT_TYPE_PROTECTED_PLACE, GOAL_WEIGHT_DEFAULT);
			
			//Lemmy
			charLemmyGoalsMap = new ArrayMap <Integer, Float>();
			charLemmyGoalsMap.put(GameObject.APPLE, GOAL_WEIGHT_DEFAULT);
			charLemmyGoalsMap.put(GameObject.MUSHROOM_BLUE, GOAL_WEIGHT_DEFAULT);
			charLemmyGoalsMap.put(GameObject.MUSHROOM_YELLOW, GOAL_WEIGHT_DEFAULT);
			charLemmyGoalsMap.put(GameObject.MUSHROOM_BLUE_RED, GOAL_WEIGHT_DEFAULT);
			charLemmyGoalsMap.put(GameObject.MUSHROOM_BROWN, GOAL_WEIGHT_DEFAULT);
			charLemmyGoalsMap.put(DynamicGameObject.ABSTRACT_TYPE_ENEMY, (float)AGGRESSION_TO_ENEMY_BY_PROFIT_RATIO);
			charLemmyGoalsMap.put(DynamicGameObject.ABSTRACT_TYPE_ENEMY_TARGET, GOAL_WEIGHT_LOW);
			charLemmyGoalsMap.put(DynamicGameObject.ABSTRACT_TYPE_FRIEND_TO_PROTECT, GOAL_WEIGHT_DEFAULT);
			
			charLemmyVikingGoalsMap = new ArrayMap <Integer, Float>();
			charLemmyVikingGoalsMap.put(GameObject.APPLE, GOAL_WEIGHT_DEFAULT);
			charLemmyVikingGoalsMap.put(GameObject.MUSHROOM_BLUE, GOAL_WEIGHT_DEFAULT);
			charLemmyVikingGoalsMap.put(GameObject.MUSHROOM_YELLOW, GOAL_WEIGHT_DEFAULT);
			charLemmyVikingGoalsMap.put(GameObject.MUSHROOM_BLUE_RED, GOAL_WEIGHT_DEFAULT);
			charLemmyVikingGoalsMap.put(GameObject.MUSHROOM_BROWN, GOAL_WEIGHT_DEFAULT);
			charLemmyVikingGoalsMap.put(DynamicGameObject.ABSTRACT_TYPE_ENEMY, (float)AGGRESSION_TO_ENEMY_BY_PROFIT_RATIO);
			charLemmyVikingGoalsMap.put(DynamicGameObject.ABSTRACT_TYPE_ENEMY_TARGET, GOAL_WEIGHT_DEFAULT);
			charLemmyVikingGoalsMap.put(DynamicGameObject.ABSTRACT_TYPE_FRIEND_TO_PROTECT, GOAL_WEIGHT_DEFAULT);
			charLemmyVikingGoalsMap.put(DynamicGameObject.ABSTRACT_TYPE_PROTECTED_PLACE, GOAL_WEIGHT_DEFAULT);
			charLemmyVikingGoalsMap.put(DynamicGameObject.ABSTRACT_TYPE_WEAPON_HELM, GOAL_WEIGHT_DEFAULT);
			charLemmyVikingGoalsMap.put(DynamicGameObject.ABSTRACT_TYPE_WEAPON_SHIELD, GOAL_WEIGHT_DEFAULT);
			charLemmyVikingGoalsMap.put(DynamicGameObject.ABSTRACT_TYPE_WEAPON_SWORD, GOAL_WEIGHT_DEFAULT);
			
			charLemmyBerserkerGoalsMap = new ArrayMap <Integer, Float>();
			charLemmyBerserkerGoalsMap.put(GameObject.APPLE, GOAL_WEIGHT_DEFAULT);
			charLemmyBerserkerGoalsMap.put(GameObject.MUSHROOM_BLUE, GOAL_WEIGHT_DEFAULT);
			charLemmyBerserkerGoalsMap.put(GameObject.MUSHROOM_YELLOW, GOAL_WEIGHT_DEFAULT);
			charLemmyBerserkerGoalsMap.put(GameObject.MUSHROOM_BLUE_RED, GOAL_WEIGHT_DEFAULT);
			charLemmyBerserkerGoalsMap.put(GameObject.MUSHROOM_BROWN, GOAL_WEIGHT_DEFAULT);
			charLemmyBerserkerGoalsMap.put(DynamicGameObject.ABSTRACT_TYPE_ENEMY, (float)AGGRESSION_TO_ENEMY_MAD);
			charLemmyBerserkerGoalsMap.put(DynamicGameObject.ABSTRACT_TYPE_ENEMY_TARGET, GOAL_WEIGHT_VERY_HIGH);
			charLemmyBerserkerGoalsMap.put(DynamicGameObject.ABSTRACT_TYPE_FRIEND_TO_PROTECT, GOAL_WEIGHT_VERY_HIGH);
			charLemmyBerserkerGoalsMap.put(DynamicGameObject.ABSTRACT_TYPE_WEAPON_HELM, GOAL_WEIGHT_HIGH);
			charLemmyBerserkerGoalsMap.put(DynamicGameObject.ABSTRACT_TYPE_WEAPON_SHIELD, GOAL_WEIGHT_HIGH);
			charLemmyBerserkerGoalsMap.put(DynamicGameObject.ABSTRACT_TYPE_WEAPON_SWORD, GOAL_WEIGHT_DEFAULT);
			charLemmyBerserkerGoalsMap.put(DynamicGameObject.ABSTRACT_TYPE_WEAPON_AXE, GOAL_WEIGHT_HIGH);
			charLemmyBerserkerGoalsMap.put(DynamicGameObject.ABSTRACT_TYPE_WEAPON_SPEAR, GOAL_WEIGHT_DEFAULT);
			
			charLemmyUlfhednarGoalsMap = new ArrayMap <Integer, Float>();
			charLemmyUlfhednarGoalsMap.put(GameObject.APPLE, GOAL_WEIGHT_DEFAULT);
			charLemmyUlfhednarGoalsMap.put(GameObject.MUSHROOM_BLUE, GOAL_WEIGHT_DEFAULT);
			charLemmyUlfhednarGoalsMap.put(GameObject.MUSHROOM_YELLOW, GOAL_WEIGHT_DEFAULT);
			charLemmyUlfhednarGoalsMap.put(GameObject.MUSHROOM_BLUE_RED, GOAL_WEIGHT_DEFAULT);
			charLemmyUlfhednarGoalsMap.put(GameObject.MUSHROOM_BROWN, GOAL_WEIGHT_DEFAULT);
			charLemmyUlfhednarGoalsMap.put(DynamicGameObject.ABSTRACT_TYPE_ENEMY, (float)AGGRESSION_TO_ENEMY_COLDMIND);
			charLemmyUlfhednarGoalsMap.put(DynamicGameObject.ABSTRACT_TYPE_ENEMY_TARGET, GOAL_WEIGHT_HIGH);
			charLemmyUlfhednarGoalsMap.put(DynamicGameObject.ABSTRACT_TYPE_FRIEND_TO_PROTECT, GOAL_WEIGHT_HIGH);
			charLemmyUlfhednarGoalsMap.put(DynamicGameObject.ABSTRACT_TYPE_WEAPON_SWORD, GOAL_WEIGHT_DEFAULT);
			charLemmyUlfhednarGoalsMap.put(DynamicGameObject.ABSTRACT_TYPE_WEAPON_AXE, GOAL_WEIGHT_HIGH);
			charLemmyUlfhednarGoalsMap.put(DynamicGameObject.ABSTRACT_TYPE_WEAPON_SPEAR, GOAL_WEIGHT_HIGH);				
			
			//MODES GOALS MAPS
			//mode: attack enemy target
			charModeEnemyTargetAttackGoalsMap = new ArrayMap <Integer, Float>();
			charModeEnemyTargetAttackGoalsMap.put(GameObject.APPLE, GOAL_WEIGHT_DEFAULT);
			charModeEnemyTargetAttackGoalsMap.put(GameObject.MUSHROOM_BLUE, GOAL_WEIGHT_DEFAULT);
			charModeEnemyTargetAttackGoalsMap.put(GameObject.MUSHROOM_YELLOW, GOAL_WEIGHT_DEFAULT);
			charModeEnemyTargetAttackGoalsMap.put(GameObject.MUSHROOM_BLUE_RED, GOAL_WEIGHT_DEFAULT);
			charModeEnemyTargetAttackGoalsMap.put(GameObject.MUSHROOM_BROWN, GOAL_WEIGHT_DEFAULT);
			charModeEnemyTargetAttackGoalsMap.put(DynamicGameObject.ABSTRACT_TYPE_ENEMY, GOAL_WEIGHT_LOW);
			charLemmyUlfhednarGoalsMap.put(DynamicGameObject.ABSTRACT_TYPE_ENEMY_TARGET, GOAL_WEIGHT_HIGH);
			charLemmyUlfhednarGoalsMap.put(DynamicGameObject.ABSTRACT_TYPE_WEAPON_SWORD, GOAL_WEIGHT_DEFAULT);
			charLemmyUlfhednarGoalsMap.put(DynamicGameObject.ABSTRACT_TYPE_WEAPON_AXE, GOAL_WEIGHT_DEFAULT);
			charLemmyUlfhednarGoalsMap.put(DynamicGameObject.ABSTRACT_TYPE_WEAPON_SPEAR, GOAL_WEIGHT_DEFAULT);	
			
			//mode: friend protect
			charModeFriendProtectGoalsMap = new ArrayMap <Integer, Float>();
			charModeFriendProtectGoalsMap.put(GameObject.APPLE, GOAL_WEIGHT_DEFAULT);
			charModeFriendProtectGoalsMap.put(GameObject.MUSHROOM_BLUE, GOAL_WEIGHT_DEFAULT);
			charModeFriendProtectGoalsMap.put(GameObject.MUSHROOM_YELLOW, GOAL_WEIGHT_DEFAULT);
			charModeFriendProtectGoalsMap.put(GameObject.MUSHROOM_BLUE_RED, GOAL_WEIGHT_DEFAULT);
			charModeFriendProtectGoalsMap.put(GameObject.MUSHROOM_BROWN, GOAL_WEIGHT_DEFAULT);
			charModeFriendProtectGoalsMap.put(DynamicGameObject.ABSTRACT_TYPE_ENEMY, GOAL_WEIGHT_LOW);
			charModeFriendProtectGoalsMap.put(DynamicGameObject.ABSTRACT_TYPE_FRIEND_TO_PROTECT, GOAL_WEIGHT_HIGH);
			charModeFriendProtectGoalsMap.put(DynamicGameObject.ABSTRACT_TYPE_WEAPON_SWORD, GOAL_WEIGHT_DEFAULT);
			charModeFriendProtectGoalsMap.put(DynamicGameObject.ABSTRACT_TYPE_WEAPON_AXE, GOAL_WEIGHT_DEFAULT);
			charModeFriendProtectGoalsMap.put(DynamicGameObject.ABSTRACT_TYPE_WEAPON_SPEAR, GOAL_WEIGHT_DEFAULT);
			
			//mode: place protect
			charModePlaceProtectGoalsMap = new ArrayMap <Integer, Float>();
			charModePlaceProtectGoalsMap.put(GameObject.APPLE, GOAL_WEIGHT_DEFAULT);
			charModePlaceProtectGoalsMap.put(GameObject.MUSHROOM_BLUE, GOAL_WEIGHT_DEFAULT);
			charModePlaceProtectGoalsMap.put(GameObject.MUSHROOM_YELLOW, GOAL_WEIGHT_DEFAULT);
			charModePlaceProtectGoalsMap.put(GameObject.MUSHROOM_BLUE_RED, GOAL_WEIGHT_DEFAULT);
			charModePlaceProtectGoalsMap.put(GameObject.MUSHROOM_BROWN, GOAL_WEIGHT_DEFAULT);
			charModePlaceProtectGoalsMap.put(DynamicGameObject.ABSTRACT_TYPE_ENEMY, GOAL_WEIGHT_DEFAULT);
			charModePlaceProtectGoalsMap.put(DynamicGameObject.ABSTRACT_TYPE_PROTECTED_PLACE, GOAL_WEIGHT_HIGH);
			charModePlaceProtectGoalsMap.put(DynamicGameObject.ABSTRACT_TYPE_WEAPON_SWORD, GOAL_WEIGHT_DEFAULT);
			charModePlaceProtectGoalsMap.put(DynamicGameObject.ABSTRACT_TYPE_WEAPON_AXE, GOAL_WEIGHT_DEFAULT);
			charModePlaceProtectGoalsMap.put(DynamicGameObject.ABSTRACT_TYPE_WEAPON_SPEAR, GOAL_WEIGHT_DEFAULT);
			
			//mode: survive
			charModeSurviveGoalsMap = new ArrayMap <Integer, Float>();
			charModeSurviveGoalsMap.put(GameObject.APPLE, GOAL_WEIGHT_DEFAULT);
			charModeSurviveGoalsMap.put(GameObject.MUSHROOM_BLUE, GOAL_WEIGHT_DEFAULT);
			charModeSurviveGoalsMap.put(GameObject.MUSHROOM_YELLOW, GOAL_WEIGHT_DEFAULT);
			charModeSurviveGoalsMap.put(GameObject.MUSHROOM_BLUE_RED, GOAL_WEIGHT_DEFAULT);
			charModeSurviveGoalsMap.put(GameObject.MUSHROOM_BROWN, GOAL_WEIGHT_DEFAULT);
			charModeSurviveGoalsMap.put(DynamicGameObject.ABSTRACT_TYPE_ENEMY, GOAL_WEIGHT_DEFAULT);
			charModeSurviveGoalsMap.put(DynamicGameObject.ABSTRACT_TYPE_WEAPON_SWORD, GOAL_WEIGHT_DEFAULT);
			charModeSurviveGoalsMap.put(DynamicGameObject.ABSTRACT_TYPE_WEAPON_AXE, GOAL_WEIGHT_DEFAULT);
			charModeSurviveGoalsMap.put(DynamicGameObject.ABSTRACT_TYPE_WEAPON_SPEAR, GOAL_WEIGHT_DEFAULT);
		}
		
		public static float getWeightFromCharGoalsMap(int goal, int charType, int charLevel)
		{
			Float weight = null;
			
			if(charType == Statics.DynamicGameObject.SNAKE)
				weight = charSnakeBrotherGoalsMap.get(goal);
			else if (charType == Statics.DynamicGameObject.LEMMING)
			{
				switch(charLevel)
				{
				case HealthScore.LEVEL_PINK:
					weight = null;
					break;
				case HealthScore.LEVEL_GREEN:
					weight = charLemmyGoalsMap.get(goal);
					break;
				case HealthScore.LEVEL_BLUE:
					weight = charLemmyVikingGoalsMap.get(goal);
					break;
				case HealthScore.LEVEL_YELLOW:
					weight = charLemmyBerserkerGoalsMap.get(goal);
					break;
				case HealthScore.LEVEL_ORANGE:
					weight = charLemmyUlfhednarGoalsMap.get(goal);
					break;
				}
			}
			
			if(weight == null)
				return 0;
			else 
				return weight.floatValue();
		}
		
		public static float getWeightFromModeGoalsMap(int goal, int mode)
		{
			Float weight = null;
			

			switch(mode)
			{
			case Statics.AI.MODE_FREE_DEFAULT:
				weight = Statics.AI.GOAL_WEIGHT_DEFAULT;
				break;
			case Statics.AI.MODE_ENEMY_TARGET_ATTACK:
				weight = charModeEnemyTargetAttackGoalsMap.get(goal);
				break;
			case Statics.AI.MODE_FRIEND_PROTECT:
				weight = charModeFriendProtectGoalsMap.get(goal);
				break;
			case Statics.AI.MODE_PLACE_PROTECT:
				weight = charModePlaceProtectGoalsMap.get(goal);
				break;
			case Statics.AI.MODE_SURVIVE:
				weight = charModeSurviveGoalsMap.get(goal);
				break;
			}
			
			if(weight == null)
				return 0;
			else 
				return weight.floatValue();
		}
		
	}
}
