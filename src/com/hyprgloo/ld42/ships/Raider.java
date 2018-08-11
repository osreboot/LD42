package com.hyprgloo.ld42.ships;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawQuadc;
import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlResetRotation;
import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlRotate;

import org.newdawn.slick.Color;

import com.hyprgloo.ld42.Ship;

public class Raider extends Ship{
		
	public Raider(float xArg, float yArg, float xGoalArg, float yGoalArg, float rotationArg){
		super(xArg, yArg, xGoalArg, yGoalArg, rotationArg, 25);
	}
	
	@Override
	public void draw(float delta){
		hvlRotate(x, y, rotation - 90f);
		hvlDrawQuadc(x, y, 24f, 24f, Color.red);
		hvlResetRotation();
	}
}
