/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import com.google.gson.Gson;
import controlles.Products;
import database.ProductTableOperations;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author toshiba
 */
public class EditProduct extends HttpServlet {

    
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        
        try {
            response.setContentType("text/html;charset=UTF-8");
            
            PrintWriter out = response.getWriter();
            Products product = new Products();
            
            DiskFileItemFactory factory = new DiskFileItemFactory();
            //Create a new file upload handler
            ServletFileUpload upload = new ServletFileUpload(factory);
            List<FileItem> items = upload.parseRequest(request);
            for (FileItem item : items) {
                if (item.isFormField()) {
                    String fieldName = item.getFieldName();
                    String fieldValue = item.getString();
                    if(fieldName.equals("productname")){
                        product.setName(fieldValue);
                    }else if(fieldName.equals("productid")){
                        product.setId(Integer.parseInt(fieldValue));
                    }else if(fieldName.equals("productdessctextArea")){
                        product.setDescription(fieldValue);
                    }else if(fieldName.equals("productquan")){
                        product.setQuantity(Integer.parseInt(fieldValue));
                    }else if(fieldName.equals("productprice")){
                        product.setSalary(Integer.parseInt(fieldValue));
                    }else if(fieldName.equals("productimage")){
                        product.setImage(fieldValue);
                    }else if(fieldName.equals("productcategory")){
                        if(fieldValue.equalsIgnoreCase("Men"))
                            product.setCategory(1);
                        else if(fieldValue.equalsIgnoreCase("Women"))
                            product.setCategory(2);
                        else if(fieldValue.equalsIgnoreCase("Accessories"))
                            product.setCategory(3);
                        else 
                            product.setCategory(0);
                    }
                }
            }
            //out.print(product.getId()+",name: "+product.getName()+",desc: "+product.getDescription()+",quan: "+product.getQuantity()+",price: "+product.getSalary()+",image: "+product.getImage()+",cat: "+product.getCategory());
            
            ProductTableOperations pto = new ProductTableOperations();
            pto.updateProduct(product);
            response.sendRedirect("manage_product.html");
            
        } catch (FileUploadException ex) {
            Logger.getLogger(EditProduct.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
