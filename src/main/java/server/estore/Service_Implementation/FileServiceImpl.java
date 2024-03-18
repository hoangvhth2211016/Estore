package server.estore.Service_Implementation;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import org.springframework.stereotype.Service;
import server.estore.Model.Order.Dto.OrderDetailRes;
import server.estore.Model.Order.Order;
import server.estore.Model.Product_Order.Dto.ProductsOrderRes;
import server.estore.Model.Product_Order.ProductsOrder;
import server.estore.Service.FileService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;

@Service
public class FileServiceImpl implements FileService {
    
    @Override
    public byte[] generateInvoiceAsPdf(OrderDetailRes orderDetail) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        
        PdfWriter writer = new PdfWriter(outputStream);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf, PageSize.A4);
        
        // User Information
        document.add(new Paragraph("Order Details").setBold().setFontSize(16));
        document.add(new Paragraph("User Information:").setBold()).setTopMargin(10);
        document.add(new Paragraph("Username: " + orderDetail.getUsername()));
        document.add(new Paragraph("Name: " + orderDetail.getFirstname()+", "+orderDetail.getLastname()));
        document.add(new Paragraph("Email: " + orderDetail.getEmail()));
        
        // Shipping Address
        document.add(new Paragraph("Shipping Address:").setBold()).setTopMargin(10);
        document.add(new Paragraph("Street: "+orderDetail.getStreet()));
        document.add(new Paragraph("City: "+orderDetail.getCity()));
        document.add(new Paragraph("Zipcode: "+orderDetail.getZipcode()));
        document.add(new Paragraph("Country: "+orderDetail.getCountry()));
        
        
        // Products Purchased
        document.add(new Paragraph("Products Purchased:").setBold()).setTopMargin(10);
        Table table = new Table(new float[]{3, 2, 2});
        //table.setWidthPercent(100);
        table.useAllAvailableWidth();
        
        table.addHeaderCell("Name").setBold();
        table.addHeaderCell("Purchase Price").setBold();
        table.addHeaderCell("Quantity").setBold();
        
        BigDecimal totalPrice = BigDecimal.ZERO;
        
        for(ProductsOrderRes product: orderDetail.getProductsOrders()){
            table.addCell(product.getProduct().getTitle());
            table.addCell(String.valueOf(product.getPurchasePrice()));
            table.addCell(String.valueOf(product.getQuantity()));
            totalPrice = totalPrice.add(product.getPurchasePrice().multiply(BigDecimal.valueOf(product.getQuantity())));
        };
        
        document.add(table);
        
        // Total Price
        document.add(new Paragraph("Total Price: $" + totalPrice).setBold()).setTopMargin(5);
        
        document.close();
        
        return outputStream.toByteArray();
        
    }
}
