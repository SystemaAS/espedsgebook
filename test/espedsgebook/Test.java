package espedsgebook;
import java.util.regex.*;
import no.systema.main.util.StringManager;
public class Test {

	public static void main(String[] args) {
		String file = "EDIFACT_ok_Omberegning_25350_889932111111111111111111111.xxx";
		
		System.out.println(Pattern.matches("\\w+[.]\\w{3,4}", file));
		Test tester = new Test();
		String fileNameRevised = tester.chopFileName(file, new StringManager());
		System.out.println(fileNameRevised);
	}
	
	private String chopFileName(String fileName, StringManager strMgr){
		int TOTAL_40_LENGTH = 40;
		String retval = fileName;
		if(strMgr.isNotNull(fileName)){
			if(fileName.length()>TOTAL_40_LENGTH){
				int index = fileName.indexOf(".");
				int suffixLen = fileName.length() - index;
				System.out.println("INDEX:" + index);
				System.out.println("SUFFIX-LEN:" + suffixLen);
				//Isolate file name without file type since we have to chop the file name excluding the file type.
				//The total length including file type = 40 
				String fileNameWithoutSuffix = fileName.substring(0, index);
				String fileTypeStringIncludingPoint = fileName.substring(index);
				System.out.println(fileNameWithoutSuffix);
				System.out.println(fileTypeStringIncludingPoint);
				int chopIndex = TOTAL_40_LENGTH - suffixLen;
				if(fileNameWithoutSuffix.length()>chopIndex){
					fileNameWithoutSuffix = fileNameWithoutSuffix.substring(0, chopIndex);
				}
			
				retval = fileNameWithoutSuffix + fileTypeStringIncludingPoint;
				
			}
		}
		return retval;
	}

}
