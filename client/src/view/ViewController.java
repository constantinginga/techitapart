package view;

import javafx.scene.layout.Region;
import viewmodel.ViewModelFactory;

public abstract class ViewController {
    private Region root;
    private ViewHandler viewHandler;
    private ViewModelFactory viewModelFactory;

    public ViewController() {
    }

    protected abstract void init() throws InterruptedException;

    public void init(ViewHandler viewHandler, ViewModelFactory viewModelFactory, Region root) throws InterruptedException {
        this.root = root;
        this.viewHandler = viewHandler;
        this.viewModelFactory = viewModelFactory;
        init();
    }

    public abstract void reset() throws InterruptedException;

    public Region getRoot() {
        return root;
    }

    public ViewModelFactory getViewModelFactory() {
        return viewModelFactory;
    }

    public ViewHandler getViewHandler() {
        return viewHandler;
    }
}
