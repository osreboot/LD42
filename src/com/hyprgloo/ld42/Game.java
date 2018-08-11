package com.hyprgloo.ld42;

import com.hyprgloo.ld42.ships.ShipMerchantMedium;
import com.hyprgloo.ld42.ships.ShipMerchantLight;

public class Game {

	public static int selected_level = -1;
	
	public static float level_fuel, level_energy, level_ammo;
	
	public static void restart(){
		Ship.ships.clear();
		level_fuel = 0f;
		level_energy = 1f;
		level_ammo = 1f;
		
		new ShipMerchantMedium(0, 0, 0, 0, 0, Cargo.FUEL);
		
		new ShipMerchantLight(0, 0, 0, 0, 0, Cargo.FUEL);
		new ShipMerchantLight(0, 0, 0, 0, 0, Cargo.ENERGY);
		new ShipMerchantLight(0, 0, 0, 0, 0, Cargo.AMMO);
		
		SpaceStation.restart();
	}
	
	public static void update(float delta){
		Ship.updateShips(delta);
		ShipSelector.update(delta);
		FancyOverlay.drawGameLevels(delta);
		SpaceStation.update(delta);
	}
	
}
