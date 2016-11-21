package com.example.framework.model;

import java.util.Random;

import com.example.framework.model.DynamicGameObject;
import com.kingsnake.math.Vector2;

public class SnakePart extends DynamicGameObject{
		
	public static  int STAY = 0;
	public static  int MOVE = 1;
	
	public static float ACT_SPEED = 0.05f;	//animation speed 
    
    float tModePeriod;    
    public Vector2 sinPos; 
    public float sinAngle;
    public Snake snake;
    public int index;	//head = 0;
    public float sPartGoldWaveTime;
    public Armors armors;
    
    
    Random rand; 	
	
	    public SnakePart(float x, float y, int dir, int level, int index, Snake snake, WorldKingSnake world) {
	    	super(x, y, Statics.Render.SNAKE_STANDART_PART_SIZE, Statics.Render.SNAKE_STANDART_PART_SIZE, 0, DynamicGameObject.SNAKE, world);	    	
	        
	 		tModePeriod = 0;
	        isCharacter = true;
	        this.index = index;
	        this.snake = snake;
	        sPartGoldWaveTime = 0;
	        armors = new Armors(this);
	        
	        if(index > 0)
	        {
		        armors.addArmor(Statics.DynamicGameObject.SNAKE_WOOD_ARMOR, Statics.FightingSkills.FIGHTSKILL_LEVEL_PINK, true);
		        armors.addArmor(Statics.DynamicGameObject.SNAKE_WOOD_ARMOR, Statics.FightingSkills.FIGHTSKILL_LEVEL_PINK, false);
	        }
	 		
	 		rand = new Random();
	        setLevel(level);	        
	        this.position.set(x, y);
	        
	        switch(dir){
		        case Snake.RIGHT:
			        this.velocity.set(stateHS.velocity, 0);
			        break;
		        case Snake.LEFT:
			        this.velocity.set(-stateHS.velocity, 0);
			        break;	
		        case Snake.UP:
			        this.velocity.set(0, stateHS.velocity);
			        break;
		        case Snake.DOWN:
			        this.velocity.set(0, -stateHS.velocity);
			        break;			        	        
	        }
	       
	        oldPos.set(x, y);	        
	        this.sinPos = new Vector2();
	        sinAngle = 0;
	    }
	    
	 public int GetDirection() {
	        return (int)this.velocity.angle();
	    }
	 
	 public void SetDirection(int angle) {
		 this.velocity.rotate(angle - this.velocity.angle());
	    }
	 	 
	 public int setLevel(int newLevel) {
		 
	 	 applyForceCharMovingKoef = Statics.DynamicGameObject.APPLY_FORCE_CHAR_MOVING_KOEFF_SNAKE_PINK 
	 			+ newLevel * Statics.PhysicsBox2D.APPLY_MAIN_FORCE_CHAR_MOVING_KOEFF_STEP;	
		 
		 switch(newLevel)
		 {
		 case HealthScore.LEVEL_PINK:
			 	toMiddleOfCellVelKoef = Statics.DynamicGameObject.TO_MIDDLE_OF_CELL_VELOCITY_KOEFF_SNAKE_PINK;	
			 	beforeTurnVelKoef = Statics.DynamicGameObject.BEFORE_TURN_VELOCITY_KOEFF_SNAKE_PINK;	
			 	break;
		 case HealthScore.LEVEL_GREEN:
			 	toMiddleOfCellVelKoef = Statics.DynamicGameObject.TO_MIDDLE_OF_CELL_VELOCITY_KOEFF_SNAKE_GREEN;	
			 	beforeTurnVelKoef = Statics.DynamicGameObject.BEFORE_TURN_VELOCITY_KOEFF_SNAKE_GREEN;	
				break;
		 }
		 applyForceCharMovingKoef *= Statics.PhysicsBox2D.ALL_FORCES_CHAR_MOVING_GENERAL_TUNING_KOEFF;
		 toMiddleOfCellVelKoef *= Statics.PhysicsBox2D.ALL_FORCES_CHAR_MOVING_GENERAL_TUNING_KOEFF;	
		 beforeTurnVelKoef *= Statics.PhysicsBox2D.ALL_FORCES_CHAR_MOVING_GENERAL_TUNING_KOEFF;
		 return super.setLevel(newLevel);
					
		}
	 
	 public void update(float deltaTime)
	 {
		 super.update(deltaTime);
		 
		 armors.update();
		 
		 if(stateHS.healthDecrease > 0)
			 ++snake.stateHS.healthDecrease;
	 }


}
