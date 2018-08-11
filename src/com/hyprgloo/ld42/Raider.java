package com.hyprgloo.ld42;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawQuadc;
import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlResetRotation;
import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlRotate;

import java.util.ArrayList;

import org.newdawn.slick.Color;

import com.osreboot.ridhvl.HvlCoord2D;
import com.osreboot.ridhvl.HvlMath;

public class Raider {
	public static ArrayList<Raider> raiders = new ArrayList<>();
	public static final float MAX_SPEED = 25;
	public static void updateRaiders(float delta) {
		for(Raider allRaiders : raiders) {
			allRaiders.update(delta);
		}
		for(Raider allRaiders : raiders) {
			allRaiders.draw(delta);
		}
	}
		
	public float x, y, xs, ys, speed, xGoal, yGoal, rotation, maxSpeed;
	public Raider(float xArg, float yArg, float xGArg, float yGArg, float rotationArg, float maxSpArg) {
		x = xArg;
		y = yArg;
		xs = 0;
		ys = 0;
		speed = 0;
		xGoal = xGArg;
		yGoal = yGArg;
		rotation = rotationArg;
		maxSpeed = maxSpArg;
		raiders.add(this);
	}
	public void update(float delta) {
		float goalDistance = HvlMath.distance(x, y, xGoal, yGoal);
		float newRotation = 0f;
		speed = HvlMath.stepTowards(speed, delta * maxSpeed, Math.min(maxSpeed, goalDistance));
		HvlCoord2D speedCoord = new HvlCoord2D(xGoal - x, yGoal - y);
		speedCoord.normalize();
		if(Float.isNaN(speedCoord.x)) speedCoord.x = 0;
		if(Float.isNaN(speedCoord.y)) speedCoord.y = 0;
		speedCoord.mult(speed);
		xs = HvlMath.stepTowards(xs, delta * maxSpeed, speedCoord.x);
		ys = HvlMath.stepTowards(ys, delta * maxSpeed, speedCoord.y);
		x += xs * delta;
		y += ys * delta;
		newRotation = (float)Math.toDegrees(HvlMath.fullRadians(new HvlCoord2D(x, y), new HvlCoord2D(xGoal, yGoal)));
		while(Math.abs(newRotation - rotation) > 180f){
			if(Math.abs(newRotation - 360f - rotation) < Math.abs(newRotation + 360f - rotation))
				newRotation -= 360f;
			else newRotation += 360f;
		}
		if(goalDistance > 64f) rotation = HvlMath.stepTowards(rotation, delta * maxSpeed * 2f, newRotation);
	}
	public void draw(float delta) {
		hvlRotate(x, y, rotation - 90f);
		hvlDrawQuadc(x, y, 24f, 24f, Color.red);
		hvlResetRotation();
	}
}
