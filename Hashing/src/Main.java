import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private static String[] arr = new String[300];
    private static final int CSS = 1;
    private static final int JAVASCRIPT = 2;

    public static void main(String[] args) {
        String webContent = getWebContent("https://www.msn.com/id-id");
        //Remove the CSS tag
        webContent = removeTags(webContent, CSS);
        //Remove the JavaScript code
        webContent = removeTags(webContent, JAVASCRIPT);
        //Print the content of the webpage
        ArrayList<String> listOfLinks = getLinks(webContent);
        System.out.println();
        System.out.println();

        System.out.println("Link yang didapat ada : " + listOfLinks.size() + " link");
        System.out.println("Link yang didapat : ");
        for(int i = 0; i < listOfLinks.size(); i++) {
            System.out.println(i + ". " + listOfLinks.get(i));
        }

        int linkNumber = 15;
        System.out.printf("Parsing link yang ke %d, dengan url : %s\n", linkNumber, listOfLinks.get(linkNumber));
        System.out.println();
        String content = getWebContent(listOfLinks.get(linkNumber));
        content = removeTags(content, CSS);
        content = removeTags(content, JAVASCRIPT);

        ArrayList<String> paragraphs = extractParagraph(content);

        for(int i = 1; i < paragraphs.size(); i++) {
            System.out.println(paragraphs.get(i));
        }
    }

    private static String removeTags(String content, int tag) {
        String tagPattern;
        if(tag == 1) {
            tagPattern = "<style[^>]*>[^<]*</style>";
        } else {
            tagPattern = "<script[^>]*>[^<]*</script>";
        }
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

    private static ArrayList<String> getLinks(String webContent) {
        ArrayList<String> listOfLinks = new ArrayList<>();
        String holder = "";
        int index;
        while((webContent.length() > listOfLinks.size() && webContent.contains("<a href="))) {
            index = webContent.indexOf(">", webContent.indexOf("<a href=\"") + 1);

            if(webContent.contains("<a href=")) {
                holder = webContent.substring(webContent.indexOf("<a href="), webContent.indexOf("/a>", webContent.indexOf("<a href=\"")) + 3);
            }

            if(holder.contains(".com")&&holder.contains("msn")) {
                listOfLinks.add(holder.substring(holder.indexOf("<a href=")+9,holder.indexOf("\"",holder.indexOf("<a href=\"")+9)));
            }

            webContent = webContent.substring(index);
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
}
