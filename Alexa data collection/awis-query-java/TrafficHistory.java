import sun.misc.BASE64Encoder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.SignatureException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Makes a request to the Alexa Web Information Service UrlInfo action.
 */
public class TrafficHistory {

    private static final String ACTION_NAME = "TrafficHistory";
    private static final String RESPONSE_GROUP_NAME = "History";
    private static final String SERVICE_HOST = "awis.amazonaws.com";
    private static final String AWS_BASE_URL = "http://" + SERVICE_HOST + "/?";
    private static final String HASH_ALGORITHM = "HmacSHA256";
	static String od = null;
    private static final String Range = "5";
    private static final String DATEFORMAT_AWS = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    private String accessKeyId;
    private String secretAccessKey;
    private String site;
    static Writer writer = null;
    
    final static String FILE_NAME = "C:\\Users\\zlxstc\\workspace\\awis\\Top90000_2.txt";
	private static final String OUTPUT_NAME = "history_filtered6month2.txt";
	//private static final String String = null;
    public TrafficHistory(String accessKeyId, String secretAccessKey, String url) {
        this.accessKeyId = accessKeyId;
        this.secretAccessKey = secretAccessKey;
        this.site = url;
    }

    /**
     * Generates a timestamp for use with AWS request signing
     *
     * @param date current date
     * @return timestamp
     */
    protected static String getTimestampFromLocalTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(DATEFORMAT_AWS);
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        return format.format(date);
    }

    /**
     * Computes RFC 2104-compliant HMAC signature.
     *
     * @param data The data to be signed.
     * @return The base64-encoded RFC 2104-compliant HMAC signature.
     * @throws java.security.SignatureException
     *          when signature generation fails
     */
    protected String generateSignature(String data)
            throws java.security.SignatureException {
        String result;
        try {
            // get a hash key from the raw key bytes
            SecretKeySpec signingKey = new SecretKeySpec(
                    secretAccessKey.getBytes(), HASH_ALGORITHM);

            // get a hasher instance and initialize with the signing key
            Mac mac = Mac.getInstance(HASH_ALGORITHM);
            mac.init(signingKey);

            // compute the hmac on input data bytes
            byte[] rawHmac = mac.doFinal(data.getBytes());

            // base64-encode the hmac
            // result = Encoding.EncodeBase64(rawHmac);
            result = new BASE64Encoder().encode(rawHmac);

        } catch (Exception e) {
            throw new SignatureException("Failed to generate HMAC : "
                    + e.getMessage());
        }
        return result;
    }

    /**
     * Makes a request to the specified Url and return the results as a String
     *
     * @param requestUrl url to make request to
     * @return the XML document as a String
     * @throws IOException
     */
    public static String makeRequest(String requestUrl) throws IOException {
        URL url = new URL(requestUrl);
        URLConnection conn = url.openConnection();
        InputStream in = conn.getInputStream();

        // Read the response
        StringBuffer sb = new StringBuffer();
        int c;
        int lastChar = 0;
        while ((c = in.read()) != -1) {
            if (c == '<' && (lastChar == '>'))
                sb.append('\n');
            sb.append((char) c);
            lastChar = c;
        }
        in.close();
        return sb.toString();
    }


    /**
     * Builds the query string
     */
    protected String buildQuery()
            throws UnsupportedEncodingException {
        String timestamp = getTimestampFromLocalTime(Calendar.getInstance().getTime());

        Map<String, String> queryParams = new TreeMap<String, String>();
        //System.out.println(sitee + "\n");
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd"); 	   
        Calendar olddate = Calendar.getInstance();
        olddate = Calendar.getInstance();
 	    olddate.add(Calendar.MONTH, -6);
 	    olddate.add(Calendar.DAY_OF_MONTH, -5);
 	    od = dateFormat.format(olddate.getTime()); 	    
        queryParams.put("Action", ACTION_NAME);
        queryParams.put("ResponseGroup", RESPONSE_GROUP_NAME);
        queryParams.put("AWSAccessKeyId", accessKeyId);
        queryParams.put("Timestamp", timestamp);
        queryParams.put("Url", site);
        queryParams.put("Start", od);        
        queryParams.put("Range", Range);
        queryParams.put("SignatureVersion", "2");
        queryParams.put("SignatureMethod", HASH_ALGORITHM);

        String query = "";
        boolean first = true;
        for (String name : queryParams.keySet()) {
            if (first)
                first = false;
            else
                query += "&";

            query += name + "=" + URLEncoder.encode(queryParams.get(name), "UTF-8");
        }

        return query;
    }

    /**
     * Get the rank data from xmlResponse, if not available, return NoRecord
     */
    public static String RankReceiver (String xmlResponse){
    	String norecord= "NoRecord" ;
    	while(xmlResponse.split("<aws:Rank>").length > 1)
    		{String[] section= xmlResponse.split("<aws:Rank>");
    	// Continue when data is available;
    		while(section.length > Converter(Range))
    			{
    				String[] rank1= section[1].split("</aws:Rank>");
    				String[] rank2= section[2].split("</aws:Rank>");
    				String[] rank3= section[3].split("</aws:Rank>");
    				String[] rank4= section[4].split("</aws:Rank>");
    				String[] rank5= section[5].split("</aws:Rank>");    		
    				//System.out.println(Converter(rank1[0]) + "\n"+rank2[0] + "\n"+rank3[0] + "\n"+rank4[0] + "\n"+rank5[0] + "\n");
    				int mean = (Converter(rank1[0])+Converter(rank2[0])+Converter(rank3[0])+Converter(rank4[0])+Converter(rank5[0]))/Converter(Range);
    				return String.valueOf(mean);
    			}
    	//System.out.println("noright:" + noright[0] + "\n");
    		return norecord;
    		}
		return norecord;
		
	}
    /**
     * Convert string to integer
     */
    public static int Converter(String rank){
    	int theValue = Integer.parseInt( rank.trim(), 10 );
        return theValue;    	
    }
    
    public static void main(String[] args) throws Exception {

        if (args.length < 2) {
            System.err.println("Usage: UrlInfo ACCESS_KEY_ID " +
                               "SECRET_ACCESS_KEY ");
            System.exit(-1);
        }
        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                  new FileOutputStream(OUTPUT_NAME), "utf-8"));
        // Read command line parameters
	        String site = null;
	        String accessKey = args[0];
	        String secretKey = args[1];
	        //String site = null;
	        BufferedReader br = new BufferedReader(new FileReader(FILE_NAME));
	        // Number of loops for historical data
	        for(int i=0;i<89300;i++){
	        	String siteurl = br.readLine();
		        if (siteurl != null){
		        	//flag=+ 1;
		        	site = siteurl;
		        	//System.out.println(sitee + "\n");
		        }
		        
		        TrafficHistory urlInfo = new TrafficHistory(accessKey, secretKey, site);
		
		        String query = urlInfo.buildQuery();
		
		        String toSign = "GET\n" + SERVICE_HOST + "\n/\n" + query;
		
		        //System.out.println("String to sign:\n" + toSign + "\n");
		
		        String signature = urlInfo.generateSignature(toSign);
		
		        String uri = AWS_BASE_URL + query + "&Signature=" +
		                URLEncoder.encode(signature, "UTF-8");
		
		        //System.out.println("Making request to:\n");
		        //System.out.println(uri + "\n");
		
		        // Make the Request
		        
		        String xmlResponse = makeRequest(uri);
		        //System.out.println(xmlResponse);
		        // Print out the XML Response
		        String oldrank = RankReceiver(xmlResponse);
		        writer.write(site + "," + oldrank+"\n");
		        //System.out.println( oldrank );
	        	}
        }
        catch (IOException ex){
	          // report
	        } finally {
	           try {writer.close();} catch (Exception ex) {}
		    }
        //System.out.println("Response:\n");"The rank of website: "+ site +"\nin the date:" + od  +"\nis:\n" +
        //
    }

	
}
