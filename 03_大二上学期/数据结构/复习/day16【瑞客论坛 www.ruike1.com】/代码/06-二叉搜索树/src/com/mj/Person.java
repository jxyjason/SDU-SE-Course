package com.mj;

public class Person implements Comparable<Person> {
	private int age;
	private String name;
	
	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Person(int age) {
		this.age = age;
	}

	public Person(int age, String name) {
		this.age = age;
		this.name = name;
	}

	@Override
	public int compareTo(Person e) {
//		if (age > e.age) return 1;
//		if (age < e.age) return -1;
//		return 0;
		return age - e.age;
	}
	
	@Override
	public String toString() {
		return age + "_" + name;
	}
	
}
