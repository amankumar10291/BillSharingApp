package com.application.invoke;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

public class BillApp {
	
	private static HashMap<String, HashMap<String,Double>> billMap =  new HashMap<>();
	private static TreeMap<String, Double> personBalance = new TreeMap<>();
	
	private void createGroup(String gName) {
		billMap.put(gName, new HashMap<>());
		System.out.println("\nGroup created: " + gName);
	}
	
	private void addPerson(String group, List<String> names) {
		if(billMap.containsKey(group)) {
			HashMap<String, Double> addPerson = billMap.get(group);
			names.forEach((name) -> {
				if(!addPerson.containsKey(name)) {
					personBalance.put(name, 0d);
					addPerson.put(name,0d);
				}
			});
			billMap.put(group, addPerson);
			System.out.println("\nAdded person to group: " + group);
		}else {
			//Group doesn't exist
			System.err.println("Group doesn't exist.");
		}		
	}
 
	private void addTransaction(String group, List<String> personShare) {
		if(billMap.containsKey(group)) {
			try{
			HashMap<String, Double> addTrans = billMap.get(group);
			Double sum = 0d;
			for(String share: personShare) {
				String person = share.split(":")[0];
				Double amount = Double.parseDouble(share.split(":")[1]);
				sum += amount;
				addTrans.put(person,addTrans.get(person) + amount);
				personBalance.put(person, personBalance.get(person) + amount);
			}
			Double perPerson = sum/addTrans.size(); 
			addTrans.forEach((k,v)-> {
				addTrans.put(k,v - perPerson);
				personBalance.put(k, personBalance.get(k) - perPerson);
			});
			System.out.println("\nTransaction added to group: " + group);
			}catch(Exception e) {
				System.err.println("Exception: " + e.getMessage());
			}
		}
		else {
			//Create group first
			System.err.println("Group doesn't exist.");
		}
	}
	
	private void balanceOfPersonInGroup(String group, String person) {
		if(billMap.containsKey(group)) {
			HashMap<String, Double> groupShare = billMap.get(group);
			System.out.println("\nBalance_of_person_in_group(\"" + group + "\",\"" + person + "\")");
			if(groupShare.containsKey(person)){
				System.out.println("  Output: " + groupShare.get(person));
			}else {
				System.err.println("Person doesn't exist in this group.");
			}
		}
		else {
			//Group not exist
			System.err.println("Group Not Exist.");
		}
	}
	
	private void balanceOfPersonsInGroup(String group) {
		if(billMap.containsKey(group)) {
			HashMap<String, Double> groupShare = billMap.get(group);
			System.out.println("\nBalance_of_person_in_group(\"" + group + "\")");
			System.out.println("  Output: ");
			for(Entry<String, Double> entry: groupShare.entrySet()) {
				System.out.println("    " + entry.getKey() + ": " + entry.getValue());
			}
		}
		else {
			//Group not exist
			System.out.println("Group Not Exist.");
		}
	}
	
	private void balanceOfPersonAcrossGroup(String person) {
		if(personBalance.containsKey(person)){
			System.out.println("\nBalance_of_person_across_group(\"" + person + "\")");
			System.out.println("  Output: ");
			System.out.println("    " + person + ": " + personBalance.get(person));
		}
		else {
			System.err.println("Person account doesn't exist: " + person);
		}
	}
	
	public static void main(String[] args) {
		System.out.println("***** WELCOME TO FLIPKART BILL APP *****");
		BillApp app = new BillApp();
		app.createGroup("FLIPKART_PARTY");
		app.addPerson("FLIPKART_PARTY",Arrays.asList("Ashish","Devbrat","Anmol","Abhishek"));
		app.createGroup("SCHOOL_FRIENDS");
		app.addPerson("SCHOOL_FRIENDS",Arrays.asList("Ashish","Rohit"));
		app.addTransaction("FLIPKART_PARTY",Arrays.asList("Ashish:40","Devbrat:160"));
		app.addTransaction("FLIPKART_PARTY",Arrays.asList("Ashish:30","Abhishek:60","Anmol:110"));
		app.addTransaction("SCHOOL_FRIENDS",Arrays.asList("Ashish:100","Rohit:60"));
		app.balanceOfPersonInGroup("FLIPKART_PARTY","Ashish");
		app.balanceOfPersonsInGroup("FLIPKART_PARTY");
		app.balanceOfPersonAcrossGroup("Ashish");
	}
}
