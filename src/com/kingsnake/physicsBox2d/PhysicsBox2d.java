package com.kingsnake.physicsBox2d;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.JointDef;
import com.badlogic.gdx.physics.box2d.JointEdge;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.DistanceJoint;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;
import com.badlogic.gdx.physics.box2d.joints.PrismaticJoint;
import com.badlogic.gdx.physics.box2d.joints.PrismaticJointDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.physics.box2d.joints.WeldJoint;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.badlogic.gdx.physics.box2d.joints.WheelJoint;
import com.badlogic.gdx.physics.box2d.joints.WheelJointDef;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.ObjectMap.Entry;
import com.example.framework.model.Armor;
import com.example.framework.model.DynamicEffect;
import com.example.framework.model.DynamicGameObject;
import com.example.framework.model.FightingSkill;
import com.example.framework.model.GameObject;
import com.example.framework.model.HealthScore;
import com.example.framework.model.Snake;
import com.example.framework.model.SnakePart;
import com.example.framework.model.Statics;
import com.example.framework.model.Statics.BodyData;
import com.example.framework.model.WorldKingSnake;
import com.example.framework.utils.SortUtils;
import com.kingsnake.math.MyMath;

public class PhysicsBox2d {	

	public static final short FIXTURE_PROPERTY1_INDEX_FIXTURE_TYPE = 0;
	public static final short FIXTURE_PROPERTY2_INDEX_MATERIAL = 1;
	public static final short FIXTURE_PROPERTY3_INDEX_HEALTH = 2;
	public static final short FIXTURE_PROPERTY4_INDEX_FIXTURE_INDEX = 3;
	
	static final float BORDER_WIDTH = Statics.PhysicsBox2D.BORDER_WIDTH;
	static final float CELL_SIZE = WorldKingSnake.CELL_SIZE;
	
	static final short FIXTURE_PROPERTY1_IS_ATTACK_PASSIVE = 0;
	static final short FIXTURE_PROPERTY1_IS_ATTACK_ACTIVE = 1;
	static final short FIXTURE_PROPERTY1_DEAD = 2;
	static final short FIXTURE_PROPERTY1_SENSOR_FOR_CORRECT_MOVING_FORCE = 3;
	static final short FIXTURE_PROPERTY1_SENSOR_FOR_FIGHT_SKILL = 4;
	static final short FIXTURE_PROPERTY1_SENSOR_DYN_EFFECT = 5; 
	static final short FIXTURE_PROPERTY1_PART_ARMOR = 6; 
	static final short FIXTURE_PROPERTY1_SENSOR_TONGUE = 7;
	
	static final short FIXTURE_PROPERTY4_SENSOR_CORRECT_MOVING_FORCE_UP = 0;
	static final short FIXTURE_PROPERTY4_SENSOR_CORRECT_MOVING_FORCE_DOWN = 1;
	
	static final short FIXTURE_PROPERTY4_SENSOR_FIGHT_SKILL_FORWARD = 0;
	static final short FIXTURE_PROPERTY4_SENSOR_FIGHT_SKILL_LEFT = 1;
	static final short FIXTURE_PROPERTY4_SENSOR_FIGHT_SKILL_RIGHT = 2;
	static final short FIXTURE_PROPERTY4_SENSOR_FIGHT_SKILL_BACK = 3;
	
	static final short FIXTURE_PROPERTY4_CHARACTER_JAWS = 20; 
	
	static final short FIXTURE_PROPERTY2_MATERIAL_MEAT = Statics.PhysicsBox2D.FIXTURE_MATERIAL_MEAT;
	static final float FIXTURE_MATERIAL_MEAT_HARDNESS = 0.5f;
	public static final float FIXTURE_MATERIAL_MEAT_BITE_RESISTANCE = HealthScore.MEAT_BITE_RESISTANCE;
	static final float FIXTURE_MATERIAL_MEAT_IMPULSE_RESISTANCE = 1f;
	public static final float FIXTURE_MATERIAL_MEAT_DENSITY = HealthScore.MEAT_DENSITY;
	
	static final short FIXTURE_PROPERTY2_MATERIAL_WOOD = Statics.PhysicsBox2D.FIXTURE_MATERIAL_WOOD;
	static final float FIXTURE_MATERIAL_WOOD_HARDNESS = 3f;
	public static final float FIXTURE_MATERIAL_WOOD_BITE_RESISTANCE = HealthScore.WOOD_BITE_RESISTANCE;
	static final float FIXTURE_MATERIAL_WOOD_IMPULSE_RESISTANCE = 2f;
	public static final float FIXTURE_MATERIAL_WOOD_DENSITY = HealthScore.WOOD_DENSITY;
	
	static final short FIXTURE_PROPERTY2_MATERIAL_ROCK = Statics.PhysicsBox2D.FIXTURE_MATERIAL_ROCK;
	static final float FIXTURE_MATERIAL_ROCK_HARDNESS = 21f;
	public static final float FIXTURE_MATERIAL_ROCK_BITE_RESISTANCE = HealthScore.ROCK_BITE_RESISTANCE;
	static final float FIXTURE_MATERIAL_ROCK_IMPULSE_RESISTANCE = 6f;
	public static final float FIXTURE_MATERIAL_ROCK_DENSITY = HealthScore.ROCK_DENSITY;
	
	static final short FIXTURE_PROPERTY2_MATERIAL_BRONZE = Statics.PhysicsBox2D.FIXTURE_MATERIAL_BRONZE;
	static final float FIXTURE_MATERIAL_BRONZE_HARDNESS = 9f;
	public static final float FIXTURE_MATERIAL_BRONZE_BITE_RESISTANCE = HealthScore.BRONZE_BITE_RESISTANCE;
	static final float FIXTURE_MATERIAL_BRONZE_IMPULSE_RESISTANCE = 9f;
	static final float FIXTURE_MATERIAL_BRONZE_DENSITY = HealthScore.BRONZE_DENSITY;
	
	static final short FIXTURE_PROPERTY2_MATERIAL_STEEL = Statics.PhysicsBox2D.FIXTURE_MATERIAL_STEEL;
	static final float FIXTURE_MATERIAL_STEEL_HARDNESS = 21f;
	public static final float FIXTURE_MATERIAL_STEEL_BITE_RESISTANCE = HealthScore.STEEL_BITE_RESISTANCE;
	static final float FIXTURE_MATERIAL_STEEL_IMPULSE_RESISTANCE = 21f;
	static final float FIXTURE_MATERIAL_STEEL_DENSITY = HealthScore.STEEL_DENSITY;
	
	static final short FIXTURE_PROPERTY2_MATERIAL_ADAMANT = Statics.PhysicsBox2D.FIXTURE_MATERIAL_ADAMANT;
	static final float FIXTURE_MATERIAL_ADAMANT_HARDNESS = 1600f;
	public static final float FIXTURE_MATERIAL_ADAMANT_BITE_RESISTANCE = HealthScore.ADAMANT_BITE_RESISTANCE;
	static final float FIXTURE_MATERIAL_ADAMANT_IMPULSE_RESISTANCE = 3f;
	static final float FIXTURE_MATERIAL_ADAMANT_DENSITY = HealthScore.ADAMANT_DENSITY;
	
	static final float FIXTURE_MATERIAL_ICE_HARDNESS = 2f;
	public static final float FIXTURE_MATERIAL_ICE_BITE_RESISTANCE = 0.7f;
	static final float FIXTURE_MATERIAL_ICE_IMPULSE_RESISTANCE = 0.2f;	
	public static final float FIXTURE_MATERIAL_ICE_DENSITY = 1f;
	
	static final float FIXTURE_MATERIAL_BITE_BASE_DEFFENSE = 50f;
	
	static final short JOINT_PROPERTY1_INDEX_JOINT_TYPE = 0;
	static final short JOINT_PROPERTY2_INDEX_JOINT_STRENGTH = 1;
	static final short JOINT_PROPERTY3_INDEX_JOINT_STATE = 2;
	static final short JOINT_PROPERTY4_INDEX_JOINT_INDEX = 3;
	
	static final short JOINT_PROPERTY1_BITEHOLD = 1;
	static final short JOINT_PROPERTY1_PENETRATION = 2;
	static final short JOINT_PROPERTY1_HELMHOLD = 3;
	static final short JOINT_PROPERTY1_SHIELDHOLD = 4;
	static final short JOINT_PROPERTY1_ARMDHOLD = 5;
	static final short JOINT_PROPERTY1_SNAKE_PARTS_JOINTS = 6; 
	static final short JOINT_PROPERTY1_MOUSE_JOINT = 7; 
	static final short JOINT_PROPERTY1_FREEZE_HOLD = 8; 
	static final short JOINT_COMMON_REVOLUTION_JOINT = 9; 
	
	static final short JOINT_PROPERTY3_STATE_ALIVE = 1;
	static final short JOINT_PROPERTY3_STATE_MUST_DESTROYED = 2;
	
	World world2d;
	WorldKingSnake world;
	MyMath myMath;
	MyContactListener cListener;
	MyDestructionListener dListener;
	List <RevoluteJoint> snakeJoints;
	List <PrismaticJoint>  penetrationJoints;	
	List<PrismaticJointDef> penetrationNewJointsQueue;
	ArrayMap <Body, Body> biteJointsQueue;
	List <WeldJoint> biteJoints;
	List <Fixture>  charMoveCorrectingForces;
	List <Entry<Fixture, Fixture>> charactersInWater;
	MouseJoint mJoint;
	CompoundBodiesDispatcher compBodyDispatcher;
	Array<Entry<Float, Object>> raySensorFixturesArray;
	
	float materialHardnness, materialBiteResist, materialImpulseResist, materialDensity, materialHealth;
	
    BodyDef bodyStaticDef;
    FixtureDef fixtureGiftDef;
    FixtureDef fixtureReliefDef;
    FixtureDef fixtureReliefSensorDef;
    
    BodyDef bodyDynDef;
    BodyDef bodyCharDef;
    FixtureDef fixtureDynDef;
	RevoluteJointDef jointSnakeDef;
	WeldJointDef jointBiteDef;
	WeldJointDef jointHelmHoldDef;
	short charsCounter;
	
	boolean tempChecker = false;
	
	public PhysicsBox2d(World world2d_, WorldKingSnake world_)
	{
		world2d = world2d_;
		world = world_;	
		myMath = new MyMath();
		
		materialHardnness = materialBiteResist = materialImpulseResist = materialDensity = materialHealth = 1;

		dListener = new MyDestructionListener(this);
		world2d.setDestructionListener(dListener); 
		
		cListener = new MyContactListener(world2d_, world_, this);
		world2d.setContactListener(cListener);
		
		compBodyDispatcher = new CompoundBodiesDispatcher(this);
		
		snakeJoints = new ArrayList<RevoluteJoint>();
		penetrationNewJointsQueue = new ArrayList <PrismaticJointDef>();
		penetrationJoints = new ArrayList <PrismaticJoint>();
		biteJointsQueue = new ArrayMap <Body, Body>();
		biteJoints = new ArrayList<WeldJoint>();
		charMoveCorrectingForces = new ArrayList<Fixture>();
		charactersInWater = new ArrayList <Entry<Fixture, Fixture>>();
		raySensorFixturesArray = new Array<Entry<Float, Object>>();
		
		bodyStaticDef = new BodyDef();
		fixtureGiftDef = new FixtureDef();
		fixtureReliefDef = new FixtureDef();
		fixtureReliefSensorDef = new FixtureDef();
    	fixtureReliefSensorDef.isSensor = true;
        
		bodyDynDef = new BodyDef();
		bodyDynDef.linearDamping = Statics.PhysicsBox2D.DYNOBJ_LINEAR_DAMPING;
		bodyDynDef.angularDamping = Statics.PhysicsBox2D.DYNOBJ_ANGUALR_DAMPING;
		bodyDynDef.type = BodyType.DynamicBody;
		fixtureDynDef = new FixtureDef();
		
		bodyCharDef = new BodyDef();
		bodyCharDef.linearDamping = Statics.PhysicsBox2D.CHAR_LINEAR_DAMPING;
		bodyCharDef.angularDamping = Statics.PhysicsBox2D.CHAR_ANGUALR_DAMPING;
		bodyCharDef.type = BodyType.DynamicBody;
		
		jointSnakeDef = new RevoluteJointDef();
		jointSnakeDef.enableLimit = true;
		jointSnakeDef.lowerAngle = (float) Math.toRadians(-55);
		jointSnakeDef.upperAngle = (float) Math.toRadians(55);	
		jointSnakeDef.maxMotorTorque = 0.1f; 
		jointSnakeDef.motorSpeed = 0.0f; 
		
		jointBiteDef = new WeldJointDef();
		jointHelmHoldDef = new WeldJointDef();		
		charsCounter = 0;
			
	}
	
	//here we create dyn objects for first hunting level
	public void initPhysicWorld_FirstHunting()
	{
       BodyDef bodyCircleDef = new BodyDef();
       bodyCircleDef.type = BodyType.DynamicBody;
       bodyCircleDef.position.set(7, 9);
       //bodyCircleDef.linearVelocity.set(-1, 0);
       Body body = world2d.createBody(bodyCircleDef);
        
        // Create a circle shape and set its radius to 6
        CircleShape circle = new CircleShape();
        circle.setRadius(1f);

        // Create a fixture definition to apply our shape to
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 0.5f; 
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.6f; // Make it bounce a little bit

        // Remember to dispose of any shapes after you're done with them!
        // BodyDef and FixtureDef don't need disposing, but shapes do.
        circle.dispose();
        
		MouseJointDef mJointDef = new MouseJointDef();
		mJointDef.bodyA = body;
		mJointDef.bodyB = body;
		mJointDef.maxForce = 10;
		mJointDef.target.set(body.getPosition());
		mJoint = (MouseJoint)world2d.createJoint(mJointDef);
		mJoint.setUserData(setJointUserData(JOINT_PROPERTY1_MOUSE_JOINT,  1000, JOINT_PROPERTY3_STATE_ALIVE, 0));

		
	}
	
	public void initMaterialProperties(Fixture fixture)
	{
		int material = (int) ((float[])fixture.getUserData())[FIXTURE_PROPERTY2_INDEX_MATERIAL];
		GameObject gObj = (GameObject)fixture.getBody().getUserData();;
		
		//if fixture is armor - get pointer to his armor object
		if( (int) ((float[])fixture.getUserData())[PhysicsBox2d.FIXTURE_PROPERTY1_INDEX_FIXTURE_TYPE] == FIXTURE_PROPERTY1_PART_ARMOR)
		{
			int fixIndex = (int) ((float[])fixture.getUserData())[PhysicsBox2d.FIXTURE_PROPERTY4_INDEX_FIXTURE_INDEX];
			gObj = ((SnakePart) gObj).armors.getArmorByFixtureIndex(fixIndex);
		}

		
		float level = 0;
		
		if(gObj.isDynamicObject)
			level = (((DynamicGameObject)gObj).stateHS.isStriking && ((DynamicGameObject)gObj).stateHS.strikeType == Statics.Render.STRIKE_JUMP) ? 
					((DynamicGameObject)gObj).stateHS.level * ((DynamicGameObject)gObj).stateHS.strikePower :
					((DynamicGameObject)gObj).stateHS.level;
		
		initMaterialProperties(material, (int)level);
	}
	
