package com.hyprgloo.ld42;

import java.util.ArrayList;

import org.lwjgl.opengl.Display;

import com.hyprgloo.ld42.ships.ShipMerchantLarge;
import com.hyprgloo.ld42.ships.ShipMerchantLight;
import com.hyprgloo.ld42.ships.ShipMerchantMedium;
import com.hyprgloo.ld42.ships.ShipRaiderLight;
import com.osreboot.ridhvl.HvlMath;

public class LevelShipSequencer {
	
	public static final float SHIP_SPAWN_EDGE_SPACING = 128f, SHIP_SPAWN_EDGE_SPACING_MIN = 16f;
	
	public static final float SMALL_TIME = 6;
	public static final float MED_TIME = 12;
	public static final float LAR_TIME = 20;
	public static final float RAID_TIME = 40;

	
	static float smallShipTimer = 0;
	static float medShipTimer = 0;
	static float largeShipTimer = 0;
	static float raiderShipTimer = RAID_TIME * 1.5f;
	
	public static boolean spawnedTutorial = false;
	
	public static ArrayList<SpaceStationPart> raiderCompParts = new ArrayList<>();
	
	public static void reset(){
		smallShipTimer = 0;
		medShipTimer = 0;
		largeShipTimer = LAR_TIME;
		raiderShipTimer = RAID_TIME * 1.5f;
	}
	
	public static void spawnRaider() {
		for(SpaceStationPart allParts : SpaceStation.stationParts) {
			if(allParts.raiderCompatible) {
				raiderCompParts.add(allParts);
			}
		}
		int targetIndex = HvlMath.randomInt(raiderCompParts.size());
		
		float y;
		do{
			y = raiderCompParts.get(targetIndex).y <= 360 ? HvlMath.randomFloatBetween(SHIP_SPAWN_EDGE_SPACING_MIN, SHIP_SPAWN_EDGE_SPACING) : HvlMath.randomFloatBetween(Display.getHeight() - SHIP_SPAWN_EDGE_SPACING, Display.getHeight() - SHIP_SPAWN_EDGE_SPACING_MIN);
		}while(Ship.shipInProximity(-128, y, ShipRaiderLight.COLLISION_SIZE));
		
		if(raiderCompParts.get(targetIndex).y <= 360) {
			ShipRaiderLight SRL = new ShipRaiderLight(-128, y, raiderCompParts.get(targetIndex).x, SHIP_SPAWN_EDGE_SPACING, 0, raiderCompParts.get(targetIndex));
			SRL.setGoal(raiderCompParts.get(targetIndex).x, y);
		} else if (raiderCompParts.get(targetIndex).y > 360) {
			ShipRaiderLight SRL = new ShipRaiderLight(-128, y, raiderCompParts.get(targetIndex).x, Display.getHeight() - SHIP_SPAWN_EDGE_SPACING, 0, raiderCompParts.get(targetIndex));
			SRL.setGoal(raiderCompParts.get(targetIndex).x, y);

		}
	}

