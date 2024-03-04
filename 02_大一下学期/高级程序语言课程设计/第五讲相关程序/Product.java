/**  
 * @Title:  Product.java
 * @Package DatabaseTest
 * @Description: TODO(用一句话描述该文件做什么)
 * @author Dangzhang
 * @date  2020-3-18 上午9:31:40
 * @version V1.0  
 * Update Logs:
 * ****************************************************
 * Name:
 * Date:
 * Description:
 ******************************************************
 */
package DatabaseTest;

/**
 * @ClassName: Product
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Dangzhang
 * @date 2020-3-18 上午9:31:40
 * 
 */
public class Product
{

    int ProductId;
    String ProductName;
    String ProductPrice;
    String ProductDiscount;

    public int getProductId()
    {
        return ProductId;
    }

    public void setProductId(int i)
    {
        ProductId = i;
    }

    public String getProductName()
    {
        return ProductName;
    }

    public void setProductName(String productName)
    {
        ProductName = productName;
    }

    public String getProductPrice()
    {
        return ProductPrice;
    }

    public void setProductPrice(String productPrice)
    {
        ProductPrice = productPrice;
    }

    public String getProductDiscount()
    {
        return ProductDiscount;
    }

    public void setProductDiscount(String productDiscount)
    {
        ProductDiscount = productDiscount;
    }

    public static void main(String[] args)
    {
        // TODO Auto-generated method stub

    }

}
