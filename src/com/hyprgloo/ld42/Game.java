package com.hyprgloo.ld42;

public class Game {

	public static int selected_level = -1;
	
	public static float level_fuel, level_energy, level_ammo;
	
	public static void restart(){
		Ship.ships.clear();
		level_fuel = 0f;
		level_energy = 1f;
		level_ammo = 1f;
	}
	
	
	public static void update(float delta){
		Ship.updateShips(delta);
		
		FancyOverlay.drawGameLevels(delta);
	}
	
}
