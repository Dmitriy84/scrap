// RGB LED Control on pins 9, 10, 11
const int redPin   = 9;
const int greenPin = 10;
const int bluePin  = 11;

void setup() {
    pinMode(redPin,   OUTPUT);
    pinMode(greenPin, OUTPUT);
    pinMode(bluePin,  OUTPUT);

    // Start with LED off
    analogWrite(redPin, 0);
    analogWrite(greenPin, 0);
    analogWrite(bluePin, 0);
}

void loop() {
    // Red
    analogWrite(redPin, 255);
    analogWrite(greenPin, 0);
    analogWrite(bluePin, 0);
    delay(800);

    // Green
    analogWrite(redPin, 0);
    analogWrite(greenPin, 255);
    analogWrite(bluePin, 0);
    delay(800);

    // Blue
    analogWrite(redPin, 0);
    analogWrite(greenPin, 0);
    analogWrite(bluePin, 255);
    delay(800);

    // Yellow
    analogWrite(redPin, 255);
    analogWrite(greenPin, 255);
    analogWrite(bluePin, 0);
    delay(800);

    // Cyan
    analogWrite(redPin, 0);
    analogWrite(greenPin, 255);
    analogWrite(bluePin, 255);
    delay(800);

    // Purple
    analogWrite(redPin, 180);
    analogWrite(greenPin, 0);
    analogWrite(bluePin, 255);
    delay(800);

    // White
    analogWrite(redPin, 200);
    analogWrite(greenPin, 200);
    analogWrite(bluePin, 200);
    delay(800);
}
