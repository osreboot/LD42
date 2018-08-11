package com.hyprgloo.ld42;

import com.hyprgloo.ld42.ships.HeavyMerchant;
import com.hyprgloo.ld42.ships.LightMerchant;

public class Game {

	public static int selected_level = -1;
	
	public static float level_fuel, level_energy, level_ammo;
	
	public static void restart(){
		Ship.ships.clear();
		level_fuel = 1f;
		level_energy = 1f;
		level_ammo = 1f;
		
		new LightMerchant(0, 0, 0, 0, 0, Cargo.FUEL);
		new HeavyMerchant(0, 0, 0, 0, 0, Cargo.FUEL);
	}
	
	public static void update(float delta){
		Ship.updateShips(delta);
		ShipSelector.update(delta);
		FancyOverlay.drawGameLevels(delta);
	}
	
}
