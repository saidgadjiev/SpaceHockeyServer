package resource.reflection;

import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;

/**
 * Created by said on 30.10.15.
 */
public class ReflectionHelper {
    @Nullable
    public static Object createInstance(String className) {
        try {
            return Class.forName(className).newInstance();
        } catch (IllegalArgumentException | SecurityException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void setFieldValue(Object object,
                                     String fieldName,
                                     String value) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            if (field.getType().equals(String.class)) {
                field.set(object, value);
            } else if (field.getType().equals(int.class)) {
                field.set(object, Integer.parseInt(value));
            }

            field.setAccessible(false);
        } catch (SecurityException | NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
