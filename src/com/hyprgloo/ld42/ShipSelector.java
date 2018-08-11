package com.hyprgloo.ld42;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawLine;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;

import com.osreboot.ridhvl.HvlMath;
import com.osreboot.ridhvl.action.HvlAction1;
import com.osreboot.ridhvl.input.HvlInput;
import com.osreboot.ridhvl.painter.HvlCursor;

public class ShipSelector {
	public static Ship selectedShip;
	static HvlInput selectShip;
	static HvlInput chooseLocation;
	public static final float MAX_DISTANCE = 150;
	
	public static Ship getClosestShip() {
		Ship closestShip = null;
		if(Ship.ships.size() > 0) {
			for(Ship allShips : Ship.ships) {
				if(closestShip == null) {
					closestShip = allShips;
				}
				float distance = HvlMath.distance(HvlCursor.getCursorX(), HvlCursor.getCursorY(), closestShip.x, closestShip.y);
				float distanceTest = HvlMath.distance(HvlCursor.getCursorX(), HvlCursor.getCursorY(), allShips.x, allShips.y);

				if(distanceTest < distance) {
					closestShip = allShips;
				}
			}
		}

		return closestShip;
	}
	
	public static void initialize() {
		selectShip = new HvlInput(new HvlInput.InputFilter() {
			@Override
			public float getCurrentOutput() {
				if(Mouse.isButtonDown(0)) {
					return 1;
				}
				else {
					return 0;
				}
			}
		});
		chooseLocation = new HvlInput(new HvlInput.InputFilter() {
			@Override
			public float getCurrentOutput() {
				if(Mouse.isButtonDown(1)) {
					return 1;
				}
				else {
					return 0;
				}
			}
		});
		selectShip.setPressedAction(new HvlAction1<HvlInput>() {
			@Override
			public void run(HvlInput a) {
				if(selectedShip == null && getClosestShip() != null) {
					float currentMouseToClose = HvlMath.distance(HvlCursor.getCursorX(), HvlCursor.getCursorY(), getClosestShip().x, getClosestShip().y);
					if(currentMouseToClose < Math.abs(MAX_DISTANCE)) {
						selectedShip = getClosestShip();
					}
				}
				
			}
		});
		chooseLocation.setPressedAction(new HvlAction1<HvlInput>() {
			@Override
			public void run(HvlInput a) {
				if(selectedShip != null) {
					selectedShip.setGoal(HvlCursor.getCursorX(), HvlCursor.getCursorY());
					selectedShip = null;
				}
			}
		});
	}
	public static void update(float delta) {
		if(selectedShip != null) {
			hvlDrawLine(selectedShip.x, selectedShip.y,HvlCursor.getCursorX(), HvlCursor.getCursorY(), Color.white);
		}
		if(selectedShip == null && getClosestShip() != null) {
			float currentMouseToClose = HvlMath.distance(HvlCursor.getCursorX(), HvlCursor.getCursorY(), getClosestShip().x, getClosestShip().y);
			if(currentMouseToClose < Math.abs(MAX_DISTANCE)) {
				hvlDrawLine(getClosestShip().x, getClosestShip().y,HvlCursor.getCursorX(), HvlCursor.getCursorY(), Color.magenta);
			}
		}

	}
}
