package by.bsac.utilities;

public final class ClazzUtils {

    private static Class[] primitive_wrappers_types = new Class[] {Byte.class, Short.class, Integer.class, Long.class, Float.class, Double.class, Character.class, Void.class};


    public static boolean isPrimitiveWrapper(Class clazz) {

        if (clazz == null) throw new NullPointerException("Class parameter is null");

        for (Class c : primitive_wrappers_types) if (c.equals(clazz)) return true;
        return false;
    }
}
