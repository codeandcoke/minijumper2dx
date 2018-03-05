package com.codeandcoke.minijumper.characters.animations;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.codeandcoke.minijumper.characters.SpriteAnimation;
import com.codeandcoke.minijumper.managers.ResourceManager;

/**
 * Clase para las explosiones de cualquier tipo
 * @author Santiago Faci
 * @version curso 2014-2015
 */
public class Explosion extends SpriteAnimation {

    private Animation<TextureRegion> animation;

    public Explosion(float x, float y, String name) {
        super(x, y);
        animation = new Animation(0.15f, ResourceManager.getRegions(name));
    }

    @Override
    public void update(float dt) {

        stateTime += dt;
        currentFrame = animation.getKeyFrame(stateTime);
        if (animation.isAnimationFinished(stateTime))
            die();
    }

    @Override
    public void die() {
        dead = true;
    }

    @Override
    public void resurrect() {
        dead = false;
    }
}
