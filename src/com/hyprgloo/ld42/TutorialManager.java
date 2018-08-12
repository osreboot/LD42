package com.hyprgloo.ld42;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawLine;
import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawQuad;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

public class TutorialManager {

	public static void emphasize(float x, float y, float xl, float yl){
		hvlDrawQuad(0, 0, x - (xl/2), Display.getHeight(), new Color(0f, 0f, 0f, 0.5f));
		hvlDrawQuad(x - (xl/2), 0, xl, y - (yl/2), new Color(0f, 0f, 0f, 0.5f));
		hvlDrawQuad(x - (xl/2), y + (yl/2), xl, Display.getHeight() - (y + (yl/2)), new Color(0f, 0f, 0f, 0.5f));
		hvlDrawQuad(x + (xl/2), 0, Display.getWidth() - (x + (xl/2)), Display.getHeight(), new Color(0f, 0f, 0f, 0.5f));
		hvlDrawLine(x - (xl/2), y - (yl/2), x + (xl/2), y - (yl/2), Color.red, 2f);
		hvlDrawLine(x - (xl/2), y - (yl/2), x - (xl/2), y + (yl/2), Color.red, 2f);
		hvlDrawLine(x + (xl/2), y - (yl/2), x + (xl/2), y + (yl/2), Color.red, 2f);
		hvlDrawLine(x - (xl/2), y + (yl/2), x + (xl/2), y + (yl/2), Color.red, 2f);
	}
	
}
