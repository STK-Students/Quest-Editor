package stk.students.Data;

import lombok.Getter;
import lombok.Setter;

public class Role {
    //Attribute
    @Getter @Setter
    private String name;
    @Getter @Setter
    private String color;

    //Konstruktor
    public Role(String name, String color){
        this.name = name;
        this.color = color;
    }
}
