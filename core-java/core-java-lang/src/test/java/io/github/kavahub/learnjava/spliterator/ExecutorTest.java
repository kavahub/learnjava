package io.github.kavahub.learnjava.spliterator;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Spliterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ExecutorTest {
    Article article;
	Stream<Author> stream;
	Spliterator<Author> spliterator;
	Spliterator<Article> split1;
	Spliterator<Article> split2;

	@BeforeAll
	public void init() {
		article = new Article(Arrays.asList(new Author("Ahmad", 0), new Author("Eugen", 0), new Author("Alice", 1),
				new Author("Alice", 1), new Author("Mike", 0), new Author("Alice", 1), new Author("Mike", 0),
				new Author("Alice", 1), new Author("Mike", 0), new Author("Alice", 1), new Author("Mike", 0),
				new Author("Mike", 0), new Author("Alice", 1), new Author("Mike", 0), new Author("Alice", 1),
				new Author("Mike", 0), new Author("Alice", 1), new Author("Mike", 0), new Author("Alice", 1),
				new Author("Mike", 0), new Author("Michał", 0), new Author("Loredana", 1)), 0);
		stream = article.getListOfAuthors().stream();
		split1 = Executor.generateElements().spliterator();
		split2 = split1.trySplit();
		spliterator = new RelatedAuthorSpliterator(article.getListOfAuthors());
	}

	@Test
	public void givenAstreamOfAuthors_whenProcessedInParallelWithCustomSpliterator_coubtProducessRightOutput() {
		Stream<Author> stream2 = StreamSupport.stream(spliterator, true);
		assertThat(Executor.countAutors(stream2.parallel())).isEqualTo(9);
	}

	@Test
	public void givenSpliterator_whenAppliedToAListOfArticle_thenSplittedInHalf() {
		assertThat(new Task(split1).call()).containsSequence(Executor.generateElements().size() / 2 + "");
		assertThat(new Task(split2).call()).containsSequence(Executor.generateElements().size() / 2 + "");
	}   
}
