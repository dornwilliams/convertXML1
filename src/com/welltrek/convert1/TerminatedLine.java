/**
 * 
 */
package com.welltrek.convert1;

/**
 * @author dorn
 *
 */

import java.io.BufferedReader;
import java.io.IOException;

public class TerminatedLine {
	
	String strWork;
	String strReturn;
	public int Count = 0;
	
	public String readLine(BufferedReader br) throws IOException {
		strReturn = "";
		
		do {
			strWork = br.readLine();
			Count++;
			
			if (strWork == null && strReturn.length() == 0)
				return null;
			else if (strWork == null)
				return strReturn;
			else
				strWork = strWork.trim();
			
			strReturn = (strReturn + " " + strWork).trim();
			
		} while ((strWork != null &&
				strWork.lastIndexOf(ConvertLine.KNonSentenceEnd_s) + ConvertLine.KNonSentenceEnd_s.length() != strWork.length()) &&
				!strWork.contains("<?"));

		return(strReturn);
	}

}
