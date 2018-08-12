package com.hyprgloo.ld42;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawLine;
import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawQuad;
import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawQuadc;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import com.hyprgloo.ld42.ships.ShipMerchant;
import com.osreboot.ridhvl.HvlMath;
import com.osreboot.ridhvl.action.HvlAction1;
import com.osreboot.ridhvl.input.HvlInput;
import com.osreboot.ridhvl.painter.HvlCursor;

public class ShipSelector {

	public static final float 
	MAX_DISTANCE = 150,
	MAX_DOCK_DISTANCE = 32f;

	public static ShipMerchant selectedShip;
	static HvlInput selectShip;
	static HvlInput chooseLocation;

	public static ShipMerchant getClosestShip() {
		ShipMerchant closestShip = null;
		if(Ship.ships.size() > 0) {
			for(Ship allShips : Ship.ships) {
				if(closestShip == null && allShips instanceof ShipMerchant) {
					closestShip = (ShipMerchant)allShips;
				}
				float distance = HvlMath.distance(HvlCursor.getCursorX(), HvlCursor.getCursorY(), closestShip.x, closestShip.y);
				float distanceTest = HvlMath.distance(HvlCursor.getCursorX(), HvlCursor.getCursorY(), allShips.x, allShips.y);

				if(distanceTest < distance && allShips instanceof ShipMerchant) {
					closestShip = (ShipMerchant)allShips;
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
						if(currentMouseToClose < Math.abs(MAX_DISTANCE) && !getClosestShip().isDead && !getClosestShip().isLeaving) {
							selectedShip = getClosestShip();
						}
					}else if(selectedShip != null) selectedShip = null;
			}
		});
		chooseLocation.setPressedAction(new HvlAction1<HvlInput>() {
			@Override
			public void run(HvlInput a) {
				if(selectedShip != null) {
					SpaceStationPart dockingPart = null;
					for(SpaceStationPart p : SpaceStation.stationParts){
						if(p.textureIndex <= selectedShip.dockingReq){
							if(HvlMath.distance(HvlCursor.getCursorX(), HvlCursor.getCursorY(), p.x, p.y) < MAX_DOCK_DISTANCE && (dockingPart == null ||
									HvlMath.distance(HvlCursor.getCursorX(), HvlCursor.getCursorY(), p.x, p.y) < HvlMath.distance(HvlCursor.getCursorX(), HvlCursor.getCursorY(), dockingPart.x, dockingPart.y))){
								dockingPart = p;
							}
						}
					}
					if(dockingPart != null){
						if(!Ship.shipInProximity(dockingPart.x, dockingPart.y, SpaceStation.GRID_SIZE, selectedShip) && selectedShip.canDock(dockingPart.textureIndex)){
							selectedShip.setGoal(dockingPart.x, dockingPart.y);
							selectedShip.docking = true;
							selectedShip = null;
						}
					}else{
						FlightPath path = null;
						for(FlightPath p : FlightPath.paths) if(p.isMouseHovering()) path = p;
						if(path != null){
							selectedShip.setFlightPath(path);
							selectedShip.docking = false;
							selectedShip = null;
						}else{
							selectedShip.setGoal(HvlCursor.getCursorX(), HvlCursor.getCursorY());
							selectedShip.docking = false;
							selectedShip = null;
						}
					}
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
			if(currentMouseToClose < Math.abs(MAX_DISTANCE) && !getClosestShip().isDead && !getClosestShip().isLeaving) {
				hvlDrawLine(getClosestShip().x, getClosestShip().y,HvlCursor.getCursorX(), HvlCursor.getCursorY(), new Color(0f, 0f, 1f, 0.8f));
			}
		}
		hvlDrawQuad(Display.getWidth() - 96f, 0, 96f, Display.getHeight(), Main.getTexture(Main.INDEX_MOVEON), new Color(1f, 1f, 1f, HvlCursor.getCursorX() > Display.getWidth() - 96f ? 0.4f : 0.05f));
		
		boolean upperZoneCursor = HvlCursor.getCursorX() < Display.getWidth() - 96f && HvlCursor.getCursorY() < LevelShipSequencer.SHIP_SPAWN_EDGE_SPACING;
		hvlDrawQuad(0, 0, Display.getWidth() - 96f, LevelShipSequencer.SHIP_SPAWN_EDGE_SPACING, 
				0, 0, (Display.getWidth() - 96f)/16f, LevelShipSequencer.SHIP_SPAWN_EDGE_SPACING/16f, 
				Main.getTexture(Main.INDEX_ZONE), new Color(0f, 1f, 0f, upperZoneCursor ? 0.2f : 0.1f));
		boolean lowerZoneCursor = HvlCursor.getCursorX() < Display.getWidth() - 96f && HvlCursor.getCursorY() > Display.getHeight() - LevelShipSequencer.SHIP_SPAWN_EDGE_SPACING;
		hvlDrawQuad(0, Display.getHeight() - LevelShipSequencer.SHIP_SPAWN_EDGE_SPACING, 
				Display.getWidth() - 96f, LevelShipSequencer.SHIP_SPAWN_EDGE_SPACING, 
				0, 0, (Display.getWidth() - 96f)/16f, LevelShipSequencer.SHIP_SPAWN_EDGE_SPACING/16f, 
				Main.getTexture(Main.INDEX_ZONE), new Color(0f, 1f, 0f, lowerZoneCursor ? 0.2f : 0.08f));
	}
}
