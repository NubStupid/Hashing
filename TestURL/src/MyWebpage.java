import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class MyWebpage {
    public static void main(String[] args) {
        try {
            // Create a URL object for the webpage you want to retrieve
            URL url = new URL("https://www.msn.com/id-id");
            // Open a connection to the URL
            URLConnection connection = url.openConnection();
            // Set the user agent to avoid getting blocked by the website
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3");
            // Create a BufferedReader to read the content of the webpage
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            // Create a StringBuilder to store the content of the webpage
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            // Close the BufferedReader
            in.close();
            // Print the content of the webpage
//            System.out.println(content.toString());
            
            String temp = content.toString();
            
            int startIdx = content.indexOf("<a href=");
            int endIdx = content.indexOf(">",content.indexOf("<a href=\""))+1;
            System.out.println("Start idx = "+startIdx+"    End idx = "+endIdx);
            String holder = content.substring(content.indexOf("<a href="),content.indexOf("/a>",content.indexOf("<a href=\""))+3);
            System.out.println(holder);
            System.out.println(holder.contains(".com"));
            
            temp = content.substring(endIdx);
            startIdx = temp.indexOf("<a href=");
            endIdx = temp.indexOf(">",temp.indexOf("<a href=\""))+1;
            System.out.println("Start idx = "+startIdx+"    End idx = "+endIdx);
            holder = temp.substring(temp.indexOf("<a href="),temp.indexOf("/a>",temp.indexOf("<a href=\""))+3);
            System.out.println(holder);
            System.out.println(holder.contains(".com"));
            
            temp = temp.substring(endIdx);
            startIdx = temp.indexOf("<a href=");
            endIdx = temp.indexOf(">",temp.indexOf("<a href=\""))+1;
            System.out.println("Start idx = "+startIdx+"    End idx = "+endIdx);
            holder = temp.substring(temp.indexOf("<a href="),temp.indexOf("/a>",temp.indexOf("<a href=\""))+3);
            System.out.println(holder);
            System.out.println(holder.contains(".com"));
            
            
            temp = temp.substring(endIdx);
            startIdx = temp.indexOf("<a href=");
            endIdx = temp.indexOf(">",temp.indexOf("<a href=\""))+1;
            System.out.println("Start idx = "+startIdx+"    End idx = "+endIdx);
            holder = temp.substring(temp.indexOf("<a href="),temp.indexOf("/a>",temp.indexOf("<a href=\""))+3);
            System.out.println(holder);
            System.out.println(holder.contains(".com"));
            
            
            temp = temp.substring(endIdx);
            startIdx = temp.indexOf("<a href=");
            endIdx = temp.indexOf(">",temp.indexOf("<a href=\""))+1;
            System.out.println("Start idx = "+startIdx+"    End idx = "+endIdx);
            holder = temp.substring(temp.indexOf("<a href="),temp.indexOf("/a>",temp.indexOf("<a href=\""))+3);
            System.out.println(holder);
            System.out.println(holder.contains(".com"));
            
            
            
            temp = temp.substring(endIdx);
            startIdx = temp.indexOf("<a href=");
            endIdx = temp.indexOf(">",temp.indexOf("<a href=\""))+1;
            System.out.println("Start idx = "+startIdx+"    End idx = "+endIdx);
            holder = temp.substring(temp.indexOf("<a href="),temp.indexOf("/a>",temp.indexOf("<a href=\""))+3);
            System.out.println(holder);
            System.out.println(holder.contains(".com"));
            
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}