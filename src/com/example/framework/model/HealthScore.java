package com.example.framework.model;

import com.kingsnake.physicsBox2d.PhysicsBox2d;

public class HealthScore {
	
	public static final int LEVEL_PINK = 0;
	public static final int LEVEL_GREEN = 1;
	public static final int LEVEL_BLUE = 2;
	public static final int LEVEL_YELLOW = 3;
	public static final int LEVEL_ORANGE = 4;
	public static final int LEVEL_RED = 5;
	public static final int LEVEL_FIRE = 6;
	
	public static int POWER_PINK = 100;
	public static int POWER_GREEN = 200;
	public static int POWER_BLUE = 300;
	public static int POWER_YELLOW = 400;
	public static int POWER_ORANGE = 5000;
	public static int POWER_RED = 600;
	public static int POWER_FIRE = 700;

	public static final float MEAT_DENSITY = 1f;
	public static final float MEAT_BITE_RESISTANCE = 1f;	
	public static final float WOOD_DENSITY = 0.5f;
	public static final float WOOD_BITE_RESISTANCE = 3f;	
	public static final float ROCK_DENSITY = 2.5f;
	public static final float ROCK_BITE_RESISTANCE = 21f;	
	public static final float BRONZE_DENSITY = 8.8f;
	public static final float BRONZE_BITE_RESISTANCE = 9f;	
	public static final float STEEL_DENSITY = 7.8f;
	public static final float STEEL_BITE_RESISTANCE = 21f;
	
	public static final float ADAMANT_DENSITY = 3.5f;
	public static final float ADAMANT_BITE_RESISTANCE = 1600f;
	
	public static final float SNAKE_DENCITY = MEAT_DENSITY;
	public static final float LEMMING_DENCITY = MEAT_DENSITY;
	public static final float HEDGEHOG_DENCITY = MEAT_DENSITY;
	public static final float FROG_DENCITY = MEAT_DENSITY;
	public static final float FISH_DENCITY = MEAT_DENSITY;
	public static final float BLOCK_WOOD_DENCITY = WOOD_DENSITY;
	public static final float BLOCK_MARBLE_DENCITY = ROCK_DENSITY;
	
	public static final int SNAKE_COST = 50;
	public static final int LEMMING_COST = 10;
	public static final int HEDGEHOG_COST = 20;
	public static final int FROG_COST = 10;
	public static final int FISH_COST = 20;
	
	public static final int FREND_BYRACE = 1;
	public static final int FREND_TOGAMER = 2;
	public static final int FREND_TOALIENRACE = 3;
	public boolean frendByRace;	//false - enemy to all, true - friend to it type 
	public int frendToGamer;	//0 - enemy to player, >0 friend to gamer and all his friends with same index 
	public int frendtoAlienRace;	//0 - enemy to all other races, >0 - the friendship race type  

	
	public int level;
	public int type;
	public float attackPower;
	public float attackPowerIncrease;
	
	public float defencePower;
	public float defencePowerIncrease;
	
	public float health;	
	public float healthDecrease;	//variable for health and bleeding calculating
	public float healthIncrease;	//variable for health increase/decrease from gift effect
	
	public float velocity;
	public float velocityIncrease;
	public boolean isBot;
	public float density;
	public int cost;
	public int coins;
	public int coinsIncrease;
	
	public boolean isHeated;
	public boolean heatFinish;
	public boolean heatStarsOn;
	public float hitPeriod;
	public float hitPeriodFull;
	
	public boolean isHypnosed;
	public float hypPeriod;
	
	public boolean isOpenJaws;
	
	public boolean isSwallow;
	public float swallowStartTime;
	
	public boolean isStriking;
	public boolean striked;
	public float strikeStartTime;
	public float strikePeriod;
	public float strikeTime;
	public float strikePower;
	public int strikeType;	//for strike render 
	
