package com.hyprgloo.ld42.ships;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawQuadc;
import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlResetRotation;
import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlRotate;

import com.hyprgloo.ld42.Main;
import com.hyprgloo.ld42.SpaceStationPart;

public class ShipRaiderLight extends Raider{

	public static final float TRADE_TIME = 20f, COLLISION_SIZE = 16f - 4f;
	
	public ShipRaiderLight(float xArg, float yArg, float xGoalArg, float yGoalArg, float rotationArg, SpaceStationPart goalPartArg){
		super(xArg, yArg, xGoalArg, yGoalArg, rotationArg, 40f, COLLISION_SIZE, TRADE_TIME, 90f, goalPartArg);
	}
	
	@Override
	public void draw(float delta){
		hvlRotate(x, y, rotation - 90f);
		hvlDrawQuadc(x, y, 32f, 32f, Main.getTexture(Main.INDEX_RAIDER));
		if(!docked) hvlDrawQuadc(x, y + 6f, 16f, 16f, Main.getTexture(cargo.texture));
		hvlResetRotation();
		//super.draw(delta);
	}

}
