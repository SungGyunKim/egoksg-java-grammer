package com.egoksg.grammer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Random;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import com.egoksg.sample.Student;

/*
 * [ Stream ]
 * 
 * Stream은 다양한 데이터 소스(배열, 컬렉션)를 표준화된 방법으로 다루기 위한 것이다.
 * Stream은 생성 > 연산 > 소비 Life Cycle을 가지고 있다.
 * 연산 단계는 여러 번 수행이 가능하며 소비 단계는 1번만 수행이 가능하다.
 * 소비 단계을 수행하게 되면 해당 Stream은 더 이상 사용할 수 없다.
 */
public class StreamMain {

	public static void main(String[] args) {
		try {
			// Stream 생성
			createStream();
		} catch (Exception e) {
			// e.printStackTrace();
		}
		
		try {
			// Stream 연산
			calculateStream();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			// Stream 소비
			spendStream();
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}
	
	public static void createStream() throws Exception {
		/*
		 * Collection에서 Stream 생성
		 * 
		 * Collection 인터페이스의 Spliterator<E> spliterator()
		 * Collection 인터페이스의 Stream<E> stream()
		 * Collection 인터페이스의 Stream<E> parallelStream()
		 */
		Stream<Integer> collectionToStream =  new ArrayList<>(Arrays.asList(1, 2, 3)).stream();
		
		/*
		 * 참조형 배열에서 Stream으로 변환
		 * 
		 * Stream 인터페이스의 Stream<T> of(T... values)
		 * Arrays 클래스의 Stream<T> stream(T[] array)
		 * Arrays 클래스의 Stream<T> stream(T[] array, int startInclusive, int endExclusive)
		 */
		Stream<Integer> referenceArrayToStream1 = Stream.of(1, 2, 3);
		Stream<Integer> referenceArrayToStream2 = Stream.of(new Integer[] { 1, 2, 3 });
		Stream<Integer> referenceArrayToStream3 = Arrays.stream(new Integer[] { 1, 2, 3 });
		Stream<Integer> referenceArrayToStream4 = Arrays.stream(new Integer[] { 1, 2, 3 }, 0, 2);
		
		/*
		 * 기본형 배멸에서 Stream으로 변환
		 * 기본형 전용 Stream은 sum(), min(), max()와 같은 부가 기능을 제공하며 사용시 Steam은 소비된다.
		 * 기본형 전용 Stream은 DoubleStream, IntStream, LongStream이 있다.
		 * 
		 * IntStream 인터페이스의 Stream<T> of(T... values)
		 * Arrays 클래스의 Stream<T> stream(T[] array)
		 * Arrays 클래스의 Stream<T> stream(T[] array, int startInclusive, int endExclusive)
		 */
		IntStream primitiveArrayToStream1 = IntStream.of(1, 2, 3);
		IntStream primitiveArrayToStream2 = IntStream.of(new int[] {1, 2, 3});
		IntStream primitiveArrayToStream3 = Arrays.stream(new int[] {1, 2, 3});
		IntStream primitiveArrayToStream4 = Arrays.stream(new int[] {1, 2, 3}, 0, 2);
		
		/*
		 * Random 클래스를 통한 무한/범위가 지정된 무한/범위가 지정된 유한 Stream을 생성
		 * 
		 * 무한 스트림인 경우, limit() 연산을 통해 잘라서 사용해야 한다.
		 * 
		 * <무한>
		 * IntStream 인터페이스의 IntStream ints()
		 * LongStream 인터페이스의 LongStream longs()
		 * DoubleStream 인터페이스의 DoubleStream doubles()
		 * 
		 * <범위가 지정된 무한>
		 * IntStream 인터페이스의 IntStream ints(int randomNumberOrigin, int randomNumberBound) 
		 * LongStream 인터페이스의 LongStream longs(long randomNumberOrigin, long randomNumberBound)
		 * DoubleStream 인터페이스의 DoubleStream doubles(double randomNumberOrigin, double randomNumberBound)
		 * 
		 * <범위가 지정된 유한>
		 * IntStream 인터페이스의 IntStream ints(long streamSize, int randomNumberOrigin, int randomNumberBound)
		 * LongStream 인터페이스의 LongStream longs(long streamSize, long randomNumberOrigin, long randomNumberBound)
		 * DoubleStream 인터페이스의 DoubleStream doubles(long streamSize, double randomNumberOrigin, double randomNumberBound)
		 */
		IntStream intsStream1 = new Random().ints().limit(5);
		LongStream longsStream1 = new Random().longs().limit(5);
		DoubleStream doublesStream1 = new Random().doubles().limit(5);
		IntStream intsStream2 = new Random().ints(0, 5);
		LongStream longsStream2 = new Random().longs(0L, 5L);
		DoubleStream doublesStream2 = new Random().doubles(0.0, 5.0);
		IntStream intsStream3 = new Random().ints(5, 0, 5);
		LongStream longsStream3 = new Random().longs(5, 0L, 5L);
		DoubleStream doublesStream3 = new Random().doubles(5, 0.0, 5.0);
		
		/*
		 * 특정 범위의 정수를 요소로 갖는 스트림 생성
		 * 
		 * IntStream 인터페이스의 IntStream range(int startInclusive, int endExclusive)
		 * IntStream 인터페이스의 IntStream rangeClosed(int startInclusive, int endInclusive)
		 * LongStream 인터페이스의 LongStream range(long startInclusive, final long endExclusive)
		 * LongStream 인터페이스의 LongStream rangeClosed(long startInclusive, final long endInclusive)
		 */
		IntStream intRangeStream = IntStream.range(1, 5); // 1, 2, 3, 4
		IntStream intRangeClosedStream = IntStream.rangeClosed(1, 5); // 1, 2, 3, 4, 5
		LongStream longRangeStream = LongStream.range(0L, 5L); // 1, 2, 3, 4
		LongStream longRangeClosedStream = LongStream.rangeClosed(0L, 5L); // 1, 2, 3, 4, 5
		
		/*
		 * 람다식을 소스로 하는 무한 스트림 생성
		 * 
		 * 무한 스트림인 경우, limit() 연산을 통해 잘라서 사용해야 한다.
		 * 
		 * Stream 인터페이스의 Stream<T> iterate(final T seed, final UnaryOperator<T> f) // 이전 요소에 종속적, seed는 초기값이다.
		 * Stream 인터페이스의 Stream<T> generate(Supplier<T> s) // 이전 요소에 독립적
		 */
		Stream<Integer> lamdaIterateStream = Stream.iterate(0, n->n+2).limit(5);
		Stream<Integer> lamdaGenerateStream = Stream.generate(()->1).limit(5);
		
		/*
		 * 파일을 소스로 하는 스트림 생성
		 * 
		 * Files 클래스의 Stream<Path> list(Path dir)
		 * Files 클래스의 Stream<String> lines(Path path)
		 * Files 클래스의 Stream<String> lines(Path path, Charset cs)
		 * BufferedReader 클래스의 Stream<String> lines()
		 */
		Stream<Path> fileStream1 = Files.list(Paths.get(".")); // 폴더 경로를 주면 해당 폴더의 파일을 목록을 가지고 있는 Stream을 생성한다.
		Stream<String> fileStream2 = Files.lines(Paths.get("abc.txt")); // 파일의 내용을 라인 단위로 Stream을 생성한다.
		Stream<String> fileStream3 = Files.lines(Paths.get("abc.txt"), StandardCharsets.UTF_8);
		Stream<String> lines = new BufferedReader(new FileReader("abc.txt")).lines();
		
		/*
		 * 빈 Stream 생성
		 * 
		 * Stream.empty()은 Optional.orElseGet(Stream.empty())에서 NullPointerException을 피해하여 Null-safe 스트림을 생성하기 위해 사용된다.
		 */
		Stream<Object> emptyStream = Stream.empty();
	}

	public static void calculateStream() throws Exception {
		/*
		 * 요소의 개수를 제한한다.
		 * 
		 * Stream<T> limit(long maxSize);
		 */
		IntStream stream01 = IntStream.rangeClosed(1, 10); // [1, 2, 3, 4, 5, 6, 7, 8, 9]
		stream01.limit(5); // [1, 2, 3, 4, 5]
		
		/*
		 * 요소의 일부를 건너뛴다.
		 * 
		 * Stream<T> skip(long n);
		 */
		IntStream stream02 = IntStream.rangeClosed(1, 10); // [1, 2, 3, 4, 5, 6, 7, 8, 9]
		stream02.skip(3); // [4, 5, 6, 7, 8, 9]
		
		/*
		 * 조건에 맞는 요소를 걸러낸다.
		 * 
		 * Stream<T> filter(Predicate<? super T> predicate);
		 */
		IntStream stream03 = IntStream.rangeClosed(1, 10); // [1, 2, 3, 4, 5, 6, 7, 8, 9]
		stream03.filter(x -> x > 5); // [6, 7, 8, 9]
		
		/*
		 * 동일한 요소를 제거한다.
		 * 
		 * Stream<T> distinct();
		 */
		IntStream stream04 = IntStream.of(1, 2, 2, 3, 3, 3, 4, 5, 5, 6);
		stream04.distinct(); // [1, 2, 3, 4, 5, 6]
		
		/*
		 * 요소를 기본 정렬한다.
		 * 
		 * Stream<T> sorted();
		 */
		IntStream stream05 = IntStream.of(5, 3, 4, 1, 2);
		stream05.sorted(); // [1, 2, 3, 4, 5]
		
		/*
		 * 요소를 비교하여 정렬 기준에 따라 정렬한다.
		 * 
		 * Stream<T> sorted(Comparator<? super T> comparator);
		 */
		Stream<String> stream06 = Stream.of("5", "3", "4", "1", "2");
		stream06
			// 기본 정렬(Comparable)
			.sorted(Comparator.naturalOrder())
			// 기본 정렬의 반대(Comparable's reverse)
			.sorted(Comparator.reverseOrder())
			.sorted((s1, s2) -> s1.compareTo(s2)) // ["1", "2", "3", "4", "5"]
			// 2개 인자를 받아 1개의 값을 반환하는 경우, 메서드 참조로 변경 가능하다.
			.sorted(String::compareTo)
			.sorted(String.CASE_INSENSITIVE_ORDER) // 대소문자 구분하지 않는다.
			// Comparator.comparing()는 두 요소를 비교할 때 변환될 함수를 지정할 수 있다.
			// 단, 지정되는 함수는 1개의 인자를 받아 1개를 반환해야 한다.
			.sorted(Comparator.comparing(String::length))
			// Comparator.comparing()는 generic을 사용하기 때문에 지정된 함수의 결과가 primitive인 경우 Auto Boxing가 일어 난다.
			// Comparator.comparingInt()는 지정된 함수의 결과가 int일 때 Auto Boxing을 피하기 위해 사용된다.
			.sorted(Comparator.comparingInt(String::length))
			;//.forEach(System.out::println);
		
		/*
		 * 요소를 비교하여 정렬 기준을 여러 개 설정하여 정렬한다.
		 * comparing()을 사용하고 이어서 thenComparing()을 사용한다.
		 * 
		 * Comparator<T> thenComparing(Function<? super T, ? extends U> keyExtractor)
		 */
		Stream<Student> stream07 = Stream.of(
			Student.builder().ban(1).totalScore(90).name("홍길동").build(),
			Student.builder().ban(1).totalScore(80).name("이순신").build(),
			Student.builder().ban(1).totalScore(80).name("유관순").build()
		);
		// 정렬 기준을 여러개 하고 싶을 경우, thenComparing
		stream07
			.sorted(Comparator.comparing(Student::getBan)
					          .thenComparing(Student::getTotalScore)
					          .thenComparing(Student::getName)
					          .thenComparing(Comparator.naturalOrder()) // 기본 정렬(Comparable)
					          .reversed())
			;//.forEach(System.out::println);
		
		/*
		 * 요소에 대해 로직을 걸쳐 새로운 요소로 반환한다.
		 * 데이터 소스는 1차원 배열([Stream])이고 최종 결과는 1차원 배열([Stream])이다.
		 * 
		 * <R> Stream<R> map(Function<? super T, ? extends R> mapper);
		 * IntStream mapToInt(ToIntFunction<? super T> mapper);
		 * LongStream mapToLong(ToLongFunction<? super T> mapper);
		 * DoubleStream mapToDouble(ToDoubleFunction<? super T> mapper);
		 */
		Stream<File> stream08 = Stream.of(new File("Ex.java"), new File("Ex1"),
				new File("Ex1.bak"), new File("Ex2.java"), new File("Ex1.txt"));
		Stream<String> stream09 = stream08.map(File::getName); // Stream<File>에서 Stream<String>로 변환
		
		/*
		 * 요소에 대해 로직을 걸쳐 새로운 요소로 반환한다.
		 * 데이터 소스는 2차원 배열([[Stream], [Stream]])이고 최종 결과는 1차원 배열([Stream])이다.
		 * 
		 * <R> Stream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper);
		 * IntStream flatMapToInt(Function<? super T, ? extends IntStream> mapper);
		 * LongStream flatMapToLong(Function<? super T, ? extends LongStream> mapper);
		 * DoubleStream flatMapToDouble(Function<? super T, ? extends DoubleStream> mapper);
		 */
		Stream<String[]> stream10 = Stream.of(new String[] {"abc", "def", "ghi"},
											  new String[] {"abc", "def", "ghi"});
		stream10.flatMap(s -> Stream.of(s))
		        ;//.forEach(System.out::println);
		
		// 파일 내용의 라인을 배열로 받았다.
		// 라의 단어로 쪼깨어 Stream으로 반환하면 2차원 배열이 만들어 지니 1차원 배열로 만든다.
		Stream<String> stream11 = Arrays.stream(new String[] {
			"Belive or not It is true",
			"Do or do not There is no try"
		});
		stream11.flatMap(line -> Arrays.stream(line.split(" +")))
		        .map(String::toLowerCase)
		        .distinct()
		        .sorted()
		        .forEach(System.out::println);
		
		/*
		 * 요소에 대한 작업을 수행한다.
		 * 
		 * Stream<T> peek(Consumer<? super T> action);
		 */
		Stream<File> stream12 = Stream.of(new File("Ex.java"), new File("Ex1"),
				new File("Ex1.bak"), new File("Ex2.java"), new File("Ex1.txt"));
		stream12.map(File::getName)
		        .filter(s -> s.indexOf(".") != -1) // 확장자 없는 파일 제거
		        .peek(s -> System.out.printf("filename=%s%n", s))
		        .map(s -> s.substring(s.indexOf(".")+1)) // 파일명에서 확장자를 추출하여 반환
		        .peek(s -> System.out.printf("extention=%s%n", s))
		        ;//.forEach(System.out::println);
	}

	public static void spendStream() throws Exception {
		/*
		 * void forEach(Consumer<? super T> action);
		 * 
		 * 요소를 순회하면서 작업을 수행한다.
		 */
		IntStream.range(1, 10).sequential().forEach(System.out::print); System.out.println();
		// 병렬 스트림으로 인해 순서 보장이 안됨.
		IntStream.range(1, 10).parallel().forEach(System.out::print); System.out.println();
		
		/*
		 * void forEachOrdered(Consumer<? super T> action);
		 * 
		 * 순차적으로 요소를 순회하면서 작업을 수행한다.
		 */
		// 직렬 스트림에서는 forEachOrdered()가 의미 없다.
		IntStream.range(1, 10).sequential().forEachOrdered(System.out::print); System.out.println();
		// 병렬 스트림에서 순서를 지키면서 반복 수행하기 때문에 성능에 영향을 끼친다.
		IntStream.range(1, 10).parallel().forEachOrdered(System.out::print); System.out.println();
		
		
		
		
		
		
		/*
		 * boolean anyMatch(Predicate<? super T> predicate);
		 * 
		 * 조건에 멎에 맞는 요소가 하나라도 있는지 여부를 반환한다.
		 */
		boolean anyMatch = IntStream.range(1, 10).anyMatch(x -> x > 5);
		
		/* 
		 * boolean allMatch(Predicate<? super T> predicate);
		 * 
		 * 조건에 멎에 맞는 요소가 모두 만족하는지 여부를 반환한다.
		 */
		boolean allMatch = IntStream.range(1, 10).allMatch(x -> x > 5);
		
		/* 
		 * boolean noneMatch(Predicate<? super T> predicate);
		 * 
		 * 조건에 멎에 맞는 요소가 모두 만족하지 않는지 여부를 반환한다.
		 */
		boolean noneMatch = IntStream.range(1, 10).noneMatch(x -> x > 5);
		
		
		
		
		
		/*
		 * Optional<T> findFirst();
		 * 
		 * 첫번째 요소를 반환한다.
		 * filter와 함께 사용한다.
		 */
		OptionalInt findFirst = IntStream.range(1, 10).filter(x -> x > 5).findFirst();
		
		/* 
		 * Optional<T> findAny();
		 * 
		 * 첫번째 요소를 반환한다.
		 * filter와 함께 사용한다.
		 * 병렬 스트림에서 사용한다.
		 */
		OptionalInt findAny = IntStream.range(1, 10).parallel().filter(x -> x > 5).findAny();
		
		
		
		
		
		
		/* 
		 * long count();
		 * 
		 * 요소의 개수를 반환한다.
		 */
		long count1 = Stream.of("A", "B", "C").count();
		
		/*
		 * Optional<T> min(Comparator<? super T> comparator);
		 * 
		 * 요소의 최대값을 반환한다.
		 * 정렬 순서를 알기 위해 Comparator가 수행된다.
		 */
		Optional<String> min1 = Stream.of("A", "B", "C").min(Comparator.naturalOrder());
		
		/* 요소의 최소값을 반환한다.
		 * 정렬 순서를 알기 위해 Comparator가 수행된다.
		 * Optional<T> max(Comparator<? super T> comparator);
		 */
		Optional<String> max1 = Stream.of("A", "B", "C").max(Comparator.naturalOrder());
		
		
		
		/*
		 * 스트림의 전체 요소를 특정 로직을 통해 요소를 줄이면서 반환한다.
		 * 
		 * T reduce(T identity, BinaryOperator<T> accumulator);
		 * Optional<T> reduce(BinaryOperator<T> accumulator);
		 * <U> U reduce(U identity, BiFunction<U, ? super T, U> accumulator, BinaryOperator<U> combiner);
		 * 
		 * identity : 초기값
		 * accumulator : 누적 작업자
		 * combiner : 병렬 스트림일 경우, 합치는 작업을 한다.
		 */
		int count2 = IntStream.range(1, 10).reduce(0, (x, y) -> x + 1);
		int sum = IntStream.range(1, 10).reduce(0, (x, y) -> x + y);
		int min2 = IntStream.range(1, 10).reduce(Integer.MIN_VALUE, (x, y) -> x < y ? x : y);
		int max2 = IntStream.range(1, 10).reduce(Integer.MAX_VALUE, (x, y) -> x > y ? x : y);
		OptionalInt minOptional1 = IntStream.range(1, 10).reduce(Integer::min);
		OptionalInt maxOptional2 = IntStream.range(1, 10).reduce(Integer::max);
		
		/* 
		 * Collector<T, A, R>
		 *
		 * 요소 T를 A에 누적하고 R롤 반환한다.
		 * 
		 * 	supplier() 		: 누적할 공간을 제공한다. R
		 * 	accumulator() 	: 누적을 수행한다. A
		 * 	combiner()		: 병렬 스트림일 경우, 흩어진 누적 공간을 합치친다.
		 *  finisher() 		: 최종 변환한다.
		 * 
		 * <R, A> R collect(Collector<? super T, A, R> collector);
		 * <R> R collect(Supplier<R> supplier, BiConsumer<R, ? super T> accumulator, BiConsumer<R, R> combiner);
		 */
		String concat = Stream.of("A", "B", "C")
						.collect(StringBuilder::new
								// (sb, s) -> sb.append(s)
								, StringBuilder::append
								// (sb1, sb2) -> sb1.append(sb2)
								, StringBuilder::append)
						.toString();
		
		/*
		 * Object[] toArray();
		 * 
		 * 스트림 요소의 타입에 맞는 참조 배열로 반환한다.
		 */
		Object[] array1 = Stream.of("A", "B", "C").toArray();
		
		/* 
		 * <A> A[] toArray(IntFunction<A[]> generator);
		 * 
		 * 스트림 요소를 변환하는 로직을 걸쳐 다른 타입의 참조 배열로 반환한다.
		 */
		String[] array2 = Stream.of("A", "B", "C").toArray(String[]::new);
	}
	
}
