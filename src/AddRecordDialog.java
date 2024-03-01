/*
 * 
 /*
 * 
 * This is a dialog for adding new Employees and saving records to file
 * 
 * */

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

public class AddRecordDialog extends JDialog implements ActionListener {
    JTextField idField, ppsField, surnameField, firstNameField, salaryField;
    JComboBox<String> genderCombo, departmentCombo, fullTimeCombo;
    JButton save, cancel;
    EmployeeDetails parent;

    // constructor for add record dialog
    public AddRecordDialog(EmployeeDetails parent) {
        setTitle("Add Record");
        setModal(true);
        this.parent = parent;
        this.parent.setEnabled(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JScrollPane scrollPane = new JScrollPane(dialogPane());
        setContentPane(scrollPane);

        getRootPane().setDefaultButton(save);

        setSize(500, 370);
        setLocation(350, 250);
        setVisible(true);
    }

    // initialize dialog container
    public Container dialogPane() {
        JPanel panel = new JPanel(new MigLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Employee Details"));

        idField = new JTextField(20);
        idField.setEditable(false);
        idField.setText(Integer.toString(this.parent.getNextFreeId()));

        ppsField = new JTextField(9);
        surnameField = new JTextField(9);
        firstNameField = new JTextField(9);

        genderCombo = new JComboBox<>(this.parent.gender);
        departmentCombo = new JComboBox<>(this.parent.department);
        fullTimeCombo = new JComboBox<>(this.parent.fullTime);

        salaryField = new JTextField(20);

        save = new JButton("Save");
        save.addActionListener(this);

        cancel = new JButton("Cancel");
        cancel.addActionListener(this);

        panel.add(new JLabel("ID: "));
        panel.add(idField, "wrap");
        panel.add(new JLabel("PPS: "));
        panel.add(ppsField, "wrap");
        panel.add(new JLabel("Surname: "));
        panel.add(surnameField, "wrap");
        panel.add(new JLabel("First Name: "));
        panel.add(firstNameField, "wrap");
        panel.add(new JLabel("Gender: "));
        panel.add(genderCombo, "wrap");
        panel.add(new JLabel("Department: "));
        panel.add(departmentCombo, "wrap");
        panel.add(new JLabel("Full Time: "));
        panel.add(fullTimeCombo, "wrap");
        panel.add(new JLabel("Salary: "));
        panel.add(salaryField, "wrap");
        panel.add(save, "skip, split 2");
        panel.add(cancel);

        return panel;
    }

    public void addRecord() {
        boolean fullTime = ((String) fullTimeCombo.getSelectedItem()).equalsIgnoreCase("Yes");
        Employee theEmployee;

        try {
            theEmployee = new EmployeeBuilder()
                    .setId(Integer.parseInt(idField.getText()))
                    .setPps(ppsField.getText().toUpperCase())
                    .setSurname(surnameField.getText().toUpperCase())
                    .setFirstName(firstNameField.getText().toUpperCase())
                    .setGender(genderCombo.getSelectedItem().toString().charAt(0))
                    .setDepartment(departmentCombo.getSelectedItem().toString())
                    .setSalary(Double.parseDouble(salaryField.getText()))
                    .setFullTime(fullTime)
                    .build();

            this.parent.currentEmployee = theEmployee;
            this.parent.addRecord(theEmployee);
            this.parent.displayRecords(theEmployee);
        } catch (NumberFormatException | IllegalStateException e) {
            e.printStackTrace();
        }
    }

    // check for input in text fields
    private boolean checkInput() {
        InputValidator ppsValidator = new PpsExistsValidator(ppsField, this.parent.currentByteStart, this.parent.application, this.parent.file);
        InputValidator firstNameValidator = new TextFieldValidator(firstNameField);
        InputValidator surnameValidator = new TextFieldValidator(surnameField);
        InputValidator genderValidator = new ComboValidator(genderCombo);
        InputValidator departmentValidator = new ComboValidator(departmentCombo);
        InputValidator salaryValidator = new SalaryValidator(salaryField);
        InputValidator fullTimeValidator = new ComboValidator(fullTimeCombo);

        ppsValidator.setNextValidator(firstNameValidator)
                .setNextValidator(surnameValidator)
                .setNextValidator(genderValidator)
                .setNextValidator(departmentValidator)
                .setNextValidator(salaryValidator)
                .setNextValidator(fullTimeValidator);

        boolean valid = ppsValidator.validate();

        if (!valid)
            JOptionPane.showMessageDialog(null, "Wrong values or format! Please check!");

        return valid;
    }

    // action performed
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Save")) {
            if (checkInput()) {
                addRecord();
                dispose();
                this.parent.changesMade = true;
            } else {
                JOptionPane.showMessageDialog(null, "Wrong values or format! Please check!");
            }
        } else if (e.getActionCommand().equals("Cancel"))
            dispose();
    }
}
