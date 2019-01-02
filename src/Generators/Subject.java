package Generators;

public class Subject {
	int id;
	String name;
	Teacher[] teacher;
	boolean lab;
	int noteachers;
	Subject(){
		teacher=new Teacher[20];
	}
}
