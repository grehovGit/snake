package com.kingsnake.screens;

import com.example.framework.model.HealthScore;
import com.example.framework.model.WorldKingSnake;
import com.example.framework.model.Snake;
import com.example.framework.model.GameObject;
import com.example.framework.model.DynamicGameObject;
import com.kingsnake.gl.AssetsGL;
import com.kingsnake.gl.MySpriteBatcher;
import com.kingsnake.gl.TextGL;

public class GameContextScreen {
	
	static final float CONTEXT_WIDTH = 8.0f;
	static final float CONTEXT_HEIGHT = 15.0f;
	static final float CONTEXT_HALFWIDTH = CONTEXT_WIDTH / 2;
	static final float CONTEXT_HALFHEIGHT = CONTEXT_HEIGHT / 2;
	static final float CONTEXT_TITLE_HEIGHT = 2.5f;
	static final float CONTEXT_PICTURE_SIZE = 3f;
	static final float CONTEXT_BORDER = 0.3f;
	static final float CONTEXT_PICTURE_X = CONTEXT_PICTURE_SIZE / 2 + CONTEXT_BORDER;
	static final float CONTEXT_PICTURE_Y = CONTEXT_HEIGHT - (CONTEXT_TITLE_HEIGHT + CONTEXT_PICTURE_SIZE / 2) - CONTEXT_BORDER;
	public static final float CONTEXT_OBJSTATE_SIZE = 1.1f;
	static final float CONTEXT_SNAKEPART_WIDTH = 2.0f;
	static final float CONTEXT_SNAKEPART_HEIGHT = 2.2f;
	public static final float CONTEXT_CANCEL_X = CONTEXT_WIDTH - CONTEXT_OBJSTATE_SIZE / 2 - CONTEXT_BORDER/2;
	public static final float CONTEXT_CANCEL_Y = CONTEXT_OBJSTATE_SIZE / 2 + CONTEXT_BORDER/2;
	
	static final float TITLE_TEXT_SCALE = 0.8f;
	static final float TEXT_SCALE = 0.3f;
	static final float SCORE_SCALE = 0.5f;
	
	static final float TEXT1_WIDTH = 5f;
	static final float TEXT1_HEIGHT = 3f;
	static final float TEXT1_X = CONTEXT_PICTURE_SIZE + CONTEXT_BORDER;;
	static final float TEXT1_Y = CONTEXT_HEIGHT - CONTEXT_TITLE_HEIGHT - CONTEXT_BORDER - TEXT1_HEIGHT;
	
	static final float TEXT2_WIDTH = 8f;
	static final float TEXT2_HEIGHT = 2f;
	static final float TEXT2_Y = TEXT1_Y - TEXT2_HEIGHT;
	
	static final float OBJ_STATE_X = 4 * CONTEXT_BORDER;
	static final float OBJ_STATE_Y = TEXT2_Y - CONTEXT_BORDER;

	
	static final String frogTitle = "JabuiN";
	static final String frogText1 = "Jabuin is perfect jumper. He destroys anything on his waY";
	static final String frogText2 = "Be afraid of his landiG";		
	static final String lemmingTitle = "LemmY";
	static final String lemmingText1 = "A harmless creature by one. But their swarm";
	static final String lemmingText2 = "is extremely deadlY";	
	static final String snakeTitle = "SnakE";
	static final String snakeText1 = "Is the king of animals in world SnakelanD";
	static final String snakeText2 = "";		
	static final String hhogTitle = "HedjY";
	static final String hhogText1 = "His barbed armor is impenetrable. Only his";
	static final String hhogText2 = "belly is a weak spot";	
	static final String fishTitle = "FisH";
	static final String fishText1 = "JabuiN";
	static final String fishText2 = "JabuiN";	
	static final String mTreeTitle = "MinertreE";
	static final String mTreeText1 = "Ancient carnivore tree uses fruits to";
	static final String mTreeText2 = "hunt animals. 20 % off health, if step oN";		
	static final String hTreeTitle = "HypnotreE";
	static final String hTreeText1 = "This plant has a hypnotic poweR It brings";
	static final String hTreeText2 = "you into a trance, even at a great distancE";
	
	static final String blockWoodTitle = "WooD BlocK";
	static final String blockWoodText1 = "Light wood block. Convenient for quick";
	static final String blockWoodText2 = "construction of shelterS";	
	static final String blockMarbleTitle = "MarblE BlocK";
	static final String blockMarbleText1 = "Heavy marble block Invaluable";
	static final String blockMarbleText2 = "for construction of safe shelterS";
	
