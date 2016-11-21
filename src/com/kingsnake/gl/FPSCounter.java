package com.kingsnake.gl;

import android.util.Log;

public class FPSCounter {
	
	long startTime = System.nanoTime();
    int frames = 0;
    public void logFrame() {
        frames++;
        if(System.nanoTime() - startTime >= 1000000000) {
            Log.d("FPSCounter", "fps: " + frames);
            frames = 0;
            startTime = System. nanoTime();
        }
    }
    
    public void logFrame(String str) {
        frames++;
        if(System.nanoTime() - startTime >= 1000000000) {
            Log.d("FPSCounter", "fps: " + frames + "note: " + str);
            frames = 0;
            startTime = System. nanoTime();
        }
    }

}
