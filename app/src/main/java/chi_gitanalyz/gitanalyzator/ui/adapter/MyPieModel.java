package chi_gitanalyz.gitanalyzator.ui.adapter;

/**
 * Created by Papin on 11.10.2016.
 */

public class MyPieModel {
    String name;
    Float value;
    Integer color;

    public MyPieModel(Integer color, String name, Float value) {
        this.color = color;
        this.name = name;
        this.value = value;
    }
}
