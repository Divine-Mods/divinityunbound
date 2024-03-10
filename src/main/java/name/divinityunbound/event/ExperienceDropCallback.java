package name.divinityunbound.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;

public interface ExperienceDropCallback {
    Event<ExperienceDropCallback> EVENT = EventFactory.createArrayBacked(ExperienceDropCallback.class,
            (listeners) -> (player, exp) -> {
                for (ExperienceDropCallback listener : listeners) {
                    ActionResult result = listener.interact(player, exp);

                    if(result != ActionResult.PASS) {
                        return result;
                    }
                }

                return ActionResult.PASS;
            });

    ActionResult interact(PlayerEntity player, PlayerEntity exp);
}