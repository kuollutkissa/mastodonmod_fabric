package com.kuollutkissa.mastodonmod.entity.custom;
import com.kuollutkissa.mastodonmod.entity.ModEntities;
import com.kuollutkissa.mastodonmod.screen.MastodonInventoryScreenHandler;
import com.kuollutkissa.mastodonmod.util.ImplementedInventory;
import com.kuollutkissa.mastodonmod.util.ModSoundEvents;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;


public class MastodonEntity extends TameableEntity implements NamedScreenHandlerFactory, IAnimatable, ImplementedInventory {
    private AnimationFactory cache = GeckoLibUtil
            .createFactory(this);
    private DefaultedList<ItemStack> inventory = DefaultedList.ofSize(27, ItemStack.EMPTY);
    private static final TrackedData<Boolean> CHESTED = DataTracker.registerData(MastodonEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> KARLED = DataTracker.registerData(MastodonEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public MastodonEntity(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return TameableEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 60f)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3f)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 2.0f);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new EscapeDangerGoal(this, 3f));
        this.goalSelector.add(1, new TemptGoal(this, 2f, Ingredient.ofItems(Items.ACACIA_LEAVES), false));
        this.goalSelector.add(1, new AnimalMateGoal(this, 2));
        this.goalSelector.add(1, new FollowParentGoal(this, 3));
        this.goalSelector.add(2, new WanderAroundFarGoal(this, 1f));
        this.goalSelector.add(3, new LookAroundGoal(this));
    }
    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(CHESTED, false);
        this.dataTracker.startTracking(KARLED, false);
    }
    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("chested", this.dataTracker.get(CHESTED));
        nbt.putBoolean("karled", this.dataTracker.get(KARLED));
        Inventories.writeNbt(nbt, inventory);
    }
    public boolean isKarled() {
        return this.dataTracker.get(KARLED);
    }
    private void setKarled(boolean set){
        this.dataTracker.set(KARLED, set);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.getItem() == Items.ACACIA_LEAVES;
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.dataTracker.set(CHESTED, nbt.getBoolean("chested"));
        this.dataTracker.set(KARLED, nbt.getBoolean("karled"));
        Inventories.readNbt(nbt, inventory);
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        Item item = itemStack.getItem();

        Item itemForTaming = Items.ACACIA_SAPLING;
        if (this.world.isClient) {
            boolean bl = itemStack.isOf(Items.ACACIA_LEAVES) && !this.isTamed();
            return bl ? ActionResult.CONSUME : ActionResult.PASS;
        }
        if(item == Items.CARROT){
            this.setKarled(true);
        }
        if (item == itemForTaming && !isTamed()) {
            if (this.world.isClient()) {
                return ActionResult.CONSUME;
            } else {
                if (!player.getAbilities().creativeMode) {
                    itemStack.decrement(1);
                }

                if (!this.world.isClient()) {
                    super.setOwner(player);
                    this.navigation.recalculatePath();
                    this.setTarget(null);
                    this.world.sendEntityStatus(this, (byte)7);
                    setSitting(true);
                }

                return ActionResult.SUCCESS;
            }
        }
        if(this.isTamed() && !this.isChested() && item == Items.CHEST && !this.isBaby()) {
            this.setChested(true);

        }
        if(!world.isClient() && this != null && this.isChested()) {
            player.openHandledScreen(this);
        }
        return super.interactMob(player, hand);
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("entity.mastodonmod.mastodont");
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        if(this.isKarled()){
            return ModSoundEvents.MASTODON_AMBIENT_NORLUND;
        }
        return ModSoundEvents.MASTODON_AMBIENT;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        if(this.isKarled()){
            return ModSoundEvents.MASTODON_HURT_NORLUND;
        }
        return ModSoundEvents.MASTODON_HURT;

    }

    //TAMING
    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return ModEntities.MASTODONT.create(world);
    }


    @Override
    public boolean isSitting() {
        return false;
    }

    @Override
    public void setSitting(boolean sitting) {
        super.setSitting(false);
    }

    @Override
    public boolean canBeLeashedBy(PlayerEntity player) {
        return super.canBeLeashedBy(player);
    }

    //INVENTORY
    @Override
    public DefaultedList<ItemStack> getItems() {
        return this.inventory;
    }

    public boolean isChested() {
        return this.dataTracker.get(CHESTED);
    }

    public void setChested(boolean set){
        this.dataTracker.set(CHESTED, set);
    }
    @Override
    protected void dropInventory() {
        super.dropInventory();
        for(ItemStack i: inventory){
            for(int j = 0; j< i.getCount(); j++){
                this.dropItem(i.getItem());
            }
        }
        if (this.isChested()) {
            if (!this.world.isClient) {
                this.dropItem(Blocks.CHEST);
            }

            this.setChested(false);
        }

    }


    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new MastodonInventoryScreenHandler(syncId, inv, this);
    }

    //ANIMATIONS
    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> state) {
        if(state.isMoving()){
            state.getController().setAnimation(new AnimationBuilder().addAnimation("move"));
            return PlayState.CONTINUE;
        }
        state.getController().setAnimation(new AnimationBuilder().addAnimation("animation.mastodon.idle"));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData controllerRegistrar) {
        controllerRegistrar.addAnimationController(new AnimationController<>(this, "controller",
                0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return cache;
    }


}
