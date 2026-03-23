package org.example.Practice3;

public class Owner {
    private String cmnd;     // 12 số, unique
    private String fullName;
    private String email;

    public Owner(String cmnd, String fullName, String email) {
        if (!cmnd.matches("\\d{12}"))
            throw new IllegalArgumentException(
                    "CMND must be exactly 12 digits!");
        if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$"))
            throw new IllegalArgumentException(
                    "Invalid email format!");

        this.cmnd     = cmnd;
        this.fullName = fullName;
        this.email    = email;
    }

    // ── Getters / Setters ─────────────────────────────────────
    public String getCmnd()     { return cmnd; }
    public String getFullName() { return fullName; }
    public String getEmail()    { return email; }

    public String getInfo() {
        return String.format("CMND: %s | Name: %-20s | Email: %s",
                cmnd, fullName, email);
    }

    @Override
    public String toString() { return getInfo(); }
}
