/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package java2.asm;

import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author pc
 */
public class Main extends javax.swing.JFrame {
    ArrayList<ProductPortfolio> productPortfolios = new ArrayList<>();
    ArrayList<Product> products = new ArrayList<>();
    DefaultTableModel tableModel; 
    
    String url = "jdbc:mysql://localhost/asm";
    String userName = "root";
    
    String name;
    Product product = null;
    ProductPortfolio productPortfolio = null;
    SimpleDateFormat formatter=new SimpleDateFormat("dd/MM/yyyy");
    /**
     * Creates new form Main
     */
    public Main() {
        this.setTitle("Product Manager");
        this.setResizable(false);
        initComponents();
        
//        check = 1;
        tableModel = (DefaultTableModel) productsTable.getModel();
        loadDataProduct();
        showAllProducts();
        updateDMbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                btnDetP.setEnabled(false);
            }
        });
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                btnDetP.setEnabled(false);
                btnCtr.setEnabled(false);
            }
        });
            
        productsTable.addMouseListener(new MouseAdapter() {
            
            @Override
            public void mouseClicked(MouseEvent me) {
                int indexColumn = productsTable.getSelectedColumn();
                int indexRow = productsTable.getSelectedRow();
                if(check==3){
                btnCtr.setEnabled(true);
                for (int i = 0; i < productPortfolios.size(); i++) {
                    if (productPortfolios.get(i).getName()==productsTable.getValueAt(indexRow, indexColumn)) {
                        productPortfolio = productPortfolios.get(i);
                        JTextField name = new JTextField(); name.setText(productPortfolios.get(i).getName());
                        JTextField id = new JTextField(); id.setText(Integer.toString(productPortfolios.get(i).getId()));
                        Object[] form = {
                            "Ten danh muc", name,
                            "Ma danh muc", id.getText()
                        };
                        int input = JOptionPane.showConfirmDialog(null,form,"Danh muc",JOptionPane.OK_CANCEL_OPTION);
                        productPortfolios.get(i).setName(name.getText());
                        boolean check = true;
                        if (input == JOptionPane.OK_OPTION&&check) {
                            if(!name.getText().isEmpty()){
                                tableModel.setColumnCount(0);
                                tableModel.setRowCount(0);
                                updateDM(productPortfolios.get(i));
                                showAllPP();}
                            else {
                                JOptionPane.showMessageDialog(null, "lai chua nhap thong tin");
                                tableModel.setColumnCount(0);
                                tableModel.setRowCount(0);
                                productPortfolios.clear();
                                loadDataPP();
                                showAllPP();
                                check=false;
                            }
                        }
                        
                    } else if (productsTable.getValueAt(indexRow, indexColumn)=="add category") {
                        JTextField name = new JTextField(); 
                        JTextField id = new JTextField(); id.setText(Integer.toString(productPortfolios.size()+1));
                        Object[] form = {
                            "Ten danh muc", name,
                            "Ma danh muc", id.getText()
                        };
//                        System.out.println(productPortfolios.get(i).getName());
                        productPortfolios.clear();
                        int input = JOptionPane.showConfirmDialog(null,form,"Danh muc",JOptionPane.OK_CANCEL_OPTION);
                        
                        if (input == JOptionPane.OK_OPTION&&!name.getText().isEmpty()) {
                            tableModel.setColumnCount(0);
                            tableModel.setRowCount(0);
                            ProductPortfolio productPortfolio = new ProductPortfolio(Integer.parseInt(id.getText()), name.getText());
                            insertPP(productPortfolio);
                            loadDataPP();
                            showAllPP();
                        } else {
                            JOptionPane.showMessageDialog(null, "chua nhap du lieu");
                            tableModel.setColumnCount(0);
                            tableModel.setRowCount(0);
                            loadDataPP();
                            showAllPP();
                        }
                        break;
                    }
                    
                }}
                if(check!=3){
                btnDetP.setEnabled(true);
                for (int i = 0; i < products.size(); i++) {
                    if (indexColumn==-1) {
                        break;
                    }
                    if (products.get(i).getName()==productsTable.getValueAt(indexRow, indexColumn)) {
                        product = products.get(i);
                        
                        JTextField id = new JTextField(); id.setText(Integer.toString(product.getId()));
                        JTextField idPP = new JTextField(); idPP.setText(Integer.toString(product.getIdPP()));
                        JTextField name = new JTextField(); name.setText(product.getName());
                        JTextField price = new JTextField(); price.setText(Float.toString(product.getPrice()));
                        JTextField dateInput = new JTextField(); dateInput.setText(product.getInputDate());
                        JTextField date = new JTextField(); date.setText(product.getDate());
                        JTextField description = new JTextField(); description.setText(product.getDescription());
                        Object[] form = {
                            "id" , id,
                            "idPP" , idPP,
                            "name" , name,
                            "price" , price,
                            "dateInput" , dateInput,
                            "date" , date,
                            "description" , description
                        };
                        System.out.println(id);
                        
                        int input = JOptionPane.showConfirmDialog(null, form, "Update&Info", JOptionPane.OK_CANCEL_OPTION);
                        if (input == JOptionPane.OK_OPTION) {
                            products.clear();
                            tableModel.setColumnCount(0);
                            tableModel.setRowCount(0);
//                    System.out.println(id.getText());
                            product.setId(Integer.parseInt(id.getText()));
                            product.setIdPP(Integer.parseInt(idPP.getText()));
                            product.setName(name.getText());
                            product.setPrice(Float.parseFloat(price.getText()));
                            product.setInputDate(dateInput.getText());
                            product.setDate(date.getText());
                            product.setDescription(description.getText());
                    
                            updateProduct(product);
                            loadDataProduct();
                            System.out.println(productPortfolios.size());
                            
                            if (check==1) {
                                showAllProducts();
                            } else if(check==0) {
                                
                                for (ProductPortfolio productPortfolio : productPortfolios) {
                                    showProducts(productPortfolio);
                                }
                            } else if (check==2) {
                                JOptionPane.showMessageDialog(null, "Updated");
                                showAllProducts();
                                check=1;
                            }
                        } 
                        break;
                    }
                    else if(productsTable.getValueAt(indexRow, indexColumn)==null) {
                        if (check==2) {
                            break;
                        }
                        ProductPortfolio temp = productPortfolios.get(indexColumn);
                        System.out.println("null");
                        JTextField idPP = new JTextField(); idPP.setText(Integer.toString(temp.getId()));
                        JTextField name = new JTextField(); 
                        JTextField price = new JTextField(); 
                        JTextField dateInput = new JTextField();
                        JTextField date = new JTextField(); 
                        JTextField description = new JTextField(); 
                        Object[] form = {
                            "idPP" , idPP,
                            "name" , name,
                            "price" , price,
                            "dateInput" , dateInput,
                            "date" , date,
                            "description" , description
                        };
                        int input = JOptionPane.showConfirmDialog(null, form, "Update", JOptionPane.OK_CANCEL_OPTION);
                        productPortfolios.clear();
                        products.clear();
                        if (input == JOptionPane.OK_OPTION) {
                            tableModel.setColumnCount(0);
                            tableModel.setRowCount(0);
                            products.clear();
                            productPortfolios.clear();
                            if (name.getText().isEmpty()) {
                                System.out.println("chua nhap");
                            } else {
                                Product newProduct = new Product(Integer.parseInt(idPP.getText()),name.getText(),Float.parseFloat(price.getText()),dateInput.getText(),date.getText(),description.getText());
                                insertProduct(newProduct);
                                
                            }
                            
                            loadDataPP();
                            loadDataProduct();
                            System.out.println(productPortfolios.size());
                            for (ProductPortfolio productPortfolio : productPortfolios) {
                                    showProducts(productPortfolio);
            
                            }
                            break;
                        }
                    }
                }}
            }
        });
        

    }
    Object[] soldP = new Object[] {};
    Object[] inventotyP = new Object[] {};
    Object[] statisD = new Object[] {};
    Object[] statisW = new Object[] {};
    Object[] statisM = new Object[] {};
    ArrayList<Object> sold = new ArrayList<Object>(Arrays.asList(soldP));
    ArrayList<Object> inventory = new ArrayList<Object>(Arrays.asList(inventotyP));
    ArrayList<Object> statisDay = new ArrayList<Object>(Arrays.asList(statisD));
    ArrayList<Object> statisWeek = new ArrayList<Object>(Arrays.asList(statisW));
    ArrayList<Object> statisMonth = new ArrayList<Object>(Arrays.asList(statisM));
    private void productDateOfSale() {
        Connection cnn = null;
        PreparedStatement preparedStatement = null;
        
        try {
            cnn = DriverManager.getConnection(url, userName, "");
            //query
            String sql = "select * from product";
            preparedStatement = cnn.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {                
                if (rs.getString("dateOfSale").isEmpty()) {
                    inventory.add(rs.getString("name"));
                } else {
                    sold.add(rs.getString("name"));
                }
                if (formatter.format(new Date()).compareTo(rs.getString("dateOfSale"))==0) {
                    statisDay.add(rs.getString("name"));
                } 
//                else if (formatter.parse(rs.getString("dateOfSale")).getYear()==new Date().getYear()) {
//                    if (formatter.parse(rs.getString("dateOfSale")).getMonth()==new Date().getMonth()) {
////                        if (formatter.parse(rs.getString("dateOfSale")).getDay()) {
////                            
////                        }
//                    }
//                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CloseConnect.close(cnn, preparedStatement);
        }
    }
    
    private void showAllPP() {
        Object[] objectsPP = new Object[] {};
        Object[] objectsPP2 = new Object[] {};
        ArrayList<Object> name = new ArrayList<Object>(Arrays.asList(objectsPP));
        ArrayList<Object> id = new ArrayList<Object>(Arrays.asList(objectsPP2));
        for (ProductPortfolio productPortfolio: productPortfolios) {
            name.add(productPortfolio.getName());
            id.add(productPortfolio.getId());
        }
        name.add("add category");
        id.add(null);
        tableModel.addColumn("Ma danh muc", id.toArray());
        tableModel.addColumn("Ten danh muc", name.toArray());
    }
    
    private void loadDataPP() {
        Connection cnn = null;
        PreparedStatement preparedStatement = null;
        
        try {
            cnn = DriverManager.getConnection(url,userName,"");
            String sql = "select * from productportfolio";
            preparedStatement = cnn.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {                
                ProductPortfolio productPortfolio = new ProductPortfolio(
                        rs.getInt("id"),
                        rs.getString("name"));
                productPortfolios.add(productPortfolio);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CloseConnect.close(cnn, preparedStatement);
        }
    }
    
    private void showAllProducts() {
        tableModel.setColumnCount(0);
        tableModel.setRowCount(0);
        Object[] objects = new Object[]{};
        ArrayList<Object> temp = new ArrayList<Object>(Arrays.asList(objects));
        for (Product p: products) {
            temp.add(p.getName());
        }
        tableModel.addColumn("ALL PRODUCT", temp.toArray());
    }
    
    private void showProducts(ProductPortfolio productPortfolio) {
        Object[] objects = new Object[]{};
        ArrayList<Object> temp = new ArrayList<Object>(Arrays.asList(objects));
        for (Product p: products) {
            if (p.getIdPP()== productPortfolio.getId()) {
                temp.add(p.getName());
            }
            
        }
        System.out.println(temp);
        tableModel.addColumn(productPortfolio.getName(), temp.toArray());
    }
    
    private void loadDataProduct() {
        Connection cnn = null;
        PreparedStatement preparedStatement = null;
        
        try {
            cnn = DriverManager.getConnection(url, userName, "");
            //query
            String sql = "select * from product";
            preparedStatement = cnn.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            
            while (rs.next()) {                
                Product product = new Product(
                        rs.getInt("id"),
                        rs.getInt("idPP"),
                        rs.getString("name"),
                        rs.getFloat("price"),
                        rs.getString("dateInput"),
                        rs.getString("date"),
                        rs.getString("description"));
                products.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CloseConnect.close(cnn, preparedStatement);
        }
    }
    
    private void updateDM(ProductPortfolio productPortfolio) {
        Connection cnn = null;
        PreparedStatement preparedStatement = null;
        
        try {
            // open
            cnn = DriverManager.getConnection(url, userName, "");
            //query
            String sql = "update productportfolio set name = ? where id = ?";
            preparedStatement = cnn.prepareStatement(sql);
            preparedStatement.setInt(2, productPortfolio.getId());
            preparedStatement.setString(1, productPortfolio.getName());
            
            preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CloseConnect.close(cnn, preparedStatement);
        }
    }
    
    private void updateProduct(Product product) {
        Connection cnn = null;
        PreparedStatement preparedStatement = null;
        try {
            cnn = DriverManager.getConnection(url, userName, "");
            String sql = "update product set idPP = ?, name = ?, price = ?, dateInput = ?, date = ?, description = ? where id = ?";
            preparedStatement = cnn.prepareStatement(sql);
            preparedStatement.setInt(7, product.getId());
            preparedStatement.setInt(1, product.getIdPP());
            preparedStatement.setString(2, product.getName());
            preparedStatement.setFloat(3, product.getPrice());
            preparedStatement.setString(4, product.getInputDate());
            preparedStatement.setString(5, product.getDate());
            preparedStatement.setString(6, product.getDescription());
            
            preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CloseConnect.close(cnn, preparedStatement);
        }
    }
    
    private void insertPP(ProductPortfolio productPortfolio) {
        Connection cnn = null;
        PreparedStatement preparedStatement = null;
        try {
            cnn = DriverManager.getConnection(url, userName, "");
            String sql = "insert into productPortfolio (name) values(?)";
            preparedStatement = cnn.prepareStatement(sql);
            preparedStatement.setString(1, productPortfolio.getName());
            preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CloseConnect.close(cnn, preparedStatement);
        }
    }
    
    private void insertProduct(Product product) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try {
            //open connection
            connection = DriverManager.getConnection(url, userName, "");

            //query insert + update + delete
            String sql = "insert into product(idPP, name, price, dateInput, date, description ) values(?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, product.getIdPP());
            preparedStatement.setString(2, product.getName());
            preparedStatement.setFloat(3, product.getPrice());
            preparedStatement.setString(4, product.getInputDate());
            preparedStatement.setString(5, product.getDate());
            preparedStatement.setString(6, product.getDescription());
            preparedStatement.execute();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            CloseConnect.close(connection, preparedStatement);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        updateDMbtn = new javax.swing.JButton();
        filterBtn = new javax.swing.JButton();
        addBtn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        productsTable = new javax.swing.JTable();
        btnBack = new javax.swing.JButton();
        btnDetP = new javax.swing.JButton();
        btnCtr = new javax.swing.JButton();
        btnOut = new javax.swing.JButton();
        btnStatistical = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        updateDMbtn.setBackground(new java.awt.Color(102, 102, 102));
        updateDMbtn.setForeground(new java.awt.Color(255, 255, 255));
        updateDMbtn.setText("category");
        updateDMbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateDMbtnActionPerformed(evt);
            }
        });

        filterBtn.setBackground(new java.awt.Color(102, 102, 102));
        filterBtn.setForeground(new java.awt.Color(255, 255, 255));
        filterBtn.setText("filter");
        filterBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filterBtnActionPerformed(evt);
            }
        });

        addBtn.setBackground(new java.awt.Color(102, 102, 102));
        addBtn.setForeground(new java.awt.Color(255, 255, 255));
        addBtn.setText("product+");
        addBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBtnActionPerformed(evt);
            }
        });

        productsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(productsTable);

        btnBack.setText("<< back");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        btnDetP.setBackground(new java.awt.Color(255, 255, 255));
        btnDetP.setText("product-");
        btnDetP.setEnabled(false);
        btnDetP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDetPActionPerformed(evt);
            }
        });

        btnCtr.setBackground(new java.awt.Color(255, 255, 255));
        btnCtr.setText("category-");
        btnCtr.setEnabled(false);
        btnCtr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCtrActionPerformed(evt);
            }
        });

        btnOut.setText("log out");
        btnOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOutActionPerformed(evt);
            }
        });

        btnStatistical.setBackground(new java.awt.Color(102, 102, 102));
        btnStatistical.setForeground(new java.awt.Color(255, 255, 255));
        btnStatistical.setText(" statistical");
        btnStatistical.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStatisticalActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 618, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(updateDMbtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                        .addComponent(btnCtr))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(filterBtn)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnBack)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnOut, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(addBtn)
                                    .addComponent(btnStatistical))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnDetP)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(updateDMbtn)
                            .addComponent(btnCtr))
                        .addGap(18, 18, 18)
                        .addComponent(filterBtn)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(addBtn)
                            .addComponent(btnDetP))
                        .addGap(18, 18, 18)
                        .addComponent(btnStatistical)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnOut)
                            .addComponent(btnBack)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(14, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void search(String search) {
        
        products.clear();
        Connection cnn = null;
        PreparedStatement preparedStatement = null;
        try {
            cnn = DriverManager.getConnection(url,userName,"");
            preparedStatement = cnn.prepareStatement("select * from product where name like '%"+search+"%'");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {                
                Product product = new Product(
                        rs.getInt("id"),
                        rs.getInt("idPP"),
                        rs.getString("name"),
                        rs.getFloat("price"),
                        rs.getString("dateInput"),
                        rs.getString("date"),
                        rs.getString("description"));
                products.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CloseConnect.close(cnn, preparedStatement);
        }
    }
    
    Object[] objects1 = new Object[] {};
    Object[] objects2 = new Object[] {};
    ArrayList<Object> outOfDate = new ArrayList<Object>(Arrays.asList(objects1));
    ArrayList<Object> stillUse = new ArrayList<Object>(Arrays.asList(objects2));
    private void filterBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filterBtnActionPerformed
        // TODO add your handling code here:
        check = 0;
        tableModel.setColumnCount(0);
        tableModel.setRowCount(0);
        String input; 
        input = JOptionPane.showInputDialog("NHAP 1->6\n1.Sap xe theo danh muc sp\n2.Han su dung\n3.Cac mat hang sap het HSD\n4.Danh sach san pham da ban\n5.Ton kho\n6.Search");
        int temp=0;

        if(input!=null){
        if (!input.isEmpty()) {
            temp=Integer.parseInt(input);
        } else {
            JOptionPane.showMessageDialog(this, "Chua nhap du lieu");
            products.clear();
            productPortfolios.clear();
            loadDataProduct();
            showAllProducts();
            temp=0;
        }
        if (temp == 1) {
            check=0;
            products.clear();
            productPortfolios.clear();
            loadDataPP();
            loadDataProduct();
            System.out.println(productPortfolios.size());
            for (ProductPortfolio productPortfolio : productPortfolios) {
                showProducts(productPortfolio);
            } // select * from product
        } else if (temp == 2) {
            check = 2;
            outOfDate.clear();
            stillUse.clear();
            System.out.println("aaa");
            productPortfolios.clear();
            products.clear();
            loadDataPP();
            loadDataProduct();
            for (Product p: products) {
                try {
                    if (formatter.parse(p.getDate()).compareTo(new Date())<0) {
                        outOfDate.add(p.getName());
                        System.out.println(outOfDate);
                    } else {
                        stillUse.add(p.getName());
                    }
                } catch (ParseException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
//            System.out.println(outOfDate);
            tableModel.addColumn("Out of date", outOfDate.toArray());
            tableModel.addColumn("Still use", stillUse.toArray());
            
        } else if (temp ==3) {
            productPortfolios.clear();
            products.clear();
            loadDataPP();
            loadDataProduct();
            SimpleDateFormat formatter=new SimpleDateFormat("dd/MM/yyyy");
            Object[] objects1 = new Object[] {};
            ArrayList<Object> expiry = new ArrayList<Object>(Arrays.asList(objects1));
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, +7);
            Date datebefore7 = cal.getTime();
            System.out.println(datebefore7);
            System.out.println(new Date());
            for (Product p: products) {
                
                try {
                    if (formatter.parse(p.getDate()).compareTo(datebefore7)<0 && formatter.parse(p.getDate()).compareTo(new Date())>0) {
                        expiry.add(p.getName());
                    }
                } catch (ParseException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            tableModel.addColumn("Sap het HSD roi", expiry.toArray());
        } else if (temp == 4) {
            sold.clear();
            productDateOfSale();
            tableModel.addColumn("Da ban", sold.toArray());
        } else if (temp == 5) {
            inventory.clear();
            productDateOfSale();
            tableModel.addColumn("Ton kho", inventory.toArray());
        } else if (temp == 6) {
            String search = JOptionPane.showInputDialog("Nhap ten sam pham can tim");
            search(search);
            showAllProducts();
        }} else {
            System.out.println("null roi");
            JOptionPane.showMessageDialog(this, "khong nhap du lieu a?");
            showAllProducts();
        }
//       
    }//GEN-LAST:event_filterBtnActionPerformed
    
    private void deletePP(ProductPortfolio productPortfolio) {
        Connection cnn = null;
        PreparedStatement preparedStatement = null;
        try {
            cnn = DriverManager.getConnection(url,userName,"");
            preparedStatement = cnn.prepareStatement("delete from productportfolio where id = ?");
            preparedStatement.setInt(1, productPortfolio.getId());
            preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CloseConnect.close(cnn, preparedStatement);
        }
    }
    
    private void updateDMbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateDMbtnActionPerformed
        // TODO add your handling code here:
        System.out.println("clicked btnUpdate");    
        check=3;
        productPortfolios.clear();
        tableModel.setColumnCount(0);
        tableModel.setRowCount(0);
        loadDataPP();
        showAllPP();
    }//GEN-LAST:event_updateDMbtnActionPerformed
    int check = 1;
    private void addBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBtnActionPerformed
        // TODO add your handling code here:
        
        JTextField idPP = new JTextField(); 
        JTextField name = new JTextField(); 
        JTextField price = new JTextField(); 
        JTextField dateInput = new JTextField();
        JTextField date = new JTextField(); 
        JTextField description = new JTextField(); 
        
        Object[] form = {
            "idPP" , idPP,
            "name" , name,
            "price" , price,
            "dateInput" , dateInput,
            "date" , date,
            "description" , description
        };
        int input = JOptionPane.showConfirmDialog(null, form, "Update", JOptionPane.OK_CANCEL_OPTION);
        productPortfolios.clear();
        products.clear();
        if (input == JOptionPane.OK_OPTION&&check==0) {
            tableModel.setRowCount(0);
            tableModel.setColumnCount(0);
            if (idPP.getText().isEmpty()&&name.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "chua nhap du lieu");
            } else {
                Product product = new Product(Integer.parseInt(idPP.getText()),name.getText(),Float.parseFloat(price.getText()),dateInput.getText(),date.getText(),description.getText());
                insertProduct(product);
                loadDataPP();
                loadDataProduct();
                for (ProductPortfolio portfolio: productPortfolios) {
                    showProducts(portfolio);
                }
            }
            
        } else if(input == JOptionPane.OK_OPTION&&check==1){
            tableModel.setColumnCount(0);
            tableModel.setRowCount(0);
            productPortfolios.clear();
            products.clear();
            if (idPP.getText().isEmpty()&&name.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "chua nhap thong tin");
                loadDataProduct();
                showAllProducts();
            } else {
                Product product = new Product(Integer.parseInt(idPP.getText()),name.getText(),Float.parseFloat(price.getText()),dateInput.getText(),date.getText(),description.getText());
                insertProduct(product);
                loadDataPP();
                loadDataProduct();
                showAllProducts();
            }
        }
    }//GEN-LAST:event_addBtnActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        // TODO add your handling code here:
        check = 1;
        products.clear();
        productPortfolios.clear();
        loadDataProduct();
        showAllProducts();
        
    }//GEN-LAST:event_btnBackActionPerformed
    
    private void deleteProduct(Product product) {
        Connection cnn = null;
        PreparedStatement preparedStatement = null;
        try {
            cnn = DriverManager.getConnection(url,userName,"");
            preparedStatement = cnn.prepareStatement("delete from product where id = ?");
            preparedStatement.setInt(1, product.getId());
            preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CloseConnect.close(cnn, preparedStatement);
        }
                
    }
    
    private void btnDetPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDetPActionPerformed
        // TODO add your handling code here:
        deleteProduct(product);
        JOptionPane.showMessageDialog(this, "deleted");
        if (check==0) {
            products.clear();
            productPortfolios.clear();
            tableModel.setColumnCount(0);
            tableModel.setRowCount(0);
            loadDataPP();
            loadDataProduct();
            for (ProductPortfolio productPortfolio : productPortfolios) {
                showProducts(productPortfolio);
            }
        } else if (check==1) {
            products.clear();
            productPortfolios.clear();
            tableModel.setColumnCount(0);
            tableModel.setRowCount(0);
            loadDataProduct();
            showAllProducts();
        }
    }//GEN-LAST:event_btnDetPActionPerformed

    private void btnCtrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCtrActionPerformed
        // TODO add your handling code here:
        deletePP(productPortfolio);
        tableModel.setColumnCount(0);
        tableModel.setRowCount(0);
        productPortfolios.clear();
        loadDataPP();
        showAllPP();
    }//GEN-LAST:event_btnCtrActionPerformed

    private void btnOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOutActionPerformed
        // TODO add your handling code here:
        Login login = new Login();
        this.setVisible(false);
        login.setVisible(true);
    }//GEN-LAST:event_btnOutActionPerformed

    private void btnStatisticalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStatisticalActionPerformed
        // TODO add your handling code here:
        statisDay.clear();
        productDateOfSale();
        tableModel.setColumnCount(0);
        tableModel.setRowCount(0);
        tableModel.addColumn("thong ke trong ngay",statisDay.toArray());
    }//GEN-LAST:event_btnStatisticalActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addBtn;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnCtr;
    private javax.swing.JButton btnDetP;
    private javax.swing.JButton btnOut;
    private javax.swing.JButton btnStatistical;
    private javax.swing.JButton filterBtn;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable productsTable;
    private javax.swing.JButton updateDMbtn;
    // End of variables declaration//GEN-END:variables
}
