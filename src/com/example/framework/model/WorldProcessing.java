package com.example.framework.model;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.kingsnake.math.Vector2;

public interface WorldProcessing {
	
	public boolean placeObjectToCell(int x, int y, int z, int type);
	public boolean placeObjectToGreedByCoords(float x, float y, int z, int type, int[] logCoords);
	public boolean placeSubObjectToGreedByCoords(float x, float y, int z, int type);
	public boolean deleteCellContent(int x, int y, int z);
	public boolean deleteObjectFromGreedByCoords(float _x, float _y, int z, int type);
	public boolean deleteSubObjectFromGreedByCoords(float x, float y, int z);
	public boolean isEmptyCell(int x, int y, int z);
	public boolean isEmptyCellByCoords(float x, float y, int z);
	public boolean isEmptyNeighbourCellByCoords(float x, float y, int z, int dir);
	public int getSubCellByCoords(float x, float y, int z);
	public void placeRelief();
	
	//public boolean dynObjMove(DynamicGameObject dynObj, Vector2 newPoint);
	public boolean dynObjGrounding(DynamicGameObject dynObj, Vector2 newPoint);
	public boolean moveObjectToGreedByCoords(float x, float y, int z, int type ,int[] logCoords);
	public DynamicGameObject getDynObj(int x, int y, int type);
	public DynamicGameObject getDynObjByCoords(float x, float y);
	public DynamicGameObject getDynObjByCoords(float x, float y, int type);
	public DynamicGameObject getFlyingObjByCoords(float x, float y);
	public GameObject getAnyObjByCoords(float x, float y);
	public GameObject getStaticObj(int x, int y);
	public void placeTreeBomb(DynamicGameObject dynObj);
	public float getReliefFF(int x, int y);
	public int getRelief(int x, int y);
	public float getDistanceToMyCellCenter(float x, float y);
	public void CharToCharAttack(DynamicGameObject dynObj, DynamicGameObject dObj);
	public void DynObjToReliefImpactProcess(DynamicGameObject dynObj, GameObject gObj, float angleImpact);
	public float getReliefImpactDamage(int type);
	public void DynObjToStatImpactProcess(DynamicGameObject dynObj, GameObject sObj, float angleImpact);
	public void DynObjImpactBasicProcess(DynamicGameObject dynObj, int hitFullPeriod, float healthDecrease, float angleImpact);
	public DynamicGameObject CreateDeadPart(float x, float y, float width, float height);
    public DynamicGameObject createDynObject(float x, float y, float width, float height, float angle, int objType, int level);
    public StaticEffect createStaticEffect(float x, float y, float width, float height, float angle, int type, float lifetime, int level, GameObject master);
    void processHypnoseCharacter(DynamicGameObject character, DynamicEffectHypnose hypnoseEffect);
    public void processObjectFlameReaction(GameObject gObj, final int levelFire, Fixture fixObj);
	
	public void provideMushroomAction(DynamicGameObject dynObj, int mushroom, int action);
	public void bloodProcess(DynamicGameObject dynObj);
	public boolean isCharFriend(DynamicGameObject dynObj, DynamicGameObject dObj);
	
	float getObstacleKoefForForwardAttack(DynamicGameObject dynObj, DynamicGameObject target);
	float getObstacleKoefForHook(DynamicGameObject dynObj, DynamicGameObject target, boolean isLeft);
	float getObstacleKoefForBackHook(DynamicGameObject dynObj, DynamicGameObject target, boolean isLeft);
	public float getObstacleKoefForBackAttack(DynamicGameObject dynObj, DynamicGameObject target);


	
}
