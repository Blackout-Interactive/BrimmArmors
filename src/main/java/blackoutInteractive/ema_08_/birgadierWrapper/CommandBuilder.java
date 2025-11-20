package blackoutInteractive.ema_08_.birgadierWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import blackoutInteractive.ema_08_.birgadierWrapper.CommandParts.*;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public final class CommandBuilder extends AbstractExtensibleCommandPart {
	
	public static LiteralArgumentBuilder<CommandSourceStack> build(CommandBuilder cmd) {
		var root = Commands.literal(cmd.name)
				.requires(source -> cmd.checker.canExecute(source));
		for (CommandPart part : cmd.parts) {
			resolveAndAdd(root, part);
		}
		return root;
	}
	
	private static void resolveAndAdd(
			ArgumentBuilder<CommandSourceStack, ?> parent, CommandPart part) {
		if (part == null) {
			throw new RuntimeException("A null command part was encountered while building");
		} else if (part instanceof EndPoint ep) {
			if (ep.args.length == 0) {
				parent.executes((ctx) -> ep.body.execute(ctx));
			} else {
		        parent.then(resolve(ep.body, ep.args));
			}
		} else if (part instanceof SubCommand sc) {
			var sub = Commands.literal(sc.subName);
			for (CommandPart subPart : sc.parts) resolveAndAdd(sub, subPart);
			parent.then(sub);
		} else {
			throw new RuntimeException("Unrecognised command part of type "+part.getClass().getName());
		}
	}
	
	private static ArgumentBuilder<CommandSourceStack, ?> resolve(CommandBody body, CommandArgument[] args) {
		var currentStackPiece = Commands.argument(args[args.length-1].argName, args[args.length-1].type);
		currentStackPiece.executes((ctx) -> body.execute(ctx));
		for (int i = args.length-2;i>=0;i--) {
			var newStackPiece = Commands.argument(args[i].argName, args[i].type);
			newStackPiece.then(currentStackPiece);
			currentStackPiece = newStackPiece;
		}
		return currentStackPiece;
	}
	
	private final String name;
	private final PermissionChecker checker;
	
	public CommandBuilder(String name, PermissionChecker checker) {
		this.name = Objects.requireNonNull(name);
		this.checker = Objects.requireNonNull(checker);
	}
	
}

abstract class AbstractExtensibleCommandPart implements ExtensibleCommandPart {
	
	protected final List<CommandPart> parts = new ArrayList<>();

	@Override
	public void addEndPoint(CommandArgument[] args, CommandBody body) {
		this.parts.add(new EndPoint(args, body));
		
	}

	@Override
	public ExtensibleCommandPart addSubCommand(String subName) {
		ExtensibleCommandPart subCommand = new SubCommand(subName);
		this.parts.add(subCommand);
		return subCommand;
	}
	
}

final class EndPoint implements ExtensionCommandPart {
	
	protected final CommandArgument[] args;
	protected final CommandBody body;
	
	protected EndPoint(CommandArgument[] args, CommandBody body) {
		this.args = requireNonNull(args);
		this.body = Objects.requireNonNull(body);
	}
	
	private static CommandArgument[] requireNonNull(CommandArgument[] args) {
		for (CommandArgument arg : Objects.requireNonNull(args)) {
			Objects.requireNonNull(arg);
		}
		return args;
	}
	
}

final class SubCommand extends AbstractExtensibleCommandPart {
	
	protected final String subName;
	
	protected SubCommand(String subName) {
		this.subName = Objects.requireNonNull(subName);
	}
	
}
