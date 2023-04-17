import java.io.Serializable;

public class Item implements Serializable{
    private int id;
    private String name;
    private String category;
    private String ipGroup;

    public Item (String name, String category, String ipGroup){
        this.name = name;
        this.category = category;
        this.ipGroup = ipGroup;
    }

    public Item (
        int id,
        String name,
        String category,
        String ipGroup
    ){
        this.id = id;
        this.name = name;
        this.category = category;
        this.ipGroup = ipGroup;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getIpGroup() {
        return ipGroup;
    }

    public void setIpGroup(String ipGroup) {
        this.ipGroup = ipGroup;
    }

}
