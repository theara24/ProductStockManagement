import java.util.Scanner;

public class ProductStockManagementUI {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[][] stock = null;
        String[] history = new String[100];
        int historyCount = 0;
        int productCount = 0;

        while (true) {
            System.out.println("\n========================================");
            System.out.println("         🛒 PRODUCT STOCK MANAGER");
            System.out.println("========================================");
            System.out.println(" 1. 📦  Set Up Stock Catalogue");
            System.out.println(" 2. 👀  View Products in Stock");
            System.out.println(" 3. ➕  Insert Product");
            System.out.println(" 4. ✏️   Update Product by Name");
            System.out.println(" 5. ❌  Delete Product by Name");
            System.out.println(" 6. 🕓  View History");
            System.out.println(" 7. 🚪  Exit");
            System.out.println("========================================");
            System.out.print("Choose an option (1-7): ");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    System.out.println("\n📦 Setup Stock:");
                    if (stock == null) {
                        System.out.print("Enter number of rows: ");
                        int rows = scanner.nextInt();
                        System.out.print("Enter number of columns: ");
                        int columns = scanner.nextInt();
                        scanner.nextLine();
                        stock = new String[rows][columns];
                        System.out.println("✅ Stock setup complete.");
                    } else {
                        System.out.println("⚠️  Stock has already been set up.");
                    }
                    break;

                case 2:
                    System.out.println("\n📋 Products in Stock:");
                    if (stock != null && productCount > 0) {
                        System.out.printf("%-20s %-10s %-10s\n", "Product Name", "Price", "Qty");
                        System.out.println("--------------------------------------------------");
                        for (int i = 0; i < productCount; i++) {
                            System.out.printf("%-20s %-10s %-10s\n", stock[i][0], stock[i][1], stock[i][2]);
                        }
                    } else {
                        System.out.println("⚠️  No products found.");
                    }
                    break;

                case 3:
                    System.out.println("\n➕ Insert New Product:");
                    if (stock != null && productCount < stock.length) {
                        System.out.print("Enter product name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter price: ");
                        String price = scanner.nextLine();
                        System.out.print("Enter quantity: ");
                        String qty = scanner.nextLine();

                        stock[productCount][0] = name;
                        stock[productCount][1] = price;
                        stock[productCount][2] = qty;
                        productCount++;

                        history[historyCount++] = "Inserted: " + name + " | $" + price + " | Qty: " + qty;
                        System.out.println("✅ Product added successfully.");
                    } else {
                        System.out.println("⚠️  Stock is full or not set up yet.");
                    }
                    break;

                case 4:
                    System.out.println("\n✏️  Update Product:");
                    if (stock != null && productCount > 0) {
                        System.out.print("Enter product name to update: ");
                        String name = scanner.nextLine();
                        boolean found = false;

                        for (int i = 0; i < productCount; i++) {
                            if (stock[i][0].equalsIgnoreCase(name)) {
                                System.out.print("Enter new price: ");
                                stock[i][1] = scanner.nextLine();
                                System.out.print("Enter new quantity: ");
                                stock[i][2] = scanner.nextLine();

                                history[historyCount++] = "Updated: " + name + " | $" + stock[i][1] + " | Qty: " + stock[i][2];
                                System.out.println("✅ Product updated.");
                                found = true;
                                break;
                            }
                        }
                        if (!found) System.out.println("❌ Product not found.");
                    } else {
                        System.out.println("⚠️  No data to update.");
                    }
                    break;

                case 5:
                    System.out.println("\n❌ Delete Product:");
                    if (stock != null && productCount > 0) {
                        System.out.print("Enter product name to delete: ");
                        String name = scanner.nextLine();
                        boolean found = false;

                        for (int i = 0; i < productCount; i++) {
                            if (stock[i][0].equalsIgnoreCase(name)) {
                                for (int j = i; j < productCount - 1; j++) {
                                    stock[j] = stock[j + 1];
                                }
                                productCount--;
                                history[historyCount++] = "Deleted: " + name;
                                System.out.println("✅ Product deleted.");
                                found = true;
                                break;
                            }
                        }
                        if (!found) System.out.println("❌ Product not found.");
                    } else {
                        System.out.println("⚠️  No data to delete.");
                    }
                    break;

                case 6:
                    System.out.println("\n🕓 Action History:");
                    if (historyCount > 0) {
                        for (int i = 0; i < historyCount; i++) {
                            System.out.println("- " + history[i]);
                        }
                    } else {
                        System.out.println("⚠️  No actions yet.");
                    }
                    break;

                case 7:
                    System.out.println("👋 Exiting Product Manager... Goodbye!");
                    scanner.close();
                    return;

                default:
                    System.out.println("❗ Invalid choice. Please select 1–7.");
            }
        }
    }
}
