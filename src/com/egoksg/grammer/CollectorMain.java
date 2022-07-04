package com.egoksg.grammer;

import static java.util.stream.Collectors.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.egoksg.sample.Student;

/** 
 * Collector 인터페이스와 Collectors 클래스는 Stream의 최종 소비인 collect()에서 사용된다.
 * 
 * [ Collector<T, A, R> 인터페이스 ]
 *
 * 	요소 T를 A에 누적하고 R롤 반환한다.
 * 
 * 		supplier() 		: 누적할 공간을 제공한다. R
 * 		accumulator() 	: 누적을 수행한다. A
 * 		combiner()		: 병렬 스트림일 경우, 흩어진 누적 공간을 합치친다.
 *  	finisher() 		: 최종 변환한다.
 *  
 * [ Collectors 클래스 ]
 * 
 * 	Collector를 구현한 클래스로 다양한 걸 제공한다.
 * 
 * 변환 - mapping(), toList(), toSet(), toCollection(), ...
 * 통계 - counting(), summingInt(), averagingInt(), minBy(), maxBy(), summarizingInt(), ...
 * 문자열 결합 - joining()
 * 리듀싱 - reducing()
 * 그룹화와 분할 - groupingBy(), partitioningBy(), collectingAndThen()
 */
public class CollectorMain {

	public static void main(String[] args) {
		// Stream을 Collection으로 변환한다.
		transrate();
		
		// Stream의 요소로 통계를 낸다.
		statistics();
		
		// reducing 
		reducingMain();
		
		// Stream<Strim>의 문자열을 결합한다.
		joiningMain();
		
		// 그룹화와 분할
		groupMain();
	}
	
	public static void transrate() {
		/*
		 * Collector<T, ?, List<T>> toList()
		 * 
		 * Stream<T>를 List<T>로 변환한다.
		 */
		List<Student> collect = Student.getStudentStream().collect(Collectors.toList());
		/*
		 * <T, C extends Collection<T>> Collector<T, ?, C> toCollection(Supplier<C> collectionFactory)
		 * 
		 * Stream<T>를 지정한 컬렉션으로 변환한다.
		 */
		ArrayList<Student> collect2 = Student.getStudentStream().collect(Collectors.toCollection(ArrayList::new));
		/*
		 * Collector<T, ?, Map<K,U>> toMap(Function<? super T, ? extends K> keyMapper, Function<? super T, ? extends U> valueMapper)
		 * 
		 * Stream<T>를 Map<K, V>로 변환한다.
		 */
		Map<String, Student> collect3 = Student.getStudentStream().collect(Collectors.toMap(student->student.getRegId(), student->student));
	}
	
	public static void statistics() {
		Student.getStudentStream().collect(counting());
		Student.getStudentStream().collect(summingInt(Student::getTotalScore));
		Student.getStudentStream().collect(averagingInt(Student::getTotalScore));
		Student.getStudentStream().collect(minBy(Comparator.comparingInt(Student::getTotalScore)));
		Student.getStudentStream().collect(maxBy(Comparator.comparingInt(Student::getTotalScore)));
	}
	
	public static void reducingMain() {
		// 클래스의 static 메서드는 import static로 클래스를 안 붙여도 된다.
		Student.getStudentStream().collect(reducing(new BinaryOperator<Student>() {
			@Override
			public Student apply(Student t, Student u) {
				return t.getTotalScore() > u.getTotalScore() ? t : u;
			}
		})).ifPresent(System.out::println);
		
		// apply()가 실행되므로 해당 부분으 FP로 넣을 수 있다.
		Student.getStudentStream().collect(reducing((t, u) -> t.getTotalScore() > u.getTotalScore() ? t : u))
		.ifPresent(System.out::println);
		
		Integer sumTotalScore1 = Student.getStudentStream().collect(reducing(0, Student::getTotalScore, new BinaryOperator<Integer>() {
			@Override
			public Integer apply(Integer t, Integer u) {
				return t + u;
			}
		}));
		// apply()가 실행되므로 해당 부분으 FP로 넣을 수 있다.
		Integer sumTotalScore2 = Student.getStudentStream().collect(reducing(0, Student::getTotalScore, Integer::sum));
		System.out.println("sumTotalScore1 : " + sumTotalScore1);
		System.out.println("sumTotalScore2 : " + sumTotalScore2);
	}
	
	public static void joiningMain() {
		/*
		 * Stream<Strim>의 문자열을 결합한다.
		 * 
		 * Collector<CharSequence, ?, String> joining()
		 * Collector<CharSequence, ?, String> joining(CharSequence delimiter)
		 * Collector<CharSequence, ?, String> joining(CharSequence delimiter, CharSequence prefix, CharSequence suffix)
		 */
		String names1 = Student.getStudentStream().map(Student::getName).collect(joining());
		String names2 = Student.getStudentStream().map(Student::getName).collect(joining(", "));
		String names3 = Student.getStudentStream().map(Student::getName).collect(joining(", ", "[", "]"));
		System.out.println("names1 : " + names1);
		System.out.println("names2 : " + names2);
		System.out.println("names3 : " + names3);
	}
	
