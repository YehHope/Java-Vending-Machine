/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vending_machine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author YehWolf
 */
public abstract class ReportGenerator {
    protected DefaultTableModel model;
    
    protected void initializeModel(String[] columnNames) {
        model = new DefaultTableModel(columnNames, 0);
    }
    
    public abstract JScrollPane generateReport(String filename);
}

// Subclass of ReportGenerator for generating daily sales reports
class DailyReportGenerator extends ReportGenerator {
    @Override
    public JScrollPane generateReport(String filename) {
        // Create a DefaultTableModel with column headers and initial row count of 0
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Date", "Name", "Quantity", "Price", "Total Price"}, 0);

        double dailyTotalPrice = 0.0; // To calculate the daily total price

        // Format the date to "dd/MM/yyyy" format
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String targetDateStr = dateFormat.format(new Date()); // Get current date in the desired format

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            // Read each line from the file until there are no more lines
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) { // Check if there are enough parts for a record
                    String dateStr = parts[0];
                    String drinkName = parts[1];
                    int quantity = Integer.parseInt(parts[2]);
                    double price = Double.parseDouble(parts[3]);

                    // Format price and calculate total price
                    String formatPrice = "RM" + String.format("%.2f", price);
                    double totalPrice = quantity * price;
                    String formatTotalPrice = "RM" + String.format("%.2f", totalPrice);

                    // Check if the record's date matches the target date
                    if (dateStr.equals(targetDateStr)) {
                        dailyTotalPrice += totalPrice; // Update the daily total price
                        // Add a row to the table model with formatted data
                        model.addRow(new Object[]{dateStr, drinkName, quantity, formatPrice, formatTotalPrice});
                    }
                }
            }

            // Add a row for the daily total price at the end of the table
            model.addRow(new Object[]{"", "", "", "Daily Total:", "RM" + String.format("%.2f", dailyTotalPrice)});

        } catch (IOException e) { // Handle any IOException that might occur
            e.printStackTrace(); // Print the exception trace for debugging
        }

        // Create a JTable using the model and put it in a JScrollPane
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        return scrollPane; // Return the scroll pane containing the generated report table
    }
}

// Subclass of ReportGenerator for generating monthly sales reports
class MonthlyReportGenerator extends ReportGenerator {
    @Override
    public JScrollPane generateReport(String filename) {
        // Create a DefaultTableModel with column headers and initial row count of 0
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Date", "Name", "Quantity", "Price", "Total Price"}, 0);

        java.util.Date currentDate = new java.util.Date(); // Get the current date
        java.util.Calendar cal = java.util.Calendar.getInstance(); // Get a calendar instance
        cal.setTime(currentDate); // Set the calendar's time to the current date

        int targetMonth = cal.get(java.util.Calendar.MONTH) + 1; // Get the current month (1-12)
        int targetYear = cal.get(java.util.Calendar.YEAR); // Get the current year

        double monthlyTotal = 0.0; // To calculate the monthly total sales

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            // Read each line from the file until there are no more lines
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) { // Check if there are enough parts for a record
                    String dateStr = parts[0];
                    String drinkName = parts[1];
                    int quantity = Integer.parseInt(parts[2]);
                    double price = Double.parseDouble(parts[3]);
                    String formatPrice = "RM" + String.format("%.2f", price);

                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    Date date = dateFormat.parse(dateStr); // Parse the date string into a Date object
                    int month = date.getMonth() + 1; // Get the month from the Date object (0-11)
                    int year = date.getYear() + 1900; // Get the year from the Date object

                    // Check if the record's month and year match the target month and year
                    if (month == targetMonth && year == targetYear) {
                        double totalPrice = quantity * price;
                        monthlyTotal += totalPrice; // Update the monthly total sales
                        String formatTotalPrice = "RM" + String.format("%.2f", totalPrice);
                        // Add a row to the table model with formatted data
                        model.addRow(new Object[]{dateStr, drinkName, quantity, formatPrice, formatTotalPrice});
                    }
                }
            }

            // Add a row for the monthly total sales at the end of the table
            String[] totalRow = new String[]{"", "", "", "Monthly Total:", "RM" + String.format("%.2f", monthlyTotal)};
            model.addRow(totalRow);

        } catch (IOException | java.text.ParseException e) { // Handle IOException and ParseException
            e.printStackTrace(); // Print the exception trace for debugging
        }

        // Create a JTable using the model and put it in a JScrollPane
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        return scrollPane; // Return the scroll pane containing the generated report table
    }
}

