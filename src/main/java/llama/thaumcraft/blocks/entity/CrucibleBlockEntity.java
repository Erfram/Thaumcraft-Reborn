package llama.thaumcraft.blocks.entity;

import llama.thaumcraft.magic.Aspect;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;

import java.util.HashMap;
import java.util.Map;

public class CrucibleBlockEntity extends BlockEntity {
    private HashMap<String, Integer> aspects = new HashMap<>();

    public CrucibleBlockEntity(BlockPos pos, BlockState state) {
        super(ThaumcraftBlockEntities.CRUCIBLE_BLOCK_ENTITY_TYPE, pos, state);
    }

    public int getAspect(String name) {
        return hasAspects() ? aspects.get(name) : 0;
    }

    public void setAspect(Aspect aspect, int aspectCount) {
        String aspectName = aspect.getName().toLowerCase();
        this.aspects.put(aspectName, this.aspects.get(aspectName) != null ? (this.aspects.get(aspectName) + aspectCount) : aspectCount);
    }

    public boolean hasAspects() {
        return !aspects.isEmpty();
    }

    @Override
    public void writeNbt(NbtCompound tag) {
        super.writeNbt(tag);
        if (!hasAspects()) return;
        NbtCompound aspectsCompound = tag.getCompound("aspects");
        for (Map.Entry<String, Integer> entry : aspects.entrySet()) {
            String aspectName = entry.getKey();
            Integer aspectCount = entry.getValue();
            if (aspectCount > 0) aspectsCompound.putInt(aspectName, aspectCount);
        }
        tag.put("aspects", aspectsCompound);
    }

    @Override
    public void readNbt(NbtCompound tag) {
        super.readNbt(tag);
        if (tag.contains("aspects")) {
            NbtCompound aspectsCompound = tag.getCompound("aspects");
            for (String aspectName : aspectsCompound.getKeys()) {
                Integer aspectCount = aspectsCompound.getInt(aspectName);
                aspects.put(aspectName, aspectCount);
            }
        }
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }

    private void updateListeners() {
        this.markDirty();
        if(this.getWorld() != null) {
            this.getWorld().updateListeners(this.getPos(), this.getCachedState(), this.getCachedState(), Block.NOTIFY_ALL);
        }
    }

    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }
}