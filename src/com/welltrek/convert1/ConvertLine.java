/**
 * 
 */
package com.welltrek.convert1;

/**
 * @author dorn
 *
 * Assumes that either sentence or utterance -- not both in one line
 */

// |File|Chapter|id|string|

public class ConvertLine {
	public static final String KNonSentenceStart_s = "<xces:s>";
	public static final String KNonSentenceEnd_s = "</xces:s>";
	public static final String KSentenceStart_s = "<xces:s id=";

	public static final String KNonSentenceStart_u = "<xces:u>";
	public static final String KNonSentenceEnd_u = "</xces:u>";
	public static final String KSentenceStart_u = "<xces:u id=";

	public String C2P(String strFile, String strIn, Boolean bVerbose) {

		int beginIndex;
		int endIndex;
		String strWork;		// Work String
		String strOut;		// String to be returned
		String strPS;		// Paragraph / sentence string
		boolean bSentence = false;

		if (strIn.startsWith("?") || strIn.startsWith("<?")) {
			return ("");
		}

		// Remove excess spaces
		strIn = strIn.trim();
		if (strIn.length() == 0) {
			return ("");
		}

		// Look for start of non-sentence of sentence 
		beginIndex = strIn.indexOf(KNonSentenceStart_s);
		if (beginIndex != -1) {
			endIndex = strIn.indexOf(KNonSentenceEnd_s);
			strOut = strIn.substring(beginIndex + KNonSentenceStart_s.length(), endIndex );
			return(strFile + "|"+strOut+"|||\n");
		}

		// Look for start of non-sentence of utterance 
		beginIndex = strIn.indexOf(KNonSentenceStart_u);
		if (beginIndex != -1) {
			endIndex = strIn.indexOf(KNonSentenceEnd_u);
			strOut = strIn.substring(beginIndex + KNonSentenceStart_u.length(), endIndex );
			return(strFile + "|"+strOut+"|||\n");
		}

		strOut = "";
		strWork = "";

		// Look for start of sentence
		try {
			while(strIn != null && strIn.length() > 0) {

				beginIndex = strIn.indexOf(KSentenceStart_s);

				if (beginIndex == -1) {
					beginIndex = strIn.indexOf(KSentenceStart_u);
					bSentence = false;
				} else
					bSentence = true;										// This is truly a sentence

				endIndex = strIn.indexOf("\">");

				// Somewhat unneeded because both sentence and utterance tag start are the same length
				if (bSentence)
					strPS = strIn.substring(beginIndex + KSentenceStart_s.length() + 1, endIndex);
				else
					strPS = strIn.substring(beginIndex + KSentenceStart_u.length() + 1, endIndex);

				beginIndex = endIndex + 2;								// End of xml tag is start of sentence

				if (bSentence)
					endIndex = strIn.indexOf(KNonSentenceEnd_s);
				else
					endIndex = strIn.indexOf(KNonSentenceEnd_u);

				strWork = strIn.substring(beginIndex, endIndex);

				if (bSentence)
					strIn = strIn.substring(endIndex + KNonSentenceEnd_s.length());
				else
					strIn = strIn.substring(endIndex + KNonSentenceEnd_u.length());
				strOut = strOut +  strFile + "||"+ strPS + "|" + strWork + "|\n";
			}
		} catch (Exception e) {
			strOut = null;
		}

		if (bVerbose)
			System.out.println(strOut);

		return (strOut);
	}

}
