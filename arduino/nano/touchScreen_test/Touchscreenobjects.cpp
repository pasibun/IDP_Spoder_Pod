#include "Touchscreenobjects.h"
#include <TFTv2.h>
#include <stdint.h>
#include <string.h>

Button::Button(int x, int y, int width, int height, int buttonnumber, String buttonMode) {
  this->x1 = x;
  this->x2 = x + height;
  this->y1 = y;
  this->y2 = y + width;
  this->width = width;
  this->height = height;
  this->buttonnumber = buttonnumber;
  this->buttonMode = buttonMode;
}

void Button::PrintBlue() {
  Tft.fillRectangle(this->x1, this->y1, this->width, this->height, RED);
}
void Button::PrintRed() {
  Tft.fillRectangle(this->x1, this->y1, this->width, this->height, GREEN);
}
boolean Button::Press(int x, int y) {
  if (this->x1 <= x && this->x2 >= x) {
    if (this->y1 <= y && this->y2 >= y) {
      return true;
    }
    else
    {
      return false;
    }
  }
  else
  {
    return false;
  }
}

int Button::GetButtonNumber(){
  return this->buttonnumber;
}

boolean Button::PrintMode() {
  this->PrintRed();
  Tft.fillRectangle(205, 5, 30, 310, WHITE);
  String mode = "MODE ";
  char const* result;
  mode += this->buttonnumber;
  result = mode.c_str();
  Serial.print(mode);
  Tft.drawStringhor(result, 75, 225, 2, BLUE);
  return true;
}

