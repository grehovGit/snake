package com.kingsnake.gl;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MyAnimation extends Animation{
	
 	public static final int ANIMATION_LOOPING = 0;
    public static final int ANIMATION_NONLOOPING = 1;
	
    float frameDuration; 
    int frNumber = 0;
    int oldFrNumber = 0;
    int deltaFrNumber = 0;
    boolean finished = false;

	
    public MyAnimation(float frameDuration, TextureRegion ... keyFrames) 
    {	
		super(frameDuration, keyFrames);
		// TODO Auto-generated constructor stub
	   	 this.frameDuration = frameDuration;
     }
	
    //animation n frames from index
    //used when need every time sprite from start
    public TextureRegion getKeyFrame(float stateTime, int nFrames, int index) 
    {
        int frameNumber = (int)(stateTime / getFrameDuration());
        deltaFrNumber = frameNumber - oldFrNumber;
        oldFrNumber = frameNumber;


        frNumber += deltaFrNumber;

       
       if(frNumber >= nFrames - 1)
        	frNumber = 0;
        
        frameNumber = frNumber + index;	 
        
        if(frameNumber < 0 || index < 0)
        	frameNumber = -20;
        
        return getKeyFrames()[frameNumber];
    }
    
    //manage
    public TextureRegion getKeyFrame(float stateTime, int mode, int[] manager, int man_size) 
    {
        int frameNumber = (int)(stateTime / frameDuration);
        int manFrameNumber = 0;
        
        //manager [i] - index from 0; [i+1] - nFrames; [i+2] - FrameDuration in frames: 1,2,3...
        
        
        //count coded frames
        for(int i=0; i < man_size; i++)
        	manFrameNumber += manager[3*i+1] * manager[3*i +2];			     	        
        
        if(mode == ANIMATION_NONLOOPING) {
      	        	
            frameNumber = Math.min(manFrameNumber-1, frameNumber);	            
            
        } 
        else 
        	
        {	//get frame number in our coded sprite coords  [0 - (manFrameNumber-1)]      		        	
            frameNumber = frameNumber % manFrameNumber;
            manFrameNumber = 0;	
            int oldNumber = 0;	         
	        
	        int i = 0;
	        
	        //find current line in our code array
	        while(manFrameNumber < frameNumber && i < man_size)
	        {
	        	oldNumber = manFrameNumber;
	        	manFrameNumber += (manager[3 * i + 1] * manager[3 * i + 2]);	
	        	//manFrameNumber -= 1; this is error!!
	        	i++;
	        }
	        		       
	        
	        //get index from current line beginning
	        frameNumber = frameNumber - oldNumber;
	       		        
	        
	        if (i > 0)
	        	--i;
	        
	        //remember data from current line
	        int indSubFr = manager[i*3];
	        int nSubFr = manager[i*3 + 1];
	        int tSubFr = manager[i*3 + 2];
	        
	        //find frame index in pattern coords 
	        for(i = 0; i < nSubFr - 1 && frameNumber > 0; i++)
	        {
	        	for(int j=0; j < tSubFr - 1 && frameNumber > 0; j++)
	        		frameNumber --;
	        	
        		if (frameNumber < 1)
        			break;
	        }
	        
	        frameNumber = indSubFr + i;
	        
	        //Log.d("Inside ANimation", "Frame number = " + frameNumber + " time=" + stateTime);
	        		

        }

        return getKeyFrames()[frameNumber];
    }
    
    public TextureRegion getKeyFrame(float stateTime, int looping) 
    {
    	if(looping == ANIMATION_LOOPING)
    		return super.getKeyFrame(stateTime, true);
    	else
    		return super.getKeyFrame(stateTime, false);
    		
    }
}
