package com.kingsnake.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.kingsnake.math.LabOrientDejkstra;
import com.kingsnake.math.MyMath;
import com.kingsnake.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.example.framework.model.CharacterMind;
import com.example.framework.model.DynamicEffect;
import com.example.framework.model.DynamicGameObject;
import com.example.framework.model.Frog;
import com.example.framework.model.GameObject;
import com.example.framework.model.Gifts;
import com.example.framework.model.Hedgehog;
import com.example.framework.model.Lemming;
import com.example.framework.model.Seed;
import com.example.framework.model.Snake;
import com.example.framework.model.SnakePart;
import com.example.framework.model.StaticEffect;
import com.example.framework.model.StaticEffectImpactStars;
import com.example.framework.model.Statics;
import com.example.framework.model.TreeHurt;
import com.example.framework.model.WorldKingSnake;
import com.kingsnake.control.ControlImpulse;
import com.kingsnake.control.ControlJumps;
import com.kingsnake.control.Controls;
import com.kingsnake.control.Joystick;
import com.kingsnake.gl.AssetsGL;
import com.kingsnake.gl.FPSCounter;
import com.kingsnake.gl.MyAnimation;
import com.kingsnake.gl.ParticlesEffectsManager;
import com.kingsnake.gl.TextGL;
import com.kingsnake.math.VertsPointers;

public class LevelScreen implements Screen {
	
	static int WORLD_WIDTH = WorldKingSnake.WORLD_WIDTH;
	static int WORLD_HEIGHT = WorldKingSnake.WORLD_HEIGHT;
	static float TIME_STEP = 1/30f;
	
	final KingSnake game;
    public OrthographicCamera camera;
    
    public WorldKingSnake world;
    
    TextGL textGL;
 	GameContextScreen gContext;      
    FPSCounter fps;
    Box2DDebugRenderer debugRenderer;
    Controls controls;
    
    boolean joystickTurnMode = false;
	boolean joystickCanTurnFlag = true;
	public float actTime = 0;
	float oldTime = 0;   
	
	 enum GameState {
	        Ready,
	        Running,
	        Paused,
	        Context,
	        GameOver
	    }
	 
	 GameState state = GameState.Ready;

    public LevelScreen(final KingSnake gam) {
        game = gam;
        
        camera = new OrthographicCamera();
        camera.setToOrtho(false, WORLD_WIDTH, WORLD_HEIGHT);
        
        world = new WorldKingSnake(game.world); 
        textGL = new TextGL(game.batch);
        gContext = new GameContextScreen(game.batch);       
        fps = new FPSCounter();                
        controls = new Controls(this);
        
        AssetsGL.load(); 
        debugRenderer = new Box2DDebugRenderer();
        debugRenderer.setDrawVelocities(true);       
    }

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}
	
	

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		
		update(delta);
		
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

	    camera.update();
	    game.batch.setProjectionMatrix(camera.combined);
	    
	    drawGame(delta);
	    
        // process user input
        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            world.world2d.updateMouseJoint(touchPos.x, touchPos.y);
        }
        if (!Gdx.input.isTouched()) {
        

        }
        
