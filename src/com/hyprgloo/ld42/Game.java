package com.hyprgloo.ld42;

import com.hyprgloo.ld42.ships.ShipMerchantMedium;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import com.hyprgloo.ld42.ships.ShipMerchantLight;

public class Game {

	public static int selected_level = -1, collisions = 0;
	
	public static float level_fuel, level_energy, level_ammo;
	
	public static void restart(){
		Ship.ships.clear();
		level_fuel = 0f;
		level_energy = 1f;
		level_ammo = 1f;
		
		collisions = 1;
		
		new ShipMerchantMedium(-128, 256, 64, 192, 0, Cargo.FUEL);
		
		new ShipMerchantLight(-128, 256, 64, 256 + 32, 0, Cargo.FUEL);
		new ShipMerchantLight(-128, 256, 64, 256 + 64, 0, Cargo.ENERGY);
		new ShipMerchantLight(-128, 256, 64, 256 + 96, 0, Cargo.AMMO);
		
		SpaceStation.restart();
	}
	
	public static void update(float delta){
		Ship.updateShips(delta);
		ShipSelector.update(delta);
		FancyOverlay.drawGameLevels(delta);
		SpaceStation.update(delta);
		if(collisions > 0) Main.font.drawWord("collisions: " + collisions, 8f, Display.getHeight() - 24f, Color.white, 0.125f);
	}
	
}
