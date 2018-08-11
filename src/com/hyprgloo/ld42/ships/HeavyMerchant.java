package com.hyprgloo.ld42.ships;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawQuadc;

import org.newdawn.slick.Color;

import com.hyprgloo.ld42.Ship;

public class HeavyMerchant extends Ship{

	public HeavyMerchant(float xArg, float yArg, float xsArg, float ysArg, float xGoalArg, float yGoalArg, float rotationArg){
		super(xArg, yArg, xsArg, ysArg, xGoalArg, yGoalArg, rotationArg, 50f);
	}

	@Override
	public void draw(float delta){
		hvlDrawQuadc(x, y, 10f, 10f, Color.blue);
	}

}
