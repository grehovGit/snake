package com.example.framework.utils;

import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap.Entry;
import com.example.framework.model.GameObject;

public class SortUtils {
	
	static Array<Entry<Float, Object>> sortArray;
	static List <Entry <Float, Entry<String, GameObject>>> sortListFloatStringGameObj;
	static List sortList;
	
	
	SortUtils()
	{
		sortArray = null;
		sortListFloatStringGameObj = null;
		sortList = null;
	}
	
	public static void quickSortList(List list)
	{
		sortList = list;
        int startIndex = 0;
        int endIndex = sortList.size() - 1;
        doSortList(startIndex, endIndex);
        sortList = null;
	}
	
    private static void doSortList(int start, int end) {
        if (start >= end)
            return;
        int i = start, j = end;
        int cur = i - (i - j) / 2;
        while (i < j) {
            while (i < cur && (((Entry<Object, Object>)sortList.get(i)).key.hashCode() <= ((Entry<Object, Object>)sortList.get(cur)).key.hashCode())) {
                i++;
            }
            while (j > cur && (((Entry<Object, Object>)sortList.get(cur)).key.hashCode() <= ((Entry<Object, Object>)sortList.get(j)).key.hashCode())) {
                j--;
            }
            if (i < j) {
            	Entry<Object, Object> temp = (Entry<Object, Object>) sortList.get(i);
            	sortList.set(i, sortList.get(j));
            	sortList.set(j, temp);
                if (i == cur)
                    cur = j;
                else if (j == cur)
                    cur = i;
            }
        }
        doSortList(start, cur);
        doSortList(cur+1, end);
    }
	
	public static void quickSortFloatObjArray(Array<Entry<Float, Object>> array)
	{
		sortArray = array;
        int startIndex = 0;
        int endIndex = sortArray.size - 1;
        doSort(startIndex, endIndex);
        sortArray = null;
	}
	
    private static void doSort(int start, int end) {
        if (start >= end)
            return;
        int i = start, j = end;
        int cur = i - (i - j) / 2;
        while (i < j) {
            while (i < cur && (sortArray.get(i).key <= sortArray.get(cur).key)) {
                i++;
            }
            while (j > cur && (sortArray.get(cur).key <= sortArray.get(j).key)) {
                j--;
            }
            if (i < j) {
            	Entry<Float, Object> temp = sortArray.get(i);
            	sortArray.set(i, sortArray.get(j));
            	sortArray.set(j, temp);
                if (i == cur)
                    cur = j;
                else if (j == cur)
                    cur = i;
            }
        }
        doSort(start, cur);
        doSort(cur+1, end);
    }
    
    
    
	public static void quickSortFloatStringObjectList(List <Entry <Float, Entry<String, GameObject>>> list)
	{
		sortListFloatStringGameObj = list;
        int startIndex = 0;
        int endIndex = sortListFloatStringGameObj.size() - 1;
        doSortListFloatStringGameObj(startIndex, endIndex);
        sortListFloatStringGameObj = null;
	}
	
    private static void doSortListFloatStringGameObj(int start, int end) {
        if (start >= end)
            return;
        int i = start, j = end;
        int cur = i - (i - j) / 2;
        while (i < j) {
            while (i < cur && (sortListFloatStringGameObj.get(i).key <= sortListFloatStringGameObj.get(cur).key)) {
                i++;
            }
            while (j > cur && (sortListFloatStringGameObj.get(cur).key <= sortListFloatStringGameObj.get(j).key)) {
                j--;
            }
            if (i < j) {
            	Entry <Float, Entry<String, GameObject>> temp = sortListFloatStringGameObj.get(i);
            	sortListFloatStringGameObj.set(i, sortListFloatStringGameObj.get(j));
            	sortListFloatStringGameObj.set(j, temp);
                if (i == cur)
                    cur = j;
                else if (j == cur)
                    cur = i;
            }
        }
        doSortListFloatStringGameObj(start, cur);
        doSortListFloatStringGameObj(cur+1, end);
    }
    
