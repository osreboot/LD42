package com.hyprgloo.ld42.ships;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawQuadc;
import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlResetRotation;
import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlRotate;

import org.newdawn.slick.Color;

import com.hyprgloo.ld42.Cargo;
import com.hyprgloo.ld42.Main;

public class ShipMerchantLarge extends ShipMerchant{
	public static final float TRADE_TIME = 30f, COLLISION_SIZE = 48f - 4f;
	
	public ShipMerchantLarge(float xArg, float yArg, float xGoalArg, float yGoalArg, float rotationArg, Cargo cargoArg){
		super(xArg, yArg, xGoalArg, yGoalArg, rotationArg, 30f, COLLISION_SIZE, cargoArg, TRADE_TIME, 90f, -3, 4f);
	}

	@Override
	public void draw(float delta){
		super.draw(delta);
		Color deadColor = new Color(isDead ? 0.4f : 1f, isDead ? 0.4f : 1f, isDead ? 0.4f : 1f, 1f);
		hvlRotate(x, y, rotation - 90f);
		hvlDrawQuadc(x, y, 96f, 96f, Main.getTexture(Main.INDEX_CARGO_SHIP_LARGE), deadColor);
		if(!docked && !isDead){
			hvlDrawQuadc(x, y - 12f, 16f, 16f, Main.getTexture(cargo.texture));
			hvlDrawQuadc(x, y + 6f, 16f, 16f, Main.getTexture(cargo.texture));
			hvlDrawQuadc(x, y + 24f, 16f, 16f, Main.getTexture(cargo.texture));
			hvlDrawQuadc(x, y -30f, 16f, 16f, Main.getTexture(cargo.texture));
		}
		hvlResetRotation();
		drawTradeProgressBar(x, y);
	}
}
