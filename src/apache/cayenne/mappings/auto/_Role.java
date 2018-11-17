package apache.cayenne.mappings.auto;

import java.util.List;

import org.apache.cayenne.CayenneDataObject;
import org.apache.cayenne.exp.Property;

import apache.cayenne.mappings.User;

/**
 * Class _Role was generated by Cayenne.
 * It is probably a good idea to avoid changing this class manually,
 * since it may be overwritten next time code is regenerated.
 * If you need to make any customizations, please use subclass.
 */
public abstract class _Role extends CayenneDataObject {

    private static final long serialVersionUID = 1L; 

    public static final String DESCRIPTION_PROPERTY = "description";
    public static final String NAME_PROPERTY = "name";
    public static final String USERS_PROPERTY = "users";

    public static final String ROLE_ID_PK_COLUMN = "role_id";

    public static final Property<String> DESCRIPTION = Property.create("description", String.class);
    public static final Property<String> NAME = Property.create("name", String.class);
    public static final Property<List<User>> USERS = Property.create("users", List.class);

    public void setDescription(String description) {
        writeProperty("description", description);
    }
    public String getDescription() {
        return (String)readProperty("description");
    }

    public void setName(String name) {
        writeProperty("name", name);
    }
    public String getName() {
        return (String)readProperty("name");
    }

    public void addToUsers(User obj) {
        addToManyTarget("users", obj, true);
    }
    public void removeFromUsers(User obj) {
        removeToManyTarget("users", obj, true);
    }
    @SuppressWarnings("unchecked")
    public List<User> getUsers() {
        return (List<User>)readProperty("users");
    }


}
