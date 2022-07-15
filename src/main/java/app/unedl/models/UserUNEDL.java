package app.unedl.models;

import javafx.scene.control.CheckBox;

/**
 * Defines standard user of U.N.E.D.L
 */
public class UserUNEDL {
    public UserUNEDL(int id, String name, String ln1, String ln2, String email, String reg, boolean active, String type){
        setId(id);
        setName(name);
        setLn1(ln1);
        setLn2(ln2);
        setEmail(email);
        setReg(reg);
        setActive(active);
        setType(type);
    }

    public void setId(int id){ this.id = id; }

    public int getId(){ return this.id; }

    public void setName(String name){ this.name = name; }

    public String getName(){ return this.name; }

    public void setLastName(String ln, LASTNAME tutor){
        switch (tutor){
            case FATHER -> this.ln2 = ln;
            case MOTHER -> this.ln1 = ln;
        }
    }

    public String getLastName(LASTNAME tutor){ return (tutor == LASTNAME.FATHER) ? this.ln2 : this.ln1; }

    public void setLn1(String ln1){
        this.ln1 = ln1;
    }
    public void setLn2(String ln2){
        this.ln2 = ln2;
    }
    public String getLn1(){
        return this.ln1;
    }
    public String getLn2(){
        return this.ln2;
    }

    public void setEmail(String email){ this.email = email; }

    public String getEmail(){ return this.email; }

    public void setReg(String reg){
        this.reg = reg;
    }

    public String getReg(){ return this.reg; }

    public void setActive(boolean active){
        this.active = active;
    }

    public boolean isActive(){
        return this.active;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public enum LASTNAME {FATHER, MOTHER};
    private int id;
    private String name;
    private String ln1;
    private String ln2;
    private String email;
    private String reg;
    private boolean active;
    private String type;
}
