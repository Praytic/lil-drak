package com.mygdx.lildrak.entity.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.lildrak.entity.Mappers;
import com.mygdx.lildrak.entity.components.ColorComponent;
import com.mygdx.lildrak.entity.components.Transform;
import com.mygdx.lildrak.entity.components.TextureComponent;
import com.mygdx.lildrak.Lildrak;

import java.util.Comparator;

public class RenderSystem extends SortedIteratingSystem
{
    private SpriteBatch batch;

    public RenderSystem(int priority)
    {
        super(Family.all(TextureComponent.class, Transform.class).get(), new ZComparator(), priority);
        batch = Lildrak.spriteBatch;
    }

    protected void processEntity(Entity entity, float deltaTime)
    {
        Transform transform = Mappers.transform.get(entity);
        TextureComponent textureComponent = Mappers.textureComponent.get(entity);
        ColorComponent colorComponent = Mappers.colorComponent.get(entity);
        Color initialColor = batch.getColor();

        if (colorComponent != null) batch.setColor(colorComponent.color);

        float scale = 1f;
        float width = textureComponent.region.getRegionWidth();
        float height = textureComponent.region.getRegionHeight();
        float originX = width * 0.5f;
        float originY = height * 0.5f;

        batch.draw(textureComponent.region,
                    transform.x - originX,
                    transform.y - originY,
                    originX,
                    originY,
                    width,
                    height,
                    scale,
                    scale,
                    transform.angle);

        batch.setColor(initialColor);
    }

    // Inner class has to be static, otherwise system can't call the supertype constructor
    private static class ZComparator implements Comparator<Entity>
    {
        @Override
        public int compare(Entity e1, Entity e2)
        {
            return (int)Math.signum(Mappers.transform.get(e1).z - Mappers.transform.get(e2).z);
        }
    }
}
