package com.kingsnake.gl;

import java.nio.file.Files;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter.ScaledNumericValue;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.particles.emitters.Emitter;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.utils.Array;
import com.example.framework.model.DynamicEffect;
import com.example.framework.model.Gifts;
import com.example.framework.model.Lemming;
import com.example.framework.model.Hedgehog;
import com.example.framework.model.Frog;
import com.example.framework.model.StaticEffect;
import com.example.framework.model.Statics;
import com.example.framework.model.WorldKingSnake;
import com.example.framework.model.GameObject;

public class AssetsGL {
	
	public static TextureAtlas gameAtlas;
	public static Texture background;
    public static TextureRegion backgroundRegion;
    
	public static Texture contextBackground;
    public static TextureRegion contextBackgroundRegion;	    

 	public static Texture items;
 	public static Texture textAsset;	 	
 	public static Texture controlsAsset;
 	
    public static TextureRegion mainMenu;
    public static TextureRegion hichScore;
    public static TextureRegion map;
    public static TextureRegion map_levelgold;
    
    public static TextureRegion apple;
    public static TextureRegion mushroomBlue;
    public static TextureRegion mushroomYellow;
    public static TextureRegion mushroomBRed;
    public static TextureRegion mushroomBrown;
    public static TextureRegion coin;
    
    public static TextureRegion bloodStain1;
    public static TextureRegion bloodStain2;
    public static TextureRegion bloodStain3;
    
    public static TextureRegion seedMinerRegion;
    public static TextureRegion seedMinerDamagedRegion;
    public static TextureRegion seedHurtRegion;
    public static TextureRegion seedHurtDamagedRegion;
    public static TextureRegion seedMadRegion;
    public static TextureRegion seedMadDamagedRegion;
    public static TextureRegion seedBeatRegion;
    public static TextureRegion seedBeatDamagedRegion;
    public static TextureRegion seedSmileRegion;
    public static TextureRegion seedSmileDamagedRegion;
    
    public static TextureRegion bombTree;
    public static TextureRegion bombFromTree;	     
    
    public static TextureRegion snakeHead;	//snake patterns
    public static TextureRegion snakeTail;
    public static TextureRegion snakeOpenMouth;
    public static TextureRegion snakeGoldForwardAttack;
    public static MyAnimation snakeGoldForwardAttackCharging;
    public static TextureRegion snakeGoldForwardHookLeft;
    public static MyAnimation snakeGoldForwardHookLeftCharging;
    public static TextureRegion snakeGoldForwardHookRight;
    public static MyAnimation snakeGoldForwardHookRightCharging;
    public static TextureRegion snakeGoldForwardLeftTurn;
    public static MyAnimation snakeGoldForwardLeftTurnCharging;
    public static TextureRegion snakeGoldForwardRightTurn;
    public static MyAnimation snakeGoldForwardRightTurnCharging;
    public static TextureRegion snakeGoldBackAttack;
    public static MyAnimation snakeGoldBackAttackCharging;
    public static TextureRegion snakeGoldBackHookLeft;
    public static MyAnimation snakeGoldBackHookLeftCharging;
    public static TextureRegion snakeGoldBackHookRight;
    public static MyAnimation snakeGoldBackHookRightCharging;
    public static TextureRegion snakeImpulseForwardDefense;
    public static MyAnimation snakeImpulseForwardDefenseCharging;
    public static TextureRegion snakeImpulseForwardAttack;
    public static MyAnimation snakeImpulseForwardAttackCharging;
    public static TextureRegion impulseForwardAttack;
    public static TextureRegion snakeImpulseLeftSideDef;
    public static MyAnimation snakeImpulseLeftSideDefCharging;
    public static TextureRegion snakeImpulseRightSideDef;
    public static MyAnimation snakeImpulseRightSideDefCharging;
    public static MyAnimation snakeGoldAttack;
    public static MyAnimation snakeGoldAttackBack;
    public static MyAnimation snakeHeadGoldAttack;
    public static MyAnimation snakeHeadGoldAttackBack;
    public static MyAnimation snakeHeadBlueAttack;
    public static MyAnimation snakePartBlueAttack;
    public static TextureRegion snakeDamagedEasy;
    public static TextureRegion snakeDamagedHard;
    
    public static TextureRegion snakePartArmorWoodLeft;
    public static TextureRegion snakePartArmorWoodDamagedLeft;
    public static TextureRegion snakePartArmorWoodRight;
    public static TextureRegion snakePartArmorWoodDamagedRight;
    
    public static TextureRegion blockWood1;
    public static TextureRegion blockWood2;
    public static TextureRegion blockWood3;
    public static TextureRegion blockWood4;	 
    public static TextureRegion blockWoodPart;	
    public static TextureRegion blockMarble1;
    public static TextureRegion blockMarble2;
    public static TextureRegion blockMarble3;
    public static TextureRegion blockMarble4;
    
    public static TextureRegion fSkillIce;
    public static TextureRegion fSkillIceBroken;
    public static TextureRegion fSkillIcePart1;
    public static TextureRegion fSkillIcePart2;
    public static TextureRegion fSkillIcePart3;
    
    public static TextureRegion arrow;	//
    public static TextureRegion back;	//180x124
    
    public static TextureRegion pause;	
    public static TextureRegion resume;	
    public static TextureRegion game_pause;	
    
    public static TextureRegion joystick;
    public static TextureRegion joystickCenter;
    public static MyAnimation joystickArrowHorizont;
    public static MyAnimation joystickArrowVert;
    
    public static MyAnimation controlJump;
    public static MyAnimation controlImpulse;
    public static MyAnimation controlWeapon;
    
    public static TextureRegion buttons;
    public static TextureRegion[] numbers;
    public static TextureRegion[] text;	    
   	    
    public static TextureRegion red;
    public static TextureRegion grey;
    public static TextureRegion yellow;
    public static TextureRegion green;
    
    
    public static MyAnimation giftApper;
    public static MyAnimation impact;
    public static MyAnimation water;
    public static MyAnimation hitStars;
    public static MyAnimation bloodSmall;
    public static MyAnimation bloodMediumLarge;
    public static MyAnimation treeBombExplosion;
    
    public static MyAnimation attackUpEffect;
    public static MyAnimation defenseUpEffect;
    public static MyAnimation healthUpEffect;
    public static MyAnimation speedUpEffect;
    public static MyAnimation getCoinsEffect;
    
    public static MyAnimation attackDownEffect;
    public static MyAnimation defenseDownEffect;
    public static MyAnimation healthDownEffect;
    public static MyAnimation speedDownEffect;
    public static MyAnimation looseCoinsEffect;
    
    public static MyAnimation charLemmingStay;
    public static MyAnimation charLemmingMove;	    
    public static MyAnimation charHedgehogStay;
    public static MyAnimation charHedgehogMove;	    
    public static MyAnimation charFrogStay;
    public static MyAnimation charFrogGrounding;
    public static TextureRegion charFrogMove;
    
    public static TextureRegion weaponHelmWoodenSimpleBack;
    public static TextureRegion weaponHelmWoodenSimplePart1;
    public static TextureRegion weaponHelmWoodenSimplePart2;
    
    public static TextureRegion weaponShieldVikkingWoodenSimplePart1;
    public static TextureRegion weaponShieldVikkingWoodenSimplePart2;
    public static TextureRegion weaponShieldVikkingWoodenSimplePart3;

    public static TextureRegion weaponSwordVikkingBronzeWooden;
    public static TextureRegion weaponSwordVikkingBronzeWoodenBroken;
    public static TextureRegion weaponSwordVikkingBronzeWoodenPart1;
    public static TextureRegion weaponSwordVikkingBronzeWoodenPart2;
    
    public static TextureRegion weaponAxeMonosideVikkingBronzeWooden;
    public static TextureRegion weaponAxeMonosideVikkingBronzeWoodenBroken;
    public static TextureRegion weaponAxeMonosideVikkingBronzeWoodenPart1;
    public static TextureRegion weaponAxeMonosideVikkingBronzeWoodenPart2;
    
    public static TextureRegion weaponAxeDoublesideVikkingBronzeWooden;
    public static TextureRegion weaponAxeDoublesideVikkingBronzeWoodenBroken;
    public static TextureRegion weaponAxeDoublesideVikkingBronzeWoodenPart1;
    public static TextureRegion weaponAxeDoublesideVikkingBronzeWoodenPart2;
    
    public static TextureRegion weaponSpearVikkingBronzeWooden;
    public static TextureRegion weaponSpearVikkingBronzeWoodenBroken;
    public static TextureRegion weaponSpearVikkingBronzeWoodenPart1;
    public static TextureRegion weaponSpearVikkingBronzeWoodenPart2;
    