	public void initMaterialProperties(int material, int level)
	{
		//char resistance increase 100 - 600% by level
		//other materials increase 10 - 60% by level
		switch(material)
		{
		case FIXTURE_PROPERTY2_MATERIAL_MEAT:
			materialHardnness = FIXTURE_MATERIAL_MEAT_HARDNESS + level * FIXTURE_MATERIAL_MEAT_HARDNESS;
			materialBiteResist = FIXTURE_MATERIAL_MEAT_BITE_RESISTANCE + level *  FIXTURE_MATERIAL_MEAT_BITE_RESISTANCE;
			materialImpulseResist = FIXTURE_MATERIAL_MEAT_IMPULSE_RESISTANCE + level * FIXTURE_MATERIAL_MEAT_IMPULSE_RESISTANCE;
			materialDensity = FIXTURE_MATERIAL_MEAT_DENSITY + level * FIXTURE_MATERIAL_MEAT_DENSITY;
			materialHealth = HealthScore.POWER_PINK * (materialBiteResist / FIXTURE_MATERIAL_MEAT_BITE_RESISTANCE);
			break;
		case FIXTURE_PROPERTY2_MATERIAL_WOOD:
			materialHardnness = FIXTURE_MATERIAL_WOOD_HARDNESS + level * FIXTURE_MATERIAL_WOOD_HARDNESS / 10;
			materialBiteResist = FIXTURE_MATERIAL_WOOD_BITE_RESISTANCE + level * FIXTURE_MATERIAL_WOOD_BITE_RESISTANCE / 10;
			materialImpulseResist = FIXTURE_MATERIAL_WOOD_IMPULSE_RESISTANCE + level * FIXTURE_MATERIAL_WOOD_IMPULSE_RESISTANCE / 10;
			materialDensity = FIXTURE_MATERIAL_WOOD_DENSITY + level * FIXTURE_MATERIAL_WOOD_DENSITY;
			materialHealth = HealthScore.POWER_PINK * (materialBiteResist / FIXTURE_MATERIAL_MEAT_BITE_RESISTANCE);
			break;
		case Statics.PhysicsBox2D.FIXTURE_MATERIAL_ICE:
			materialHardnness = FIXTURE_MATERIAL_ICE_HARDNESS + level * FIXTURE_MATERIAL_ICE_HARDNESS / 10;
			materialBiteResist = FIXTURE_MATERIAL_ICE_BITE_RESISTANCE + level * FIXTURE_MATERIAL_ICE_BITE_RESISTANCE / 10;
			materialImpulseResist = FIXTURE_MATERIAL_ICE_IMPULSE_RESISTANCE + level * FIXTURE_MATERIAL_ICE_IMPULSE_RESISTANCE / 10;
			materialDensity = FIXTURE_MATERIAL_ICE_DENSITY + level * FIXTURE_MATERIAL_ICE_DENSITY;
			materialHealth = HealthScore.POWER_PINK * (materialBiteResist / FIXTURE_MATERIAL_MEAT_BITE_RESISTANCE);
			break;
		case FIXTURE_PROPERTY2_MATERIAL_ROCK:			
			materialHardnness = FIXTURE_MATERIAL_ROCK_HARDNESS + level * FIXTURE_MATERIAL_ROCK_HARDNESS / 10;
			materialBiteResist = FIXTURE_MATERIAL_ROCK_BITE_RESISTANCE + level * FIXTURE_MATERIAL_ROCK_BITE_RESISTANCE / 10;
			materialImpulseResist = FIXTURE_MATERIAL_ROCK_IMPULSE_RESISTANCE + level * FIXTURE_MATERIAL_ROCK_IMPULSE_RESISTANCE / 10;
			materialDensity = FIXTURE_MATERIAL_ROCK_DENSITY + level * FIXTURE_MATERIAL_ROCK_DENSITY;
			materialHealth = HealthScore.POWER_PINK * (materialBiteResist / FIXTURE_MATERIAL_MEAT_BITE_RESISTANCE);
			break;
		case FIXTURE_PROPERTY2_MATERIAL_BRONZE:
			materialHardnness = FIXTURE_MATERIAL_BRONZE_HARDNESS + level * FIXTURE_MATERIAL_BRONZE_HARDNESS / 10;
			materialBiteResist = FIXTURE_MATERIAL_BRONZE_BITE_RESISTANCE + level *FIXTURE_MATERIAL_BRONZE_BITE_RESISTANCE / 10;
			materialImpulseResist = FIXTURE_MATERIAL_BRONZE_IMPULSE_RESISTANCE + level * FIXTURE_MATERIAL_BRONZE_IMPULSE_RESISTANCE / 10;
			materialDensity = FIXTURE_MATERIAL_BRONZE_DENSITY + level * FIXTURE_MATERIAL_BRONZE_DENSITY;
			materialHealth = HealthScore.POWER_PINK * (materialBiteResist / FIXTURE_MATERIAL_MEAT_BITE_RESISTANCE);
			break;
		case FIXTURE_PROPERTY2_MATERIAL_STEEL:
			materialHardnness = FIXTURE_MATERIAL_STEEL_HARDNESS + level * FIXTURE_MATERIAL_STEEL_HARDNESS / 10;
			materialBiteResist = FIXTURE_MATERIAL_STEEL_BITE_RESISTANCE + level * FIXTURE_MATERIAL_STEEL_BITE_RESISTANCE / 10;
			materialImpulseResist = FIXTURE_MATERIAL_STEEL_IMPULSE_RESISTANCE + level * FIXTURE_MATERIAL_STEEL_IMPULSE_RESISTANCE / 10;
			materialDensity = FIXTURE_MATERIAL_STEEL_DENSITY + level * FIXTURE_MATERIAL_STEEL_DENSITY;
			materialHealth = HealthScore.POWER_PINK * (materialBiteResist / FIXTURE_MATERIAL_MEAT_BITE_RESISTANCE);
			break;
		case FIXTURE_PROPERTY2_MATERIAL_ADAMANT:
			materialHardnness = FIXTURE_MATERIAL_ADAMANT_HARDNESS + level * FIXTURE_MATERIAL_ADAMANT_HARDNESS / 10;
			materialBiteResist = FIXTURE_MATERIAL_ADAMANT_BITE_RESISTANCE + level * FIXTURE_MATERIAL_ADAMANT_BITE_RESISTANCE / 10;
			materialImpulseResist = FIXTURE_MATERIAL_ADAMANT_IMPULSE_RESISTANCE + level * FIXTURE_MATERIAL_ADAMANT_IMPULSE_RESISTANCE / 10;
			materialDensity = FIXTURE_MATERIAL_ADAMANT_DENSITY + level * FIXTURE_MATERIAL_ADAMANT_DENSITY;
			materialHealth = HealthScore.POWER_PINK * (materialBiteResist / FIXTURE_MATERIAL_MEAT_BITE_RESISTANCE);
			break;
		}		
	}	
	
	//here we create dyn objects
	void dispatchDynObjects()
	{
		
	}
	
	//here we create dyn objects
	public void updatePhysics(float deltaTime)
	{
		processOutOfWorld();
		snakeMove();
		automatedEffectsActionsManager(deltaTime);	//auto providing damage from water, fire ...
		automatedJointsManager();
		destroyAndDamageFixturesAutoProcessing();	//objects breaking and separating automatic process
	}
	
	public void processOutOfWorld()
	{
		Array <Body> bodies = new Array<Body>();
		world2d.getBodies(bodies);
		Body tBody;
		
		for(int i = 0; i < bodies.size; ++i)
		{
			tBody = bodies.get(i);
			
			Vector2 vector = new Vector2(); 
			vector = tBody.getPosition();
			
			if(vector.x >= WorldKingSnake.WORLD_WIDTH)
				tBody.setTransform(0, vector.y, tBody.getAngle());
			else if (vector.x < 0)
				tBody.setTransform( WorldKingSnake.WORLD_WIDTH, vector.y, tBody.getAngle());
			
			if(vector.y >= WorldKingSnake.WORLD_HEIGHT)
				tBody.setTransform(vector.x, 0, tBody.getAngle());
			else if (vector.y < 0)
				tBody.setTransform(vector.x, WorldKingSnake.WORLD_HEIGHT, tBody.getAngle());
		}
		
		bodies.clear();
	}
	
	
	void snakeMove()
	{
		int size = snakeJoints.size();
		
		for(int i = 0; i < size; ++i)
		{
			snakeJoints.get(i).setMotorSpeed((float) (2 * Math.sin( 2 * world.actTime * Math.PI -  0.5f *i * Math.PI)));
			
		}
			
	}
	
	
	
	public void updateMouseJoint(float x, float y)
	{
		Vector2 vector = new Vector2(); 
		vector.set(x, y);
		mJoint.setTarget(vector);
	}
	
	public void createGiftObject(GameObject gObj)
	{
		bodyStaticDef.position.set(gObj.position.x + CELL_SIZE/2, gObj.position.y + CELL_SIZE/2);
        Body bodyGift = world2d.createBody(bodyStaticDef);
        bodyGift.setUserData(gObj);
        gObj.myBody = bodyGift;
		
        CircleShape circle = new CircleShape();
        circle.setRadius(gObj.bounds.width * 0.5f);
        fixtureGiftDef.shape = circle;
        Fixture fixtureGift = bodyGift.createFixture(fixtureGiftDef); 
        fixtureGift.setUserData(setFixUserData(FIXTURE_PROPERTY1_IS_ATTACK_PASSIVE, 
    			FIXTURE_PROPERTY2_MATERIAL_ROCK, 0f, 0));
        circle.dispose();
	}
	
	float[] setFixUserData(short attackMode, short material, float health, int fixIndex)
	{
		float[] fixUserData = new float [4];
		fixUserData[FIXTURE_PROPERTY1_INDEX_FIXTURE_TYPE] = attackMode;
		fixUserData[FIXTURE_PROPERTY2_INDEX_MATERIAL] = material;
		fixUserData[FIXTURE_PROPERTY3_INDEX_HEALTH] = health;
		fixUserData[FIXTURE_PROPERTY4_INDEX_FIXTURE_INDEX] = fixIndex;
		
		return fixUserData;		
	}
	
	float[] setJointUserData(float type, float strength, float state, int index)
	{
		float[] jointUserData = new float [4];
		jointUserData[JOINT_PROPERTY1_INDEX_JOINT_TYPE] = type;
		jointUserData[JOINT_PROPERTY2_INDEX_JOINT_STRENGTH] = strength;
		jointUserData[JOINT_PROPERTY3_INDEX_JOINT_STATE] = state;
		jointUserData[JOINT_PROPERTY4_INDEX_JOINT_INDEX] = index;
		
		return jointUserData;		
	}
	
	/*public void createReliefObject(GameObject gObj)
	{
		bodyStaticDef.position.set(gObj.position.x + CELL_SIZE/2, gObj.position.y + CELL_SIZE/2);
        Body bodyRelief = world2d.createBody(bodyStaticDef);
        bodyRelief.setUserData(gObj);
        gObj.myBody = bodyRelief;
		
        PolygonShape box = new PolygonShape();
        
        if(gObj.type == WorldKingSnake.WATER || gObj.type == WorldKingSnake.SAND || gObj.type == WorldKingSnake.SWAMP)
        {
            box.setAsBox(gObj.bounds.width * 0.45f, gObj.bounds.width * 0.45f);
	        fixtureReliefSensorDef.shape = box;
	        Fixture fixtureRelief = bodyRelief.createFixture(fixtureReliefSensorDef);
        	fixtureRelief.setUserData(setFixUserData(FIXTURE_PROPERTY1_IS_ATTACK_PASSIVE, (short) 0, 0f, 0));
        }
        else
        {	
        	box.setAsBox(gObj.bounds.width * 0.4f, gObj.bounds.width * 0.4f);      
	        fixtureReliefDef.shape = box;
	        Fixture fixtureRelief = bodyRelief.createFixture(fixtureReliefDef); 
	        
	        if(gObj.type >= WorldKingSnake.ARROW_STONE && gObj.type <= WorldKingSnake.DEAD_STONE3)
	        	fixtureRelief.setUserData(setFixUserData(FIXTURE_PROPERTY1_IS_ATTACK_ACTIVE, 
	        			FIXTURE_PROPERTY2_MATERIAL_ROCK, 0f, 0));
	        else if(gObj.type >= WorldKingSnake.WALL && gObj.type <= WorldKingSnake.BROWN_TREE)
	        	fixtureRelief.setUserData(setFixUserData(FIXTURE_PROPERTY1_IS_ATTACK_PASSIVE, 
	        			FIXTURE_PROPERTY2_MATERIAL_WOOD, 0f, 0));
        }
        
        box.dispose();
	}*/
	
	public void createReliefObject(GameObject gObj)
	{
		bodyStaticDef.position.set(gObj.position.x + CELL_SIZE/2, gObj.position.y + CELL_SIZE/2);
        Body bodyRelief = world2d.createBody(bodyStaticDef);
        bodyRelief.setUserData(gObj);
        gObj.myBody = bodyRelief;
		
        CircleShape circelShape = new CircleShape();
        circelShape.setRadius(0.4f);
        
        if(gObj.type == WorldKingSnake.WATER || gObj.type == WorldKingSnake.SAND || gObj.type == WorldKingSnake.SWAMP)
        {
	        fixtureReliefSensorDef.shape = circelShape;
	        Fixture fixtureRelief = bodyRelief.createFixture(fixtureReliefSensorDef);
        	fixtureRelief.setUserData(setFixUserData(FIXTURE_PROPERTY1_IS_ATTACK_PASSIVE, (short) 0, 0f, 0));
        }
        else
        {	   
	        fixtureReliefDef.shape = circelShape;
	        Fixture fixtureRelief = bodyRelief.createFixture(fixtureReliefDef); 
	        
	        if(gObj.type >= WorldKingSnake.ARROW_STONE && gObj.type <= WorldKingSnake.DEAD_STONE3)
	        	fixtureRelief.setUserData(setFixUserData(FIXTURE_PROPERTY1_IS_ATTACK_ACTIVE, 
	        			FIXTURE_PROPERTY2_MATERIAL_ROCK, 0f, 0));
	        else if(gObj.type >= WorldKingSnake.WALL && gObj.type <= WorldKingSnake.BROWN_TREE)
	        	fixtureRelief.setUserData(setFixUserData(FIXTURE_PROPERTY1_IS_ATTACK_PASSIVE, 
	        			FIXTURE_PROPERTY2_MATERIAL_WOOD, 0f, 0));
        }
        
        circelShape.dispose();
	}
	
	public void createDynEffectSensor(DynamicEffect gObj, float radius, int sensorType)
	{
		BodyDef bodysensDef = new BodyDef();
		bodysensDef.type = BodyDef.BodyType.DynamicBody;
		bodysensDef .position.set(gObj.position.x, gObj.position.y);
		bodysensDef .angle = (float) Math.toRadians(gObj.angle);
        Body bodyDynEffectSensor = world2d.createBody(bodysensDef );
        bodyDynEffectSensor.setAngularDamping(0);
        bodyDynEffectSensor.setLinearDamping(0);
        bodyDynEffectSensor.setUserData(gObj);
        gObj.myBody = bodyDynEffectSensor;
		
        CircleShape circelShape = new CircleShape();
        circelShape.setRadius(radius);

	    FixtureDef fixtureSensorDef = new FixtureDef();
	    fixtureSensorDef.shape = circelShape;
	    
	    if(sensorType != Statics.DynamicGameObject.FSKILL_FORWARD_ATTACK_FIRE_EFFECT)
	    	fixtureSensorDef.isSensor = true;
	    
	    Fixture fixtureDynEffectSensor = bodyDynEffectSensor.createFixture(fixtureSensorDef);	
	    fixtureDynEffectSensor.setUserData(setFixUserData(FIXTURE_PROPERTY1_SENSOR_DYN_EFFECT, (short) 0, 0f, sensorType));	              
        circelShape.dispose();
	}
	
	public void destroyPhysicBody(GameObject gObj)
	{
		if(gObj.myBody != null)
		{
			world2d.destroyBody(gObj.myBody);
			gObj.myBody = null;
		}
	}
	
