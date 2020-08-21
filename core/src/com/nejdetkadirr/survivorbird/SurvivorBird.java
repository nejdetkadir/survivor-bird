package com.nejdetkadirr.survivorbird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;

import java.util.Random;

import javax.swing.JColorChooser;

public class SurvivorBird extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
	Texture bird;
	Texture ufo1;
	Texture ufo2;
	Texture ufo3;
	float birdY=0;
	float birdX=0;
	int gameStart = 0;
	float velocity = 0; //Fast
	float ufoVelocity = 5; //Fast
	float gravity = 0.4f;
	int intNumberofUfo = 4;
	int score=0;
	int scoredUfo = 0;
	Random random;
	Circle birdCircle;
	ShapeRenderer shapeRenderer;
	BitmapFont font;
	float[] ufoX = new float[intNumberofUfo];
	float[] ufoOfset1 = new float[intNumberofUfo];
	float[] ufoOfset2 = new float[intNumberofUfo];
	float[] ufoOfset3 = new float[intNumberofUfo];
	float distance = 0;
	Circle[] ufoCircles1;
	Circle[] ufoCircles2;
	Circle[] ufoCircles3;

	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("background.png");
		bird = new Texture("bird.png");
		ufo1 = new Texture("ufo.png");
		ufo2 = new Texture("ufo.png");
		ufo3 = new Texture("ufo.png");
		birdY=Gdx.graphics.getHeight()/2;
		birdX=Gdx.graphics.getWidth()/4;
		shapeRenderer = new ShapeRenderer();
		font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(4);
		birdCircle = new Circle();
		ufoCircles1 = new Circle[intNumberofUfo];
		ufoCircles2 = new Circle[intNumberofUfo];
		ufoCircles3 = new Circle[intNumberofUfo];

		distance = Gdx.graphics.getWidth()/2;
		random = new Random();
		for (int i = 0; i < intNumberofUfo; i++) {
			ufoOfset1[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
			ufoOfset2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
			ufoOfset2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);


			ufoX[i] = Gdx.graphics.getWidth() - ufo1.getWidth() / 2 + i * distance;
			ufoCircles1[i] = new Circle();
			ufoCircles3[i] = new Circle();
			ufoCircles2[i] = new Circle();

		}
	}

	@Override
	public void render () {
		batch.begin();

		//Draw background
		batch.draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

		if (gameStart == 1) {

			if (ufoX[scoredUfo] < birdX) {
				score++;
				if (scoredUfo < intNumberofUfo-1) {
					scoredUfo++;
				} else {
					scoredUfo=0;
				}
			}


			if (birdY >= Gdx.graphics.getHeight()-Gdx.graphics.getWidth()/13) {
				birdY = Gdx.graphics.getHeight()-Gdx.graphics.getWidth()/13;
				velocity+=gravity;
				birdY-=velocity;
			}

			if(Gdx.input.justTouched() && birdY < Gdx.graphics.getHeight()-Gdx.graphics.getWidth()/13) {

					velocity-=8;
			}

			for (int i = 0; i < intNumberofUfo; i++) {
				if (ufoX[i] < 0) {
					ufoX[i]+=intNumberofUfo*distance;
					ufoOfset1[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					ufoOfset2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					ufoOfset2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
				} else {
					ufoX[i] -= ufoVelocity;
				}
					batch.draw(ufo1,ufoX[i],Gdx.graphics.getHeight()/2  + ufoOfset1[i],Gdx.graphics.getWidth()/13,Gdx.graphics.getHeight()/10);
					batch.draw(ufo2,ufoX[i],Gdx.graphics.getHeight()/2  + ufoOfset2[i],Gdx.graphics.getWidth()/13,Gdx.graphics.getHeight()/10);
					batch.draw(ufo3,ufoX[i],Gdx.graphics.getHeight()/2  + ufoOfset3[i],Gdx.graphics.getWidth()/13,Gdx.graphics.getHeight()/10);

					ufoCircles1[i] = new Circle(ufoX[i]+Gdx.graphics.getWidth()/26,Gdx.graphics.getHeight()/2  + ufoOfset1[i] +Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/26);
					ufoCircles2[i] = new Circle(ufoX[i]+Gdx.graphics.getWidth()/26,Gdx.graphics.getHeight()/2  + ufoOfset2[i] +Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/26);
					ufoCircles3[i] = new Circle(ufoX[i]+Gdx.graphics.getWidth()/26,Gdx.graphics.getHeight()/2  + ufoOfset3[i] +Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/26);
			}



			if (birdY > 0) {
				velocity+=gravity;
				birdY-=velocity;
			} else {
				gameStart=2;
			}

		} else if (gameStart == 0){
			if(Gdx.input.justTouched()) {
				gameStart = 1;
			}
		} else if (gameStart == 2) {
			if(Gdx.input.justTouched()) {
				gameStart = 1;
				birdY=Gdx.graphics.getHeight()/2;
				for (int i = 0; i < intNumberofUfo; i++) {
					ufoOfset1[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					ufoOfset2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					ufoOfset2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);


					ufoX[i] = Gdx.graphics.getWidth() - ufo1.getWidth() / 2 + i * distance;
					ufoCircles1[i] = new Circle();
					ufoCircles3[i] = new Circle();
					ufoCircles2[i] = new Circle();

				}

				velocity=0;
				scoredUfo=0;
				score=0;
			} else {
				font.draw(batch,"Game over! If you want to try again you can touch to screen :)",100,Gdx.graphics.getHeight()/2);
			}
		}



		//Draw bird
		batch.draw(bird,birdX,birdY,Gdx.graphics.getWidth()/13,Gdx.graphics.getHeight()/10);
		font.draw(batch,"Score : " + String.valueOf(score),100,200);
		batch.end();
		birdCircle.set(birdX+Gdx.graphics.getWidth()/26,birdY+Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/26);
		/*
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setColor(Color.BLUE);
		shapeRenderer.circle(birdCircle.x,birdCircle.y,birdCircle.radius);
		*/
		for (int i = 0; i < intNumberofUfo; i++) {
			/*
			shapeRenderer.circle(ufoX[i]+Gdx.graphics.getWidth()/26,Gdx.graphics.getHeight()/2  + ufoOfset1[i] +Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/26);
			shapeRenderer.circle(ufoX[i]+Gdx.graphics.getWidth()/26,Gdx.graphics.getHeight()/2  + ufoOfset2[i] +Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/26);
			shapeRenderer.circle(ufoX[i]+Gdx.graphics.getWidth()/26,Gdx.graphics.getHeight()/2  + ufoOfset3[i] +Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/26);
			*/
			if (Intersector.overlaps(birdCircle,ufoCircles1[i]) || Intersector.overlaps(birdCircle,ufoCircles2[i]) || Intersector.overlaps(birdCircle,ufoCircles3[i])) {
				gameStart = 2;
				//System.out.println("collision detection");
			}
		}
		//shapeRenderer.end();

	}
	
	@Override
	public void dispose () {

	}
}
