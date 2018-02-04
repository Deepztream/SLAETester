package com.abysmal.slaetester;

import com.abysmal.slae.SLAE;
import com.abysmal.slae.message.Message;
import com.abysmal.slae.message.MessageBus;
import com.abysmal.slae.object.GUIObject;
import com.abysmal.slae.system.Render;
import com.abysmal.slae.system.System;

import org.joml.Rectanglef;
import org.joml.Vector3f;

public class SLAETester implements System {

	public static void main(String[] args) {
		SLAE.initialise(new SLAETester());
	}

	public void init() {
		String shaderPath = SLAETester.class.getResource("/shaders/Basic.shader").getPath();
		Object[] data = { Render.MAIN_MENU,
				new GUIObject(new Rectanglef(-0.5f, -0.5f, 0.5f, 0.5f), new Vector3f(0.6f, 0.3f, 0.0f), shaderPath) };
		MessageBus.getBus().postMessage(new Message("Add GUIObject", data));
	}

	@Override
	public void handleMessage(Message message) {
		if (message.getMessage().equalsIgnoreCase("slae init")) {
			init();
		}
	}
}