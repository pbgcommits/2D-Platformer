package entities;

/**
 * Used to implement entities which can take damage.
 */
public interface Damageable {
    /**
     * Reduce the entity's health.
     * @param damage The amount by which to reduce the entity's health.
     */
    void takeDamage(double damage);

    /**
     * Play the entity's death animation.
     */
    void die();
}
