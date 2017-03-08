package com.rpalesh.chaemelonj.scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.rpalesh.chaemelonj.ChameleonJ;

/**
 * Created by rpalesh on 13/10/16.
 */
public class Pause implements Disposable {
    public Stage stage;
    private Viewport viewport;

    private Label pauseLabel;

    public Pause(SpriteBatch sb){
        viewport = new FitViewport(ChameleonJ.V_WIDTH, ChameleonJ.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        pauseLabel= new Label("PAUSE", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        Label emptyLabel = new Label("", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        Label emptyLabel2 = new Label("", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        Label emptyLabel3 = new Label("", new Label.LabelStyle(new BitmapFont(), Color.WHITE));


        table.add(emptyLabel).expandX();
        table.row();
        table.add(emptyLabel2).expandX();
        table.row();
        table.add(emptyLabel3).expandX();
        table.row();
        table.add(pauseLabel);

        stage.addActor(table);
    }

    public void update(float dt) {
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}