import javax.swing.*;
import java.awt.*;
import java.io.File;

public class PpsChecker {
    protected final JTextField ppsField;
    protected final long currentByteStart;
    protected final RandomFile application;
    protected final File file;

    public PpsChecker(JTextField ppsField, long currentByteStart, RandomFile application, File file) {
        this.ppsField = ppsField;
        this.currentByteStart = currentByteStart;
        this.application = application;
        this.file = file;
    }

    public boolean validate() {
        boolean valid = true;

        if (ppsField.isEditable() && ppsField.getText().trim().isEmpty()) {
            ppsField.setBackground(new Color(255, 150, 150));
            valid = false;
        }

        if (ppsField.isEditable() && !correctPps(ppsField.getText().trim())) {
            ppsField.setBackground(new Color(255, 150, 150));
            valid = false;
        }

        return valid;
    }

    public boolean correctPps(String pps) {
        if (pps.length() == 8 || pps.length() == 9) {
            for (int i = 0; i < pps.length(); i++) {
                if (i < 7 && !Character.isDigit(pps.charAt(i))) {
                    return false;
                } else if (i == 7 && !Character.isLetter(pps.charAt(i))) {
                    return false;
                } else if (i == 8 && !Character.isLetter(pps.charAt(i))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
