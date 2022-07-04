package com.egoksg.sample;

import java.util.stream.Stream;

public class Student implements Comparable<Student> {
	private String regId;
	private int hak;
	private int ban;
	private int totalScore;
	private String name;
	private String male;
	
	public Student(Builder builder) {
		this.regId = builder.regId;
		this.hak = builder.hak;
		this.ban = builder.ban;
		this.totalScore = builder.totalScore;
		this.name = builder.name;
		this.male = builder.male;
	}
	
	public static class Builder {
		private String regId;
		private int hak;
		private int ban;
		private int totalScore;
		private String name;
		private String male;
		
		public Builder regId(String regId) {
			this.regId = regId;
			return this;
		}
		public Builder ban(int ban) {
			this.ban = ban;
			return this;
		}
		public Builder hak(int hak) {
			this.hak = hak;
			return this;
		}
		public Builder totalScore(int totalScore) {
			this.totalScore = totalScore;
			return this;
		}
		public Builder name(String name) {
			this.name = name;
			return this;
		}
		public Builder male(String male) {
			this.male = male;
			return this;
		}
		
		public Student build() {
			return new Student(this);
		}
	}
	
	public static Builder builder() {
		return new Builder();
	}
	
	
	// Comparable 기본 정렬 : 총점으로 내리차순
	@Override
	public int compareTo(Student o) {
		return o.getTotalScore() - this.getTotalScore();
	}
	
	@Override
	public String toString() {
		return
			"regId = " + this.getRegId()
			+ "hak = " + this.getHak()
			+ "ban = " + this.getBan()
			+ ", totalScore = " + this.getTotalScore()
			+ ", name = " + this.getName()
			+ ", male = " + this.getMale()
		;
	}


	public String getRegId() {
		return regId;
	}
	public int getBan() {
		return ban;
	}
	public int getTotalScore() {
		return totalScore;
	}
	public String getName() {
		return name;
	}
	public String getMale() {
		return male;
	}
	public boolean isMale() {
		return "남자".equals(this.male) ? true : false;
	}
	public int getHak() {
		return hak;
	}
	
	public static Stream<Student> getStudentStream() {
		return Stream.of(
			Student.builder().regId("std1").hak(1).ban(1).totalScore(90).name("홍길동").male("남자").build(),
			Student.builder().regId("std2").hak(1).ban(2).totalScore(80).name("이순신").male("여자").build(),
			Student.builder().regId("std3").hak(1).ban(2).totalScore(40).name("강감찬").male("남자").build(),
			Student.builder().regId("std4").hak(2).ban(1).totalScore(80).name("유관순").male("여자").build()
		);
	}
	
}
