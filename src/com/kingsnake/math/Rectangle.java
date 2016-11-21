package com.kingsnake.math;

public class Rectangle {
	
	 public Vector2 lowerLeft;
	    public float width, height;
	    public Rectangle(float x, float y, float width, float height) {
	        this.lowerLeft = new Vector2(x,y);
	        this.width = width;
	        this.height = height;
	    }
	    
	    public void set(float x, float y, float width, float height) {
	        this.lowerLeft = new Vector2(x,y);
	        this.width = width;
	        this.height = height;
	    }

}
