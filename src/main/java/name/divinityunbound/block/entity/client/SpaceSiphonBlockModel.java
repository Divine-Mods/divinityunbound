package name.divinityunbound.block.entity.client;

import name.divinityunbound.DivinityUnbound;
import name.divinityunbound.block.entity.SpaceSiphonBlockEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class SpaceSiphonBlockModel extends GeoModel<SpaceSiphonBlockEntity> {
    @Override
    public Identifier getModelResource(SpaceSiphonBlockEntity animatable) {
        return new Identifier(DivinityUnbound.MOD_ID, "geo/space_siphon.geo.json");
    }

    @Override
    public Identifier getTextureResource(SpaceSiphonBlockEntity animatable) {
        return new Identifier(DivinityUnbound.MOD_ID, "textures/block/space_siphon.png");
    }

    @Override
    public Identifier getAnimationResource(SpaceSiphonBlockEntity animatable) {
        return new Identifier(DivinityUnbound.MOD_ID, "animations/space_siphon.animation.json");
    }
}
