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
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.SignatureException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.Writer;
/**
 * Makes a request to the Alexa Web Information Service UrlInfo action.
 */
public class UrlInfo {

    private static final String ACTION_NAME = "UrlInfo";
    private static final String RESPONSE_GROUP_NAME = "RankByCountry";//"Rank,ContactInfo,LinksInCount";
    private static final String SERVICE_HOST = "awis.amazonaws.com";
    private static final String AWS_BASE_URL = "http://" + SERVICE_HOST + "/?";
    private static final String HASH_ALGORITHM = "HmacSHA256";

    private static final String DATEFORMAT_AWS = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    private String accessKeyId;
    private String secretAccessKey;
    private String site;
    static Writer writer = null;
    final static String FILE_NAME = "C:\\Users\\zlxstc\\workspace\\awis\\Top90000_4.txt";
    final static String OUTPUT_NAME = "USrank4.csv";

    public UrlInfo(String accessKeyId, String secretAccessKey, String site) {
        this.accessKeyId = accessKeyId;
        this.secretAccessKey = secretAccessKey;
        this.site = site;
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
        queryParams.put("Action", ACTION_NAME);
        queryParams.put("ResponseGroup", RESPONSE_GROUP_NAME);
        queryParams.put("AWSAccessKeyId", accessKeyId);
        queryParams.put("Timestamp", timestamp);
        queryParams.put("Url", site);
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
    public static String IsAdultContent (String xmlResponse){
    	String[] section= xmlResponse.split("<aws:AdultContent>");
    	String[] section2= xmlResponse.split("</aws:AdultContent>");
    	while(section2.length > 1)		
			{
    		String[] yesorno= section[1].split("</aws:AdultContent>");
    		String result = yesorno[0];
    		return result;
			}
		return "no";
    	}		
    public static String USrank (String xmlResponse){
    	String[] section= xmlResponse.split("<aws:Country Code=\"US\">");
    	while(section.length > 1)
    	{
    		String[] section2= section[1].split(">");
    		while(section2.length > 1)
    		{
    			String[] section3 = section2[1].split("<");
    			return section3[0];
    		}
    		
    	}
    			return "-1";
    	}

    /**
     * Makes a request to the Alexa Web Information Service UrlInfo action
     */
    public static void main(String[] args) throws Exception {

        if (args.length < 2) {
            System.err.println("Usage: UrlInfo ACCESS_KEY_ID " +
                               "SECRET_ACCESS_KEY site");
            System.exit(-1);
        }

        // Read command line parameters
        String site = null;

        String accessKey = args[0];
        String secretKey = args[1];
        BufferedReader br = new BufferedReader(new FileReader(FILE_NAME));
        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                  new FileOutputStream(OUTPUT_NAME), "utf-8"));
        // Number of loops for historical data
	        for(int i=0;i<89232;i++)
	        	{
	        	String siteurl = br.readLine();
		        if (siteurl != null){
		        	//flag=+ 1;
		        	site = siteurl;
		        	//System.out.println(sitee + "\n");
		        }
		        UrlInfo urlInfo = new UrlInfo(accessKey, secretKey, site);
		
		        String query = urlInfo.buildQuery();
		
		        String toSign = "GET\n" + SERVICE_HOST + "\n/\n" + query;
		
		        //System.out.println("String to sign:\n" + toSign + "\n");
		
		        String signature = urlInfo.generateSignature(toSign);
		
		        String uri = AWS_BASE_URL + query + "&Signature=" +
		                URLEncoder.encode(signature, "UTF-8");
		
		        //System.out.println("Making request to:\n");
		        //System.out.println(uri + "\n");
		
		        // Make the Request
		        //System.out.println(site );
		
		        String xmlResponse = makeRequest(uri);
		        //System.out.println(xmlResponse);
		        String usrank = USrank(xmlResponse);
		        writer.write(site + "," + usrank + "\n");
		        
		        }
	        }
	        catch (IOException ex){
		          // report
		        } finally {
		           try {writer.close();} catch (Exception ex) {}
	        //System.out.println(adultornot);	
	        // Print out the XML Response
	
	        //System.out.println("Response:\n");
	        //System.out.println(xmlResponse);
        }
    }
}
