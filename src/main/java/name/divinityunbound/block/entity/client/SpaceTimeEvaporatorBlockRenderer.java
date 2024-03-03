package name.divinityunbound.block.entity.client;

import name.divinityunbound.block.entity.SpaceSiphonBlockEntity;
import name.divinityunbound.block.entity.SpaceTimeEvaporatorBlockEntity;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class SpaceTimeEvaporatorBlockRenderer extends GeoBlockRenderer<SpaceTimeEvaporatorBlockEntity> {
    public SpaceTimeEvaporatorBlockRenderer(BlockEntityRendererFactory.Context context) {
        super(new SpaceTimeEvaporatorBlockModel());
    }
}
