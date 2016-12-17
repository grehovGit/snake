package com.kingsnake.math;

import java.security.cert.CRLException;

import com.badlogic.gdx.math.Vector2;
import com.example.framework.model.Gifts;

public class MyMath {
	
	public Vector2 getLineCircleIntersectionPoint(float r, float a, float b, float c, float xCur, float yCur, Vector2 dir, boolean left)
	{
		Vector2 point = null;
		float EPS = (float) 1e-3;
		
		float x0 = - a * c / ( a * a + b * b),  y0 = - b * c / (a * a + b * b);
		if (c * c > r * r * (a * a + b * b) + EPS)
			point = null;
		else if (Math.abs(c * c - r * r * (a * a + b * b)) < EPS) 
		{
			point = new Vector2();
			point.x = x0;
			point.y = y0;
		}
		else 
		{
			float d = r * r - c * c / (a * a + b * b );
			float mult = (float) Math.sqrt(d / (a*a+b*b));
			float ax,ay,bx,by;
			ax = x0 + b * mult;
			bx = x0 - b * mult;
			ay = y0 - a * mult;
			by = y0 + a * mult;	
			
			point = new Vector2();
			float deltaAngleA = point.set(ax, ay).angle(dir);
			float deltaAngleB = point.set(bx, by).angle(dir);

			
			if(left)
			{
				if(deltaAngleA <= 0)
				{
					point.x = ax;
					point.y = ay;
				}
				else if(deltaAngleB < 0)
				{
					point.x = bx;
					point.y = by;
				}
			}
			else
			{
				if(deltaAngleA >= 0)
				{
					point.x = ax;
					point.y = ay;
				}
				else if(deltaAngleB > 0)
				{
					point.x = bx;
					point.y = by;
				}				
			}
		}
		
		return point;
	}
	
	public static float getRisingBulbApperianceBasedOnSin(float curTime, float period, float deltaScale)
	{
		if(curTime > period)
			return 1;
		else
		{
			float curScale = (float) (Math.sin(Math.PI * curTime / period) * (1 + deltaScale));
			
			if(curTime > period / 2 && curScale <= 1)
				return 1;
			else
				return curScale;		
		}
	}
	
	/**
	 * scaling given verts coords by scale factor. Mutates verts parameter
	 * @param verts requires: not empty even sized array
	 * @param scale requires: >= 0
	 * @return scaled verts list
	 * @throws  ArithmeticException if the result overflows a float
	 */
	public static float[] scaleVertices (float[] verts, float scale) throws ArithmeticException {
		assert verts.length > 1 : "length should be > 1";
		assert verts.length % 2 == 0 : "length should be even";
		assert scale >= 0 : "scale should be >= 0";
		
		for (int i=0; i<verts.length; i++) {
			verts[i] *= scale;
			if (verts[i] == Float.POSITIVE_INFINITY || verts[i] == Float.NEGATIVE_INFINITY)
				throw new ArithmeticException();
		}
		return verts;
	}
	
	/**
	 * produces 2-stage sines heart-beaten scaling (normal, excited) for rendering,  managed by timing args and stages
	 * @param curTime 												>=0
	 * @param endTime 												>=0
	 * @param exPeriod excited period before endTime 				>=0
	 * @param beatPeriod - exactly beat period 						>=0
	 * @param normalBeatPeriod - beaten period during normal period	>= 2 * beatPeriod
	 * @param exBeatPeriod - beaten period during excited period 	>= 2 * beatPeriod
	 * @param ampDelta max delta from original scale (1)			>=0
	 * @return current scale by current stage. After endTime starts normal stage. If current beatPeriod == 0, = original scale
	 */
	public static float getTwoStagedHurtBittenScale(final float curTime, final float endTime, final float exPeriod,
			final float beatPeriod, final float normalBeatPeriod, final float exBeatPeriod, final float ampDelta, boolean isSin) {
		assert curTime >= 0;
		assert endTime >= 0;
		assert exPeriod >= 0;
		assert beatPeriod >= 0;
		assert normalBeatPeriod >= 2 * beatPeriod;
		assert exBeatPeriod >= 2 * beatPeriod;
		assert ampDelta >= 0;
		
		final float curPeriod = curTime > endTime || curTime < endTime - exPeriod ? normalBeatPeriod : exBeatPeriod;		
		if (curPeriod == 0)
			return 0;		
		final float curPeriodTime = Math.abs(endTime - curTime) % curPeriod; 		
		if (curPeriodTime > 2 * beatPeriod)
			return 0;	
		
		if (isSin)
			return ampDelta * (float)Math.sin(2 * Math.PI * curPeriodTime / beatPeriod);	
		else
			return ampDelta * (float)Math.cos(2 * Math.PI * curPeriodTime / beatPeriod);
	};

}
