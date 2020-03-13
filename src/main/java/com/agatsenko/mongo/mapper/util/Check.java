package com.agatsenko.mongo.mapper.util;

import lombok.experimental.UtilityClass;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

@UtilityClass
public class Check {
    public static void arg(boolean condition, Supplier<CharSequence> errorMsg) {
        check(condition, errorMsg, IllegalArgumentException::new);
    }

    public static void arg(boolean condition, CharSequence errorMsgFormat, Object... errorMsgFormatArgs) {
        arg(condition, () -> String.format(errorMsgFormat.toString(), errorMsgFormatArgs));
    }

    public static void argNotNull(Object arg, CharSequence argName) {
        arg(arg != null, () -> argName + " is null");
    }

    public static void argNotNullOrEmpty(CharSequence arg, CharSequence argName) {
        arg(arg != null && arg.length() > 0, () -> argName + " is null or empty");
    }

    public static void argNotNullOrEmpty(Collection<?> arg, CharSequence argName) {
        arg(arg != null && !arg.isEmpty(), () -> argName + " is null or empty");
    }

    public static void argNotNullOrEmpty(Map<?, ?> arg, CharSequence argName) {
        arg(arg != null && !arg.isEmpty(), () -> argName + " is null or empty");
    }

    public static void argNotNullOrEmpty(byte[] arg, CharSequence argName) {
        arg(arg != null && arg.length > 0, () -> argName + " is null or empty");
    }

    public static void argNotNullOrEmpty(short[] arg, CharSequence argName) {
        arg(arg != null && arg.length > 0, () -> argName + " is null or empty");
    }

    public static void argNotNullOrEmpty(int[] arg, CharSequence argName) {
        arg(arg != null && arg.length > 0, () -> argName + " is null or empty");
    }

    public static void argNotNullOrEmpty(long[] arg, CharSequence argName) {
        arg(arg != null && arg.length > 0, () -> argName + " is null or empty");
    }

    public static void argNotNullOrEmpty(float[] arg, CharSequence argName) {
        arg(arg != null && arg.length > 0, () -> argName + " is null or empty");
    }

    public static void argNotNullOrEmpty(double[] arg, CharSequence argName) {
        arg(arg != null && arg.length > 0, () -> argName + " is null or empty");
    }

    public static void argNotNullOrEmpty(char[] arg, CharSequence argName) {
        arg(arg != null && arg.length > 0, () -> argName + " is null or empty");
    }

    public static void argNotNullOrEmpty(boolean[] arg, CharSequence argName) {
        arg(arg != null && arg.length > 0, () -> argName + " is null or empty");
    }

    public static <T> void argNotNullOrEmpty(T[] arg, CharSequence argName) {
        arg(arg != null && arg.length > 0, () -> argName + " is null or empty");
    }

    public static void state(boolean condition, Supplier<CharSequence> errorMsg) {
        check(condition, errorMsg, IllegalStateException::new);
    }

    public static void state(boolean condition, CharSequence errorMsgFormat, Object... errorMsgFormatArgs) {
        state(condition, () -> String.format(errorMsgFormat.toString(), errorMsgFormatArgs));
    }

    private static <E extends RuntimeException> void check(
            boolean condition,
            Supplier<CharSequence> errorMsg,
            Function<String, E> error) {
        if (!condition) {
            throw error.apply(errorMsg.get().toString());
        }
    }
}