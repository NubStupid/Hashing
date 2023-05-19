import node.Node;
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

    private static NodeArray<String> arrayTable = new NodeArray<>(300);
    private static int lastAccessed = 0;

    public static void main(String[] args) {
        String webContent = getWebContent("https://www.msn.com/id-id");
        //Remove the CSS tag
        webContent = removeTags(webContent, "<style[^>]*>[^<]*</style>");
        //Remove the JavaScript code
        webContent = removeTags(webContent, "<script[^>]*>[^<]*</script>");
        //Print the content of the webpage
        ArrayList<String> listOfLinks = getLinks(webContent);
        System.out.println();
        System.out.println();

        System.out.println("Link yang didapat ada : " + listOfLinks.size() + " link");
        System.out.println("Link yang didapat : ");
        for(int i = 0; i < listOfLinks.size(); i++) {
            String formedLink = "https://" + "www.msn.com" + listOfLinks.get(i);
            System.out.printf("%d. %s\n", i + 1, formedLink);
        }

        int counter = 1;
        String currentLink = "https://msn.com";
        for(String link : listOfLinks) {
            currentLink += link;
            System.out.printf("Parsing link yang ke %d, dengan url : https://www.msn.com%s\n", counter, link);
            System.out.println();
            String content = getWebContent(link);
            content = removeTags(content, "<style[^>]*>[^<]*</style>");
            content = removeTags(content, "<script[^>]*>[^<]*</script>");
            content = removeTags(content, "<a[^>]*>(.*?)</a>");
            content = replacePattern(content, "<em>|</em>", "");
            content = replacePattern(content, "<span>|</span>", "");
            content = replacePattern(content, "<strong>|</strong>", "");
            content = removeTags(content, "");

            ArrayList<String> paragraphs = extractParagraph(content);
            String[] arrOfWords;
            for (int i = 1; i < paragraphs.size(); i++) {
                arrOfWords = paragraphs.get(i).split(" ");
                for (String arrOfWord : arrOfWords) {
                    insertInto(arrOfWord, currentLink);
                }
            }
            counter++;
        }

        try {
            arrayTable.show("rekening");
        } catch(Exception e) {
            System.err.println("ERROR!");
            e.printStackTrace();
        }



        /*
        NodeArray<String> testArr = new NodeArray<>(10);
        String word = "(<d";
        String nama = "22d";
        int wordHash = arrayTable.getHashCode(word, 10);
        int namaHash = arrayTable.getHashCode(nama, 10);
        testArr.insertNode(wordHash, new Node<>(word, "halo"));
        testArr.insertNode(namaHash, new Node<>(nama, "halo"));

        System.out.println(testArr.getNodeFromKey("(<d", 10).getWord());
        testArr.showAll(testArr.getHashCode("(<d", 10));
         */

    }

    private static String removeTags(String content, String tagPattern) {
        Pattern pattern = Pattern.compile(tagPattern);
        Matcher matcher = pattern.matcher(content);
        return matcher.replaceAll("");
    }

    private static String getWebContent(String url) {
        URL newURL = null;
        try {
            newURL = new URL(url);
        } catch(MalformedURLException malformedURLException) {
            String protocol = "https://";
            String host = "www.msn.com";
            url = protocol + host + url;
            try {
                newURL = new URL(url);
            } catch(MalformedURLException malformedURLException1) {
                malformedURLException1.printStackTrace();
            }
        }

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

    private static String replacePattern(String content, String pattern, String replaceWith) {
        Pattern regexPattern = Pattern.compile(pattern);
        Matcher matcher = regexPattern.matcher(content);

        return matcher.replaceAll(replaceWith);
    }

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

    private static ArrayList<String> extractParagraph(String content) {
        ArrayList<String> paragraphs = new ArrayList<>();
        Pattern pattern = Pattern.compile("<p>(.*?)</p>", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            String paragraph = matcher.group(1);
            paragraphs.add(paragraph);
        }
        return paragraphs;
    }

    private static void insertInto(String word, String url) {
        int hashValue = arrayTable.getHashCode(word, 300);
        arrayTable.insertNode(hashValue, new Node<>(word, url));
    }
}
