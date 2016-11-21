package com.kingsnake.math;

import java.util.ArrayList;
import java.util.List;

public class EllipseTrajectory {
	
	 public final Vector2 center;
	    public float width, height, angle;
	    public List <Vector2> points;
	    Vector2 sectionRadius;
	    
	    public EllipseTrajectory(float x, float y, float width, float height, float angle) {
	        this.center = new Vector2(x,y);
	        this.width = width;
	        this.height = height;
	        this.angle = angle;
	        this.sectionRadius = new Vector2();
	        
	        
	        points = new ArrayList<Vector2>();
	        
	    }
	    
	    
	    public int PlacePoint(float percentPeriod, float speed)		//percentPeriod: 0 - 1;
	    {
	    	if (percentPeriod > 1)
	    		percentPeriod = percentPeriod % 1;
	    	
	    	points.add(new Vector2((float) (2 * Math.PI * percentPeriod), speed));
	    	
	    	return points.size() - 1;
	    }
	    
	    public Vector2 GetPointPos(int index, float time)
	    {
	    	if (index < 0 || index > points.size() - 1)
	    		return null;
	    	
	    	Vector2 point = points.get(index);
	    	float x = (float) ((this.width / 2) * Math.cos(2* Math.PI * time * point.y + point.x)) + this.center.x; 
	    	float y = (float) ((this.height / 2) * Math.sin(2* Math.PI * time * point.y + point.x)) + this.center.y;
	    	
	    	sectionRadius.x = x - center.x;
	    	sectionRadius.y = y - center.y;
	    	
	    	sectionRadius.rotate(this.angle);
	    	
	    	sectionRadius.x -= x - center.x;
	    	sectionRadius.y -= y - center.y;;
	    	
	    	sectionRadius.x = x + sectionRadius.x;
	    	sectionRadius.y = y + sectionRadius.y;
	    	
	    	return sectionRadius;
	    }
	    
	    public void setPos(float x, float y)
	    {
	        center.set(x, y);	    	
	    }
	  

}
