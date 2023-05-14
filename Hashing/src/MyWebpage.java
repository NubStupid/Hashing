import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

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
            ArrayList<String> links = new ArrayList<>();
            String temp = content.toString();
            int endIdx;
            String holder = "";
            while ((temp.length() > holder.length()) && temp.contains("<a href=")) {
                endIdx = temp.indexOf(">", temp.indexOf("<a href=\"")) + 1;
                if (temp.contains("<a href=")) {
                    holder = temp.substring(temp.indexOf("<a href="), temp.indexOf("/a>", temp.indexOf("<a href=\"")) + 3);
                }
                if (holder.contains(".com") && holder.contains("msn")) {
                    links.add(holder.substring(holder.indexOf("<a href=") + 9, holder.indexOf("\"", holder.indexOf("<a href=\"") + 9)));
                }
                temp = temp.substring(endIdx);
            }
            System.out.println();
            System.out.println();
            for (String a : links) {
                System.out.println(a);
            }
        }catch(IOException e) {
            e.printStackTrace();
        }

//            System.out.println(links.size());
//            try {
//                url = new URL(links.get(0));
//                connection = url.openConnection();
//                in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//                // Create a StringBuilder to store the content of the webpage
//                content = new StringBuilder();
//                while ((inputLine = in.readLine()) != null) {
//                    content.append(inputLine);
//                }
//                // Close the BufferedReader
//                in.close();
//                temp = content.toString();
//                holder = "";
//                System.out.println();
//                System.out.println();
//                System.out.println(content);
//                System.out.println();
//                System.out.println();
//                while((temp.length() > holder.length()) && temp.contains("<p>")) {
//                    endIdx = temp.indexOf("</p>",temp.indexOf("<p>"))+1;
//                    if(temp.contains("</p>")) {
//                        holder = temp.substring(temp.indexOf("<p>"),temp.indexOf("</p>",temp.indexOf("<p>"))+3);
//                        System.out.println(holder);
//                        System.out.println(separateParagraph(holder));
//                    }
//                    temp = temp.substring(endIdx);
//                }
//            }catch(Exception e){
//                System.out.println("Link segaban");
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//    public static String separateParagraph(String text) {
//        while(text.charAt(0) == '<') {
//            text = text.substring(text.indexOf(">") + 1);
//        }
//        text = text.substring(0, text.indexOf("<"));
//        if(text.charAt(0) == ' ') {
//            text = text.substring(1);
//        }
//        return text;
//    }
}}