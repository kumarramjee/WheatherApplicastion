package teleportscreenlatest.mobimedia.com.wheatherapplicastion.ui;

/**
 * Created by ram on 26/6/15.
 */
public class User {
    private final String firstName;
    private final String lastName;
    private final String middleName;

    public User(String firstName, String lastName, String middleName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getMiddleName() {
        return middleName;
    }
}
