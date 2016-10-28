/**
 * 
 */
package com.welltrek.convert1;

/**
 * @author dorn
 *
 */

// |File|Chapter|id|string|

public class ConvertLine {
	public static final String KNonSentenceStart = "<xces:s>";
	public static final String KNonSentenceEnd = "</xces:s>";
	public static final String KSentenceStart = "<xces:s id=";

	public String C2P(String strFile, String strIn) {

		int beginIndex;
		int endIndex;
		String strWork;		// Work String
		String strOut;		// String to be returned
		String strPS;		// Paragraph / sentence string

		if (strIn.startsWith("?") || strIn.startsWith("<?")) {
			return ("");
		}

		strIn = strIn.trim();
		if (strIn.length() == 0) {
			return ("");
		}

		// Look for start of non-sentence
		beginIndex = strIn.indexOf(KNonSentenceStart);
		if (beginIndex != -1) {
			endIndex = strIn.indexOf(KNonSentenceEnd);
			strOut = strIn.substring(beginIndex + KNonSentenceStart.length(), endIndex );
			return(strFile + "|"+strOut+"|||\n");
		}

		strOut = "";
		strWork = "";
		// Look for start of sentence

		try {
			while(strIn != null && strIn.length() > 0) {

				beginIndex = strIn.indexOf(KSentenceStart);
				endIndex = strIn.indexOf("\">");
				strPS = strIn.substring(beginIndex + KSentenceStart.length() + 1, endIndex);
				beginIndex = endIndex + 2;		// End of xml tag is start of sentence
				endIndex = strIn.indexOf(KNonSentenceEnd);
				strWork = strIn.substring(beginIndex, endIndex);
				strIn = strIn.substring(endIndex + KNonSentenceEnd.length());
				strOut = strOut +  strFile + "||"+ strPS + "|" + strWork + "|\n";
			}
		} catch (Exception e) {
			strOut = null;
		}


		System.out.println(strOut);
		return (strOut);
	}

}
