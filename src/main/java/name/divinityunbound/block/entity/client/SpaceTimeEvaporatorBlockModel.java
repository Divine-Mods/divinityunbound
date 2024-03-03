package name.divinityunbound.block.entity.client;

import name.divinityunbound.DivinityUnbound;
import name.divinityunbound.block.entity.SpaceSiphonBlockEntity;
import name.divinityunbound.block.entity.SpaceTimeEvaporatorBlockEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class SpaceTimeEvaporatorBlockModel extends GeoModel<SpaceTimeEvaporatorBlockEntity> {
    @Override
    public Identifier getModelResource(SpaceTimeEvaporatorBlockEntity animatable) {
        return new Identifier(DivinityUnbound.MOD_ID, "geo/space_time_evaporator.geo.json");
    }

    @Override
    public Identifier getTextureResource(SpaceTimeEvaporatorBlockEntity animatable) {
        return new Identifier(DivinityUnbound.MOD_ID, "textures/block/space_time_evaporator.png");
    }

    @Override
    public Identifier getAnimationResource(SpaceTimeEvaporatorBlockEntity animatable) {
        return new Identifier(DivinityUnbound.MOD_ID, "animations/space_time_evaporator.animation.json");
    }
}
