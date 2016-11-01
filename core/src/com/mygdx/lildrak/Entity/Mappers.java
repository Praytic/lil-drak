package com.mygdx.lildrak.entity;

import com.badlogic.ashley.core.ComponentMapper;
import com.mygdx.lildrak.entity.components.*;

public class Mappers
{
    public static final ComponentMapper<BatAnimation> batAnimation = ComponentMapper.getFor(BatAnimation.class);
    public static final ComponentMapper<BodyComponent> bodyComponent = ComponentMapper.getFor(BodyComponent.class);
    public static final ComponentMapper<ColorComponent> colorComponent = ComponentMapper.getFor(ColorComponent.class);
    public static final ComponentMapper<Damage> damage = ComponentMapper.getFor(Damage.class);
    public static final ComponentMapper<Health> health = ComponentMapper.getFor(Health.class);
    public static final ComponentMapper<HorizontalLimit> horizontalLimit = ComponentMapper.getFor(HorizontalLimit.class);
    public static final ComponentMapper<HurtSound> hurtSound = ComponentMapper.getFor(HurtSound.class);
    public static final ComponentMapper<Invincibility> invincibility = ComponentMapper.getFor(Invincibility.class);
    public static final ComponentMapper<OriginalPosition> originalPosition = ComponentMapper.getFor(OriginalPosition.class);
    public static final ComponentMapper<PlayerMovement> playerMovement = ComponentMapper.getFor(PlayerMovement.class);
    public static final ComponentMapper<Transform> transform = ComponentMapper.getFor(Transform.class);
    public static final ComponentMapper<Score> score = ComponentMapper.getFor(Score.class);
    public static final ComponentMapper<TextureComponent> textureComponent = ComponentMapper.getFor(TextureComponent.class);
    public static final ComponentMapper<VerticalLimit> verticalLimit = ComponentMapper.getFor(VerticalLimit.class);
}