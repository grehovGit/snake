package com.example.framework.model;

import com.kingsnake.math.EllipseTrajectory;
import com.kingsnake.math.Vector2;

public class StaticEffectImpactStars extends StaticEffect {
	
	 EllipseTrajectory ellipse;
	 Vector2 offset;
	
	 public StaticEffectImpactStars(float x, float y, float width, float height, float angle, float lifeTime, GameObject gObj) 
	 {
	 		super(x, y, width, height, Statics.StaticEffect.IMPACT_STARS, angle, lifeTime) ;
	 
	 		offset = new Vector2(0, STAR_ELLIPSE_HEIGHT_DISTANCE);
	 		master = (DynamicGameObject)gObj;	 		
	 		
	 		if(type == Statics.StaticEffect.IMPACT_STARS)
	 		{		 			
	 			int ang = (int) angle;		 			
	 			
	 			if(ang > 270 || ang < 90)
	 			{
	 				offset.rotate(angle);
	 				offset.x += x;
	 				offset.y += y;
	 			}
	 			else if(ang > 90 && ang < 270)
	 			{
	 				offset.rotate(angle - 180);
	 				offset.x += x;
	 				offset.y += y;
	 			}
	 			else if(ang == 90)
	 			{
			 		this.angle = 0;
	 				offset.x += x;
	 				offset.y += y;
	 			}
	 			else					//270
	 			{
			 		this.angle = 0;
	 				offset.x += x;
	 				offset.y += y;
	 			}
	 		
	 			ellipse = new EllipseTrajectory(offset.x, offset.y, width * STAR_ELLIPSE_WIDTH_PROPORTION, width * STAR_ELLIPSE_HEIGHT_PROPORTION, this.angle);
	 			
	 			ellipse.PlacePoint(0, 1);
	 			ellipse.PlacePoint(0.33f, 1);
	 			ellipse.PlacePoint(0.66f, 1);
	 		}
	 }
	 
	 public void setImpactStarsPos(float x, float y) 
	 {

		 	offset.set(0, STAR_ELLIPSE_HEIGHT_DISTANCE);
	 			
		 	int ang = (int) angle;		 			
		 			
		 	if(ang > 270 || ang < 90)
		 	{
		 		offset.rotate(angle);
		 		offset.x += x;
		 		offset.y += y;
		 	}
		 	else if(ang > 90 && ang < 270)
		 	{
		 		offset.rotate(angle - 180);
		 		offset.x += x;
		 		offset.y += y;
		 	}
		 	else if(ang == 90)
		 	{
				this.angle = 0;
		 		offset.x += x;
		 		offset.y += y;
		 	}
		 	else					//270
		 	{
				 this.angle = 0;
		 		offset.x += x;
		 		offset.y += y;
		 	}
		 		
		 	ellipse.setPos(offset.x, offset.y);	
	 }
	 
     public void update(float deltaTime) {

         super.update(deltaTime);
         
         if(master != null)
        	 setImpactStarsPos(master.position.x, master.position.y);      	 
     }
     
     public Vector2 GetImpactStar(int index) {

         if(this.type != Statics.StaticEffect.IMPACT_STARS || this.ellipse == null)
        	 return null;
         
         return ellipse.GetPointPos(index, actTime);
     }
     
     public int GetImpactStarsNumber() {

         if(this.type != Statics.StaticEffect.IMPACT_STARS || this.ellipse == null || this.ellipse.points.size() < 1)
        	 return 0;
         
         return this.ellipse.points.size();
     }
     
	 

}
