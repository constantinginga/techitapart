package viewmodel;

import model.LocalModel;

public class EntryViewModel {
    private LocalModel model;
    private ViewState state;

    public EntryViewModel(LocalModel model, ViewState state) {
        this.model = model;
        this.state = state;
    }
}
