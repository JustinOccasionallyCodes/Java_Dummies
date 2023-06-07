import javax.xml.parsers.*;
import org.xml.sax.*;
import org.w3c.dom.*;
import java.text.*;

public class ListMoviesXML {
    private static NumberFormat cf = NumberFormat.getCurrencyInstance();

    public static void main(String[] args) {
        // Get the XML document
        Document doc = getDocument("movies.xml");

        // Get the root element of the document
        Element root = doc.getDocumentElement();

        // Get the first movie element
        Element movieElement = (Element) root.getFirstChild();
        Movie m;

        // Iterate through each movie element
        while (movieElement != null) {
            // Get the Movie object from the movie element
            m = getMovie(movieElement);

            // Construct a message with the movie details
            String msg = Integer.toString(m.year);
            msg += ": " + m.title;
            msg += " (" + cf.format(m.price) + ")";

            // Print the movie details
            System.out.println(msg);

            // Move to the next movie element
            movieElement = (Element) movieElement.getNextSibling();
        }
    }

    // Method to get the XML document from a file
    private static Document getDocument(String name) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setIgnoringComments(true);
            factory.setIgnoringElementContentWhitespace(true);
            factory.setValidating(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.parse(new InputSource(name));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    // Method to extract a Movie object from a movie element
    private static Movie getMovie(Element e) {
        // Get the year attribute
        String yearString = e.getAttribute("year");
        int year = Integer.parseInt(yearString);

        // Get the Title element
        Element tElement = (Element) e.getFirstChild();
        String title = getTextValue(tElement).trim();

        // Get the Price element
        Element pElement = (Element) tElement.getNextSibling();
        String pString = getTextValue(pElement).trim();
        double price = Double.parseDouble(pString);

        return new Movie(title, year, price);
    }

    // Method to extract the text value from a Node
    private static String getTextValue(Node n) {
        return n.getFirstChild().getNodeValue();
    }

    // Movie class to represent a movie
    private static class Movie {
        public String title;
        public int year;
        public double price;

        public Movie(String title, int year, double price) {
            this.title = title;
            this.year = year;
            this.price = price;
        }
    }
}
