package game.hud;

import org.newdawn.slick.Input;

/**
 * HUD that always displays and never interacts
 * with the player, such as Minimap. Implementations
 * are not bound to those rules; Minimap uses 'm' to
 * toggle visibility (and eventually type).
 */
public abstract class PassiveHUD extends HUD {
    
    protected boolean visible;
    
    public PassiveHUD(boolean visible) {
        super();
        this.visible = visible;
    }
    
    @Override
    public void respondToUserInput(Input in) { }
    
    @Override
    public boolean isVisible() {
        return visible;
    }
}
