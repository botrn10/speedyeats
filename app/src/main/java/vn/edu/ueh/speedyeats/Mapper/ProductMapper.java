package vn.edu.ueh.speedyeats.Mapper;

import vn.edu.ueh.speedyeats.Model.Product;

public class ProductMapper {

    public static Product fromInput(
            String ten,
            String gia,
            String hansudung,
            String trongluong,
            String soluong,
            String type,
            String mota,
            String loaisp,
            String image
    ) {
        Product sp = new Product();

        sp.setTensp(ten);
        sp.setGiatien(Long.parseLong(gia));
        sp.setHansudung(hansudung);
        sp.setTrongluong(trongluong);
        sp.setSoluong(Long.parseLong(soluong));
        sp.setType(Long.parseLong(type));
        sp.setMota(mota);
        sp.setLoaisp(loaisp);
        sp.setHinhanh(image);

        return sp;
    }
}
