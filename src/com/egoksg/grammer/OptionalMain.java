package com.egoksg.grammer;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Stream;

/*
 * [ Optional ]
 * 
 * Optional은 객체를 담고 있는 wrapper 클래스로 다음과 같은 상황을 방지하기 위해서 사용된다.
 * 
 * 변수의 값이 null일 경우, null을 직접 다루면서 발생되는 작업은 다음과 같다.
 * - 변수의 값을 if로 null 체크하여 빈 객체를 넣어 준다.
 * - 변수를 선언과 초기화를 동시에 하면서 빈 객체로 초기화 한다.
 * - null인 변수에 접근하면 NullPointException 발생하기 때문에 try catch로 감싼다.
 */
public class OptionalMain {

	public static void main(String[] args) {
		
		// Optional 생성
		createOptional();
		
		// Optional 값 가져오기
		getValueAndNullCheckOptional();
		
		// Optional 값 연산하기
		calculateOptional();
		
		// 원시값을 감싼 Optional
		primitiveWrapperOptional();
	}
	
	public static void createOptional() {
		/*
		 * Optional<T> of(T value)
		 * 
		 * 값의 Optional을 반환한다.
		 */
		String str = "abc";
		Optional<String> optional1 = Optional.of(str);
		Optional<String> optional2 = Optional.of("abc");
		/*
		 * Optional<T> ofNullable(T value)
		 * 
		 * 값이 null일 경우, empty Optional을 생성하여 반환한다.
		 * 값이 null이 아닐 경우, 값을 포함한 Optional을 반환한다.
		 */
		Optional<String> optional3 = Optional.ofNullable(null);
		
		/*
		 * Optional<T> empty()
		 * 
		 * empty Optional을 생성하여 반환한다.
		 */
		Optional<String> optional4 = Optional.empty();
	}
	
	public static void getValueAndNullCheckOptional() {
		Optional<String> optional = Optional.of("abc");
		/*
		 * T get()
		 * 
		 * 값이 null일 경우, NoSuchElementException 발생한다.
		 */
		String str1 = optional.get();
		
		/*
		 * T orElse(T other)
		 * 
		 * 값이 null일 경우, 지정된 default 값 반환한다.
		 */
		String str2 = optional.orElse("default");
		
		/*
		 * T orElseGet(Supplier<? extends T> other)
		 * 
		 * 값이 null일 경우, 지정된 Supplier 통해서 default 값 반환한다.
		 */
		String str3 = optional.orElseGet(String::new);
		
		/*
		 * <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X
		 * 
		 * 값이 null일 경우, 지정된 Supplier 통해서 Exception 객체 밴환한다.
		 */
		String str4 = optional.orElseThrow(NullPointerException::new);
		
		/*
		 * boolean isPresent()
		 * 
		 * 값이 null이 아닐 경우, true를 반환한다.
		 * 값이 null일 경우, false를 반환한다.
		 */
		boolean present = optional.isPresent();
		
		/*
		 * void ifPresent(Consumer<? super T> consumer)
		 * 
		 * 값이 null이 아닐 경우, 지정된 Consumer를 호출한다.
		 */
		optional.ifPresent(System.out::println);
	}
	
	public static void calculateOptional() {
		/*
		 * <R> Stream<R> map(Function<? super T, ? extends R> mapper)
		 * 
		 * 값이 null일 경우, empty Optional 반환한다.
		 * 값이 null이 아닐 경우, 지정된 mapper를 실행 후 결과를 Optional.ofNullable(T)하여 반환한다.
		 */
		Stream<String> stream1 = Stream.of("abc", "Ext", "Java", null);
		Optional<String> optional1 =
			stream1
				.map(x -> Optional.of(x).orElse("EMPTY"))
				.findAny()
					.map(String::toUpperCase); // Optional의 map
		
		/*
		 * <R> Stream<R> map(Function<? super T, ? extends R> mapper)
		 * 
		 * 값이 null일 경우, empty Optional 반환한다.
		 * 값이 null이 아닐 경우, 지정된 mapper를 실행 후 결과를 T 반환한다.
		 */
		Stream<String> stream2 = Stream.of("abc", "Ext", "Java", null);
		Optional<String> optional2 =
			stream2
				.map(x -> Optional.of(x).orElse("EMPTY"))
				.findAny()
					.flatMap(x -> Optional.of(x.toUpperCase())); // Optional의 flatMap
		
		/*
		 * Optional<T> filter(Predicate<? super T> predicate)
		 * 
		 * 값이 null일 경우, 자기 자신을 반환한다.
		 * 값이 null이 아닐경우, 지정된 predicate를 통해 test() 후 true이면 자기 자신을 반환한다.
		 */
		Stream<String> stream3 = Stream.of("abc", "Ext", "Java", null);
		Optional<String> optional3 =
			stream3
				.map(x -> Optional.of(x).orElse("EMPTY"))
				.findAny()
					.filter(x -> !"abc".equals(x)); // Optional의 filter
	}
	
	public static void primitiveWrapperOptional() {
		/*
		 * OptionalInt, OptionalDouble, OptionalLong
		 * 
		 * primitive value를 wrapper한 Optional은 Auto Boxing을 피하여 성능을 높이기 위해서 사용한다.
		 */
		// new OptionalInt(value)를 반환한다.
		// OptionalInt(value) 생성자에서 this.isPresent = false, this.value = value로 설정한다.
		OptionalInt optionalInt1 = OptionalInt.of(0);
		// new OptionalInt()를 반환한다.
		// OptionalInt 생성자에서 this.isPresent = false, this.value = 0으로 설정한다.
		OptionalInt optionalInt2 = OptionalInt.empty();
		
		System.out.println("equals : " + optionalInt1.equals(optionalInt2));
		System.out.println("optionalInt1 isPresent : " + optionalInt1.isPresent());
		System.out.println("optionalInt1 value : " + optionalInt1.getAsInt());
		System.out.println("optionalInt2 isPresent : " + optionalInt2.isPresent());
		System.out.println("optionalInt2 value : " + optionalInt2.getAsInt()); // NoSuchElementException 발생
	}
	
}
