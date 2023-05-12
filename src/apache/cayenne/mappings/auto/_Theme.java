package apache.cayenne.mappings.auto;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import org.apache.cayenne.BaseDataObject;
import org.apache.cayenne.exp.Property;

import apache.cayenne.mappings.Subtheme;

/**
 * Class _Theme was generated by Cayenne.
 * It is probably a good idea to avoid changing this class manually,
 * since it may be overwritten next time code is regenerated.
 * If you need to make any customizations, please use subclass.
 */
public abstract class _Theme extends BaseDataObject {

    private static final long serialVersionUID = 1L; 

    public static final String THEME_ID_PK_COLUMN = "theme_id";

    public static final Property<String> DESCRIPTION = Property.create("description", String.class);
    public static final Property<String> NAME = Property.create("name", String.class);
    public static final Property<List<Subtheme>> SUBTHEMES = Property.create("subthemes", List.class);

    protected String description;
    protected String name;

    protected Object subthemes;

    public void setDescription(String description) {
        beforePropertyWrite("description", this.description, description);
        this.description = description;
    }

    public String getDescription() {
        beforePropertyRead("description");
        return this.description;
    }

    public void setName(String name) {
        beforePropertyWrite("name", this.name, name);
        this.name = name;
    }

    public String getName() {
        beforePropertyRead("name");
        return this.name;
    }

    public void addToSubthemes(Subtheme obj) {
        addToManyTarget("subthemes", obj, true);
    }

    public void removeFromSubthemes(Subtheme obj) {
        removeToManyTarget("subthemes", obj, true);
    }

    @SuppressWarnings("unchecked")
    public List<Subtheme> getSubthemes() {
        return (List<Subtheme>)readProperty("subthemes");
    }

    @Override
    public Object readPropertyDirectly(String propName) {
        if(propName == null) {
            throw new IllegalArgumentException();
        }

        switch(propName) {
            case "description":
                return this.description;
            case "name":
                return this.name;
            case "subthemes":
                return this.subthemes;
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
            case "description":
                this.description = (String)val;
                break;
            case "name":
                this.name = (String)val;
                break;
            case "subthemes":
                this.subthemes = val;
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
        out.writeObject(this.description);
        out.writeObject(this.name);
        out.writeObject(this.subthemes);
    }

    @Override
    protected void readState(ObjectInputStream in) throws IOException, ClassNotFoundException {
        super.readState(in);
        this.description = (String)in.readObject();
        this.name = (String)in.readObject();
        this.subthemes = in.readObject();
    }

}
