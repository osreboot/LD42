package com.hyprgloo.ld42.ships;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawQuadc;

import org.newdawn.slick.Color;

import com.hyprgloo.ld42.Cargo;
import com.hyprgloo.ld42.Ship;

public class LightMerchant extends Ship{

	public LightMerchant(float xArg, float yArg, float xGoalArg, float yGoalArg, float rotationArg, Cargo cargoArg){
		super(xArg, yArg, xGoalArg, yGoalArg, rotationArg, 20f, cargoArg);
	}

	@Override
	public void draw(float delta){
		hvlDrawQuadc(x, y, 30f, 30f, Color.blue);
		super.draw(delta);
	}

}