	public static Texture contextAssets;
    public static TextureRegion contextSnakeHead;
    public static TextureRegion contextSnakePart;
    public static TextureRegion contextSnakeLevelPink;
    public static TextureRegion contextWoundEasy;
    public static TextureRegion contextWoundStrong;
    public static TextureRegion contextFrog;
    public static TextureRegion contextLemming;
    public static TextureRegion contextHHog;
    public static TextureRegion contextMinerTree;
    public static TextureRegion contextHypTree;
    public static TextureRegion contextGreenTree;
    public static TextureRegion contextYellowTree;
    public static TextureRegion contextBrownTree;
    public static TextureRegion contextBlackStone;
    public static TextureRegion contextGreyStone;
    public static TextureRegion contextSkullStone;
    public static TextureRegion contextArrowStone;
    public static TextureRegion contextWoodBlock;
    public static TextureRegion contextMarbleBlock;	    
    public static TextureRegion contextMushroomYellow;
    public static TextureRegion contextMushroomBlue;
    public static TextureRegion contextMushroomBrown;
    public static TextureRegion contextMushroomBRed;
    public static TextureRegion contextApple;
    public static TextureRegion contextAttack;
    public static TextureRegion contextDefense;
    public static TextureRegion contextSpeed;
    public static TextureRegion contextCoins;	    
    public static TextureRegion contextWater;
    public static TextureRegion contextSand;
    public static TextureRegion contextSwamp;
    public static TextureRegion contextArrow;
    
    //particke effects	    
    public static Array<PooledEffect> particlesEffectsAdditiveArray;
    public static Array<PooledEffect> particlesEffectsNormalArray;	
    
    public static ParticleEffectPool particlesEffectAdditiveSparksPool;
    public static ParticleEffectPool particlesEffectAdditiveHypnosePool;
    public static ParticleEffectPool particlesEffectAdditiveFirePool;
    public static ParticleEffectPool particlesEffectAdditiveSteamPool;
    public static ParticleEffectPool particlesEffectNormalWoodenFlindersPool;
    public static ParticleEffectPool particlesEffectNormalRockShardsPool;
    public static ParticleEffectPool particlesEffectNormalIceShardsPool;
    public static ParticleEffectPool particlesEffectNormalWaterBulbsPool;
    
    public static TextureRegion particlesEffectAdditiveSparksRegion;
    public static ParticleEffect particlesEffectAdditiveSpark;	
    public static TextureRegion particlesEffectAdditiveHypnoseStar1Region;
    public static TextureRegion particlesEffectAdditiveHypnoseStar2Region;
    public static TextureRegion particlesEffectAdditiveHypnoseBulbRegion; 
    public static ParticleEffect particlesEffectAdditiveHypnose;
    
    public static TextureRegion particlesEffectAdditiveFireRegion;
    public static TextureRegion particlesEffectAdditiveSteamRegion; 
    public static ParticleEffect particlesEffectAdditiveFire;
    public static ParticleEffect particlesEffectAdditiveSteam;
    
    public static TextureRegion particlesEffectNormalWoodenFlindersRegion;
    public static ParticleEffect particlesEffectNormalWoodenFlinders; 
    public static TextureRegion particlesEffectNormalRockShardsRegion;
    public static ParticleEffect particlesEffectNormalRockShards; 
    public static TextureRegion particlesEffectNormalIceShardsRegion;
    public static ParticleEffect particlesEffectNormalIceShards;
    public static TextureRegion particlesEffectNormalWaterBulbsRegion;
    public static ParticleEffect particlesEffectNormalWaterBulbs; 	    	    
    
    
    public static Sound click;
    public static Sound eat;
    public static Sound bitten;
    
