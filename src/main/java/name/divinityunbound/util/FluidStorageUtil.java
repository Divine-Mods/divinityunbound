package name.divinityunbound.util;

import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StoragePreconditions;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import team.reborn.energy.api.EnergyStorage;

/**
 * Helper functions to work with {@link net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage}s.
 * Based on Tech Reborn EnergyStorageUtil
 */
@SuppressWarnings({"unused", "deprecation", "UnstableApiUsage"})
public class FluidStorageUtil {
    /**
     * Move fluid between two fluid storages, and return the amount that was successfully moved.
     *
     * @param from The source storage. May be null.
     * @param to The target storage. May be null.
     * @param maxAmount The maximum amount that may be moved.
     * @param transaction The transaction this transfer is part of,
     *                    or {@code null} if a transaction should be opened just for this transfer.
     * @return The amount of energy that was successfully moved.
     */
    public static long move(@Nullable Storage<FluidVariant> from, @Nullable Storage<FluidVariant> to, FluidVariant variant, long maxAmount, @Nullable TransactionContext transaction) {
        if (from == null || to == null) return 0;

        StoragePreconditions.notNegative(maxAmount);

        // Simulate extraction first.
        long maxExtracted;

        try (Transaction extractionTestTransaction = Transaction.openNested(transaction)) {
            maxExtracted = from.extract(variant, maxAmount, extractionTestTransaction);
        }

        try (Transaction moveTransaction = Transaction.openNested(transaction)) {
            // Then insert what can be extracted.
            long accepted = to.insert(variant, maxExtracted, moveTransaction);

            // Extract for real.
            if (from.extract(variant, accepted, moveTransaction) == accepted) {
                // Commit if the amounts match.
                moveTransaction.commit();
                return accepted;
            }
        }

        return 0;
    }

    /**
     * Return true if the passed stack offers a fluid storage through {@link net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage#ITEM}.
     * This can typically be used for inventories or slots that want to accept fluid storages only.
     */
    public static boolean isFluidStorage(ItemStack stack) {
        return ContainerItemContext.withConstant(stack).find(FluidStorage.ITEM) != null;
    }

    private FluidStorageUtil() {
    }
}
