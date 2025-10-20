package com.devstack.pos.bo.custom.impl;

import com.devstack.pos.bo.custom.ProductBO;
import com.devstack.pos.dao.DaoFactory;
import com.devstack.pos.dao.custom.ProductDao;
import com.devstack.pos.dto.request.RequestProductDTO;
import com.devstack.pos.dto.response.ResponseProductDTO;
import com.devstack.pos.entity.Product;
import com.devstack.pos.util.DaoType;
import com.devstack.pos.util.QRGenerator;
import com.google.zxing.WriterException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProductBOImpl implements ProductBO {

    private final ProductDao productDao = DaoFactory.getInstance().getDao(DaoType.PRODUCT);

    @Override
    public boolean createProduct(RequestProductDTO product) throws IOException, WriterException, SQLException, ClassNotFoundException {
       String id =  UUID.randomUUID().toString();
        byte [] qr = QRGenerator.generateBarcodeImageBytes(id,400,100);

        Product p1 = new Product(
                id,
                product.getDescription(),
                product.getUnitPrice(),
                product.getQtyOnHand(),
                qr
        );
        return productDao.save(p1);
    }

    @Override
    public List<ResponseProductDTO> loadAllProducts() throws IOException, WriterException, SQLException, ClassNotFoundException {
        List<ResponseProductDTO> list = new ArrayList<>();
        for(Product p : productDao.findAll()){
            ResponseProductDTO dto = new ResponseProductDTO(
                    p.getProductId(),
                    p.getDescription(),
                    p.getUnitPrice(),
                    p.getQtyOnHand(),
                    p.getQr()
            );
            list.add(dto);
        }
        return list;
    }

    @Override
    public long fillableCount() throws SQLException, ClassNotFoundException {
        return productDao.fillableCount();
    }
}
