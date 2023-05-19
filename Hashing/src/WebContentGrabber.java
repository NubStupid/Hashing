import node.NodeArray;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private static NodeArray<String> contentTable = new NodeArray<>(300);
    public static void main(String[] args) {
        //Access the content of the URL
        String webContent = getWebContent("https://www.msn.com/id-id");

        //Get all the links inside the webContent
        ArrayList<String> listOfLinks = getLinks(webContent);

        System.out.println("Link yang didapat ada : " + listOfLinks.size() + " link");
        System.out.println("Link yang didapat : ");

        //List all the acquired links.
        for(int i = 0; i < listOfLinks.size(); i++) {
            String formedLink = "https://" + "www.msn.com" + listOfLinks.get(i);
            System.out.printf("%d. %s\n", i + 1, formedLink);
        }

        String currentLink = "https://msn.com";
        String linkToAccess = listOfLinks.get(5);

        currentLink += linkToAccess;
        String content = Main.getWebContent(currentLink);

        //Extract only the paragraphs, one with <p> tag.
        ArrayList<String> paragraphs = Main.extractParagraph(content);

        Main.printContent(paragraphs);
        String[] extractedWords;

        for(String sentence : paragraphs) {
            extractedWords = sentence.split(" ");

            for(String word : extractedWords) {
                int hashIndex = Hasher.hash(word, 300);
                //System.out.println(hashIndex);
                contentTable.insert(hashIndex, word, linkToAccess);
            }
        }

        for(int i = 0; i < contentTable.getSize(); i++) {
            System.out.println(contentTable.getNode(i));
        }

        contentTable.printAll();
    }


    /*
        Extract only clean string from the webcontent.
     */
    public String cleanWebContent(String webContent) {
        //Remove the CSS tag
        webContent = webContent.replaceAll("<style[^>]*>[^<]*</style>", "");
        //Remove the JavaScript code
        webContent = webContent.replaceAll("\"<script[^>]*>[^<]*</script>\"", "");
        //Remove every HTML tag, except for the paragraphs tag.
        webContent = webContent.replaceAll("<(?!/?p(?=>|\\s.*>))[^>]*>", "");

        //Remove all double quotation.
        webContent = webContent.replaceAll("\"([^\"]*?)\"", "$1");

        //Remove all symbol
        webContent = webContent.replaceAll("[.,()]", "");

        //Remove all the invalid characters.
        webContent = webContent.replaceAll("[�]", "");

        //Remove all hyphen (-)
        webContent = webContent.replaceAll("\\s*-\\s*", "");

        return webContent;
    }
    /*
        Extract the webcontent (HTML, CSS, Javascript) from the given URL.
     */
    private static String getWebContent(String url) {
        URL newURL = null;

        //Create a new URL object.
        try {
            newURL = new URL(url);
        } catch(MalformedURLException malformedURLException) {
            malformedURLException.printStackTrace();
        }

        //Try to estabilish a connection.
        URLConnection urlConnection = null;
        try {
            if (newURL != null) {
                urlConnection = newURL.openConnection();
            }
        } catch(IOException ioException) {
            System.err.println("Unable to estabilish connection!");
            return null;
        }

        if (urlConnection != null) {
            urlConnection.setRequestProperty(
                    "User-Agent",
                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3"
            );
        }
        
        Scanner reader = null;
        try {
            if (urlConnection != null) {
                reader = new Scanner(urlConnection.getInputStream());
            }
        } catch(IOException ioException) {
            System.err.println("Unable to get input stream!");
            return null;
        }

        String inputLine;
        StringBuilder content = new StringBuilder();
        int counter = 1;
        if (reader != null) {
            while((inputLine = reader.nextLine()) != null && reader.hasNextLine()) {
                content.append(inputLine);
                content.append("\n");
            }
        }
        if (reader != null) {
            reader.close();
        }
        return content.toString();
    }

    /*
        Extract all the links from the given webContent string.
     */
    private static ArrayList<String> getLinks(String webContent) {
        ArrayList<String> listOfLinks = new ArrayList<>();
        String holder = "";
        int index;
        int counter = 1;
        while((webContent.length() > listOfLinks.size() && webContent.contains("<a href="))) {

            if(counter <= 10) {
                index = webContent.indexOf(">", webContent.indexOf("<a href=\"") + 1);

                if (webContent.contains("<a href=")) {
                    holder = webContent.substring(webContent.indexOf("<a href="), webContent.indexOf("/a>", webContent.indexOf("<a href=\"")) + 3);
                }

                if (!holder.contains(".com") && holder.contains("msn")) {
                    listOfLinks.add(holder.substring(holder.indexOf("<a href=") + 9, holder.indexOf("\"", holder.indexOf("<a href=\"") + 9)));
                    counter++;
                }

                webContent = webContent.substring(index);
            } else {
                break;
            }
        }
        return listOfLinks;
    }

    /*
        Extract the paragraphs from a given webcontent.
     */
    private static ArrayList<String> extractParagraph(String content) {
        ArrayList<String> paragraphs = new ArrayList<>();
        Pattern pattern = Pattern.compile("<p>(.*?)</p>");
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            String paragraph = matcher.group(1);
            paragraphs.add(paragraph);
        }
        return paragraphs;
    }

    /*
        Print out the paragraphs, filter the junk.
     */
    private static void printContent(ArrayList<String> content) {
        for(String s : content) {
            if(!s.contains("Ini terkadang dapat terjadi")) {
                System.out.println(s);
            }
        }
    }
}