	static final String appleTitle = "ApplE";
	static final String appleText1 = "It brings to you 10 percent of healtH. Do";
	static final String appleText2 = "not forget to treat yourself in timE";		
	static final String blueMushroomTitle = "MagiC  MushrooM";
	static final String blueMushroomText1 = "Eating it, you get a magic gifT Health and";
	static final String blueMushroomText2 = "coins, attack and defense power yourS";	
	static final String yellowMushroomTitle = "GoldeN  MushrooM";
	static final String yellowMushroomText1 = "It is very valuable mushrooM Take a bite";
	static final String yellowMushroomText2 = "and 20 golden coins just yourS";	
	static final String brownMushroomTitle = "DamneD  MushrooM";
	static final String brownMushroomText1 = "Beware of this grebE. If you eat, be ready for";
	static final String brownMushroomText2 = "troubleS. Poisoning - just the beginninG";	
	static final String bredMushroomTitle = "WarcrafT MushrooM";
	static final String bredMushroomText1 = "It makes you strong, improves your war";
	static final String bredMushroomText2 = "skills or endows you SuperweapoN";
	
	static final String blackStoneTitle = "BlackstonE";
	static final String blackStoneText1 = "It takes 30 percent of your healtH";
	static final String blackStoneText2 = "You will wake in a seconD";
	static final String skullStoneTitle = "SkullstonE";
	static final String skullStoneText1 = "It takes 20 percent of your healtH";
	static final String skullStoneText2 = "You will wake in a seconD";	
	static final String greyStoneTitle = "GreystonE";
	static final String greyStoneText1 = "It takes 10 paercent of your healtH";
	static final String greyStoneText2 = "You will wake in a seconD";	
	static final String arrowStoneTitle = "ArrowstonE";
	static final String arrowStoneText1 = "It cuts down you as much as 10 secondS";
	static final String arrowStoneText2 = "Be careful, get round iT";
	
	static final String greenTreeTitle = "GreeN  OaK";
	static final String greenTreeText1 = "It cuts down you as much as 2 secondS";
	static final String greenTreeText2 = "";
	static final String yellowTreeTitle = "YelloW  MaplE";
	static final String yellowTreeText1 = "It cuts down you as much as 3 secondS";
	static final String yellowTreeText2 = "";
	static final String brownTreeTitle = "BrowN  AldeR";
	static final String brownTreeText1 = "It cuts down you as much as 4 secondS";
	static final String brownTreeText2 = "";
	
	static final String waterTitle = "WateR";
	static final String waterText1 = "Water slows your motion By strangling";
	static final String waterText2 = "you loose health with every movemenT";
	static final String sandTitle = "SanD";
	static final String sandText1 = "Sand slows your motioN";
	static final String sandText2 = "";
	static final String swampTitle = "SwamP";
	static final String swampText1 = "Swamp slows your motioN";
	static final String swampText2 = "";
	
	static final float GIFT_SIZE_KOEF = 1.5f;
	static final float WATER_SIZE_KOEF = 0.6f;
	
	MySpriteBatcher batcher;
	TextGL text;
	WorldKingSnake world;
 	public GameObject contextObj;
 	public int contextRelief;
 	float constReliefX, constReliefY;
	public float contX;
	public float contY;
 	String title, text1, text2;
 	
	
	public GameContextScreen(MySpriteBatcher sBatcher)
	{
		this.batcher = sBatcher;
		text = new TextGL(sBatcher);
        contextObj = null;
        contextRelief = 0;
	}
	
	public void set(GameObject obj)
	{
        contextObj = obj;
        contextRelief = 0;
	}
	
	public void set(int relief, float x, float y)
	{
        contextRelief = relief;
        constReliefX = contX = x;
        constReliefY = contY = y;
	}
	
