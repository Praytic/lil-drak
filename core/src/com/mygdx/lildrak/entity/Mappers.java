package com.mygdx.lildrak.entity;

import com.badlogic.ashley.core.ComponentMapper;
import com.mygdx.lildrak.component.*;

public class Mappers
{
    public static final ComponentMapper<BatAnimationComponent> batAnimation = ComponentMapper.getFor(BatAnimationComponent.class);
    public static final ComponentMapper<FireAnimationComponent> fireAnimation = ComponentMapper.getFor(FireAnimationComponent.class);
    public static final ComponentMapper<BodyComponent> bodyComponent = ComponentMapper.getFor(BodyComponent.class);
    public static final ComponentMapper<ColorComponent> colorComponent = ComponentMapper.getFor(ColorComponent.class);
    public static final ComponentMapper<DamageComponent> damage = ComponentMapper.getFor(DamageComponent.class);
    public static final ComponentMapper<HealthComponent> health = ComponentMapper.getFor(HealthComponent.class);
    public static final ComponentMapper<HorizontalLimitComponent> horizontalLimit = ComponentMapper.getFor(HorizontalLimitComponent.class);
    public static final ComponentMapper<HurtSoundComponent> hurtSound = ComponentMapper.getFor(HurtSoundComponent.class);
    public static final ComponentMapper<InvincibilityComponent> invincibility = ComponentMapper.getFor(InvincibilityComponent.class);
    public static final ComponentMapper<OriginalPositionComponent> originalPosition = ComponentMapper.getFor(OriginalPositionComponent.class);
    public static final ComponentMapper<PlayerMovementComponent> playerMovement = ComponentMapper.getFor(PlayerMovementComponent.class);
    public static final ComponentMapper<TransformComponent> transform = ComponentMapper.getFor(TransformComponent.class);
    public static final ComponentMapper<ScoreComponent> score = ComponentMapper.getFor(ScoreComponent.class);
    public static final ComponentMapper<TextureComponent> textureComponent = ComponentMapper.getFor(TextureComponent.class);
    public static final ComponentMapper<VerticalLimitComponent> verticalLimit = ComponentMapper.getFor(VerticalLimitComponent.class);
}