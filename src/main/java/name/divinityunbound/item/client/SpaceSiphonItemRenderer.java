package name.divinityunbound.item.client;

import name.divinityunbound.item.custom.SpaceSiphonItem;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class SpaceSiphonItemRenderer extends GeoItemRenderer<SpaceSiphonItem> {
    public SpaceSiphonItemRenderer() {
        super(new SpaceSiphonItemModel());
    }
}
