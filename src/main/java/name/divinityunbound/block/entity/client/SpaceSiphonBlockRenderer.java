package name.divinityunbound.block.entity.client;

import name.divinityunbound.block.entity.SpaceSiphonBlockEntity;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class SpaceSiphonBlockRenderer extends GeoBlockRenderer<SpaceSiphonBlockEntity> {
    public SpaceSiphonBlockRenderer(BlockEntityRendererFactory.Context context) {
        super(new SpaceSiphonBlockModel());
    }
}
