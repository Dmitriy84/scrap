// RGB LED Control using arrays
const int rgbPins[3] = {9, 10, 11};  // Red, Green, Blue pins
const int delayTime = 100;            // Time to display each color (ms)

// Array of colors: {Red, Green, Blue} values (0-255)
const int colors[][3] = {
  {255,   0,   0},  // Red
  {  0, 255,   0},  // Green
  {  0,   0, 255},  // Blue
  {255, 255,   0},  // Yellow
  {  0, 255, 255},  // Cyan
  {180,   0, 255},  // Purple
  {200, 200, 200}   // White
};

const int numColors = sizeof(colors) / sizeof(colors[0]);

void setup() {
  for (int i = 0; i < 3; i++) {
    pinMode(rgbPins[i], OUTPUT);
    analogWrite(rgbPins[i], 0);  // Start with LED off
  }
}

void loop() {
  for (int i = 0; i < numColors; i++) {
    // Set all three channels using the color array
    for (int channel = 0; channel < 3; channel++) {
      analogWrite(rgbPins[channel], colors[i][channel]);
    }
    delay(delayTime);
  }
}
