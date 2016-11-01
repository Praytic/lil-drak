package com.mygdx.lildrak.entity.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.lildrak.GameScreen;
import com.mygdx.lildrak.entity.Mappers;
import com.mygdx.lildrak.entity.components.*;

public class CollisionSystem extends EntitySystem
{
    private World box2dworld;
    private MyContactListener contactListener;

    public CollisionSystem()
    {
        this.box2dworld = GameScreen.world;
        this.contactListener = new MyContactListener();
        box2dworld.setContactListener(contactListener);
    }

    public class MyContactListener implements ContactListener
    {
        @Override
        public void beginContact(Contact contact)
        {
            Fixture fixtureA = contact.getFixtureA();
            Fixture fixtureB = contact.getFixtureB();
            if (fixtureA.getUserData() != null && fixtureB.getUserData() != null)
            {
                Entity entityA = (Entity) fixtureA.getUserData();
                Entity entityB = (Entity) fixtureB.getUserData();
                processDamagingCollision(entityA, entityB);
                processScoreCollision(entityA, entityB, contact);
            }
        }
        @Override
        public void endContact(Contact contact)
        {
        }
        @Override
        public void preSolve(Contact contact, Manifold oldManifold)
        {
        }
        @Override
        public void postSolve(Contact contact, ContactImpulse impulse)
        {
        }
    }

    private void processDamagingCollision(Entity entityA, Entity entityB)
    {
        if (Mappers.damage.has(entityA) && Mappers.health.has(entityB))
        {
            if (!(isInvincible(entityB) && Mappers.playerMovement.has(entityB)))
            {
                Damage dc = Mappers.damage.get(entityA);
                Health hc = Mappers.health.get(entityB);

                hc.health -= dc.damage;

                if (Mappers.hurtSound.has(entityB))
                {
                    HurtSound sc = Mappers.hurtSound.get(entityB);
                    sc.sound.play();
                }
                if (Mappers.playerMovement.has(entityB)) GameScreen.playerHealth -= dc.damage;

                if (Mappers.invincibility.has(entityB))
                {
                    Invincibility ic = Mappers.invincibility.get(entityB);
                    ic.counter = ic.duration;
                }
            }
        }
        if (Mappers.damage.has(entityB) && Mappers.health.has(entityA))
        {
            if (!(isInvincible(entityA) && Mappers.playerMovement.has(entityA)))
            {
                Damage dc = Mappers.damage.get(entityB);
                Health hc = Mappers.health.get(entityA);

                hc.health -= dc.damage;

                if (Mappers.hurtSound.has(entityA))
                {
                    HurtSound sc = Mappers.hurtSound.get(entityA);
                    sc.sound.play();
                }
                if (Mappers.playerMovement.has(entityA)) GameScreen.playerHealth -= dc.damage;
                if (Mappers.invincibility.has(entityA))
                {
                    Invincibility ic = Mappers.invincibility.get(entityA);
                    ic.counter = ic.duration;
                }
            }
        }
    }

    private void processScoreCollision(Entity entityA, Entity entityB, Contact contact)
    {
        if (Mappers.score.has(entityA))
        {
            Score sc = Mappers.score.get(entityA);
            GameScreen.score += sc.score;
            contact.setEnabled(false);
        }
        if (Mappers.score.has(entityB))
        {
            Score sc = Mappers.score.get(entityB);
            GameScreen.score += sc.score;
            contact.setEnabled(false);
        }
    }

    private boolean isInvincible(Entity entity)
    {
        if (Mappers.invincibility.has(entity))
        {
            Invincibility ic = Mappers.invincibility.get(entity);

            return (ic.counter > 0);
        }
        return false;
    }
}