    public static boolean overlapBodies(Body a, Body b)
    {
    	Iterator <Fixture> iteratorA = a.getFixtureList().iterator();
 
    	boolean overlapFlag = false;
    	    	
    	while(iteratorA.hasNext())
    	{
    		Fixture fixA = iteratorA.next();
    		
    		if(fixA.isSensor())
    			continue;
    		
    		Shape shapeA = fixA.getShape();
    		Shape.Type shapeTypeA = shapeA.getType();
        	Iterator <Fixture> iteratorB = b.getFixtureList().iterator();
    		
    		while(iteratorB.hasNext())
    		{
    			Fixture fixB = iteratorB.next();
    			
    			if(fixB.isSensor())
    				continue;
    			
        		Shape shapeB = fixB.getShape();
        		Shape.Type shapeTypeB = shapeB.getType();  
        		
        		if(shapeTypeA == Shape.Type.Polygon && shapeTypeB == Shape.Type.Polygon)
        			overlapFlag = Intersector.overlapConvexPolygons(getPolygonShapeVerts((PolygonShape) shapeA, a), getPolygonShapeVerts((PolygonShape) shapeB, b), null);
        		else if(shapeTypeA == Shape.Type.Circle && shapeTypeB == Shape.Type.Polygon)
        			overlapFlag = Intersector.overlapConvexPolygons(getPolygonShapeVertsFromCircleShape((CircleShape) shapeA, a), getPolygonShapeVerts((PolygonShape) shapeB, b), null);
        		else if(shapeTypeA == Shape.Type.Polygon && shapeTypeB == Shape.Type.Circle)
        			overlapFlag = Intersector.overlapConvexPolygons(getPolygonShapeVerts((PolygonShape) shapeA, a), getPolygonShapeVertsFromCircleShape((CircleShape) shapeB, b), null);

        		if(overlapFlag)
        			return true;
    		}
    	}

    	return overlapFlag;
    }
    
    public static boolean overlapFixtures(Fixture fixA, Fixture fixB)
    {
		boolean overlapFlag = false;
		
		Shape shapeA = fixA.getShape();
		Shape.Type shapeTypeA = shapeA.getType();
		Body a = fixA.getBody();
		 			
		Shape shapeB = fixB.getShape();
		Shape.Type shapeTypeB = shapeB.getType();
		Body b = fixB.getBody();
		
		if(shapeTypeA == Shape.Type.Polygon && shapeTypeB == Shape.Type.Polygon)
			overlapFlag = Intersector.overlapConvexPolygons(getPolygonShapeVerts((PolygonShape) shapeA, a), getPolygonShapeVerts((PolygonShape) shapeB, b), null);
		else if(shapeTypeA == Shape.Type.Circle && shapeTypeB == Shape.Type.Polygon)
			overlapFlag = Intersector.overlapConvexPolygons(getPolygonShapeVertsFromCircleShape((CircleShape) shapeA, a), getPolygonShapeVerts((PolygonShape) shapeB, b), null);
		else if(shapeTypeA == Shape.Type.Polygon && shapeTypeB == Shape.Type.Circle)
			overlapFlag = Intersector.overlapConvexPolygons(getPolygonShapeVerts((PolygonShape) shapeA, a), getPolygonShapeVertsFromCircleShape((CircleShape) shapeB, b), null);

		return overlapFlag;
    }
    
    static float[] getPolygonShapeVerts(PolygonShape shape, Body body)
    {
    	int vertsCount = shape.getVertexCount();
    	float[] verts = null;  
    	
    	if(vertsCount > 0)
    	{
    		verts = new float[2 * vertsCount];
    		Vector2 vertex = new Vector2();
    		
    		for(int i = 0; i < vertsCount; ++i)
    		{
    			shape.getVertex(i, vertex);
    			vertex.set(body.getWorldPoint(vertex));
    			verts[2 * i] = vertex.x;
    			verts[2 * i + 1] = vertex.y;    			
    		}    			
    	}   	
    	return verts;
    }
    
    static Circle getCircleFromCircleShape(CircleShape shape, Body body)
    {
    	Vector2 centerPos = shape.getPosition();
    	centerPos.set(body.getWorldPoint(centerPos));
    	Circle circle = new Circle(centerPos, shape.getRadius());
    	return circle;
    }
    
    static float[] getPolygonShapeVertsFromCircleShape(CircleShape shape, Body body)
    {  	
    	Vector2 centerPos = shape.getPosition();
    	centerPos.set(body.getWorldPoint(centerPos));
    	float radius = shape.getRadius();
    	
		float [] verts = new float[2 * 4];

		verts[0] = centerPos.x - radius;
		verts[1] = centerPos.y - radius; 
		
		verts[2] = centerPos.x + radius;
		verts[3] = centerPos.y - radius; 
		
		verts[4] = centerPos.x + radius;
		verts[5] = centerPos.y + radius; 
		
		verts[6] = centerPos.x - radius;
		verts[7] = centerPos.y + radius; 
		
	
		

   				
		return verts;
    }
    

}
