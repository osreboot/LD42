package com.hyprgloo.ld42;

import java.util.ArrayList;

import com.hyprgloo.ld42.ships.ShipMerchantLight;

public class LevelShipSequencer {
	public static final int smallShipTimer = 10;
	public static final int mediumShipTimer = 10;
	public static final int largeShipTimer = 10;
	public static boolean spawnedTutorial = false;
	
	public static ArrayList<SpaceStationPart> raiderCompParts = new ArrayList<>();
	
	public static void spawnRaider() {
		for(SpaceStationPart allParts : SpaceStation.stationParts) {
			if(allParts.raiderCompatible) {
				raiderCompParts.add(allParts);
			}
		}
		
	}
	
	public static void updateLevels(float delta) {
		
		if(Game.selected_level == 0) {
			//TODO tutorial????
			if(!spawnedTutorial) {
				new ShipMerchantLight(-128, 250, 64, 250, 0, Cargo.FUEL);
				new ShipMerchantLight(-500, 500, 64, 500 , 0, Cargo.ENERGY);
				spawnedTutorial = true;
			}

		} else if(Game.selected_level == 1){
			
		} else if(Game.selected_level == 2) {
			
		} else if(Game.selected_level == 3) {
			
		}
	}
	
}
