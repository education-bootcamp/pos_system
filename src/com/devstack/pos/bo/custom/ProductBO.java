package com.devstack.pos.bo.custom;

import com.devstack.pos.dto.request.RequestProductDTO;
import com.devstack.pos.dto.response.ResponseProductDTO;
import com.google.zxing.WriterException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


public interface ProductBO {
    public boolean createProduct(RequestProductDTO product) throws IOException, WriterException, SQLException, ClassNotFoundException;
    public List<ResponseProductDTO> loadAllProducts() throws IOException, WriterException, SQLException, ClassNotFoundException;
    public long fillableCount() throws SQLException, ClassNotFoundException;
}
