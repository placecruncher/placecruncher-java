package com.placecruncher.server.util;

import java.util.Collection;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;

public final class TransformUtils {

    private TransformUtils() {}

    @SuppressWarnings("unchecked")
    public static<I, O> Collection<O> transform(Collection<I> input, Transformer transformer) {
        return CollectionUtils.collect(input, transformer);
    }
    @SuppressWarnings("unchecked")
    public static<I extends Collection<?>, O extends Collection<?>> O transform(I input, Transformer transformer, O output) {
        return (O)CollectionUtils.collect(input, transformer, output);
    }

}
