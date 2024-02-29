package name.divinityunbound.item.client;

import name.divinityunbound.DivinityUnbound;
import name.divinityunbound.block.entity.SpaceSiphonBlockEntity;
import name.divinityunbound.item.custom.SpaceSiphonItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class SpaceSiphonItemModel extends GeoModel<SpaceSiphonItem> {
    @Override
    public Identifier getModelResource(SpaceSiphonItem animatable) {
        return new Identifier(DivinityUnbound.MOD_ID, "geo/space_siphon.geo.json");
    }

    @Override
    public Identifier getTextureResource(SpaceSiphonItem animatable) {
        return new Identifier(DivinityUnbound.MOD_ID, "textures/block/space_siphon.png");
    }

    @Override
    public Identifier getAnimationResource(SpaceSiphonItem animatable) {
        return new Identifier(DivinityUnbound.MOD_ID, "animations/space_siphon.animation.json");
    }
}
