package DatabaseTest;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class ProductText extends JFrame implements ActionListener
{

    private JLabel lblProductId;
    private JLabel lblProductName;
    private JLabel lblProductPrice;
    private JLabel lblDiscount;

    private JTextField tfProductId;
    private JTextField tfPRoductName;
    private JTextField tfPRoductPrice;
    private JTextField tfDiscount;

    private JButton btnFirst;
    private JButton btnLast;
    private JButton btnBefore;
    private JButton btnNext;
    private JButton btnAdd;
    private JButton btnModify;
    private JButton btnDel;
    private JButton btnQuery;

    private DbConnection dbconn = null;
    private Connection conn = null;
    private ResultSet rs = null;
    private PreparedStatement pstmt = null;
    private Statement stmt = null;

    public ProductText()
    {
        this.setLayout(null);
        this.setBounds(200, 200, 400, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        lblProductId = new JLabel("商品编号");
        lblProductId.setBounds(70, 15, 60, 25);
        this.add(lblProductId);

        tfProductId = new JTextField();
        tfProductId.setBounds(140, 15, 160, 25);
        this.add(tfProductId);

        lblProductName = new JLabel("商品名称");
        lblProductName.setBounds(70, 60, 60, 25);
        this.add(lblProductName);

        tfPRoductName = new JTextField();
        tfPRoductName.setBounds(140, 60, 160, 25);
        this.add(tfPRoductName);

        lblProductPrice = new JLabel("商品价格");
        lblProductPrice.setBounds(70, 105, 60, 25);
        this.add(lblProductPrice);

        tfPRoductPrice = new JTextField();
        tfPRoductPrice.setBounds(140, 105, 160, 25);
        this.add(tfPRoductPrice);

        lblDiscount = new JLabel("商品数量");
        lblDiscount.setBounds(70, 150, 60, 25);
        this.add(lblDiscount);

        tfDiscount = new JTextField();
        tfDiscount.setBounds(140, 150, 160, 25);
        this.add(tfDiscount);

        btnAdd = new JButton("添加");
        btnAdd.addActionListener(this);
        btnAdd.setBounds(60, 250, 60, 30);
        this.add(btnAdd);

        btnDel = new JButton("删除");
        btnDel.addActionListener(this);
        btnDel.setBounds(130, 250, 60, 30);
        this.add(btnDel);

        btnModify = new JButton("修改");
        btnModify.addActionListener(this);
        btnModify.setBounds(200, 250, 60, 30);
        this.add(btnModify);

        btnQuery = new JButton("查询");
        btnQuery.addActionListener(this);
        btnQuery.setBounds(270, 250, 60, 30);
        this.add(btnQuery);

        btnFirst = new JButton("第一个");
        btnFirst.addActionListener(this);
        btnFirst.setBounds(15, 290, 80, 30);
        this.add(btnFirst);

        btnLast = new JButton("最后一个");
        btnLast.addActionListener(this);
        btnLast.setBounds(105, 290, 90, 30);
        this.add(btnLast);

        btnBefore = new JButton("上一个");
        btnBefore.setBounds(205, 290, 80, 30);
        btnBefore.addActionListener(this);
        this.add(btnBefore);

        btnNext = new JButton("下一个");
        btnNext.addActionListener(this);
        btnNext.setBounds(295, 290, 80, 30);
        this.add(btnNext);

        this.setTitle("商品信息");
        this.setResizable(false);
        this.setVisible(true);
    }

    public static void main(String[] args)
    {
        new ProductText();
    }

    public void actionPerformed(ActionEvent e)
    {
        if (e.getActionCommand().equals("添加"))
        {
            if (tfProductId.getText().equals("")
                    || tfPRoductName.getText().equals("")
                    || tfPRoductPrice.getText().equals("")
                    || tfDiscount.getText().equals(""))
            {
                JOptionPane.showMessageDialog(this, "请填写相关信息");
                return;
            }
            else
            {
                add();
                tfProductId.setText("");
                tfPRoductName.setText("");
                tfPRoductPrice.setText("");
                tfDiscount.setText("");
            }
        }
        else if (e.getActionCommand().equals("删除"))
        {
            if (tfProductId.getText().equals(""))
            {
                JOptionPane.showMessageDialog(this, "请选择一条信息");
            }
            else
            {
                int n = JOptionPane.showConfirmDialog(this, "您确定要删除此条记录吗？");
                if (n == 0)
                {
                    del();
                    tfProductId.setText("");
                    tfPRoductName.setText("");
                    tfPRoductPrice.setText("");
                    tfDiscount.setText("");
                }
            }
        }
        else if (e.getActionCommand().equals("修改"))
        {
            if (tfProductId.getText().trim().equals("")
                    || tfPRoductName.getText().trim().equals("")
                    || tfPRoductPrice.getText().trim().equals("")
                    || tfDiscount.getText().trim().equals(""))
            {
                JOptionPane.showMessageDialog(this, "请填写相关信息");
            }
            else
            {
                modify();
            }
        }
        else if (e.getActionCommand().equals("查询"))
        {
            if (tfProductId.getText().trim().equals(""))
            {
                JOptionPane.showMessageDialog(this, "请选择要查询高品的ID");
            }
            else
            {
                query();
            }
        }
        else if (e.getActionCommand().equals("第一个"))
        {
            first();
        }
        else if (e.getActionCommand().equals("最后一个"))
        {
            laster();
        }
        else if (e.getActionCommand().equals("上一个"))
        {
            if (tfProductId.getText().trim().equals(""))
            {
                JOptionPane.showMessageDialog(this, "请您先选择一条商品信息");
            }
            else
            {
                before();
            }
        }
        else if (e.getActionCommand().equals("下一个"))
        {
            if (tfProductId.getText().equals(""))
            {
                JOptionPane.showMessageDialog(this, "请您先选择一条商品信息");
            }
            else
            {
                next();
            }
        }
    }

    public void add()
    {
        dbconn = new DbConnection();
        conn = dbconn.getConnection();
        String sql1 = "select ProductId from Product where ProductId="
                + tfProductId.getText();
        String sql = "insert into Product (ProductId,ProductName,ProductPrice,ProductDiscount) values(?,?,?,?)";
        try
        {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql1);
        }
        catch (SQLException e1)
        {
            e1.printStackTrace();
        }
        try
        {
            if (!rs.next())
            {
                try
                {
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setInt(1, Integer.parseInt(tfProductId.getText()));
                    pstmt.setString(2, tfPRoductName.getText());
                    pstmt.setString(3, tfPRoductPrice.getText());
                    pstmt.setString(4, tfDiscount.getText());
                    pstmt.executeUpdate();
                    JOptionPane.showMessageDialog(this, "添加成功");
                }
                catch (SQLException e)
                {
                    // e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "添加失败");
                }
                finally
                {
                    try
                    {
                        if (pstmt != null)
                            pstmt.close();
                        if (conn != null)
                            conn.close();
                    }
                    catch (SQLException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
            else
            {
                JOptionPane.showMessageDialog(this, "商品编号重复");
            }
        }
        catch (NumberFormatException e)
        {
            e.printStackTrace();
        }
        catch (HeadlessException e)
        {
            e.printStackTrace();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void del()
    {
        dbconn = new DbConnection();
        conn = dbconn.getConnection();
        String sql = "delete from Product where ProductId = "
                + tfProductId.getText();
        try
        {
            pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
            // JOptionPane.showMessageDialog(this, "删除成功");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void modify()
    {
        dbconn = new DbConnection();
        conn = dbconn.getConnection();
        String sql1 = "select  ProductId from Product where ProductId="
                + tfProductId.getText();
        try
        {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql1);
        }
        catch (SQLException e1)
        {
            e1.printStackTrace();
        }
        try
        {
            if (rs.next())
            {
                String sql = "update Product set ProductName = ?,ProductPrice = ?,ProductDiscount=? where ProductId = "
                        + tfProductId.getText();
                try
                {
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, tfPRoductName.getText());
                    pstmt.setString(2, tfPRoductPrice.getText());
                    pstmt.setString(3, tfDiscount.getText());
                    pstmt.executeUpdate();
                    JOptionPane.showMessageDialog(this, "修改成功");
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    try
                    {
                        if (pstmt != null)
                            pstmt.close();
                        if (conn != null)
                            conn.close();
                    }
                    catch (SQLException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
            else
            {
                JOptionPane.showMessageDialog(this, "没有与此ID相对应的记录");
                tfProductId.setText("");
                tfPRoductName.setText("");
                tfPRoductPrice.setText("");
                tfDiscount.setText("");
            }
        }
        catch (HeadlessException e)
        {
            e.printStackTrace();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void query()
    {
        dbconn = new DbConnection();
        conn = dbconn.getConnection();
        String sql = "select ProductName,ProductPrice,ProductDiscount from Product where ProductId="
                + tfProductId.getText();
        try
        {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            if (rs.next())
            {
                String ProductName = rs.getString("ProductName");
                String ProductPrice = rs.getString("ProductPrice");
                String ProductDiscount = rs.getString("ProductDiscount");
                tfPRoductName.setText(ProductName);
                tfPRoductPrice.setText(ProductPrice);
                tfDiscount.setText(ProductDiscount);
            }
            else
            {
                JOptionPane.showMessageDialog(this, "查询失败,您可能没有此信息");
                tfProductId.setText("");
                tfPRoductName.setText("");
                tfPRoductPrice.setText("");
                tfDiscount.setText("");
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (rs != null)
                    rs.close();
                if (stmt != null)
                    stmt.close();
                if (conn != null)
                    conn.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void first()
    {
        dbconn = new DbConnection();
        conn = dbconn.getConnection();
        String sql = "select * from Product";
        try
        {
            // 1.TYPE_FORWORD_ONLY,只可向前滚动；
            // 2.TYPE_SCROLL_INSENSITIVE,双向滚动，但不及时更新，就是如果数据库里的数据修改过，并不在ResultSet中反应出来。
            // 3.TYPE_SCROLL_SENSITIVE，双向滚动，并及时跟踪数据库的更新,以便更改ResultSet中的数据。
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            rs = stmt.executeQuery(sql);
            if (rs.first())
            {
                // stmt = conn.createStatement();
                String ProductId = rs.getString("ProductId");
                String ProductName = rs.getString("ProductName");
                String ProductPrice = rs.getString("ProductPrice");
                String ProductDiscount = rs.getString("ProductDiscount");
                tfProductId.setText(ProductId);
                tfPRoductName.setText(ProductName);
                tfPRoductPrice.setText(ProductPrice);
                tfDiscount.setText(ProductDiscount);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (rs != null)
                    rs.close();
                if (stmt != null)
                    stmt.close();
                if (conn != null)
                    conn.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void laster()
    {
        dbconn = new DbConnection();
        conn = dbconn.getConnection();
        String sql = "select * from Product";
        try
        {
            // 1.TYPE_FORWORD_ONLY,只可向前滚动；
            // 2.TYPE_SCROLL_INSENSITIVE,双向滚动，但不及时更新，就是如果数据库里的数据修改过，并不在ResultSet中反应出来。
            // 3.TYPE_SCROLL_SENSITIVE，双向滚动，并及时跟踪数据库的更新,以便更改ResultSet中的数据。
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            rs = stmt.executeQuery(sql);
            if (rs.last())
            {
                String ProductId = rs.getString("ProductId");
                String ProductName = rs.getString("ProductName");
                String ProductPrice = rs.getString("ProductPrice");
                String ProductDiscount = rs.getString("ProductDiscount");
                tfProductId.setText(ProductId);
                tfPRoductName.setText(ProductName);
                tfPRoductPrice.setText(ProductPrice);
                tfDiscount.setText(ProductDiscount);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (rs != null)
                    rs.close();
                if (stmt != null)
                    stmt.close();
                if (conn != null)
                    conn.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void before()
    {
        ArrayList list = new ArrayList();
        int index = 0;
        dbconn = new DbConnection();
        conn = dbconn.getConnection();
        String sql = "select * from Product";
        try
        {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next())
            {
                Product p = new Product();
                p.setProductId(rs.getInt(1));
                p.setProductName(rs.getString(2));
                p.setProductPrice(rs.getString(3));
                p.setProductDiscount(rs.getString(4));
                list.add(p);
            }

            int id = Integer.parseInt(tfProductId.getText());
            for (int i = 0; i < list.size(); i++)
            {
                Product pr = (Product) list.get(i);
                if (pr.getProductId() == id)
                {
                    index = i;
                    break;
                }
            }
            if (index >= 1)
            {
                Product pro = (Product) list.get(--index);
                String s = String.valueOf(pro.getProductId());
                tfProductId.setText(s);
                tfPRoductName.setText(pro.getProductName());
                tfPRoductPrice.setText(pro.getProductPrice());
                tfDiscount.setText(pro.getProductDiscount());
            }
            else
            {
                JOptionPane.showMessageDialog(this, "已经是第一条记录");
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void next()
    {
        ArrayList list = new ArrayList();
        int index = 0;
        dbconn = new DbConnection();
        conn = dbconn.getConnection();
        String sql = "select * from Product";
        try
        {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next())
            {
                Product p = new Product();
                p.setProductId(rs.getInt(1));
                p.setProductName(rs.getString(2));
                p.setProductPrice(rs.getString(3));
                p.setProductDiscount(rs.getString(4));
                list.add(p);
            }

            int id = Integer.parseInt(tfProductId.getText());
            for (int i = 0; i < list.size(); i++)
            {
                Product pr = (Product) list.get(i);
                if (pr.getProductId() == id)
                {
                    index = i;
                    break;
                }
            }
            if (index < list.size() - 1)
            {
                Product pro = (Product) list.get(++index);
                String s = String.valueOf(pro.getProductId());
                tfProductId.setText(s);
                tfPRoductName.setText(pro.getProductName());
                tfPRoductPrice.setText(pro.getProductPrice());
                tfDiscount.setText(pro.getProductDiscount());
            }
            else
            {
                JOptionPane.showMessageDialog(this, "已经是最后一条记录");
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}