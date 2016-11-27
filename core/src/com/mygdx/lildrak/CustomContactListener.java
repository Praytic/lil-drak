package com.mygdx.lildrak;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.lildrak.component.*;
import com.mygdx.lildrak.entity.Mappers;
import org.springframework.stereotype.Component;

@Component
public class CustomContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        if (fixtureA.getUserData() != null && fixtureB.getUserData() != null) {
            Entity entityA = (Entity) fixtureA.getUserData();
            Entity entityB = (Entity) fixtureB.getUserData();
            processDamagingCollision(entityA, entityB);
            processScoreCollision(entityA, entityB, contact);
        }
    }

    @Override
    public void endContact(Contact contact) {
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }

    private void processDamagingCollision(Entity entityA, Entity entityB) {
        if (Mappers.damage.has(entityA) && Mappers.health.has(entityB)) {
            if (!(isInvincible(entityB) && Mappers.playerMovement.has(entityB))) {
                DamageComponent dc = Mappers.damage.get(entityA);
                HealthComponent hc = Mappers.health.get(entityB);

                hc.health -= dc.damage;

                if (Mappers.hurtSound.has(entityB)) {
                    HurtSoundComponent sc = Mappers.hurtSound.get(entityB);
                    sc.sound.play();
                }
                if (Mappers.playerMovement.has(entityB)) GameScreen.playerHealth -= dc.damage;

                if (Mappers.invincibility.has(entityB)) {
                    InvincibilityComponent ic = Mappers.invincibility.get(entityB);
                    ic.counter = ic.duration;
                }
            }
        }
        if (Mappers.damage.has(entityB) && Mappers.health.has(entityA)) {
            if (!(isInvincible(entityA) && Mappers.playerMovement.has(entityA))) {
                DamageComponent dc = Mappers.damage.get(entityB);
                HealthComponent hc = Mappers.health.get(entityA);

                hc.health -= dc.damage;

                if (Mappers.hurtSound.has(entityA)) {
                    HurtSoundComponent sc = Mappers.hurtSound.get(entityA);
                    sc.sound.play();
                }
                if (Mappers.playerMovement.has(entityA)) GameScreen.playerHealth -= dc.damage;
                if (Mappers.invincibility.has(entityA)) {
                    InvincibilityComponent ic = Mappers.invincibility.get(entityA);
                    ic.counter = ic.duration;
                }
            }
        }
    }

    private void processScoreCollision(Entity entityA, Entity entityB, Contact contact) {
        if (Mappers.score.has(entityA)) {
            ScoreComponent sc = Mappers.score.get(entityA);
            GameScreen.score += sc.score;
            contact.setEnabled(false);
        }
        if (Mappers.score.has(entityB)) {
            ScoreComponent sc = Mappers.score.get(entityB);
            GameScreen.score += sc.score;
            contact.setEnabled(false);
        }
    }

    private boolean isInvincible(Entity entity) {
        if (Mappers.invincibility.has(entity)) {
            InvincibilityComponent ic = Mappers.invincibility.get(entity);

            return (ic.counter > 0);
        }
        return false;
    }
}
