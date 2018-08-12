package com.hyprgloo.ld42.ships;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawQuadc;
import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlResetRotation;
import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlRotate;

import com.hyprgloo.ld42.Cargo;
import com.hyprgloo.ld42.Main;

public class ShipMerchantLight extends ShipMerchant{

	public static final float TRADE_TIME = 16f;
	
	public ShipMerchantLight(float xArg, float yArg, float xGoalArg, float yGoalArg, float rotationArg, Cargo cargoArg){
		super(xArg, yArg, xGoalArg, yGoalArg, rotationArg, 50f, 30f, cargoArg, TRADE_TIME, 90f, -1, 1f);
	}

	@Override
	public void draw(float delta){
		hvlRotate(x, y, rotation - 90f);
		hvlDrawQuadc(x, y, 32f, 32f, Main.getTexture(Main.INDEX_CARGO_SHIP_SMALL));
		if(!docked){
			hvlDrawQuadc(x, y + 6f, 16f, 16f, Main.getTexture(cargo.texture));
		}
		hvlResetRotation();
		drawTradeProgressBar(x, y);
		super.draw(delta);
	}

}
