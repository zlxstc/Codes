
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;



public class RankReceiver {
	final static String FILE_NAME = "C:\\Users\\zlxstc\\workspace\\awis\\9200-9300 in us";
	final static String OUTPUT_FILE_NAME = "C:\\Users\\zlxstc\\workspace\\awis\\output";
	final static Charset ENCODING = StandardCharsets.UTF_8;
	public RankReceiver(String accessKeyId, String secretAccessKey, String site) {
    }
	static List<String> readSmallTextFile(String aFileName) throws IOException {
		Path path = Paths.get(aFileName);
		return Files.readAllLines(path, ENCODING);
	}
	
	static void writeSmallTextFile(List<String> aLines, String aFileName) throws IOException {
	    Path path = Paths.get(aFileName);
	    Files.write(path, aLines, ENCODING);
	  }
	
	
	private static void log(Object aMsg){
	    System.out.println(String.valueOf(aMsg));
	  }
	public static void main (String[] args) throws IOException{
		List<String> lines = readSmallTextFile(FILE_NAME);
		
		BufferedReader br = new BufferedReader(new FileReader(FILE_NAME));
        log(br);
        String line;
        while((line = br.readLine()) != null) {
             // do something with line.
        }
	    
	}
	
}