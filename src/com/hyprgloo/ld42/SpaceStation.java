package com.hyprgloo.ld42;

import java.util.ArrayList;

public class SpaceStation {

	public static final float GRID_SIZE = 24f;
	
	public static ArrayList<SpaceStation> stationGrid = new ArrayList<>();
	
	public float x, y, rotation;
	public int textureIndex;
	
	public SpaceStation(float xArg, float yArg, float rotationArg, int textureIndexArg){
		x = xArg;
		y = yArg;
		rotation = rotationArg;
		textureIndex = textureIndexArg;
		stationGrid.add(this);
	}
	
	public static void restart(){
		
	}
	
	public static void update(float delta){
		
	}
	
}
