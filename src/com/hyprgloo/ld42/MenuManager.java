package com.hyprgloo.ld42;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawQuad;

import java.util.HashMap;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import com.osreboot.ridhvl.action.HvlAction1;
import com.osreboot.ridhvl.action.HvlAction2;
import com.osreboot.ridhvl.menu.HvlButtonMenuLink;
import com.osreboot.ridhvl.menu.HvlComponent;
import com.osreboot.ridhvl.menu.HvlComponentDefault;
import com.osreboot.ridhvl.menu.HvlMenu;
import com.osreboot.ridhvl.menu.component.HvlArrangerBox;
import com.osreboot.ridhvl.menu.component.HvlButton;
import com.osreboot.ridhvl.menu.component.HvlArrangerBox.ArrangementStyle;
import com.osreboot.ridhvl.menu.component.HvlComponentDrawable;
import com.osreboot.ridhvl.menu.component.HvlSpacer;
import com.osreboot.ridhvl.menu.component.collection.HvlLabeledButton;

public class MenuManager {
	
	public static final float BUTTON_SPACING = 16f;
	
	private static HashMap<HvlLabeledButton, ButtonWrapper> buttonWrappers = new HashMap<>();

	public static HvlMenu main, levels, game;
	
	public static void initialize(){
		
		HvlArrangerBox defaultArrangerBox = new HvlArrangerBox(Display.getWidth(), Display.getHeight(), ArrangementStyle.VERTICAL);
		defaultArrangerBox.setxAlign(0.15f);
		defaultArrangerBox.setyAlign(0.85f);
		HvlComponentDefault.setDefault(defaultArrangerBox);
		
		HvlLabeledButton defaultLabeledButton = new HvlLabeledButton(256, 64, new HvlComponentDrawable(){
			@Override
			public void draw(float deltaArg, float xArg, float yArg, float widthArg, float heightArg){
				hvlDrawQuad(xArg, yArg, widthArg, heightArg, Main.COLOR_BLUE3);
			}
		}, new HvlComponentDrawable(){
			@Override
			public void draw(float deltaArg, float xArg, float yArg, float widthArg, float heightArg){
				hvlDrawQuad(xArg, yArg, widthArg, heightArg, Main.COLOR_BLUE4);
			}
		}, Main.font, "", Color.white);
		defaultLabeledButton.setUpdateOverride(new HvlAction2<HvlComponent, Float>(){
			@Override
			public void run(HvlComponent aArg, Float delta){
				HvlLabeledButton b = (HvlLabeledButton)aArg;
				if(!buttonWrappers.containsKey(b)) buttonWrappers.put(b, new ButtonWrapper());
				buttonWrappers.get(b).update(delta);
				
				if(b.isHovering()) FancyOverlay.drawMainButton(delta, b);
				b.update(delta);
			}
		});
		defaultLabeledButton.setTextScale(0.25f);
		defaultLabeledButton.setyAlign(0.7f);
		HvlComponentDefault.setDefault(defaultLabeledButton);
		
		main = new HvlMenu();
		levels = new HvlMenu();
		game = new HvlMenu();
		
		main.add(new HvlArrangerBox.Builder().build());
		main.getFirstArrangerBox().add(new HvlLabeledButton.Builder().setText("start").setClickedCommand(new HvlButtonMenuLink(levels)).build());
		main.getFirstArrangerBox().add(new HvlSpacer(0, BUTTON_SPACING));
		main.getFirstArrangerBox().add(new HvlLabeledButton.Builder().setText("credits").build());
		main.getFirstArrangerBox().add(new HvlSpacer(0, BUTTON_SPACING));
		main.getFirstArrangerBox().add(new HvlLabeledButton.Builder().setText("options").build());
		main.getFirstArrangerBox().add(new HvlSpacer(0, BUTTON_SPACING));
		main.getFirstArrangerBox().add(new HvlLabeledButton.Builder().setText("exit").setClickedCommand(new HvlAction1<HvlButton>(){
			@Override
			public void run(HvlButton aArg){
				System.exit(0);
			}
		}).build());
		
		levels.add(new HvlArrangerBox.Builder().setStyle(ArrangementStyle.HORIZONTAL).build());
		levels.getFirstArrangerBox().add(new HvlLabeledButton.Builder().setText("back").setClickedCommand(new HvlButtonMenuLink(main)).build());
		levels.getFirstArrangerBox().add(new HvlSpacer(BUTTON_SPACING, 0));
		levels.getFirstArrangerBox().add(new HvlLabeledButton.Builder().setWidth(64f).setText("1").setClickedCommand(new HvlAction1<HvlButton>(){
			@Override
			public void run(HvlButton aArg){
				Game.selected_level = 1;
				HvlMenu.setCurrent(game);
				Game.restart();
			}
		}).build());
		levels.getFirstArrangerBox().add(new HvlSpacer(BUTTON_SPACING, 0));
		levels.getFirstArrangerBox().add(new HvlLabeledButton.Builder().setWidth(64f).setText("2").setClickedCommand(new HvlAction1<HvlButton>(){
			@Override
			public void run(HvlButton aArg){
				Game.selected_level = 2;
				HvlMenu.setCurrent(game);
				Game.restart();
			}
		}).build());
		levels.getFirstArrangerBox().add(new HvlSpacer(BUTTON_SPACING, 0));
		levels.getFirstArrangerBox().add(new HvlLabeledButton.Builder().setWidth(64f).setText("3").setClickedCommand(new HvlAction1<HvlButton>(){
			@Override
			public void run(HvlButton aArg){
				Game.selected_level = 3;
				HvlMenu.setCurrent(game);
				Game.restart();
			}
		}).build());
		
		HvlMenu.setCurrent(main);
	}
	
	public static void update(float delta){
		if(HvlMenu.getCurrent() == main || HvlMenu.getCurrent() == levels){
			FancyOverlay.updateMain(delta);
		}else if(HvlMenu.getCurrent() == game){
			Game.update(delta);
		}
		
		HvlMenu.updateMenus(delta);
	}
	
	private static class ButtonWrapper{
		
		private ButtonWrapper(){
			
		}
		
		private void update(float delta){
			
		}
		
	}
	
}