	public void setStaticObjectPos(GameObject gObj)
	{
		if(gObj.myBody != null)
		{
			Vector2 vector = new Vector2(); 
			vector.x = gObj.position.x + CELL_SIZE/2;
			vector.y = gObj.position.y + CELL_SIZE / 2;
			gObj.myBody.setTransform(vector, 0);
		}
	}
	
	
	public void createSnake(Snake snake)
	{
		// First we create a head;
		SnakePart sHead = snake.parts.get(0);
		
		bodyCharDef.position.set(sHead.position.x, sHead.position.y);
		bodyCharDef.angle = (float) Math.toRadians(snake.angle);
        Body bodyHead = world2d.createBody(bodyCharDef);
        bodyHead.setUserData(sHead);
        sHead.myBody = bodyHead;
        sHead.fixtureGroupFilterId = (short) (Statics.PhysicsBox2D.FILTER_GROUP_CHAR_START_RANGE - charsCounter);
        
        float density = sHead.stateHS.density;
               
		// Create a polygon  head
		PolygonShape snakeVerts = new PolygonShape();  
		snakeVerts.set(Statics.PhysicsBox2D.SNAKE_HEAD);	
		
		FixtureDef fixSnakeDef = new FixtureDef();
		fixSnakeDef.filter.groupIndex = (short) (Statics.PhysicsBox2D.FILTER_GROUP_CHAR_START_RANGE - charsCounter);
		fixSnakeDef.shape = snakeVerts;		
		fixSnakeDef.density = density;		
        bodyHead.createFixture(fixSnakeDef).setUserData(setFixUserData(FIXTURE_PROPERTY1_IS_ATTACK_PASSIVE, 
        		FIXTURE_PROPERTY2_MATERIAL_MEAT, 0f, 0));
        
		snakeVerts.set(Statics.PhysicsBox2D.SNAKE_MOUTH);	
		fixSnakeDef.shape = snakeVerts;			
        bodyHead.createFixture(fixSnakeDef).setUserData(setFixUserData(FIXTURE_PROPERTY1_IS_ATTACK_ACTIVE, 
        		FIXTURE_PROPERTY2_MATERIAL_MEAT, 0f, FIXTURE_PROPERTY4_CHARACTER_JAWS));
        
		Vector2 sensorVertices[] = new Vector2[5];
		
		for(int i = 0; i < 5; ++i)
			sensorVertices[i] = new Vector2();
		
		sensorVertices[0].set(-0.469f, 0);
		sensorVertices[1].set(-0.44f, -0.45f);		
		sensorVertices[2].set(0.1f, -0.45f);
		sensorVertices[3].set(0.6f, -0.1f);
		sensorVertices[4].set(0.6f, 0);
		snakeVerts.set(sensorVertices);
    
		FixtureDef fixSnakeSensorDef = new FixtureDef();		
		fixSnakeSensorDef.shape = snakeVerts;
		fixSnakeSensorDef.isSensor = true;
		fixSnakeSensorDef.filter.groupIndex = (short) (Statics.PhysicsBox2D.FILTER_GROUP_CHAR_START_RANGE - charsCounter);
        bodyHead.createFixture(fixSnakeSensorDef).setUserData(setFixUserData(FIXTURE_PROPERTY1_SENSOR_FOR_CORRECT_MOVING_FORCE, (short) 0, 0f, 
        		FIXTURE_PROPERTY4_SENSOR_CORRECT_MOVING_FORCE_DOWN));
        
		sensorVertices[0].set(-0.469f, 0);
		sensorVertices[1].set(-0.44f, 0.45f);		
		sensorVertices[2].set(0.1f, 0.45f);	
		sensorVertices[3].set(0.6f, 0.1f);
		sensorVertices[4].set(0.6f, 0);
		snakeVerts.set(sensorVertices);
        bodyHead.createFixture(fixSnakeSensorDef).setUserData(setFixUserData(FIXTURE_PROPERTY1_SENSOR_FOR_CORRECT_MOVING_FORCE, (short) 0, 0f, 
        		FIXTURE_PROPERTY4_SENSOR_CORRECT_MOVING_FORCE_UP));
               
        
		//create snake parts
        Vector2 partVertices[] = new Vector2[6];
		
		for(int i = 0; i < 6; ++i)
			partVertices[i] = new Vector2();
		
		partVertices[0].set(-0.438f, 0.281f);		
		partVertices[1].set(-0.438f, -0.281f);			
		partVertices[2].set(0.078f, -0.281f);		
		partVertices[3].set(0.406f, -0.047f);		
		partVertices[4].set(0.406f, 0.094f);		
		partVertices[5].set(0.125f, 0.281f);
		
		snakeVerts.set(partVertices);	
		fixSnakeDef.shape = snakeVerts;
		
		Vector2 vector = new Vector2(); 
		int size = snake.parts.size();
        CircleShape snakeSensorShape = new CircleShape();
        fixSnakeSensorDef.shape = snakeSensorShape; 
        snakeSensorShape.setRadius(0.1f);      
        snakeSensorShape.setPosition(vector.set(0.3f, 0));         
        bodyHead.createFixture(fixSnakeSensorDef).setUserData(setFixUserData(FIXTURE_PROPERTY1_SENSOR_TONGUE, (short) 0, 0f, 0));   
        
        snakeSensorShape.setRadius(0.3f);

		for(int i = 1; i < size; ++i)
		{
			SnakePart sPart = snake.parts.get(i);
			SnakePart sPartPrev = snake.parts.get(i - 1);	
	        sPart.fixtureGroupFilterId = (short) (Statics.PhysicsBox2D.FILTER_GROUP_CHAR_START_RANGE - charsCounter);
			
			bodyDynDef.position.set(sPart.position.x, sPart.position.y);
			Body bodyPart = world2d.createBody(bodyDynDef);
	        bodyPart.setUserData(sPart);
	        sPart.myBody = bodyPart;					
	        bodyPart.createFixture(fixSnakeDef).setUserData(setFixUserData(FIXTURE_PROPERTY1_IS_ATTACK_PASSIVE, 
	        		FIXTURE_PROPERTY2_MATERIAL_MEAT, 0, 0));
	        
	        snakeSensorShape.setPosition(vector.set(-0.3f, 0.12f));         
	        bodyPart.createFixture(fixSnakeSensorDef).setUserData(setFixUserData(FIXTURE_PROPERTY1_SENSOR_FOR_CORRECT_MOVING_FORCE, (short) 0, 0f, 
	        		FIXTURE_PROPERTY4_SENSOR_CORRECT_MOVING_FORCE_UP));   
	        snakeSensorShape.setPosition(vector.set(-0.3f, -0.12f));         
	        bodyPart.createFixture(fixSnakeSensorDef).setUserData(setFixUserData(FIXTURE_PROPERTY1_SENSOR_FOR_CORRECT_MOVING_FORCE, (short) 0, 0f, 
	        		FIXTURE_PROPERTY4_SENSOR_CORRECT_MOVING_FORCE_DOWN)); 
			
			//JOINTS
			vector = sPartPrev.myBody.getPosition().sub(sPart.myBody.getPosition());
			vector.scl(0.67f);
			vector = sPart.myBody.getPosition().add(vector);
			jointSnakeDef.initialize(sPartPrev.myBody, sPart.myBody, vector);
			RevoluteJoint joint = (RevoluteJoint)world2d.createJoint(jointSnakeDef);
			joint.setUserData(setJointUserData(JOINT_PROPERTY1_SNAKE_PARTS_JOINTS, 
					getJointStrengthSnakeParts(sPartPrev.stateHS.level, sPart.stateHS.level), JOINT_PROPERTY3_STATE_ALIVE, 0));
			snakeJoints.add(joint);				
		}
		
		Body bodyPart = world2d.createBody(bodyDynDef);
		Body bodyPart2 = world2d.createBody(bodyDynDef);
		jointSnakeDef.initialize(bodyPart, bodyPart2, bodyPart.getPosition());
		world2d.destroyBody(bodyPart);
		
		// Clean up after ourselves
		snakeVerts.dispose();
		snakeSensorShape.dispose();
		++charsCounter;
	}
	
	float getJointStrengthSnakeParts(int levelA, int level2)
	{
		return ((Statics.PhysicsBox2D.JOINT_FORCE_BASIC * (levelA + 1)) + (Statics.PhysicsBox2D.JOINT_FORCE_BASIC * (levelA + 1))) / 2; 
	}
	
	float getJointStrengthHold(int level)
	{
		return (Statics.PhysicsBox2D.JOINT_FORCE_HOLD_BASIC * (level + 1)); 		
	}
	
	public void createCharObject(DynamicGameObject dynObj)
	{
		bodyCharDef.position.set(dynObj.position.x, dynObj.position.y);
		bodyCharDef.angle = (float) Math.toRadians(dynObj.angle);
        Body bodyDyn = world2d.createBody(bodyCharDef);
        bodyDyn.setUserData(dynObj);
        dynObj.myBody = bodyDyn;
        dynObj.stateHS.helmOffset = dynObj.bounds.width /2;
        dynObj.fixtureGroupFilterId = (short) (Statics.PhysicsBox2D.FILTER_GROUP_CHAR_START_RANGE - charsCounter);

        PolygonShape rect = new PolygonShape();
		Vector2 vector = new Vector2(); 
        vector.set( -(dynObj.bounds.width /2 - dynObj.bounds.width * BORDER_WIDTH), 0);
        vector.x += (dynObj.bounds.width /2 - dynObj.bounds.width * BORDER_WIDTH) * 2 * 0.7f +
        		 	(dynObj.bounds.width /2 - dynObj.bounds.width * BORDER_WIDTH) * 0.3f;
        
        rect.setAsBox((dynObj.bounds.width /2 - dynObj.bounds.width * BORDER_WIDTH) * 0.3f,
        		dynObj.bounds.height / 2 - 3 * dynObj.bounds.height * BORDER_WIDTH, vector, 0);
        
        fixtureDynDef.shape = rect;
        fixtureDynDef.density = dynObj.stateHS.density ;
        fixtureDynDef.filter.groupIndex = (short) (Statics.PhysicsBox2D.FILTER_GROUP_CHAR_START_RANGE - charsCounter);
        bodyDyn.createFixture(fixtureDynDef).setUserData(setFixUserData(FIXTURE_PROPERTY1_IS_ATTACK_ACTIVE, 
        		FIXTURE_PROPERTY2_MATERIAL_MEAT, 0, FIXTURE_PROPERTY4_CHARACTER_JAWS));
        
        vector.set(-(dynObj.bounds.width /2 - dynObj.bounds.width * BORDER_WIDTH), 0);
        vector.x += (dynObj.bounds.width /2 - dynObj.bounds.width * BORDER_WIDTH) * 0.7f;
        
        rect.setAsBox((dynObj.bounds.width /2 - dynObj.bounds.width * BORDER_WIDTH) * 0.7f,
        		dynObj.bounds.height / 2 - 3 * dynObj.bounds.height * BORDER_WIDTH, vector, 0);
        
        fixtureDynDef.shape = rect;   
        bodyDyn.createFixture(fixtureDynDef).setUserData(setFixUserData(FIXTURE_PROPERTY1_IS_ATTACK_PASSIVE, 
        		FIXTURE_PROPERTY2_MATERIAL_MEAT, 0, 0));
        fixtureDynDef.filter.groupIndex = 0;
        rect.dispose();
		++charsCounter;
	}
	
	public void createDynObject(DynamicGameObject dynObj)
	{
	//passive dyn objects		
		bodyDynDef.position.set(dynObj.position.x, dynObj.position.y);
		bodyDynDef.angle = (float) Math.toRadians(dynObj.angle);
        Body bodyDyn = world2d.createBody(bodyDynDef);
        bodyDyn.setUserData(dynObj);
        dynObj.myBody = bodyDyn;

        PolygonShape rect = new PolygonShape();
        float bodyWidth = dynObj.bounds.width / 2 -  dynObj.bounds.width * BORDER_WIDTH / 2;
        float bodyHeight = dynObj.bounds.height /2 - dynObj.bounds.height * BORDER_WIDTH / 2;
       
        if(Statics.DynamicGameObject.isCompoundBody(dynObj.objType))
        	compBodyDispatcher.createCompoundBody(dynObj, bodyDyn, null);
        else 
        {
        	if(dynObj.objType == DynamicGameObject.BOMB_FROM_TREE)
        	{
	            rect.setAsBox(bodyWidth, bodyHeight);
	            fixtureDynDef.shape = rect;
	            fixtureDynDef.density = dynObj.stateHS.density;
	        	bodyDyn.createFixture(fixtureDynDef).setUserData(setFixUserData(FIXTURE_PROPERTY1_IS_ATTACK_PASSIVE, 
	        		FIXTURE_PROPERTY2_MATERIAL_ROCK, 0, 0)); 
        	}
        }
        
        if(dynObj.objType == Statics.DynamicGameObject.FSKILL_ICEBALL)
        	provideFreezeJoints(dynObj);
        
        rect.dispose();
	}
	
	void provideFreezeJoints(DynamicGameObject freezeObj)
	{
		float xEpicenter = freezeObj.position.x;
		float yEpicenter = freezeObj.position.y;
		float freezeRadius = freezeObj.bounds.width / 2;
		
		MyQueryCallback qRegion = new MyQueryCallback(world2d, world, this);
		qRegion.setTask(MyQueryCallback.FSKILL_ICE);
		qRegion.setDataForFreeze(xEpicenter, yEpicenter, freezeObj);
		world2d.QueryAABB(qRegion, xEpicenter - freezeRadius, yEpicenter - freezeRadius, xEpicenter + freezeRadius, yEpicenter + freezeRadius);	
	}
	
	public void addFSkillSensor(DynamicGameObject dynObj, String sType)
	{
		FixtureDef fixSensorDef = new FixtureDef();		
		fixSensorDef.isSensor = true;
        CircleShape sensorShape = new CircleShape();
        fixSensorDef.shape = sensorShape;	
		Vector2 vector = new Vector2(); 
		
		if(sType == Statics.FightingSkills.SENSOR_FORWARD)
		{      
		    sensorShape.setRadius(Statics.PhysicsBox2D.SENSOR_FIGHT_SKILL_FORWARD_RADIUS);  
		    sensorShape.setPosition(vector.set(Statics.PhysicsBox2D.SENSOR_FIGHT_SKILL_FORWARD_CENTER_POS_X, Statics.PhysicsBox2D.SENSOR_FIGHT_SKILL_FORWARD_CENTER_POS_Y));         
		    dynObj.myBody.createFixture(fixSensorDef).setUserData(setFixUserData(FIXTURE_PROPERTY1_SENSOR_FOR_FIGHT_SKILL, (short) 0, 0f, FIXTURE_PROPERTY4_SENSOR_FIGHT_SKILL_FORWARD)); 
		}
		else if(sType == Statics.FightingSkills.SENSOR_LEFT_SIDE)
		{      
		    sensorShape.setRadius(Statics.PhysicsBox2D.SENSOR_FIGHT_SKILL_SIDE_RADIUS);  
		    sensorShape.setPosition(vector.set(Statics.PhysicsBox2D.SENSOR_FIGHT_SKILL_LEFT_CENTER_POS_X, Statics.PhysicsBox2D.SENSOR_FIGHT_SKILL_LEFT_CENTER_POS_Y));         
		    dynObj.myBody.createFixture(fixSensorDef).setUserData(setFixUserData(FIXTURE_PROPERTY1_SENSOR_FOR_FIGHT_SKILL, (short) 0, 0f, FIXTURE_PROPERTY4_SENSOR_FIGHT_SKILL_LEFT)); 
		}
		else if(sType == Statics.FightingSkills.SENSOR_RIGHT_SIDE)
		{      
		    sensorShape.setRadius(Statics.PhysicsBox2D.SENSOR_FIGHT_SKILL_SIDE_RADIUS);  
		    sensorShape.setPosition(vector.set(Statics.PhysicsBox2D.SENSOR_FIGHT_SKILL_RIGHT_CENTER_POS_X, Statics.PhysicsBox2D.SENSOR_FIGHT_SKILL_RIGHT_CENTER_POS_Y));         
		    dynObj.myBody.createFixture(fixSensorDef).setUserData(setFixUserData(FIXTURE_PROPERTY1_SENSOR_FOR_FIGHT_SKILL, (short) 0, 0f, FIXTURE_PROPERTY4_SENSOR_FIGHT_SKILL_RIGHT)); 
		}
		else if(sType == Statics.FightingSkills.SENSOR_BACK)
		{      
		    sensorShape.setRadius(Statics.PhysicsBox2D.SENSOR_FIGHT_SKILL_BACK_RADIUS);  
		    sensorShape.setPosition(vector.set(Statics.PhysicsBox2D.SENSOR_FIGHT_SKILL_BACK_CENTER_POS_X, Statics.PhysicsBox2D.SENSOR_FIGHT_SKILL_BACK_CENTER_POS_Y));               
		    dynObj.myBody.createFixture(fixSensorDef).setUserData(setFixUserData(FIXTURE_PROPERTY1_SENSOR_FOR_FIGHT_SKILL, (short) 0, 0f, FIXTURE_PROPERTY4_SENSOR_FIGHT_SKILL_BACK)); 
		}
		
		sensorShape.dispose();
	}
	
