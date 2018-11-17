package apache.cayenne.mappings.auto;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.cayenne.BaseDataObject;
import org.apache.cayenne.exp.Property;

import apache.cayenne.mappings.Role;

/**
 * Class _User was generated by Cayenne.
 * It is probably a good idea to avoid changing this class manually,
 * since it may be overwritten next time code is regenerated.
 * If you need to make any customizations, please use subclass.
 */
public abstract class _User extends BaseDataObject {

    private static final long serialVersionUID = 1L; 

    public static final String FIRST_NAME_PROPERTY = "firstName";
    public static final String LAST_NAME_PROPERTY = "lastName";
    public static final String MIDDLE_NAME_PROPERTY = "middleName";
    public static final String LOGIN_PROPERTY = "login";
    public static final String PASSWORD_PROPERTY = "password";
    public static final String RATING_PROPERTY = "rating";
    public static final String ROLE_PROPERTY = "role";

    public static final String PRIMARY_KEY_PK_COLUMN = "primaryKey";

    public static final Property<String> FIRST_NAME = Property.create("firstName", String.class);
    public static final Property<String> LAST_NAME = Property.create("lastName", String.class);
    public static final Property<String> MIDDLE_NAME = Property.create("middleName", String.class);
    public static final Property<String> LOGIN = Property.create("login", String.class);
    public static final Property<String> PASSWORD = Property.create("password", String.class);
    public static final Property<Double> RATING = Property.create("rating", Double.class);
    public static final Property<Role> ROLE = Property.create("role", Role.class);

    protected String firstName;
    protected String lastName;
    protected String middleName;
    protected String login;
    protected String password;
    protected Double rating;

    protected Object role;

    public void setFirstName(String firstName) {
        beforePropertyWrite("firstName", this.firstName, firstName);
        this.firstName = firstName;
    }

    public String getFirstName() {
        beforePropertyRead("firstName");
        return this.firstName;
    }

    public void setLastName(String lastName) {
        beforePropertyWrite("lastName", this.lastName, lastName);
        this.lastName = lastName;
    }

    public String getLastName() {
        beforePropertyRead("lastName");
        return this.lastName;
    }

    public void setMiddleName(String middleName) {
        beforePropertyWrite("middleName", this.middleName, middleName);
        this.middleName = middleName;
    }

    public String getMiddleName() {
        beforePropertyRead("middleName");
        return this.middleName;
    }

    public void setLogin(String login) {
        beforePropertyWrite("login", this.login, login);
        this.login = login;
    }

    public String getLogin() {
        beforePropertyRead("login");
        return this.login;
    }

    public void setPassword(String password) {
        beforePropertyWrite("password", this.password, password);
        this.password = password;
    }

    public String getPassword() {
        beforePropertyRead("password");
        return this.password;
    }

    public void setRating(Double rating) {
        beforePropertyWrite("rating", this.rating, rating);
        this.rating = rating;
    }

    public Double getRating() {
        beforePropertyRead("rating");
        return this.rating;
    }

    public void setRole(Role role) {
        setToOneTarget("role", role, true);
    }

    public Role getRole() {
        return (Role)readProperty("role");
    }

    @Override
    public Object readPropertyDirectly(String propName) {
        if(propName == null) {
            throw new IllegalArgumentException();
        }

        switch(propName) {
            case "firstName":
                return this.firstName;
            case "lastName":
                return this.lastName;
            case "middleName":
                return this.middleName;
            case "login":
                return this.login;
            case "password":
                return this.password;
            case "rating":
                return this.rating;
            case "role":
                return this.role;
            default:
                return super.readPropertyDirectly(propName);
        }
    }

    @Override
    public void writePropertyDirectly(String propName, Object val) {
        if(propName == null) {
            throw new IllegalArgumentException();
        }

        switch (propName) {
            case "firstName":
                this.firstName = (String)val;
                break;
            case "lastName":
                this.lastName = (String)val;
                break;
            case "middleName":
                this.middleName = (String)val;
                break;
            case "login":
                this.login = (String)val;
                break;
            case "password":
                this.password = (String)val;
                break;
            case "rating":
                this.rating = (Double)val;
                break;
            case "role":
                this.role = val;
                break;
            default:
                super.writePropertyDirectly(propName, val);
        }
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        writeSerialized(out);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        readSerialized(in);
    }

    @Override
    protected void writeState(ObjectOutputStream out) throws IOException {
        super.writeState(out);
        out.writeObject(this.firstName);
        out.writeObject(this.lastName);
        out.writeObject(this.middleName);
        out.writeObject(this.login);
        out.writeObject(this.password);
        out.writeObject(this.rating);
        out.writeObject(this.role);
    }

    @Override
    protected void readState(ObjectInputStream in) throws IOException, ClassNotFoundException {
        super.readState(in);
        this.firstName = (String)in.readObject();
        this.lastName = (String)in.readObject();
        this.middleName = (String)in.readObject();
        this.login = (String)in.readObject();
        this.password = (String)in.readObject();
        this.rating = (Double)in.readObject();
        this.role = in.readObject();
    }

}
