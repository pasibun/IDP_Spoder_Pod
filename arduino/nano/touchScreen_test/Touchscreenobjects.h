class Button
{
  public:
    int x1, x2, y1, y2, width, height, buttonnumber;
    char buttonMode[20];
  public:
    Button(int x, int y, int width, int height, int buttonnumber, char *buttonMode);
    void PrintRed();
    void PrintGreen();
    int GetButtonNumber();
    bool Press(int x, int y);
    bool PrintMode();
};