	public void fSkillRayCast(DynamicGameObject dynObj,  Vector2 targetPoint)
	{
		raySensorFixturesArray.clear();
		MyRayCastCallback ray = new MyRayCastCallback(world2d, world, this, dynObj, raySensorFixturesArray);
		world2d.rayCast(ray, new Vector2 (dynObj.position.x, dynObj.position.y), targetPoint);
		SortUtils.quickSortFloatObjArray(raySensorFixturesArray);
	}
			
	public void implementCharMoveForce(DynamicGameObject dynObj)
	{
		if(dynObj.myBody == null || dynObj.stateHS.isHeated || dynObj.stateHS.isHypnosed)
			return;	
		
		Vector2 forceVector = new Vector2(dynObj.velocity.x, dynObj.velocity.y);
		Vector2 posVector = new Vector2(dynObj.bounds.width /2 - dynObj.bounds.width * BORDER_WIDTH, 0);
		
		forceVector.scl(dynObj.myBody.getMass());	//scale force by mass, to get the char acceleration
												//proportional to his velocity but not to his mass		
		if(dynObj.isTurn)
		{		
			dynObj.myBody.setAngularVelocity(0);
			dynObj.myBody.setLinearVelocity(0, 0);
			dynObj.isTurn = false;		
		}
		
		float distNexTurn = dynObj.myBody.getPosition().dst(dynObj.xNextTurn, dynObj.yNextTurn);
		float distOldTurn = dynObj.myBody.getPosition().dst(dynObj.xOldTurn, dynObj.yOldTurn);
			
		//if we in correcting placement to cell center mode our force must be small
		if (dynObj.isPlaceToCellCenterMovingMode == true)
			forceVector.scl(dynObj.toMiddleOfCellVelKoef);
		
		else if(distNexTurn < CELL_SIZE)
			forceVector.scl(dynObj.beforeTurnVelKoef);
		else if(distOldTurn < 0.7 * CELL_SIZE)
			forceVector.scl(dynObj.beforeTurnVelKoef);
		else
			forceVector.scl(dynObj.applyForceCharMovingKoef);
		
		//apply force around charcater's nose		
		posVector.scl(0.95f);
		posVector = dynObj.myBody.getWorldPoint(posVector);	
		dynObj.myBody.applyForce(forceVector, posVector, true);
		dynObj.forcePoint.set(posVector.x, posVector.y);
	}
	
	
	public void implementSnakeMoveForce(DynamicGameObject dynObj)
	{
		if(dynObj.myBody == null || dynObj.stateHS.isHeated || dynObj.stateHS.isHypnosed)
			return;	
		
		Vector2 forceVector = new Vector2();
		Vector2 posVector = new Vector2();
		
		forceVector.set(dynObj.velocity.x, dynObj.velocity.y);
		forceVector.scl(dynObj.myBody.getMass());	//scale force by mass, to get the char acceleration
													//proportional to his velocity but not to his mass
		int size = ((SnakePart)dynObj).snake.parts.size();
		//make force independent from snake parts count
		forceVector.scl(dynObj.applyForceCharMovingKoef * size / Statics.DynamicGameObject.SNAKE_PINK_SIZE);
		
		//apply force around charcater's nose
		posVector.set(dynObj.bounds.width /2 - dynObj.bounds.width * BORDER_WIDTH, 0);
		
		posVector.scl(0.95f);
		posVector = dynObj.myBody.getWorldPoint(posVector);	
		dynObj.myBody.applyForce(forceVector, posVector, true);
		
		forceVector.scl(0.4F);
		
		//parts forces
		for(int i = 0; i < size; ++i)
		{
			SnakePart sPart = ((SnakePart)dynObj).snake.parts.get(i);
			
			if(sPart.stateHS.isDead == HealthScore.ALIVE)
			{
				posVector.set(sPart.myBody.getWorldCenter());			
				forceVector.setAngleRad(sPart.myBody.getAngle());				
				sPart.myBody.applyForce(forceVector, posVector, true);				
			}					
		}
	}
	
	
	public void implementSnakeMoveCorrectingForce(DynamicGameObject dynObj)
	{	
		Vector2 forceVector = new Vector2();
		Vector2 posVector = new Vector2();
		
		forceVector.set(((SnakePart)dynObj).snake.stateHS.velocity, 0);
		forceVector.scl(dynObj.myBody.getMass());	//scale force by mass, to get the char acceleration
													//proportional to his velocity but not to his mass
		forceVector.scl(dynObj.applyForceCharMovingKoef);

		int size = ((SnakePart)dynObj).snake.parts.size();
		//make force independent from snake parts count
		forceVector.scl(dynObj.applyForceCharMovingKoef * size / Statics.DynamicGameObject.SNAKE_PINK_SIZE);
		forceVector.scl(Statics.PhysicsBox2D.CORRECT_POWER_TO_MAIN_POWER_KOEF);
	
		for(int i = 0; i < size; ++i)
		{
			SnakePart sPart = ((SnakePart)dynObj).snake.parts.get(i);
			int correctForceAct = sPart.getCorrectForceDir(world.actTime);
			
			if(sPart.stateHS.isDead == HealthScore.ALIVE && correctForceAct != Statics.PhysicsBox2D.CORRECT_POWER_NO)
			{							
				//correcting forces	
				posVector.set(sPart.myBody.getWorldCenter());			
				forceVector.setAngleRad(sPart.myBody.getAngle());
					
				if(correctForceAct > Statics.PhysicsBox2D.CORRECT_POWER_NO)
					forceVector.rotate(90);
				else if(correctForceAct < Statics.PhysicsBox2D.CORRECT_POWER_NO)
					forceVector.rotate(-90);
							
				sPart.myBody.applyForce(forceVector, posVector, true);
				sPart.forcePoint.set(posVector.x + forceVector.x, posVector.y + forceVector.y);
			}
			else
			{
				sPart.forcePoint.set(0, 0);				
			}
		}
	}
	
	public void snakeForwardJumpImpulse(DynamicGameObject dynObj, Vector2 targetPos)
	{	
		Vector2 forceVector = new Vector2();
		Vector2 posVector = new Vector2();
		Vector2 direction = new Vector2(targetPos);
		direction.sub(dynObj.position.x, dynObj.position.y);
		
		forceVector.set(Statics.PhysicsBox2D.JUMP_FORWARD_FORCE, 0);
		forceVector.scl(dynObj.myBody.getMass());	//scale force by mass, to get the char acceleration
													//proportional to his velocity but not to his mass
		int size = ((SnakePart)dynObj).snake.parts.size();
		//make force independent from snake parts count
		forceVector.scl(Statics.PhysicsBox2D.JUMP_FORWARD_IMPULSE_TO_MAIN_FORCE_KOEF * dynObj.applyForceCharMovingKoef * size 
				/ Statics.DynamicGameObject.SNAKE_PINK_SIZE);
		forceVector.setAngle(direction.angle());
		
		//apply force head's center of mass	
		posVector.set(dynObj.myBody.getWorldCenter());	
		dynObj.myBody.applyLinearImpulse(forceVector, posVector, true);	
	}
	
	public void forwardJumpImpulse(DynamicGameObject dynObj, Vector2 targetPos)
	{	
		Vector2 forceVector = new Vector2();
		Vector2 posVector = new Vector2();
		Vector2 direction = new Vector2(targetPos);
		direction.sub(dynObj.position.x, dynObj.position.y);
		
		forceVector.set(Statics.PhysicsBox2D.JUMP_FORWARD_FORCE, 0);
		forceVector.scl(dynObj.myBody.getMass());	//scale force by mass, to get the char acceleration
													//proportional to his velocity but not to his mass
		forceVector.scl(Statics.PhysicsBox2D.JUMP_FORWARD_IMPULSE_TO_MAIN_FORCE_KOEF * dynObj.applyForceCharMovingKoef);
		forceVector.setAngle(direction.angle());
		
		//apply force head's center of mass	
		posVector.set(dynObj.myBody.getWorldCenter());	
		dynObj.myBody.applyLinearImpulse(forceVector, posVector, true);	
	}
	
	public void snakeForwardHookImpulse(DynamicGameObject dynObj, Vector2 targetPos, boolean isLeft, float maxPath)
	{	
		Vector2 forceVector = new Vector2();
		Vector2 posVector = new Vector2();
		Vector2 direction = new Vector2(targetPos);
		direction.sub(dynObj.position.x, dynObj.position.y);
		
		forceVector.set(Statics.PhysicsBox2D.JUMP_FORWARD_FORCE, 0);
		forceVector.scl(dynObj.myBody.getMass());	//scale force by mass, to get the char acceleration
													//proportional to his velocity but not to his mass
		int size = ((SnakePart)dynObj).snake.parts.size();
		//make force independent from snake parts count
		forceVector.scl(Statics.PhysicsBox2D.JUMP_FORWARD_HOOK_IMPULSE_TO_MAIN_FORCE_KOEF * dynObj.applyForceCharMovingKoef * size 
				/ Statics.DynamicGameObject.SNAKE_PINK_SIZE);	
		forceVector.scl(maxPath / Statics.FightingSkills.SENSOR_FORWARD_SENS_START_PATH);
		forceVector.setAngle(direction.angle());
		
		if(isLeft)
			forceVector.rotate(60);
		else
			forceVector.rotate(-60);
		
		//apply force head's center of mass	
		posVector.set(dynObj.myBody.getWorldCenter());	
		dynObj.myBody.applyLinearImpulse(forceVector, posVector, true);	
	}
	
	public void forwardHookImpulse(DynamicGameObject dynObj, Vector2 targetPos, boolean isLeft, float maxPath)
	{	
		Vector2 forceVector = new Vector2();
		Vector2 posVector = new Vector2();
		Vector2 direction = new Vector2(targetPos);
		direction.sub(dynObj.position.x, dynObj.position.y);
		
		forceVector.set(Statics.PhysicsBox2D.JUMP_FORWARD_FORCE, 0);
		forceVector.scl(dynObj.myBody.getMass());	//scale force by mass, to get the char acceleration
													//proportional to his velocity but not to his mass
		forceVector.scl(Statics.PhysicsBox2D.JUMP_FORWARD_HOOK_IMPULSE_TO_MAIN_FORCE_KOEF * dynObj.applyForceCharMovingKoef);	
		forceVector.scl(maxPath / Statics.FightingSkills.SENSOR_FORWARD_SENS_START_PATH);
		forceVector.setAngle(direction.angle());
		
		if(isLeft)
			forceVector.rotate(60);
		else
			forceVector.rotate(-60);
		
		//apply force head's center of mass	
		posVector.set(dynObj.myBody.getWorldCenter());	
		dynObj.myBody.applyLinearImpulse(forceVector, posVector, true);	
	}
	
	public void snakeForwardJump(DynamicGameObject dynObj, Vector2 targetPos, boolean isForward)
	{			
		Vector2 forceVector = new Vector2();
		Vector2 posVector = new Vector2();
		Vector2 direction = new Vector2(targetPos);
		direction.sub(dynObj.position.x, dynObj.position.y);
		
		forceVector.set(Statics.PhysicsBox2D.JUMP_FORWARD_FORCE, 0);
		forceVector.scl(dynObj.myBody.getMass());	//scale force by mass, to get the char acceleration
													//proportional to his velocity but not to his mass
		int size = ((SnakePart)dynObj).snake.parts.size();
		//make force independent from snake parts count
		forceVector.scl(Statics.PhysicsBox2D.JUMP_FORWARD_FORCE_TO_MAIN_FORCE_KOEF * dynObj.applyForceCharMovingKoef * size 
				/ Statics.DynamicGameObject.SNAKE_PINK_SIZE);
		forceVector.setAngle(direction.angle());
		
		if(isForward)
		{
			//apply force around charcater's nose
			posVector.set(dynObj.bounds.width /2 - dynObj.bounds.width * BORDER_WIDTH, 0);
			posVector.scl(0.95f);
		}
		else
		{
			//apply force around charcater's ass
			posVector.set(-(dynObj.bounds.width /2 - dynObj.bounds.width * BORDER_WIDTH), 0);
			posVector.scl(0.8f);			
		}
		
		posVector = dynObj.myBody.getWorldPoint(posVector);	
		dynObj.myBody.applyForce(forceVector, posVector, true);
		
		forceVector.scl(0.4F);
		
		//parts forces
		for(int i = 0; i < size; ++i)
		{
			SnakePart sPart = ((SnakePart)dynObj).snake.parts.get(i);
			
			if(sPart.stateHS.isDead == HealthScore.ALIVE)
			{
				posVector.set(sPart.myBody.getWorldCenter());			
				forceVector.setAngleRad((float) (isForward ? sPart.myBody.getAngle() : sPart.myBody.getAngle() + Math.PI));				
				sPart.myBody.applyForce(forceVector, posVector, true);				
			}					
		}
	}
	
	public void snakeForwardHook(DynamicGameObject dynObj, Vector2 startPos, Vector2 targetPos, boolean isLeft, float maxPath, boolean isForward)
	{			
		Vector2 forceVector = new Vector2();
		Vector2 posVector = new Vector2();
		Vector2 direction = new Vector2(targetPos);
		direction.set(targetPos);
		direction.sub(startPos.x, startPos.y);
		
		//find circle center
		float xCenter = (targetPos.x + startPos.x) / 2;
		float yCenter = (targetPos.y + startPos.y) / 2;
		
		//find k, b coefficients of straight between start and end points of hook trajectory
		float k1 = targetPos.x == startPos.x ? 1000 :(targetPos.y - startPos.y) / (targetPos.x - startPos.x);
		
		//find k, b coefficients of ORT straight passing our current position and orthogonal to straight before
		float k = k1 == 0 ? 0.01f : - 1 / k1;
		float b = dynObj.position.y - k * dynObj.position.x;
		
		float A = k;
		float B = -1;
		float C = b;
		float R = (float) Math.sqrt((xCenter - startPos.x) * (xCenter - startPos.x) + (yCenter - startPos.y) * (yCenter - startPos.y)); 
		
		//shift coordinate center to circle center
		C -= (yCenter - k * xCenter);
		
		//get point of interlace of ORT straight and hook circle
		Vector2 tangentPoint = myMath.getLineCircleIntersectionPoint(R, A, B, C, dynObj.position.x, dynObj.position.y, direction, isLeft);
		
		if(tangentPoint == null)
			//our position is far out from our hook trajectory, so return
			return;
		
		//back to general coordinates
		tangentPoint.x += xCenter;
		tangentPoint.y += yCenter;

		//find tangent vector to circle in interlace point
		float bTang = tangentPoint.y == yCenter ? 0.01f : tangentPoint.y - yCenter;
		float kTangent = - (tangentPoint.x - xCenter) / bTang;
		float angleTangent =  (float) Math.toDegrees(Math.atan(kTangent));
		
		if(angleTangent < 0)
			angleTangent = 360f + angleTangent;
		
		Vector2 tangent = new Vector2 (1, 0);
		tangent.setAngle(angleTangent);
		
		//provide tangent angle accord to our direction 
		if(Math.abs(direction.angle(tangent)) > 90)
			angleTangent += 180;		
		
		
		forceVector.set(Statics.PhysicsBox2D.JUMP_FORWARD_FORCE, 0);
		forceVector.scl(dynObj.myBody.getMass());	//scale force by mass, to get the char acceleration
													//proportional to his velocity but not to his mass
		int size = ((SnakePart)dynObj).snake.parts.size();
		//make force independent from snake parts count
		forceVector.scl(Statics.PhysicsBox2D.JUMP_FORWARD_HOOK_FORCE_TO_MAIN_FORCE_KOEF * dynObj.applyForceCharMovingKoef * size 
				/ Statics.DynamicGameObject.SNAKE_PINK_SIZE);	
		//add fskill power koef to force 
		float skillPowerKoef = maxPath / Statics.FightingSkills.SENSOR_FORWARD_SENS_START_PATH;
		forceVector.scl(skillPowerKoef);	
		forceVector.setAngle(angleTangent);
		
		//find vector from our position to interlace point
		Vector2 deltaPos = new Vector2(tangentPoint);
		deltaPos.sub(dynObj.position.x, dynObj.position.y);
		deltaPos.scl(forceVector.len());
		
		direction.set(forceVector);		
		direction.add(deltaPos);
		forceVector.setAngle(direction.angle());
		
		if(isForward)
		{
			//apply force around charcater's nose
			posVector.set(dynObj.bounds.width /2 - dynObj.bounds.width * BORDER_WIDTH, 0);
			posVector.scl(0.95f);
		}
		else
		{
			//apply force around charcater's ass
			posVector.set(-(dynObj.bounds.width /2 - dynObj.bounds.width * BORDER_WIDTH), 0);
			posVector.scl(0.8f);			
		}
		
		posVector = dynObj.myBody.getWorldPoint(posVector);			
		dynObj.myBody.applyForce(forceVector, posVector, true);
		
		forceVector.scl(0.4F);
		
		//parts forces
		for(int i = 0; i < size; ++i)
		{
			SnakePart sPart = ((SnakePart)dynObj).snake.parts.get(i);
			
			if(sPart.stateHS.isDead == HealthScore.ALIVE)
			{
				posVector.set(sPart.myBody.getWorldCenter());			
				forceVector.setAngleRad((float) (isForward ? sPart.myBody.getAngle() : sPart.myBody.getAngle() + Math.PI));		
				sPart.myBody.applyForce(forceVector, posVector, true);				
			}					
		}
	}
	
