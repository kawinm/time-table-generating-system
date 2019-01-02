package Generators;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class inputdata {

	public static StudentGroup[] studentgroup;
	public static Teacher[] teacher;
	public static double crossoverrate = 1.0, mutationrate = 0.1;
	public static int nostudentgroup, noteacher;
	public static int hoursperday, daysperweek;
	int maxhours;
	public static String[] allSubject;
	public static int theorySubjectHours =0;
	public static int[] labHours;
	public static int[] labCheckPoint;
	public static int noSubject = 0;
	
	
	public inputdata() {
		studentgroup = new StudentGroup[10];
		teacher =   new Teacher[100];
	}

	boolean classformat(String l) {
		StringTokenizer st = new StringTokenizer(l, " ");
		if (st.countTokens() == 3)
			return (true);
		else
			return (false);
	}

	public void takeinput()// takes input from file input.txt
	{
		//this method of taking input through file is only for development purpose so hours and days are hard coded
		hoursperday = 7;
		daysperweek = 5;
		try {
			BufferedReader br = new BufferedReader(new FileReader("input.txt"));
			// File file = new File(System.getProperty("user.dir") +
			// "/input.txt");
			String line = br.readLine();
			while (line != null) {
				
				// input student groups
				if (line.equalsIgnoreCase("studentgroups")) {
					int i = 0, j;
					while (!((line = br.readLine()).equalsIgnoreCase("end"))) {
						studentgroup[i] = new StudentGroup();
						StringTokenizer st = new StringTokenizer(line, " ");
						studentgroup[i].id = i;
						studentgroup[i].name = st.nextToken();
						studentgroup[i].nosubject = 0;
						maxhours =0;
						j = 0;
						while (st.hasMoreTokens()) {
							studentgroup[i].subject[j] = st.nextToken();
							if (studentgroup[i].subject[j].startsWith("l"))
								studentgroup[i].lab[j] = true;
							else
								studentgroup[i].lab[j] = false;
							studentgroup[i].hours[j] = Integer.parseInt(st.nextToken());
							teacher[j] = new Teacher();
							teacher[j].id = j;
							teacher[j].name = st.nextToken();
							teacher[j].subject = studentgroup[i].subject[j];
							maxhours += studentgroup[i].hours[j++];
							studentgroup[i].nosubject++;
						}
						noteacher = j;
						studentgroup[i].maxHours = maxhours;
						i++;
					}
					nostudentgroup = i;
				}

				line = br.readLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		allSubject = studentgroup[0].subject.clone();
		assignTeacher();

	}

	// assigning a teacher for each subject for every studentgroup
	public void assignTeacher() {

		// looping through every studentgroup
		for (int i = 0; i < nostudentgroup; i++) {

			// looping through every subject of a student group
			for (int j = 0; j < studentgroup[i].nosubject; j++) {

				int teacherid = -1;
				int assignedmin = -1;

				String subject = studentgroup[i].subject[j];

				// looping through every teacher to find which teacher teaches the current subject
				for (int k = 0; k < noteacher; k++) {

					// if such teacher found,checking if he should be assigned the subject or some other teacher based on prior assignments
					if (teacher[k].subject.equalsIgnoreCase(subject)) {

						// if first teacher found for this subject
						if (assignedmin == -1) {
							assignedmin = teacher[k].assigned;
							teacherid = k;
						}

						// if teacher found has less no of pre assignments than the teacher assigned for this subject
						else if (assignedmin > teacher[k].assigned) {
							assignedmin = teacher[k].assigned;
							teacherid = k;
						}
					}
				}

				// 'assigned' variable for selected teacher incremented
				teacher[teacherid].assigned++;

				studentgroup[i].teacherid[j]= teacherid;
			}
		}
	}

	public void subjectClassifier() {

		int sub = studentgroup[0].nosubject;
		for (int j = 0; j < sub; j++ ) {
			if (studentgroup[0].lab[j] == true) {
				studentgroup[0].noLabSubject++;
			}
			else {
				studentgroup[0].noTheorySubject++;
			}
		}
		totalTheorySubjectHours();

	}

	void totalTheorySubjectHours() {
		int sub = studentgroup[0].nosubject;
		for (int j = 0; j < sub; j++ ) {
			if (studentgroup[0].lab[j] == false) {
				theorySubjectHours += studentgroup[0].hours[j];
				noSubject += studentgroup[0].hours[j];
			}
			else {
				noSubject += studentgroup[0].hours[j];
			}
		}
		calculateLabCheckPoint();
	}

	void calculateLabCheckPoint() {
		labCheckPoint = new int[studentgroup[0].noLabSubject];
		int k = 0, next = 0;
		int sub = studentgroup[0].nosubject;
		for (int j = 0; j < sub; j++ ) {
			if (studentgroup[0].lab[j] == true) {
				if (k == 0) {
					labCheckPoint[k] = theorySubjectHours;
					next = studentgroup[0].hours[j];
				}
				else{
					labCheckPoint[k] = labCheckPoint[k-1] + next;
					next = studentgroup[0].hours[j];
				}
				k++;
			}
		}
		calculateLabHours();
	}

	void calculateLabHours() {
		int k = 0;
		int sub = studentgroup[0].nosubject;
		labHours = new int[studentgroup[0].noLabSubject];
		for (int j = 0; j < sub; j++ ) {
			if (studentgroup[0].lab[j] == true) {
				labHours[k] = studentgroup[0].hours[j];
				k++;
			}
		}

	}



}