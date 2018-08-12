package com.hyprgloo.ld42;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;

import com.osreboot.ridhvl.display.collection.HvlDisplayModeDefault;
import com.osreboot.ridhvl.input.HvlInput;
import com.osreboot.ridhvl.painter.painter2d.HvlFontPainter2D;
import com.osreboot.ridhvl.template.HvlTemplateInteg2D;

public class Main extends HvlTemplateInteg2D{

	public static void main(String[] args){
		new Main();
	}

	public static final int 
	INDEX_FONT = 0,
	INDEX_CANISTER_FUEL = 1,
	INDEX_CANISTER_ENERGY = 2,
	INDEX_CANISTER_AMMO = 3,
	INDEX_STATION_SOLAR_PANEL = 4,
	INDEX_CARGO_SHIP_SMALL = 5,
	INDEX_CANISTER_EMPTY = 6,
	INDEX_CARGO_SHIP_MEDIUM = 7,
	INDEX_STATION_DOCKING_PORT = 8,
	INDEX_STATION_TRUSS = 9,
	INDEX_STATION_TURRET_MOUNT = 10,
	INDEX_STATION_DOCKING_PORT_MED = 11,
	INDEX_STATION_DOCKING_PORT_LRG = 12,
	INDEX_STATION_TURRET = 13,
	INDEX_MOVEON = 14,
	INDEX_ZONE = 15;

	public static final Color 
	COLOR_BLUE0 = new Color(0.2f, 0.2f, 1.0f),
	COLOR_BLUE1 = new Color(0f, 0f, 1.0f),
	COLOR_BLUE2 = new Color(0f, 0f, 0.8f),
	COLOR_BLUE3 = new Color(0f, 0f, 0.6f),
	COLOR_BLUE4 = new Color(0f, 0f, 0.4f);

	public static HvlFontPainter2D font;

	public static HvlInput inputPause;

	public Main(){
		super(144, 1280, 720, "Airlock Gridlock by HYPRGLOO", new HvlDisplayModeDefault());
	}

	@Override
	public void initialize(){
		getTextureLoader().loadResource("Font");//0
		getTextureLoader().loadResource("CanisterFuel");//1
		getTextureLoader().loadResource("CanisterEnergy");//2
		getTextureLoader().loadResource("CanisterAmmo");//3

		getTextureLoader().loadResource("StationSolarPanel");//4
		getTextureLoader().loadResource("CargoShipSmall");//5
		getTextureLoader().loadResource("CanisterEmpty");//6
		getTextureLoader().loadResource("CargoShipMedium");//7
		getTextureLoader().loadResource("StationDockingPort");//8
		getTextureLoader().loadResource("StationTruss");//9
		getTextureLoader().loadResource("StationTurretMount");//10
		getTextureLoader().loadResource("StationDockingPortMed");//11
		getTextureLoader().loadResource("StationDockingPortLrg");//12
		getTextureLoader().loadResource("StationTurret");//13
		getTextureLoader().loadResource("MoveOn");//14
		getTextureLoader().loadResource("Zone");//15

		font = new HvlFontPainter2D(getTexture(INDEX_FONT), HvlFontPainter2D.Preset.FP_AGOFFICIAL);
		font.setCharSpacing(16f);

		inputPause = new HvlInput(new HvlInput.InputFilter(){
			@Override
			public float getCurrentOutput(){
				return Keyboard.isKeyDown(Keyboard.KEY_ESCAPE) || Keyboard.isKeyDown(Keyboard.KEY_P) ? 1 : 0;
			}
		});

		ShipSelector.initialize();
		MenuManager.initialize();
	}

	@Override
	public void update(float delta){
		MenuManager.update(delta);
	}

}
