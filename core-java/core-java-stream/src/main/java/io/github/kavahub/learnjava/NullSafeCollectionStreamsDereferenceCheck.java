package io.github.kavahub.learnjava;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.commons.collections4.CollectionUtils;

public class NullSafeCollectionStreamsDereferenceCheck {
    public Stream<String> toStreamUsingNull(Collection<String> collection) {
        return collection == null ? Stream.empty() : collection.stream();
    } 

    public Stream<String> toStreamUsingCommons(Collection<String> collection) {
        return CollectionUtils.emptyIfNull(collection).stream();
    }

    public Stream<String> toStreamJava8Optional(Collection<String> collection) {
        return Optional.ofNullable(collection)
            .map(Collection::stream)
            .orElseGet(Stream::empty);
    }
}
