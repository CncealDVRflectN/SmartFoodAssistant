package by.solutions.dumb.smartfoodassistant.containers;

public class Recipe implements Container {

    //region Variables

    private String name;

    //endregion


    //region Constructors

    public Recipe(String name) {
        this.name = name;
    }

    //endregion


    //region Getters

    @Override
    public String getName() {
        return name;
    }

    //endregion


    //region Setters

    public void setName(String name) {
        this.name = name;
    }

    //endregion
}