	public static void groupMain() {
		/*
		 * partitioningBy은 조건(predicate)에 따라 true/false로 나뉜다.
		 * 3번째 인자에 Collector을 분할된 목록을 가지고 통계를 낼 있다.
		 * 
		 * Collector<T, ?, Map<Boolean, List<T>>> partitioningBy(Predicate<? super T> predicate)
		 * Collector<T, ?, Map<Boolean, D>> partitioningBy(Predicate<? super T> predicate, Collector<? super T, A, D> downstream)
		 */
		// 분할
		Map<Boolean, List<Student>> collect1 = Student.getStudentStream().collect(partitioningBy(Student::isMale));
		System.out.println("남자 목록 : " + collect1.get(true).toString());
		System.out.println("여자 목록 : " + collect1.get(false).toString());
		
		// 분할 + 통계
		Map<Boolean, Long> collect2 = Student.getStudentStream().collect(partitioningBy(Student::isMale, counting()));
		System.out.println("남자 수 : " + collect2.get(true));
		System.out.println("여자 수 : " + collect2.get(false));
		
		// 분할 + 통계
		Map<Boolean, Optional<Student>> collect3 = Student.getStudentStream().collect(
			partitioningBy(Student::isMale, // 1. 남자와 여자를 파티셔닝한다.
				maxBy(Comparator.comparingInt(Student::getTotalScore)))); // 2. 학생의 점수를 비교하여 최대인 학생 추출
		System.out.println("남자 1등 : " + collect3.get(true));
		System.out.println("여자 1등 : " + collect3.get(false));
		
		// 다중 분할
		Map<Boolean, Map<Boolean, List<Student>>> collect4 = Student.getStudentStream().collect(
			partitioningBy(Student::isMale,// 1. 남자와 여자를 파티셔닝한다.
				partitioningBy(student -> student.getTotalScore() > 80))); // 2. 80점 기준으로 파티셔님한다.
		System.out.println("남자 합격자 : " + collect4.get(true).get(true));
		System.out.println("여자 합격자 : " + collect4.get(false).get(true));
		
		
		
		
		
		/*
		 * [ groupingBy ]
		 * 	
		 * Function classifier : 그룹핑할 대상
		 * Supplier mapFactory : 그룹이 추가될 때 생성될 Map
		 * Collector downstream : 추가적으로 그롭핑할 대상
		 * 
		 * 
		 * Collector<T, ?, Map<K, List<T>>> groupingBy(Function<? super T, ? extends K> classifier)
		 * Collector<T, ?, Map<K, D>> groupingBy(Function<? super T, ? extends K> classifier,
                                          Collector<? super T, A, D> downstream)
		 * Collector<T, ?, M> groupingBy(Function<? super T, ? extends K> classifier,
                                  Supplier<M> mapFactory,
                                  Collector<? super T, A, D> downstream)
		 */
		//  ban
		Map<Integer, List<Student>> collect5 = Student.getStudentStream().collect(
			groupingBy(Student::getBan, toList())); // ban으로 그룹핑
		System.out.println("------------- group by ban -------------");
		System.out.println(collect5.toString());
		
		//  hak          ban
		Map<Integer, Map<Integer, List<Student>>> collect6 = Student.getStudentStream().collect(
				groupingBy(Student::getHak, // hak으로 그룹핑
					groupingBy(Student::getBan, toList()))); // ban으로 그룹핑
		System.out.println("------------- group by hak, ban -------------");
		System.out.println(collect6.toString());
		
		//  hak          ban          grade
		Map<Integer, Map<Integer, Set<String>>> collect7 = Student.getStudentStream().collect(
			groupingBy(Student::getHak, // hak으로 그룹핑
				groupingBy(Student::getBan, // ban으로 그룹핑
					mapping(student -> { // 학생 정보를 받아 totalScore를 3단계로 분류하여 반환
						if     (student.getTotalScore() > 80) return "HIGH";
						else if(student.getTotalScore() > 50) return "MIDDEL";
						else                                  return "LOW";
					}, toSet())
				)
			)
		);
		System.out.println("------------- group by hak, ban -------------");
		System.out.println(collect7.toString());
		
		//  hak          ban      최고 성적 학생
		Map<Integer, Map<Integer, Student>> collect8 = Student.getStudentStream().collect(
			groupingBy(Student::getHak,// hak으로 그룹핑
				groupingBy(Student::getBan, // ban으로 그룹핑
					// collectingAndThen : 추가 마무리 변환을 수행하도록 수집기를 조정합니다.(통계, Collector)
					collectingAndThen(
						// 학생의 totalScore를 꺼내서
						// 기존 학생과 비교하여 더 높은(max) 꺼내라
						maxBy(Comparator.comparingInt(Student::getTotalScore)),
						Optional::get
					)
				)
			)
		);
		System.out.println("------------- group by hak, ban, 최고 성적 학생 -------------");
		System.out.println(collect8.toString());
		
	}
}
