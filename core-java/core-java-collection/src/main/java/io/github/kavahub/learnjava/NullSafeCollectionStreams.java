package io.github.kavahub.learnjava;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

import lombok.experimental.UtilityClass;

import static org.apache.commons.collections4.CollectionUtils.emptyIfNull;

@UtilityClass
public class NullSafeCollectionStreams {
    public Stream<String> nullSafeCollectionStreamsUsingCommonsEmptyIfNull(Collection<String> collection) {
        return emptyIfNull(collection).stream();
    }

    public Stream<String> nullSafeCollectionStreamsUsingJava8OptionalContainer(Collection<String> collection) {
        return Optional.ofNullable(collection)
                .map(Collection::stream)
                .orElseGet(Stream::empty);
    }

    public Stream<String> nullSafeCollectionStreamsUsingNullDereferenceCheck(Collection<String> collection) {
        return collection == null ? Stream.empty() : collection.stream();
    }
}
