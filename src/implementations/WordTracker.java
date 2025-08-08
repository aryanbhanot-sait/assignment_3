package implementations;

import utilities.Iterator;

import java.io.*;
import java.util.*;

/**
 * WordTracker program for Assignment 3
 * This program reads text files and tracks words
 */
public class WordTracker {
    // The tree to store words
    private BSTree<Word> tree;
    // File to save the repository
    private static final String REPO_FILE = "repository.ser";
    private static final long serialVersionUID = 1L;

    /**
     * Word class to store words and their occurrences
     */
    private static class Word implements Comparable<Word>, Serializable {
        private static final long serialVersionUID = 1L;
        // The actual word
        private String word;
        // Store file names and line numbers
        private HashMap<String, ArrayList<Integer>> fileLines;

        /**
         * Constructor
         */
        public Word(String w) {
            word = w.toLowerCase();
            fileLines = new HashMap<String, ArrayList<Integer>>();
        }

        /**
         * Add a new occurrence
         */
        public void addOccurrence(String file, int line) {
            // Check if file exists in map
            if (fileLines.containsKey(file) == false) {
                // Create new list for this file
                ArrayList<Integer> lines = new ArrayList<Integer>();
                fileLines.put(file, lines);
            }
            
            // Get the lines list
            ArrayList<Integer> lines = fileLines.get(file);
            
            // Check if line already exists
            boolean exists = false;
            for (int i = 0; i < lines.size(); i++) {
                if (lines.get(i) == line) {
                    exists = true;
                    break;
                }
            }
            
            // Add line if it doesn't exist
            if (exists == false) {
                lines.add(line);
            }
        }

        /**
         * Get total occurrences
         */
        public int getCount() {
            int count = 0;
            // Loop through all files
            for (String file : fileLines.keySet()) {
                // Add the number of lines for this file
                ArrayList<Integer> lines = fileLines.get(file);
                count = count + lines.size();
            }
            return count;
        }

        /**
         * Get the word text
         */
        public String getWord() {
            return word;
        }

        /**
         * Get the files and lines
         */
        public HashMap<String, ArrayList<Integer>> getFileLines() {
            return fileLines;
        }

        /**
         * Compare words alphabetically
         */
        @Override
        public int compareTo(Word other) {
            return this.word.compareTo(other.word);
        }

        /**
         * Format word with files only
         */
        public String formatWithFiles() {
            String result = word + ": ";
            
            // Get all file names
            ArrayList<String> files = new ArrayList<String>();
            for (String file : fileLines.keySet()) {
                files.add(file);
            }
            
            // Sort file names
            Collections.sort(files);
            
            // Add files to result
            for (int i = 0; i < files.size(); i++) {
                result = result + files.get(i);
                if (i < files.size() - 1) {
                    result = result + ", ";
                }
            }
            
            return result;
        }

        /**
         * Format word with files and lines
         */
        public String formatWithLines() {
            String result = word + ": ";
            
            // Get all file names
            ArrayList<String> files = new ArrayList<String>();
            for (String file : fileLines.keySet()) {
                files.add(file);
            }
            
            // Sort file names
            Collections.sort(files);
            
            // Add files and lines to result
            for (int i = 0; i < files.size(); i++) {
                String file = files.get(i);
                result = result + file + " (lines ";
                
                // Get lines for this file
                ArrayList<Integer> lines = fileLines.get(file);
                
                // Sort lines
                Collections.sort(lines);
                
                // Add lines to result
                for (int j = 0; j < lines.size(); j++) {
                    result = result + lines.get(j);
                    if (j < lines.size() - 1) {
                        result = result + ", ";
                    }
                }
                
                result = result + ")";
                if (i < files.size() - 1) {
                    result = result + ", ";
                }
            }
            
            return result;
        }

        /**
         * Format word with files, lines, and occurrences
         */
        public String formatWithOccurrences() {
            String result = word + " (" + getCount() + " occurrences): ";
            
            // Get all file names
            ArrayList<String> files = new ArrayList<String>();
            for (String file : fileLines.keySet()) {
                files.add(file);
            }
            
            // Sort file names
            Collections.sort(files);
            
            // Add files and lines to result
            for (int i = 0; i < files.size(); i++) {
                String file = files.get(i);
                result = result + file + " (lines ";
                
                // Get lines for this file
                ArrayList<Integer> lines = fileLines.get(file);
                
                // Sort lines
                Collections.sort(lines);
                
                // Add lines to result
                for (int j = 0; j < lines.size(); j++) {
                    result = result + lines.get(j);
                    if (j < lines.size() - 1) {
                        result = result + ", ";
                    }
                }
                
                result = result + ")";
                if (i < files.size() - 1) {
                    result = result + ", ";
                }
            }
            
            return result;
        }
    }

    /**
     * Constructor
     */
    public WordTracker() {
        // Create a new tree
        tree = new BSTree<Word>();
        
        // Load repository if it exists
        loadRepo();
    }

    /**
     * Load repository from file
     */
    @SuppressWarnings("unchecked")
    private void loadRepo() {
        // Check if file exists
        File f = new File(REPO_FILE);
        if (f.exists()) {
            // Try to load the file
            try {
                // Create input stream
                FileInputStream fis = new FileInputStream(f);
                ObjectInputStream ois = new ObjectInputStream(fis);
                
                // Read the tree
                tree = (BSTree<Word>) ois.readObject();
                
                // Close streams
                ois.close();
                fis.close();
                
                System.out.println("Repository loaded from " + REPO_FILE);
            } catch (Exception e) {
                // Print error
                System.out.println("Error loading repository: " + e.getMessage());
                
                // Create new tree
                tree = new BSTree<Word>();
            }
        }
    }

