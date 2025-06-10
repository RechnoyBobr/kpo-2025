package hse.bank;

import hse.bank.domains.BankAccount;
import hse.bank.domains.Category;
import hse.bank.domains.Operation;
import hse.bank.enums.IoFormat;
import hse.bank.facades.BankFacade;
import java.util.List;
import java.util.Scanner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Main application class for the Banking System.
 * Provides a console interface for interacting with banking operations.
 */
@SpringBootApplication
public class BankingApplication {

    /**
     * Main entry point for the application.
     * Initializes the Spring context and starts the console UI.
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(BankingApplication.class, args);
        BankFacade bankFacade = context.getBean(BankFacade.class);

        initializeDefaultCategories(bankFacade);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            printMainMenu();
            int choice = parseUserChoice(scanner);

            switch (choice) {
                case 1 -> accountsMenu(scanner, bankFacade);
                case 2 -> categoriesMenu(scanner, bankFacade);
                case 3 -> operationsMenu(scanner, bankFacade);
                case 4 -> transferMenu(scanner, bankFacade);
                case 5 -> importExportMenu(scanner, bankFacade);
                case 6 -> listAllCategories(bankFacade);
                case 0 -> {
                    System.out.println("Выход из программы. До свидания!");
                    context.close();
                    return;
                }
                default -> System.out.println("Неверный выбор. Попробуйте еще раз.");
            }
        }
    }

    /**
     * Initializes default categories for the banking application.
     * Creates standard income and expense categories for common transactions.
     *
     * @param bankFacade The bank facade instance used to create categories
     */
    private static void initializeDefaultCategories(BankFacade bankFacade) {
        try {
            // Income categories
            bankFacade.createCategory("Salary", true);
            bankFacade.createCategory("Account Replenishment", true);
            bankFacade.createCategory("Transfer", true);
            bankFacade.createCategory("Gift", true);

            // Expense categories
            bankFacade.createCategory("Groceries", false);
            bankFacade.createCategory("Transportation", false);
            bankFacade.createCategory("Utilities", false);
            bankFacade.createCategory("Dining Out", false);
            bankFacade.createCategory("Entertainment", false);
            bankFacade.createCategory("Shopping", false);
            bankFacade.createCategory("Transfer", false);
            bankFacade.createCategory("Withdrawal", false);
        } catch (Exception e) {
            System.out.println("Error initializing default categories: " + e.getMessage());
        }
    }

    /**
     * Lists all available categories in the system.
     *
     * @param bankFacade The bank facade instance used to retrieve categories
     */
    private static void listAllCategories(BankFacade bankFacade) {
        List<Category> categories = bankFacade.getCategories();

        System.out.println("\n=== Список всех категорий ===");
        System.out.println("Доходные категории:");
        for (Category category : categories) {
            if (category.isPositive()) {
                System.out.println("ID: " + category.getId() + ", Название: " + category.getName());
            }
        }

        System.out.println("\nРасходные категории:");
        for (Category category : categories) {
            if (!category.isPositive()) {
                System.out.println("ID: " + category.getId() + ", Название: " + category.getName());
            }
        }
        System.out.println();
    }

    /**
     * Displays the main menu options to the user.
     */
    private static void printMainMenu() {
        System.out.println("\n=== Банковское приложение ===");
        System.out.println("1. Управление счетами");
        System.out.println("2. Управление категориями");
        System.out.println("3. Управление операциями");
        System.out.println("4. Перевод между счетами");
        System.out.println("5. Импорт/Экспорт данных");
        System.out.println("6. Показать все категории");
        System.out.println("0. Выход");
        System.out.print("Выберите опцию: ");
    }

