package JavaSwing_Project; /**
 * Created by sumitshrestha on 5/11/16.
 */

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.*;
import java.util.ArrayList;

public class StudentInformationSystem extends JFrame implements ItemListener,ActionListener, ListSelectionListener{

    JPanel panel= new JPanel ();
    JLabel backGround = new JLabel(new ImageIcon(getClass().getResource("icon.png")));
    JLabel backGround2 = new JLabel(new ImageIcon(getClass().getResource("deer.png")));
    JLabel labelID= new JLabel("ID (Roll No.)");
    JLabel labelFName= new JLabel("First Name:");
    JLabel labelMidName= new JLabel("Middle Name:");
    JLabel labelLastName = new JLabel("Last Name:");
    JLabel labelAge= new JLabel("Age:");
    JLabel labelGender= new JLabel("Gender:");
    JLabel labelAddress= new JLabel("Address:");

    //JTextField

    JTextField txtID=new JTextField ();
    JTextField txtFirst= new JTextField ();
    JTextField txtMid= new JTextField ();
    JTextField txtLast= new JTextField ();
    JTextField txtAge= new JTextField ();
    JTextField txtAddress= new JTextField();
    JTextField txtGenderHolder = new JTextField();

    //RadioButton

    JRadioButton RBMale=new JRadioButton ("Male");
    JRadioButton RBFemale= new JRadioButton ("Female");
    JRadioButton RBHidden=new JRadioButton("Hidden");

    ButtonGroup bgroup= new ButtonGroup ();
    String [] listAddress= {"Kathmandu","Bhaktapur","Lalitpur","Pokhara","BiratNagar","Lamjung","Gorkha","Gulmi","Syangja","Butwal"};
    JList addressList = new JList (listAddress);

    //Button

    JButton New= new JButton ("New");
    JButton save= new JButton ("Save");
    JButton update = new JButton ("Update");
    JButton delete= new JButton ("Delete");
    JButton search= new JButton ("Search");
    JButton reset=new JButton ("Reset");
    JButton viewAll = new JButton("View All Students");
    //
    //
    //
    //
    // JButton exportButton = new JButton("Export");
    Connection cn;
    Statement st;
    PreparedStatement ps;


    public StudentInformationSystem() {
        super ("Student Information System");
        setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);

        panel.setLayout(null);
        panel.add(backGround);
        backGround.setBounds(300,270,225,225);
        panel.add(backGround2);
        backGround2.setBounds(300,45,225,225);

        //********************************************************JLabel

        panel.add(labelID);
        labelID.setBounds (30,50,120,30);
        //	id.setForeground(Color.CYAN);

        panel.add(labelFName);
        labelFName.setBounds (30,80,120,30);

        panel.add (labelMidName)	;
        labelMidName.setBounds (30,110,120,30);

        panel.add (labelLastName);
        labelLastName.setBounds (30,140,120,30);

        panel.add(labelAge);
        labelAge.setBounds (30,170,120,30);

        panel.add (labelGender);
        labelGender.setBounds (30,210,120,30);

        panel.add (labelAddress);
        labelAddress.setBounds (30,280,120,30);

        //********************************************************JTextField

        panel.add (txtID);
        txtID.setBounds (120,50,120,30)    ;
        txtID.setHorizontalAlignment (JTextField.LEFT);

        panel.add (txtFirst);
        txtFirst.setBounds (120,80,120,30);
        txtFirst.setHorizontalAlignment (JTextField.LEFT);

        panel.add (txtMid);
        txtMid.setBounds (120,110,120,30);
        txtMid.setHorizontalAlignment (JTextField.LEFT);

        panel.add (txtLast);
        txtLast.setBounds (120,140,120,30);
        txtLast.setHorizontalAlignment (JTextField.LEFT);

        panel.add (txtAge);
        txtAge.setBounds (120,170,120,30);
        txtAge.setHorizontalAlignment (JTextField.LEFT);
        panel.add(txtAddress);
        txtAddress.setBounds (130,470,100,30);

