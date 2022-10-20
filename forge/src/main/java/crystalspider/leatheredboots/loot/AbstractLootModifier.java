package crystalspider.leatheredboots.loot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.BiFunction;

import javax.annotation.Nonnull;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * Abstract loot modifier.
 */
public abstract class AbstractLootModifier extends LootModifier {
  /**
   * Additional items to add to the chest loot.
   */
  private final HashMap<ItemStack, Float> additions;

  protected AbstractLootModifier(LootItemCondition[] conditionsIn, HashMap<ItemStack, Float> additions) {
    super(conditionsIn);
    this.additions = additions;
  }

  @Override
  @Nonnull
  protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
    if (generatedLoot == null) {
      generatedLoot = new ArrayList<>();
    }
    for (Entry<ItemStack, Float> entry : additions.entrySet()) {
      if (context.getRandom().nextFloat() <= entry.getValue()) {
        generatedLoot.add(entry.getKey());
      }
    }
    return generatedLoot;
  }

  /**
   * {@link AbstractLootModifier} Serializer.
   */
  protected static abstract class Serializer extends GlobalLootModifierSerializer<AbstractLootModifier> {
    /**
     * Constructor for a {@link AbstractLootModifier}.
     */
    private final BiFunction<LootItemCondition[], HashMap<ItemStack, Float>, AbstractLootModifier> constructor;

    protected Serializer(BiFunction<LootItemCondition[], HashMap<ItemStack, Float>, AbstractLootModifier> constructor) {
      this.constructor = constructor;
    }

    @Override
    public AbstractLootModifier read(ResourceLocation name, JsonObject json, LootItemCondition[] conditionsIn) {
      HashMap<ItemStack, Float> additions = new HashMap<>();
      for (JsonElement jsonElement : GsonHelper.getAsJsonArray(json, "additions")) {
        JsonObject entry = jsonElement.getAsJsonObject();
        additions.put(
          new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(GsonHelper.getAsString(entry, "item"))), GsonHelper.getAsInt(entry, "quantity")),
          GsonHelper.getAsFloat(entry, "chance")
        );
      }
      return constructor.apply(conditionsIn, additions);
    }

    @Override
    public JsonObject write(AbstractLootModifier instance) {
      return makeConditions(instance.conditions);
    }
  }
}