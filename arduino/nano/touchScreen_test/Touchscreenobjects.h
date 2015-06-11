class Button
{
  public:
    int x1, x2, y1, y2, width, height, buttonnumber;
    String buttonMode;
  public:
    Button(int x, int y, int width, int height, int buttonnumber, String buttonMode);
    void PrintBlue();
    void PrintRed();
    int GetButtonNumber();
    bool Press(int x, int y);
    bool PrintMode();
};

