package llama.thaumcraft.blocks.entity;

import llama.thaumcraft.magic.Aspect;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;

import java.util.HashMap;
import java.util.Map;

public class CrucibleBlockEntity extends BlockEntity {
    private HashMap<String, Integer> aspects = new HashMap<>();
    private byte boilingPercentage = 0;

    public CrucibleBlockEntity(BlockPos pos, BlockState state) {
        super(ThaumcraftBlockEntities.CRUCIBLE_BLOCK_ENTITY_TYPE, pos, state);
    }

    public byte getBoilingPercentage() {
        return this.boilingPercentage;
    }

    public void addBoilingPercentage(int boilingPercentage) {
        this.setBoilingPercentage((byte) (this.getBoilingPercentage() + boilingPercentage));
    }

    public void setBoilingPercentage(byte boilingPercentage) {
        if (boilingPercentage >= 0 && boilingPercentage <= 100) {
            this.boilingPercentage = boilingPercentage;
        }
    }

    public int getAspect(String name) {
        return !this.aspects.isEmpty() ? aspects.get(name) : 0;
    }

    public void setAspect(Aspect aspect, int aspectCount) {
        String aspectName = aspect.getName().toLowerCase();
        this.aspects.put(aspectName, this.aspects.get(aspectName) != null ? (this.aspects.get(aspectName) + aspectCount) : aspectCount);
        this.markDirty();
    }

    @Override
    public void writeNbt(NbtCompound tag) {
        super.writeNbt(tag);
        tag.putByte("boiling_percentage", boilingPercentage);
        if (this.aspects.isEmpty()) return;
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
            boilingPercentage = tag.getByte("boiling_percentage");
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

    @Override
    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }
}