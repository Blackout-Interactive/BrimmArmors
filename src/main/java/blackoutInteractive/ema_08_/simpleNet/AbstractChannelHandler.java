package blackoutInteractive.ema_08_.simpleNet;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.WrongMethodTypeException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.stream.Stream;

import blackoutInteractive.brimmArmors.BrimmArmors;
import blackoutInteractive.ema_08_.misc.AnnotationsProcessing;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public abstract class AbstractChannelHandler {
	
	protected final SimpleChannel channel;
	protected volatile int pid = 0;
	
	protected AbstractChannelHandler(String modid, String channelName, String protVersion) {
		this.channel = NetworkRegistry.newSimpleChannel(
                new ResourceLocation(modid, channelName),
                protVersion::toString,
                protVersion::equals,
                protVersion::equals
        );
	}
	
	private static Method assertValidSignature(Method method, String type, String implName) {
		int modifiers = method.getModifiers();
		if (!Modifier.isStatic(modifiers)) throw new IllegalArgumentException("Packet "+implName+" "+type+" method is not static");
		if (!Modifier.isPrivate(modifiers)) throw new IllegalArgumentException("Packet "+implName+" "+type+" method is not private");
		return method;
	}
	
	private static final Stream<? extends Class<? extends Throwable>> coreApiEx = List.of(
			IllegalAccessException.class, IllegalArgumentException.class,
			InvocationTargetException.class, ExceptionInInitializerError.class
			).stream();
	
	private static final Stream<? extends Class<? extends Throwable>> handleApiEx = List.of(
			WrongMethodTypeException.class, ClassCastException.class
			).stream();
	
	private static void handleReflectionEx(Throwable t, boolean coreApi) {
		if ((coreApi ? coreApiEx : handleApiEx).anyMatch(t.getClass()::equals)) {
			BrimmArmors.LOGGER.error("A reflection exception has occurred handling a packet via "+
					"an abstract channel handler. CoreApi="+coreApi+".");
			t.printStackTrace();
			throw new RuntimeException("Reflection exception in packet handling");
		} else if (t instanceof RuntimeException re) {
			throw re;
		} else {
			BrimmArmors.LOGGER.error("An unexpected checked exception has occurred handling a packet via "+
							"an abstract channel handler. Propagating.");
			throw new RuntimeException("Unexpected checked exception", t);
		}
	}
	
	private static void invokestaticV(Method m, Object... params) {
		try {
			m.invoke(null, params);
		} catch (Throwable t) {
			handleReflectionEx(t, true);
		}
	}
	
	@SuppressWarnings("unchecked")
	private static <T> T invokestaticR(Method m, Object... params) {
		try {
			Object ret = m.invoke(null, params);
			return ret == null ? null : (T)ret;
		} catch (Throwable t) {
			handleReflectionEx(t, true);
			throw new Error("How did i even got here?"); /*unreachable*/
		}
	}
	
	private static void invokestaticV(MethodHandle m, Object... params) {
		try {
			m.invokeWithArguments(params);
		} catch (Throwable t) {
			handleReflectionEx(t, false);
		}
	}
	
	@SuppressWarnings("unchecked")
	private static <T> T invokestaticR(MethodHandle m, Object... params) {
		try {
			Object ret = m.invokeWithArguments(params);
			return ret == null ? null : (T)ret;
		} catch (Throwable t) {
			handleReflectionEx(t, false);
			throw new Error("How did i even got here?"); /*unreachable*/
		}
	}
	
	protected final void register(final Class<? extends APacket> packet) {
		String implName = packet.getName();
		Method[] encoders = AnnotationsProcessing.getAnnotatedMethods(packet, PacketEncoder.class,
				(ann)->ann.implClassName().equals(implName));
		if (encoders.length == 0) throw new IllegalArgumentException("Packet "+implName+" has no encoder method");
		if (encoders.length > 1) throw new IllegalArgumentException("Packet "+implName+" has too many encoder methods");
		Method[] decoders = AnnotationsProcessing.getAnnotatedMethods(packet, PacketDecoder.class,
				(ann)->ann.implClassName().equals(implName));
		if (decoders.length == 0) throw new IllegalArgumentException("Packet "+implName+" has no decoder method");
		if (decoders.length > 1) throw new IllegalArgumentException("Packet "+implName+" has too many decoder methods");
		Method enc = assertValidSignature(encoders[0], "encoder", implName);
		Method dec = assertValidSignature(decoders[0], "decoder", implName);
		if (!enc.getReturnType().equals(void.class) && !enc.getReturnType().equals(Void.class))
			throw new IllegalArgumentException("Packet "+implName+" encoder method shall be declared as void");
		if (!dec.getReturnType().equals(packet))
			throw new IllegalArgumentException("Packet "+implName+" decoder method shall return a "+implName+" type");
		Class<?>[] encArgs = enc.getParameterTypes();
		if (encArgs.length != 2 || !encArgs[0].equals(packet) || !encArgs[1].equals(FriendlyByteBuf.class))
			throw new IllegalArgumentException("Packet "+implName+" encoder method shall accept exactly two arguments of "+implName+" and FriendlyByteBuf types");
		Class<?>[] decArgs = dec.getParameterTypes();
		if (decArgs.length != 1 || !decArgs[0].equals(FriendlyByteBuf.class))
			throw new IllegalArgumentException("Packet "+implName+" decoder method shall accept a single argument of FriendlyByteBuf type");
		if (enc.getExceptionTypes().length != 0)
			throw new IllegalArgumentException("Packet "+implName+" encoder method shall throw no exceptions");
		if (dec.getExceptionTypes().length != 0)
			throw new IllegalArgumentException("Packet "+implName+" decoder method shall throw no exceptions");
		enc.setAccessible(true);
		dec.setAccessible(true);
		try {
			MethodHandles.Lookup privateLookup =
		            MethodHandles.privateLookupIn(packet, MethodHandles.lookup());
			MethodHandle encHandle = privateLookup.unreflect(enc);
			MethodHandle decHandle = privateLookup.unreflect(dec);
			this.channel.registerMessage(this.pid++, packet,
					(p, b)->invokestaticV(encHandle, p, b),
					(b)->invokestaticR(decHandle, b),
					(p, ctx)->p.handle(ctx));
		} catch (Exception e) {
			BrimmArmors.LOGGER.warn("Falling back to core reflection api for packet " + packet.getName(), e);
			this.channel.registerMessage(this.pid++, packet,
					(p, b)->invokestaticV(enc, p, b),
					(b)->invokestaticR(dec, b),
					(p, ctx)->p.handle(ctx));
		}
	}
	
    public void sendTo(APacket message, ServerPlayer player) {
        throw new UnsupportedOperationException(getClass().getSimpleName()+" channel handler does not support this function");
    }

    public void sendToAll(APacket message) {
    	throw new UnsupportedOperationException(getClass().getSimpleName()+" channel handler does not support this function");
    }

    public void sendToAllAround(APacket message, PacketDistributor.TargetPoint point) {
    	throw new UnsupportedOperationException(getClass().getSimpleName()+" channel handler does not support this function");
    }

    public void sendToServer(APacket message) {
    	throw new UnsupportedOperationException(getClass().getSimpleName()+" channel handler does not support this function");
    }

}
