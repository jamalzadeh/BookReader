package dictonary.mj.dastan;

public class Fruit {
    private static final String TAG = "Fruit";

    private String fruitImg;
	private String fruitName;
    private String calories;
    private String id;
    private String table;

	public Fruit(String fruitImg, String fruitName,String calories,String _id) {
		super();
        this.setFruitImg(fruitImg);
		this.setFruitName(fruitName);
        this.setCalories(calories);
        this.id=_id;
	}
	public Fruit(String fruitImg, String fruitName,String calories,String tablename,String _id) {
		super();
        this.setFruitImg(fruitImg);
		this.setFruitName(fruitName);
        this.setCalories(calories);
        this.setFruitTable(tablename);
        this.id=_id;
	}
	public String getTableName(){
		return table;
	}
	public void setFruitTable(String tableN){
		this.table=tableN;
	}
    public String getFruitName() {
        return fruitName;
    }

    public void setFruitName(String fruitName) {
        this.fruitName = fruitName;
    }

    public String getFruitImg() {
        return fruitImg;
    }
    public String getid(){
    	return id;
    }

    public void setFruitImg(String fruitImg) {
        this.fruitImg = fruitImg;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }
}