	public void ViewScreen()
	{
		if(contextObj == null)
			return;
		
		contX = contextObj.position.x;
		contY = contextObj.position.y;
		
		if(contX - CONTEXT_HALFWIDTH < 0)
			contX -= contX - CONTEXT_HALFWIDTH;
		else if(contX + CONTEXT_HALFWIDTH > WorldKingSnake.WORLD_WIDTH)
			contX -= contX + CONTEXT_HALFWIDTH - WorldKingSnake.WORLD_WIDTH;
		
		if(contY - CONTEXT_HALFHEIGHT < 0)
			contY -= contY - CONTEXT_HALFHEIGHT;
		else if(contY + CONTEXT_HALFHEIGHT > WorldKingSnake.WORLD_HEIGHT)
			contY -= contY + CONTEXT_HALFHEIGHT - WorldKingSnake.WORLD_HEIGHT;
					
        batcher.begin();	
    	batcher.draw( AssetsGL.contextBackgroundRegion, contX, contY, CONTEXT_WIDTH, CONTEXT_HEIGHT); 		        		        
        batcher.end();	
        
        contX -= CONTEXT_HALFWIDTH;
        contY -= CONTEXT_HALFHEIGHT;  
        
		float x = contX;
		float y = contY;
		
        batcher.begin();	
        
        if(contextObj.type > 0)
        {
        //this is static object:
        	switch(contextObj.type)
        	{
        	case GameObject.APPLE:
            	batcher.draw( AssetsGL.contextApple, x + CONTEXT_PICTURE_X, y + CONTEXT_PICTURE_Y, GIFT_SIZE_KOEF * contextObj.bounds.width, GIFT_SIZE_KOEF * contextObj.bounds.height);
            	title = appleTitle;
            	text1 = appleText1;
            	text2 = appleText2;
            	break;
        	case GameObject.MUSHROOM_BLUE:
            	batcher.draw(AssetsGL.contextMushroomBlue, x + CONTEXT_PICTURE_X, y + CONTEXT_PICTURE_Y, GIFT_SIZE_KOEF * contextObj.bounds.width, GIFT_SIZE_KOEF * contextObj.bounds.height); 	
            	title = blueMushroomTitle;
            	text1 = blueMushroomText1;
            	text2 = blueMushroomText2;
            	break; 
        	case GameObject.MUSHROOM_YELLOW:
            	batcher.draw(AssetsGL.contextMushroomYellow, x + CONTEXT_PICTURE_X, y + CONTEXT_PICTURE_Y, GIFT_SIZE_KOEF * contextObj.bounds.width, GIFT_SIZE_KOEF * contextObj.bounds.height); 	
            	title = yellowMushroomTitle;
            	text1 = yellowMushroomText1;
            	text2 = yellowMushroomText2; 
            	break;
        	case GameObject.MUSHROOM_BLUE_RED:
            	batcher.draw(AssetsGL.contextMushroomBRed, x + CONTEXT_PICTURE_X, y + CONTEXT_PICTURE_Y, GIFT_SIZE_KOEF * contextObj.bounds.width, GIFT_SIZE_KOEF * contextObj.bounds.height); 	
            	title = bredMushroomTitle;
            	text1 = bredMushroomText1;
            	text2 = bredMushroomText2;
            	break;
        	case GameObject.MUSHROOM_BROWN:
            	batcher.draw(AssetsGL.contextMushroomBrown, x + CONTEXT_PICTURE_X, y + CONTEXT_PICTURE_Y, GIFT_SIZE_KOEF * contextObj.bounds.width, GIFT_SIZE_KOEF * contextObj.bounds.height); 	
            	title = brownMushroomTitle;
            	text1 = brownMushroomText1;
            	text2 = brownMushroomText2;
            	break;
        	}

        }
        else if(((DynamicGameObject)contextObj).objType > 0)
        {
        //this is dynamic object:
        	switch(((DynamicGameObject)contextObj).objType)
        	{
        	case DynamicGameObject.SNAKE:
        		float yStart = CONTEXT_PICTURE_Y;
        		for(int i = ((Snake)contextObj).parts.size() - 1; i >= 0; i--)
        		{
        			if(i > 0)
        			{
    					batcher.draw(AssetsGL.contextSnakePart, x + CONTEXT_PICTURE_X + CONTEXT_PICTURE_X / 3, y + yStart - i * 0.55f * CONTEXT_SNAKEPART_HEIGHT - 0.28f, 0.52f * CONTEXT_SNAKEPART_WIDTH, CONTEXT_SNAKEPART_HEIGHT);
       					batcher.draw(AssetsGL.contextApple, x + CONTEXT_PICTURE_X / 4, y + yStart - i * 0.55f * CONTEXT_SNAKEPART_HEIGHT, 0.4f * CONTEXT_OBJSTATE_SIZE, 0.4f * CONTEXT_OBJSTATE_SIZE);
       					batcher.draw(AssetsGL.contextDefense, x + CONTEXT_PICTURE_X / 4, y + yStart - i * 0.55f * CONTEXT_SNAKEPART_HEIGHT - 0.58f, 0.4f * CONTEXT_OBJSTATE_SIZE, 0.4f * CONTEXT_OBJSTATE_SIZE);

        				if(((DynamicGameObject)contextObj).stateHS.level == HealthScore.LEVEL_PINK)
        					batcher.draw(AssetsGL.contextSnakeLevelPink, x + CONTEXT_PICTURE_X + CONTEXT_PICTURE_X / 3, y + yStart - i * 0.55f * CONTEXT_SNAKEPART_HEIGHT - 0.28f, 0.8f * CONTEXT_SNAKEPART_WIDTH, CONTEXT_SNAKEPART_HEIGHT);


        				if(((DynamicGameObject)contextObj).stateHS.health <= HealthScore.WOUND_STRONG_LEVEL * ((DynamicGameObject)contextObj).stateHS.attackPower)
        					batcher.draw(x + CONTEXT_PICTURE_X + CONTEXT_PICTURE_X / 3, y + yStart - i * 0.55f * CONTEXT_SNAKEPART_HEIGHT - 0.28f, 0.52f * CONTEXT_SNAKEPART_WIDTH, CONTEXT_SNAKEPART_HEIGHT, 30, AssetsGL.contextWoundStrong);
        				else if(((DynamicGameObject)contextObj).stateHS.health <= HealthScore.WOUND_EASY_LEVEL * ((DynamicGameObject)contextObj).stateHS.attackPower)
        					batcher.draw(AssetsGL.contextWoundEasy, x + CONTEXT_PICTURE_X + CONTEXT_PICTURE_X / 3, y + yStart - i * 0.55f * CONTEXT_SNAKEPART_HEIGHT - 0.28f, 0.52f * CONTEXT_SNAKEPART_WIDTH, CONTEXT_SNAKEPART_HEIGHT);

        			}
        			else
        			{
           				batcher.draw(AssetsGL.contextSnakeHead, x + CONTEXT_PICTURE_X + CONTEXT_PICTURE_X / 3, y + yStart, CONTEXT_SNAKEPART_WIDTH, CONTEXT_SNAKEPART_HEIGHT); 
 
        				if(((DynamicGameObject)contextObj).stateHS.level == HealthScore.LEVEL_PINK)
               				batcher.draw(AssetsGL.contextSnakeLevelPink, x + CONTEXT_PICTURE_X + CONTEXT_PICTURE_X / 3, y + yStart, CONTEXT_SNAKEPART_WIDTH, CONTEXT_SNAKEPART_HEIGHT); 
        				
        				if(((DynamicGameObject)contextObj).stateHS.health <= HealthScore.WOUND_STRONG_LEVEL * ((DynamicGameObject)contextObj).stateHS.attackPower)
        					batcher.draw(x + CONTEXT_PICTURE_X + CONTEXT_PICTURE_X / 3 - 0.05f, y + yStart - i * 0.55f * CONTEXT_SNAKEPART_HEIGHT - 0.28f, 1.2f * 0.52f * CONTEXT_SNAKEPART_WIDTH, 1.1f * CONTEXT_SNAKEPART_HEIGHT, 30, AssetsGL.contextWoundStrong);
        				else if(((DynamicGameObject)contextObj).stateHS.health <=  HealthScore.WOUND_EASY_LEVEL * ((DynamicGameObject)contextObj).stateHS.attackPower)
        					batcher.draw(AssetsGL.contextWoundEasy, x + CONTEXT_PICTURE_X + CONTEXT_PICTURE_X / 3, y + yStart - i * 0.55f * CONTEXT_SNAKEPART_HEIGHT - 0.28f, 0.52f * CONTEXT_SNAKEPART_WIDTH, CONTEXT_SNAKEPART_HEIGHT);

        			}
        		}
            	title = snakeTitle;
            	text1 = snakeText1;
            	text2 = snakeText2; 
            	break;
        	case DynamicGameObject.LEMMING:
            	batcher.draw(AssetsGL.contextLemming, x + CONTEXT_PICTURE_X, y + CONTEXT_PICTURE_Y, 3 * CONTEXT_PICTURE_SIZE / 4, CONTEXT_PICTURE_SIZE); 	
            	title = lemmingTitle;
            	text1 = lemmingText1;
            	text2 = lemmingText2; 
            	break;
        	case DynamicGameObject.HEDGEHOG:
            	batcher.draw(AssetsGL.contextHHog, x + CONTEXT_PICTURE_X, y + CONTEXT_PICTURE_Y, CONTEXT_PICTURE_SIZE, 3 * CONTEXT_PICTURE_SIZE / 4); 	
            	title = hhogTitle;
            	text1 = hhogText1;
            	text2 = hhogText2; 
            	break;
        	case DynamicGameObject.FROG:
            	batcher.draw(AssetsGL.contextFrog, x + CONTEXT_PICTURE_X, y + CONTEXT_PICTURE_Y, CONTEXT_PICTURE_SIZE, CONTEXT_PICTURE_SIZE); 	
            	title = frogTitle;
            	text1 = frogText1;
            	text2 = frogText2; 
            	break;
        	case DynamicGameObject.FISH:
            	batcher.draw(AssetsGL.contextFrog, x + CONTEXT_PICTURE_X, y + CONTEXT_PICTURE_Y, CONTEXT_PICTURE_SIZE, CONTEXT_PICTURE_SIZE); 	
            	title = fishTitle;
            	text1 = fishText1;
            	text2 = fishText2; 
            	break;
        	case DynamicGameObject.BLOCK_WOOD:
            	batcher.draw(AssetsGL.contextWoodBlock, x + CONTEXT_PICTURE_X, y + CONTEXT_PICTURE_Y, CONTEXT_PICTURE_SIZE, CONTEXT_PICTURE_SIZE); 	
            	title = blockWoodTitle;
            	text1 = blockWoodText1;
            	text2 = blockWoodText2; 
            	break;
        	case DynamicGameObject.BLOCK_MARBLE:
            	batcher.draw(AssetsGL.contextMarbleBlock, x + CONTEXT_PICTURE_X, y + CONTEXT_PICTURE_Y, CONTEXT_PICTURE_SIZE, CONTEXT_PICTURE_SIZE); 	
            	title = blockMarbleTitle;
            	text1 = blockMarbleText1;
            	text2 = blockMarbleText2; 
            	break;
        	case DynamicGameObject.TREE_HYPNOTIC:
            	batcher.draw(AssetsGL.contextHypTree, x + CONTEXT_PICTURE_X, y + CONTEXT_PICTURE_Y, 2 * CONTEXT_PICTURE_SIZE / 3, CONTEXT_PICTURE_SIZE); 	
            	title = hTreeTitle;
            	text1 = hTreeText1;
            	text2 = hTreeText2; 
            	break;
        	case DynamicGameObject.TREE_BOMB:
            	batcher.draw(AssetsGL.contextMinerTree, x + CONTEXT_PICTURE_X, y + CONTEXT_PICTURE_Y, CONTEXT_PICTURE_SIZE / 3, CONTEXT_PICTURE_SIZE); 	
            	title = mTreeTitle;
            	text1 = mTreeText1;
            	text2 = mTreeText2; 
            	break;
        	}
        	
            //show state of dynamic object:
        	if(((DynamicGameObject)contextObj).objType != DynamicGameObject.SNAKE)
        	{
	            batcher.draw(AssetsGL.contextApple, x + OBJ_STATE_X, y + OBJ_STATE_Y, CONTEXT_OBJSTATE_SIZE, CONTEXT_OBJSTATE_SIZE); 	
	            batcher.draw(AssetsGL.contextCoins, x + OBJ_STATE_X, y + OBJ_STATE_Y - 1.3f * CONTEXT_OBJSTATE_SIZE, CONTEXT_OBJSTATE_SIZE, CONTEXT_OBJSTATE_SIZE);
	            batcher.draw(AssetsGL.contextAttack,x + OBJ_STATE_X, y + OBJ_STATE_Y - 2.6f * CONTEXT_OBJSTATE_SIZE, CONTEXT_OBJSTATE_SIZE, CONTEXT_OBJSTATE_SIZE);
	            batcher.draw(AssetsGL.contextDefense, x + OBJ_STATE_X, y + OBJ_STATE_Y - 3.9f * CONTEXT_OBJSTATE_SIZE, CONTEXT_OBJSTATE_SIZE, CONTEXT_OBJSTATE_SIZE);
	            batcher.draw(AssetsGL.contextSpeed, x + OBJ_STATE_X, y + OBJ_STATE_Y - 5.1f * CONTEXT_OBJSTATE_SIZE, CONTEXT_OBJSTATE_SIZE, CONTEXT_OBJSTATE_SIZE);
        	}
        	else
        	{
	            batcher.draw(AssetsGL.contextApple, x + 3.5f * OBJ_STATE_X, y + OBJ_STATE_Y, CONTEXT_OBJSTATE_SIZE, CONTEXT_OBJSTATE_SIZE); 	
	            batcher.draw(AssetsGL.contextCoins, x + 3.5f * OBJ_STATE_X, y + OBJ_STATE_Y - 1.3f * CONTEXT_OBJSTATE_SIZE, CONTEXT_OBJSTATE_SIZE, CONTEXT_OBJSTATE_SIZE);
	            batcher.draw(AssetsGL.contextAttack,x + 3.5f * OBJ_STATE_X, y + OBJ_STATE_Y - 2.6f * CONTEXT_OBJSTATE_SIZE, CONTEXT_OBJSTATE_SIZE, CONTEXT_OBJSTATE_SIZE);
	            batcher.draw(AssetsGL.contextDefense, x + 3.5f * OBJ_STATE_X, y + OBJ_STATE_Y - 3.9f * CONTEXT_OBJSTATE_SIZE, CONTEXT_OBJSTATE_SIZE, CONTEXT_OBJSTATE_SIZE);
	            batcher.draw(AssetsGL.contextSpeed, x + 3.5f * OBJ_STATE_X, y + OBJ_STATE_Y - 5.1f * CONTEXT_OBJSTATE_SIZE, CONTEXT_OBJSTATE_SIZE, CONTEXT_OBJSTATE_SIZE);       		
        	}            
        }
        batcher.draw(AssetsGL.contextArrow, x + CONTEXT_CANCEL_X, y + CONTEXT_CANCEL_Y, CONTEXT_OBJSTATE_SIZE, CONTEXT_OBJSTATE_SIZE);       		        
        batcher.end();	
        
        text.beginBatch();
        text.ViewTextMiddleAlignedAutoscaled(AssetsGL.text, title, x + CONTEXT_HALFWIDTH, y + CONTEXT_HEIGHT - CONTEXT_TITLE_HEIGHT / 2, TITLE_TEXT_SCALE, CONTEXT_WIDTH - 2 * CONTEXT_BORDER);        
        text.ViewTextinRectangleRightAlign(AssetsGL.text, text1, x + TEXT1_X, y + TEXT1_Y, TEXT1_WIDTH, TEXT1_HEIGHT, TEXT_SCALE, true);
        text.ViewTextinRectangleRightAlign(AssetsGL.text, text2, x, y + TEXT2_Y, TEXT2_WIDTH, TEXT2_HEIGHT, TEXT_SCALE, false);              	
        
        if(!(contextObj.type > 0) && ((DynamicGameObject)contextObj).objType > 0)
        {   
        	if(((DynamicGameObject)contextObj).objType != DynamicGameObject.SNAKE)
        	{
	            text.ViewText(AssetsGL.text, String.valueOf(((DynamicGameObject)contextObj).stateHS.health), x +  2 * OBJ_STATE_X, y + OBJ_STATE_Y, SCORE_SCALE);        
	            text.ViewText(AssetsGL.text, String.valueOf(((DynamicGameObject)contextObj).stateHS.coins), x +  2 * OBJ_STATE_X, y + OBJ_STATE_Y - 1.3f * CONTEXT_OBJSTATE_SIZE, SCORE_SCALE);   
	            text.ViewText(AssetsGL.text, String.valueOf(((DynamicGameObject)contextObj).stateHS.attackPower), x +  2 * OBJ_STATE_X, y + OBJ_STATE_Y - 2.6f * CONTEXT_OBJSTATE_SIZE, SCORE_SCALE);  
	            text.ViewText(AssetsGL.text, String.valueOf(((DynamicGameObject)contextObj).stateHS.defencePower), x +  2 * OBJ_STATE_X, y + OBJ_STATE_Y- 3.9f * CONTEXT_OBJSTATE_SIZE, SCORE_SCALE);  
	            text.ViewText(AssetsGL.text, String.valueOf(((DynamicGameObject)contextObj).stateHS.velocity), x +  2 * OBJ_STATE_X, y + OBJ_STATE_Y - 5.1f * CONTEXT_OBJSTATE_SIZE, SCORE_SCALE);  
        	}
        	else
        	{
        		float yStart = CONTEXT_PICTURE_Y;
        		for(int i = ((Snake)contextObj).parts.size() - 1; i > 0; i--)
        		{
       				text.ViewText(AssetsGL.text, String.valueOf(((DynamicGameObject)contextObj).stateHS.health), x + CONTEXT_PICTURE_X / 2, y + yStart - i * 0.55f * CONTEXT_SNAKEPART_HEIGHT, 0.4f * SCORE_SCALE);
       				text.ViewText(AssetsGL.text, String.valueOf(((DynamicGameObject)contextObj).stateHS.defencePower), x + CONTEXT_PICTURE_X / 2, y + yStart - i * 0.55f * CONTEXT_SNAKEPART_HEIGHT - 0.58f, 0.4f * SCORE_SCALE);
        		}

	            text.ViewText(AssetsGL.text, String.valueOf(((DynamicGameObject)contextObj).stateHS.health), x +  4.5f * OBJ_STATE_X, y + OBJ_STATE_Y, SCORE_SCALE);        
	            text.ViewText(AssetsGL.text, String.valueOf(((DynamicGameObject)contextObj).stateHS.coins), x +  4.5f * OBJ_STATE_X, y + OBJ_STATE_Y - 1.3f * CONTEXT_OBJSTATE_SIZE, SCORE_SCALE);   
	            text.ViewText(AssetsGL.text, String.valueOf(((DynamicGameObject)contextObj).stateHS.attackPower), x +  4.5f * OBJ_STATE_X, y + OBJ_STATE_Y - 2.6f * CONTEXT_OBJSTATE_SIZE, SCORE_SCALE);  
	            text.ViewText(AssetsGL.text, String.valueOf(((DynamicGameObject)contextObj).stateHS.defencePower), x +  4.5f * OBJ_STATE_X, y + OBJ_STATE_Y- 3.9f * CONTEXT_OBJSTATE_SIZE, SCORE_SCALE);  
	            text.ViewText(AssetsGL.text, String.valueOf(((DynamicGameObject)contextObj).stateHS.velocity), x +  4.5f * OBJ_STATE_X, y + OBJ_STATE_Y - 5.1f * CONTEXT_OBJSTATE_SIZE, SCORE_SCALE);  
        		
        	}
        }
        
        text.endBatch();
	}
	
