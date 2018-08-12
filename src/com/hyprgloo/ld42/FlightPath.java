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
		if(Game.selected_level == 1){
			paths.add(new FlightPath("A3-Light", new HvlCoord2D(500, 500 + 48), 
					new PathNode(new HvlCoord2D(500, 500), false), 
					new PathNode(new HvlCoord2D(550, 500), true), 
					new PathNode(new HvlCoord2D(600, 500), true), 
					new PathNode(new HvlCoord2D(650, 500), true), 
					new PathNode(new HvlCoord2D(700, 500), true), 
					new PathNode(new HvlCoord2D(750, 500), false), 
					new PathNode(new HvlCoord2D(750, 450), false)));
			paths.add(new FlightPath("A1-Light", new HvlCoord2D(350, 220 - 48), 
					new PathNode(new HvlCoord2D(350, 220), false), 
					new PathNode(new HvlCoord2D(400, 220), true), 
					new PathNode(new HvlCoord2D(450, 220), true), 
					new PathNode(new HvlCoord2D(500, 220), true), 
					new PathNode(new HvlCoord2D(550, 220), true), 
					new PathNode(new HvlCoord2D(600, 220), true), 
					new PathNode(new HvlCoord2D(650, 220), true), 
					new PathNode(new HvlCoord2D(700, 220), true), 
					new PathNode(new HvlCoord2D(750, 220), false), 
					new PathNode(new HvlCoord2D(750, 270), false)));
			paths.add(new FlightPath("A2-Heavy", new HvlCoord2D(150, 360 - 48), 
					new PathNode(new HvlCoord2D(150, 360), false), 
					new PathNode(new HvlCoord2D(250, 360), true),
					new PathNode(new HvlCoord2D(350, 360), true),
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
		Color color = isMouseHovering() ? Color.green : new Color(0f, 0.2f, 0f);
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