    /**
     * Parses user input into an integer choice.
     *
     * @param scanner The scanner used to read user input
     * @return The user's choice as an integer
     */
    private static int parseUserChoice(Scanner scanner) {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * Displays the account management menu and handles user choices.
     *
     * @param scanner    The scanner used to read user input
     * @param bankFacade The bank facade instance used for account operations
     */
    private static void accountsMenu(Scanner scanner, BankFacade bankFacade) {
        while (true) {
            System.out.println("\n=== Управление счетами ===");
            System.out.println("1. Создать счет");
            System.out.println("2. Показать счет");
            System.out.println("3. Удалить счет");
            System.out.println("0. Вернуться в главное меню");
            System.out.print("Выберите опцию: ");

            int choice = parseUserChoice(scanner);

            switch (choice) {
                case 1 -> createAccount(scanner, bankFacade);
                case 2 -> showAccount(scanner, bankFacade);
                case 3 -> deleteAccount(scanner, bankFacade);
                case 0 -> {
                    return;
                }
                default -> System.out.println("Неверный выбор. Попробуйте еще раз.");
            }
        }
    }

    /**
     * Handles the account creation process.
     *
     * @param scanner    The scanner used to read user input
     * @param bankFacade The bank facade instance used to create accounts
     */
    private static void createAccount(Scanner scanner, BankFacade bankFacade) {
        System.out.print("Введите название счета: ");
        String name = scanner.nextLine();

        System.out.print("Введите начальный баланс: ");
        try {
            double balance = Double.parseDouble(scanner.nextLine());
            BankAccount account = bankFacade.createAccount(name, balance);
            System.out.println("Счет успешно создан с ID: " + account.getId());
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: Неверный формат числа");
        } catch (Exception e) {
            System.out.println("Ошибка при создании счета: " + e.getMessage());
        }
    }

    /**
     * Displays the details of a selected account.
     *
     * @param scanner    The scanner used to read user input
     * @param bankFacade The bank facade instance used to retrieve account information
     */
    private static void showAccount(Scanner scanner, BankFacade bankFacade) {
        System.out.print("Введите ID счета: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            BankAccount account = bankFacade.getAccount(id);
            if (account != null) {
                System.out.println("\nИнформация о счете:");
                System.out.println("ID: " + account.getId());
                System.out.println("Название: " + account.getName());
                System.out.println("Баланс: " + account.getBalance());
            } else {
                System.out.println("Счет с ID " + id + " не найден.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: Неверный формат ID");
        } catch (Exception e) {
            System.out.println("Ошибка при получении счета: " + e.getMessage());
        }
    }

    /**
     * Handles the account deletion process.
     *
     * @param scanner    The scanner used to read user input
     * @param bankFacade The bank facade instance used to delete accounts
     */
    private static void deleteAccount(Scanner scanner, BankFacade bankFacade) {
        System.out.print("Введите ID счета для удаления: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            bankFacade.deleteAccount(id);
            System.out.println("Счет успешно удален.");
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: Неверный формат ID");
        } catch (Exception e) {
            System.out.println("Ошибка при удалении счета: " + e.getMessage());
        }
    }

    /**
     * Displays the category management menu and handles user choices.
     *
     * @param scanner    The scanner used to read user input
     * @param bankFacade The bank facade instance used for category operations
     */
    private static void categoriesMenu(Scanner scanner, BankFacade bankFacade) {
        while (true) {
            System.out.println("\n=== Управление категориями ===");
            System.out.println("1. Создать категорию");
            System.out.println("2. Показать категорию");
            System.out.println("3. Удалить категорию");
            System.out.println("4. Показать все категории");
            System.out.println("0. Вернуться в главное меню");
            System.out.print("Выберите опцию: ");

            int choice = parseUserChoice(scanner);

            switch (choice) {
                case 1 -> createCategory(scanner, bankFacade);
                case 2 -> showCategory(scanner, bankFacade);
                case 3 -> deleteCategory(scanner, bankFacade);
                case 4 -> listAllCategories(bankFacade);
                case 0 -> {
                    return;
                }
                default -> System.out.println("Неверный выбор. Попробуйте еще раз.");
            }
        }
    }

    /**
     * Handles the category creation process.
     *
     * @param scanner    The scanner used to read user input
     * @param bankFacade The bank facade instance used to create categories
     */
    private static void createCategory(Scanner scanner, BankFacade bankFacade) {
        System.out.print("Введите название категории: ");
        String name = scanner.nextLine();

        System.out.print("Это доходная категория? (да/нет): ");
        String isPositiveStr = scanner.nextLine().toLowerCase();
        boolean isPositive = isPositiveStr.equals("да") || isPositiveStr.equals("yes") || isPositiveStr.equals("y");

        try {
            Category category = bankFacade.createCategory(name, isPositive);
            System.out.println("Категория успешно создана с ID: " + category.getId());
        } catch (Exception e) {
            System.out.println("Ошибка при создании категории: " + e.getMessage());
        }
    }

    /**
     * Displays the details of a selected category.
     *
     * @param scanner    The scanner used to read user input
     * @param bankFacade The bank facade instance used to retrieve category information
     */
    private static void showCategory(Scanner scanner, BankFacade bankFacade) {
        System.out.print("Введите ID категории: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            Category category = bankFacade.getCategory(id);
            if (category != null) {
                System.out.println("\nИнформация о категории:");
                System.out.println("ID: " + category.getId());
                System.out.println("Название: " + category.getName());
                System.out.println("Тип: " + (category.isPositive() ? "Доходная" : "Расходная"));
            } else {
                System.out.println("Категория с ID " + id + " не найдена.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: Неверный формат ID");
        } catch (Exception e) {
            System.out.println("Ошибка при получении категории: " + e.getMessage());
        }
    }

    /**
     * Handles the category deletion process.
     *
     * @param scanner    The scanner used to read user input
     * @param bankFacade The bank facade instance used to delete categories
     */
    private static void deleteCategory(Scanner scanner, BankFacade bankFacade) {
        System.out.print("Введите ID категории для удаления: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            bankFacade.deleteCategory(id);
            System.out.println("Категория успешно удалена.");
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: Неверный формат ID");
        } catch (Exception e) {
            System.out.println("Ошибка при удалении категории: " + e.getMessage());
        }
    }

    /**
     * Displays the operation management menu and handles user choices.
     *
     * @param scanner    The scanner used to read user input
     * @param bankFacade The bank facade instance used for operation management
     */
    private static void operationsMenu(Scanner scanner, BankFacade bankFacade) {
        while (true) {
            System.out.println("\n=== Управление операциями ===");
            System.out.println("1. Создать операцию");
            System.out.println("2. Показать операцию");
            System.out.println("3. Удалить операцию");
            System.out.println("0. Вернуться в главное меню");
            System.out.print("Выберите опцию: ");

            int choice = parseUserChoice(scanner);

            switch (choice) {
                case 1 -> createOperation(scanner, bankFacade);
                case 2 -> showOperation(scanner, bankFacade);
                case 3 -> deleteOperation(scanner, bankFacade);
                case 0 -> {
                    return;
                }
                default -> System.out.println("Неверный выбор. Попробуйте еще раз.");
            }
        }
    }

    /**
     * Handles the operation creation process.
     *
     * @param scanner    The scanner used to read user input
     * @param bankFacade The bank facade instance used to create operations
     */
    private static void createOperation(Scanner scanner, BankFacade bankFacade) {
        try {
            System.out.print("Введите ID счета: ");
            int accountId = Integer.parseInt(scanner.nextLine());

            System.out.print("Это доходная операция? (да/нет): ");
            String isPositiveStr = scanner.nextLine().toLowerCase();
            boolean isPositive = isPositiveStr.equals("да") || isPositiveStr.equals("yes") || isPositiveStr.equals("y");

            // Show suitable categories
            listAllCategories(bankFacade);

            System.out.print("Введите ID категории: ");
            int categoryId = Integer.parseInt(scanner.nextLine());

            System.out.print("Введите сумму: ");
            double amount = Double.parseDouble(scanner.nextLine());

            Operation operation = bankFacade.createOperation(amount, accountId, isPositive, categoryId);
            System.out.println("Операция успешно создана с ID: " + operation.getId());
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: Неверный формат числа");
        } catch (Exception e) {
            System.out.println("Ошибка при создании операции: " + e.getMessage());
        }
    }

    /**
     * Displays the details of a selected operation.
     *
     * @param scanner    The scanner used to read user input
     * @param bankFacade The bank facade instance used to retrieve operation information
     */
    private static void showOperation(Scanner scanner, BankFacade bankFacade) {
        System.out.print("Введите ID операции: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            Operation operation = bankFacade.getOperation(id);
            if (operation != null) {
                System.out.println("\nИнформация об операции:");
                System.out.println("ID: " + operation.getId());
                System.out.println(
                    "Счет: " + operation.getAccount().getName() + " (ID: " + operation.getAccount().getId() + ")");
                System.out.println(
                    "Категория: " + operation.getCategory().getName() + " (ID: " + operation.getCategory().getId() +
                        ")");
                System.out.println("Сумма: " + operation.getAmount());
                System.out.println("Тип: " + (operation.isType() ? "Доходная" : "Расходная"));

                // No linked operation field in current implementation
                // if (operation.getLinkedOperation().isPresent()) {
                //    System.out.println("Связанная операция: " + operation.getLinkedOperation().get().getId());
                // }
            } else {
                System.out.println("Операция с ID " + id + " не найдена.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: Неверный формат ID");
        } catch (Exception e) {
            System.out.println("Ошибка при получении операции: " + e.getMessage());
        }
    }

    /**
     * Handles the operation deletion process.
     *
     * @param scanner    The scanner used to read user input
     * @param bankFacade The bank facade instance used to delete operations
     */
    private static void deleteOperation(Scanner scanner, BankFacade bankFacade) {
        System.out.print("Введите ID операции для удаления: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            bankFacade.deleteOperation(id);
            System.out.println("Операция успешно удалена.");
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: Неверный формат ID");
        } catch (Exception e) {
            System.out.println("Ошибка при удалении операции: " + e.getMessage());
        }
    }

    /**
     * Displays the transfer menu and handles transfers between accounts.
     *
     * @param scanner    The scanner used to read user input
     * @param bankFacade The bank facade instance used for transfer operations
     */
    private static void transferMenu(Scanner scanner, BankFacade bankFacade) {
        try {
            System.out.print("Введите ID счета отправителя: ");
            int fromAccountId = Integer.parseInt(scanner.nextLine());

            System.out.print("Введите ID счета получателя: ");
            int toAccountId = Integer.parseInt(scanner.nextLine());

            // Print only transfer categories
            System.out.println("\n=== Категории переводов ===");
            List<Category> categories = bankFacade.getCategories();
            for (Category category : categories) {
                if (category.getName().equalsIgnoreCase("Transfer")) {
                    System.out.println("ID: " + category.getId() + ", Название: " + category.getName() +
                        ", Тип: " + (category.isPositive() ? "Доходная" : "Расходная"));
                }
            }

            System.out.print("\nВведите ID категории перевода: ");
            int categoryId = Integer.parseInt(scanner.nextLine());

            System.out.print("Введите сумму перевода: ");
            double amount = Double.parseDouble(scanner.nextLine());

            bankFacade.transferMoney(fromAccountId, toAccountId, categoryId, amount);
            System.out.println("Перевод успешно выполнен.");
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: Неверный формат числа");
        } catch (Exception e) {
            System.out.println("Ошибка при выполнении перевода: " + e.getMessage());
        }
    }

    /**
     * Displays the import/export menu and handles data import/export operations.
     *
     * @param scanner    The scanner used to read user input
     * @param bankFacade The bank facade instance used for import/export operations
     */
    private static void importExportMenu(Scanner scanner, BankFacade bankFacade) {
        while (true) {
            System.out.println("\n=== Импорт/Экспорт данных ===");
            System.out.println("1. Экспортировать данные");
            System.out.println("2. Импортировать данные");
            System.out.println("0. Вернуться в главное меню");
            System.out.print("Выберите опцию: ");

            int choice = parseUserChoice(scanner);

            switch (choice) {
                case 1 -> exportData(scanner, bankFacade);
                case 2 -> importData(scanner, bankFacade);
                case 0 -> {
                    return;
                }
                default -> System.out.println("Неверный выбор. Попробуйте еще раз.");
            }
        }
    }

    /**
     * Handles the data export process.
     *
     * @param scanner    The scanner used to read user input
     * @param bankFacade The bank facade instance used for data export
     */
    private static void exportData(Scanner scanner, BankFacade bankFacade) {
        System.out.print("Введите путь к файлу: ");
        String path = scanner.nextLine();

        System.out.println("Выберите формат:");
        System.out.println("1. CSV");
        System.out.println("2. JSON");
        System.out.println("3. YAML");
        System.out.print("Ваш выбор: ");

        try {
            int formatChoice = Integer.parseInt(scanner.nextLine());
            IoFormat format = switch (formatChoice) {
                case 1 -> IoFormat.CSV;
                case 2 -> IoFormat.JSON;
                case 3 -> IoFormat.YAML;
                default -> throw new IllegalArgumentException("Неверный формат");
            };

            bankFacade.exportData(path, format);
            System.out.println("Данные успешно экспортированы.");
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: Неверный формат выбора");
        } catch (Exception e) {
            System.out.println("Ошибка при экспорте данных: " + e.getMessage());
        }
    }

    /**
     * Handles the data import process.
     *
     * @param scanner    The scanner used to read user input
     * @param bankFacade The bank facade instance used for data import
     */
    private static void importData(Scanner scanner, BankFacade bankFacade) {
        System.out.print("Введите путь к файлу: ");
        String path = scanner.nextLine();

        System.out.println("Выберите формат:");
        System.out.println("1. CSV");
        System.out.println("2. JSON");
        System.out.println("3. YAML");
        System.out.print("Ваш выбор: ");

        try {
            int formatChoice = Integer.parseInt(scanner.nextLine());
            IoFormat format = switch (formatChoice) {
                case 1 -> IoFormat.CSV;
                case 2 -> IoFormat.JSON;
                case 3 -> IoFormat.YAML;
                default -> throw new IllegalArgumentException("Неверный формат");
            };

            bankFacade.importData(path, format);
            System.out.println("Данные успешно импортированы.");
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: Неверный формат выбора");
        } catch (Exception e) {
            System.out.println("Ошибка при импорте данных: " + e.getMessage());
        }
    }
}