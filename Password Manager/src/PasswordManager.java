import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class PasswordManager {
    private Map<String, String> storedPasswords; // Armazena as senhas criptografadas

    public PasswordManager() {
        storedPasswords = new HashMap<>();
    }

    public static void main(String[] args) {
        PasswordManager passwordManager = new PasswordManager();
        passwordManager.run();
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("1. Armazenar senha");
            System.out.println("2. Recuperar senha");
            System.out.println("3. Sair");
            System.out.print("Escolha uma opção: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consumir a nova linha

            switch (choice) {
                case 1:
                    storePassword(scanner);
                    break;
                case 2:
                    retrievePassword(scanner);
                    break;
                case 3:
                    exit = true;
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }

        System.out.println("Obrigado por usar o gerenciador de senhas!");
    }

    private void storePassword(Scanner scanner) {
        System.out.print("Digite o nome da conta: ");
        String account = scanner.nextLine();
        System.out.print("Digite a senha: ");
        String password = scanner.nextLine();

        // Criptografar a senha antes de armazená-la
        String hashedPassword = hashPassword(password);

        // Armazenar a senha criptografada
        storedPasswords.put(account, hashedPassword);

        System.out.println("Senha armazenada com sucesso!");
    }

    private void retrievePassword(Scanner scanner) {
        System.out.print("Digite o nome da conta: ");
        String account = scanner.nextLine();

        // Recuperar a senha criptografada
        String hashedPassword = storedPasswords.get(account);

        if (hashedPassword != null) {
            System.out.println("Senha recuperada: " + hashedPassword);
        } else {
            System.out.println("Conta não encontrada.");
        }
    }

    private String hashPassword(String password) {
        try {
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[16];
            random.nextBytes(salt);

            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] hashedPassword = md.digest(password.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b : hashedPassword) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}