        //******************************************************RadioButton

        panel.add (RBMale);
        RBMale.setBounds(100,210,90,30);
        RBMale.setBackground(Color.white);
        txtGenderHolder.setText("male");

        panel.add (RBFemale);
        RBFemale.setBounds (190,210,90,30);
        RBFemale.setBackground(Color.white);

        bgroup.add (RBMale);
        bgroup.add (RBFemale);
        bgroup.add (RBHidden);


        RBMale.addItemListener(this);
        RBFemale.addItemListener(this);

        //***********************************************************checkbox


        panel.add (addressList);
        addressList.setBounds (130,280,100,180);
        addressList.setSelectedIndex(0);
        addressList.setBackground(Color.yellow);
        addressList.addListSelectionListener(new ListSelectionListener(){

            public void valueChanged(ListSelectionEvent cl){
                txtAddress.setText(listAddress[addressList.getSelectedIndex()]);
            }
        });

        //Button

        panel.add (New);
        New.setBounds (5,520,80,30);
        panel.add (save);
        save.setBounds (95,520,80,30);
        panel.add (update);
        update.setBounds (185,520,80,30);
        panel.add (delete);
        delete.setBounds (275,520,80,30);
        panel.add (search);
        search.setBounds (365,520,80,30);
        panel.add(reset);
        reset.setBounds (455,520,80,30);
        panel.add(viewAll);
        viewAll.setBounds(400,20,170,30);
        //panel.add(exportButton);
        //exportButton.setBounds(300,20,70,30);

        txtID.setEnabled(false);
        txtFirst.setEnabled(false);
        txtMid.setEnabled(false);
        txtLast.setEnabled(false);
        txtAge.setEnabled(false);
        txtAddress.setEnabled(false);
        txtGenderHolder.setEnabled(false);

        New.addActionListener (this);
        save.addActionListener (this);
        save.setEnabled(false);
        update.addActionListener (this);
        update.setEnabled(false);
        delete.addActionListener (this);
        delete.setEnabled(false);
        search.addActionListener (this);
        search.setEnabled(true);
        reset.addActionListener(this);
        viewAll.setEnabled(true);
        viewAll.addActionListener(this);
        //exportButton.setEnabled(true);
        //exportButton.addActionListener(this);

        setContentPane(panel);

        try{
            Class.forName("com.mysql.jdbc.Driver");
            cn = DriverManager.getConnection("jdbc:mysql://localhost/studentInformationSystem","root","");
        }catch(ClassNotFoundException e){
            System.err.println("Failed to Load Driver");
            e.printStackTrace();
        }catch (SQLException e){
            System.err.println("Unable to Connect");
            e.printStackTrace();
        }
    }

    public void clear(){
        txtID.setText("");
        txtFirst.setText("");
        txtMid.setText("");
        txtLast.setText("");
        txtAge.setText("");
        txtAddress.setText("");
        RBHidden.setEnabled(true);
    }

