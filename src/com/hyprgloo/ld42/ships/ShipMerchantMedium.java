package com.hyprgloo.ld42.ships;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawQuadc;
import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlResetRotation;
import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlRotate;

import org.newdawn.slick.Color;

import com.hyprgloo.ld42.Cargo;
import com.hyprgloo.ld42.Main;

public class ShipMerchantMedium extends ShipMerchant{

	public static final float TRADE_TIME = 22f, COLLISION_SIZE = 32f - 8f;
	
	public ShipMerchantMedium(float xArg, float yArg, float xGoalArg, float yGoalArg, float rotationArg, Cargo cargoArg){
		super(xArg, yArg, xGoalArg, yGoalArg, rotationArg, 30f, COLLISION_SIZE, cargoArg, TRADE_TIME, 180f, -2, 2f);
	}

	@Override
	public void draw(float delta){
		super.draw(delta);
		Color deadColor = new Color(isDead ? 0.4f : 1f, isDead ? 0.4f : 1f, isDead ? 0.4f : 1f, 1f);
		hvlRotate(x, y, rotation - 90f);
		hvlDrawQuadc(x, y, 64f, 64f, Main.getTexture(Main.INDEX_CARGO_SHIP_MEDIUM), deadColor);
		if(!docked && !isDead){
			hvlDrawQuadc(x + 10f, y - 8f, 16f, 16f, Main.getTexture(cargo.texture));
			hvlDrawQuadc(x + 22f, y - 8f, 16f, 16f, Main.getTexture(cargo.texture));
		}
		hvlResetRotation();
		drawTradeProgressBar(x, y);
	}

}
