package vn.edu.ueh.speedyeats.Presenter;

import vn.edu.ueh.speedyeats.Model.User;
import vn.edu.ueh.speedyeats.my_interface.IUser;
import vn.edu.ueh.speedyeats.my_interface.UserView;

public class UserPresenter implements IUser {
    private User user;
    private UserView callback;

    public UserPresenter(UserView callback){
        user = new User(this);
        this.callback = callback;
    }

    public void HandleGetUsers(String iduser, String email){
        user.HandleGetUsers(iduser, email);
    }
    @Override
    public void OnSucess() {

    }

    @Override
    public void OnFail() {

    }

    @Override
    public void getDataUser(String id, String email, String name, String address, String phone, String date, String gender, String avatar) {
        callback.getDataUser(id, email, name, address, phone, date, gender, avatar);
    }
}
