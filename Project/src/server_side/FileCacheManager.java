package server_side;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class FileCacheManager<Pro,Sol> implements CacheManager<Pro, Sol> {

	HashMap<Pro, Sol> hash1 = new HashMap<Pro, Sol>();

	@Override
	public boolean existSolution(Pro problem) {

		if (hash1.containsValue(problem))
			return true;
		return false;

	}

	@Override
	public Sol loadSolution(Pro problem){
		
		Sol solution = null;

		if (hash1.containsKey(problem)) {
			solution = hash1.get(problem);
		} else {
			File file = new File("./" + (String)hash1.get(problem) + ".txt");
			if (file.exists()) {
				try {

					FileInputStream fileIn = new FileInputStream(file);
					ObjectInputStream objectIn = new ObjectInputStream(fileIn);

					solution = (Sol)(objectIn.readObject());

					System.out.println("The Object has been read from the file");
					objectIn.close();

				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
		return solution;

	}

	@Override
	public void store(Pro problem, Sol solution) {
		File file = new File("./" + (String)hash1.get(problem) + ".txt");
        try {
        	 
            FileOutputStream fileOut = new FileOutputStream(file);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(solution);
            objectOut.close();
            System.out.println("The Object  was succesfully written to a file");
 
        } catch (Exception ex) {
            ex.printStackTrace();
        }
		hash1.put(problem, solution);

	}


}