//        game.batch.begin();
//		debugRenderer.render(game.world, camera.combined);
//        game.batch.end();
        oldTime = actTime;
        actTime += delta;
	}
	
	void update(float delta)
	{
		controls.update(delta);
		world.doPhysicsStep(delta);		
	}
	
	void drawGame(float delta)
	{
	    game.batch.begin();
	    game.batch.draw(AssetsGL.backgroundRegion, 0, 0, WORLD_WIDTH, WORLD_HEIGHT);   
	    game.batch.end();
	    game.batch.begin();
	    
        drawWorld(delta);
        
        if(state == GameState.Ready)
            drawReadyUI();
        if(state == GameState.Running)
            drawRunningUI();
        if(state == GameState.Paused)
            drawPausedUI();
        if(state == GameState.GameOver)
            drawGameOverUI();
        
        game.batch.end();
        
        if(state == GameState.Context)
            drawContextUI();
        
        fps.logFrame();
		
		
		/*		 
		 		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		        game.getInput().getKeyEvents();
		        

		        int len = touchEvents.size();	      
		        
		        for(int i = 0; i < len; i++) 
		        {
		            TouchEvent event = touchEvents.get(i);
		            if(event.type == TouchEvent.TOUCH_UP) {
		            	if(state == GameState.Running)
		            	{
			                if(event.x <= 64 && event.y <= 64) {
			                    if(Settings.soundEnabled)
			                        AssetsGL.click.play(1);
			                    state = GameState.Paused;
			                    return;
			                }
		            	}
		            	else if(state == GameState.Paused)
		            	{
		                	vector.x = event.x;
		                	vector.y = event.y;
		                	camera.touchToWorld(vector);
		                	
			                if(vector.x > 2.5f && vector.x < 6 && vector.y > 9.5f && vector.y < 10.5f) {
			                    if(Settings.soundEnabled)
			                        AssetsGL.click.play(1);
			                    return;
			                }
			                else if(vector.x > 6.5f && vector.x < 7.5f && vector.y > 9.5f && vector.y < 10.5f) {
			                    
			                	if(Settings.soundEnabled)
			                        AssetsGL.click.play(1);
			                	state = GameState.Running;
			                    return;			                	
			                }
		            	}
		            	else if(state == GameState.Context)
		            	{
		                	//if touched arrow close context screen
		                	vector.x = event.x;
		                	vector.y = event.y;
		                	camera.touchToWorld(vector);
		                	
		                	if((gContext.contextRelief > 0 && vector.x >= gContext.contX + GameContextScreen.CONTEXT_CANCEL_X - GameContextScreen.CONTEXT_OBJSTATE_SIZE /2
		                			&& vector.x <= gContext.contX + GameContextScreen.CONTEXT_CANCEL_X + GameContextScreen.CONTEXT_OBJSTATE_SIZE /2
		                			&& vector.y >= gContext.contY + GameContextScreen.CONTEXT_CANCEL_Y - GameContextScreen.CONTEXT_OBJSTATE_SIZE /2
		                			&& vector.y <= gContext.contY + GameContextScreen.CONTEXT_CANCEL_Y + GameContextScreen.CONTEXT_OBJSTATE_SIZE /2)
		                			||
		                			(gContext.contextObj != null && vector.x >= gContext.contX + GameContextScreen.CONTEXT_CANCEL_X - GameContextScreen.CONTEXT_OBJSTATE_SIZE /2
		                			&& vector.x <= gContext.contX + GameContextScreen.CONTEXT_CANCEL_X + GameContextScreen.CONTEXT_OBJSTATE_SIZE /2
		                			&& vector.y >= gContext.contY + GameContextScreen.CONTEXT_CANCEL_Y - GameContextScreen.CONTEXT_OBJSTATE_SIZE /2
		                			&& vector.y <= gContext.contY + GameContextScreen.CONTEXT_CANCEL_Y + GameContextScreen.CONTEXT_OBJSTATE_SIZE /2)
		                	)
		                	{
			                	gContext.set(null);
			                	gContext.set(0, 0, 0);	
		                		state = GameState.Running;	                		
		                	}
		                			               
		            	}
		            	else if(state == GameState.Ready)
		            	{
		            		
		            	}
		            	else if(state == GameState.Ready)
		            	{
		            		
		            	}
		            	else if(state == GameState.GameOver)
		            	{
		            		
		            	}
		            }
		            
		            if(event.type == TouchEvent.TOUCH_DOWN) 
		            {
		            	if(state == GameState.Running)
		            	{
			                if(event.x > 64 && event.x < 128 && event.y > 1100) 
			                {
			                    ((Snake)world.dynObjects.get(0)).turnLeft();
			                }
			                else if(event.x > 192 && event.x < 256 && event.y > 1100) {
			                    ((Snake)world.dynObjects.get(0)).turnRight();
			                }
			                else if(event.x > 600 && event.x < 11 && event.y > 1100) {
			                    //go forward TO DO:
			                	
			                }
			                else
			                {
			                	//check touching to dynamic object. if so, show context info
			                	vector.x = event.x;
			                	vector.y = event.y;
			                	camera.touchToWorld(vector);
			                	
			                	gContext.set(null);
			                	gContext.set(0, 0, 0);
			                	
			                	gContext.set(world.wProc.getAnyObjByCoords(vector.x, vector.y));
			                	
			                	if(gContext.contextObj != null)
			                		state = GameState.Context;	
			                	else 
			                	{
			                		gContext.set(world.wProc.getRelief(((int)vector.x), ((int)vector.y)), vector.x, vector.y);
			                		
			                		if(gContext.contextRelief > 0)
				                		state = GameState.Context;	
			                	}
			                }
			            }
		            	else if(state == GameState.Paused)
		            	{
		            		
		            	}
		            	else if(state == GameState.Context)
		            	{
		            		
		            	}
		            	else if(state == GameState.Ready)
		            	{
		            		
		            	}
		            	else if(state == GameState.Ready)
		            	{
		            		
		            	}
		            	else if(state == GameState.GameOver)
		            	{
		            		
		            	}
		            }
		        }*/
       

		        
		       /* if(world.gameOver) {
		            if(Settings.soundEnabled)
		                Assets.bitten.play(1);
		            state = GameState.GameOver;
		        }*/	
        
	    //game.batch.end();
		
	}
	
	
	
    private void drawWorld(float deltaTime) {

    	//draw blood stains
    	int size =  world.bloodStains.size();
    	
    	for(int i=0; i < size; ++i)
    	{
    		StaticEffect stain = (StaticEffect) world.bloodStains.get(i);
    		switch(stain.type)
    		{
    		case GameObject.BLOOD_STAIN1:
	            game.batch.draw(stain.position.x, stain.position.y, stain.bounds.width, stain.bounds.height, stain.angle, AssetsGL.bloodStain1);
	            break;
    		case GameObject.BLOOD_STAIN2:
	            game.batch.draw(stain.position.x, stain.position.y, stain.bounds.width, stain.bounds.height, stain.angle, AssetsGL.bloodStain2);
	            break;
    		case GameObject.BLOOD_STAIN3:
	            game.batch.draw(stain.position.x, stain.position.y, stain.bounds.width, stain.bounds.height, stain.angle, AssetsGL.bloodStain3);
	            break;
    		}

    	}
    	
    	//draw statObjects
    	size = world.statObjects.size();
    	
    	for(int i=0; i < size; ++i)
    	{
			GameObject gObj = world.statObjects.get(i);
			
    		if(gObj.type == GameObject.APPLE)
    		{
		    	Gifts sObj = (Gifts) gObj;
		    	
		    	if(!sObj.isEaten)
		    	{
		    		if(sObj.actTime > Gifts.APPERAIANCE_PERIOD/2)
		    		{
		    			//to do: add twitch
					    game.batch.draw(sObj.position.x + 0.5f, sObj.position.y+ 0.5f, 
				            		sObj.bounds.width, sObj.bounds.height, AssetsGL.apple);
		    		}
			    	
		    		if(sObj.actTime <= Gifts.APPERAIANCE_PERIOD)
		    		{
			            TextureRegion keyFrame = AssetsGL.giftApper.getKeyFrame(world.actTime, 15, 0);		            
			            game.batch.draw(sObj.position.x + 0.5f, sObj.position.y+ 0.5f, 1.5f, 1.5f, keyFrame);
		    		}
		        }
    		}
    		else if(gObj.type == Gifts.MUSHROOM_BLUE)
    		{
		    	Gifts sObj = (Gifts) gObj;
	    		if(sObj.actTime> Gifts.APPERAIANCE_PERIOD/2)
			    	game.batch.draw(sObj.position.x +0.5f, sObj.position.y+ 0.5f, 
			    			sObj.bounds.width, sObj.bounds.height, AssetsGL.mushroomBlue);
	    		
	    		if(sObj.actTime <= Gifts.APPERAIANCE_PERIOD)
	    		{
		            TextureRegion keyFrame = AssetsGL.giftApper.getKeyFrame(world.actTime, 15, 0);		            
		            game.batch.draw(sObj.position.x + 0.5f, sObj.position.y + 0.5f, 1.5f, 1.5f, keyFrame);
	    		}
    		}
    		else if(gObj.type == Gifts.MUSHROOM_YELLOW)
    		{
		    	Gifts sObj = (Gifts) gObj;
	    		if(sObj.actTime> Gifts.APPERAIANCE_PERIOD/2)
			    	game.batch.draw(sObj.position.x+0.5f, sObj.position.y+0.5f, 
			    			sObj.bounds.width, sObj.bounds.height, AssetsGL.mushroomYellow);
	    		
	    		if(sObj.actTime <= Gifts.APPERAIANCE_PERIOD)
	    		{
		            TextureRegion keyFrame = AssetsGL.giftApper.getKeyFrame(world.actTime, 15, 0);		            
		            game.batch.draw(sObj.position.x + 0.5f, sObj.position.y + 0.5f, 1.5f, 1.5f, keyFrame);
	    		}
    		}
    		else if(gObj.type == Gifts.MUSHROOM_BLUE_RED)
    		{
		    	Gifts sObj = (Gifts) gObj;
	    		if(sObj.actTime> Gifts.APPERAIANCE_PERIOD/2)
			    	game.batch.draw(sObj.position.x+0.5f, sObj.position.y+0.5f, 
			    			sObj.bounds.width, sObj.bounds.height, AssetsGL.mushroomBRed);
	    		
	    		if(sObj.actTime <= Gifts.APPERAIANCE_PERIOD)
	    		{
		            TextureRegion keyFrame = AssetsGL.giftApper.getKeyFrame(world.actTime, 15, 0);		            
		            game.batch.draw(sObj.position.x + 0.5f, sObj.position.y + 0.5f, 1.5f, 1.5f, keyFrame);
	    		}
    		}
    		else if(gObj.type == Gifts.MUSHROOM_BROWN)
    		{
		    	Gifts sObj = (Gifts) gObj;
	    		if(sObj.actTime> Gifts.APPERAIANCE_PERIOD/2)
			    	game.batch.draw(sObj.position.x+0.5f,sObj.position.y+0.5f, 
			    			sObj.bounds.width, sObj.bounds.height, AssetsGL.mushroomBrown);
	    		
	    		if(sObj.actTime <= Gifts.APPERAIANCE_PERIOD)
	    		{
		            TextureRegion keyFrame = AssetsGL.giftApper.getKeyFrame(world.actTime, 15, 0);		            
		            game.batch.draw(sObj.position.x + 0.5f, sObj.position.y + 0.5f, 1.5f, 1.5f, keyFrame);
	    		}
    		}
    		else if(gObj.type == Statics.StaticEffect.BLOOD_SPLATTER_SMALL)
    		{
	            TextureRegion keyFrame = AssetsGL.bloodSmall.getKeyFrame(world.actTime, 4, 0);		            
	            game.batch.draw(gObj.position.x, gObj.position.y, - gObj.bounds.width, gObj.bounds.height, ((StaticEffect) gObj).angle, keyFrame);			    			
    		}
    		else if(gObj.type == Statics.StaticEffect.BLOOD_SPLATTER_MEDIUM)
    		{
	            TextureRegion keyFrame = AssetsGL.bloodMediumLarge.getKeyFrame(world.actTime, 12, 0);		            
	            game.batch.draw(gObj.position.x, gObj.position.y, gObj.bounds.width, gObj.bounds.height, ((StaticEffect) gObj).angle, keyFrame);			    			
    		}
    		else if(gObj.type == Statics.StaticEffect.BLOOD_SPLATTER_BIG)
    		{
			
    		}
    	}
    	
        //draw dynamic objects
    	size = world.dynObjects.size();
    	
        for(int i= 0; i < size; ++i)
        {
        	DynamicGameObject dynObj =  (DynamicGameObject)world.dynObjects.get(i);
        	
        	switch(dynObj.objType)
        	{
        	case DynamicGameObject.BOMB_FROM_TREE:
	            game.batch.draw(dynObj.position.x, dynObj.position.y, dynObj.bounds.width,
	            		dynObj.bounds.height, AssetsGL.bombFromTree);
	            break;
        	case DynamicGameObject.SNAKE:
        		
		    	Snake snake = (Snake) dynObj;
		    	int sizeParts = snake.parts.size();		    	
		    	if(sizeParts <= 0)
		    		continue;
		    	
	            for(int j = sizeParts - 1; j >= 0; --j)
	            {
	            	SnakePart sPart = (SnakePart)snake.parts.get(j);	      
	            
	            	if(sPart.index == Statics.DynamicGameObject.SNAKE_HEAD_INDEX)
	            	{
	            		if(snake.stateHS.isOpenJaws)	            		
		            	game.batch.draw(AssetsGL.snakeOpenMouth, sPart.position.x - sPart.bounds.width / 2 + sPart.bounds.width / 16, sPart.position.y - sPart.bounds.height / 2, 
		            			sPart.bounds.width / 2 - sPart.bounds.width / 16, sPart.bounds.height / 2, sPart.bounds.width, sPart.bounds.height, 1, 1, sPart.angle);

	            		if(!sPart.stateHS.isStriking)
	            			game.batch.draw(sPart.position.x, sPart.position.y, 
	    	            		sPart.bounds.width, sPart.bounds.height, sPart.angle, AssetsGL.snakeHead);
	            		else if(snake.stateHS.strikeType == Statics.Render.STRIKE_JUMP)
	            		{
	            			TextureRegion keyFrame;
	            			if(sPart.sPartGoldWaveTime < sPart.stateHS.strikePeriod - Statics.Render.SNAKE_GOLD_STRIKE_SEGMENT_CHANGING_PERIOD)
	            				keyFrame = AssetsGL.snakeHeadGoldAttack.getKeyFrame(sPart.sPartGoldWaveTime, MyAnimation.ANIMATION_NONLOOPING);	
	            			else
	            				keyFrame = AssetsGL.snakeHeadGoldAttackBack.getKeyFrame(sPart.sPartGoldWaveTime, MyAnimation.ANIMATION_NONLOOPING);	

				            game.batch.draw(sPart.position.x, sPart.position.y, sPart.bounds.width, sPart.bounds.height, sPart.angle, keyFrame);	
	            		}
	            		else if(snake.stateHS.strikeType == Statics.Render.STRIKE_IMPULSE)
	            		{
	            			TextureRegion keyFrame = AssetsGL.snakeHeadBlueAttack.getKeyFrame(snake.stateHS.strikeTime, MyAnimation.ANIMATION_NONLOOPING);	
				            game.batch.draw(sPart.position.x, sPart.position.y, sPart.bounds.width, sPart.bounds.height, sPart.angle, keyFrame);	
	            		}
	            		
	            		if(snake.fSkills.backAttack)
	            		{
	            			if(snake.fSkills.isSkillReady(Statics.FightingSkills.BACK_ATTACK))
				            	game.batch.draw(AssetsGL.snakeGoldBackAttack, sPart.position.x - sPart.bounds.width / 2, sPart.position.y - sPart.bounds.height / 8, 
				            			sPart.bounds.width / 2, sPart.bounds.height / 8, sPart.bounds.width / 4, sPart.bounds.height / 4, 1, 1, sPart.angle);
	            			else
	            			{
			            		TextureRegion keyFrame = AssetsGL.snakeGoldBackAttackCharging.getKeyFrame(world.actTime, MyAnimation.ANIMATION_LOOPING);	
					            game.batch.draw(keyFrame, sPart.position.x - sPart.bounds.width / 2, sPart.position.y - sPart.bounds.height / 8, sPart.bounds.width / 2, sPart.bounds.height / 8,
					            		sPart.bounds.width / 4, sPart.bounds.height / 4, 1, 1, sPart.angle);
	            			}		            			
	            		}
	            		
	            		if(snake.fSkills.backLeftHook)
	            		{
	            			if(snake.fSkills.isSkillReady(Statics.FightingSkills.BACK_LEFT_HOOK))
				            	game.batch.draw(AssetsGL.snakeGoldBackHookLeft, sPart.position.x - sPart.bounds.width / 2, sPart.position.y, 
				            			sPart.bounds.width / 2, 0, sPart.bounds.width / 4, sPart.bounds.height / 4, 1, 1, sPart.angle);
	            			else
	            			{
			            		TextureRegion keyFrame = AssetsGL.snakeGoldBackHookLeftCharging.getKeyFrame(world.actTime, MyAnimation.ANIMATION_LOOPING);	
					            game.batch.draw(keyFrame, sPart.position.x - sPart.bounds.width / 2, sPart.position.y, sPart.bounds.width / 2, 0, sPart.bounds.width / 4, sPart.bounds.height / 4, 1, 1, sPart.angle);
	            			}		            			
	            		}
	            		
	            		if(snake.fSkills.backRightHook)
	            		{
	            			if(snake.fSkills.isSkillReady(Statics.FightingSkills.BACK_RIGHT_HOOK))
				            	game.batch.draw(AssetsGL.snakeGoldBackHookRight, sPart.position.x - sPart.bounds.width / 2, sPart.position.y - sPart.bounds.height / 4, 
				            			sPart.bounds.width / 2, sPart.bounds.height / 4, sPart.bounds.width / 4, sPart.bounds.height / 4, 1, 1, sPart.angle);
	            			else
	            			{
			            		TextureRegion keyFrame = AssetsGL.snakeGoldBackHookRightCharging.getKeyFrame(world.actTime, MyAnimation.ANIMATION_LOOPING);	
					            game.batch.draw(keyFrame, sPart.position.x - sPart.bounds.width / 2, sPart.position.y - sPart.bounds.height / 4,
					            		sPart.bounds.width / 2, sPart.bounds.height / 4, sPart.bounds.width / 4, sPart.bounds.height / 4, 1, 1, sPart.angle);
	            			}		            			
	            		}
	            		
	            		if(snake.fSkills.impulseForwardDef)
	            		{
	            			if(snake.fSkills.isSkillReady(Statics.FightingSkills.IMPULSE_DEFFENSE_FORWARD))
				            	game.batch.draw(AssetsGL.snakeImpulseForwardDefense, sPart.position.x - sPart.bounds.width / 2, sPart.position.y + sPart.bounds.height / 8, 
				            			sPart.bounds.width / 2, -sPart.bounds.height / 8, sPart.bounds.width / 4, sPart.bounds.height / 4, 1, 1, sPart.angle);
	            			else
	            			{
			            		TextureRegion keyFrame = AssetsGL.snakeImpulseForwardDefenseCharging.getKeyFrame(world.actTime, MyAnimation.ANIMATION_LOOPING);	
					            game.batch.draw(keyFrame, sPart.position.x - sPart.bounds.width / 2, sPart.position.y + sPart.bounds.height / 8, 
					            sPart.bounds.width / 2, -sPart.bounds.height / 8, sPart.bounds.width / 4, sPart.bounds.height / 4, 1, 1, sPart.angle);
	            			}		            			
	            		}
	            		
	            		if(snake.fSkills.impulseForwardAttack)
	            		{
	            			if(snake.fSkills.isSkillReady(Statics.FightingSkills.IMPULSE_ATTACK_FORWARD))
				            	game.batch.draw(AssetsGL.snakeImpulseForwardAttack, sPart.position.x - sPart.bounds.width / 2, sPart.position.y - 3 * sPart.bounds.height / 8, 
				            			sPart.bounds.width / 2, 3 * sPart.bounds.height / 8, sPart.bounds.width / 4, sPart.bounds.height / 4, 1, 1, sPart.angle);
	            			else
	            			{
			            		TextureRegion keyFrame = AssetsGL.snakeImpulseForwardAttackCharging.getKeyFrame(world.actTime, MyAnimation.ANIMATION_LOOPING);	
					            game.batch.draw(keyFrame, sPart.position.x - sPart.bounds.width / 2, sPart.position.y - 3 * sPart.bounds.height / 8, 
					            sPart.bounds.width / 2, 3 * sPart.bounds.height / 8, sPart.bounds.width / 4, sPart.bounds.height / 4, 1, 1, sPart.angle);
	            			}		            			
	            		}	            		    
	            		
	            		if(sPart.stateHS.health < Statics.Render.SNAKE_DAMAGED_HARD_LEVEL * sPart.stateHS.defencePower)
			            	game.batch.draw(sPart.position.x, sPart.position.y, 
			            			sPart.bounds.width, sPart.bounds.height, sPart.angle, AssetsGL.snakeDamagedHard);
	            		else if(sPart.stateHS.health < Statics.Render.SNAKE_DAMAGED_EASY_LEVEL * sPart.stateHS.defencePower)
			            	game.batch.draw(sPart.position.x, sPart.position.y, 
			            			sPart.bounds.width, sPart.bounds.height, sPart.angle, AssetsGL.snakeDamagedEasy);  
	            		
	            		if(sPart.armors.haveArmor(true))
	            		{
	            			if(sPart.armors.getArmorType(true) == Statics.DynamicGameObject.SNAKE_WOOD_ARMOR)
	            				if(sPart.armors.getArmor(true).stateHS.health > sPart.armors.getArmor(true).stateHS.defencePower / 2)
					            	game.batch.draw(AssetsGL.snakePartArmorWoodLeft, sPart.position.x - sPart.bounds.width / 2 - 0.05f, sPart.position.y + 0.05f, 
					            			sPart.bounds.width / 2 + 0.05f, -0.05f, sPart.bounds.width, sPart.bounds.height / 2, 1, 1, sPart.angle);
	            				else
					            	game.batch.draw(AssetsGL.snakePartArmorWoodLeft, sPart.position.x - sPart.bounds.width / 2 - 0.05f, sPart.position.y + 0.05f, 
					            			sPart.bounds.width / 2 + 0.05f, -0.05f, sPart.bounds.width, sPart.bounds.height / 2, 1, 1, sPart.angle);
	            					
	            		}
	            		if(sPart.armors.haveArmor(false))
	            		{
	            			if(sPart.armors.getArmorType(false) == Statics.DynamicGameObject.SNAKE_WOOD_ARMOR)
	            				if(sPart.armors.getArmor(false).stateHS.health > sPart.armors.getArmor(false).stateHS.defencePower / 2)
	        		            	game.batch.draw(AssetsGL.snakePartArmorWoodRight, sPart.position.x - sPart.bounds.width / 2 - 0.05f, sPart.position.y - sPart.bounds.height / 2 - 0.05f, 
	        		            			sPart.bounds.width / 2 + 0.05f, sPart.bounds.height / 2 + 0.05f, sPart.bounds.width, sPart.bounds.height / 2, 1, 1, sPart.angle);
	            				else
	        		            	game.batch.draw(AssetsGL.snakePartArmorWoodRight, sPart.position.x - sPart.bounds.width / 2 - 0.05f, sPart.position.y - sPart.bounds.height / 2 - 0.05f, 
	        		            			sPart.bounds.width / 2 + 0.05f, sPart.bounds.height / 2 + 0.05f, sPart.bounds.width, sPart.bounds.height / 2, 1, 1, sPart.angle);
	            		}	
	            	}
	            	else
	            	{	          		
	            		if(!sPart.stateHS.isStriking)
			            	game.batch.draw(sPart.position.x, sPart.position.y, 
			            			sPart.bounds.width, sPart.bounds.height, sPart.angle, AssetsGL.snakeTail);
	            		else if(snake.stateHS.strikeType == Statics.Render.STRIKE_JUMP)
	            		{
	            			TextureRegion keyFrame;
	            			if(sPart.sPartGoldWaveTime < sPart.stateHS.strikePeriod - Statics.Render.SNAKE_GOLD_STRIKE_SEGMENT_CHANGING_PERIOD)
	            				keyFrame = AssetsGL.snakeGoldAttack.getKeyFrame(sPart.sPartGoldWaveTime, MyAnimation.ANIMATION_NONLOOPING);	
	            			else
	            				keyFrame = AssetsGL.snakeGoldAttackBack.getKeyFrame(sPart.sPartGoldWaveTime, MyAnimation.ANIMATION_NONLOOPING);	

	            			game.batch.draw(sPart.position.x, sPart.position.y, sPart.bounds.width, sPart.bounds.height, sPart.angle, keyFrame);	
	            		}
		            	
		            	if(j == sizeParts - 1)
		            	{
		            		if(snake.fSkills.forwardAttack)
		            		{		            			
		            			if(snake.fSkills.isSkillReady(Statics.FightingSkills.FORWARD_ATTACK))
					            	game.batch.draw(AssetsGL.snakeGoldForwardAttack, sPart.position.x, sPart.position.y - sPart.bounds.height / 2, 
					            			0, sPart.bounds.height / 2, sPart.bounds.width / 2, sPart.bounds.height, 1, 1, sPart.angle);
		            			else
		            			{
				            		TextureRegion keyFrame = AssetsGL.snakeGoldForwardAttackCharging.getKeyFrame(world.actTime, MyAnimation.ANIMATION_LOOPING);	
						            game.batch.draw(keyFrame, sPart.position.x, sPart.position.y - sPart.bounds.height / 2, 
					            			0, sPart.bounds.height / 2, sPart.bounds.width / 2, sPart.bounds.height, 1, 1, sPart.angle);
		            			}		     
		            		}
		            		
		            		if(snake.fSkills.forwardLeftHook)
		            		{
		            			if(snake.fSkills.isSkillReady(Statics.FightingSkills.FORWARD_LEFT_HOOK))
					            	game.batch.draw(AssetsGL.snakeGoldForwardHookLeft, sPart.position.x - sPart.bounds.width / 2, sPart.position.y, 
					            			sPart.bounds.width / 2, 0, sPart.bounds.width / 2, sPart.bounds.height / 2, 1, 1, sPart.angle);
		            			else
		            			{
				            		TextureRegion keyFrame = AssetsGL.snakeGoldForwardHookLeftCharging.getKeyFrame(world.actTime, MyAnimation.ANIMATION_LOOPING);	
						            game.batch.draw(keyFrame, sPart.position.x - sPart.bounds.width / 2, sPart.position.y, sPart.bounds.width / 2, 0, sPart.bounds.width / 2, sPart.bounds.height / 2, 1, 1, sPart.angle);
		            			}		            			
		            		}
		            		
		            		if(snake.fSkills.forwardRightHook)
		            		{
		            			if(snake.fSkills.isSkillReady(Statics.FightingSkills.FORWARD_RIGHT_HOOK))
					            	game.batch.draw(AssetsGL.snakeGoldForwardHookRight, sPart.position.x - sPart.bounds.width / 2, sPart.position.y - sPart.bounds.height / 2, 
					            			sPart.bounds.width / 2, sPart.bounds.height / 2, sPart.bounds.width / 2, sPart.bounds.height / 2, 1, 1, sPart.angle);
		            			else
		            			{
				            		TextureRegion keyFrame = AssetsGL.snakeGoldForwardHookRightCharging.getKeyFrame(world.actTime, MyAnimation.ANIMATION_LOOPING);	
						            game.batch.draw(keyFrame, sPart.position.x - sPart.bounds.width / 2, sPart.position.y - sPart.bounds.height / 2, sPart.bounds.width / 2, sPart.bounds.height / 2, sPart.bounds.width / 2, sPart.bounds.height / 2, 1, 1, sPart.angle);
		            			}		            			
		            		}
		            		
		            		if(snake.fSkills.forwardLeftTurn)
		            		{
		            			if(snake.fSkills.isSkillReady(Statics.FightingSkills.FORWARD_LEFT_TURN))
					            	game.batch.draw(AssetsGL.snakeGoldForwardLeftTurn, sPart.position.x - sPart.bounds.width / 4, sPart.position.y, 
					            			sPart.bounds.width / 4, 0, sPart.bounds.width / 4, sPart.bounds.height / 4, 1, 1, sPart.angle);
		            			else
		            			{
				            		TextureRegion keyFrame = AssetsGL.snakeGoldForwardLeftTurnCharging.getKeyFrame(world.actTime, MyAnimation.ANIMATION_LOOPING);	
						            game.batch.draw(keyFrame, sPart.position.x - sPart.bounds.width / 4, sPart.position.y, sPart.bounds.width / 4, 0, sPart.bounds.width / 4, sPart.bounds.height / 4, 1, 1, sPart.angle);
		            			}		            			
		            		}
		            		
		            		if(snake.fSkills.forwardRightTurn)
		            		{
		            			if(snake.fSkills.isSkillReady(Statics.FightingSkills.FORWARD_RIGHT_TURN))
					            	game.batch.draw(AssetsGL.snakeGoldForwardRightTurn, sPart.position.x - sPart.bounds.width / 4, sPart.position.y - sPart.bounds.height / 4, 
					            			sPart.bounds.width / 4, sPart.bounds.height / 4, sPart.bounds.width / 4, sPart.bounds.height / 4, 1, 1, sPart.angle);
		            			else
		            			{
				            		TextureRegion keyFrame = AssetsGL.snakeGoldForwardRightTurnCharging.getKeyFrame(world.actTime, MyAnimation.ANIMATION_LOOPING);	
						            game.batch.draw(keyFrame, sPart.position.x - sPart.bounds.width / 4, sPart.position.y - sPart.bounds.height / 4, sPart.bounds.width / 4, sPart.bounds.height / 4, sPart.bounds.width / 4, sPart.bounds.height / 4, 1, 1, sPart.angle);
		            			}		            			
		            		}
		            	}
		            	else
		            	{
		            		if(sPart.stateHS.isStriking && snake.stateHS.strikeType == Statics.Render.STRIKE_IMPULSE)
		            		{
		            			TextureRegion keyFrame = AssetsGL.snakePartBlueAttack.getKeyFrame(sPart.stateHS.strikeTime, MyAnimation.ANIMATION_NONLOOPING);	
					            game.batch.draw(sPart.position.x, sPart.position.y, sPart.bounds.width, sPart.bounds.height, sPart.angle, keyFrame);			            			
		            		}
		            		
		            		if(snake.fSkills.impulseLeftSideDef)
		            		{
		            			if(snake.fSkills.isSkillReady(Statics.FightingSkills.IMPULSE_DEFFENSE_LEFTSIDE))
					            	game.batch.draw(AssetsGL.snakeImpulseLeftSideDef, sPart.position.x - sPart.bounds.width / 2 + sPart.bounds.width / 8, sPart.position.y, 
					            			sPart.bounds.width / 2 - sPart.bounds.width / 8, 0, sPart.bounds.width / 4, sPart.bounds.height / 4, 1, 1, sPart.angle);
		            			else
		            			{
				            		TextureRegion keyFrame = AssetsGL.snakeImpulseLeftSideDefCharging.getKeyFrame(world.actTime, MyAnimation.ANIMATION_LOOPING);	
						            game.batch.draw(keyFrame, sPart.position.x - sPart.bounds.width / 2 + sPart.bounds.width / 8, sPart.position.y, 
						            		sPart.bounds.width / 2 - sPart.bounds.width / 8, 0, sPart.bounds.width / 4, sPart.bounds.height / 4, 1, 1, sPart.angle);
		            			}		            			
		            		}
		            		
		            		if(snake.fSkills.impulseRightSideDef)
		            		{
		            			if(snake.fSkills.isSkillReady(Statics.FightingSkills.IMPULSE_DEFFENSE_RIGHTSIDE))
					            	game.batch.draw(AssetsGL.snakeImpulseRightSideDef, sPart.position.x - sPart.bounds.width / 2 + sPart.bounds.width / 8, sPart.position.y - sPart.bounds.height / 4, 
					            			sPart.bounds.width / 2 - sPart.bounds.width / 8, sPart.bounds.height / 4, sPart.bounds.width / 4, sPart.bounds.height / 4, 1, 1, sPart.angle);
		            			else
		            			{
				            		TextureRegion keyFrame = AssetsGL.snakeImpulseRightSideDefCharging.getKeyFrame(world.actTime, MyAnimation.ANIMATION_LOOPING);	
						            game.batch.draw(keyFrame, sPart.position.x - sPart.bounds.width / 2 + sPart.bounds.width / 8, sPart.position.y - sPart.bounds.height / 4,
						            		sPart.bounds.width / 2 - sPart.bounds.width / 8, sPart.bounds.height / 4, sPart.bounds.width / 4, sPart.bounds.height / 4, 1, 1, sPart.angle);
		            			}		            			
		            		}
		            	}
		            	
	            		if(sPart.stateHS.health < Statics.Render.SNAKE_DAMAGED_HARD_LEVEL * sPart.stateHS.defencePower)
			            	game.batch.draw(sPart.position.x, sPart.position.y, 
			            			sPart.bounds.width, sPart.bounds.height, sPart.angle, AssetsGL.snakeDamagedHard);
	            		else if(sPart.stateHS.health < Statics.Render.SNAKE_DAMAGED_EASY_LEVEL * sPart.stateHS.defencePower)
			            	game.batch.draw(sPart.position.x, sPart.position.y, 
			            			sPart.bounds.width, sPart.bounds.height, sPart.angle, AssetsGL.snakeDamagedEasy);    
		            	
	            		if(sPart.armors.haveArmor(true))
	            		{
	            			if(sPart.armors.getArmorType(true) == Statics.DynamicGameObject.SNAKE_WOOD_ARMOR)
	            				if(sPart.armors.getArmor(true).stateHS.health > sPart.armors.getArmor(true).stateHS.defencePower / 2)
					            	game.batch.draw(AssetsGL.snakePartArmorWoodLeft, sPart.position.x - sPart.bounds.width / 2, sPart.position.y, 
					            			sPart.bounds.width / 2, 0, sPart.bounds.width, sPart.bounds.height / 2, 1, 1, sPart.angle);
	            				else
					            	game.batch.draw(AssetsGL.snakePartArmorWoodDamagedLeft, sPart.position.x - sPart.bounds.width / 2, sPart.position.y, 
					            			sPart.bounds.width / 2, 0, sPart.bounds.width, sPart.bounds.height / 2, 1, 1, sPart.angle);
	            					
	            		}
	            		if(sPart.armors.haveArmor(false))
	            		{
	            			if(sPart.armors.getArmorType(false) == Statics.DynamicGameObject.SNAKE_WOOD_ARMOR)
	            				if(sPart.armors.getArmor(false).stateHS.health > sPart.armors.getArmor(false).stateHS.defencePower / 2)
	        		            	game.batch.draw(AssetsGL.snakePartArmorWoodRight, sPart.position.x - sPart.bounds.width / 2, sPart.position.y - sPart.bounds.height / 2 , 
	        		            			sPart.bounds.width / 2, sPart.bounds.height / 2, sPart.bounds.width, sPart.bounds.height / 2, 1, 1, sPart.angle);
	            				else
	        		            	game.batch.draw(AssetsGL.snakePartArmorWoodDamagedRight, sPart.position.x - sPart.bounds.width / 2, sPart.position.y - sPart.bounds.height / 2, 
	        		            			sPart.bounds.width / 2, sPart.bounds.height / 2, sPart.bounds.width, sPart.bounds.height / 2, 1, 1, sPart.angle);
	            		}		            	
	            	}
	            	        	            	
	            	/*if(sPart.index == Statics.DynamicGameObject.SNAKE_HEAD_INDEX)
	    	            game.batch.draw(sPart.sinPos.x, sPart.sinPos.y, sPart.bounds.width, sPart.bounds.height, sPart.sinAngle, AssetsGL.snakeHead);
	            	else
		            	game.batch.draw(sPart.sinPos.x, sPart.sinPos.y, 
		            			sPart.bounds.width, sPart.bounds.height, snake.parts.get(j).sinAngle, AssetsGL.snakeTail);*/
	            	
		            //force point
		            //game.batch.draw(sPart.forcePoint.x, sPart.forcePoint.y, 
		            //		0.2f, 0.2f, sPart.angle, AssetsGL.red);	
	            }
	            
	            //draw my next turn point
	            //game.batch.draw(dynObj.xNextTurn / 3, dynObj.yNextTurn / 3, 0.5f, 0.5f, AssetsGL.red);		                    
	            
	            break;
        	case DynamicGameObject.DEAD_PART_VISIBLE_STILL:
        		if(dynObj.type == DynamicGameObject.BLOCK_WOOD)
        		{
        			//isMoving holds part index for dead parts
		            game.batch.draw(dynObj.position.x, dynObj.position.y, dynObj.bounds.width, dynObj.bounds.height, dynObj.angle + 90 * dynObj.isMoving, AssetsGL.blockWoodPart);        			
        		}
        		else if(dynObj.type == Statics.DynamicGameObject.HELM_VIKING_WOODEN)
        		{
        			//isMoving holds part index for dead parts
        			if(dynObj.isMoving == Statics.PhysicsBox2D.BODY_FIRST_PART_INDEX)
    	            	game.batch.draw(AssetsGL.weaponHelmWoodenSimplePart1, dynObj.position.x - dynObj.bounds.width / 2, dynObj.position.y - dynObj.bounds.height / 2, 
    	            			dynObj.bounds.width / 2, dynObj.bounds.height / 2,  dynObj.bounds.width / 2, dynObj.bounds.height, 1, 1, dynObj.angle);
        			else if(dynObj.isMoving == Statics.PhysicsBox2D.BODY_SECOND_PART_INDEX)
    	            	game.batch.draw(AssetsGL.weaponHelmWoodenSimplePart2, dynObj.position.x, dynObj.position.y - dynObj.bounds.height / 2, 
    	            			0, dynObj.bounds.height / 2, dynObj.bounds.width / 2, dynObj.bounds.height, 1, 1, dynObj.angle);        			
        		}
        		else if(dynObj.type == Statics.DynamicGameObject.SHIELD_VIKING_WOODEN)
        		{
        			//isMoving holds part index for dead parts
        			if(dynObj.isMoving == Statics.PhysicsBox2D.BODY_FIRST_PART_INDEX)
    	            	game.batch.draw(AssetsGL.weaponShieldVikkingWoodenSimplePart1, dynObj.position.x - dynObj.bounds.width / 2, dynObj.position.y - dynObj.bounds.height / 2, 
    	            			dynObj.bounds.width / 2, dynObj.bounds.height / 2,  dynObj.bounds.width, dynObj.bounds.height, 1, 1, dynObj.angle);
        			else if(dynObj.isMoving == Statics.PhysicsBox2D.BODY_SECOND_PART_INDEX)
    	            	game.batch.draw(AssetsGL.weaponShieldVikkingWoodenSimplePart2, dynObj.position.x - dynObj.bounds.width / 2, dynObj.position.y - dynObj.bounds.height / 2, 
    	            			dynObj.bounds.width / 2, dynObj.bounds.height / 2,  dynObj.bounds.width, dynObj.bounds.height, 1, 1, dynObj.angle);      			
        			else if(dynObj.isMoving == Statics.PhysicsBox2D.BODY_THIRD_PART_INDEX)
    	            	game.batch.draw(AssetsGL.weaponShieldVikkingWoodenSimplePart3, dynObj.position.x - dynObj.bounds.width / 2, dynObj.position.y - dynObj.bounds.height / 2, 
    	            			dynObj.bounds.width / 2, dynObj.bounds.height / 2,  dynObj.bounds.width, dynObj.bounds.height, 1, 1, dynObj.angle);      			
 
        		}
        		else if(dynObj.type == Statics.DynamicGameObject.SNAKE_WOOD_ARMOR)
        		{
        			//isMoving holds part index for dead parts
        			if(dynObj.isMoving >= Statics.PhysicsBox2D.SNAKE_WOOD_ARMOR_RIGHT_FIXTURE_START_INDEX)
        			{
        				if(dynObj.isMoving - Statics.PhysicsBox2D.SNAKE_WOOD_ARMOR_RIGHT_FIXTURE_START_INDEX == Statics.PhysicsBox2D.BODY_FIRST_PART_INDEX)
        					game.batch.draw(AssetsGL.items, dynObj.position.x - dynObj.bounds.width /2, dynObj.position.y - dynObj.bounds.height / 2, 
        							dynObj.bounds.width / 2, dynObj.bounds.height / 2, dynObj.bounds.width /2, dynObj.bounds.height / 2, 1, 1, dynObj.angle, 1152, 608, 32, 32, false, false);  
        				else if(dynObj.isMoving - Statics.PhysicsBox2D.SNAKE_WOOD_ARMOR_RIGHT_FIXTURE_START_INDEX == Statics.PhysicsBox2D.BODY_SECOND_PART_INDEX)
        					game.batch.draw( AssetsGL.items, dynObj.position.x, dynObj.position.y - dynObj.bounds.height / 2,
        							0,  dynObj.bounds.height / 2, dynObj.bounds.width / 2, dynObj.bounds.height / 2, 1, 1, dynObj.angle, 1184, 608, 32, 32, false, false); 
        			}
        			else if(dynObj.isMoving >= Statics.PhysicsBox2D.SNAKE_WOOD_ARMOR_LEFT_FIXTURE_START_INDEX)
        			{
        				if(dynObj.isMoving - Statics.PhysicsBox2D.SNAKE_WOOD_ARMOR_LEFT_FIXTURE_START_INDEX == Statics.PhysicsBox2D.BODY_FIRST_PART_INDEX)
        					game.batch.draw( AssetsGL.items, dynObj.position.x - dynObj.bounds.width /2, dynObj.position.y, 
        							dynObj.bounds.width / 2, 0, dynObj.bounds.width /2, dynObj.bounds.height / 2, 1, 1, dynObj.angle, 1152, 576, 32, 32, false, false);  
        				else if(dynObj.isMoving - Statics.PhysicsBox2D.SNAKE_WOOD_ARMOR_LEFT_FIXTURE_START_INDEX == Statics.PhysicsBox2D.BODY_SECOND_PART_INDEX)
        					game.batch.draw( AssetsGL.items, dynObj.position.x, dynObj.position.y,
        							0, 0, dynObj.bounds.width / 2, dynObj.bounds.height / 2, 1, 1, dynObj.angle, 1184, 576, 32, 32, false, false); 
        			}
        		}
        		else if(dynObj.type == Statics.DynamicGameObject.SWORD_VIKING_BRONZE_WOODEN)
        		{
        			//isMoving holds part index for dead parts
        			if(dynObj.isMoving == Statics.PhysicsBox2D.BODY_FIRST_PART_INDEX)
    	            	game.batch.draw(AssetsGL.weaponSwordVikkingBronzeWoodenPart1, dynObj.position.x - dynObj.bounds.width / 4, dynObj.position.y - dynObj.bounds.height / 2, 
    	            			dynObj.bounds.width / 4, dynObj.bounds.height / 2,  dynObj.bounds.width / 2, dynObj.bounds.height / 2, 1, 1, dynObj.angle);
        			else if(dynObj.isMoving == Statics.PhysicsBox2D.BODY_SECOND_PART_INDEX)
    	            	game.batch.draw(AssetsGL.weaponSwordVikkingBronzeWoodenPart2, dynObj.position.x -  dynObj.bounds.width / 4, dynObj.position.y, 
    	            			dynObj.bounds.width / 4, 0, dynObj.bounds.width / 2, dynObj.bounds.height / 2, 1, 1, dynObj.angle);        			 			
        		}
        		else if(dynObj.type == Statics.DynamicGameObject.AXE_VIKING_MONOSIDE_BRONZE_WOODEN)
        		{
        			//isMoving holds part index for dead parts
        			if(dynObj.isMoving == Statics.PhysicsBox2D.BODY_FIRST_PART_INDEX)
    	            	game.batch.draw(AssetsGL.weaponAxeMonosideVikkingBronzeWoodenPart1, dynObj.position.x - dynObj.bounds.width / 4, dynObj.position.y - dynObj.bounds.height / 2, 
    	            			dynObj.bounds.width / 4, dynObj.bounds.height / 2,  dynObj.bounds.width / 2, dynObj.bounds.height / 2, 1, 1, dynObj.angle);
        			else if(dynObj.isMoving == Statics.PhysicsBox2D.BODY_SECOND_PART_INDEX)
    	            	game.batch.draw(AssetsGL.weaponAxeMonosideVikkingBronzeWoodenPart2, dynObj.position.x -  dynObj.bounds.width / 4, dynObj.position.y, 
    	            			dynObj.bounds.width / 4, 0, dynObj.bounds.width / 2, dynObj.bounds.height / 2, 1, 1, dynObj.angle);        			 			
        		}
        		else if(dynObj.type == Statics.DynamicGameObject.AXE_VIKING_DOUBLESIDE_BRONZE_WOODEN)
        		{
        			//isMoving holds part index for dead parts
        			if(dynObj.isMoving == Statics.PhysicsBox2D.BODY_FIRST_PART_INDEX)
    	            	game.batch.draw(AssetsGL.weaponAxeDoublesideVikkingBronzeWoodenPart1, dynObj.position.x - dynObj.bounds.width / 2, dynObj.position.y - dynObj.bounds.height / 2, 
    	            			dynObj.bounds.width / 2, dynObj.bounds.height / 2,  dynObj.bounds.width, dynObj.bounds.height / 2, 1, 1, dynObj.angle);
        			else if(dynObj.isMoving == Statics.PhysicsBox2D.BODY_SECOND_PART_INDEX)
    	            	game.batch.draw(AssetsGL.weaponAxeDoublesideVikkingBronzeWoodenPart2, dynObj.position.x -  dynObj.bounds.width / 2, dynObj.position.y, 
    	            			dynObj.bounds.width / 2, 0, dynObj.bounds.width, dynObj.bounds.height / 2, 1, 1, dynObj.angle);        			 			
        		}
        		else if(dynObj.type == Statics.DynamicGameObject.SPEAR_VIKING_BRONZE_WOODEN)
        		{
        			//isMoving holds part index for dead parts
        			if(dynObj.isMoving == Statics.PhysicsBox2D.BODY_FIRST_PART_INDEX)
    	            	game.batch.draw(AssetsGL.weaponSpearVikkingBronzeWoodenPart1, dynObj.position.x - dynObj.bounds.width / 4, dynObj.position.y - dynObj.bounds.height / 2, 
    	            			dynObj.bounds.width / 4, dynObj.bounds.height / 2,  dynObj.bounds.width / 2, dynObj.bounds.height / 2, 1, 1, dynObj.angle);
        			else if(dynObj.isMoving == Statics.PhysicsBox2D.BODY_SECOND_PART_INDEX)
    	            	game.batch.draw(AssetsGL.weaponSpearVikkingBronzeWoodenPart2, dynObj.position.x -  dynObj.bounds.width / 4, dynObj.position.y, 
    	            			dynObj.bounds.width / 4, 0, dynObj.bounds.width / 2, dynObj.bounds.height / 2, 1, 1, dynObj.angle);        			 			
        		}
        		else if(dynObj.type == Statics.DynamicGameObject.FSKILL_ICEBALL)
        		{
        			//isMoving holds part index for dead parts
        			if(dynObj.isMoving == Statics.PhysicsBox2D.BODY_FIRST_PART_INDEX)
    	            	game.batch.draw(AssetsGL.fSkillIcePart1, dynObj.position.x - 3 *  dynObj.bounds.width / 8, dynObj.position.y - dynObj.bounds.height / 2, 
    	            			3 * dynObj.bounds.width / 8, dynObj.bounds.height / 2,  3 * dynObj.bounds.width / 4, dynObj.bounds.height, 1, 1, dynObj.angle);
        			else if(dynObj.isMoving == Statics.PhysicsBox2D.BODY_SECOND_PART_INDEX)
    	            	game.batch.draw(AssetsGL.fSkillIcePart2, dynObj.position.x - 3 * dynObj.bounds.width / 8, dynObj.position.y - dynObj.bounds.height / 2, 
    	            			3 * dynObj.bounds.width / 8, dynObj.bounds.height / 2, 3 * dynObj.bounds.width / 4, dynObj.bounds.height, 1, 1, dynObj.angle);      			
        			else if(dynObj.isMoving == Statics.PhysicsBox2D.BODY_THIRD_PART_INDEX)
    	            	game.batch.draw(AssetsGL.fSkillIcePart3, dynObj.position.x, dynObj.position.y - dynObj.bounds.height / 2, 
    	            			0, dynObj.bounds.height / 2,  dynObj.bounds.width / 2 , dynObj.bounds.height, 1, 1, dynObj.angle);      			
        		}
        		break;
        	case DynamicGameObject.BLOCK_WOOD:
        		if(dynObj.stateHS.health > 0.75f * dynObj.stateHS.defencePower)
		            game.batch.draw(dynObj.position.x, dynObj.position.y, dynObj.bounds.width, dynObj.bounds.height, dynObj.angle, AssetsGL.blockWood1);	
        		else if(dynObj.stateHS.health > 0.5f * dynObj.stateHS.defencePower)
		            game.batch.draw(dynObj.position.x, dynObj.position.y, dynObj.bounds.width, dynObj.bounds.height, dynObj.angle, AssetsGL.blockWood2);
        		else if(dynObj.stateHS.health > 0.25f * dynObj.stateHS.defencePower)
		            game.batch.draw(dynObj.position.x, dynObj.position.y, dynObj.bounds.width, dynObj.bounds.height, dynObj.angle, AssetsGL.blockWood3);
        		else if(dynObj.stateHS.health > 0)
		            game.batch.draw(dynObj.position.x, dynObj.position.y, dynObj.bounds.width, dynObj.bounds.height, dynObj.angle, AssetsGL.blockWood4);
        		break;
        	case DynamicGameObject.BLOCK_MARBLE:
        		if(dynObj.stateHS.health > 0.75f * dynObj.stateHS.defencePower)
		            game.batch.draw(dynObj.position.x, dynObj.position.y, dynObj.bounds.width, dynObj.bounds.height, dynObj.angle, AssetsGL.blockMarble1);	
        		else if(dynObj.stateHS.health > 0.5f * dynObj.stateHS.defencePower)
		            game.batch.draw(dynObj.position.x, dynObj.position.y, dynObj.bounds.width, dynObj.bounds.height, dynObj.angle, AssetsGL.blockMarble2);
        		else if(dynObj.stateHS.health > 0.25f * dynObj.stateHS.defencePower)
		            game.batch.draw(dynObj.position.x, dynObj.position.y, dynObj.bounds.width, dynObj.bounds.height, dynObj.angle, AssetsGL.blockMarble3);
        		else if(dynObj.stateHS.health > 0)
		            game.batch.draw(dynObj.position.x, dynObj.position.y, dynObj.bounds.width, dynObj.bounds.height, dynObj.angle, AssetsGL.blockMarble4);
        		break;
        	case DynamicGameObject.FROG:
        		if(((Frog)dynObj).isMoving == Frog.SIT){
            		TextureRegion keyFrame = AssetsGL.charFrogStay.getKeyFrame(world.actTime, MyAnimation.ANIMATION_LOOPING, 
            				Frog.frameCodeFrogSit, Frog.manSizeFrogSit);	
		            game.batch.draw(dynObj.position.x, dynObj.position.y, dynObj.bounds.width,
		            		dynObj.bounds.height, keyFrame);	
        		}
        		else if(((Frog)dynObj).isMoving == Frog.JUMPING){	
		            game.batch.draw(dynObj.position.x, dynObj.position.y, ((Frog)dynObj).move.x > 0 ? dynObj.bounds.width : -dynObj.bounds.width,
		            		dynObj.bounds.height * 2, AssetsGL.charFrogMove);	
        		}
        		else
        		{// grounding
            		TextureRegion keyFrame = AssetsGL.charFrogGrounding.getKeyFrame(world.actTime, 8, 0);	
		            game.batch.draw(dynObj.position.x, dynObj.position.y, dynObj.bounds.width * 2,
		            		dynObj.bounds.height * 2, keyFrame);	            			
        		}
	            break;
        	case DynamicGameObject.LEMMING:
        		if(((Lemming)dynObj).isMoving == CharacterMind.STAY){
            		TextureRegion keyFrame = AssetsGL.charLemmingStay.getKeyFrame(world.actTime, MyAnimation.ANIMATION_LOOPING);	
		            game.batch.draw(dynObj.position.x, dynObj.position.y, dynObj.bounds.width,
		            		dynObj.bounds.height, keyFrame);	
        		}
        		else {
            		TextureRegion keyFrame = AssetsGL.charLemmingMove.getKeyFrame(world.actTime, MyAnimation.ANIMATION_LOOPING);			            
		            game.batch.draw(keyFrame, dynObj.position.x - dynObj.bounds.width / 2, dynObj.position.y - 1.5f * dynObj.bounds.height, 
		            		dynObj.bounds.width / 2, 1.5f * dynObj.bounds.height, dynObj.bounds.width, 2 * dynObj.bounds.height, 1, 1, dynObj.angle);
		            
		            //draw my next turn point
		           // game.batch.draw(dynObj.xNextTurn, dynObj.yNextTurn , 0.33f, 0.33f, AssetsGL.red);		          
        		}
        		
	            //force point
	            game.batch.draw(dynObj.forcePoint.x, dynObj.forcePoint.y, 
	            		0.1f, 0.1f, dynObj.angle, AssetsGL.red);
	            break;
        	case Statics.DynamicGameObject.HELM_VIKING_WOODEN:
        		if(dynObj.renderParts[Statics.PhysicsBox2D.BODY_FIRST_PART_INDEX] == 1)
	            	game.batch.draw(AssetsGL.weaponHelmWoodenSimplePart1, dynObj.position.x - dynObj.bounds.width / 2, dynObj.position.y - dynObj.bounds.height / 2, 
	            			dynObj.bounds.width / 2, dynObj.bounds.height / 2,  dynObj.bounds.width / 2, dynObj.bounds.height, 1, 1, dynObj.angle);	            
        		if(dynObj.renderParts[Statics.PhysicsBox2D.BODY_SECOND_PART_INDEX] == 1)        		
	            	game.batch.draw(AssetsGL.weaponHelmWoodenSimplePart2, dynObj.position.x, dynObj.position.y - dynObj.bounds.height / 2, 
	            			0, dynObj.bounds.height / 2, dynObj.bounds.width / 2, dynObj.bounds.height, 1, 1, dynObj.angle);           		        		
        		break;
        	case Statics.DynamicGameObject.SHIELD_VIKING_WOODEN:
        		if(dynObj.renderParts[Statics.PhysicsBox2D.BODY_FIRST_PART_INDEX] == 1)
	            	game.batch.draw(AssetsGL.weaponShieldVikkingWoodenSimplePart1, dynObj.position.x - dynObj.bounds.width / 2, dynObj.position.y - dynObj.bounds.height / 2, 
	            			dynObj.bounds.width / 2, dynObj.bounds.height / 2,  dynObj.bounds.width, dynObj.bounds.height, 1, 1, dynObj.angle);	            
        		if(dynObj.renderParts[Statics.PhysicsBox2D.BODY_SECOND_PART_INDEX] == 1)        		
	            	game.batch.draw(AssetsGL.weaponShieldVikkingWoodenSimplePart2, dynObj.position.x - dynObj.bounds.width / 2, dynObj.position.y - dynObj.bounds.height / 2, 
	            			dynObj.bounds.width / 2, dynObj.bounds.height / 2, dynObj.bounds.width, dynObj.bounds.height, 1, 1, dynObj.angle);     
        		if(dynObj.renderParts[Statics.PhysicsBox2D.BODY_THIRD_PART_INDEX] == 1)        		
	            	game.batch.draw(AssetsGL.weaponShieldVikkingWoodenSimplePart3, dynObj.position.x - dynObj.bounds.width / 2, dynObj.position.y - dynObj.bounds.height / 2, 
	            			dynObj.bounds.width / 2, dynObj.bounds.height / 2, dynObj.bounds.width, dynObj.bounds.height, 1, 1, dynObj.angle); 
        		break;       		
        	case Statics.DynamicGameObject.SWORD_VIKING_BRONZE_WOODEN:
        		if(dynObj.stateHS.health > 0.5f * dynObj.stateHS.defencePower)
		            game.batch.draw(dynObj.position.x, dynObj.position.y, dynObj.bounds.width / 2, dynObj.bounds.height, dynObj.angle, AssetsGL.weaponSwordVikkingBronzeWooden);	
        		else 
		            game.batch.draw(dynObj.position.x, dynObj.position.y, dynObj.bounds.width / 2, dynObj.bounds.height, dynObj.angle, AssetsGL.weaponSwordVikkingBronzeWoodenBroken); 
        		break;
        	case Statics.DynamicGameObject.AXE_VIKING_MONOSIDE_BRONZE_WOODEN:
        		if(dynObj.stateHS.health > 0.5f * dynObj.stateHS.defencePower)
		            game.batch.draw(dynObj.position.x, dynObj.position.y, dynObj.bounds.width / 2, dynObj.bounds.height, dynObj.angle, AssetsGL.weaponAxeMonosideVikkingBronzeWooden);	
        		else 
		            game.batch.draw(dynObj.position.x, dynObj.position.y, dynObj.bounds.width / 2, dynObj.bounds.height, dynObj.angle, AssetsGL.weaponAxeMonosideVikkingBronzeWoodenBroken); 
        		break;
        	case Statics.DynamicGameObject.AXE_VIKING_DOUBLESIDE_BRONZE_WOODEN:
        		if(dynObj.stateHS.health > 0.5f * dynObj.stateHS.defencePower)
		            game.batch.draw(dynObj.position.x, dynObj.position.y, dynObj.bounds.width, dynObj.bounds.height, dynObj.angle, AssetsGL.weaponAxeDoublesideVikkingBronzeWooden);	
        		else 
		            game.batch.draw(dynObj.position.x, dynObj.position.y, dynObj.bounds.width, dynObj.bounds.height, dynObj.angle, AssetsGL.weaponAxeDoublesideVikkingBronzeWoodenBroken); 
        		break;
        	case Statics.DynamicGameObject.SPEAR_VIKING_BRONZE_WOODEN:
        		if(dynObj.stateHS.health > 0.5f * dynObj.stateHS.defencePower)
		            game.batch.draw(dynObj.position.x, dynObj.position.y, dynObj.bounds.width / 2, dynObj.bounds.height, dynObj.angle, AssetsGL.weaponSpearVikkingBronzeWooden);	
        		else 
		            game.batch.draw(dynObj.position.x, dynObj.position.y, dynObj.bounds.width / 2, dynObj.bounds.height, dynObj.angle, AssetsGL.weaponSpearVikkingBronzeWoodenBroken); 
        		break;
        	case DynamicGameObject.HEDGEHOG:
        		if(((Hedgehog)dynObj).isMoving == Hedgehog.STAY){
            		TextureRegion keyFrame = AssetsGL.charHedgehogStay.getKeyFrame(world.actTime, MyAnimation.ANIMATION_LOOPING);	
		            game.batch.draw(dynObj.position.x, dynObj.position.y, dynObj.bounds.width,
		            		dynObj.bounds.height, keyFrame);		    
        		}
        		else if(((Hedgehog)dynObj).isMoving == Hedgehog.MOVE_UP){
            		TextureRegion keyFrame = AssetsGL.charHedgehogMove.getKeyFrame(world.actTime, MyAnimation.ANIMATION_LOOPING);	
		            game.batch.draw(dynObj.position.x, dynObj.position.y, dynObj.bounds.width,
		            		dynObj.bounds.height, 90, keyFrame);	
        		}
        		else if(((Hedgehog)dynObj).isMoving == Hedgehog.MOVE_LEFT){
            		TextureRegion keyFrame = AssetsGL.charHedgehogMove.getKeyFrame(world.actTime, MyAnimation.ANIMATION_LOOPING);	
		            game.batch.draw(dynObj.position.x, dynObj.position.y, -dynObj.bounds.width,
		            		dynObj.bounds.height, keyFrame);	
        		}
        		else if(((Hedgehog)dynObj).isMoving == Hedgehog.MOVE_RIGHT){
            		TextureRegion keyFrame = AssetsGL.charHedgehogMove.getKeyFrame(world.actTime, MyAnimation.ANIMATION_LOOPING);	
		            game.batch.draw(dynObj.position.x, dynObj.position.y, dynObj.bounds.width,
		            		dynObj.bounds.height, keyFrame);	
        		}
        		else if(((Hedgehog)dynObj).isMoving == Hedgehog.MOVE_DOWN){
            		TextureRegion keyFrame = AssetsGL.charHedgehogMove.getKeyFrame(world.actTime, MyAnimation.ANIMATION_LOOPING);	
		            game.batch.draw(dynObj.position.x, dynObj.position.y, dynObj.bounds.width,
		            		dynObj.bounds.height, 270, keyFrame);	
        		}
        		break;	
        	case Statics.DynamicGameObject.FSKILL_ICEBALL:
        		float scale = MyMath.getRisingBulbApperianceBasedOnSin(dynObj.actTime, 0.5f, 0.2f);
        		if(dynObj.stateHS.health > dynObj.stateHS.defencePower / 2)
	            	game.batch.draw(AssetsGL.fSkillIce, dynObj.position.x - dynObj.bounds.width / 2, dynObj.position.y - dynObj.bounds.height / 2, 
	            			dynObj.bounds.width / 2, dynObj.bounds.height / 2,  dynObj.bounds.width, dynObj.bounds.height, scale, scale, dynObj.angle);	            
        		else
	            	game.batch.draw(AssetsGL.fSkillIceBroken, dynObj.position.x - dynObj.bounds.width / 2, dynObj.position.y - dynObj.bounds.height / 2, 
	            			dynObj.bounds.width / 2, dynObj.bounds.height / 2,  dynObj.bounds.width, dynObj.bounds.height, scale, scale, dynObj.angle);	            
        		break; 
        	case Statics.DynamicGameObject.SEED_BEAT:
        		float scaleX = ((Seed)dynObj).getCurScale(true);
        		float scaleY = ((Seed)dynObj).getCurScale(false);
        		if(dynObj.stateHS.health > dynObj.stateHS.defencePower / 2)
	            	game.batch.draw(AssetsGL.gameAtlas.findRegion("seedBeat"), dynObj.position.x - dynObj.bounds.width / 2, dynObj.position.y - dynObj.bounds.height / 2, 
	            			dynObj.bounds.width / 2, dynObj.bounds.height / 2,  dynObj.bounds.width, dynObj.bounds.height, scaleX, scaleY, dynObj.angle);	            
        		else
	            	game.batch.draw(AssetsGL.gameAtlas.findRegion("seedBeatDamage"), dynObj.position.x - dynObj.bounds.width / 2, dynObj.position.y - dynObj.bounds.height / 2, 
	            			dynObj.bounds.width / 2, dynObj.bounds.height / 2,  dynObj.bounds.width, dynObj.bounds.height, scaleX, scaleY, dynObj.angle);	 
        		break;
        	case Statics.DynamicGameObject.SEED_HURT:
        		scaleX = ((Seed)dynObj).getCurScale(true);
        		scaleY = ((Seed)dynObj).getCurScale(false);
        		if(dynObj.stateHS.health > dynObj.stateHS.defencePower / 2)
	            	game.batch.draw(AssetsGL.gameAtlas.findRegion("seedHurt"), dynObj.position.x - dynObj.bounds.width / 2, dynObj.position.y - dynObj.bounds.height / 2, 
	            			dynObj.bounds.width / 2, dynObj.bounds.height / 2,  dynObj.bounds.width, dynObj.bounds.height, scaleX, scaleY, dynObj.angle);	            
        		else
	            	game.batch.draw(AssetsGL.gameAtlas.findRegion("seedHurtDamage"), dynObj.position.x - dynObj.bounds.width / 2, dynObj.position.y - dynObj.bounds.height / 2, 
	            			dynObj.bounds.width / 2, dynObj.bounds.height / 2,  dynObj.bounds.width, dynObj.bounds.height, scaleX, scaleY, dynObj.angle);	 
        		break;
        	case Statics.DynamicGameObject.SEED_MINER:
        		scaleX = ((Seed)dynObj).getCurScale(true);
        		scaleY = ((Seed)dynObj).getCurScale(false);
        		if(dynObj.stateHS.health > dynObj.stateHS.defencePower / 2)
	            	game.batch.draw(AssetsGL.gameAtlas.findRegion("seedMiner"), dynObj.position.x - dynObj.bounds.width / 2, dynObj.position.y - dynObj.bounds.height / 2, 
	            			dynObj.bounds.width / 2, dynObj.bounds.height / 2,  dynObj.bounds.width, dynObj.bounds.height, scaleX, scaleY, dynObj.angle);	            
        		else
	            	game.batch.draw(AssetsGL.gameAtlas.findRegion("seedMinerDamage"), dynObj.position.x - dynObj.bounds.width / 2, dynObj.position.y - dynObj.bounds.height / 2, 
	            			dynObj.bounds.width / 2, dynObj.bounds.height / 2,  dynObj.bounds.width, dynObj.bounds.height, scaleX, scaleY, dynObj.angle);	 
        		break;
        	case Statics.DynamicGameObject.SEED_MAD:
        		scaleX = ((Seed)dynObj).getCurScale(true);
        		scaleY = ((Seed)dynObj).getCurScale(false);
        		if(dynObj.stateHS.health > dynObj.stateHS.defencePower / 2)
	            	game.batch.draw(AssetsGL.gameAtlas.findRegion("seedMad"), dynObj.position.x - dynObj.bounds.width / 2, dynObj.position.y - dynObj.bounds.height / 2, 
	            			dynObj.bounds.width / 2, dynObj.bounds.height / 2,  dynObj.bounds.width, dynObj.bounds.height, scaleX, scaleY, dynObj.angle);	            
        		else
	            	game.batch.draw(AssetsGL.gameAtlas.findRegion("seedMadDamage"), dynObj.position.x - dynObj.bounds.width / 2, dynObj.position.y - dynObj.bounds.height / 2, 
	            			dynObj.bounds.width / 2, dynObj.bounds.height / 2,  dynObj.bounds.width, dynObj.bounds.height, scaleX, scaleY, dynObj.angle);	 
        		break;
        	case Statics.DynamicGameObject.SEED_SMILE:
        		scaleX = ((Seed)dynObj).getCurScale(true);
        		scaleY = ((Seed)dynObj).getCurScale(false);
        		if(dynObj.stateHS.health > dynObj.stateHS.defencePower / 2)
	            	game.batch.draw(AssetsGL.gameAtlas.findRegion("seedSmile"), dynObj.position.x - dynObj.bounds.width / 2, dynObj.position.y - dynObj.bounds.height / 2, 
	            			dynObj.bounds.width / 2, dynObj.bounds.height / 2,  dynObj.bounds.width, dynObj.bounds.height, scaleX, scaleY, dynObj.angle);	            
        		else
	            	game.batch.draw(AssetsGL.gameAtlas.findRegion("seedSmileDamage"), dynObj.position.x - dynObj.bounds.width / 2, dynObj.position.y - dynObj.bounds.height / 2, 
	            			dynObj.bounds.width / 2, dynObj.bounds.height / 2,  dynObj.bounds.width, dynObj.bounds.height, scaleX, scaleY, dynObj.angle);	 
        		break;  
        		
        	case Statics.DynamicGameObject.TREE_HURT:
        	case Statics.DynamicGameObject.TREE_HURT_ORDYNARY_SEGMENT:
        		if (dynObj.objType == Statics.DynamicGameObject.TREE_HURT) {
	        		scaleX = 1 + MyMath.getTwoStagedHurtBittenScale(world.actTime, 0, 5f, 5f, 5f, 5f, 0.03f, true);
	        		scaleY = 1 + MyMath.getTwoStagedHurtBittenScale(world.actTime, 0, 5f, 5f, 5f, 5f, 0.03f, false);
	            	game.batch.draw(AssetsGL.gameAtlas.findRegion("treeHurtDown"), dynObj.position.x - dynObj.bounds.width / 2, dynObj.position.y - dynObj.bounds.height / 2, 
	            			dynObj.bounds.width / 2, dynObj.bounds.height / 2,  dynObj.bounds.width, dynObj.bounds.height, scaleX, scaleX, dynObj.angle);	
        		}
        		
        		if (dynObj.objType == Statics.DynamicGameObject.TREE_HURT_ORDYNARY_SEGMENT) {
	            	game.batch.draw(AssetsGL.gameAtlas.findRegion("treeHurtItem"), dynObj.position.x - dynObj.bounds.width / 2, dynObj.position.y - dynObj.bounds.height / 4, 
	            			dynObj.bounds.width / 2, dynObj.bounds.height / 4,  dynObj.bounds.width, dynObj.bounds.height / 2, 1, 1, dynObj.angle);	       			
        		}
        		
        		if (dynObj.objType == Statics.DynamicGameObject.TREE_HURT) {
	        		scaleY = 1 + MyMath.getTwoStagedHurtBittenScale(world.actTime, 0, 5f, 5f, 5f, 5f, 0.03f, false);
	            	game.batch.draw(AssetsGL.gameAtlas.findRegion("treeHurtUp"), dynObj.position.x - dynObj.bounds.width / 2, dynObj.position.y - dynObj.bounds.height / 2 + 1, 
	            			dynObj.bounds.width / 2, dynObj.bounds.height / 2 - 1,  dynObj.bounds.width, dynObj.bounds.height, scaleY, scaleY, dynObj.angle);		
	            	float beatPeriod = ((TreeHurt)dynObj).getHurtBeatPeriod();
	            	scaleX = 1 + MyMath.getTwoStagedHurtBittenScale(world.actTime, 0, 0, 0.15f, beatPeriod, 1f, 0.1f, true);
	        		scaleY = 1 + MyMath.getTwoStagedHurtBittenScale(world.actTime, 0, 0, 0.15f, beatPeriod, 1f, 0.1f, false);	        		
	            	game.batch.draw(AssetsGL.gameAtlas.findRegion("treeHurtHurt"), dynObj.position.x - dynObj.bounds.width / 8 + 0.05f, dynObj.position.y - dynObj.bounds.height / 8 + .4f, 
	            			dynObj.bounds.width / 8, dynObj.bounds.height / 8 - 0.4f,  dynObj.bounds.width / 4, dynObj.bounds.height / 4, scaleX, scaleY, dynObj.angle);	
        		}
            	break;   		


        	}
        	
    		if(dynObj.isCharacter && dynObj.actTime <= Gifts.APPERAIANCE_PERIOD)
    		{
	            TextureRegion keyFrame = AssetsGL.giftApper.getKeyFrame(world.actTime, 15, 0);		            
	            game.batch.draw(dynObj.position.x, dynObj.position.y, 1.5f, 1.5f, keyFrame);
    		}	            	
        	
     	            	
        }	            
        
        //draw relief
        size = world.relief.size();
        
        for(int i=0; i< size; ++i)
        {
        	GameObject relief = world.relief.get(i);
        	if(relief.type == WorldKingSnake.WATER)
        	{
	            TextureRegion keyFrame = AssetsGL.water.getKeyFrame(world.actTime, MyAnimation.ANIMATION_LOOPING);		            
	            game.batch.draw(relief.position.x + 0.5f, relief.position.y+ 0.5f, 1, 1, keyFrame);	    
        	}
        }
        
        //draw static effects above objects
        size = world.statEffectsAbove.size();
        
        for(GameObject effectObj : world.statEffectsAbove)
        {      	
        	switch(effectObj.type)
        	{
        	case Statics.StaticEffect.BOMB_FROM_TREE_EXPLOSION:
        		TextureRegion keyFrame = AssetsGL.treeBombExplosion.getKeyFrame(world.actTime, 8, 0);	
	            game.batch.draw(effectObj.position.x, effectObj.position.y, effectObj.bounds.width,
	            		effectObj.bounds.height, keyFrame);	
	            break;
        	case Statics.StaticEffect.IMPACT:
	    		{
		            keyFrame = AssetsGL.impact.getKeyFrame(effectObj.actTime, MyAnimation.ANIMATION_NONLOOPING);	
		            game.batch.draw(effectObj.position.x, effectObj.position.y, effectObj.bounds.width, effectObj.bounds.height, ((StaticEffect) effectObj).angle, keyFrame);
	    		}
	    		break;
        	case Statics.StaticEffect.IMPACT_STARS:
    			for(int j = 0; j< ((StaticEffectImpactStars) effectObj).GetImpactStarsNumber(); j++)
    			{
    				Vector2 point = (Vector2) ((StaticEffectImpactStars) effectObj).GetImpactStar(j);
    				
		            keyFrame = AssetsGL.hitStars.getKeyFrame(world.actTime, MyAnimation.ANIMATION_LOOPING);		            
		            game.batch.draw(point.x, point.y, StaticEffect.IMPACT_STAR_SIZE, StaticEffect.IMPACT_STAR_SIZE, ((StaticEffect) effectObj).angle, keyFrame);	
    			}
	            break;
        	}	         	            	
        }
        
        //draw emitter particles effects using: 1.Atlas 2.Pooling 3.Batching
        
//       if(AssetsGL.particlesEffectAdditiveFire.isComplete())
//       {
//    	   	AssetsGL.particlesEffectAdditiveFire.setDuration(5000);
//        	AssetsGL.particlesEffectAdditiveFire.start();
//       }
        
        AssetsGL.particlesEffectAdditiveFire.draw(game.batch, deltaTime);
        
        for (int i = AssetsGL.particlesEffectsAdditiveArray.size - 1; i >= 0; i--)
        {
            PooledEffect effect = AssetsGL.particlesEffectsAdditiveArray.get(i);
            effect.draw(game.batch, deltaTime);
            if (effect.isComplete())
                ParticlesEffectsManager.releaseEffectByValue(effect);
        }
        
        game.batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        
        for (int i = AssetsGL.particlesEffectsNormalArray.size - 1; i >= 0; i--)
        {
            PooledEffect effect = AssetsGL.particlesEffectsNormalArray.get(i);
            effect.draw(game.batch, deltaTime);
            if (effect.isComplete())
                ParticlesEffectsManager.releaseEffectByValue(effect);
        }
        
        //draw dynamic effects above objects
        size = world.dynEffectsAboveObjects.size();
        		
        for(int i= 0; i < size; ++i)
        {
        	DynamicGameObject dynObj =  (DynamicGameObject)world.dynEffectsAboveObjects.get(i);	            	            		
            
        	switch(dynObj.objType)
        	{
	        	case Statics.DynamicGameObject.FSKILL_FORWARD_ATTACK_IMPULSE_EFFECT:
			        game.batch.draw(dynObj.position.x, dynObj.position.y, dynObj.bounds.width, dynObj.bounds.height, dynObj.angle, AssetsGL.impulseForwardAttack);
			        break;
	        	case Statics.DynamicGameObject.FSKILL_FORWARD_ATTACK_ICE_EFFECT:
			        game.batch.draw(dynObj.position.x, dynObj.position.y, dynObj.bounds.width, dynObj.bounds.height, dynObj.angle, AssetsGL.impulseForwardAttack);
			        break;
        	}
     	            	
        }
        
        //draw Flying objects
        size = world.flyingObjects.size();
        
        for(int i= 0; i < size; ++i)
        {
        	DynamicGameObject dynObj =  (DynamicGameObject)world.flyingObjects.get(i);
        	
            //bombtree 
        	switch(dynObj.objType)
        	{
        	case DynamicGameObject.TREE_BOMB:
	            float rock = 0.07f * (float)Math.sin(world.actTime);	            
	            game.batch.draw(dynObj.position.x, dynObj.position.y + rock, dynObj.bounds.width,
	            		dynObj.bounds.height, AssetsGL.bombTree);
	            break;
        	case DynamicGameObject.BOMB_FALL_ACTION:
	            game.batch.draw(dynObj.position.x, dynObj.position.y, dynObj.bounds.width,
	            		dynObj.bounds.height, AssetsGL.bombFromTree);	
	            break;
        	}
     	            	
        }
        
        //draw dynamic effects above
        size = world.dynEffectsAbove.size();
        		
        for(int i= 0; i < size; i++)
        {
        	DynamicGameObject dynObj =  (DynamicGameObject)world.dynEffectsAbove.get(i);	            	            		
            
        	switch(dynObj.objType)
        	{
        	case DynamicEffect.HEALTH_UP_EFFECT:
        		TextureRegion keyFrame = AssetsGL.healthUpEffect.getKeyFrame(world.actTime, 6, 0);	
	            game.batch.draw(dynObj.position.x, dynObj.position.y, dynObj.bounds.width,
	            		dynObj.bounds.height, keyFrame);	
	            break;
        	case DynamicEffect.HEALTH_DOWN_EFFECT:
        		keyFrame = AssetsGL.healthDownEffect.getKeyFrame(world.actTime, 6, 0);	
	            game.batch.draw(dynObj.position.x, dynObj.position.y, dynObj.bounds.width,
	            		dynObj.bounds.height, keyFrame);	
	            break;
        	case DynamicEffect.GET_COINS_EFFECT:
        		keyFrame = AssetsGL.getCoinsEffect.getKeyFrame(world.actTime, 6, 0);	
	            game.batch.draw(dynObj.position.x, dynObj.position.y, dynObj.bounds.width,
	            		dynObj.bounds.height, keyFrame);	
	            break;
        	case DynamicEffect.LOOSE_COINS_EFFECT:
        		keyFrame = AssetsGL.looseCoinsEffect.getKeyFrame(world.actTime, 6, 0);	
	            game.batch.draw(dynObj.position.x, dynObj.position.y, dynObj.bounds.width,
	            		dynObj.bounds.height, keyFrame);	
	            break;
        	case DynamicEffect.ATTACK_UP_EFFECT:
        		keyFrame = AssetsGL.attackUpEffect.getKeyFrame(world.actTime, 6, 0);	
	            game.batch.draw(dynObj.position.x, dynObj.position.y, dynObj.bounds.width,
	            		dynObj.bounds.height, keyFrame);	
	            break;
        	case DynamicEffect.ATTACK_DOWN_EFFECT:
        		keyFrame = AssetsGL.attackDownEffect.getKeyFrame(world.actTime, 6, 0);	
	            game.batch.draw(dynObj.position.x, dynObj.position.y, dynObj.bounds.width,
	            		dynObj.bounds.height, keyFrame);	
	            break;
        	case DynamicEffect.DEFENSE_UP_EFFECT:
        		keyFrame = AssetsGL.defenseUpEffect.getKeyFrame(world.actTime, 6, 0);	
	            game.batch.draw(dynObj.position.x, dynObj.position.y, dynObj.bounds.width,
	            		dynObj.bounds.height, keyFrame);	
	            break;
        	case DynamicEffect.DEFENSE_DOWN_EFFECT:
        		keyFrame = AssetsGL.defenseDownEffect.getKeyFrame(world.actTime, 6, 0);	
	            game.batch.draw(dynObj.position.x, dynObj.position.y, dynObj.bounds.width,
	            		dynObj.bounds.height, keyFrame);	
	            break;
        	case DynamicEffect.SPEED_UP_EFFECT:
        		keyFrame = AssetsGL.speedUpEffect.getKeyFrame(world.actTime, 6, 0);	
	            game.batch.draw(dynObj.position.x, dynObj.position.y, dynObj.bounds.width,
	            		dynObj.bounds.height, keyFrame);	
	            break;
        	case DynamicEffect.SPEED_DOWN_EFFECT:
        		keyFrame = AssetsGL.speedDownEffect.getKeyFrame(world.actTime, 6, 0);	
	            game.batch.draw(dynObj.position.x, dynObj.position.y, dynObj.bounds.width,
	            		dynObj.bounds.height, keyFrame);	
	            break;
        	}
     	            	
        }
          
        
        //draw red squares
        for(int i= 0; i < 36; i++)
        {
        	for(int j = 0; j < 60 ; j++)
        	{
        		//show log pos
        		//if(world.fields[3][i][j] > 0)
        		//	game.batch.draw(((float) i) /3, ((float) j) /3, 0.33f, 0.33f, AssetsGL.red);	 
        		
        		if(world.fields[WorldKingSnake.WORLD_LAB_ORIENT_WEIGHTS_LEVEL][i][j] != LabOrientDejkstra.LAB_ORIENT_EMPTY_SPACE_WEIGHT
        		//		&& world.fields[World.WORLD_LAB_ORIENT_WEIGHTS_LEVEL][i][j] < LabOrientDejkstra.LAB_ORIENT_MAX_WEIGHT
        		//		&& world.fields[World.WORLD_LAB_ORIENT_WEIGHTS_LEVEL][i][j] < LabOrientDejkstra.LAB_ORIENT_EMPTY_SPACE_WEIGHT
                //		&& world.fields[WorldKingSnake.WORLD_LAB_ORIENT_WEIGHTS_LEVEL][i][j] < LabOrientDejkstra.LAB_ORIENT_EMPTY_SPACE_WEIGHT
                		&& world.fields[WorldKingSnake.WORLD_LAB_ORIENT_WEIGHTS_LEVEL][i][j] < 0
        				)
        			textGL.ViewNumber(AssetsGL.numbers, String.valueOf((int)world.fields[WorldKingSnake.WORLD_LAB_ORIENT_WEIGHTS_LEVEL][i][j]), ((float) i) /3, ((float) j) /3, 0.15f);

        		
        	//	if(world.fields[World.WORLD_LAB_ORIENT_WEIGHTS_LEVEL][i][j] < LabOrientDejkstra.LAB_ORIENT_EMPTY_SPACE_WEIGHT)
        	//		game.batch.draw(((float) i) /3, ((float) j) /3 , 0.33f, 0.33f, AssetsGL.green);
        	//	else if(world.fields[World.WORLD_LAB_ORIENT_WEIGHTS_LEVEL][i][j] > LabOrientDejkstra.LAB_ORIENT_EMPTY_SPACE_WEIGHT * 2)
        	//		game.batch.draw(((float) i) /3, ((float) j) /3, 0.33f, 0.33f, AssetsGL.red);
        	//	else if(world.fields[World.WORLD_LAB_ORIENT_WEIGHTS_LEVEL][i][j] > LabOrientDejkstra.LAB_ORIENT_EMPTY_SPACE_WEIGHT)
        	//		game.batch.draw(((float) i) /3, ((float) j) /3, 0.33f, 0.33f, AssetsGL.yellow);
        	}
        }
        	
        

        
       	/*size = world.mind.labOrientD.pathList.size() - 1;
       	
        
        for(int i=size; i>= 0; --i)
        {
        	VertsPointers vert = world.mind.labOrientD.pathList.get(i);
        	int x = (vert.indexByBits & 0xff00) >> 8;
        	int y = vert.indexByBits & 0x00ff;
    		game.batch.draw(((float) x) /3, ((float) y) /3, 0.33f, 0.33f, AssetsGL.red);	            	
    		//game.batch.draw( x + 0.5f , y + 0.5f, 1f, 1f, AssetsGL.red);	            	

        }*/
        	
        
        //draw high score
        game.batch.draw(5f, 19.3f, 1.1f, 1.1f, AssetsGL.apple);	
        textGL.ViewNumber(AssetsGL.numbers, String.valueOf((int)world.dynObjects.get(1).stateHS.health), 6f, 18.2f);
        game.batch.draw(9f, 19.3f, 1.1f, 1.1f, AssetsGL.coin);
        textGL.ViewNumber(AssetsGL.numbers, String.valueOf(world.dynObjects.get(1).stateHS.coins),10.1f, 18.2f);        
        
        //joystick
        float x = Statics.Render.JOYSTICK_POS_X;
        float y = Statics.Render.JOYSTICK_POS_Y;
        	
		int intActTime;
		int intOldTime;

		if(joystickTurnMode)
		{
			intActTime = (int) (actTime / Statics.Render.JOYSTICK_PERIOD);
			intOldTime = (int) (oldTime / Statics.Render.JOYSTICK_PERIOD);
		}
		else
		{
			intActTime = (int) (actTime /(4f * Statics.Render.JOYSTICK_PERIOD));
			intOldTime = (int) (oldTime /(4f * Statics.Render.JOYSTICK_PERIOD));			
		}
		
		if(intOldTime != intActTime && joystickCanTurnFlag)
		{			
			if(joystickTurnMode)
				joystickTurnMode = false;
			else
				joystickTurnMode = true;
			
			joystickCanTurnFlag = false;
		}
		
		if(intOldTime == intActTime)
			joystickCanTurnFlag = true;
		

		if(controls.joystick.state == Joystick.JoystickState.Ready)
		{
			if(joystickTurnMode)
			{
				float periodTime = actTime % Statics.Render.JOYSTICK_PERIOD;
				float angle = 360 * periodTime / Statics.Render.JOYSTICK_PERIOD;
		    	game.batch.draw(x, y, 2, 2, angle, AssetsGL.joystick); 
			}
			else
			{
		    	game.batch.draw(x, y, 2, 2, AssetsGL.joystick); 
				TextureRegion keyFrame = AssetsGL.joystickArrowHorizont.getKeyFrame(actTime, MyAnimation.ANIMATION_LOOPING);	
				game.batch.draw(x + Statics.Render.JOYSTICK_LEFT_ARROW_OFFSET_X, y + Statics.Render.JOYSTICK_HORISONT_ARROW_OFFSET_Y, 1, 1, keyFrame);			
				game.batch.draw(x + Statics.Render.JOYSTICK_RIGHT_ARROW_OFFSET_X, y + Statics.Render.JOYSTICK_HORISONT_ARROW_OFFSET_Y, -1, 1, keyFrame);               
		        keyFrame = AssetsGL.joystickArrowVert.getKeyFrame(actTime, MyAnimation.ANIMATION_LOOPING);	
		        game.batch.draw(x + Statics.Render.JOYSTICK_VERT_ARROW_OFFSET_X, y + Statics.Render.JOYSTICK_UP_ARROW_OFFSET_Y, 1, 1, keyFrame);
		        game.batch.draw(x + Statics.Render.JOYSTICK_VERT_ARROW_OFFSET_X, y + Statics.Render.JOYSTICK_DOWN_ARROW_OFFSET_Y, 1, -1, keyFrame);        			
			}
		}
		else
		{
	    	game.batch.draw(x, y, 2, 2, controls.joystick.angle, AssetsGL.joystick); 	    	
			TextureRegion keyFrame = AssetsGL.joystickArrowHorizont.getKeyFrame(world.actTime, MyAnimation.ANIMATION_LOOPING);	
			game.batch.draw(keyFrame, x - 1.5f, y - 0.5f, 1.5f, 0.5f, 1, 1, 1, 1, controls.joystick.angle);	
			game.batch.draw(keyFrame, x + 1.5f, y - 0.5f, -1.5f, 0.5f, -1, 1, 1, 1, controls.joystick.angle);
			
	        keyFrame = AssetsGL.joystickArrowVert.getKeyFrame(world.actTime, MyAnimation.ANIMATION_LOOPING);	
			game.batch.draw(keyFrame, x - 0.5f, y + 0.5f, 0.5f, -0.5f, 1, 1, 1, 1, controls.joystick.angle);	
			game.batch.draw(keyFrame, x - 0.5f, y - 0.5f, 0.5f, 0.5f, 1, -1, 1, 1, controls.joystick.angle);			
		}		
		game.batch.draw(x, y, 2, 2, AssetsGL.joystickCenter); 
		
		//first draw passive controls
		Controls.ControlActive[] items = Controls.ControlActive.values();
		
		for(int i = 0; i < items.length; ++i)
		{
			if(items[i] != controls.controlsActive)
				drawGameControls(items[i]);				
		}
		
		//then active game control
		drawGameControls(controls.controlsActive);
       
	}
    
    void drawGameControls(Controls.ControlActive control)
    {
    	TextureRegion keyFrame;
    	
		float offset;
		float offsetDiag;
     	float radius;
     	float radiusDiag;
    	
    	if(control == Controls.ControlActive.Jumps)
    	{
    		keyFrame = AssetsGL.controlJump.getKeyFrame(actTime, MyAnimation.ANIMATION_LOOPING);			
            
    		offset = Statics.Render.CONTROL_JUMPS_SKILLS_OFFSET;
    		offsetDiag = (float) Math.sqrt(offset);
         	radius = offset;
         	radiusDiag = offsetDiag;
         	boolean showJumpSkills = true;
            
            if(controls.jumps.state == ControlJumps.JumpsState.Starting)
            {
             	radius = offset * controls.jumps.startStateChangeTime / Statics.Controls.CONTROL_JUMP_STATE_CHANGE_PERIOD;
             	radiusDiag = offsetDiag * controls.jumps.startStateChangeTime / Statics.Controls.CONTROL_JUMP_STATE_CHANGE_PERIOD;
            }
            else if(controls.jumps.state == ControlJumps.JumpsState.Ending)
            {
             	radius = offset - offset * controls.jumps.startStateChangeTime / Statics.Controls.CONTROL_JUMP_STATE_CHANGE_PERIOD;
             	radiusDiag = offsetDiag - offsetDiag * controls.jumps.startStateChangeTime / Statics.Controls.CONTROL_JUMP_STATE_CHANGE_PERIOD;
            }
            else if(controls.jumps.state == ControlJumps.JumpsState.Ready)
            	showJumpSkills = false;
            
            if(showJumpSkills)
            {
    			if(world.character.fSkills.forwardAttack && world.character.fSkills.isSkillReady(Statics.FightingSkills.FORWARD_ATTACK))
    			{
    		        game.batch.draw(Statics.Render.CONTROL_JUMPS_POS_X, Statics.Render.CONTROL_JUMPS_POS_Y + radius, 
    		        		Statics.Render.CONTROL_JUMPS_SKILLS_SIZE, Statics.Render.CONTROL_JUMPS_SKILLS_SIZE, keyFrame);
    			}
    			if(world.character.fSkills.forwardLeftHook && world.character.fSkills.isSkillReady(Statics.FightingSkills.FORWARD_LEFT_HOOK))
    			{	
    		        game.batch.draw(Statics.Render.CONTROL_JUMPS_POS_X - radiusDiag, Statics.Render.CONTROL_JUMPS_POS_Y + radiusDiag, 
    		        		Statics.Render.CONTROL_JUMPS_SKILLS_SIZE, Statics.Render.CONTROL_JUMPS_SKILLS_SIZE, keyFrame);
    			}
    			if(world.character.fSkills.forwardRightHook && world.character.fSkills.isSkillReady(Statics.FightingSkills.FORWARD_RIGHT_HOOK))
    			{
    		        game.batch.draw(Statics.Render.CONTROL_JUMPS_POS_X + radiusDiag, Statics.Render.CONTROL_JUMPS_POS_Y + radiusDiag, 
    		        		Statics.Render.CONTROL_JUMPS_SKILLS_SIZE, Statics.Render.CONTROL_JUMPS_SKILLS_SIZE, keyFrame);
    			}
    			if(world.character.fSkills.forwardLeftTurn && world.character.fSkills.isSkillReady(Statics.FightingSkills.FORWARD_LEFT_TURN))
    			{
    		        game.batch.draw(Statics.Render.CONTROL_JUMPS_POS_X - radius, Statics.Render.CONTROL_JUMPS_POS_Y, 
    		        		Statics.Render.CONTROL_JUMPS_SKILLS_SIZE, Statics.Render.CONTROL_JUMPS_SKILLS_SIZE, keyFrame);
    			}
    			if(world.character.fSkills.forwardRightTurn && world.character.fSkills.isSkillReady(Statics.FightingSkills.FORWARD_RIGHT_TURN))
    			{	
    		        game.batch.draw(Statics.Render.CONTROL_JUMPS_POS_X + radius, Statics.Render.CONTROL_JUMPS_POS_Y, 
    		        		Statics.Render.CONTROL_JUMPS_SKILLS_SIZE, Statics.Render.CONTROL_JUMPS_SKILLS_SIZE, keyFrame);

    			}
    			if(world.character.fSkills.backLeftHook && world.character.fSkills.isSkillReady(Statics.FightingSkills.BACK_LEFT_HOOK))
    			{	
    		        game.batch.draw(Statics.Render.CONTROL_JUMPS_POS_X - radiusDiag, Statics.Render.CONTROL_JUMPS_POS_Y - radiusDiag, 
    		        		Statics.Render.CONTROL_JUMPS_SKILLS_SIZE, Statics.Render.CONTROL_JUMPS_SKILLS_SIZE, keyFrame);				
    			}
    			if(world.character.fSkills.backRightHook && world.character.fSkills.isSkillReady(Statics.FightingSkills.BACK_RIGHT_HOOK))
    			{	
    		        game.batch.draw(Statics.Render.CONTROL_JUMPS_POS_X + radiusDiag, Statics.Render.CONTROL_JUMPS_POS_Y - radiusDiag, 
    		        		Statics.Render.CONTROL_JUMPS_SKILLS_SIZE, Statics.Render.CONTROL_JUMPS_SKILLS_SIZE, keyFrame);

    			}
    			if(world.character.fSkills.backAttack && world.character.fSkills.isSkillReady(Statics.FightingSkills.BACK_ATTACK))
    			{
    		        game.batch.draw(Statics.Render.CONTROL_JUMPS_POS_X, Statics.Render.CONTROL_JUMPS_POS_Y - radius, 
    		        		Statics.Render.CONTROL_JUMPS_SKILLS_SIZE, Statics.Render.CONTROL_JUMPS_SKILLS_SIZE, keyFrame);
    			}	      	
            }
            
            game.batch.draw(Statics.Render.CONTROL_JUMPS_POS_X, Statics.Render.CONTROL_JUMPS_POS_Y, 
            		Statics.Render.CONTROL_JUMPS_SIZE, Statics.Render.CONTROL_JUMPS_SIZE, keyFrame);	
    	}
    	else if(control == Controls.ControlActive.Impulses)
    	{
    	    game.batch.end();
    	    game.batch.begin();
    		keyFrame = AssetsGL.controlImpulse.getKeyFrame(actTime, MyAnimation.ANIMATION_LOOPING);	
    	    
    		offset = Statics.Render.CONTROL_JUMPS_SKILLS_OFFSET;
         	radius = offset;
         	boolean showImpulseSkills = true;
         	            
            if(controls.impulses.state == ControlImpulse.ImpulseState.Starting)
             	radius = offset * controls.impulses.startStateChangeTime / Statics.Controls.CONTROL_IMP_STATE_CHANGE_PERIOD;
            else if(controls.impulses.state == ControlImpulse.ImpulseState.Ending)
             	radius = offset - offset * controls.impulses.startStateChangeTime / Statics.Controls.CONTROL_IMP_STATE_CHANGE_PERIOD;
            else if(controls.impulses.state == ControlImpulse.ImpulseState.Ready)
            	showImpulseSkills = false;
            
            if(showImpulseSkills)
            {
    			if(world.character.fSkills.impulseForwardAttack && world.character.fSkills.isSkillReady(Statics.FightingSkills.IMPULSE_ATTACK_FORWARD))
    			{
    		        game.batch.draw(Statics.Render.CONTROL_IMPULSE_POS_X, Statics.Render.CONTROL_IMPULSE_POS_Y + radius, 
    		        		Statics.Render.CONTROL_IMPULSE_SKILLS_SIZE, Statics.Render.CONTROL_IMPULSE_SKILLS_SIZE, keyFrame);
    			}
    			if(world.character.fSkills.impulseLeftSideDef && world.character.fSkills.isSkillReady(Statics.FightingSkills.IMPULSE_DEFFENSE_LEFTSIDE))
    			{	
    		        game.batch.draw(Statics.Render.CONTROL_IMPULSE_POS_X - radius, Statics.Render.CONTROL_IMPULSE_POS_Y, 
    		        		Statics.Render.CONTROL_IMPULSE_SKILLS_SIZE, Statics.Render.CONTROL_IMPULSE_SKILLS_SIZE, keyFrame);
    			}
    			if(world.character.fSkills.impulseRightSideDef && world.character.fSkills.isSkillReady(Statics.FightingSkills.IMPULSE_DEFFENSE_RIGHTSIDE))
    			{
    		        game.batch.draw(Statics.Render.CONTROL_IMPULSE_POS_X + radius, Statics.Render.CONTROL_IMPULSE_POS_Y, 
    		        		Statics.Render.CONTROL_IMPULSE_SKILLS_SIZE, Statics.Render.CONTROL_IMPULSE_SKILLS_SIZE, keyFrame);
    			}
    			if(world.character.fSkills.impulseForwardDef && world.character.fSkills.isSkillReady(Statics.FightingSkills.IMPULSE_DEFFENSE_FORWARD))
    			{
    		        game.batch.draw(Statics.Render.CONTROL_IMPULSE_POS_X, Statics.Render.CONTROL_IMPULSE_POS_Y - radius, 
    		        		Statics.Render.CONTROL_IMPULSE_SKILLS_SIZE, Statics.Render.CONTROL_IMPULSE_SKILLS_SIZE, keyFrame);
    			}	     	
            }	        	    		
            game.batch.draw(Statics.Render.CONTROL_IMPULSE_POS_X, Statics.Render.CONTROL_IMPULSE_POS_Y, Statics.Render.CONTROL_IMPULSE_SIZE, Statics.Render.CONTROL_IMPULSE_SIZE, keyFrame);   		
    	}
    	else if(control == Controls.ControlActive.Weapon)
    	{
    	    game.batch.end();
    	    game.batch.begin();
    		keyFrame = AssetsGL.controlWeapon.getKeyFrame(actTime, MyAnimation.ANIMATION_LOOPING);			
            game.batch.draw(10, 1.5f, 1.8f, 1.8f, keyFrame);    		
    	}
    	
    }
    
    
    
    private void drawReadyUI() {
        //game.batch.draw(100, 100, 5, 1, AssetsGL.);    	

    }
    
    private void drawRunningUI() {
    	
    	game.batch.draw(1, 1, 1, 1, AssetsGL.arrow); 
    	game.batch.draw(3, 1, 1, 1, 180, AssetsGL.arrow); 
    	game.batch.draw(10, 1, 1, 1, 90, AssetsGL.arrow); 		    	
    	game.batch.draw(0.55f, 19.45f, 1, 1, AssetsGL.pause); 
    	
    }
    
    private void drawPausedUI() {
    	
    	game.batch.draw(3, 10, 1, 1, AssetsGL.arrow); 
    	game.batch.draw(5, 10, 2, 1, AssetsGL.back); 
    	game.batch.draw(7, 10, 1, 1, AssetsGL.resume); 
    	
    }
    
    private void drawContextUI() {
    	
    	if(gContext.contextObj != null)		    	
    		gContext.ViewScreen();
    	else if(gContext.contextRelief > 0)
    		gContext.ViewScreenRelief();			    	
    }
    
    private void drawGameOverUI() {
        //Graphics g = game.getGraphics();
       // g.drawPixmap(Assets.back, 264, 609, 0, 0, 180, 62);
    }
 
	
	
	
	//////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
        //if(Settings.soundEnabled)
            AssetsGL.music.pause();
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
    	state = GameState.Running;
    	
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
