package crystalspider.leatheredboots.handlers;

import crystalspider.leatheredboots.LeatheredBootsLoader;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

/**
 * {@link CreativeModeTabEvent} handler.
 */
@EventBusSubscriber(modid = LeatheredBootsLoader.MODID, bus = Bus.MOD)
public class CreativeModeTabEventHandler {
  /**
   * Registers the creative mode tab for leathered boots.
   * 
   * @param event
   */
  @SubscribeEvent
  public static void handle(CreativeModeTabEvent.Register event)  {
    event.registerCreativeModeTab(
      new ResourceLocation(LeatheredBootsLoader.MODID, LeatheredBootsLoader.LEATHERED_BOOTS_TAB_ID),
      builder -> builder
        .icon(() -> LeatheredBootsLoader.NETHERITE_LEATHERED_BOOTS.get().getDefaultInstance())
        .title(Component.translatable("itemGroup." + LeatheredBootsLoader.MODID + "." + LeatheredBootsLoader.LEATHERED_BOOTS_TAB_ID))
        .displayItems((features, output, hasPermissions) -> {
          output.accept(LeatheredBootsLoader.CHAINMAIL_LEATHERED_BOOTS.get().getDefaultInstance());
          output.accept(LeatheredBootsLoader.IRON_LEATHERED_BOOTS.get().getDefaultInstance());
          output.accept(LeatheredBootsLoader.GOLDEN_LEATHERED_BOOTS.get().getDefaultInstance());
          output.accept(LeatheredBootsLoader.DIAMOND_LEATHERED_BOOTS.get().getDefaultInstance());
          output.accept(LeatheredBootsLoader.NETHERITE_LEATHERED_BOOTS.get().getDefaultInstance());
        })
    );
  }
}
