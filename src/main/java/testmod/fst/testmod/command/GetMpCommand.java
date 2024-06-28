package testmod.fst.testmod.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import testmod.fst.testmod.capability.myMP.PlayerMpProvider;

public class GetMpCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("mp").executes((context) -> {
            context.getSource().getPlayer().getCapability(PlayerMpProvider.PLAYER_MP_CAPABILITY).ifPresent((mp) -> {
                context.getSource().sendSuccess(() -> Component.literal("mp："+mp.getMp()),false);
            });
            return 0;
        }));
    }
}
