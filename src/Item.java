import java.io.Serializable;

public class Item implements Serializable{
    private int idItem;
    private String name;
    private String category;

    public Item (
        int idItem,
        String name,
        String category
    ){
        this.idItem = idItem;
        this.name = name;
        this.category = category;
    }

    public int getIdItem() {
        return idItem;
    }

    public void setIdItem(int idItem) {
        this.idItem = idItem;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}
