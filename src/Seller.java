import java.util.ArrayList;
import java.util.Scanner;

public class Seller {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/asta";
        String username = "root";
        String password = "leopoldodinarnia";

        Repository repository = new Repository(
            url, 
            username, 
            password);

        int comando = -1;

        Scanner scanner = new Scanner(System.in);

        while (comando != 0){
            System.out.println("Quale operazione sul db vuoi fare ?");
            System.out.println("0: stop\n1: add item\n2: remove item\n3: add category\n4: remove category\n5: vedi Items\n6: vedi Category");
            comando = scanner.nextInt();
            switch (comando){
                case 1: addItem(repository);break;
                case 2: deleteItem(repository);break;
                case 3: addCategory(repository);break;
                case 4: deleteCategory(repository);break;
                case 5: showItems(repository); break;
                case 6: showCategory(repository); break;
            }
        }

        scanner.close();
    }

    private static void showCategory(Repository repository) {
       ArrayList<Category> categories =  repository.selectCategoriess();
       for (Category category : categories) {
            System.out.println(" id " + category.getId() + " name " + category.getName());
       }
    }

    private static void showItems(Repository repository) {
        ArrayList<Item> items  = repository.selectItems();
        for (Item item : items) {
            System.out.println("id " + item.getId() + " name " + item.getName() + " category " + item.getCategory() + " ipGroup " + item.getIpGroup());
        }
    }
    
    private static void deleteCategory(Repository repository) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("inserisci l'id della categoria da eliminare");
        int id = scanner.nextInt();

        boolean ris = repository.deleteCategoryById(id);

        if (ris){
            System.out.println("categoria eliminata correttamente");
        } else {
            System.out.println("errore");
        }

        scanner.close();

    }

    private static void addCategory(Repository repository) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Inserisci il nome della categoria");
        String name = scanner.nextLine();

        Category category = new Category(name);

        boolean ris = repository.addCategory(category);

        if(ris){
            System.out.println("CAtegoria inserita correttamente");
        } else {
            System.out.println("errore");
        }
        scanner.close();
    }

    private static void deleteItem(Repository repository) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("inserisci il nome dell'item");
        int id = scanner.nextInt();

        boolean ris = repository.deleteItemById(id);

        if (ris){
            System.out.println("item eliminato correttamente");
        } else {
            System.out.println("errore");
        }
        scanner.close();
    }

    private static void addItem(Repository repository) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Inserisci nome item");
        String name = scanner.nextLine();
        System.out.println("Inserisci la categoria");
        String category = scanner.nextLine();
        System.out.println("Inserisci l'ipGroup");
        String ipGroup = scanner.nextLine();

        Item item = new Item(name, category, ipGroup);

        repository.addItem(item);
        scanner.close();
    }
}
