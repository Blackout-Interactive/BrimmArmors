package blackoutInteractive.ema_08_.birgadierWrapper;

import java.util.Objects;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;

import net.minecraft.commands.CommandSourceStack;

public final class CommandParts {
	
	private CommandParts() {}
	
	public static interface CommandPart {}
	
	public static interface ExtensionCommandPart extends CommandPart {}
	
	public static interface ExtensibleCommandPart extends CommandPart {
		
		void addEndPoint(CommandArgument[] args, CommandBody body);
		
		ExtensibleCommandPart addSubCommand(String subName);
		
	}
	
	public static final class CommandArgument {
		
		public static CommandArgument newArg(String argName, ArgumentType<?> type) {
			return new CommandArgument(Objects.requireNonNull(argName), Objects.requireNonNull(type));
		}
		
		public final String argName;
		public final ArgumentType<?> type;
		
		private CommandArgument(String argName, ArgumentType<?> type) {
			this.argName = argName; this.type =type;
		}
		
		public static CommandArgument[] group(CommandArgument... args) {
			return args;
		}
		
		public static CommandArgument[] empty() { return new CommandArgument[0]; }

	}
	
	@FunctionalInterface
	public static interface CommandBody {
		
		int execute(CommandContext<CommandSourceStack> ctx);
		
	}
	
	@FunctionalInterface
	public static interface PermissionChecker {
		
		boolean canExecute(CommandSourceStack src);
		
	}

}
