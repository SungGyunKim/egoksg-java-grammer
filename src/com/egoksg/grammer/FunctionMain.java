package com.egoksg.grammer;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class FunctionMain {
	
	public FunctionMain() {
		
	}
	public FunctionMain(Integer t) {
		
	}
	public FunctionMain(Integer t, Integer u) {
		
	}

	public static void main(String[] args) {
		lambda();
		
		function();
		
		functionOfCollection();
		
		methodReference();
		
		constructroReference();
	}
	
	public static void lambda() {
		// 익명 클래스
		MyFunction f1 = new MyFunction() {
			@Override
			public int max(int a, int b) {
				return a > b ? a : b;
			}
		};
		
		// 람다식은 인자의 개수 및 타입, 반환의 타입과 개수가 같아야 한다.
		// 아래의 람다식은 max() 함수로 대체된다.
		MyFunction f2 = (a, b) -> a > b ? a : b;
		
		System.out.println("f1.max() : " + f1.max(5, 10));
		System.out.println("f2.max() : " + f1.max(5, 10));
	}
	
	public static void function() {
		/*
		 * Interface			method					설명														키워드
		 * Runnable				void run()				인자 0개이며 반환은 void를 반환한다.
		 * Supplier<T>			T get()					인자를 받지 않고 로직을 통해 값을 반환한다.							공급하다(공급자)
		 * Predicate<T>			boolean test(T t		인자 t를 받아서 로직을 통해 boolean을 반환한다.					판단하다
		 * Function<T, R>		R apply(T t)			인자 t를 받아 로직을 통해 R을 반환한다.							처리하다
		 * XXXOperator			applyXXX				피연산자(operend)를 받아 xxx연산(operator)을 결과를 반환한다.		연산하다
		 * Comparator			compare(T, T)			2개의 인자를 받아 비교하여 int를 반환한다.							비교하다
		 * Consumer<T>			void accept(T t)		인자 t를 받아 로직을 통해 void를 반환한다.						소비하다(소비자)
		 * 
		 * ※ Bi 접두사가 붙은 함수형 인터페이스는 인자가 2개이다.
		 * Interface			method					설명
		 * BiFunction<T, U>		R apply(T t, U u)		인자 2개(T, U)를 받아 수행하고 R을 반환한다.
		 * BiPredicate<T, U>	boolean test(T t, U u)	인자 2개(T, U)를 받아 판단하고 boolean을 반환한다.
		 * BiConsumer<T, U>		void accept(T t, U u)	인자 2개(T, U)를 받아 소비하고 void를 반환한다.
		 * 
		 * UnaryOperator<T>		T apply(T t)			Function의 자식 인터페이스이며 단항 연산자로 인자와 결과의 타입이 같다.
		 * BinaryOperator<T>	T apply(T t1, T t2)		BiFunction의 자식 인터페이스이며 다항 연산자로 인자와 결과의 타입이 같다.
		 */
		
		/*
		 * Predicate의 and(), or(), negate()
		 */
		Predicate<Integer> p = i -> i < 100;
		Predicate<Integer> q = i -> i < 200;
		Predicate<Integer> r = i -> i % 2 == 0;
		
		Predicate<Integer> notP = p.negate();			// !(i < 100)
		Predicate<Integer> all1 = notP.and(q).or(r);	// !(i < 100) && i < 200 || i % 2 == 0
		Predicate<Integer> all2 = notP.and(q.or(r));	//  !(i < 100) && (i < 200 || i % 2 == 0)
		
		System.out.println("all1.test() : " + all1.test(2));
		System.out.println("all2.test() : " + all2.test(2));
		
		/*
		 * Function 의 andThen(), compose()
		 * 
		 * Function<T, V> andThen(Function<? super R, ? extends V> after) : 함수의 결과를 after 함수에서 받는다.
		 */
		Function<String, Integer> f = (s) -> Integer.parseInt(s, 16);
		Function<Integer, String> g = (i) -> Integer.toBinaryString(i);
		
		Function<String, String> fg = f.andThen(g); // s -> f(s) -> i -> g(i) -> result
		Function<Integer, Integer> gf = f.compose(g); // i -> g(i) -> s -> f(s) -> result
		
		System.out.println("fg : " + fg.apply("FF"));
		System.out.println("gf : " + gf.apply(2));
	}
	
	public static void functionOfCollection() {
		/*
		 * 인터페이스		메서드																설명
		 * Collection	boolean removeIf(Predicate<E> filter)								조건에 맞으면 요소 삭제
		 * 
		 * List			void replaceAll(UnaryOperator<E> operator)							모든 요소를 변환하여 대체
		 * 
		 * Iterable		void forEach(ConsumerT> action)										모든 요소에 작업 action 수행
		 * 
		 * Map			V compute(K key, BiFunction<K, V, V> remappingFunction)				지정된 키의 값에 작업 remappingFunction 수행
		 * 				V computeIfAbsent(K key, Function<K, V> mappingFunction)			키가 없으면, 작업 remappingFunction 수행 후 추가
		 * 				V computeIfPresent(K key, BiFunction<K, V, V> remappingFunction)	지정된 키가 있을 때, 작업 remappingFunction 수행
		 * 				V merge(K key, V value, BiFunction<V, V, V> remappingFunction)		모든 요소 병합작업 remappingFunction 수행
		 * 				void forEach(BiConsumer<K, V> action)								모든 요소에 작업 action 수행
		 * 				void replaceAll(BiFunction<K, V, V> function)						모든 요소에 치환작업 function 수행
		 */
	}
	
	public static void methodReference() {
		// Static 메서드를 Function에 담는다.
		Function<String, Integer> method1 = (t) -> Integer.parseInt(t);
		
		// Function<String, Integer>에서 인자의 개수 및 타입과 결과의 개수 및 타입을 알기 때문에 메서드 참조로 변경이 가능하다.
		Function<String, Integer> method2 = Integer::parseInt;
		
		System.out.println("method1 : " + (method1.apply("10") + 10));
		System.out.println("method2 : " + (method2.apply("20") + 20));
	}
	
	public static void constructroReference() {
		// 인자가 없는 객체를 생성할 경우, Supplier에 담는다.
		Supplier<FunctionMain> constructor1 = () -> new FunctionMain();
		
		// Supplier<FunctionMain>에서 결과의 개수와 타입을 알기 때문에 객체 참조로 변경이 가능하다.
		Supplier<FunctionMain> constructor2 = FunctionMain::new;
		
		// 인자가 1개인 객체를 생성할 경우, Function에 담는다.
		Function<Integer, FunctionMain> constructor3 = (t) -> new FunctionMain(t);
		
		// Function<Integer, FunctionMain>에서 인자의 개수 및 타입과 결과의 개수 및 타입을 알기 때문에 객체 참조로 변경이 가능하다.
		Function<Integer, FunctionMain> constructor4 = FunctionMain::new;
		
		// 인자가 2개인 객체를 생성할 경우, Function에 담는다.
		BiFunction<Integer, Integer, FunctionMain> constructor5 = (t, u) -> new FunctionMain(t, u);
		
		// BiFunction<Integer, Integer, FunctionMain>에서 인자의 개수 및 타입과 결과의 개수 및 타입을 알기 때문에 객체 참조로 변경이 가능하다.
		BiFunction<Integer, Integer, FunctionMain> constructor6 = FunctionMain::new;
		
		// 인자가 1개인 객체를 생성할 경우, Function에 담는다.
		Function<Integer, int[]> constructor7 = (t) -> new int[t];
		
		// Function<Integer, FunctionMain>에서 인자의 개수 및 타입과 결과의 개수 및 타입을 알기 때문에 객체 참조로 변경이 가능하다.
		Function<Integer, int[]> constructor8 = int[]::new;
	}

}

@FunctionalInterface // 함수형 인터페이스는 단 하나의 추상 메서드만 가져야 함.
interface MyFunction {
	int max(int a, int b);
}
