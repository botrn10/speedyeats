package vn.edu.ueh.speedyeats.Presenter;

import vn.edu.ueh.speedyeats.Model.Story;
import vn.edu.ueh.speedyeats.my_interface.IStory;
import vn.edu.ueh.speedyeats.my_interface.StoryView;

public class StoryPresenter implements IStory {

    private Story story;
    private StoryView callback;

    public StoryPresenter(StoryView callback){
        story = new Story(this);
        this.callback = callback;
    }

    public void HandleGetStory(String iduser){
        story.HandleGetStory(iduser);
    }
    @Override
    public void getDataStory(String noidung) {
        callback.getDataStory(noidung);
    }
}