	public static int BLOOD_SMALL_LEVEL = 1;	
	public static int BLOOD_MEDIUM_LEVEL = 100;	
	public static int BLOOD_LARGE_LEVEL = 3000;	
	
	public static int WOUND_NO = 0;
	public static int WOUND_EASY = 1;
	public static float WOUND_EASY_LEVEL = 0.66f;
	public static int WOUND_STRONG = 2;
	public static float WOUND_STRONG_LEVEL = 0.33f;
	public int isWounded;	//wounded textures flag
	
	public static int ALIVE = 0;
	public static int DEAD_DELETED = 1;
	public static int DEAD_SHOWN = 2;	
	public static int DEAD_SOME_MY_PART_DEAD = 3;	
	public int isDead;	//0 - alive, 1 - dead and deleted, 2 - dead and shown
	
	public float helmOffset;
	
	
	HealthScore(int level, int type)
	{
		this.level = level;
		this.type = type;
		velocity = (float) level + 1;
		hitPeriod = 0;
		hitPeriodFull = -1;
		hypPeriod = 0;		
		isHeated = false;
		heatFinish = false;
		isHypnosed = false;
		healthDecrease = 0;
		isWounded = WOUND_NO;
		isDead = 0; 	//alive
		isBot = true;
		coins = 0;
		coinsIncrease = 0;
		healthIncrease = 0;
		attackPowerIncrease = 0;
		defencePowerIncrease = 0;
		velocityIncrease = 0;
		frendByRace = true;
		frendtoAlienRace = 0;
		frendToGamer = 0;
		heatStarsOn = false;
		isSwallow = false;
		isOpenJaws = false;
		swallowStartTime = 0;
		isStriking = false;
		striked = false;
		strikeStartTime = 0;
		strikePower = 0;
		strikeType = 0;
		helmOffset = 0;
		
		switch(this.type)
		{
			case DynamicGameObject.SNAKE:
				this.SetSnakeLevel(level);
				break;
			case DynamicGameObject.FROG:
				this.SetFrogLevel(level);
				break;
			case DynamicGameObject.LEMMING:
				this.SetLemmingLevel(level);
				break;
			case DynamicGameObject.HEDGEHOG:
				this.SetHedgehogLevel(level);
				break;
		}
	}
	
	void SetBotState(boolean isBot)
	{
		this.isBot = isBot;
	}
	
	public void SetLevel(int level)
	{
		this.level = level;
		
		switch(this.type)
		{
			case DynamicGameObject.SNAKE:
				this.SetSnakeLevel(level);
				break;
			case DynamicGameObject.FROG:
				this.SetFrogLevel(level);
				break;
			case DynamicGameObject.LEMMING:
				this.SetLemmingLevel(level);
				break;
			case DynamicGameObject.HEDGEHOG:
				this.SetHedgehogLevel(level);
				break;		
		}
	}
	
	void SetFriendship(int fType, int val)
	{
		if(fType == FREND_BYRACE)
		{
			if(val == 0)
				frendByRace = false;
			else
				frendByRace = true;
		}
		else if(fType == FREND_TOGAMER)
			frendToGamer = val;
		else if(fType == FREND_TOALIENRACE)
			frendtoAlienRace = val;		
	}
	
	void SetSnakeLevel(int level)
	{
		velocity = (float) level + Snake.SPEED;
		density = (float) level + SNAKE_DENCITY;
		cost = level * 100 + SNAKE_COST;
		
		switch(level)
		{
			case LEVEL_PINK:
				attackPower = POWER_PINK;
				defencePower = POWER_PINK;
				health = POWER_PINK;	
				break;
			case LEVEL_GREEN:
				attackPower = POWER_GREEN;
				defencePower = POWER_GREEN;
				health = POWER_GREEN;	
				break;
			case LEVEL_BLUE:
				attackPower = POWER_BLUE;
				defencePower = POWER_BLUE;
				health = POWER_BLUE;	
				break;
			case LEVEL_YELLOW:
				attackPower = POWER_YELLOW;
				defencePower = POWER_YELLOW;
				health = POWER_YELLOW;	
				break;
			case LEVEL_ORANGE:
				attackPower = POWER_ORANGE;
				defencePower = POWER_ORANGE;
				health = POWER_ORANGE;	
				break;
			case LEVEL_RED:
				attackPower = POWER_RED;
				defencePower = POWER_RED;
				health = POWER_RED;	
				break;
			case LEVEL_FIRE:
				attackPower = POWER_FIRE;
				defencePower = POWER_FIRE;
				health = POWER_FIRE;	
				break;		
		}
	}
	