	public void forwardHook(DynamicGameObject dynObj, Vector2 startPos, Vector2 targetPos, boolean isLeft, float maxPath)
	{			
		Vector2 forceVector = new Vector2();
		Vector2 posVector = new Vector2();
		Vector2 direction = new Vector2(targetPos);
		direction.set(targetPos);
		direction.sub(startPos.x, startPos.y);
		
		//find circle center
		float xCenter = (targetPos.x + startPos.x) / 2;
		float yCenter = (targetPos.y + startPos.y) / 2;
		
		//find k, b coefficients of straight between start and end points of hook trajectory
		float k1 = targetPos.x == startPos.x ? 1000 :(targetPos.y - startPos.y) / (targetPos.x - startPos.x);
		
		//find k, b coefficients of ORT straight passing our current position and orthogonal to straight before
		float k = k1 == 0 ? 0.01f : - 1 / k1;
		float b = dynObj.position.y - k * dynObj.position.x;
		
		float A = k;
		float B = -1;
		float C = b;
		float R = (float) Math.sqrt((xCenter - startPos.x) * (xCenter - startPos.x) + (yCenter - startPos.y) * (yCenter - startPos.y)); 
		
		//shift coordinate center to circle center
		C -= (yCenter - k * xCenter);
		
		//get point of interlace of ORT straight and hook circle
		Vector2 tangentPoint = myMath.getLineCircleIntersectionPoint(R, A, B, C, dynObj.position.x, dynObj.position.y, direction, isLeft);
		
		if(tangentPoint == null)
			//our position is far out from our hook trajectory, so return
			return;
		
		//back to general coordinates
		tangentPoint.x += xCenter;
		tangentPoint.y += yCenter;

		//find tangent vector to circle in interlace point
		float bTang = tangentPoint.y == yCenter ? 0.01f : tangentPoint.y - yCenter;
		float kTangent = - (tangentPoint.x - xCenter) / bTang;
		float angleTangent =  (float) Math.toDegrees(Math.atan(kTangent));
		
		if(angleTangent < 0)
			angleTangent = 360f + angleTangent;
		
		Vector2 tangent = new Vector2 (1, 0);
		tangent.setAngle(angleTangent);
		
		//provide tangent angle accord to our direction 
		if(Math.abs(direction.angle(tangent)) > 90)
			angleTangent += 180;		
		
		forceVector.set(Statics.PhysicsBox2D.JUMP_FORWARD_FORCE, 0);
		forceVector.scl(dynObj.myBody.getMass());	//scale force by mass, to get the char acceleration
													//proportional to his velocity but not to his mass
		forceVector.scl(Statics.PhysicsBox2D.JUMP_FORWARD_HOOK_FORCE_TO_MAIN_FORCE_KOEF * dynObj.applyForceCharMovingKoef);	
		//add fskill power koef to force 
		float skillPowerKoef = maxPath / Statics.FightingSkills.SENSOR_FORWARD_SENS_START_PATH;
		forceVector.scl(skillPowerKoef);	
		forceVector.setAngle(angleTangent);
		
		//find vector from our position to interlace point
		Vector2 deltaPos = new Vector2(tangentPoint);
		deltaPos.sub(dynObj.position.x, dynObj.position.y);
		deltaPos.scl(forceVector.len());
		
		direction.set(forceVector);		
		direction.add(deltaPos);
		forceVector.setAngle(direction.angle());
		
		//apply force around charcater's nose
		posVector.set(dynObj.bounds.width /2 - dynObj.bounds.width * BORDER_WIDTH, 0);		
		posVector.scl(0.95f);
		posVector = dynObj.myBody.getWorldPoint(posVector);
		dynObj.myBody.applyForce(forceVector, posVector, true);
	}

	
	public void forwardJump(DynamicGameObject dynObj, Vector2 targetPos)
	{	
		Vector2 forceVector = new Vector2();
		Vector2 posVector = new Vector2();
		Vector2 direction = new Vector2(targetPos);
		direction.sub(dynObj.position.x, dynObj.position.y);
		
		forceVector.set(dynObj.velocity.x, dynObj.velocity.y);
		forceVector.scl(dynObj.myBody.getMass());	//scale force by mass, to get the char acceleration
													//proportional to his velocity but not to his mass
		forceVector.scl(Statics.PhysicsBox2D.JUMP_FORWARD_FORCE_TO_MAIN_FORCE_KOEF * dynObj.applyForceCharMovingKoef);
		forceVector.setAngle(direction.angle());	
		
		//apply force around charcater's nose
		posVector.set(dynObj.bounds.width /2 - dynObj.bounds.width * BORDER_WIDTH, 0);
		
		posVector.scl(0.95f);
		posVector = dynObj.myBody.getWorldPoint(posVector);	
		dynObj.myBody.applyLinearImpulse(forceVector, posVector, true);			
	}
	
	
	/*public void implementSnakeMoveForce(DynamicGameObject dynObj)
	{
		if(dynObj.myBody == null || dynObj.stateHS.isHeated || dynObj.stateHS.isHypnosed)
			return;
		
		Vector2 forceVector = vector;
		Vector2 posVector = vector2;
		
		forceVector.set(dynObj.velocity.x, dynObj.velocity.y);
		forceVector.scl(dynObj.myBody.getMass());	//scale force by mass, to get the char acceleration
												//proportional to his velocity but not to his mass
		forceVector.scl(dynObj.applyForceCharMovingKoef);
		
		//apply force around charcater's nose
		posVector.set(dynObj.bounds.width /2 - dynObj.bounds.width * BORDER_WIDTH, 0);
		
		posVector.scl(0.95f);
		posVector.rotate((float) Math.toDegrees(dynObj.myBody.getAngle()));
		posVector.add(dynObj.myBody.getPosition());		
		dynObj.myBody.applyForce(forceVector, posVector, true);
		
		int size = ((SnakePart)dynObj).snake.parts.size();
		//forceVector.scl(Statics.PhysicsBox2D.CORRECT_POWER_TO_MAIN_POWER_KOEF);
		forceVector.scl(0.3F);
		
		//parts forces
		for(int i = 0; i < size; ++i)
		{
			SnakePart sPart = ((SnakePart)dynObj).snake.parts.get(i);
			posVector.set(sPart.myBody.getPosition());
			posVector.add(sPart.myBody.getLocalCenter());			
			forceVector.setAngleRad(sPart.myBody.getAngle());
			sPart.myBody.applyForce(forceVector, posVector, true);
			
			if(sPart.stateHS.isDead == HealthScore.ALIVE)
			{
				//correcting forces
				if( sPart.correctForceAct > Statics.PhysicsBox2D.CORRECT_POWER_NO)
				{


					Log.d("Correcting Force", "INDEX PART=" + String.valueOf(sPart.index));
					
					if(sPart.correctForceAct == Statics.PhysicsBox2D.CORRECT_POWER_UP)
						forceVector.rotate(90);
					else if(sPart.correctForceAct == Statics.PhysicsBox2D.CORRECT_POWER_DOWN)
						forceVector.rotate(-90);
							
					sPart.myBody.applyForce(forceVector, posVector, true);
					sPart.forcePoint.set(posVector.x + forceVector.x, posVector.y + forceVector.y);
				}
			}					
		}
	}*/

	
	public void destroyAndDamageFixturesAutoProcessing()
	{
		Array <Body> bodies = new Array <Body>();
		world2d.getBodies(bodies);
		
		for(int i = 0; i < bodies.size; ++i)
		{
			Body body = bodies.get(i);
			GameObject gObj = (GameObject) body.getUserData();			
			
			if(gObj != null && gObj.isDynamicObject && ((DynamicGameObject)gObj).isCharacter)
			{
				Array <Fixture> fixtures = new Array <Fixture>();
				fixtures = body.getFixtureList();
				
				//gather damages from all fixtures and put it to obj
				for(int j = 0; j < fixtures.size; ++j)
				{
					Fixture fixture = fixtures.get(j);
					
					if(fixture.isSensor())
						continue;
					
					//char fixture
					//char health: init.: (type, level) -> stateHS.health, defensePower; fixture.health = 0 and gather all damages to fixture.health
					//and here we move damages from fix.health to char.healthDecrease
					if(((float[])fixture.getUserData())[FIXTURE_PROPERTY1_INDEX_FIXTURE_TYPE] == FIXTURE_PROPERTY1_IS_ATTACK_ACTIVE
							|| ((float[])fixture.getUserData())[FIXTURE_PROPERTY1_INDEX_FIXTURE_TYPE] == FIXTURE_PROPERTY1_IS_ATTACK_PASSIVE)
					{					
						((DynamicGameObject)gObj).stateHS.healthDecrease -= ((float[])fixture.getUserData())[FIXTURE_PROPERTY3_INDEX_HEALTH];
						((float[])fixture.getUserData())[FIXTURE_PROPERTY3_INDEX_HEALTH] = 0;					
					}
					//other char parts, armor for example. Parts are breakable
					else
					{
						//armor
						//armor health: init.: stateHS.health, defPower = initMaterialProps(Statics.getObjectMaterial(objType), obj.stateHS.level).materialHealth; 
						//fix.health = 0 and gather all damages to fixture.health
						//and here we move damages from fix.health to armor.healthDecrease
						if(((float[])fixture.getUserData())[FIXTURE_PROPERTY1_INDEX_FIXTURE_TYPE] == FIXTURE_PROPERTY1_PART_ARMOR)
						{
							int fixIndex = (int) ((float[])fixture.getUserData())[PhysicsBox2d.FIXTURE_PROPERTY4_INDEX_FIXTURE_INDEX];
							Armor armor = ((SnakePart) gObj).armors.getArmorByFixtureIndex(fixIndex);
						
							armor.stateHS.healthDecrease -= ((float[])fixture.getUserData())[FIXTURE_PROPERTY3_INDEX_HEALTH];
							((float[])fixture.getUserData())[FIXTURE_PROPERTY3_INDEX_HEALTH] = 0;	
													
							//if damages > health - dyn obj will be destroyed, so let's break it on parts:
							if(armor.stateHS.healthDecrease > armor.stateHS.health)
							{	
								int indexStart = armor.isLeft ? Statics.PhysicsBox2D.SNAKE_WOOD_ARMOR_LEFT_FIXTURE_START_INDEX : Statics.PhysicsBox2D.SNAKE_WOOD_ARMOR_RIGHT_FIXTURE_START_INDEX;
								
								for(int k = 0; k < fixtures.size; ++k)
								{
									//all fixtures of current armor are dead, separate them and link to the dead bodies
									Fixture fixtureDestroy = fixtures.get(k);
									
									if(((float[])fixtureDestroy.getUserData())[FIXTURE_PROPERTY1_INDEX_FIXTURE_TYPE] == FIXTURE_PROPERTY1_PART_ARMOR 
											&& ((float[])fixtureDestroy.getUserData())[FIXTURE_PROPERTY4_INDEX_FIXTURE_INDEX] - indexStart >= 0 
											&& ((float[])fixtureDestroy.getUserData())[FIXTURE_PROPERTY4_INDEX_FIXTURE_INDEX] - indexStart < Statics.PhysicsBox2D.SNAKE_ARMOR_MAX_PARTS_COUNT)
									{
										createDeadPart(body, fixtureDestroy, armor.type, armor);
										--k;
									}
								}
							}
						}
					}									
				}						
			}
			else if(gObj != null && gObj.isDynamicObject && ((DynamicGameObject)gObj).isBreakable())
			{				
				//dyn obj can is breakable and may has a few fixtures. so process them all
				//breakable health: init.: stateHS.health, defPower = initMaterialProps(Statics.getObjectMaterial(objType), obj.stateHS.level).materialHealth; 
				//fix.health = 0 and gather all damages to fixture.health
				//and here we move damages from fix.health to armor.healthDecrease
				Array <Fixture> fixtures = new Array <Fixture>();
				fixtures = body.getFixtureList();
				
				//gather damages from all fixtures and put it to obj
				for(int j = 0; j < fixtures.size; ++j)
				{
					Fixture fixture = fixtures.get(j);
					//healthDecrease must be positive, so as fixture[index-health] is negative we use '-=' operator 
					((DynamicGameObject)gObj).stateHS.healthDecrease -= ((float[])fixture.getUserData())[FIXTURE_PROPERTY3_INDEX_HEALTH];
					((float[])fixture.getUserData())[FIXTURE_PROPERTY3_INDEX_HEALTH] = 0;					
				}
				
				//if damages > health - dyn obj will be destroyed, so let's break it on parts:
				if(((DynamicGameObject)gObj).stateHS.healthDecrease > ((DynamicGameObject)gObj).stateHS.health)
				{	
					if(fixtures.size > 1)
						while(fixtures.size > 0)
							//all fixtures are dead, separate them and link to the dead body
							createDeadPart(body, fixtures.get(0), ((DynamicGameObject)gObj).objType, null);	
					else
						createDeadParts(body, (DynamicGameObject)gObj);							
				}
			}
			else if(gObj != null && gObj.isDynamicObject && ((DynamicGameObject)gObj).isSeparetable())
			{	
				//dyn obj is separatable and may has a few fixtures. so process them all
				//separateble health: init.: stateHS.health, defPower = 0; 
				//fix.health = initMaterialProps(Statics.getObjectMaterial(objType), obj.stateHS.level).materialHealth and decreases from damages;
				//and here we check fix.health and separate dead fixes
				Array <Fixture> fixtures = new Array <Fixture>();
				fixtures = body.getFixtureList();
				
				for ( int j = 0; j < fixtures.size; ++j)
				{
					Fixture fixture = fixtures.get(j);
					float[] fixData = (float[])fixture.getUserData();
					//if fixture is dead, separate it and link to the dead body
					if(fixData[FIXTURE_PROPERTY3_INDEX_HEALTH] <= 0)
					{
						((DynamicGameObject)gObj).renderParts[(int) fixData[FIXTURE_PROPERTY4_INDEX_FIXTURE_INDEX]] = 0;
						createDeadPart(body, fixture, ((DynamicGameObject)gObj).objType, null);
						--j;
					}
				}
				
				if(fixtures.size == 0)
				{
					((DynamicGameObject)gObj).stateHS.health = 0;
					((DynamicGameObject)gObj).stateHS.healthDecrease = 1;
				}
			}
		}				
	}
	
