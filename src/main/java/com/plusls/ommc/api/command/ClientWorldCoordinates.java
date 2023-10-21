package com.plusls.ommc.api.command;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.commands.arguments.coordinates.WorldCoordinate;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

// Modified from brigadier
public class ClientWorldCoordinates implements ClientCoordinates {
    private final WorldCoordinate x;
    private final WorldCoordinate y;
    private final WorldCoordinate z;

    public ClientWorldCoordinates(WorldCoordinate worldCoordinate, WorldCoordinate worldCoordinate2, WorldCoordinate worldCoordinate3) {
        this.x = worldCoordinate;
        this.y = worldCoordinate2;
        this.z = worldCoordinate3;
    }

    @Override
    public Vec3 getPosition(@NotNull FabricClientCommandSource commandSourceStack) {
        Vec3 vec3 = commandSourceStack.getPosition();
        return new Vec3(this.x.get(vec3.x), this.y.get(vec3.y), this.z.get(vec3.z));
    }

    @Override
    public Vec2 getRotation(@NotNull FabricClientCommandSource commandSourceStack) {
        Vec2 vec2 = commandSourceStack.getRotation();
        return new Vec2((float)this.x.get((double)vec2.x), (float)this.y.get((double)vec2.y));
    }

    @Override
    public boolean isXRelative() {
        return this.x.isRelative();
    }

    @Override
    public boolean isYRelative() {
        return this.y.isRelative();
    }

    @Override
    public boolean isZRelative() {
        return this.z.isRelative();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        } else if (!(object instanceof ClientWorldCoordinates)) {
            return false;
        } else {
            ClientWorldCoordinates worldCoordinates = (ClientWorldCoordinates) object;

            if (!this.x.equals(worldCoordinates.x)) {
                return false;
            } else {
                return this.y.equals(worldCoordinates.y) && this.z.equals(worldCoordinates.z);
            }
        }
    }

    @Contract("_ -> new")
    public static @NotNull ClientWorldCoordinates parseInt(@NotNull StringReader stringReader) throws CommandSyntaxException {
        int i = stringReader.getCursor();
        WorldCoordinate worldCoordinate = WorldCoordinate.parseInt(stringReader);
        if (stringReader.canRead() && stringReader.peek() == ' ') {
            stringReader.skip();
            WorldCoordinate worldCoordinate2 = WorldCoordinate.parseInt(stringReader);
            if (stringReader.canRead() && stringReader.peek() == ' ') {
                stringReader.skip();
                WorldCoordinate worldCoordinate3 = WorldCoordinate.parseInt(stringReader);
                return new ClientWorldCoordinates(worldCoordinate, worldCoordinate2, worldCoordinate3);
            } else {
                stringReader.setCursor(i);
                throw Vec3Argument.ERROR_NOT_COMPLETE.createWithContext(stringReader);
            }
        } else {
            stringReader.setCursor(i);
            throw Vec3Argument.ERROR_NOT_COMPLETE.createWithContext(stringReader);
        }
    }

    @Contract("_, _ -> new")
    public static @NotNull ClientWorldCoordinates parseDouble(@NotNull StringReader stringReader, boolean bl) throws CommandSyntaxException {
        int i = stringReader.getCursor();
        WorldCoordinate worldCoordinate = WorldCoordinate.parseDouble(stringReader, bl);
        if (stringReader.canRead() && stringReader.peek() == ' ') {
            stringReader.skip();
            WorldCoordinate worldCoordinate2 = WorldCoordinate.parseDouble(stringReader, false);
            if (stringReader.canRead() && stringReader.peek() == ' ') {
                stringReader.skip();
                WorldCoordinate worldCoordinate3 = WorldCoordinate.parseDouble(stringReader, bl);
                return new ClientWorldCoordinates(worldCoordinate, worldCoordinate2, worldCoordinate3);
            } else {
                stringReader.setCursor(i);
                throw Vec3Argument.ERROR_NOT_COMPLETE.createWithContext(stringReader);
            }
        } else {
            stringReader.setCursor(i);
            throw Vec3Argument.ERROR_NOT_COMPLETE.createWithContext(stringReader);
        }
    }

    @Contract("_, _, _ -> new")
    public static @NotNull ClientWorldCoordinates absolute(double d, double e, double f) {
        return new ClientWorldCoordinates(new WorldCoordinate(false, d), new WorldCoordinate(false, e), new WorldCoordinate(false, f));
    }

    @Contract("_ -> new")
    public static @NotNull ClientWorldCoordinates absolute(@NotNull Vec2 vec2) {
        return new ClientWorldCoordinates(new WorldCoordinate(false, (double)vec2.x), new WorldCoordinate(false, (double)vec2.y), new WorldCoordinate(true, 0.0));
    }

    @Contract(" -> new")
    public static @NotNull ClientWorldCoordinates current() {
        return new ClientWorldCoordinates(new WorldCoordinate(true, 0.0), new WorldCoordinate(true, 0.0), new WorldCoordinate(true, 0.0));
    }

    @Override
    public int hashCode() {
        int i = this.x.hashCode();
        i = 31 * i + this.y.hashCode();
        return 31 * i + this.z.hashCode();
    }
}