	void SetFrogLevel(int level)
	{
		density = (float) level + FROG_DENCITY;
		cost = level * 20 + FROG_COST;
		
		switch(level)
		{
			case LEVEL_PINK:
				attackPower = POWER_PINK / 2;
				defencePower = POWER_PINK / 2;
				health = POWER_PINK / 2;	
				break;
			case LEVEL_GREEN:
				attackPower = POWER_GREEN / 10;
				defencePower = POWER_GREEN / 10;
				health = POWER_GREEN / 10;	
				break;
			case LEVEL_BLUE:
				attackPower = POWER_BLUE / 10;
				defencePower = POWER_BLUE / 10;
				health = POWER_BLUE / 10;	
				break;	
		}			
	}
	
	void SetLemmingLevel(int level)
	{
		velocity = Lemming.SPEED;
		density = (float) level + LEMMING_DENCITY;
		cost = level * 20 + LEMMING_COST;
		
		//Pink - Lemmy Haotic
		//Green - Lemmy Simple Deykstra
		//Blue - Lemmy Viking
		//Yellow - Lemmy Berserker
		//Orange - Lemmy Ulfhednar
		//Red - Lemmy Konung Harrold(Harry) Fairhair
		
		switch(level)
		{
			case LEVEL_PINK:	//Lemmy Haotic
				attackPower = POWER_PINK / 5;
				defencePower = POWER_PINK / 5;
				health = POWER_PINK / 5;	
				break;
			case LEVEL_GREEN:	//Lemmy deykstra
				attackPower = POWER_PINK / 5;
				defencePower = POWER_PINK / 5;
				health = POWER_PINK / 5;	
				break;
			case LEVEL_BLUE:	//Lemmy Viking
				attackPower = POWER_PINK / 3;
				defencePower = POWER_PINK / 3;
				health = POWER_PINK / 3;	
				break;	
			case LEVEL_YELLOW:	//Lemmy Berserker
				attackPower = POWER_PINK / 2;
				defencePower = POWER_PINK / 2;
				health = POWER_PINK / 2;	
				break;	
			case LEVEL_ORANGE:	//Lemmy Ulfehednar
				attackPower = POWER_PINK / 2;
				defencePower = POWER_PINK / 2;
				health = POWER_PINK / 2;	
				break;	
			case LEVEL_RED:	//Lemmy Konung Harrold(Harry) Fairhair
				attackPower = POWER_PINK;
				defencePower = POWER_PINK;
				health = POWER_PINK;
				break;
		}		
	}
	
	void SetHedgehogLevel(int level)
	{
		velocity = (float) level + Hedgehog.SPEED;
		density = (float) level + HEDGEHOG_DENCITY;
		cost = level * 20 + HEDGEHOG_COST;
		
		switch(level)
		{
			case LEVEL_PINK:
				attackPower = POWER_PINK / 5;
				defencePower = POWER_PINK / 5;
				health = POWER_PINK / 5;	
				break;
			case LEVEL_GREEN:
				attackPower = POWER_GREEN / 10;
				defencePower = POWER_GREEN / 10;
				health = POWER_GREEN / 10;	
				break;
			case LEVEL_BLUE:
				attackPower = POWER_BLUE / 10;
				defencePower = POWER_BLUE / 10;
				health = POWER_BLUE / 10;	
				break;	
		}		
	}

}