class BestSellersReportGenerator extends ReportGenerator {
    @Override
    public JScrollPane generateReport(String filename) {
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Name", "Total Sales"}, 0);

        Map<String, Double> drinkTotals = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            // Read each line from the file until there are no more lines
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String drinkName = parts[1];
                    int quantity = Integer.parseInt(parts[2]);
                    double price = Double.parseDouble(parts[3]);

                    double totalPrice = quantity * price;
                    // Update the total sales for each drink
                    drinkTotals.put(drinkName, drinkTotals.getOrDefault(drinkName, 0.0) + totalPrice);
                }
            }

            // Sort the drinkTotals map by value (total sales) in descending order
            drinkTotals.entrySet().stream()
                    .sorted((e1, e2) -> Double.compare(e2.getValue(), e1.getValue()))
                    .forEach(entry -> {
                        String drinkName = entry.getKey();
                        double totalSales = entry.getValue();
                        String formatTotalSales = "RM" + String.format("%.2f", totalSales);
                        // Add a row to the table model with the best-selling drink's name and total sales
                        model.addRow(new Object[]{drinkName, formatTotalSales});
                    });

        } catch (IOException e) {
            e.printStackTrace(); // Print the exception trace for debugging
        }

        // Create a JTable using the model and put it in a JScrollPane
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        return scrollPane; // Return the scroll pane containing the generated report table
    }
}

class OverallReportGenerator extends ReportGenerator {
    @Override
    public JScrollPane generateReport(String filename) {
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Date", "Name", "Quantity", "Price", "Total Price"}, 0);
        double grandTotalPrice = 0.0; // To calculate the grand total price

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            // Read each line from the file until there are no more lines
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) { // Check if there are enough parts for a record
                    String dateStr = parts[0];
                    String drinkName = parts[1];
                    int quantity = Integer.parseInt(parts[2]);
                    double price = Double.parseDouble(parts[3]);
                    String formatPrice = "RM" + String.format("%.2f", price);
                    double totalPrice = quantity * price;
                    String formatTotalPrice = "RM" + String.format("%.2f", totalPrice);

                    grandTotalPrice += totalPrice; // Update the grand total price

                    // Add a row to the table model with formatted data
                    model.addRow(new Object[]{dateStr, drinkName, quantity, formatPrice, formatTotalPrice});
                }
            }

            // Add a row for the grand total price at the end of the table
            model.addRow(new Object[]{"", "", "", "Grand Total:", "RM" + String.format("%.2f", grandTotalPrice)});

        } catch (IOException e) {
            e.printStackTrace(); // Print the exception trace for debugging
        }

        // Create a JTable using the model and put it in a JScrollPane
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        return scrollPane; // Return the scroll pane containing the generated report table
    }
}

// Factory class for creating different types of ReportGenerator instances
class ReportGeneratorFactory {
    // Create a ReportGenerator instance based on the provided report type
    public static ReportGenerator createReportGenerator(String reportType) {
        switch (reportType) {
            case "daily":
                return new DailyReportGenerator();
            case "monthly":
                return new MonthlyReportGenerator();
            case "overall":
                return new OverallReportGenerator();
            case "best_sellers":
                return new BestSellersReportGenerator();
            default:
                // If an invalid report type is provided, throw an exception
                throw new IllegalArgumentException("Invalid report type: " + reportType);
        }
    }
}