	public void ViewScreenRelief()
	{			
		if(constReliefX - CONTEXT_HALFWIDTH < 0)
			constReliefX -= constReliefX - CONTEXT_HALFWIDTH;
		else if(constReliefX + CONTEXT_HALFWIDTH > WorldKingSnake.WORLD_WIDTH)
			constReliefX -= constReliefX + CONTEXT_HALFWIDTH - WorldKingSnake.WORLD_WIDTH;
		
		if(constReliefY - CONTEXT_HALFHEIGHT < 0)
			constReliefY -= contY - CONTEXT_HALFHEIGHT;
		else if(constReliefY + CONTEXT_HALFHEIGHT > WorldKingSnake.WORLD_HEIGHT)
			constReliefY -= constReliefY + CONTEXT_HALFHEIGHT - WorldKingSnake.WORLD_HEIGHT;		

        batcher.begin();	
    	batcher.draw(AssetsGL.contextBackgroundRegion, constReliefX, constReliefY, CONTEXT_WIDTH, CONTEXT_HEIGHT); 		        		        
        batcher.end();	
        
        contX = constReliefX - CONTEXT_HALFWIDTH;
        contY = constReliefY - CONTEXT_HALFHEIGHT;       
		float reliefX = contX;
		float reliefY = contY;
        
        batcher.begin();	
        
        switch(contextRelief)
        {
        case WorldKingSnake.GREEN_TREE:
        	batcher.draw(AssetsGL.contextGreenTree, reliefX + CONTEXT_PICTURE_X, reliefY + CONTEXT_PICTURE_Y, CONTEXT_PICTURE_SIZE, CONTEXT_PICTURE_SIZE); 	
        	title = greenTreeTitle;
        	text1 = greenTreeText1;
        	text2 = greenTreeText2; 
        	break;
        case WorldKingSnake.YELLOW_TREE:
        	batcher.draw(AssetsGL.contextYellowTree, reliefX + CONTEXT_PICTURE_X, reliefY + CONTEXT_PICTURE_Y, CONTEXT_PICTURE_SIZE, CONTEXT_PICTURE_SIZE); 	
        	title = yellowTreeTitle;
        	text1 = yellowTreeText1;
        	text2 = yellowTreeText2; 
        	break;
        case WorldKingSnake.BROWN_TREE:
        	batcher.draw(AssetsGL.contextBrownTree, reliefX + CONTEXT_PICTURE_X, reliefY + CONTEXT_PICTURE_Y, 3 * CONTEXT_PICTURE_SIZE / 4, CONTEXT_PICTURE_SIZE); 	
        	title = brownTreeTitle;
        	text1 = brownTreeText1;
        	text2 = brownTreeText2; 
        	break;
        case WorldKingSnake.DEAD_STONE1:
        	batcher.draw(AssetsGL.contextGreyStone, reliefX + CONTEXT_PICTURE_X, reliefY + CONTEXT_PICTURE_Y, CONTEXT_PICTURE_SIZE / 2, CONTEXT_PICTURE_SIZE / 2); 	
        	title = greyStoneTitle;
        	text1 = greyStoneText1;
        	text2 = greyStoneText2; 
        	break;
        case WorldKingSnake.DEAD_STONE2:
        	batcher.draw(AssetsGL.contextSkullStone, reliefX + CONTEXT_PICTURE_X, reliefY + CONTEXT_PICTURE_Y, CONTEXT_PICTURE_SIZE / 2, CONTEXT_PICTURE_SIZE / 2); 	
        	title = skullStoneTitle;
        	text1 = skullStoneText1;
        	text2 = skullStoneText2; 
        	break;
        case WorldKingSnake.DEAD_STONE3:
        	batcher.draw(AssetsGL.contextBlackStone, reliefX + CONTEXT_PICTURE_X, reliefY + CONTEXT_PICTURE_Y, CONTEXT_PICTURE_SIZE / 2, CONTEXT_PICTURE_SIZE / 2); 	
        	title = blackStoneTitle;
        	text1 = blackStoneText1;
        	text2 = blackStoneText2; 
        	break;
        case WorldKingSnake.ARROW_STONE:
        	batcher.draw(AssetsGL.contextArrowStone, reliefX + CONTEXT_PICTURE_X, reliefY + CONTEXT_PICTURE_Y, CONTEXT_PICTURE_SIZE / 2, CONTEXT_PICTURE_SIZE / 2); 	
        	title = arrowStoneTitle;
        	text1 = arrowStoneText1;
        	text2 = arrowStoneText2; 
        	break;
        case WorldKingSnake.WATER:
        	batcher.draw(AssetsGL.contextWater, reliefX + CONTEXT_PICTURE_X, reliefY + CONTEXT_PICTURE_Y, CONTEXT_PICTURE_SIZE, WATER_SIZE_KOEF * CONTEXT_PICTURE_SIZE); 	
        	title = waterTitle;
        	text1 = waterText1;
        	text2 = waterText2; 
        	break;
        case WorldKingSnake.SAND:
        	batcher.draw(AssetsGL.contextSand, reliefX + CONTEXT_PICTURE_X, reliefY + CONTEXT_PICTURE_Y, CONTEXT_PICTURE_SIZE, CONTEXT_PICTURE_SIZE / 2); 	
        	title = sandTitle;
        	text1 = sandText1;
        	text2 = sandText2; 
        	break;
        case WorldKingSnake.SWAMP:
        	batcher.draw(AssetsGL.contextSwamp, reliefX + CONTEXT_PICTURE_X, reliefY + CONTEXT_PICTURE_Y, CONTEXT_PICTURE_SIZE, CONTEXT_PICTURE_SIZE / 2); 	
        	title = swampTitle;
        	text1 = swampText1;
        	text2 = swampText2; 
        	break;
        case WorldKingSnake.WALL:
        	batcher.draw(AssetsGL.contextGreenTree, reliefX + CONTEXT_PICTURE_X, reliefY + CONTEXT_PICTURE_Y, CONTEXT_PICTURE_SIZE, CONTEXT_PICTURE_SIZE); 	
        	title = hTreeTitle;
        	text1 = hTreeText1;
        	text2 = hTreeText2; 
        	break;

        }
        batcher.draw(AssetsGL.contextArrow, reliefX + CONTEXT_CANCEL_X, reliefY + CONTEXT_CANCEL_Y, CONTEXT_OBJSTATE_SIZE, CONTEXT_OBJSTATE_SIZE);       		                        
        batcher.end();	
        
        text.beginBatch();
        text.ViewTextMiddleAlignedAutoscaled(AssetsGL.text, title, reliefX + CONTEXT_HALFWIDTH, reliefY + CONTEXT_HEIGHT - CONTEXT_TITLE_HEIGHT / 2, TITLE_TEXT_SCALE, CONTEXT_WIDTH - 2 * CONTEXT_BORDER);        
        text.ViewTextinRectangleRightAlign(AssetsGL.text, text1, reliefX + TEXT1_X, reliefY + TEXT1_Y, TEXT1_WIDTH, TEXT1_HEIGHT, TEXT_SCALE, true);
        text.ViewTextinRectangleRightAlign(AssetsGL.text, text2, reliefX, reliefY + TEXT2_Y, TEXT2_WIDTH, TEXT2_HEIGHT, TEXT_SCALE, false);              	
        text.endBatch();
        
	}
	
	void onClose()
	{
		contextObj = null;
		contextRelief = 0;
	}
	
	

}