	public void createDeadPart(Body body, Fixture fix, int objType, GameObject gObj)
	{       
        DynamicGameObject deadPartObj;
    	int partsNumber = Statics.PhysicsBox2D.getObjPartsFixturesCount(objType);
        
        Vector2 deadPartPos = new Vector2(body.getPosition());
        Vector2 offset = new Vector2(0, 0);
        
        float width = 1;
        float height = 1;
        
        if(objType >= Statics.DynamicGameObject.BEGIN_OF_BLOCKS_STANDART_RANGE &&
        		objType <= Statics.DynamicGameObject.END_OF_BLOCKS_STANDART_RANGE)
        {
        	width = height = Statics.Render.BLOCK_STANDART_PART_SIZE;
        	offset.set(-Statics.Render.BLOCK_STANDART_PART_OFFSET, Statics.Render.BLOCK_STANDART_PART_OFFSET);
        	
        	//rotate our offset vector on -90* for each next part 
        	offset.rotate(90 * ((float[])fix.getUserData())[FIXTURE_PROPERTY4_INDEX_FIXTURE_INDEX]).angle();        	 
      	   
        }
        else if(Statics.DynamicGameObject.isWeapon(objType))
        {
        	int weaponClass = Statics.DynamicGameObject.getWeaponClass(objType);
        	
            if(weaponClass == Statics.DynamicGameObject.ABSTRACT_TYPE_WEAPON_ARMOR)
            {
            	width =  Statics.Render.SNAKE_WOOD_ARMOR_WIDTH;
            	height =  Statics.Render.SNAKE_WOOD_ARMOR_WIDTH;
            }
            else if(weaponClass == Statics.DynamicGameObject.ABSTRACT_TYPE_WEAPON_HELM)
            {
            	if(partsNumber == Statics.INDEX_2)
            	{
            	}
            }      	
        }
       
    	//this is our part center point
        deadPartPos.add(offset);  
    	
        bodyDynDef.position.set(deadPartPos.x, deadPartPos.y);        
        Body bodyDeadDyn = world2d.createBody(bodyDynDef);
        bodyDeadDyn.setAngularVelocity(body.getAngularVelocity());
        bodyDeadDyn.setLinearVelocity(body.getLinearVelocity());       

        deadPartObj = world.wProc.CreateDeadPart(deadPartPos.x, deadPartPos.y, width, height);
        //remember whose dead part is and index of part for rendering
        deadPartObj.type = objType;
        deadPartObj.lifetimePeriod = Statics.PhysicsBox2D.DEAD_PART_ALIFIE_BEFORE_DESTROYING_PERIOD;
        //isMoving holds fixture index for render
        deadPartObj.isMoving = (int) ((float[])fix.getUserData())[FIXTURE_PROPERTY4_INDEX_FIXTURE_INDEX];
        
        bodyDeadDyn.setUserData(deadPartObj);
        deadPartObj.myBody = bodyDeadDyn;

        fixtureDynDef.shape = fix.getShape();
        fixtureDynDef.density = fix.getDensity();
        fixtureDynDef.friction = fix.getFriction();
        fixtureDynDef.restitution = fix.getRestitution();
       
        Fixture deadFix = bodyDeadDyn.createFixture(fixtureDynDef);
        deadFix.setUserData(fix.getUserData());
        ((float[])deadFix.getUserData())[FIXTURE_PROPERTY1_INDEX_FIXTURE_TYPE] = FIXTURE_PROPERTY1_DEAD;
        body.destroyFixture(fix);	        
	}
	
	public void createDeadParts(Body body, DynamicGameObject dynObj)
	{       
        DynamicGameObject deadPartObj;      
        Vector2 pos = new Vector2(body.getPosition());
        
        float width = dynObj.bounds.width;
        float height = dynObj.bounds.height; 
        bodyDynDef.position.set(pos.x, pos.y);   
        
        BodyData bData = Statics.DynamicGameObject.getBodyData(dynObj.objType);
		PolygonShape deadPartShape = new PolygonShape();  
        
        for(int i = 0; i < bData.getNParts(); ++i)
        {
            Body bodyDeadDyn = world2d.createBody(bodyDynDef);
            bodyDeadDyn.setAngularVelocity(body.getAngularVelocity());
            bodyDeadDyn.setLinearVelocity(body.getLinearVelocity()); 
            
            deadPartObj = world.wProc.CreateDeadPart(pos.x, pos.y, width, height);
            //remember whose dead part is and index of part for rendering
            deadPartObj.type = dynObj.objType;
            deadPartObj.lifetimePeriod = Statics.PhysicsBox2D.DEAD_PART_ALIFIE_BEFORE_DESTROYING_PERIOD;
            //isMoving holds fixture index for render
            deadPartObj.isMoving = i;           
            bodyDeadDyn.setUserData(deadPartObj);
            deadPartObj.myBody = bodyDeadDyn;         
			
            deadPartShape.set(bData.getVerts()[i]);				
			FixtureDef fixDeadPartDef = new FixtureDef();
			fixDeadPartDef.shape = deadPartShape;	
			initMaterialProperties(bData.getMaterials()[i], dynObj.stateHS.level);
			fixDeadPartDef.density = materialDensity;         
            Fixture deadFix = bodyDeadDyn.createFixture(fixDeadPartDef);
            deadFix.setUserData(setFixUserData(FIXTURE_PROPERTY1_DEAD, (short)bData.getMaterials()[i],  0, i));         
        } 
	}
	
	public void physicalExplosion(float power, float xEpicenter, float yEpicenter) 
	{
		float explosionRadius = (float) (Math.sqrt(power) / 3);
		MyQueryCallback qRegion = new MyQueryCallback(world2d, world, this);
		qRegion.setTask(MyQueryCallback.TREE_BOMB_EXPLOSION);
		qRegion.setDataForTreeBombExplosion(power, xEpicenter, yEpicenter);
		world2d.QueryAABB(qRegion, xEpicenter - explosionRadius, yEpicenter - explosionRadius, xEpicenter + explosionRadius, yEpicenter + explosionRadius);
	}
	
	public void fSkillImpulse(float power,  float damage,  float impulseRadius, DynamicGameObject master) 
	{
		float xEpicenter = master.position.x;
		float yEpicenter = master.position.y;
		float angle = master.angle;
		
		MyQueryCallback qRegion = new MyQueryCallback(world2d, world, this);
		qRegion.setTask(MyQueryCallback.FSKILL_IMPULSE);
		qRegion.setDataForFImpulse(power, xEpicenter, yEpicenter, damage, angle, master);
		world2d.QueryAABB(qRegion, xEpicenter - impulseRadius, yEpicenter - impulseRadius, xEpicenter + impulseRadius, yEpicenter + impulseRadius);
	}
	
	public void fSkillSideImpulse(float power,  float damage,  float impulseRadius, DynamicGameObject master, boolean isLeft) 
	{
		float xEpicenter = master.position.x;
		float yEpicenter = master.position.y;
		float angle = master.angle + (isLeft ? 90 : -90);
		
		MyQueryCallback qRegion = new MyQueryCallback(world2d, world, this);
		qRegion.setTask(MyQueryCallback.FSKILL_IMPULSE);
		qRegion.setDataForFImpulse(power, xEpicenter, yEpicenter, damage, angle, master);
		world2d.QueryAABB(qRegion, xEpicenter - impulseRadius, yEpicenter - impulseRadius, xEpicenter + impulseRadius, yEpicenter + impulseRadius);
	}
	
	public void createArmorFixture(int type, boolean isLeft, DynamicGameObject master, Armor armor)
	{
		compBodyDispatcher.createCompoundBody(armor, master.myBody, master);
	}
	
	public void setJawsSate(Body myBody, boolean open)
	{
		Array <Fixture> fixList = myBody.getFixtureList();
		int size = fixList.size;
		float[] userData = null;
		
		for(int i = 0; i < size; ++i)
		{
			Fixture fixture = fixList.get(i);
			userData = (float[]) fixture.getUserData();
			
			if(userData[FIXTURE_PROPERTY1_INDEX_FIXTURE_TYPE] == FIXTURE_PROPERTY1_IS_ATTACK_ACTIVE &&
					userData[FIXTURE_PROPERTY4_INDEX_FIXTURE_INDEX] == FIXTURE_PROPERTY4_CHARACTER_JAWS)
				fixture.setSensor(open);
		}
	}
	
	public void addBiteHoldToQeue(Body myBody, Body body)
	{	
		biteJointsQueue.put(myBody, body);
	}
	
	void automatedJointsDestroyBiteJoints()
	{
		//destroy needed bite joints:
		Body myBody = null;
		Joint joint = null;
		float[] userData = null;
		DynamicGameObject dynObj = null;
		
		for(int i = 0; i < biteJoints.size(); ++i)
		{
			joint = biteJoints.get(i);		
			
			//if joint was destroyed implicit - remove it from list and cotinue
			if(joint.getUserData() == null)
			{
				biteJoints.remove(i);
				--i;	
				continue;
			}
			
			myBody = joint.getBodyA();			
			dynObj = ((DynamicGameObject)myBody.getUserData());
			
			if(dynObj.objType == Statics.DynamicGameObject.SNAKE)
				dynObj = ((SnakePart)dynObj).snake;
			
			if(dynObj.stateHS.isOpenJaws)
			{
				world2d.destroyJoint(joint);
				biteJoints.remove(i);
				--i;
				
				Array <Fixture> fixList = myBody.getFixtureList();
				int size = fixList.size;
				
				for(int j = 0; j < size; ++j)
				{
					Fixture fixture = fixList.get(j);
					userData = (float[]) fixture.getUserData();
					
					if(userData[FIXTURE_PROPERTY1_INDEX_FIXTURE_TYPE] == FIXTURE_PROPERTY1_SENSOR_TONGUE)
					{
						userData[FIXTURE_PROPERTY3_INDEX_HEALTH] = world.actTime;
						break;
					}
				}				
			}
		}
		
	}
	
	void automatedJointsDestroyOverloadedJoints()
	{
		Array<Joint> joints = new Array<Joint>();
		world2d.getJoints(joints);
		Iterator<Joint> iteratorJoints = joints.iterator();
		
		while(iteratorJoints.hasNext())
		{
			Joint joint = iteratorJoints.next();
			
			if(joint == null)
				continue;				
			
			Body bodyA = joint.getBodyA();
			Body bodyB = joint.getBodyB();
			
			if(bodyA == null || bodyB == null)
				continue;
			
			float reactForce = joint.getReactionForce(1).len();
			float jointStrength = ((float[])joint.getUserData())[JOINT_PROPERTY2_INDEX_JOINT_STRENGTH];
			
			if(jointStrength > 0 && reactForce > jointStrength)
				world2d.destroyJoint(joint);			
		}		
	}
	
	void automatedJointsDestroyPenetrationJoints()
	{
		for (int i = 0; i < penetrationJoints.size(); ++i)
		{
			PrismaticJoint joint = penetrationJoints.get(i);
			
			if(joint == null)
			{
				penetrationJoints.remove(i);
				--i;
				continue;				
			}
			
			Body bodyA = joint.getBodyA();
			Body bodyB = joint.getBodyB();
			
			if(bodyA == null || bodyB == null)
			{
				penetrationJoints.remove(i);
				--i;
				continue;
			}
			
			if(!SortUtils.overlapBodies(bodyA, bodyB))
			{
				world2d.destroyJoint(joint);
				penetrationJoints.remove(i);
				--i;			
			}
		}			
	}
	
	void automatedJointsCreateBiteJoints()
	{
		//create bite joints from queue:		
		int size = biteJointsQueue.size;
		Body myBody = null;
		Body body = null;
		DynamicGameObject dynObj = null;
		Vector2 point = new Vector2();
	
		for(int i = 0; i < size; ++i)
		{	
			myBody = biteJointsQueue.getKeyAt(i);
			body = biteJointsQueue.getValueAt(i);
			dynObj = (DynamicGameObject) myBody.getUserData();
			
			if(dynObj.objType == Statics.DynamicGameObject.SNAKE)
				dynObj = ((SnakePart)dynObj).snake;
			
			point.set(0.3f, 0);
			point.setAngleRad(myBody.getAngle());
			point.add(myBody.getPosition());
			
			float jointStrength = (dynObj.stateHS.level + 1) * Statics.PhysicsBox2D.JOINT_FORCE_HOLD_BASIC;
			
			jointBiteDef.initialize(myBody, body, point);
			Joint jointTemp = world2d.createJoint(jointBiteDef);
			jointTemp.setUserData(setJointUserData(JOINT_PROPERTY1_BITEHOLD, jointStrength, JOINT_PROPERTY3_STATE_ALIVE, 0));
			biteJoints.add((WeldJoint)jointTemp);
			 
			setJawsSate(myBody, false);			
		}		
		biteJointsQueue.clear();	
		
	}
	
	void automatedJointsCreatePenetrationJoints()
	{
		Iterator<PrismaticJointDef> iterator = penetrationNewJointsQueue.iterator();
		
		while(iterator.hasNext())
			processNewPentration(iterator.next());		
		
		penetrationNewJointsQueue.clear();		
	}
	
	public void automatedEffectsActionsManager(float deltaTime)
	{
		float actTime = world.actTime;
		float oldTime = actTime - deltaTime;
		
		
		int intActTime = (int) (actTime / Statics.PhysicsBox2D.EFFECTS_ACTIONS_UPDATE_FREQUENCY);
		int intOldTime = (int) (oldTime / Statics.PhysicsBox2D.EFFECTS_ACTIONS_UPDATE_FREQUENCY);
		
		if(intOldTime != intActTime)
		{
			automatedEffectsActionWaterDamageTochar();
		}
	}
	
	public void automatedEffectsActionWaterDamageTochar()
	{	
		Fixture oldCharacter = null;
		
		for (int i = 0; i < charactersInWater.size(); ++i)
		{
			Entry<Fixture, Fixture> entry = charactersInWater.get(i);
			
			if(entry.key.getUserData() == null || entry.value.getUserData() == null)
			{
				charactersInWater.remove(i);
				--i;
				continue;
			}
			else if(!SortUtils.overlapFixtures(entry.key, entry.value))
			{
				charactersInWater.remove(i);
				--i;
				continue;
			}
			else
			{		
				//don't implement multiple damage to char fixture if it's placed at a few water cells at same time
				if(!entry.key.equals(oldCharacter))
					((float[])entry.key.getUserData())[PhysicsBox2d.FIXTURE_PROPERTY3_INDEX_HEALTH] -= Statics.Static.WATER_DAMAGE;
			}
			oldCharacter = entry.key;
		}
	}
	
	
	public void addCharToWater(Fixture character, Fixture water)
	{
		Entry <Fixture, Fixture> entry = new Entry<Fixture, Fixture>();
		entry.key = character;
		entry.value = water;
		charactersInWater.add(entry);		
		SortUtils.quickSortList(charactersInWater);
	}
	
	public boolean isCharInWater(Fixture charFix, Fixture waterFix)
	{	
		for (Entry <Fixture, Fixture> entry: charactersInWater)
		{		
			if(charFix.equals(entry.key) && waterFix.equals(entry.value))
				return true;
		}
		
		return false;
	}
	
	
	public void automatedJointsManager()
	{
		automatedJointsDestroyOverloadedJoints();
		automatedJointsDestroyPenetrationJoints();
		automatedJointsDestroyBiteJoints();
		
		automatedJointsCreatePenetrationJoints();
		automatedJointsCreateBiteJoints();		
	}
	
	public void processPhysicalHelmHolding(DynamicGameObject master, Body body)
	{		
		Body myBody = master.myBody;
		setWeaponFilterByMaster(master, body);
		
		float helmOffset = master.stateHS.helmOffset;
		Array<JointEdge> jointsEdges = body.getJointList();

		while(jointsEdges.size > 0)
			world2d.destroyJoint(jointsEdges.get(0).joint);
		
		float angleRad = myBody.getAngle();
		Vector2 helmPos = new Vector2(helmOffset, 0);
		helmPos.setAngleRad(angleRad);
		helmPos.add(myBody.getPosition());
		body.setTransform(helmPos, (float) (angleRad - Math.PI / 2));		
		float jointStrength = (master.stateHS.level + 1) * Statics.PhysicsBox2D.JOINT_FORCE_HOLD_BASIC;		
		jointHelmHoldDef.initialize(myBody, body, helmPos);
		world2d.createJoint(jointHelmHoldDef).setUserData(setJointUserData(JOINT_PROPERTY1_HELMHOLD, jointStrength, JOINT_PROPERTY3_STATE_ALIVE, 0));		
	}
	
