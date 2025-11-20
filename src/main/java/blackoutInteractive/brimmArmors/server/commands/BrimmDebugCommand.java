package blackoutInteractive.brimmArmors.server.commands;

import java.util.Arrays;
import java.util.stream.Collectors;

import com.mojang.brigadier.arguments.StringArgumentType;

import blackoutInteractive.brimmArmors.BrimmArmors;
import blackoutInteractive.brimmArmors.common.items.BasicArmor;
import blackoutInteractive.brimmArmors.common.packets.PatchesAdjusterDebugPacket;
import blackoutInteractive.brimmArmors.common.registries.ItemRegistry;
import blackoutInteractive.ema_08_.birgadierWrapper.CommandBuilder;
import blackoutInteractive.ema_08_.birgadierWrapper.CommandParts.CommandArgument;
import blackoutInteractive.ema_08_.birgadierWrapper.CommandParts.ExtensibleCommandPart;
import blackoutInteractive.ema_08_.birgadierWrapper.CommandParts.PermissionChecker;
import blackoutInteractive.ema_08_.birgadierWrapper.IRegistrableCommand;
import blackoutInteractive.ema_08_.rendering.overlay.OverlayPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;

public class BrimmDebugCommand implements IRegistrableCommand {
	
public static final String LITERAL = "brimmDebug";
	
	private static final PermissionChecker permChecker = (src) -> src.hasPermission(2);

	@Override
	public CommandBuilder getBuilt() {
		CommandBuilder builder = new CommandBuilder(LITERAL, permChecker);
		ExtensibleCommandPart patchesAdjusterSub = builder.addSubCommand("patchesAdjuster");
		patchesAdjusterSub.addEndPoint(CommandArgument.group(
				CommandArgument.newArg("armorItem", StringArgumentType.word()),
				CommandArgument.newArg("overlayPos", StringArgumentType.word())), (ctx) -> {
					if (ctx.getSource().isPlayer()) {
						String armorItemName = ctx.getArgument("armorItem", String.class);
						String overlayPos = ctx.getArgument("overlayPos", String.class);
						OverlayPos op = null;
						try {
							op = OverlayPos.valueOf(overlayPos);
						} catch (IllegalArgumentException e) {
							ctx.getSource().sendFailure(Component.literal(
									"Invalid overlay position "+overlayPos+" was specified: only acceptable positions are: "+
									String.join(", ", Arrays.stream(
											OverlayPos.values()).map(OverlayPos::name).collect(Collectors.toList()))+"."));
							return 0;
						}
						Item armor = ItemRegistry.get(armorItemName).orElse(null);
						if (armor != null && armor instanceof BasicArmor) {
							ctx.getSource().sendSuccess(
									()->Component.literal("Intialising patch adjuster for "+armorItemName+" armor."), false);
							BrimmArmors.network.sendTo(new PatchesAdjusterDebugPacket(armorItemName, op),
									ctx.getSource().getPlayer());
						} else {
							ctx.getSource().sendFailure(Component.literal(
									(armor == null) ?
										"No Item named "+armorItemName+" could be found." :
										"Item named "+armorItemName+" is not an armor."
							    ));
						}
					} else {
						ctx.getSource().sendFailure(Component.literal("Only players can execute this command."));
					}
					return 1;
				});
		return builder;		
	}

}
