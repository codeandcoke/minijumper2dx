package com.codeandcoke.minijumper.characters.items;

import com.codeandcoke.minijumper.managers.ResourceManager;

/**
 * Globos que el personaje puede coger
 * @author Santiago Faci
 * @version curso 2014-2015
 */
public class Balloon extends Item {

    public Balloon(float x, float y, int score) {
        super(x, y);
        this.score = score;

        currentFrame = ResourceManager.getRegion("balloon");
        rect.x = x;
        rect.y = y;
        rect.width = currentFrame.getRegionWidth();
        rect.height = currentFrame.getRegionHeight();
    }

    @Override
    public void update(float dt) {

    }
}
