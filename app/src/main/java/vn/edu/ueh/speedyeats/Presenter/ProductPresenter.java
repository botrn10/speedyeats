package vn.edu.ueh.speedyeats.Presenter;

import vn.edu.ueh.speedyeats.Model.Product;
import vn.edu.ueh.speedyeats.my_interface.IProduct;
import vn.edu.ueh.speedyeats.my_interface.ProductView;

public class ProductPresenter implements IProduct {

    private Product product;
    private ProductView callback;

    public ProductPresenter(ProductView callback){
        product = new Product(this);
        this.callback = callback;
    }

    public void HandleGetDataProduct(){
        product.HandleGetDataProduct();
    }

    public void HandleGetWithIDProduct(String idproduct){
        product.HandleGetWithIDProduct(idproduct);
    }
    @Override
    public void OnSucess() {

    }

    @Override
    public void OnFail() {

    }

    @Override
    public void getDataProduct(String id, String ten, Long gia, String hinhanh, String loaisp, String mota, Long soluong, String hansudung, Long type, String trongluong) {
        callback.getDataProduct(id, ten, gia, hinhanh, loaisp, mota, soluong, hansudung, type, trongluong);
    }
}
