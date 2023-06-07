package com.example.demo;

import java.sql.*;
import java.text.NumberFormat;

public class ListMovies {
    public static void main(String[] args) {
        // Create a NumberFormat instance for formatting currency
        NumberFormat cf = NumberFormat.getCurrencyInstance();

        // Insert new movie entries
        insertMovies();

        // Retrieve the movies from the database
        ResultSet movies = getMovies();
        try {
            // Iterate over the movie records
            while (movies.next()) {
                // Create a Movie object from the current movie record
                Movie m = getMovie(movies);

                // Construct a message with the movie details
                String msg = Integer.toString(m.year);
                msg += ": " + m.title;
                msg += " (" + cf.format(m.price) + ")";

                // Print the movie details
                System.out.println(msg);
            }
        } catch (SQLException e) {
            // Handle any SQL exception that occurs during processing
            System.out.println(e.getMessage());
        }
    }

    // Method to insert new movie entries into the database
    private static void insertMovies() {
        // Establish a database connection
        Connection con = getConnection();
        try {
            // Create a statement and execute insert queries to add new movies
            Statement s = con.createStatement();

            // Generate random movie, year, and price values
            String movie1 = generateRandomMovie();
            String movie2 = generateRandomMovie();
            int year1 = generateRandomYear();
            int year2 = generateRandomYear();
            double price1 = generateRandomPrice();
            double price2 = generateRandomPrice();

            // Insert the new movies into the database
            String insert1 = "INSERT INTO movie (title, year, price) VALUES ('" + movie1 + "', " + year1 + ", " + price1 + ")";
            String insert2 = "INSERT INTO movie (title, year, price) VALUES ('" + movie2 + "', " + year2 + ", " + price2 + ")";
            s.executeUpdate(insert1);
            s.executeUpdate(insert2);

            System.out.println("Two new movies inserted into the database.");
        } catch (SQLException e) {
            // Handle any SQL exception that occurs during processing
            System.out.println(e.getMessage());
        }
    }

    // Method to retrieve movies from the database and return a ResultSet
    private static ResultSet getMovies() {
        // Establish a database connection
        Connection con = getConnection();
        try {
            // Create a statement and execute a select query to retrieve movies
            Statement s = con.createStatement();
            String select = "SELECT title, year, price " +
                    "FROM movie ORDER BY year";
            ResultSet rows = s.executeQuery(select);
            return rows;
        } catch (SQLException e) {
            // Handle any SQL exception that occurs during processing
            System.out.println(e.getMessage());
        }
        return null;
    }

 // Method to establish a database connection and return a Connection object
    private static Connection getConnection() {
        Connection con = null;
        try {
            // Load the JDBC driver for MySQL Connector/J 8.x
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Create the database connection URL, username, and password
            String url = "jdbc:mysql://localhost/Movies";
            String user = "root";
            String pw = "GHJcD%32@N4m7HqK7";

            // Establish a connection to the database
            con = DriverManager.getConnection(url, user, pw);
        } catch (ClassNotFoundException e) {
            // Handle any exception related to the JDBC driver
            System.out.println(e); // Always show full error message
            System.exit(0);
        } catch (SQLException e) {
            // Handle any SQL exception that occurs during processing
            System.out.println(e.getMessage());
            System.exit(0);
        }
        return con;
    }


    // Method to create a Movie object from a ResultSet
    private static Movie getMovie(ResultSet movies) {
        try {
            // Extract the movie details from the ResultSet
            String title = movies.getString("title");
            int year = movies.getInt("year");
            double price = movies.getDouble("price");

            // Create a Movie object with the extracted details and return it
            return new Movie(title, year, price);
        } catch (SQLException e) {
            // Handle any SQL exception that occurs during processing
            System.out.println(e.getMessage());
        }
        return null;
    }

    // Inner class representing a Movie object
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

    // Helper method to generate a random movie title
    private static String generateRandomMovie() {
        String[] movies = {
                "The Matrix",
                "Inception",
                "Pulp Fiction",
                "The Shawshank Redemption",
                "Fight Club",
                // Add more movie titles as desired
        };

        // Generate a random index to select a movie from the array
        int randomIndex = (int) (Math.random() * movies.length);

        // Return the randomly selected movie
        return movies[randomIndex];
    }

    // Helper method to generate a random year between 2000 and 2022
    private static int generateRandomYear() {
        int minYear = 2000;
        int maxYear = 2022;

        // Generate a random year within the specified range
        return minYear + (int) (Math.random() * (maxYear - minYear + 1));
    }

    // Helper method to generate a random price between 10 and 100
    private static double generateRandomPrice() {
        double minPrice = 10.0;
        double maxPrice = 100.0;

        // Generate a random price within the specified range
        return minPrice + (Math.random() * (maxPrice - minPrice));
    }
}
