package com.hyprgloo.ld42;

public class Game {

	public static void restart(){
		Ship.ships.clear();
	}
	
	public static void update(float delta){
		Ship.updateShips(delta);
	}
	
}