//ActionListener Actions

    public void actionPerformed (ActionEvent e){
        Object source = e.getSource();
        if (source==New){
            save.setEnabled (true)	;
            update.setEnabled (false);
            delete.setEnabled (false);
            search.setEnabled (false);
            txtID.setEnabled(true);
            txtFirst.setEnabled(true);
            txtMid.setEnabled(true);
            txtLast.setEnabled(true);
            txtAge.setEnabled(true);
            txtAddress.setEnabled(true);
            txtGenderHolder.setEnabled(true);
            txtID.setText("");
            txtFirst.setText("");
            txtMid.setText("");
            txtLast.setText("");
            txtAge.setText("");
            txtAddress.setText("");
            txtGenderHolder.setText("");
        }
        if(source == save){
            String idf=txtID.getText();
            String ftname=txtFirst.getText();
            String mdname=txtMid.getText();
            String ltname=txtLast.getText();
            String addresscd=txtAddress.getText();
            String gender=txtGenderHolder.getText();
            String agef = txtAge.getText();
            if (!gender.equals("")&&!idf.equals("") && !ftname.equals("") && !ltname.equals("") && !agef.equals("")&& !addresscd.equals("")){
                New.setEnabled (true);
                update.setEnabled (false);
                delete.setEnabled (false);
                search.setEnabled(true);
                //----------------------------Check--------------------
                int id=0;
                int idChecker=0;
                int age=0;
                int ageChecker=0;
                int checkerFirst=0;
                int checkerMid=0;
                int checkerLast=0;
                try{
                    age=Integer.parseInt(txtAge.getText());
                    if(age==0) {

                        JOptionPane.showMessageDialog(null,"Change Age","Warning",JOptionPane.WARNING_MESSAGE);
                        ageChecker=1;
                    }
                }catch(NumberFormatException nfe){
                    JOptionPane.showMessageDialog(null,"Age should be Numbers only","Warning",JOptionPane.WARNING_MESSAGE);
                    age=0;
                    ageChecker=1;
                }
                try{
                    id=Integer.parseInt(txtID.getText());

                }catch(NumberFormatException nfe){
                    JOptionPane.showMessageDialog(null,"ID should be Numbers only","Warning",JOptionPane.WARNING_MESSAGE);
                    idChecker=1;
                }
                String checkFirstName=txtFirst.getText();
                String checkLastName=txtLast.getText();
                String checkMidName=txtMid.getText();
                if(checkFirstName.matches(".*\\d.*")){
                    JOptionPane.showMessageDialog(null,"Invalid input in First Name","Warning",JOptionPane.WARNING_MESSAGE);
                    checkerFirst=1;
                } else{
                    checkerFirst=0;
                }
                if(checkLastName.matches(".*\\d.*")){
                    JOptionPane.showMessageDialog(null,"Invalid input in Last Name","Warning",JOptionPane.WARNING_MESSAGE);
                    checkerLast=1;
                } else{
                    checkerLast=0;
                }

                if(checkMidName.matches(".*\\d.*")){
                    JOptionPane.showMessageDialog(null,"Invalid input in Mid Name","Warning",JOptionPane.WARNING_MESSAGE);
                    checkerMid=1;
                } else{
                    checkerMid=0;
                }


/*
                if(txtMid.getText().length()>1){
                    JOptionPane.showMessageDialog(null,"Set Middle initial to one character only!","Warning",JOptionPane.WARNING_MESSAGE);
                    checkerMid=1;

                }*/

                if(checkerFirst==1||checkerMid==1||checkerLast==1||idChecker==1||ageChecker==1){

                }

                //----------------------------End of Check--------------------


                else{
                    String idx = String.valueOf(id);

                    try{


                        if (!gender.equals("")&&!idx.equals("") && !ftname.equals("") && !ltname.equals("") && age!=0&& !addresscd.equals("")) {

                            st= cn.createStatement();
                            ps=cn.prepareStatement("INSERT INTO Student" + " (id,firstName,middleName,lastName,age,gender,address) " + " VALUES(?,?,?,?,?,?,?)");

                            ps.setString(1,txtID.getText());
                            ps.setString(2,txtFirst.getText());
                            ps.setString(3,txtMid.getText());
                            ps.setString(4,txtLast.getText());
                            ps.setString(5,txtAge.getText());
                            ps.setString(6,txtGenderHolder.getText());
                            ps.setString(7,txtAddress.getText());
                            ps.executeUpdate();
                            JOptionPane.showMessageDialog(null,"New account has been successfully added.","Student Information System",JOptionPane.INFORMATION_MESSAGE);
                            txtID.setEnabled(false);
                            txtFirst.setEnabled(false);
                            txtMid.setEnabled(false);
                            txtLast.setEnabled(false);
                            txtAge.setEnabled(false);
                            txtAddress.setEnabled(false);
                            txtGenderHolder.setEnabled(false);
                            st.close();
                            clear();
                        }
                        else{
                            JOptionPane.showMessageDialog(null,"Fill Up the Empty Fields!.","Student Information System",JOptionPane.ERROR_MESSAGE);

                        }


                    }catch(SQLException sqlEx){
                        sqlEx.printStackTrace();
                        JOptionPane.showMessageDialog(null,"Unable to save! ID number already taken.","Student Information System",JOptionPane.ERROR_MESSAGE);}




                }
            }
            else{
                JOptionPane.showMessageDialog(null,"Fill Up the Empty Fields!.","Student Information System",JOptionPane.ERROR_MESSAGE);

            }
        }

        if (source==delete){
            New.setEnabled (true);
            save.setEnabled (true);
            delete.setEnabled (true);
            search.setEnabled (true);
        }
        if (source==search){
            delete.setEnabled(true);
            update.setEnabled(true);
            save.setEnabled(false);
            txtID.setEnabled(true);
            txtFirst.setEnabled(true);
            txtMid.setEnabled(true);
            txtLast.setEnabled(true);
            txtAge.setEnabled(true);
            txtAddress.setEnabled(true);
            txtGenderHolder.setEnabled(true);
            String sUser ="";
            int tmp= 0;
            clear();
            sUser = JOptionPane.showInputDialog(null,"Enter Student ID to search.","Student Information System",JOptionPane.QUESTION_MESSAGE);

            if(!sUser.equals("")){
                try{

                    st=cn.createStatement();
                    ResultSet rs=st.executeQuery("SELECT * FROM Student WHERE id = '" + sUser + "'");

                    while(rs.next()){

                        txtID.setText(rs.getString(1));

                        txtFirst.setText(rs.getString(2));

                        txtMid.setText(rs.getString(3));

                        txtLast.setText(rs.getString(4));

                        txtAge.setText(rs.getString(5));

                        txtAddress.setText(rs.getString(7));

                        if(txtGenderHolder.equals("male")){
                            RBFemale.setSelected(true);

                        }
                        else{
                            RBMale.setSelected(true);
                        }
                        tmp=1;
                    }
                    st.close();

                    if (tmp==0){
                        update.setEnabled(false);
                        delete.setEnabled(false);
                        JOptionPane.showMessageDialog(null,"No record found!!.","Student Information System",JOptionPane.INFORMATION_MESSAGE);
                    }

                }catch(SQLException s){
                    JOptionPane.showMessageDialog(null,"Unable to search!.","Student Information System",JOptionPane.ERROR_MESSAGE);
                    System.out.println("SQL Error" + s.toString() + " " + s.getErrorCode() + " " + s.getSQLState());
                }
            }
        }
        if(source == update){
            txtID.setEnabled(true);
            txtFirst.setEnabled(true);
            txtMid.setEnabled(true);
            txtLast.setEnabled(true);
            txtAge.setEnabled(true);
            txtAddress.setEnabled(true);
            txtGenderHolder.setEnabled(true);
            //----------------------------Check--------------------
            int id=0;
            int idChecker=0;
            int age=0;
            int ageChecker=0;
            int checkerFirst=0;
            int checkerMid=0;
            int checkerLast=0;

            try{

                age=Integer.parseInt(txtAge.getText());
                if(age==0){
                    ageChecker=1;
                    JOptionPane.showMessageDialog(null,"Change Age","Warning",JOptionPane.WARNING_MESSAGE);
                }
            }catch(NumberFormatException nfe){
                JOptionPane.showMessageDialog(null,"Age should be Numbers only","Warning",JOptionPane.WARNING_MESSAGE);
                ageChecker=1;
                age=0;
            }
            try{
                id=Integer.parseInt(txtID.getText());

            }catch(NumberFormatException nfe){
                JOptionPane.showMessageDialog(null,"ID should be Numbers only","Warning",JOptionPane.WARNING_MESSAGE);

                idChecker=1;
            }
            String checkFirstName=txtFirst.getText();
            String checkLastName=txtLast.getText();
            String checkMidName=txtMid.getText();
            if(checkFirstName.matches(".*\\d.*")){
                JOptionPane.showMessageDialog(null,"Invalid input in First Name","Warning",JOptionPane.WARNING_MESSAGE);
                checkerFirst=1;
            } else{
                checkerFirst=0;
            }
            if(checkLastName.matches(".*\\d.*")){
                JOptionPane.showMessageDialog(null,"Invalid input in Last Name","Warning",JOptionPane.WARNING_MESSAGE);
                checkerLast=1;
            } else{
                checkerLast=0;
            }
            if(checkMidName.matches(".*\\d.*")){
                JOptionPane.showMessageDialog(null,"Invalid input in Mid Name","Warning",JOptionPane.WARNING_MESSAGE);
                checkerMid=1;
            } else{
                checkerMid=0;
            }
           /* if(txtMid.getText().length()>1){
                JOptionPane.showMessageDialog(null,"Set Middle initial to one character only!","Warning",JOptionPane.WARNING_MESSAGE);
                checkerMid=1;
            }*/
            if(checkerFirst==1||checkerMid==1||checkerLast==1||idChecker==1||ageChecker==1){

            }

            else{

                String idx = String.valueOf(id);
                String ftname=txtFirst.getText();
                String mdname=txtMid.getText();
                String ltname=txtLast.getText();
                String addresscd=txtAddress.getText();
                String gender=txtGenderHolder.getText();
                try{
                    if (!gender.equals("")&&!idx.equals("") && !ftname.equals("") && !ltname.equals("") && age>0&& !addresscd.equals("")) {
                        st=cn.createStatement();
                        PreparedStatement ps = cn.prepareStatement("UPDATE Student SET firstName = '" + ftname + "', middleName = '" + mdname + "',lastName= '" + ltname+"',age = '"+age+"',gender='"+gender+"',address='"+addresscd+"' WHERE id = '" + id + "'");
                        ps.executeUpdate();
                        JOptionPane.showMessageDialog(null,"Account has been successfully updated.","Student Information System",JOptionPane.INFORMATION_MESSAGE);
                        txtID.setEnabled(false);
                        txtFirst.setEnabled(false);
                        txtMid.setEnabled(false);
                        txtLast.setEnabled(false);
                        txtAge.setEnabled(false);
                        txtAddress.setEnabled(false);
                        txtGenderHolder.setEnabled(false);
                        txtID.requestFocus(true);
                        clear();
                        st.close();
                    }
                    else{
                        JOptionPane.showMessageDialog(null,"Please Fill Up The Empty Fields","Warning",JOptionPane.WARNING_MESSAGE);
                    }
                }catch (SQLException y){
                    JOptionPane.showMessageDialog(null,"Unable to update!.","Student Information System",JOptionPane.ERROR_MESSAGE);
                    System.out.println("SQL Error" + y.toString() + " " +y.getErrorCode() + " " + y.getSQLState());
                    if (!gender.equals("")&&!idx.equals("") && !ftname.equals("")&& !mdname.equals("") && !ltname.equals("") && age!=0&& !addresscd.equals("")) {
                    }
                    else{

                        delete.setEnabled(false);
                        update.setEnabled(false);
                        save.setEnabled(false);
                    }
                }
            }
        }
        if(source==delete){
            if(!txtID.getText().equalsIgnoreCase("")){

                try{
                    PreparedStatement ps = cn.prepareStatement("DELETE FROM Student WHERE id ='"+ txtID.getText() + "'");
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(null,"Account has been successfully deleted.","Payroll System: User settings ",JOptionPane.INFORMATION_MESSAGE);
                    txtID.setEnabled(false);
                    txtFirst.setEnabled(false);
                    txtMid.setEnabled(false);
                    txtLast.setEnabled(false);
                    txtAge.setEnabled(false);
                    txtAddress.setEnabled(false);
                    txtGenderHolder.setEnabled(false);
                    txtID.requestFocus(true);
                    clear();
                    st.close();
                }catch(SQLException s){
                    JOptionPane.showMessageDialog(null,"Unable to delete!.","Student Information System",JOptionPane.ERROR_MESSAGE);
                }
            }
            delete.setEnabled(false);
            update.setEnabled(false);
            save.setEnabled(false);
        }
        if(source==reset){
            save.setEnabled(false);
            search.setEnabled(true);
            New.setEnabled(true);
            clear();
        }
        if(source==viewAll){
            new OpenDialogue();
        }
        //if(source==exportButton){
       // }
    }

    class OpenDialogue extends JFrame{
        public OpenDialogue(){
            super("Student List");
            JPanel resultPanel = new JPanel();
            String columnName[]={"Roll No.","First Name","Middle Name","Last Name","Age", "Gender","Address"};
            resultPanel.setLayout(new GridLayout(5,6));
            /*JButton exportButton = new JButton("Export");
            resultPanel.add(exportButton);
            exportButton.setBounds(400,200,30,40);
            exportButton.setEnabled(true);
            setContentPane(resultPanel);*/
            add(resultPanel);
            setVisible(true);
            setSize(new Dimension(600,400));
            setResizable(false);
            java.util.List<String[]> dataList = new ArrayList<>();

            try{
                st=cn.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM Student ");
                while(rs.next()){
                    String id = rs.getString(1);
                    JLabel idLab = new JLabel(id);
                    resultPanel.add(idLab);

                    String firstName = rs.getString(2);
                    JLabel firstLab = new JLabel(firstName);
                    resultPanel.add(firstLab);

                    String middleName = rs.getString(3);
                    JLabel middleLab = new JLabel(middleName);
                    resultPanel.add(middleLab);

                    String lastName = rs.getString(4);
                    JLabel lastLab = new JLabel(lastName);
                    resultPanel.add(lastLab);

                    String age = rs.getString(5);
                    JLabel ageLab = new JLabel(age);
                    resultPanel.add(ageLab);

                    String gender = rs.getString(6);
                    JLabel ageGender = new JLabel(gender);
                    resultPanel.add(ageGender);

                    String address = rs.getString(7);
                    JLabel ageAddress = new JLabel(address);
                    resultPanel.add(ageAddress);
                    String values[]= {id,firstName,middleName,lastName,age,gender,address};
                    dataList.add(values);
                }
            }catch(SQLException s){
                JOptionPane.showMessageDialog(null,"Unable to delete!.","Student Information System",JOptionPane.ERROR_MESSAGE);
            }

            Object data[][] = new Object[dataList.size()][7];
            int i=0;
            for(String[] each:dataList){
                data[i][0] = each[0];
                data[i][1] = each[1];
                data[i][2] = each[2];
                data[i][3] = each[3];
                data[i][4] = each[4];
                data[i][5] = each[5];
                data[i][6] = each[6];
                i++;
            }
            JTable table = new JTable(data,columnName);
            JScrollPane pane = new JScrollPane(table);
            add(pane);
            //File file = new File("book.xls");
            //new ExcelExporter(table,file);

        }
    }

  /*  class ExcelExporter {

        public ExcelExporter(JTable table, File file){
            try{
                System.out.println("ddd");
                TableModel model = table.getModel();
                System.out.println(model);
                FileWriter excel = new FileWriter(file);

                for(int i = 0; i < model.getColumnCount(); i++){
                    excel.write(model.getColumnName(i) + "\t");
                }

                excel.write("\n");

                for(int i=0; i< model.getRowCount(); i++) {
                    for(int j=0; j < model.getColumnCount(); j++) {
                        excel.write(model.getValueAt(i,j).toString()+"\t");
                    }
                    excel.write("\n");
                }

                excel.close();

            }catch(IOException e){ System.out.println(e); }
        }

    }*/

    public void itemStateChanged (ItemEvent j)	{

        if(j.getSource().equals(RBFemale)){
            txtGenderHolder.setText("female");
        }
        if(j.getSource().equals(RBMale)){
            txtGenderHolder.setText("male");
        }

    }

    public void valueChanged(ListSelectionEvent e){

    }

    public static void main (String args []){
        JFrame frame= new StudentInformationSystem();
        frame.setSize(600,600);
        frame.setVisible(true);
        frame.setResizable(false);

    }
}