	public static void updateLevels(float delta) {
		smallShipTimer -= delta;
		medShipTimer -= delta;
		largeShipTimer -= delta;
		raiderShipTimer -= delta;
		if(Game.selected_level == 0) {
			if(!spawnedTutorial) {
				new ShipMerchantLight(-64, 250, 64, 250, 0, Cargo.FUEL);
				new ShipMerchantLight(-64, 500, 64, 500 , 0, Cargo.ENERGY);
				spawnedTutorial = true;
			}

		} else if(Game.selected_level == 1){
			if(smallShipTimer <= 0) {
				float startY;
				do{
					startY = HvlMath.randomInt(2) == 0 ? HvlMath.randomFloatBetween(SHIP_SPAWN_EDGE_SPACING_MIN, SHIP_SPAWN_EDGE_SPACING) : HvlMath.randomFloatBetween(Display.getHeight() - SHIP_SPAWN_EDGE_SPACING, Display.getHeight() - SHIP_SPAWN_EDGE_SPACING_MIN);
				}while(Ship.shipInProximity(-128, startY, ShipMerchantLight.COLLISION_SIZE));
				Cargo cargo = Cargo.EMPTY;
				int cargoSelect = HvlMath.randomInt(3);
				if(cargoSelect == 0) cargo = Cargo.FUEL;
				if(cargoSelect == 1) cargo = Cargo.ENERGY;
				if(cargoSelect == 2) cargo = Cargo.AMMO;
				new ShipMerchantLight(-128, startY , Display.getWidth() + 128, startY, 0, cargo);
				smallShipTimer = SMALL_TIME;
			}
			if(medShipTimer <= 0) {
				float startY;
				do{
					startY = HvlMath.randomInt(2) == 0 ? HvlMath.randomFloatBetween(SHIP_SPAWN_EDGE_SPACING_MIN, SHIP_SPAWN_EDGE_SPACING) : HvlMath.randomFloatBetween(Display.getHeight() - SHIP_SPAWN_EDGE_SPACING, Display.getHeight() - SHIP_SPAWN_EDGE_SPACING_MIN);
				}while(Ship.shipInProximity(-128, startY, ShipMerchantMedium.COLLISION_SIZE));
				Cargo cargo = Cargo.EMPTY;
				int cargoSelect = HvlMath.randomInt(3);
				if(cargoSelect == 0) cargo = Cargo.FUEL;
				if(cargoSelect == 1) cargo = Cargo.ENERGY;
				if(cargoSelect == 2) cargo = Cargo.AMMO;
				new ShipMerchantMedium(-128, startY , Display.getWidth() + 128, startY, 0, cargo);
				medShipTimer = MED_TIME;
			}
			
		} else if(Game.selected_level == 2) {
			if(smallShipTimer <= 0) {
				float startY;
				do{
					startY = HvlMath.randomInt(2) == 0 ? HvlMath.randomFloatBetween(SHIP_SPAWN_EDGE_SPACING_MIN, SHIP_SPAWN_EDGE_SPACING) : HvlMath.randomFloatBetween(Display.getHeight() - SHIP_SPAWN_EDGE_SPACING, Display.getHeight() - SHIP_SPAWN_EDGE_SPACING_MIN);
				}while(Ship.shipInProximity(-128, startY, ShipMerchantLight.COLLISION_SIZE));
				Cargo cargo = Cargo.EMPTY;
				int cargoSelect = HvlMath.randomInt(3);
				if(cargoSelect == 0) cargo = Cargo.FUEL;
				if(cargoSelect == 1) cargo = Cargo.ENERGY;
				if(cargoSelect == 2) cargo = Cargo.AMMO;
				new ShipMerchantLight(-128, startY , Display.getWidth() + 128, startY, 0, cargo);
				smallShipTimer = SMALL_TIME;
			}
			if(medShipTimer <= 0) {
				float startY;
				do{
					startY = HvlMath.randomInt(2) == 0 ? HvlMath.randomFloatBetween(SHIP_SPAWN_EDGE_SPACING_MIN, SHIP_SPAWN_EDGE_SPACING) : HvlMath.randomFloatBetween(Display.getHeight() - SHIP_SPAWN_EDGE_SPACING, Display.getHeight() - SHIP_SPAWN_EDGE_SPACING_MIN);
				}while(Ship.shipInProximity(-128, startY, ShipMerchantMedium.COLLISION_SIZE));
				Cargo cargo = Cargo.EMPTY;
				int cargoSelect = HvlMath.randomInt(3);
				if(cargoSelect == 0) cargo = Cargo.FUEL;
				if(cargoSelect == 1) cargo = Cargo.ENERGY;
				if(cargoSelect == 2) cargo = Cargo.AMMO;
				new ShipMerchantMedium(-128, startY , Display.getWidth() + 128, startY, 0, cargo);
				medShipTimer = MED_TIME;
			}
			if(raiderShipTimer <= 0) {

				spawnRaider();
				raiderShipTimer = RAID_TIME;
			}
		} else if(Game.selected_level == 3) {
			if(smallShipTimer <= 0) {
				float startY;
				do{
					startY = HvlMath.randomInt(2) == 0 ? HvlMath.randomFloatBetween(SHIP_SPAWN_EDGE_SPACING_MIN, SHIP_SPAWN_EDGE_SPACING) : HvlMath.randomFloatBetween(Display.getHeight() - SHIP_SPAWN_EDGE_SPACING, Display.getHeight() - SHIP_SPAWN_EDGE_SPACING_MIN);
				}while(Ship.shipInProximity(-128, startY, ShipMerchantLight.COLLISION_SIZE));
				Cargo cargo = Cargo.EMPTY;
				int cargoSelect = HvlMath.randomInt(3);
				if(cargoSelect == 0) cargo = Cargo.FUEL;
				if(cargoSelect == 1) cargo = Cargo.ENERGY;
				if(cargoSelect == 2) cargo = Cargo.AMMO;
				new ShipMerchantLight(-128, startY , Display.getWidth() + 128, startY, 0, cargo);
				smallShipTimer = SMALL_TIME;
			}
			if(medShipTimer <= 0) {
				float startY;
				do{
					startY = HvlMath.randomInt(2) == 0 ? HvlMath.randomFloatBetween(SHIP_SPAWN_EDGE_SPACING_MIN, SHIP_SPAWN_EDGE_SPACING) : HvlMath.randomFloatBetween(Display.getHeight() - SHIP_SPAWN_EDGE_SPACING, Display.getHeight() - SHIP_SPAWN_EDGE_SPACING_MIN);
				}while(Ship.shipInProximity(-128, startY, ShipMerchantMedium.COLLISION_SIZE));
				Cargo cargo = Cargo.EMPTY;
				int cargoSelect = HvlMath.randomInt(3);
				if(cargoSelect == 0) cargo = Cargo.FUEL;
				if(cargoSelect == 1) cargo = Cargo.ENERGY;
				if(cargoSelect == 2) cargo = Cargo.AMMO;
				new ShipMerchantMedium(-128, startY , Display.getWidth() + 128, startY, 0, cargo);
				medShipTimer = MED_TIME;
			}
			if(largeShipTimer <= 0) {
				float startY;
				do{
					startY = HvlMath.randomInt(2) == 0 ? HvlMath.randomFloatBetween(SHIP_SPAWN_EDGE_SPACING_MIN, SHIP_SPAWN_EDGE_SPACING) : HvlMath.randomFloatBetween(Display.getHeight() - SHIP_SPAWN_EDGE_SPACING, Display.getHeight() - SHIP_SPAWN_EDGE_SPACING_MIN);
				}while(Ship.shipInProximity(-128, startY, ShipMerchantLarge.COLLISION_SIZE));
				Cargo cargo = Cargo.EMPTY;
				int cargoSelect = HvlMath.randomInt(3);
				if(cargoSelect == 0) cargo = Cargo.FUEL;
				if(cargoSelect == 1) cargo = Cargo.ENERGY;
				if(cargoSelect == 2) cargo = Cargo.AMMO;
				new ShipMerchantLarge(-128, startY , Display.getWidth() + 128, startY, 0, cargo);
				largeShipTimer = LAR_TIME;
			}
			if(raiderShipTimer <= 0) {

				spawnRaider();
				raiderShipTimer = RAID_TIME;
			}
		}
	}
	
}
