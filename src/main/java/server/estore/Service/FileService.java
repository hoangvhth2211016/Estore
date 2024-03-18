package server.estore.Service;

import server.estore.Model.Order.Dto.OrderDetailRes;

public interface FileService {
    
    byte[] generateInvoiceAsPdf(OrderDetailRes order);
    
}
