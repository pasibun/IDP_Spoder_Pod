#include <TFTv2.h>

#include <TFTv2.h>
#include <stdint.h>
#include <SeeedTouchScreen.h>
#include <SPI.h>
#include "Touchscreenobjects.h"
extern "C" {
#include "msg.h"
}

#define XP A0   // can be a digital pin, this is A3 
#define XM A2   // must be an analog pin, use "An" notation!
#define YP A1   // must be an analog pin, use "An" notation!
#define YM A3   // can be a digital pin, this is A0

#define TS_MINX 116*2
#define TS_MAXX 890*2
#define TS_MINY 83*2
#define TS_MAXY 913*2

int z[10];
int i;
boolean buttonchanged = false;
int buttonnumber;
int currentMode;
boolean errorboolean;
boolean joystickchanged = true;
TouchScreen ts = TouchScreen(XP, YP, XM, YM);

// Init Buttons
Button BT1 = Button(103, 6, 91, 73, 1, "Walking");
Button BT2 = Button(103, 85, 91, 73, 2, "Crab Walk");
Button BT3 = Button(103, 164, 91, 72, 3, "Balloon Red");
Button BT4 = Button(103, 242, 91, 72, 4, "Balloon Blue");
Button BT5 = Button(6, 6, 91, 73, 5, "Spider Gap");
Button BT6 = Button(6, 85, 91, 73, 6, "Pir");
Button BT7 = Button(6, 164, 91, 72, 7, "");
Button BT8 = Button(6, 242, 91, 72, 8, "");

void setup(void) {
  Serial.begin(9600);
  
  Tft.TFTinit();
  Tft.fillRectangle( 0, 0, 240, 320, BLUE); // bgcolor  
  Tft.fillRectangle(200, 0, 60, 320, RED); // red box
  Tft.fillRectangle(205, 5, 30, 310, WHITE); // white box in red box
  Tft.drawStringhor("Select a mode", 75, 225, 2, BLUE); // draw text

  //aanmaken knoppen
  BT1.PrintBlue();
  BT2.PrintBlue();
  BT3.PrintBlue();
  BT4.PrintBlue();
  BT5.PrintBlue();
  BT6.PrintBlue();
  BT7.PrintBlue();
  BT8.PrintBlue();
}

int n = 0;
Buffer buff;
Message m[10];

void loop(void) {
  Point p = ts.getPoint();

  for (i = 0; i < 10; i++) {
    Point zp = ts.getPoint();
    z[i] = zp.z;
  }
  
  //meting  van x en y waarden
  p.x = map(p.x, TS_MINX, TS_MAXX, 0, 240);
  p.y = map(p.y, TS_MINY, TS_MAXY, 0, 320);

  //Checks which button is being pressed
  if (p.z > 100) {
    if (BT1.Press(p.x, p.y) && !buttonchanged) {
      buttonchanged = BT1.PrintMode();
      buttonnumber = BT1.GetButtonNumber();
    }
    if (BT2.Press(p.x, p.y) && !buttonchanged) {
      buttonchanged = BT2.PrintMode();
      buttonnumber = BT2.GetButtonNumber();
    }
    if (BT3.Press(p.x, p.y) && !buttonchanged) {
      buttonchanged = BT3.PrintMode();
      buttonnumber = BT3.GetButtonNumber();
    }
    if (BT4.Press(p.x, p.y) && !buttonchanged) {
      buttonchanged = BT4.PrintMode();
      buttonnumber = BT4.GetButtonNumber();
    }
    if (BT5.Press(p.x, p.y) && !buttonchanged) {
      buttonchanged = BT5.PrintMode();
      buttonnumber = BT5.GetButtonNumber();
    }
    if (BT6.Press(p.x, p.y) && !buttonchanged) {
      buttonchanged = BT6.PrintMode();
      buttonnumber = BT6.GetButtonNumber();
    }
    if (BT7.Press(p.x, p.y) && !buttonchanged) {
      buttonchanged = BT7.PrintMode();
      buttonnumber = BT7.GetButtonNumber();
    }
    if (BT8.Press(p.x, p.y) && !buttonchanged) {
      buttonchanged = BT8.PrintMode();
      buttonnumber = BT8.GetButtonNumber();
    }
  }
  
  for (i = 0; i < 10; i++) {
    if (z[i] != 0) {
      errorboolean = false;
    }
    else {
      errorboolean = true;
    }
  }

  // Check if buttons are released
  if (errorboolean == true) {
    if (buttonchanged == true) {
      if (p.z < 10) {
        switch (buttonnumber) {
          case 1:
            BT1.PrintBlue();
            buttonchanged = false;
            break;
          case 2:
            BT2.PrintBlue();
            buttonchanged = false;
            break;
          case 3:
            BT3.PrintBlue();
            buttonchanged = false;
            break;
          case 4:
            BT4.PrintBlue();
            buttonchanged = false;
            break;
          case 5:
            BT5.PrintBlue();
            buttonchanged = false;
            break;
          case 6:
            BT6.PrintBlue();
            buttonchanged = false;
            break;
          case 7:
            BT7.PrintBlue();
            buttonchanged = false;
            break;
          case 8:
            BT8.PrintBlue();
            buttonchanged = false;
            break;
        }
      }
    }
  }
  
  // Messages
  // Joystick
  m[0].type = 5;
  m[0].id = 0;
  m[0].data = ((byte)analogRead(A4) / 4) << 8 | ((1024 - analogRead(A5) / 4));
  // Buttons
  m[1].type = 6;
  m[1].id = 0;
  m[1].data = ((byte)digitalRead(7) << 8 | digitalRead(8));
  // Mode
  m[2].type = 7;
  m[2].id = 0;
  m[2].data = buttonnumber;
  // Light Sensor
  m[3].type = 3;
  m[3].id = 0;
  m[3].data = digitalRead(2) * 256;

  EncodePacket(&buff, 1, m, 4);
  Serial.write('\0');
  Serial.write(buff.data, buff.size);
  Serial.write('\0');
  Serial.flush();
}
