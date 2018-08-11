package com.hyprgloo.ld42.ships;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawQuadc;
import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlResetRotation;
import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlRotate;

import com.hyprgloo.ld42.Cargo;
import com.hyprgloo.ld42.Main;

public class ShipMerchantMedium extends ShipMerchant{

	public ShipMerchantMedium(float xArg, float yArg, float xGoalArg, float yGoalArg, float rotationArg, Cargo cargoArg){
		super(xArg, yArg, xGoalArg, yGoalArg, rotationArg, 30f, cargoArg, 15f, 180f);
	}

	@Override
	public void draw(float delta){
		hvlRotate(x, y, rotation - 90f);
		hvlDrawQuadc(x, y, 64f, 64f, Main.getTexture(Main.INDEX_CARGO_SHIP_MEDIUM));
		if(!docked){
			hvlDrawQuadc(x + 10f, y - 8f, 16f, 16f, Main.getTexture(cargo.texture));
			hvlDrawQuadc(x + 22f, y - 8f, 16f, 16f, Main.getTexture(cargo.texture));
		}
		hvlResetRotation();
		super.draw(delta);
	}

}
