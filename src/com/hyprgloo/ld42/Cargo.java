package com.hyprgloo.ld42;

public enum Cargo {
	FUEL(Main.INDEX_CANISTER_FUEL), ENERGY(Main.INDEX_CANISTER_ENERGY), AMMO(Main.INDEX_CANISTER_AMMO);
	
	public int texture;
	
	Cargo(int textureArg){
		texture = textureArg;
	}
}
