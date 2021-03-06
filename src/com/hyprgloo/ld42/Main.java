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
	INDEX_CURSOR_2 = 28,
	INDEX_CLEAR = 29,
	INDEX_TARGET_INDICATOR = 30,
	INDEX_DOCKING_ICON = 31,
	INDEX_TUTORIAL = 32,
	INDEX_HYPRGLOO = 41,
	INDEX_STRUT = 42,
	INDEX_CHART = 43,
	INDEX_BUTTON = 44,
	INDEX_XWING = 45;

	public static final int 
	INDEX_CRASH = 0,
	INDEX_ELECTRIC = 1,
	INDEX_ELECTRIC2 = 2,
	INDEX_LASER = 3,
	INDEX_MENU_BLIP = 4,
	INDEX_SHIP_SELECT = 5,
	INDEX_SHIP_COMMAND = 6,
	INDEX_WORLDOFFIRE = 7,
	INDEX_SHIPTOCATCH = 8,
	INDEX_ELECTRIC3 = 9,
	INDEX_ITEM_COMPLETE = 10,
	INDEX_ITEM_STOLEN = 11,
	INDEX_BATTERY_DEATH = 12,
	INDEX_JUMP = 13,
	INDEX_LASER2 = 14;


	public static final Color 
	COLOR_GREEN0 = new Color(0f, 1.0f, 0f),
	COLOR_GREEN1 = new Color(0f, 0.8f, 0f),
	COLOR_GREEN2 = new Color(0f, 0.6f, 0f),
	COLOR_GREEN3 = new Color(0f, 0.4f, 0f),
	COLOR_GREEN4 = new Color(0f, 0.2f, 0f),
	COLOR_GREEN5 = new Color(0f, 0.1f, 0f);

	public static HvlFontPainter2D font;

	public static HvlInput inputPause, inputTutorialSkip;

	public static final String PATH_SETTINGS = "res\\settings.cfg";
	public static Settings settings;
	public static int musicPreference = 1;

	public Main(){
		super(144, 1280, 720, "Airlock Gridlock by HYPRGLOO", "CargoShipSmall", new HvlDisplayModeDefault());
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
		getTextureLoader().loadResource("CargoShipLarge"); //20
		getTextureLoader().loadResource("cursor"); //21
		getTextureLoader().loadResource("Nebula1"); //22
		getTextureLoader().loadResource("Nebula2"); //23
		getTextureLoader().loadResource("Stars"); //24
		getTextureLoader().loadResource("lightning1"); //25
		getTextureLoader().loadResource("lightning2"); //26
		getTextureLoader().loadResource("lightning3"); //27
		getTextureLoader().loadResource("cursor2"); //28
		getTextureLoader().loadResource("clear"); //29
		getTextureLoader().loadResource("TargetIndicator"); //30
		getTextureLoader().loadResource("Docking_Icon"); //31
		getTextureLoader().loadResource("Tutorial1"); //32
		getTextureLoader().loadResource("Tutorial2"); //33
		getTextureLoader().loadResource("Tutorial3"); //34
		getTextureLoader().loadResource("Tutorial4"); //35
		getTextureLoader().loadResource("Tutorial5"); //36
		getTextureLoader().loadResource("Tutorial6"); //37
		getTextureLoader().loadResource("Tutorial7"); //38
		getTextureLoader().loadResource("Tutorial8"); //39
		getTextureLoader().loadResource("Tutorial9"); //40
		getTextureLoader().loadResource("HYPRGLOO"); //41
		getTextureLoader().loadResource("strut");//42
		getTextureLoader().loadResource("SizeChart");//43
		getTextureLoader().loadResource("Button");//44
		getTextureLoader().loadResource("XWing");//45

		getSoundLoader().loadResource("CrashExplode");//0
		getSoundLoader().loadResource("Electric");//1
		getSoundLoader().loadResource("Electric2");//2
		getSoundLoader().loadResource("LaserPew");//3
		getSoundLoader().loadResource("MenuBlipGood");//4;
		getSoundLoader().loadResource("ShipSelect");//5
		getSoundLoader().loadResource("ShipCommand");//6
		getSoundLoader().loadResource("World of Fire");//7
		getSoundLoader().loadResource("Ship to Catch");//8
		getSoundLoader().loadResource("Electric3");//9
		getSoundLoader().loadResource("ItemComplete");//10
		getSoundLoader().loadResource("ItemStolen");//11
		getSoundLoader().loadResource("BatteryDeath");//12
		getSoundLoader().loadResource("Jump");//13
		getSoundLoader().loadResource("Laser2");//14








		musicPreference = 1;

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

		inputTutorialSkip = new HvlInput(new HvlInput.InputFilter(){
			@Override
			public float getCurrentOutput(){
				return Keyboard.isKeyDown(Keyboard.KEY_SPACE) ? 1 : 0;
			}
		});
		inputTutorialSkip.setPressedAction(new HvlAction1<HvlInput>(){
			@Override
			public void run(HvlInput aArg){
				TutorialManager.advanceTutorial();
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
		TutorialManager.initialize();
	}

	@Override
	public void update(float delta){
		if(!getSound(INDEX_WORLDOFFIRE).isPlaying() && !getSound(INDEX_SHIPTOCATCH).isPlaying() && settings.musicEnabled && musicPreference == 2){
			Main.getSound(Main.INDEX_WORLDOFFIRE).playAsSoundEffect(1f, 0.5f, false);
			musicPreference = 1;
		}
		if(getSound(INDEX_WORLDOFFIRE).isPlaying() && !settings.musicEnabled){
			Main.getSound(Main.INDEX_WORLDOFFIRE).stop();
		}
		if(!getSound(INDEX_WORLDOFFIRE).isPlaying() && !getSound(INDEX_SHIPTOCATCH).isPlaying() && settings.musicEnabled && musicPreference == 1){
			Main.getSound(Main.INDEX_SHIPTOCATCH).playAsSoundEffect(1f, 0.5f, false);
			musicPreference = 2;
		}
		if(getSound(INDEX_SHIPTOCATCH).isPlaying() && !settings.musicEnabled){
			Main.getSound(Main.INDEX_SHIPTOCATCH).stop();
		}


		//TODO Add mouse menu remembrance
		if(settings.customCursor) {
			//			if(HvlMenu.getCurrent() == MenuManager.pause) {
			//				HvlCursor.setTexture(getTexture(INDEX_CLEAR));
			//				HvlCursor.setNativeHidden(false);
			//			} else {
			if(HvlCursor.getTexture() == getTexture(INDEX_CLEAR)) {
				HvlCursor.setTexture(Main.getTexture(Main.INDEX_CURSOR_2));
				HvlCursor.setHeight(32);
				HvlCursor.setWidth(32);
				HvlCursor.setXOffset(-HvlCursor.getWidth()/2);
				HvlCursor.setYOffset(-HvlCursor.getHeight()/2);
			}
			HvlCursor.setNativeHidden(true);
			//			}
		}else {
			HvlCursor.setTexture(getTexture(INDEX_CLEAR));
			HvlCursor.setNativeHidden(false);
		}
		MenuManager.update(delta);
	}

	public static void saveConfig(){
		HvlConfig.saveToFile(settings, PATH_SETTINGS);
	}

}