    public static Music music;
    
    
    public static void load() {
    	FileHandle a = Gdx.files.internal("packerAssets/snakeGamePack.pack");
    	boolean b = a.exists();
	    gameAtlas = new TextureAtlas(a);
	    

    	
        background = new Texture("GamePlay.jpg");
        backgroundRegion = new TextureRegion(background, 0, 0, 800, 1280);	
        contextBackground = new Texture("ContextInfoBlank.png");
        contextBackgroundRegion = new TextureRegion(contextBackground, 0, 0, 512, 960);	 
        contextAssets = new Texture("ContextInfoAssets.png"); 
        
	    //items = new Texture("ItemsAtlas512.png");
        items = gameAtlas.getTextures().first();
	    textAsset = new Texture("RingFont.png");
	    controlsAsset = new Texture("AtlasControls.png");
	    
	    arrow = new TextureRegion(items, 384, 448, 64, 64);
	    pause = new TextureRegion(items, 320, 320, 64, 64);
	    resume = new TextureRegion(items, 448, 448, 64, 64);
	    back = new TextureRegion(items, 384, 384, 128, 64);
	    
	    joystick = new TextureRegion(items, 1152, 768, 128, 128);
	    joystickCenter = new TextureRegion(items, 1152, 1024, 128, 128);
	    
	    joystickArrowHorizont = new MyAnimation(0.1f,
                new TextureRegion(items, 1024, 640, 64, 64),
                new TextureRegion(items, 1088, 640, 64, 64),
                new TextureRegion(items, 1152, 640, 64, 64),
                new TextureRegion(items, 1216, 640, 64, 64),                   
                new TextureRegion(items, 1024, 704, 64, 64),
                new TextureRegion(items, 1088, 704, 64, 64),
                new TextureRegion(items, 1152, 704, 64, 64),
                new TextureRegion(items, 1216, 704, 64, 64),                  
                new TextureRegion(items, 1024, 768, 64, 64),
                new TextureRegion(items, 1088, 768, 64, 64),
                new TextureRegion(items, 1024, 768, 64, 64),                   
                new TextureRegion(items, 1216, 704, 64, 64),                    
                new TextureRegion(items, 1152, 704, 64, 64),
                new TextureRegion(items, 1088, 704, 64, 64),
                new TextureRegion(items, 1024, 704, 64, 64),                   
                new TextureRegion(items, 1216, 640, 64, 64),
                new TextureRegion(items, 1152, 640, 64, 64),
                new TextureRegion(items, 1088, 640, 64, 64),
                new TextureRegion(items, 1024, 640, 64, 64)                    
                );
	    
	    joystickArrowVert = new MyAnimation(0.1f,
                new TextureRegion(items, 1024, 832, 64, 64),
                new TextureRegion(items, 1088, 832, 64, 64),                    
                new TextureRegion(items, 1024, 896, 64, 64),
                new TextureRegion(items, 1088, 896, 64, 64),
                new TextureRegion(items, 1152, 896, 64, 64),
                new TextureRegion(items, 1216, 896, 64, 64),                    
                new TextureRegion(items, 1024, 960, 64, 64),
                new TextureRegion(items, 1088, 960, 64, 64),
                new TextureRegion(items, 1152, 960, 64, 64),
                new TextureRegion(items, 1216, 960, 64, 64),                    
                new TextureRegion(items, 1152, 960, 64, 64),
                new TextureRegion(items, 1088, 960, 64, 64),                    
                new TextureRegion(items, 1024, 960, 64, 64),                   
                new TextureRegion(items, 1216, 896, 64, 64),
                new TextureRegion(items, 1152, 896, 64, 64),                    
                new TextureRegion(items, 1088, 896, 64, 64),                    
                new TextureRegion(items, 1024, 896, 64, 64),                    
                new TextureRegion(items, 1088, 832, 64, 64),
                new TextureRegion(items, 1024, 832, 64, 64)                    
                );
	    	    
	    
	    controlJump = new MyAnimation(0.1f,
                new TextureRegion(items, 0, 1024, 128, 128), 
                new TextureRegion(items, 128, 1024, 128, 128),  
                new TextureRegion(items, 256, 1024, 128, 128),  
                new TextureRegion(items, 384, 1024, 128, 128), 
                new TextureRegion(items, 512, 1024, 128, 128),                     
                new TextureRegion(items, 0, 1152, 128, 128), 
                new TextureRegion(items, 128, 1152, 128, 128),  
                new TextureRegion(items, 256, 1152, 128, 128),  
                new TextureRegion(items, 384, 1152, 128, 128), 
                new TextureRegion(items, 512, 1152, 128, 128), 
                new TextureRegion(items, 384, 1152, 128, 128), 
                new TextureRegion(items, 256, 1152, 128, 128), 
                new TextureRegion(items, 128, 1152, 128, 128), 
                new TextureRegion(items, 0, 1152, 128, 128),  
                new TextureRegion(items, 512, 1024, 128, 128), 
                new TextureRegion(items, 384, 1024, 128, 128),  
                new TextureRegion(items, 256, 1024, 128, 128),
                new TextureRegion(items, 128, 1024, 128, 128), 
                new TextureRegion(items, 0, 1024, 128, 128)
                );
	    
	    controlImpulse = new MyAnimation(0.1f,
                new TextureRegion(controlsAsset, 0, 0, 128, 128), 
                new TextureRegion(controlsAsset, 128, 0, 128, 128),  
                new TextureRegion(controlsAsset, 256, 0, 128, 128),  
                new TextureRegion(controlsAsset, 384, 0, 128, 128), 
                new TextureRegion(controlsAsset, 512, 0, 128, 128),                     
                new TextureRegion(controlsAsset, 0, 128, 128, 128), 
                new TextureRegion(controlsAsset, 128, 128, 128, 128),  
                new TextureRegion(controlsAsset, 256, 128, 128, 128),  
                new TextureRegion(controlsAsset, 384, 128, 128, 128), 
                new TextureRegion(controlsAsset, 512, 128, 128, 128), 
                new TextureRegion(controlsAsset, 384, 128, 128, 128), 
                new TextureRegion(controlsAsset, 256, 128, 128, 128), 
                new TextureRegion(controlsAsset, 128, 128, 128, 128), 
                new TextureRegion(controlsAsset, 0, 128, 128, 128),  
                new TextureRegion(controlsAsset, 512, 0, 128, 128), 
                new TextureRegion(controlsAsset, 384, 0, 128, 128),  
                new TextureRegion(controlsAsset, 256, 0, 128, 128),
                new TextureRegion(controlsAsset, 128, 0, 128, 128), 
                new TextureRegion(controlsAsset, 0, 0, 128, 128)
                );
	    
	    controlWeapon = new MyAnimation(0.1f,
                new TextureRegion(controlsAsset, 0, 256, 128, 128), 
                new TextureRegion(controlsAsset, 128, 256, 128, 128),  
                new TextureRegion(controlsAsset, 256, 256, 128, 128),  
                new TextureRegion(controlsAsset, 384, 256, 128, 128), 
                new TextureRegion(controlsAsset, 512, 256, 128, 128),                     
                new TextureRegion(controlsAsset, 0, 384, 128, 128), 
                new TextureRegion(controlsAsset, 128, 384, 128, 128),  
                new TextureRegion(controlsAsset, 256, 384, 128, 128),  
                new TextureRegion(controlsAsset, 384, 384, 128, 128), 
                new TextureRegion(controlsAsset, 512, 384, 128, 128), 
                new TextureRegion(controlsAsset, 384, 384, 128, 128), 
                new TextureRegion(controlsAsset, 256, 384, 128, 128), 
                new TextureRegion(controlsAsset, 128, 384, 128, 128), 
                new TextureRegion(controlsAsset, 0, 384, 128, 128),  
                new TextureRegion(controlsAsset, 512, 256, 128, 128), 
                new TextureRegion(controlsAsset, 384, 256, 128, 128),  
                new TextureRegion(controlsAsset, 256, 256, 128, 128),
                new TextureRegion(controlsAsset, 128, 256, 128, 128), 
                new TextureRegion(controlsAsset, 0, 256, 128, 128)
                );
	    
	    
	    
	    snakeHead = new TextureRegion(items, 0, 0, 64, 64); 
	    snakeTail = new TextureRegion(items, 320, 64, 64, 64); 
	    snakeOpenMouth = new TextureRegion(items, 1088, 1024, 64, 64); 
	    
	    snakeGoldForwardAttack = new TextureRegion(items, 1056, 384, 32, 64); 
	    snakeGoldForwardAttackCharging = new MyAnimation(Statics.Render.SNAKE_GOLD_FORWARD_ATTACK_CHARGING_ACT_SPEED,
                new TextureRegion(items, 320, 448, 32, 64),		//empty cell
                new TextureRegion(items, 1120, 384, 32, 64),
                new TextureRegion(items, 1056, 384, 32, 64),
                new TextureRegion(items, 1120, 384, 32, 64),
                new TextureRegion(items, 320, 448, 32, 64)                                       
                );
	    
	    snakeGoldForwardHookLeft = new TextureRegion(items, 1024, 384, 32, 32); 
	    snakeGoldForwardHookLeftCharging = new MyAnimation(Statics.Render.SNAKE_GOLD_FORWARD_ATTACK_CHARGING_ACT_SPEED,
                new TextureRegion(items, 320, 448, 32, 32),		//empty cell
                new TextureRegion(items, 1088, 384, 32, 32),
                new TextureRegion(items, 1024, 384, 32, 32),
                new TextureRegion(items, 1088, 384, 32, 32),
                new TextureRegion(items, 320, 448, 32, 32)                                       
                );
	    
	    snakeGoldForwardHookRight = new TextureRegion(items, 1024, 416, 32, 32); 
	    snakeGoldForwardHookRightCharging = new MyAnimation(Statics.Render.SNAKE_GOLD_FORWARD_ATTACK_CHARGING_ACT_SPEED,
                new TextureRegion(items, 320, 448, 32, 32),		//empty cell
                new TextureRegion(items, 1088, 416, 32, 32),
                new TextureRegion(items, 1024, 416, 32, 32),
                new TextureRegion(items, 1088, 416, 32, 32),
                new TextureRegion(items, 320, 448, 32, 32)                                       
                );
	    
	    snakeGoldForwardLeftTurn = new TextureRegion(items, 1168, 464, 16, 16); 
	    snakeGoldForwardLeftTurnCharging = new MyAnimation(Statics.Render.SNAKE_GOLD_FORWARD_ATTACK_CHARGING_ACT_SPEED,
                new TextureRegion(items, 320, 464, 16, 16),		//empty cell
                new TextureRegion(items, 1232, 464, 16, 16),
                new TextureRegion(items, 1168, 464, 16, 16),
                new TextureRegion(items, 1232, 464, 16, 16),
                new TextureRegion(items, 320, 464, 16, 16)        //empty cell                               
                );
	    
	    snakeGoldForwardRightTurn = new TextureRegion(items, 1168, 480, 16, 16); 
	    snakeGoldForwardRightTurnCharging = new MyAnimation(Statics.Render.SNAKE_GOLD_FORWARD_ATTACK_CHARGING_ACT_SPEED,
                new TextureRegion(items, 320, 448, 16, 16),		//empty cell
                new TextureRegion(items, 1232, 480, 16, 16),
                new TextureRegion(items, 1168, 480, 16, 16),
                new TextureRegion(items, 1232, 480, 16, 16),
                new TextureRegion(items, 320, 448, 16,16)       //empty cell                                
                );
	    
	    snakeGoldBackAttack = new TextureRegion(items, 1184, 472, 16, 16); 
	    snakeGoldBackAttackCharging = new MyAnimation(Statics.Render.SNAKE_GOLD_FORWARD_ATTACK_CHARGING_ACT_SPEED,
                new TextureRegion(items, 320, 472, 16, 16),		//empty cell
                new TextureRegion(items, 1248, 472, 16, 16),
                new TextureRegion(items, 1184, 472, 16, 16),
                new TextureRegion(items, 1248, 472, 16, 16),
                new TextureRegion(items, 320, 472, 16, 16)        //empty cell                               
                );
	    
	    snakeGoldBackHookLeft = new TextureRegion(items, 1152, 464, 16, 16); 
	    snakeGoldBackHookLeftCharging = new MyAnimation(Statics.Render.SNAKE_GOLD_FORWARD_ATTACK_CHARGING_ACT_SPEED,
                new TextureRegion(items, 320, 464, 16, 16),		//empty cell
                new TextureRegion(items, 1216, 464, 16, 16),
                new TextureRegion(items, 1152, 464, 16, 16),
                new TextureRegion(items, 1216, 464, 16, 16),
                new TextureRegion(items, 320, 464, 16, 16)        //empty cell                               
                );
	    
	    snakeGoldBackHookRight = new TextureRegion(items, 1152, 480, 16, 16); 
	    snakeGoldBackHookRightCharging = new MyAnimation(Statics.Render.SNAKE_GOLD_FORWARD_ATTACK_CHARGING_ACT_SPEED,
                new TextureRegion(items, 320, 480, 16, 16),		//empty cell
                new TextureRegion(items, 1216, 480, 16, 16),
                new TextureRegion(items, 1152, 480, 16, 16),
                new TextureRegion(items, 1216, 480, 16, 16),
                new TextureRegion(items, 320, 480, 16, 16)        //empty cell                               
                );
	    
	    snakeImpulseForwardDefense = new TextureRegion(items, 1200, 464, 16, 16); 
	    snakeImpulseForwardDefenseCharging = new MyAnimation(Statics.Render.SNAKE_GOLD_FORWARD_ATTACK_CHARGING_ACT_SPEED,
                new TextureRegion(items, 320, 464, 16, 16),		//empty cell
                new TextureRegion(items, 1264, 464, 16, 16),
                new TextureRegion(items, 1200, 464, 16, 16),
                new TextureRegion(items, 1264, 464, 16, 16),
                new TextureRegion(items, 320, 464, 16, 16)        //empty cell                               
                );
	    
	    snakeImpulseForwardAttack = new TextureRegion(items, 1200, 480, 16, 16); 
	    snakeImpulseForwardAttackCharging = new MyAnimation(Statics.Render.SNAKE_GOLD_FORWARD_ATTACK_CHARGING_ACT_SPEED,
                new TextureRegion(items, 320, 480, 16, 16),		//empty cell
                new TextureRegion(items, 1264, 480, 16, 16),
                new TextureRegion(items, 1200, 480, 16, 16),
                new TextureRegion(items, 1264, 480, 16, 16),
                new TextureRegion(items, 320, 480, 16, 16)        //empty cell                               
                );
	    
	    snakeImpulseLeftSideDef = new TextureRegion(items, 1072, 400, 16, 16); 
	    snakeImpulseLeftSideDefCharging = new MyAnimation(Statics.Render.SNAKE_GOLD_FORWARD_ATTACK_CHARGING_ACT_SPEED,
                new TextureRegion(items, 320, 448, 16, 16),		//empty cell
                new TextureRegion(items, 1136, 400, 16, 16),
                new TextureRegion(items, 1072, 400, 16, 16),
                new TextureRegion(items, 1136, 400, 16, 16),
                new TextureRegion(items, 320, 448, 16, 16)                                       
                );
	    
	    snakeImpulseRightSideDef = new TextureRegion(items, 1072, 416, 16, 16); 
	    snakeImpulseRightSideDefCharging = new MyAnimation(Statics.Render.SNAKE_GOLD_FORWARD_ATTACK_CHARGING_ACT_SPEED,
                new TextureRegion(items, 320, 448, 16, 16),		//empty cell
                new TextureRegion(items, 1136, 416, 16, 16),
                new TextureRegion(items, 1072, 416, 16, 16),
                new TextureRegion(items, 1136, 416, 16, 16),
                new TextureRegion(items, 320, 448, 16, 16)                                       
                );
	    
	    snakeGoldAttack = new MyAnimation(Statics.Render.SNAKE_GOLD_ATTACK_ACT_SPEED,	
                new TextureRegion(items, 1152, 384, 64, 64),
                new TextureRegion(items, 1216, 384, 64, 64)                                  
                );
	    
	    snakeGoldAttackBack = new MyAnimation(Statics.Render.SNAKE_GOLD_ATTACK_ACT_SPEED,	
                new TextureRegion(items, 1216, 384, 64, 64),
                new TextureRegion(items, 1152, 384, 64, 64)                                  
                );
	    
	    snakeHeadGoldAttack = new MyAnimation(Statics.Render.SNAKE_GOLD_ATTACK_ACT_SPEED,	
                new TextureRegion(items, 1024, 448, 64, 64),
                new TextureRegion(items, 1088, 448, 64, 64)                                    
                );
	    
	    snakeHeadGoldAttackBack = new MyAnimation(Statics.Render.SNAKE_GOLD_ATTACK_ACT_SPEED,	
                new TextureRegion(items, 1088, 448, 64, 64),
                new TextureRegion(items, 1024, 448, 64, 64)                                  
                );
	    
	    snakeHeadBlueAttack = new MyAnimation(Statics.Render.SPECEFFECT_STANDART_ACT_SPEED,	
                new TextureRegion(items, 1024, 512, 64, 64),
                new TextureRegion(items, 1088, 512, 64, 64),
                new TextureRegion(items, 1088, 512, 64, 64),
                new TextureRegion(items, 1024, 512, 64, 64)
                );
	    
	    snakePartBlueAttack = new MyAnimation(Statics.Render.SPECEFFECT_STANDART_ACT_SPEED,	
                new TextureRegion(items, 1152, 512, 64, 64),
                new TextureRegion(items, 1216, 512, 64, 64),
                new TextureRegion(items, 1216, 512, 64, 64),
                new TextureRegion(items, 1152, 512, 64, 64)
                );
	    
	    impulseForwardAttack = new TextureRegion(items, 384, 512, 128, 128); 
	    
	    snakeDamagedEasy = new TextureRegion(items, 1024, 576, 64, 64); 
	    snakeDamagedHard = new TextureRegion(items, 1088, 576, 64, 64); 
	    
	    snakePartArmorWoodLeft = new TextureRegion(items, 1152, 576, 64, 32); 
	    snakePartArmorWoodDamagedLeft = new TextureRegion(items, 1216, 576, 64, 32); 	
	    snakePartArmorWoodRight = new TextureRegion(items, 1152, 608, 64, 32); 
	    snakePartArmorWoodDamagedRight = new TextureRegion(items, 1216, 608, 64, 32); 
	    
	    apple = new TextureRegion(items, 0, 256, 64, 64); 
	    mushroomBlue = new TextureRegion(items, 64, 256, 64, 64); 
	    mushroomYellow = new TextureRegion(items, 128, 256, 64, 64);
	    mushroomBRed = new TextureRegion(items, 192, 256, 64, 64);
	    mushroomBrown = new TextureRegion(items, 256, 256, 64, 64);
	    coin = new TextureRegion(items, 320, 256, 64, 64);
	    
	    bloodStain1 = new TextureRegion(items, 320, 384, 64, 64);
	    bloodStain2 = new TextureRegion(items, 384, 896, 64, 64);
	    bloodStain3 = new TextureRegion(items, 128, 960, 64, 64);
	    	    
	    bombTree = new TextureRegion(items, 384, 0, 64, 256);
	    bombFromTree = new TextureRegion(items, 320, 192, 16, 16);
	    charFrogMove = new TextureRegion(items, 704, 192, 64, 128);
	    
	    blockWood1 = new TextureRegion(items, 448, 0, 64, 64);
	    blockWood2 = new TextureRegion(items, 448, 64, 64, 64);
	    blockWood3 = new TextureRegion(items, 448, 128, 64, 64);
	    blockWood4 = new TextureRegion(items, 448, 192, 64, 64);
	    blockWoodPart = new TextureRegion(items, 448, 192, 32, 32);
	    blockMarble1 = new TextureRegion(items, 768, 448, 64, 64);
	    blockMarble2 = new TextureRegion(items, 832, 448, 64, 64);
	    blockMarble3 = new TextureRegion(items, 896, 448, 64, 64);
	    blockMarble4 = new TextureRegion(items, 960, 448, 64, 64);
	    
	    fSkillIce = new TextureRegion(items, 1024, 1024, 64, 64);
	    fSkillIceBroken = new TextureRegion(items, 1024, 1088, 64, 64);
	    fSkillIcePart1 = new TextureRegion(items, 896, 1024, 48, 64);
	    fSkillIcePart2 = new TextureRegion(items, 944, 1024, 48, 64);
	    fSkillIcePart3 = new TextureRegion(items, 992, 1024, 32, 64);
	    
	    numbers = new TextureRegion[10];
	    numbers[0] = new TextureRegion(items, 512, 896, 60, 128);
	    numbers[1] = new TextureRegion(items, 570, 896, 44, 128);
	    numbers[2] = new TextureRegion(items, 612, 896, 60, 128);
	    numbers[3] = new TextureRegion(items, 672, 896, 44, 128);
	    numbers[4] = new TextureRegion(items, 716, 896, 58, 128);
	    numbers[5] = new TextureRegion(items, 774, 896, 44, 128);
	    numbers[6] = new TextureRegion(items, 818, 896, 54, 128);
	    numbers[7] = new TextureRegion(items, 872, 896, 52, 128);
	    numbers[8] = new TextureRegion(items, 924, 896, 48, 128);
	    numbers[9] = new TextureRegion(items, 972, 896, 54, 128);
	    
	    text = new TextureRegion[93];
	    text[0] = new TextureRegion(textAsset, 20, 0, 90, 185);
	    text[1] = new TextureRegion(textAsset, 110, 0, 105, 185);
	    text[2] = new TextureRegion(textAsset, 215, 0, 125, 185);
	    text[3] = new TextureRegion(textAsset, 340, 0, 115, 185);
	    text[4] = new TextureRegion(textAsset, 455, 0, 170, 185);
	    text[5] = new TextureRegion(textAsset, 625, 0, 140, 185);
	    text[6] = new TextureRegion(textAsset, 760, 0, 65, 185);
	    text[7] = new TextureRegion(textAsset, 825, 0, 95, 185);
	    text[8] = new TextureRegion(textAsset, 920, 0, 100, 185);
	    text[9] = new TextureRegion(textAsset, 1020, 0, 115, 185);
	    text[10] = new TextureRegion(textAsset, 1135, 0, 130, 185);
	    text[11] = new TextureRegion(textAsset, 1265, 0, 85, 185);
	    text[12] = new TextureRegion(textAsset, 1350, 0, 95, 185);
	    text[13] = new TextureRegion(textAsset, 1445, 0, 80, 185);
	    text[14] = new TextureRegion(textAsset, 1525, 0, 90, 185);
	    
	    text[15] = new TextureRegion(textAsset, 0, 178, 105, 182);
	    text[16] = new TextureRegion(textAsset, 105, 178, 80, 182);
	    text[17] = new TextureRegion(textAsset, 185, 178, 110, 182);
	    text[18] = new TextureRegion(textAsset, 295, 178, 80, 182);
	    text[19] = new TextureRegion(textAsset, 375, 178, 110, 182);
	    text[20] = new TextureRegion(textAsset, 485, 178, 85, 182);
	    text[21] = new TextureRegion(textAsset, 570, 178, 103, 182);
	    text[22] = new TextureRegion(textAsset, 673, 178, 95, 182);
	    text[23] = new TextureRegion(textAsset, 768, 178, 92, 182);
	    text[24] = new TextureRegion(textAsset, 860, 178, 100, 182);
	    text[25] = new TextureRegion(textAsset, 960, 178, 45, 182);
	    text[26] = new TextureRegion(textAsset, 1005, 178, 43, 182);
	    text[27] = new TextureRegion(textAsset, 1048, 178, 87, 182);
	    text[28] = new TextureRegion(textAsset, 1135, 178, 90, 182);
	    text[29] = new TextureRegion(textAsset, 1225, 178, 93, 182);
	    text[30] = new TextureRegion(textAsset, 1318, 178, 67, 182);
	    
	    text[31] = new TextureRegion(textAsset, 0, 360, 127, 170);
	    text[32] = new TextureRegion(textAsset, 127, 360, 133, 170);
	    text[33] = new TextureRegion(textAsset, 260, 360, 115, 170);
	    text[34] = new TextureRegion(textAsset, 375, 360, 135, 170);
	    text[35] = new TextureRegion(textAsset, 510, 360, 150, 170);
	    text[36] = new TextureRegion(textAsset, 660, 360, 105, 170);
	    text[37] = new TextureRegion(textAsset, 765, 360, 105, 170);
	    text[38] = new TextureRegion(textAsset, 870, 360, 135, 170);
	    text[39] = new TextureRegion(textAsset, 1005, 360, 125, 170);
	    text[40] = new TextureRegion(textAsset, 1130, 360, 80, 170);
	    text[41] = new TextureRegion(textAsset, 1210, 360, 85, 170);
	    text[42] = new TextureRegion(textAsset, 1295, 360, 125, 170);
	    text[43] = new TextureRegion(textAsset, 1420, 360, 100, 170);		    
	    text[44] = new TextureRegion(textAsset, 1520, 360, 155, 170);
	    text[45] = new TextureRegion(textAsset, 1675, 360, 140, 170);
	    text[46] = new TextureRegion(textAsset, 1815, 360, 155, 170);
	    
	    text[47] = new TextureRegion(textAsset, 0, 530, 105, 170);
	    text[48] = new TextureRegion(textAsset, 105, 530, 165, 170);
	    text[49] = new TextureRegion(textAsset, 270, 530, 155, 170);
	    text[50] = new TextureRegion(textAsset, 425, 530, 120, 170);
	    text[51] = new TextureRegion(textAsset, 545, 530, 145, 170);
	    text[52] = new TextureRegion(textAsset, 690, 530, 130, 170);
	    text[53] = new TextureRegion(textAsset, 820, 530, 135, 170);
	    text[54] = new TextureRegion(textAsset, 955, 530, 155, 170);
	    text[55] = new TextureRegion(textAsset, 1110, 530, 120, 170);
	    text[56] = new TextureRegion(textAsset, 1230, 530, 120, 170);
	    text[57] = new TextureRegion(textAsset, 1350, 530, 120, 170);
	    text[58] = new TextureRegion(textAsset, 1470, 530, 80, 170);
	    text[59] = new TextureRegion(textAsset, 1550, 530, 85, 170);
	    text[60] = new TextureRegion(textAsset, 1635, 530, 90, 170);
	    text[61] = new TextureRegion(textAsset, 1725, 530, 105, 170);
	    text[62] = new TextureRegion(textAsset, 1830, 530, 110, 170);		    
	    
	    text[63] = new TextureRegion(textAsset, 0, 700, 45, 150);
	    text[64] = new TextureRegion(textAsset, 45, 700, 110, 150);
	    text[65] = new TextureRegion(textAsset, 155, 700, 110, 150);
	    text[66] = new TextureRegion(textAsset, 265, 700, 125, 150);
	    text[67] = new TextureRegion(textAsset, 390, 700, 135, 150);
	    text[68] = new TextureRegion(textAsset, 525, 700, 115, 150);
	    text[69] = new TextureRegion(textAsset, 640, 700, 100, 150);
	    text[70] = new TextureRegion(textAsset, 740, 700, 120, 150);
	    text[71] = new TextureRegion(textAsset, 860, 700, 130, 150);
	    text[72] = new TextureRegion(textAsset, 1010, 700, 80, 150);
	    text[73] = new TextureRegion(textAsset, 1070, 700, 85, 150);
	    text[74] = new TextureRegion(textAsset, 1155, 700, 125, 150);
	    text[75] = new TextureRegion(textAsset, 1280, 700, 110, 150);
	    text[76] = new TextureRegion(textAsset, 1390, 700, 150, 150);
	    text[77] = new TextureRegion(textAsset, 1540, 700, 125, 150);
	    text[78] = new TextureRegion(textAsset, 1665, 700, 135, 150);
	    
	    text[79] = new TextureRegion(textAsset, 0, 850, 95, 150);
	    text[80] = new TextureRegion(textAsset, 95, 850, 140, 150);
	    text[81] = new TextureRegion(textAsset, 235, 850, 120, 150);
	    text[82] = new TextureRegion(textAsset, 355, 850, 100, 150);
	    text[83] = new TextureRegion(textAsset, 455, 850, 110, 150);
	    text[84] = new TextureRegion(textAsset, 565, 850, 120, 150);
	    text[85] = new TextureRegion(textAsset, 685, 850, 120, 150);
	    text[86] = new TextureRegion(textAsset, 805, 850, 135, 150);
	    text[87] = new TextureRegion(textAsset, 940, 850, 125, 150);
	    text[88] = new TextureRegion(textAsset, 1065, 850, 110, 150);
	    text[89] = new TextureRegion(textAsset, 1175, 850, 115, 150);
	    text[90] = new TextureRegion(textAsset, 1290, 850, 100, 150);
	    text[91] = new TextureRegion(textAsset, 1390, 850, 75, 150);
	    text[92] = new TextureRegion(textAsset, 1465, 850, 95, 150);
	    		    
	    
	    red = new TextureRegion(items, 320, 128, 32, 32);
	    grey = new TextureRegion(items, 352, 128, 32, 32);
	    green = new TextureRegion(items, 320, 160, 32, 32);
	    yellow = new TextureRegion(items, 352, 160, 32, 32);		    
	    
	    giftApper = new MyAnimation(Gifts.ACT_SPEED,
                new TextureRegion(items, 0, 320, 64, 64),	
                new TextureRegion(items, 64, 320, 64, 64),
                new TextureRegion(items, 128, 320, 64, 64),
                new TextureRegion(items, 192, 320, 64, 64),
                new TextureRegion(items, 256, 320, 64, 64),
                
                new TextureRegion(items, 0, 384, 64, 64),	
                new TextureRegion(items, 64, 384, 64, 64),
                new TextureRegion(items, 128, 384, 64, 64),
                new TextureRegion(items, 192, 384, 64, 64),
                new TextureRegion(items, 256, 384, 64, 64),
                
                new TextureRegion(items, 0, 448, 64, 64),	
                new TextureRegion(items, 64, 448, 64, 64),
                new TextureRegion(items, 128, 448, 64, 64),
                new TextureRegion(items, 192, 448, 64, 64),
                new TextureRegion(items, 256, 448, 64, 64)                    
                );
	    
	    impact = new MyAnimation(WorldKingSnake.SEPECEFFECT_ACT_SPEED,
                new TextureRegion(items, 384, 512, 128, 128),	
                new TextureRegion(items, 256, 512, 128, 128),
                new TextureRegion(items, 128, 512, 128, 128),
                new TextureRegion(items, 0, 512, 128, 128),
                
                new TextureRegion(items, 384, 640, 128, 128),	
                new TextureRegion(items, 256, 640, 128, 128),
                new TextureRegion(items, 128, 640, 128, 128),
                new TextureRegion(items, 0, 640, 128, 128),
                
                new TextureRegion(items, 384, 768, 128, 128),	
                new TextureRegion(items, 256, 768, 128, 128),
                new TextureRegion(items, 128, 768, 128, 128),
                new TextureRegion(items, 0, 768, 128, 128)
                );
	    
	    water = new MyAnimation(Statics.StaticEffect.WATER_ACT_SPEED,
                new TextureRegion(items, 384, 256, 64, 64),	
                new TextureRegion(items, 448, 256, 64, 64),
                new TextureRegion(items, 384, 320, 64, 64),
                new TextureRegion(items, 448, 320, 64, 64),
                new TextureRegion(items, 384, 320, 64, 64),
                new TextureRegion(items, 448, 256, 64, 64)
                );
	    
	    hitStars = new MyAnimation(WorldKingSnake.SEPECEFFECT_ACT_SPEED * 1.2f,
                new TextureRegion(items, 0, 192, 64, 64),	
                new TextureRegion(items, 64, 192, 64, 64),
                new TextureRegion(items, 128, 192, 64, 64),
                new TextureRegion(items, 192, 192, 64, 64),
                new TextureRegion(items, 256, 192, 64, 64)
                );
	    
	    bloodSmall = new MyAnimation(WorldKingSnake.SEPECEFFECT_ACT_SPEED,
                new TextureRegion(items, 0, 896, 64, 64),	
                new TextureRegion(items, 64, 896, 64, 64),
                new TextureRegion(items, 0, 960, 64, 64),
                new TextureRegion(items, 64, 960, 64, 64)
                );
	    
	    bloodMediumLarge = new MyAnimation(StaticEffect.BLOOD_SPLATTER_MEDIUM_LARGE_ACT_SPEED,
                new TextureRegion(items, 128, 896, 64, 64),	
                new TextureRegion(items, 192, 896, 64, 64),
                new TextureRegion(items, 256, 896, 64, 64),
                new TextureRegion(items, 320, 896, 64, 64),
                new TextureRegion(items, 384, 896, 64, 64),
                new TextureRegion(items, 448, 896, 64, 64),                    
                new TextureRegion(items, 128, 960, 64, 64),	
                new TextureRegion(items, 192, 960, 64, 64),
                new TextureRegion(items, 256, 960, 64, 64),
                new TextureRegion(items, 320, 960, 64, 64),
                new TextureRegion(items, 384, 960, 64, 64),
                new TextureRegion(items, 448, 960, 64, 64)
                );
	    
	    treeBombExplosion = new MyAnimation(WorldKingSnake.SEPECEFFECT_ACT_SPEED,
                new TextureRegion(items, 0, 64, 64, 64),	
                new TextureRegion(items, 64, 64, 64, 64),
                new TextureRegion(items, 128, 64, 64, 64),
                new TextureRegion(items, 192, 64, 64, 64),
                new TextureRegion(items, 0, 128, 64, 64),	
                new TextureRegion(items, 64, 128, 64, 64),
                new TextureRegion(items, 128, 128, 64, 64),
                new TextureRegion(items, 192, 128, 64, 64)
                );
	    
	    attackUpEffect = new MyAnimation(DynamicEffect.ACT_SPEED_BONUS,
                new TextureRegion(items, 768, 512, 64, 64),	
                new TextureRegion(items, 768, 576, 64, 64),
                new TextureRegion(items, 768, 640, 64, 64),
                new TextureRegion(items, 768, 704, 64, 64),
                new TextureRegion(items, 768, 768, 64, 64),	
                new TextureRegion(items, 768, 832, 64, 64)
                );
	    
	    defenseUpEffect = new MyAnimation(DynamicEffect.ACT_SPEED_BONUS,
                new TextureRegion(items, 896, 512, 64, 64),	
                new TextureRegion(items, 896, 576, 64, 64),
                new TextureRegion(items, 896, 640, 64, 64),
                new TextureRegion(items, 896, 704, 64, 64),
                new TextureRegion(items, 896, 768, 64, 64),	
                new TextureRegion(items, 896, 832, 64, 64)
                );
	    
	    healthUpEffect = new MyAnimation(DynamicEffect.ACT_SPEED_BONUS,
                new TextureRegion(items, 832, 512, 64, 64),	
                new TextureRegion(items, 832, 576, 64, 64),
                new TextureRegion(items, 832, 640, 64, 64),
                new TextureRegion(items, 832, 704, 64, 64),
                new TextureRegion(items, 832, 768, 64, 64),	
                new TextureRegion(items, 832, 832, 64, 64)
                );	
	    
	    speedUpEffect = new MyAnimation(DynamicEffect.ACT_SPEED_BONUS,
                new TextureRegion(items, 960, 512, 64, 64),	
                new TextureRegion(items, 960, 576, 64, 64),
                new TextureRegion(items, 960, 640, 64, 64),
                new TextureRegion(items, 960, 704, 64, 64),
                new TextureRegion(items, 960, 768, 64, 64),	
                new TextureRegion(items, 960, 832, 64, 64)
                );
	    
	    getCoinsEffect = new MyAnimation(DynamicEffect.ACT_SPEED_COIN,
                new TextureRegion(items, 256, 64, 64, 64),	
                new TextureRegion(items, 256, 128, 64, 64),
                new TextureRegion(items, 576, 256, 64, 64),
                new TextureRegion(items, 640, 256, 64, 64),
                new TextureRegion(items, 576, 256, 64, 64),
                new TextureRegion(items, 256, 128, 64, 64)
                );
	    
	    looseCoinsEffect = new MyAnimation(DynamicEffect.ACT_SPEED_COIN,
                new TextureRegion(items, 256, 64, 64, 64),	
                new TextureRegion(items, 256, 128, 64, 64),
                new TextureRegion(items, 576, 256, 64, 64),
                new TextureRegion(items, 640, 256, 64, 64),
                new TextureRegion(items, 576, 256, 64, 64),
                new TextureRegion(items, 256, 128, 64, 64)
                );		    
	    
	    attackDownEffect = new MyAnimation(DynamicEffect.ACT_SPEED_BONUS,
                new TextureRegion(items, 1024, 0, 64, 64),	
                new TextureRegion(items, 1024, 64, 64, 64),
                new TextureRegion(items, 1024, 128, 64, 64),
                new TextureRegion(items, 1024, 192, 64, 64),
                new TextureRegion(items, 1024, 256, 64, 64),	
                new TextureRegion(items, 1024, 320, 64, 64)
                );
	    
	    defenseDownEffect = new MyAnimation(DynamicEffect.ACT_SPEED_BONUS,
                new TextureRegion(items, 1152, 0, 64, 64),	
                new TextureRegion(items, 1152, 64, 64, 64),
                new TextureRegion(items, 1152, 128, 64, 64),
                new TextureRegion(items, 1152, 192, 64, 64),
                new TextureRegion(items, 1152, 256, 64, 64),	
                new TextureRegion(items, 1152, 320, 64, 64)
                );
	    
	    healthDownEffect = new MyAnimation(DynamicEffect.ACT_SPEED_BONUS,
                new TextureRegion(items, 1088, 0, 64, 64),	
                new TextureRegion(items, 1088, 64, 64, 64),
                new TextureRegion(items, 1088, 128, 64, 64),
                new TextureRegion(items, 1088, 192, 64, 64),
                new TextureRegion(items, 1088, 256, 64, 64),	
                new TextureRegion(items, 1088, 320, 64, 64)
                );	
	    
	    speedDownEffect = new MyAnimation(DynamicEffect.ACT_SPEED_BONUS,
                new TextureRegion(items, 1216, 0, 64, 64),	
                new TextureRegion(items, 1216, 64, 64, 64),
                new TextureRegion(items, 1216, 128, 64, 64),
                new TextureRegion(items, 1216, 192, 64, 64),
                new TextureRegion(items, 1216, 256, 64, 64),	
                new TextureRegion(items, 1216, 320, 64, 64)
                );
	    		    
	    charLemmingStay = new MyAnimation(Lemming.ACT_SPEED,
                new TextureRegion(items, 768, 128, 64, 64),	//roll 2xtimes
                new TextureRegion(items, 832, 128, 64, 64),
                new TextureRegion(items, 896, 128, 64, 64),
                new TextureRegion(items, 960, 128, 64, 64),                    
                new TextureRegion(items, 896, 128, 64, 64),	
                new TextureRegion(items, 832, 128, 64, 64),
                new TextureRegion(items, 768, 128, 64, 64),                    
                new TextureRegion(items, 832, 128, 64, 64),
                new TextureRegion(items, 896, 128, 64, 64),
                new TextureRegion(items, 960, 128, 64, 64),                    
                new TextureRegion(items, 896, 128, 64, 64),	
                new TextureRegion(items, 832, 128, 64, 64),
                new TextureRegion(items, 768, 128, 64, 64),
                new TextureRegion(items, 768, 192, 64, 64),//clipping
                new TextureRegion(items, 832, 192, 64, 64),
                new TextureRegion(items, 896, 192, 64, 64),
                new TextureRegion(items, 960, 192, 64, 64),                    
                new TextureRegion(items, 896, 192, 64, 64),	
                new TextureRegion(items, 832, 192, 64, 64),
                new TextureRegion(items, 768, 192, 64, 64)
                );
	    
	    charLemmingMove = new MyAnimation(Lemming.ACT_SPEED,
                new TextureRegion(items, 768, 0, 64, 128),
                new TextureRegion(items, 832, 0, 64, 128),
                new TextureRegion(items, 896, 0, 64, 128),
                new TextureRegion(items, 960, 0, 64, 128),                    
                new TextureRegion(items, 896, 0, 64, 128),	
                new TextureRegion(items, 832, 0, 64, 128)                   
                );
	    
	    charHedgehogStay = new MyAnimation(Hedgehog.ACT_SPEED,
                new TextureRegion(items, 768, 320, 64, 64),	//sniff forward 2xtimes
                new TextureRegion(items, 768, 320, 64, 64),
                new TextureRegion(items, 832, 320, 64, 64),
                new TextureRegion(items, 896, 320, 64, 64),
                new TextureRegion(items, 960, 320, 64, 64),                    
                new TextureRegion(items, 896, 320, 64, 64),	
                new TextureRegion(items, 832, 320, 64, 64),
                new TextureRegion(items, 768, 320, 64, 64),                    
                new TextureRegion(items, 832, 320, 64, 64),
                new TextureRegion(items, 896, 320, 64, 64),
                new TextureRegion(items, 960, 320, 64, 64),                    
                new TextureRegion(items, 896, 320, 64, 64),	
                new TextureRegion(items, 832, 320, 64, 64),
                new TextureRegion(items, 768, 320, 64, 64),
                new TextureRegion(items, 768, 320, 64, 64),
                new TextureRegion(items, 768, 384, 64, 64),// sniff up 2xtimes
                new TextureRegion(items, 768, 384, 64, 64),
                new TextureRegion(items, 832, 384, 64, 64),
                new TextureRegion(items, 896, 384, 64, 64),
                new TextureRegion(items, 960, 384, 64, 64),                    
                new TextureRegion(items, 896, 384, 64, 64),	
                new TextureRegion(items, 832, 384, 64, 64),                    
                new TextureRegion(items, 768, 384, 64, 64),
                new TextureRegion(items, 832, 384, 64, 64),
                new TextureRegion(items, 896, 384, 64, 64),
                new TextureRegion(items, 960, 384, 64, 64),                    
                new TextureRegion(items, 896, 384, 64, 64),	
                new TextureRegion(items, 832, 384, 64, 64)
                );
	    
	    charHedgehogMove = new MyAnimation(Hedgehog.ACT_SPEED,
                new TextureRegion(items, 768, 256, 64, 64),
                new TextureRegion(items, 832, 256, 64, 64),
                new TextureRegion(items, 896, 256, 64, 64),
                new TextureRegion(items, 960, 256, 64, 64),                    
                new TextureRegion(items, 896, 256, 64, 64),	
                new TextureRegion(items, 832, 256, 64, 64)                   
                );
	    
	    charFrogStay = new MyAnimation(Frog.ACT_SPEED,
	            new TextureRegion(items, 512, 0, 64, 64),	//breathing, eyes from right to left
	            new TextureRegion(items, 576, 0, 64, 64),
	            new TextureRegion(items, 640, 0, 64, 64),
	            new TextureRegion(items, 704, 0, 64, 64),                
	            new TextureRegion(items, 704, 64, 64, 64),                
	            new TextureRegion(items, 640, 64, 64, 64),                
	            new TextureRegion(items, 576, 64, 64, 64),               
	            new TextureRegion(items, 512, 64, 64, 64),		
	            
	            new TextureRegion(items, 512, 128, 64, 64),	//breathing, eyes from left to right
	            new TextureRegion(items, 576, 128, 64, 64),
	            new TextureRegion(items, 640, 128, 64, 64),
	            new TextureRegion(items, 704, 128, 64, 64),		       	         			              		            
	            
	            new TextureRegion(items, 512, 192, 64, 64),	//breathing, eyes to right
	            new TextureRegion(items, 576, 192, 64, 64),               
	            new TextureRegion(items, 640, 192, 64, 64),                
	            new TextureRegion(items, 512, 256, 64, 64),                
	            new TextureRegion(items, 640, 192, 64, 64),               
	            new TextureRegion(items, 576, 192, 64, 64),
	            new TextureRegion(items, 512, 192, 64, 64),
	            
	            new TextureRegion(items, 512, 320, 64, 64),	//breathing, eyes forward
	            new TextureRegion(items, 576, 320, 64, 64),
	            new TextureRegion(items, 640, 320, 64, 64),
	            new TextureRegion(items, 704, 320, 64, 64),                              
	            new TextureRegion(items, 640, 320, 64, 64),                
	            new TextureRegion(items, 576, 320, 64, 64),               
	            new TextureRegion(items, 512, 320, 64, 64)		    
	    		);	
	    
	    charFrogGrounding = new MyAnimation(Frog.ACT_SPEED,
	            new TextureRegion(items, 512, 384, 128, 128),	
	            new TextureRegion(items, 640, 384, 128, 128),
	            new TextureRegion(items, 512, 512, 128, 128),
	            new TextureRegion(items, 640, 512, 128, 128),
	            new TextureRegion(items, 512, 640, 128, 128),
	            new TextureRegion(items, 640, 640, 128, 128),
	            new TextureRegion(items, 512, 768, 128, 128),
	            new TextureRegion(items, 640, 768, 128, 128)
	    		);
	    
	    weaponHelmWoodenSimpleBack = new TextureRegion(items, 704, 1024, 64, 64);
	    weaponHelmWoodenSimplePart1 = new TextureRegion(items, 640, 1024, 32, 64);
	    weaponHelmWoodenSimplePart2 = new TextureRegion(items, 672, 1024, 32, 64);
	    
	    weaponShieldVikkingWoodenSimplePart1 = new TextureRegion(items, 640, 1088, 64, 64);
	    weaponShieldVikkingWoodenSimplePart2 = new TextureRegion(items, 640, 1152, 64, 64);
	    weaponShieldVikkingWoodenSimplePart3 = new TextureRegion(items, 640, 1216, 64, 64);

	    weaponSwordVikkingBronzeWooden = new TextureRegion(items, 768, 1024, 32, 64);
	    weaponSwordVikkingBronzeWoodenBroken = new TextureRegion(items, 800, 1024, 32, 64);
	    weaponSwordVikkingBronzeWoodenPart1 = new TextureRegion(items, 800, 1056, 32, 32);
	    weaponSwordVikkingBronzeWoodenPart2 = new TextureRegion(items, 800, 1024, 32, 32);
	    
	    weaponAxeMonosideVikkingBronzeWooden = new TextureRegion(items, 704, 1088, 32, 64);
	    weaponAxeMonosideVikkingBronzeWoodenBroken = new TextureRegion(items, 736, 1088, 32, 64);
	    weaponAxeMonosideVikkingBronzeWoodenPart1 = new TextureRegion(items, 736, 1120, 32, 32);
	    weaponAxeMonosideVikkingBronzeWoodenPart2 = new TextureRegion(items, 736, 1088, 32, 32);
	    
	    weaponAxeDoublesideVikkingBronzeWooden = new TextureRegion(items, 704, 1152, 64, 64);
	    weaponAxeDoublesideVikkingBronzeWoodenBroken = new TextureRegion(items, 704, 1216, 64, 64);
	    weaponAxeDoublesideVikkingBronzeWoodenPart1 = new TextureRegion(items, 704, 1248, 64, 32);
	    weaponAxeDoublesideVikkingBronzeWoodenPart2 = new TextureRegion(items, 704, 1216, 64, 32);
	    
	    weaponSpearVikkingBronzeWooden = new TextureRegion(items, 768, 1152, 32, 128);
	    weaponSpearVikkingBronzeWoodenBroken = new TextureRegion(items, 800, 1152, 32, 128);
	    weaponSpearVikkingBronzeWoodenPart1 = new TextureRegion(items, 704, 1216, 32, 64);
	    weaponSpearVikkingBronzeWoodenPart2 = new TextureRegion(items, 704, 1152, 32, 64);
	    
	    contextSnakeHead = new TextureRegion(contextAssets, 512, 0, 128, 128);
	    contextSnakePart = new TextureRegion(contextAssets, 512, 128, 64, 128);
	    contextSnakeLevelPink = new TextureRegion(contextAssets, 512, 384, 128, 128);
	    contextWoundEasy = new TextureRegion(contextAssets, 576, 128, 64, 128);
	    contextWoundStrong = new TextureRegion(contextAssets, 576, 256, 64, 128);
	    contextFrog = new TextureRegion(contextAssets, 0, 0, 256, 256);
	    contextLemming = new TextureRegion(contextAssets, 256, 0, 192, 256);
	    contextHHog = new TextureRegion(contextAssets, 0, 192, 256, 256);
	    contextMinerTree = new TextureRegion(contextAssets, 256, 256, 64, 192);
	    contextHypTree = new TextureRegion(contextAssets, 384, 832, 128, 192);
	    contextGreenTree = new TextureRegion(contextAssets, 192, 512, 256, 256);
	    contextYellowTree = new TextureRegion(contextAssets, 0, 768, 256, 256);
	    contextBrownTree = new TextureRegion(contextAssets, 0, 512, 192, 256);
	    contextBlackStone = new TextureRegion(contextAssets, 384, 384, 64, 64);
	    contextGreyStone = new TextureRegion(contextAssets, 448, 384, 64, 64);
	    contextSkullStone = new TextureRegion(contextAssets, 320, 448, 64, 64);
	    contextArrowStone = new TextureRegion(contextAssets, 384, 448, 64, 64);
	    contextWoodBlock = new TextureRegion(contextAssets, 256, 768, 128, 128);
	    contextMarbleBlock = new TextureRegion(contextAssets, 256, 896, 128, 128);    
	    contextMushroomYellow = new TextureRegion(contextAssets, 384, 256, 64, 64);
	    contextMushroomBlue = new TextureRegion(contextAssets, 448, 256, 64, 64);
	    contextMushroomBrown = new TextureRegion(contextAssets, 384, 320, 64, 64);
	    contextMushroomBRed = new TextureRegion(contextAssets, 448, 320, 64, 64);
	    contextApple = new TextureRegion(contextAssets, 320, 384, 64, 64);
	    contextAttack = new TextureRegion(contextAssets, 320, 256, 64, 64);
	    contextDefense = new TextureRegion(contextAssets, 320, 320, 64, 64);
	    contextSpeed = new TextureRegion(contextAssets, 448, 448, 64, 64);
	    contextCoins = new TextureRegion(contextAssets, 448, 512, 64, 64);		    
	    contextWater = new TextureRegion(contextAssets, 384, 768, 128, 64);
	    contextSand = new TextureRegion(contextAssets, 128, 448, 128, 64);
	    contextSwamp = new TextureRegion(contextAssets, 0, 448, 128, 64);
	    contextArrow = new TextureRegion(contextAssets, 448, 576, 64, 64);
	    
	    //particles effects
	    particlesEffectsAdditiveArray = new Array<PooledEffect>();
	    particlesEffectsNormalArray = new Array<PooledEffect>();
	    
	    particlesEffectAdditiveSparksRegion = new TextureRegion(items, 768, 1088, 32, 32);
	    gameAtlas.addRegion("sparkles", particlesEffectAdditiveSparksRegion);	
	    
	    particlesEffectAdditiveHypnoseStar1Region = new TextureRegion(items, 1088, 1088, 32, 32);
	    particlesEffectAdditiveHypnoseStar2Region = new TextureRegion(items, 1120, 1088, 32, 32);
	    particlesEffectAdditiveHypnoseBulbRegion = new TextureRegion(items, 1088, 1120, 32, 32);
	    gameAtlas.addRegion("hypnoseStar1", particlesEffectAdditiveHypnoseStar1Region);	
	    gameAtlas.addRegion("hypnoseStar2", particlesEffectAdditiveHypnoseStar2Region);	
	    gameAtlas.addRegion("hypnoseBulb", particlesEffectAdditiveHypnoseBulbRegion);	
	    
	    particlesEffectAdditiveFireRegion = new TextureRegion(items, 1120, 1120, 32, 32);
	    gameAtlas.addRegion("fire", particlesEffectAdditiveFireRegion);	
	    particlesEffectAdditiveSteamRegion = new TextureRegion(items, 1120, 1120, 32, 32);
	    gameAtlas.addRegion("steam", particlesEffectAdditiveSteamRegion);		    
	    
	    particlesEffectAdditiveSpark = new ParticleEffect();
	    particlesEffectAdditiveSpark.load(Gdx.files.internal("effects/sparkles.p"), gameAtlas);
	    particlesEffectAdditiveSpark.setEmittersCleanUpBlendFunction(false); //Stop the additive effect resting the blend state    
	    particlesEffectAdditiveSparksPool = new ParticleEffectPool(particlesEffectAdditiveSpark, 1, 10);
	    
	    particlesEffectAdditiveHypnose = new ParticleEffect();
	    particlesEffectAdditiveHypnose.load(Gdx.files.internal("effects/hypnose.p"), gameAtlas);
	    particlesEffectAdditiveHypnose.setEmittersCleanUpBlendFunction(false); 	//Stop the additive effect resting the blend state 
	    particlesEffectAdditiveHypnose.scaleEffect(0.02f);
	    particlesEffectAdditiveHypnosePool = new ParticleEffectPool(particlesEffectAdditiveHypnose, 1, 10);
	    
	    particlesEffectAdditiveFire = new ParticleEffect();
	    particlesEffectAdditiveFire.load(Gdx.files.internal("effects/fire.p"), gameAtlas);
	    particlesEffectAdditiveFire.setEmittersCleanUpBlendFunction(false); 	//Stop the additive effect resting the blend state 
	    particlesEffectAdditiveFire.scaleEffect(0.01f);
	    particlesEffectAdditiveFire.setPosition(2, 4);
	    particlesEffectAdditiveFirePool = new ParticleEffectPool(particlesEffectAdditiveFire, 1, 10);
	    
	    particlesEffectAdditiveSteam = new ParticleEffect();
	    particlesEffectAdditiveSteam.load(Gdx.files.internal("effects/steam.p"), gameAtlas);
	    particlesEffectAdditiveSteam.setEmittersCleanUpBlendFunction(false); 	//Stop the additive effect resting the blend state 
	    particlesEffectAdditiveSteam.scaleEffect(0.01f);
	    particlesEffectAdditiveSteamPool = new ParticleEffectPool(particlesEffectAdditiveSteam, 1, 10);
	    
	    particlesEffectNormalWoodenFlindersRegion = new TextureRegion(items, 768, 1120, 32, 32);;
	    gameAtlas.addRegion("woodenFlinders", particlesEffectNormalWoodenFlindersRegion);
	    particlesEffectNormalWoodenFlinders = new ParticleEffect();
	    particlesEffectNormalWoodenFlinders.load(Gdx.files.internal("effects/woodenFlinders.p"), gameAtlas);
	    particlesEffectNormalWoodenFlindersPool = new ParticleEffectPool(particlesEffectNormalWoodenFlinders, 1, 10);
	    
	    particlesEffectNormalRockShardsRegion = new TextureRegion(items, 800, 1088, 32, 32);;
	    gameAtlas.addRegion("rockShards", particlesEffectNormalRockShardsRegion);
	    particlesEffectNormalRockShards = new ParticleEffect();
	    particlesEffectNormalRockShards.load(Gdx.files.internal("effects/rockShards.p"), gameAtlas);
	    particlesEffectNormalRockShardsPool = new ParticleEffectPool(particlesEffectNormalRockShards, 1, 10);
	    
	    particlesEffectNormalIceShardsRegion = new TextureRegion(items, 800, 1120, 32, 32);;
	    gameAtlas.addRegion("iceShards", particlesEffectNormalIceShardsRegion);
	    particlesEffectNormalIceShards = new ParticleEffect();
	    particlesEffectNormalIceShards.load(Gdx.files.internal("effects/iceShards.p"), gameAtlas);
	    particlesEffectNormalIceShardsPool = new ParticleEffectPool(particlesEffectNormalIceShards, 1, 10);
	    
	    particlesEffectNormalWaterBulbsRegion = new TextureRegion(items, 832, 1024, 32, 32);
	    gameAtlas.addRegion("waterBulbs", particlesEffectNormalWaterBulbsRegion);
	    particlesEffectNormalWaterBulbs = new ParticleEffect();
	    particlesEffectNormalWaterBulbs.load(Gdx.files.internal("effects/waterBulb.p"), gameAtlas);
	    particlesEffectNormalWaterBulbs.scaleEffect(Statics.Render.PARTICLE_EFFECTS_WATER_BULBS_SCALE);
	    particlesEffectNormalWaterBulbsPool = new ParticleEffectPool(particlesEffectNormalWaterBulbs, 1, 10);	        
	    
	    
	    //music =  Gdx.audio.newMusic(FileHandle.tempFile("music.mp3"));		   
	   // music.setLooping(true);
	   // music.setVolume(0.5f);
	    //if(Settings.soundEnabled)
	    //    music.play();
	        
	    //click = Gdx.audio.newSound(FileHandle.tempFile("click.wav"));		
    }
    
    public static void reload() {
        //background.reload();
        //items.reload();
        //if(Settings.soundEnabled)
          //  music.play();
        }
    
    public static void playSound(Sound sound) {
        //if(Settings.soundEnabled)
         //   sound.play(1);
	        }
}
