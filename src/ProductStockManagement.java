import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Scanner;

static String[][] stock = null;
        static String[] history = new String[100];
        static String[] insertionHistory = new String[100];
        static int historyCount = 0;
        static int insertionHistoryCount = 0;

        static boolean isValidInteger(String input) {
            if (input == null || input.trim().isEmpty()) {
                return false;
            }
            input = input.trim();
            for (int i = 0; i < input.length(); i++) {
                if (!Character.isDigit(input.charAt(i))) {
                    return false;
                }
            }
            return true;
        }

        static boolean hasAvailableSlot() {
            for (int i = 0; i < stock.length; i++) {
                for (String item : stock[i]) {
                    if (item.equals("EMPTY")) {
                        return true;
                    }
                }
            }
            return false;
        }

        static void setUpStock() {
            Scanner scanner = new Scanner(System.in);
            int rows = 0;

            while (true) {
                System.out.print("Insert number of Stock: ");
                String input = scanner.nextLine();
                if (!isValidInteger(input)) {
                    System.out.println("Invalid input. Please enter a valid number.");
                    continue;
                }
                rows = Integer.parseInt(input);
                if (rows > 0 && rows <= 100) {
                    break;
                } else {
                    System.out.println("Please enter a number between 1 and 100.");
                }
            }

            if (stock != null) {
                System.out.print("Stock already exists. Reinitialize and clear all data? (y/n): ");
                String response = scanner.nextLine().trim().toLowerCase();
                if (!response.equals("y")) {
                    System.out.println("Stock setup cancelled.");
                    return;
                }
            }

            stock = new String[rows][];
            for (int i = 0; i < rows; i++) {
                int columns = 0;
                while (true) {
                    System.out.print("Insert number of catalogue on stock [" + (i + 1) + "]: ");
                    String input = scanner.nextLine();
                    if (!isValidInteger(input)) {
                        System.out.println("Invalid input. Please enter a valid number.");
                        continue;
                    }
                    columns = Integer.parseInt(input);
                    if (columns > 0 && columns <= 10) {
                        break;
                    } else {
                        System.out.println("Please enter a number between 1 and 10.");
                    }
                }
                stock[i] = new String[columns];
                Arrays.fill(stock[i], "EMPTY");
            }

            // Reset history
            history = new String[100];
            insertionHistory = new String[100];
            historyCount = 0;
            insertionHistoryCount = 0;

            System.out.println("----- SET UP STOCK SUCCEEDED -----");
            for (int i = 0; i < stock.length; i++) {
                System.out.print("Stock [" + (i + 1) + "] => ");
                for (int j = 0; j < stock[i].length; j++) {
                    System.out.print("[ " + (j + 1) + " - " + stock[i][j] + " ] ");
                }
                System.out.println();
            }
        }

        static void availableStocks(boolean check, boolean showAll) {
            System.out.print("[*] Stock number available: ");
            boolean hasAvailable = false;
            for (int i = 0; i < stock.length; i++) {
                if (check) {
                    if (Arrays.asList(stock[i]).contains("EMPTY")) {
                        System.out.print((i + 1) + " | ");
                        hasAvailable = true;
                    }
                } else {
                    if (showAll) {
                        System.out.print((i + 1) + " | ");
                        hasAvailable = true;
                    } else {
                        boolean hasStock = false;
                        for (String item : stock[i]) {
                            if (!item.equals("EMPTY")) {
                                hasStock = true;
                                break;
                            }
                        }
                        if (hasStock) {
                            System.out.print((i + 1) + " | ");
                            hasAvailable = true;
                        }
                    }
                }
            }
            if (!hasAvailable) {
                System.out.print("None");
            }
            System.out.print("\b\b\n");
        }

        static void catalogue() {
            if (stock == null) {
                System.out.println("Stock is not set up.");
                return;
            }

            System.out.println("\n----- STOCK CATALOGUE -----");
            for (int i = 0; i < stock.length; i++) {
                System.out.print("Stock [" + (i + 1) + "] => ");
                for (int j = 0; j < stock[i].length; j++) {
                    System.out.print("[ " + (j + 1) + " - " + stock[i][j] + " ] ");
                }
                System.out.println();
            }
        }

        static void insertProduct() {
            if (stock == null) {
                System.out.println("Stock is not set up.");
                return;
            }

            if (!hasAvailableSlot()) {
                System.out.println("All stocks are full.");
                return;
            }

            Scanner scanner = new Scanner(System.in);
            System.out.println("----- Insertion Product to Stock -----");

            availableStocks(true, false);

            System.out.print("[+] Insert stock number: ");
            int slot = -1;
            while (true) {
                String input = scanner.nextLine();
                if (!isValidInteger(input)) {
                    System.out.println("Invalid input. Please enter a valid number.");
                    System.out.print("[+] Insert stock number: ");
                    continue;
                }
                slot = Integer.parseInt(input) - 1;
                if (slot < 0 || slot >= stock.length) {
                    System.out.println("Invalid stock slot.");
                    return;
                }
                if (!Arrays.asList(stock[slot]).contains("EMPTY")) {
                    System.out.println("This stock slot is full.");
                    return;
                }
                break;
            }

            System.out.print("Stock [" + (slot + 1) + "] => ");
            for (int j = 0; j < stock[slot].length; j++) {
                System.out.print("[ " + (j + 1) + " - " + stock[slot][j] + " ] ");
            }
            System.out.println();

            System.out.print("[+] Catalogue number available: ");
            int emptyCount = 0;
            for (int j = 0; j < stock[slot].length; j++) {
                if (stock[slot][j].equals("EMPTY")) {
                    System.out.print((j + 1) + " | ");
                    emptyCount++;
                }
            }
            System.out.print("\b\b\n");

            int fieldsToFill;
            if (emptyCount == 1) {
                fieldsToFill = 1;
            } else {
                while (true) {
                    System.out.print("[+] Insert number of catalogue to put product: ");
                    String input = scanner.nextLine();
                    if (!isValidInteger(input)) {
                        System.out.println("Invalid input. Please enter a valid number.");
                        continue;
                    }
                    fieldsToFill = Integer.parseInt(input);
                    if (fieldsToFill > 0 && fieldsToFill <= emptyCount) {
                        break;
                    } else {
                        System.out.println("Please enter a number between 1 and " + emptyCount + ".");
                    }
                }
            }

            System.out.print("[+] Insert Product name: ");
            String productName = scanner.nextLine();

            if (productName != null && !productName.trim().isEmpty()) {
                productName = productName.trim();
                productName = productName.substring(0, 1).toUpperCase() + productName.substring(1).toLowerCase();
            }

            String[] product = stock[slot].clone();
            int filled = 0;
            for (int i = 0; i < product.length && filled < fieldsToFill; i++) {
                if (product[i].equals("EMPTY")) {
                    filled++;
                    if (filled == fieldsToFill) {
                        product[i] = productName;
                    }
                }
            }

            stock[slot] = product;

            StringBuilder historyEntry = new StringBuilder("Inserted into Stock [" + (slot + 1) + "]: ");
            for (int i = 0; i < product.length; i++) {
                historyEntry.append("Field ").append(i + 1).append("=").append(product[i]);
                if (i < product.length - 1) {
                    historyEntry.append(", ");
                }
            }
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            insertionHistory[insertionHistoryCount++] = historyEntry.toString() + " at " + timestamp;

            System.out.println("----- PRODUCT HAS BEEN INSERTED -----");
        }

        static void updateProduct() {
            if (stock == null) {
                System.out.println("Stock is not set up.");
                return;
            }

            boolean hasProducts = false;
            for (int i = 0; i < stock.length; i++) {
                for (String item : stock[i]) {
                    if (!item.equals("EMPTY")) {
                        hasProducts = true;
                        break;
                    }
                }
                if (hasProducts) break;
            }
            if (!hasProducts) {
                System.out.println("No products available to update.");
                return;
            }

            Scanner scanner = new Scanner(System.in);
            System.out.println("----- Update Product In Stock -----");

            availableStocks(false, true);

            System.out.print("[+] Insert stock number to update product: ");
            int slot = -1;
            while (true) {
                String input = scanner.nextLine();
                if (!isValidInteger(input)) {
                    System.out.println("Invalid input. Please enter a valid number.");
                    System.out.print("[+] Insert stock number to update product: ");
                    continue;
                }
                slot = Integer.parseInt(input) - 1;
                if (slot < 0 || slot >= stock.length) {
                    System.out.println("Invalid stock slot.");
                    return;
                }
                boolean hasProduct = false;
                for (String item : stock[slot]) {
                    if (!item.equals("EMPTY")) {
                        hasProduct = true;
                        break;
                    }
                }
                if (!hasProduct) {
                    System.out.println("No products in this stock slot to update.");
                    return;
                }
                break;
            }

            System.out.print("Stock [" + (slot + 1) + "] => ");
            for (int j = 0; j < stock[slot].length; j++) {
                System.out.print("[ " + (j + 1) + " - " + stock[slot][j] + " ] ");
            }
            System.out.println();

            System.out.print("[+] Insert product name to update new one: ");
            String nameToUpdate = scanner.nextLine();
            boolean found = false;
            for (String item : stock[slot]) {
                if (item.equalsIgnoreCase(nameToUpdate)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("Product not found in this stock slot.");
                return;
            }

            System.out.print("[+] Insert new Product name to update: ");
            String newName = scanner.nextLine();

            if (newName != null && !newName.trim().isEmpty()) {
                newName = newName.trim();
                newName = newName.substring(0, 1).toUpperCase() + newName.substring(1).toLowerCase();
            }

            String[] oldProduct = stock[slot].clone();
            for (int i = 0; i < stock[slot].length; i++) {
                if (stock[slot][i].equalsIgnoreCase(nameToUpdate)) {
                    stock[slot][i] = newName;
                }
            }

            StringBuilder historyEntry = new StringBuilder("Updated in Stock [" + (slot + 1) + "]: ");
            for (int j = 0; j < oldProduct.length; j++) {
                historyEntry.append("Field ").append(j + 1).append("=").append(oldProduct[j]);
                if (j < oldProduct.length - 1) {
                    historyEntry.append(", ");
                }
            }
            historyEntry.append(" → New: ");
            for (int j = 0; j < stock[slot].length; j++) {
                historyEntry.append("Field ").append(j + 1).append("=").append(stock[slot][j]);
                if (j < stock[slot].length - 1) {
                    historyEntry.append(", ");
                }
            }
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            history[historyCount++] = historyEntry.toString() + " at " + timestamp;

            System.out.println("----- PRODUCT HAS BEEN UPDATED -----");
        }

        static void deleteProduct() {
            if (stock == null) {
                System.out.println("Stock is not set up.");
                return;
            }

            boolean hasProducts = false;
            for (int i = 0; i < stock.length; i++) {
                for (String item : stock[i]) {
                    if (!item.equals("EMPTY")) {
                        hasProducts = true;
                        break;
                    }
                }
                if (hasProducts) break;
            }
            if (!hasProducts) {
                System.out.println("No products available to delete.");
                return;
            }

            Scanner scanner = new Scanner(System.in);
            System.out.println("----- Delete Product In Stock -----");

            availableStocks(false, false);

            System.out.print("[+] Insert stock number to delete product: ");
            int slot = -1;
            while (true) {
                String input = scanner.nextLine();
                if (!isValidInteger(input)) {
                    System.out.println("Invalid input. Please enter a valid number.");
                    System.out.print("[+] Insert stock number to delete product: ");
                    continue;
                }
                slot = Integer.parseInt(input) - 1;
                if (slot < 0 || slot >= stock.length) {
                    System.out.println("Invalid stock slot.");
                    return;
                }
                boolean hasProduct = false;
                for (String item : stock[slot]) {
                    if (!item.equals("EMPTY")) {
                        hasProduct = true;
                        break;
                    }
                }
                if (!hasProduct) {
                    System.out.println("No products in this stock slot to delete.");
                    return;
                }
                break;
            }

            System.out.print("Stock [" + (slot + 1) + "] => ");
            for (int j = 0; j < stock[slot].length; j++) {
                System.out.print("[ " + (j + 1) + " - " + stock[slot][j] + " ] ");
            }
            System.out.println();

            System.out.print("[+] Insert product name to delete: ");
            String nameToDelete = scanner.nextLine();
            boolean found = false;
            for (String item : stock[slot]) {
                if (item.equalsIgnoreCase(nameToDelete)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("Product not found in this stock slot.");
                return;
            }

            String[] oldProduct = stock[slot].clone();
            for (int i = 0; i < stock[slot].length; i++) {
                if (stock[slot][i].equalsIgnoreCase(nameToDelete)) {
                    stock[slot][i] = "EMPTY";
                }
            }

            StringBuilder historyEntry = new StringBuilder("Deleted from Stock [" + (slot + 1) + "]: ");
            for (int j = 0; j < oldProduct.length; j++) {
                historyEntry.append("Field ").append(j + 1).append("=").append(oldProduct[j]);
                if (j < oldProduct.length - 1) {
                    historyEntry.append(", ");
                }
            }
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            history[historyCount++] = historyEntry.toString() + " at " + timestamp;

            System.out.println("----- PRODUCT HAS BEEN DELETED -----");
        }


        static void viewHistory() {
            if (insertionHistoryCount > 0) {
                System.out.println("Insertion History:");
                for (int i = 0; i < insertionHistoryCount; i++) {
                    System.out.println(insertionHistory[i]);
                }
            } else {
                System.out.println("No insertion history available.");
            }
        }


        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("\n=========================================");
                System.out.println("          PRODUCT STOCK MANAGER");
                System.out.println("=========================================");
                System.out.println(" 1.   Set Up Stock");
                System.out.println(" 2.   View Stock");
                System.out.println(" 3.   Insert Product to Stock");
                System.out.println(" 4.   Update Product to Stock");
                System.out.println(" 5.   Delete Product from Stock");
                System.out.println(" 6.   View insertion History");
                System.out.println(" 7.   Exit");
                System.out.println("=========================================");
                System.out.print("Choose option (1-7): ");

                try {
                    int option = Integer.parseInt(scanner.nextLine());

                    switch (option) {
                        case 1:
                            setUpStock();
                            break;
                        case 2:
                            catalogue();
                            break;
                        case 3:
                            insertProduct();
                            break;
                        case 4:
                            updateProduct();
                            break;
                        case 5:
                            deleteProduct();
                            break;
                        case 6:
                            viewHistory();
                            break;
                        case 7:
                            System.out.println("Exiting...");
                            scanner.close();
                            return;
                        default:
                            System.out.println("Invalid option. Please choose a number between 1 and 7.");
                    }
                } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 7.");
            }
        }
}
