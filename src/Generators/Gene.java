package Generators;

import java.util.Random;
import java.io.*;

//gene represents permutation of slots as timetable for a single student group(looks like {5,22,14,1,...} )
public class Gene implements Serializable{

	public int slotno[];
	int days=inputdata.daysperweek;
	int hours=inputdata.hoursperday;
	
	Random r=new Random();
	
	Gene(int i){
		
		boolean[] flag=new boolean[days*hours];
		
		/*  generating an array of slot no corresponding to index of gene eg suppose index
		 *	is 2 then slotno will vary from 2*hours*days to 3*hours*days
		 */
		int[] sep = {0,1,2,3,4,3,4,4};
		int[] labHours = inputdata.labHours.clone();
		int[] labCP = inputdata.labCheckPoint.clone();
		slotno=new int[days*hours];

		int j = 0;
		while (j<days*hours){
			
			int rnd;
			while(flag[rnd=r.nextInt(days*hours)]==true){}

			if (rnd >= inputdata.theorySubjectHours && rnd < inputdata.noSubject) {
				int k = 0;
				while (k < labHours.length-1) {
					if (rnd >= labCP[k] && rnd < labCP[k+1]) {
						break;
					}
					k++;
				}
				rnd = labCP[k];
				int hour = 1;
				while (hour <= labHours[k]) {
					flag[rnd]=true;
					slotno[j]=i*days*hours + rnd;
					j++;
					hour++;
					rnd++;
				}
			}

			else {
				flag[rnd]=true;
				slotno[j]=i*days*hours + rnd;
				j++;
			}
		}
	}
	
	public Gene deepClone() {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(this);

			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bais);
			return (Gene) ois.readObject();
		} catch (IOException e) {
			return null;
		} catch (ClassNotFoundException e) {
			return null;
		}
	}
}