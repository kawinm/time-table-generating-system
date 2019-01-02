package Generators;

public class StudentGroup {
	public int id;
	public String name;
	public String[] subject;
	public boolean[] lab;
	public int nosubject;
	public int noTheorySubject;
	public int noLabSubject;
	public int teacherid[];
	public int[] hours;
	public int maxHours;
	
	public StudentGroup() {
		lab = new boolean[10];
		subject=new String[10];
		hours=new int[10];
		teacherid=new int[10];
	}

}
