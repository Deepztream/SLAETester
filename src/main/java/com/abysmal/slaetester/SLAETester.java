package com.abysmal.slaetester;

import java.awt.Color;

import com.abysmal.slae.SLAE;
import com.abysmal.slae.message.Message;
import com.abysmal.slae.object.GUIObject;
import com.abysmal.slae.object.HUDObject;
import com.abysmal.slae.system.Render;
import com.abysmal.slae.system.System;

import org.joml.Rectanglef;
import org.joml.Vector2i;

public class SLAETester implements System {

	public static void main(String[] args) {
		SLAE.initialise(new SLAETester());
	}

	public void init() {
		String shaderPath = SLAETester.class.getResource("/shaders/Basic.shader").getPath();
		Object[] data = { Render.MAIN_MENU, new GUIObject(new Rectanglef(1f, 1f, -1f, -1f), Color.white, shaderPath) };
		SLAE.execute("Add GUIObject", data.clone());
		data[1] = new GUIObject(new Rectanglef(-1f, 1f, 1f, .75f), Color.gray, shaderPath);
		SLAE.execute("Add GUIObject", data.clone());
		Object[] data2 = { new HUDObject(new Vector2i(0, 0), new Vector2i(100, 100), (b, a, m) -> {
			if (b == 0 && a == 1) {
				data[1] = new GUIObject(new Rectanglef(-1f, 1f, 1f, .75f), Color.RED, shaderPath);
			} else if (b == 0 && a == 0) {
				data[1] = new GUIObject(new Rectanglef(-1f, 1f, 1f, .75f), Color.gray, shaderPath);
			}
			SLAE.execute("Add GUIObject", data.clone());
		}) };
		SLAE.execute("Add HUDObject", data2);
	}

	@Override
	public void handleMessage(Message message) {
		if (message.getMessage().equalsIgnoreCase("slae init")) {
			init();
		}
	}
}