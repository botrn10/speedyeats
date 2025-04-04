package vn.edu.ueh.speedyeats.Presenter;

import vn.edu.ueh.speedyeats.Model.Favorite;
import vn.edu.ueh.speedyeats.my_interface.FavoriteView;
import vn.edu.ueh.speedyeats.my_interface.IFavorite;

public class FavoritePresenter implements IFavorite {
    private Favorite favorite;
    private FavoriteView callback;

    public FavoritePresenter(FavoriteView callback){
        this.callback = callback;
        favorite = new Favorite(this);
    }

    public void HandleGetFavorite(String iduser){
        favorite.HandleGetFavorite(iduser);
    }


    @Override
    public void getDataFavorite(String idlove, String idproduct, String iduser) {
        callback.getDataFavorite(idlove, idproduct, iduser);
    }
}
