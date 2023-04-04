import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Repository {
    private String url;
    private String username;
    private String password;

    public Repository (
        String url,
        String username,
        String password
    ){
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public ArrayList<String> selectCategories (){
        ArrayList<String> categories = new ArrayList<String>();

        try {
            Connection connection = DriverManager.getConnection(
                url, 
                username, 
                password);

            String query = "select distinct category from asta.objects";
            java.sql.Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);

            while(result.next()){
                categories.add(new String(result.getString(1)));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categories;
    }

    public ArrayList<Item> selectItemsByCategory (String category){
        ArrayList<Item> items = new ArrayList<Item>();

        try {
            Connection connection = DriverManager.getConnection(
                url, 
                username, 
                password);
                String query = "SELECT * FROM asta.objects where category = '"+ category +"';";
            java.sql.Statement statement = connection.createStatement();
            ResultSet resultQuery = statement.executeQuery(query);

            while (resultQuery.next()) {
				items.add(
                    new Item(
                        resultQuery.getInt(1), 
                        resultQuery.getString(2), 
                        resultQuery.getString(3)));
			}
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }

    public int deleteItemById (int idItem){
        int response = 0;

        try {
            Connection connection = DriverManager.getConnection(
                url,
                username,
                password);
            java.sql.Statement statement = connection.createStatement();
            String query = "delete from asta.objects where idObject = '"+ idItem +"'";
            response = statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return response;
    }

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/Scuola";
        String username = "root";
        String password = "leopoldodinarnia";

        Repository repo = new Repository(url, username, password);

        System.out.println("ITEMS");
        String cat = "giocattoli";
        ArrayList<Item> items = repo.selectItemsByCategory(cat);
        for (Item item : items) {
            System.out.println("idItem: "+item.getIdItem()+"\nnome: " + item.getName()+"\ncategory: " + item.getCategory()+"\n");
        }


        System.out.println("CATEGORIES");

        ArrayList<String> categories = repo.selectCategories();
        for (String category : categories){
            System.out.println("category:" + category);
        }

        System.out.println("DELETE");
        if (repo.deleteItemById(9)>0){
            System.out.println("delete successful");
        } else {
            System.out.println("delete failed");
        }
    }
}
