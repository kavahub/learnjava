package io.github.kavahub.learnjava.spliterator;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 
 * 执行器
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class Executor {
	/**
	 * 统计作者
	 * 
	 * @param stream
	 * @return
	 */
    public static int countAuthors(Stream<Author> stream) {
		RelatedAuthorCounter wordCounter = stream.reduce(new RelatedAuthorCounter(0, true),
				RelatedAuthorCounter::accumulate, RelatedAuthorCounter::combine);
		return wordCounter.getCounter();
	}

	public static List<Article> generateElements() {
		return Stream.generate(() -> new Article("Java")).limit(35000).collect(Collectors.toList());
	}   
}
