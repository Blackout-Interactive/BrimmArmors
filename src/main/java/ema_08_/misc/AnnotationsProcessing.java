package ema_08_.misc;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Function;

public class AnnotationsProcessing {
	
	public static <ANN extends Annotation> Method[] getAnnotatedMethods(
			Class<?> source, Class<ANN> annotation, Function<ANN, Boolean> accepter) {
		ArrayList<Method> methods = new ArrayList<>();
		for (Method method : source.getDeclaredMethods()) {
            if (method.isAnnotationPresent(annotation) &&
            		(accepter == null || accepter.apply(Objects.requireNonNull(method.getAnnotation(annotation),
            				"This should not be happening")))) {
                methods.add(method);
            }
        }
		return methods.toArray(new Method[0]);
	}

}
