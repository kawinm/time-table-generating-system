package Generators;

import Database.condb;

import java.io.*;
import java.util.*;

import static Generators.inputdata.studentgroup;

//Chromosome represents array of genes as complete timetable (looks like gene[0]gene[1]gene[2]...)
public class Chromosome implements Comparable<Chromosome>,Serializable{
	
	static double crossoverrate=inputdata.crossoverrate;
	static double mutationrate=inputdata.mutationrate;
	static int hours=inputdata.hoursperday,days=inputdata.daysperweek;
	static int nostgrp=inputdata.nostudentgroup;
	int maxhours[] = new int[nostgrp];
	double fitness;
	int point;
	public static String[][] tt = new String[5][7];
	public Gene[] gene;
	
	Chromosome(){
		
		gene=new Gene[nostgrp];
		
		for(int i=0;i<nostgrp;i++){
			
			gene[i]=new Gene(i);

		}
		fitness=getFitness();		
		
	}
	
	public Chromosome deepClone() {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(this);

			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bais);
			return (Chromosome) ois.readObject();
		} catch (IOException e) {
			return null;
		} catch (ClassNotFoundException e) {
			return null;
		}
	}

	public double getFitness(){
		point=0;
		double freehourpoints = 0, labpoints = 0;
		for (int j=0;j<nostgrp;j++) {
			maxhours[j] = studentgroup[j].maxHours +(j*hours*days);
		}
		for(int i=0;i<hours*days;i++){
			
			List<Integer> teacherlist=new ArrayList<Integer>();
			
			for(int j=0;j<nostgrp;j++) {

				Slot slot;
				//System.out.println("i="+i+" j="+j);
				if (TimeTable.slot[gene[j].slotno[i]] != null)
					slot = TimeTable.slot[gene[j].slotno[i]];
				else slot = null;

				//teacher clash check
				if (slot != null) {

					if (teacherlist.contains(slot.teacherid)) {
						point++;
					} else teacherlist.add(slot.teacherid);
				}

				//Hard Constraint: No free first hours
				if (i % 7 == 0 && gene[j].slotno[i] > maxhours[j])
					freehourpoints += 0.1;

				//Hard Constraint: No free fifth hours
				if (i % 7 == 4 && gene[j].slotno[i] > maxhours[j])
					freehourpoints += 0.1;

				//Hard Constraint: No lab cutting between days
				if (i != 0 && i % 7 == 0 && gene[j].slotno[i] == gene[j].slotno[i-1]+1 )
					labpoints += 0.1;

				//Hard Constraint: No lab cutting in lunch hour
				if (i % 7 == 4 && gene[j].slotno[i] == gene[j].slotno[i-1]+1)
					labpoints += 0.05;


			}
		}

		fitness=1-(point/(nostgrp*hours*days)) - (freehourpoints / nostgrp) - labpoints;
		point=0;
		return fitness;
	}
	
	
	
	//printing final timetable
	public void printTimeTable(){
		
		//for each student group separate time table
		for(int i=0;i<nostgrp;i++){
			
			//status used to get name of student grp because in case first class is free it will throw error
			boolean status=false;
			int l=0;
			while(!status){
				
				//printing name of batch
				if(TimeTable.slot[gene[i].slotno[l]]!=null){
					//System.out.println("Batch "+TimeTable.slot[gene[i].slotno[l]].studentgroup.name+" Timetable-");
					
					status=true;
				}
				l++;
			
			}
			
			//looping for each day
			for(int j=0;j<days;j++){
				
				//looping for each hour of the day
				for(int k=0;k<hours;k++){
				
					//checking if this slot is free otherwise printing it
					if(TimeTable.slot[gene[i].slotno[k+j*hours]]!=null) {

						//System.out.print(TimeTable.slot[gene[i].slotno[k + j * hours]].subject + " ");
						tt[j][k] = TimeTable.slot[gene[i].slotno[k + j * hours]].subject ;
					}
				
					else {
						//System.out.print("*FREE* ");
						tt[j][k] = "*FREE*";
					}
				
				}
				
				//System.out.println("");
			}
			
			//System.out.println("\n\n\n");
		
		}

		condb.insertSG(TimeTable.slot[gene[0].slotno[0]].studentgroup.name);
		condb.insertTTSG();

	}
	
	
	
	public void printChromosome(){
		
		for(int i=0;i<nostgrp;i++){
			for(int j=0;j<hours*days;j++){
				//System.out.print(gene[i].slotno[j]+" ");
			}
			//System.out.println("");
		}
		
	}



	public int compareTo(Chromosome c) {
		
		if(fitness==c.fitness) return 0;
		else if(fitness>c.fitness) return -1;
		else return 1;
	
	}
	
	
	
}