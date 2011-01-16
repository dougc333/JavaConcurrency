package com.hackerdojo.concurrency;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
//import java.util.concurrent.*;
public class PopulationQuery {
	// next four constants are relevant to parsing
	public static final int TOKENS_PER_LINE  = 7;
	public static final int POPULATION_INDEX = 4; // zero-based indices
	public static final int LATITUDE_INDEX   = 5;
	public static final int LONGITUDE_INDEX  = 6;
	
	// parse the input file into a large array held in a CensusData object
	public static CensusData parse(String filename) {
		CensusData result = new CensusData();
		
        try {
            BufferedReader fileIn = new BufferedReader(new FileReader(filename));
            
            // Skip the first line of the file
            // After that each line has 7 comma-separated numbers (see constants above)
            // We want to skip the first 4, the 5th is the population (an int)
            // and the 6th and 7th are latitude and longitude (floats)
            // If the population is 0, then the line has latitude and longitude of +.,-.
            // which cannot be parsed as floats, so that's a special case
            //   (we could fix this, but noisy data is a fact of life, more fun
            //    to process the real data as provided by the government)
            
            String oneLine = fileIn.readLine(); // skip the first line

            // read each subsequent line and add relevant data to a big array
            while ((oneLine = fileIn.readLine()) != null) {
                String[] tokens = oneLine.split(",");
                if(tokens.length != TOKENS_PER_LINE)
                	throw new NumberFormatException();
                int population = Integer.parseInt(tokens[POPULATION_INDEX]);
                if(population != 0)
                	result.add(population,
                			   Float.parseFloat(tokens[LATITUDE_INDEX]),
                		       Float.parseFloat(tokens[LONGITUDE_INDEX]));
            }

            fileIn.close();
        } catch(IOException ioe) {
            System.err.println("Error opening/reading/writing input or output file.");
            System.exit(1);
        } catch(NumberFormatException nfe) {
            System.err.println(nfe.toString());
            System.err.println("Error in file format");
            System.exit(1);
        }
        return result;
	}
	public static void swap(float x, float y){
		float temp = x;
		x = y;
		y = temp;
	}
	
	// argument 1: file name for input data: pass this to parse
	// argument 2: number of x-dimension buckets
	// argument 3: number of y-dimension buckets
    // argument 4: -v1, -v2, -v3, -v4, or -v5
	public static void main(String[] args) throws IOException{
		// FOR YOU
		//min x, max x, min y, max y
		CensusData resultParse = parse("C:\\Users\\dc\\workspace\\Fork\\src\\blkgrp_pop_centroid_withname.txt");
		
		float minLat = resultParse.data[0].realLatitude;
		float maxLat = resultParse.data[0].realLatitude;
		
		float minLong = resultParse.data[0].longitude;
		float maxLong = resultParse.data[0].longitude;
		float pop = 0;
		BufferedWriter bw = new BufferedWriter(new java.io.FileWriter("C:\\Users\\dc\\Desktop\\lat.txt"));
		bw.write(new Float(resultParse.data[0].realLatitude).toString());
		bw.newLine();
		System.out.println("maxLat:"+maxLat+" minLat:"+minLat+" minLong:"+minLong+" maxLong:"+maxLong);	
		for(int i = 1;i < resultParse.data_size;i++){
		//	System.out.println("Lat:"+resultParse.data[i].realLatitude+" Long:"+resultParse.data[i].longitude);
			bw.write(new Float(resultParse.data[i].realLatitude).toString());
			bw.newLine();
			if(resultParse.data[i].realLatitude < minLat ){
				minLat=resultParse.data[i].realLatitude;
			}
			if(resultParse.data[i].realLatitude > maxLat ){
				maxLat=resultParse.data[i].realLatitude;
			}
			if(resultParse.data[i].longitude < minLong ){
				minLong=resultParse.data[i].longitude;
			}
			if(resultParse.data[i].longitude > maxLong ){
				maxLong=resultParse.data[i].longitude;
			}
			pop += resultParse.data[i].population;
		}
		bw.close();
		double floorMinLat = Math.floor((double)minLat);
		double ceilMaxLat = Math.ceil((double)maxLat);
		
		System.out.println("maxLat:"+maxLat+" minLat:"+minLat+" minLong:"+minLong+" maxLong:"+maxLong);
		System.out.println("minLat:"+minLat+" floorMinLat:"+floorMinLat+" maxLat:"+maxLat+" ceilMaxLat:"+ceilMaxLat);

		System.out.println("total pop:"+pop);
		
		//maxLat:32.48103 minLat:17.995453 minLong:-172.97658 maxLong:-65.300705
		int numXRects = 100;
		int numYRects = 50;
		float latGridSize = (maxLat - minLat)/100;
		float longGridSize = (maxLong - minLong)/50;
		double gridLat = minLat;
		double gridLong = minLong;
		int grid[][]=new int [100][50];
		for(int i=0;i<numXRects;i++){
			System.out.println("i is:"+i+" xsize is:"+latGridSize+" Lat:"+gridLat);
			gridLat = gridLat+latGridSize;
			for(int j=0;j<numYRects;j++){
				if(j==0||j==1){
				System.out.println("lat:"+gridLat+"j is:"+j+" ySize:"+longGridSize+" long:"+gridLong);
				}
				gridLong = gridLong + longGridSize;
				grid[i][j]=0;
			}
			gridLong=0;
			
		}
		
		//go through population grid again and figure out which grid coordinate the population belongs 
		//in
		for(int i = 1;i < resultParse.data_size;i++){
			System.out.println("Lat:"+resultParse.data[i].realLatitude+" Long:"+resultParse.data[i].longitude);
			System.out.println("maxLat:"+maxLat+" minLat:"+minLat+" minLong:"+minLong+" maxLong:"+maxLong);
			System.out.println("xSize:"+latGridSize+" ySize:"+longGridSize);
			//subtract the min latitude and minlongitude then do a mod xSize and ySize to 
			//figure out the grid coordinate the population is added to
			float subMinLat = minLat - resultParse.data[i].realLatitude;
			float subMinLong = minLong -resultParse.data[i].longitude;
			System.out.println("subMinLat:"+subMinLat+" subMinLong:"+subMinLong);
			//mod out the xsize
			//are these nubmers correct? 
			double latIndex = subMinLat / latGridSize;
			double longIndex = subMinLong / longGridSize;
			System.out.println("latIndex:"+latIndex+" longIndex:"+longIndex);
			System.exit(0);
		}
		
		//2 dim query array, minx(west) max x(east), min y(south)  max y(north)
		float queryRectangle [] = {10, 35, 20, 40};
		//how do you tell which grid[][] are in the query rectangle? 
		//how do you tell which coordinates are in which grid entry?
		
		
	}
}
