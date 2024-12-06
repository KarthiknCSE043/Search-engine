package SearchEngine1;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class SearchEngineSystem extends Frame implements ActionListener {
    // UI Components
    Label loginLabel, usernameLabel, passwordLabel, actionLabel;
    TextField usernameField, passwordField, inputField;
    TextArea resultArea;
    Button loginButton, actionButton, logoutButton;
    Choice userTypeChoice;

    // Document storage
    Map<Integer, String> documents = new HashMap<>();
    int docId = 1;

    // User role tracking
    String currentUserType = "";

    // Constructor
    public SearchEngineSystem() {
        // Frame setup
        setTitle("Search Engine System");
        setSize(600, 500);
        setLayout(null);
        setResizable(false);

        // Login Section
        loginLabel = new Label("Login as (Admin/User): ");
        loginLabel.setBounds(50, 50, 150, 30);
        add(loginLabel);

        userTypeChoice = new Choice();
        userTypeChoice.add("Admin");
        userTypeChoice.add("User");
        userTypeChoice.setBounds(200, 50, 150, 30);
        add(userTypeChoice);

        usernameLabel = new Label("Username:");
        usernameLabel.setBounds(50, 100, 150, 30);
        add(usernameLabel);

        usernameField = new TextField(15);
        usernameField.setBounds(200, 100, 150, 30);
        add(usernameField);

        passwordLabel = new Label("Password:");
        passwordLabel.setBounds(50, 150, 150, 30);
        add(passwordLabel);

        passwordField = new TextField(15);
        passwordField.setEchoChar('*');
        passwordField.setBounds(200, 150, 150, 30);
        add(passwordField);

        loginButton = new Button("Login");
        loginButton.setBounds(200, 200, 100, 30);
        loginButton.addActionListener(this);
        add(loginButton);

        // Action Section (hidden initially)
        actionLabel = new Label();
        actionLabel.setBounds(50, 50, 500, 30);
        actionLabel.setVisible(false);
        add(actionLabel);

        inputField = new TextField(30);
        inputField.setBounds(50, 100, 400, 30);
        inputField.setVisible(false);
        add(inputField);

        actionButton = new Button("Submit");
        actionButton.setBounds(470, 100, 80, 30);
        actionButton.setVisible(false);
        actionButton.addActionListener(this);
        add(actionButton);

        resultArea = new TextArea(10, 50);
        resultArea.setBounds(50, 150, 500, 200);
        resultArea.setEditable(false);
        resultArea.setVisible(false);
        add(resultArea);

        logoutButton = new Button("Logout");
        logoutButton.setBounds(250, 370, 100, 30);
        logoutButton.setVisible(false);
        logoutButton.addActionListener(this);
        add(logoutButton);

        // Frame close operation
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });

        setVisible(true);
    }

    // Action handling
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            handleLogin();
        } else if (e.getSource() == actionButton) {
            if (currentUserType.equals("Admin")) {
                handleAddDocument();
            } else if (currentUserType.equals("User")) {
                handleSearch();
            }
        } else if (e.getSource() == logoutButton) {
            handleLogout();
        }
    }

    // Handle login
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        currentUserType = userTypeChoice.getSelectedItem();

        if (validateLogin(username, password)) {
            switchToActionScreen();
            resultArea.setText("Welcome, " + currentUserType + "!");
        } else {
            showMessageDialog("Invalid credentials! Please try again.");
        }
    }

    // Validate login credentials
    private boolean validateLogin(String username, String password) {
        if (currentUserType.equals("Admin") && username.equals("admin") && password.equals("admin123")) {
            return true;
        } else if (currentUserType.equals("User") && username.equals("user") && password.equals("user123")) {
            return true;
        }
        return false;
    }

    // Switch to Action Screen
    private void switchToActionScreen() {
        // Hide login components
        loginLabel.setVisible(false);
        userTypeChoice.setVisible(false);
        usernameLabel.setVisible(false);
        usernameField.setVisible(false);
        passwordLabel.setVisible(false);
        passwordField.setVisible(false);
        loginButton.setVisible(false);

        // Show action components
        actionLabel.setText(currentUserType.equals("Admin") ? "Add a Document:" : "Search Documents:");
        actionLabel.setVisible(true);
        inputField.setVisible(true);
        actionButton.setLabel(currentUserType.equals("Admin") ? "Add" : "Search");
        actionButton.setVisible(true);
        resultArea.setVisible(true);
        logoutButton.setVisible(true);
    }

    // Handle adding a document
    private void handleAddDocument() {
        String newDocument = inputField.getText().trim();
        if (!newDocument.isEmpty()) {
            documents.put(docId++, newDocument);
            inputField.setText("");
            resultArea.setText("Document added successfully!\n");
        } else {
            showMessageDialog("Document cannot be empty!");
        }
    }

    // Handle searching for documents
    private void handleSearch() {
        String query = inputField.getText().trim();
        if (!query.isEmpty()) {
            String results = searchDocuments(query);
            resultArea.setText(results);
        } else {
            showMessageDialog("Search query cannot be empty!");
        }
    }

    // Search documents for the given query
    private String searchDocuments(String query) {
        StringBuilder results = new StringBuilder();
        for (Map.Entry<Integer, String> entry : documents.entrySet()) {
            if (entry.getValue().toLowerCase().contains(query.toLowerCase())) {
                results.append("Doc ID ").append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
            }
        }
        return results.length() > 0 ? results.toString() : "No results found!";
    }

    // Handle logout
    private void handleLogout() {
        // Hide action components
        actionLabel.setVisible(false);
        inputField.setVisible(false);
        actionButton.setVisible(false);
        resultArea.setVisible(false);
        logoutButton.setVisible(false);

        // Show login components
        loginLabel.setVisible(true);
        userTypeChoice.setVisible(true);
        usernameLabel.setVisible(true);
        usernameField.setVisible(true);
        passwordLabel.setVisible(true);
        passwordField.setVisible(true);
        loginButton.setVisible(true);

        usernameField.setText("");
        passwordField.setText("");
    }

    // Show dialog message
    private void showMessageDialog(String message) {
        resultArea.setVisible(true);
        resultArea.setText(message);
    }

    // Main method
    public static void main(String[] args) {
        new SearchEngineSystem();
    }
}