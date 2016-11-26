package com.mygdx.lildrak.entity.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.mygdx.lildrak.Lildrak;
import com.mygdx.lildrak.component.ColorComponent;
import com.mygdx.lildrak.component.TextureComponent;
import com.mygdx.lildrak.component.TransformComponent;
import com.mygdx.lildrak.entity.Mappers;

import java.util.Comparator;

public class RenderSystem extends SortedIteratingSystem {

    public RenderSystem(int priority) {
        super(Family.all(TextureComponent.class, TransformComponent.class).get(), new ZComparator(), priority);
    }

    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent transform = Mappers.transform.get(entity);
        TextureComponent textureComponent = Mappers.textureComponent.get(entity);
        ColorComponent colorComponent = Mappers.colorComponent.get(entity);
        Color initialColor = Lildrak.spriteBatch.getColor();

        if (colorComponent != null) Lildrak.spriteBatch.setColor(colorComponent.color);

        float scale = 1f;
        float width = textureComponent.region.getRegionWidth();
        float height = textureComponent.region.getRegionHeight();
        float originX = width * 0.5f;
        float originY = height * 0.5f;

        Lildrak.spriteBatch.draw(textureComponent.region,
                transform.x - originX,
                transform.y - originY,
                originX,
                originY,
                width,
                height,
                scale,
                scale,
                transform.angle);

        Lildrak.spriteBatch.setColor(initialColor);
    }

    // Inner class has to be static, otherwise system can't call the supertype constructor
    private static class ZComparator implements Comparator<Entity> {
        @Override
        public int compare(Entity e1, Entity e2) {
            return (int) Math.signum(Mappers.transform.get(e1).z - Mappers.transform.get(e2).z);
        }
    }
}