    /**
     * Save repository to file
     */
    private void saveRepo() {
        try {
            // Create output stream
            FileOutputStream fos = new FileOutputStream(REPO_FILE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            
            // Write the tree
            oos.writeObject(tree);
            
            // Close streams
            oos.close();
            fos.close();
            
            System.out.println("Repository saved to " + REPO_FILE);
        } catch (Exception e) {
            // Print error
            System.out.println("Error saving repository: " + e.getMessage());
        }
    }

    /**
     * Process a file
     */
    public void processFile(String filename) throws IOException {
        // Check if file exists
        File f = new File(filename);
        if (!f.exists()) {
            throw new IOException("File not found: " + filename);
        }

        // Open file
        FileReader fr = new FileReader(f);
        BufferedReader br = new BufferedReader(fr);
        
        // Read file line by line
        String line;
        int lineNum = 1;
        while ((line = br.readLine()) != null) {
            // Process this line
            String[] words = line.split("\\s+");
            
            // Process each word
            for (int i = 0; i < words.length; i++) {
                String w = words[i];
                
                // Remove punctuation
                w = w.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
                
                // Skip empty words
                if (w.length() == 0) {
                    continue;
                }
                
                // Create word object
                Word word = new Word(w);
                
                // Check if word exists in tree
                BSTreeNode<Word> node = tree.search(word);
                
                if (node == null) {
                    // Word not found, add it
                    word.addOccurrence(filename, lineNum);
                    tree.add(word);
                } else {
                    // Word found, update it
                    node.getElement().addOccurrence(filename, lineNum);
                }
            }
            
            // Increment line number
            lineNum++;
        }
        
        // Close file
        br.close();
        fr.close();
        
        // Save repository
        saveRepo();
    }

    /**
     * Generate file report
     */
    public String makeFileReport() {
        // Create report header
        String report = "Word Tracker Report (Files Only)\n";
        report = report + "==============================\n\n";
        
        // Get iterator
        Iterator<Word> it = tree.inorderIterator();
        
        // Process all words
        while (it.hasNext()) {
            Word w = it.next();
            report = report + w.formatWithFiles() + "\n";
        }
        
        return report;
    }

    /**
     * Generate line report
     */
    public String makeLineReport() {
        // Create report header
        String report = "Word Tracker Report (Files and Lines)\n";
        report = report + "===================================\n\n";
        
        // Get iterator
        Iterator<Word> it = tree.inorderIterator();
        
        // Process all words
        while (it.hasNext()) {
            Word w = it.next();
            report = report + w.formatWithLines() + "\n";
        }
        
        return report;
    }

    /**
     * Generate occurrence report
     */
    public String makeOccurrenceReport() {
        // Create report header
        String report = "Word Tracker Report (Files, Lines, and Occurrences)\n";
        report = report + "=================================================\n\n";
        
        // Get iterator
        Iterator<Word> it = tree.inorderIterator();
        
        // Process all words
        while (it.hasNext()) {
            Word w = it.next();
            report = report + w.formatWithOccurrences() + "\n";
        }
        
        return report;
    }

    /**
     * Write report to file
     */
    public void writeReport(String report, String filename) throws IOException {
        // Create file writer
        FileWriter fw = new FileWriter(filename);
        PrintWriter pw = new PrintWriter(fw);
        
        // Write report
        pw.print(report);
        
        // Close file
        pw.close();
        fw.close();
        
        System.out.println("Report written to " + filename);
    }

    /**
     * Print report to console
     */
    public void printReport(String report) {
        System.out.println(report);
    }

    /**
     * Main method
     */
    public static void main(String[] args) {
        // Check arguments
        if (args.length < 2) {
            showUsage();
            return;
        }

        // Get input file and report option
        String inFile = args[0];
        String option = args[1];
        String outFile = null;

        // Check for output file
        if (args.length > 2) {
            if (args[2].startsWith("-f")) {
                outFile = args[2].substring(2);
                outFile = outFile + ".txt";
                System.out.println("Output will be redirected to: " + outFile);
            }
        }

        try {
            // Create word tracker
            WordTracker tracker = new WordTracker();
            
            // Process input file
            tracker.processFile(inFile);

            // Generate report
            String report = null;
            
            // Check which report to generate
            if (option.equals("-pf")) {
                report = tracker.makeFileReport();
            } else if (option.equals("-pl")) {
                report = tracker.makeLineReport();
            } else if (option.equals("-po")) {
                report = tracker.makeOccurrenceReport();
            } else {
                // Invalid option
                System.out.println("Invalid report option: " + option);
                showUsage();
                return;
            }

            // Output report
            if (outFile != null) {
                // Write to file
                tracker.writeReport(report, outFile);
            } else {
                // Print to console
                tracker.printReport(report);
            }
        } catch (IOException e) {
            // Print error
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Show usage information
     */
    private static void showUsage() {
        System.out.println("Usage: java -jar WordTracker.jar <input.txt> -pf/-pl/-po [-f<output.txt>]");
        System.out.println("  <input.txt>     Path to the input text file");
        System.out.println("  -pf             Print words with files");
        System.out.println("  -pl             Print words with files and line numbers");
        System.out.println("  -po             Print words with files, line numbers, and occurrences");
        System.out.println("  -f<output.txt>  Optional: Redirect output to the specified file");
    }
}