	public void processPhysicalShieldHolding(DynamicGameObject master, Body body)
	{		
		Body masterBody = master.myBody;
		setWeaponFilterByMaster(master, body);
		
		float shieldOffset = master.bounds.height / 4;
		float holdOffset = Statics.PhysicsBox2D.WEAPON_SHIELD_HOLD_JOINT_OFFSET;
		destroyBodyJoints(body);
		
		float angleRad = masterBody.getAngle();
		Vector2 shieldPos = new Vector2(shieldOffset, 0);	
		shieldPos.setAngleRad((float) (angleRad - Math.PI));
		shieldPos.add(masterBody.getPosition());	
		body.setTransform(shieldPos, (float) (angleRad - Math.PI / 2));
		
		Vector2 holdPos1 = new Vector2(holdOffset, 0);		
		holdPos1.setAngleRad((float) (angleRad + Math.PI / 2));	
		holdPos1.add(shieldPos);
		
		Vector2 holdPos2 = new Vector2(holdOffset + Statics.PhysicsBox2D.WEAPON_SHIELD_HOLD_JOINT_LENGTH, 0);
		holdPos2.setAngleRad((float) (angleRad + Math.PI / 2));
		holdPos2.add(shieldPos);
		
		float jointStrength = (master.stateHS.level + 1) * Statics.PhysicsBox2D.JOINT_FORCE_HOLD_BASIC;		
		DistanceJointDef dJointDef = new DistanceJointDef();
		dJointDef.initialize(masterBody, body, holdPos1, holdPos2);
		dJointDef.frequencyHz = Statics.PhysicsBox2D.WEAPON_SHIELD_HOLD_JOINT_FREQ;
		dJointDef.dampingRatio = Statics.PhysicsBox2D.WEAPON_SHIELD_HOLD_JOINT_DAMP_RATIO;
		DistanceJoint dJoint = (DistanceJoint) world2d.createJoint(dJointDef);
		dJoint.setUserData(setJointUserData(JOINT_PROPERTY1_SHIELDHOLD, jointStrength, JOINT_PROPERTY3_STATE_ALIVE, Statics.PhysicsBox2D.WEAPON_SHIELD_HOLD_JOINT_LEFT_INDEX));
		
		holdPos1.set(holdOffset, 0);
		holdPos1.setAngleRad((float) (angleRad - Math.PI / 2));	
		holdPos1.add(shieldPos);
		
		holdPos2.set(holdOffset + Statics.PhysicsBox2D.WEAPON_SHIELD_HOLD_JOINT_LENGTH, 0);
		holdPos2.setAngleRad((float) (angleRad - Math.PI / 2));	
		holdPos2.add(shieldPos);
		
		dJointDef.initialize(masterBody, body, holdPos1, holdPos2);
		dJointDef.frequencyHz = Statics.PhysicsBox2D.WEAPON_SHIELD_HOLD_JOINT_FREQ;
		dJointDef.dampingRatio = Statics.PhysicsBox2D.WEAPON_SHIELD_HOLD_JOINT_DAMP_RATIO;
		dJoint = (DistanceJoint) world2d.createJoint(dJointDef);
		dJoint.setUserData(setJointUserData(JOINT_PROPERTY1_SHIELDHOLD, jointStrength, JOINT_PROPERTY3_STATE_ALIVE, Statics.PhysicsBox2D.WEAPON_SHIELD_HOLD_JOINT_RIGHT_INDEX));	
		
		holdPos1.set(shieldPos);
		holdPos2.set(Statics.PhysicsBox2D.WEAPON_SHIELD_HOLD_JOINT_LENGTH, 0);
		holdPos2.setAngleRad((float) (angleRad - Math.PI));	
		holdPos2.add(shieldPos);
		
		dJointDef.initialize(masterBody, body, holdPos1, holdPos2);
		dJointDef.frequencyHz = Statics.PhysicsBox2D.WEAPON_SHIELD_HOLD_JOINT_FREQ;
		dJointDef.dampingRatio = Statics.PhysicsBox2D.WEAPON_SHIELD_HOLD_JOINT_DAMP_RATIO;
		dJoint = (DistanceJoint) world2d.createJoint(dJointDef);
		dJoint.setUserData(setJointUserData(JOINT_PROPERTY1_SHIELDHOLD, jointStrength, JOINT_PROPERTY3_STATE_ALIVE, Statics.PhysicsBox2D.WEAPON_SHIELD_HOLD_JOINT_UP_INDEX));
	}
	
	public void processArmSwordHolding(DynamicGameObject master, DynamicGameObject weapon, boolean isLeft, int power)
	{
		Body masterBody = master.myBody;
		Body weaponBody = weapon.myBody;
		setWeaponFilterByMaster(master, weaponBody);
		destroyBodyJoints(weaponBody);
		float handOffset = master.bounds.height / 6;
		
		Vector2 weaponPos = new Vector2(3 * handOffset, isLeft ? handOffset : -handOffset);
		float angleRad = masterBody.getAngle();
		weaponPos.rotateRad(angleRad);
		weaponPos.add(masterBody.getPosition());	
		weaponBody.setTransform(weaponPos, (float) (angleRad - Math.PI / 2));
		
		weaponPos.set(0, isLeft ? handOffset : -handOffset);
		weaponPos.rotateRad(angleRad);
		weaponPos.add(masterBody.getPosition());	
		
		Vector2 axis = new Vector2(0, 1);					
		float jointStrength = (master.stateHS.level + 1) * Statics.PhysicsBox2D.JOINT_FORCE_HOLD_BASIC;
		float massKoef = weaponBody.getMass() / Statics.PhysicsBox2D.WEAPON_HOLD_ARM_ETALON_MASS;
		
		WheelJointDef wheelJointDef = new WheelJointDef();
		wheelJointDef.initialize(weaponBody, masterBody, weaponPos, axis);	
		wheelJointDef.frequencyHz = massKoef * (Statics.PhysicsBox2D.WEAPON_HOLD_ARM_FREQUENCY_KOEF_START_AXE
				+ power * Statics.PhysicsBox2D.WEAPON_HOLD_ARM_FREQUENCY_KOEF_STEP_AXE);
		wheelJointDef.dampingRatio = massKoef * (Statics.PhysicsBox2D.WEAPON_HOLD_ARM_IMPULSE_START_AXE
				+ power * Statics.PhysicsBox2D.WEAPON_HOLD_ARM_IMPULSE_STEP_AXE);		
		WheelJoint wheelJoint = (WheelJoint) world2d.createJoint(wheelJointDef);
		wheelJoint.setUserData(setJointUserData(JOINT_PROPERTY1_ARMDHOLD, jointStrength, JOINT_PROPERTY3_STATE_ALIVE, 0));	
		setWeaponFlyMode(weaponBody);
	}
	
	public void processArmAxeHolding(DynamicGameObject master, DynamicGameObject weapon, boolean isLeft, int power)
	{
		Body masterBody = master.myBody;
		Body weaponBody = weapon.myBody;
		setWeaponFilterByMaster(master, weaponBody);
		destroyBodyJoints(weaponBody);
		float handOffset = master.bounds.height / 6;
		
		Vector2 weaponPos = new Vector2(3 * handOffset, isLeft ? handOffset : -handOffset);
		float angleRad = masterBody.getAngle();
		weaponPos.rotateRad(angleRad);
		weaponPos.add(masterBody.getPosition());	
		weaponBody.setTransform(weaponPos, (float) (angleRad - Math.PI / 2));
		
		weaponPos.set(0, isLeft ? handOffset : -handOffset);
		weaponPos.rotateRad(angleRad);
		weaponPos.add(masterBody.getPosition());	
		
		Vector2 axis = new Vector2(0, 1);					
		float jointStrength = (master.stateHS.level + 1) * Statics.PhysicsBox2D.JOINT_FORCE_HOLD_BASIC;
		float massKoef = 1;
		
		WheelJointDef wheelJointDef = new WheelJointDef();
		wheelJointDef.initialize(weaponBody, masterBody, weaponPos, axis);	
		wheelJointDef.frequencyHz = massKoef * (Statics.PhysicsBox2D.WEAPON_HOLD_ARM_FREQUENCY_KOEF_START_AXE
				+ power * Statics.PhysicsBox2D.WEAPON_HOLD_ARM_FREQUENCY_KOEF_STEP_AXE);
		wheelJointDef.dampingRatio = massKoef * (Statics.PhysicsBox2D.WEAPON_HOLD_ARM_IMPULSE_START_AXE
				+ power * Statics.PhysicsBox2D.WEAPON_HOLD_ARM_IMPULSE_STEP_AXE);		
		WheelJoint wheelJoint = (WheelJoint) world2d.createJoint(wheelJointDef);
		wheelJoint.setUserData(setJointUserData(JOINT_PROPERTY1_ARMDHOLD, jointStrength, JOINT_PROPERTY3_STATE_ALIVE, 0));	
		setWeaponFlyMode(weaponBody);
	}
	
	public void processArmSpearHolding(DynamicGameObject master, DynamicGameObject weapon, boolean isLeft, int power)
	{
		Body masterBody = master.myBody;
		Body weaponBody = weapon.myBody;
		setWeaponFilterByMaster(master, weaponBody);
		float handOffset = master.bounds.height / 6;
		destroyBodyJoints(weaponBody);
		
		Vector2 weaponPos = new Vector2(3 * handOffset, isLeft ? handOffset : -handOffset);
		float angleRad = masterBody.getAngle();
		weaponPos.rotateRad(angleRad);
		weaponPos.add(masterBody.getPosition());	
		weaponBody.setTransform(weaponPos, (float) (angleRad - Math.PI / 2));
		
		weaponPos.set(0, isLeft ? handOffset : -handOffset);
		weaponPos.rotateRad(angleRad);
		weaponPos.add(masterBody.getPosition());	
		
		Vector2 axis = new Vector2(0, 1);					
		float jointStrength = (master.stateHS.level + 1) * Statics.PhysicsBox2D.JOINT_FORCE_HOLD_BASIC;
		float massKoef = 1;
		
		WheelJointDef wheelJointDef = new WheelJointDef();
		wheelJointDef.initialize(weaponBody, masterBody, weaponPos, axis);	
		wheelJointDef.frequencyHz = massKoef * (Statics.PhysicsBox2D.WEAPON_HOLD_ARM_FREQUENCY_KOEF_START_SPEAR
				+ power * Statics.PhysicsBox2D.WEAPON_HOLD_ARM_FREQUENCY_KOEF_STEP_SPEAR);
		wheelJointDef.dampingRatio = massKoef * (Statics.PhysicsBox2D.WEAPON_HOLD_ARM_IMPULSE_START_SPEAR
				+ power * Statics.PhysicsBox2D.WEAPON_HOLD_ARM_IMPULSE_STEP_SPEAR);		
		WheelJoint wheelJoint = (WheelJoint) world2d.createJoint(wheelJointDef);
		wheelJoint.setUserData(setJointUserData(JOINT_PROPERTY1_ARMDHOLD, jointStrength, JOINT_PROPERTY3_STATE_ALIVE, 0));
		setWeaponFlyMode(weaponBody);
	}
	
	void setWeaponFilterByMaster(DynamicGameObject master, Body weapon)
	{
		setBodyFilter(master.fixtureGroupFilterId, weapon);
	}
	
	public static void setBodyFilter(int groupFilterId, Body body)
	{
		Iterator <Fixture> fixIterator = body.getFixtureList().iterator();		
		while (fixIterator.hasNext())
			setFixtureFilter(groupFilterId, fixIterator.next());
	}
	
	public static void setFixtureFilter(int groupFilterId, Fixture fixture)
	{
		Filter filter = fixture.getFilterData();
		filter.groupIndex = (short) groupFilterId;	
		fixture.setFilterData(filter);
	}
	
	void destroyBodyJoints(Body body)
	{
		Array<JointEdge> jointsEdges = body.getJointList();

		while(jointsEdges.size > 0)
			world2d.destroyJoint(jointsEdges.get(0).joint);	
	}
	
	public void tryThrowWeapon(DynamicGameObject master, FightingSkill armSkill)
	{
		DynamicGameObject weapon = armSkill.getWeapon();		
		Body weaponBody = weapon.myBody;
		Vector2 weaponAngle = new Vector2(1, 0);
		weaponAngle.setAngleRad((float) (weaponBody.getAngle() + Math.PI / 2));
		
		Body masterBody = master.myBody;
		Vector2 masterAngle = new Vector2(1, 0);
		masterAngle.setAngleRad(masterBody.getAngle());			
		float deltaAngle = weaponAngle.angle(masterAngle);
		
		if (Math.abs(deltaAngle) < 5)
		{
			float impPower = Statics.PhysicsBox2D.WEAPON_ARM_THROW_IMPULSE_START +
					armSkill.getPower() * Statics.PhysicsBox2D.WEAPON_ARM_THROW_IMPULSE_STEP;
			impPower *= weaponBody.getMass() / Statics.PhysicsBox2D.WEAPON_HOLD_ARM_ETALON_MASS;
			
			Vector2 impulse = new Vector2(0, impPower);
			Vector2 impPos = new Vector2(weaponBody.getWorldCenter());				
			impulse.setAngleRad((float) (weaponBody.getAngle() + Math.PI / 2));	
			destroyBodyJoints(weaponBody);
			armSkill.releaseWeapon();
			weaponBody.applyLinearImpulse(impulse, impPos, true);
			
			int weaponClass = Statics.DynamicGameObject.getWeaponClass(weapon.objType);
			
			if(weaponClass == Statics.DynamicGameObject.ABSTRACT_TYPE_WEAPON_SWORD ||
					weaponClass == Statics.DynamicGameObject.ABSTRACT_TYPE_WEAPON_AXE)
				weaponBody.applyAngularImpulse(impulse.len(), true);
		}
	}
	
	void setWeaponFlyMode(Body weaponBody)
	{
		weaponBody.setBullet(true);
		weaponBody.setAngularDamping(Statics.PhysicsBox2D.WEAPON_FLY_ANGULAR_DAMPING);
		weaponBody.setLinearDamping(Statics.PhysicsBox2D.WEAPON_FLY_LINEAR_DAMPING);		
	}
	
	void stopWeaponFlyMode(Body weaponBody)
	{
		if(weaponBody.getLinearDamping() == Statics.PhysicsBox2D.WEAPON_FLY_LINEAR_DAMPING)
		{ 
			Array<JointEdge> jEdges = weaponBody.getJointList();
			
			for(JointEdge jEdge : jEdges)
			{
				if( ((float[])jEdge.joint.getUserData())[PhysicsBox2d.JOINT_PROPERTY1_INDEX_JOINT_TYPE] 
						== PhysicsBox2d.JOINT_PROPERTY1_ARMDHOLD) 
					return;
			}
			weaponBody.setBullet(false);
			setBodyDamping(Statics.PhysicsBox2D.DYNOBJ_LINEAR_DAMPING, Statics.PhysicsBox2D.DYNOBJ_ANGUALR_DAMPING, weaponBody);
			setBodyFilter(0, weaponBody);		
		}
	}
	
	public void tryThrowShield(DynamicGameObject master, FightingSkill fSkill)
	{
		DynamicGameObject weapon = fSkill.getWeapon();		
		Body weaponBody = weapon.myBody;
		Vector2 targetDirection = new Vector2(fSkill.getTargetPos());
		targetDirection.sub(weapon.position.x, weapon.position.y);			
		
		float impPower = Statics.PhysicsBox2D.WEAPON_SHIELD_THROW_IMPULSE_START +
				fSkill.getPower() * Statics.PhysicsBox2D.WEAPON_SHIELD_THROW_IMPULSE_STEP;
		
		Vector2 impulse = new Vector2(0, impPower);
		Vector2 impPos = new Vector2(weaponBody.getWorldCenter());				
		impulse.setAngle(targetDirection.angle());	
		destroyBodyJoints(weaponBody);
		fSkill.releaseWeapon();
		weaponBody.applyLinearImpulse(impulse, impPos, true);
	}
	
