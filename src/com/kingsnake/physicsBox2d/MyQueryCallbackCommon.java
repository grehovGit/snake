package com.kingsnake.physicsBox2d;

import java.util.List;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.QueryCallback;

public class MyQueryCallbackCommon implements QueryCallback {
	
	private final List<Fixture> fixList;
	
	MyQueryCallbackCommon (List<Fixture> fList) {
		fixList = fList;
	}

	@Override
	public boolean reportFixture(Fixture fixture) {
		if (!fixture.isSensor())
			fixList.add(fixture);
		return true;
	}

}
