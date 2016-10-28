/**
 * 
 */
package com.welltrek.convert1;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;

import java.io.IOException;

/**
 * @author dorn
 *
 */
public class Process {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

        FileReader inputStream = null;
        FileWriter outputStream = null;
        BufferedReader bufferReader = null;
        TerminatedLine tl = new TerminatedLine();

        String strIn;
        
        System.out.println("Starting AdinaConvert 1.1\n");
        System.out.println(System.getProperty("user.dir"));

        for (int i=0; i<args.length; i++)
        	System.out.printf("Num %d: String %s\n",i,args[i]);

        // Validate the input parameters
        if (args.length < 2) {
        	System.out.println("Must have two parameters: Infile Outfile\n");
        	System.exit(1);
        }
        
        try {
            inputStream = new FileReader(args[0]);
            outputStream = new FileWriter(args[1]);
            bufferReader = new BufferedReader(inputStream);
            ConvertLine convertLine = new ConvertLine();
            
            outputStream.write("FileName | Chapter|ID|Sentence|\n");
            
            // Get a line which ends with string end
            

            while ((strIn = tl.readLine(bufferReader)) != null) {
            	String strOut = convertLine.C2P(args[0],strIn);
            	
            	if (strOut != null && strOut.length() > 0)
                	//if (lineCount++ !=0) outputStream.append('\n');
            		outputStream.write(strOut);
            }
        } catch (IOException e) {
        	System.out.printf("Exception : %s\n",e);
        	e.printStackTrace();

        } finally {
        	try {
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
        	} catch (IOException e) { 
        		System.out.println("Exception e.toString()");
        		
        	}
        }		
		
        System.out.printf("Normal termination: %d lines\n",tl.Count);

	}

}
