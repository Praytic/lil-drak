package com.mygdx.lildrak.entity.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.mygdx.lildrak.component.ColorComponent;
import com.mygdx.lildrak.component.TextureComponent;
import com.mygdx.lildrak.component.TransformComponent;
import com.mygdx.lildrak.entity.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.mygdx.lildrak.CustomApplicationAdapter.spriteBatch;

@Component
public class RenderSystem extends SortedIteratingSystem {


    @Autowired
    public RenderSystem(Engine engine) {
        super(Family.all(TextureComponent.class, TransformComponent.class).get(), (e1, e2) ->
                (int) Math.signum(Mappers.transform.get(e1).z - Mappers.transform.get(e2).z), 1);
        engine.addSystem(this);
    }

    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent transform = Mappers.transform.get(entity);
        TextureComponent textureComponent = Mappers.textureComponent.get(entity);
        ColorComponent colorComponent = Mappers.colorComponent.get(entity);
        Color initialColor = spriteBatch.getColor();

        if (colorComponent != null) spriteBatch.setColor(colorComponent.color);

        float scale = 1f;
        float width = textureComponent.region.getRegionWidth();
        float height = textureComponent.region.getRegionHeight();
        float originX = width * 0.5f;
        float originY = height * 0.5f;

        spriteBatch.draw(textureComponent.region,
                transform.x - originX, transform.y - originY,
                originX, originY,
                width, height,
                scale, scale,
                transform.angle);
        spriteBatch.setColor(initialColor);
    }
}
