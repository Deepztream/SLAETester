package com.abysmal.slaetester;

import java.awt.Polygon;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.abysmal.slae.SLAE;
import com.abysmal.slae.framework.Input;
import com.abysmal.slae.framework.Window;
import com.abysmal.slae.message.Message;
import com.abysmal.slae.object.GameObject;
import com.abysmal.slae.object.HUDObject;
import com.abysmal.slae.system.ISO;
import com.abysmal.slae.system.System;
import com.abysmal.slae.util.User;

import org.joml.Vector2d;
import org.joml.Vector2i;
import org.joml.Vector3d;
import org.joml.Vector3f;

public class SLAETester implements System {

	public static void main(String[] args) {
		SLAE.execute("ToggleVerboseConsole", null);
		SLAE.execute("Start Server", 1337);
		SLAE.initialise(new SLAETester());
	}

	Map<Vector2i, Double> heightmap = new HashMap<Vector2i, Double>();
	GameObject floor = new GameObject(new ArrayList<GameObject>());

	public void init() {

		for (int y = -20; y < 1000; y++)
			for (int x = -20; x < 1000; x++)
				heightmap.put(new Vector2i(y, x), Math.random() * .5);

		int size = 80;

		for (int y = -50; y < Window.getHeight() + 50; y++) {
			for (int x = -50; x < Window.getWidth() + 50; x++) {
				Vector2d v = ISO.orthoToIso(new Vector2d(x, y));
				if (v.x % size == 0 && v.y % size == 0) {
					drawIso((int) v.x / size, (int) v.y / size, size);
				}
			}
		}
		Object[] data = { 0, floor };
		SLAE.execute("Add GameObject", data.clone());

		Object[] data2 = { 0, new HUDObject(new Vector2i(0, 0), new Vector2i(100, 100), (b, a, m) -> {
			SLAE.execute("Switch Scene", a);
		}) };
		SLAE.execute("Add HUDObject", data2);
		SLAE.execute("Connect", new Object[] { "localhost:1337", new User("Mojken", "") });

		new Input();

		new Thread(() -> {
			while (true) {
				if (Input.keyboard.get(0x39) != null && Input.keyboard.get(0x39) == 1) {
					floor.move(new Vector3f(.3f, 0, 0));
					Input.keyboard.put(0x39, 2);
					try {
						Thread.sleep(100);
					} catch (Exception e) {
					}
				}
			}
		}).start();

	}

	@Override
	public void handleMessage(Message message) {
		switch (message.getMessage().toLowerCase()) {
		case "slae init":
			init();
			break;
		case "server":
			if(message.getData().equals("Mojken: Nibbay"))
			SLAE.execute("Send", new Object[] {"Mojken", "Dawg"});
		}
	}

	void drawIso(int x, int y, int size) {
		drawIso(x, y, 0, size);
	}

	void drawIso(int x, int y, double z, int size) {
		String shaderPath = SLAETester.class.getResource("/shaders/Texture.shader").getPath();

		Polygon shape = new Polygon();
		Vector2d isop = ISO.isoToOrtho(new Vector3d(x * size, y * size, heightmap.get(new Vector2i(x, y)) * size));
		shape.addPoint((int) isop.x, (int) isop.y);
		isop = ISO.isoToOrtho(new Vector3d(x * size, y * size + size, heightmap.get(new Vector2i(x, y + 1)) * size));
		shape.addPoint((int) isop.x, (int) isop.y);
		isop = ISO.isoToOrtho(
				new Vector3d(x * size + size, y * size + size, heightmap.get(new Vector2i(x + 1, y + 1)) * size));
		shape.addPoint((int) isop.x, (int) isop.y);
		isop = ISO.isoToOrtho(new Vector3d(x * size + size, y * size, heightmap.get(new Vector2i(x + 1, y)) * size));
		shape.addPoint((int) isop.x, (int) isop.y);

		floor.addChild(new GameObject(shape, SLAETester.class.getResource("/sand.png").getPath(), shaderPath));
	}
}