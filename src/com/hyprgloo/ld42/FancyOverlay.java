package com.hyprgloo.ld42;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawLine;

import org.lwjgl.opengl.Display;

import com.osreboot.ridhvl.HvlCoord2D;
import com.osreboot.ridhvl.HvlMath;
import com.osreboot.ridhvl.menu.component.collection.HvlLabeledButton;

public class FancyOverlay {

	public static final float MAIN_CROSSHAIR_SPEED = 1024f;
	
	private static HvlCoord2D mainCrosshair = new HvlCoord2D(), mainCrossHairGoal = new HvlCoord2D();
	
	public static void updateMain(float delta){
		mainCrosshair.x = HvlMath.stepTowards(mainCrosshair.x, delta * MAIN_CROSSHAIR_SPEED, mainCrossHairGoal.x);
		mainCrosshair.y = HvlMath.stepTowards(mainCrosshair.y, delta * MAIN_CROSSHAIR_SPEED, mainCrossHairGoal.y);
		
		hvlDrawLine(0, Display.getHeight()/2, 16f, Display.getHeight()/2, Main.COLOR_BLUE0);
		hvlDrawLine(16f, Display.getHeight()/2, 16f, mainCrosshair.y, Main.COLOR_BLUE0);
		
		mainCrossHairGoal = new HvlCoord2D();
	}
	
	public static void drawMainButton(float delta, HvlLabeledButton b){
		mainCrossHairGoal.x = b.getX();
		mainCrossHairGoal.y = b.getY() + (b.getHeight()/2);
	}
	
}
