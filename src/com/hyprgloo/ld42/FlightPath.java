package com.hyprgloo.ld42;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawLine;
import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawQuadc;

import java.util.ArrayList;
import java.util.Arrays;

import org.newdawn.slick.Color;

import com.osreboot.ridhvl.HvlCoord2D;
import com.osreboot.ridhvl.painter.HvlCursor;

public class FlightPath {

	public static ArrayList<FlightPath> paths = new ArrayList<>();

	public static void restart(){
		paths.clear();
		if(Game.selected_level == 3){
			paths.add(new FlightPath("C3-Light", new HvlCoord2D(500, 500 + 48), 
					new PathNode(new HvlCoord2D(500, 500), false), 
					new PathNode(new HvlCoord2D(550, 500), true), 
					new PathNode(new HvlCoord2D(600, 500), true), 
					new PathNode(new HvlCoord2D(650, 500), true), 
					new PathNode(new HvlCoord2D(700, 500), true), 
					new PathNode(new HvlCoord2D(750, 500), false), 
					new PathNode(new HvlCoord2D(750, 450), false)));
			paths.add(new FlightPath("C1-Heavy", new HvlCoord2D(400, 200 + 48), 
					new PathNode(new HvlCoord2D(400, 200), false), 
					new PathNode(new HvlCoord2D(500, 200), true), 
					new PathNode(new HvlCoord2D(600, 200), true), 
					new PathNode(new HvlCoord2D(700, 200), true), 
					new PathNode(new HvlCoord2D(800, 200), false), 
					new PathNode(new HvlCoord2D(800, 300), false)));
			paths.add(new FlightPath("C2-Medium", new HvlCoord2D(225, 360 - 48), 
					new PathNode(new HvlCoord2D(225, 360), false), 
					new PathNode(new HvlCoord2D(300, 360), true),
					new PathNode(new HvlCoord2D(375, 360), true),
					new PathNode(new HvlCoord2D(450, 360), false)));
		}
	}

	public static void update(float delta){
		for(FlightPath p : paths) p.draw(delta);
	}

	public static final float BOX_WIDTH = 128f, BOX_HEIGHT = 64f;

	public String name;
	public ArrayList<PathNode> path;
	public HvlCoord2D boxLocation;

	public FlightPath(String nameArg, HvlCoord2D boxLocationArg, PathNode... pathArg){
		name = nameArg;
		boxLocation = boxLocationArg;
		path = new ArrayList<>(Arrays.asList(pathArg));
	}

	public void draw(float delta){
		Color color = isMouseHovering() ? Color.green : new Color(0f, 0.3f, 0f);
		hvlDrawQuadc(boxLocation.x, boxLocation.y, BOX_WIDTH + 2, BOX_HEIGHT + 2, color);
		hvlDrawQuadc(boxLocation.x, boxLocation.y, BOX_WIDTH, BOX_HEIGHT, Color.black);
		Main.font.drawWordc(name, boxLocation.x, boxLocation.y, color, 0.125f);
		HvlCoord2D current = path.get(0).c;
		for(int i = 1; i < path.size(); i++){
			hvlDrawLine(current.x, current.y, path.get(i).c.x, path.get(i).c.y, color);
			current = path.get(i).c;
		}
	}

	public boolean isMouseHovering(){
		return HvlCursor.getCursorX() > boxLocation.x - (BOX_WIDTH/2) && HvlCursor.getCursorX() < boxLocation.x + (BOX_WIDTH/2) &&
				HvlCursor.getCursorY() > boxLocation.y - (BOX_HEIGHT/2) && HvlCursor.getCursorY() < boxLocation.y + (BOX_HEIGHT/2);
	}

	public static class PathNode{

		public HvlCoord2D c;
		public boolean buffer = false;

		public PathNode(HvlCoord2D cArg, boolean bufferArg){
			c = cArg;
			buffer = bufferArg;
		}

	}

}
