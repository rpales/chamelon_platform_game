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
 * Created by rpalesh on 18/09/16.
 */
public class Hud implements Disposable {
    public Stage stage;
    private Viewport viewport;

    private static Integer score;
    private static Label scoreLabel;
    private Label scoreDescLabel;

    public Hud(SpriteBatch sb){
        score = 0;

        viewport = new FitViewport(ChameleonJ.V_WIDTH, ChameleonJ.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        scoreLabel = new Label(String.format("%03d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreDescLabel = new Label("SCORE", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(scoreDescLabel).expandX();
        table.row();
        table.add(scoreLabel).expandX();

        stage.addActor(table);
    }

    public void update(float dt){
    }

    public static void addScore(int value){
        score += value;
        scoreLabel.setText(String.format("%03d", score));
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
