/**
 * 
 */
package com.welltrek.convert1;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.File;
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
		String strFileName = "";

		Boolean bFail = false;
		Boolean bAppend = false;
		Boolean bVerbose = false;
		Boolean bOutputExists = false;

		String strIn;

		System.out.println("Starting AdinaConvert 1.3");
		System.out.println("\tWorking Directory:" + System.getProperty("user.dir"));

		for (int i=0; i<args.length; i++)
			System.out.printf("\tArg %d: %s\n",i,args[i]);

		// Validate the input parameters
		if (args.length < 2 || args.length > 3) {
			System.out.println("Invalid parameters: (options) Infile Outfile\n");
			System.out.println(" -v -- verbose");
			System.out.println(" -a -- append output file");
			System.exit(1);
		}

		// Case where there isn't an option
		if (args.length == 2) {
			try {
				inputStream = new FileReader(args[0]);
				strFileName = args[0];
				File fOut = new File(args[1]);
				bOutputExists = fOut.exists();
				outputStream = new FileWriter(args[1]);
			} catch (IOException e) {
				System.out.printf("IOException : %s\n",e);
				e.printStackTrace();
				System.exit(1);
			} catch(Exception e) {
				System.out.printf("Exception : %s\n",e);
				e.printStackTrace();
				System.exit(1);
			}
		} else if (args.length == 3) {
			if (args[0].substring(0, 1).compareTo("-") != 0)
				bFail = true;
			else if (args[0].substring(1, 2).compareToIgnoreCase("a") == 0)
				bAppend = true;
			else if (args[0].substring(1, 2).compareToIgnoreCase("v") == 0)
				bVerbose = true;
			else if  (args[0].substring(2, 3).compareToIgnoreCase("a") == 0)
				bAppend = true;
			else if (args[0].substring(2, 3).compareToIgnoreCase("v") == 0)
				bVerbose = true;
			else bFail = true;

			// Three parameters
			try {
				inputStream = new FileReader(args[1]);
				strFileName = args[1];
				File fOut = new File(args[2]);
				bOutputExists = fOut.exists();
				outputStream = new FileWriter(args[2],bAppend);

			} catch (IOException e) {
				System.out.printf("IOException : %s\n",e);
				e.printStackTrace();
				System.exit(1);
			} catch(Exception e) {
				System.out.printf("Exception : %s\n",e);
				e.printStackTrace();
				System.exit(1);
			}
		}

		if (bFail) {
			System.out.println("Invalid parameters: (options) Infile Outfile");
			System.out.println(" -v -- verbose");
			System.out.println(" -a -- append output file");
			System.exit(1);
		}

		try {
			bufferReader = new BufferedReader(inputStream);
			ConvertLine convertLine = new ConvertLine();
			
			// Only output header if the output file does not exist yet
			if (!bOutputExists || !bAppend)
				outputStream.write("FileName | Chapter|ID|Sentence|\n");

			// Get a line which ends with string end
			while ((strIn = tl.readLine(bufferReader)) != null) {
				String strOut = convertLine.C2P(strFileName,strIn,bVerbose);

				if (strOut != null && strOut.length() > 0)
					outputStream.write(strOut);
			}
		} catch (IOException e) {
			System.out.printf("IOException : %s\n",e);
			e.printStackTrace();
		} catch(Exception e) {
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
