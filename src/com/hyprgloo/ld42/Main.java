package com.hyprgloo.ld42;

import java.io.File;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;

import com.osreboot.ridhvl.action.HvlAction1;
import com.osreboot.ridhvl.config.HvlConfig;
import com.osreboot.ridhvl.display.collection.HvlDisplayModeDefault;
import com.osreboot.ridhvl.input.HvlInput;
import com.osreboot.ridhvl.menu.HvlMenu;
import com.osreboot.ridhvl.painter.HvlCursor;
import com.osreboot.ridhvl.painter.painter2d.HvlFontPainter2D;
import com.osreboot.ridhvl.template.HvlTemplateInteg2D;

public class Main extends HvlTemplateInteg2D{

	//TODO
	/*
	 * TUTORIALS - menus
	 * TUTORIALS - level
	 * music
	 * sound effects
	 * station 2
	 * station 3
	 * large merchants
	 * options config
	 * raider/turret shooting (low draw index)
	 * energy capsule effect
	 * game loss states (just titles left)
	 * interaction textures (select, target, targetoptions)
	 * menu titles
	 * credits screen
	 * splash screen
	 * station collision
	 * disaster fail state?
	 * 
	 * cursor?
	 * 
	 */

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
	INDEX_ZONE = 15, 
	INDEX_EXP = 16,
	INDEX_RAIDER = 17,
	INDEX_BUTTON_TARGET = 18,
	INDEX_BLINK = 19,
	INDEX_CARGO_SHIP_LARGE = 20,
	INDEX_CURSOR = 21,
	INDEX_NEBULA1 = 22,
	INDEX_NEBULA2 = 23,
	INDEX_STARS = 24,
	INDEX_LIGHT_1 = 25,
	INDEX_LIGHT_2 = 26,
	INDEX_LIGHT_3 = 27,
	INDEX_CURSOR_2 = 28;

	public static final Color 
	COLOR_GREEN0 = new Color(0f, 1.0f, 0f),
	COLOR_GREEN1 = new Color(0f, 0.8f, 0f),
	COLOR_GREEN2 = new Color(0f, 0.6f, 0f),
	COLOR_GREEN3 = new Color(0f, 0.4f, 0f),
	COLOR_GREEN4 = new Color(0f, 0.2f, 0f),
	COLOR_GREEN5 = new Color(0f, 0.1f, 0f);

	public static HvlFontPainter2D font;

	public static HvlInput inputPause;

	public static final String PATH_SETTINGS = "res\\settings.cfg";
	public static Settings settings;

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
		getTextureLoader().loadResource("explosion"); //16
		getTextureLoader().loadResource("Raider1"); //17
		getTextureLoader().loadResource("ButtonTarget"); //18
		getTextureLoader().loadResource("Blink"); //19
		getTextureLoader().loadResource("CargoShipMedium"); //20//TODO placeholder
		getTextureLoader().loadResource("cursor"); //21
		getTextureLoader().loadResource("Nebula1"); //22
		getTextureLoader().loadResource("Nebula2"); //23
		getTextureLoader().loadResource("Stars"); //24
		getTextureLoader().loadResource("lightning1"); //25
		getTextureLoader().loadResource("lightning2"); //26
		getTextureLoader().loadResource("lightning3"); //27
		getTextureLoader().loadResource("cursor2"); //28


		font = new HvlFontPainter2D(getTexture(INDEX_FONT), HvlFontPainter2D.Preset.FP_AGOFFICIAL);
		font.setCharSpacing(16f);

		inputPause = new HvlInput(new HvlInput.InputFilter(){
			@Override
			public float getCurrentOutput(){
				return Keyboard.isKeyDown(Keyboard.KEY_ESCAPE) || Keyboard.isKeyDown(Keyboard.KEY_P) ? 1 : 0;
			}
		});
		inputPause.setPressedAction(new HvlAction1<HvlInput>(){
			@Override
			public void run(HvlInput aArg){
				if(HvlMenu.getCurrent() == MenuManager.game){
					HvlMenu.setCurrent(MenuManager.pause);
				}else if(HvlMenu.getCurrent() == MenuManager.pause){
					HvlMenu.setCurrent(MenuManager.game);
				}
			}
		});

		File config = new File(PATH_SETTINGS);
		if(config.exists()){
			settings = HvlConfig.loadFromFile(PATH_SETTINGS);
		}else{
			HvlConfig.saveToFile(new Settings(), PATH_SETTINGS);
			settings = HvlConfig.loadFromFile(PATH_SETTINGS);
		}
		HvlCursor.setTexture(Main.getTexture(Main.INDEX_CURSOR_2));
		HvlCursor.setNativeHidden(true);
		HvlCursor.setHeight(32);
		HvlCursor.setWidth(32);
		HvlCursor.setXOffset(-HvlCursor.getWidth()/2);
		HvlCursor.setYOffset(-HvlCursor.getHeight()/2);
		ShipSelector.initialize();
		MenuManager.initialize();
	}

	@Override
	public void update(float delta){
		MenuManager.update(delta);
	}

	public static void saveConfig(){
		HvlConfig.saveToFile(settings, PATH_SETTINGS);
	}

}