	public void processFencing(DynamicGameObject master, DynamicGameObject weapon, Vector2 targetPos, float deltaTime, 
			FightingSkill armSkill, int typeFencing)
	{
		int armPower = (int) armSkill.getPower();
		float workRadius = Statics.FightingSkills.getweaponWorkDistance(Statics.DynamicGameObject.getWeaponClass(weapon.objType));
		
		Body weaponBody = weapon.myBody;
		Vector2 weaponAngle = new Vector2(1, 0);
		weaponAngle.setAngleRad((float) (weaponBody.getAngle() + Math.PI / 2));
		
		Body masterBody = master.myBody;
		Vector2 masterAngle = new Vector2(1, 0);
		masterAngle.setAngleRad(masterBody.getAngle());
		
		if (targetPos.x == -1 && targetPos.y == -1)
		{
			float deltaAngle = weaponAngle.angle(masterAngle);
			
			if (Math.abs(deltaAngle) > 5)
				processWeaponAxis(weaponBody, (int) deltaAngle, Statics.PhysicsBox2D.WEAPON_HOLD_ARM_AXIS_FORCE_DEFAULT);
		}
		else
		{
			int weaponAbType = Statics.DynamicGameObject.getWeaponClass(weapon.objType);
			float freqKoef = 1;
			float impPower = 1;
			float impPosY = 0;
			float axisForce = 1;
			
			switch(weaponAbType)
			{
			case Statics.DynamicGameObject.ABSTRACT_TYPE_WEAPON_SWORD:
				freqKoef = (Statics.PhysicsBox2D.WEAPON_HOLD_ARM_FREQUENCY_KOEF_START_SWORD +
						armPower * Statics.PhysicsBox2D.WEAPON_HOLD_ARM_FREQUENCY_KOEF_STEP_SWORD);
				impPower = Statics.PhysicsBox2D.WEAPON_HOLD_ARM_IMPULSE_START_SWORD + 
						armPower * Statics.PhysicsBox2D.WEAPON_HOLD_ARM_IMPULSE_STEP_SWORD;
				impPosY = Statics.PhysicsBox2D.WEAPON_HOLD_ARM_AXIS_FORCE_SWORD;
				axisForce = Statics.PhysicsBox2D.WEAPON_HOLD_ARM_AXIS_FORCE_SWORD + 
						armPower * Statics.PhysicsBox2D.WEAPON_HOLD_ARM_AXIS_FORCE_STEP_SWORD;
				break;
			case Statics.DynamicGameObject.ABSTRACT_TYPE_WEAPON_AXE:
				freqKoef = (Statics.PhysicsBox2D.WEAPON_HOLD_ARM_FREQUENCY_KOEF_START_AXE +
						armPower * Statics.PhysicsBox2D.WEAPON_HOLD_ARM_FREQUENCY_KOEF_STEP_AXE);
				impPower = Statics.PhysicsBox2D.WEAPON_HOLD_ARM_IMPULSE_START_AXE + 
						armPower * Statics.PhysicsBox2D.WEAPON_HOLD_ARM_IMPULSE_STEP_AXE;
				impPosY = Statics.PhysicsBox2D.WEAPON_HOLD_ARM_AXIS_FORCE_AXE;
				axisForce = Statics.PhysicsBox2D.WEAPON_HOLD_ARM_AXIS_FORCE_AXE + 
						armPower * Statics.PhysicsBox2D.WEAPON_HOLD_ARM_AXIS_FORCE_STEP_AXE;
				break;
			case Statics.DynamicGameObject.ABSTRACT_TYPE_WEAPON_SPEAR:
				freqKoef = (Statics.PhysicsBox2D.WEAPON_HOLD_ARM_FREQUENCY_KOEF_START_SPEAR +
						armPower * Statics.PhysicsBox2D.WEAPON_HOLD_ARM_FREQUENCY_KOEF_STEP_SPEAR);
				impPower = Statics.PhysicsBox2D.WEAPON_HOLD_ARM_IMPULSE_START_SPEAR + 
						armPower * Statics.PhysicsBox2D.WEAPON_HOLD_ARM_IMPULSE_STEP_SPEAR;
				impPosY = Statics.PhysicsBox2D.WEAPON_HOLD_ARM_AXIS_FORCE_SPEAR;
				axisForce = Statics.PhysicsBox2D.WEAPON_HOLD_ARM_AXIS_FORCE_SPEAR + 
						armPower * Statics.PhysicsBox2D.WEAPON_HOLD_ARM_AXIS_FORCE_STEP_SPEAR;
				break;
			}
			
			float targetDist = master.position.dist(targetPos.x, targetPos.y);
			Vector2 targetDir = targetPos.sub(master.position.x, master.position.y);
			float deltaAngle = weaponAngle.angle(targetDir);		
			
			if (Math.abs(deltaAngle) > Statics.FightingSkills.getFencingPermittedAngleAttack(typeFencing))
				processWeaponAxis(weaponBody, (int) deltaAngle, axisForce);
			else
			{				
				if(targetDist < workRadius)
					processWeaponStrike(weaponBody, (int) deltaAngle, deltaTime, typeFencing, freqKoef, impPower, impPosY);					
			}
		}		
	}

	public void processWeaponStrike(Body weaponBody, int ccw, float deltaTime, int typeFencing, float freqKoef,
			float impPower, float impPosY)
	{	
		float actTime = world.actTime;		
		float oldTime = actTime - deltaTime;

		float massKoef = weaponBody.getMass() / Statics.PhysicsBox2D.WEAPON_HOLD_ARM_ETALON_MASS;
		freqKoef /= massKoef;
		impPower *= massKoef;
		
		int intActTime = (int) (freqKoef * actTime);
		int intOldTime = (int) (freqKoef * oldTime);
		
		Vector2 impulse = new Vector2(0, impPower);
		Vector2 impPos = new Vector2(0, impPosY);
		impPos = weaponBody.getWorldPoint(impPos);		
		
		if(intOldTime != intActTime)
		{
			if (typeFencing == Statics.FightingSkills.WEAPON_HOLD_ARM_FENCING_TYPE_CHOP)
				impulse.setAngleRad((float) (weaponBody.getAngle() + (ccw > 0 ? Math.PI : 0)));
			else if(typeFencing == Statics.FightingSkills.WEAPON_HOLD_ARM_FENCING_TYPE_STAB)
				impulse.setAngleRad((float) (weaponBody.getAngle() + Math.PI / 2));
			
			weaponBody.applyLinearImpulse(impulse, impPos, true);
		}		
	}
	
	public void processWeaponAxis(Body weaponBody, int ccw, float axisForce)
	{	
		Vector2 force = new Vector2(0, axisForce);
		Vector2 forcePos = new Vector2(0, 1f);
		forcePos = weaponBody.getWorldPoint(forcePos);		
		
		force.setAngleRad((float) (weaponBody.getAngle() + (ccw > 0 ? Math.PI : 0)));
		weaponBody.applyForce(force, forcePos, true);	
	}
	
	
	public boolean hasBodyJoints(Body body)
	{
		if(body.getJointList().size > 0)
			return true;
		else
			return false;
	}
	
	Joint getBodyJointByType(int jointType, Body body)
	{
		Joint joint = null;
		
		Iterator <JointEdge> jointsEdges = body.getJointList().iterator();

		while(jointsEdges.hasNext())
		{
			joint = jointsEdges.next().joint;
			
			if (((float[])joint.getUserData())[JOINT_PROPERTY1_INDEX_JOINT_TYPE] == jointType)
				return joint;			
		}
		
		return null;
	}
	
	public void changeShieldPos(Body masterBody, int shieldPos)
	{
		Array<JointEdge> jointsEdges = masterBody.getJointList();
		DistanceJoint leftJoint = null;
		DistanceJoint rightJoint = null;
		DistanceJoint forwardJoint = null;
		Joint tempJoint = null;
		
		for (int i = 0; i < jointsEdges.size; ++i)
		{
			tempJoint = jointsEdges.get(i).joint;
			
			if(((float[])tempJoint.getUserData())[PhysicsBox2d.JOINT_PROPERTY1_INDEX_JOINT_TYPE] == JOINT_PROPERTY1_SHIELDHOLD
					&& ((float[])tempJoint.getUserData())[PhysicsBox2d.JOINT_PROPERTY4_INDEX_JOINT_INDEX] == Statics.PhysicsBox2D.WEAPON_SHIELD_HOLD_JOINT_LEFT_INDEX)
				leftJoint = (DistanceJoint) tempJoint;
			else if(((float[])tempJoint.getUserData())[PhysicsBox2d.JOINT_PROPERTY1_INDEX_JOINT_TYPE] == JOINT_PROPERTY1_SHIELDHOLD
					&& ((float[])tempJoint.getUserData())[PhysicsBox2d.JOINT_PROPERTY4_INDEX_JOINT_INDEX] == Statics.PhysicsBox2D.WEAPON_SHIELD_HOLD_JOINT_RIGHT_INDEX)
				rightJoint = (DistanceJoint) tempJoint;
			else if(((float[])tempJoint.getUserData())[PhysicsBox2d.JOINT_PROPERTY1_INDEX_JOINT_TYPE] == JOINT_PROPERTY1_SHIELDHOLD
					&& ((float[])tempJoint.getUserData())[PhysicsBox2d.JOINT_PROPERTY4_INDEX_JOINT_INDEX] == Statics.PhysicsBox2D.WEAPON_SHIELD_HOLD_JOINT_UP_INDEX)
				forwardJoint = (DistanceJoint) tempJoint;
		}
		
		if(leftJoint != null && rightJoint != null && forwardJoint != null)
		{
			if (shieldPos == Statics.FightingSkills.WEAPON_HOLD_SHIELD_MODE_NORMAL)
			{
				leftJoint.setLength(Statics.PhysicsBox2D.WEAPON_SHIELD_HOLD_JOINT_LENGTH);
				rightJoint.setLength(Statics.PhysicsBox2D.WEAPON_SHIELD_HOLD_JOINT_LENGTH);
				forwardJoint.setLength(Statics.PhysicsBox2D.WEAPON_SHIELD_HOLD_JOINT_LENGTH);
			}
			else if (shieldPos == Statics.FightingSkills.WEAPON_HOLD_SHIELD_MODE_LEFT)
			{
				leftJoint.setLength(Statics.PhysicsBox2D.WEAPON_SHIELD_HOLD_JOINT_LENGTH_LONG);
				rightJoint.setLength(Statics.PhysicsBox2D.WEAPON_SHIELD_HOLD_JOINT_LENGTH_SHORT);
				forwardJoint.setLength(Statics.PhysicsBox2D.WEAPON_SHIELD_HOLD_JOINT_LENGTH_HALF );
			}
			else if (shieldPos == Statics.FightingSkills.WEAPON_HOLD_SHIELD_MODE_RIGHT)
			{
				leftJoint.setLength(Statics.PhysicsBox2D.WEAPON_SHIELD_HOLD_JOINT_LENGTH_SHORT);
				rightJoint.setLength(Statics.PhysicsBox2D.WEAPON_SHIELD_HOLD_JOINT_LENGTH_LONG);
				forwardJoint.setLength(Statics.PhysicsBox2D.WEAPON_SHIELD_HOLD_JOINT_LENGTH_HALF);
			}
			else if (shieldPos == Statics.FightingSkills.WEAPON_HOLD_SHIELD_MODE_FORWARD)
			{
				leftJoint.setLength(Statics.PhysicsBox2D.WEAPON_SHIELD_HOLD_JOINT_LENGTH);
				rightJoint.setLength(Statics.PhysicsBox2D.WEAPON_SHIELD_HOLD_JOINT_LENGTH);
				forwardJoint.setLength(Statics.PhysicsBox2D.WEAPON_SHIELD_HOLD_JOINT_LENGTH_SHORT);
			}
		}
	}
	
	public float getPenetrationDistance(float weaponImpulse, float penetrationWidth, int weaponMaterial,
			int weaponLevel, Fixture target)
	{
		initMaterialProperties(weaponMaterial, weaponLevel);
		float weaponHardness = materialHardnness;
		
		initMaterialProperties(target);
		float targetHardness = materialHardnness;		
		float penetrationDepth = 0;
		
		if(weaponImpulse / penetrationWidth > targetHardness)		
			penetrationDepth = Statics.PhysicsBox2D.PENETRATION_EMPIRICAL_KOEF *
				weaponImpulse * (weaponHardness / targetHardness) / penetrationWidth;
			
		return penetrationDepth;
	}
	
	public boolean isBodyComplete(DynamicGameObject dynObj)
	{
		return compBodyDispatcher.isCompoundBodyComplete(dynObj);
	}
	
	public void processNewPentration(PrismaticJointDef jointDef) 
	{	
		PrismaticJoint penetrationJoint  = (PrismaticJoint)world2d.createJoint(jointDef);
		penetrationJoint.setUserData(setJointUserData(PhysicsBox2d.JOINT_PROPERTY1_PENETRATION, 0, PhysicsBox2d.JOINT_PROPERTY3_STATE_ALIVE, 0));
		penetrationJoints.add(penetrationJoint);	
	}
	
	public void setBodyDamping(float linearDamping, float angularDamping, Body body)
	{
		body.setAngularDamping(angularDamping);
		body.setLinearDamping(linearDamping);
	}

	public boolean isPenetrationExist(Body bodyA, Body bodyB)
	{
		Iterator<PrismaticJointDef> iterator = penetrationNewJointsQueue.iterator();
		
		while(iterator.hasNext())
		{
			PrismaticJointDef jDef = iterator.next();
			if(bodyA.equals(jDef.bodyA) && bodyB.equals(jDef.bodyB)
					|| bodyB.equals(jDef.bodyA) && bodyA.equals(jDef.bodyB))
				return true;
		}
		
		return false;
	}
	
	public boolean isSparkingMaterial(int material)
	{
		if(material == Statics.PhysicsBox2D.FIXTURE_MATERIAL_ROCK
			|| material == Statics.PhysicsBox2D.FIXTURE_MATERIAL_BRONZE
			|| material == Statics.PhysicsBox2D.FIXTURE_MATERIAL_STEEL
			|| material == Statics.PhysicsBox2D.FIXTURE_MATERIAL_ADAMANT)
			return true;
		else
			return false;
	}
	
	public static boolean isInflammableMaterial(int material)
	{
		if(material == Statics.PhysicsBox2D.FIXTURE_MATERIAL_MEAT
			|| material == Statics.PhysicsBox2D.FIXTURE_MATERIAL_WOOD
			|| material == Statics.PhysicsBox2D.FIXTURE_MATERIAL_ICE)
			return true;
		else
			return false;
	}
	
	public boolean isMetal(int material)
	{
		if(material == Statics.PhysicsBox2D.FIXTURE_MATERIAL_BRONZE
			|| material == Statics.PhysicsBox2D.FIXTURE_MATERIAL_STEEL)
			return true;
		else
			return false;
	}
	
	public Array<Entry<Float, Object>> getRaySensorFixturesArray()
	{
		return raySensorFixturesArray;
	}
	
	public Joint createJoint(final int id, JointDef jDef, final int strength, final int index, 
			Body bodyA, Body bodyB, Vector2 anchor) {
		assert jDef != null : "a joint def is null";
		assert bodyA != null : "bodyA is null";
		assert bodyB != null : "bodyB is null";
		
		switch (jDef.type) {
		case DistanceJoint:
			break;
		case FrictionJoint:
			break;
		case GearJoint:
			break;
		case MotorJoint:
			break;
		case MouseJoint:
			break;
		case PrismaticJoint:
			break;
		case PulleyJoint:
			break;
		case RevoluteJoint:
			RevoluteJointDef jointDef = (RevoluteJointDef) jDef;
			jointDef.initialize(bodyA, bodyB, anchor);
			Joint joint =  world2d.createJoint(jointDef);
			joint.setUserData(setJointUserData(id, strength, JOINT_PROPERTY3_STATE_ALIVE, index));
			return joint;
		case RopeJoint:
			break;
		case Unknown:
			break;
		case WeldJoint:
			break;
		case WheelJoint:
			break;
		default:
			throw new AssertionError();		
		}
		throw new AssertionError();	
	}
	
	public static void setBodyVertical(Body body) {
		body.setFixedRotation(true);
	}
	
	public static void unsetBodyVertical(Body body) {
		body.setFixedRotation(false);
	}
	
	public static void setBodyMass(Body body, final float mass) {
		MassData mData = new MassData();
		mData.mass = mass;
		body.setMassData(mData);
	}
	
	public int addCharacterFilterGroup(Body body) {
		setBodyFilter(charsCounter, body);
		return charsCounter++;
	}
}
