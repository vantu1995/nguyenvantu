package com.example.myproject.client;

import java.util.Random;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.RootPanel;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Progressbar;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;

public class Myproject implements EntryPoint {

	Label lblTime;
	Label lblMath;
	IButton btnRight;
	IButton btnLeft;
	IButton btnStart;
	int point;
	private boolean flag;
	MyTimer timer;
	Progressbar prgBar;

	public void onModuleLoad() {
		lblTime = new Label();
		lblMath = new Label();
		btnRight = new IButton();
		btnLeft = new IButton();
		btnStart = new IButton("Báº¯t Ä‘áº§u");
		prgBar = new Progressbar();
		timer = new MyTimer();
		// We can add style names to widgets
		btnRight.addStyleName("btn btn-info btn-lg");
		btnLeft.addStyleName("btn btn-info btn-lg");
		btnStart.addStyleName("btn btn-danger btn-lg");
		btnRight.removeStyleName("gwt-Button");
		btnLeft.removeStyleName("gwt-Button");
		btnStart.removeStyleName("gwt-Button");

		//RootPanel.get("timeLabelContainer").add(lblTime);
		RootPanel.get("mathLabelContainer").add(lblMath);
		RootPanel.get("rightButtonContainer").add(btnRight);
		RootPanel.get("leftButtonContainer").add(btnLeft);
		RootPanel.get("startButtonContainer").add(btnStart);
		RootPanel.get("timeLabelContainer").add(prgBar);
		
		btnStart.focus();
		btnRight.setVisible(false);
		btnLeft.setVisible(false);

		btnStart.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				startMath();
			}
		});

		btnRight.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (flag) {
					trueBtnClick();
				} else {
					falseBtnClick();
				}
			}
		});

		btnLeft.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (!flag) {
					trueBtnClick();
				} else {
					falseBtnClick();
				}
			}
		});
	}

	private void startMath() {
		btnStart.setVisible(false);
		point = 0;

		displayMath();
	}

	private void trueBtnClick() {
		point++;
		displayMath();
	}

	private void falseBtnClick() {
		btnRight.setVisible(false);
		btnLeft.setVisible(false);

		lblMath.setContents("Báº¡n Ä‘Æ°á»£c " + point + " Ä‘iá»ƒm");
		// lblTime.setText("");
		btnStart.setVisible(true);
		if (timer.isRunning()) {
			timer.cancel();
		}
	}

	private void displayMath() {
		btnRight.setVisible(true);
		btnLeft.setVisible(true);

		Random random = new Random();
		int num1 = random.nextInt(20);
		int num2 = random.nextInt(20);
		this.flag = random.nextBoolean();

		int trueSum = num1 + num2;
		int falseSum;
		do {
			falseSum = random.nextInt(20);
		} while (falseSum == trueSum);

		lblMath.setContents(num1 + " + " + num2 + " =");
		if (flag) {
			btnRight.setTitle(trueSum + "");
			btnLeft.setTitle(falseSum + "");
		} else {
			btnRight.setTitle(falseSum + "");
			btnLeft.setTitle(trueSum + "");
		}


		prgBar.setVertical(false);
		prgBar.setHeight(15);
		prgBar.setWidth("100%");
		prgBar.setPercentDone(100);
		
		timer.setTotal(15);	// set 15 * 100 millisecond below is total 1.5 seconds
		timer.schedule(100);
		//timer.scheduleRepeating(100);
	}

	private class MyTimer extends Timer {

		int total;
		int current;
		int percentValue;

		MyTimer() {
		}

		public void run() {
			percentValue = current-- / total;
			prgBar.setPercentDone(percentValue);
			//lblTime.setContents(total-- / (10.0) + "");
			if (percentValue < 0) {
				percentValue = 0;
				falseBtnClick();
			}
			
			if (percentValue != 0) {
				schedule(100);
			}
		}

		public void setTotal(int total) {
			this.total = total;
			this.current = total;
		}
	}
}